package com.android.systemui.screenshot;

import android.graphics.Bitmap;
import android.graphics.Rect;
import kotlin.coroutines.Continuation;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ImageCapture.class */
public interface ImageCapture {
    Bitmap captureDisplay(int i, Rect rect);

    Object captureTask(int i, Continuation<? super Bitmap> continuation);
}