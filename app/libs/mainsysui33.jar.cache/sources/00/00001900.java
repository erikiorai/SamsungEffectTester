package com.android.systemui.keyguard.dagger;

import android.app.trust.TrustManager;
import android.content.Context;
import android.os.PowerManager;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardDisplayManager;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardViewController;
import com.android.keyguard.mediator.ScreenOnCoordinator;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.DismissCallbackRegistry;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.util.DeviceConfigProxy;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/dagger/KeyguardModule_NewKeyguardViewMediatorFactory.class */
public final class KeyguardModule_NewKeyguardViewMediatorFactory implements Factory<KeyguardViewMediator> {
    public final Provider<ActivityLaunchAnimator> activityLaunchAnimatorProvider;
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DeviceConfigProxy> deviceConfigProvider;
    public final Provider<DismissCallbackRegistry> dismissCallbackRegistryProvider;
    public final Provider<DozeParameters> dozeParametersProvider;
    public final Provider<DreamOverlayStateController> dreamOverlayStateControllerProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<FalsingCollector> falsingCollectorProvider;
    public final Provider<InteractionJankMonitor> interactionJankMonitorProvider;
    public final Provider<KeyguardDisplayManager> keyguardDisplayManagerProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<KeyguardUnlockAnimationController> keyguardUnlockAnimationControllerProvider;
    public final Provider<LockPatternUtils> lockPatternUtilsProvider;
    public final Provider<NavigationModeController> navigationModeControllerProvider;
    public final Provider<NotificationShadeDepthController> notificationShadeDepthControllerProvider;
    public final Provider<NotificationShadeWindowController> notificationShadeWindowControllerProvider;
    public final Provider<PowerManager> powerManagerProvider;
    public final Provider<ScreenOffAnimationController> screenOffAnimationControllerProvider;
    public final Provider<ScreenOnCoordinator> screenOnCoordinatorProvider;
    public final Provider<ScrimController> scrimControllerLazyProvider;
    public final Provider<ShadeController> shadeControllerProvider;
    public final Provider<KeyguardViewController> statusBarKeyguardViewManagerLazyProvider;
    public final Provider<SysuiStatusBarStateController> statusBarStateControllerProvider;
    public final Provider<TrustManager> trustManagerProvider;
    public final Provider<Executor> uiBgExecutorProvider;
    public final Provider<KeyguardUpdateMonitor> updateMonitorProvider;
    public final Provider<UserSwitcherController> userSwitcherControllerProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public KeyguardModule_NewKeyguardViewMediatorFactory(Provider<Context> provider, Provider<UserTracker> provider2, Provider<FalsingCollector> provider3, Provider<LockPatternUtils> provider4, Provider<BroadcastDispatcher> provider5, Provider<KeyguardViewController> provider6, Provider<DismissCallbackRegistry> provider7, Provider<KeyguardUpdateMonitor> provider8, Provider<DumpManager> provider9, Provider<PowerManager> provider10, Provider<TrustManager> provider11, Provider<UserSwitcherController> provider12, Provider<Executor> provider13, Provider<DeviceConfigProxy> provider14, Provider<NavigationModeController> provider15, Provider<KeyguardDisplayManager> provider16, Provider<DozeParameters> provider17, Provider<SysuiStatusBarStateController> provider18, Provider<KeyguardStateController> provider19, Provider<KeyguardUnlockAnimationController> provider20, Provider<ScreenOffAnimationController> provider21, Provider<NotificationShadeDepthController> provider22, Provider<ScreenOnCoordinator> provider23, Provider<InteractionJankMonitor> provider24, Provider<DreamOverlayStateController> provider25, Provider<ShadeController> provider26, Provider<NotificationShadeWindowController> provider27, Provider<ActivityLaunchAnimator> provider28, Provider<ScrimController> provider29) {
        this.contextProvider = provider;
        this.userTrackerProvider = provider2;
        this.falsingCollectorProvider = provider3;
        this.lockPatternUtilsProvider = provider4;
        this.broadcastDispatcherProvider = provider5;
        this.statusBarKeyguardViewManagerLazyProvider = provider6;
        this.dismissCallbackRegistryProvider = provider7;
        this.updateMonitorProvider = provider8;
        this.dumpManagerProvider = provider9;
        this.powerManagerProvider = provider10;
        this.trustManagerProvider = provider11;
        this.userSwitcherControllerProvider = provider12;
        this.uiBgExecutorProvider = provider13;
        this.deviceConfigProvider = provider14;
        this.navigationModeControllerProvider = provider15;
        this.keyguardDisplayManagerProvider = provider16;
        this.dozeParametersProvider = provider17;
        this.statusBarStateControllerProvider = provider18;
        this.keyguardStateControllerProvider = provider19;
        this.keyguardUnlockAnimationControllerProvider = provider20;
        this.screenOffAnimationControllerProvider = provider21;
        this.notificationShadeDepthControllerProvider = provider22;
        this.screenOnCoordinatorProvider = provider23;
        this.interactionJankMonitorProvider = provider24;
        this.dreamOverlayStateControllerProvider = provider25;
        this.shadeControllerProvider = provider26;
        this.notificationShadeWindowControllerProvider = provider27;
        this.activityLaunchAnimatorProvider = provider28;
        this.scrimControllerLazyProvider = provider29;
    }

