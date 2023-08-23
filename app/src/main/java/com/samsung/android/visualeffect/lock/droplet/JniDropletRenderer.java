package com.samsung.android.visualeffect.lock.droplet;

import android.graphics.Bitmap;
import android.util.Log;
import com.samsung.android.visualeffect.lock.common.ISPhysicsJniRenderer;

/* loaded from: classes.dex */
public class JniDropletRenderer implements ISPhysicsJniRenderer {
    private static String type;
    private long mNativeJNI = -1;
    private String TAG;

    private static native void native_DeInit_JNI(long j);

    private static native void native_Draw_PhysicsEngine(long j);

    private static native long native_Init_JNI();

    private static native void native_Init_PhysicsEngine(long j, int i, int i2, int i3, int i4);

    private static native void native_SetTexture(long j, String str, Bitmap bitmap);

    private static native void native_SetTextureColor(long j, String str, Bitmap bitmap);

    private static native int native_isEmpty(long j);

    private static native void native_onCustomEvent(long j, int i, float f);

    private static native void native_onCustomEventVec(long j, int i, float f, float f2, float f3);

    private static native void native_onKeyEvent(long j, int i);

    private static native void native_onSensorEvent(long j, int i, float f, float f2, float f3);

    private static native void native_onSurfaceChangedEvent(long j, int i, int i2);

    private static native void native_onTouchEvent(long j, int i, int i2, int i3, int[] iArr, int[] iArr2);

    static {
        System.loadLibrary(type + "DropletEffect");
    }

    public JniDropletRenderer(String type) {
        JniDropletRenderer.type = type;
        this.TAG = "Jni" + type + "DropletRenderer";
        Log.e(this.TAG, "Jni" + type + "DropletRenderer is called");
    }

    @Override // com.samsung.android.visualeffect.lock.common.ISPhysicsJniRenderer
    public void Init_PhysicsEngineJNI() {
        Log.e(this.TAG, "native_Init_JNI is called");
        this.mNativeJNI = native_Init_JNI();
    }

    @Override // com.samsung.android.visualeffect.lock.common.ISPhysicsJniRenderer
    public void DeInit_PhysicsEngineJNI() {
        native_DeInit_JNI(this.mNativeJNI);
    }

    @Override // com.samsung.android.visualeffect.lock.common.ISPhysicsJniRenderer
    public void Init_PhysicsEngine(int tabletmode, int quality, int width, int height) {
        native_Init_PhysicsEngine(this.mNativeJNI, tabletmode, quality, width, height);
    }

    @Override // com.samsung.android.visualeffect.lock.common.ISPhysicsJniRenderer
    public void onSurfaceChangedEvent(int width, int height) {
        native_onSurfaceChangedEvent(this.mNativeJNI, width, height);
    }

    @Override // com.samsung.android.visualeffect.lock.common.ISPhysicsJniRenderer
    public void Draw_PhysicsEngine() {
        native_Draw_PhysicsEngine(this.mNativeJNI);
    }

    @Override // com.samsung.android.visualeffect.lock.common.ISPhysicsJniRenderer
    public void onTouchEvent(int touchID, int touchNum, int eventType, int[] x, int[] y) {
        native_onTouchEvent(this.mNativeJNI, touchID, touchNum, eventType, x, y);
    }

    @Override // com.samsung.android.visualeffect.lock.common.ISPhysicsJniRenderer
    public void onSensorEvent(int sensorType, float xValue, float yValue, float zValue) {
        native_onSensorEvent(this.mNativeJNI, sensorType, xValue, yValue, zValue);
    }

    @Override // com.samsung.android.visualeffect.lock.common.ISPhysicsJniRenderer
    public void SetTexture(String textureName, Bitmap jbitmap) {
        Log.i(this.TAG, "SetBitmapData ");
        native_SetTexture(this.mNativeJNI, textureName, jbitmap);
    }

    @Override // com.samsung.android.visualeffect.lock.common.ISPhysicsJniRenderer
    public void SetTextureColor(String textureName, Bitmap jbitmap) {
        Log.i(this.TAG, "SetBitmapData ");
        native_SetTextureColor(this.mNativeJNI, textureName, jbitmap);
    }

    @Override // com.samsung.android.visualeffect.lock.common.ISPhysicsJniRenderer
    public void onKeyEvent(int keyID) {
        native_onKeyEvent(this.mNativeJNI, keyID);
    }

    @Override // com.samsung.android.visualeffect.lock.common.ISPhysicsJniRenderer
    public void onCustomEvent(int keyID, float value) {
        native_onCustomEvent(this.mNativeJNI, keyID, value);
    }

    @Override // com.samsung.android.visualeffect.lock.common.ISPhysicsJniRenderer
    public void onCustomEvent(int keyID, float xValue, float yValue, float zValue) {
        native_onCustomEventVec(this.mNativeJNI, keyID, xValue, yValue, zValue);
    }

    @Override // com.samsung.android.visualeffect.lock.common.ISPhysicsJniRenderer
    public int isEmpty() {
        return native_isEmpty(this.mNativeJNI);
    }
}