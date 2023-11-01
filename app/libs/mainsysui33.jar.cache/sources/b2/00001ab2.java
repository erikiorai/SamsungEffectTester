package com.android.systemui.keyguard.shared.model;

import java.util.Set;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/shared/model/BiometricUnlockModel.class */
public enum BiometricUnlockModel {
    NONE,
    WAKE_AND_UNLOCK,
    WAKE_AND_UNLOCK_PULSING,
    SHOW_BOUNCER,
    ONLY_WAKE,
    UNLOCK_COLLAPSING,
    DISMISS_BOUNCER,
    WAKE_AND_UNLOCK_FROM_DREAM;
    
    public static final Companion Companion = new Companion(null);
    public static final Set<BiometricUnlockModel> wakeAndUnlockModes;

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/shared/model/BiometricUnlockModel$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final boolean isWakeAndUnlock(BiometricUnlockModel biometricUnlockModel) {
            return BiometricUnlockModel.wakeAndUnlockModes.contains(biometricUnlockModel);
        }
    }

    static {
        BiometricUnlockModel biometricUnlockModel;
        BiometricUnlockModel biometricUnlockModel2;
        wakeAndUnlockModes = SetsKt__SetsKt.setOf(new BiometricUnlockModel[]{r0, biometricUnlockModel2, biometricUnlockModel});
    }
}