package com.android.systemui.keyguard.shared.model;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/shared/model/WakefulnessState.class */
public enum WakefulnessState {
    ASLEEP,
    STARTING_TO_WAKE,
    AWAKE,
    STARTING_TO_SLEEP;
    
    public static final Companion Companion = new Companion(null);

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/shared/model/WakefulnessState$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final WakefulnessState fromWakefulnessLifecycleInt(int i) {
            WakefulnessState wakefulnessState;
            if (i == 0) {
                wakefulnessState = WakefulnessState.ASLEEP;
            } else if (i == 1) {
                wakefulnessState = WakefulnessState.STARTING_TO_WAKE;
            } else if (i == 2) {
                wakefulnessState = WakefulnessState.AWAKE;
            } else if (i != 3) {
                throw new IllegalArgumentException("Invalid Wakefulness value: " + i);
            } else {
                wakefulnessState = WakefulnessState.STARTING_TO_SLEEP;
            }
            return wakefulnessState;
        }
    }
}