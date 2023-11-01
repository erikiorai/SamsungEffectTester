package com.android.systemui.keyguard.shared.model;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/shared/model/WakeSleepReason.class */
public enum WakeSleepReason {
    POWER_BUTTON,
    OTHER;
    
    public static final Companion Companion = new Companion(null);

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/shared/model/WakeSleepReason$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final WakeSleepReason fromPowerManagerSleepReason(int i) {
            return i == 4 ? WakeSleepReason.POWER_BUTTON : WakeSleepReason.OTHER;
        }

        public final WakeSleepReason fromPowerManagerWakeReason(int i) {
            return i == 1 ? WakeSleepReason.POWER_BUTTON : WakeSleepReason.OTHER;
        }
    }
}