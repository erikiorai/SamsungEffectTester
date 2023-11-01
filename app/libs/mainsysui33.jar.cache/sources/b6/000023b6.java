package com.android.systemui.recents;

import android.content.Context;
import com.android.internal.app.AssistUtils;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.wm.shell.sysui.ShellInterface;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import java.util.Optional;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/recents/OverviewProxyService_Factory.class */
public final class OverviewProxyService_Factory implements Factory<OverviewProxyService> {
    public final Provider<AssistUtils> assistUtilsProvider;
    public final Provider<Optional<CentralSurfaces>> centralSurfacesOptionalLazyProvider;
    public final Provider<CommandQueue> commandQueueProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<Executor> mainExecutorProvider;
    public final Provider<NavigationBarController> navBarControllerLazyProvider;
    public final Provider<NavigationModeController> navModeControllerProvider;
    public final Provider<ScreenLifecycle> screenLifecycleProvider;
    public final Provider<ShellInterface> shellInterfaceProvider;
    public final Provider<NotificationShadeWindowController> statusBarWinControllerProvider;
    public final Provider<SysUiState> sysUiStateProvider;
    public final Provider<KeyguardUnlockAnimationController> sysuiUnlockAnimationControllerProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public OverviewProxyService_Factory(Provider<Context> provider, Provider<Executor> provider2, Provider<CommandQueue> provider3, Provider<ShellInterface> provider4, Provider<NavigationBarController> provider5, Provider<Optional<CentralSurfaces>> provider6, Provider<NavigationModeController> provider7, Provider<NotificationShadeWindowController> provider8, Provider<SysUiState> provider9, Provider<UserTracker> provider10, Provider<ScreenLifecycle> provider11, Provider<UiEventLogger> provider12, Provider<KeyguardUnlockAnimationController> provider13, Provider<AssistUtils> provider14, Provider<DumpManager> provider15) {
        this.contextProvider = provider;
        this.mainExecutorProvider = provider2;
        this.commandQueueProvider = provider3;
        this.shellInterfaceProvider = provider4;
        this.navBarControllerLazyProvider = provider5;
        this.centralSurfacesOptionalLazyProvider = provider6;
        this.navModeControllerProvider = provider7;
        this.statusBarWinControllerProvider = provider8;
        this.sysUiStateProvider = provider9;
        this.userTrackerProvider = provider10;
        this.screenLifecycleProvider = provider11;
        this.uiEventLoggerProvider = provider12;
        this.sysuiUnlockAnimationControllerProvider = provider13;
        this.assistUtilsProvider = provider14;
        this.dumpManagerProvider = provider15;
    }

    public static OverviewProxyService_Factory create(Provider<Context> provider, Provider<Executor> provider2, Provider<CommandQueue> provider3, Provider<ShellInterface> provider4, Provider<NavigationBarController> provider5, Provider<Optional<CentralSurfaces>> provider6, Provider<NavigationModeController> provider7, Provider<NotificationShadeWindowController> provider8, Provider<SysUiState> provider9, Provider<UserTracker> provider10, Provider<ScreenLifecycle> provider11, Provider<UiEventLogger> provider12, Provider<KeyguardUnlockAnimationController> provider13, Provider<AssistUtils> provider14, Provider<DumpManager> provider15) {
        return new OverviewProxyService_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15);
    }

    public static OverviewProxyService newInstance(Context context, Executor executor, CommandQueue commandQueue, ShellInterface shellInterface, Lazy<NavigationBarController> lazy, Lazy<Optional<CentralSurfaces>> lazy2, NavigationModeController navigationModeController, NotificationShadeWindowController notificationShadeWindowController, SysUiState sysUiState, UserTracker userTracker, ScreenLifecycle screenLifecycle, UiEventLogger uiEventLogger, KeyguardUnlockAnimationController keyguardUnlockAnimationController, AssistUtils assistUtils, DumpManager dumpManager) {
        return new OverviewProxyService(context, executor, commandQueue, shellInterface, lazy, lazy2, navigationModeController, notificationShadeWindowController, sysUiState, userTracker, screenLifecycle, uiEventLogger, keyguardUnlockAnimationController, assistUtils, dumpManager);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public OverviewProxyService m4154get() {
        return newInstance((Context) this.contextProvider.get(), (Executor) this.mainExecutorProvider.get(), (CommandQueue) this.commandQueueProvider.get(), (ShellInterface) this.shellInterfaceProvider.get(), DoubleCheck.lazy(this.navBarControllerLazyProvider), DoubleCheck.lazy(this.centralSurfacesOptionalLazyProvider), (NavigationModeController) this.navModeControllerProvider.get(), (NotificationShadeWindowController) this.statusBarWinControllerProvider.get(), (SysUiState) this.sysUiStateProvider.get(), (UserTracker) this.userTrackerProvider.get(), (ScreenLifecycle) this.screenLifecycleProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get(), (KeyguardUnlockAnimationController) this.sysuiUnlockAnimationControllerProvider.get(), (AssistUtils) this.assistUtilsProvider.get(), (DumpManager) this.dumpManagerProvider.get());
    }
}