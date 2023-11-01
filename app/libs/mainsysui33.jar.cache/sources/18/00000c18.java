package com.android.keyguard;

import android.content.res.Resources;
import android.telephony.SubscriptionManager;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.DejankUtils;
import java.util.function.Supplier;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSecurityModel.class */
public class KeyguardSecurityModel {
    public final boolean mIsPukScreenAvailable;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final LockPatternUtils mLockPatternUtils;

    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSecurityModel$SecurityMode.class */
    public enum SecurityMode {
        Invalid,
        None,
        Pattern,
        Password,
        PIN,
        SimPin,
        SimPuk
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardSecurityModel$$ExternalSyntheticLambda0.get():java.lang.Object] */
    public static /* synthetic */ Integer $r8$lambda$Csy6W7Fs3TE8ZZ37FOoU7VLTN7Y(KeyguardSecurityModel keyguardSecurityModel, int i) {
        return keyguardSecurityModel.lambda$getSecurityMode$0(i);
    }

    public KeyguardSecurityModel(Resources resources, LockPatternUtils lockPatternUtils, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        this.mIsPukScreenAvailable = resources.getBoolean(17891668);
        this.mLockPatternUtils = lockPatternUtils;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
    }

    public /* synthetic */ Integer lambda$getSecurityMode$0(int i) {
        return Integer.valueOf(this.mLockPatternUtils.getActivePasswordQuality(i));
    }

    public SecurityMode getSecurityMode(final int i) {
        if (this.mIsPukScreenAvailable && SubscriptionManager.isValidSubscriptionId(this.mKeyguardUpdateMonitor.getNextSubIdForState(3))) {
            return SecurityMode.SimPuk;
        }
        if (SubscriptionManager.isValidSubscriptionId(this.mKeyguardUpdateMonitor.getNextSubIdForState(2))) {
            return SecurityMode.SimPin;
        }
        int intValue = ((Integer) DejankUtils.whitelistIpcs(new Supplier() { // from class: com.android.keyguard.KeyguardSecurityModel$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return KeyguardSecurityModel.$r8$lambda$Csy6W7Fs3TE8ZZ37FOoU7VLTN7Y(KeyguardSecurityModel.this, i);
            }
        })).intValue();
        if (intValue != 0) {
            if (intValue != 65536) {
                if (intValue == 131072 || intValue == 196608) {
                    return SecurityMode.PIN;
                }
                if (intValue == 262144 || intValue == 327680 || intValue == 393216 || intValue == 524288) {
                    return SecurityMode.Password;
                }
                throw new IllegalStateException("Unknown security quality:" + intValue);
            }
            return SecurityMode.Pattern;
        }
        return SecurityMode.None;
    }
}