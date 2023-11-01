package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.settings.UserTracker;
import dagger.internal.Factory;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceProviderClientFactoryImpl_Factory.class */
public final class KeyguardQuickAffordanceProviderClientFactoryImpl_Factory implements Factory<KeyguardQuickAffordanceProviderClientFactoryImpl> {
    public final Provider<CoroutineDispatcher> backgroundDispatcherProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public KeyguardQuickAffordanceProviderClientFactoryImpl_Factory(Provider<UserTracker> provider, Provider<CoroutineDispatcher> provider2) {
        this.userTrackerProvider = provider;
        this.backgroundDispatcherProvider = provider2;
    }

    public static KeyguardQuickAffordanceProviderClientFactoryImpl_Factory create(Provider<UserTracker> provider, Provider<CoroutineDispatcher> provider2) {
        return new KeyguardQuickAffordanceProviderClientFactoryImpl_Factory(provider, provider2);
    }

    public static KeyguardQuickAffordanceProviderClientFactoryImpl newInstance(UserTracker userTracker, CoroutineDispatcher coroutineDispatcher) {
        return new KeyguardQuickAffordanceProviderClientFactoryImpl(userTracker, coroutineDispatcher);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public KeyguardQuickAffordanceProviderClientFactoryImpl m2951get() {
        return newInstance((UserTracker) this.userTrackerProvider.get(), (CoroutineDispatcher) this.backgroundDispatcherProvider.get());
    }
}