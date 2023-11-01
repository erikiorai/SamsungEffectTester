package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.Factory;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceLegacySettingSyncer_Factory.class */
public final class KeyguardQuickAffordanceLegacySettingSyncer_Factory implements Factory<KeyguardQuickAffordanceLegacySettingSyncer> {
    public final Provider<CoroutineDispatcher> backgroundDispatcherProvider;
    public final Provider<CoroutineScope> scopeProvider;
    public final Provider<SecureSettings> secureSettingsProvider;
    public final Provider<KeyguardQuickAffordanceLocalUserSelectionManager> selectionsManagerProvider;

    public KeyguardQuickAffordanceLegacySettingSyncer_Factory(Provider<CoroutineScope> provider, Provider<CoroutineDispatcher> provider2, Provider<SecureSettings> provider3, Provider<KeyguardQuickAffordanceLocalUserSelectionManager> provider4) {
        this.scopeProvider = provider;
        this.backgroundDispatcherProvider = provider2;
        this.secureSettingsProvider = provider3;
        this.selectionsManagerProvider = provider4;
    }

    public static KeyguardQuickAffordanceLegacySettingSyncer_Factory create(Provider<CoroutineScope> provider, Provider<CoroutineDispatcher> provider2, Provider<SecureSettings> provider3, Provider<KeyguardQuickAffordanceLocalUserSelectionManager> provider4) {
        return new KeyguardQuickAffordanceLegacySettingSyncer_Factory(provider, provider2, provider3, provider4);
    }

    public static KeyguardQuickAffordanceLegacySettingSyncer newInstance(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, SecureSettings secureSettings, KeyguardQuickAffordanceLocalUserSelectionManager keyguardQuickAffordanceLocalUserSelectionManager) {
        return new KeyguardQuickAffordanceLegacySettingSyncer(coroutineScope, coroutineDispatcher, secureSettings, keyguardQuickAffordanceLocalUserSelectionManager);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public KeyguardQuickAffordanceLegacySettingSyncer m2943get() {
        return newInstance((CoroutineScope) this.scopeProvider.get(), (CoroutineDispatcher) this.backgroundDispatcherProvider.get(), (SecureSettings) this.secureSettingsProvider.get(), (KeyguardQuickAffordanceLocalUserSelectionManager) this.selectionsManagerProvider.get());
    }
}