package com.android.systemui.keyguard.data.quickaffordance;

import com.android.systemui.settings.UserTracker;
import com.android.systemui.shared.customization.data.content.CustomizationProviderClient;
import com.android.systemui.shared.customization.data.content.CustomizationProviderClientImpl;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardQuickAffordanceProviderClientFactoryImpl.class */
public final class KeyguardQuickAffordanceProviderClientFactoryImpl implements KeyguardQuickAffordanceProviderClientFactory {
    public final CoroutineDispatcher backgroundDispatcher;
    public final UserTracker userTracker;

    public KeyguardQuickAffordanceProviderClientFactoryImpl(UserTracker userTracker, CoroutineDispatcher coroutineDispatcher) {
        this.userTracker = userTracker;
        this.backgroundDispatcher = coroutineDispatcher;
    }

    @Override // com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceProviderClientFactory
    public CustomizationProviderClient create() {
        return new CustomizationProviderClientImpl(this.userTracker.getUserContext(), this.backgroundDispatcher);
    }
}