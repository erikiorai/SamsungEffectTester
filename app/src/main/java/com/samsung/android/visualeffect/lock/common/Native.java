package com.samsung.android.visualeffect.lock.common;

/* loaded from: classes.dex */
public class Native {
    private long mEffectId = 0;

    static {
        System.loadLibrary("stlport");
        System.loadLibrary("secveSrkCommon");
    }

    public static native void pauseAnimation();

    public static native void resumeAnimation();

    public native void clear();

    public native void destroy();

    public native boolean draw();

    public native void drawBgOnly(int i, int i2);

    public native void init(int i, int i2, boolean z);

    public native String[] loadEffect(String str);

    public native void loadModel(String str, byte[] bArr);

    public native void loadTexture(String str, int[] iArr, int i, int i2);

    public native void onTouch(int i, int i2, int i3);

    public native void setParameters(int[] iArr, float[] fArr);

    public native void showAffordance(int i, int i2);

    public native void showUnlock();
}
