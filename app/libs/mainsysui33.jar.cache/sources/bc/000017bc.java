package com.android.systemui.flags;

import com.android.systemui.keyguard.WakefulnessLifecycle;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/FeatureFlagsDebugRestarter_Factory.class */
public final class FeatureFlagsDebugRestarter_Factory implements Factory<FeatureFlagsDebugRestarter> {
    public final Provider<SystemExitRestarter> systemExitRestarterProvider;
    public final Provider<WakefulnessLifecycle> wakefulnessLifecycleProvider;

    public FeatureFlagsDebugRestarter_Factory(Provider<WakefulnessLifecycle> provider, Provider<SystemExitRestarter> provider2) {
        this.wakefulnessLifecycleProvider = provider;
        this.systemExitRestarterProvider = provider2;
    }

    public static FeatureFlagsDebugRestarter_Factory create(Provider<WakefulnessLifecycle> provider, Provider<SystemExitRestarter> provider2) {
        return new FeatureFlagsDebugRestarter_Factory(provider, provider2);
    }

    public static FeatureFlagsDebugRestarter newInstance(WakefulnessLifecycle wakefulnessLifecycle, SystemExitRestarter systemExitRestarter) {
        return new FeatureFlagsDebugRestarter(wakefulnessLifecycle, systemExitRestarter);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public FeatureFlagsDebugRestarter m2682get() {
        return newInstance((WakefulnessLifecycle) this.wakefulnessLifecycleProvider.get(), (SystemExitRestarter) this.systemExitRestarterProvider.get());
    }
}