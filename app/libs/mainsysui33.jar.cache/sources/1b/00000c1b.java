package com.android.keyguard;

import android.content.res.Resources;
import com.android.internal.widget.LockPatternUtils;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSecurityModel_Factory.class */
public final class KeyguardSecurityModel_Factory implements Factory<KeyguardSecurityModel> {
    public final Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;
    public final Provider<LockPatternUtils> lockPatternUtilsProvider;
    public final Provider<Resources> resourcesProvider;

    public KeyguardSecurityModel_Factory(Provider<Resources> provider, Provider<LockPatternUtils> provider2, Provider<KeyguardUpdateMonitor> provider3) {
        this.resourcesProvider = provider;
        this.lockPatternUtilsProvider = provider2;
        this.keyguardUpdateMonitorProvider = provider3;
    }

    public static KeyguardSecurityModel_Factory create(Provider<Resources> provider, Provider<LockPatternUtils> provider2, Provider<KeyguardUpdateMonitor> provider3) {
        return new KeyguardSecurityModel_Factory(provider, provider2, provider3);
    }

    public static KeyguardSecurityModel newInstance(Resources resources, LockPatternUtils lockPatternUtils, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        return new KeyguardSecurityModel(resources, lockPatternUtils, keyguardUpdateMonitor);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public KeyguardSecurityModel m655get() {
        return newInstance((Resources) this.resourcesProvider.get(), (LockPatternUtils) this.lockPatternUtilsProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get());
    }
}