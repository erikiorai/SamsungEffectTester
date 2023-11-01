package com.android.systemui.keyguard;

import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/KeyguardLifecyclesDispatcher_Factory.class */
public final class KeyguardLifecyclesDispatcher_Factory implements Factory<KeyguardLifecyclesDispatcher> {
    public final Provider<ScreenLifecycle> screenLifecycleProvider;
    public final Provider<WakefulnessLifecycle> wakefulnessLifecycleProvider;

    public KeyguardLifecyclesDispatcher_Factory(Provider<ScreenLifecycle> provider, Provider<WakefulnessLifecycle> provider2) {
        this.screenLifecycleProvider = provider;
        this.wakefulnessLifecycleProvider = provider2;
    }

    public static KeyguardLifecyclesDispatcher_Factory create(Provider<ScreenLifecycle> provider, Provider<WakefulnessLifecycle> provider2) {
        return new KeyguardLifecyclesDispatcher_Factory(provider, provider2);
    }

    public static KeyguardLifecyclesDispatcher newInstance(ScreenLifecycle screenLifecycle, WakefulnessLifecycle wakefulnessLifecycle) {
        return new KeyguardLifecyclesDispatcher(screenLifecycle, wakefulnessLifecycle);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public KeyguardLifecyclesDispatcher m2813get() {
        return newInstance((ScreenLifecycle) this.screenLifecycleProvider.get(), (WakefulnessLifecycle) this.wakefulnessLifecycleProvider.get());
    }
}