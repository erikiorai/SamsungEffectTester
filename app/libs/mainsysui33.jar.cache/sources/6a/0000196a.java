package com.android.systemui.keyguard.data.quickaffordance;

import android.os.UserHandle;
import com.android.systemui.settings.UserTracker;
import dagger.internal.Factory;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceRemoteUserSelectionManager_Factory.class */
public final class KeyguardQuickAffordanceRemoteUserSelectionManager_Factory implements Factory<KeyguardQuickAffordanceRemoteUserSelectionManager> {
    public final Provider<KeyguardQuickAffordanceProviderClientFactory> clientFactoryProvider;
    public final Provider<CoroutineScope> scopeProvider;
    public final Provider<UserHandle> userHandleProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public KeyguardQuickAffordanceRemoteUserSelectionManager_Factory(Provider<CoroutineScope> provider, Provider<UserTracker> provider2, Provider<KeyguardQuickAffordanceProviderClientFactory> provider3, Provider<UserHandle> provider4) {
        this.scopeProvider = provider;
        this.userTrackerProvider = provider2;
        this.clientFactoryProvider = provider3;
        this.userHandleProvider = provider4;
    }

    public static KeyguardQuickAffordanceRemoteUserSelectionManager_Factory create(Provider<CoroutineScope> provider, Provider<UserTracker> provider2, Provider<KeyguardQuickAffordanceProviderClientFactory> provider3, Provider<UserHandle> provider4) {
        return new KeyguardQuickAffordanceRemoteUserSelectionManager_Factory(provider, provider2, provider3, provider4);
    }

    public static KeyguardQuickAffordanceRemoteUserSelectionManager newInstance(CoroutineScope coroutineScope, UserTracker userTracker, KeyguardQuickAffordanceProviderClientFactory keyguardQuickAffordanceProviderClientFactory, UserHandle userHandle) {
        return new KeyguardQuickAffordanceRemoteUserSelectionManager(coroutineScope, userTracker, keyguardQuickAffordanceProviderClientFactory, userHandle);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public KeyguardQuickAffordanceRemoteUserSelectionManager m2955get() {
        return newInstance((CoroutineScope) this.scopeProvider.get(), (UserTracker) this.userTrackerProvider.get(), (KeyguardQuickAffordanceProviderClientFactory) this.clientFactoryProvider.get(), (UserHandle) this.userHandleProvider.get());
    }
}