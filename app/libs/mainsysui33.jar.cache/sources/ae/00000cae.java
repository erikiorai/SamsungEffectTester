package com.android.keyguard;

/* loaded from: mainsysui33.jar:com/android/keyguard/ViewMediatorCallback.class */
public interface ViewMediatorCallback {
    CharSequence consumeCustomMessage();

    int getBouncerPromptReason();

    void keyguardDone(boolean z, int i);

    void keyguardDoneDrawing();

    void keyguardDonePending(boolean z, int i);

    void keyguardGone();

    void onCancelClicked();

    void playTrustedSound();

    void readyForKeyguardDone();

    void resetKeyguard();

    void setNeedsInput(boolean z);

    void userActivity();
}