package com.android.systemui.biometrics;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/AuthDialogCallback.class */
public interface AuthDialogCallback {
    void onDeviceCredentialPressed(long j);

    void onDialogAnimatedIn(long j);

    void onDismissed(int i, byte[] bArr, long j);

    void onSystemEvent(int i, long j);

    void onTryAgainPressed(long j);
}