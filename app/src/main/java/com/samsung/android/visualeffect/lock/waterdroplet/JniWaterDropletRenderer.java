package com.samsung.android.visualeffect.lock.waterdroplet;

import android.graphics.Bitmap;
import android.util.Log;

import com.samsung.android.visualeffect.lock.common.ISPhysicsJniRenderer;

/* loaded from: classes.dex */
public class JniWaterDropletRenderer implements ISPhysicsJniRenderer {
    private final String TAG = "WaterDroplet_JniWaterDropletRenderer";

    private static native void native_DeInit_JNI();

    private static native void native_Draw_PhysicsEngine();

    private static native void native_Init_JNI();

    private static native void native_Init_PhysicsEngine(int i, int i2, int i3, int i4);

    private static native void native_SetTexture(String str, Bitmap bitmap);

    private static native void native_SetTextureColor(String str, Bitmap bitmap);

    private static native int native_isEmpty();

    private static native void native_onCustomEvent(int i, float f);

    private static native void native_onCustomEventVec(int i, float f, float f2, float f3);

    private static native void native_onKeyEvent(int i);

    private static native void native_onSensorEvent(int i, float f, float f2, float f3);

    private static native void native_onSurfaceChangedEvent(int i, int i2);

    private static native void native_onTouchEvent(int i, int i2, int i3, int[] iArr, int[] iArr2);

    static {
        System.loadLibrary("WaterDropletEffect");
    }

    public JniWaterDropletRenderer() {
        Log.e("WaterDroplet_JniWaterDropletRenderer", "JniWaterDropletRenderer is called");
    }

    public void Init_PhysicsEngineJNI() {
        Log.e("WaterDroplet_JniWaterDropletRenderer", "native_Init_JNI is called");
        Log.e("<< JOOON >> ", "Call the native_Init_JNI");
        native_Init_JNI();
    }

    public void DeInit_PhysicsEngineJNI() {
        native_DeInit_JNI();
    }

    public void Init_PhysicsEngine(int tabletmode, int quality, int width, int height) {
        native_Init_PhysicsEngine(tabletmode, quality, width, height);
    }

    public void onSurfaceChangedEvent(int width, int height) {
        native_onSurfaceChangedEvent(width, height);
    }

    public void Draw_PhysicsEngine() {
        native_Draw_PhysicsEngine();
    }

    public void onTouchEvent(int touchID, int touchNum, int eventType, int[] x, int[] y) {
        native_onTouchEvent(touchID, touchNum, eventType, x, y);
    }

    public void onSensorEvent(int sensorType, float xValue, float yValue, float zValue) {
        native_onSensorEvent(sensorType, xValue, yValue, zValue);
    }

    public void SetTexture(String textureName, Bitmap jbitmap) {
        Log.i("WaterDroplet_JniWaterDropletRenderer", "SetBitmapData ");
        native_SetTexture(textureName, jbitmap);
    }

    public void SetTextureColor(String textureName, Bitmap jbitmap) {
        Log.i("WaterDroplet_JniWaterDropletRenderer", "SetBitmapData ");
        native_SetTextureColor(textureName, jbitmap);
    }

    public void onKeyEvent(int keyID) {
        native_onKeyEvent(keyID);
    }

    public void onCustomEvent(int keyID, float value) {
        native_onCustomEvent(keyID, value);
    }

    public void onCustomEvent(int keyID, float xValue, float yValue, float zValue) {
        native_onCustomEventVec(keyID, xValue, yValue, zValue);
    }

    public int isEmpty() {
        return native_isEmpty();
    }
}