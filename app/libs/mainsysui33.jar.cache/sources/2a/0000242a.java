package com.android.systemui.screenshot;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.FileUtils;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.provider.MediaStore;
import android.util.Log;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.exifinterface.media.ExifInterface;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAmount;
import java.util.UUID;
import java.util.concurrent.Executor;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ImageExporter.class */
public class ImageExporter {
    public final FeatureFlags mFlags;
    public final ContentResolver mResolver;
    public static final String TAG = LogConfig.logTag(ImageExporter.class);
    public static final Duration PENDING_ENTRY_TTL = Duration.ofHours(24);
    public static final String SCREENSHOTS_PATH = Environment.DIRECTORY_PICTURES + File.separator + Environment.DIRECTORY_SCREENSHOTS;
    public Bitmap.CompressFormat mCompressFormat = Bitmap.CompressFormat.PNG;
    public int mQuality = 100;

    /* renamed from: com.android.systemui.screenshot.ImageExporter$1 */
    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ImageExporter$1.class */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$android$graphics$Bitmap$CompressFormat;

        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:47:0x0041 -> B:61:0x0014). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:49:0x0045 -> B:59:0x001f). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:51:0x0049 -> B:57:0x002a). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:53:0x004d -> B:63:0x0035). Please submit an issue!!! */
        static {
            int[] iArr = new int[Bitmap.CompressFormat.values().length];
            $SwitchMap$android$graphics$Bitmap$CompressFormat = iArr;
            try {
                iArr[Bitmap.CompressFormat.JPEG.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$CompressFormat[Bitmap.CompressFormat.PNG.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$CompressFormat[Bitmap.CompressFormat.WEBP.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$CompressFormat[Bitmap.CompressFormat.WEBP_LOSSLESS.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$android$graphics$Bitmap$CompressFormat[Bitmap.CompressFormat.WEBP_LOSSY.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ImageExporter$ImageExportException.class */
    public static final class ImageExportException extends IOException {
        public ImageExportException(String str) {
            super(str);
        }

        public ImageExportException(String str, Throwable th) {
            super(str, th);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ImageExporter$Result.class */
    public static class Result {
        public String fileName;
        public Bitmap.CompressFormat format;
        public boolean published;
        public UUID requestId;
        public long timestamp;
        public Uri uri;

        public String toString() {
            return "Result{uri=" + this.uri + ", requestId=" + this.requestId + ", fileName='" + this.fileName + "', timestamp=" + this.timestamp + ", format=" + this.format + ", published=" + this.published + '}';
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ImageExporter$Task.class */
    public static class Task {
        public final Bitmap mBitmap;
        public final ZonedDateTime mCaptureTime;
        public final String mFileName;
        public final FeatureFlags mFlags;
        public final Bitmap.CompressFormat mFormat;
        public final UserHandle mOwner;
        public final boolean mPublish;
        public final int mQuality;
        public final UUID mRequestId;
        public final ContentResolver mResolver;

        public Task(ContentResolver contentResolver, UUID uuid, Bitmap bitmap, ZonedDateTime zonedDateTime, String str, Bitmap.CompressFormat compressFormat, int i, boolean z, UserHandle userHandle, FeatureFlags featureFlags) {
            this.mResolver = contentResolver;
            this.mRequestId = uuid;
            this.mBitmap = bitmap;
            this.mCaptureTime = zonedDateTime;
            this.mFormat = compressFormat;
            this.mQuality = i;
            this.mOwner = userHandle;
            this.mFileName = ImageExporter.createFilename(zonedDateTime, compressFormat, str);
            this.mPublish = z;
            this.mFlags = featureFlags;
        }

        public Result execute() throws ImageExportException, InterruptedException {
            Uri uri;
            Trace.beginSection("ImageExporter_execute");
            Result result = new Result();
            try {
                try {
                    uri = ImageExporter.createEntry(this.mResolver, this.mFormat, this.mCaptureTime, this.mFileName, this.mOwner, this.mFlags);
                    try {
                        ImageExporter.throwIfInterrupted();
                        ImageExporter.writeImage(this.mResolver, this.mBitmap, this.mFormat, this.mQuality, uri);
                        ImageExporter.throwIfInterrupted();
                        ImageExporter.writeExif(this.mResolver, uri, this.mRequestId, this.mBitmap.getWidth(), this.mBitmap.getHeight(), this.mCaptureTime);
                        ImageExporter.throwIfInterrupted();
                        if (this.mPublish) {
                            ImageExporter.publishEntry(this.mResolver, uri);
                            result.published = true;
                        }
                        result.timestamp = this.mCaptureTime.toInstant().toEpochMilli();
                        result.requestId = this.mRequestId;
                        result.uri = uri;
                        result.fileName = this.mFileName;
                        result.format = this.mFormat;
                        Trace.endSection();
                        return result;
                    } catch (ImageExportException e) {
                        e = e;
                        if (uri != null) {
                            this.mResolver.delete(uri, null);
                        }
                        throw e;
                    }
                } catch (ImageExportException e2) {
                    e = e2;
                    uri = null;
                }
            } catch (Throwable th) {
                Trace.endSection();
                throw th;
            }
        }

        public String toString() {
            return "export [" + this.mBitmap + "] to [" + this.mFormat + "] at quality " + this.mQuality;
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ImageExporter$$ExternalSyntheticLambda3.run():void] */
    public static /* synthetic */ void $r8$lambda$KIet6qHM6e0ch093NbaNOXoCeoI(CallbackToFutureAdapter.Completer completer, Task task) {
        lambda$export$2(completer, task);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ImageExporter$$ExternalSyntheticLambda1.run():void] */
    public static /* synthetic */ void $r8$lambda$OnqINtOL69BvGyNJ0WTEtPAkevk(ImageExporter imageExporter, File file, Bitmap bitmap, CallbackToFutureAdapter.Completer completer) {
        imageExporter.lambda$exportToRawFile$0(file, bitmap, completer);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ImageExporter$$ExternalSyntheticLambda2.attachCompleter(androidx.concurrent.futures.CallbackToFutureAdapter$Completer):java.lang.Object] */
    /* renamed from: $r8$lambda$qma6MDVus-f-uK9lzVXtmOkUzGk */
    public static /* synthetic */ Object m4223$r8$lambda$qma6MDVusfuK9lzVXtmOkUzGk(Executor executor, Task task, CallbackToFutureAdapter.Completer completer) {
        return lambda$export$3(executor, task, completer);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.screenshot.ImageExporter$$ExternalSyntheticLambda0.attachCompleter(androidx.concurrent.futures.CallbackToFutureAdapter$Completer):java.lang.Object] */
    public static /* synthetic */ Object $r8$lambda$yjrfcN9tIQcZd4RAekYYOMRhu14(ImageExporter imageExporter, Executor executor, File file, Bitmap bitmap, CallbackToFutureAdapter.Completer completer) {
        return imageExporter.lambda$exportToRawFile$1(executor, file, bitmap, completer);
    }

    public ImageExporter(ContentResolver contentResolver, FeatureFlags featureFlags) {
        this.mResolver = contentResolver;
        this.mFlags = featureFlags;
    }

    public static Uri createEntry(ContentResolver contentResolver, Bitmap.CompressFormat compressFormat, ZonedDateTime zonedDateTime, String str, UserHandle userHandle, FeatureFlags featureFlags) throws ImageExportException {
        Trace.beginSection("ImageExporter_createEntry");
        try {
            ContentValues createMetadata = createMetadata(zonedDateTime, compressFormat, str);
            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            Uri uri2 = uri;
            if (featureFlags.isEnabled(Flags.SCREENSHOT_WORK_PROFILE_POLICY)) {
                uri2 = uri;
                if (UserHandle.myUserId() != userHandle.getIdentifier()) {
                    uri2 = ContentProvider.maybeAddUserId(uri, userHandle.getIdentifier());
                }
            }
            Uri insert = contentResolver.insert(uri2, createMetadata);
            if (insert != null) {
                String str2 = TAG;
                Log.d(str2, "Inserted new URI: " + insert);
                Trace.endSection();
                return insert;
            }
            throw new ImageExportException("ContentResolver#insert returned null.");
        } catch (Throwable th) {
            Trace.endSection();
            throw th;
        }
    }

    @VisibleForTesting
    public static String createFilename(ZonedDateTime zonedDateTime, Bitmap.CompressFormat compressFormat, String str) {
        return str != null ? String.format("Screenshot_%1$tY%<tm%<td-%<tH%<tM%<tS_%2$s.%3$s", zonedDateTime, str, fileExtension(compressFormat)) : String.format("Screenshot_%1$tY%<tm%<td-%<tH%<tM%<tS.%2$s", zonedDateTime, fileExtension(compressFormat));
    }

    public static ContentValues createMetadata(ZonedDateTime zonedDateTime, Bitmap.CompressFormat compressFormat, String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("relative_path", SCREENSHOTS_PATH);
        contentValues.put("_display_name", str);
        contentValues.put("mime_type", getMimeType(compressFormat));
        contentValues.put("date_added", Long.valueOf(zonedDateTime.toEpochSecond()));
        contentValues.put("date_modified", Long.valueOf(zonedDateTime.toEpochSecond()));
        contentValues.put("date_expires", Long.valueOf(zonedDateTime.plus((TemporalAmount) PENDING_ENTRY_TTL).toEpochSecond()));
        contentValues.put("is_pending", (Integer) 1);
        return contentValues;
    }

    public static String fileExtension(Bitmap.CompressFormat compressFormat) {
        int i = AnonymousClass1.$SwitchMap$android$graphics$Bitmap$CompressFormat[compressFormat.ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i == 3 || i == 4 || i == 5) {
                    return "webp";
                }
                throw new IllegalArgumentException("Unknown CompressFormat!");
            }
            return "png";
        }
        return "jpg";
    }

    public static String getMimeType(Bitmap.CompressFormat compressFormat) {
        int i = AnonymousClass1.$SwitchMap$android$graphics$Bitmap$CompressFormat[compressFormat.ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i == 3 || i == 4 || i == 5) {
                    return "image/webp";
                }
                throw new IllegalArgumentException("Unknown CompressFormat!");
            }
            return "image/png";
        }
        return "image/jpeg";
    }

    public static /* synthetic */ void lambda$export$2(CallbackToFutureAdapter.Completer completer, Task task) {
        try {
            completer.set(task.execute());
        } catch (ImageExportException | InterruptedException e) {
            completer.setException(e);
        }
    }

    public static /* synthetic */ Object lambda$export$3(Executor executor, final Task task, final CallbackToFutureAdapter.Completer completer) throws Exception {
        executor.execute(new Runnable() { // from class: com.android.systemui.screenshot.ImageExporter$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                ImageExporter.$r8$lambda$KIet6qHM6e0ch093NbaNOXoCeoI(CallbackToFutureAdapter.Completer.this, task);
            }
        });
        return task;
    }

    public /* synthetic */ void lambda$exportToRawFile$0(File file, Bitmap bitmap, CallbackToFutureAdapter.Completer completer) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(this.mCompressFormat, this.mQuality, fileOutputStream);
            completer.set(file);
            fileOutputStream.close();
        } catch (IOException e) {
            if (file.exists()) {
                file.delete();
            }
            completer.setException(e);
        }
    }

    public /* synthetic */ Object lambda$exportToRawFile$1(Executor executor, final File file, final Bitmap bitmap, final CallbackToFutureAdapter.Completer completer) throws Exception {
        executor.execute(new Runnable() { // from class: com.android.systemui.screenshot.ImageExporter$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ImageExporter.$r8$lambda$OnqINtOL69BvGyNJ0WTEtPAkevk(ImageExporter.this, file, bitmap, completer);
            }
        });
        return "Bitmap#compress";
    }

    public static void publishEntry(ContentResolver contentResolver, Uri uri) throws ImageExportException {
        Trace.beginSection("ImageExporter_publishEntry");
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("is_pending", (Integer) 0);
            contentValues.putNull("date_expires");
            if (contentResolver.update(uri, contentValues, null) >= 1) {
                return;
            }
            throw new ImageExportException("Failed to publish entry. ContentResolver#update reported no rows updated.");
        } finally {
            Trace.endSection();
        }
    }

    public static void throwIfInterrupted() throws InterruptedException {
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException();
        }
    }

    public static void updateExifAttributes(ExifInterface exifInterface, UUID uuid, int i, int i2, ZonedDateTime zonedDateTime) {
        exifInterface.setAttribute("ImageUniqueID", uuid.toString());
        exifInterface.setAttribute("Software", "Android " + Build.DISPLAY);
        exifInterface.setAttribute("ImageWidth", Integer.toString(i));
        exifInterface.setAttribute("ImageLength", Integer.toString(i2));
        String format = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ss").format(zonedDateTime);
        String format2 = DateTimeFormatter.ofPattern("SSS").format(zonedDateTime);
        String format3 = DateTimeFormatter.ofPattern("xxx").format(zonedDateTime);
        exifInterface.setAttribute("DateTimeOriginal", format);
        exifInterface.setAttribute("SubSecTimeOriginal", format2);
        exifInterface.setAttribute("OffsetTimeOriginal", format3);
    }

    public static void writeExif(ContentResolver contentResolver, Uri uri, UUID uuid, int i, int i2, ZonedDateTime zonedDateTime) throws ImageExportException {
        Trace.beginSection("ImageExporter_writeExif");
        try {
            try {
                ParcelFileDescriptor openFile = contentResolver.openFile(uri, "rw", null);
                if (openFile == null) {
                    throw new ImageExportException("ContentResolver#openFile returned null.");
                }
                try {
                    ExifInterface exifInterface = new ExifInterface(openFile.getFileDescriptor());
                    updateExifAttributes(exifInterface, uuid, i, i2, zonedDateTime);
                    try {
                        exifInterface.saveAttributes();
                        FileUtils.closeQuietly(openFile);
                        Trace.endSection();
                    } catch (IOException e) {
                        throw new ImageExportException("ExifInterface threw an exception writing to the file descriptor.", e);
                    }
                } catch (IOException e2) {
                    throw new ImageExportException("ExifInterface threw an exception reading from the file descriptor.", e2);
                }
            } catch (FileNotFoundException e3) {
                throw new ImageExportException("ContentResolver#openFile threw an exception.", e3);
            }
        } catch (Throwable th) {
            FileUtils.closeQuietly((AutoCloseable) null);
            Trace.endSection();
            throw th;
        }
    }

    public static void writeImage(ContentResolver contentResolver, Bitmap bitmap, Bitmap.CompressFormat compressFormat, int i, Uri uri) throws ImageExportException {
        Trace.beginSection("ImageExporter_writeImage");
        try {
            try {
                OutputStream openOutputStream = contentResolver.openOutputStream(uri);
                try {
                    SystemClock.elapsedRealtime();
                    if (!bitmap.compress(compressFormat, i, openOutputStream)) {
                        throw new ImageExportException("Bitmap.compress returned false. (Failure unknown)");
                    }
                    if (openOutputStream != null) {
                        openOutputStream.close();
                    }
                } catch (Throwable th) {
                    if (openOutputStream != null) {
                        try {
                            openOutputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (IOException e) {
                throw new ImageExportException("ContentResolver#openOutputStream threw an exception.", e);
            }
        } finally {
            Trace.endSection();
        }
    }

    public ListenableFuture<Result> export(Executor executor, UUID uuid, Bitmap bitmap, UserHandle userHandle, String str) {
        return export(executor, uuid, bitmap, ZonedDateTime.now(), userHandle, str);
    }

    public ListenableFuture<Result> export(final Executor executor, UUID uuid, Bitmap bitmap, ZonedDateTime zonedDateTime, UserHandle userHandle, String str) {
        final Task task = new Task(this.mResolver, uuid, bitmap, zonedDateTime, str, this.mCompressFormat, this.mQuality, true, userHandle, this.mFlags);
        return CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: com.android.systemui.screenshot.ImageExporter$$ExternalSyntheticLambda2
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return ImageExporter.m4223$r8$lambda$qma6MDVusfuK9lzVXtmOkUzGk(executor, task, completer);
            }
        });
    }

    public ListenableFuture<File> exportToRawFile(final Executor executor, final Bitmap bitmap, final File file) {
        return CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: com.android.systemui.screenshot.ImageExporter$$ExternalSyntheticLambda0
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                return ImageExporter.$r8$lambda$yjrfcN9tIQcZd4RAekYYOMRhu14(ImageExporter.this, executor, file, bitmap, completer);
            }
        });
    }
}