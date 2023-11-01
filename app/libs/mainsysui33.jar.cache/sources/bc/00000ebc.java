package com.android.settingslib.users;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.android.internal.util.UserIcons;
import com.android.settingslib.drawable.CircleFramedDrawable;
import com.android.settingslib.utils.ThreadUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

/* loaded from: mainsysui33.jar:com/android/settingslib/users/EditUserPhotoController.class */
public class EditUserPhotoController {
    public final Activity mActivity;
    public final ActivityStarter mActivityStarter;
    public final String mFileAuthority;
    public final ImageView mImageView;
    public final File mImagesDir;
    public Bitmap mNewUserPhotoBitmap;
    public Drawable mNewUserPhotoDrawable;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.users.EditUserPhotoController$$ExternalSyntheticLambda1.run():void] */
    public static /* synthetic */ void $r8$lambda$BBM4g40McoTbhjqBXCkxZ0a70Ps(EditUserPhotoController editUserPhotoController, Uri uri) {
        editUserPhotoController.lambda$onPhotoCropped$4(uri);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.users.EditUserPhotoController$$ExternalSyntheticLambda3.run():void] */
    public static /* synthetic */ void $r8$lambda$O5wYneZajfRNiWcXNManfmB7MiI(EditUserPhotoController editUserPhotoController, Bitmap bitmap) {
        editUserPhotoController.lambda$onDefaultIconSelected$1(bitmap);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.users.EditUserPhotoController$$ExternalSyntheticLambda4.run():void] */
    /* renamed from: $r8$lambda$fh80cxYFkrvq3ueyFxHv6--bi80 */
    public static /* synthetic */ void m1148$r8$lambda$fh80cxYFkrvq3ueyFxHv6bi80(EditUserPhotoController editUserPhotoController, Bitmap bitmap) {
        editUserPhotoController.lambda$onPhotoCropped$3(bitmap);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.users.EditUserPhotoController$$ExternalSyntheticLambda0.onClick(android.view.View):void] */
    /* renamed from: $r8$lambda$l5Ii_wTBlZ-Ga7QDusWqZNnPYVk */
    public static /* synthetic */ void m1149$r8$lambda$l5Ii_wTBlZGa7QDusWqZNnPYVk(EditUserPhotoController editUserPhotoController, View view) {
        editUserPhotoController.lambda$new$0(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.users.EditUserPhotoController$$ExternalSyntheticLambda2.run():void] */
    public static /* synthetic */ void $r8$lambda$sxqVj_zmVlNrmg4tc7B8ZiKmwxY(EditUserPhotoController editUserPhotoController, int i) {
        editUserPhotoController.lambda$onDefaultIconSelected$2(i);
    }

    public EditUserPhotoController(Activity activity, ActivityStarter activityStarter, ImageView imageView, Bitmap bitmap, Drawable drawable, String str) {
        this.mActivity = activity;
        this.mActivityStarter = activityStarter;
        this.mFileAuthority = str;
        File file = new File(activity.getCacheDir(), "multi_user");
        this.mImagesDir = file;
        file.mkdir();
        this.mImageView = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.android.settingslib.users.EditUserPhotoController$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                EditUserPhotoController.m1149$r8$lambda$l5Ii_wTBlZGa7QDusWqZNnPYVk(EditUserPhotoController.this, view);
            }
        });
        this.mNewUserPhotoBitmap = bitmap;
        this.mNewUserPhotoDrawable = drawable;
    }

    public /* synthetic */ void lambda$new$0(View view) {
        showAvatarPicker();
    }

    public /* synthetic */ void lambda$onDefaultIconSelected$2(int i) {
        Resources resources = this.mActivity.getResources();
        final Bitmap convertToBitmapAtUserIconSize = UserIcons.convertToBitmapAtUserIconSize(resources, UserIcons.getDefaultUserIconInColor(resources, i));
        ThreadUtils.postOnMainThread(new Runnable() { // from class: com.android.settingslib.users.EditUserPhotoController$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                EditUserPhotoController.$r8$lambda$O5wYneZajfRNiWcXNManfmB7MiI(EditUserPhotoController.this, convertToBitmapAtUserIconSize);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:77:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x008b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:97:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v31, types: [android.graphics.Bitmap] */
    /* JADX WARN: Type inference failed for: r3v0, types: [android.graphics.Bitmap] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public /* synthetic */ void lambda$onPhotoCropped$4(Uri uri) {
        InputStream inputStream;
        InputStream inputStream2;
        InputStream inputStream3;
        ?? decodeStream;
        try {
            try {
                inputStream2 = this.mActivity.getContentResolver().openInputStream(uri);
                inputStream3 = inputStream2;
            } catch (FileNotFoundException e) {
                e = e;
                inputStream2 = null;
            } catch (Throwable th) {
                th = th;
                inputStream = null;
                if (inputStream != null) {
                }
                throw th;
            }
        } catch (IOException e2) {
            Log.w("EditUserPhotoController", "Cannot close image stream", e2);
        }
        try {
            try {
                decodeStream = BitmapFactory.decodeStream(inputStream2);
                uri = decodeStream;
            } catch (Throwable th2) {
                InputStream inputStream4 = inputStream3;
                th = th2;
                inputStream = inputStream4;
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e3) {
                        Log.w("EditUserPhotoController", "Cannot close image stream", e3);
                    }
                }
                throw th;
            }
        } catch (FileNotFoundException e4) {
            e = e4;
            inputStream3 = inputStream2;
            Log.w("EditUserPhotoController", "Cannot find image file", e);
            uri = null;
            if (inputStream2 != null) {
                inputStream2.close();
                uri = null;
            }
            if (uri == null) {
            }
        }
        if (inputStream2 != null) {
            inputStream2.close();
            uri = decodeStream;
        }
        if (uri == null) {
            final ?? r3 = uri;
            ThreadUtils.postOnMainThread(new Runnable() { // from class: com.android.settingslib.users.EditUserPhotoController$$ExternalSyntheticLambda4
                @Override // java.lang.Runnable
                public final void run() {
                    EditUserPhotoController.m1148$r8$lambda$fh80cxYFkrvq3ueyFxHv6bi80(EditUserPhotoController.this, r3);
                }
            });
        }
    }

    public static Bitmap loadNewUserPhotoBitmap(File file) {
        return BitmapFactory.decodeFile(file.getAbsolutePath());
    }

    public Drawable getNewUserPhotoDrawable() {
        return this.mNewUserPhotoDrawable;
    }

    public boolean onActivityResult(int i, int i2, Intent intent) {
        if (i2 == -1 && i == 1004) {
            if (intent.hasExtra("default_icon_tint_color")) {
                onDefaultIconSelected(intent.getIntExtra("default_icon_tint_color", -1));
                return true;
            } else if (intent.getData() != null) {
                onPhotoCropped(intent.getData());
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public final void onDefaultIconSelected(final int i) {
        try {
            ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.android.settingslib.users.EditUserPhotoController$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    EditUserPhotoController.$r8$lambda$sxqVj_zmVlNrmg4tc7B8ZiKmwxY(EditUserPhotoController.this, i);
                }
            }).get();
        } catch (InterruptedException | ExecutionException e) {
            Log.e("EditUserPhotoController", "Error processing default icon", e);
        }
    }

    public final void onPhotoCropped(final Uri uri) {
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.android.settingslib.users.EditUserPhotoController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                EditUserPhotoController.$r8$lambda$BBM4g40McoTbhjqBXCkxZ0a70Ps(EditUserPhotoController.this, uri);
            }
        });
    }

    /* renamed from: onPhotoProcessed */
    public final void lambda$onPhotoCropped$3(Bitmap bitmap) {
        if (bitmap != null) {
            this.mNewUserPhotoBitmap = bitmap;
            CircleFramedDrawable circleFramedDrawable = CircleFramedDrawable.getInstance(this.mImageView.getContext(), this.mNewUserPhotoBitmap);
            this.mNewUserPhotoDrawable = circleFramedDrawable;
            this.mImageView.setImageDrawable(circleFramedDrawable);
        }
    }

    public void removeNewUserPhotoBitmapFile() {
        new File(this.mImagesDir, "NewUserPhoto.png").delete();
    }

    public File saveNewUserPhotoBitmap() {
        if (this.mNewUserPhotoBitmap == null) {
            return null;
        }
        try {
            File file = new File(this.mImagesDir, "NewUserPhoto.png");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            this.mNewUserPhotoBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return file;
        } catch (IOException e) {
            Log.e("EditUserPhotoController", "Cannot create temp file", e);
            return null;
        }
    }

    public final void showAvatarPicker() {
        Intent intent = new Intent(this.mImageView.getContext(), AvatarPickerActivity.class);
        intent.putExtra("file_authority", this.mFileAuthority);
        this.mActivityStarter.startActivityForResult(intent, 1004);
    }
}