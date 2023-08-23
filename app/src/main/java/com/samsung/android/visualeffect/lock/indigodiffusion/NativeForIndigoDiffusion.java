package com.samsung.android.visualeffect.lock.indigodiffusion;

import android.graphics.Bitmap;

/* loaded from: classes.dex */
public class NativeForIndigoDiffusion {
    public static native void LoadTextures(Bitmap bitmap, int i);

    public static native void clearInkValue();

    public static native int getClearInkValue();

    public static native void initWaters(float[] fArr, short[] sArr, int i, int i2, int i3, int i4, int i5);

    public static native int move(float[] fArr, float[] fArr2, int i, int i2, int i3, int i4, int i5, int i6, boolean z, float f, float f2);

    public static native void onDrawFrame(float[] fArr, float[] fArr2, short[] sArr, int i, int i2, int i3, float[] fArr3, int i4, int i5, int i6, int i7, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, boolean z);

    public static native void onInitGPU();

    public static native void onInitSetting(int i, int i2, boolean z);

    public static native void onInitUVHBuffer();

    public static native void onTouch(int i, int i2, int i3, float f);

    public static native void ripple(float[] fArr, int i, int i2, int i3, int i4, float f, float f2, float f3);

    static {
        System.loadLibrary("secveIndigoDiffusion");
    }
}