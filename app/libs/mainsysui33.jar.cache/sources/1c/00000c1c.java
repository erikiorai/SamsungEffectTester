package com.android.keyguard;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSecurityView.class */
public interface KeyguardSecurityView {
    boolean needsInput();

    default void onStartingToHide() {
    }
}