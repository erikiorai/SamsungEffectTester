package com.android.systemui.charging;

import android.content.Context;
import android.view.WindowManager;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/charging/WiredChargingRippleController_Factory.class */
public final class WiredChargingRippleController_Factory implements Factory<WiredChargingRippleController> {
    public final Provider<BatteryController> batteryControllerProvider;
    public final Provider<CommandRegistry> commandRegistryProvider;
    public final Provider<ConfigurationController> configurationControllerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<FeatureFlags> featureFlagsProvider;
    public final Provider<SystemClock> systemClockProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;
    public final Provider<WindowManager> windowManagerProvider;

    public WiredChargingRippleController_Factory(Provider<CommandRegistry> provider, Provider<BatteryController> provider2, Provider<ConfigurationController> provider3, Provider<FeatureFlags> provider4, Provider<Context> provider5, Provider<WindowManager> provider6, Provider<SystemClock> provider7, Provider<UiEventLogger> provider8) {
        this.commandRegistryProvider = provider;
        this.batteryControllerProvider = provider2;
        this.configurationControllerProvider = provider3;
        this.featureFlagsProvider = provider4;
        this.contextProvider = provider5;
        this.windowManagerProvider = provider6;
        this.systemClockProvider = provider7;
        this.uiEventLoggerProvider = provider8;
    }

    public static WiredChargingRippleController_Factory create(Provider<CommandRegistry> provider, Provider<BatteryController> provider2, Provider<ConfigurationController> provider3, Provider<FeatureFlags> provider4, Provider<Context> provider5, Provider<WindowManager> provider6, Provider<SystemClock> provider7, Provider<UiEventLogger> provider8) {
        return new WiredChargingRippleController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8);
    }

    public static WiredChargingRippleController newInstance(CommandRegistry commandRegistry, BatteryController batteryController, ConfigurationController configurationController, FeatureFlags featureFlags, Context context, WindowManager windowManager, SystemClock systemClock, UiEventLogger uiEventLogger) {
        return new WiredChargingRippleController(commandRegistry, batteryController, configurationController, featureFlags, context, windowManager, systemClock, uiEventLogger);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public WiredChargingRippleController m1659get() {
        return newInstance((CommandRegistry) this.commandRegistryProvider.get(), (BatteryController) this.batteryControllerProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (FeatureFlags) this.featureFlagsProvider.get(), (Context) this.contextProvider.get(), (WindowManager) this.windowManagerProvider.get(), (SystemClock) this.systemClockProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get());
    }
}