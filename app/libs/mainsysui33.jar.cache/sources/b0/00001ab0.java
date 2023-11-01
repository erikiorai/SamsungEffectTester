package com.android.systemui.keyguard.domain.quickaffordance;

import com.android.systemui.keyguard.data.quickaffordance.HomeControlsKeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.data.quickaffordance.QrCodeScannerKeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/quickaffordance/KeyguardQuickAffordanceRegistryImpl_Factory.class */
public final class KeyguardQuickAffordanceRegistryImpl_Factory implements Factory<KeyguardQuickAffordanceRegistryImpl> {
    public final Provider<HomeControlsKeyguardQuickAffordanceConfig> homeControlsProvider;
    public final Provider<QrCodeScannerKeyguardQuickAffordanceConfig> qrCodeScannerProvider;
    public final Provider<QuickAccessWalletKeyguardQuickAffordanceConfig> quickAccessWalletProvider;

    public KeyguardQuickAffordanceRegistryImpl_Factory(Provider<HomeControlsKeyguardQuickAffordanceConfig> provider, Provider<QuickAccessWalletKeyguardQuickAffordanceConfig> provider2, Provider<QrCodeScannerKeyguardQuickAffordanceConfig> provider3) {
        this.homeControlsProvider = provider;
        this.quickAccessWalletProvider = provider2;
        this.qrCodeScannerProvider = provider3;
    }

    public static KeyguardQuickAffordanceRegistryImpl_Factory create(Provider<HomeControlsKeyguardQuickAffordanceConfig> provider, Provider<QuickAccessWalletKeyguardQuickAffordanceConfig> provider2, Provider<QrCodeScannerKeyguardQuickAffordanceConfig> provider3) {
        return new KeyguardQuickAffordanceRegistryImpl_Factory(provider, provider2, provider3);
    }

    public static KeyguardQuickAffordanceRegistryImpl newInstance(HomeControlsKeyguardQuickAffordanceConfig homeControlsKeyguardQuickAffordanceConfig, QuickAccessWalletKeyguardQuickAffordanceConfig quickAccessWalletKeyguardQuickAffordanceConfig, QrCodeScannerKeyguardQuickAffordanceConfig qrCodeScannerKeyguardQuickAffordanceConfig) {
        return new KeyguardQuickAffordanceRegistryImpl(homeControlsKeyguardQuickAffordanceConfig, quickAccessWalletKeyguardQuickAffordanceConfig, qrCodeScannerKeyguardQuickAffordanceConfig);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public KeyguardQuickAffordanceRegistryImpl m3057get() {
        return newInstance((HomeControlsKeyguardQuickAffordanceConfig) this.homeControlsProvider.get(), (QuickAccessWalletKeyguardQuickAffordanceConfig) this.quickAccessWalletProvider.get(), (QrCodeScannerKeyguardQuickAffordanceConfig) this.qrCodeScannerProvider.get());
    }
}