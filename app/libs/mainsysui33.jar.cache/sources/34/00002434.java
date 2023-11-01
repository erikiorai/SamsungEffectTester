package com.android.systemui.screenshot;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ImageLoader.class */
public class ImageLoader {
    public final ContentResolver mResolver;

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ImageLoader$Result.class */
    public static class Result {
        public Bitmap bitmap;
        public File fileName;
        public Uri uri;

        public String toString() {
            return "Result{uri=" + this.uri + ", fileName=" + this.fileName + ", bitmap=" + this.bitmap + '}';
        }
    }

    public ImageLoader(ContentResolver contentResolver) {
        this.mResolver = contentResolver;
    }

    public static /* synthetic */ Object lambda$load$1(File file, CallbackToFutureAdapter.Completer completer) throws Exception {
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            Result result = new Result();
            result.fileName = file;
            result.bitmap = BitmapFactory.decodeStream(bufferedInputStream);
            completer.set(result);
            bufferedInputStream.close();
            return "BitmapFactory#decodeStream";
        } catch (IOException e) {
            completer.setException(e);
            return "BitmapFactory#decodeStream";
        }
    }

    public ListenableFuture<Result> load(final File file) {
        return CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: com.android.systemui.screenshot.ImageLoader$$ExternalSyntheticLambda0
            @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
            public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                Object lambda$load$1;
                lambda$load$1 = ImageLoader.lambda$load$1(file, completer);
                return lambda$load$1;
            }
        });
    }
}