    public static KeyguardModule_NewKeyguardViewMediatorFactory create(Provider<Context> provider, Provider<UserTracker> provider2, Provider<FalsingCollector> provider3, Provider<LockPatternUtils> provider4, Provider<BroadcastDispatcher> provider5, Provider<KeyguardViewController> provider6, Provider<DismissCallbackRegistry> provider7, Provider<KeyguardUpdateMonitor> provider8, Provider<DumpManager> provider9, Provider<PowerManager> provider10, Provider<TrustManager> provider11, Provider<UserSwitcherController> provider12, Provider<Executor> provider13, Provider<DeviceConfigProxy> provider14, Provider<NavigationModeController> provider15, Provider<KeyguardDisplayManager> provider16, Provider<DozeParameters> provider17, Provider<SysuiStatusBarStateController> provider18, Provider<KeyguardStateController> provider19, Provider<KeyguardUnlockAnimationController> provider20, Provider<ScreenOffAnimationController> provider21, Provider<NotificationShadeDepthController> provider22, Provider<ScreenOnCoordinator> provider23, Provider<InteractionJankMonitor> provider24, Provider<DreamOverlayStateController> provider25, Provider<ShadeController> provider26, Provider<NotificationShadeWindowController> provider27, Provider<ActivityLaunchAnimator> provider28, Provider<ScrimController> provider29) {
        return new KeyguardModule_NewKeyguardViewMediatorFactory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17, provider18, provider19, provider20, provider21, provider22, provider23, provider24, provider25, provider26, provider27, provider28, provider29);
    }

    public static KeyguardViewMediator newKeyguardViewMediator(Context context, UserTracker userTracker, FalsingCollector falsingCollector, LockPatternUtils lockPatternUtils, BroadcastDispatcher broadcastDispatcher, Lazy<KeyguardViewController> lazy, DismissCallbackRegistry dismissCallbackRegistry, KeyguardUpdateMonitor keyguardUpdateMonitor, DumpManager dumpManager, PowerManager powerManager, TrustManager trustManager, UserSwitcherController userSwitcherController, Executor executor, DeviceConfigProxy deviceConfigProxy, NavigationModeController navigationModeController, KeyguardDisplayManager keyguardDisplayManager, DozeParameters dozeParameters, SysuiStatusBarStateController sysuiStatusBarStateController, KeyguardStateController keyguardStateController, Lazy<KeyguardUnlockAnimationController> lazy2, ScreenOffAnimationController screenOffAnimationController, Lazy<NotificationShadeDepthController> lazy3, ScreenOnCoordinator screenOnCoordinator, InteractionJankMonitor interactionJankMonitor, DreamOverlayStateController dreamOverlayStateController, Lazy<ShadeController> lazy4, Lazy<NotificationShadeWindowController> lazy5, Lazy<ActivityLaunchAnimator> lazy6, Lazy<ScrimController> lazy7) {
        return (KeyguardViewMediator) Preconditions.checkNotNullFromProvides(KeyguardModule.newKeyguardViewMediator(context, userTracker, falsingCollector, lockPatternUtils, broadcastDispatcher, lazy, dismissCallbackRegistry, keyguardUpdateMonitor, dumpManager, powerManager, trustManager, userSwitcherController, executor, deviceConfigProxy, navigationModeController, keyguardDisplayManager, dozeParameters, sysuiStatusBarStateController, keyguardStateController, lazy2, screenOffAnimationController, lazy3, screenOnCoordinator, interactionJankMonitor, dreamOverlayStateController, lazy4, lazy5, lazy6, lazy7));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public KeyguardViewMediator m2912get() {
        return newKeyguardViewMediator((Context) this.contextProvider.get(), (UserTracker) this.userTrackerProvider.get(), (FalsingCollector) this.falsingCollectorProvider.get(), (LockPatternUtils) this.lockPatternUtilsProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), DoubleCheck.lazy(this.statusBarKeyguardViewManagerLazyProvider), (DismissCallbackRegistry) this.dismissCallbackRegistryProvider.get(), (KeyguardUpdateMonitor) this.updateMonitorProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (PowerManager) this.powerManagerProvider.get(), (TrustManager) this.trustManagerProvider.get(), (UserSwitcherController) this.userSwitcherControllerProvider.get(), (Executor) this.uiBgExecutorProvider.get(), (DeviceConfigProxy) this.deviceConfigProvider.get(), (NavigationModeController) this.navigationModeControllerProvider.get(), (KeyguardDisplayManager) this.keyguardDisplayManagerProvider.get(), (DozeParameters) this.dozeParametersProvider.get(), (SysuiStatusBarStateController) this.statusBarStateControllerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), DoubleCheck.lazy(this.keyguardUnlockAnimationControllerProvider), (ScreenOffAnimationController) this.screenOffAnimationControllerProvider.get(), DoubleCheck.lazy(this.notificationShadeDepthControllerProvider), (ScreenOnCoordinator) this.screenOnCoordinatorProvider.get(), (InteractionJankMonitor) this.interactionJankMonitorProvider.get(), (DreamOverlayStateController) this.dreamOverlayStateControllerProvider.get(), DoubleCheck.lazy(this.shadeControllerProvider), DoubleCheck.lazy(this.notificationShadeWindowControllerProvider), DoubleCheck.lazy(this.activityLaunchAnimatorProvider), DoubleCheck.lazy(this.scrimControllerLazyProvider));
    }
}