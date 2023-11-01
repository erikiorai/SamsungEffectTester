package com.android.systemui.navigationbar;

import android.content.Context;
import android.os.Handler;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.NavigationBarComponent;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.AutoHideController;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.wm.shell.back.BackAnimation;
import com.android.wm.shell.pip.Pip;
import dagger.internal.Factory;
import java.util.Optional;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/NavigationBarController_Factory.class */
public final class NavigationBarController_Factory implements Factory<NavigationBarController> {
    public final Provider<AutoHideController> autoHideControllerProvider;
    public final Provider<Optional<BackAnimation>> backAnimationProvider;
    public final Provider<CommandQueue> commandQueueProvider;
    public final Provider<ConfigurationController> configurationControllerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<FeatureFlags> featureFlagsProvider;
    public final Provider<LightBarController> lightBarControllerProvider;
    public final Provider<Handler> mainHandlerProvider;
    public final Provider<NavBarHelper> navBarHelperProvider;
    public final Provider<NavigationBarComponent.Factory> navigationBarComponentFactoryProvider;
    public final Provider<NavigationModeController> navigationModeControllerProvider;
    public final Provider<OverviewProxyService> overviewProxyServiceProvider;
    public final Provider<Optional<Pip>> pipOptionalProvider;
    public final Provider<StatusBarKeyguardViewManager> statusBarKeyguardViewManagerProvider;
    public final Provider<SysUiState> sysUiFlagsContainerProvider;
    public final Provider<TaskbarDelegate> taskbarDelegateProvider;

    public NavigationBarController_Factory(Provider<Context> provider, Provider<OverviewProxyService> provider2, Provider<NavigationModeController> provider3, Provider<SysUiState> provider4, Provider<CommandQueue> provider5, Provider<Handler> provider6, Provider<ConfigurationController> provider7, Provider<NavBarHelper> provider8, Provider<TaskbarDelegate> provider9, Provider<NavigationBarComponent.Factory> provider10, Provider<StatusBarKeyguardViewManager> provider11, Provider<DumpManager> provider12, Provider<AutoHideController> provider13, Provider<LightBarController> provider14, Provider<Optional<Pip>> provider15, Provider<Optional<BackAnimation>> provider16, Provider<FeatureFlags> provider17) {
        this.contextProvider = provider;
        this.overviewProxyServiceProvider = provider2;
        this.navigationModeControllerProvider = provider3;
        this.sysUiFlagsContainerProvider = provider4;
        this.commandQueueProvider = provider5;
        this.mainHandlerProvider = provider6;
        this.configurationControllerProvider = provider7;
        this.navBarHelperProvider = provider8;
        this.taskbarDelegateProvider = provider9;
        this.navigationBarComponentFactoryProvider = provider10;
        this.statusBarKeyguardViewManagerProvider = provider11;
        this.dumpManagerProvider = provider12;
        this.autoHideControllerProvider = provider13;
        this.lightBarControllerProvider = provider14;
        this.pipOptionalProvider = provider15;
        this.backAnimationProvider = provider16;
        this.featureFlagsProvider = provider17;
    }

    public static NavigationBarController_Factory create(Provider<Context> provider, Provider<OverviewProxyService> provider2, Provider<NavigationModeController> provider3, Provider<SysUiState> provider4, Provider<CommandQueue> provider5, Provider<Handler> provider6, Provider<ConfigurationController> provider7, Provider<NavBarHelper> provider8, Provider<TaskbarDelegate> provider9, Provider<NavigationBarComponent.Factory> provider10, Provider<StatusBarKeyguardViewManager> provider11, Provider<DumpManager> provider12, Provider<AutoHideController> provider13, Provider<LightBarController> provider14, Provider<Optional<Pip>> provider15, Provider<Optional<BackAnimation>> provider16, Provider<FeatureFlags> provider17) {
        return new NavigationBarController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17);
    }

    public static NavigationBarController newInstance(Context context, OverviewProxyService overviewProxyService, NavigationModeController navigationModeController, SysUiState sysUiState, CommandQueue commandQueue, Handler handler, ConfigurationController configurationController, NavBarHelper navBarHelper, TaskbarDelegate taskbarDelegate, NavigationBarComponent.Factory factory, StatusBarKeyguardViewManager statusBarKeyguardViewManager, DumpManager dumpManager, AutoHideController autoHideController, LightBarController lightBarController, Optional<Pip> optional, Optional<BackAnimation> optional2, FeatureFlags featureFlags) {
        return new NavigationBarController(context, overviewProxyService, navigationModeController, sysUiState, commandQueue, handler, configurationController, navBarHelper, taskbarDelegate, factory, statusBarKeyguardViewManager, dumpManager, autoHideController, lightBarController, optional, optional2, featureFlags);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public NavigationBarController m3427get() {
        return newInstance((Context) this.contextProvider.get(), (OverviewProxyService) this.overviewProxyServiceProvider.get(), (NavigationModeController) this.navigationModeControllerProvider.get(), (SysUiState) this.sysUiFlagsContainerProvider.get(), (CommandQueue) this.commandQueueProvider.get(), (Handler) this.mainHandlerProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (NavBarHelper) this.navBarHelperProvider.get(), (TaskbarDelegate) this.taskbarDelegateProvider.get(), (NavigationBarComponent.Factory) this.navigationBarComponentFactoryProvider.get(), (StatusBarKeyguardViewManager) this.statusBarKeyguardViewManagerProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (AutoHideController) this.autoHideControllerProvider.get(), (LightBarController) this.lightBarControllerProvider.get(), (Optional) this.pipOptionalProvider.get(), (Optional) this.backAnimationProvider.get(), (FeatureFlags) this.featureFlagsProvider.get());
    }
}