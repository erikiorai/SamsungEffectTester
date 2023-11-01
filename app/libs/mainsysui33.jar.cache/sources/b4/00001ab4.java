package com.android.systemui.keyguard.shared.model;

import android.hardware.biometrics.BiometricSourceType;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/shared/model/BiometricUnlockSource.class */
public enum BiometricUnlockSource {
    FINGERPRINT_SENSOR,
    FACE_SENSOR;
    
    public static final Companion Companion = new Companion(null);

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/shared/model/BiometricUnlockSource$Companion.class */
    public static final class Companion {

        /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/shared/model/BiometricUnlockSource$Companion$WhenMappings.class */
        public final /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[BiometricSourceType.values().length];
                try {
                    iArr[BiometricSourceType.FINGERPRINT.ordinal()] = 1;
                } catch (NoSuchFieldError e) {
                }
                try {
                    iArr[BiometricSourceType.FACE.ordinal()] = 2;
                } catch (NoSuchFieldError e2) {
                }
                try {
                    iArr[BiometricSourceType.IRIS.ordinal()] = 3;
                } catch (NoSuchFieldError e3) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final BiometricUnlockSource fromBiometricSourceType(BiometricSourceType biometricSourceType) {
            int i = biometricSourceType == null ? -1 : WhenMappings.$EnumSwitchMapping$0[biometricSourceType.ordinal()];
            return i != 1 ? i != 2 ? i != 3 ? null : BiometricUnlockSource.FACE_SENSOR : BiometricUnlockSource.FACE_SENSOR : BiometricUnlockSource.FINGERPRINT_SENSOR;
        }
    }
}