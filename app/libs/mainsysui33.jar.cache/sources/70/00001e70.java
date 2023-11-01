package com.android.systemui.navigationbar;

import android.content.Context;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/NavigationModeController_Factory.class */
public final class NavigationModeController_Factory implements Factory<NavigationModeController> {
    public final Provider<ConfigurationController> configurationControllerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DeviceProvisionedController> deviceProvisionedControllerProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<Executor> uiBgExecutorProvider;

    public NavigationModeController_Factory(Provider<Context> provider, Provider<DeviceProvisionedController> provider2, Provider<ConfigurationController> provider3, Provider<Executor> provider4, Provider<DumpManager> provider5) {
        this.contextProvider = provider;
        this.deviceProvisionedControllerProvider = provider2;
        this.configurationControllerProvider = provider3;
        this.uiBgExecutorProvider = provider4;
        this.dumpManagerProvider = provider5;
    }

    public static NavigationModeController_Factory create(Provider<Context> provider, Provider<DeviceProvisionedController> provider2, Provider<ConfigurationController> provider3, Provider<Executor> provider4, Provider<DumpManager> provider5) {
        return new NavigationModeController_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static NavigationModeController newInstance(Context context, DeviceProvisionedController deviceProvisionedController, ConfigurationController configurationController, Executor executor, DumpManager dumpManager) {
        return new NavigationModeController(context, deviceProvisionedController, configurationController, executor, dumpManager);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public NavigationModeController m3445get() {
        return newInstance((Context) this.contextProvider.get(), (DeviceProvisionedController) this.deviceProvisionedControllerProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (Executor) this.uiBgExecutorProvider.get(), (DumpManager) this.dumpManagerProvider.get());
    }
}