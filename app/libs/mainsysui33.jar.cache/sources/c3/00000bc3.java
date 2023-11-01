package com.android.keyguard;

import com.android.keyguard.KeyguardMessageAreaController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardMessageAreaController_Factory_Factory.class */
public final class KeyguardMessageAreaController_Factory_Factory implements Factory<KeyguardMessageAreaController.Factory> {
    public final Provider<ConfigurationController> configurationControllerProvider;
    public final Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;

    public KeyguardMessageAreaController_Factory_Factory(Provider<KeyguardUpdateMonitor> provider, Provider<ConfigurationController> provider2) {
        this.keyguardUpdateMonitorProvider = provider;
        this.configurationControllerProvider = provider2;
    }

    public static KeyguardMessageAreaController_Factory_Factory create(Provider<KeyguardUpdateMonitor> provider, Provider<ConfigurationController> provider2) {
        return new KeyguardMessageAreaController_Factory_Factory(provider, provider2);
    }

    public static KeyguardMessageAreaController.Factory newInstance(KeyguardUpdateMonitor keyguardUpdateMonitor, ConfigurationController configurationController) {
        return new KeyguardMessageAreaController.Factory(keyguardUpdateMonitor, configurationController);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public KeyguardMessageAreaController.Factory m607get() {
        return newInstance((KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get(), (ConfigurationController) this.configurationControllerProvider.get());
    }
}