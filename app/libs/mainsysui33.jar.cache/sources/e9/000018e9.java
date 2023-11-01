package com.android.systemui.keyguard;

import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/LifecycleScreenStatusProvider_Factory.class */
public final class LifecycleScreenStatusProvider_Factory implements Factory<LifecycleScreenStatusProvider> {
    public final Provider<ScreenLifecycle> screenLifecycleProvider;

    public LifecycleScreenStatusProvider_Factory(Provider<ScreenLifecycle> provider) {
        this.screenLifecycleProvider = provider;
    }

    public static LifecycleScreenStatusProvider_Factory create(Provider<ScreenLifecycle> provider) {
        return new LifecycleScreenStatusProvider_Factory(provider);
    }

    public static LifecycleScreenStatusProvider newInstance(ScreenLifecycle screenLifecycle) {
        return new LifecycleScreenStatusProvider(screenLifecycle);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LifecycleScreenStatusProvider m2905get() {
        return newInstance((ScreenLifecycle) this.screenLifecycleProvider.get());
    }
}