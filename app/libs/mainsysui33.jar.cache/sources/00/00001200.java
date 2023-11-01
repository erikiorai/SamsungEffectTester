package com.android.systemui.biometrics;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Handler;
import android.os.PowerManager;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.util.LatencyTracker;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.biometrics.udfps.SinglePointerTouchProcessor;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.phone.SystemUIDialogManager;
import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.Execution;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import java.util.Optional;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsController_Factory.class */
public final class UdfpsController_Factory implements Factory<UdfpsController> {
    public final Provider<AccessibilityManager> accessibilityManagerProvider;
    public final Provider<ActivityLaunchAnimator> activityLaunchAnimatorProvider;
    public final Provider<Optional<Provider<AlternateUdfpsTouchProvider>>> alternateTouchProvider;
    public final Provider<Executor> biometricsExecutorProvider;
    public final Provider<ConfigurationController> configurationControllerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<SystemUIDialogManager> dialogManagerProvider;
    public final Provider<DisplayManager> displayManagerProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<Execution> executionProvider;
    public final Provider<FalsingManager> falsingManagerProvider;
    public final Provider<FeatureFlags> featureFlagsProvider;
    public final Provider<DelayableExecutor> fgExecutorProvider;
    public final Provider<FingerprintManager> fingerprintManagerProvider;
    public final Provider<LayoutInflater> inflaterProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;
    public final Provider<LatencyTracker> latencyTrackerProvider;
    public final Provider<LockscreenShadeTransitionController> lockscreenShadeTransitionControllerProvider;
    public final Provider<Handler> mainHandlerProvider;
    public final Provider<PowerManager> powerManagerProvider;
    public final Provider<PrimaryBouncerInteractor> primaryBouncerInteractorProvider;
    public final Provider<ScreenLifecycle> screenLifecycleProvider;
    public final Provider<SecureSettings> secureSettingsProvider;
    public final Provider<ShadeExpansionStateManager> shadeExpansionStateManagerProvider;
    public final Provider<SinglePointerTouchProcessor> singlePointerTouchProcessorProvider;
    public final Provider<StatusBarKeyguardViewManager> statusBarKeyguardViewManagerProvider;
    public final Provider<StatusBarStateController> statusBarStateControllerProvider;
    public final Provider<SystemClock> systemClockProvider;
    public final Provider<UdfpsHapticsSimulator> udfpsHapticsSimulatorProvider;
    public final Provider<UdfpsShell> udfpsShellProvider;
    public final Provider<UnlockedScreenOffAnimationController> unlockedScreenOffAnimationControllerProvider;
    public final Provider<VibratorHelper> vibratorProvider;
    public final Provider<WindowManager> windowManagerProvider;

