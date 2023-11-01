package androidx.core.content;

import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: mainsysui33.jar:androidx/core/content/FileProvider.class */
public class FileProvider extends ContentProvider {
    public static final String[] COLUMNS = {"_display_name", "_size"};
    public static final File DEVICE_ROOT = new File("/");
    public static final HashMap<String, PathStrategy> sCache = new HashMap<>();
    public int mResourceId = 0;
    public PathStrategy mStrategy;

    /* loaded from: mainsysui33.jar:androidx/core/content/FileProvider$Api21Impl.class */
    public static class Api21Impl {
        public static File[] getExternalMediaDirs(Context context) {
            return context.getExternalMediaDirs();
        }
    }

    /* loaded from: mainsysui33.jar:androidx/core/content/FileProvider$PathStrategy.class */
    public interface PathStrategy {
        File getFileForUri(Uri uri);

        Uri getUriForFile(File file);
    }

    /* loaded from: mainsysui33.jar:androidx/core/content/FileProvider$SimplePathStrategy.class */
    public static class SimplePathStrategy implements PathStrategy {
        public final String mAuthority;
        public final HashMap<String, File> mRoots = new HashMap<>();

        public SimplePathStrategy(String str) {
            this.mAuthority = str;
        }

        public void addRoot(String str, File file) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("Name must not be empty");
            }
            try {
                this.mRoots.put(str, file.getCanonicalFile());
            } catch (IOException e) {
                throw new IllegalArgumentException("Failed to resolve canonical path for " + file, e);
            }
        }

        @Override // androidx.core.content.FileProvider.PathStrategy
        public File getFileForUri(Uri uri) {
            String encodedPath = uri.getEncodedPath();
            int indexOf = encodedPath.indexOf(47, 1);
            String decode = Uri.decode(encodedPath.substring(1, indexOf));
            String decode2 = Uri.decode(encodedPath.substring(indexOf + 1));
            File file = this.mRoots.get(decode);
            if (file == null) {
                throw new IllegalArgumentException("Unable to find configured root for " + uri);
            }
            File file2 = new File(file, decode2);
            try {
                File canonicalFile = file2.getCanonicalFile();
                if (canonicalFile.getPath().startsWith(file.getPath())) {
                    return canonicalFile;
                }
                throw new SecurityException("Resolved path jumped beyond configured root");
            } catch (IOException e) {
                throw new IllegalArgumentException("Failed to resolve canonical path for " + file2);
            }
        }

        @Override // androidx.core.content.FileProvider.PathStrategy
        public Uri getUriForFile(File file) {
            try {
                String canonicalPath = file.getCanonicalPath();
                Map.Entry<String, File> entry = null;
                for (Map.Entry<String, File> entry2 : this.mRoots.entrySet()) {
                    String path = entry2.getValue().getPath();
                    if (canonicalPath.startsWith(path) && (entry == null || path.length() > entry.getValue().getPath().length())) {
                        entry = entry2;
                    }
                }
                if (entry == null) {
                    throw new IllegalArgumentException("Failed to find configured root that contains " + canonicalPath);
                }
                String path2 = entry.getValue().getPath();
                String substring = path2.endsWith("/") ? canonicalPath.substring(path2.length()) : canonicalPath.substring(path2.length() + 1);
                return new Uri.Builder().scheme("content").authority(this.mAuthority).encodedPath(Uri.encode(entry.getKey()) + '/' + Uri.encode(substring, "/")).build();
            } catch (IOException e) {
                throw new IllegalArgumentException("Failed to resolve canonical path for " + file);
            }
        }
    }

    public static File buildPath(File file, String... strArr) {
        int length = strArr.length;
        int i = 0;
        while (true) {
            File file2 = file;
            if (i >= length) {
                return file2;
            }
            String str = strArr[i];
            file = file2;
            if (str != null) {
                file = new File(file2, str);
            }
            i++;
        }
    }

    public static Object[] copyOf(Object[] objArr, int i) {
        Object[] objArr2 = new Object[i];
        System.arraycopy(objArr, 0, objArr2, 0, i);
        return objArr2;
    }

    public static String[] copyOf(String[] strArr, int i) {
        String[] strArr2 = new String[i];
        System.arraycopy(strArr, 0, strArr2, 0, i);
        return strArr2;
    }

    public static XmlResourceParser getFileProviderPathsMetaData(Context context, String str, ProviderInfo providerInfo, int i) {
        if (providerInfo == null) {
            throw new IllegalArgumentException("Couldn't find meta-data for provider with authority " + str);
        }
        if (providerInfo.metaData == null && i != 0) {
            Bundle bundle = new Bundle(1);
            providerInfo.metaData = bundle;
            bundle.putInt("android.support.FILE_PROVIDER_PATHS", i);
        }
        XmlResourceParser loadXmlMetaData = providerInfo.loadXmlMetaData(context.getPackageManager(), "android.support.FILE_PROVIDER_PATHS");
        if (loadXmlMetaData != null) {
            return loadXmlMetaData;
        }
        throw new IllegalArgumentException("Missing android.support.FILE_PROVIDER_PATHS meta-data");
    }

    public static PathStrategy getPathStrategy(Context context, String str, int i) {
        PathStrategy pathStrategy;
        HashMap<String, PathStrategy> hashMap = sCache;
        synchronized (hashMap) {
            PathStrategy pathStrategy2 = hashMap.get(str);
            pathStrategy = pathStrategy2;
            if (pathStrategy2 == null) {
                try {
                    pathStrategy = parsePathStrategy(context, str, i);
                    hashMap.put(str, pathStrategy);
                } catch (IOException e) {
                    throw new IllegalArgumentException("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", e);
                } catch (XmlPullParserException e2) {
                    throw new IllegalArgumentException("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", e2);
                }
            }
        }
        return pathStrategy;
    }

    public static Uri getUriForFile(Context context, String str, File file) {
        return getPathStrategy(context, str, 0).getUriForFile(file);
    }

    public static int modeToMode(String str) {
        int i;
        if ("r".equals(str)) {
            i = 268435456;
        } else if ("w".equals(str) || "wt".equals(str)) {
            i = 738197504;
        } else if ("wa".equals(str)) {
            i = 704643072;
        } else if ("rw".equals(str)) {
            i = 939524096;
        } else if (!"rwt".equals(str)) {
            throw new IllegalArgumentException("Invalid mode: " + str);
        } else {
            i = 1006632960;
        }
        return i;
    }

    public static PathStrategy parsePathStrategy(Context context, String str, int i) throws IOException, XmlPullParserException {
        File file;
        SimplePathStrategy simplePathStrategy = new SimplePathStrategy(str);
        XmlResourceParser fileProviderPathsMetaData = getFileProviderPathsMetaData(context, str, context.getPackageManager().resolveContentProvider(str, RecyclerView.ViewHolder.FLAG_IGNORE), i);
        while (true) {
            int next = fileProviderPathsMetaData.next();
            if (next == 1) {
                return simplePathStrategy;
            }
            if (next == 2) {
                String name = fileProviderPathsMetaData.getName();
                String attributeValue = fileProviderPathsMetaData.getAttributeValue(null, "name");
                String attributeValue2 = fileProviderPathsMetaData.getAttributeValue(null, "path");
                if ("root-path".equals(name)) {
                    file = DEVICE_ROOT;
                } else if ("files-path".equals(name)) {
                    file = context.getFilesDir();
                } else if ("cache-path".equals(name)) {
                    file = context.getCacheDir();
                } else if ("external-path".equals(name)) {
                    file = Environment.getExternalStorageDirectory();
                } else if ("external-files-path".equals(name)) {
                    File[] externalFilesDirs = ContextCompat.getExternalFilesDirs(context, null);
                    file = null;
                    if (externalFilesDirs.length > 0) {
                        file = externalFilesDirs[0];
                    }
                } else if ("external-cache-path".equals(name)) {
                    File[] externalCacheDirs = ContextCompat.getExternalCacheDirs(context);
                    file = null;
                    if (externalCacheDirs.length > 0) {
                        file = externalCacheDirs[0];
                    }
                } else {
                    file = null;
                    if ("external-media-path".equals(name)) {
                        File[] externalMediaDirs = Api21Impl.getExternalMediaDirs(context);
                        file = null;
                        if (externalMediaDirs.length > 0) {
                            file = externalMediaDirs[0];
                        }
                    }
                }
                if (file != null) {
                    simplePathStrategy.addRoot(attributeValue, buildPath(file, attributeValue2));
                }
            }
        }
    }

    @Override // android.content.ContentProvider
    public void attachInfo(Context context, ProviderInfo providerInfo) {
        super.attachInfo(context, providerInfo);
        if (providerInfo.exported) {
            throw new SecurityException("Provider must not be exported");
        }
        if (!providerInfo.grantUriPermissions) {
            throw new SecurityException("Provider must grant uri permissions");
        }
        String str = providerInfo.authority.split(";")[0];
        HashMap<String, PathStrategy> hashMap = sCache;
        synchronized (hashMap) {
            hashMap.remove(str);
        }
        this.mStrategy = getPathStrategy(context, str, this.mResourceId);
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        return this.mStrategy.getFileForUri(uri).delete() ? 1 : 0;
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        File fileForUri = this.mStrategy.getFileForUri(uri);
        int lastIndexOf = fileForUri.getName().lastIndexOf(46);
        if (lastIndexOf >= 0) {
            String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileForUri.getName().substring(lastIndexOf + 1));
            return mimeTypeFromExtension != null ? mimeTypeFromExtension : "application/octet-stream";
        }
        return "application/octet-stream";
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("No external inserts");
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        return true;
    }

    @Override // android.content.ContentProvider
    @SuppressLint({"UnknownNullness"})
    public ParcelFileDescriptor openFile(Uri uri, String str) throws FileNotFoundException {
        return ParcelFileDescriptor.open(this.mStrategy.getFileForUri(uri), modeToMode(str));
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        int i;
        int i2;
        File fileForUri = this.mStrategy.getFileForUri(uri);
        String queryParameter = uri.getQueryParameter("displayName");
        String[] strArr3 = strArr;
        if (strArr == null) {
            strArr3 = COLUMNS;
        }
        String[] strArr4 = new String[strArr3.length];
        Object[] objArr = new Object[strArr3.length];
        int length = strArr3.length;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            int i5 = i4;
            if (i3 >= length) {
                String[] copyOf = copyOf(strArr4, i5);
                Object[] copyOf2 = copyOf(objArr, i5);
                MatrixCursor matrixCursor = new MatrixCursor(copyOf, 1);
                matrixCursor.addRow(copyOf2);
                return matrixCursor;
            }
            String str3 = strArr3[i3];
            if ("_display_name".equals(str3)) {
                strArr4[i5] = "_display_name";
                int i6 = i5 + 1;
                objArr[i5] = queryParameter == null ? fileForUri.getName() : queryParameter;
                i2 = i6;
            } else {
                i = i5;
                if ("_size".equals(str3)) {
                    strArr4[i5] = "_size";
                    objArr[i5] = Long.valueOf(fileForUri.length());
                    i2 = i5 + 1;
                } else {
                    i3++;
                    i4 = i;
                }
            }
            i = i2;
            i3++;
            i4 = i;
        }
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        throw new UnsupportedOperationException("No external updates");
    }
}