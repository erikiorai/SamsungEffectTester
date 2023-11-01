package com.android.systemui.biometrics;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsControllerOverlayKt.class */
public final class UdfpsControllerOverlayKt {
    public static /* synthetic */ void getSETTING_REMOVE_ENROLLMENT_UI$annotations() {
    }

    public static final boolean isEnrollmentReason(int i) {
        boolean z = true;
        if (i != 1) {
            z = i == 2;
        }
        return z;
    }

    public static final boolean isImportantForAccessibility(int i) {
        boolean z = true;
        if (i != 1) {
            z = true;
            if (i != 2) {
                z = i == 3;
            }
        }
        return z;
    }
}