    public UdfpsController_Factory(Provider<Context> provider, Provider<Execution> provider2, Provider<LayoutInflater> provider3, Provider<FingerprintManager> provider4, Provider<WindowManager> provider5, Provider<StatusBarStateController> provider6, Provider<DelayableExecutor> provider7, Provider<ShadeExpansionStateManager> provider8, Provider<StatusBarKeyguardViewManager> provider9, Provider<DumpManager> provider10, Provider<KeyguardUpdateMonitor> provider11, Provider<FeatureFlags> provider12, Provider<FalsingManager> provider13, Provider<PowerManager> provider14, Provider<AccessibilityManager> provider15, Provider<LockscreenShadeTransitionController> provider16, Provider<ScreenLifecycle> provider17, Provider<VibratorHelper> provider18, Provider<UdfpsHapticsSimulator> provider19, Provider<UdfpsShell> provider20, Provider<KeyguardStateController> provider21, Provider<DisplayManager> provider22, Provider<Handler> provider23, Provider<ConfigurationController> provider24, Provider<SystemClock> provider25, Provider<UnlockedScreenOffAnimationController> provider26, Provider<SystemUIDialogManager> provider27, Provider<LatencyTracker> provider28, Provider<ActivityLaunchAnimator> provider29, Provider<Optional<Provider<AlternateUdfpsTouchProvider>>> provider30, Provider<Executor> provider31, Provider<PrimaryBouncerInteractor> provider32, Provider<SinglePointerTouchProcessor> provider33, Provider<SecureSettings> provider34) {
        this.contextProvider = provider;
        this.executionProvider = provider2;
        this.inflaterProvider = provider3;
        this.fingerprintManagerProvider = provider4;
        this.windowManagerProvider = provider5;
        this.statusBarStateControllerProvider = provider6;
        this.fgExecutorProvider = provider7;
        this.shadeExpansionStateManagerProvider = provider8;
        this.statusBarKeyguardViewManagerProvider = provider9;
        this.dumpManagerProvider = provider10;
        this.keyguardUpdateMonitorProvider = provider11;
        this.featureFlagsProvider = provider12;
        this.falsingManagerProvider = provider13;
        this.powerManagerProvider = provider14;
        this.accessibilityManagerProvider = provider15;
        this.lockscreenShadeTransitionControllerProvider = provider16;
        this.screenLifecycleProvider = provider17;
        this.vibratorProvider = provider18;
        this.udfpsHapticsSimulatorProvider = provider19;
        this.udfpsShellProvider = provider20;
        this.keyguardStateControllerProvider = provider21;
        this.displayManagerProvider = provider22;
        this.mainHandlerProvider = provider23;
        this.configurationControllerProvider = provider24;
        this.systemClockProvider = provider25;
        this.unlockedScreenOffAnimationControllerProvider = provider26;
        this.dialogManagerProvider = provider27;
        this.latencyTrackerProvider = provider28;
        this.activityLaunchAnimatorProvider = provider29;
        this.alternateTouchProvider = provider30;
        this.biometricsExecutorProvider = provider31;
        this.primaryBouncerInteractorProvider = provider32;
        this.singlePointerTouchProcessorProvider = provider33;
        this.secureSettingsProvider = provider34;
    }

