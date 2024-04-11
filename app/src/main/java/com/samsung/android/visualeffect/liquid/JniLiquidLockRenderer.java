package com.samsung.android.visualeffect.liquid;

import android.graphics.Bitmap;
import android.util.Log;

/* loaded from: classes.dex */
public class JniLiquidLockRenderer {
    public int mNativeJNI = -1;
    private final String TAG = "Liquid_JNI";

    private static native void native_DeInit_JNI(int j);

    private static native void native_Draw_PhysicsEngine(int j);

    private static native int native_Init_JNI();

    private static native void native_Init_PhysicsEngine(int j, int i, int i2, int i3, int i4);

    private static native void native_SetTexture(int j, String str, Bitmap bitmap);

    private static native void native_SetTextureColor(int j, String str, Bitmap bitmap);

    private static native int native_isEmpty(int j);

    private static native void native_onCustomEvent(int j, int i, float f);

    private static native void native_onKeyEvent(int j, int i);

    private static native void native_onSensorEvent(int j, int i, float f, float f2, float f3);

    private static native void native_onSurfaceChangedEvent(int j, int i, int i2);

    private static native void native_onTouchEvent(int j, int i, int i2, int i3, int[] iArr, int[] iArr2);

    static {
        System.loadLibrary("LiquidLockEffect");
    }

    public JniLiquidLockRenderer() {
        Log.e(TAG, "JniLiquidLockRenderer is called");
    }

    public void Init_PhysicsEngineJNI() {
        Log.e(TAG, "native_Init_JNI is called");
        this.mNativeJNI = native_Init_JNI();
    }

    public void DeInit_PhysicsEngineJNI() {
        native_DeInit_JNI(this.mNativeJNI);
    }

    public void Init_PhysicsEngine(int tabletmode, int quality, int width, int height) {
        native_Init_PhysicsEngine(this.mNativeJNI, tabletmode, quality, width, height);
    }

    public void onSurfaceChangedEvent(int width, int height) {
        native_onSurfaceChangedEvent(this.mNativeJNI, width, height);
    }

    public void Draw_PhysicsEngine() {
        native_Draw_PhysicsEngine(this.mNativeJNI);
    }

    public void onTouchEvent(int touchID, int touchNum, int eventType, int[] x, int[] y) {
        native_onTouchEvent(this.mNativeJNI, touchID, touchNum, eventType, x, y);
    }

    public void onSensorEvent(int sensorType, float xValue, float yValue, float zValue) {
        native_onSensorEvent(this.mNativeJNI, sensorType, xValue, yValue, zValue);
    }

    public void SetTexture(String textureName, Bitmap jbitmap) {
        Log.i(TAG, "SetBitmapData ");
        native_SetTexture(this.mNativeJNI, textureName, jbitmap);
    }

    public void SetTextureColor(String textureName, Bitmap jbitmap) {
        Log.i(TAG, "SetTextureColor ");
        native_SetTextureColor(this.mNativeJNI, textureName, jbitmap);
    }

    public void onKeyEvent(int keyID) {
        native_onKeyEvent(this.mNativeJNI, keyID);
    }

    public void onCustomEvent(int keyID, float value) {
        native_onCustomEvent(this.mNativeJNI, keyID, value);
    }

    public int isEmpty() {
        return native_isEmpty(this.mNativeJNI);
    }
}