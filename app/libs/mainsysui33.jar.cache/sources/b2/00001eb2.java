package com.android.systemui.navigationbar.gestural;

import android.os.Handler;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import com.android.internal.util.LatencyTracker;
import com.android.systemui.navigationbar.gestural.BackPanelController;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.policy.ConfigurationController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/gestural/BackPanelController_Factory_Factory.class */
public final class BackPanelController_Factory_Factory implements Factory<BackPanelController.Factory> {
    public final Provider<ConfigurationController> configurationControllerProvider;
    public final Provider<LatencyTracker> latencyTrackerProvider;
    public final Provider<Handler> mainHandlerProvider;
    public final Provider<VibratorHelper> vibratorHelperProvider;
    public final Provider<ViewConfiguration> viewConfigurationProvider;
    public final Provider<WindowManager> windowManagerProvider;

    public BackPanelController_Factory_Factory(Provider<WindowManager> provider, Provider<ViewConfiguration> provider2, Provider<Handler> provider3, Provider<VibratorHelper> provider4, Provider<ConfigurationController> provider5, Provider<LatencyTracker> provider6) {
        this.windowManagerProvider = provider;
        this.viewConfigurationProvider = provider2;
        this.mainHandlerProvider = provider3;
        this.vibratorHelperProvider = provider4;
        this.configurationControllerProvider = provider5;
        this.latencyTrackerProvider = provider6;
    }

    public static BackPanelController_Factory_Factory create(Provider<WindowManager> provider, Provider<ViewConfiguration> provider2, Provider<Handler> provider3, Provider<VibratorHelper> provider4, Provider<ConfigurationController> provider5, Provider<LatencyTracker> provider6) {
        return new BackPanelController_Factory_Factory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    public static BackPanelController.Factory newInstance(WindowManager windowManager, ViewConfiguration viewConfiguration, Handler handler, VibratorHelper vibratorHelper, ConfigurationController configurationController, LatencyTracker latencyTracker) {
        return new BackPanelController.Factory(windowManager, viewConfiguration, handler, vibratorHelper, configurationController, latencyTracker);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public BackPanelController.Factory m3476get() {
        return newInstance((WindowManager) this.windowManagerProvider.get(), (ViewConfiguration) this.viewConfigurationProvider.get(), (Handler) this.mainHandlerProvider.get(), (VibratorHelper) this.vibratorHelperProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (LatencyTracker) this.latencyTrackerProvider.get());
    }
}