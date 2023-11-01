package com.android.systemui.keyguard.data.quickaffordance;

import java.util.Set;
import kotlin.collections.SetsKt__SetsKt;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardDataQuickAffordanceModule.class */
public interface KeyguardDataQuickAffordanceModule {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/KeyguardDataQuickAffordanceModule$Companion.class */
    public static final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        public final Set<KeyguardQuickAffordanceConfig> quickAffordanceConfigs(DoNotDisturbQuickAffordanceConfig doNotDisturbQuickAffordanceConfig, FlashlightQuickAffordanceConfig flashlightQuickAffordanceConfig, HomeControlsKeyguardQuickAffordanceConfig homeControlsKeyguardQuickAffordanceConfig, QuickAccessWalletKeyguardQuickAffordanceConfig quickAccessWalletKeyguardQuickAffordanceConfig, QrCodeScannerKeyguardQuickAffordanceConfig qrCodeScannerKeyguardQuickAffordanceConfig, CameraQuickAffordanceConfig cameraQuickAffordanceConfig) {
            return SetsKt__SetsKt.setOf(new KeyguardQuickAffordanceConfig[]{cameraQuickAffordanceConfig, doNotDisturbQuickAffordanceConfig, flashlightQuickAffordanceConfig, homeControlsKeyguardQuickAffordanceConfig, quickAccessWalletKeyguardQuickAffordanceConfig, qrCodeScannerKeyguardQuickAffordanceConfig});
        }
    }
}