    public static UdfpsController_Factory create(Provider<Context> provider, Provider<Execution> provider2, Provider<LayoutInflater> provider3, Provider<FingerprintManager> provider4, Provider<WindowManager> provider5, Provider<StatusBarStateController> provider6, Provider<DelayableExecutor> provider7, Provider<ShadeExpansionStateManager> provider8, Provider<StatusBarKeyguardViewManager> provider9, Provider<DumpManager> provider10, Provider<KeyguardUpdateMonitor> provider11, Provider<FeatureFlags> provider12, Provider<FalsingManager> provider13, Provider<PowerManager> provider14, Provider<AccessibilityManager> provider15, Provider<LockscreenShadeTransitionController> provider16, Provider<ScreenLifecycle> provider17, Provider<VibratorHelper> provider18, Provider<UdfpsHapticsSimulator> provider19, Provider<UdfpsShell> provider20, Provider<KeyguardStateController> provider21, Provider<DisplayManager> provider22, Provider<Handler> provider23, Provider<ConfigurationController> provider24, Provider<SystemClock> provider25, Provider<UnlockedScreenOffAnimationController> provider26, Provider<SystemUIDialogManager> provider27, Provider<LatencyTracker> provider28, Provider<ActivityLaunchAnimator> provider29, Provider<Optional<Provider<AlternateUdfpsTouchProvider>>> provider30, Provider<Executor> provider31, Provider<PrimaryBouncerInteractor> provider32, Provider<SinglePointerTouchProcessor> provider33, Provider<SecureSettings> provider34) {
        return new UdfpsController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17, provider18, provider19, provider20, provider21, provider22, provider23, provider24, provider25, provider26, provider27, provider28, provider29, provider30, provider31, provider32, provider33, provider34);
    }

    public static UdfpsController newInstance(Context context, Execution execution, LayoutInflater layoutInflater, FingerprintManager fingerprintManager, WindowManager windowManager, StatusBarStateController statusBarStateController, DelayableExecutor delayableExecutor, ShadeExpansionStateManager shadeExpansionStateManager, StatusBarKeyguardViewManager statusBarKeyguardViewManager, DumpManager dumpManager, KeyguardUpdateMonitor keyguardUpdateMonitor, FeatureFlags featureFlags, FalsingManager falsingManager, PowerManager powerManager, AccessibilityManager accessibilityManager, LockscreenShadeTransitionController lockscreenShadeTransitionController, ScreenLifecycle screenLifecycle, VibratorHelper vibratorHelper, UdfpsHapticsSimulator udfpsHapticsSimulator, UdfpsShell udfpsShell, KeyguardStateController keyguardStateController, DisplayManager displayManager, Handler handler, ConfigurationController configurationController, SystemClock systemClock, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, SystemUIDialogManager systemUIDialogManager, LatencyTracker latencyTracker, ActivityLaunchAnimator activityLaunchAnimator, Optional<Provider<AlternateUdfpsTouchProvider>> optional, Executor executor, PrimaryBouncerInteractor primaryBouncerInteractor, SinglePointerTouchProcessor singlePointerTouchProcessor, SecureSettings secureSettings) {
        return new UdfpsController(context, execution, layoutInflater, fingerprintManager, windowManager, statusBarStateController, delayableExecutor, shadeExpansionStateManager, statusBarKeyguardViewManager, dumpManager, keyguardUpdateMonitor, featureFlags, falsingManager, powerManager, accessibilityManager, lockscreenShadeTransitionController, screenLifecycle, vibratorHelper, udfpsHapticsSimulator, udfpsShell, keyguardStateController, displayManager, handler, configurationController, systemClock, unlockedScreenOffAnimationController, systemUIDialogManager, latencyTracker, activityLaunchAnimator, optional, executor, primaryBouncerInteractor, singlePointerTouchProcessor, secureSettings);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public UdfpsController m1591get() {
        return newInstance((Context) this.contextProvider.get(), (Execution) this.executionProvider.get(), (LayoutInflater) this.inflaterProvider.get(), (FingerprintManager) this.fingerprintManagerProvider.get(), (WindowManager) this.windowManagerProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (DelayableExecutor) this.fgExecutorProvider.get(), (ShadeExpansionStateManager) this.shadeExpansionStateManagerProvider.get(), (StatusBarKeyguardViewManager) this.statusBarKeyguardViewManagerProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get(), (FeatureFlags) this.featureFlagsProvider.get(), (FalsingManager) this.falsingManagerProvider.get(), (PowerManager) this.powerManagerProvider.get(), (AccessibilityManager) this.accessibilityManagerProvider.get(), (LockscreenShadeTransitionController) this.lockscreenShadeTransitionControllerProvider.get(), (ScreenLifecycle) this.screenLifecycleProvider.get(), (VibratorHelper) this.vibratorProvider.get(), (UdfpsHapticsSimulator) this.udfpsHapticsSimulatorProvider.get(), (UdfpsShell) this.udfpsShellProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (DisplayManager) this.displayManagerProvider.get(), (Handler) this.mainHandlerProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (SystemClock) this.systemClockProvider.get(), (UnlockedScreenOffAnimationController) this.unlockedScreenOffAnimationControllerProvider.get(), (SystemUIDialogManager) this.dialogManagerProvider.get(), (LatencyTracker) this.latencyTrackerProvider.get(), (ActivityLaunchAnimator) this.activityLaunchAnimatorProvider.get(), (Optional) this.alternateTouchProvider.get(), (Executor) this.biometricsExecutorProvider.get(), (PrimaryBouncerInteractor) this.primaryBouncerInteractorProvider.get(), (SinglePointerTouchProcessor) this.singlePointerTouchProcessorProvider.get(), (SecureSettings) this.secureSettingsProvider.get());
    }
}