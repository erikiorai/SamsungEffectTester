package com.android.keyguard.logging;

/* loaded from: mainsysui33.jar:com/android/keyguard/logging/BiometricUnlockLoggerKt.class */
public final class BiometricUnlockLoggerKt {
    public static final String wakeAndUnlockModeToString(int i) {
        String str;
        switch (i) {
            case 0:
                str = "MODE_NONE";
                break;
            case 1:
                str = "MODE_WAKE_AND_UNLOCK";
                break;
            case 2:
                str = "MODE_WAKE_AND_UNLOCK_PULSING";
                break;
            case 3:
                str = "MODE_SHOW_BOUNCER";
                break;
            case 4:
                str = "MODE_ONLY_WAKE";
                break;
            case 5:
                str = "MODE_UNLOCK_COLLAPSING";
                break;
            case 6:
                str = "MODE_WAKE_AND_UNLOCK_FROM_DREAM";
                break;
            case 7:
                str = "MODE_DISMISS_BOUNCER";
                break;
            default:
                str = "UNKNOWN{" + i + "}";
                break;
        }
        return str;
    }
}