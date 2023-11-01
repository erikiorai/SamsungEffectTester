package com.android.systemui.navigationbar.gestural;

import android.view.IWindowManager;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.gestural.BackPanelController;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.tracing.ProtoTracer;
import com.android.wm.shell.pip.Pip;
import dagger.internal.Factory;
import java.util.Optional;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/gestural/EdgeBackGestureHandler_Factory_Factory.class */
public final class EdgeBackGestureHandler_Factory_Factory implements Factory<EdgeBackGestureHandler.Factory> {
    public final Provider<BackGestureTfClassifierProvider> backGestureTfClassifierProvider;
    public final Provider<BackPanelController.Factory> backPanelControllerFactoryProvider;
    public final Provider<Executor> backgroundExecutorProvider;
    public final Provider<Executor> executorProvider;
    public final Provider<FalsingManager> falsingManagerProvider;
    public final Provider<FeatureFlags> featureFlagsProvider;
    public final Provider<NavigationBarEdgePanel> navBarEdgePanelProvider;
    public final Provider<NavigationModeController> navigationModeControllerProvider;
    public final Provider<OverviewProxyService> overviewProxyServiceProvider;
    public final Provider<Optional<Pip>> pipOptionalProvider;
    public final Provider<PluginManager> pluginManagerProvider;
    public final Provider<ProtoTracer> protoTracerProvider;
    public final Provider<SysUiState> sysUiStateProvider;
    public final Provider<UserTracker> userTrackerProvider;
    public final Provider<ViewConfiguration> viewConfigurationProvider;
    public final Provider<WindowManager> windowManagerProvider;
    public final Provider<IWindowManager> windowManagerServiceProvider;

    public EdgeBackGestureHandler_Factory_Factory(Provider<OverviewProxyService> provider, Provider<SysUiState> provider2, Provider<PluginManager> provider3, Provider<Executor> provider4, Provider<Executor> provider5, Provider<UserTracker> provider6, Provider<ProtoTracer> provider7, Provider<NavigationModeController> provider8, Provider<BackPanelController.Factory> provider9, Provider<ViewConfiguration> provider10, Provider<WindowManager> provider11, Provider<IWindowManager> provider12, Provider<Optional<Pip>> provider13, Provider<FalsingManager> provider14, Provider<NavigationBarEdgePanel> provider15, Provider<BackGestureTfClassifierProvider> provider16, Provider<FeatureFlags> provider17) {
        this.overviewProxyServiceProvider = provider;
        this.sysUiStateProvider = provider2;
        this.pluginManagerProvider = provider3;
        this.executorProvider = provider4;
        this.backgroundExecutorProvider = provider5;
        this.userTrackerProvider = provider6;
        this.protoTracerProvider = provider7;
        this.navigationModeControllerProvider = provider8;
        this.backPanelControllerFactoryProvider = provider9;
        this.viewConfigurationProvider = provider10;
        this.windowManagerProvider = provider11;
        this.windowManagerServiceProvider = provider12;
        this.pipOptionalProvider = provider13;
        this.falsingManagerProvider = provider14;
        this.navBarEdgePanelProvider = provider15;
        this.backGestureTfClassifierProvider = provider16;
        this.featureFlagsProvider = provider17;
    }

    public static EdgeBackGestureHandler_Factory_Factory create(Provider<OverviewProxyService> provider, Provider<SysUiState> provider2, Provider<PluginManager> provider3, Provider<Executor> provider4, Provider<Executor> provider5, Provider<UserTracker> provider6, Provider<ProtoTracer> provider7, Provider<NavigationModeController> provider8, Provider<BackPanelController.Factory> provider9, Provider<ViewConfiguration> provider10, Provider<WindowManager> provider11, Provider<IWindowManager> provider12, Provider<Optional<Pip>> provider13, Provider<FalsingManager> provider14, Provider<NavigationBarEdgePanel> provider15, Provider<BackGestureTfClassifierProvider> provider16, Provider<FeatureFlags> provider17) {
        return new EdgeBackGestureHandler_Factory_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17);
    }

    public static EdgeBackGestureHandler.Factory newInstance(OverviewProxyService overviewProxyService, SysUiState sysUiState, PluginManager pluginManager, Executor executor, Executor executor2, UserTracker userTracker, ProtoTracer protoTracer, NavigationModeController navigationModeController, BackPanelController.Factory factory, ViewConfiguration viewConfiguration, WindowManager windowManager, IWindowManager iWindowManager, Optional<Pip> optional, FalsingManager falsingManager, Provider<NavigationBarEdgePanel> provider, Provider<BackGestureTfClassifierProvider> provider2, FeatureFlags featureFlags) {
        return new EdgeBackGestureHandler.Factory(overviewProxyService, sysUiState, pluginManager, executor, executor2, userTracker, protoTracer, navigationModeController, factory, viewConfiguration, windowManager, iWindowManager, optional, falsingManager, provider, provider2, featureFlags);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public EdgeBackGestureHandler.Factory m3498get() {
        return newInstance((OverviewProxyService) this.overviewProxyServiceProvider.get(), (SysUiState) this.sysUiStateProvider.get(), (PluginManager) this.pluginManagerProvider.get(), (Executor) this.executorProvider.get(), (Executor) this.backgroundExecutorProvider.get(), (UserTracker) this.userTrackerProvider.get(), (ProtoTracer) this.protoTracerProvider.get(), (NavigationModeController) this.navigationModeControllerProvider.get(), (BackPanelController.Factory) this.backPanelControllerFactoryProvider.get(), (ViewConfiguration) this.viewConfigurationProvider.get(), (WindowManager) this.windowManagerProvider.get(), (IWindowManager) this.windowManagerServiceProvider.get(), (Optional) this.pipOptionalProvider.get(), (FalsingManager) this.falsingManagerProvider.get(), this.navBarEdgePanelProvider, this.backGestureTfClassifierProvider, (FeatureFlags) this.featureFlagsProvider.get());
    }
}