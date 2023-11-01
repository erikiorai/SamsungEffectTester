package com.android.systemui.biometrics;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/AlternateUdfpsTouchProvider.class */
public interface AlternateUdfpsTouchProvider {
    void onPointerDown(long j, int i, int i2, float f, float f2);

    void onPointerUp(long j);

    void onUiReady();
}