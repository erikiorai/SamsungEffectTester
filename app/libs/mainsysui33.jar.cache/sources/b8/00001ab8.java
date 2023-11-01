package com.android.systemui.keyguard.shared.model;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/shared/model/DozeStateModel.class */
public enum DozeStateModel {
    UNINITIALIZED,
    INITIALIZED,
    DOZE,
    DOZE_SUSPEND_TRIGGERS,
    DOZE_AOD,
    DOZE_REQUEST_PULSE,
    DOZE_PULSING,
    DOZE_PULSING_BRIGHT,
    DOZE_PULSE_DONE,
    FINISH,
    DOZE_AOD_PAUSED,
    DOZE_AOD_PAUSING,
    DOZE_AOD_DOCKED;
    
    public static final Companion Companion = new Companion(null);

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/shared/model/DozeStateModel$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final boolean isDozeOff(DozeStateModel dozeStateModel) {
            return dozeStateModel == DozeStateModel.UNINITIALIZED || dozeStateModel == DozeStateModel.FINISH;
        }
    }
}