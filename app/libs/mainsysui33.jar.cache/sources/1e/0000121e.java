package com.android.systemui.biometrics;

import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsHapticsSimulator_Factory.class */
public final class UdfpsHapticsSimulator_Factory implements Factory<UdfpsHapticsSimulator> {
    public final Provider<CommandRegistry> commandRegistryProvider;
    public final Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;
    public final Provider<VibratorHelper> vibratorProvider;

    public UdfpsHapticsSimulator_Factory(Provider<CommandRegistry> provider, Provider<VibratorHelper> provider2, Provider<KeyguardUpdateMonitor> provider3) {
        this.commandRegistryProvider = provider;
        this.vibratorProvider = provider2;
        this.keyguardUpdateMonitorProvider = provider3;
    }

    public static UdfpsHapticsSimulator_Factory create(Provider<CommandRegistry> provider, Provider<VibratorHelper> provider2, Provider<KeyguardUpdateMonitor> provider3) {
        return new UdfpsHapticsSimulator_Factory(provider, provider2, provider3);
    }

    public static UdfpsHapticsSimulator newInstance(CommandRegistry commandRegistry, VibratorHelper vibratorHelper, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        return new UdfpsHapticsSimulator(commandRegistry, vibratorHelper, keyguardUpdateMonitor);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public UdfpsHapticsSimulator m1599get() {
        return newInstance((CommandRegistry) this.commandRegistryProvider.get(), (VibratorHelper) this.vibratorProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get());
    }
}