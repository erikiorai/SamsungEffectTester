package com.samsung.android.visualeffect.lock.common;

import android.graphics.Bitmap;

/* loaded from: classes.dex */
public interface ISPhysicsJniRenderer {
    void DeInit_PhysicsEngineJNI();

    void Draw_PhysicsEngine();

    void Init_PhysicsEngine(int i, int i2, int i3, int i4);

    void Init_PhysicsEngineJNI();

    void SetTexture(String str, Bitmap bitmap);

    void SetTextureColor(String str, Bitmap bitmap);

    int isEmpty();

    void onCustomEvent(int i, float f);

    void onCustomEvent(int i, float f, float f2, float f3);

    void onKeyEvent(int i);

    void onSensorEvent(int i, float f, float f2, float f3);

    void onSurfaceChangedEvent(int i, int i2);

    void onTouchEvent(int i, int i2, int i3, int[] iArr, int[] iArr2);
}