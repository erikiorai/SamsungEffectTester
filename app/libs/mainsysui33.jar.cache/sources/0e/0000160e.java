package com.android.systemui.doze;

import com.android.keyguard.KeyguardUpdateMonitor;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeAuthRemover_Factory.class */
public final class DozeAuthRemover_Factory implements Factory<DozeAuthRemover> {
    public final Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;

    public DozeAuthRemover_Factory(Provider<KeyguardUpdateMonitor> provider) {
        this.keyguardUpdateMonitorProvider = provider;
    }

    public static DozeAuthRemover_Factory create(Provider<KeyguardUpdateMonitor> provider) {
        return new DozeAuthRemover_Factory(provider);
    }

    public static DozeAuthRemover newInstance(KeyguardUpdateMonitor keyguardUpdateMonitor) {
        return new DozeAuthRemover(keyguardUpdateMonitor);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DozeAuthRemover m2405get() {
        return newInstance((KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get());
    }
}