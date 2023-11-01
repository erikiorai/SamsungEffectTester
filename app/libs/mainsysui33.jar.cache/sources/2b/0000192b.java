package com.android.systemui.keyguard.data.quickaffordance;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.util.Set;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardDataQuickAffordanceModule_Companion_QuickAffordanceConfigsFactory.class */
public final class KeyguardDataQuickAffordanceModule_Companion_QuickAffordanceConfigsFactory implements Factory<Set<KeyguardQuickAffordanceConfig>> {
    public final Provider<CameraQuickAffordanceConfig> cameraProvider;
    public final Provider<DoNotDisturbQuickAffordanceConfig> doNotDisturbProvider;
    public final Provider<FlashlightQuickAffordanceConfig> flashlightProvider;
    public final Provider<HomeControlsKeyguardQuickAffordanceConfig> homeProvider;
    public final Provider<QrCodeScannerKeyguardQuickAffordanceConfig> qrCodeScannerProvider;
    public final Provider<QuickAccessWalletKeyguardQuickAffordanceConfig> quickAccessWalletProvider;

    public KeyguardDataQuickAffordanceModule_Companion_QuickAffordanceConfigsFactory(Provider<DoNotDisturbQuickAffordanceConfig> provider, Provider<FlashlightQuickAffordanceConfig> provider2, Provider<HomeControlsKeyguardQuickAffordanceConfig> provider3, Provider<QuickAccessWalletKeyguardQuickAffordanceConfig> provider4, Provider<QrCodeScannerKeyguardQuickAffordanceConfig> provider5, Provider<CameraQuickAffordanceConfig> provider6) {
        this.doNotDisturbProvider = provider;
        this.flashlightProvider = provider2;
        this.homeProvider = provider3;
        this.quickAccessWalletProvider = provider4;
        this.qrCodeScannerProvider = provider5;
        this.cameraProvider = provider6;
    }

    public static KeyguardDataQuickAffordanceModule_Companion_QuickAffordanceConfigsFactory create(Provider<DoNotDisturbQuickAffordanceConfig> provider, Provider<FlashlightQuickAffordanceConfig> provider2, Provider<HomeControlsKeyguardQuickAffordanceConfig> provider3, Provider<QuickAccessWalletKeyguardQuickAffordanceConfig> provider4, Provider<QrCodeScannerKeyguardQuickAffordanceConfig> provider5, Provider<CameraQuickAffordanceConfig> provider6) {
        return new KeyguardDataQuickAffordanceModule_Companion_QuickAffordanceConfigsFactory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    public static Set<KeyguardQuickAffordanceConfig> quickAffordanceConfigs(DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig, FlashlightQuickAffordanceConfig flashlightQuickAffordanceConfig, HomeControlsKeyguardQuickAffordanceConfig homeControlsKeyguardQuickAffordanceConfig, QuickAccessWalletKeyguardQuickAffordanceConfig quickAccessWalletKeyguardQuickAffordanceConfig, QrCodeScannerKeyguardQuickAffordanceConfig qrCodeScannerKeyguardQuickAffordanceConfig, CameraQuickAffordanceConfig cameraQuickAffordanceConfig) {
        return (Set) Preconditions.checkNotNullFromProvides(KeyguardDataQuickAffordanceModule.Companion.quickAffordanceConfigs(doNotDisturbQuickAffordanceConfig, flashlightQuickAffordanceConfig, homeControlsKeyguardQuickAffordanceConfig, quickAccessWalletKeyguardQuickAffordanceConfig, qrCodeScannerKeyguardQuickAffordanceConfig, cameraQuickAffordanceConfig));
    }

    /* JADX DEBUG: Method merged with bridge method */
    public Set<KeyguardQuickAffordanceConfig> get() {
        return quickAffordanceConfigs((DoNotDisturbQuickAffordanceConfig) this.doNotDisturbProvider.get(), (FlashlightQuickAffordanceConfig) this.flashlightProvider.get(), (HomeControlsKeyguardQuickAffordanceConfig) this.homeProvider.get(), (QuickAccessWalletKeyguardQuickAffordanceConfig) this.quickAccessWalletProvider.get(), (QrCodeScannerKeyguardQuickAffordanceConfig) this.qrCodeScannerProvider.get(), (CameraQuickAffordanceConfig) this.cameraProvider.get());
    }
}