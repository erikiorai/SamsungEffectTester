package com.android.systemui.dagger;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.AlarmManager;
import android.app.IActivityManager;
import android.app.IActivityTaskManager;
import android.app.INotificationManager;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.Service;
import android.app.StatsManager;
import android.app.WallpaperManager;
import android.app.admin.DevicePolicyManager;
import android.app.job.JobScheduler;
import android.app.smartspace.SmartspaceManager;
import android.app.trust.TrustManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.om.OverlayManager;
import android.content.pm.IPackageManager;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutManager;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorPrivacyManager;
import android.hardware.biometrics.BiometricManager;
import android.hardware.camera2.CameraManager;
import android.hardware.devicestate.DeviceStateManager;
import android.hardware.display.AmbientDisplayConfiguration;
import android.hardware.display.ColorDisplayManager;
import android.hardware.display.DisplayManager;
import android.hardware.display.NightDisplayListener;
import android.hardware.face.FaceManager;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.input.InputManager;
import android.media.AudioManager;
import android.media.IAudioService;
import android.media.MediaRouter2Manager;
import android.media.session.MediaSessionManager;
import android.net.ConnectivityManager;
import android.net.NetworkScoreManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.PowerExemptionManager;
import android.os.PowerManager;
import android.os.UserManager;
import android.os.Vibrator;
import android.os.storage.StorageManager;
import android.permission.PermissionManager;
import android.safetycenter.SafetyCenterManager;
import android.service.dreams.IDreamManager;
import android.service.notification.StatusBarNotification;
import android.service.quickaccesswallet.QuickAccessWalletClient;
import android.telecom.TelecomManager;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Choreographer;
import android.view.CrossWindowBlurListeners;
import android.view.GestureDetector;
import android.view.IWindowManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.CaptioningManager;
import android.view.inputmethod.InputMethodManager;
import android.view.textclassifier.TextClassificationManager;
import android.widget.FrameLayout;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelStore;
import com.android.app.motiontool.DdmHandleMotionTool;
import com.android.app.motiontool.MotionToolManager;
import com.android.dream.lowlight.dagger.LowLightDreamModule_ProvidesLowLightDreamComponentFactory;
import com.android.internal.app.AssistUtils;
import com.android.internal.app.IBatteryStats;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.util.LatencyTracker;
import com.android.internal.util.NotificationMessagingUtil;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.ActiveUnlockConfig;
import com.android.keyguard.ActiveUnlockConfig_Factory;
import com.android.keyguard.AdminSecondaryLockScreenController;
import com.android.keyguard.AdminSecondaryLockScreenController_Factory_Factory;
import com.android.keyguard.CarrierText;
import com.android.keyguard.CarrierTextController;
import com.android.keyguard.CarrierTextManager;
import com.android.keyguard.CarrierTextManager_Builder_Factory;
import com.android.keyguard.ClockEventController;
import com.android.keyguard.ClockEventController_Factory;
import com.android.keyguard.EmergencyButtonController;
import com.android.keyguard.EmergencyButtonController_Factory_Factory;
import com.android.keyguard.FaceWakeUpTriggersConfig;
import com.android.keyguard.FaceWakeUpTriggersConfig_Factory;
import com.android.keyguard.KeyguardBiometricLockoutLogger;
import com.android.keyguard.KeyguardBiometricLockoutLogger_Factory;
import com.android.keyguard.KeyguardClockSwitch;
import com.android.keyguard.KeyguardClockSwitchController;
import com.android.keyguard.KeyguardDisplayManager;
import com.android.keyguard.KeyguardDisplayManager_Factory;
import com.android.keyguard.KeyguardHostView;
import com.android.keyguard.KeyguardHostViewController;
import com.android.keyguard.KeyguardHostViewController_Factory;
import com.android.keyguard.KeyguardInputViewController;
import com.android.keyguard.KeyguardInputViewController_Factory_Factory;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardMessageAreaController_Factory_Factory;
import com.android.keyguard.KeyguardSecurityContainer;
import com.android.keyguard.KeyguardSecurityContainerController_Factory_Factory;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardSecurityModel_Factory;
import com.android.keyguard.KeyguardSecurityViewFlipper;
import com.android.keyguard.KeyguardSecurityViewFlipperController;
import com.android.keyguard.KeyguardSecurityViewFlipperController_Factory;
import com.android.keyguard.KeyguardSliceView;
import com.android.keyguard.KeyguardSliceViewController;
import com.android.keyguard.KeyguardSliceViewController_Factory;
import com.android.keyguard.KeyguardStatusView;
import com.android.keyguard.KeyguardStatusViewController;
import com.android.keyguard.KeyguardUnfoldTransition;
import com.android.keyguard.KeyguardUnfoldTransition_Factory;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitor_Factory;
import com.android.keyguard.LiftToActivateListener_Factory;
import com.android.keyguard.LockIconView;
import com.android.keyguard.LockIconViewController;
import com.android.keyguard.LockIconViewController_Factory;
import com.android.keyguard.ViewMediatorCallback;
import com.android.keyguard.clock.ClockInfoModule_ProvideClockInfoListFactory;
import com.android.keyguard.clock.ClockManager;
import com.android.keyguard.clock.ClockManager_Factory;
import com.android.keyguard.clock.ClockOptionsProvider;
import com.android.keyguard.clock.ClockOptionsProvider_MembersInjector;
import com.android.keyguard.dagger.ClockRegistryModule_GetClockRegistryFactory;
import com.android.keyguard.dagger.KeyguardBouncerComponent;
import com.android.keyguard.dagger.KeyguardBouncerModule_ProvidesKeyguardHostViewFactory;
import com.android.keyguard.dagger.KeyguardBouncerModule_ProvidesKeyguardSecurityContainerFactory;
import com.android.keyguard.dagger.KeyguardBouncerModule_ProvidesKeyguardSecurityViewFlipperFactory;
import com.android.keyguard.dagger.KeyguardBouncerModule_ProvidesOptionalSidefpsControllerFactory;
import com.android.keyguard.dagger.KeyguardQsUserSwitchComponent;
import com.android.keyguard.dagger.KeyguardStatusBarViewComponent;
import com.android.keyguard.dagger.KeyguardStatusBarViewModule_GetBatteryMeterViewFactory;
import com.android.keyguard.dagger.KeyguardStatusBarViewModule_GetCarrierTextFactory;
import com.android.keyguard.dagger.KeyguardStatusViewComponent;
import com.android.keyguard.dagger.KeyguardStatusViewModule_GetKeyguardClockSwitchFactory;
import com.android.keyguard.dagger.KeyguardStatusViewModule_GetKeyguardSliceViewFactory;
import com.android.keyguard.dagger.KeyguardUserSwitcherComponent;
import com.android.keyguard.logging.BiometricUnlockLogger;
import com.android.keyguard.logging.BiometricUnlockLogger_Factory;
import com.android.keyguard.logging.FaceMessageDeferralLogger;
import com.android.keyguard.logging.FaceMessageDeferralLogger_Factory;
import com.android.keyguard.logging.KeyguardLogger;
import com.android.keyguard.logging.KeyguardLogger_Factory;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger_Factory;
import com.android.keyguard.mediator.ScreenOnCoordinator;
import com.android.keyguard.mediator.ScreenOnCoordinator_Factory;
import com.android.launcher3.icons.IconFactory;
import com.android.launcher3.icons.IconProvider;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.devicestate.DeviceStateRotationLockSettingsManager;
import com.android.systemui.ActivityIntentHelper;
import com.android.systemui.ActivityIntentHelper_Factory;
import com.android.systemui.ActivityStarterDelegate;
import com.android.systemui.ActivityStarterDelegate_Factory;
import com.android.systemui.BootCompleteCacheImpl;
import com.android.systemui.BootCompleteCacheImpl_Factory;
import com.android.systemui.C0050GuestResetOrExitSessionReceiver_ExitSessionDialog_Factory;
import com.android.systemui.C0051GuestResetOrExitSessionReceiver_ResetSessionDialog_Factory;
import com.android.systemui.C0052GuestResumeSessionReceiver_ResetSessionDialog_Factory;
import com.android.systemui.ChooserSelector;
import com.android.systemui.ChooserSelector_Factory;
import com.android.systemui.CoreStartable;
import com.android.systemui.Dependency;
import com.android.systemui.Dependency_Factory;
import com.android.systemui.ForegroundServiceController;
import com.android.systemui.ForegroundServiceController_Factory;
import com.android.systemui.ForegroundServiceNotificationListener;
import com.android.systemui.ForegroundServiceNotificationListener_Factory;
import com.android.systemui.ForegroundServicesDialog;
import com.android.systemui.ForegroundServicesDialog_Factory;
import com.android.systemui.GuestResetOrExitSessionReceiver;
import com.android.systemui.GuestResetOrExitSessionReceiver_ExitSessionDialog_Factory_Impl;
import com.android.systemui.GuestResetOrExitSessionReceiver_Factory;
import com.android.systemui.GuestResetOrExitSessionReceiver_ResetSessionDialog_Factory_Impl;
import com.android.systemui.GuestResumeSessionReceiver;
import com.android.systemui.GuestResumeSessionReceiver_Factory;
import com.android.systemui.GuestResumeSessionReceiver_ResetSessionDialog_Factory_Impl;
import com.android.systemui.GuestSessionNotification;
import com.android.systemui.GuestSessionNotification_Factory;
import com.android.systemui.InitController;
import com.android.systemui.InitController_Factory;
import com.android.systemui.LatencyTester;
import com.android.systemui.LatencyTester_Factory;
import com.android.systemui.ScreenDecorations;
import com.android.systemui.ScreenDecorations_Factory;
import com.android.systemui.SliceBroadcastRelayHandler;
import com.android.systemui.SliceBroadcastRelayHandler_Factory;
import com.android.systemui.SystemUIAppComponentFactoryBase;
import com.android.systemui.SystemUIAppComponentFactoryBase_MembersInjector;
import com.android.systemui.SystemUIService;
import com.android.systemui.SystemUIService_Factory;
import com.android.systemui.UiOffloadThread;
import com.android.systemui.UiOffloadThread_Factory;
import com.android.systemui.accessibility.AccessibilityButtonModeObserver;
import com.android.systemui.accessibility.AccessibilityButtonModeObserver_Factory;
import com.android.systemui.accessibility.AccessibilityButtonTargetsObserver;
import com.android.systemui.accessibility.AccessibilityButtonTargetsObserver_Factory;
import com.android.systemui.accessibility.ModeSwitchesController;
import com.android.systemui.accessibility.ModeSwitchesController_Factory;
import com.android.systemui.accessibility.SystemActions;
import com.android.systemui.accessibility.SystemActions_Factory;
import com.android.systemui.accessibility.WindowMagnification;
import com.android.systemui.accessibility.WindowMagnification_Factory;
import com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController;
import com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController_Factory;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.appops.AppOpsControllerImpl;
import com.android.systemui.appops.AppOpsControllerImpl_Factory;
import com.android.systemui.assist.AssistLogger;
import com.android.systemui.assist.AssistLogger_Factory;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.assist.AssistManager_Factory;
import com.android.systemui.assist.AssistModule_ProvideAssistUtilsFactory;
import com.android.systemui.assist.PhoneStateMonitor;
import com.android.systemui.assist.PhoneStateMonitor_Factory;
import com.android.systemui.assist.ui.DefaultUiController;
import com.android.systemui.assist.ui.DefaultUiController_Factory;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.battery.BatteryMeterViewController;
import com.android.systemui.battery.BatteryMeterViewController_Factory;
import com.android.systemui.biometrics.AlternateUdfpsTouchProvider;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.AuthController_Factory;
import com.android.systemui.biometrics.AuthRippleController;
import com.android.systemui.biometrics.AuthRippleController_Factory;
import com.android.systemui.biometrics.AuthRippleView;
import com.android.systemui.biometrics.FaceHelpMessageDeferral;
import com.android.systemui.biometrics.FaceHelpMessageDeferral_Factory;
import com.android.systemui.biometrics.SideFpsController;
import com.android.systemui.biometrics.SideFpsController_Factory;
import com.android.systemui.biometrics.UdfpsController;
import com.android.systemui.biometrics.UdfpsController_Factory;
import com.android.systemui.biometrics.UdfpsHapticsSimulator;
import com.android.systemui.biometrics.UdfpsHapticsSimulator_Factory;
import com.android.systemui.biometrics.UdfpsShell;
import com.android.systemui.biometrics.UdfpsShell_Factory;
import com.android.systemui.biometrics.dagger.BiometricsModule_ProvidesPluginExecutorFactory;
import com.android.systemui.biometrics.dagger.UdfpsModule_Companion_ProvidesOverlapDetectorFactory;
import com.android.systemui.biometrics.udfps.OverlapDetector;
import com.android.systemui.biometrics.udfps.SinglePointerTouchProcessor;
import com.android.systemui.biometrics.udfps.SinglePointerTouchProcessor_Factory;
import com.android.systemui.bluetooth.BluetoothLogger;
import com.android.systemui.bluetooth.BluetoothLogger_Factory;
import com.android.systemui.bluetooth.BroadcastDialogController;
import com.android.systemui.bluetooth.BroadcastDialogController_Factory;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.broadcast.BroadcastDispatcherStartable;
import com.android.systemui.broadcast.BroadcastDispatcherStartable_Factory;
import com.android.systemui.broadcast.BroadcastDispatcher_Factory;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.broadcast.BroadcastSender_Factory;
import com.android.systemui.broadcast.PendingRemovalStore;
import com.android.systemui.broadcast.PendingRemovalStore_Factory;
import com.android.systemui.broadcast.logging.BroadcastDispatcherLogger;
import com.android.systemui.broadcast.logging.BroadcastDispatcherLogger_Factory;
import com.android.systemui.camera.CameraGestureHelper;
import com.android.systemui.camera.CameraGestureHelper_Factory;
import com.android.systemui.camera.CameraIntentsWrapper;
import com.android.systemui.camera.CameraIntentsWrapper_Factory;
import com.android.systemui.charging.WiredChargingRippleController;
import com.android.systemui.charging.WiredChargingRippleController_Factory;
import com.android.systemui.classifier.BrightLineFalsingManager;
import com.android.systemui.classifier.BrightLineFalsingManager_Factory;
import com.android.systemui.classifier.DiagonalClassifier_Factory;
import com.android.systemui.classifier.DistanceClassifier_Factory;
import com.android.systemui.classifier.DoubleTapClassifier;
import com.android.systemui.classifier.DoubleTapClassifier_Factory;
import com.android.systemui.classifier.FalsingA11yDelegate;
import com.android.systemui.classifier.FalsingA11yDelegate_Factory;
import com.android.systemui.classifier.FalsingClassifier;
import com.android.systemui.classifier.FalsingCollectorImpl_Factory;
import com.android.systemui.classifier.FalsingDataProvider;
import com.android.systemui.classifier.FalsingDataProvider_Factory;
import com.android.systemui.classifier.FalsingManagerProxy;
import com.android.systemui.classifier.FalsingManagerProxy_Factory;
import com.android.systemui.classifier.FalsingModule_ProvidesBrightLineGestureClassifiersFactory;
import com.android.systemui.classifier.FalsingModule_ProvidesDoubleTapTimeoutMsFactory;
import com.android.systemui.classifier.FalsingModule_ProvidesDoubleTapTouchSlopFactory;
import com.android.systemui.classifier.FalsingModule_ProvidesLongTapTouchSlopFactory;
import com.android.systemui.classifier.FalsingModule_ProvidesSingleTapTouchSlopFactory;
import com.android.systemui.classifier.HistoryTracker;
import com.android.systemui.classifier.HistoryTracker_Factory;
import com.android.systemui.classifier.LongTapClassifier;
import com.android.systemui.classifier.LongTapClassifier_Factory;
import com.android.systemui.classifier.PointerCountClassifier_Factory;
import com.android.systemui.classifier.ProximityClassifier_Factory;
import com.android.systemui.classifier.SingleTapClassifier;
import com.android.systemui.classifier.SingleTapClassifier_Factory;
import com.android.systemui.classifier.TypeClassifier;
import com.android.systemui.classifier.TypeClassifier_Factory;
import com.android.systemui.classifier.ZigZagClassifier_Factory;
import com.android.systemui.clipboardoverlay.ClipboardListener;
import com.android.systemui.clipboardoverlay.ClipboardListener_Factory;
import com.android.systemui.clipboardoverlay.ClipboardOverlayController;
import com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacyFactory;
import com.android.systemui.clipboardoverlay.ClipboardOverlayControllerLegacyFactory_Factory;
import com.android.systemui.clipboardoverlay.ClipboardOverlayController_Factory;
import com.android.systemui.clipboardoverlay.ClipboardOverlayUtils_Factory;
import com.android.systemui.clipboardoverlay.ClipboardOverlayView;
import com.android.systemui.clipboardoverlay.ClipboardOverlayWindow;
import com.android.systemui.clipboardoverlay.ClipboardOverlayWindow_Factory;
import com.android.systemui.clipboardoverlay.ClipboardToast_Factory;
import com.android.systemui.clipboardoverlay.dagger.ClipboardOverlayModule_ProvideClipboardOverlayViewFactory;
import com.android.systemui.clipboardoverlay.dagger.ClipboardOverlayModule_ProvideWindowContextFactory;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.colorextraction.SysuiColorExtractor_Factory;
import com.android.systemui.controls.ControlsMetricsLoggerImpl;
import com.android.systemui.controls.ControlsMetricsLoggerImpl_Factory;
import com.android.systemui.controls.CustomIconCache;
import com.android.systemui.controls.CustomIconCache_Factory;
import com.android.systemui.controls.controller.ControlsBindingControllerImpl;
import com.android.systemui.controls.controller.ControlsBindingControllerImpl_Factory;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.controller.ControlsControllerImpl_Factory;
import com.android.systemui.controls.controller.ControlsFavoritePersistenceWrapper;
import com.android.systemui.controls.controller.ControlsTileResourceConfiguration;
import com.android.systemui.controls.dagger.ControlsComponent;
import com.android.systemui.controls.dagger.ControlsComponent_Factory;
import com.android.systemui.controls.dagger.ControlsModule_ProvidesControlsFeatureEnabledFactory;
import com.android.systemui.controls.management.ControlsEditingActivity;
import com.android.systemui.controls.management.ControlsEditingActivity_Factory;
import com.android.systemui.controls.management.ControlsFavoritingActivity;
import com.android.systemui.controls.management.ControlsFavoritingActivity_Factory;
import com.android.systemui.controls.management.ControlsListingControllerImpl;
import com.android.systemui.controls.management.ControlsListingControllerImpl_Factory;
import com.android.systemui.controls.management.ControlsProviderSelectorActivity;
import com.android.systemui.controls.management.ControlsProviderSelectorActivity_Factory;
import com.android.systemui.controls.management.ControlsRequestDialog;
import com.android.systemui.controls.management.ControlsRequestDialog_Factory;
import com.android.systemui.controls.settings.ControlsSettingsDialogManagerImpl;
import com.android.systemui.controls.settings.ControlsSettingsDialogManagerImpl_Factory;
import com.android.systemui.controls.settings.ControlsSettingsRepositoryImpl;
import com.android.systemui.controls.settings.ControlsSettingsRepositoryImpl_Factory;
import com.android.systemui.controls.ui.ControlActionCoordinatorImpl;
import com.android.systemui.controls.ui.ControlActionCoordinatorImpl_Factory;
import com.android.systemui.controls.ui.ControlsActivity;
import com.android.systemui.controls.ui.ControlsActivity_Factory;
import com.android.systemui.controls.ui.ControlsUiControllerImpl;
import com.android.systemui.controls.ui.ControlsUiControllerImpl_Factory;
import com.android.systemui.dagger.NightDisplayListenerModule;
import com.android.systemui.dagger.ReferenceGlobalRootComponent;
import com.android.systemui.dagger.ReferenceSysUIComponent;
import com.android.systemui.dagger.SysUIComponent;
import com.android.systemui.dagger.WMComponent;
import com.android.systemui.decor.FaceScanningProviderFactory;
import com.android.systemui.decor.FaceScanningProviderFactory_Factory;
import com.android.systemui.decor.PrivacyDotDecorProviderFactory;
import com.android.systemui.decor.PrivacyDotDecorProviderFactory_Factory;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.demomode.dagger.DemoModeModule_ProvideDemoModeControllerFactory;
import com.android.systemui.dock.DockManagerImpl;
import com.android.systemui.dock.DockManagerImpl_Factory;
import com.android.systemui.doze.AlwaysOnDisplayPolicy;
import com.android.systemui.doze.AlwaysOnDisplayPolicy_Factory;
import com.android.systemui.doze.DozeAuthRemover;
import com.android.systemui.doze.DozeAuthRemover_Factory;
import com.android.systemui.doze.DozeDockHandler;
import com.android.systemui.doze.DozeDockHandler_Factory;
import com.android.systemui.doze.DozeFalsingManagerAdapter;
import com.android.systemui.doze.DozeFalsingManagerAdapter_Factory;
import com.android.systemui.doze.DozeLog;
import com.android.systemui.doze.DozeLog_Factory;
import com.android.systemui.doze.DozeLogger;
import com.android.systemui.doze.DozeLogger_Factory;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.doze.DozeMachine_Factory;
import com.android.systemui.doze.DozePauser;
import com.android.systemui.doze.DozePauser_Factory;
import com.android.systemui.doze.DozeScreenBrightness;
import com.android.systemui.doze.DozeScreenBrightness_Factory;
import com.android.systemui.doze.DozeScreenState;
import com.android.systemui.doze.DozeScreenState_Factory;
import com.android.systemui.doze.DozeService;
import com.android.systemui.doze.DozeService_Factory;
import com.android.systemui.doze.DozeSuppressor;
import com.android.systemui.doze.DozeSuppressor_Factory;
import com.android.systemui.doze.DozeTransitionListener;
import com.android.systemui.doze.DozeTransitionListener_Factory;
import com.android.systemui.doze.DozeTriggers;
import com.android.systemui.doze.DozeTriggers_Factory;
import com.android.systemui.doze.DozeUi;
import com.android.systemui.doze.DozeUi_Factory;
import com.android.systemui.doze.DozeWallpaperState;
import com.android.systemui.doze.DozeWallpaperState_Factory;
import com.android.systemui.doze.dagger.DozeComponent;
import com.android.systemui.doze.dagger.DozeModule_ProvidesBrightnessSensorsFactory;
import com.android.systemui.doze.dagger.DozeModule_ProvidesDozeMachinePartsFactory;
import com.android.systemui.doze.dagger.DozeModule_ProvidesDozeWakeLockFactory;
import com.android.systemui.doze.dagger.DozeModule_ProvidesWrappedServiceFactory;
import com.android.systemui.doze.util.BurnInHelperWrapper_Factory;
import com.android.systemui.dreams.DreamOverlayAnimationsController;
import com.android.systemui.dreams.DreamOverlayAnimationsController_Factory;
import com.android.systemui.dreams.DreamOverlayCallbackController;
import com.android.systemui.dreams.DreamOverlayCallbackController_Factory;
import com.android.systemui.dreams.DreamOverlayContainerView;
import com.android.systemui.dreams.DreamOverlayContainerViewController;
import com.android.systemui.dreams.DreamOverlayContainerViewController_Factory;
import com.android.systemui.dreams.DreamOverlayNotificationCountProvider;
import com.android.systemui.dreams.DreamOverlayService;
import com.android.systemui.dreams.DreamOverlayService_Factory;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.dreams.DreamOverlayStateController_Factory;
import com.android.systemui.dreams.DreamOverlayStatusBarItemsProvider;
import com.android.systemui.dreams.DreamOverlayStatusBarItemsProvider_Factory;
import com.android.systemui.dreams.DreamOverlayStatusBarView;
import com.android.systemui.dreams.DreamOverlayStatusBarViewController;
import com.android.systemui.dreams.DreamOverlayStatusBarViewController_Factory;
import com.android.systemui.dreams.complication.Complication;
import com.android.systemui.dreams.complication.ComplicationCollectionLiveData;
import com.android.systemui.dreams.complication.ComplicationCollectionLiveData_Factory;
import com.android.systemui.dreams.complication.ComplicationCollectionViewModel;
import com.android.systemui.dreams.complication.ComplicationCollectionViewModel_Factory;
import com.android.systemui.dreams.complication.ComplicationHostViewController;
import com.android.systemui.dreams.complication.ComplicationHostViewController_Factory;
import com.android.systemui.dreams.complication.ComplicationId;
import com.android.systemui.dreams.complication.ComplicationLayoutEngine;
import com.android.systemui.dreams.complication.ComplicationLayoutEngine_Factory;
import com.android.systemui.dreams.complication.ComplicationViewModel;
import com.android.systemui.dreams.complication.ComplicationViewModelProvider;
import com.android.systemui.dreams.complication.ComplicationViewModelTransformer;
import com.android.systemui.dreams.complication.ComplicationViewModelTransformer_Factory;
import com.android.systemui.dreams.complication.dagger.ComplicationHostViewModule_ProvidesComplicationHostViewFactory;
import com.android.systemui.dreams.complication.dagger.ComplicationHostViewModule_ProvidesComplicationPaddingFactory;
import com.android.systemui.dreams.complication.dagger.ComplicationHostViewModule_ProvidesComplicationsFadeInDurationFactory;
import com.android.systemui.dreams.complication.dagger.ComplicationHostViewModule_ProvidesComplicationsFadeOutDelayFactory;
import com.android.systemui.dreams.complication.dagger.ComplicationHostViewModule_ProvidesComplicationsFadeOutDurationFactory;
import com.android.systemui.dreams.complication.dagger.ComplicationHostViewModule_ProvidesComplicationsRestoreTimeoutFactory;
import com.android.systemui.dreams.complication.dagger.ComplicationModule_ProvidesComplicationCollectionViewModelFactory;
import com.android.systemui.dreams.complication.dagger.ComplicationModule_ProvidesVisibilityControllerFactory;
import com.android.systemui.dreams.complication.dagger.ComplicationViewModelComponent;
import com.android.systemui.dreams.dagger.DreamModule_ProvidesDreamOnlyEnabledForDockUserFactory;
import com.android.systemui.dreams.dagger.DreamModule_ProvidesDreamOverlayEnabledFactory;
import com.android.systemui.dreams.dagger.DreamModule_ProvidesDreamOverlayNotificationCountProviderFactory;
import com.android.systemui.dreams.dagger.DreamModule_ProvidesDreamOverlayServiceFactory;
import com.android.systemui.dreams.dagger.DreamModule_ProvidesDreamSupportedFactory;
import com.android.systemui.dreams.dagger.DreamOverlayComponent;
import com.android.systemui.dreams.dagger.DreamOverlayModule_ProvidesBurnInProtectionUpdateIntervalFactory;
import com.android.systemui.dreams.dagger.DreamOverlayModule_ProvidesDreamBlurRadiusFactory;
import com.android.systemui.dreams.dagger.DreamOverlayModule_ProvidesDreamInBlurAnimationDurationFactory;
import com.android.systemui.dreams.dagger.DreamOverlayModule_ProvidesDreamInComplicationsAnimationDurationFactory;
import com.android.systemui.dreams.dagger.DreamOverlayModule_ProvidesDreamInComplicationsTranslationYDurationFactory;
import com.android.systemui.dreams.dagger.DreamOverlayModule_ProvidesDreamInComplicationsTranslationYFactory;
import com.android.systemui.dreams.dagger.DreamOverlayModule_ProvidesDreamOverlayContainerViewFactory;
import com.android.systemui.dreams.dagger.DreamOverlayModule_ProvidesDreamOverlayContentViewFactory;
import com.android.systemui.dreams.dagger.DreamOverlayModule_ProvidesDreamOverlayStatusBarViewFactory;
import com.android.systemui.dreams.dagger.DreamOverlayModule_ProvidesLifecycleFactory;
import com.android.systemui.dreams.dagger.DreamOverlayModule_ProvidesLifecycleOwnerFactory;
import com.android.systemui.dreams.dagger.DreamOverlayModule_ProvidesLifecycleRegistryFactory;
import com.android.systemui.dreams.dagger.DreamOverlayModule_ProvidesMaxBurnInOffsetFactory;
import com.android.systemui.dreams.dagger.DreamOverlayModule_ProvidesMillisUntilFullJitterFactory;
import com.android.systemui.dreams.dagger.DreamOverlayModule_ProvidesTouchInsetManagerFactory;
import com.android.systemui.dreams.dagger.DreamOverlayModule_ProvidesTouchInsetSessionFactory;
import com.android.systemui.dreams.touch.BouncerSwipeTouchHandler;
import com.android.systemui.dreams.touch.DreamOverlayTouchMonitor;
import com.android.systemui.dreams.touch.DreamTouchHandler;
import com.android.systemui.dreams.touch.HideComplicationTouchHandler;
import com.android.systemui.dreams.touch.HideComplicationTouchHandler_Factory;
import com.android.systemui.dreams.touch.InputSession;
import com.android.systemui.dreams.touch.dagger.BouncerSwipeModule;
import com.android.systemui.dreams.touch.dagger.BouncerSwipeModule_ProvidesBouncerSwipeTouchHandlerFactory;
import com.android.systemui.dreams.touch.dagger.BouncerSwipeModule_ProvidesSwipeToBouncerFlingAnimationUtilsClosingFactory;
import com.android.systemui.dreams.touch.dagger.BouncerSwipeModule_ProvidesSwipeToBouncerFlingAnimationUtilsOpeningFactory;
import com.android.systemui.dreams.touch.dagger.BouncerSwipeModule_ProvidesValueAnimatorCreatorFactory;
import com.android.systemui.dreams.touch.dagger.BouncerSwipeModule_ProvidesVelocityTrackerFactoryFactory;
import com.android.systemui.dreams.touch.dagger.HideComplicationModule_ProvidesHideComplicationTouchHandlerFactory;
import com.android.systemui.dreams.touch.dagger.InputSessionComponent;
import com.android.systemui.dump.DumpHandler;
import com.android.systemui.dump.DumpHandler_Factory;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.dump.DumpManager_Factory;
import com.android.systemui.dump.LogBufferEulogizer;
import com.android.systemui.dump.LogBufferEulogizer_Factory;
import com.android.systemui.dump.LogBufferFreezer;
import com.android.systemui.dump.LogBufferFreezer_Factory;
import com.android.systemui.dump.SystemUIAuxiliaryDumpService;
import com.android.systemui.dump.SystemUIAuxiliaryDumpService_Factory;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.FeatureFlagsDebug;
import com.android.systemui.flags.FeatureFlagsDebugRestarter;
import com.android.systemui.flags.FeatureFlagsDebugRestarter_Factory;
import com.android.systemui.flags.FeatureFlagsDebugStartable;
import com.android.systemui.flags.FeatureFlagsDebugStartable_Factory;
import com.android.systemui.flags.FeatureFlagsDebug_Factory;
import com.android.systemui.flags.FlagCommand;
import com.android.systemui.flags.FlagCommand_Factory;
import com.android.systemui.flags.FlagManager;
import com.android.systemui.flags.FlagsCommonModule_ProvidesAllFlagsFactory;
import com.android.systemui.flags.FlagsModule_ProvideFlagManagerFactory;
import com.android.systemui.flags.ServerFlagReader;
import com.android.systemui.flags.ServerFlagReaderModule_BindsReaderFactory;
import com.android.systemui.flags.SystemExitRestarter;
import com.android.systemui.flags.SystemExitRestarter_Factory;
import com.android.systemui.flags.SystemPropertiesHelper;
import com.android.systemui.flags.SystemPropertiesHelper_Factory;
import com.android.systemui.fragments.FragmentService;
import com.android.systemui.fragments.FragmentService_Factory;
import com.android.systemui.globalactions.GlobalActionsComponent;
import com.android.systemui.globalactions.GlobalActionsComponent_Factory;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import com.android.systemui.globalactions.GlobalActionsDialogLite_Factory;
import com.android.systemui.globalactions.GlobalActionsImpl;
import com.android.systemui.globalactions.GlobalActionsImpl_Factory;
import com.android.systemui.hdmi.HdmiCecSetMenuLanguageActivity;
import com.android.systemui.hdmi.HdmiCecSetMenuLanguageActivity_Factory;
import com.android.systemui.hdmi.HdmiCecSetMenuLanguageHelper;
import com.android.systemui.hdmi.HdmiCecSetMenuLanguageHelper_Factory;
import com.android.systemui.keyboard.KeyboardUI;
import com.android.systemui.keyboard.KeyboardUI_Factory;
import com.android.systemui.keyguard.CustomizationProvider;
import com.android.systemui.keyguard.CustomizationProvider_MembersInjector;
import com.android.systemui.keyguard.DismissCallbackRegistry;
import com.android.systemui.keyguard.DismissCallbackRegistry_Factory;
import com.android.systemui.keyguard.KeyguardLifecyclesDispatcher;
import com.android.systemui.keyguard.KeyguardLifecyclesDispatcher_Factory;
import com.android.systemui.keyguard.KeyguardService;
import com.android.systemui.keyguard.KeyguardService_Factory;
import com.android.systemui.keyguard.KeyguardSliceProvider;
import com.android.systemui.keyguard.KeyguardSliceProvider_MembersInjector;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController;
import com.android.systemui.keyguard.KeyguardUnlockAnimationController_Factory;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.LifecycleScreenStatusProvider;
import com.android.systemui.keyguard.LifecycleScreenStatusProvider_Factory;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.ScreenLifecycle_Factory;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle_Factory;
import com.android.systemui.keyguard.WorkLockActivity;
import com.android.systemui.keyguard.WorkLockActivity_Factory;
import com.android.systemui.keyguard.dagger.KeyguardModule;
import com.android.systemui.keyguard.dagger.KeyguardModule_NewKeyguardViewMediatorFactory;
import com.android.systemui.keyguard.dagger.KeyguardModule_ProvidesViewMediatorCallbackFactory;
import com.android.systemui.keyguard.data.BouncerViewImpl;
import com.android.systemui.keyguard.data.BouncerViewImpl_Factory;
import com.android.systemui.keyguard.data.quickaffordance.CameraQuickAffordanceConfig;
import com.android.systemui.keyguard.data.quickaffordance.CameraQuickAffordanceConfig_Factory;
import com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig;
import com.android.systemui.keyguard.data.quickaffordance.DoNotDisturbQuickAffordanceConfig_Factory;
import com.android.systemui.keyguard.data.quickaffordance.FlashlightQuickAffordanceConfig;
import com.android.systemui.keyguard.data.quickaffordance.FlashlightQuickAffordanceConfig_Factory;
import com.android.systemui.keyguard.data.quickaffordance.HomeControlsKeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.data.quickaffordance.HomeControlsKeyguardQuickAffordanceConfig_Factory;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardDataQuickAffordanceModule_Companion_QuickAffordanceConfigsFactory;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLegacySettingSyncer;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLegacySettingSyncer_Factory;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLocalUserSelectionManager;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceLocalUserSelectionManager_Factory;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceProviderClientFactoryImpl;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceProviderClientFactoryImpl_Factory;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceRemoteUserSelectionManager;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceRemoteUserSelectionManager_Factory;
import com.android.systemui.keyguard.data.quickaffordance.QrCodeScannerKeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.data.quickaffordance.QrCodeScannerKeyguardQuickAffordanceConfig_Factory;
import com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.data.quickaffordance.QuickAccessWalletKeyguardQuickAffordanceConfig_Factory;
import com.android.systemui.keyguard.data.repository.KeyguardBouncerRepository;
import com.android.systemui.keyguard.data.repository.KeyguardBouncerRepository_Factory;
import com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository;
import com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository_Factory;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl_Factory;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl_Factory;
import com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl;
import com.android.systemui.keyguard.data.repository.LightRevealScrimRepositoryImpl_Factory;
import com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.FromAodTransitionInteractor_Factory;
import com.android.systemui.keyguard.domain.interactor.FromBouncerTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.FromBouncerTransitionInteractor_Factory;
import com.android.systemui.keyguard.domain.interactor.FromDozingTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.FromDozingTransitionInteractor_Factory;
import com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.FromDreamingTransitionInteractor_Factory;
import com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor_Factory;
import com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor_Factory;
import com.android.systemui.keyguard.domain.interactor.FromOccludedTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.FromOccludedTransitionInteractor_Factory;
import com.android.systemui.keyguard.domain.interactor.KeyguardBottomAreaInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardBottomAreaInteractor_Factory;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor_Factory;
import com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor_Factory;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionAuditLogger;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionAuditLogger_Factory;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionCoreStartable;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionCoreStartable_Factory;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor_Factory;
import com.android.systemui.keyguard.domain.interactor.LightRevealScrimInteractor;
import com.android.systemui.keyguard.domain.interactor.LightRevealScrimInteractor_Factory;
import com.android.systemui.keyguard.domain.interactor.PrimaryBouncerCallbackInteractor;
import com.android.systemui.keyguard.domain.interactor.PrimaryBouncerCallbackInteractor_Factory;
import com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor_Factory;
import com.android.systemui.keyguard.domain.interactor.TransitionInteractor;
import com.android.systemui.keyguard.domain.quickaffordance.KeyguardQuickAffordanceRegistryImpl;
import com.android.systemui.keyguard.domain.quickaffordance.KeyguardQuickAffordanceRegistryImpl_Factory;
import com.android.systemui.keyguard.ui.preview.KeyguardPreviewRendererFactory;
import com.android.systemui.keyguard.ui.preview.KeyguardPreviewRendererFactory_Impl;
import com.android.systemui.keyguard.ui.preview.KeyguardPreviewRenderer_Factory;
import com.android.systemui.keyguard.ui.preview.KeyguardRemotePreviewManager;
import com.android.systemui.keyguard.ui.preview.KeyguardRemotePreviewManager_Factory;
import com.android.systemui.keyguard.ui.viewmodel.DreamingToLockscreenTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.DreamingToLockscreenTransitionViewModel_Factory;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBottomAreaViewModel_Factory;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBouncerViewModel;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardBouncerViewModel_Factory;
import com.android.systemui.keyguard.ui.viewmodel.LightRevealScrimViewModel;
import com.android.systemui.keyguard.ui.viewmodel.LightRevealScrimViewModel_Factory;
import com.android.systemui.keyguard.ui.viewmodel.OccludedToLockscreenTransitionViewModel;
import com.android.systemui.keyguard.ui.viewmodel.OccludedToLockscreenTransitionViewModel_Factory;
import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.log.LogBufferFactory_Factory;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.log.SessionTracker_Factory;
import com.android.systemui.log.dagger.LogModule_ProvideBiometricLogBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideBouncerLogBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideBroadcastDispatcherLogBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideCollapsedSbFragmentLogBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideDozeLogBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideKeyguardClockLogFactory;
import com.android.systemui.log.dagger.LogModule_ProvideKeyguardLogBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideKeyguardUpdateMonitorLogBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideLSShadeTransitionControllerBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideLogcatEchoTrackerFactory;
import com.android.systemui.log.dagger.LogModule_ProvideMediaBrowserBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideMediaCarouselControllerBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideMediaMuteAwaitLogBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideMediaTttReceiverLogBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideMediaTttSenderLogBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideMediaViewLogBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideNearbyMediaDevicesLogBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideNotifInteractionLogBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideNotificationHeadsUpLogBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideNotificationInterruptLogBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideNotificationRenderLogBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideNotificationsLogBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvidePrivacyLogBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideQSFragmentDisableLogBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideQuickSettingsLogBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideShadeHeightLogBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideShadeLogBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideShadeWindowLogBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideStatusBarConnectivityBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideStatusBarNetworkControllerBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideSwipeAwayGestureLogBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvideToastLogBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProviderBluetoothLogBufferFactory;
import com.android.systemui.log.dagger.LogModule_ProvidesMediaTimeoutListenerLogBufferFactory;
import com.android.systemui.log.table.TableLogBuffer;
import com.android.systemui.log.table.TableLogBufferFactory;
import com.android.systemui.log.table.TableLogBufferFactory_Factory;
import com.android.systemui.media.MediaProjectionAppSelectorActivity;
import com.android.systemui.media.MediaProjectionAppSelectorActivity_Factory;
import com.android.systemui.media.RingtonePlayer;
import com.android.systemui.media.RingtonePlayer_Factory;
import com.android.systemui.media.controls.models.player.SeekBarViewModel;
import com.android.systemui.media.controls.models.player.SeekBarViewModel_Factory;
import com.android.systemui.media.controls.models.recommendation.SmartspaceMediaDataProvider_Factory;
import com.android.systemui.media.controls.pipeline.LocalMediaManagerFactory;
import com.android.systemui.media.controls.pipeline.LocalMediaManagerFactory_Factory;
import com.android.systemui.media.controls.pipeline.MediaDataCombineLatest_Factory;
import com.android.systemui.media.controls.pipeline.MediaDataFilter;
import com.android.systemui.media.controls.pipeline.MediaDataFilter_Factory;
import com.android.systemui.media.controls.pipeline.MediaDataManager;
import com.android.systemui.media.controls.pipeline.MediaDataManager_Factory;
import com.android.systemui.media.controls.pipeline.MediaDeviceManager;
import com.android.systemui.media.controls.pipeline.MediaDeviceManager_Factory;
import com.android.systemui.media.controls.pipeline.MediaSessionBasedFilter;
import com.android.systemui.media.controls.pipeline.MediaSessionBasedFilter_Factory;
import com.android.systemui.media.controls.pipeline.MediaTimeoutListener;
import com.android.systemui.media.controls.pipeline.MediaTimeoutListener_Factory;
import com.android.systemui.media.controls.pipeline.MediaTimeoutLogger;
import com.android.systemui.media.controls.pipeline.MediaTimeoutLogger_Factory;
import com.android.systemui.media.controls.resume.MediaBrowserFactory;
import com.android.systemui.media.controls.resume.MediaBrowserFactory_Factory;
import com.android.systemui.media.controls.resume.MediaResumeListener;
import com.android.systemui.media.controls.resume.MediaResumeListener_Factory;
import com.android.systemui.media.controls.resume.ResumeMediaBrowserFactory;
import com.android.systemui.media.controls.resume.ResumeMediaBrowserFactory_Factory;
import com.android.systemui.media.controls.resume.ResumeMediaBrowserLogger;
import com.android.systemui.media.controls.resume.ResumeMediaBrowserLogger_Factory;
import com.android.systemui.media.controls.ui.KeyguardMediaController;
import com.android.systemui.media.controls.ui.KeyguardMediaController_Factory;
import com.android.systemui.media.controls.ui.MediaCarouselController;
import com.android.systemui.media.controls.ui.MediaCarouselControllerLogger;
import com.android.systemui.media.controls.ui.MediaCarouselControllerLogger_Factory;
import com.android.systemui.media.controls.ui.MediaCarouselController_Factory;
import com.android.systemui.media.controls.ui.MediaControlPanel;
import com.android.systemui.media.controls.ui.MediaControlPanel_Factory;
import com.android.systemui.media.controls.ui.MediaHierarchyManager;
import com.android.systemui.media.controls.ui.MediaHierarchyManager_Factory;
import com.android.systemui.media.controls.ui.MediaHost;
import com.android.systemui.media.controls.ui.MediaHostStatesManager;
import com.android.systemui.media.controls.ui.MediaHostStatesManager_Factory;
import com.android.systemui.media.controls.ui.MediaHost_MediaHostStateHolder_Factory;
import com.android.systemui.media.controls.ui.MediaViewController;
import com.android.systemui.media.controls.ui.MediaViewController_Factory;
import com.android.systemui.media.controls.ui.MediaViewLogger;
import com.android.systemui.media.controls.ui.MediaViewLogger_Factory;
import com.android.systemui.media.controls.util.MediaControllerFactory;
import com.android.systemui.media.controls.util.MediaControllerFactory_Factory;
import com.android.systemui.media.controls.util.MediaFeatureFlag;
import com.android.systemui.media.controls.util.MediaFeatureFlag_Factory;
import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.media.controls.util.MediaFlags_Factory;
import com.android.systemui.media.controls.util.MediaUiEventLogger;
import com.android.systemui.media.controls.util.MediaUiEventLogger_Factory;
import com.android.systemui.media.dagger.MediaModule_ProvidesKeyguardMediaHostFactory;
import com.android.systemui.media.dagger.MediaModule_ProvidesMediaMuteAwaitConnectionCliFactory;
import com.android.systemui.media.dagger.MediaModule_ProvidesMediaTttReceiverLoggerFactory;
import com.android.systemui.media.dagger.MediaModule_ProvidesMediaTttSenderLoggerFactory;
import com.android.systemui.media.dagger.MediaModule_ProvidesNearbyMediaDevicesManagerFactory;
import com.android.systemui.media.dagger.MediaModule_ProvidesQSMediaHostFactory;
import com.android.systemui.media.dagger.MediaModule_ProvidesQuickQSMediaHostFactory;
import com.android.systemui.media.dialog.MediaOutputBroadcastDialogFactory;
import com.android.systemui.media.dialog.MediaOutputBroadcastDialogFactory_Factory;
import com.android.systemui.media.dialog.MediaOutputDialogFactory;
import com.android.systemui.media.dialog.MediaOutputDialogFactory_Factory;
import com.android.systemui.media.dialog.MediaOutputDialogReceiver;
import com.android.systemui.media.dialog.MediaOutputDialogReceiver_Factory;
import com.android.systemui.media.muteawait.MediaMuteAwaitConnectionCli;
import com.android.systemui.media.muteawait.MediaMuteAwaitConnectionCli_Factory;
import com.android.systemui.media.muteawait.MediaMuteAwaitConnectionManagerFactory;
import com.android.systemui.media.muteawait.MediaMuteAwaitConnectionManagerFactory_Factory;
import com.android.systemui.media.muteawait.MediaMuteAwaitLogger;
import com.android.systemui.media.muteawait.MediaMuteAwaitLogger_Factory;
import com.android.systemui.media.nearby.NearbyMediaDevicesLogger;
import com.android.systemui.media.nearby.NearbyMediaDevicesLogger_Factory;
import com.android.systemui.media.nearby.NearbyMediaDevicesManager;
import com.android.systemui.media.nearby.NearbyMediaDevicesManager_Factory;
import com.android.systemui.media.taptotransfer.MediaTttCommandLineHelper;
import com.android.systemui.media.taptotransfer.MediaTttCommandLineHelper_Factory;
import com.android.systemui.media.taptotransfer.MediaTttFlags;
import com.android.systemui.media.taptotransfer.MediaTttFlags_Factory;
import com.android.systemui.media.taptotransfer.common.MediaTttLogger;
import com.android.systemui.media.taptotransfer.receiver.ChipReceiverInfo;
import com.android.systemui.media.taptotransfer.receiver.MediaTttChipControllerReceiver;
import com.android.systemui.media.taptotransfer.receiver.MediaTttChipControllerReceiver_Factory;
import com.android.systemui.media.taptotransfer.receiver.MediaTttReceiverUiEventLogger;
import com.android.systemui.media.taptotransfer.receiver.MediaTttReceiverUiEventLogger_Factory;
import com.android.systemui.media.taptotransfer.sender.MediaTttSenderCoordinator;
import com.android.systemui.media.taptotransfer.sender.MediaTttSenderCoordinator_Factory;
import com.android.systemui.media.taptotransfer.sender.MediaTttSenderUiEventLogger;
import com.android.systemui.media.taptotransfer.sender.MediaTttSenderUiEventLogger_Factory;
import com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorComponent;
import com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorController;
import com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorController_Factory;
import com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorModule_Companion_BindIconFactoryFactory;
import com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorModule_Companion_ProvideAppSelectorComponentNameFactory;
import com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorModule_Companion_ProvideCoroutineScopeFactory;
import com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorResultHandler;
import com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorView;
import com.android.systemui.mediaprojection.appselector.data.ActivityTaskManagerThumbnailLoader;
import com.android.systemui.mediaprojection.appselector.data.ActivityTaskManagerThumbnailLoader_Factory;
import com.android.systemui.mediaprojection.appselector.data.AppIconLoader;
import com.android.systemui.mediaprojection.appselector.data.IconLoaderLibAppIconLoader;
import com.android.systemui.mediaprojection.appselector.data.IconLoaderLibAppIconLoader_Factory;
import com.android.systemui.mediaprojection.appselector.data.RecentTaskListProvider;
import com.android.systemui.mediaprojection.appselector.data.RecentTaskThumbnailLoader;
import com.android.systemui.mediaprojection.appselector.data.ShellRecentTaskListProvider;
import com.android.systemui.mediaprojection.appselector.data.ShellRecentTaskListProvider_Factory;
import com.android.systemui.mediaprojection.appselector.view.C0053RecentTaskViewHolder_Factory;
import com.android.systemui.mediaprojection.appselector.view.C0054RecentTasksAdapter_Factory;
import com.android.systemui.mediaprojection.appselector.view.MediaProjectionRecentsViewController;
import com.android.systemui.mediaprojection.appselector.view.MediaProjectionRecentsViewController_Factory;
import com.android.systemui.mediaprojection.appselector.view.RecentTaskViewHolder;
import com.android.systemui.mediaprojection.appselector.view.RecentTaskViewHolder_Factory_Impl;
import com.android.systemui.mediaprojection.appselector.view.RecentTasksAdapter;
import com.android.systemui.mediaprojection.appselector.view.RecentTasksAdapter_Factory_Impl;
import com.android.systemui.mediaprojection.appselector.view.TaskPreviewSizeProvider;
import com.android.systemui.mediaprojection.appselector.view.TaskPreviewSizeProvider_Factory;
import com.android.systemui.model.SysUiState;
import com.android.systemui.motiontool.MotionToolModule_Companion_ProvideDdmHandleMotionToolFactory;
import com.android.systemui.motiontool.MotionToolModule_Companion_ProvideMotionToolManagerFactory;
import com.android.systemui.motiontool.MotionToolModule_Companion_ProvideViewCaptureFactory;
import com.android.systemui.motiontool.MotionToolModule_Companion_ProvideWindowManagerGlobalFactory;
import com.android.systemui.motiontool.MotionToolStartable;
import com.android.systemui.motiontool.MotionToolStartable_Factory;
import com.android.systemui.navigationbar.NavBarHelper;
import com.android.systemui.navigationbar.NavBarHelper_Factory;
import com.android.systemui.navigationbar.NavigationBar;
import com.android.systemui.navigationbar.NavigationBarComponent;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationBarController_Factory;
import com.android.systemui.navigationbar.NavigationBarFrame;
import com.android.systemui.navigationbar.NavigationBarModule_ProvideEdgeBackGestureHandlerFactory;
import com.android.systemui.navigationbar.NavigationBarModule_ProvideLayoutInflaterFactory;
import com.android.systemui.navigationbar.NavigationBarModule_ProvideNavigationBarFrameFactory;
import com.android.systemui.navigationbar.NavigationBarModule_ProvideNavigationBarviewFactory;
import com.android.systemui.navigationbar.NavigationBarModule_ProvideWindowManagerFactory;
import com.android.systemui.navigationbar.NavigationBarTransitions;
import com.android.systemui.navigationbar.NavigationBarTransitions_Factory;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.NavigationBar_Factory;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.NavigationModeController_Factory;
import com.android.systemui.navigationbar.TaskbarDelegate;
import com.android.systemui.navigationbar.TaskbarDelegate_Factory;
import com.android.systemui.navigationbar.buttons.DeadZone;
import com.android.systemui.navigationbar.buttons.DeadZone_Factory;
import com.android.systemui.navigationbar.gestural.BackPanelController;
import com.android.systemui.navigationbar.gestural.BackPanelController_Factory_Factory;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler_Factory_Factory;
import com.android.systemui.navigationbar.gestural.GestureModule_ProvidsBackGestureTfClassifierProviderFactory;
import com.android.systemui.navigationbar.gestural.NavigationBarEdgePanel;
import com.android.systemui.navigationbar.gestural.NavigationBarEdgePanel_Factory;
import com.android.systemui.notetask.NoteTaskController;
import com.android.systemui.notetask.NoteTaskController_Factory;
import com.android.systemui.notetask.NoteTaskInitializer;
import com.android.systemui.notetask.NoteTaskInitializer_Factory;
import com.android.systemui.notetask.NoteTaskIntentResolver;
import com.android.systemui.notetask.NoteTaskIntentResolver_Factory;
import com.android.systemui.notetask.NoteTaskModule_Companion_ProvideIsNoteTaskEnabledFactory;
import com.android.systemui.notetask.NoteTaskModule_Companion_ProvideOptionalKeyguardManagerFactory;
import com.android.systemui.notetask.NoteTaskModule_Companion_ProvideOptionalUserManagerFactory;
import com.android.systemui.notetask.shortcut.CreateNoteTaskShortcutActivity;
import com.android.systemui.notetask.shortcut.CreateNoteTaskShortcutActivity_Factory;
import com.android.systemui.notetask.shortcut.LaunchNoteTaskActivity;
import com.android.systemui.notetask.shortcut.LaunchNoteTaskActivity_Factory;
import com.android.systemui.people.PeopleProvider;
import com.android.systemui.people.PeopleProvider_MembersInjector;
import com.android.systemui.people.PeopleSpaceActivity;
import com.android.systemui.people.PeopleSpaceActivity_Factory;
import com.android.systemui.people.data.repository.PeopleTileRepositoryImpl;
import com.android.systemui.people.data.repository.PeopleTileRepositoryImpl_Factory;
import com.android.systemui.people.data.repository.PeopleWidgetRepositoryImpl;
import com.android.systemui.people.data.repository.PeopleWidgetRepositoryImpl_Factory;
import com.android.systemui.people.ui.viewmodel.PeopleViewModel;
import com.android.systemui.people.ui.viewmodel.PeopleViewModel_Factory_Factory;
import com.android.systemui.people.widget.LaunchConversationActivity;
import com.android.systemui.people.widget.LaunchConversationActivity_Factory;
import com.android.systemui.people.widget.PeopleSpaceWidgetManager;
import com.android.systemui.people.widget.PeopleSpaceWidgetManager_Factory;
import com.android.systemui.people.widget.PeopleSpaceWidgetPinnedReceiver;
import com.android.systemui.people.widget.PeopleSpaceWidgetPinnedReceiver_Factory;
import com.android.systemui.people.widget.PeopleSpaceWidgetProvider;
import com.android.systemui.people.widget.PeopleSpaceWidgetProvider_Factory;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.PluginDependencyProvider;
import com.android.systemui.plugins.PluginDependencyProvider_Factory;
import com.android.systemui.plugins.PluginEnablerImpl;
import com.android.systemui.plugins.PluginEnablerImpl_Factory;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.plugins.PluginsModule_ProvidePluginInstanceManagerFactoryFactory;
import com.android.systemui.plugins.PluginsModule_ProvidesPluginDebugFactory;
import com.android.systemui.plugins.PluginsModule_ProvidesPluginExecutorFactory;
import com.android.systemui.plugins.PluginsModule_ProvidesPluginInstanceFactoryFactory;
import com.android.systemui.plugins.PluginsModule_ProvidesPluginManagerFactory;
import com.android.systemui.plugins.PluginsModule_ProvidesPluginPrefsFactory;
import com.android.systemui.plugins.PluginsModule_ProvidesPrivilegedPluginsFactory;
import com.android.systemui.plugins.VolumeDialog;
import com.android.systemui.plugins.log.LogBuffer;
import com.android.systemui.plugins.log.LogcatEchoTracker;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.power.EnhancedEstimatesImpl;
import com.android.systemui.power.EnhancedEstimatesImpl_Factory;
import com.android.systemui.power.PowerNotificationWarnings;
import com.android.systemui.power.PowerNotificationWarnings_Factory;
import com.android.systemui.power.PowerUI;
import com.android.systemui.power.PowerUI_Factory;
import com.android.systemui.power.data.repository.PowerRepositoryImpl;
import com.android.systemui.power.data.repository.PowerRepositoryImpl_Factory;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.power.domain.interactor.PowerInteractor_Factory;
import com.android.systemui.privacy.AppOpsPrivacyItemMonitor;
import com.android.systemui.privacy.AppOpsPrivacyItemMonitor_Factory;
import com.android.systemui.privacy.OngoingPrivacyChip;
import com.android.systemui.privacy.PrivacyConfig;
import com.android.systemui.privacy.PrivacyConfig_Factory;
import com.android.systemui.privacy.PrivacyDialogController;
import com.android.systemui.privacy.PrivacyDialogController_Factory;
import com.android.systemui.privacy.PrivacyItemController;
import com.android.systemui.privacy.PrivacyItemController_Factory;
import com.android.systemui.privacy.PrivacyItemMonitor;
import com.android.systemui.privacy.logging.PrivacyLogger;
import com.android.systemui.privacy.logging.PrivacyLogger_Factory;
import com.android.systemui.qrcodescanner.controller.QRCodeScannerController;
import com.android.systemui.qrcodescanner.controller.QRCodeScannerController_Factory;
import com.android.systemui.qs.AutoAddTracker;
import com.android.systemui.qs.AutoAddTracker_Builder_Factory;
import com.android.systemui.qs.FgsManagerControllerImpl;
import com.android.systemui.qs.FgsManagerControllerImpl_Factory;
import com.android.systemui.qs.FooterActionsController;
import com.android.systemui.qs.FooterActionsController_Factory;
import com.android.systemui.qs.HeaderPrivacyIconsController;
import com.android.systemui.qs.HeaderPrivacyIconsController_Factory;
import com.android.systemui.qs.QSAnimator;
import com.android.systemui.qs.QSAnimator_Factory;
import com.android.systemui.qs.QSContainerImpl;
import com.android.systemui.qs.QSContainerImplController;
import com.android.systemui.qs.QSContainerImplController_Factory;
import com.android.systemui.qs.QSExpansionPathInterpolator;
import com.android.systemui.qs.QSExpansionPathInterpolator_Factory;
import com.android.systemui.qs.QSFooter;
import com.android.systemui.qs.QSFooterView;
import com.android.systemui.qs.QSFooterViewController;
import com.android.systemui.qs.QSFooterViewController_Factory;
import com.android.systemui.qs.QSFragment;
import com.android.systemui.qs.QSFragmentDisableFlagsLogger;
import com.android.systemui.qs.QSPanel;
import com.android.systemui.qs.QSPanelController;
import com.android.systemui.qs.QSPanelController_Factory;
import com.android.systemui.qs.QSSecurityFooterUtils;
import com.android.systemui.qs.QSSecurityFooterUtils_Factory;
import com.android.systemui.qs.QSSquishinessController;
import com.android.systemui.qs.QSSquishinessController_Factory;
import com.android.systemui.qs.QSTileHost;
import com.android.systemui.qs.QSTileHost_Factory;
import com.android.systemui.qs.QSTileRevealController_Factory_Factory;
import com.android.systemui.qs.QuickQSPanel;
import com.android.systemui.qs.QuickQSPanelController;
import com.android.systemui.qs.QuickQSPanelController_Factory;
import com.android.systemui.qs.QuickStatusBarHeader;
import com.android.systemui.qs.QuickStatusBarHeaderController_Factory;
import com.android.systemui.qs.ReduceBrightColorsController;
import com.android.systemui.qs.ReduceBrightColorsController_Factory;
import com.android.systemui.qs.carrier.QSCarrierGroupController;
import com.android.systemui.qs.carrier.QSCarrierGroupController_Builder_Factory;
import com.android.systemui.qs.carrier.QSCarrierGroupController_SubscriptionManagerSlotIndexResolver_Factory;
import com.android.systemui.qs.customize.QSCustomizer;
import com.android.systemui.qs.customize.QSCustomizerController;
import com.android.systemui.qs.customize.QSCustomizerController_Factory;
import com.android.systemui.qs.customize.TileAdapter;
import com.android.systemui.qs.customize.TileAdapter_Factory;
import com.android.systemui.qs.customize.TileQueryHelper;
import com.android.systemui.qs.customize.TileQueryHelper_Factory;
import com.android.systemui.qs.dagger.QSFlagsModule_IsPMLiteEnabledFactory;
import com.android.systemui.qs.dagger.QSFlagsModule_IsReduceBrightColorsAvailableFactory;
import com.android.systemui.qs.dagger.QSFragmentComponent;
import com.android.systemui.qs.dagger.QSFragmentModule_ProvideQSPanelFactory;
import com.android.systemui.qs.dagger.QSFragmentModule_ProvideRootViewFactory;
import com.android.systemui.qs.dagger.QSFragmentModule_ProvideThemedContextFactory;
import com.android.systemui.qs.dagger.QSFragmentModule_ProvidesBatteryMeterViewFactory;
import com.android.systemui.qs.dagger.QSFragmentModule_ProvidesPrivacyChipFactory;
import com.android.systemui.qs.dagger.QSFragmentModule_ProvidesQSContainerImplFactory;
import com.android.systemui.qs.dagger.QSFragmentModule_ProvidesQSCutomizerFactory;
import com.android.systemui.qs.dagger.QSFragmentModule_ProvidesQSFooterFactory;
import com.android.systemui.qs.dagger.QSFragmentModule_ProvidesQSFooterViewFactory;
import com.android.systemui.qs.dagger.QSFragmentModule_ProvidesQSUsingCollapsedLandscapeMediaFactory;
import com.android.systemui.qs.dagger.QSFragmentModule_ProvidesQSUsingMediaPlayerFactory;
import com.android.systemui.qs.dagger.QSFragmentModule_ProvidesQuickQSPanelFactory;
import com.android.systemui.qs.dagger.QSFragmentModule_ProvidesQuickStatusBarHeaderFactory;
import com.android.systemui.qs.dagger.QSFragmentModule_ProvidesStatusIconContainerFactory;
import com.android.systemui.qs.dagger.QSModule_ProvideAutoTileManagerFactory;
import com.android.systemui.qs.external.C0055TileLifecycleManager_Factory;
import com.android.systemui.qs.external.CustomTile;
import com.android.systemui.qs.external.CustomTileStatePersister;
import com.android.systemui.qs.external.CustomTileStatePersister_Factory;
import com.android.systemui.qs.external.CustomTile_Builder_Factory;
import com.android.systemui.qs.external.PackageManagerAdapter;
import com.android.systemui.qs.external.PackageManagerAdapter_Factory;
import com.android.systemui.qs.external.TileLifecycleManager;
import com.android.systemui.qs.external.TileLifecycleManager_Factory_Impl;
import com.android.systemui.qs.external.TileServiceRequestController;
import com.android.systemui.qs.external.TileServiceRequestController_Builder_Factory;
import com.android.systemui.qs.external.TileServices;
import com.android.systemui.qs.external.TileServices_Factory;
import com.android.systemui.qs.footer.data.repository.ForegroundServicesRepositoryImpl;
import com.android.systemui.qs.footer.data.repository.ForegroundServicesRepositoryImpl_Factory;
import com.android.systemui.qs.footer.data.repository.UserSwitcherRepositoryImpl;
import com.android.systemui.qs.footer.data.repository.UserSwitcherRepositoryImpl_Factory;
import com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractorImpl;
import com.android.systemui.qs.footer.domain.interactor.FooterActionsInteractorImpl_Factory;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModel;
import com.android.systemui.qs.footer.ui.viewmodel.FooterActionsViewModel_Factory_Factory;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.logging.QSLogger_Factory;
import com.android.systemui.qs.tileimpl.QSFactoryImpl;
import com.android.systemui.qs.tileimpl.QSFactoryImpl_Factory;
import com.android.systemui.qs.tiles.AODTile;
import com.android.systemui.qs.tiles.AODTile_Factory;
import com.android.systemui.qs.tiles.AirplaneModeTile;
import com.android.systemui.qs.tiles.AirplaneModeTile_Factory;
import com.android.systemui.qs.tiles.AlarmTile;
import com.android.systemui.qs.tiles.AlarmTile_Factory;
import com.android.systemui.qs.tiles.AmbientDisplayTile;
import com.android.systemui.qs.tiles.AmbientDisplayTile_Factory;
import com.android.systemui.qs.tiles.AntiFlickerTile;
import com.android.systemui.qs.tiles.AntiFlickerTile_Factory;
import com.android.systemui.qs.tiles.BatterySaverTile;
import com.android.systemui.qs.tiles.BatterySaverTile_Factory;
import com.android.systemui.qs.tiles.BluetoothTile;
import com.android.systemui.qs.tiles.BluetoothTile_Factory;
import com.android.systemui.qs.tiles.CaffeineTile;
import com.android.systemui.qs.tiles.CaffeineTile_Factory;
import com.android.systemui.qs.tiles.CameraToggleTile;
import com.android.systemui.qs.tiles.CameraToggleTile_Factory;
import com.android.systemui.qs.tiles.CastTile;
import com.android.systemui.qs.tiles.CastTile_Factory;
import com.android.systemui.qs.tiles.CellularTile;
import com.android.systemui.qs.tiles.CellularTile_Factory;
import com.android.systemui.qs.tiles.ColorCorrectionTile;
import com.android.systemui.qs.tiles.ColorCorrectionTile_Factory;
import com.android.systemui.qs.tiles.ColorInversionTile;
import com.android.systemui.qs.tiles.ColorInversionTile_Factory;
import com.android.systemui.qs.tiles.DataSaverTile;
import com.android.systemui.qs.tiles.DataSaverTile_Factory;
import com.android.systemui.qs.tiles.DeviceControlsTile;
import com.android.systemui.qs.tiles.DeviceControlsTile_Factory;
import com.android.systemui.qs.tiles.DndTile;
import com.android.systemui.qs.tiles.DndTile_Factory;
import com.android.systemui.qs.tiles.DreamTile;
import com.android.systemui.qs.tiles.DreamTile_Factory;
import com.android.systemui.qs.tiles.FlashlightTile;
import com.android.systemui.qs.tiles.FlashlightTile_Factory;
import com.android.systemui.qs.tiles.HeadsUpTile;
import com.android.systemui.qs.tiles.HeadsUpTile_Factory;
import com.android.systemui.qs.tiles.HotspotTile;
import com.android.systemui.qs.tiles.HotspotTile_Factory;
import com.android.systemui.qs.tiles.InternetTile;
import com.android.systemui.qs.tiles.InternetTile_Factory;
import com.android.systemui.qs.tiles.LiveDisplayTile;
import com.android.systemui.qs.tiles.LiveDisplayTile_Factory;
import com.android.systemui.qs.tiles.LocationTile;
import com.android.systemui.qs.tiles.LocationTile_Factory;
import com.android.systemui.qs.tiles.MicrophoneToggleTile;
import com.android.systemui.qs.tiles.MicrophoneToggleTile_Factory;
import com.android.systemui.qs.tiles.NfcTile;
import com.android.systemui.qs.tiles.NfcTile_Factory;
import com.android.systemui.qs.tiles.NightDisplayTile;
import com.android.systemui.qs.tiles.NightDisplayTile_Factory;
import com.android.systemui.qs.tiles.OneHandedModeTile;
import com.android.systemui.qs.tiles.OneHandedModeTile_Factory;
import com.android.systemui.qs.tiles.PowerShareTile;
import com.android.systemui.qs.tiles.PowerShareTile_Factory;
import com.android.systemui.qs.tiles.QRCodeScannerTile;
import com.android.systemui.qs.tiles.QRCodeScannerTile_Factory;
import com.android.systemui.qs.tiles.QuickAccessWalletTile;
import com.android.systemui.qs.tiles.QuickAccessWalletTile_Factory;
import com.android.systemui.qs.tiles.ReadingModeTile;
import com.android.systemui.qs.tiles.ReadingModeTile_Factory;
import com.android.systemui.qs.tiles.ReduceBrightColorsTile;
import com.android.systemui.qs.tiles.ReduceBrightColorsTile_Factory;
import com.android.systemui.qs.tiles.RotationLockTile;
import com.android.systemui.qs.tiles.RotationLockTile_Factory;
import com.android.systemui.qs.tiles.ScreenRecordTile;
import com.android.systemui.qs.tiles.ScreenRecordTile_Factory;
import com.android.systemui.qs.tiles.SyncTile;
import com.android.systemui.qs.tiles.SyncTile_Factory;
import com.android.systemui.qs.tiles.UiModeNightTile;
import com.android.systemui.qs.tiles.UiModeNightTile_Factory;
import com.android.systemui.qs.tiles.UsbTetherTile;
import com.android.systemui.qs.tiles.UsbTetherTile_Factory;
import com.android.systemui.qs.tiles.UserDetailView;
import com.android.systemui.qs.tiles.UserDetailView_Adapter_Factory;
import com.android.systemui.qs.tiles.VpnTile;
import com.android.systemui.qs.tiles.VpnTile_Factory;
import com.android.systemui.qs.tiles.WifiTile;
import com.android.systemui.qs.tiles.WifiTile_Factory;
import com.android.systemui.qs.tiles.WorkModeTile;
import com.android.systemui.qs.tiles.WorkModeTile_Factory;
import com.android.systemui.qs.tiles.dialog.InternetDialogController;
import com.android.systemui.qs.tiles.dialog.InternetDialogController_Factory;
import com.android.systemui.qs.tiles.dialog.InternetDialogFactory;
import com.android.systemui.qs.tiles.dialog.InternetDialogFactory_Factory;
import com.android.systemui.qs.tiles.dialog.WifiStateWorker;
import com.android.systemui.qs.tiles.dialog.WifiStateWorker_Factory;
import com.android.systemui.qs.user.UserSwitchDialogController;
import com.android.systemui.qs.user.UserSwitchDialogController_Factory;
import com.android.systemui.reardisplay.RearDisplayDialogController;
import com.android.systemui.reardisplay.RearDisplayDialogController_Factory;
import com.android.systemui.recents.OverviewProxyRecentsImpl;
import com.android.systemui.recents.OverviewProxyRecentsImpl_Factory;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.recents.OverviewProxyService_Factory;
import com.android.systemui.recents.Recents;
import com.android.systemui.recents.RecentsImplementation;
import com.android.systemui.recents.RecentsModule_ProvideRecentsImplFactory;
import com.android.systemui.recents.ScreenPinningRequest;
import com.android.systemui.recents.ScreenPinningRequest_Factory;
import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.screenrecord.RecordingController_Factory;
import com.android.systemui.screenrecord.RecordingService;
import com.android.systemui.screenrecord.RecordingService_Factory;
import com.android.systemui.screenshot.ActionIntentExecutor;
import com.android.systemui.screenshot.ActionIntentExecutor_Factory;
import com.android.systemui.screenshot.ActionProxyReceiver;
import com.android.systemui.screenshot.ActionProxyReceiver_Factory;
import com.android.systemui.screenshot.DeleteScreenshotReceiver;
import com.android.systemui.screenshot.DeleteScreenshotReceiver_Factory;
import com.android.systemui.screenshot.ImageCaptureImpl;
import com.android.systemui.screenshot.ImageCaptureImpl_Factory;
import com.android.systemui.screenshot.ImageExporter_Factory;
import com.android.systemui.screenshot.ImageTileSet_Factory;
import com.android.systemui.screenshot.LongScreenshotActivity;
import com.android.systemui.screenshot.LongScreenshotActivity_Factory;
import com.android.systemui.screenshot.LongScreenshotData;
import com.android.systemui.screenshot.LongScreenshotData_Factory;
import com.android.systemui.screenshot.ReferenceScreenshotModule_ProvidesScrnshtNotifSmartActionsProviderFactory;
import com.android.systemui.screenshot.RequestProcessor;
import com.android.systemui.screenshot.RequestProcessor_Factory;
import com.android.systemui.screenshot.ScreenshotController;
import com.android.systemui.screenshot.ScreenshotController_Factory;
import com.android.systemui.screenshot.ScreenshotNotificationsController;
import com.android.systemui.screenshot.ScreenshotNotificationsController_Factory;
import com.android.systemui.screenshot.ScreenshotPolicyImpl;
import com.android.systemui.screenshot.ScreenshotPolicyImpl_Factory;
import com.android.systemui.screenshot.ScreenshotProxyService;
import com.android.systemui.screenshot.ScreenshotProxyService_Factory;
import com.android.systemui.screenshot.ScreenshotSmartActions;
import com.android.systemui.screenshot.ScreenshotSmartActions_Factory;
import com.android.systemui.screenshot.ScrollCaptureClient;
import com.android.systemui.screenshot.ScrollCaptureClient_Factory;
import com.android.systemui.screenshot.ScrollCaptureController;
import com.android.systemui.screenshot.ScrollCaptureController_Factory;
import com.android.systemui.screenshot.SmartActionsReceiver;
import com.android.systemui.screenshot.SmartActionsReceiver_Factory;
import com.android.systemui.screenshot.TakeScreenshotService;
import com.android.systemui.screenshot.TakeScreenshotService_Factory;
import com.android.systemui.screenshot.TimeoutHandler;
import com.android.systemui.screenshot.TimeoutHandler_Factory;
import com.android.systemui.security.data.repository.SecurityRepositoryImpl;
import com.android.systemui.security.data.repository.SecurityRepositoryImpl_Factory;
import com.android.systemui.sensorprivacy.SensorUseStartedActivity;
import com.android.systemui.sensorprivacy.SensorUseStartedActivity_Factory;
import com.android.systemui.sensorprivacy.television.TvUnblockSensorActivity;
import com.android.systemui.sensorprivacy.television.TvUnblockSensorActivity_Factory;
import com.android.systemui.settings.UserFileManagerImpl;
import com.android.systemui.settings.UserFileManagerImpl_Factory;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.brightness.BrightnessController;
import com.android.systemui.settings.brightness.BrightnessController_Factory_Factory;
import com.android.systemui.settings.brightness.BrightnessDialog;
import com.android.systemui.settings.brightness.BrightnessDialog_Factory;
import com.android.systemui.settings.brightness.BrightnessSliderController;
import com.android.systemui.settings.brightness.BrightnessSliderController_Factory_Factory;
import com.android.systemui.settings.dagger.MultiUserUtilsModule_ProvideUserTrackerFactory;
import com.android.systemui.shade.CameraLauncher;
import com.android.systemui.shade.CameraLauncher_Factory;
import com.android.systemui.shade.CombinedShadeHeadersConstraintManager;
import com.android.systemui.shade.LargeScreenShadeHeaderController;
import com.android.systemui.shade.LargeScreenShadeHeaderController_Factory;
import com.android.systemui.shade.NotificationPanelUnfoldAnimationController;
import com.android.systemui.shade.NotificationPanelUnfoldAnimationController_Factory;
import com.android.systemui.shade.NotificationPanelView;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.NotificationPanelViewController_Factory;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl_Factory;
import com.android.systemui.shade.NotificationShadeWindowView;
import com.android.systemui.shade.NotificationShadeWindowViewController;
import com.android.systemui.shade.NotificationShadeWindowViewController_Factory;
import com.android.systemui.shade.NotificationsQSContainerController;
import com.android.systemui.shade.NotificationsQSContainerController_Factory;
import com.android.systemui.shade.NotificationsQuickSettingsContainer;
import com.android.systemui.shade.PulsingGestureListener;
import com.android.systemui.shade.PulsingGestureListener_Factory;
import com.android.systemui.shade.ShadeControllerImpl;
import com.android.systemui.shade.ShadeControllerImpl_Factory;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.shade.ShadeExpansionStateManager_Factory;
import com.android.systemui.shade.ShadeHeightLogger;
import com.android.systemui.shade.ShadeHeightLogger_Factory;
import com.android.systemui.shade.ShadeLogger;
import com.android.systemui.shade.ShadeLogger_Factory;
import com.android.systemui.shade.ShadeWindowLogger;
import com.android.systemui.shade.ShadeWindowLogger_Factory;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl;
import com.android.systemui.shade.data.repository.ShadeRepositoryImpl_Factory;
import com.android.systemui.shade.transition.NoOpOverScroller_Factory;
import com.android.systemui.shade.transition.ScrimShadeTransitionController;
import com.android.systemui.shade.transition.ScrimShadeTransitionController_Factory;
import com.android.systemui.shade.transition.ShadeTransitionController;
import com.android.systemui.shade.transition.ShadeTransitionController_Factory;
import com.android.systemui.shade.transition.SplitShadeOverScroller;
import com.android.systemui.shade.transition.SplitShadeOverScroller_Factory;
import com.android.systemui.shade.transition.SplitShadeOverScroller_Factory_Impl;
import com.android.systemui.shared.clocks.ClockRegistry;
import com.android.systemui.shared.plugins.PluginActionManager;
import com.android.systemui.shared.plugins.PluginInstance;
import com.android.systemui.shared.plugins.PluginPrefs;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.DevicePolicyManagerWrapper;
import com.android.systemui.shared.system.InputChannelCompat;
import com.android.systemui.shared.system.PackageManagerWrapper;
import com.android.systemui.shared.system.UncaughtExceptionPreHandlerManager;
import com.android.systemui.shared.system.UncaughtExceptionPreHandlerManager_Factory;
import com.android.systemui.shortcut.ShortcutKeyDispatcher;
import com.android.systemui.shortcut.ShortcutKeyDispatcher_Factory;
import com.android.systemui.statusbar.ActionClickLogger;
import com.android.systemui.statusbar.ActionClickLogger_Factory;
import com.android.systemui.statusbar.BlurUtils;
import com.android.systemui.statusbar.BlurUtils_Factory;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.DisableFlagsLogger;
import com.android.systemui.statusbar.DisableFlagsLogger_Factory;
import com.android.systemui.statusbar.HeadsUpStatusBarView;
import com.android.systemui.statusbar.KeyguardIndicationController;
import com.android.systemui.statusbar.KeyguardIndicationController_Factory;
import com.android.systemui.statusbar.LockscreenShadeKeyguardTransitionController;
import com.android.systemui.statusbar.LockscreenShadeKeyguardTransitionController_Factory;
import com.android.systemui.statusbar.LockscreenShadeKeyguardTransitionController_Factory_Impl;
import com.android.systemui.statusbar.LockscreenShadeQsTransitionController;
import com.android.systemui.statusbar.LockscreenShadeQsTransitionController_Factory;
import com.android.systemui.statusbar.LockscreenShadeQsTransitionController_Factory_Impl;
import com.android.systemui.statusbar.LockscreenShadeScrimTransitionController;
import com.android.systemui.statusbar.LockscreenShadeScrimTransitionController_Factory;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.LockscreenShadeTransitionController_Factory;
import com.android.systemui.statusbar.MediaArtworkProcessor;
import com.android.systemui.statusbar.MediaArtworkProcessor_Factory;
import com.android.systemui.statusbar.NotificationClickNotifier;
import com.android.systemui.statusbar.NotificationClickNotifier_Factory;
import com.android.systemui.statusbar.NotificationInsetsImpl;
import com.android.systemui.statusbar.NotificationInsetsImpl_Factory;
import com.android.systemui.statusbar.NotificationInteractionTracker;
import com.android.systemui.statusbar.NotificationInteractionTracker_Factory;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.NotificationListener_Factory;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl_Factory;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import com.android.systemui.statusbar.NotificationShadeDepthController_Factory;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.NotificationShelfController;
import com.android.systemui.statusbar.NotificationShelfController_Factory;
import com.android.systemui.statusbar.OperatorNameViewController;
import com.android.systemui.statusbar.PulseExpansionHandler;
import com.android.systemui.statusbar.PulseExpansionHandler_Factory;
import com.android.systemui.statusbar.QsFrameTranslateImpl;
import com.android.systemui.statusbar.QsFrameTranslateImpl_Factory;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.RemoteInputNotificationRebuilder;
import com.android.systemui.statusbar.RemoteInputNotificationRebuilder_Factory;
import com.android.systemui.statusbar.SingleShadeLockScreenOverScroller;
import com.android.systemui.statusbar.SingleShadeLockScreenOverScroller_Factory;
import com.android.systemui.statusbar.SingleShadeLockScreenOverScroller_Factory_Impl;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.SplitShadeLockScreenOverScroller;
import com.android.systemui.statusbar.SplitShadeLockScreenOverScroller_Factory;
import com.android.systemui.statusbar.SplitShadeLockScreenOverScroller_Factory_Impl;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.StatusBarStateControllerImpl_Factory;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.VibratorHelper_Factory;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.statusbar.commandline.CommandRegistry_Factory;
import com.android.systemui.statusbar.connectivity.AccessPointControllerImpl;
import com.android.systemui.statusbar.connectivity.AccessPointControllerImpl_WifiPickerTrackerFactory_Factory;
import com.android.systemui.statusbar.connectivity.CallbackHandler;
import com.android.systemui.statusbar.connectivity.CallbackHandler_Factory;
import com.android.systemui.statusbar.connectivity.MobileSignalControllerFactory;
import com.android.systemui.statusbar.connectivity.MobileSignalControllerFactory_Factory;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.connectivity.NetworkControllerImpl;
import com.android.systemui.statusbar.connectivity.NetworkControllerImpl_Factory;
import com.android.systemui.statusbar.connectivity.WifiStatusTrackerFactory;
import com.android.systemui.statusbar.connectivity.WifiStatusTrackerFactory_Factory;
import com.android.systemui.statusbar.connectivity.ui.MobileContextProvider;
import com.android.systemui.statusbar.connectivity.ui.MobileContextProvider_Factory;
import com.android.systemui.statusbar.core.StatusBarInitializer;
import com.android.systemui.statusbar.core.StatusBarInitializer_Factory;
import com.android.systemui.statusbar.dagger.CentralSurfacesDependenciesModule_ProvideActivityLaunchAnimatorFactory;
import com.android.systemui.statusbar.dagger.CentralSurfacesDependenciesModule_ProvideCommandQueueFactory;
import com.android.systemui.statusbar.dagger.CentralSurfacesDependenciesModule_ProvideDialogLaunchAnimatorFactory;
import com.android.systemui.statusbar.dagger.CentralSurfacesDependenciesModule_ProvideNotificationMediaManagerFactory;
import com.android.systemui.statusbar.dagger.CentralSurfacesDependenciesModule_ProvideNotificationRemoteInputManagerFactory;
import com.android.systemui.statusbar.dagger.CentralSurfacesDependenciesModule_ProvideOngoingCallControllerFactory;
import com.android.systemui.statusbar.dagger.CentralSurfacesDependenciesModule_ProvideSmartReplyControllerFactory;
import com.android.systemui.statusbar.dagger.CentralSurfacesDependenciesModule_ProvideStatusBarIconListFactory;
import com.android.systemui.statusbar.events.PrivacyDotViewController;
import com.android.systemui.statusbar.events.PrivacyDotViewController_Factory;
import com.android.systemui.statusbar.events.SystemEventChipAnimationController;
import com.android.systemui.statusbar.events.SystemEventChipAnimationController_Factory;
import com.android.systemui.statusbar.events.SystemEventCoordinator;
import com.android.systemui.statusbar.events.SystemEventCoordinator_Factory;
import com.android.systemui.statusbar.events.SystemStatusAnimationScheduler;
import com.android.systemui.statusbar.events.SystemStatusAnimationScheduler_Factory;
import com.android.systemui.statusbar.gesture.SwipeStatusBarAwayGestureHandler;
import com.android.systemui.statusbar.gesture.SwipeStatusBarAwayGestureHandler_Factory;
import com.android.systemui.statusbar.gesture.SwipeStatusBarAwayGestureLogger;
import com.android.systemui.statusbar.gesture.SwipeStatusBarAwayGestureLogger_Factory;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController_Factory;
import com.android.systemui.statusbar.notification.AnimatedImageNotificationManager;
import com.android.systemui.statusbar.notification.AnimatedImageNotificationManager_Factory;
import com.android.systemui.statusbar.notification.AssistantFeedbackController;
import com.android.systemui.statusbar.notification.AssistantFeedbackController_Factory;
import com.android.systemui.statusbar.notification.ConversationNotificationManager;
import com.android.systemui.statusbar.notification.ConversationNotificationManager_Factory;
import com.android.systemui.statusbar.notification.ConversationNotificationProcessor;
import com.android.systemui.statusbar.notification.ConversationNotificationProcessor_Factory;
import com.android.systemui.statusbar.notification.DynamicPrivacyController;
import com.android.systemui.statusbar.notification.DynamicPrivacyController_Factory;
import com.android.systemui.statusbar.notification.InstantAppNotifier;
import com.android.systemui.statusbar.notification.InstantAppNotifier_Factory;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.NotifPipelineFlags_Factory;
import com.android.systemui.statusbar.notification.NotificationActivityStarter;
import com.android.systemui.statusbar.notification.NotificationClicker;
import com.android.systemui.statusbar.notification.NotificationClickerLogger;
import com.android.systemui.statusbar.notification.NotificationClickerLogger_Factory;
import com.android.systemui.statusbar.notification.NotificationClicker_Builder_Factory;
import com.android.systemui.statusbar.notification.NotificationLaunchAnimatorControllerProvider;
import com.android.systemui.statusbar.notification.NotificationLaunchAnimatorControllerProvider_Factory;
import com.android.systemui.statusbar.notification.NotificationSectionsFeatureManager;
import com.android.systemui.statusbar.notification.NotificationSectionsFeatureManager_Factory;
import com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator;
import com.android.systemui.statusbar.notification.NotificationWakeUpCoordinatorLogger;
import com.android.systemui.statusbar.notification.NotificationWakeUpCoordinatorLogger_Factory;
import com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotifCollection_Factory;
import com.android.systemui.statusbar.notification.collection.NotifInflaterImpl;
import com.android.systemui.statusbar.notification.collection.NotifInflaterImpl_Factory;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStoreImpl;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStoreImpl_Factory;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotifPipelineChoreographerImpl_Factory;
import com.android.systemui.statusbar.notification.collection.NotifPipeline_Factory;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.ShadeListBuilder;
import com.android.systemui.statusbar.notification.collection.ShadeListBuilder_Factory;
import com.android.systemui.statusbar.notification.collection.TargetSdkResolver;
import com.android.systemui.statusbar.notification.collection.TargetSdkResolver_Factory;
import com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescer;
import com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescerLogger;
import com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescerLogger_Factory;
import com.android.systemui.statusbar.notification.collection.coalescer.GroupCoalescer_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.AppOpsCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.AppOpsCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.BubbleCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.BubbleCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.ConversationCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.DataStoreCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.DataStoreCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.DebugModeCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.DebugModeCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.DeviceProvisionedCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.DeviceProvisionedCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.GroupCountCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.GroupCountCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.GutsCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.GutsCoordinatorLogger;
import com.android.systemui.statusbar.notification.collection.coordinator.GutsCoordinatorLogger_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.GutsCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger;
import com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinatorLogger_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.HideLocallyDismissedNotifsCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.HideLocallyDismissedNotifsCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.HideNotifsForOtherUsersCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.HideNotifsForOtherUsersCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.KeyguardCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.MediaCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.MediaCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.NotifCoordinators;
import com.android.systemui.statusbar.notification.collection.coordinator.NotifCoordinatorsImpl;
import com.android.systemui.statusbar.notification.collection.coordinator.NotifCoordinatorsImpl_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinatorLogger;
import com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinatorLogger_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.RankingCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.RowAppearanceCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.RowAppearanceCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.SensitiveContentCoordinatorImpl_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.ShadeEventCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.ShadeEventCoordinatorLogger;
import com.android.systemui.statusbar.notification.collection.coordinator.ShadeEventCoordinatorLogger_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.ShadeEventCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.SmartspaceDedupingCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.SmartspaceDedupingCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.StackCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.StackCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.ViewConfigCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.ViewConfigCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.VisualStabilityCoordinator;
import com.android.systemui.statusbar.notification.collection.coordinator.VisualStabilityCoordinator_Factory;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorsModule_NotifCoordinatorsFactory;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorsSubcomponent;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManagerImpl;
import com.android.systemui.statusbar.notification.collection.inflation.BindEventManagerImpl_Factory;
import com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustmentProvider;
import com.android.systemui.statusbar.notification.collection.inflation.NotifUiAdjustmentProvider_Factory;
import com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderImpl;
import com.android.systemui.statusbar.notification.collection.inflation.NotificationRowBinderImpl_Factory;
import com.android.systemui.statusbar.notification.collection.inflation.OnUserInteractionCallbackImpl;
import com.android.systemui.statusbar.notification.collection.inflation.OnUserInteractionCallbackImpl_Factory;
import com.android.systemui.statusbar.notification.collection.init.NotifPipelineInitializer;
import com.android.systemui.statusbar.notification.collection.init.NotifPipelineInitializer_Factory;
import com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger;
import com.android.systemui.statusbar.notification.collection.listbuilder.ShadeListBuilderLogger_Factory;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionLogger_Factory;
import com.android.systemui.statusbar.notification.collection.provider.DebugModeFilterProvider;
import com.android.systemui.statusbar.notification.collection.provider.DebugModeFilterProvider_Factory;
import com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider;
import com.android.systemui.statusbar.notification.collection.provider.HighPriorityProvider_Factory;
import com.android.systemui.statusbar.notification.collection.provider.LaunchFullScreenIntentProvider;
import com.android.systemui.statusbar.notification.collection.provider.LaunchFullScreenIntentProvider_Factory;
import com.android.systemui.statusbar.notification.collection.provider.NotificationVisibilityProviderImpl;
import com.android.systemui.statusbar.notification.collection.provider.NotificationVisibilityProviderImpl_Factory;
import com.android.systemui.statusbar.notification.collection.provider.SectionHeaderVisibilityProvider;
import com.android.systemui.statusbar.notification.collection.provider.SectionHeaderVisibilityProvider_Factory;
import com.android.systemui.statusbar.notification.collection.provider.SectionStyleProvider;
import com.android.systemui.statusbar.notification.collection.provider.SectionStyleProvider_Factory;
import com.android.systemui.statusbar.notification.collection.provider.SeenNotificationsProviderImpl;
import com.android.systemui.statusbar.notification.collection.provider.SeenNotificationsProviderImpl_Factory;
import com.android.systemui.statusbar.notification.collection.provider.VisibilityLocationProviderDelegator;
import com.android.systemui.statusbar.notification.collection.provider.VisibilityLocationProviderDelegator_Factory;
import com.android.systemui.statusbar.notification.collection.provider.VisualStabilityProvider;
import com.android.systemui.statusbar.notification.collection.provider.VisualStabilityProvider_Factory;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl_Factory;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.collection.render.MediaContainerController;
import com.android.systemui.statusbar.notification.collection.render.MediaContainerController_Factory;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.collection.render.NodeSpecBuilderLogger;
import com.android.systemui.statusbar.notification.collection.render.NodeSpecBuilderLogger_Factory;
import com.android.systemui.statusbar.notification.collection.render.NotifViewBarn;
import com.android.systemui.statusbar.notification.collection.render.NotifViewBarn_Factory;
import com.android.systemui.statusbar.notification.collection.render.RenderStageManager;
import com.android.systemui.statusbar.notification.collection.render.RenderStageManager_Factory;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderNodeControllerImpl;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderNodeControllerImpl_Factory;
import com.android.systemui.statusbar.notification.collection.render.ShadeViewDifferLogger;
import com.android.systemui.statusbar.notification.collection.render.ShadeViewDifferLogger_Factory;
import com.android.systemui.statusbar.notification.collection.render.ShadeViewManagerFactory;
import com.android.systemui.statusbar.notification.collection.render.ShadeViewManagerFactory_Impl;
import com.android.systemui.statusbar.notification.collection.render.ShadeViewManager_Factory;
import com.android.systemui.statusbar.notification.dagger.NotificationSectionHeadersModule_ProvidesAlertingHeaderControllerFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationSectionHeadersModule_ProvidesAlertingHeaderNodeControllerFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationSectionHeadersModule_ProvidesAlertingHeaderSubcomponentFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationSectionHeadersModule_ProvidesIncomingHeaderControllerFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationSectionHeadersModule_ProvidesIncomingHeaderNodeControllerFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationSectionHeadersModule_ProvidesIncomingHeaderSubcomponentFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationSectionHeadersModule_ProvidesPeopleHeaderControllerFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationSectionHeadersModule_ProvidesPeopleHeaderNodeControllerFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationSectionHeadersModule_ProvidesPeopleHeaderSubcomponentFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationSectionHeadersModule_ProvidesSilentHeaderControllerFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationSectionHeadersModule_ProvidesSilentHeaderNodeControllerFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationSectionHeadersModule_ProvidesSilentHeaderSubcomponentFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationsModule_ProvideGroupMembershipManagerFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationsModule_ProvideNotificationGutsManagerFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationsModule_ProvideNotificationLoggerFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationsModule_ProvideNotificationPanelLoggerFactory;
import com.android.systemui.statusbar.notification.dagger.NotificationsModule_ProvideNotificationsControllerFactory;
import com.android.systemui.statusbar.notification.dagger.SectionHeaderControllerSubcomponent;
import com.android.systemui.statusbar.notification.fsi.FsiChromeRepo;
import com.android.systemui.statusbar.notification.fsi.FsiChromeRepo_Factory;
import com.android.systemui.statusbar.notification.fsi.FsiChromeViewBinder;
import com.android.systemui.statusbar.notification.fsi.FsiChromeViewBinder_Factory;
import com.android.systemui.statusbar.notification.fsi.FsiChromeViewModelFactory;
import com.android.systemui.statusbar.notification.fsi.FsiChromeViewModelFactory_Factory;
import com.android.systemui.statusbar.notification.icon.IconBuilder;
import com.android.systemui.statusbar.notification.icon.IconBuilder_Factory;
import com.android.systemui.statusbar.notification.icon.IconManager;
import com.android.systemui.statusbar.notification.icon.IconManager_Factory;
import com.android.systemui.statusbar.notification.init.NotificationsController;
import com.android.systemui.statusbar.notification.init.NotificationsControllerImpl;
import com.android.systemui.statusbar.notification.init.NotificationsControllerImpl_Factory;
import com.android.systemui.statusbar.notification.init.NotificationsControllerStub;
import com.android.systemui.statusbar.notification.init.NotificationsControllerStub_Factory;
import com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinder;
import com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinderLogger;
import com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinderLogger_Factory;
import com.android.systemui.statusbar.notification.interruption.HeadsUpViewBinder_Factory;
import com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProviderImpl_Factory;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptLogger_Factory;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProviderImpl;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProviderImpl_Factory;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;
import com.android.systemui.statusbar.notification.logging.NotificationLogger_ExpansionStateLogger_Factory;
import com.android.systemui.statusbar.notification.logging.NotificationMemoryDumper;
import com.android.systemui.statusbar.notification.logging.NotificationMemoryDumper_Factory;
import com.android.systemui.statusbar.notification.logging.NotificationMemoryLogger;
import com.android.systemui.statusbar.notification.logging.NotificationMemoryLogger_Factory;
import com.android.systemui.statusbar.notification.logging.NotificationMemoryMonitor;
import com.android.systemui.statusbar.notification.logging.NotificationMemoryMonitor_Factory;
import com.android.systemui.statusbar.notification.logging.NotificationPanelLogger;
import com.android.systemui.statusbar.notification.logging.NotificationRoundnessLogger;
import com.android.systemui.statusbar.notification.logging.NotificationRoundnessLogger_Factory;
import com.android.systemui.statusbar.notification.people.NotificationPersonExtractorPluginBoundary;
import com.android.systemui.statusbar.notification.people.NotificationPersonExtractorPluginBoundary_Factory;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifierImpl;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifierImpl_Factory;
import com.android.systemui.statusbar.notification.row.ActivatableNotificationViewController;
import com.android.systemui.statusbar.notification.row.ActivatableNotificationViewController_Factory;
import com.android.systemui.statusbar.notification.row.ChannelEditorDialogController;
import com.android.systemui.statusbar.notification.row.ChannelEditorDialogController_Factory;
import com.android.systemui.statusbar.notification.row.ChannelEditorDialog_Builder_Factory;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController_Factory;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowDragController;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowDragController_Factory;
import com.android.systemui.statusbar.notification.row.ExpandableOutlineViewController;
import com.android.systemui.statusbar.notification.row.ExpandableOutlineViewController_Factory;
import com.android.systemui.statusbar.notification.row.ExpandableViewController;
import com.android.systemui.statusbar.notification.row.ExpandableViewController_Factory;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline;
import com.android.systemui.statusbar.notification.row.NotifBindPipelineInitializer;
import com.android.systemui.statusbar.notification.row.NotifBindPipelineInitializer_Factory;
import com.android.systemui.statusbar.notification.row.NotifBindPipelineLogger;
import com.android.systemui.statusbar.notification.row.NotifBindPipelineLogger_Factory;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline_Factory;
import com.android.systemui.statusbar.notification.row.NotifInflationErrorManager;
import com.android.systemui.statusbar.notification.row.NotifInflationErrorManager_Factory;
import com.android.systemui.statusbar.notification.row.NotifRemoteViewCache;
import com.android.systemui.statusbar.notification.row.NotifRemoteViewCacheImpl;
import com.android.systemui.statusbar.notification.row.NotifRemoteViewCacheImpl_Factory;
import com.android.systemui.statusbar.notification.row.NotificationContentInflater;
import com.android.systemui.statusbar.notification.row.NotificationContentInflater_Factory;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.notification.row.NotificationRowLogger;
import com.android.systemui.statusbar.notification.row.NotificationRowLogger_Factory;
import com.android.systemui.statusbar.notification.row.RowContentBindStage;
import com.android.systemui.statusbar.notification.row.RowContentBindStageLogger;
import com.android.systemui.statusbar.notification.row.RowContentBindStageLogger_Factory;
import com.android.systemui.statusbar.notification.row.RowContentBindStage_Factory;
import com.android.systemui.statusbar.notification.row.RowInflaterTask_Factory;
import com.android.systemui.statusbar.notification.row.dagger.ExpandableNotificationRowComponent;
import com.android.systemui.statusbar.notification.row.dagger.ExpandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideAppNameFactory;
import com.android.systemui.statusbar.notification.row.dagger.ExpandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideNotificationKeyFactory;
import com.android.systemui.statusbar.notification.row.dagger.ExpandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideStatusBarNotificationFactory;
import com.android.systemui.statusbar.notification.row.dagger.NotificationShelfComponent;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.AmbientState_Factory;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.NotificationRoundnessManager;
import com.android.systemui.statusbar.notification.stack.NotificationRoundnessManager_Factory;
import com.android.systemui.statusbar.notification.stack.NotificationSectionsManager;
import com.android.systemui.statusbar.notification.stack.NotificationSectionsManager_Factory;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController_Factory;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutListContainerModule_ProvideListContainerFactory;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLogger_Factory;
import com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator;
import com.android.systemui.statusbar.notification.stack.NotificationStackSizeCalculator_Factory;
import com.android.systemui.statusbar.notification.stack.NotificationSwipeHelper_Builder_Factory;
import com.android.systemui.statusbar.notification.stack.NotificationTargetsHelper;
import com.android.systemui.statusbar.notification.stack.NotificationTargetsHelper_Factory;
import com.android.systemui.statusbar.notification.stack.StackStateLogger;
import com.android.systemui.statusbar.notification.stack.StackStateLogger_Factory;
import com.android.systemui.statusbar.phone.AutoHideController;
import com.android.systemui.statusbar.phone.AutoHideController_Factory;
import com.android.systemui.statusbar.phone.AutoHideController_Factory_Factory;
import com.android.systemui.statusbar.phone.AutoTileManager;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.BiometricUnlockController_Factory;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.CentralSurfacesCommandQueueCallbacks;
import com.android.systemui.statusbar.phone.CentralSurfacesCommandQueueCallbacks_Factory;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl;
import com.android.systemui.statusbar.phone.CentralSurfacesImpl_Factory;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl_Factory;
import com.android.systemui.statusbar.phone.DarkIconDispatcherImpl;
import com.android.systemui.statusbar.phone.DarkIconDispatcherImpl_Factory;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.DozeParameters_Factory;
import com.android.systemui.statusbar.phone.DozeScrimController;
import com.android.systemui.statusbar.phone.DozeScrimController_Factory;
import com.android.systemui.statusbar.phone.DozeServiceHost;
import com.android.systemui.statusbar.phone.DozeServiceHost_Factory;
import com.android.systemui.statusbar.phone.HeadsUpAppearanceController;
import com.android.systemui.statusbar.phone.HeadsUpAppearanceController_Factory;
import com.android.systemui.statusbar.phone.HeadsUpManagerPhone;
import com.android.systemui.statusbar.phone.KeyguardBottomAreaView;
import com.android.systemui.statusbar.phone.KeyguardBottomAreaViewController;
import com.android.systemui.statusbar.phone.KeyguardBottomAreaViewController_Factory;
import com.android.systemui.statusbar.phone.KeyguardBouncer;
import com.android.systemui.statusbar.phone.KeyguardBouncer_Factory_Factory;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.KeyguardBypassController_Factory;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil_Factory;
import com.android.systemui.statusbar.phone.KeyguardLiftController;
import com.android.systemui.statusbar.phone.KeyguardLiftController_Factory;
import com.android.systemui.statusbar.phone.KeyguardStatusBarView;
import com.android.systemui.statusbar.phone.KeyguardStatusBarViewController;
import com.android.systemui.statusbar.phone.LSShadeTransitionLogger;
import com.android.systemui.statusbar.phone.LSShadeTransitionLogger_Factory;
import com.android.systemui.statusbar.phone.LetterboxAppearanceCalculator;
import com.android.systemui.statusbar.phone.LetterboxAppearanceCalculator_Factory;
import com.android.systemui.statusbar.phone.LetterboxBackgroundProvider;
import com.android.systemui.statusbar.phone.LetterboxBackgroundProvider_Factory;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.phone.LightBarController_Factory;
import com.android.systemui.statusbar.phone.LightBarController_Factory_Factory;
import com.android.systemui.statusbar.phone.LightBarTransitionsController;
import com.android.systemui.statusbar.phone.LightBarTransitionsController_Factory;
import com.android.systemui.statusbar.phone.LightBarTransitionsController_Factory_Impl;
import com.android.systemui.statusbar.phone.LightsOutNotifController;
import com.android.systemui.statusbar.phone.LightsOutNotifController_Factory;
import com.android.systemui.statusbar.phone.LockscreenGestureLogger;
import com.android.systemui.statusbar.phone.LockscreenGestureLogger_Factory;
import com.android.systemui.statusbar.phone.LockscreenWallpaper;
import com.android.systemui.statusbar.phone.LockscreenWallpaper_Factory;
import com.android.systemui.statusbar.phone.ManagedProfileControllerImpl;
import com.android.systemui.statusbar.phone.ManagedProfileControllerImpl_Factory;
import com.android.systemui.statusbar.phone.NotificationIconAreaController;
import com.android.systemui.statusbar.phone.NotificationIconAreaController_Factory;
import com.android.systemui.statusbar.phone.NotificationListenerWithPlugins;
import com.android.systemui.statusbar.phone.NotificationListenerWithPlugins_Factory;
import com.android.systemui.statusbar.phone.NotificationTapHelper;
import com.android.systemui.statusbar.phone.NotificationTapHelper_Factory_Factory;
import com.android.systemui.statusbar.phone.PhoneStatusBarPolicy;
import com.android.systemui.statusbar.phone.PhoneStatusBarPolicy_Factory;
import com.android.systemui.statusbar.phone.PhoneStatusBarTransitions;
import com.android.systemui.statusbar.phone.PhoneStatusBarView;
import com.android.systemui.statusbar.phone.PhoneStatusBarViewController;
import com.android.systemui.statusbar.phone.PhoneStatusBarViewController_Factory_Factory;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController_Factory;
import com.android.systemui.statusbar.phone.ScrimController;
import com.android.systemui.statusbar.phone.ScrimController_Factory;
import com.android.systemui.statusbar.phone.StatusBarBoundsProvider;
import com.android.systemui.statusbar.phone.StatusBarBoundsProvider_Factory;
import com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider;
import com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider_Factory;
import com.android.systemui.statusbar.phone.StatusBarDemoMode;
import com.android.systemui.statusbar.phone.StatusBarDemoMode_Factory;
import com.android.systemui.statusbar.phone.StatusBarHeadsUpChangeListener;
import com.android.systemui.statusbar.phone.StatusBarHeadsUpChangeListener_Factory;
import com.android.systemui.statusbar.phone.StatusBarHideIconsForBouncerManager;
import com.android.systemui.statusbar.phone.StatusBarHideIconsForBouncerManager_Factory;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.phone.StatusBarIconControllerImpl;
import com.android.systemui.statusbar.phone.StatusBarIconControllerImpl_Factory;
import com.android.systemui.statusbar.phone.StatusBarIconController_DarkIconManager_Factory_Factory;
import com.android.systemui.statusbar.phone.StatusBarIconController_TintedIconManager_Factory_Factory;
import com.android.systemui.statusbar.phone.StatusBarIconList;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager_Factory;
import com.android.systemui.statusbar.phone.StatusBarLocationPublisher;
import com.android.systemui.statusbar.phone.StatusBarLocationPublisher_Factory;
import com.android.systemui.statusbar.phone.StatusBarMoveFromCenterAnimationController;
import com.android.systemui.statusbar.phone.StatusBarMoveFromCenterAnimationController_Factory;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger_Factory;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter_Factory;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter_Factory;
import com.android.systemui.statusbar.phone.StatusBarRemoteInputCallback;
import com.android.systemui.statusbar.phone.StatusBarRemoteInputCallback_Factory;
import com.android.systemui.statusbar.phone.StatusBarSignalPolicy;
import com.android.systemui.statusbar.phone.StatusBarSignalPolicy_Factory;
import com.android.systemui.statusbar.phone.StatusBarTouchableRegionManager;
import com.android.systemui.statusbar.phone.StatusBarTouchableRegionManager_Factory;
import com.android.systemui.statusbar.phone.StatusIconContainer;
import com.android.systemui.statusbar.phone.SystemBarAttributesListener;
import com.android.systemui.statusbar.phone.SystemBarAttributesListener_Factory;
import com.android.systemui.statusbar.phone.SystemUIDialogManager;
import com.android.systemui.statusbar.phone.SystemUIDialogManager_Factory;
import com.android.systemui.statusbar.phone.TapAgainView;
import com.android.systemui.statusbar.phone.TapAgainViewController;
import com.android.systemui.statusbar.phone.TapAgainViewController_Factory;
import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController;
import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController_Factory;
import com.android.systemui.statusbar.phone.dagger.CentralSurfacesComponent;
import com.android.systemui.statusbar.phone.dagger.StatusBarViewModule_CreateCollapsedStatusBarFragmentFactory;
import com.android.systemui.statusbar.phone.dagger.StatusBarViewModule_GetAuthRippleViewFactory;
import com.android.systemui.statusbar.phone.dagger.StatusBarViewModule_GetBatteryMeterViewControllerFactory;
import com.android.systemui.statusbar.phone.dagger.StatusBarViewModule_GetBatteryMeterViewFactory;
import com.android.systemui.statusbar.phone.dagger.StatusBarViewModule_GetLargeScreenShadeHeaderBarViewFactory;
import com.android.systemui.statusbar.phone.dagger.StatusBarViewModule_GetLockIconViewFactory;
import com.android.systemui.statusbar.phone.dagger.StatusBarViewModule_GetNotificationPanelViewFactory;
import com.android.systemui.statusbar.phone.dagger.StatusBarViewModule_GetNotificationsQuickSettingsContainerFactory;
import com.android.systemui.statusbar.phone.dagger.StatusBarViewModule_GetSplitShadeOngoingPrivacyChipFactory;
import com.android.systemui.statusbar.phone.dagger.StatusBarViewModule_GetTapAgainViewFactory;
import com.android.systemui.statusbar.phone.dagger.StatusBarViewModule_ProvideCombinedShadeHeadersConstraintManagerFactory;
import com.android.systemui.statusbar.phone.dagger.StatusBarViewModule_ProvidesKeyguardBottomAreaViewFactory;
import com.android.systemui.statusbar.phone.dagger.StatusBarViewModule_ProvidesNotificationShadeWindowViewFactory;
import com.android.systemui.statusbar.phone.dagger.StatusBarViewModule_ProvidesNotificationShelfFactory;
import com.android.systemui.statusbar.phone.dagger.StatusBarViewModule_ProvidesNotificationStackScrollLayoutFactory;
import com.android.systemui.statusbar.phone.dagger.StatusBarViewModule_ProvidesStatusBarWindowViewFactory;
import com.android.systemui.statusbar.phone.dagger.StatusBarViewModule_ProvidesStatusIconContainerFactory;
import com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment;
import com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragmentLogger;
import com.android.systemui.statusbar.phone.fragment.dagger.StatusBarFragmentComponent;
import com.android.systemui.statusbar.phone.fragment.dagger.StatusBarFragmentModule_EndSideContentFactory;
import com.android.systemui.statusbar.phone.fragment.dagger.StatusBarFragmentModule_ProvideBatteryMeterViewFactory;
import com.android.systemui.statusbar.phone.fragment.dagger.StatusBarFragmentModule_ProvideClockFactory;
import com.android.systemui.statusbar.phone.fragment.dagger.StatusBarFragmentModule_ProvideLightsOutNotifViewFactory;
import com.android.systemui.statusbar.phone.fragment.dagger.StatusBarFragmentModule_ProvideOperatorFrameNameViewFactory;
import com.android.systemui.statusbar.phone.fragment.dagger.StatusBarFragmentModule_ProvideOperatorNameViewFactory;
import com.android.systemui.statusbar.phone.fragment.dagger.StatusBarFragmentModule_ProvidePhoneStatusBarTransitionsFactory;
import com.android.systemui.statusbar.phone.fragment.dagger.StatusBarFragmentModule_ProvidePhoneStatusBarViewControllerFactory;
import com.android.systemui.statusbar.phone.fragment.dagger.StatusBarFragmentModule_ProvidePhoneStatusBarViewFactory;
import com.android.systemui.statusbar.phone.fragment.dagger.StatusBarFragmentModule_ProvidesHeasdUpStatusBarViewFactory;
import com.android.systemui.statusbar.phone.fragment.dagger.StatusBarFragmentModule_StartSideContentFactory;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallFlags;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallFlags_Factory;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallLogger;
import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallLogger_Factory;
import com.android.systemui.statusbar.pipeline.StatusBarPipelineFlags;
import com.android.systemui.statusbar.pipeline.StatusBarPipelineFlags_Factory;
import com.android.systemui.statusbar.pipeline.airplane.data.repository.AirplaneModeRepositoryImpl;
import com.android.systemui.statusbar.pipeline.airplane.data.repository.AirplaneModeRepositoryImpl_Factory;
import com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor;
import com.android.systemui.statusbar.pipeline.airplane.domain.interactor.AirplaneModeInteractor_Factory;
import com.android.systemui.statusbar.pipeline.airplane.ui.viewmodel.AirplaneModeViewModelImpl;
import com.android.systemui.statusbar.pipeline.airplane.ui.viewmodel.AirplaneModeViewModelImpl_Factory;
import com.android.systemui.statusbar.pipeline.dagger.StatusBarPipelineModule_Companion_ProvideAirplaneTableLogBufferFactory;
import com.android.systemui.statusbar.pipeline.dagger.StatusBarPipelineModule_Companion_ProvideRealWifiRepositoryFactory;
import com.android.systemui.statusbar.pipeline.dagger.StatusBarPipelineModule_Companion_ProvideWifiTableLogBufferFactory;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileRepositorySwitcher;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileRepositorySwitcher_Factory;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.UserSetupRepositoryImpl;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.UserSetupRepositoryImpl_Factory;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionsRepository;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoMobileConnectionsRepository_Factory;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoModeMobileConnectionDataSource;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.demo.DemoModeMobileConnectionDataSource_Factory;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionRepositoryImpl_Factory_Factory;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.prod.MobileConnectionsRepositoryImpl_Factory;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl;
import com.android.systemui.statusbar.pipeline.mobile.domain.interactor.MobileIconsInteractorImpl_Factory;
import com.android.systemui.statusbar.pipeline.mobile.ui.MobileUiAdapter;
import com.android.systemui.statusbar.pipeline.mobile.ui.MobileUiAdapter_Factory;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel;
import com.android.systemui.statusbar.pipeline.mobile.ui.viewmodel.MobileIconsViewModel_Factory_Factory;
import com.android.systemui.statusbar.pipeline.mobile.util.MobileMappingsProxyImpl_Factory;
import com.android.systemui.statusbar.pipeline.shared.ConnectivityConstants;
import com.android.systemui.statusbar.pipeline.shared.ConnectivityConstants_Factory;
import com.android.systemui.statusbar.pipeline.shared.ConnectivityPipelineLogger;
import com.android.systemui.statusbar.pipeline.shared.ConnectivityPipelineLogger_Factory;
import com.android.systemui.statusbar.pipeline.shared.data.model.ConnectivitySlots;
import com.android.systemui.statusbar.pipeline.shared.data.model.ConnectivitySlots_Factory;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepositoryImpl_Factory;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.RealWifiRepository;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepositorySwitcher;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.WifiRepositorySwitcher_Factory;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.DemoModeWifiDataSource;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.DemoModeWifiDataSource_Factory;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.DemoWifiRepository;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.DemoWifiRepository_Factory;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.DisabledWifiRepository;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.DisabledWifiRepository_Factory;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl;
import com.android.systemui.statusbar.pipeline.wifi.data.repository.prod.WifiRepositoryImpl_Factory_Factory;
import com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl;
import com.android.systemui.statusbar.pipeline.wifi.domain.interactor.WifiInteractorImpl_Factory;
import com.android.systemui.statusbar.pipeline.wifi.shared.WifiConstants;
import com.android.systemui.statusbar.pipeline.wifi.shared.WifiConstants_Factory;
import com.android.systemui.statusbar.pipeline.wifi.ui.WifiUiAdapter;
import com.android.systemui.statusbar.pipeline.wifi.ui.WifiUiAdapter_Factory;
import com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel;
import com.android.systemui.statusbar.pipeline.wifi.ui.viewmodel.WifiViewModel_Factory;
import com.android.systemui.statusbar.policy.AccessibilityController;
import com.android.systemui.statusbar.policy.AccessibilityController_Factory;
import com.android.systemui.statusbar.policy.AccessibilityManagerWrapper;
import com.android.systemui.statusbar.policy.AccessibilityManagerWrapper_Factory;
import com.android.systemui.statusbar.policy.AospPolicyModule_ProvideBatteryControllerFactory;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.BatteryStateNotifier;
import com.android.systemui.statusbar.policy.BatteryStateNotifier_Factory;
import com.android.systemui.statusbar.policy.BluetoothControllerImpl;
import com.android.systemui.statusbar.policy.BluetoothControllerImpl_Factory;
import com.android.systemui.statusbar.policy.CastControllerImpl;
import com.android.systemui.statusbar.policy.CastControllerImpl_Factory;
import com.android.systemui.statusbar.policy.Clock;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DataSaverController;
import com.android.systemui.statusbar.policy.DeviceControlsControllerImpl;
import com.android.systemui.statusbar.policy.DeviceControlsControllerImpl_Factory;
import com.android.systemui.statusbar.policy.DevicePostureControllerImpl;
import com.android.systemui.statusbar.policy.DevicePostureControllerImpl_Factory;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl_Factory;
import com.android.systemui.statusbar.policy.DeviceStateRotationLockSettingController;
import com.android.systemui.statusbar.policy.DeviceStateRotationLockSettingController_Factory;
import com.android.systemui.statusbar.policy.ExtensionControllerImpl;
import com.android.systemui.statusbar.policy.ExtensionControllerImpl_Factory;
import com.android.systemui.statusbar.policy.FlashlightControllerImpl;
import com.android.systemui.statusbar.policy.FlashlightControllerImpl_Factory;
import com.android.systemui.statusbar.policy.HeadsUpManagerLogger;
import com.android.systemui.statusbar.policy.HeadsUpManagerLogger_Factory;
import com.android.systemui.statusbar.policy.HotspotControllerImpl;
import com.android.systemui.statusbar.policy.HotspotControllerImpl_Factory;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;
import com.android.systemui.statusbar.policy.KeyguardQsUserSwitchController;
import com.android.systemui.statusbar.policy.KeyguardQsUserSwitchController_Factory;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl_Factory;
import com.android.systemui.statusbar.policy.KeyguardUserSwitcherController;
import com.android.systemui.statusbar.policy.KeyguardUserSwitcherController_Factory;
import com.android.systemui.statusbar.policy.KeyguardUserSwitcherView;
import com.android.systemui.statusbar.policy.LocationControllerImpl;
import com.android.systemui.statusbar.policy.LocationControllerImpl_Factory;
import com.android.systemui.statusbar.policy.NextAlarmController;
import com.android.systemui.statusbar.policy.NextAlarmControllerImpl;
import com.android.systemui.statusbar.policy.NextAlarmControllerImpl_Factory;
import com.android.systemui.statusbar.policy.RemoteInputQuickSettingsDisabler;
import com.android.systemui.statusbar.policy.RemoteInputQuickSettingsDisabler_Factory;
import com.android.systemui.statusbar.policy.RemoteInputUriController;
import com.android.systemui.statusbar.policy.RemoteInputUriController_Factory;
import com.android.systemui.statusbar.policy.RemoteInputView;
import com.android.systemui.statusbar.policy.RemoteInputViewController;
import com.android.systemui.statusbar.policy.RemoteInputViewControllerImpl;
import com.android.systemui.statusbar.policy.RotationLockControllerImpl;
import com.android.systemui.statusbar.policy.RotationLockControllerImpl_Factory;
import com.android.systemui.statusbar.policy.SafetyController;
import com.android.systemui.statusbar.policy.SafetyController_Factory;
import com.android.systemui.statusbar.policy.SecurityControllerImpl;
import com.android.systemui.statusbar.policy.SecurityControllerImpl_Factory;
import com.android.systemui.statusbar.policy.SensorPrivacyController;
import com.android.systemui.statusbar.policy.SmartActionInflaterImpl;
import com.android.systemui.statusbar.policy.SmartActionInflaterImpl_Factory;
import com.android.systemui.statusbar.policy.SmartReplyConstants;
import com.android.systemui.statusbar.policy.SmartReplyConstants_Factory;
import com.android.systemui.statusbar.policy.SmartReplyInflaterImpl;
import com.android.systemui.statusbar.policy.SmartReplyInflaterImpl_Factory;
import com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl;
import com.android.systemui.statusbar.policy.SmartReplyStateInflaterImpl_Factory;
import com.android.systemui.statusbar.policy.UserInfoController;
import com.android.systemui.statusbar.policy.UserInfoControllerImpl;
import com.android.systemui.statusbar.policy.UserInfoControllerImpl_Factory;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.statusbar.policy.UserSwitcherController_Factory;
import com.android.systemui.statusbar.policy.VariableDateViewController;
import com.android.systemui.statusbar.policy.VariableDateViewController_Factory_Factory;
import com.android.systemui.statusbar.policy.WalletControllerImpl;
import com.android.systemui.statusbar.policy.WalletControllerImpl_Factory;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl_Factory;
import com.android.systemui.statusbar.policy.dagger.RemoteInputViewSubcomponent;
import com.android.systemui.statusbar.policy.dagger.StatusBarPolicyModule_ProvideAccessPointControllerImplFactory;
import com.android.systemui.statusbar.policy.dagger.StatusBarPolicyModule_ProvideAutoRotateSettingsManagerFactory;
import com.android.systemui.statusbar.policy.dagger.StatusBarPolicyModule_ProvideDataSaverControllerFactory;
import com.android.systemui.statusbar.policy.dagger.StatusBarPolicyModule_ProvidesDeviceStateRotationLockDefaultsFactory;
import com.android.systemui.statusbar.tv.notifications.TvNotificationHandler;
import com.android.systemui.statusbar.tv.notifications.TvNotificationHandler_Factory;
import com.android.systemui.statusbar.tv.notifications.TvNotificationPanelActivity;
import com.android.systemui.statusbar.tv.notifications.TvNotificationPanelActivity_Factory;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.statusbar.window.StatusBarWindowController_Factory;
import com.android.systemui.statusbar.window.StatusBarWindowModule_ProvidesStatusBarWindowViewFactory;
import com.android.systemui.statusbar.window.StatusBarWindowStateController;
import com.android.systemui.statusbar.window.StatusBarWindowStateController_Factory;
import com.android.systemui.statusbar.window.StatusBarWindowView;
import com.android.systemui.stylus.StylusManager;
import com.android.systemui.stylus.StylusManager_Factory;
import com.android.systemui.stylus.StylusUsiPowerStartable;
import com.android.systemui.stylus.StylusUsiPowerStartable_Factory;
import com.android.systemui.stylus.StylusUsiPowerUI;
import com.android.systemui.stylus.StylusUsiPowerUI_Factory;
import com.android.systemui.telephony.TelephonyCallback_Factory;
import com.android.systemui.telephony.TelephonyListenerManager;
import com.android.systemui.telephony.TelephonyListenerManager_Factory;
import com.android.systemui.telephony.data.repository.TelephonyRepositoryImpl;
import com.android.systemui.telephony.data.repository.TelephonyRepositoryImpl_Factory;
import com.android.systemui.telephony.domain.interactor.TelephonyInteractor;
import com.android.systemui.telephony.domain.interactor.TelephonyInteractor_Factory;
import com.android.systemui.temporarydisplay.chipbar.ChipbarCoordinator;
import com.android.systemui.temporarydisplay.chipbar.ChipbarCoordinator_Factory;
import com.android.systemui.temporarydisplay.chipbar.ChipbarInfo;
import com.android.systemui.temporarydisplay.chipbar.ChipbarLogger;
import com.android.systemui.temporarydisplay.chipbar.ChipbarLogger_Factory;
import com.android.systemui.temporarydisplay.dagger.TemporaryDisplayModule_ProvideChipbarLogBufferFactory;
import com.android.systemui.theme.ThemeModule_ProvideLauncherPackageFactory;
import com.android.systemui.theme.ThemeModule_ProvideThemePickerPackageFactory;
import com.android.systemui.theme.ThemeOverlayApplier;
import com.android.systemui.theme.ThemeOverlayApplier_Factory;
import com.android.systemui.theme.ThemeOverlayController;
import com.android.systemui.theme.ThemeOverlayController_Factory;
import com.android.systemui.toast.ToastFactory;
import com.android.systemui.toast.ToastFactory_Factory;
import com.android.systemui.toast.ToastLogger;
import com.android.systemui.toast.ToastLogger_Factory;
import com.android.systemui.toast.ToastUI;
import com.android.systemui.toast.ToastUI_Factory;
import com.android.systemui.touch.TouchInsetManager;
import com.android.systemui.tracing.ProtoTracer;
import com.android.systemui.tracing.ProtoTracer_Factory;
import com.android.systemui.tuner.TunablePadding;
import com.android.systemui.tuner.TunablePadding_TunablePaddingService_Factory;
import com.android.systemui.tuner.TunerActivity;
import com.android.systemui.tuner.TunerActivity_Factory;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.tuner.TunerServiceImpl;
import com.android.systemui.tuner.TunerServiceImpl_Factory;
import com.android.systemui.unfold.FoldAodAnimationController;
import com.android.systemui.unfold.FoldAodAnimationController_Factory;
import com.android.systemui.unfold.FoldStateLogger;
import com.android.systemui.unfold.FoldStateLoggingProvider;
import com.android.systemui.unfold.SysUIUnfoldComponent;
import com.android.systemui.unfold.SysUIUnfoldModule;
import com.android.systemui.unfold.SysUIUnfoldModule_ProvideSysUIUnfoldComponentFactory;
import com.android.systemui.unfold.UnfoldHapticsPlayer;
import com.android.systemui.unfold.UnfoldHapticsPlayer_Factory;
import com.android.systemui.unfold.UnfoldLatencyTracker;
import com.android.systemui.unfold.UnfoldLatencyTracker_Factory;
import com.android.systemui.unfold.UnfoldLightRevealOverlayAnimation;
import com.android.systemui.unfold.UnfoldLightRevealOverlayAnimation_Factory;
import com.android.systemui.unfold.UnfoldSharedModule;
import com.android.systemui.unfold.UnfoldSharedModule_HingeAngleProviderFactory;
import com.android.systemui.unfold.UnfoldSharedModule_ProvideFoldStateProviderFactory;
import com.android.systemui.unfold.UnfoldSharedModule_UnfoldKeyguardVisibilityManagerFactory;
import com.android.systemui.unfold.UnfoldSharedModule_UnfoldKeyguardVisibilityProviderFactory;
import com.android.systemui.unfold.UnfoldSharedModule_UnfoldTransitionProgressProviderFactory;
import com.android.systemui.unfold.UnfoldTransitionModule;
import com.android.systemui.unfold.UnfoldTransitionModule_ProvideNaturalRotationProgressProviderFactory;
import com.android.systemui.unfold.UnfoldTransitionModule_ProvideShellProgressProviderFactory;
import com.android.systemui.unfold.UnfoldTransitionModule_ProvideStatusBarScopedTransitionProviderFactory;
import com.android.systemui.unfold.UnfoldTransitionModule_ProvidesFoldStateLoggerFactory;
import com.android.systemui.unfold.UnfoldTransitionModule_ProvidesFoldStateLoggingProviderFactory;
import com.android.systemui.unfold.UnfoldTransitionModule_ScreenStatusProviderFactory;
import com.android.systemui.unfold.UnfoldTransitionModule_TracingTagPrefixFactory;
import com.android.systemui.unfold.UnfoldTransitionProgressProvider;
import com.android.systemui.unfold.UnfoldTransitionWallpaperController;
import com.android.systemui.unfold.UnfoldTransitionWallpaperController_Factory;
import com.android.systemui.unfold.config.ResourceUnfoldTransitionConfig;
import com.android.systemui.unfold.config.ResourceUnfoldTransitionConfig_Factory;
import com.android.systemui.unfold.system.ActivityManagerActivityTypeProvider;
import com.android.systemui.unfold.system.ActivityManagerActivityTypeProvider_Factory;
import com.android.systemui.unfold.system.DeviceStateManagerFoldProvider;
import com.android.systemui.unfold.system.DeviceStateManagerFoldProvider_Factory;
import com.android.systemui.unfold.updates.DeviceFoldStateProvider;
import com.android.systemui.unfold.updates.DeviceFoldStateProvider_Factory;
import com.android.systemui.unfold.updates.FoldStateProvider;
import com.android.systemui.unfold.updates.RotationChangeProvider;
import com.android.systemui.unfold.updates.RotationChangeProvider_Factory;
import com.android.systemui.unfold.updates.hinge.HingeAngleProvider;
import com.android.systemui.unfold.updates.screen.ScreenStatusProvider;
import com.android.systemui.unfold.util.ATraceLoggerTransitionProgressListener;
import com.android.systemui.unfold.util.ATraceLoggerTransitionProgressListener_Factory;
import com.android.systemui.unfold.util.NaturalRotationUnfoldProgressProvider;
import com.android.systemui.unfold.util.ScaleAwareTransitionProgressProvider;
import com.android.systemui.unfold.util.ScaleAwareTransitionProgressProvider_Factory;
import com.android.systemui.unfold.util.ScaleAwareTransitionProgressProvider_Factory_Impl;
import com.android.systemui.unfold.util.ScopedUnfoldTransitionProgressProvider;
import com.android.systemui.unfold.util.UnfoldKeyguardVisibilityManager;
import com.android.systemui.unfold.util.UnfoldKeyguardVisibilityManagerImpl;
import com.android.systemui.unfold.util.UnfoldKeyguardVisibilityManagerImpl_Factory;
import com.android.systemui.unfold.util.UnfoldKeyguardVisibilityProvider;
import com.android.systemui.usb.StorageNotification;
import com.android.systemui.usb.StorageNotification_Factory;
import com.android.systemui.usb.UsbAudioWarningDialogMessage_Factory;
import com.android.systemui.usb.UsbConfirmActivity;
import com.android.systemui.usb.UsbConfirmActivity_Factory;
import com.android.systemui.usb.UsbDebuggingActivity;
import com.android.systemui.usb.UsbDebuggingActivity_Factory;
import com.android.systemui.usb.UsbDebuggingSecondaryUserActivity;
import com.android.systemui.usb.UsbDebuggingSecondaryUserActivity_Factory;
import com.android.systemui.usb.UsbPermissionActivity;
import com.android.systemui.usb.UsbPermissionActivity_Factory;
import com.android.systemui.user.CreateUserActivity;
import com.android.systemui.user.CreateUserActivity_Factory;
import com.android.systemui.user.UserCreator;
import com.android.systemui.user.UserCreator_Factory;
import com.android.systemui.user.UserModule_ProvideEditUserInfoControllerFactory;
import com.android.systemui.user.UserModule_ProvideUserHandleFactory;
import com.android.systemui.user.UserSwitcherActivity;
import com.android.systemui.user.UserSwitcherActivity_Factory;
import com.android.systemui.user.data.repository.UserRepositoryImpl;
import com.android.systemui.user.data.repository.UserRepositoryImpl_Factory;
import com.android.systemui.user.domain.interactor.GuestUserInteractor;
import com.android.systemui.user.domain.interactor.GuestUserInteractor_Factory;
import com.android.systemui.user.domain.interactor.RefreshUsersScheduler;
import com.android.systemui.user.domain.interactor.RefreshUsersScheduler_Factory;
import com.android.systemui.user.domain.interactor.UserInteractor;
import com.android.systemui.user.domain.interactor.UserInteractor_Factory;
import com.android.systemui.user.ui.dialog.UserSwitcherDialogCoordinator;
import com.android.systemui.user.ui.dialog.UserSwitcherDialogCoordinator_Factory;
import com.android.systemui.user.ui.viewmodel.StatusBarUserChipViewModel;
import com.android.systemui.user.ui.viewmodel.StatusBarUserChipViewModel_Factory;
import com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel;
import com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel_Factory_Factory;
import com.android.systemui.util.AsyncActivityLauncher;
import com.android.systemui.util.AsyncActivityLauncher_Factory;
import com.android.systemui.util.CarrierConfigTracker;
import com.android.systemui.util.CarrierConfigTracker_Factory;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.DeviceConfigProxy_Factory;
import com.android.systemui.util.InitializationChecker;
import com.android.systemui.util.NotificationChannels;
import com.android.systemui.util.NotificationChannels_Factory;
import com.android.systemui.util.RingerModeTrackerImpl;
import com.android.systemui.util.RingerModeTrackerImpl_Factory;
import com.android.systemui.util.WallpaperController;
import com.android.systemui.util.WallpaperController_Factory;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.Execution;
import com.android.systemui.util.concurrency.ExecutionImpl_Factory;
import com.android.systemui.util.concurrency.GlobalConcurrencyModule_ProvideHandlerFactory;
import com.android.systemui.util.concurrency.GlobalConcurrencyModule_ProvideMainDelayableExecutorFactory;
import com.android.systemui.util.concurrency.GlobalConcurrencyModule_ProvideMainExecutorFactory;
import com.android.systemui.util.concurrency.GlobalConcurrencyModule_ProvideMainHandlerFactory;
import com.android.systemui.util.concurrency.GlobalConcurrencyModule_ProvideMainLooperFactory;
import com.android.systemui.util.concurrency.GlobalConcurrencyModule_ProvideUiBackgroundExecutorFactory;
import com.android.systemui.util.concurrency.MessageRouter;
import com.android.systemui.util.concurrency.RepeatableExecutor;
import com.android.systemui.util.concurrency.SysUIConcurrencyModule_ProvideBackgroundDelayableExecutorFactory;
import com.android.systemui.util.concurrency.SysUIConcurrencyModule_ProvideBackgroundExecutorFactory;
import com.android.systemui.util.concurrency.SysUIConcurrencyModule_ProvideBackgroundRepeatableExecutorFactory;
import com.android.systemui.util.concurrency.SysUIConcurrencyModule_ProvideBgHandlerFactory;
import com.android.systemui.util.concurrency.SysUIConcurrencyModule_ProvideBgLooperFactory;
import com.android.systemui.util.concurrency.SysUIConcurrencyModule_ProvideBroadcastRunningExecutorFactory;
import com.android.systemui.util.concurrency.SysUIConcurrencyModule_ProvideBroadcastRunningLooperFactory;
import com.android.systemui.util.concurrency.SysUIConcurrencyModule_ProvideDelayableExecutorFactory;
import com.android.systemui.util.concurrency.SysUIConcurrencyModule_ProvideExecutorFactory;
import com.android.systemui.util.concurrency.SysUIConcurrencyModule_ProvideLongRunningExecutorFactory;
import com.android.systemui.util.concurrency.SysUIConcurrencyModule_ProvideLongRunningLooperFactory;
import com.android.systemui.util.concurrency.SysUIConcurrencyModule_ProvideTimeTickHandlerFactory;
import com.android.systemui.util.concurrency.SysUIConcurrencyModule_ProvidesBackgroundMessageRouterFactory;
import com.android.systemui.util.concurrency.SysUIConcurrencyModule_ProvidesMainMessageRouterFactory;
import com.android.systemui.util.concurrency.ThreadFactoryImpl_Factory;
import com.android.systemui.util.io.Files;
import com.android.systemui.util.io.Files_Factory;
import com.android.systemui.util.kotlin.CoroutinesModule_ApplicationScopeFactory;
import com.android.systemui.util.kotlin.CoroutinesModule_BgDispatcherFactory;
import com.android.systemui.util.kotlin.CoroutinesModule_MainDispatcherFactory;
import com.android.systemui.util.leak.GarbageMonitor;
import com.android.systemui.util.leak.GarbageMonitor_Factory;
import com.android.systemui.util.leak.GarbageMonitor_MemoryTile_Factory;
import com.android.systemui.util.leak.GarbageMonitor_Service_Factory;
import com.android.systemui.util.leak.LeakDetector;
import com.android.systemui.util.leak.LeakModule;
import com.android.systemui.util.leak.LeakModule_ProvidesLeakDetectorFactory;
import com.android.systemui.util.leak.LeakReporter;
import com.android.systemui.util.leak.LeakReporter_Factory;
import com.android.systemui.util.leak.TrackedCollections_Factory;
import com.android.systemui.util.sensors.AsyncSensorManager;
import com.android.systemui.util.sensors.AsyncSensorManager_Factory;
import com.android.systemui.util.sensors.PostureDependentProximitySensor_Factory;
import com.android.systemui.util.sensors.ProximityCheck;
import com.android.systemui.util.sensors.ProximitySensor;
import com.android.systemui.util.sensors.ProximitySensorImpl_Factory;
import com.android.systemui.util.sensors.SensorModule_ProvidePostureToProximitySensorMappingFactory;
import com.android.systemui.util.sensors.SensorModule_ProvidePostureToSecondaryProximitySensorMappingFactory;
import com.android.systemui.util.sensors.SensorModule_ProvidePrimaryProximitySensorFactory;
import com.android.systemui.util.sensors.SensorModule_ProvideProximityCheckFactory;
import com.android.systemui.util.sensors.SensorModule_ProvideProximitySensorFactory;
import com.android.systemui.util.sensors.SensorModule_ProvideSecondaryProximitySensorFactory;
import com.android.systemui.util.sensors.ThresholdSensor;
import com.android.systemui.util.sensors.ThresholdSensorImpl;
import com.android.systemui.util.sensors.ThresholdSensorImpl_BuilderFactory_Factory;
import com.android.systemui.util.sensors.ThresholdSensorImpl_Builder_Factory;
import com.android.systemui.util.settings.GlobalSettingsImpl_Factory;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.settings.SecureSettingsImpl_Factory;
import com.android.systemui.util.settings.SystemSettingsImpl_Factory;
import com.android.systemui.util.time.DateFormatUtil;
import com.android.systemui.util.time.DateFormatUtil_Factory;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.time.SystemClockImpl_Factory;
import com.android.systemui.util.view.ViewUtil;
import com.android.systemui.util.view.ViewUtil_Factory;
import com.android.systemui.util.wakelock.DelayedWakeLock;
import com.android.systemui.util.wakelock.DelayedWakeLock_Builder_Factory;
import com.android.systemui.util.wakelock.WakeLock;
import com.android.systemui.util.wakelock.WakeLock_Builder_Factory;
import com.android.systemui.util.wrapper.RotationPolicyWrapper;
import com.android.systemui.util.wrapper.RotationPolicyWrapperImpl;
import com.android.systemui.util.wrapper.RotationPolicyWrapperImpl_Factory;
import com.android.systemui.volume.VolumeDialogComponent;
import com.android.systemui.volume.VolumeDialogComponent_Factory;
import com.android.systemui.volume.VolumeDialogControllerImpl;
import com.android.systemui.volume.VolumeDialogControllerImpl_Factory;
import com.android.systemui.volume.VolumePanelDialogReceiver;
import com.android.systemui.volume.VolumePanelDialogReceiver_Factory;
import com.android.systemui.volume.VolumePanelFactory;
import com.android.systemui.volume.VolumePanelFactory_Factory;
import com.android.systemui.volume.VolumeUI;
import com.android.systemui.volume.VolumeUI_Factory;
import com.android.systemui.volume.dagger.VolumeModule_ProvideVolumeDialogFactory;
import com.android.systemui.wallet.controller.QuickAccessWalletController;
import com.android.systemui.wallet.controller.QuickAccessWalletController_Factory;
import com.android.systemui.wallet.dagger.WalletModule_ProvideQuickAccessWalletClientFactory;
import com.android.systemui.wallet.ui.WalletActivity;
import com.android.systemui.wallet.ui.WalletActivity_Factory;
import com.android.systemui.wallpapers.ImageWallpaper;
import com.android.systemui.wallpapers.ImageWallpaper_Factory;
import com.android.systemui.wmshell.BubblesManager;
import com.android.systemui.wmshell.WMShell;
import com.android.systemui.wmshell.WMShell_Factory;
import com.android.wm.shell.ProtoLogController;
import com.android.wm.shell.RootDisplayAreaOrganizer;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.TaskViewFactory;
import com.android.wm.shell.TaskViewFactoryController;
import com.android.wm.shell.TaskViewTransitions;
import com.android.wm.shell.WindowManagerShellWrapper;
import com.android.wm.shell.activityembedding.ActivityEmbeddingController;
import com.android.wm.shell.animation.FlingAnimationUtils;
import com.android.wm.shell.animation.FlingAnimationUtils_Builder_Factory;
import com.android.wm.shell.back.BackAnimation;
import com.android.wm.shell.back.BackAnimationController;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleData;
import com.android.wm.shell.bubbles.BubbleLogger;
import com.android.wm.shell.bubbles.BubblePositioner;
import com.android.wm.shell.bubbles.Bubbles;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayImeController;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.DockStateReader;
import com.android.wm.shell.common.DockStateReader_Factory;
import com.android.wm.shell.common.FloatingContentCoordinator;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.common.SystemWindows;
import com.android.wm.shell.common.TaskStackListenerImpl;
import com.android.wm.shell.common.TransactionPool;
import com.android.wm.shell.compatui.CompatUIController;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideActivityEmbeddingControllerFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideBackAnimationControllerFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideBackAnimationFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideBubblesFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideCompatUIControllerFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideDesktopModeControllerFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideDesktopModeFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideDesktopTaskRepositoryFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideDisplayAreaHelperFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideDisplayControllerFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideDisplayImeControllerFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideDisplayInsetsControllerFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideDisplayLayoutFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideDragAndDropControllerFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideFloatingContentCoordinatorFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideFreeformComponentsFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideFullscreenTaskListenerFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideHideDisplayCutoutControllerFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideIconProviderFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideIndependentShellComponentsToCreateFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideOneHandedFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvidePipMediaControllerFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvidePipSurfaceTransactionHelperFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvidePipUiEventLoggerFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideProtoLogControllerFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideRecentTasksControllerFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideRecentTasksFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideRemoteTransitionsFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideRootDisplayAreaOrganizerFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideRootTaskDisplayAreaOrganizerFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideShellCommandHandlerFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideShellControllerFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideShellInitFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideShellSysuiCallbacksFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideShellTaskOrganizerFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideSplitScreenFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideStartingSurfaceFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideStartingWindowControllerFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideStartingWindowTypeAlgorithmFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideSyncTransactionQueueFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideSystemWindowsFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideTaskViewFactoryControllerFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideTaskViewFactoryFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideTaskViewTransitionsFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideTransactionPoolFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideTransitionsFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideUnfoldControllerFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideUnfoldTransitionHandlerFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvideWindowManagerShellWrapperFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProviderTaskStackListenerImplFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvidesDesktopTasksControllerFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvidesOneHandedControllerFactory;
import com.android.wm.shell.dagger.WMShellBaseModule_ProvidesSplitScreenControllerFactory;
import com.android.wm.shell.dagger.WMShellConcurrencyModule_ProvideMainHandlerFactory;
import com.android.wm.shell.dagger.WMShellConcurrencyModule_ProvideSharedBackgroundExecutorFactory;
import com.android.wm.shell.dagger.WMShellConcurrencyModule_ProvideSharedBackgroundHandlerFactory;
import com.android.wm.shell.dagger.WMShellConcurrencyModule_ProvideShellAnimationExecutorFactory;
import com.android.wm.shell.dagger.WMShellConcurrencyModule_ProvideShellMainChoreographerFactory;
import com.android.wm.shell.dagger.WMShellConcurrencyModule_ProvideShellMainExecutorFactory;
import com.android.wm.shell.dagger.WMShellConcurrencyModule_ProvideShellMainHandlerFactory;
import com.android.wm.shell.dagger.WMShellConcurrencyModule_ProvideSplashScreenExecutorFactory;
import com.android.wm.shell.dagger.WMShellConcurrencyModule_ProvideSysUIMainExecutorFactory;
import com.android.wm.shell.dagger.WMShellModule_ProvideBubbleControllerFactory;
import com.android.wm.shell.dagger.WMShellModule_ProvideBubbleDataFactory;
import com.android.wm.shell.dagger.WMShellModule_ProvideBubbleLoggerFactory;
import com.android.wm.shell.dagger.WMShellModule_ProvideBubblePositionerFactory;
import com.android.wm.shell.dagger.WMShellModule_ProvideDefaultMixedHandlerFactory;
import com.android.wm.shell.dagger.WMShellModule_ProvideDesktopModeControllerFactory;
import com.android.wm.shell.dagger.WMShellModule_ProvideDesktopModeTaskRepositoryFactory;
import com.android.wm.shell.dagger.WMShellModule_ProvideDesktopTasksControllerFactory;
import com.android.wm.shell.dagger.WMShellModule_ProvideFreeformComponentsFactory;
import com.android.wm.shell.dagger.WMShellModule_ProvideFreeformTaskListenerFactory;
import com.android.wm.shell.dagger.WMShellModule_ProvideFreeformTaskTransitionHandlerFactory;
import com.android.wm.shell.dagger.WMShellModule_ProvideFreeformTaskTransitionObserverFactory;
import com.android.wm.shell.dagger.WMShellModule_ProvideFullscreenUnfoldTaskAnimatorFactory;
import com.android.wm.shell.dagger.WMShellModule_ProvideIndependentShellComponentsToCreateFactory;
import com.android.wm.shell.dagger.WMShellModule_ProvideKidsModeTaskOrganizerFactory;
import com.android.wm.shell.dagger.WMShellModule_ProvideOneHandedControllerFactory;
import com.android.wm.shell.dagger.WMShellModule_ProvidePhonePipKeepClearAlgorithmFactory;
import com.android.wm.shell.dagger.WMShellModule_ProvidePipAnimationControllerFactory;
import com.android.wm.shell.dagger.WMShellModule_ProvidePipAppOpsListenerFactory;
import com.android.wm.shell.dagger.WMShellModule_ProvidePipBoundsStateFactory;
import com.android.wm.shell.dagger.WMShellModule_ProvidePipFactory;
import com.android.wm.shell.dagger.WMShellModule_ProvidePipMotionHelperFactory;
import com.android.wm.shell.dagger.WMShellModule_ProvidePipParamsChangedForwarderFactory;
import com.android.wm.shell.dagger.WMShellModule_ProvidePipSnapAlgorithmFactory;
import com.android.wm.shell.dagger.WMShellModule_ProvidePipTaskOrganizerFactory;
import com.android.wm.shell.dagger.WMShellModule_ProvidePipTouchHandlerFactory;
import com.android.wm.shell.dagger.WMShellModule_ProvidePipTransitionControllerFactory;
import com.android.wm.shell.dagger.WMShellModule_ProvidePipTransitionStateFactory;
import com.android.wm.shell.dagger.WMShellModule_ProvideSplitScreenControllerFactory;
import com.android.wm.shell.dagger.WMShellModule_ProvideSplitTaskUnfoldAnimatorBaseFactory;
import com.android.wm.shell.dagger.WMShellModule_ProvideUnfoldAnimationControllerFactory;
import com.android.wm.shell.dagger.WMShellModule_ProvideUnfoldBackgroundControllerFactory;
import com.android.wm.shell.dagger.WMShellModule_ProvideUnfoldTransitionHandlerFactory;
import com.android.wm.shell.dagger.WMShellModule_ProvideWindowDecorViewModelFactory;
import com.android.wm.shell.dagger.WMShellModule_ProvidesPipBoundsAlgorithmFactory;
import com.android.wm.shell.dagger.WMShellModule_ProvidesPipPhoneMenuControllerFactory;
import com.android.wm.shell.desktopmode.DesktopMode;
import com.android.wm.shell.desktopmode.DesktopModeController;
import com.android.wm.shell.desktopmode.DesktopModeTaskRepository;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.displayareahelper.DisplayAreaHelper;
import com.android.wm.shell.draganddrop.DragAndDropController;
import com.android.wm.shell.freeform.FreeformComponents;
import com.android.wm.shell.freeform.FreeformTaskListener;
import com.android.wm.shell.freeform.FreeformTaskTransitionHandler;
import com.android.wm.shell.freeform.FreeformTaskTransitionObserver;
import com.android.wm.shell.fullscreen.FullscreenTaskListener;
import com.android.wm.shell.hidedisplaycutout.HideDisplayCutoutController;
import com.android.wm.shell.kidsmode.KidsModeTaskOrganizer;
import com.android.wm.shell.onehanded.OneHanded;
import com.android.wm.shell.onehanded.OneHandedController;
import com.android.wm.shell.pip.Pip;
import com.android.wm.shell.pip.PipAnimationController;
import com.android.wm.shell.pip.PipAppOpsListener;
import com.android.wm.shell.pip.PipBoundsAlgorithm;
import com.android.wm.shell.pip.PipBoundsState;
import com.android.wm.shell.pip.PipMediaController;
import com.android.wm.shell.pip.PipParamsChangedForwarder;
import com.android.wm.shell.pip.PipSnapAlgorithm;
import com.android.wm.shell.pip.PipSurfaceTransactionHelper;
import com.android.wm.shell.pip.PipTaskOrganizer;
import com.android.wm.shell.pip.PipTransitionController;
import com.android.wm.shell.pip.PipTransitionState;
import com.android.wm.shell.pip.PipUiEventLogger;
import com.android.wm.shell.pip.phone.PhonePipKeepClearAlgorithm;
import com.android.wm.shell.pip.phone.PhonePipMenuController;
import com.android.wm.shell.pip.phone.PipMotionHelper;
import com.android.wm.shell.pip.phone.PipTouchHandler;
import com.android.wm.shell.recents.RecentTasks;
import com.android.wm.shell.recents.RecentTasksController;
import com.android.wm.shell.splitscreen.SplitScreen;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.startingsurface.StartingSurface;
import com.android.wm.shell.startingsurface.StartingWindowController;
import com.android.wm.shell.startingsurface.StartingWindowTypeAlgorithm;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.sysui.ShellInterface;
import com.android.wm.shell.transition.DefaultMixedHandler;
import com.android.wm.shell.transition.ShellTransitions;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.unfold.ShellUnfoldProgressProvider;
import com.android.wm.shell.unfold.UnfoldAnimationController;
import com.android.wm.shell.unfold.UnfoldBackgroundController;
import com.android.wm.shell.unfold.UnfoldTransitionHandler;
import com.android.wm.shell.unfold.animation.FullscreenUnfoldTaskAnimator;
import com.android.wm.shell.unfold.animation.SplitTaskUnfoldAnimator;
import com.android.wm.shell.windowdecor.WindowDecorViewModel;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dagger.Lazy;
import dagger.internal.DelegateFactory;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import dagger.internal.InstanceFactory;
import dagger.internal.MapBuilder;
import dagger.internal.MapProviderFactory;
import dagger.internal.Preconditions;
import dagger.internal.SetBuilder;
import dagger.internal.SetFactory;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent.class */
public final class DaggerReferenceGlobalRootComponent implements ReferenceGlobalRootComponent {
    public static final Provider ABSENT_JDK_OPTIONAL_PROVIDER = InstanceFactory.create(Optional.empty());
    public Provider<ATraceLoggerTransitionProgressListener> aTraceLoggerTransitionProgressListenerProvider;
    public Provider<ActivityManagerActivityTypeProvider> activityManagerActivityTypeProvider;
    public final Context context;
    public Provider<Context> contextProvider;
    public Provider<DeviceFoldStateProvider> deviceFoldStateProvider;
    public Provider<DeviceStateManagerFoldProvider> deviceStateManagerFoldProvider;
    public Provider<DumpManager> dumpManagerProvider;
    public Provider<ScaleAwareTransitionProgressProvider.Factory> factoryProvider;
    public final GlobalModule globalModule;
    public Provider<HingeAngleProvider> hingeAngleProvider;
    public final Boolean instrumentationTest;
    public Provider<LifecycleScreenStatusProvider> lifecycleScreenStatusProvider;
    public Provider<PluginDependencyProvider> pluginDependencyProvider;
    public Provider<PluginEnablerImpl> pluginEnablerImplProvider;
    public Provider<AccessibilityManager> provideAccessibilityManagerProvider;
    public Provider<ActivityManager> provideActivityManagerProvider;
    public Provider<ActivityTaskManager> provideActivityTaskManagerProvider;
    public Provider<AlarmManager> provideAlarmManagerProvider;
    public Provider<AmbientDisplayConfiguration> provideAmbientDisplayConfigurationProvider;
    public Provider<Context> provideApplicationContextProvider;
    public Provider<AudioManager> provideAudioManagerProvider;
    public Provider<BluetoothAdapter> provideBluetoothAdapterProvider;
    public Provider<BluetoothManager> provideBluetoothManagerProvider;
    public Provider<CameraManager> provideCameraManagerProvider;
    public Provider<CaptioningManager> provideCaptioningManagerProvider;
    public Provider<CarrierConfigManager> provideCarrierConfigManagerProvider;
    public Provider<ClipboardManager> provideClipboardManagerProvider;
    public Provider<ColorDisplayManager> provideColorDisplayManagerProvider;
    public Provider<ConnectivityManager> provideConnectivityManagagerProvider;
    public Provider<ContentResolver> provideContentResolverProvider;
    public Provider<CrossWindowBlurListeners> provideCrossWindowBlurListenersProvider;
    public Provider<DevicePolicyManager> provideDevicePolicyManagerProvider;
    public Provider<DeviceStateManager> provideDeviceStateManagerProvider;
    public Provider<Integer> provideDisplayIdProvider;
    public Provider<DisplayManager> provideDisplayManagerProvider;
    public Provider<DisplayMetrics> provideDisplayMetricsProvider;
    public Provider<Execution> provideExecutionProvider;
    public Provider<FaceManager> provideFaceManagerProvider;
    public Provider<FoldStateProvider> provideFoldStateProvider;
    public Provider<IActivityManager> provideIActivityManagerProvider;
    public Provider<IActivityTaskManager> provideIActivityTaskManagerProvider;
    public Provider<IAudioService> provideIAudioServiceProvider;
    public Provider<IBatteryStats> provideIBatteryStatsProvider;
    public Provider<IDreamManager> provideIDreamManagerProvider;
    public Provider<INotificationManager> provideINotificationManagerProvider;
    public Provider<IPackageManager> provideIPackageManagerProvider;
    public Provider<IStatusBarService> provideIStatusBarServiceProvider;
    public Provider<IWindowManager> provideIWindowManagerProvider;
    public Provider<InputManager> provideInputManagerProvider;
    public Provider<InputMethodManager> provideInputMethodManagerProvider;
    public Provider<InteractionJankMonitor> provideInteractionJankMonitorProvider;
    public Provider<Boolean> provideIsTestHarnessProvider;
    public Provider<JobScheduler> provideJobSchedulerProvider;
    public Provider<KeyguardManager> provideKeyguardManagerProvider;
    public Provider<LatencyTracker> provideLatencyTrackerProvider;
    public Provider<LauncherApps> provideLauncherAppsProvider;
    public Provider<LockPatternUtils> provideLockPatternUtilsProvider;
    public Provider<DelayableExecutor> provideMainDelayableExecutorProvider;
    public Provider<Executor> provideMainExecutorProvider;
    public Provider<Handler> provideMainHandlerProvider;
    public Provider<MediaRouter2Manager> provideMediaRouter2ManagerProvider;
    public Provider<MediaSessionManager> provideMediaSessionManagerProvider;
    public Provider<MetricsLogger> provideMetricsLoggerProvider;
    public Provider<Optional<NaturalRotationUnfoldProgressProvider>> provideNaturalRotationProgressProvider;
    public Provider<NetworkScoreManager> provideNetworkScoreManagerProvider;
    public Provider<NotificationManagerCompat> provideNotificationManagerCompatProvider;
    public Provider<NotificationManager> provideNotificationManagerProvider;
    public Provider<NotificationMessagingUtil> provideNotificationMessagingUtilProvider;
    public Provider<Optional<TelecomManager>> provideOptionalTelecomManagerProvider;
    public Provider<Optional<Vibrator>> provideOptionalVibratorProvider;
    public Provider<OverlayManager> provideOverlayManagerProvider;
    public Provider<PackageManager> providePackageManagerProvider;
    public Provider<PackageManagerWrapper> providePackageManagerWrapperProvider;
    public Provider<PermissionManager> providePermissionManagerProvider;
    public Provider<PluginActionManager.Factory> providePluginInstanceManagerFactoryProvider;
    public Provider<PowerExemptionManager> providePowerExemptionManagerProvider;
    public Provider<PowerManager> providePowerManagerProvider;
    public Provider<Resources> provideResourcesProvider;
    public Provider<SafetyCenterManager> provideSafetyCenterManagerProvider;
    public Provider<SensorPrivacyManager> provideSensorPrivacyManagerProvider;
    public Provider<SharedPreferences> provideSharePreferencesProvider;
    public Provider<ShellUnfoldProgressProvider> provideShellProgressProvider;
    public Provider<ShortcutManager> provideShortcutManagerProvider;
    public Provider<SmartspaceManager> provideSmartspaceManagerProvider;
    public Provider<StatsManager> provideStatsManagerProvider;
    public Provider<Optional<ScopedUnfoldTransitionProgressProvider>> provideStatusBarScopedTransitionProvider;
    public Provider<StorageManager> provideStorageManagerProvider;
    public Provider<SubscriptionManager> provideSubscriptionManagerProvider;
    public Provider<TelecomManager> provideTelecomManagerProvider;
    public Provider<TelephonyManager> provideTelephonyManagerProvider;
    public Provider<TextClassificationManager> provideTextClassificationManagerProvider;
    public Provider<TrustManager> provideTrustManagerProvider;
    public Provider<Executor> provideUiBackgroundExecutorProvider;
    public Provider<UiEventLogger> provideUiEventLoggerProvider;
    public Provider<UserManager> provideUserManagerProvider;
    public Provider<Vibrator> provideVibratorProvider;
    public Provider<ViewConfiguration> provideViewConfigurationProvider;
    public Provider<WallpaperManager> provideWallpaperManagerProvider;
    public Provider<WifiManager> provideWifiManagerProvider;
    public Provider<WindowManager> provideWindowManagerProvider;
    public Provider<LayoutInflater> providerLayoutInflaterProvider;
    public Provider<BiometricManager> providesBiometricManagerProvider;
    public Provider<Choreographer> providesChoreographerProvider;
    public Provider<FingerprintManager> providesFingerprintManagerProvider;
    public Provider<Optional<FoldStateLogger>> providesFoldStateLoggerProvider;
    public Provider<Optional<FoldStateLoggingProvider>> providesFoldStateLoggingProvider;
    public Provider<Executor> providesPluginExecutorProvider;
    public Provider<PluginInstance.Factory> providesPluginInstanceFactoryProvider;
    public Provider<PluginManager> providesPluginManagerProvider;
    public Provider<PluginPrefs> providesPluginPrefsProvider;
    public Provider<List<String>> providesPrivilegedPluginsProvider;
    public Provider<SensorManager> providesSensorManagerProvider;
    public Provider<QSExpansionPathInterpolator> qSExpansionPathInterpolatorProvider;
    public Provider<ResourceUnfoldTransitionConfig> resourceUnfoldTransitionConfigProvider;
    public Provider<RotationChangeProvider> rotationChangeProvider;
    public ScaleAwareTransitionProgressProvider_Factory scaleAwareTransitionProgressProvider;
    public Provider<ScreenLifecycle> screenLifecycleProvider;
    public Provider<ScreenStatusProvider> screenStatusProvider;
    public Provider<String> tracingTagPrefixProvider;
    public Provider<UncaughtExceptionPreHandlerManager> uncaughtExceptionPreHandlerManagerProvider;
    public Provider<UnfoldKeyguardVisibilityManagerImpl> unfoldKeyguardVisibilityManagerImplProvider;
    public Provider<UnfoldKeyguardVisibilityManager> unfoldKeyguardVisibilityManagerProvider;
    public Provider<UnfoldKeyguardVisibilityProvider> unfoldKeyguardVisibilityProvider;
    public Provider<Optional<UnfoldTransitionProgressProvider>> unfoldTransitionProgressProvider;

    /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$Builder.class */
    public static final class Builder implements ReferenceGlobalRootComponent.Builder {
        public Context context;
        public Boolean instrumentationTest;

        public Builder() {
        }

        /* JADX DEBUG: Method merged with bridge method */
        @Override // com.android.systemui.dagger.GlobalRootComponent.Builder
        public ReferenceGlobalRootComponent build() {
            Preconditions.checkBuilderRequirement(this.context, Context.class);
            Preconditions.checkBuilderRequirement(this.instrumentationTest, Boolean.class);
            return new DaggerReferenceGlobalRootComponent(new GlobalModule(), new AndroidInternalsModule(), new FrameworkServicesModule(), new UnfoldTransitionModule(), new UnfoldSharedModule(), this.context, this.instrumentationTest, null);
        }

        /* JADX DEBUG: Method merged with bridge method */
        @Override // com.android.systemui.dagger.GlobalRootComponent.Builder
        public Builder context(Context context) {
            this.context = (Context) Preconditions.checkNotNull(context);
            return this;
        }

        /* JADX DEBUG: Method merged with bridge method */
        @Override // com.android.systemui.dagger.GlobalRootComponent.Builder
        public Builder instrumentationTest(boolean z) {
            this.instrumentationTest = (Boolean) Preconditions.checkNotNull(Boolean.valueOf(z));
            return this;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$PresentJdkOptionalInstanceProvider.class */
    public static final class PresentJdkOptionalInstanceProvider<T> implements Provider<Optional<T>> {
        public final Provider<T> delegate;

        public PresentJdkOptionalInstanceProvider(Provider<T> provider) {
            this.delegate = (Provider) Preconditions.checkNotNull(provider);
        }

        public static <T> Provider<Optional<T>> of(Provider<T> provider) {
            return new PresentJdkOptionalInstanceProvider(provider);
        }

        /* JADX DEBUG: Method merged with bridge method */
        public Optional<T> get() {
            return Optional.of(this.delegate.get());
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$PresentJdkOptionalLazyProvider.class */
    public static final class PresentJdkOptionalLazyProvider<T> implements Provider<Optional<Lazy<T>>> {
        public final Provider<T> delegate;

        public PresentJdkOptionalLazyProvider(Provider<T> provider) {
            this.delegate = (Provider) Preconditions.checkNotNull(provider);
        }

        public static <T> Provider<Optional<Lazy<T>>> of(Provider<T> provider) {
            return new PresentJdkOptionalLazyProvider(provider);
        }

        /* JADX DEBUG: Method merged with bridge method */
        public Optional<Lazy<T>> get() {
            return Optional.of(DoubleCheck.lazy(this.delegate));
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentBuilder.class */
    public final class ReferenceSysUIComponentBuilder implements ReferenceSysUIComponent.Builder {
        public Optional<BackAnimation> setBackAnimation;
        public Optional<Bubbles> setBubbles;
        public Optional<DesktopMode> setDesktopMode;
        public Optional<DisplayAreaHelper> setDisplayAreaHelper;
        public Optional<OneHanded> setOneHanded;
        public Optional<Pip> setPip;
        public Optional<RecentTasks> setRecentTasks;
        public ShellInterface setShell;
        public Optional<SplitScreen> setSplitScreen;
        public Optional<StartingSurface> setStartingSurface;
        public Optional<TaskViewFactory> setTaskViewFactory;
        public ShellTransitions setTransitions;

        public ReferenceSysUIComponentBuilder() {
        }

        /* JADX DEBUG: Method merged with bridge method */
        @Override // com.android.systemui.dagger.SysUIComponent.Builder
        public ReferenceSysUIComponent build() {
            Preconditions.checkBuilderRequirement(this.setShell, ShellInterface.class);
            Preconditions.checkBuilderRequirement(this.setPip, Optional.class);
            Preconditions.checkBuilderRequirement(this.setSplitScreen, Optional.class);
            Preconditions.checkBuilderRequirement(this.setOneHanded, Optional.class);
            Preconditions.checkBuilderRequirement(this.setBubbles, Optional.class);
            Preconditions.checkBuilderRequirement(this.setTaskViewFactory, Optional.class);
            Preconditions.checkBuilderRequirement(this.setTransitions, ShellTransitions.class);
            Preconditions.checkBuilderRequirement(this.setStartingSurface, Optional.class);
            Preconditions.checkBuilderRequirement(this.setDisplayAreaHelper, Optional.class);
            Preconditions.checkBuilderRequirement(this.setRecentTasks, Optional.class);
            Preconditions.checkBuilderRequirement(this.setBackAnimation, Optional.class);
            Preconditions.checkBuilderRequirement(this.setDesktopMode, Optional.class);
            return new ReferenceSysUIComponentImpl(new LeakModule(), new NightDisplayListenerModule(), new SharedLibraryModule(), new KeyguardModule(), new SysUIUnfoldModule(), this.setShell, this.setPip, this.setSplitScreen, this.setOneHanded, this.setBubbles, this.setTaskViewFactory, this.setTransitions, this.setStartingSurface, this.setDisplayAreaHelper, this.setRecentTasks, this.setBackAnimation, this.setDesktopMode);
        }

        @Override // com.android.systemui.dagger.SysUIComponent.Builder
        public ReferenceSysUIComponentBuilder setBackAnimation(Optional<BackAnimation> optional) {
            this.setBackAnimation = (Optional) Preconditions.checkNotNull(optional);
            return this;
        }

        @Override // com.android.systemui.dagger.SysUIComponent.Builder
        public /* bridge */ /* synthetic */ SysUIComponent.Builder setBackAnimation(Optional optional) {
            return setBackAnimation((Optional<BackAnimation>) optional);
        }

        @Override // com.android.systemui.dagger.SysUIComponent.Builder
        public ReferenceSysUIComponentBuilder setBubbles(Optional<Bubbles> optional) {
            this.setBubbles = (Optional) Preconditions.checkNotNull(optional);
            return this;
        }

        @Override // com.android.systemui.dagger.SysUIComponent.Builder
        public /* bridge */ /* synthetic */ SysUIComponent.Builder setBubbles(Optional optional) {
            return setBubbles((Optional<Bubbles>) optional);
        }

        @Override // com.android.systemui.dagger.SysUIComponent.Builder
        public ReferenceSysUIComponentBuilder setDesktopMode(Optional<DesktopMode> optional) {
            this.setDesktopMode = (Optional) Preconditions.checkNotNull(optional);
            return this;
        }

        @Override // com.android.systemui.dagger.SysUIComponent.Builder
        public /* bridge */ /* synthetic */ SysUIComponent.Builder setDesktopMode(Optional optional) {
            return setDesktopMode((Optional<DesktopMode>) optional);
        }

        @Override // com.android.systemui.dagger.SysUIComponent.Builder
        public ReferenceSysUIComponentBuilder setDisplayAreaHelper(Optional<DisplayAreaHelper> optional) {
            this.setDisplayAreaHelper = (Optional) Preconditions.checkNotNull(optional);
            return this;
        }

        @Override // com.android.systemui.dagger.SysUIComponent.Builder
        public /* bridge */ /* synthetic */ SysUIComponent.Builder setDisplayAreaHelper(Optional optional) {
            return setDisplayAreaHelper((Optional<DisplayAreaHelper>) optional);
        }

        @Override // com.android.systemui.dagger.SysUIComponent.Builder
        public ReferenceSysUIComponentBuilder setOneHanded(Optional<OneHanded> optional) {
            this.setOneHanded = (Optional) Preconditions.checkNotNull(optional);
            return this;
        }

        @Override // com.android.systemui.dagger.SysUIComponent.Builder
        public /* bridge */ /* synthetic */ SysUIComponent.Builder setOneHanded(Optional optional) {
            return setOneHanded((Optional<OneHanded>) optional);
        }

        @Override // com.android.systemui.dagger.SysUIComponent.Builder
        public ReferenceSysUIComponentBuilder setPip(Optional<Pip> optional) {
            this.setPip = (Optional) Preconditions.checkNotNull(optional);
            return this;
        }

        @Override // com.android.systemui.dagger.SysUIComponent.Builder
        public /* bridge */ /* synthetic */ SysUIComponent.Builder setPip(Optional optional) {
            return setPip((Optional<Pip>) optional);
        }

        @Override // com.android.systemui.dagger.SysUIComponent.Builder
        public ReferenceSysUIComponentBuilder setRecentTasks(Optional<RecentTasks> optional) {
            this.setRecentTasks = (Optional) Preconditions.checkNotNull(optional);
            return this;
        }

        @Override // com.android.systemui.dagger.SysUIComponent.Builder
        public /* bridge */ /* synthetic */ SysUIComponent.Builder setRecentTasks(Optional optional) {
            return setRecentTasks((Optional<RecentTasks>) optional);
        }

        /* JADX DEBUG: Method merged with bridge method */
        @Override // com.android.systemui.dagger.SysUIComponent.Builder
        public ReferenceSysUIComponentBuilder setShell(ShellInterface shellInterface) {
            this.setShell = (ShellInterface) Preconditions.checkNotNull(shellInterface);
            return this;
        }

        @Override // com.android.systemui.dagger.SysUIComponent.Builder
        public ReferenceSysUIComponentBuilder setSplitScreen(Optional<SplitScreen> optional) {
            this.setSplitScreen = (Optional) Preconditions.checkNotNull(optional);
            return this;
        }

        @Override // com.android.systemui.dagger.SysUIComponent.Builder
        public /* bridge */ /* synthetic */ SysUIComponent.Builder setSplitScreen(Optional optional) {
            return setSplitScreen((Optional<SplitScreen>) optional);
        }

        @Override // com.android.systemui.dagger.SysUIComponent.Builder
        public ReferenceSysUIComponentBuilder setStartingSurface(Optional<StartingSurface> optional) {
            this.setStartingSurface = (Optional) Preconditions.checkNotNull(optional);
            return this;
        }

        @Override // com.android.systemui.dagger.SysUIComponent.Builder
        public /* bridge */ /* synthetic */ SysUIComponent.Builder setStartingSurface(Optional optional) {
            return setStartingSurface((Optional<StartingSurface>) optional);
        }

        @Override // com.android.systemui.dagger.SysUIComponent.Builder
        public ReferenceSysUIComponentBuilder setTaskViewFactory(Optional<TaskViewFactory> optional) {
            this.setTaskViewFactory = (Optional) Preconditions.checkNotNull(optional);
            return this;
        }

        @Override // com.android.systemui.dagger.SysUIComponent.Builder
        public /* bridge */ /* synthetic */ SysUIComponent.Builder setTaskViewFactory(Optional optional) {
            return setTaskViewFactory((Optional<TaskViewFactory>) optional);
        }

        /* JADX DEBUG: Method merged with bridge method */
        @Override // com.android.systemui.dagger.SysUIComponent.Builder
        public ReferenceSysUIComponentBuilder setTransitions(ShellTransitions shellTransitions) {
            this.setTransitions = (ShellTransitions) Preconditions.checkNotNull(shellTransitions);
            return this;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl.class */
    public final class ReferenceSysUIComponentImpl implements ReferenceSysUIComponent {
        public Provider<AODTile> aODTileProvider;
        public Provider<AccessibilityButtonModeObserver> accessibilityButtonModeObserverProvider;
        public Provider<AccessibilityButtonTargetsObserver> accessibilityButtonTargetsObserverProvider;
        public Provider<AccessibilityController> accessibilityControllerProvider;
        public Provider<AccessibilityFloatingMenuController> accessibilityFloatingMenuControllerProvider;
        public Provider<AccessibilityManagerWrapper> accessibilityManagerWrapperProvider;
        public Provider<ActionClickLogger> actionClickLoggerProvider;
        public Provider<ActionIntentExecutor> actionIntentExecutorProvider;
        public Provider<ActionProxyReceiver> actionProxyReceiverProvider;
        public Provider<ActiveUnlockConfig> activeUnlockConfigProvider;
        public Provider<ActivityIntentHelper> activityIntentHelperProvider;
        public Provider<ActivityStarterDelegate> activityStarterDelegateProvider;
        public Provider<UserDetailView.Adapter> adapterProvider;
        public Provider<AirplaneModeInteractor> airplaneModeInteractorProvider;
        public Provider<AirplaneModeRepositoryImpl> airplaneModeRepositoryImplProvider;
        public Provider<AirplaneModeTile> airplaneModeTileProvider;
        public Provider<AirplaneModeViewModelImpl> airplaneModeViewModelImplProvider;
        public Provider<AlarmTile> alarmTileProvider;
        public Provider<AlwaysOnDisplayPolicy> alwaysOnDisplayPolicyProvider;
        public Provider<AmbientDisplayTile> ambientDisplayTileProvider;
        public Provider<AmbientState> ambientStateProvider;
        public Provider<AnimatedImageNotificationManager> animatedImageNotificationManagerProvider;
        public Provider<AntiFlickerTile> antiFlickerTileProvider;
        public Provider<AppOpsControllerImpl> appOpsControllerImplProvider;
        public Provider<AppOpsPrivacyItemMonitor> appOpsPrivacyItemMonitorProvider;
        public Provider<CoroutineScope> applicationScopeProvider;
        public Provider<AssistLogger> assistLoggerProvider;
        public Provider<AssistManager> assistManagerProvider;
        public Provider<AssistantFeedbackController> assistantFeedbackControllerProvider;
        public Provider<AsyncActivityLauncher> asyncActivityLauncherProvider;
        public Provider<AsyncSensorManager> asyncSensorManagerProvider;
        public Provider<AuthController> authControllerProvider;
        public Provider<AutoHideController> autoHideControllerProvider;
        public Provider<BatterySaverTile> batterySaverTileProvider;
        public Provider<BatteryStateNotifier> batteryStateNotifierProvider;
        public Provider<CoroutineDispatcher> bgDispatcherProvider;
        public Provider<DeviceProvisionedController> bindDeviceProvisionedControllerProvider;
        public Provider<BindEventManagerImpl> bindEventManagerImplProvider;
        public Provider<RotationPolicyWrapper> bindRotationPolicyWrapperProvider;
        public Provider<SystemClock> bindSystemClockProvider;
        public Provider<ServerFlagReader> bindsReaderProvider;
        public Provider<BiometricUnlockController> biometricUnlockControllerProvider;
        public Provider<BiometricUnlockLogger> biometricUnlockLoggerProvider;
        public Provider<BluetoothControllerImpl> bluetoothControllerImplProvider;
        public Provider<BluetoothLogger> bluetoothLoggerProvider;
        public Provider<BluetoothTile> bluetoothTileProvider;
        public Provider<BlurUtils> blurUtilsProvider;
        public Provider<BootCompleteCacheImpl> bootCompleteCacheImplProvider;
        public Provider<BouncerViewImpl> bouncerViewImplProvider;
        public Provider<BrightLineFalsingManager> brightLineFalsingManagerProvider;
        public Provider<BrightnessDialog> brightnessDialogProvider;
        public Provider<BroadcastDialogController> broadcastDialogControllerProvider;
        public Provider<BroadcastDispatcherLogger> broadcastDispatcherLoggerProvider;
        public Provider<BroadcastDispatcher> broadcastDispatcherProvider;
        public Provider<BroadcastDispatcherStartable> broadcastDispatcherStartableProvider;
        public Provider<BroadcastSender> broadcastSenderProvider;
        public Provider<ThresholdSensorImpl.BuilderFactory> builderFactoryProvider;
        public Provider<ThresholdSensorImpl.Builder> builderProvider;
        public Provider<NotificationClicker.Builder> builderProvider2;
        public Provider<WakeLock.Builder> builderProvider3;
        public Provider<DelayedWakeLock.Builder> builderProvider4;
        public Provider<CustomTile.Builder> builderProvider5;
        public Provider<NightDisplayListenerModule.Builder> builderProvider6;
        public Provider<AutoAddTracker.Builder> builderProvider7;
        public Provider<TileServiceRequestController.Builder> builderProvider8;
        public Provider<CaffeineTile> caffeineTileProvider;
        public Provider<CallbackHandler> callbackHandlerProvider;
        public Provider<CameraGestureHelper> cameraGestureHelperProvider;
        public Provider<CameraIntentsWrapper> cameraIntentsWrapperProvider;
        public Provider<CameraLauncher> cameraLauncherProvider;
        public Provider<CameraQuickAffordanceConfig> cameraQuickAffordanceConfigProvider;
        public Provider<CameraToggleTile> cameraToggleTileProvider;
        public Provider<CarrierConfigTracker> carrierConfigTrackerProvider;
        public Provider<CastControllerImpl> castControllerImplProvider;
        public Provider<CastTile> castTileProvider;
        public Provider<CellularTile> cellularTileProvider;
        public Provider<CentralSurfacesComponent.Factory> centralSurfacesComponentFactoryProvider;
        public Provider<CentralSurfacesImpl> centralSurfacesImplProvider;
        public Provider<ChannelEditorDialogController> channelEditorDialogControllerProvider;
        public Provider<ChipbarCoordinator> chipbarCoordinatorProvider;
        public Provider<ChipbarLogger> chipbarLoggerProvider;
        public Provider<ChooserSelector> chooserSelectorProvider;
        public Provider<ClipboardListener> clipboardListenerProvider;
        public Provider<ClipboardOverlayControllerLegacyFactory> clipboardOverlayControllerLegacyFactoryProvider;
        public Provider<ClipboardOverlayController> clipboardOverlayControllerProvider;
        public Provider clipboardOverlayUtilsProvider;
        public Provider<ClipboardOverlayWindow> clipboardOverlayWindowProvider;
        public Provider clipboardToastProvider;
        public Provider<ClockEventController> clockEventControllerProvider;
        public Provider<ClockManager> clockManagerProvider;
        public Provider<ColorCorrectionTile> colorCorrectionTileProvider;
        public Provider<ColorInversionTile> colorInversionTileProvider;
        public Provider<CommandRegistry> commandRegistryProvider;
        public Provider<ConfigurationControllerImpl> configurationControllerImplProvider;
        public Provider<ConnectivityConstants> connectivityConstantsProvider;
        public Provider<ConnectivityPipelineLogger> connectivityPipelineLoggerProvider;
        public Provider<ConnectivityRepositoryImpl> connectivityRepositoryImplProvider;
        public Provider<ConnectivitySlots> connectivitySlotsProvider;
        public Provider<ContextComponentResolver> contextComponentResolverProvider;
        public Provider<ControlActionCoordinatorImpl> controlActionCoordinatorImplProvider;
        public Provider<ControlsActivity> controlsActivityProvider;
        public Provider<ControlsBindingControllerImpl> controlsBindingControllerImplProvider;
        public Provider<ControlsComponent> controlsComponentProvider;
        public Provider<ControlsControllerImpl> controlsControllerImplProvider;
        public Provider<ControlsEditingActivity> controlsEditingActivityProvider;
        public Provider<ControlsFavoritingActivity> controlsFavoritingActivityProvider;
        public Provider<ControlsListingControllerImpl> controlsListingControllerImplProvider;
        public Provider<ControlsMetricsLoggerImpl> controlsMetricsLoggerImplProvider;
        public Provider<ControlsProviderSelectorActivity> controlsProviderSelectorActivityProvider;
        public Provider<ControlsRequestDialog> controlsRequestDialogProvider;
        public Provider<ControlsSettingsDialogManagerImpl> controlsSettingsDialogManagerImplProvider;
        public Provider<ControlsSettingsRepositoryImpl> controlsSettingsRepositoryImplProvider;
        public Provider<ControlsUiControllerImpl> controlsUiControllerImplProvider;
        public Provider<ConversationNotificationManager> conversationNotificationManagerProvider;
        public Provider<ConversationNotificationProcessor> conversationNotificationProcessorProvider;
        public Provider<CoordinatorsSubcomponent.Factory> coordinatorsSubcomponentFactoryProvider;
        public Provider<CreateUserActivity> createUserActivityProvider;
        public Provider<CustomIconCache> customIconCacheProvider;
        public Provider<CustomTileStatePersister> customTileStatePersisterProvider;
        public Provider<DarkIconDispatcherImpl> darkIconDispatcherImplProvider;
        public Provider<DataSaverTile> dataSaverTileProvider;
        public Provider<DateFormatUtil> dateFormatUtilProvider;
        public Provider<DebugModeFilterProvider> debugModeFilterProvider;
        public Provider<DefaultUiController> defaultUiControllerProvider;
        public Provider<DeleteScreenshotReceiver> deleteScreenshotReceiverProvider;
        public Provider<DemoMobileConnectionsRepository> demoMobileConnectionsRepositoryProvider;
        public Provider<DemoModeMobileConnectionDataSource> demoModeMobileConnectionDataSourceProvider;
        public Provider<DemoModeWifiDataSource> demoModeWifiDataSourceProvider;
        public Provider<DemoWifiRepository> demoWifiRepositoryProvider;
        public Provider<Dependency> dependencyProvider;
        public Provider<DeviceConfigProxy> deviceConfigProxyProvider;
        public Provider<DeviceControlsControllerImpl> deviceControlsControllerImplProvider;
        public Provider<DeviceControlsTile> deviceControlsTileProvider;
        public Provider<DevicePostureControllerImpl> devicePostureControllerImplProvider;
        public Provider<DeviceProvisionedControllerImpl> deviceProvisionedControllerImplProvider;
        public Provider<DeviceStateRotationLockSettingController> deviceStateRotationLockSettingControllerProvider;
        public Provider diagonalClassifierProvider;
        public Provider<DisableFlagsLogger> disableFlagsLoggerProvider;
        public Provider<DisabledWifiRepository> disabledWifiRepositoryProvider;
        public Provider<DismissCallbackRegistry> dismissCallbackRegistryProvider;
        public Provider distanceClassifierProvider;
        public Provider<DndTile> dndTileProvider;
        public Provider<DoNotDisturbQuickAffordanceConfig> doNotDisturbQuickAffordanceConfigProvider;
        public Provider<DockManagerImpl> dockManagerImplProvider;
        public Provider<DoubleTapClassifier> doubleTapClassifierProvider;
        public Provider<DozeComponent.Builder> dozeComponentBuilderProvider;
        public Provider<DozeLog> dozeLogProvider;
        public Provider<DozeLogger> dozeLoggerProvider;
        public Provider<DozeParameters> dozeParametersProvider;
        public Provider<DozeScrimController> dozeScrimControllerProvider;
        public Provider<DozeServiceHost> dozeServiceHostProvider;
        public Provider<DozeService> dozeServiceProvider;
        public Provider<DozeTransitionListener> dozeTransitionListenerProvider;
        public Provider<DreamOverlayCallbackController> dreamOverlayCallbackControllerProvider;
        public Provider<DreamOverlayComponent.Factory> dreamOverlayComponentFactoryProvider;
        public Provider<DreamOverlayService> dreamOverlayServiceProvider;
        public Provider<DreamOverlayStateController> dreamOverlayStateControllerProvider;
        public Provider<DreamOverlayStatusBarItemsProvider> dreamOverlayStatusBarItemsProvider;
        public Provider<DreamTile> dreamTileProvider;
        public Provider<DreamingToLockscreenTransitionViewModel> dreamingToLockscreenTransitionViewModelProvider;
        public Provider<DumpHandler> dumpHandlerProvider;
        public Provider<DynamicPrivacyController> dynamicPrivacyControllerProvider;
        public Provider<EnhancedEstimatesImpl> enhancedEstimatesImplProvider;
        public C0050GuestResetOrExitSessionReceiver_ExitSessionDialog_Factory exitSessionDialogProvider;
        public Provider<ExpandableNotificationRowComponent.Builder> expandableNotificationRowComponentBuilderProvider;
        public Provider<NotificationLogger.ExpansionStateLogger> expansionStateLoggerProvider;
        public Provider<ExtensionControllerImpl> extensionControllerImplProvider;
        public Provider<FaceHelpMessageDeferral> faceHelpMessageDeferralProvider;
        public Provider<FaceMessageDeferralLogger> faceMessageDeferralLoggerProvider;
        public Provider<FaceScanningProviderFactory> faceScanningProviderFactoryProvider;
        public Provider<FaceWakeUpTriggersConfig> faceWakeUpTriggersConfigProvider;
        public Provider<LightBarTransitionsController.Factory> factoryProvider;
        public Provider<KeyguardBouncer.Factory> factoryProvider10;
        public Provider<KeyguardMessageAreaController.Factory> factoryProvider11;
        public Provider<BackPanelController.Factory> factoryProvider12;
        public Provider<EdgeBackGestureHandler.Factory> factoryProvider13;
        public Provider<MobileConnectionRepositoryImpl.Factory> factoryProvider14;
        public Provider<MobileIconsViewModel.Factory> factoryProvider15;
        public Provider<PeopleViewModel.Factory> factoryProvider16;
        public Provider<UserSwitcherViewModel.Factory> factoryProvider17;
        public Provider<TileLifecycleManager.Factory> factoryProvider18;
        public Provider<FooterActionsViewModel.Factory> factoryProvider19;
        public Provider<LockscreenShadeKeyguardTransitionController.Factory> factoryProvider2;
        public Provider<SplitShadeOverScroller.Factory> factoryProvider20;
        public Provider<WifiRepositoryImpl.Factory> factoryProvider21;
        public Provider<StatusBarIconController.TintedIconManager.Factory> factoryProvider22;
        public Provider<StatusBarIconController.DarkIconManager.Factory> factoryProvider23;
        public Provider<SplitShadeLockScreenOverScroller.Factory> factoryProvider3;
        public Provider<SingleShadeLockScreenOverScroller.Factory> factoryProvider4;
        public Provider<LockscreenShadeQsTransitionController.Factory> factoryProvider5;
        public Provider<GuestResumeSessionReceiver.ResetSessionDialog.Factory> factoryProvider6;
        public Provider<GuestResetOrExitSessionReceiver.ResetSessionDialog.Factory> factoryProvider7;
        public Provider<GuestResetOrExitSessionReceiver.ExitSessionDialog.Factory> factoryProvider8;
        public Provider<BrightnessSliderController.Factory> factoryProvider9;
        public Provider falsingCollectorImplProvider;
        public Provider<FalsingDataProvider> falsingDataProvider;
        public Provider<FalsingManagerProxy> falsingManagerProxyProvider;
        public Provider<FeatureFlagsDebug> featureFlagsDebugProvider;
        public Provider<FeatureFlagsDebugRestarter> featureFlagsDebugRestarterProvider;
        public Provider<FeatureFlagsDebugStartable> featureFlagsDebugStartableProvider;
        public Provider<FgsManagerControllerImpl> fgsManagerControllerImplProvider;
        public Provider<Files> filesProvider;
        public Provider<FlagCommand> flagCommandProvider;
        public Provider<FlashlightControllerImpl> flashlightControllerImplProvider;
        public Provider<FlashlightQuickAffordanceConfig> flashlightQuickAffordanceConfigProvider;
        public Provider<FlashlightTile> flashlightTileProvider;
        public Provider<FooterActionsController> footerActionsControllerProvider;
        public Provider<FooterActionsInteractorImpl> footerActionsInteractorImplProvider;
        public Provider<ForegroundServiceController> foregroundServiceControllerProvider;
        public Provider<ForegroundServiceNotificationListener> foregroundServiceNotificationListenerProvider;
        public Provider<ForegroundServicesDialog> foregroundServicesDialogProvider;
        public Provider<ForegroundServicesRepositoryImpl> foregroundServicesRepositoryImplProvider;
        public Provider<FragmentService.FragmentCreator.Factory> fragmentCreatorFactoryProvider;
        public Provider<FragmentService> fragmentServiceProvider;
        public Provider<FromAodTransitionInteractor> fromAodTransitionInteractorProvider;
        public Provider<FromBouncerTransitionInteractor> fromBouncerTransitionInteractorProvider;
        public Provider<FromDozingTransitionInteractor> fromDozingTransitionInteractorProvider;
        public Provider<FromDreamingTransitionInteractor> fromDreamingTransitionInteractorProvider;
        public Provider<FromGoneTransitionInteractor> fromGoneTransitionInteractorProvider;
        public Provider<FromLockscreenTransitionInteractor> fromLockscreenTransitionInteractorProvider;
        public Provider<FromOccludedTransitionInteractor> fromOccludedTransitionInteractorProvider;
        public Provider<FsiChromeRepo> fsiChromeRepoProvider;
        public Provider<FsiChromeViewBinder> fsiChromeViewBinderProvider;
        public Provider<FsiChromeViewModelFactory> fsiChromeViewModelFactoryProvider;
        public Provider<GarbageMonitor> garbageMonitorProvider;
        public Provider<ClockRegistry> getClockRegistryProvider;
        public Provider<GlobalActionsComponent> globalActionsComponentProvider;
        public Provider<GlobalActionsDialogLite> globalActionsDialogLiteProvider;
        public Provider<GlobalActionsImpl> globalActionsImplProvider;
        public Provider globalSettingsImplProvider;
        public Provider<GroupCoalescerLogger> groupCoalescerLoggerProvider;
        public Provider<GroupCoalescer> groupCoalescerProvider;
        public Provider<GroupExpansionManagerImpl> groupExpansionManagerImplProvider;
        public Provider<GuestResetOrExitSessionReceiver> guestResetOrExitSessionReceiverProvider;
        public Provider<GuestResumeSessionReceiver> guestResumeSessionReceiverProvider;
        public Provider<GuestSessionNotification> guestSessionNotificationProvider;
        public Provider<GuestUserInteractor> guestUserInteractorProvider;
        public Provider<HdmiCecSetMenuLanguageActivity> hdmiCecSetMenuLanguageActivityProvider;
        public Provider<HdmiCecSetMenuLanguageHelper> hdmiCecSetMenuLanguageHelperProvider;
        public Provider<HeadsUpManagerLogger> headsUpManagerLoggerProvider;
        public Provider<HeadsUpTile> headsUpTileProvider;
        public Provider<HeadsUpViewBinderLogger> headsUpViewBinderLoggerProvider;
        public Provider<HeadsUpViewBinder> headsUpViewBinderProvider;
        public Provider<HighPriorityProvider> highPriorityProvider;
        public Provider<HistoryTracker> historyTrackerProvider;
        public Provider<HomeControlsKeyguardQuickAffordanceConfig> homeControlsKeyguardQuickAffordanceConfigProvider;
        public Provider<HotspotControllerImpl> hotspotControllerImplProvider;
        public Provider<HotspotTile> hotspotTileProvider;
        public Provider<IconBuilder> iconBuilderProvider;
        public Provider<IconManager> iconManagerProvider;
        public Provider<ImageCaptureImpl> imageCaptureImplProvider;
        public Provider imageExporterProvider;
        public Provider imageTileSetProvider;
        public Provider<ImageWallpaper> imageWallpaperProvider;
        public Provider<InitController> initControllerProvider;
        public Provider<InstantAppNotifier> instantAppNotifierProvider;
        public Provider<InternetDialogController> internetDialogControllerProvider;
        public Provider<InternetDialogFactory> internetDialogFactoryProvider;
        public Provider<InternetTile> internetTileProvider;
        public Provider<Boolean> isPMLiteEnabledProvider;
        public Provider<Boolean> isReduceBrightColorsAvailableProvider;
        public Provider<KeyboardUI> keyboardUIProvider;
        public Provider<KeyguardBiometricLockoutLogger> keyguardBiometricLockoutLoggerProvider;
        public Provider<KeyguardBottomAreaInteractor> keyguardBottomAreaInteractorProvider;
        public Provider<KeyguardBottomAreaViewModel> keyguardBottomAreaViewModelProvider;
        public Provider<KeyguardBouncerComponent.Factory> keyguardBouncerComponentFactoryProvider;
        public Provider<KeyguardBouncerRepository> keyguardBouncerRepositoryProvider;
        public Provider<KeyguardBypassController> keyguardBypassControllerProvider;
        public Provider<KeyguardDismissUtil> keyguardDismissUtilProvider;
        public Provider<KeyguardDisplayManager> keyguardDisplayManagerProvider;
        public Provider<KeyguardIndicationController> keyguardIndicationControllerProvider;
        public Provider<KeyguardInteractor> keyguardInteractorProvider;
        public Provider<KeyguardLifecyclesDispatcher> keyguardLifecyclesDispatcherProvider;
        public Provider<KeyguardLiftController> keyguardLiftControllerProvider;
        public Provider<KeyguardLogger> keyguardLoggerProvider;
        public Provider<KeyguardMediaController> keyguardMediaControllerProvider;
        public Provider keyguardNotificationVisibilityProviderImplProvider;
        public Provider<KeyguardPreviewRendererFactory> keyguardPreviewRendererFactoryProvider;
        public KeyguardPreviewRenderer_Factory keyguardPreviewRendererProvider;
        public Provider<KeyguardQsUserSwitchComponent.Factory> keyguardQsUserSwitchComponentFactoryProvider;
        public Provider<KeyguardQuickAffordanceInteractor> keyguardQuickAffordanceInteractorProvider;
        public Provider<KeyguardQuickAffordanceLegacySettingSyncer> keyguardQuickAffordanceLegacySettingSyncerProvider;
        public Provider<KeyguardQuickAffordanceLocalUserSelectionManager> keyguardQuickAffordanceLocalUserSelectionManagerProvider;
        public Provider<KeyguardQuickAffordanceProviderClientFactoryImpl> keyguardQuickAffordanceProviderClientFactoryImplProvider;
        public Provider<KeyguardQuickAffordanceRegistryImpl> keyguardQuickAffordanceRegistryImplProvider;
        public Provider<KeyguardQuickAffordanceRemoteUserSelectionManager> keyguardQuickAffordanceRemoteUserSelectionManagerProvider;
        public Provider<KeyguardQuickAffordanceRepository> keyguardQuickAffordanceRepositoryProvider;
        public Provider<KeyguardRemotePreviewManager> keyguardRemotePreviewManagerProvider;
        public Provider<KeyguardRepositoryImpl> keyguardRepositoryImplProvider;
        public Provider<KeyguardSecurityModel> keyguardSecurityModelProvider;
        public Provider<KeyguardService> keyguardServiceProvider;
        public Provider<KeyguardStateControllerImpl> keyguardStateControllerImplProvider;
        public Provider<KeyguardStatusBarViewComponent.Factory> keyguardStatusBarViewComponentFactoryProvider;
        public Provider<KeyguardStatusViewComponent.Factory> keyguardStatusViewComponentFactoryProvider;
        public Provider<KeyguardTransitionAuditLogger> keyguardTransitionAuditLoggerProvider;
        public Provider<KeyguardTransitionCoreStartable> keyguardTransitionCoreStartableProvider;
        public Provider<KeyguardTransitionInteractor> keyguardTransitionInteractorProvider;
        public Provider<KeyguardTransitionRepositoryImpl> keyguardTransitionRepositoryImplProvider;
        public Provider<KeyguardUnlockAnimationController> keyguardUnlockAnimationControllerProvider;
        public Provider<KeyguardUpdateMonitorLogger> keyguardUpdateMonitorLoggerProvider;
        public Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;
        public Provider<KeyguardUserSwitcherComponent.Factory> keyguardUserSwitcherComponentFactoryProvider;
        public Provider<LSShadeTransitionLogger> lSShadeTransitionLoggerProvider;
        public Provider<LatencyTester> latencyTesterProvider;
        public Provider<LaunchConversationActivity> launchConversationActivityProvider;
        public Provider<LaunchFullScreenIntentProvider> launchFullScreenIntentProvider;
        public Provider<LaunchNoteTaskActivity> launchNoteTaskActivityProvider;
        public Provider<LeakReporter> leakReporterProvider;
        public Provider<LightBarController> lightBarControllerProvider;
        public LightBarTransitionsController_Factory lightBarTransitionsControllerProvider;
        public Provider<LightRevealScrimInteractor> lightRevealScrimInteractorProvider;
        public Provider<LightRevealScrimRepositoryImpl> lightRevealScrimRepositoryImplProvider;
        public Provider<LightRevealScrimViewModel> lightRevealScrimViewModelProvider;
        public Provider<LiveDisplayTile> liveDisplayTileProvider;
        public Provider<LocalMediaManagerFactory> localMediaManagerFactoryProvider;
        public Provider<LocationControllerImpl> locationControllerImplProvider;
        public Provider<LocationTile> locationTileProvider;
        public Provider<LockscreenGestureLogger> lockscreenGestureLoggerProvider;
        public LockscreenShadeKeyguardTransitionController_Factory lockscreenShadeKeyguardTransitionControllerProvider;
        public LockscreenShadeQsTransitionController_Factory lockscreenShadeQsTransitionControllerProvider;
        public Provider<LockscreenShadeScrimTransitionController> lockscreenShadeScrimTransitionControllerProvider;
        public Provider<LockscreenShadeTransitionController> lockscreenShadeTransitionControllerProvider;
        public Provider<LockscreenSmartspaceController> lockscreenSmartspaceControllerProvider;
        public Provider<LockscreenWallpaper> lockscreenWallpaperProvider;
        public Provider<LogBufferEulogizer> logBufferEulogizerProvider;
        public Provider<LogBufferFactory> logBufferFactoryProvider;
        public Provider<LogBufferFreezer> logBufferFreezerProvider;
        public Provider<LongScreenshotActivity> longScreenshotActivityProvider;
        public Provider<LongScreenshotData> longScreenshotDataProvider;
        public Provider<LongTapClassifier> longTapClassifierProvider;
        public Provider<CoroutineDispatcher> mainDispatcherProvider;
        public Provider<ManagedProfileControllerImpl> managedProfileControllerImplProvider;
        public Provider<Map<Class<?>, Provider<Activity>>> mapOfClassOfAndProviderOfActivityProvider;
        public Provider<Map<Class<?>, Provider<BroadcastReceiver>>> mapOfClassOfAndProviderOfBroadcastReceiverProvider;
        public Provider<Map<Class<?>, Provider<CoreStartable>>> mapOfClassOfAndProviderOfCoreStartableProvider;
        public Provider<Map<Class<?>, Provider<RecentsImplementation>>> mapOfClassOfAndProviderOfRecentsImplementationProvider;
        public Provider<Map<Class<?>, Provider<Service>>> mapOfClassOfAndProviderOfServiceProvider;
        public Provider<MediaArtworkProcessor> mediaArtworkProcessorProvider;
        public Provider<MediaBrowserFactory> mediaBrowserFactoryProvider;
        public Provider<MediaCarouselControllerLogger> mediaCarouselControllerLoggerProvider;
        public Provider<MediaCarouselController> mediaCarouselControllerProvider;
        public Provider<MediaContainerController> mediaContainerControllerProvider;
        public Provider<MediaControlPanel> mediaControlPanelProvider;
        public Provider<MediaControllerFactory> mediaControllerFactoryProvider;
        public Provider<MediaDataFilter> mediaDataFilterProvider;
        public Provider<MediaDataManager> mediaDataManagerProvider;
        public Provider<MediaDeviceManager> mediaDeviceManagerProvider;
        public Provider<MediaFeatureFlag> mediaFeatureFlagProvider;
        public Provider<MediaFlags> mediaFlagsProvider;
        public Provider<MediaHierarchyManager> mediaHierarchyManagerProvider;
        public Provider<MediaHostStatesManager> mediaHostStatesManagerProvider;
        public Provider<MediaMuteAwaitConnectionCli> mediaMuteAwaitConnectionCliProvider;
        public Provider<MediaMuteAwaitConnectionManagerFactory> mediaMuteAwaitConnectionManagerFactoryProvider;
        public Provider<MediaMuteAwaitLogger> mediaMuteAwaitLoggerProvider;
        public Provider<MediaOutputBroadcastDialogFactory> mediaOutputBroadcastDialogFactoryProvider;
        public Provider<MediaOutputDialogFactory> mediaOutputDialogFactoryProvider;
        public Provider<MediaOutputDialogReceiver> mediaOutputDialogReceiverProvider;
        public Provider<MediaProjectionAppSelectorActivity> mediaProjectionAppSelectorActivityProvider;
        public Provider<MediaProjectionAppSelectorComponent.Factory> mediaProjectionAppSelectorComponentFactoryProvider;
        public Provider<MediaResumeListener> mediaResumeListenerProvider;
        public Provider<MediaSessionBasedFilter> mediaSessionBasedFilterProvider;
        public Provider<MediaTimeoutListener> mediaTimeoutListenerProvider;
        public Provider<MediaTimeoutLogger> mediaTimeoutLoggerProvider;
        public Provider<MediaTttChipControllerReceiver> mediaTttChipControllerReceiverProvider;
        public Provider<MediaTttCommandLineHelper> mediaTttCommandLineHelperProvider;
        public Provider<MediaTttFlags> mediaTttFlagsProvider;
        public Provider<MediaTttReceiverUiEventLogger> mediaTttReceiverUiEventLoggerProvider;
        public Provider<MediaTttSenderCoordinator> mediaTttSenderCoordinatorProvider;
        public Provider<MediaTttSenderUiEventLogger> mediaTttSenderUiEventLoggerProvider;
        public Provider<MediaUiEventLogger> mediaUiEventLoggerProvider;
        public Provider<MediaViewController> mediaViewControllerProvider;
        public Provider<MediaViewLogger> mediaViewLoggerProvider;
        public Provider<GarbageMonitor.MemoryTile> memoryTileProvider;
        public Provider<MicrophoneToggleTile> microphoneToggleTileProvider;
        public Provider<MobileConnectionsRepositoryImpl> mobileConnectionsRepositoryImplProvider;
        public Provider<MobileContextProvider> mobileContextProvider;
        public Provider<MobileIconsInteractorImpl> mobileIconsInteractorImplProvider;
        public Provider<MobileRepositorySwitcher> mobileRepositorySwitcherProvider;
        public Provider<MobileSignalControllerFactory> mobileSignalControllerFactoryProvider;
        public Provider<MobileUiAdapter> mobileUiAdapterProvider;
        public Provider<ModeSwitchesController> modeSwitchesControllerProvider;
        public Provider<MotionToolStartable> motionToolStartableProvider;
        public Provider<Set<FalsingClassifier>> namedSetOfFalsingClassifierProvider;
        public Provider<NavBarHelper> navBarHelperProvider;
        public Provider<NavigationBarComponent.Factory> navigationBarComponentFactoryProvider;
        public Provider<NavigationBarController> navigationBarControllerProvider;
        public Provider<NavigationBarEdgePanel> navigationBarEdgePanelProvider;
        public Provider<NavigationModeController> navigationModeControllerProvider;
        public Provider<NearbyMediaDevicesLogger> nearbyMediaDevicesLoggerProvider;
        public Provider<NearbyMediaDevicesManager> nearbyMediaDevicesManagerProvider;
        public Provider<NetworkControllerImpl> networkControllerImplProvider;
        public Provider<KeyguardViewMediator> newKeyguardViewMediatorProvider;
        public Provider<NextAlarmControllerImpl> nextAlarmControllerImplProvider;
        public Provider<NfcTile> nfcTileProvider;
        public Provider<NightDisplayTile> nightDisplayTileProvider;
        public Provider<NodeSpecBuilderLogger> nodeSpecBuilderLoggerProvider;
        public Provider<NoteTaskController> noteTaskControllerProvider;
        public Provider<NoteTaskInitializer> noteTaskInitializerProvider;
        public Provider<NoteTaskIntentResolver> noteTaskIntentResolverProvider;
        public Provider<NotifBindPipelineInitializer> notifBindPipelineInitializerProvider;
        public Provider<NotifBindPipelineLogger> notifBindPipelineLoggerProvider;
        public Provider<NotifBindPipeline> notifBindPipelineProvider;
        public Provider<NotifCollectionLogger> notifCollectionLoggerProvider;
        public Provider<NotifCollection> notifCollectionProvider;
        public Provider<NotifCoordinators> notifCoordinatorsProvider;
        public Provider<NotifInflaterImpl> notifInflaterImplProvider;
        public Provider<NotifInflationErrorManager> notifInflationErrorManagerProvider;
        public Provider<NotifLiveDataStoreImpl> notifLiveDataStoreImplProvider;
        public Provider notifPipelineChoreographerImplProvider;
        public Provider<NotifPipelineFlags> notifPipelineFlagsProvider;
        public Provider<NotifPipelineInitializer> notifPipelineInitializerProvider;
        public Provider<NotifPipeline> notifPipelineProvider;
        public Provider<NotifRemoteViewCacheImpl> notifRemoteViewCacheImplProvider;
        public Provider<NotifUiAdjustmentProvider> notifUiAdjustmentProvider;
        public Provider<NotifViewBarn> notifViewBarnProvider;
        public Provider<NotificationChannels> notificationChannelsProvider;
        public Provider<NotificationClickNotifier> notificationClickNotifierProvider;
        public Provider<NotificationClickerLogger> notificationClickerLoggerProvider;
        public Provider<NotificationContentInflater> notificationContentInflaterProvider;
        public Provider<NotificationIconAreaController> notificationIconAreaControllerProvider;
        public Provider<NotificationInsetsImpl> notificationInsetsImplProvider;
        public Provider<NotificationInteractionTracker> notificationInteractionTrackerProvider;
        public Provider<NotificationInterruptLogger> notificationInterruptLoggerProvider;
        public Provider<NotificationInterruptStateProviderImpl> notificationInterruptStateProviderImplProvider;
        public Provider<NotificationListener> notificationListenerProvider;
        public Provider<NotificationListenerWithPlugins> notificationListenerWithPluginsProvider;
        public Provider<NotificationLockscreenUserManagerImpl> notificationLockscreenUserManagerImplProvider;
        public Provider<NotificationMemoryDumper> notificationMemoryDumperProvider;
        public Provider<NotificationMemoryLogger> notificationMemoryLoggerProvider;
        public Provider<NotificationMemoryMonitor> notificationMemoryMonitorProvider;
        public Provider<NotificationPersonExtractorPluginBoundary> notificationPersonExtractorPluginBoundaryProvider;
        public Provider<NotificationRoundnessLogger> notificationRoundnessLoggerProvider;
        public Provider<NotificationRoundnessManager> notificationRoundnessManagerProvider;
        public Provider<NotificationRowBinderImpl> notificationRowBinderImplProvider;
        public Provider<NotificationSectionsFeatureManager> notificationSectionsFeatureManagerProvider;
        public Provider<NotificationSectionsManager> notificationSectionsManagerProvider;
        public Provider<NotificationShadeDepthController> notificationShadeDepthControllerProvider;
        public Provider<NotificationShadeWindowControllerImpl> notificationShadeWindowControllerImplProvider;
        public Provider<NotificationShelfComponent.Builder> notificationShelfComponentBuilderProvider;
        public Provider<NotificationStackSizeCalculator> notificationStackSizeCalculatorProvider;
        public Provider<NotificationTargetsHelper> notificationTargetsHelperProvider;
        public Provider<NotificationVisibilityProviderImpl> notificationVisibilityProviderImplProvider;
        public Provider<NotificationWakeUpCoordinatorLogger> notificationWakeUpCoordinatorLoggerProvider;
        public Provider<NotificationWakeUpCoordinator> notificationWakeUpCoordinatorProvider;
        public Provider<NotificationsControllerImpl> notificationsControllerImplProvider;
        public Provider<NotificationsControllerStub> notificationsControllerStubProvider;
        public Provider<OccludedToLockscreenTransitionViewModel> occludedToLockscreenTransitionViewModelProvider;
        public Provider<OnUserInteractionCallbackImpl> onUserInteractionCallbackImplProvider;
        public Provider<OneHandedModeTile> oneHandedModeTileProvider;
        public Provider<OngoingCallFlags> ongoingCallFlagsProvider;
        public Provider<OngoingCallLogger> ongoingCallLoggerProvider;
        public Provider<Optional<BcSmartspaceDataPlugin>> optionalOfBcSmartspaceDataPluginProvider;
        public Provider<Optional<CentralSurfaces>> optionalOfCentralSurfacesProvider;
        public Provider<Optional<ControlsFavoritePersistenceWrapper>> optionalOfControlsFavoritePersistenceWrapperProvider;
        public Provider<Optional<ControlsTileResourceConfiguration>> optionalOfControlsTileResourceConfigurationProvider;
        public Provider<Optional<Provider<AlternateUdfpsTouchProvider>>> optionalOfProviderOfAlternateUdfpsTouchProvider;
        public Provider<Optional<Recents>> optionalOfRecentsProvider;
        public Provider<OverviewProxyRecentsImpl> overviewProxyRecentsImplProvider;
        public Provider<OverviewProxyService> overviewProxyServiceProvider;
        public Provider<PackageManagerAdapter> packageManagerAdapterProvider;
        public Provider<PendingRemovalStore> pendingRemovalStoreProvider;
        public Provider<PeopleNotificationIdentifierImpl> peopleNotificationIdentifierImplProvider;
        public Provider<PeopleSpaceActivity> peopleSpaceActivityProvider;
        public Provider<PeopleSpaceWidgetManager> peopleSpaceWidgetManagerProvider;
        public Provider<PeopleSpaceWidgetPinnedReceiver> peopleSpaceWidgetPinnedReceiverProvider;
        public Provider<PeopleSpaceWidgetProvider> peopleSpaceWidgetProvider;
        public Provider<PeopleTileRepositoryImpl> peopleTileRepositoryImplProvider;
        public Provider<PeopleWidgetRepositoryImpl> peopleWidgetRepositoryImplProvider;
        public Provider<PhoneStateMonitor> phoneStateMonitorProvider;
        public Provider<PhoneStatusBarPolicy> phoneStatusBarPolicyProvider;
        public Provider pointerCountClassifierProvider;
        public Provider postureDependentProximitySensorProvider;
        public Provider<PowerInteractor> powerInteractorProvider;
        public Provider<PowerNotificationWarnings> powerNotificationWarningsProvider;
        public Provider<PowerRepositoryImpl> powerRepositoryImplProvider;
        public Provider<PowerShareTile> powerShareTileProvider;
        public Provider<PowerUI> powerUIProvider;
        public Provider<PrimaryBouncerCallbackInteractor> primaryBouncerCallbackInteractorProvider;
        public Provider<PrimaryBouncerInteractor> primaryBouncerInteractorProvider;
        public Provider<PrivacyConfig> privacyConfigProvider;
        public Provider<PrivacyDialogController> privacyDialogControllerProvider;
        public Provider<PrivacyDotDecorProviderFactory> privacyDotDecorProviderFactoryProvider;
        public Provider<PrivacyDotViewController> privacyDotViewControllerProvider;
        public Provider<PrivacyItemController> privacyItemControllerProvider;
        public Provider<PrivacyLogger> privacyLoggerProvider;
        public Provider<ProtoTracer> protoTracerProvider;
        public Provider<AccessPointControllerImpl> provideAccessPointControllerImplProvider;
        public Provider<ActivityLaunchAnimator> provideActivityLaunchAnimatorProvider;
        public Provider<ActivityManagerWrapper> provideActivityManagerWrapperProvider;
        public Provider<ActivityStarter> provideActivityStarterProvider;
        public Provider<TableLogBuffer> provideAirplaneTableLogBufferProvider;
        public Provider<Boolean> provideAllowNotificationLongPressProvider;
        public Provider<AssistUtils> provideAssistUtilsProvider;
        public Provider<DeviceStateRotationLockSettingsManager> provideAutoRotateSettingsManagerProvider;
        public Provider<AutoTileManager> provideAutoTileManagerProvider;
        public Provider<DelayableExecutor> provideBackgroundDelayableExecutorProvider;
        public Provider<Executor> provideBackgroundExecutorProvider;
        public Provider<RepeatableExecutor> provideBackgroundRepeatableExecutorProvider;
        public Provider<BatteryController> provideBatteryControllerProvider;
        public Provider<Handler> provideBgHandlerProvider;
        public Provider<Looper> provideBgLooperProvider;
        public Provider<LogBuffer> provideBiometricLogBufferProvider;
        public Provider<TableLogBuffer> provideBouncerLogBufferProvider;
        public Provider<LogBuffer> provideBroadcastDispatcherLogBufferProvider;
        public Provider<Executor> provideBroadcastRunningExecutorProvider;
        public Provider<Looper> provideBroadcastRunningLooperProvider;
        public Provider<Optional<BubblesManager>> provideBubblesManagerProvider;
        public Provider<LogBuffer> provideChipbarLogBufferProvider;
        public Provider<ClipboardOverlayView> provideClipboardOverlayViewProvider;
        public Provider provideClockInfoListProvider;
        public Provider<LogBuffer> provideCollapsedSbFragmentLogBufferProvider;
        public Provider<CommandQueue> provideCommandQueueProvider;
        public Provider<DataSaverController> provideDataSaverControllerProvider;
        public Provider<DdmHandleMotionTool> provideDdmHandleMotionToolProvider;
        public Provider<DelayableExecutor> provideDelayableExecutorProvider;
        public Provider<DemoModeController> provideDemoModeControllerProvider;
        public Provider<DevicePolicyManagerWrapper> provideDevicePolicyManagerWrapperProvider;
        public Provider<DialogLaunchAnimator> provideDialogLaunchAnimatorProvider;
        public Provider<LogBuffer> provideDozeLogBufferProvider;
        public Provider<Executor> provideExecutorProvider;
        public Provider<FlagManager> provideFlagManagerProvider;
        public Provider<GroupMembershipManager> provideGroupMembershipManagerProvider;
        public Provider<HeadsUpManagerPhone> provideHeadsUpManagerPhoneProvider;
        public Provider<IndividualSensorPrivacyController> provideIndividualSensorPrivacyControllerProvider;
        public Provider<Boolean> provideIsNoteTaskEnabledProvider;
        public Provider<LogBuffer> provideKeyguardClockLogProvider;
        public Provider<LogBuffer> provideKeyguardLogBufferProvider;
        public Provider<LogBuffer> provideKeyguardUpdateMonitorLogBufferProvider;
        public Provider<LogBuffer> provideLSShadeTransitionControllerBufferProvider;
        public Provider<String> provideLauncherPackageProvider;
        public Provider<String> provideLeakReportEmailProvider;
        public Provider<LocalBluetoothManager> provideLocalBluetoothControllerProvider;
        public Provider<LogcatEchoTracker> provideLogcatEchoTrackerProvider;
        public Provider<Executor> provideLongRunningExecutorProvider;
        public Provider<Looper> provideLongRunningLooperProvider;
        public Provider<LogBuffer> provideMediaBrowserBufferProvider;
        public Provider<LogBuffer> provideMediaCarouselControllerBufferProvider;
        public Provider<LogBuffer> provideMediaMuteAwaitLogBufferProvider;
        public Provider<LogBuffer> provideMediaTttReceiverLogBufferProvider;
        public Provider<LogBuffer> provideMediaTttSenderLogBufferProvider;
        public Provider<LogBuffer> provideMediaViewLogBufferProvider;
        public Provider<MotionToolManager> provideMotionToolManagerProvider;
        public Provider<LogBuffer> provideNearbyMediaDevicesLogBufferProvider;
        public Provider<NightDisplayListener> provideNightDisplayListenerProvider;
        public Provider<LogBuffer> provideNotifInteractionLogBufferProvider;
        public Provider<NotifRemoteViewCache> provideNotifRemoteViewCacheProvider;
        public Provider<NotificationGutsManager> provideNotificationGutsManagerProvider;
        public Provider<LogBuffer> provideNotificationHeadsUpLogBufferProvider;
        public Provider<LogBuffer> provideNotificationInterruptLogBufferProvider;
        public Provider<NotificationLogger> provideNotificationLoggerProvider;
        public Provider<NotificationMediaManager> provideNotificationMediaManagerProvider;
        public Provider<NotificationPanelLogger> provideNotificationPanelLoggerProvider;
        public Provider<NotificationRemoteInputManager> provideNotificationRemoteInputManagerProvider;
        public Provider<LogBuffer> provideNotificationRenderLogBufferProvider;
        public Provider<NotificationsController> provideNotificationsControllerProvider;
        public Provider<LogBuffer> provideNotificationsLogBufferProvider;
        public Provider<OngoingCallController> provideOngoingCallControllerProvider;
        public Provider<Optional<KeyguardManager>> provideOptionalKeyguardManagerProvider;
        public Provider<Optional<UserManager>> provideOptionalUserManagerProvider;
        public Provider<ThresholdSensor[]> providePostureToProximitySensorMappingProvider;
        public Provider<ThresholdSensor[]> providePostureToSecondaryProximitySensorMappingProvider;
        public Provider<ThresholdSensor> providePrimaryProximitySensorProvider;
        public Provider<LogBuffer> providePrivacyLogBufferProvider;
        public Provider<ProximityCheck> provideProximityCheckProvider;
        public Provider<ProximitySensor> provideProximitySensorProvider;
        public Provider<LogBuffer> provideQSFragmentDisableLogBufferProvider;
        public Provider<QuickAccessWalletClient> provideQuickAccessWalletClientProvider;
        public Provider<LogBuffer> provideQuickSettingsLogBufferProvider;
        public Provider<RealWifiRepository> provideRealWifiRepositoryProvider;
        public Provider<RecentsImplementation> provideRecentsImplProvider;
        public Provider<Recents> provideRecentsProvider;
        public Provider<ThresholdSensor> provideSecondaryProximitySensorProvider;
        public Provider<SensorPrivacyController> provideSensorPrivacyControllerProvider;
        public Provider<LogBuffer> provideShadeHeightLogBufferProvider;
        public Provider<LogBuffer> provideShadeLogBufferProvider;
        public Provider<LogBuffer> provideShadeWindowLogBufferProvider;
        public Provider<SmartReplyController> provideSmartReplyControllerProvider;
        public Provider<LogBuffer> provideStatusBarConnectivityBufferProvider;
        public Provider<StatusBarIconList> provideStatusBarIconListProvider;
        public Provider<LogBuffer> provideStatusBarNetworkControllerBufferProvider;
        public Provider<LogBuffer> provideSwipeAwayGestureLogBufferProvider;
        public Provider<Optional<SysUIUnfoldComponent>> provideSysUIUnfoldComponentProvider;
        public Provider<SysUiState> provideSysUiStateProvider;
        public Provider<String> provideThemePickerPackageProvider;
        public Provider<Handler> provideTimeTickHandlerProvider;
        public Provider<LogBuffer> provideToastLogBufferProvider;
        public Provider<UserTracker> provideUserTrackerProvider;
        public Provider<VolumeDialog> provideVolumeDialogProvider;
        public Provider<TableLogBuffer> provideWifiTableLogBufferProvider;
        public Provider<Context> provideWindowContextProvider;
        public Provider<LogBuffer> providerBluetoothLogBufferProvider;
        public Provider<SectionHeaderController> providesAlertingHeaderControllerProvider;
        public Provider<NodeController> providesAlertingHeaderNodeControllerProvider;
        public Provider<SectionHeaderControllerSubcomponent> providesAlertingHeaderSubcomponentProvider;
        public Provider<MessageRouter> providesBackgroundMessageRouterProvider;
        public Provider<Set<FalsingClassifier>> providesBrightLineGestureClassifiersProvider;
        public Provider<Boolean> providesControlsFeatureEnabledProvider;
        public Provider<String[]> providesDeviceStateRotationLockDefaultsProvider;
        public Provider<Float> providesDoubleTapTouchSlopProvider;
        public Provider<Boolean> providesDreamOnlyEnabledForDockUserProvider;
        public Provider<Boolean> providesDreamOverlayEnabledProvider;
        public Provider<Optional<DreamOverlayNotificationCountProvider>> providesDreamOverlayNotificationCountProvider;
        public Provider<ComponentName> providesDreamOverlayServiceProvider;
        public Provider<Boolean> providesDreamSupportedProvider;
        public Provider<SectionHeaderController> providesIncomingHeaderControllerProvider;
        public Provider<NodeController> providesIncomingHeaderNodeControllerProvider;
        public Provider<SectionHeaderControllerSubcomponent> providesIncomingHeaderSubcomponentProvider;
        public Provider<MediaHost> providesKeyguardMediaHostProvider;
        public Provider<LeakDetector> providesLeakDetectorProvider;
        public Provider<Float> providesLongTapTouchSlopProvider;
        public Provider<ComponentName> providesLowLightDreamComponentProvider;
        public Provider<MessageRouter> providesMainMessageRouterProvider;
        public Provider<Optional<MediaMuteAwaitConnectionCli>> providesMediaMuteAwaitConnectionCliProvider;
        public Provider<LogBuffer> providesMediaTimeoutListenerLogBufferProvider;
        public Provider<MediaTttLogger<ChipReceiverInfo>> providesMediaTttReceiverLoggerProvider;
        public Provider<MediaTttLogger<ChipbarInfo>> providesMediaTttSenderLoggerProvider;
        public Provider<Optional<NearbyMediaDevicesManager>> providesNearbyMediaDevicesManagerProvider;
        public Provider<OverlapDetector> providesOverlapDetectorProvider;
        public Provider<SectionHeaderController> providesPeopleHeaderControllerProvider;
        public Provider<NodeController> providesPeopleHeaderNodeControllerProvider;
        public Provider<SectionHeaderControllerSubcomponent> providesPeopleHeaderSubcomponentProvider;
        public Provider<Executor> providesPluginExecutorProvider;
        public Provider<MediaHost> providesQSMediaHostProvider;
        public Provider<MediaHost> providesQuickQSMediaHostProvider;
        public Provider<SectionHeaderController> providesSilentHeaderControllerProvider;
        public Provider<NodeController> providesSilentHeaderNodeControllerProvider;
        public Provider<SectionHeaderControllerSubcomponent> providesSilentHeaderSubcomponentProvider;
        public Provider<Float> providesSingleTapTouchSlopProvider;
        public Provider<StatusBarWindowView> providesStatusBarWindowViewProvider;
        public Provider<ViewMediatorCallback> providesViewMediatorCallbackProvider;
        public Provider proximityClassifierProvider;
        public Provider proximitySensorImplProvider;
        public Provider<PulseExpansionHandler> pulseExpansionHandlerProvider;
        public Provider<QRCodeScannerController> qRCodeScannerControllerProvider;
        public Provider<QRCodeScannerTile> qRCodeScannerTileProvider;
        public Provider<QSFactoryImpl> qSFactoryImplProvider;
        public Provider<QSLogger> qSLoggerProvider;
        public Provider<QSSecurityFooterUtils> qSSecurityFooterUtilsProvider;
        public Provider<QSTileHost> qSTileHostProvider;
        public Provider<QrCodeScannerKeyguardQuickAffordanceConfig> qrCodeScannerKeyguardQuickAffordanceConfigProvider;
        public Provider<QsFrameTranslateImpl> qsFrameTranslateImplProvider;
        public Provider<QuickAccessWalletController> quickAccessWalletControllerProvider;
        public Provider<QuickAccessWalletKeyguardQuickAffordanceConfig> quickAccessWalletKeyguardQuickAffordanceConfigProvider;
        public Provider<QuickAccessWalletTile> quickAccessWalletTileProvider;
        public Provider<Set<KeyguardQuickAffordanceConfig>> quickAffordanceConfigsProvider;
        public Provider<ReadingModeTile> readingModeTileProvider;
        public Provider<RearDisplayDialogController> rearDisplayDialogControllerProvider;
        public Provider<RecordingController> recordingControllerProvider;
        public Provider<RecordingService> recordingServiceProvider;
        public Provider<ReduceBrightColorsController> reduceBrightColorsControllerProvider;
        public Provider<ReduceBrightColorsTile> reduceBrightColorsTileProvider;
        public Provider<RefreshUsersScheduler> refreshUsersSchedulerProvider;
        public Provider<RemoteInputNotificationRebuilder> remoteInputNotificationRebuilderProvider;
        public Provider<RemoteInputQuickSettingsDisabler> remoteInputQuickSettingsDisablerProvider;
        public Provider<RemoteInputUriController> remoteInputUriControllerProvider;
        public Provider<RenderStageManager> renderStageManagerProvider;
        public Provider<RequestProcessor> requestProcessorProvider;
        public C0052GuestResumeSessionReceiver_ResetSessionDialog_Factory resetSessionDialogProvider;
        public C0051GuestResetOrExitSessionReceiver_ResetSessionDialog_Factory resetSessionDialogProvider2;
        public Provider<ResumeMediaBrowserFactory> resumeMediaBrowserFactoryProvider;
        public Provider<ResumeMediaBrowserLogger> resumeMediaBrowserLoggerProvider;
        public Provider<RingerModeTrackerImpl> ringerModeTrackerImplProvider;
        public Provider<RingtonePlayer> ringtonePlayerProvider;
        public Provider<RotationLockControllerImpl> rotationLockControllerImplProvider;
        public Provider<RotationLockTile> rotationLockTileProvider;
        public Provider<RotationPolicyWrapperImpl> rotationPolicyWrapperImplProvider;
        public Provider<RowContentBindStageLogger> rowContentBindStageLoggerProvider;
        public Provider<RowContentBindStage> rowContentBindStageProvider;
        public Provider<SafetyController> safetyControllerProvider;
        public Provider<ScreenDecorations> screenDecorationsProvider;
        public Provider<ScreenOffAnimationController> screenOffAnimationControllerProvider;
        public Provider<ScreenOnCoordinator> screenOnCoordinatorProvider;
        public Provider<ScreenPinningRequest> screenPinningRequestProvider;
        public Provider<ScreenRecordTile> screenRecordTileProvider;
        public Provider<ScreenshotController> screenshotControllerProvider;
        public Provider<ScreenshotNotificationsController> screenshotNotificationsControllerProvider;
        public Provider<ScreenshotPolicyImpl> screenshotPolicyImplProvider;
        public Provider<ScreenshotProxyService> screenshotProxyServiceProvider;
        public Provider<ScreenshotSmartActions> screenshotSmartActionsProvider;
        public Provider<ScrimController> scrimControllerProvider;
        public Provider<ScrimShadeTransitionController> scrimShadeTransitionControllerProvider;
        public Provider<ScrollCaptureClient> scrollCaptureClientProvider;
        public Provider<ScrollCaptureController> scrollCaptureControllerProvider;
        public Provider<SectionHeaderControllerSubcomponent.Builder> sectionHeaderControllerSubcomponentBuilderProvider;
        public Provider<SectionHeaderVisibilityProvider> sectionHeaderVisibilityProvider;
        public Provider<SectionStyleProvider> sectionStyleProvider;
        public Provider secureSettingsImplProvider;
        public Provider<SecurityControllerImpl> securityControllerImplProvider;
        public Provider<SecurityRepositoryImpl> securityRepositoryImplProvider;
        public Provider<SeekBarViewModel> seekBarViewModelProvider;
        public Provider<SeenNotificationsProviderImpl> seenNotificationsProviderImplProvider;
        public Provider<SensorUseStartedActivity> sensorUseStartedActivityProvider;
        public Provider<GarbageMonitor.Service> serviceProvider;
        public Provider<SessionTracker> sessionTrackerProvider;
        public Provider<Optional<BackAnimation>> setBackAnimationProvider;
        public Provider<Optional<Bubbles>> setBubblesProvider;
        public Provider<Optional<DesktopMode>> setDesktopModeProvider;
        public Provider<Optional<DisplayAreaHelper>> setDisplayAreaHelperProvider;
        public Provider<Set<KeyguardQuickAffordanceConfig>> setOfKeyguardQuickAffordanceConfigProvider;
        public Provider<Set<PrivacyItemMonitor>> setOfPrivacyItemMonitorProvider;
        public Provider<Set<TransitionInteractor>> setOfTransitionInteractorProvider;
        public Provider<Optional<OneHanded>> setOneHandedProvider;
        public Provider<Optional<Pip>> setPipProvider;
        public Provider<Optional<RecentTasks>> setRecentTasksProvider;
        public Provider<ShellInterface> setShellProvider;
        public Provider<Optional<SplitScreen>> setSplitScreenProvider;
        public Provider<Optional<StartingSurface>> setStartingSurfaceProvider;
        public Provider<Optional<TaskViewFactory>> setTaskViewFactoryProvider;
        public Provider<ShellTransitions> setTransitionsProvider;
        public Provider<ShadeControllerImpl> shadeControllerImplProvider;
        public Provider<ShadeEventCoordinatorLogger> shadeEventCoordinatorLoggerProvider;
        public Provider<ShadeEventCoordinator> shadeEventCoordinatorProvider;
        public Provider<ShadeExpansionStateManager> shadeExpansionStateManagerProvider;
        public Provider<ShadeListBuilderLogger> shadeListBuilderLoggerProvider;
        public Provider<ShadeListBuilder> shadeListBuilderProvider;
        public Provider<ShadeRepositoryImpl> shadeRepositoryImplProvider;
        public Provider<ShadeTransitionController> shadeTransitionControllerProvider;
        public Provider<ShadeViewDifferLogger> shadeViewDifferLoggerProvider;
        public Provider<ShadeViewManagerFactory> shadeViewManagerFactoryProvider;
        public ShadeViewManager_Factory shadeViewManagerProvider;
        public Provider<ShadeWindowLogger> shadeWindowLoggerProvider;
        public Provider<ShortcutKeyDispatcher> shortcutKeyDispatcherProvider;
        public Provider<SideFpsController> sideFpsControllerProvider;
        public Provider<SinglePointerTouchProcessor> singlePointerTouchProcessorProvider;
        public SingleShadeLockScreenOverScroller_Factory singleShadeLockScreenOverScrollerProvider;
        public Provider<SingleTapClassifier> singleTapClassifierProvider;
        public Provider<SliceBroadcastRelayHandler> sliceBroadcastRelayHandlerProvider;
        public Provider<SmartActionInflaterImpl> smartActionInflaterImplProvider;
        public Provider<SmartActionsReceiver> smartActionsReceiverProvider;
        public Provider<SmartReplyConstants> smartReplyConstantsProvider;
        public Provider<SmartReplyInflaterImpl> smartReplyInflaterImplProvider;
        public Provider<SmartReplyStateInflaterImpl> smartReplyStateInflaterImplProvider;
        public SplitShadeLockScreenOverScroller_Factory splitShadeLockScreenOverScrollerProvider;
        public SplitShadeOverScroller_Factory splitShadeOverScrollerProvider;
        public Provider<StatusBarContentInsetsProvider> statusBarContentInsetsProvider;
        public Provider<StatusBarHideIconsForBouncerManager> statusBarHideIconsForBouncerManagerProvider;
        public Provider<StatusBarIconControllerImpl> statusBarIconControllerImplProvider;
        public Provider<StatusBarKeyguardViewManager> statusBarKeyguardViewManagerProvider;
        public Provider<StatusBarLocationPublisher> statusBarLocationPublisherProvider;
        public Provider<StatusBarPipelineFlags> statusBarPipelineFlagsProvider;
        public Provider<StatusBarRemoteInputCallback> statusBarRemoteInputCallbackProvider;
        public Provider<StatusBarSignalPolicy> statusBarSignalPolicyProvider;
        public Provider<StatusBarStateControllerImpl> statusBarStateControllerImplProvider;
        public Provider<StatusBarTouchableRegionManager> statusBarTouchableRegionManagerProvider;
        public Provider<StatusBarWindowController> statusBarWindowControllerProvider;
        public Provider<StatusBarWindowStateController> statusBarWindowStateControllerProvider;
        public Provider<StorageNotification> storageNotificationProvider;
        public Provider<StylusManager> stylusManagerProvider;
        public Provider<StylusUsiPowerStartable> stylusUsiPowerStartableProvider;
        public Provider<StylusUsiPowerUI> stylusUsiPowerUIProvider;
        public Provider<QSCarrierGroupController.SubscriptionManagerSlotIndexResolver> subscriptionManagerSlotIndexResolverProvider;
        public Provider<SwipeStatusBarAwayGestureHandler> swipeStatusBarAwayGestureHandlerProvider;
        public Provider<SwipeStatusBarAwayGestureLogger> swipeStatusBarAwayGestureLoggerProvider;
        public Provider<SyncTile> syncTileProvider;
        public Provider<SysUIUnfoldComponent.Factory> sysUIUnfoldComponentFactoryProvider;
        public Provider<SystemActions> systemActionsProvider;
        public Provider<SystemEventChipAnimationController> systemEventChipAnimationControllerProvider;
        public Provider<SystemEventCoordinator> systemEventCoordinatorProvider;
        public Provider<SystemExitRestarter> systemExitRestarterProvider;
        public Provider<SystemPropertiesHelper> systemPropertiesHelperProvider;
        public Provider systemSettingsImplProvider;
        public Provider<SystemStatusAnimationScheduler> systemStatusAnimationSchedulerProvider;
        public Provider<SystemUIAuxiliaryDumpService> systemUIAuxiliaryDumpServiceProvider;
        public Provider<SystemUIDialogManager> systemUIDialogManagerProvider;
        public Provider<SystemUIService> systemUIServiceProvider;
        public Provider<SysuiColorExtractor> sysuiColorExtractorProvider;
        public Provider<TableLogBufferFactory> tableLogBufferFactoryProvider;
        public Provider<TakeScreenshotService> takeScreenshotServiceProvider;
        public Provider<TargetSdkResolver> targetSdkResolverProvider;
        public Provider<TaskbarDelegate> taskbarDelegateProvider;
        public Provider<TelephonyInteractor> telephonyInteractorProvider;
        public Provider<TelephonyListenerManager> telephonyListenerManagerProvider;
        public Provider<TelephonyRepositoryImpl> telephonyRepositoryImplProvider;
        public Provider<ThemeOverlayApplier> themeOverlayApplierProvider;
        public Provider<ThemeOverlayController> themeOverlayControllerProvider;
        public C0055TileLifecycleManager_Factory tileLifecycleManagerProvider;
        public Provider<TileServices> tileServicesProvider;
        public Provider<TimeoutHandler> timeoutHandlerProvider;
        public Provider<ToastFactory> toastFactoryProvider;
        public Provider<ToastLogger> toastLoggerProvider;
        public Provider<ToastUI> toastUIProvider;
        public Provider<TunablePadding.TunablePaddingService> tunablePaddingServiceProvider;
        public Provider<TunerActivity> tunerActivityProvider;
        public Provider<TunerServiceImpl> tunerServiceImplProvider;
        public Provider<TvNotificationHandler> tvNotificationHandlerProvider;
        public Provider<TvNotificationPanelActivity> tvNotificationPanelActivityProvider;
        public Provider<TvUnblockSensorActivity> tvUnblockSensorActivityProvider;
        public Provider<TypeClassifier> typeClassifierProvider;
        public Provider<UdfpsController> udfpsControllerProvider;
        public Provider<UdfpsHapticsSimulator> udfpsHapticsSimulatorProvider;
        public Provider<UdfpsShell> udfpsShellProvider;
        public Provider<UiModeNightTile> uiModeNightTileProvider;
        public Provider<UiOffloadThread> uiOffloadThreadProvider;
        public Provider<UnfoldLatencyTracker> unfoldLatencyTrackerProvider;
        public Provider<UnlockedScreenOffAnimationController> unlockedScreenOffAnimationControllerProvider;
        public Provider<UsbConfirmActivity> usbConfirmActivityProvider;
        public Provider<UsbDebuggingActivity> usbDebuggingActivityProvider;
        public Provider<UsbDebuggingSecondaryUserActivity> usbDebuggingSecondaryUserActivityProvider;
        public Provider<UsbPermissionActivity> usbPermissionActivityProvider;
        public Provider<UsbTetherTile> usbTetherTileProvider;
        public Provider<UserCreator> userCreatorProvider;
        public Provider<UserFileManagerImpl> userFileManagerImplProvider;
        public Provider<UserInfoControllerImpl> userInfoControllerImplProvider;
        public Provider<UserInteractor> userInteractorProvider;
        public Provider<UserRepositoryImpl> userRepositoryImplProvider;
        public Provider<UserSetupRepositoryImpl> userSetupRepositoryImplProvider;
        public Provider<UserSwitchDialogController> userSwitchDialogControllerProvider;
        public Provider<UserSwitcherActivity> userSwitcherActivityProvider;
        public Provider<UserSwitcherController> userSwitcherControllerProvider;
        public Provider<UserSwitcherDialogCoordinator> userSwitcherDialogCoordinatorProvider;
        public Provider<UserSwitcherRepositoryImpl> userSwitcherRepositoryImplProvider;
        public Provider<VibratorHelper> vibratorHelperProvider;
        public Provider<ViewUtil> viewUtilProvider;
        public Provider<VisibilityLocationProviderDelegator> visibilityLocationProviderDelegatorProvider;
        public Provider<VisualStabilityCoordinator> visualStabilityCoordinatorProvider;
        public Provider<VisualStabilityProvider> visualStabilityProvider;
        public Provider<VolumeDialogComponent> volumeDialogComponentProvider;
        public Provider<VolumeDialogControllerImpl> volumeDialogControllerImplProvider;
        public Provider<VolumePanelDialogReceiver> volumePanelDialogReceiverProvider;
        public Provider<VolumePanelFactory> volumePanelFactoryProvider;
        public Provider<VolumeUI> volumeUIProvider;
        public Provider<VpnTile> vpnTileProvider;
        public Provider<WMShell> wMShellProvider;
        public Provider<WakefulnessLifecycle> wakefulnessLifecycleProvider;
        public Provider<WalletActivity> walletActivityProvider;
        public Provider<WalletControllerImpl> walletControllerImplProvider;
        public Provider<WallpaperController> wallpaperControllerProvider;
        public Provider<WifiConstants> wifiConstantsProvider;
        public Provider<WifiInteractorImpl> wifiInteractorImplProvider;
        public Provider<AccessPointControllerImpl.WifiPickerTrackerFactory> wifiPickerTrackerFactoryProvider;
        public Provider<WifiRepositorySwitcher> wifiRepositorySwitcherProvider;
        public Provider<WifiStateWorker> wifiStateWorkerProvider;
        public Provider<WifiStatusTrackerFactory> wifiStatusTrackerFactoryProvider;
        public Provider<WifiTile> wifiTileProvider;
        public Provider<WifiUiAdapter> wifiUiAdapterProvider;
        public Provider<WifiViewModel> wifiViewModelProvider;
        public Provider<WindowMagnification> windowMagnificationProvider;
        public Provider<WiredChargingRippleController> wiredChargingRippleControllerProvider;
        public Provider<WorkLockActivity> workLockActivityProvider;
        public Provider<WorkModeTile> workModeTileProvider;
        public Provider<ZenModeControllerImpl> zenModeControllerImplProvider;
        public Provider zigZagClassifierProvider;

        /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$CentralSurfacesComponentFactory.class */
        public final class CentralSurfacesComponentFactory implements CentralSurfacesComponent.Factory {
            public CentralSurfacesComponentFactory() {
            }

            public CentralSurfacesComponent create() {
                return new CentralSurfacesComponentImpl();
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$CentralSurfacesComponentImpl.class */
        public final class CentralSurfacesComponentImpl implements CentralSurfacesComponent {
            public Provider<AuthRippleController> authRippleControllerProvider;
            public Provider builderProvider;
            public Provider<FlingAnimationUtils.Builder> builderProvider2;
            public Provider<CarrierTextManager.Builder> builderProvider3;
            public Provider<QSCarrierGroupController.Builder> builderProvider4;
            public Provider<CentralSurfacesCommandQueueCallbacks> centralSurfacesCommandQueueCallbacksProvider;
            public Provider<VariableDateViewController.Factory> factoryProvider;
            public Provider<AuthRippleView> getAuthRippleViewProvider;
            public Provider<BatteryMeterViewController> getBatteryMeterViewControllerProvider;
            public Provider<BatteryMeterView> getBatteryMeterViewProvider;
            public Provider<View> getLargeScreenShadeHeaderBarViewProvider;
            public Provider<LockIconView> getLockIconViewProvider;
            public Provider<NotificationPanelView> getNotificationPanelViewProvider;
            public Provider<NotificationsQuickSettingsContainer> getNotificationsQuickSettingsContainerProvider;
            public Provider<OngoingPrivacyChip> getSplitShadeOngoingPrivacyChipProvider;
            public Provider<TapAgainView> getTapAgainViewProvider;
            public Provider<HeaderPrivacyIconsController> headerPrivacyIconsControllerProvider;
            public Provider<KeyguardBottomAreaViewController> keyguardBottomAreaViewControllerProvider;
            public Provider<KeyguardBouncerViewModel> keyguardBouncerViewModelProvider;
            public Provider<LargeScreenShadeHeaderController> largeScreenShadeHeaderControllerProvider;
            public Provider<LetterboxAppearanceCalculator> letterboxAppearanceCalculatorProvider;
            public Provider<LetterboxBackgroundProvider> letterboxBackgroundProvider;
            public Provider<LockIconViewController> lockIconViewControllerProvider;
            public Provider<NotificationLaunchAnimatorControllerProvider> notificationLaunchAnimatorControllerProvider;
            public Provider<NotificationPanelViewController> notificationPanelViewControllerProvider;
            public Provider<NotificationShadeWindowViewController> notificationShadeWindowViewControllerProvider;
            public Provider<NotificationStackScrollLayoutController> notificationStackScrollLayoutControllerProvider;
            public Provider<NotificationStackScrollLogger> notificationStackScrollLoggerProvider;
            public Provider<NotificationsQSContainerController> notificationsQSContainerControllerProvider;
            public Provider<CombinedShadeHeadersConstraintManager> provideCombinedShadeHeadersConstraintManagerProvider;
            public Provider<NotificationListContainer> provideListContainerProvider;
            public Provider<KeyguardBottomAreaView> providesKeyguardBottomAreaViewProvider;
            public Provider<NotificationShadeWindowView> providesNotificationShadeWindowViewProvider;
            public Provider<NotificationShelf> providesNotificationShelfProvider;
            public Provider<NotificationStackScrollLayout> providesNotificationStackScrollLayoutProvider;
            public Provider<NotificationShelfController> providesStatusBarWindowViewProvider;
            public Provider<StatusIconContainer> providesStatusIconContainerProvider;
            public Provider<PulsingGestureListener> pulsingGestureListenerProvider;
            public Provider<Set<StatusBarInitializer.OnStatusBarViewInitializedListener>> setOfOnStatusBarViewInitializedListenerProvider;
            public Provider<ShadeHeightLogger> shadeHeightLoggerProvider;
            public Provider<ShadeLogger> shadeLoggerProvider;
            public Provider<StackStateLogger> stackStateLoggerProvider;
            public Provider<StatusBarHeadsUpChangeListener> statusBarHeadsUpChangeListenerProvider;
            public Provider<StatusBarInitializer> statusBarInitializerProvider;
            public Provider<StatusBarNotificationActivityStarterLogger> statusBarNotificationActivityStarterLoggerProvider;
            public Provider statusBarNotificationActivityStarterProvider;
            public Provider statusBarNotificationPresenterProvider;
            public Provider<SystemBarAttributesListener> systemBarAttributesListenerProvider;
            public Provider<TapAgainViewController> tapAgainViewControllerProvider;

            /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$CentralSurfacesComponentImpl$StatusBarFragmentComponentFactory.class */
            public final class StatusBarFragmentComponentFactory implements StatusBarFragmentComponent.Factory {
                public StatusBarFragmentComponentFactory() {
                }

                public StatusBarFragmentComponent create(CollapsedStatusBarFragment collapsedStatusBarFragment) {
                    Preconditions.checkNotNull(collapsedStatusBarFragment);
                    return new StatusBarFragmentComponentI(collapsedStatusBarFragment);
                }
            }

            /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$CentralSurfacesComponentImpl$StatusBarFragmentComponentI.class */
            public final class StatusBarFragmentComponentI implements StatusBarFragmentComponent {
                public Provider<CollapsedStatusBarFragment> collapsedStatusBarFragmentProvider;
                public Provider<View> endSideContentProvider;
                public Provider<PhoneStatusBarViewController.Factory> factoryProvider;
                public Provider<HeadsUpAppearanceController> headsUpAppearanceControllerProvider;
                public Provider<LightsOutNotifController> lightsOutNotifControllerProvider;
                public Provider<BatteryMeterView> provideBatteryMeterViewProvider;
                public Provider<Clock> provideClockProvider;
                public Provider<View> provideLightsOutNotifViewProvider;
                public Provider<Optional<View>> provideOperatorFrameNameViewProvider;
                public Provider<View> provideOperatorNameViewProvider;
                public Provider<PhoneStatusBarTransitions> providePhoneStatusBarTransitionsProvider;
                public Provider<PhoneStatusBarViewController> providePhoneStatusBarViewControllerProvider;
                public Provider<PhoneStatusBarView> providePhoneStatusBarViewProvider;
                public Provider<HeadsUpStatusBarView> providesHeasdUpStatusBarViewProvider;
                public Provider<Set<StatusBarBoundsProvider.BoundsChangeListener>> setOfBoundsChangeListenerProvider;
                public Provider<View> startSideContentProvider;
                public Provider<StatusBarBoundsProvider> statusBarBoundsProvider;
                public Provider<StatusBarDemoMode> statusBarDemoModeProvider;
                public Provider<StatusBarUserChipViewModel> statusBarUserChipViewModelProvider;

                public StatusBarFragmentComponentI(CollapsedStatusBarFragment collapsedStatusBarFragment) {
                    initialize(collapsedStatusBarFragment);
                }

                public BatteryMeterViewController getBatteryMeterViewController() {
                    return new BatteryMeterViewController((BatteryMeterView) this.provideBatteryMeterViewProvider.get(), (UserTracker) ReferenceSysUIComponentImpl.this.provideUserTrackerProvider.get(), (ConfigurationController) ReferenceSysUIComponentImpl.this.configurationControllerImplProvider.get(), (TunerService) ReferenceSysUIComponentImpl.this.tunerServiceImplProvider.get(), DaggerReferenceGlobalRootComponent.this.mainHandler(), (ContentResolver) DaggerReferenceGlobalRootComponent.this.provideContentResolverProvider.get(), (FeatureFlags) ReferenceSysUIComponentImpl.this.featureFlagsDebugProvider.get(), (BatteryController) ReferenceSysUIComponentImpl.this.provideBatteryControllerProvider.get());
                }

                public StatusBarBoundsProvider getBoundsProvider() {
                    return (StatusBarBoundsProvider) this.statusBarBoundsProvider.get();
                }

                public HeadsUpAppearanceController getHeadsUpAppearanceController() {
                    return (HeadsUpAppearanceController) this.headsUpAppearanceControllerProvider.get();
                }

                public LightsOutNotifController getLightsOutNotifController() {
                    return (LightsOutNotifController) this.lightsOutNotifControllerProvider.get();
                }

                public PhoneStatusBarTransitions getPhoneStatusBarTransitions() {
                    return (PhoneStatusBarTransitions) this.providePhoneStatusBarTransitionsProvider.get();
                }

                public PhoneStatusBarView getPhoneStatusBarView() {
                    return (PhoneStatusBarView) this.providePhoneStatusBarViewProvider.get();
                }

                public PhoneStatusBarViewController getPhoneStatusBarViewController() {
                    return (PhoneStatusBarViewController) this.providePhoneStatusBarViewControllerProvider.get();
                }

                public Set<StatusBarFragmentComponent.Startable> getStartables() {
                    return Collections.singleton((StatusBarFragmentComponent.Startable) this.statusBarBoundsProvider.get());
                }

                public StatusBarDemoMode getStatusBarDemoMode() {
                    return (StatusBarDemoMode) this.statusBarDemoModeProvider.get();
                }

                public /* bridge */ /* synthetic */ void init() {
                    super.init();
                }

                public final void initialize(CollapsedStatusBarFragment collapsedStatusBarFragment) {
                    Factory create = InstanceFactory.create(collapsedStatusBarFragment);
                    this.collapsedStatusBarFragmentProvider = create;
                    Provider<PhoneStatusBarView> provider = DoubleCheck.provider(StatusBarFragmentModule_ProvidePhoneStatusBarViewFactory.create(create));
                    this.providePhoneStatusBarViewProvider = provider;
                    this.provideBatteryMeterViewProvider = DoubleCheck.provider(StatusBarFragmentModule_ProvideBatteryMeterViewFactory.create(provider));
                    this.statusBarUserChipViewModelProvider = StatusBarUserChipViewModel_Factory.create(DaggerReferenceGlobalRootComponent.this.provideApplicationContextProvider, ReferenceSysUIComponentImpl.this.userInteractorProvider);
                    PhoneStatusBarViewController_Factory_Factory create2 = PhoneStatusBarViewController_Factory_Factory.create(ReferenceSysUIComponentImpl.this.provideSysUIUnfoldComponentProvider, DaggerReferenceGlobalRootComponent.this.provideStatusBarScopedTransitionProvider, this.statusBarUserChipViewModelProvider, ReferenceSysUIComponentImpl.this.centralSurfacesImplProvider, ReferenceSysUIComponentImpl.this.shadeControllerImplProvider, CentralSurfacesComponentImpl.this.shadeLoggerProvider, ReferenceSysUIComponentImpl.this.viewUtilProvider, ReferenceSysUIComponentImpl.this.configurationControllerImplProvider);
                    this.factoryProvider = create2;
                    this.providePhoneStatusBarViewControllerProvider = DoubleCheck.provider(StatusBarFragmentModule_ProvidePhoneStatusBarViewControllerFactory.create(create2, this.providePhoneStatusBarViewProvider));
                    this.providesHeasdUpStatusBarViewProvider = DoubleCheck.provider(StatusBarFragmentModule_ProvidesHeasdUpStatusBarViewFactory.create(this.providePhoneStatusBarViewProvider));
                    this.provideClockProvider = DoubleCheck.provider(StatusBarFragmentModule_ProvideClockFactory.create(this.providePhoneStatusBarViewProvider));
                    this.provideOperatorFrameNameViewProvider = DoubleCheck.provider(StatusBarFragmentModule_ProvideOperatorFrameNameViewFactory.create(this.providePhoneStatusBarViewProvider));
                    this.headsUpAppearanceControllerProvider = DoubleCheck.provider(HeadsUpAppearanceController_Factory.create(ReferenceSysUIComponentImpl.this.notificationIconAreaControllerProvider, ReferenceSysUIComponentImpl.this.provideHeadsUpManagerPhoneProvider, ReferenceSysUIComponentImpl.this.statusBarStateControllerImplProvider, ReferenceSysUIComponentImpl.this.keyguardBypassControllerProvider, ReferenceSysUIComponentImpl.this.notificationWakeUpCoordinatorProvider, ReferenceSysUIComponentImpl.this.darkIconDispatcherImplProvider, ReferenceSysUIComponentImpl.this.keyguardStateControllerImplProvider, ReferenceSysUIComponentImpl.this.provideCommandQueueProvider, CentralSurfacesComponentImpl.this.notificationStackScrollLayoutControllerProvider, CentralSurfacesComponentImpl.this.notificationPanelViewControllerProvider, ReferenceSysUIComponentImpl.this.notificationRoundnessManagerProvider, ReferenceSysUIComponentImpl.this.featureFlagsDebugProvider, this.providesHeasdUpStatusBarViewProvider, this.provideClockProvider, this.provideOperatorFrameNameViewProvider, this.providePhoneStatusBarViewProvider));
                    Provider<View> provider2 = DoubleCheck.provider(StatusBarFragmentModule_ProvideLightsOutNotifViewFactory.create(this.providePhoneStatusBarViewProvider));
                    this.provideLightsOutNotifViewProvider = provider2;
                    this.lightsOutNotifControllerProvider = DoubleCheck.provider(LightsOutNotifController_Factory.create(provider2, DaggerReferenceGlobalRootComponent.this.provideWindowManagerProvider, ReferenceSysUIComponentImpl.this.notifLiveDataStoreImplProvider, ReferenceSysUIComponentImpl.this.provideCommandQueueProvider));
                    this.provideOperatorNameViewProvider = DoubleCheck.provider(StatusBarFragmentModule_ProvideOperatorNameViewFactory.create(this.providePhoneStatusBarViewProvider));
                    this.providePhoneStatusBarTransitionsProvider = DoubleCheck.provider(StatusBarFragmentModule_ProvidePhoneStatusBarTransitionsFactory.create(this.providePhoneStatusBarViewProvider, ReferenceSysUIComponentImpl.this.statusBarWindowControllerProvider));
                    this.statusBarDemoModeProvider = DoubleCheck.provider(StatusBarDemoMode_Factory.create(this.provideClockProvider, this.provideOperatorNameViewProvider, ReferenceSysUIComponentImpl.this.provideDemoModeControllerProvider, this.providePhoneStatusBarTransitionsProvider, ReferenceSysUIComponentImpl.this.navigationBarControllerProvider, DaggerReferenceGlobalRootComponent.this.provideDisplayIdProvider));
                    this.setOfBoundsChangeListenerProvider = SetFactory.builder(1, 0).addProvider(CentralSurfacesComponentImpl.this.systemBarAttributesListenerProvider).build();
                    this.startSideContentProvider = DoubleCheck.provider(StatusBarFragmentModule_StartSideContentFactory.create(this.providePhoneStatusBarViewProvider));
                    Provider<View> provider3 = DoubleCheck.provider(StatusBarFragmentModule_EndSideContentFactory.create(this.providePhoneStatusBarViewProvider));
                    this.endSideContentProvider = provider3;
                    this.statusBarBoundsProvider = DoubleCheck.provider(StatusBarBoundsProvider_Factory.create(this.setOfBoundsChangeListenerProvider, this.startSideContentProvider, provider3));
                }
            }

            public CentralSurfacesComponentImpl() {
                initialize();
            }

            public final CollapsedStatusBarFragmentLogger collapsedStatusBarFragmentLogger() {
                return new CollapsedStatusBarFragmentLogger((LogBuffer) ReferenceSysUIComponentImpl.this.provideCollapsedSbFragmentLogBufferProvider.get(), (DisableFlagsLogger) ReferenceSysUIComponentImpl.this.disableFlagsLoggerProvider.get());
            }

            public CollapsedStatusBarFragment createCollapsedStatusBarFragment() {
                return StatusBarViewModule_CreateCollapsedStatusBarFragmentFactory.createCollapsedStatusBarFragment(new StatusBarFragmentComponentFactory(), (OngoingCallController) ReferenceSysUIComponentImpl.this.provideOngoingCallControllerProvider.get(), (SystemStatusAnimationScheduler) ReferenceSysUIComponentImpl.this.systemStatusAnimationSchedulerProvider.get(), (StatusBarLocationPublisher) ReferenceSysUIComponentImpl.this.statusBarLocationPublisherProvider.get(), (NotificationIconAreaController) ReferenceSysUIComponentImpl.this.notificationIconAreaControllerProvider.get(), (ShadeExpansionStateManager) ReferenceSysUIComponentImpl.this.shadeExpansionStateManagerProvider.get(), (FeatureFlags) ReferenceSysUIComponentImpl.this.featureFlagsDebugProvider.get(), (StatusBarIconController) ReferenceSysUIComponentImpl.this.statusBarIconControllerImplProvider.get(), (StatusBarIconController.DarkIconManager.Factory) ReferenceSysUIComponentImpl.this.factoryProvider23.get(), (StatusBarHideIconsForBouncerManager) ReferenceSysUIComponentImpl.this.statusBarHideIconsForBouncerManagerProvider.get(), (KeyguardStateController) ReferenceSysUIComponentImpl.this.keyguardStateControllerImplProvider.get(), (NotificationPanelViewController) this.notificationPanelViewControllerProvider.get(), (NetworkController) ReferenceSysUIComponentImpl.this.networkControllerImplProvider.get(), (StatusBarStateController) ReferenceSysUIComponentImpl.this.statusBarStateControllerImplProvider.get(), (CommandQueue) ReferenceSysUIComponentImpl.this.provideCommandQueueProvider.get(), (CarrierConfigTracker) ReferenceSysUIComponentImpl.this.carrierConfigTrackerProvider.get(), collapsedStatusBarFragmentLogger(), operatorNameViewControllerFactory(), (SecureSettings) ReferenceSysUIComponentImpl.this.secureSettingsImpl(), (Executor) DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider.get(), (DumpManager) DaggerReferenceGlobalRootComponent.this.dumpManagerProvider.get());
            }

            public AuthRippleController getAuthRippleController() {
                return (AuthRippleController) this.authRippleControllerProvider.get();
            }

            public NotificationRowBinderImpl.BindRowCallback getBindRowCallback() {
                return (NotificationRowBinderImpl.BindRowCallback) this.statusBarNotificationPresenterProvider.get();
            }

            public CentralSurfacesCommandQueueCallbacks getCentralSurfacesCommandQueueCallbacks() {
                return (CentralSurfacesCommandQueueCallbacks) this.centralSurfacesCommandQueueCallbacksProvider.get();
            }

            public LargeScreenShadeHeaderController getLargeScreenShadeHeaderController() {
                return (LargeScreenShadeHeaderController) this.largeScreenShadeHeaderControllerProvider.get();
            }

            public LockIconViewController getLockIconViewController() {
                return (LockIconViewController) this.lockIconViewControllerProvider.get();
            }

            public NotificationActivityStarter getNotificationActivityStarter() {
                return (NotificationActivityStarter) this.statusBarNotificationActivityStarterProvider.get();
            }

            public NotificationListContainer getNotificationListContainer() {
                return (NotificationListContainer) this.provideListContainerProvider.get();
            }

            public NotificationPanelViewController getNotificationPanelViewController() {
                return (NotificationPanelViewController) this.notificationPanelViewControllerProvider.get();
            }

            public NotificationPresenter getNotificationPresenter() {
                return (NotificationPresenter) this.statusBarNotificationPresenterProvider.get();
            }

            public NotificationShadeWindowView getNotificationShadeWindowView() {
                return (NotificationShadeWindowView) this.providesNotificationShadeWindowViewProvider.get();
            }

            public NotificationShadeWindowViewController getNotificationShadeWindowViewController() {
                return (NotificationShadeWindowViewController) this.notificationShadeWindowViewControllerProvider.get();
            }

            public NotificationShelfController getNotificationShelfController() {
                return (NotificationShelfController) this.providesStatusBarWindowViewProvider.get();
            }

            public NotificationStackScrollLayoutController getNotificationStackScrollLayoutController() {
                return (NotificationStackScrollLayoutController) this.notificationStackScrollLayoutControllerProvider.get();
            }

            public Set<CentralSurfacesComponent.Startable> getStartables() {
                return SetBuilder.newSetBuilder(3).add((CentralSurfacesComponent.Startable) this.letterboxAppearanceCalculatorProvider.get()).add((CentralSurfacesComponent.Startable) this.systemBarAttributesListenerProvider.get()).add((CentralSurfacesComponent.Startable) this.letterboxBackgroundProvider.get()).build();
            }

            public StatusBarHeadsUpChangeListener getStatusBarHeadsUpChangeListener() {
                return (StatusBarHeadsUpChangeListener) this.statusBarHeadsUpChangeListenerProvider.get();
            }

            public StatusBarInitializer getStatusBarInitializer() {
                return (StatusBarInitializer) this.statusBarInitializerProvider.get();
            }

            public final void initialize() {
                Provider<NotificationShadeWindowView> provider = DoubleCheck.provider(StatusBarViewModule_ProvidesNotificationShadeWindowViewFactory.create(DaggerReferenceGlobalRootComponent.this.providerLayoutInflaterProvider));
                this.providesNotificationShadeWindowViewProvider = provider;
                this.providesNotificationStackScrollLayoutProvider = DoubleCheck.provider(StatusBarViewModule_ProvidesNotificationStackScrollLayoutFactory.create(provider));
                this.providesNotificationShelfProvider = DoubleCheck.provider(StatusBarViewModule_ProvidesNotificationShelfFactory.create(DaggerReferenceGlobalRootComponent.this.providerLayoutInflaterProvider, this.providesNotificationStackScrollLayoutProvider));
                this.providesStatusBarWindowViewProvider = DoubleCheck.provider(StatusBarViewModule_ProvidesStatusBarWindowViewFactory.create(ReferenceSysUIComponentImpl.this.notificationShelfComponentBuilderProvider, this.providesNotificationShelfProvider));
                this.builderProvider = NotificationSwipeHelper_Builder_Factory.create(DaggerReferenceGlobalRootComponent.this.provideResourcesProvider, DaggerReferenceGlobalRootComponent.this.provideViewConfigurationProvider, ReferenceSysUIComponentImpl.this.falsingManagerProxyProvider, ReferenceSysUIComponentImpl.this.featureFlagsDebugProvider, ReferenceSysUIComponentImpl.this.notificationRoundnessManagerProvider);
                this.stackStateLoggerProvider = StackStateLogger_Factory.create(ReferenceSysUIComponentImpl.this.provideNotificationHeadsUpLogBufferProvider);
                this.notificationStackScrollLoggerProvider = NotificationStackScrollLogger_Factory.create(ReferenceSysUIComponentImpl.this.provideNotificationHeadsUpLogBufferProvider);
                this.notificationStackScrollLayoutControllerProvider = DoubleCheck.provider(NotificationStackScrollLayoutController_Factory.create(ReferenceSysUIComponentImpl.this.provideAllowNotificationLongPressProvider, ReferenceSysUIComponentImpl.this.provideNotificationGutsManagerProvider, ReferenceSysUIComponentImpl.this.notificationVisibilityProviderImplProvider, ReferenceSysUIComponentImpl.this.provideHeadsUpManagerPhoneProvider, ReferenceSysUIComponentImpl.this.notificationRoundnessManagerProvider, ReferenceSysUIComponentImpl.this.tunerServiceImplProvider, ReferenceSysUIComponentImpl.this.bindDeviceProvisionedControllerProvider, ReferenceSysUIComponentImpl.this.dynamicPrivacyControllerProvider, ReferenceSysUIComponentImpl.this.configurationControllerImplProvider, ReferenceSysUIComponentImpl.this.statusBarStateControllerImplProvider, ReferenceSysUIComponentImpl.this.keyguardMediaControllerProvider, ReferenceSysUIComponentImpl.this.keyguardBypassControllerProvider, ReferenceSysUIComponentImpl.this.zenModeControllerImplProvider, ReferenceSysUIComponentImpl.this.notificationLockscreenUserManagerImplProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, ReferenceSysUIComponentImpl.this.falsingCollectorImplProvider, ReferenceSysUIComponentImpl.this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideResourcesProvider, this.builderProvider, ReferenceSysUIComponentImpl.this.centralSurfacesImplProvider, ReferenceSysUIComponentImpl.this.scrimControllerProvider, ReferenceSysUIComponentImpl.this.groupExpansionManagerImplProvider, ReferenceSysUIComponentImpl.this.providesSilentHeaderControllerProvider, ReferenceSysUIComponentImpl.this.notifPipelineProvider, ReferenceSysUIComponentImpl.this.notifPipelineFlagsProvider, ReferenceSysUIComponentImpl.this.notifCollectionProvider, ReferenceSysUIComponentImpl.this.lockscreenShadeTransitionControllerProvider, ReferenceSysUIComponentImpl.this.shadeTransitionControllerProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, ReferenceSysUIComponentImpl.this.provideNotificationRemoteInputManagerProvider, ReferenceSysUIComponentImpl.this.visibilityLocationProviderDelegatorProvider, ReferenceSysUIComponentImpl.this.seenNotificationsProviderImplProvider, ReferenceSysUIComponentImpl.this.shadeControllerImplProvider, DaggerReferenceGlobalRootComponent.this.provideInteractionJankMonitorProvider, this.stackStateLoggerProvider, this.notificationStackScrollLoggerProvider, ReferenceSysUIComponentImpl.this.notificationStackSizeCalculatorProvider, ReferenceSysUIComponentImpl.this.featureFlagsDebugProvider, ReferenceSysUIComponentImpl.this.notificationTargetsHelperProvider));
                this.getNotificationPanelViewProvider = DoubleCheck.provider(StatusBarViewModule_GetNotificationPanelViewFactory.create(this.providesNotificationShadeWindowViewProvider));
                this.shadeLoggerProvider = ShadeLogger_Factory.create(ReferenceSysUIComponentImpl.this.provideShadeLogBufferProvider);
                this.shadeHeightLoggerProvider = ShadeHeightLogger_Factory.create(ReferenceSysUIComponentImpl.this.provideShadeHeightLogBufferProvider);
                this.builderProvider2 = FlingAnimationUtils_Builder_Factory.create(DaggerReferenceGlobalRootComponent.this.provideDisplayMetricsProvider);
                this.getNotificationsQuickSettingsContainerProvider = DoubleCheck.provider(StatusBarViewModule_GetNotificationsQuickSettingsContainerFactory.create(this.providesNotificationShadeWindowViewProvider));
                Provider<View> provider2 = DoubleCheck.provider(StatusBarViewModule_GetLargeScreenShadeHeaderBarViewFactory.create(this.providesNotificationShadeWindowViewProvider, ReferenceSysUIComponentImpl.this.featureFlagsDebugProvider));
                this.getLargeScreenShadeHeaderBarViewProvider = provider2;
                this.getSplitShadeOngoingPrivacyChipProvider = DoubleCheck.provider(StatusBarViewModule_GetSplitShadeOngoingPrivacyChipFactory.create(provider2));
                this.providesStatusIconContainerProvider = DoubleCheck.provider(StatusBarViewModule_ProvidesStatusIconContainerFactory.create(this.getLargeScreenShadeHeaderBarViewProvider));
                this.headerPrivacyIconsControllerProvider = HeaderPrivacyIconsController_Factory.create(ReferenceSysUIComponentImpl.this.privacyItemControllerProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, this.getSplitShadeOngoingPrivacyChipProvider, ReferenceSysUIComponentImpl.this.privacyDialogControllerProvider, ReferenceSysUIComponentImpl.this.privacyLoggerProvider, this.providesStatusIconContainerProvider, DaggerReferenceGlobalRootComponent.this.providePermissionManagerProvider, ReferenceSysUIComponentImpl.this.provideBackgroundExecutorProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, ReferenceSysUIComponentImpl.this.provideActivityStarterProvider, ReferenceSysUIComponentImpl.this.appOpsControllerImplProvider, ReferenceSysUIComponentImpl.this.broadcastDispatcherProvider, DaggerReferenceGlobalRootComponent.this.provideSafetyCenterManagerProvider, ReferenceSysUIComponentImpl.this.bindDeviceProvisionedControllerProvider);
                this.factoryProvider = VariableDateViewController_Factory_Factory.create(ReferenceSysUIComponentImpl.this.bindSystemClockProvider, ReferenceSysUIComponentImpl.this.broadcastDispatcherProvider, ReferenceSysUIComponentImpl.this.provideTimeTickHandlerProvider);
                Provider<BatteryMeterView> provider3 = DoubleCheck.provider(StatusBarViewModule_GetBatteryMeterViewFactory.create(this.getLargeScreenShadeHeaderBarViewProvider));
                this.getBatteryMeterViewProvider = provider3;
                this.getBatteryMeterViewControllerProvider = DoubleCheck.provider(StatusBarViewModule_GetBatteryMeterViewControllerFactory.create(provider3, ReferenceSysUIComponentImpl.this.provideUserTrackerProvider, ReferenceSysUIComponentImpl.this.configurationControllerImplProvider, ReferenceSysUIComponentImpl.this.tunerServiceImplProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, DaggerReferenceGlobalRootComponent.this.provideContentResolverProvider, ReferenceSysUIComponentImpl.this.featureFlagsDebugProvider, ReferenceSysUIComponentImpl.this.provideBatteryControllerProvider));
                this.builderProvider3 = CarrierTextManager_Builder_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideResourcesProvider, DaggerReferenceGlobalRootComponent.this.provideWifiManagerProvider, DaggerReferenceGlobalRootComponent.this.provideTelephonyManagerProvider, ReferenceSysUIComponentImpl.this.telephonyListenerManagerProvider, ReferenceSysUIComponentImpl.this.wakefulnessLifecycleProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, ReferenceSysUIComponentImpl.this.provideBackgroundExecutorProvider, ReferenceSysUIComponentImpl.this.keyguardUpdateMonitorProvider);
                this.builderProvider4 = QSCarrierGroupController_Builder_Factory.create(ReferenceSysUIComponentImpl.this.provideActivityStarterProvider, ReferenceSysUIComponentImpl.this.provideBgHandlerProvider, GlobalConcurrencyModule_ProvideMainLooperFactory.create(), ReferenceSysUIComponentImpl.this.networkControllerImplProvider, this.builderProvider3, DaggerReferenceGlobalRootComponent.this.contextProvider, ReferenceSysUIComponentImpl.this.carrierConfigTrackerProvider, ReferenceSysUIComponentImpl.this.subscriptionManagerSlotIndexResolverProvider);
                this.provideCombinedShadeHeadersConstraintManagerProvider = DoubleCheck.provider(StatusBarViewModule_ProvideCombinedShadeHeadersConstraintManagerFactory.create());
                this.largeScreenShadeHeaderControllerProvider = DoubleCheck.provider(LargeScreenShadeHeaderController_Factory.create(this.getLargeScreenShadeHeaderBarViewProvider, ReferenceSysUIComponentImpl.this.statusBarIconControllerImplProvider, ReferenceSysUIComponentImpl.this.factoryProvider22, this.headerPrivacyIconsControllerProvider, ReferenceSysUIComponentImpl.this.statusBarContentInsetsProvider, ReferenceSysUIComponentImpl.this.configurationControllerImplProvider, this.factoryProvider, this.getBatteryMeterViewControllerProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, ReferenceSysUIComponentImpl.this.featureFlagsDebugProvider, this.builderProvider4, this.provideCombinedShadeHeadersConstraintManagerProvider, ReferenceSysUIComponentImpl.this.provideDemoModeControllerProvider, ReferenceSysUIComponentImpl.this.provideActivityStarterProvider));
                this.notificationsQSContainerControllerProvider = NotificationsQSContainerController_Factory.create(this.getNotificationsQuickSettingsContainerProvider, ReferenceSysUIComponentImpl.this.navigationModeControllerProvider, ReferenceSysUIComponentImpl.this.overviewProxyServiceProvider, this.largeScreenShadeHeaderControllerProvider, ReferenceSysUIComponentImpl.this.shadeExpansionStateManagerProvider, ReferenceSysUIComponentImpl.this.featureFlagsDebugProvider, DaggerReferenceGlobalRootComponent.this.provideMainDelayableExecutorProvider);
                this.getLockIconViewProvider = DoubleCheck.provider(StatusBarViewModule_GetLockIconViewFactory.create(this.providesNotificationShadeWindowViewProvider));
                this.getAuthRippleViewProvider = DoubleCheck.provider(StatusBarViewModule_GetAuthRippleViewFactory.create(this.providesNotificationShadeWindowViewProvider));
                this.authRippleControllerProvider = DoubleCheck.provider(AuthRippleController_Factory.create(ReferenceSysUIComponentImpl.this.centralSurfacesImplProvider, DaggerReferenceGlobalRootComponent.this.contextProvider, ReferenceSysUIComponentImpl.this.authControllerProvider, ReferenceSysUIComponentImpl.this.configurationControllerImplProvider, ReferenceSysUIComponentImpl.this.keyguardUpdateMonitorProvider, ReferenceSysUIComponentImpl.this.keyguardStateControllerImplProvider, ReferenceSysUIComponentImpl.this.wakefulnessLifecycleProvider, ReferenceSysUIComponentImpl.this.commandRegistryProvider, ReferenceSysUIComponentImpl.this.notificationShadeWindowControllerImplProvider, ReferenceSysUIComponentImpl.this.keyguardBypassControllerProvider, ReferenceSysUIComponentImpl.this.biometricUnlockControllerProvider, ReferenceSysUIComponentImpl.this.udfpsControllerProvider, ReferenceSysUIComponentImpl.this.statusBarStateControllerImplProvider, ReferenceSysUIComponentImpl.this.featureFlagsDebugProvider, this.getAuthRippleViewProvider));
                this.lockIconViewControllerProvider = DoubleCheck.provider(LockIconViewController_Factory.create(this.getLockIconViewProvider, ReferenceSysUIComponentImpl.this.statusBarStateControllerImplProvider, ReferenceSysUIComponentImpl.this.keyguardUpdateMonitorProvider, ReferenceSysUIComponentImpl.this.statusBarKeyguardViewManagerProvider, ReferenceSysUIComponentImpl.this.keyguardStateControllerImplProvider, ReferenceSysUIComponentImpl.this.falsingManagerProxyProvider, ReferenceSysUIComponentImpl.this.authControllerProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, DaggerReferenceGlobalRootComponent.this.provideAccessibilityManagerProvider, ReferenceSysUIComponentImpl.this.configurationControllerImplProvider, DaggerReferenceGlobalRootComponent.this.provideMainDelayableExecutorProvider, ReferenceSysUIComponentImpl.this.vibratorHelperProvider, this.authRippleControllerProvider, DaggerReferenceGlobalRootComponent.this.provideResourcesProvider, ReferenceSysUIComponentImpl.this.keyguardTransitionInteractorProvider, ReferenceSysUIComponentImpl.this.keyguardInteractorProvider, ReferenceSysUIComponentImpl.this.featureFlagsDebugProvider));
                Provider<TapAgainView> provider4 = DoubleCheck.provider(StatusBarViewModule_GetTapAgainViewFactory.create(this.getNotificationPanelViewProvider));
                this.getTapAgainViewProvider = provider4;
                this.tapAgainViewControllerProvider = DoubleCheck.provider(TapAgainViewController_Factory.create(provider4, DaggerReferenceGlobalRootComponent.this.provideMainDelayableExecutorProvider, ReferenceSysUIComponentImpl.this.configurationControllerImplProvider, FalsingModule_ProvidesDoubleTapTimeoutMsFactory.create()));
                StatusBarViewModule_ProvidesKeyguardBottomAreaViewFactory create = StatusBarViewModule_ProvidesKeyguardBottomAreaViewFactory.create(this.getNotificationPanelViewProvider, DaggerReferenceGlobalRootComponent.this.providerLayoutInflaterProvider);
                this.providesKeyguardBottomAreaViewProvider = create;
                this.keyguardBottomAreaViewControllerProvider = KeyguardBottomAreaViewController_Factory.create(create);
                this.provideListContainerProvider = DoubleCheck.provider(NotificationStackScrollLayoutListContainerModule_ProvideListContainerFactory.create(this.notificationStackScrollLayoutControllerProvider));
                this.notificationPanelViewControllerProvider = DoubleCheck.provider(NotificationPanelViewController_Factory.create(this.getNotificationPanelViewProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, DaggerReferenceGlobalRootComponent.this.providerLayoutInflaterProvider, ReferenceSysUIComponentImpl.this.featureFlagsDebugProvider, ReferenceSysUIComponentImpl.this.notificationWakeUpCoordinatorProvider, ReferenceSysUIComponentImpl.this.pulseExpansionHandlerProvider, ReferenceSysUIComponentImpl.this.dynamicPrivacyControllerProvider, ReferenceSysUIComponentImpl.this.keyguardBypassControllerProvider, ReferenceSysUIComponentImpl.this.falsingManagerProxyProvider, ReferenceSysUIComponentImpl.this.falsingCollectorImplProvider, ReferenceSysUIComponentImpl.this.keyguardStateControllerImplProvider, ReferenceSysUIComponentImpl.this.statusBarStateControllerImplProvider, ReferenceSysUIComponentImpl.this.statusBarWindowStateControllerProvider, ReferenceSysUIComponentImpl.this.notificationShadeWindowControllerImplProvider, ReferenceSysUIComponentImpl.this.dozeLogProvider, ReferenceSysUIComponentImpl.this.dozeParametersProvider, ReferenceSysUIComponentImpl.this.provideCommandQueueProvider, ReferenceSysUIComponentImpl.this.vibratorHelperProvider, DaggerReferenceGlobalRootComponent.this.provideLatencyTrackerProvider, DaggerReferenceGlobalRootComponent.this.providePowerManagerProvider, DaggerReferenceGlobalRootComponent.this.provideAccessibilityManagerProvider, DaggerReferenceGlobalRootComponent.this.provideDisplayIdProvider, ReferenceSysUIComponentImpl.this.keyguardUpdateMonitorProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.shadeLoggerProvider, this.shadeHeightLoggerProvider, ReferenceSysUIComponentImpl.this.configurationControllerImplProvider, this.builderProvider2, ReferenceSysUIComponentImpl.this.statusBarTouchableRegionManagerProvider, ReferenceSysUIComponentImpl.this.conversationNotificationManagerProvider, ReferenceSysUIComponentImpl.this.mediaHierarchyManagerProvider, ReferenceSysUIComponentImpl.this.statusBarKeyguardViewManagerProvider, ReferenceSysUIComponentImpl.this.provideNotificationGutsManagerProvider, this.notificationsQSContainerControllerProvider, this.notificationStackScrollLayoutControllerProvider, ReferenceSysUIComponentImpl.this.keyguardStatusViewComponentFactoryProvider, ReferenceSysUIComponentImpl.this.keyguardQsUserSwitchComponentFactoryProvider, ReferenceSysUIComponentImpl.this.keyguardUserSwitcherComponentFactoryProvider, ReferenceSysUIComponentImpl.this.keyguardStatusBarViewComponentFactoryProvider, ReferenceSysUIComponentImpl.this.lockscreenShadeTransitionControllerProvider, ReferenceSysUIComponentImpl.this.authControllerProvider, ReferenceSysUIComponentImpl.this.scrimControllerProvider, DaggerReferenceGlobalRootComponent.this.provideUserManagerProvider, ReferenceSysUIComponentImpl.this.mediaDataManagerProvider, ReferenceSysUIComponentImpl.this.notificationShadeDepthControllerProvider, ReferenceSysUIComponentImpl.this.ambientStateProvider, this.lockIconViewControllerProvider, ReferenceSysUIComponentImpl.this.keyguardMediaControllerProvider, this.tapAgainViewControllerProvider, ReferenceSysUIComponentImpl.this.navigationModeControllerProvider, ReferenceSysUIComponentImpl.this.navigationBarControllerProvider, ReferenceSysUIComponentImpl.this.fragmentServiceProvider, DaggerReferenceGlobalRootComponent.this.provideContentResolverProvider, ReferenceSysUIComponentImpl.this.recordingControllerProvider, this.largeScreenShadeHeaderControllerProvider, ReferenceSysUIComponentImpl.this.screenOffAnimationControllerProvider, ReferenceSysUIComponentImpl.this.lockscreenGestureLoggerProvider, ReferenceSysUIComponentImpl.this.shadeExpansionStateManagerProvider, ReferenceSysUIComponentImpl.this.provideNotificationRemoteInputManagerProvider, ReferenceSysUIComponentImpl.this.provideSysUIUnfoldComponentProvider, DaggerReferenceGlobalRootComponent.this.provideInteractionJankMonitorProvider, ReferenceSysUIComponentImpl.this.qsFrameTranslateImplProvider, ReferenceSysUIComponentImpl.this.provideSysUiStateProvider, this.keyguardBottomAreaViewControllerProvider, ReferenceSysUIComponentImpl.this.keyguardUnlockAnimationControllerProvider, ReferenceSysUIComponentImpl.this.keyguardIndicationControllerProvider, this.provideListContainerProvider, ReferenceSysUIComponentImpl.this.notificationStackSizeCalculatorProvider, ReferenceSysUIComponentImpl.this.unlockedScreenOffAnimationControllerProvider, ReferenceSysUIComponentImpl.this.shadeTransitionControllerProvider, ReferenceSysUIComponentImpl.this.bindSystemClockProvider, ReferenceSysUIComponentImpl.this.keyguardBottomAreaViewModelProvider, ReferenceSysUIComponentImpl.this.keyguardBottomAreaInteractorProvider, ReferenceSysUIComponentImpl.this.dreamingToLockscreenTransitionViewModelProvider, ReferenceSysUIComponentImpl.this.occludedToLockscreenTransitionViewModelProvider, ReferenceSysUIComponentImpl.this.mainDispatcherProvider, ReferenceSysUIComponentImpl.this.keyguardTransitionInteractorProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, ReferenceSysUIComponentImpl.this.tunerServiceImplProvider, DaggerReferenceGlobalRootComponent.this.contextProvider));
                this.pulsingGestureListenerProvider = DoubleCheck.provider(PulsingGestureListener_Factory.create(this.providesNotificationShadeWindowViewProvider, ReferenceSysUIComponentImpl.this.falsingManagerProxyProvider, ReferenceSysUIComponentImpl.this.dockManagerImplProvider, ReferenceSysUIComponentImpl.this.centralSurfacesImplProvider, DaggerReferenceGlobalRootComponent.this.provideAmbientDisplayConfigurationProvider, ReferenceSysUIComponentImpl.this.statusBarStateControllerImplProvider, this.shadeLoggerProvider, DaggerReferenceGlobalRootComponent.this.providePowerManagerProvider, ReferenceSysUIComponentImpl.this.tunerServiceImplProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, DaggerReferenceGlobalRootComponent.this.contextProvider));
                this.keyguardBouncerViewModelProvider = KeyguardBouncerViewModel_Factory.create(ReferenceSysUIComponentImpl.this.bouncerViewImplProvider, ReferenceSysUIComponentImpl.this.primaryBouncerInteractorProvider);
                this.notificationShadeWindowViewControllerProvider = DoubleCheck.provider(NotificationShadeWindowViewController_Factory.create(ReferenceSysUIComponentImpl.this.lockscreenShadeTransitionControllerProvider, ReferenceSysUIComponentImpl.this.falsingCollectorImplProvider, ReferenceSysUIComponentImpl.this.statusBarStateControllerImplProvider, ReferenceSysUIComponentImpl.this.dockManagerImplProvider, ReferenceSysUIComponentImpl.this.notificationShadeDepthControllerProvider, this.providesNotificationShadeWindowViewProvider, this.notificationPanelViewControllerProvider, ReferenceSysUIComponentImpl.this.shadeExpansionStateManagerProvider, this.notificationStackScrollLayoutControllerProvider, ReferenceSysUIComponentImpl.this.statusBarKeyguardViewManagerProvider, ReferenceSysUIComponentImpl.this.statusBarWindowStateControllerProvider, this.lockIconViewControllerProvider, ReferenceSysUIComponentImpl.this.centralSurfacesImplProvider, ReferenceSysUIComponentImpl.this.notificationShadeWindowControllerImplProvider, ReferenceSysUIComponentImpl.this.keyguardUnlockAnimationControllerProvider, ReferenceSysUIComponentImpl.this.notificationInsetsImplProvider, ReferenceSysUIComponentImpl.this.ambientStateProvider, this.pulsingGestureListenerProvider, ReferenceSysUIComponentImpl.this.featureFlagsDebugProvider, this.keyguardBouncerViewModelProvider, ReferenceSysUIComponentImpl.this.keyguardBouncerComponentFactoryProvider));
                this.statusBarHeadsUpChangeListenerProvider = DoubleCheck.provider(StatusBarHeadsUpChangeListener_Factory.create(ReferenceSysUIComponentImpl.this.notificationShadeWindowControllerImplProvider, ReferenceSysUIComponentImpl.this.statusBarWindowControllerProvider, this.notificationPanelViewControllerProvider, ReferenceSysUIComponentImpl.this.keyguardBypassControllerProvider, ReferenceSysUIComponentImpl.this.provideHeadsUpManagerPhoneProvider, ReferenceSysUIComponentImpl.this.statusBarStateControllerImplProvider, ReferenceSysUIComponentImpl.this.provideNotificationRemoteInputManagerProvider));
                this.letterboxBackgroundProvider = DoubleCheck.provider(LetterboxBackgroundProvider_Factory.create(DaggerReferenceGlobalRootComponent.this.provideIWindowManagerProvider, ReferenceSysUIComponentImpl.this.provideBackgroundExecutorProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, DaggerReferenceGlobalRootComponent.this.provideWallpaperManagerProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider));
                this.letterboxAppearanceCalculatorProvider = DoubleCheck.provider(LetterboxAppearanceCalculator_Factory.create(ReferenceSysUIComponentImpl.this.lightBarControllerProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.letterboxBackgroundProvider));
                this.systemBarAttributesListenerProvider = DoubleCheck.provider(SystemBarAttributesListener_Factory.create(ReferenceSysUIComponentImpl.this.centralSurfacesImplProvider, this.letterboxAppearanceCalculatorProvider, ReferenceSysUIComponentImpl.this.statusBarStateControllerImplProvider, ReferenceSysUIComponentImpl.this.lightBarControllerProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
                this.centralSurfacesCommandQueueCallbacksProvider = DoubleCheck.provider(CentralSurfacesCommandQueueCallbacks_Factory.create(ReferenceSysUIComponentImpl.this.centralSurfacesImplProvider, DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideResourcesProvider, ReferenceSysUIComponentImpl.this.shadeControllerImplProvider, ReferenceSysUIComponentImpl.this.provideCommandQueueProvider, this.notificationPanelViewControllerProvider, ReferenceSysUIComponentImpl.this.remoteInputQuickSettingsDisablerProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, ReferenceSysUIComponentImpl.this.keyguardUpdateMonitorProvider, ReferenceSysUIComponentImpl.this.keyguardStateControllerImplProvider, ReferenceSysUIComponentImpl.this.provideHeadsUpManagerPhoneProvider, ReferenceSysUIComponentImpl.this.wakefulnessLifecycleProvider, ReferenceSysUIComponentImpl.this.bindDeviceProvisionedControllerProvider, ReferenceSysUIComponentImpl.this.statusBarKeyguardViewManagerProvider, ReferenceSysUIComponentImpl.this.assistManagerProvider, ReferenceSysUIComponentImpl.this.dozeServiceHostProvider, this.notificationStackScrollLayoutControllerProvider, ReferenceSysUIComponentImpl.this.statusBarHideIconsForBouncerManagerProvider, DaggerReferenceGlobalRootComponent.this.providePowerManagerProvider, ReferenceSysUIComponentImpl.this.vibratorHelperProvider, DaggerReferenceGlobalRootComponent.this.provideOptionalVibratorProvider, ReferenceSysUIComponentImpl.this.disableFlagsLoggerProvider, DaggerReferenceGlobalRootComponent.this.provideDisplayIdProvider, this.systemBarAttributesListenerProvider, ReferenceSysUIComponentImpl.this.cameraLauncherProvider));
                this.setOfOnStatusBarViewInitializedListenerProvider = SetFactory.builder(1, 0).addProvider(this.letterboxAppearanceCalculatorProvider).build();
                this.statusBarInitializerProvider = DoubleCheck.provider(StatusBarInitializer_Factory.create(ReferenceSysUIComponentImpl.this.statusBarWindowControllerProvider, this.setOfOnStatusBarViewInitializedListenerProvider));
                this.statusBarNotificationActivityStarterLoggerProvider = StatusBarNotificationActivityStarterLogger_Factory.create(ReferenceSysUIComponentImpl.this.provideNotifInteractionLogBufferProvider);
                this.statusBarNotificationPresenterProvider = DoubleCheck.provider(StatusBarNotificationPresenter_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.notificationPanelViewControllerProvider, ReferenceSysUIComponentImpl.this.provideHeadsUpManagerPhoneProvider, this.providesNotificationShadeWindowViewProvider, ReferenceSysUIComponentImpl.this.provideActivityStarterProvider, this.notificationStackScrollLayoutControllerProvider, ReferenceSysUIComponentImpl.this.dozeScrimControllerProvider, ReferenceSysUIComponentImpl.this.notificationShadeWindowControllerImplProvider, ReferenceSysUIComponentImpl.this.dynamicPrivacyControllerProvider, ReferenceSysUIComponentImpl.this.keyguardStateControllerImplProvider, ReferenceSysUIComponentImpl.this.keyguardIndicationControllerProvider, ReferenceSysUIComponentImpl.this.centralSurfacesImplProvider, ReferenceSysUIComponentImpl.this.lockscreenShadeTransitionControllerProvider, ReferenceSysUIComponentImpl.this.provideCommandQueueProvider, ReferenceSysUIComponentImpl.this.notificationLockscreenUserManagerImplProvider, ReferenceSysUIComponentImpl.this.statusBarStateControllerImplProvider, ReferenceSysUIComponentImpl.this.shadeEventCoordinatorProvider, ReferenceSysUIComponentImpl.this.provideNotificationMediaManagerProvider, ReferenceSysUIComponentImpl.this.provideNotificationGutsManagerProvider, ReferenceSysUIComponentImpl.this.lockscreenGestureLoggerProvider, ReferenceSysUIComponentImpl.this.initControllerProvider, ReferenceSysUIComponentImpl.this.notificationInterruptStateProviderImplProvider, ReferenceSysUIComponentImpl.this.provideNotificationRemoteInputManagerProvider, ReferenceSysUIComponentImpl.this.notifPipelineFlagsProvider, ReferenceSysUIComponentImpl.this.statusBarRemoteInputCallbackProvider, this.provideListContainerProvider));
                this.notificationLaunchAnimatorControllerProvider = DoubleCheck.provider(NotificationLaunchAnimatorControllerProvider_Factory.create(this.notificationShadeWindowViewControllerProvider, this.provideListContainerProvider, ReferenceSysUIComponentImpl.this.provideHeadsUpManagerPhoneProvider, DaggerReferenceGlobalRootComponent.this.provideInteractionJankMonitorProvider));
                this.statusBarNotificationActivityStarterProvider = DoubleCheck.provider(StatusBarNotificationActivityStarter_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, GlobalConcurrencyModule_ProvideHandlerFactory.create(), ReferenceSysUIComponentImpl.this.provideExecutorProvider, ReferenceSysUIComponentImpl.this.notificationVisibilityProviderImplProvider, ReferenceSysUIComponentImpl.this.provideHeadsUpManagerPhoneProvider, ReferenceSysUIComponentImpl.this.provideActivityStarterProvider, ReferenceSysUIComponentImpl.this.notificationClickNotifierProvider, ReferenceSysUIComponentImpl.this.statusBarKeyguardViewManagerProvider, DaggerReferenceGlobalRootComponent.this.provideKeyguardManagerProvider, DaggerReferenceGlobalRootComponent.this.provideIDreamManagerProvider, ReferenceSysUIComponentImpl.this.provideBubblesManagerProvider, ReferenceSysUIComponentImpl.this.assistManagerProvider, ReferenceSysUIComponentImpl.this.provideNotificationRemoteInputManagerProvider, ReferenceSysUIComponentImpl.this.notificationLockscreenUserManagerImplProvider, ReferenceSysUIComponentImpl.this.shadeControllerImplProvider, ReferenceSysUIComponentImpl.this.keyguardStateControllerImplProvider, ReferenceSysUIComponentImpl.this.notificationInterruptStateProviderImplProvider, DaggerReferenceGlobalRootComponent.this.provideLockPatternUtilsProvider, ReferenceSysUIComponentImpl.this.statusBarRemoteInputCallbackProvider, ReferenceSysUIComponentImpl.this.activityIntentHelperProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarNotificationActivityStarterLoggerProvider, ReferenceSysUIComponentImpl.this.onUserInteractionCallbackImplProvider, ReferenceSysUIComponentImpl.this.centralSurfacesImplProvider, this.statusBarNotificationPresenterProvider, this.notificationPanelViewControllerProvider, ReferenceSysUIComponentImpl.this.provideActivityLaunchAnimatorProvider, this.notificationLaunchAnimatorControllerProvider, ReferenceSysUIComponentImpl.this.launchFullScreenIntentProvider, ReferenceSysUIComponentImpl.this.featureFlagsDebugProvider));
            }

            public final OperatorNameViewController.Factory operatorNameViewControllerFactory() {
                return new OperatorNameViewController.Factory((DarkIconDispatcher) ReferenceSysUIComponentImpl.this.darkIconDispatcherImplProvider.get(), (NetworkController) ReferenceSysUIComponentImpl.this.networkControllerImplProvider.get(), (TunerService) ReferenceSysUIComponentImpl.this.tunerServiceImplProvider.get(), (TelephonyManager) DaggerReferenceGlobalRootComponent.this.provideTelephonyManagerProvider.get(), (KeyguardUpdateMonitor) ReferenceSysUIComponentImpl.this.keyguardUpdateMonitorProvider.get(), (CarrierConfigTracker) ReferenceSysUIComponentImpl.this.carrierConfigTrackerProvider.get());
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$CoordinatorsSubcomponentFactory.class */
        public final class CoordinatorsSubcomponentFactory implements CoordinatorsSubcomponent.Factory {
            public CoordinatorsSubcomponentFactory() {
            }

            public CoordinatorsSubcomponent create() {
                return new CoordinatorsSubcomponentImpl();
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$CoordinatorsSubcomponentImpl.class */
        public final class CoordinatorsSubcomponentImpl implements CoordinatorsSubcomponent {
            public Provider<AppOpsCoordinator> appOpsCoordinatorProvider;
            public Provider<BubbleCoordinator> bubbleCoordinatorProvider;
            public Provider<ConversationCoordinator> conversationCoordinatorProvider;
            public Provider<DataStoreCoordinator> dataStoreCoordinatorProvider;
            public Provider<DebugModeCoordinator> debugModeCoordinatorProvider;
            public Provider<DeviceProvisionedCoordinator> deviceProvisionedCoordinatorProvider;
            public Provider<GroupCountCoordinator> groupCountCoordinatorProvider;
            public Provider<GutsCoordinatorLogger> gutsCoordinatorLoggerProvider;
            public Provider<GutsCoordinator> gutsCoordinatorProvider;
            public Provider<HeadsUpCoordinatorLogger> headsUpCoordinatorLoggerProvider;
            public Provider<HeadsUpCoordinator> headsUpCoordinatorProvider;
            public Provider<HideLocallyDismissedNotifsCoordinator> hideLocallyDismissedNotifsCoordinatorProvider;
            public Provider<HideNotifsForOtherUsersCoordinator> hideNotifsForOtherUsersCoordinatorProvider;
            public Provider<KeyguardCoordinator> keyguardCoordinatorProvider;
            public Provider<MediaCoordinator> mediaCoordinatorProvider;
            public Provider<NotifCoordinatorsImpl> notifCoordinatorsImplProvider;
            public Provider<PreparationCoordinatorLogger> preparationCoordinatorLoggerProvider;
            public Provider<PreparationCoordinator> preparationCoordinatorProvider;
            public Provider<RankingCoordinator> rankingCoordinatorProvider;
            public Provider<RemoteInputCoordinator> remoteInputCoordinatorProvider;
            public Provider<RowAppearanceCoordinator> rowAppearanceCoordinatorProvider;
            public Provider sensitiveContentCoordinatorImplProvider;
            public Provider<SmartspaceDedupingCoordinator> smartspaceDedupingCoordinatorProvider;
            public Provider<StackCoordinator> stackCoordinatorProvider;
            public Provider<ViewConfigCoordinator> viewConfigCoordinatorProvider;

            public CoordinatorsSubcomponentImpl() {
                initialize();
            }

            public NotifCoordinators getNotifCoordinators() {
                return (NotifCoordinators) this.notifCoordinatorsImplProvider.get();
            }

            public final void initialize() {
                this.dataStoreCoordinatorProvider = DoubleCheck.provider(DataStoreCoordinator_Factory.create(ReferenceSysUIComponentImpl.this.notifLiveDataStoreImplProvider));
                this.hideLocallyDismissedNotifsCoordinatorProvider = DoubleCheck.provider(HideLocallyDismissedNotifsCoordinator_Factory.create());
                this.hideNotifsForOtherUsersCoordinatorProvider = DoubleCheck.provider(HideNotifsForOtherUsersCoordinator_Factory.create(ReferenceSysUIComponentImpl.this.notificationLockscreenUserManagerImplProvider));
                this.keyguardCoordinatorProvider = DoubleCheck.provider(KeyguardCoordinator_Factory.create(ReferenceSysUIComponentImpl.this.bgDispatcherProvider, ReferenceSysUIComponentImpl.this.keyguardNotificationVisibilityProviderImplProvider, ReferenceSysUIComponentImpl.this.keyguardRepositoryImplProvider, ReferenceSysUIComponentImpl.this.notifPipelineFlagsProvider, ReferenceSysUIComponentImpl.this.applicationScopeProvider, ReferenceSysUIComponentImpl.this.sectionHeaderVisibilityProvider, ReferenceSysUIComponentImpl.this.secureSettingsImplProvider, ReferenceSysUIComponentImpl.this.seenNotificationsProviderImplProvider, ReferenceSysUIComponentImpl.this.statusBarStateControllerImplProvider));
                this.rankingCoordinatorProvider = DoubleCheck.provider(RankingCoordinator_Factory.create(ReferenceSysUIComponentImpl.this.statusBarStateControllerImplProvider, ReferenceSysUIComponentImpl.this.highPriorityProvider, ReferenceSysUIComponentImpl.this.sectionStyleProvider, ReferenceSysUIComponentImpl.this.providesAlertingHeaderNodeControllerProvider, ReferenceSysUIComponentImpl.this.providesSilentHeaderControllerProvider, ReferenceSysUIComponentImpl.this.providesSilentHeaderNodeControllerProvider));
                this.appOpsCoordinatorProvider = DoubleCheck.provider(AppOpsCoordinator_Factory.create(ReferenceSysUIComponentImpl.this.foregroundServiceControllerProvider, ReferenceSysUIComponentImpl.this.appOpsControllerImplProvider, DaggerReferenceGlobalRootComponent.this.provideMainDelayableExecutorProvider));
                this.deviceProvisionedCoordinatorProvider = DoubleCheck.provider(DeviceProvisionedCoordinator_Factory.create(ReferenceSysUIComponentImpl.this.bindDeviceProvisionedControllerProvider, DaggerReferenceGlobalRootComponent.this.provideIPackageManagerProvider));
                this.bubbleCoordinatorProvider = DoubleCheck.provider(BubbleCoordinator_Factory.create(ReferenceSysUIComponentImpl.this.provideBubblesManagerProvider, ReferenceSysUIComponentImpl.this.setBubblesProvider, ReferenceSysUIComponentImpl.this.notifCollectionProvider));
                HeadsUpCoordinatorLogger_Factory create = HeadsUpCoordinatorLogger_Factory.create(ReferenceSysUIComponentImpl.this.provideNotificationHeadsUpLogBufferProvider);
                this.headsUpCoordinatorLoggerProvider = create;
                this.headsUpCoordinatorProvider = DoubleCheck.provider(HeadsUpCoordinator_Factory.create(create, ReferenceSysUIComponentImpl.this.bindSystemClockProvider, ReferenceSysUIComponentImpl.this.provideHeadsUpManagerPhoneProvider, ReferenceSysUIComponentImpl.this.headsUpViewBinderProvider, ReferenceSysUIComponentImpl.this.notificationInterruptStateProviderImplProvider, ReferenceSysUIComponentImpl.this.provideNotificationRemoteInputManagerProvider, ReferenceSysUIComponentImpl.this.launchFullScreenIntentProvider, ReferenceSysUIComponentImpl.this.notifPipelineFlagsProvider, ReferenceSysUIComponentImpl.this.providesIncomingHeaderNodeControllerProvider, DaggerReferenceGlobalRootComponent.this.provideMainDelayableExecutorProvider));
                this.gutsCoordinatorLoggerProvider = GutsCoordinatorLogger_Factory.create(ReferenceSysUIComponentImpl.this.provideNotificationsLogBufferProvider);
                this.gutsCoordinatorProvider = DoubleCheck.provider(GutsCoordinator_Factory.create(ReferenceSysUIComponentImpl.this.provideNotificationGutsManagerProvider, this.gutsCoordinatorLoggerProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
                this.conversationCoordinatorProvider = DoubleCheck.provider(ConversationCoordinator_Factory.create(ReferenceSysUIComponentImpl.this.peopleNotificationIdentifierImplProvider, ReferenceSysUIComponentImpl.this.iconManagerProvider, ReferenceSysUIComponentImpl.this.providesPeopleHeaderNodeControllerProvider));
                this.debugModeCoordinatorProvider = DoubleCheck.provider(DebugModeCoordinator_Factory.create(ReferenceSysUIComponentImpl.this.debugModeFilterProvider));
                this.groupCountCoordinatorProvider = DoubleCheck.provider(GroupCountCoordinator_Factory.create());
                this.mediaCoordinatorProvider = DoubleCheck.provider(MediaCoordinator_Factory.create(ReferenceSysUIComponentImpl.this.mediaFeatureFlagProvider, DaggerReferenceGlobalRootComponent.this.provideIStatusBarServiceProvider, ReferenceSysUIComponentImpl.this.iconManagerProvider));
                PreparationCoordinatorLogger_Factory create2 = PreparationCoordinatorLogger_Factory.create(ReferenceSysUIComponentImpl.this.provideNotificationsLogBufferProvider);
                this.preparationCoordinatorLoggerProvider = create2;
                this.preparationCoordinatorProvider = DoubleCheck.provider(PreparationCoordinator_Factory.create(create2, ReferenceSysUIComponentImpl.this.notifInflaterImplProvider, ReferenceSysUIComponentImpl.this.notifInflationErrorManagerProvider, ReferenceSysUIComponentImpl.this.notifViewBarnProvider, ReferenceSysUIComponentImpl.this.notifUiAdjustmentProvider, DaggerReferenceGlobalRootComponent.this.provideIStatusBarServiceProvider, ReferenceSysUIComponentImpl.this.bindEventManagerImplProvider));
                this.remoteInputCoordinatorProvider = DoubleCheck.provider(RemoteInputCoordinator_Factory.create(DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, ReferenceSysUIComponentImpl.this.remoteInputNotificationRebuilderProvider, ReferenceSysUIComponentImpl.this.provideNotificationRemoteInputManagerProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, ReferenceSysUIComponentImpl.this.provideSmartReplyControllerProvider));
                this.rowAppearanceCoordinatorProvider = DoubleCheck.provider(RowAppearanceCoordinator_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, ReferenceSysUIComponentImpl.this.assistantFeedbackControllerProvider, ReferenceSysUIComponentImpl.this.sectionStyleProvider));
                this.stackCoordinatorProvider = DoubleCheck.provider(StackCoordinator_Factory.create(ReferenceSysUIComponentImpl.this.notificationIconAreaControllerProvider));
                this.smartspaceDedupingCoordinatorProvider = DoubleCheck.provider(SmartspaceDedupingCoordinator_Factory.create(ReferenceSysUIComponentImpl.this.statusBarStateControllerImplProvider, ReferenceSysUIComponentImpl.this.lockscreenSmartspaceControllerProvider, ReferenceSysUIComponentImpl.this.notifPipelineProvider, DaggerReferenceGlobalRootComponent.this.provideMainDelayableExecutorProvider, ReferenceSysUIComponentImpl.this.bindSystemClockProvider));
                this.viewConfigCoordinatorProvider = DoubleCheck.provider(ViewConfigCoordinator_Factory.create(ReferenceSysUIComponentImpl.this.configurationControllerImplProvider, ReferenceSysUIComponentImpl.this.notificationLockscreenUserManagerImplProvider, ReferenceSysUIComponentImpl.this.provideNotificationGutsManagerProvider, ReferenceSysUIComponentImpl.this.keyguardUpdateMonitorProvider));
                this.sensitiveContentCoordinatorImplProvider = DoubleCheck.provider(SensitiveContentCoordinatorImpl_Factory.create(ReferenceSysUIComponentImpl.this.dynamicPrivacyControllerProvider, ReferenceSysUIComponentImpl.this.notificationLockscreenUserManagerImplProvider, ReferenceSysUIComponentImpl.this.keyguardUpdateMonitorProvider, ReferenceSysUIComponentImpl.this.statusBarStateControllerImplProvider, ReferenceSysUIComponentImpl.this.keyguardStateControllerImplProvider));
                this.notifCoordinatorsImplProvider = DoubleCheck.provider(NotifCoordinatorsImpl_Factory.create(ReferenceSysUIComponentImpl.this.notifPipelineFlagsProvider, this.dataStoreCoordinatorProvider, this.hideLocallyDismissedNotifsCoordinatorProvider, this.hideNotifsForOtherUsersCoordinatorProvider, this.keyguardCoordinatorProvider, this.rankingCoordinatorProvider, this.appOpsCoordinatorProvider, this.deviceProvisionedCoordinatorProvider, this.bubbleCoordinatorProvider, this.headsUpCoordinatorProvider, this.gutsCoordinatorProvider, this.conversationCoordinatorProvider, this.debugModeCoordinatorProvider, this.groupCountCoordinatorProvider, this.mediaCoordinatorProvider, this.preparationCoordinatorProvider, this.remoteInputCoordinatorProvider, this.rowAppearanceCoordinatorProvider, this.stackCoordinatorProvider, ReferenceSysUIComponentImpl.this.shadeEventCoordinatorProvider, this.smartspaceDedupingCoordinatorProvider, this.viewConfigCoordinatorProvider, ReferenceSysUIComponentImpl.this.visualStabilityCoordinatorProvider, this.sensitiveContentCoordinatorImplProvider));
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$DozeComponentFactory.class */
        public final class DozeComponentFactory implements DozeComponent.Builder {
            public DozeComponentFactory() {
            }

            @Override // com.android.systemui.doze.dagger.DozeComponent.Builder
            public DozeComponent build(DozeMachine.Service service) {
                Preconditions.checkNotNull(service);
                return new DozeComponentImpl(service);
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$DozeComponentImpl.class */
        public final class DozeComponentImpl implements DozeComponent {
            public Provider<DozeAuthRemover> dozeAuthRemoverProvider;
            public Provider<DozeDockHandler> dozeDockHandlerProvider;
            public Provider<DozeFalsingManagerAdapter> dozeFalsingManagerAdapterProvider;
            public Provider<DozeMachine> dozeMachineProvider;
            public Provider<DozeMachine.Service> dozeMachineServiceProvider;
            public Provider<DozePauser> dozePauserProvider;
            public Provider<DozeScreenBrightness> dozeScreenBrightnessProvider;
            public Provider<DozeScreenState> dozeScreenStateProvider;
            public Provider<DozeSuppressor> dozeSuppressorProvider;
            public Provider<DozeTriggers> dozeTriggersProvider;
            public Provider<DozeUi> dozeUiProvider;
            public Provider<DozeWallpaperState> dozeWallpaperStateProvider;
            public Provider<Optional<Sensor>[]> providesBrightnessSensorsProvider;
            public Provider<DozeMachine.Part[]> providesDozeMachinePartsProvider;
            public Provider<WakeLock> providesDozeWakeLockProvider;
            public Provider<DozeMachine.Service> providesWrappedServiceProvider;

            public DozeComponentImpl(DozeMachine.Service service) {
                initialize(service);
            }

            @Override // com.android.systemui.doze.dagger.DozeComponent
            public DozeMachine getDozeMachine() {
                return (DozeMachine) this.dozeMachineProvider.get();
            }

            public final void initialize(DozeMachine.Service service) {
                Factory create = InstanceFactory.create(service);
                this.dozeMachineServiceProvider = create;
                this.providesWrappedServiceProvider = DoubleCheck.provider(DozeModule_ProvidesWrappedServiceFactory.create(create, ReferenceSysUIComponentImpl.this.dozeServiceHostProvider, ReferenceSysUIComponentImpl.this.dozeParametersProvider));
                this.providesDozeWakeLockProvider = DoubleCheck.provider(DozeModule_ProvidesDozeWakeLockFactory.create(ReferenceSysUIComponentImpl.this.builderProvider4, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider));
                this.dozePauserProvider = DoubleCheck.provider(DozePauser_Factory.create(DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, DaggerReferenceGlobalRootComponent.this.provideAlarmManagerProvider, ReferenceSysUIComponentImpl.this.alwaysOnDisplayPolicyProvider));
                this.dozeFalsingManagerAdapterProvider = DoubleCheck.provider(DozeFalsingManagerAdapter_Factory.create(ReferenceSysUIComponentImpl.this.falsingCollectorImplProvider));
                this.dozeTriggersProvider = DoubleCheck.provider(DozeTriggers_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, ReferenceSysUIComponentImpl.this.dozeServiceHostProvider, DaggerReferenceGlobalRootComponent.this.provideAmbientDisplayConfigurationProvider, ReferenceSysUIComponentImpl.this.dozeParametersProvider, ReferenceSysUIComponentImpl.this.asyncSensorManagerProvider, this.providesDozeWakeLockProvider, ReferenceSysUIComponentImpl.this.dockManagerImplProvider, ReferenceSysUIComponentImpl.this.provideProximitySensorProvider, ReferenceSysUIComponentImpl.this.provideProximityCheckProvider, ReferenceSysUIComponentImpl.this.dozeLogProvider, ReferenceSysUIComponentImpl.this.broadcastDispatcherProvider, ReferenceSysUIComponentImpl.this.secureSettingsImplProvider, ReferenceSysUIComponentImpl.this.authControllerProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, ReferenceSysUIComponentImpl.this.sessionTrackerProvider, ReferenceSysUIComponentImpl.this.keyguardStateControllerImplProvider, ReferenceSysUIComponentImpl.this.devicePostureControllerImplProvider, ReferenceSysUIComponentImpl.this.provideUserTrackerProvider));
                this.dozeUiProvider = DoubleCheck.provider(DozeUi_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideAlarmManagerProvider, this.providesDozeWakeLockProvider, ReferenceSysUIComponentImpl.this.dozeServiceHostProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, ReferenceSysUIComponentImpl.this.dozeParametersProvider, ReferenceSysUIComponentImpl.this.statusBarStateControllerImplProvider, ReferenceSysUIComponentImpl.this.dozeLogProvider));
                this.providesBrightnessSensorsProvider = DozeModule_ProvidesBrightnessSensorsFactory.create(ReferenceSysUIComponentImpl.this.asyncSensorManagerProvider, DaggerReferenceGlobalRootComponent.this.contextProvider, ReferenceSysUIComponentImpl.this.dozeParametersProvider);
                this.dozeScreenBrightnessProvider = DoubleCheck.provider(DozeScreenBrightness_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.providesWrappedServiceProvider, ReferenceSysUIComponentImpl.this.asyncSensorManagerProvider, this.providesBrightnessSensorsProvider, ReferenceSysUIComponentImpl.this.dozeServiceHostProvider, GlobalConcurrencyModule_ProvideHandlerFactory.create(), ReferenceSysUIComponentImpl.this.alwaysOnDisplayPolicyProvider, ReferenceSysUIComponentImpl.this.wakefulnessLifecycleProvider, ReferenceSysUIComponentImpl.this.dozeParametersProvider, ReferenceSysUIComponentImpl.this.devicePostureControllerImplProvider, ReferenceSysUIComponentImpl.this.dozeLogProvider));
                this.dozeScreenStateProvider = DoubleCheck.provider(DozeScreenState_Factory.create(this.providesWrappedServiceProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, ReferenceSysUIComponentImpl.this.dozeServiceHostProvider, ReferenceSysUIComponentImpl.this.dozeParametersProvider, this.providesDozeWakeLockProvider, ReferenceSysUIComponentImpl.this.authControllerProvider, ReferenceSysUIComponentImpl.this.udfpsControllerProvider, ReferenceSysUIComponentImpl.this.dozeLogProvider, this.dozeScreenBrightnessProvider));
                this.dozeWallpaperStateProvider = DoubleCheck.provider(DozeWallpaperState_Factory.create(FrameworkServicesModule_ProvideIWallPaperManagerFactory.create(), ReferenceSysUIComponentImpl.this.biometricUnlockControllerProvider, ReferenceSysUIComponentImpl.this.dozeParametersProvider));
                this.dozeDockHandlerProvider = DoubleCheck.provider(DozeDockHandler_Factory.create(DaggerReferenceGlobalRootComponent.this.provideAmbientDisplayConfigurationProvider, ReferenceSysUIComponentImpl.this.dockManagerImplProvider));
                this.dozeAuthRemoverProvider = DoubleCheck.provider(DozeAuthRemover_Factory.create(ReferenceSysUIComponentImpl.this.keyguardUpdateMonitorProvider));
                Provider<DozeSuppressor> provider = DoubleCheck.provider(DozeSuppressor_Factory.create(ReferenceSysUIComponentImpl.this.dozeServiceHostProvider, DaggerReferenceGlobalRootComponent.this.provideAmbientDisplayConfigurationProvider, ReferenceSysUIComponentImpl.this.dozeLogProvider, ReferenceSysUIComponentImpl.this.biometricUnlockControllerProvider));
                this.dozeSuppressorProvider = provider;
                this.providesDozeMachinePartsProvider = DozeModule_ProvidesDozeMachinePartsFactory.create(this.dozePauserProvider, this.dozeFalsingManagerAdapterProvider, this.dozeTriggersProvider, this.dozeUiProvider, this.dozeScreenStateProvider, this.dozeScreenBrightnessProvider, this.dozeWallpaperStateProvider, this.dozeDockHandlerProvider, this.dozeAuthRemoverProvider, provider, ReferenceSysUIComponentImpl.this.dozeTransitionListenerProvider);
                this.dozeMachineProvider = DoubleCheck.provider(DozeMachine_Factory.create(this.providesWrappedServiceProvider, DaggerReferenceGlobalRootComponent.this.provideAmbientDisplayConfigurationProvider, this.providesDozeWakeLockProvider, ReferenceSysUIComponentImpl.this.wakefulnessLifecycleProvider, ReferenceSysUIComponentImpl.this.dozeLogProvider, ReferenceSysUIComponentImpl.this.dockManagerImplProvider, ReferenceSysUIComponentImpl.this.dozeServiceHostProvider, this.providesDozeMachinePartsProvider));
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$DreamOverlayComponentFactory.class */
        public final class DreamOverlayComponentFactory implements DreamOverlayComponent.Factory {
            public DreamOverlayComponentFactory() {
            }

            @Override // com.android.systemui.dreams.dagger.DreamOverlayComponent.Factory
            public DreamOverlayComponent create(ViewModelStore viewModelStore, Complication.Host host) {
                Preconditions.checkNotNull(viewModelStore);
                Preconditions.checkNotNull(host);
                return new DreamOverlayComponentImpl(viewModelStore, host);
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$DreamOverlayComponentImpl.class */
        public final class DreamOverlayComponentImpl implements DreamOverlayComponent {
            public Provider<FlingAnimationUtils.Builder> builderProvider;
            public Provider<ComplicationCollectionLiveData> complicationCollectionLiveDataProvider;
            public Provider<ComplicationCollectionViewModel> complicationCollectionViewModelProvider;
            public Provider<ComplicationHostViewController> complicationHostViewControllerProvider;
            public Provider<ComplicationLayoutEngine> complicationLayoutEngineProvider;
            public Provider<ComplicationViewModelComponent.Factory> complicationViewModelComponentFactoryProvider;
            public Provider<ComplicationViewModelTransformer> complicationViewModelTransformerProvider;
            public Provider<DreamOverlayAnimationsController> dreamOverlayAnimationsControllerProvider;
            public Provider<DreamOverlayContainerViewController> dreamOverlayContainerViewControllerProvider;
            public Provider<DreamOverlayStatusBarViewController> dreamOverlayStatusBarViewControllerProvider;
            public final Complication.Host host;
            public Provider<Long> providesBurnInProtectionUpdateIntervalProvider;
            public Provider<ComplicationCollectionViewModel> providesComplicationCollectionViewModelProvider;
            public Provider<ConstraintLayout> providesComplicationHostViewProvider;
            public Provider<Integer> providesComplicationPaddingProvider;
            public Provider<Integer> providesComplicationsFadeInDurationProvider;
            public Provider<Integer> providesComplicationsFadeOutDelayProvider;
            public Provider<Integer> providesComplicationsFadeOutDurationProvider;
            public Provider<Integer> providesComplicationsRestoreTimeoutProvider;
            public Provider<Integer> providesDreamBlurRadiusProvider;
            public Provider<Long> providesDreamInBlurAnimationDurationProvider;
            public Provider<Long> providesDreamInComplicationsAnimationDurationProvider;
            public Provider<Long> providesDreamInComplicationsTranslationYDurationProvider;
            public Provider<Integer> providesDreamInComplicationsTranslationYProvider;
            public Provider<DreamOverlayContainerView> providesDreamOverlayContainerViewProvider;
            public Provider<ViewGroup> providesDreamOverlayContentViewProvider;
            public Provider<DreamOverlayStatusBarView> providesDreamOverlayStatusBarViewProvider;
            public Provider<LifecycleOwner> providesLifecycleOwnerProvider;
            public Provider<Lifecycle> providesLifecycleProvider;
            public Provider<LifecycleRegistry> providesLifecycleRegistryProvider;
            public Provider<Integer> providesMaxBurnInOffsetProvider;
            public Provider<Long> providesMillisUntilFullJitterProvider;
            public Provider<TouchInsetManager> providesTouchInsetManagerProvider;
            public Provider<TouchInsetManager.TouchInsetSession> providesTouchInsetSessionProvider;
            public final ViewModelStore store;
            public Provider<ViewModelStore> storeProvider;

            /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$DreamOverlayComponentImpl$ComplicationViewModelComponentFactory.class */
            public final class ComplicationViewModelComponentFactory implements ComplicationViewModelComponent.Factory {
                public ComplicationViewModelComponentFactory() {
                }

                @Override // com.android.systemui.dreams.complication.dagger.ComplicationViewModelComponent.Factory
                public ComplicationViewModelComponent create(Complication complication, ComplicationId complicationId) {
                    Preconditions.checkNotNull(complication);
                    Preconditions.checkNotNull(complicationId);
                    return new ComplicationViewModelComponentI(complication, complicationId);
                }
            }

            /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$DreamOverlayComponentImpl$ComplicationViewModelComponentI.class */
            public final class ComplicationViewModelComponentI implements ComplicationViewModelComponent {
                public final Complication complication;
                public final ComplicationId id;

                public ComplicationViewModelComponentI(Complication complication, ComplicationId complicationId) {
                    this.complication = complication;
                    this.id = complicationId;
                }

                public final ComplicationViewModel complicationViewModel() {
                    return new ComplicationViewModel(this.complication, this.id, DreamOverlayComponentImpl.this.host);
                }

                @Override // com.android.systemui.dreams.complication.dagger.ComplicationViewModelComponent
                public ComplicationViewModelProvider getViewModelProvider() {
                    return new ComplicationViewModelProvider(DreamOverlayComponentImpl.this.store, complicationViewModel());
                }
            }

            /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$DreamOverlayComponentImpl$InputSessionComponentFactory.class */
            public final class InputSessionComponentFactory implements InputSessionComponent.Factory {
                public InputSessionComponentFactory() {
                }

                @Override // com.android.systemui.dreams.touch.dagger.InputSessionComponent.Factory
                public InputSessionComponent create(String str, InputChannelCompat.InputEventListener inputEventListener, GestureDetector.OnGestureListener onGestureListener, boolean z) {
                    Preconditions.checkNotNull(str);
                    Preconditions.checkNotNull(inputEventListener);
                    Preconditions.checkNotNull(onGestureListener);
                    Preconditions.checkNotNull(Boolean.valueOf(z));
                    return new InputSessionComponentI(str, inputEventListener, onGestureListener, Boolean.valueOf(z));
                }
            }

            /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$DreamOverlayComponentImpl$InputSessionComponentI.class */
            public final class InputSessionComponentI implements InputSessionComponent {
                public final GestureDetector.OnGestureListener gestureListener;
                public final InputChannelCompat.InputEventListener inputEventListener;
                public final String name;
                public final Boolean pilferOnGestureConsume;

                public InputSessionComponentI(String str, InputChannelCompat.InputEventListener inputEventListener, GestureDetector.OnGestureListener onGestureListener, Boolean bool) {
                    this.name = str;
                    this.inputEventListener = inputEventListener;
                    this.gestureListener = onGestureListener;
                    this.pilferOnGestureConsume = bool;
                }

                @Override // com.android.systemui.dreams.touch.dagger.InputSessionComponent
                public InputSession getInputSession() {
                    return new InputSession(this.name, this.inputEventListener, this.gestureListener, this.pilferOnGestureConsume.booleanValue());
                }
            }

            public DreamOverlayComponentImpl(ViewModelStore viewModelStore, Complication.Host host) {
                this.store = viewModelStore;
                this.host = host;
                initialize(viewModelStore, host);
            }

            public final BouncerSwipeTouchHandler bouncerSwipeTouchHandler() {
                return new BouncerSwipeTouchHandler(DaggerReferenceGlobalRootComponent.this.displayMetrics(), (StatusBarKeyguardViewManager) ReferenceSysUIComponentImpl.this.statusBarKeyguardViewManagerProvider.get(), Optional.of((CentralSurfaces) ReferenceSysUIComponentImpl.this.centralSurfacesImplProvider.get()), (NotificationShadeWindowController) ReferenceSysUIComponentImpl.this.notificationShadeWindowControllerImplProvider.get(), BouncerSwipeModule_ProvidesValueAnimatorCreatorFactory.providesValueAnimatorCreator(), BouncerSwipeModule_ProvidesVelocityTrackerFactoryFactory.providesVelocityTrackerFactory(), namedFlingAnimationUtils(), namedFlingAnimationUtils2(), namedFloat(), (UiEventLogger) DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider.get());
            }

            @Override // com.android.systemui.dreams.dagger.DreamOverlayComponent
            public DreamOverlayContainerViewController getDreamOverlayContainerViewController() {
                return (DreamOverlayContainerViewController) this.dreamOverlayContainerViewControllerProvider.get();
            }

            @Override // com.android.systemui.dreams.dagger.DreamOverlayComponent
            public DreamOverlayTouchMonitor getDreamOverlayTouchMonitor() {
                return new DreamOverlayTouchMonitor((Executor) DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider.get(), (Lifecycle) this.providesLifecycleProvider.get(), new InputSessionComponentFactory(), setOfDreamTouchHandler());
            }

            public LifecycleOwner getLifecycleOwner() {
                return (LifecycleOwner) this.providesLifecycleOwnerProvider.get();
            }

            @Override // com.android.systemui.dreams.dagger.DreamOverlayComponent
            public LifecycleRegistry getLifecycleRegistry() {
                return (LifecycleRegistry) this.providesLifecycleRegistryProvider.get();
            }

            public final HideComplicationTouchHandler hideComplicationTouchHandler() {
                return HideComplicationTouchHandler_Factory.newInstance(visibilityController(), ((Integer) this.providesComplicationsRestoreTimeoutProvider.get()).intValue(), ((Integer) this.providesComplicationsFadeOutDelayProvider.get()).intValue(), (TouchInsetManager) this.providesTouchInsetManagerProvider.get(), (StatusBarKeyguardViewManager) ReferenceSysUIComponentImpl.this.statusBarKeyguardViewManagerProvider.get(), (DelayableExecutor) DaggerReferenceGlobalRootComponent.this.provideMainDelayableExecutorProvider.get(), (DreamOverlayStateController) ReferenceSysUIComponentImpl.this.dreamOverlayStateControllerProvider.get());
            }

            public final void initialize(ViewModelStore viewModelStore, Complication.Host host) {
                this.providesDreamOverlayContainerViewProvider = DoubleCheck.provider(DreamOverlayModule_ProvidesDreamOverlayContainerViewFactory.create(DaggerReferenceGlobalRootComponent.this.providerLayoutInflaterProvider));
                this.providesComplicationHostViewProvider = DoubleCheck.provider(ComplicationHostViewModule_ProvidesComplicationHostViewFactory.create(DaggerReferenceGlobalRootComponent.this.providerLayoutInflaterProvider));
                this.providesComplicationPaddingProvider = DoubleCheck.provider(ComplicationHostViewModule_ProvidesComplicationPaddingFactory.create(DaggerReferenceGlobalRootComponent.this.provideResourcesProvider));
                Provider<TouchInsetManager> provider = DoubleCheck.provider(DreamOverlayModule_ProvidesTouchInsetManagerFactory.create(DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.providesDreamOverlayContainerViewProvider));
                this.providesTouchInsetManagerProvider = provider;
                this.providesTouchInsetSessionProvider = DreamOverlayModule_ProvidesTouchInsetSessionFactory.create(provider);
                this.providesComplicationsFadeInDurationProvider = DoubleCheck.provider(ComplicationHostViewModule_ProvidesComplicationsFadeInDurationFactory.create(DaggerReferenceGlobalRootComponent.this.provideResourcesProvider));
                Provider<Integer> provider2 = DoubleCheck.provider(ComplicationHostViewModule_ProvidesComplicationsFadeOutDurationFactory.create(DaggerReferenceGlobalRootComponent.this.provideResourcesProvider));
                this.providesComplicationsFadeOutDurationProvider = provider2;
                this.complicationLayoutEngineProvider = DoubleCheck.provider(ComplicationLayoutEngine_Factory.create(this.providesComplicationHostViewProvider, this.providesComplicationPaddingProvider, this.providesTouchInsetSessionProvider, this.providesComplicationsFadeInDurationProvider, provider2));
                DelegateFactory delegateFactory = new DelegateFactory();
                this.providesLifecycleOwnerProvider = delegateFactory;
                Provider<LifecycleRegistry> provider3 = DoubleCheck.provider(DreamOverlayModule_ProvidesLifecycleRegistryFactory.create(delegateFactory));
                this.providesLifecycleRegistryProvider = provider3;
                DelegateFactory.setDelegate(this.providesLifecycleOwnerProvider, DoubleCheck.provider(DreamOverlayModule_ProvidesLifecycleOwnerFactory.create(provider3)));
                this.storeProvider = InstanceFactory.create(viewModelStore);
                this.complicationCollectionLiveDataProvider = ComplicationCollectionLiveData_Factory.create(ReferenceSysUIComponentImpl.this.dreamOverlayStateControllerProvider);
                Provider<ComplicationViewModelComponent.Factory> provider4 = new Provider<ComplicationViewModelComponent.Factory>() { // from class: com.android.systemui.dagger.DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.DreamOverlayComponentImpl.1
                    /* JADX DEBUG: Method merged with bridge method */
                    /* renamed from: get */
                    public ComplicationViewModelComponent.Factory m2253get() {
                        return new ComplicationViewModelComponentFactory();
                    }
                };
                this.complicationViewModelComponentFactoryProvider = provider4;
                ComplicationViewModelTransformer_Factory create = ComplicationViewModelTransformer_Factory.create(provider4);
                this.complicationViewModelTransformerProvider = create;
                ComplicationCollectionViewModel_Factory create2 = ComplicationCollectionViewModel_Factory.create(this.complicationCollectionLiveDataProvider, create);
                this.complicationCollectionViewModelProvider = create2;
                this.providesComplicationCollectionViewModelProvider = ComplicationModule_ProvidesComplicationCollectionViewModelFactory.create(this.storeProvider, create2);
                this.complicationHostViewControllerProvider = ComplicationHostViewController_Factory.create(this.providesComplicationHostViewProvider, this.complicationLayoutEngineProvider, ReferenceSysUIComponentImpl.this.dreamOverlayStateControllerProvider, this.providesLifecycleOwnerProvider, this.providesComplicationCollectionViewModelProvider);
                this.providesDreamOverlayContentViewProvider = DoubleCheck.provider(DreamOverlayModule_ProvidesDreamOverlayContentViewFactory.create(this.providesDreamOverlayContainerViewProvider));
                Provider<DreamOverlayStatusBarView> provider5 = DoubleCheck.provider(DreamOverlayModule_ProvidesDreamOverlayStatusBarViewFactory.create(this.providesDreamOverlayContainerViewProvider));
                this.providesDreamOverlayStatusBarViewProvider = provider5;
                this.dreamOverlayStatusBarViewControllerProvider = DoubleCheck.provider(DreamOverlayStatusBarViewController_Factory.create(provider5, DaggerReferenceGlobalRootComponent.this.provideResourcesProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, DaggerReferenceGlobalRootComponent.this.provideConnectivityManagagerProvider, this.providesTouchInsetSessionProvider, DaggerReferenceGlobalRootComponent.this.provideAlarmManagerProvider, ReferenceSysUIComponentImpl.this.nextAlarmControllerImplProvider, ReferenceSysUIComponentImpl.this.dateFormatUtilProvider, ReferenceSysUIComponentImpl.this.provideIndividualSensorPrivacyControllerProvider, ReferenceSysUIComponentImpl.this.providesDreamOverlayNotificationCountProvider, ReferenceSysUIComponentImpl.this.zenModeControllerImplProvider, ReferenceSysUIComponentImpl.this.statusBarWindowStateControllerProvider, ReferenceSysUIComponentImpl.this.dreamOverlayStatusBarItemsProvider, ReferenceSysUIComponentImpl.this.dreamOverlayStateControllerProvider));
                this.providesMaxBurnInOffsetProvider = DoubleCheck.provider(DreamOverlayModule_ProvidesMaxBurnInOffsetFactory.create(DaggerReferenceGlobalRootComponent.this.provideResourcesProvider));
                this.providesBurnInProtectionUpdateIntervalProvider = DreamOverlayModule_ProvidesBurnInProtectionUpdateIntervalFactory.create(DaggerReferenceGlobalRootComponent.this.provideResourcesProvider);
                this.providesMillisUntilFullJitterProvider = DreamOverlayModule_ProvidesMillisUntilFullJitterFactory.create(DaggerReferenceGlobalRootComponent.this.provideResourcesProvider);
                this.providesDreamBlurRadiusProvider = DreamOverlayModule_ProvidesDreamBlurRadiusFactory.create(DaggerReferenceGlobalRootComponent.this.provideResourcesProvider);
                this.providesDreamInBlurAnimationDurationProvider = DreamOverlayModule_ProvidesDreamInBlurAnimationDurationFactory.create(DaggerReferenceGlobalRootComponent.this.provideResourcesProvider);
                this.providesDreamInComplicationsAnimationDurationProvider = DreamOverlayModule_ProvidesDreamInComplicationsAnimationDurationFactory.create(DaggerReferenceGlobalRootComponent.this.provideResourcesProvider);
                this.providesDreamInComplicationsTranslationYProvider = DoubleCheck.provider(DreamOverlayModule_ProvidesDreamInComplicationsTranslationYFactory.create(DaggerReferenceGlobalRootComponent.this.provideResourcesProvider));
                this.providesDreamInComplicationsTranslationYDurationProvider = DoubleCheck.provider(DreamOverlayModule_ProvidesDreamInComplicationsTranslationYDurationFactory.create(DaggerReferenceGlobalRootComponent.this.provideResourcesProvider));
                this.dreamOverlayAnimationsControllerProvider = DreamOverlayAnimationsController_Factory.create(ReferenceSysUIComponentImpl.this.blurUtilsProvider, this.complicationHostViewControllerProvider, this.dreamOverlayStatusBarViewControllerProvider, ReferenceSysUIComponentImpl.this.dreamOverlayStateControllerProvider, this.providesDreamBlurRadiusProvider, ReferenceSysUIComponentImpl.this.dreamingToLockscreenTransitionViewModelProvider, ReferenceSysUIComponentImpl.this.configurationControllerImplProvider, this.providesDreamInBlurAnimationDurationProvider, this.providesDreamInComplicationsAnimationDurationProvider, this.providesDreamInComplicationsTranslationYProvider, this.providesDreamInComplicationsTranslationYDurationProvider);
                this.dreamOverlayContainerViewControllerProvider = DoubleCheck.provider(DreamOverlayContainerViewController_Factory.create(this.providesDreamOverlayContainerViewProvider, this.complicationHostViewControllerProvider, this.providesDreamOverlayContentViewProvider, this.dreamOverlayStatusBarViewControllerProvider, ReferenceSysUIComponentImpl.this.statusBarKeyguardViewManagerProvider, ReferenceSysUIComponentImpl.this.blurUtilsProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, DaggerReferenceGlobalRootComponent.this.provideResourcesProvider, this.providesMaxBurnInOffsetProvider, this.providesBurnInProtectionUpdateIntervalProvider, this.providesMillisUntilFullJitterProvider, ReferenceSysUIComponentImpl.this.primaryBouncerCallbackInteractorProvider, this.dreamOverlayAnimationsControllerProvider, ReferenceSysUIComponentImpl.this.dreamOverlayStateControllerProvider));
                this.providesLifecycleProvider = DoubleCheck.provider(DreamOverlayModule_ProvidesLifecycleFactory.create(this.providesLifecycleOwnerProvider));
                this.builderProvider = FlingAnimationUtils_Builder_Factory.create(DaggerReferenceGlobalRootComponent.this.provideDisplayMetricsProvider);
                this.providesComplicationsRestoreTimeoutProvider = DoubleCheck.provider(ComplicationHostViewModule_ProvidesComplicationsRestoreTimeoutFactory.create(DaggerReferenceGlobalRootComponent.this.provideResourcesProvider));
                this.providesComplicationsFadeOutDelayProvider = DoubleCheck.provider(ComplicationHostViewModule_ProvidesComplicationsFadeOutDelayFactory.create(DaggerReferenceGlobalRootComponent.this.provideResourcesProvider));
            }

            public final FlingAnimationUtils namedFlingAnimationUtils() {
                return BouncerSwipeModule_ProvidesSwipeToBouncerFlingAnimationUtilsOpeningFactory.providesSwipeToBouncerFlingAnimationUtilsOpening(this.builderProvider);
            }

            public final FlingAnimationUtils namedFlingAnimationUtils2() {
                return BouncerSwipeModule_ProvidesSwipeToBouncerFlingAnimationUtilsClosingFactory.providesSwipeToBouncerFlingAnimationUtilsClosing(this.builderProvider);
            }

            public final float namedFloat() {
                return BouncerSwipeModule.providesSwipeToBouncerStartRegion(DaggerReferenceGlobalRootComponent.this.mainResources());
            }

            public final DreamTouchHandler providesBouncerSwipeTouchHandler() {
                return BouncerSwipeModule_ProvidesBouncerSwipeTouchHandlerFactory.providesBouncerSwipeTouchHandler(bouncerSwipeTouchHandler());
            }

            public final DreamTouchHandler providesHideComplicationTouchHandler() {
                return HideComplicationModule_ProvidesHideComplicationTouchHandlerFactory.providesHideComplicationTouchHandler(hideComplicationTouchHandler());
            }

            public final Set<DreamTouchHandler> setOfDreamTouchHandler() {
                return SetBuilder.newSetBuilder(2).add(providesBouncerSwipeTouchHandler()).add(providesHideComplicationTouchHandler()).build();
            }

            public final Complication.VisibilityController visibilityController() {
                return ComplicationModule_ProvidesVisibilityControllerFactory.providesVisibilityController((ComplicationLayoutEngine) this.complicationLayoutEngineProvider.get());
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$ExpandableNotificationRowComponentBuilder.class */
        public final class ExpandableNotificationRowComponentBuilder implements ExpandableNotificationRowComponent.Builder {
            public ExpandableNotificationRow expandableNotificationRow;
            public NotificationListContainer listContainer;
            public NotificationEntry notificationEntry;
            public ExpandableNotificationRow.OnExpandClickListener onExpandClickListener;

            public ExpandableNotificationRowComponentBuilder() {
            }

            public ExpandableNotificationRowComponent build() {
                Preconditions.checkBuilderRequirement(this.expandableNotificationRow, ExpandableNotificationRow.class);
                Preconditions.checkBuilderRequirement(this.notificationEntry, NotificationEntry.class);
                Preconditions.checkBuilderRequirement(this.onExpandClickListener, ExpandableNotificationRow.OnExpandClickListener.class);
                Preconditions.checkBuilderRequirement(this.listContainer, NotificationListContainer.class);
                return new ExpandableNotificationRowComponentImpl(this.expandableNotificationRow, this.notificationEntry, this.onExpandClickListener, this.listContainer);
            }

            /* JADX DEBUG: Method merged with bridge method */
            /* renamed from: expandableNotificationRow */
            public ExpandableNotificationRowComponentBuilder m2254expandableNotificationRow(ExpandableNotificationRow expandableNotificationRow) {
                this.expandableNotificationRow = (ExpandableNotificationRow) Preconditions.checkNotNull(expandableNotificationRow);
                return this;
            }

            /* JADX DEBUG: Method merged with bridge method */
            /* renamed from: listContainer */
            public ExpandableNotificationRowComponentBuilder m2255listContainer(NotificationListContainer notificationListContainer) {
                this.listContainer = (NotificationListContainer) Preconditions.checkNotNull(notificationListContainer);
                return this;
            }

            /* JADX DEBUG: Method merged with bridge method */
            /* renamed from: notificationEntry */
            public ExpandableNotificationRowComponentBuilder m2256notificationEntry(NotificationEntry notificationEntry) {
                this.notificationEntry = (NotificationEntry) Preconditions.checkNotNull(notificationEntry);
                return this;
            }

            /* JADX DEBUG: Method merged with bridge method */
            /* renamed from: onExpandClickListener */
            public ExpandableNotificationRowComponentBuilder m2257onExpandClickListener(ExpandableNotificationRow.OnExpandClickListener onExpandClickListener) {
                this.onExpandClickListener = (ExpandableNotificationRow.OnExpandClickListener) Preconditions.checkNotNull(onExpandClickListener);
                return this;
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$ExpandableNotificationRowComponentImpl.class */
        public final class ExpandableNotificationRowComponentImpl implements ExpandableNotificationRowComponent {
            public Provider<ActivatableNotificationViewController> activatableNotificationViewControllerProvider;
            public Provider<ExpandableNotificationRowController> expandableNotificationRowControllerProvider;
            public Provider<ExpandableNotificationRowDragController> expandableNotificationRowDragControllerProvider;
            public Provider<ExpandableNotificationRow> expandableNotificationRowProvider;
            public Provider<ExpandableOutlineViewController> expandableOutlineViewControllerProvider;
            public Provider<ExpandableViewController> expandableViewControllerProvider;
            public Provider<NotificationTapHelper.Factory> factoryProvider;
            public Provider<NotificationListContainer> listContainerProvider;
            public final NotificationEntry notificationEntry;
            public Provider<NotificationEntry> notificationEntryProvider;
            public Provider<NotificationRowLogger> notificationRowLoggerProvider;
            public Provider<ExpandableNotificationRow.OnExpandClickListener> onExpandClickListenerProvider;
            public Provider<String> provideAppNameProvider;
            public Provider<String> provideNotificationKeyProvider;
            public Provider<StatusBarNotification> provideStatusBarNotificationProvider;
            public Provider<RemoteInputViewSubcomponent.Factory> remoteInputViewSubcomponentFactoryProvider;

            /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$ExpandableNotificationRowComponentImpl$RemoteInputViewSubcomponentFactory.class */
            public final class RemoteInputViewSubcomponentFactory implements RemoteInputViewSubcomponent.Factory {
                public RemoteInputViewSubcomponentFactory() {
                }

                public RemoteInputViewSubcomponent create(RemoteInputView remoteInputView, RemoteInputController remoteInputController) {
                    Preconditions.checkNotNull(remoteInputView);
                    Preconditions.checkNotNull(remoteInputController);
                    return new RemoteInputViewSubcomponentI(remoteInputView, remoteInputController);
                }
            }

            /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$ExpandableNotificationRowComponentImpl$RemoteInputViewSubcomponentI.class */
            public final class RemoteInputViewSubcomponentI implements RemoteInputViewSubcomponent {
                public final RemoteInputController remoteInputController;
                public final RemoteInputView view;

                public RemoteInputViewSubcomponentI(RemoteInputView remoteInputView, RemoteInputController remoteInputController) {
                    this.view = remoteInputView;
                    this.remoteInputController = remoteInputController;
                }

                public RemoteInputViewController getController() {
                    return remoteInputViewControllerImpl();
                }

                public final RemoteInputViewControllerImpl remoteInputViewControllerImpl() {
                    return new RemoteInputViewControllerImpl(this.view, ExpandableNotificationRowComponentImpl.this.notificationEntry, (RemoteInputQuickSettingsDisabler) ReferenceSysUIComponentImpl.this.remoteInputQuickSettingsDisablerProvider.get(), this.remoteInputController, (ShortcutManager) DaggerReferenceGlobalRootComponent.this.provideShortcutManagerProvider.get(), (UiEventLogger) DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider.get(), (FeatureFlags) ReferenceSysUIComponentImpl.this.featureFlagsDebugProvider.get());
                }
            }

            public ExpandableNotificationRowComponentImpl(ExpandableNotificationRow expandableNotificationRow, NotificationEntry notificationEntry, ExpandableNotificationRow.OnExpandClickListener onExpandClickListener, NotificationListContainer notificationListContainer) {
                this.notificationEntry = notificationEntry;
                initialize(expandableNotificationRow, notificationEntry, onExpandClickListener, notificationListContainer);
            }

            public ExpandableNotificationRowController getExpandableNotificationRowController() {
                return (ExpandableNotificationRowController) this.expandableNotificationRowControllerProvider.get();
            }

            public final void initialize(ExpandableNotificationRow expandableNotificationRow, NotificationEntry notificationEntry, ExpandableNotificationRow.OnExpandClickListener onExpandClickListener, NotificationListContainer notificationListContainer) {
                this.expandableNotificationRowProvider = InstanceFactory.create(expandableNotificationRow);
                this.factoryProvider = NotificationTapHelper_Factory_Factory.create(ReferenceSysUIComponentImpl.this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMainDelayableExecutorProvider);
                ExpandableViewController_Factory create = ExpandableViewController_Factory.create(this.expandableNotificationRowProvider);
                this.expandableViewControllerProvider = create;
                ExpandableOutlineViewController_Factory create2 = ExpandableOutlineViewController_Factory.create(this.expandableNotificationRowProvider, create);
                this.expandableOutlineViewControllerProvider = create2;
                this.activatableNotificationViewControllerProvider = ActivatableNotificationViewController_Factory.create(this.expandableNotificationRowProvider, this.factoryProvider, create2, DaggerReferenceGlobalRootComponent.this.provideAccessibilityManagerProvider, ReferenceSysUIComponentImpl.this.falsingManagerProxyProvider, ReferenceSysUIComponentImpl.this.falsingCollectorImplProvider);
                this.remoteInputViewSubcomponentFactoryProvider = new Provider<RemoteInputViewSubcomponent.Factory>() { // from class: com.android.systemui.dagger.DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.ExpandableNotificationRowComponentImpl.1
                    /* JADX DEBUG: Method merged with bridge method */
                    /* renamed from: get */
                    public RemoteInputViewSubcomponent.Factory m2259get() {
                        return new RemoteInputViewSubcomponentFactory();
                    }
                };
                this.notificationRowLoggerProvider = NotificationRowLogger_Factory.create(ReferenceSysUIComponentImpl.this.provideNotificationsLogBufferProvider);
                this.listContainerProvider = InstanceFactory.create(notificationListContainer);
                Factory create3 = InstanceFactory.create(notificationEntry);
                this.notificationEntryProvider = create3;
                this.provideStatusBarNotificationProvider = ExpandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideStatusBarNotificationFactory.create(create3);
                this.provideAppNameProvider = ExpandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideAppNameFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideStatusBarNotificationProvider);
                this.provideNotificationKeyProvider = ExpandableNotificationRowComponent_ExpandableNotificationRowModule_ProvideNotificationKeyFactory.create(this.provideStatusBarNotificationProvider);
                this.onExpandClickListenerProvider = InstanceFactory.create(onExpandClickListener);
                this.expandableNotificationRowDragControllerProvider = ExpandableNotificationRowDragController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, ReferenceSysUIComponentImpl.this.provideHeadsUpManagerPhoneProvider, ReferenceSysUIComponentImpl.this.shadeControllerImplProvider, ReferenceSysUIComponentImpl.this.provideNotificationPanelLoggerProvider);
                this.expandableNotificationRowControllerProvider = DoubleCheck.provider(ExpandableNotificationRowController_Factory.create(this.expandableNotificationRowProvider, this.activatableNotificationViewControllerProvider, this.remoteInputViewSubcomponentFactoryProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.notificationRowLoggerProvider, this.listContainerProvider, ReferenceSysUIComponentImpl.this.provideNotificationMediaManagerProvider, ReferenceSysUIComponentImpl.this.smartReplyConstantsProvider, ReferenceSysUIComponentImpl.this.provideSmartReplyControllerProvider, DaggerReferenceGlobalRootComponent.this.providesPluginManagerProvider, ReferenceSysUIComponentImpl.this.bindSystemClockProvider, this.provideAppNameProvider, this.provideNotificationKeyProvider, ReferenceSysUIComponentImpl.this.keyguardBypassControllerProvider, ReferenceSysUIComponentImpl.this.provideGroupMembershipManagerProvider, ReferenceSysUIComponentImpl.this.groupExpansionManagerImplProvider, ReferenceSysUIComponentImpl.this.rowContentBindStageProvider, ReferenceSysUIComponentImpl.this.provideNotificationLoggerProvider, ReferenceSysUIComponentImpl.this.provideHeadsUpManagerPhoneProvider, this.onExpandClickListenerProvider, ReferenceSysUIComponentImpl.this.statusBarStateControllerImplProvider, ReferenceSysUIComponentImpl.this.provideNotificationGutsManagerProvider, ReferenceSysUIComponentImpl.this.provideAllowNotificationLongPressProvider, ReferenceSysUIComponentImpl.this.onUserInteractionCallbackImplProvider, ReferenceSysUIComponentImpl.this.falsingManagerProxyProvider, ReferenceSysUIComponentImpl.this.falsingCollectorImplProvider, ReferenceSysUIComponentImpl.this.featureFlagsDebugProvider, ReferenceSysUIComponentImpl.this.peopleNotificationIdentifierImplProvider, ReferenceSysUIComponentImpl.this.provideBubblesManagerProvider, this.expandableNotificationRowDragControllerProvider));
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$FragmentCreatorFactory.class */
        public final class FragmentCreatorFactory implements FragmentService.FragmentCreator.Factory {
            public FragmentCreatorFactory() {
            }

            @Override // com.android.systemui.fragments.FragmentService.FragmentCreator.Factory
            public FragmentService.FragmentCreator build() {
                return new FragmentCreatorImpl();
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$FragmentCreatorImpl.class */
        public final class FragmentCreatorImpl implements FragmentService.FragmentCreator {
            public FragmentCreatorImpl() {
            }

            @Override // com.android.systemui.fragments.FragmentService.FragmentCreator
            public QSFragment createQSFragment() {
                return new QSFragment((RemoteInputQuickSettingsDisabler) ReferenceSysUIComponentImpl.this.remoteInputQuickSettingsDisablerProvider.get(), (QSTileHost) ReferenceSysUIComponentImpl.this.qSTileHostProvider.get(), (SysuiStatusBarStateController) ReferenceSysUIComponentImpl.this.statusBarStateControllerImplProvider.get(), (CommandQueue) ReferenceSysUIComponentImpl.this.provideCommandQueueProvider.get(), (MediaHost) ReferenceSysUIComponentImpl.this.providesQSMediaHostProvider.get(), (MediaHost) ReferenceSysUIComponentImpl.this.providesQuickQSMediaHostProvider.get(), (KeyguardBypassController) ReferenceSysUIComponentImpl.this.keyguardBypassControllerProvider.get(), new QSFragmentComponentFactory(), qSFragmentDisableFlagsLogger(), (FalsingManager) ReferenceSysUIComponentImpl.this.falsingManagerProxyProvider.get(), (DumpManager) DaggerReferenceGlobalRootComponent.this.dumpManagerProvider.get(), (FeatureFlags) ReferenceSysUIComponentImpl.this.featureFlagsDebugProvider.get(), (FooterActionsController) ReferenceSysUIComponentImpl.this.footerActionsControllerProvider.get(), (FooterActionsViewModel.Factory) ReferenceSysUIComponentImpl.this.factoryProvider19.get());
            }

            public final QSFragmentDisableFlagsLogger qSFragmentDisableFlagsLogger() {
                return new QSFragmentDisableFlagsLogger((LogBuffer) ReferenceSysUIComponentImpl.this.provideQSFragmentDisableLogBufferProvider.get(), (DisableFlagsLogger) ReferenceSysUIComponentImpl.this.disableFlagsLoggerProvider.get());
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$KeyguardBouncerComponentFactory.class */
        public final class KeyguardBouncerComponentFactory implements KeyguardBouncerComponent.Factory {
            public KeyguardBouncerComponentFactory() {
            }

            @Override // com.android.keyguard.dagger.KeyguardBouncerComponent.Factory
            public KeyguardBouncerComponent create(ViewGroup viewGroup) {
                Preconditions.checkNotNull(viewGroup);
                return new KeyguardBouncerComponentImpl(viewGroup);
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$KeyguardBouncerComponentImpl.class */
        public final class KeyguardBouncerComponentImpl implements KeyguardBouncerComponent {
            public Provider<ViewGroup> bouncerContainerProvider;
            public Provider<AdminSecondaryLockScreenController.Factory> factoryProvider;
            public Provider<EmergencyButtonController.Factory> factoryProvider2;
            public Provider<KeyguardInputViewController.Factory> factoryProvider3;
            public Provider factoryProvider4;
            public Provider<FalsingA11yDelegate> falsingA11yDelegateProvider;
            public Provider<KeyguardHostViewController> keyguardHostViewControllerProvider;
            public Provider<KeyguardSecurityViewFlipperController> keyguardSecurityViewFlipperControllerProvider;
            public Provider liftToActivateListenerProvider;
            public Provider<KeyguardHostView> providesKeyguardHostViewProvider;
            public Provider<KeyguardSecurityContainer> providesKeyguardSecurityContainerProvider;
            public Provider<KeyguardSecurityViewFlipper> providesKeyguardSecurityViewFlipperProvider;
            public Provider<Optional<SideFpsController>> providesOptionalSidefpsControllerProvider;

            public KeyguardBouncerComponentImpl(ViewGroup viewGroup) {
                initialize(viewGroup);
            }

            @Override // com.android.keyguard.dagger.KeyguardBouncerComponent
            public KeyguardHostViewController getKeyguardHostViewController() {
                return (KeyguardHostViewController) this.keyguardHostViewControllerProvider.get();
            }

            public final void initialize(ViewGroup viewGroup) {
                Factory create = InstanceFactory.create(viewGroup);
                this.bouncerContainerProvider = create;
                Provider<KeyguardHostView> provider = DoubleCheck.provider(KeyguardBouncerModule_ProvidesKeyguardHostViewFactory.create(create, DaggerReferenceGlobalRootComponent.this.providerLayoutInflaterProvider));
                this.providesKeyguardHostViewProvider = provider;
                this.providesKeyguardSecurityContainerProvider = DoubleCheck.provider(KeyguardBouncerModule_ProvidesKeyguardSecurityContainerFactory.create(provider));
                this.factoryProvider = DoubleCheck.provider(AdminSecondaryLockScreenController_Factory_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.providesKeyguardSecurityContainerProvider, ReferenceSysUIComponentImpl.this.keyguardUpdateMonitorProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider));
                this.providesKeyguardSecurityViewFlipperProvider = DoubleCheck.provider(KeyguardBouncerModule_ProvidesKeyguardSecurityViewFlipperFactory.create(this.providesKeyguardSecurityContainerProvider));
                this.liftToActivateListenerProvider = LiftToActivateListener_Factory.create(DaggerReferenceGlobalRootComponent.this.provideAccessibilityManagerProvider);
                this.factoryProvider2 = EmergencyButtonController_Factory_Factory.create(ReferenceSysUIComponentImpl.this.configurationControllerImplProvider, ReferenceSysUIComponentImpl.this.keyguardUpdateMonitorProvider, DaggerReferenceGlobalRootComponent.this.provideTelephonyManagerProvider, DaggerReferenceGlobalRootComponent.this.providePowerManagerProvider, DaggerReferenceGlobalRootComponent.this.provideActivityTaskManagerProvider, ReferenceSysUIComponentImpl.this.shadeControllerImplProvider, DaggerReferenceGlobalRootComponent.this.provideTelecomManagerProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider);
                this.factoryProvider3 = KeyguardInputViewController_Factory_Factory.create(ReferenceSysUIComponentImpl.this.keyguardUpdateMonitorProvider, DaggerReferenceGlobalRootComponent.this.provideLockPatternUtilsProvider, DaggerReferenceGlobalRootComponent.this.provideLatencyTrackerProvider, ReferenceSysUIComponentImpl.this.factoryProvider11, DaggerReferenceGlobalRootComponent.this.provideInputMethodManagerProvider, DaggerReferenceGlobalRootComponent.this.provideMainDelayableExecutorProvider, DaggerReferenceGlobalRootComponent.this.provideResourcesProvider, this.liftToActivateListenerProvider, DaggerReferenceGlobalRootComponent.this.provideTelephonyManagerProvider, ReferenceSysUIComponentImpl.this.falsingCollectorImplProvider, this.factoryProvider2, ReferenceSysUIComponentImpl.this.devicePostureControllerImplProvider, ReferenceSysUIComponentImpl.this.statusBarKeyguardViewManagerProvider);
                this.keyguardSecurityViewFlipperControllerProvider = DoubleCheck.provider(KeyguardSecurityViewFlipperController_Factory.create(this.providesKeyguardSecurityViewFlipperProvider, DaggerReferenceGlobalRootComponent.this.providerLayoutInflaterProvider, this.factoryProvider3, this.factoryProvider2));
                this.providesOptionalSidefpsControllerProvider = DoubleCheck.provider(KeyguardBouncerModule_ProvidesOptionalSidefpsControllerFactory.create(DaggerReferenceGlobalRootComponent.this.providesFingerprintManagerProvider, ReferenceSysUIComponentImpl.this.sideFpsControllerProvider));
                this.falsingA11yDelegateProvider = FalsingA11yDelegate_Factory.create(ReferenceSysUIComponentImpl.this.falsingCollectorImplProvider);
                this.factoryProvider4 = KeyguardSecurityContainerController_Factory_Factory.create(this.providesKeyguardSecurityContainerProvider, this.factoryProvider, DaggerReferenceGlobalRootComponent.this.provideLockPatternUtilsProvider, ReferenceSysUIComponentImpl.this.keyguardUpdateMonitorProvider, ReferenceSysUIComponentImpl.this.keyguardSecurityModelProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, ReferenceSysUIComponentImpl.this.keyguardStateControllerImplProvider, this.keyguardSecurityViewFlipperControllerProvider, ReferenceSysUIComponentImpl.this.configurationControllerImplProvider, ReferenceSysUIComponentImpl.this.falsingCollectorImplProvider, ReferenceSysUIComponentImpl.this.falsingManagerProxyProvider, ReferenceSysUIComponentImpl.this.userSwitcherControllerProvider, ReferenceSysUIComponentImpl.this.featureFlagsDebugProvider, ReferenceSysUIComponentImpl.this.globalSettingsImplProvider, ReferenceSysUIComponentImpl.this.sessionTrackerProvider, this.providesOptionalSidefpsControllerProvider, this.falsingA11yDelegateProvider);
                this.keyguardHostViewControllerProvider = DoubleCheck.provider(KeyguardHostViewController_Factory.create(this.providesKeyguardHostViewProvider, ReferenceSysUIComponentImpl.this.keyguardUpdateMonitorProvider, DaggerReferenceGlobalRootComponent.this.provideAudioManagerProvider, DaggerReferenceGlobalRootComponent.this.provideTelephonyManagerProvider, ReferenceSysUIComponentImpl.this.providesViewMediatorCallbackProvider, this.factoryProvider4));
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$KeyguardQsUserSwitchComponentFactory.class */
        public final class KeyguardQsUserSwitchComponentFactory implements KeyguardQsUserSwitchComponent.Factory {
            public KeyguardQsUserSwitchComponentFactory() {
            }

            @Override // com.android.keyguard.dagger.KeyguardQsUserSwitchComponent.Factory
            public KeyguardQsUserSwitchComponent build(FrameLayout frameLayout) {
                Preconditions.checkNotNull(frameLayout);
                return new KeyguardQsUserSwitchComponentImpl(frameLayout);
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$KeyguardQsUserSwitchComponentImpl.class */
        public final class KeyguardQsUserSwitchComponentImpl implements KeyguardQsUserSwitchComponent {
            public Provider<KeyguardQsUserSwitchController> keyguardQsUserSwitchControllerProvider;
            public Provider<FrameLayout> userAvatarContainerProvider;

            public KeyguardQsUserSwitchComponentImpl(FrameLayout frameLayout) {
                initialize(frameLayout);
            }

            @Override // com.android.keyguard.dagger.KeyguardQsUserSwitchComponent
            public KeyguardQsUserSwitchController getKeyguardQsUserSwitchController() {
                return (KeyguardQsUserSwitchController) this.keyguardQsUserSwitchControllerProvider.get();
            }

            public final void initialize(FrameLayout frameLayout) {
                Factory create = InstanceFactory.create(frameLayout);
                this.userAvatarContainerProvider = create;
                this.keyguardQsUserSwitchControllerProvider = DoubleCheck.provider(KeyguardQsUserSwitchController_Factory.create(create, DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideResourcesProvider, ReferenceSysUIComponentImpl.this.userSwitcherControllerProvider, ReferenceSysUIComponentImpl.this.keyguardStateControllerImplProvider, ReferenceSysUIComponentImpl.this.falsingManagerProxyProvider, ReferenceSysUIComponentImpl.this.configurationControllerImplProvider, ReferenceSysUIComponentImpl.this.statusBarStateControllerImplProvider, ReferenceSysUIComponentImpl.this.dozeParametersProvider, ReferenceSysUIComponentImpl.this.screenOffAnimationControllerProvider, ReferenceSysUIComponentImpl.this.userSwitchDialogControllerProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider));
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$KeyguardStatusBarViewComponentFactory.class */
        public final class KeyguardStatusBarViewComponentFactory implements KeyguardStatusBarViewComponent.Factory {
            public KeyguardStatusBarViewComponentFactory() {
            }

            @Override // com.android.keyguard.dagger.KeyguardStatusBarViewComponent.Factory
            public KeyguardStatusBarViewComponent build(KeyguardStatusBarView keyguardStatusBarView, NotificationPanelViewController.NotificationPanelViewStateProvider notificationPanelViewStateProvider) {
                Preconditions.checkNotNull(keyguardStatusBarView);
                Preconditions.checkNotNull(notificationPanelViewStateProvider);
                return new KeyguardStatusBarViewComponentImpl(keyguardStatusBarView, notificationPanelViewStateProvider);
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$KeyguardStatusBarViewComponentImpl.class */
        public final class KeyguardStatusBarViewComponentImpl implements KeyguardStatusBarViewComponent {
            public Provider<BatteryMeterView> getBatteryMeterViewProvider;
            public Provider<CarrierText> getCarrierTextProvider;
            public final NotificationPanelViewController.NotificationPanelViewStateProvider notificationPanelViewStateProvider;
            public final KeyguardStatusBarView view;
            public Provider<KeyguardStatusBarView> viewProvider;

            public KeyguardStatusBarViewComponentImpl(KeyguardStatusBarView keyguardStatusBarView, NotificationPanelViewController.NotificationPanelViewStateProvider notificationPanelViewStateProvider) {
                this.view = keyguardStatusBarView;
                this.notificationPanelViewStateProvider = notificationPanelViewStateProvider;
                initialize(keyguardStatusBarView, notificationPanelViewStateProvider);
            }

            public final BatteryMeterViewController batteryMeterViewController() {
                return new BatteryMeterViewController((BatteryMeterView) this.getBatteryMeterViewProvider.get(), (UserTracker) ReferenceSysUIComponentImpl.this.provideUserTrackerProvider.get(), (ConfigurationController) ReferenceSysUIComponentImpl.this.configurationControllerImplProvider.get(), (TunerService) ReferenceSysUIComponentImpl.this.tunerServiceImplProvider.get(), DaggerReferenceGlobalRootComponent.this.mainHandler(), (ContentResolver) DaggerReferenceGlobalRootComponent.this.provideContentResolverProvider.get(), (FeatureFlags) ReferenceSysUIComponentImpl.this.featureFlagsDebugProvider.get(), (BatteryController) ReferenceSysUIComponentImpl.this.provideBatteryControllerProvider.get());
            }

            public final CarrierTextController carrierTextController() {
                return new CarrierTextController((CarrierText) this.getCarrierTextProvider.get(), carrierTextManagerBuilder(), (KeyguardUpdateMonitor) ReferenceSysUIComponentImpl.this.keyguardUpdateMonitorProvider.get());
            }

            public final CarrierTextManager.Builder carrierTextManagerBuilder() {
                return new CarrierTextManager.Builder(DaggerReferenceGlobalRootComponent.this.context, DaggerReferenceGlobalRootComponent.this.mainResources(), (WifiManager) DaggerReferenceGlobalRootComponent.this.provideWifiManagerProvider.get(), (TelephonyManager) DaggerReferenceGlobalRootComponent.this.provideTelephonyManagerProvider.get(), (TelephonyListenerManager) ReferenceSysUIComponentImpl.this.telephonyListenerManagerProvider.get(), (WakefulnessLifecycle) ReferenceSysUIComponentImpl.this.wakefulnessLifecycleProvider.get(), (Executor) DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider.get(), (Executor) ReferenceSysUIComponentImpl.this.provideBackgroundExecutorProvider.get(), (KeyguardUpdateMonitor) ReferenceSysUIComponentImpl.this.keyguardUpdateMonitorProvider.get());
            }

            @Override // com.android.keyguard.dagger.KeyguardStatusBarViewComponent
            public KeyguardStatusBarViewController getKeyguardStatusBarViewController() {
                return new KeyguardStatusBarViewController(this.view, carrierTextController(), (ConfigurationController) ReferenceSysUIComponentImpl.this.configurationControllerImplProvider.get(), (SystemStatusAnimationScheduler) ReferenceSysUIComponentImpl.this.systemStatusAnimationSchedulerProvider.get(), (BatteryController) ReferenceSysUIComponentImpl.this.provideBatteryControllerProvider.get(), (UserInfoController) ReferenceSysUIComponentImpl.this.userInfoControllerImplProvider.get(), (StatusBarIconController) ReferenceSysUIComponentImpl.this.statusBarIconControllerImplProvider.get(), (StatusBarIconController.TintedIconManager.Factory) ReferenceSysUIComponentImpl.this.factoryProvider22.get(), batteryMeterViewController(), this.notificationPanelViewStateProvider, (KeyguardStateController) ReferenceSysUIComponentImpl.this.keyguardStateControllerImplProvider.get(), (KeyguardBypassController) ReferenceSysUIComponentImpl.this.keyguardBypassControllerProvider.get(), (KeyguardUpdateMonitor) ReferenceSysUIComponentImpl.this.keyguardUpdateMonitorProvider.get(), (BiometricUnlockController) ReferenceSysUIComponentImpl.this.biometricUnlockControllerProvider.get(), (SysuiStatusBarStateController) ReferenceSysUIComponentImpl.this.statusBarStateControllerImplProvider.get(), (StatusBarContentInsetsProvider) ReferenceSysUIComponentImpl.this.statusBarContentInsetsProvider.get(), (UserManager) DaggerReferenceGlobalRootComponent.this.provideUserManagerProvider.get(), statusBarUserChipViewModel(), (SecureSettings) ReferenceSysUIComponentImpl.this.secureSettingsImpl(), (CommandQueue) ReferenceSysUIComponentImpl.this.provideCommandQueueProvider.get(), (Executor) DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider.get(), ReferenceSysUIComponentImpl.this.keyguardLogger());
            }

            public final void initialize(KeyguardStatusBarView keyguardStatusBarView, NotificationPanelViewController.NotificationPanelViewStateProvider notificationPanelViewStateProvider) {
                Factory create = InstanceFactory.create(keyguardStatusBarView);
                this.viewProvider = create;
                this.getCarrierTextProvider = DoubleCheck.provider(KeyguardStatusBarViewModule_GetCarrierTextFactory.create(create));
                this.getBatteryMeterViewProvider = DoubleCheck.provider(KeyguardStatusBarViewModule_GetBatteryMeterViewFactory.create(this.viewProvider));
            }

            public final StatusBarUserChipViewModel statusBarUserChipViewModel() {
                return new StatusBarUserChipViewModel(DaggerReferenceGlobalRootComponent.this.applicationContext(), (UserInteractor) ReferenceSysUIComponentImpl.this.userInteractorProvider.get());
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$KeyguardStatusViewComponentFactory.class */
        public final class KeyguardStatusViewComponentFactory implements KeyguardStatusViewComponent.Factory {
            public KeyguardStatusViewComponentFactory() {
            }

            @Override // com.android.keyguard.dagger.KeyguardStatusViewComponent.Factory
            public KeyguardStatusViewComponent build(KeyguardStatusView keyguardStatusView) {
                Preconditions.checkNotNull(keyguardStatusView);
                return new KeyguardStatusViewComponentImpl(keyguardStatusView);
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$KeyguardStatusViewComponentImpl.class */
        public final class KeyguardStatusViewComponentImpl implements KeyguardStatusViewComponent {
            public Provider<KeyguardClockSwitch> getKeyguardClockSwitchProvider;
            public Provider<KeyguardSliceView> getKeyguardSliceViewProvider;
            public Provider<KeyguardSliceViewController> keyguardSliceViewControllerProvider;
            public final KeyguardStatusView presentation;
            public Provider<KeyguardStatusView> presentationProvider;

            public KeyguardStatusViewComponentImpl(KeyguardStatusView keyguardStatusView) {
                this.presentation = keyguardStatusView;
                initialize(keyguardStatusView);
            }

            @Override // com.android.keyguard.dagger.KeyguardStatusViewComponent
            public KeyguardClockSwitchController getKeyguardClockSwitchController() {
                return new KeyguardClockSwitchController(keyguardClockSwitch(), (StatusBarStateController) ReferenceSysUIComponentImpl.this.statusBarStateControllerImplProvider.get(), (ClockRegistry) ReferenceSysUIComponentImpl.this.getClockRegistryProvider.get(), (KeyguardSliceViewController) this.keyguardSliceViewControllerProvider.get(), (NotificationIconAreaController) ReferenceSysUIComponentImpl.this.notificationIconAreaControllerProvider.get(), (LockscreenSmartspaceController) ReferenceSysUIComponentImpl.this.lockscreenSmartspaceControllerProvider.get(), (KeyguardUnlockAnimationController) ReferenceSysUIComponentImpl.this.keyguardUnlockAnimationControllerProvider.get(), (SecureSettings) ReferenceSysUIComponentImpl.this.secureSettingsImpl(), (Executor) DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider.get(), (DumpManager) DaggerReferenceGlobalRootComponent.this.dumpManagerProvider.get(), ReferenceSysUIComponentImpl.this.clockEventController());
            }

            @Override // com.android.keyguard.dagger.KeyguardStatusViewComponent
            public KeyguardStatusViewController getKeyguardStatusViewController() {
                return new KeyguardStatusViewController(this.presentation, (KeyguardSliceViewController) this.keyguardSliceViewControllerProvider.get(), getKeyguardClockSwitchController(), (KeyguardStateController) ReferenceSysUIComponentImpl.this.keyguardStateControllerImplProvider.get(), (KeyguardUpdateMonitor) ReferenceSysUIComponentImpl.this.keyguardUpdateMonitorProvider.get(), (ConfigurationController) ReferenceSysUIComponentImpl.this.configurationControllerImplProvider.get(), (DozeParameters) ReferenceSysUIComponentImpl.this.dozeParametersProvider.get(), (FeatureFlags) ReferenceSysUIComponentImpl.this.featureFlagsDebugProvider.get(), (ScreenOffAnimationController) ReferenceSysUIComponentImpl.this.screenOffAnimationControllerProvider.get());
            }

            public final void initialize(KeyguardStatusView keyguardStatusView) {
                Factory create = InstanceFactory.create(keyguardStatusView);
                this.presentationProvider = create;
                KeyguardStatusViewModule_GetKeyguardClockSwitchFactory create2 = KeyguardStatusViewModule_GetKeyguardClockSwitchFactory.create(create);
                this.getKeyguardClockSwitchProvider = create2;
                KeyguardStatusViewModule_GetKeyguardSliceViewFactory create3 = KeyguardStatusViewModule_GetKeyguardSliceViewFactory.create(create2);
                this.getKeyguardSliceViewProvider = create3;
                this.keyguardSliceViewControllerProvider = DoubleCheck.provider(KeyguardSliceViewController_Factory.create(create3, ReferenceSysUIComponentImpl.this.provideActivityStarterProvider, ReferenceSysUIComponentImpl.this.configurationControllerImplProvider, ReferenceSysUIComponentImpl.this.tunerServiceImplProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
            }

            public final KeyguardClockSwitch keyguardClockSwitch() {
                return KeyguardStatusViewModule_GetKeyguardClockSwitchFactory.getKeyguardClockSwitch(this.presentation);
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$KeyguardUserSwitcherComponentFactory.class */
        public final class KeyguardUserSwitcherComponentFactory implements KeyguardUserSwitcherComponent.Factory {
            public KeyguardUserSwitcherComponentFactory() {
            }

            @Override // com.android.keyguard.dagger.KeyguardUserSwitcherComponent.Factory
            public KeyguardUserSwitcherComponent build(KeyguardUserSwitcherView keyguardUserSwitcherView) {
                Preconditions.checkNotNull(keyguardUserSwitcherView);
                return new KeyguardUserSwitcherComponentImpl(keyguardUserSwitcherView);
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$KeyguardUserSwitcherComponentImpl.class */
        public final class KeyguardUserSwitcherComponentImpl implements KeyguardUserSwitcherComponent {
            public Provider<KeyguardUserSwitcherController> keyguardUserSwitcherControllerProvider;
            public Provider<KeyguardUserSwitcherView> keyguardUserSwitcherViewProvider;

            public KeyguardUserSwitcherComponentImpl(KeyguardUserSwitcherView keyguardUserSwitcherView) {
                initialize(keyguardUserSwitcherView);
            }

            @Override // com.android.keyguard.dagger.KeyguardUserSwitcherComponent
            public KeyguardUserSwitcherController getKeyguardUserSwitcherController() {
                return (KeyguardUserSwitcherController) this.keyguardUserSwitcherControllerProvider.get();
            }

            public final void initialize(KeyguardUserSwitcherView keyguardUserSwitcherView) {
                Factory create = InstanceFactory.create(keyguardUserSwitcherView);
                this.keyguardUserSwitcherViewProvider = create;
                this.keyguardUserSwitcherControllerProvider = DoubleCheck.provider(KeyguardUserSwitcherController_Factory.create(create, DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideResourcesProvider, DaggerReferenceGlobalRootComponent.this.providerLayoutInflaterProvider, DaggerReferenceGlobalRootComponent.this.screenLifecycleProvider, ReferenceSysUIComponentImpl.this.userSwitcherControllerProvider, ReferenceSysUIComponentImpl.this.keyguardStateControllerImplProvider, ReferenceSysUIComponentImpl.this.statusBarStateControllerImplProvider, ReferenceSysUIComponentImpl.this.keyguardUpdateMonitorProvider, ReferenceSysUIComponentImpl.this.dozeParametersProvider, ReferenceSysUIComponentImpl.this.screenOffAnimationControllerProvider));
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$MediaProjectionAppSelectorComponentFactory.class */
        public final class MediaProjectionAppSelectorComponentFactory implements MediaProjectionAppSelectorComponent.Factory {
            public MediaProjectionAppSelectorComponentFactory() {
            }

            @Override // com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorComponent.Factory
            public MediaProjectionAppSelectorComponent create(MediaProjectionAppSelectorActivity mediaProjectionAppSelectorActivity, MediaProjectionAppSelectorView mediaProjectionAppSelectorView, MediaProjectionAppSelectorResultHandler mediaProjectionAppSelectorResultHandler) {
                Preconditions.checkNotNull(mediaProjectionAppSelectorActivity);
                Preconditions.checkNotNull(mediaProjectionAppSelectorView);
                Preconditions.checkNotNull(mediaProjectionAppSelectorResultHandler);
                return new MediaProjectionAppSelectorComponentImpl(mediaProjectionAppSelectorActivity, mediaProjectionAppSelectorView, mediaProjectionAppSelectorResultHandler);
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$MediaProjectionAppSelectorComponentImpl.class */
        public final class MediaProjectionAppSelectorComponentImpl implements MediaProjectionAppSelectorComponent {
            public Provider<ActivityTaskManagerThumbnailLoader> activityTaskManagerThumbnailLoaderProvider;
            public Provider<AppIconLoader> bindAppIconLoaderProvider;
            public Provider<IconFactory> bindIconFactoryProvider;
            public Provider<RecentTaskListProvider> bindRecentTaskListProvider;
            public Provider<RecentTaskThumbnailLoader> bindRecentTaskThumbnailLoaderProvider;
            public Provider<RecentTaskViewHolder.Factory> factoryProvider;
            public Provider<RecentTasksAdapter.Factory> factoryProvider2;
            public Provider<IconLoaderLibAppIconLoader> iconLoaderLibAppIconLoaderProvider;
            public Provider<MediaProjectionAppSelectorController> mediaProjectionAppSelectorControllerProvider;
            public Provider<MediaProjectionRecentsViewController> mediaProjectionRecentsViewControllerProvider;
            public Provider<ComponentName> provideAppSelectorComponentNameProvider;
            public Provider<CoroutineScope> provideCoroutineScopeProvider;
            public C0053RecentTaskViewHolder_Factory recentTaskViewHolderProvider;
            public C0054RecentTasksAdapter_Factory recentTasksAdapterProvider;
            public Provider<MediaProjectionAppSelectorResultHandler> resultHandlerProvider;
            public Provider<ShellRecentTaskListProvider> shellRecentTaskListProvider;
            public Provider<TaskPreviewSizeProvider> taskPreviewSizeProvider;
            public Provider<MediaProjectionAppSelectorView> viewProvider;

            public MediaProjectionAppSelectorComponentImpl(MediaProjectionAppSelectorActivity mediaProjectionAppSelectorActivity, MediaProjectionAppSelectorView mediaProjectionAppSelectorView, MediaProjectionAppSelectorResultHandler mediaProjectionAppSelectorResultHandler) {
                initialize(mediaProjectionAppSelectorActivity, mediaProjectionAppSelectorView, mediaProjectionAppSelectorResultHandler);
            }

            @Override // com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorComponent
            public ConfigurationController getConfigurationController() {
                return (ConfigurationController) ReferenceSysUIComponentImpl.this.configurationControllerImplProvider.get();
            }

            @Override // com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorComponent
            public MediaProjectionAppSelectorController getController() {
                return (MediaProjectionAppSelectorController) this.mediaProjectionAppSelectorControllerProvider.get();
            }

            @Override // com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorComponent
            public MediaProjectionRecentsViewController getRecentsViewController() {
                return (MediaProjectionRecentsViewController) this.mediaProjectionRecentsViewControllerProvider.get();
            }

            public final void initialize(MediaProjectionAppSelectorActivity mediaProjectionAppSelectorActivity, MediaProjectionAppSelectorView mediaProjectionAppSelectorView, MediaProjectionAppSelectorResultHandler mediaProjectionAppSelectorResultHandler) {
                ShellRecentTaskListProvider_Factory create = ShellRecentTaskListProvider_Factory.create(ReferenceSysUIComponentImpl.this.bgDispatcherProvider, ReferenceSysUIComponentImpl.this.provideBackgroundExecutorProvider, ReferenceSysUIComponentImpl.this.setRecentTasksProvider, ReferenceSysUIComponentImpl.this.provideUserTrackerProvider);
                this.shellRecentTaskListProvider = create;
                this.bindRecentTaskListProvider = DoubleCheck.provider(create);
                this.viewProvider = InstanceFactory.create(mediaProjectionAppSelectorView);
                this.provideCoroutineScopeProvider = DoubleCheck.provider(MediaProjectionAppSelectorModule_Companion_ProvideCoroutineScopeFactory.create(ReferenceSysUIComponentImpl.this.applicationScopeProvider));
                Provider<ComponentName> provider = DoubleCheck.provider(MediaProjectionAppSelectorModule_Companion_ProvideAppSelectorComponentNameFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider));
                this.provideAppSelectorComponentNameProvider = provider;
                this.mediaProjectionAppSelectorControllerProvider = DoubleCheck.provider(MediaProjectionAppSelectorController_Factory.create(this.bindRecentTaskListProvider, this.viewProvider, this.provideCoroutineScopeProvider, provider));
                this.bindIconFactoryProvider = MediaProjectionAppSelectorModule_Companion_BindIconFactoryFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider);
                IconLoaderLibAppIconLoader_Factory create2 = IconLoaderLibAppIconLoader_Factory.create(ReferenceSysUIComponentImpl.this.bgDispatcherProvider, DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.providePackageManagerWrapperProvider, DaggerReferenceGlobalRootComponent.this.providePackageManagerProvider, this.bindIconFactoryProvider);
                this.iconLoaderLibAppIconLoaderProvider = create2;
                this.bindAppIconLoaderProvider = DoubleCheck.provider(create2);
                ActivityTaskManagerThumbnailLoader_Factory create3 = ActivityTaskManagerThumbnailLoader_Factory.create(ReferenceSysUIComponentImpl.this.bgDispatcherProvider, ReferenceSysUIComponentImpl.this.provideActivityManagerWrapperProvider);
                this.activityTaskManagerThumbnailLoaderProvider = create3;
                this.bindRecentTaskThumbnailLoaderProvider = DoubleCheck.provider(create3);
                Provider<TaskPreviewSizeProvider> provider2 = DoubleCheck.provider(TaskPreviewSizeProvider_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideWindowManagerProvider, ReferenceSysUIComponentImpl.this.configurationControllerImplProvider));
                this.taskPreviewSizeProvider = provider2;
                C0053RecentTaskViewHolder_Factory create4 = C0053RecentTaskViewHolder_Factory.create(this.bindAppIconLoaderProvider, this.bindRecentTaskThumbnailLoaderProvider, provider2, this.provideCoroutineScopeProvider);
                this.recentTaskViewHolderProvider = create4;
                Provider<RecentTaskViewHolder.Factory> create5 = RecentTaskViewHolder_Factory_Impl.create(create4);
                this.factoryProvider = create5;
                C0054RecentTasksAdapter_Factory create6 = C0054RecentTasksAdapter_Factory.create(create5);
                this.recentTasksAdapterProvider = create6;
                this.factoryProvider2 = RecentTasksAdapter_Factory_Impl.create(create6);
                this.resultHandlerProvider = InstanceFactory.create(mediaProjectionAppSelectorResultHandler);
                this.mediaProjectionRecentsViewControllerProvider = DoubleCheck.provider(MediaProjectionRecentsViewController_Factory.create(this.factoryProvider2, this.taskPreviewSizeProvider, DaggerReferenceGlobalRootComponent.this.provideIActivityTaskManagerProvider, this.resultHandlerProvider));
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$NavigationBarComponentFactory.class */
        public final class NavigationBarComponentFactory implements NavigationBarComponent.Factory {
            public NavigationBarComponentFactory() {
            }

            @Override // com.android.systemui.navigationbar.NavigationBarComponent.Factory
            public NavigationBarComponent create(Context context, Bundle bundle) {
                Preconditions.checkNotNull(context);
                return new NavigationBarComponentImpl(context, bundle);
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$NavigationBarComponentImpl.class */
        public final class NavigationBarComponentImpl implements NavigationBarComponent {
            public Provider<Context> contextProvider;
            public Provider<DeadZone> deadZoneProvider;
            public Provider<LightBarController.Factory> factoryProvider;
            public Provider<AutoHideController.Factory> factoryProvider2;
            public Provider<NavigationBar> navigationBarProvider;
            public Provider<NavigationBarTransitions> navigationBarTransitionsProvider;
            public Provider<EdgeBackGestureHandler> provideEdgeBackGestureHandlerProvider;
            public Provider<LayoutInflater> provideLayoutInflaterProvider;
            public Provider<NavigationBarFrame> provideNavigationBarFrameProvider;
            public Provider<NavigationBarView> provideNavigationBarviewProvider;
            public Provider<WindowManager> provideWindowManagerProvider;
            public Provider<Bundle> savedStateProvider;

            public NavigationBarComponentImpl(Context context, Bundle bundle) {
                initialize(context, bundle);
            }

            @Override // com.android.systemui.navigationbar.NavigationBarComponent
            public NavigationBar getNavigationBar() {
                return (NavigationBar) this.navigationBarProvider.get();
            }

            public final void initialize(Context context, Bundle bundle) {
                Factory create = InstanceFactory.create(context);
                this.contextProvider = create;
                Provider<LayoutInflater> provider = DoubleCheck.provider(NavigationBarModule_ProvideLayoutInflaterFactory.create(create));
                this.provideLayoutInflaterProvider = provider;
                Provider<NavigationBarFrame> provider2 = DoubleCheck.provider(NavigationBarModule_ProvideNavigationBarFrameFactory.create(provider));
                this.provideNavigationBarFrameProvider = provider2;
                this.provideNavigationBarviewProvider = DoubleCheck.provider(NavigationBarModule_ProvideNavigationBarviewFactory.create(this.provideLayoutInflaterProvider, provider2));
                this.savedStateProvider = InstanceFactory.createNullable(bundle);
                this.provideWindowManagerProvider = DoubleCheck.provider(NavigationBarModule_ProvideWindowManagerFactory.create(this.contextProvider));
                this.factoryProvider = LightBarController_Factory_Factory.create(ReferenceSysUIComponentImpl.this.darkIconDispatcherImplProvider, ReferenceSysUIComponentImpl.this.provideBatteryControllerProvider, ReferenceSysUIComponentImpl.this.navigationModeControllerProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider);
                this.factoryProvider2 = AutoHideController_Factory_Factory.create(DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, DaggerReferenceGlobalRootComponent.this.provideIWindowManagerProvider);
                this.deadZoneProvider = DeadZone_Factory.create(this.provideNavigationBarviewProvider);
                this.navigationBarTransitionsProvider = DoubleCheck.provider(NavigationBarTransitions_Factory.create(this.provideNavigationBarviewProvider, DaggerReferenceGlobalRootComponent.this.provideIWindowManagerProvider, ReferenceSysUIComponentImpl.this.factoryProvider));
                this.provideEdgeBackGestureHandlerProvider = DoubleCheck.provider(NavigationBarModule_ProvideEdgeBackGestureHandlerFactory.create(ReferenceSysUIComponentImpl.this.factoryProvider13, this.contextProvider));
                this.navigationBarProvider = DoubleCheck.provider(NavigationBar_Factory.create(this.provideNavigationBarviewProvider, this.provideNavigationBarFrameProvider, this.savedStateProvider, this.contextProvider, this.provideWindowManagerProvider, ReferenceSysUIComponentImpl.this.assistManagerProvider, DaggerReferenceGlobalRootComponent.this.provideAccessibilityManagerProvider, ReferenceSysUIComponentImpl.this.bindDeviceProvisionedControllerProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, ReferenceSysUIComponentImpl.this.overviewProxyServiceProvider, ReferenceSysUIComponentImpl.this.navigationModeControllerProvider, ReferenceSysUIComponentImpl.this.statusBarStateControllerImplProvider, ReferenceSysUIComponentImpl.this.statusBarKeyguardViewManagerProvider, ReferenceSysUIComponentImpl.this.provideSysUiStateProvider, ReferenceSysUIComponentImpl.this.provideUserTrackerProvider, ReferenceSysUIComponentImpl.this.provideCommandQueueProvider, ReferenceSysUIComponentImpl.this.setPipProvider, ReferenceSysUIComponentImpl.this.optionalOfRecentsProvider, ReferenceSysUIComponentImpl.this.optionalOfCentralSurfacesProvider, ReferenceSysUIComponentImpl.this.shadeControllerImplProvider, ReferenceSysUIComponentImpl.this.provideNotificationRemoteInputManagerProvider, ReferenceSysUIComponentImpl.this.notificationShadeDepthControllerProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, ReferenceSysUIComponentImpl.this.provideBackgroundExecutorProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, ReferenceSysUIComponentImpl.this.navBarHelperProvider, ReferenceSysUIComponentImpl.this.lightBarControllerProvider, this.factoryProvider, ReferenceSysUIComponentImpl.this.autoHideControllerProvider, this.factoryProvider2, DaggerReferenceGlobalRootComponent.this.provideOptionalTelecomManagerProvider, DaggerReferenceGlobalRootComponent.this.provideInputMethodManagerProvider, this.deadZoneProvider, ReferenceSysUIComponentImpl.this.deviceConfigProxyProvider, this.navigationBarTransitionsProvider, this.provideEdgeBackGestureHandlerProvider, ReferenceSysUIComponentImpl.this.setBackAnimationProvider, ReferenceSysUIComponentImpl.this.provideUserTrackerProvider, ReferenceSysUIComponentImpl.this.wakefulnessLifecycleProvider));
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$NotificationShelfComponentBuilder.class */
        public final class NotificationShelfComponentBuilder implements NotificationShelfComponent.Builder {
            public NotificationShelf notificationShelf;

            public NotificationShelfComponentBuilder() {
            }

            public NotificationShelfComponent build() {
                Preconditions.checkBuilderRequirement(this.notificationShelf, NotificationShelf.class);
                return new NotificationShelfComponentImpl(this.notificationShelf);
            }

            /* JADX DEBUG: Method merged with bridge method */
            /* renamed from: notificationShelf */
            public NotificationShelfComponentBuilder m2260notificationShelf(NotificationShelf notificationShelf) {
                this.notificationShelf = (NotificationShelf) Preconditions.checkNotNull(notificationShelf);
                return this;
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$NotificationShelfComponentImpl.class */
        public final class NotificationShelfComponentImpl implements NotificationShelfComponent {
            public Provider<ActivatableNotificationViewController> activatableNotificationViewControllerProvider;
            public Provider<ExpandableOutlineViewController> expandableOutlineViewControllerProvider;
            public Provider<ExpandableViewController> expandableViewControllerProvider;
            public Provider<NotificationTapHelper.Factory> factoryProvider;
            public Provider<NotificationShelfController> notificationShelfControllerProvider;
            public Provider<NotificationShelf> notificationShelfProvider;

            public NotificationShelfComponentImpl(NotificationShelf notificationShelf) {
                initialize(notificationShelf);
            }

            public NotificationShelfController getNotificationShelfController() {
                return (NotificationShelfController) this.notificationShelfControllerProvider.get();
            }

            public final void initialize(NotificationShelf notificationShelf) {
                this.notificationShelfProvider = InstanceFactory.create(notificationShelf);
                this.factoryProvider = NotificationTapHelper_Factory_Factory.create(ReferenceSysUIComponentImpl.this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMainDelayableExecutorProvider);
                ExpandableViewController_Factory create = ExpandableViewController_Factory.create(this.notificationShelfProvider);
                this.expandableViewControllerProvider = create;
                ExpandableOutlineViewController_Factory create2 = ExpandableOutlineViewController_Factory.create(this.notificationShelfProvider, create);
                this.expandableOutlineViewControllerProvider = create2;
                ActivatableNotificationViewController_Factory create3 = ActivatableNotificationViewController_Factory.create(this.notificationShelfProvider, this.factoryProvider, create2, DaggerReferenceGlobalRootComponent.this.provideAccessibilityManagerProvider, ReferenceSysUIComponentImpl.this.falsingManagerProxyProvider, ReferenceSysUIComponentImpl.this.falsingCollectorImplProvider);
                this.activatableNotificationViewControllerProvider = create3;
                this.notificationShelfControllerProvider = DoubleCheck.provider(NotificationShelfController_Factory.create(this.notificationShelfProvider, create3, ReferenceSysUIComponentImpl.this.keyguardBypassControllerProvider, ReferenceSysUIComponentImpl.this.statusBarStateControllerImplProvider, ReferenceSysUIComponentImpl.this.featureFlagsDebugProvider));
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$QSFragmentComponentFactory.class */
        public final class QSFragmentComponentFactory implements QSFragmentComponent.Factory {
            public QSFragmentComponentFactory() {
            }

            @Override // com.android.systemui.qs.dagger.QSFragmentComponent.Factory
            public QSFragmentComponent create(QSFragment qSFragment) {
                Preconditions.checkNotNull(qSFragment);
                return new QSFragmentComponentImpl(qSFragment);
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$QSFragmentComponentImpl.class */
        public final class QSFragmentComponentImpl implements QSFragmentComponent {
            public Provider<BatteryMeterViewController> batteryMeterViewControllerProvider;
            public Provider<CarrierTextManager.Builder> builderProvider;
            public Provider<QSCarrierGroupController.Builder> builderProvider2;
            public Provider factoryProvider;
            public Provider<BrightnessController.Factory> factoryProvider2;
            public Provider<VariableDateViewController.Factory> factoryProvider3;
            public Provider<HeaderPrivacyIconsController> headerPrivacyIconsControllerProvider;
            public Provider<QSPanel> provideQSPanelProvider;
            public Provider<View> provideRootViewProvider;
            public Provider<Context> provideThemedContextProvider;
            public Provider<BatteryMeterView> providesBatteryMeterViewProvider;
            public Provider<OngoingPrivacyChip> providesPrivacyChipProvider;
            public Provider<QSContainerImpl> providesQSContainerImplProvider;
            public Provider<QSCustomizer> providesQSCutomizerProvider;
            public Provider<QSFooter> providesQSFooterProvider;
            public Provider<QSFooterView> providesQSFooterViewProvider;
            public Provider<Boolean> providesQSUsingCollapsedLandscapeMediaProvider;
            public Provider<Boolean> providesQSUsingMediaPlayerProvider;
            public Provider<QuickQSPanel> providesQuickQSPanelProvider;
            public Provider<QuickStatusBarHeader> providesQuickStatusBarHeaderProvider;
            public Provider<StatusIconContainer> providesStatusIconContainerProvider;
            public Provider<QSAnimator> qSAnimatorProvider;
            public Provider<QSContainerImplController> qSContainerImplControllerProvider;
            public Provider<QSCustomizerController> qSCustomizerControllerProvider;
            public Provider<QSFooterViewController> qSFooterViewControllerProvider;
            public Provider<QSPanelController> qSPanelControllerProvider;
            public Provider<QSSquishinessController> qSSquishinessControllerProvider;
            public Provider<QSFragment> qsFragmentProvider;
            public Provider<QuickQSPanelController> quickQSPanelControllerProvider;
            public Provider quickStatusBarHeaderControllerProvider;
            public Provider<TileAdapter> tileAdapterProvider;
            public Provider<TileQueryHelper> tileQueryHelperProvider;

            public QSFragmentComponentImpl(QSFragment qSFragment) {
                initialize(qSFragment);
            }

            @Override // com.android.systemui.qs.dagger.QSFragmentComponent
            public QSAnimator getQSAnimator() {
                return (QSAnimator) this.qSAnimatorProvider.get();
            }

            @Override // com.android.systemui.qs.dagger.QSFragmentComponent
            public QSContainerImplController getQSContainerImplController() {
                return (QSContainerImplController) this.qSContainerImplControllerProvider.get();
            }

            @Override // com.android.systemui.qs.dagger.QSFragmentComponent
            public QSCustomizerController getQSCustomizerController() {
                return (QSCustomizerController) this.qSCustomizerControllerProvider.get();
            }

            @Override // com.android.systemui.qs.dagger.QSFragmentComponent
            public QSFooter getQSFooter() {
                return (QSFooter) this.providesQSFooterProvider.get();
            }

            public FooterActionsController getQSFooterActionController() {
                return (FooterActionsController) ReferenceSysUIComponentImpl.this.footerActionsControllerProvider.get();
            }

            @Override // com.android.systemui.qs.dagger.QSFragmentComponent
            public QSPanelController getQSPanelController() {
                return (QSPanelController) this.qSPanelControllerProvider.get();
            }

            @Override // com.android.systemui.qs.dagger.QSFragmentComponent
            public QSSquishinessController getQSSquishinessController() {
                return (QSSquishinessController) this.qSSquishinessControllerProvider.get();
            }

            @Override // com.android.systemui.qs.dagger.QSFragmentComponent
            public QuickQSPanelController getQuickQSPanelController() {
                return (QuickQSPanelController) this.quickQSPanelControllerProvider.get();
            }

            public final void initialize(QSFragment qSFragment) {
                Factory create = InstanceFactory.create(qSFragment);
                this.qsFragmentProvider = create;
                QSFragmentModule_ProvideRootViewFactory create2 = QSFragmentModule_ProvideRootViewFactory.create(create);
                this.provideRootViewProvider = create2;
                this.provideQSPanelProvider = QSFragmentModule_ProvideQSPanelFactory.create(create2);
                this.providesQSCutomizerProvider = DoubleCheck.provider(QSFragmentModule_ProvidesQSCutomizerFactory.create(this.provideRootViewProvider));
                this.tileQueryHelperProvider = DoubleCheck.provider(TileQueryHelper_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, ReferenceSysUIComponentImpl.this.provideUserTrackerProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, ReferenceSysUIComponentImpl.this.provideBackgroundExecutorProvider));
                QSFragmentModule_ProvideThemedContextFactory create3 = QSFragmentModule_ProvideThemedContextFactory.create(this.provideRootViewProvider);
                this.provideThemedContextProvider = create3;
                this.tileAdapterProvider = DoubleCheck.provider(TileAdapter_Factory.create(create3, ReferenceSysUIComponentImpl.this.qSTileHostProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider));
                this.qSCustomizerControllerProvider = DoubleCheck.provider(QSCustomizerController_Factory.create(this.providesQSCutomizerProvider, this.tileQueryHelperProvider, ReferenceSysUIComponentImpl.this.qSTileHostProvider, this.tileAdapterProvider, DaggerReferenceGlobalRootComponent.this.screenLifecycleProvider, ReferenceSysUIComponentImpl.this.keyguardStateControllerImplProvider, ReferenceSysUIComponentImpl.this.lightBarControllerProvider, ReferenceSysUIComponentImpl.this.configurationControllerImplProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider));
                this.providesQSUsingMediaPlayerProvider = QSFragmentModule_ProvidesQSUsingMediaPlayerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider);
                this.factoryProvider = DoubleCheck.provider(QSTileRevealController_Factory_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.qSCustomizerControllerProvider));
                this.factoryProvider2 = BrightnessController_Factory_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, ReferenceSysUIComponentImpl.this.provideUserTrackerProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, ReferenceSysUIComponentImpl.this.provideBgHandlerProvider);
                this.qSPanelControllerProvider = DoubleCheck.provider(QSPanelController_Factory.create(this.provideQSPanelProvider, ReferenceSysUIComponentImpl.this.tunerServiceImplProvider, ReferenceSysUIComponentImpl.this.qSTileHostProvider, this.qSCustomizerControllerProvider, this.providesQSUsingMediaPlayerProvider, ReferenceSysUIComponentImpl.this.providesQSMediaHostProvider, this.factoryProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, ReferenceSysUIComponentImpl.this.qSLoggerProvider, this.factoryProvider2, ReferenceSysUIComponentImpl.this.factoryProvider9, ReferenceSysUIComponentImpl.this.falsingManagerProxyProvider, ReferenceSysUIComponentImpl.this.statusBarKeyguardViewManagerProvider, ReferenceSysUIComponentImpl.this.featureFlagsDebugProvider));
                QSFragmentModule_ProvidesQuickStatusBarHeaderFactory create4 = QSFragmentModule_ProvidesQuickStatusBarHeaderFactory.create(this.provideRootViewProvider);
                this.providesQuickStatusBarHeaderProvider = create4;
                this.providesQuickQSPanelProvider = QSFragmentModule_ProvidesQuickQSPanelFactory.create(create4);
                this.providesQSUsingCollapsedLandscapeMediaProvider = QSFragmentModule_ProvidesQSUsingCollapsedLandscapeMediaFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider);
                Provider<QuickQSPanelController> provider = DoubleCheck.provider(QuickQSPanelController_Factory.create(this.providesQuickQSPanelProvider, ReferenceSysUIComponentImpl.this.qSTileHostProvider, this.qSCustomizerControllerProvider, this.providesQSUsingMediaPlayerProvider, ReferenceSysUIComponentImpl.this.providesQuickQSMediaHostProvider, this.providesQSUsingCollapsedLandscapeMediaProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, ReferenceSysUIComponentImpl.this.qSLoggerProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
                this.quickQSPanelControllerProvider = provider;
                this.qSAnimatorProvider = DoubleCheck.provider(QSAnimator_Factory.create(this.qsFragmentProvider, this.providesQuickQSPanelProvider, this.providesQuickStatusBarHeaderProvider, this.qSPanelControllerProvider, provider, ReferenceSysUIComponentImpl.this.qSTileHostProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, ReferenceSysUIComponentImpl.this.tunerServiceImplProvider, DaggerReferenceGlobalRootComponent.this.qSExpansionPathInterpolatorProvider));
                this.providesQSContainerImplProvider = QSFragmentModule_ProvidesQSContainerImplFactory.create(this.provideRootViewProvider);
                this.providesPrivacyChipProvider = DoubleCheck.provider(QSFragmentModule_ProvidesPrivacyChipFactory.create(this.providesQuickStatusBarHeaderProvider));
                this.providesStatusIconContainerProvider = DoubleCheck.provider(QSFragmentModule_ProvidesStatusIconContainerFactory.create(this.providesQuickStatusBarHeaderProvider));
                this.headerPrivacyIconsControllerProvider = HeaderPrivacyIconsController_Factory.create(ReferenceSysUIComponentImpl.this.privacyItemControllerProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, this.providesPrivacyChipProvider, ReferenceSysUIComponentImpl.this.privacyDialogControllerProvider, ReferenceSysUIComponentImpl.this.privacyLoggerProvider, this.providesStatusIconContainerProvider, DaggerReferenceGlobalRootComponent.this.providePermissionManagerProvider, ReferenceSysUIComponentImpl.this.provideBackgroundExecutorProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, ReferenceSysUIComponentImpl.this.provideActivityStarterProvider, ReferenceSysUIComponentImpl.this.appOpsControllerImplProvider, ReferenceSysUIComponentImpl.this.broadcastDispatcherProvider, DaggerReferenceGlobalRootComponent.this.provideSafetyCenterManagerProvider, ReferenceSysUIComponentImpl.this.bindDeviceProvisionedControllerProvider);
                this.builderProvider = CarrierTextManager_Builder_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideResourcesProvider, DaggerReferenceGlobalRootComponent.this.provideWifiManagerProvider, DaggerReferenceGlobalRootComponent.this.provideTelephonyManagerProvider, ReferenceSysUIComponentImpl.this.telephonyListenerManagerProvider, ReferenceSysUIComponentImpl.this.wakefulnessLifecycleProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, ReferenceSysUIComponentImpl.this.provideBackgroundExecutorProvider, ReferenceSysUIComponentImpl.this.keyguardUpdateMonitorProvider);
                this.builderProvider2 = QSCarrierGroupController_Builder_Factory.create(ReferenceSysUIComponentImpl.this.provideActivityStarterProvider, ReferenceSysUIComponentImpl.this.provideBgHandlerProvider, GlobalConcurrencyModule_ProvideMainLooperFactory.create(), ReferenceSysUIComponentImpl.this.networkControllerImplProvider, this.builderProvider, DaggerReferenceGlobalRootComponent.this.contextProvider, ReferenceSysUIComponentImpl.this.carrierConfigTrackerProvider, ReferenceSysUIComponentImpl.this.subscriptionManagerSlotIndexResolverProvider);
                this.factoryProvider3 = VariableDateViewController_Factory_Factory.create(ReferenceSysUIComponentImpl.this.bindSystemClockProvider, ReferenceSysUIComponentImpl.this.broadcastDispatcherProvider, ReferenceSysUIComponentImpl.this.provideTimeTickHandlerProvider);
                QSFragmentModule_ProvidesBatteryMeterViewFactory create5 = QSFragmentModule_ProvidesBatteryMeterViewFactory.create(this.providesQuickStatusBarHeaderProvider);
                this.providesBatteryMeterViewProvider = create5;
                this.batteryMeterViewControllerProvider = BatteryMeterViewController_Factory.create(create5, ReferenceSysUIComponentImpl.this.provideUserTrackerProvider, ReferenceSysUIComponentImpl.this.configurationControllerImplProvider, ReferenceSysUIComponentImpl.this.tunerServiceImplProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, DaggerReferenceGlobalRootComponent.this.provideContentResolverProvider, ReferenceSysUIComponentImpl.this.featureFlagsDebugProvider, ReferenceSysUIComponentImpl.this.provideBatteryControllerProvider);
                Provider provider2 = DoubleCheck.provider(QuickStatusBarHeaderController_Factory.create(this.providesQuickStatusBarHeaderProvider, this.headerPrivacyIconsControllerProvider, ReferenceSysUIComponentImpl.this.statusBarIconControllerImplProvider, ReferenceSysUIComponentImpl.this.provideDemoModeControllerProvider, this.quickQSPanelControllerProvider, this.builderProvider2, ReferenceSysUIComponentImpl.this.sysuiColorExtractorProvider, DaggerReferenceGlobalRootComponent.this.qSExpansionPathInterpolatorProvider, ReferenceSysUIComponentImpl.this.featureFlagsDebugProvider, this.factoryProvider3, this.batteryMeterViewControllerProvider, ReferenceSysUIComponentImpl.this.statusBarContentInsetsProvider, ReferenceSysUIComponentImpl.this.factoryProvider22));
                this.quickStatusBarHeaderControllerProvider = provider2;
                this.qSContainerImplControllerProvider = DoubleCheck.provider(QSContainerImplController_Factory.create(this.providesQSContainerImplProvider, this.qSPanelControllerProvider, provider2, ReferenceSysUIComponentImpl.this.configurationControllerImplProvider, ReferenceSysUIComponentImpl.this.falsingManagerProxyProvider, ReferenceSysUIComponentImpl.this.featureFlagsDebugProvider));
                QSFragmentModule_ProvidesQSFooterViewFactory create6 = QSFragmentModule_ProvidesQSFooterViewFactory.create(this.provideRootViewProvider);
                this.providesQSFooterViewProvider = create6;
                Provider<QSFooterViewController> provider3 = DoubleCheck.provider(QSFooterViewController_Factory.create(create6, ReferenceSysUIComponentImpl.this.provideUserTrackerProvider, ReferenceSysUIComponentImpl.this.falsingManagerProxyProvider, ReferenceSysUIComponentImpl.this.provideActivityStarterProvider, this.qSPanelControllerProvider));
                this.qSFooterViewControllerProvider = provider3;
                this.providesQSFooterProvider = DoubleCheck.provider(QSFragmentModule_ProvidesQSFooterFactory.create(provider3));
                this.qSSquishinessControllerProvider = DoubleCheck.provider(QSSquishinessController_Factory.create(this.qSAnimatorProvider, this.qSPanelControllerProvider, this.quickQSPanelControllerProvider));
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$SectionHeaderControllerSubcomponentBuilder.class */
        public final class SectionHeaderControllerSubcomponentBuilder implements SectionHeaderControllerSubcomponent.Builder {
            public String clickIntentAction;
            public Integer headerText;
            public String nodeLabel;

            public SectionHeaderControllerSubcomponentBuilder() {
            }

            public SectionHeaderControllerSubcomponent build() {
                Preconditions.checkBuilderRequirement(this.nodeLabel, String.class);
                Preconditions.checkBuilderRequirement(this.headerText, Integer.class);
                Preconditions.checkBuilderRequirement(this.clickIntentAction, String.class);
                return new SectionHeaderControllerSubcomponentImpl(this.nodeLabel, this.headerText, this.clickIntentAction);
            }

            /* JADX DEBUG: Method merged with bridge method */
            /* renamed from: clickIntentAction */
            public SectionHeaderControllerSubcomponentBuilder m2261clickIntentAction(String str) {
                this.clickIntentAction = (String) Preconditions.checkNotNull(str);
                return this;
            }

            /* JADX DEBUG: Method merged with bridge method */
            /* renamed from: headerText */
            public SectionHeaderControllerSubcomponentBuilder m2262headerText(int i) {
                this.headerText = (Integer) Preconditions.checkNotNull(Integer.valueOf(i));
                return this;
            }

            /* JADX DEBUG: Method merged with bridge method */
            /* renamed from: nodeLabel */
            public SectionHeaderControllerSubcomponentBuilder m2263nodeLabel(String str) {
                this.nodeLabel = (String) Preconditions.checkNotNull(str);
                return this;
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$SectionHeaderControllerSubcomponentImpl.class */
        public final class SectionHeaderControllerSubcomponentImpl implements SectionHeaderControllerSubcomponent {
            public Provider<String> clickIntentActionProvider;
            public Provider<Integer> headerTextProvider;
            public Provider<String> nodeLabelProvider;
            public Provider<SectionHeaderNodeControllerImpl> sectionHeaderNodeControllerImplProvider;

            public SectionHeaderControllerSubcomponentImpl(String str, Integer num, String str2) {
                initialize(str, num, str2);
            }

            public SectionHeaderController getHeaderController() {
                return (SectionHeaderController) this.sectionHeaderNodeControllerImplProvider.get();
            }

            public NodeController getNodeController() {
                return (NodeController) this.sectionHeaderNodeControllerImplProvider.get();
            }

            public final void initialize(String str, Integer num, String str2) {
                this.nodeLabelProvider = InstanceFactory.create(str);
                this.headerTextProvider = InstanceFactory.create(num);
                this.clickIntentActionProvider = InstanceFactory.create(str2);
                this.sectionHeaderNodeControllerImplProvider = DoubleCheck.provider(SectionHeaderNodeControllerImpl_Factory.create(this.nodeLabelProvider, DaggerReferenceGlobalRootComponent.this.providerLayoutInflaterProvider, this.headerTextProvider, ReferenceSysUIComponentImpl.this.provideActivityStarterProvider, this.clickIntentActionProvider));
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$SysUIUnfoldComponentFactory.class */
        public final class SysUIUnfoldComponentFactory implements SysUIUnfoldComponent.Factory {
            public SysUIUnfoldComponentFactory() {
            }

            public SysUIUnfoldComponent create(UnfoldTransitionProgressProvider unfoldTransitionProgressProvider, NaturalRotationUnfoldProgressProvider naturalRotationUnfoldProgressProvider, ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider) {
                Preconditions.checkNotNull(unfoldTransitionProgressProvider);
                Preconditions.checkNotNull(naturalRotationUnfoldProgressProvider);
                Preconditions.checkNotNull(scopedUnfoldTransitionProgressProvider);
                return new SysUIUnfoldComponentImpl(unfoldTransitionProgressProvider, naturalRotationUnfoldProgressProvider, scopedUnfoldTransitionProgressProvider);
            }
        }

        /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$ReferenceSysUIComponentImpl$SysUIUnfoldComponentImpl.class */
        public final class SysUIUnfoldComponentImpl implements SysUIUnfoldComponent {
            public Provider<FoldAodAnimationController> foldAodAnimationControllerProvider;
            public Provider<KeyguardUnfoldTransition> keyguardUnfoldTransitionProvider;
            public Provider<NotificationPanelUnfoldAnimationController> notificationPanelUnfoldAnimationControllerProvider;
            public Provider<UnfoldTransitionProgressProvider> p1Provider;
            public Provider<NaturalRotationUnfoldProgressProvider> p2Provider;
            public Provider<ScopedUnfoldTransitionProgressProvider> p3Provider;
            public Provider<StatusBarMoveFromCenterAnimationController> statusBarMoveFromCenterAnimationControllerProvider;
            public Provider<UnfoldHapticsPlayer> unfoldHapticsPlayerProvider;
            public Provider<UnfoldLightRevealOverlayAnimation> unfoldLightRevealOverlayAnimationProvider;
            public Provider<UnfoldTransitionWallpaperController> unfoldTransitionWallpaperControllerProvider;

            public SysUIUnfoldComponentImpl(UnfoldTransitionProgressProvider unfoldTransitionProgressProvider, NaturalRotationUnfoldProgressProvider naturalRotationUnfoldProgressProvider, ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider) {
                initialize(unfoldTransitionProgressProvider, naturalRotationUnfoldProgressProvider, scopedUnfoldTransitionProgressProvider);
            }

            public FoldAodAnimationController getFoldAodAnimationController() {
                return (FoldAodAnimationController) this.foldAodAnimationControllerProvider.get();
            }

            public KeyguardUnfoldTransition getKeyguardUnfoldTransition() {
                return (KeyguardUnfoldTransition) this.keyguardUnfoldTransitionProvider.get();
            }

            public NotificationPanelUnfoldAnimationController getNotificationPanelUnfoldAnimationController() {
                return (NotificationPanelUnfoldAnimationController) this.notificationPanelUnfoldAnimationControllerProvider.get();
            }

            public StatusBarMoveFromCenterAnimationController getStatusBarMoveFromCenterAnimationController() {
                return (StatusBarMoveFromCenterAnimationController) this.statusBarMoveFromCenterAnimationControllerProvider.get();
            }

            public UnfoldHapticsPlayer getUnfoldHapticsPlayer() {
                return (UnfoldHapticsPlayer) this.unfoldHapticsPlayerProvider.get();
            }

            public UnfoldKeyguardVisibilityManager getUnfoldKeyguardVisibilityManager() {
                return (UnfoldKeyguardVisibilityManager) DaggerReferenceGlobalRootComponent.this.unfoldKeyguardVisibilityManagerProvider.get();
            }

            public UnfoldLightRevealOverlayAnimation getUnfoldLightRevealOverlayAnimation() {
                return (UnfoldLightRevealOverlayAnimation) this.unfoldLightRevealOverlayAnimationProvider.get();
            }

            public UnfoldTransitionWallpaperController getUnfoldTransitionWallpaperController() {
                return (UnfoldTransitionWallpaperController) this.unfoldTransitionWallpaperControllerProvider.get();
            }

            public final void initialize(UnfoldTransitionProgressProvider unfoldTransitionProgressProvider, NaturalRotationUnfoldProgressProvider naturalRotationUnfoldProgressProvider, ScopedUnfoldTransitionProgressProvider scopedUnfoldTransitionProgressProvider) {
                this.p2Provider = InstanceFactory.create(naturalRotationUnfoldProgressProvider);
                this.keyguardUnfoldTransitionProvider = DoubleCheck.provider(KeyguardUnfoldTransition_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, ReferenceSysUIComponentImpl.this.statusBarStateControllerImplProvider, this.p2Provider));
                Factory create = InstanceFactory.create(scopedUnfoldTransitionProgressProvider);
                this.p3Provider = create;
                this.statusBarMoveFromCenterAnimationControllerProvider = DoubleCheck.provider(StatusBarMoveFromCenterAnimationController_Factory.create(create, DaggerReferenceGlobalRootComponent.this.provideWindowManagerProvider));
                this.notificationPanelUnfoldAnimationControllerProvider = DoubleCheck.provider(NotificationPanelUnfoldAnimationController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, ReferenceSysUIComponentImpl.this.statusBarStateControllerImplProvider, this.p2Provider));
                this.foldAodAnimationControllerProvider = DoubleCheck.provider(FoldAodAnimationController_Factory.create(DaggerReferenceGlobalRootComponent.this.provideMainDelayableExecutorProvider, DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideDeviceStateManagerProvider, ReferenceSysUIComponentImpl.this.wakefulnessLifecycleProvider, ReferenceSysUIComponentImpl.this.globalSettingsImplProvider, DaggerReferenceGlobalRootComponent.this.provideLatencyTrackerProvider, ReferenceSysUIComponentImpl.this.keyguardInteractorProvider));
                Factory create2 = InstanceFactory.create(unfoldTransitionProgressProvider);
                this.p1Provider = create2;
                this.unfoldTransitionWallpaperControllerProvider = DoubleCheck.provider(UnfoldTransitionWallpaperController_Factory.create(create2, ReferenceSysUIComponentImpl.this.wallpaperControllerProvider));
                this.unfoldHapticsPlayerProvider = DoubleCheck.provider(UnfoldHapticsPlayer_Factory.create(this.p1Provider, DaggerReferenceGlobalRootComponent.this.provideVibratorProvider));
                this.unfoldLightRevealOverlayAnimationProvider = DoubleCheck.provider(UnfoldLightRevealOverlayAnimation_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideDeviceStateManagerProvider, DaggerReferenceGlobalRootComponent.this.provideContentResolverProvider, DaggerReferenceGlobalRootComponent.this.provideDisplayManagerProvider, this.p1Provider, ReferenceSysUIComponentImpl.this.setDisplayAreaHelperProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, ThreadFactoryImpl_Factory.create(), DaggerReferenceGlobalRootComponent.this.rotationChangeProvider));
            }
        }

        public ReferenceSysUIComponentImpl(LeakModule leakModule, NightDisplayListenerModule nightDisplayListenerModule, SharedLibraryModule sharedLibraryModule, KeyguardModule keyguardModule, SysUIUnfoldModule sysUIUnfoldModule, ShellInterface shellInterface, Optional<Pip> optional, Optional<SplitScreen> optional2, Optional<OneHanded> optional3, Optional<Bubbles> optional4, Optional<TaskViewFactory> optional5, ShellTransitions shellTransitions, Optional<StartingSurface> optional6, Optional<DisplayAreaHelper> optional7, Optional<RecentTasks> optional8, Optional<BackAnimation> optional9, Optional<DesktopMode> optional10) {
            initialize(leakModule, nightDisplayListenerModule, sharedLibraryModule, keyguardModule, sysUIUnfoldModule, shellInterface, optional, optional2, optional3, optional4, optional5, shellTransitions, optional6, optional7, optional8, optional9, optional10);
            initialize2(leakModule, nightDisplayListenerModule, sharedLibraryModule, keyguardModule, sysUIUnfoldModule, shellInterface, optional, optional2, optional3, optional4, optional5, shellTransitions, optional6, optional7, optional8, optional9, optional10);
            initialize3(leakModule, nightDisplayListenerModule, sharedLibraryModule, keyguardModule, sysUIUnfoldModule, shellInterface, optional, optional2, optional3, optional4, optional5, shellTransitions, optional6, optional7, optional8, optional9, optional10);
            initialize4(leakModule, nightDisplayListenerModule, sharedLibraryModule, keyguardModule, sysUIUnfoldModule, shellInterface, optional, optional2, optional3, optional4, optional5, shellTransitions, optional6, optional7, optional8, optional9, optional10);
            initialize5(leakModule, nightDisplayListenerModule, sharedLibraryModule, keyguardModule, sysUIUnfoldModule, shellInterface, optional, optional2, optional3, optional4, optional5, shellTransitions, optional6, optional7, optional8, optional9, optional10);
            initialize6(leakModule, nightDisplayListenerModule, sharedLibraryModule, keyguardModule, sysUIUnfoldModule, shellInterface, optional, optional2, optional3, optional4, optional5, shellTransitions, optional6, optional7, optional8, optional9, optional10);
            initialize7(leakModule, nightDisplayListenerModule, sharedLibraryModule, keyguardModule, sysUIUnfoldModule, shellInterface, optional, optional2, optional3, optional4, optional5, shellTransitions, optional6, optional7, optional8, optional9, optional10);
            initialize8(leakModule, nightDisplayListenerModule, sharedLibraryModule, keyguardModule, sysUIUnfoldModule, shellInterface, optional, optional2, optional3, optional4, optional5, shellTransitions, optional6, optional7, optional8, optional9, optional10);
            initialize9(leakModule, nightDisplayListenerModule, sharedLibraryModule, keyguardModule, sysUIUnfoldModule, shellInterface, optional, optional2, optional3, optional4, optional5, shellTransitions, optional6, optional7, optional8, optional9, optional10);
        }

        public final ClockEventController clockEventController() {
            return new ClockEventController((KeyguardInteractor) this.keyguardInteractorProvider.get(), (KeyguardTransitionInteractor) this.keyguardTransitionInteractorProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (BatteryController) this.provideBatteryControllerProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get(), (ConfigurationController) this.configurationControllerImplProvider.get(), DaggerReferenceGlobalRootComponent.this.mainResources(), DaggerReferenceGlobalRootComponent.this.context, (Executor) DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider.get(), (Executor) this.provideBackgroundExecutorProvider.get(), (LogBuffer) this.provideKeyguardClockLogProvider.get(), (FeatureFlags) this.featureFlagsDebugProvider.get());
        }

        @Override // com.android.systemui.dagger.SysUIComponent
        public Dependency createDependency() {
            return (Dependency) this.dependencyProvider.get();
        }

        @Override // com.android.systemui.dagger.SysUIComponent
        public DumpManager createDumpManager() {
            return (DumpManager) DaggerReferenceGlobalRootComponent.this.dumpManagerProvider.get();
        }

        @Override // com.android.systemui.dagger.SysUIComponent
        public ConfigurationController getConfigurationController() {
            return (ConfigurationController) this.configurationControllerImplProvider.get();
        }

        public ContextComponentHelper getContextComponentHelper() {
            return (ContextComponentHelper) this.contextComponentResolverProvider.get();
        }

        @Override // com.android.systemui.dagger.SysUIComponent
        public Optional<FoldStateLogger> getFoldStateLogger() {
            return (Optional) DaggerReferenceGlobalRootComponent.this.providesFoldStateLoggerProvider.get();
        }

        @Override // com.android.systemui.dagger.SysUIComponent
        public Optional<FoldStateLoggingProvider> getFoldStateLoggingProvider() {
            return (Optional) DaggerReferenceGlobalRootComponent.this.providesFoldStateLoggingProvider.get();
        }

        @Override // com.android.systemui.dagger.SysUIComponent
        public InitController getInitController() {
            return (InitController) this.initControllerProvider.get();
        }

        @Override // com.android.systemui.dagger.SysUIComponent
        public Optional<MediaMuteAwaitConnectionCli> getMediaMuteAwaitConnectionCli() {
            return (Optional) this.providesMediaMuteAwaitConnectionCliProvider.get();
        }

        @Override // com.android.systemui.dagger.SysUIComponent
        public Optional<NaturalRotationUnfoldProgressProvider> getNaturalRotationUnfoldProgressProvider() {
            return (Optional) DaggerReferenceGlobalRootComponent.this.provideNaturalRotationProgressProvider.get();
        }

        @Override // com.android.systemui.dagger.SysUIComponent
        public Optional<NearbyMediaDevicesManager> getNearbyMediaDevicesManager() {
            return (Optional) this.providesNearbyMediaDevicesManagerProvider.get();
        }

        @Override // com.android.systemui.dagger.SysUIComponent
        public Map<Class<?>, Provider<CoreStartable>> getPerUserStartables() {
            return Collections.singletonMap(NotificationChannels.class, this.notificationChannelsProvider);
        }

        @Override // com.android.systemui.dagger.SysUIComponent
        public Map<Class<?>, Provider<CoreStartable>> getStartables() {
            return MapBuilder.newMapBuilder(43).put(BroadcastDispatcherStartable.class, this.broadcastDispatcherStartableProvider).put(KeyguardNotificationVisibilityProvider.class, this.keyguardNotificationVisibilityProviderImplProvider).put(KeyguardTransitionCoreStartable.class, this.keyguardTransitionCoreStartableProvider).put(FeatureFlagsDebugStartable.class, this.featureFlagsDebugStartableProvider).put(MotionToolStartable.class, this.motionToolStartableProvider).put(UserFileManagerImpl.class, this.userFileManagerImplProvider).put(MobileUiAdapter.class, this.mobileUiAdapterProvider).put(UserSwitcherDialogCoordinator.class, this.userSwitcherDialogCoordinatorProvider).put(AuthController.class, this.authControllerProvider).put(ChooserSelector.class, this.chooserSelectorProvider).put(ClipboardListener.class, this.clipboardListenerProvider).put(FsiChromeRepo.class, this.fsiChromeRepoProvider).put(FsiChromeViewModelFactory.class, this.fsiChromeViewModelFactoryProvider).put(FsiChromeViewBinder.class, this.fsiChromeViewBinderProvider).put(GarbageMonitor.class, this.serviceProvider).put(GlobalActionsComponent.class, this.globalActionsComponentProvider).put(InstantAppNotifier.class, this.instantAppNotifierProvider).put(KeyboardUI.class, this.keyboardUIProvider).put(KeyguardBiometricLockoutLogger.class, this.keyguardBiometricLockoutLoggerProvider).put(KeyguardViewMediator.class, this.newKeyguardViewMediatorProvider).put(LatencyTester.class, this.latencyTesterProvider).put(PowerUI.class, this.powerUIProvider).put(Recents.class, this.provideRecentsProvider).put(RingtonePlayer.class, this.ringtonePlayerProvider).put(ScreenDecorations.class, this.screenDecorationsProvider).put(SessionTracker.class, this.sessionTrackerProvider).put(ShortcutKeyDispatcher.class, this.shortcutKeyDispatcherProvider).put(SliceBroadcastRelayHandler.class, this.sliceBroadcastRelayHandlerProvider).put(StorageNotification.class, this.storageNotificationProvider).put(SystemActions.class, this.systemActionsProvider).put(ThemeOverlayController.class, this.themeOverlayControllerProvider).put(ToastUI.class, this.toastUIProvider).put(VolumeUI.class, this.volumeUIProvider).put(WindowMagnification.class, this.windowMagnificationProvider).put(WMShell.class, this.wMShellProvider).put(KeyguardLiftController.class, this.keyguardLiftControllerProvider).put(MediaTttSenderCoordinator.class, this.mediaTttSenderCoordinatorProvider).put(MediaTttChipControllerReceiver.class, this.mediaTttChipControllerReceiverProvider).put(MediaTttCommandLineHelper.class, this.mediaTttCommandLineHelperProvider).put(ChipbarCoordinator.class, this.chipbarCoordinatorProvider).put(RearDisplayDialogController.class, this.rearDisplayDialogControllerProvider).put(StylusUsiPowerStartable.class, this.stylusUsiPowerStartableProvider).put(CentralSurfaces.class, this.centralSurfacesImplProvider).build();
        }

        @Override // com.android.systemui.dagger.SysUIComponent
        public Optional<SysUIUnfoldComponent> getSysUIUnfoldComponent() {
            return (Optional) this.provideSysUIUnfoldComponentProvider.get();
        }

        @Override // com.android.systemui.dagger.SysUIComponent
        public UnfoldLatencyTracker getUnfoldLatencyTracker() {
            return (UnfoldLatencyTracker) this.unfoldLatencyTrackerProvider.get();
        }

        @Override // com.android.systemui.dagger.SysUIComponent
        public /* bridge */ /* synthetic */ void init() {
            super.init();
        }

        public final void initialize(LeakModule leakModule, NightDisplayListenerModule nightDisplayListenerModule, SharedLibraryModule sharedLibraryModule, KeyguardModule keyguardModule, SysUIUnfoldModule sysUIUnfoldModule, ShellInterface shellInterface, Optional<Pip> optional, Optional<SplitScreen> optional2, Optional<OneHanded> optional3, Optional<Bubbles> optional4, Optional<TaskViewFactory> optional5, ShellTransitions shellTransitions, Optional<StartingSurface> optional6, Optional<DisplayAreaHelper> optional7, Optional<RecentTasks> optional8, Optional<BackAnimation> optional9, Optional<DesktopMode> optional10) {
            this.bootCompleteCacheImplProvider = DoubleCheck.provider(BootCompleteCacheImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
            this.configurationControllerImplProvider = DoubleCheck.provider(ConfigurationControllerImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider));
            this.globalSettingsImplProvider = GlobalSettingsImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.provideContentResolverProvider);
            Provider<Looper> provider = DoubleCheck.provider(SysUIConcurrencyModule_ProvideBroadcastRunningLooperFactory.create());
            this.provideBroadcastRunningLooperProvider = provider;
            this.provideBroadcastRunningExecutorProvider = DoubleCheck.provider(SysUIConcurrencyModule_ProvideBroadcastRunningExecutorFactory.create(provider));
            this.provideLogcatEchoTrackerProvider = DoubleCheck.provider(LogModule_ProvideLogcatEchoTrackerFactory.create(DaggerReferenceGlobalRootComponent.this.provideContentResolverProvider, GlobalConcurrencyModule_ProvideMainLooperFactory.create()));
            Provider<LogBufferFactory> provider2 = DoubleCheck.provider(LogBufferFactory_Factory.create(DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.provideLogcatEchoTrackerProvider));
            this.logBufferFactoryProvider = provider2;
            Provider<LogBuffer> provider3 = DoubleCheck.provider(LogModule_ProvideBroadcastDispatcherLogBufferFactory.create(provider2));
            this.provideBroadcastDispatcherLogBufferProvider = provider3;
            this.broadcastDispatcherLoggerProvider = BroadcastDispatcherLogger_Factory.create(provider3);
            Provider<Looper> provider4 = DoubleCheck.provider(SysUIConcurrencyModule_ProvideBgLooperFactory.create());
            this.provideBgLooperProvider = provider4;
            this.provideBgHandlerProvider = SysUIConcurrencyModule_ProvideBgHandlerFactory.create(provider4);
            this.provideUserTrackerProvider = DoubleCheck.provider(MultiUserUtilsModule_ProvideUserTrackerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideUserManagerProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.provideBgHandlerProvider));
            this.pendingRemovalStoreProvider = PendingRemovalStore_Factory.create(this.broadcastDispatcherLoggerProvider);
            this.broadcastDispatcherProvider = DoubleCheck.provider(BroadcastDispatcher_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.provideBroadcastRunningLooperProvider, this.provideBroadcastRunningExecutorProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.broadcastDispatcherLoggerProvider, this.provideUserTrackerProvider, this.pendingRemovalStoreProvider));
            this.provideDemoModeControllerProvider = DoubleCheck.provider(DemoModeModule_ProvideDemoModeControllerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.globalSettingsImplProvider, this.broadcastDispatcherProvider));
            this.providesLeakDetectorProvider = DoubleCheck.provider(LeakModule_ProvidesLeakDetectorFactory.create(leakModule, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, TrackedCollections_Factory.create()));
            Provider<TunerServiceImpl> provider5 = DoubleCheck.provider(TunerServiceImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.providesLeakDetectorProvider, this.provideDemoModeControllerProvider, this.provideUserTrackerProvider));
            this.tunerServiceImplProvider = provider5;
            this.tunerActivityProvider = TunerActivity_Factory.create(this.provideDemoModeControllerProvider, provider5, this.globalSettingsImplProvider);
            this.foregroundServicesDialogProvider = ForegroundServicesDialog_Factory.create(DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider);
            this.workLockActivityProvider = WorkLockActivity_Factory.create(this.broadcastDispatcherProvider, DaggerReferenceGlobalRootComponent.this.provideUserManagerProvider, DaggerReferenceGlobalRootComponent.this.providePackageManagerProvider);
            this.deviceConfigProxyProvider = DoubleCheck.provider(DeviceConfigProxy_Factory.create());
            this.enhancedEstimatesImplProvider = DoubleCheck.provider(EnhancedEstimatesImpl_Factory.create());
            this.provideBatteryControllerProvider = DoubleCheck.provider(AospPolicyModule_ProvideBatteryControllerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.enhancedEstimatesImplProvider, DaggerReferenceGlobalRootComponent.this.providePowerManagerProvider, this.broadcastDispatcherProvider, this.provideDemoModeControllerProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.provideBgHandlerProvider));
            this.dockManagerImplProvider = DoubleCheck.provider(DockManagerImpl_Factory.create());
            Provider<FalsingDataProvider> provider6 = DoubleCheck.provider(FalsingDataProvider_Factory.create(DaggerReferenceGlobalRootComponent.this.provideDisplayMetricsProvider, this.provideBatteryControllerProvider, this.dockManagerImplProvider));
            this.falsingDataProvider = provider6;
            DistanceClassifier_Factory create = DistanceClassifier_Factory.create(provider6, this.deviceConfigProxyProvider);
            this.distanceClassifierProvider = create;
            this.proximityClassifierProvider = ProximityClassifier_Factory.create(create, this.falsingDataProvider, this.deviceConfigProxyProvider);
            this.pointerCountClassifierProvider = PointerCountClassifier_Factory.create(this.falsingDataProvider);
            this.typeClassifierProvider = TypeClassifier_Factory.create(this.falsingDataProvider);
            this.diagonalClassifierProvider = DiagonalClassifier_Factory.create(this.falsingDataProvider, this.deviceConfigProxyProvider);
            ZigZagClassifier_Factory create2 = ZigZagClassifier_Factory.create(this.falsingDataProvider, this.deviceConfigProxyProvider);
            this.zigZagClassifierProvider = create2;
            this.providesBrightLineGestureClassifiersProvider = FalsingModule_ProvidesBrightLineGestureClassifiersFactory.create(this.distanceClassifierProvider, this.proximityClassifierProvider, this.pointerCountClassifierProvider, this.typeClassifierProvider, this.diagonalClassifierProvider, create2);
            this.namedSetOfFalsingClassifierProvider = SetFactory.builder(0, 1).addCollectionProvider(this.providesBrightLineGestureClassifiersProvider).build();
            FalsingModule_ProvidesSingleTapTouchSlopFactory create3 = FalsingModule_ProvidesSingleTapTouchSlopFactory.create(DaggerReferenceGlobalRootComponent.this.provideViewConfigurationProvider);
            this.providesSingleTapTouchSlopProvider = create3;
            this.singleTapClassifierProvider = SingleTapClassifier_Factory.create(this.falsingDataProvider, create3);
            FalsingModule_ProvidesLongTapTouchSlopFactory create4 = FalsingModule_ProvidesLongTapTouchSlopFactory.create(DaggerReferenceGlobalRootComponent.this.provideViewConfigurationProvider);
            this.providesLongTapTouchSlopProvider = create4;
            this.longTapClassifierProvider = LongTapClassifier_Factory.create(this.falsingDataProvider, create4);
            FalsingModule_ProvidesDoubleTapTouchSlopFactory create5 = FalsingModule_ProvidesDoubleTapTouchSlopFactory.create(DaggerReferenceGlobalRootComponent.this.provideResourcesProvider);
            this.providesDoubleTapTouchSlopProvider = create5;
            this.doubleTapClassifierProvider = DoubleTapClassifier_Factory.create(this.falsingDataProvider, this.singleTapClassifierProvider, create5, FalsingModule_ProvidesDoubleTapTimeoutMsFactory.create());
            Provider<SystemClock> provider7 = DoubleCheck.provider(SystemClockImpl_Factory.create());
            this.bindSystemClockProvider = provider7;
            this.historyTrackerProvider = DoubleCheck.provider(HistoryTracker_Factory.create(provider7));
            this.secureSettingsImplProvider = SecureSettingsImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.provideContentResolverProvider);
            this.provideBackgroundExecutorProvider = DoubleCheck.provider(SysUIConcurrencyModule_ProvideBackgroundExecutorFactory.create(this.provideBgLooperProvider));
            this.shadeExpansionStateManagerProvider = DoubleCheck.provider(ShadeExpansionStateManager_Factory.create());
            this.statusBarStateControllerImplProvider = DoubleCheck.provider(StatusBarStateControllerImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, DaggerReferenceGlobalRootComponent.this.provideInteractionJankMonitorProvider, this.shadeExpansionStateManagerProvider));
            this.protoTracerProvider = DoubleCheck.provider(ProtoTracer_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
            this.commandRegistryProvider = DoubleCheck.provider(CommandRegistry_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider));
            this.filesProvider = DoubleCheck.provider(Files_Factory.create());
            this.logBufferEulogizerProvider = DoubleCheck.provider(LogBufferEulogizer_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.bindSystemClockProvider, this.filesProvider));
            this.broadcastDispatcherStartableProvider = BroadcastDispatcherStartable_Factory.create(this.broadcastDispatcherProvider);
            this.keyguardStateControllerImplProvider = new DelegateFactory();
            this.notifLiveDataStoreImplProvider = DoubleCheck.provider(NotifLiveDataStoreImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider));
            this.provideFlagManagerProvider = FlagsModule_ProvideFlagManagerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider);
            this.systemPropertiesHelperProvider = DoubleCheck.provider(SystemPropertiesHelper_Factory.create());
            this.bindsReaderProvider = DoubleCheck.provider(ServerFlagReaderModule_BindsReaderFactory.create(this.deviceConfigProxyProvider, this.provideBackgroundExecutorProvider));
            this.wakefulnessLifecycleProvider = DoubleCheck.provider(WakefulnessLifecycle_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, FrameworkServicesModule_ProvideIWallPaperManagerFactory.create(), DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
            SystemExitRestarter_Factory create6 = SystemExitRestarter_Factory.create(DaggerReferenceGlobalRootComponent.this.provideIStatusBarServiceProvider);
            this.systemExitRestarterProvider = create6;
            this.featureFlagsDebugRestarterProvider = FeatureFlagsDebugRestarter_Factory.create(this.wakefulnessLifecycleProvider, create6);
            this.featureFlagsDebugProvider = DoubleCheck.provider(FeatureFlagsDebug_Factory.create(this.provideFlagManagerProvider, DaggerReferenceGlobalRootComponent.this.contextProvider, this.secureSettingsImplProvider, this.systemPropertiesHelperProvider, DaggerReferenceGlobalRootComponent.this.provideResourcesProvider, this.bindsReaderProvider, FlagsCommonModule_ProvidesAllFlagsFactory.create(), this.featureFlagsDebugRestarterProvider));
            NotifPipelineFlags_Factory create7 = NotifPipelineFlags_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.featureFlagsDebugProvider);
            this.notifPipelineFlagsProvider = create7;
            Provider<LogBuffer> provider8 = DoubleCheck.provider(LogModule_ProvideNotificationsLogBufferFactory.create(this.logBufferFactoryProvider, create7));
            this.provideNotificationsLogBufferProvider = provider8;
            this.notifCollectionLoggerProvider = NotifCollectionLogger_Factory.create(provider8);
            this.notifCollectionProvider = DoubleCheck.provider(NotifCollection_Factory.create(DaggerReferenceGlobalRootComponent.this.provideIStatusBarServiceProvider, this.bindSystemClockProvider, this.notifPipelineFlagsProvider, this.notifCollectionLoggerProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.provideBackgroundExecutorProvider, this.logBufferEulogizerProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
            this.notifPipelineChoreographerImplProvider = DoubleCheck.provider(NotifPipelineChoreographerImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.providesChoreographerProvider, DaggerReferenceGlobalRootComponent.this.provideMainDelayableExecutorProvider));
            Provider<NotificationClickNotifier> provider9 = DoubleCheck.provider(NotificationClickNotifier_Factory.create(DaggerReferenceGlobalRootComponent.this.provideIStatusBarServiceProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider));
            this.notificationClickNotifierProvider = provider9;
            this.notificationInteractionTrackerProvider = DoubleCheck.provider(NotificationInteractionTracker_Factory.create(provider9));
            this.shadeListBuilderLoggerProvider = ShadeListBuilderLogger_Factory.create(this.notifPipelineFlagsProvider, this.provideNotificationsLogBufferProvider);
            this.shadeListBuilderProvider = DoubleCheck.provider(ShadeListBuilder_Factory.create(DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.notifPipelineChoreographerImplProvider, this.notifPipelineFlagsProvider, this.notificationInteractionTrackerProvider, this.shadeListBuilderLoggerProvider, this.bindSystemClockProvider));
            Provider<RenderStageManager> provider10 = DoubleCheck.provider(RenderStageManager_Factory.create());
            this.renderStageManagerProvider = provider10;
            Provider<NotifPipeline> provider11 = DoubleCheck.provider(NotifPipeline_Factory.create(this.notifCollectionProvider, this.shadeListBuilderProvider, provider10));
            this.notifPipelineProvider = provider11;
            this.notificationVisibilityProviderImplProvider = DoubleCheck.provider(NotificationVisibilityProviderImpl_Factory.create(this.notifLiveDataStoreImplProvider, provider11));
            this.provideCommandQueueProvider = new DelegateFactory();
            this.setShellProvider = InstanceFactory.create(shellInterface);
            this.overviewProxyServiceProvider = new DelegateFactory();
            Provider<DeviceProvisionedControllerImpl> provider12 = DoubleCheck.provider(DeviceProvisionedControllerImpl_Factory.create(this.secureSettingsImplProvider, this.globalSettingsImplProvider, this.provideUserTrackerProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.provideBgHandlerProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider));
            this.deviceProvisionedControllerImplProvider = provider12;
            this.bindDeviceProvisionedControllerProvider = DoubleCheck.provider(ReferenceSystemUIModule_BindDeviceProvisionedControllerFactory.create(provider12));
            this.navigationModeControllerProvider = DoubleCheck.provider(NavigationModeController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.bindDeviceProvisionedControllerProvider, this.configurationControllerImplProvider, DaggerReferenceGlobalRootComponent.this.provideUiBackgroundExecutorProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
            this.provideSysUiStateProvider = DoubleCheck.provider(SystemUIModule_ProvideSysUiStateFactory.create(DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
            this.accessibilityButtonModeObserverProvider = DoubleCheck.provider(AccessibilityButtonModeObserver_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider));
            this.accessibilityButtonTargetsObserverProvider = DoubleCheck.provider(AccessibilityButtonTargetsObserver_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider));
            this.alwaysOnDisplayPolicyProvider = DoubleCheck.provider(AlwaysOnDisplayPolicy_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider));
            this.sysUIUnfoldComponentFactoryProvider = new Provider<SysUIUnfoldComponent.Factory>() { // from class: com.android.systemui.dagger.DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.1
                /* JADX DEBUG: Method merged with bridge method */
                /* renamed from: get */
                public SysUIUnfoldComponent.Factory m2231get() {
                    return new SysUIUnfoldComponentFactory();
                }
            };
            this.provideSysUIUnfoldComponentProvider = DoubleCheck.provider(SysUIUnfoldModule_ProvideSysUIUnfoldComponentFactory.create(sysUIUnfoldModule, DaggerReferenceGlobalRootComponent.this.unfoldTransitionProgressProvider, DaggerReferenceGlobalRootComponent.this.provideNaturalRotationProgressProvider, DaggerReferenceGlobalRootComponent.this.provideStatusBarScopedTransitionProvider, this.sysUIUnfoldComponentFactoryProvider));
            this.falsingManagerProxyProvider = new DelegateFactory();
            this.keyguardUpdateMonitorProvider = new DelegateFactory();
            this.asyncSensorManagerProvider = DoubleCheck.provider(AsyncSensorManager_Factory.create(DaggerReferenceGlobalRootComponent.this.providesSensorManagerProvider, ThreadFactoryImpl_Factory.create(), DaggerReferenceGlobalRootComponent.this.providesPluginManagerProvider));
            ThresholdSensorImpl_BuilderFactory_Factory create8 = ThresholdSensorImpl_BuilderFactory_Factory.create(DaggerReferenceGlobalRootComponent.this.provideResourcesProvider, this.asyncSensorManagerProvider, DaggerReferenceGlobalRootComponent.this.provideExecutionProvider);
            this.builderFactoryProvider = create8;
            this.providePostureToProximitySensorMappingProvider = SensorModule_ProvidePostureToProximitySensorMappingFactory.create(create8, DaggerReferenceGlobalRootComponent.this.provideResourcesProvider);
            this.providePostureToSecondaryProximitySensorMappingProvider = SensorModule_ProvidePostureToSecondaryProximitySensorMappingFactory.create(this.builderFactoryProvider, DaggerReferenceGlobalRootComponent.this.provideResourcesProvider);
            this.devicePostureControllerImplProvider = DoubleCheck.provider(DevicePostureControllerImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideDeviceStateManagerProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider));
            this.postureDependentProximitySensorProvider = PostureDependentProximitySensor_Factory.create(this.providePostureToProximitySensorMappingProvider, this.providePostureToSecondaryProximitySensorMappingProvider, DaggerReferenceGlobalRootComponent.this.provideMainDelayableExecutorProvider, DaggerReferenceGlobalRootComponent.this.provideExecutionProvider, this.devicePostureControllerImplProvider);
            this.builderProvider = ThresholdSensorImpl_Builder_Factory.create(DaggerReferenceGlobalRootComponent.this.provideResourcesProvider, this.asyncSensorManagerProvider, DaggerReferenceGlobalRootComponent.this.provideExecutionProvider);
            this.providePrimaryProximitySensorProvider = SensorModule_ProvidePrimaryProximitySensorFactory.create(DaggerReferenceGlobalRootComponent.this.providesSensorManagerProvider, this.builderProvider);
            SensorModule_ProvideSecondaryProximitySensorFactory create9 = SensorModule_ProvideSecondaryProximitySensorFactory.create(this.builderProvider);
            this.provideSecondaryProximitySensorProvider = create9;
            this.proximitySensorImplProvider = ProximitySensorImpl_Factory.create(this.providePrimaryProximitySensorProvider, create9, DaggerReferenceGlobalRootComponent.this.provideMainDelayableExecutorProvider, DaggerReferenceGlobalRootComponent.this.provideExecutionProvider);
            SensorModule_ProvideProximitySensorFactory create10 = SensorModule_ProvideProximitySensorFactory.create(DaggerReferenceGlobalRootComponent.this.provideResourcesProvider, this.postureDependentProximitySensorProvider, this.proximitySensorImplProvider);
            this.provideProximitySensorProvider = create10;
            this.falsingCollectorImplProvider = DoubleCheck.provider(FalsingCollectorImpl_Factory.create(this.falsingDataProvider, this.falsingManagerProxyProvider, this.keyguardUpdateMonitorProvider, this.historyTrackerProvider, create10, this.statusBarStateControllerImplProvider, this.keyguardStateControllerImplProvider, this.shadeExpansionStateManagerProvider, this.provideBatteryControllerProvider, this.dockManagerImplProvider, DaggerReferenceGlobalRootComponent.this.provideMainDelayableExecutorProvider, this.bindSystemClockProvider));
            DelegateFactory delegateFactory = new DelegateFactory();
            this.newKeyguardViewMediatorProvider = delegateFactory;
            this.providesViewMediatorCallbackProvider = KeyguardModule_ProvidesViewMediatorCallbackFactory.create(keyguardModule, delegateFactory);
            this.providesDreamOverlayServiceProvider = DreamModule_ProvidesDreamOverlayServiceFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider);
        }

        public final void initialize2(LeakModule leakModule, NightDisplayListenerModule nightDisplayListenerModule, SharedLibraryModule sharedLibraryModule, KeyguardModule keyguardModule, SysUIUnfoldModule sysUIUnfoldModule, ShellInterface shellInterface, Optional<Pip> optional, Optional<SplitScreen> optional2, Optional<OneHanded> optional3, Optional<Bubbles> optional4, Optional<TaskViewFactory> optional5, ShellTransitions shellTransitions, Optional<StartingSurface> optional6, Optional<DisplayAreaHelper> optional7, Optional<RecentTasks> optional8, Optional<BackAnimation> optional9, Optional<DesktopMode> optional10) {
            this.providesDreamOverlayEnabledProvider = DreamModule_ProvidesDreamOverlayEnabledFactory.create(DaggerReferenceGlobalRootComponent.this.providePackageManagerProvider, this.providesDreamOverlayServiceProvider);
            this.dreamOverlayStateControllerProvider = DoubleCheck.provider(DreamOverlayStateController_Factory.create(DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.providesDreamOverlayEnabledProvider));
            this.notificationShadeWindowControllerImplProvider = new DelegateFactory();
            this.notificationListenerProvider = DoubleCheck.provider(NotificationListener_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideNotificationManagerProvider, this.bindSystemClockProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, DaggerReferenceGlobalRootComponent.this.providesPluginManagerProvider));
            this.targetSdkResolverProvider = DoubleCheck.provider(TargetSdkResolver_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider));
            this.groupCoalescerLoggerProvider = GroupCoalescerLogger_Factory.create(this.provideNotificationsLogBufferProvider);
            this.groupCoalescerProvider = GroupCoalescer_Factory.create(DaggerReferenceGlobalRootComponent.this.provideMainDelayableExecutorProvider, this.bindSystemClockProvider, this.groupCoalescerLoggerProvider);
            Provider<CoordinatorsSubcomponent.Factory> provider = new Provider<CoordinatorsSubcomponent.Factory>() { // from class: com.android.systemui.dagger.DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.2
                /* JADX DEBUG: Method merged with bridge method */
                /* renamed from: get */
                public CoordinatorsSubcomponent.Factory m2239get() {
                    return new CoordinatorsSubcomponentFactory();
                }
            };
            this.coordinatorsSubcomponentFactoryProvider = provider;
            this.notifCoordinatorsProvider = DoubleCheck.provider(CoordinatorsModule_NotifCoordinatorsFactory.create(provider));
            Provider<NotifInflationErrorManager> provider2 = DoubleCheck.provider(NotifInflationErrorManager_Factory.create());
            this.notifInflationErrorManagerProvider = provider2;
            this.notifInflaterImplProvider = DoubleCheck.provider(NotifInflaterImpl_Factory.create(provider2));
            this.mediaContainerControllerProvider = DoubleCheck.provider(MediaContainerController_Factory.create(DaggerReferenceGlobalRootComponent.this.providerLayoutInflaterProvider));
            this.notificationSectionsFeatureManagerProvider = DoubleCheck.provider(NotificationSectionsFeatureManager_Factory.create(this.deviceConfigProxyProvider, DaggerReferenceGlobalRootComponent.this.contextProvider));
            this.sectionHeaderVisibilityProvider = DoubleCheck.provider(SectionHeaderVisibilityProvider_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider));
            this.nodeSpecBuilderLoggerProvider = NodeSpecBuilderLogger_Factory.create(this.notifPipelineFlagsProvider, this.provideNotificationsLogBufferProvider);
            this.shadeViewDifferLoggerProvider = ShadeViewDifferLogger_Factory.create(this.provideNotificationsLogBufferProvider);
            this.notifViewBarnProvider = DoubleCheck.provider(NotifViewBarn_Factory.create());
            ShadeViewManager_Factory create = ShadeViewManager_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.mediaContainerControllerProvider, this.notificationSectionsFeatureManagerProvider, this.sectionHeaderVisibilityProvider, this.nodeSpecBuilderLoggerProvider, this.shadeViewDifferLoggerProvider, this.notifViewBarnProvider);
            this.shadeViewManagerProvider = create;
            this.shadeViewManagerFactoryProvider = ShadeViewManagerFactory_Impl.create(create);
            this.notifPipelineInitializerProvider = DoubleCheck.provider(NotifPipelineInitializer_Factory.create(this.notifPipelineProvider, this.groupCoalescerProvider, this.notifCollectionProvider, this.shadeListBuilderProvider, this.renderStageManagerProvider, this.notifCoordinatorsProvider, this.notifInflaterImplProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.shadeViewManagerFactoryProvider));
            NotifBindPipelineLogger_Factory create2 = NotifBindPipelineLogger_Factory.create(this.provideNotificationsLogBufferProvider);
            this.notifBindPipelineLoggerProvider = create2;
            this.notifBindPipelineProvider = DoubleCheck.provider(NotifBindPipeline_Factory.create(this.notifPipelineProvider, create2, GlobalConcurrencyModule_ProvideMainLooperFactory.create()));
            NotifRemoteViewCacheImpl_Factory create3 = NotifRemoteViewCacheImpl_Factory.create(this.notifPipelineProvider);
            this.notifRemoteViewCacheImplProvider = create3;
            this.provideNotifRemoteViewCacheProvider = DoubleCheck.provider(create3);
            this.notificationLockscreenUserManagerImplProvider = new DelegateFactory();
            this.provideSmartReplyControllerProvider = DoubleCheck.provider(CentralSurfacesDependenciesModule_ProvideSmartReplyControllerFactory.create(DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.notificationVisibilityProviderImplProvider, DaggerReferenceGlobalRootComponent.this.provideIStatusBarServiceProvider, this.notificationClickNotifierProvider));
            this.optionalOfCentralSurfacesProvider = new DelegateFactory();
            this.remoteInputUriControllerProvider = DoubleCheck.provider(RemoteInputUriController_Factory.create(DaggerReferenceGlobalRootComponent.this.provideIStatusBarServiceProvider));
            Provider<LogBuffer> provider3 = DoubleCheck.provider(LogModule_ProvideNotifInteractionLogBufferFactory.create(this.logBufferFactoryProvider));
            this.provideNotifInteractionLogBufferProvider = provider3;
            this.actionClickLoggerProvider = ActionClickLogger_Factory.create(provider3);
            this.provideNotificationRemoteInputManagerProvider = DoubleCheck.provider(CentralSurfacesDependenciesModule_ProvideNotificationRemoteInputManagerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.notifPipelineFlagsProvider, this.notificationLockscreenUserManagerImplProvider, this.provideSmartReplyControllerProvider, this.notificationVisibilityProviderImplProvider, this.optionalOfCentralSurfacesProvider, this.statusBarStateControllerImplProvider, this.remoteInputUriControllerProvider, this.notificationClickNotifierProvider, this.actionClickLoggerProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
            Provider<BindEventManagerImpl> provider4 = DoubleCheck.provider(BindEventManagerImpl_Factory.create());
            this.bindEventManagerImplProvider = provider4;
            this.conversationNotificationManagerProvider = DoubleCheck.provider(ConversationNotificationManager_Factory.create(provider4, DaggerReferenceGlobalRootComponent.this.contextProvider, this.notifPipelineProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider));
            this.conversationNotificationProcessorProvider = ConversationNotificationProcessor_Factory.create(DaggerReferenceGlobalRootComponent.this.provideLauncherAppsProvider, this.conversationNotificationManagerProvider);
            this.mediaFeatureFlagProvider = MediaFeatureFlag_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider);
            this.smartReplyConstantsProvider = DoubleCheck.provider(SmartReplyConstants_Factory.create(DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, DaggerReferenceGlobalRootComponent.this.contextProvider, this.deviceConfigProxyProvider));
            this.provideActivityManagerWrapperProvider = DoubleCheck.provider(SharedLibraryModule_ProvideActivityManagerWrapperFactory.create(sharedLibraryModule));
            this.provideDevicePolicyManagerWrapperProvider = DoubleCheck.provider(SharedLibraryModule_ProvideDevicePolicyManagerWrapperFactory.create(sharedLibraryModule));
            Provider<KeyguardDismissUtil> provider5 = DoubleCheck.provider(KeyguardDismissUtil_Factory.create());
            this.keyguardDismissUtilProvider = provider5;
            this.smartReplyInflaterImplProvider = SmartReplyInflaterImpl_Factory.create(this.smartReplyConstantsProvider, provider5, this.provideNotificationRemoteInputManagerProvider, this.provideSmartReplyControllerProvider, DaggerReferenceGlobalRootComponent.this.contextProvider);
            Provider<ActivityStarterDelegate> provider6 = DoubleCheck.provider(ActivityStarterDelegate_Factory.create(this.optionalOfCentralSurfacesProvider));
            this.activityStarterDelegateProvider = provider6;
            this.provideActivityStarterProvider = PluginModule_ProvideActivityStarterFactory.create(provider6, DaggerReferenceGlobalRootComponent.this.pluginDependencyProvider);
            Provider<LogBuffer> provider7 = DoubleCheck.provider(LogModule_ProvideNotificationHeadsUpLogBufferFactory.create(this.logBufferFactoryProvider));
            this.provideNotificationHeadsUpLogBufferProvider = provider7;
            this.headsUpManagerLoggerProvider = HeadsUpManagerLogger_Factory.create(provider7);
            this.keyguardBypassControllerProvider = DoubleCheck.provider(KeyguardBypassController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.tunerServiceImplProvider, this.statusBarStateControllerImplProvider, this.notificationLockscreenUserManagerImplProvider, this.keyguardStateControllerImplProvider, this.shadeExpansionStateManagerProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
            this.provideGroupMembershipManagerProvider = DoubleCheck.provider(NotificationsModule_ProvideGroupMembershipManagerFactory.create());
            this.visualStabilityProvider = DoubleCheck.provider(VisualStabilityProvider_Factory.create());
            this.accessibilityManagerWrapperProvider = DoubleCheck.provider(AccessibilityManagerWrapper_Factory.create(DaggerReferenceGlobalRootComponent.this.provideAccessibilityManagerProvider));
            Provider<HeadsUpManagerPhone> provider8 = DoubleCheck.provider(ReferenceSystemUIModule_ProvideHeadsUpManagerPhoneFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.headsUpManagerLoggerProvider, this.statusBarStateControllerImplProvider, this.keyguardBypassControllerProvider, this.provideGroupMembershipManagerProvider, this.visualStabilityProvider, this.configurationControllerImplProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.accessibilityManagerWrapperProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, this.shadeExpansionStateManagerProvider));
            this.provideHeadsUpManagerPhoneProvider = provider8;
            this.smartActionInflaterImplProvider = SmartActionInflaterImpl_Factory.create(this.smartReplyConstantsProvider, this.provideActivityStarterProvider, this.provideSmartReplyControllerProvider, provider8);
            SmartReplyStateInflaterImpl_Factory create4 = SmartReplyStateInflaterImpl_Factory.create(this.smartReplyConstantsProvider, this.provideActivityManagerWrapperProvider, DaggerReferenceGlobalRootComponent.this.providePackageManagerWrapperProvider, this.provideDevicePolicyManagerWrapperProvider, this.smartReplyInflaterImplProvider, this.smartActionInflaterImplProvider);
            this.smartReplyStateInflaterImplProvider = create4;
            this.notificationContentInflaterProvider = DoubleCheck.provider(NotificationContentInflater_Factory.create(this.provideNotifRemoteViewCacheProvider, this.provideNotificationRemoteInputManagerProvider, this.conversationNotificationProcessorProvider, this.mediaFeatureFlagProvider, this.provideBackgroundExecutorProvider, create4));
            RowContentBindStageLogger_Factory create5 = RowContentBindStageLogger_Factory.create(this.provideNotificationsLogBufferProvider);
            this.rowContentBindStageLoggerProvider = create5;
            Provider<RowContentBindStage> provider9 = DoubleCheck.provider(RowContentBindStage_Factory.create(this.notificationContentInflaterProvider, this.notifInflationErrorManagerProvider, create5));
            this.rowContentBindStageProvider = provider9;
            this.notifBindPipelineInitializerProvider = NotifBindPipelineInitializer_Factory.create(this.notifBindPipelineProvider, provider9);
            this.expansionStateLoggerProvider = NotificationLogger_ExpansionStateLogger_Factory.create(DaggerReferenceGlobalRootComponent.this.provideUiBackgroundExecutorProvider);
            this.provideNotificationPanelLoggerProvider = DoubleCheck.provider(NotificationsModule_ProvideNotificationPanelLoggerFactory.create());
            this.provideNotificationLoggerProvider = DoubleCheck.provider(NotificationsModule_ProvideNotificationLoggerFactory.create(this.notificationListenerProvider, DaggerReferenceGlobalRootComponent.this.provideUiBackgroundExecutorProvider, this.notifLiveDataStoreImplProvider, this.notificationVisibilityProviderImplProvider, this.notifPipelineProvider, this.statusBarStateControllerImplProvider, this.shadeExpansionStateManagerProvider, this.expansionStateLoggerProvider, this.provideNotificationPanelLoggerProvider));
            this.expandableNotificationRowComponentBuilderProvider = new Provider<ExpandableNotificationRowComponent.Builder>() { // from class: com.android.systemui.dagger.DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.3
                /* JADX DEBUG: Method merged with bridge method */
                /* renamed from: get */
                public ExpandableNotificationRowComponent.Builder m2240get() {
                    return new ExpandableNotificationRowComponentBuilder();
                }
            };
            this.iconBuilderProvider = IconBuilder_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider);
            this.iconManagerProvider = DoubleCheck.provider(IconManager_Factory.create(this.notifPipelineProvider, DaggerReferenceGlobalRootComponent.this.provideLauncherAppsProvider, this.iconBuilderProvider));
            this.notificationRowBinderImplProvider = DoubleCheck.provider(NotificationRowBinderImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideNotificationMessagingUtilProvider, this.provideNotificationRemoteInputManagerProvider, this.notificationLockscreenUserManagerImplProvider, this.notifBindPipelineProvider, this.rowContentBindStageProvider, RowInflaterTask_Factory.create(), this.expandableNotificationRowComponentBuilderProvider, this.iconManagerProvider, this.featureFlagsDebugProvider));
            this.provideNotificationMediaManagerProvider = new DelegateFactory();
            this.headsUpViewBinderLoggerProvider = HeadsUpViewBinderLogger_Factory.create(this.provideNotificationHeadsUpLogBufferProvider);
            this.headsUpViewBinderProvider = DoubleCheck.provider(HeadsUpViewBinder_Factory.create(DaggerReferenceGlobalRootComponent.this.provideNotificationMessagingUtilProvider, this.rowContentBindStageProvider, this.headsUpViewBinderLoggerProvider));
            NotificationClickerLogger_Factory create6 = NotificationClickerLogger_Factory.create(this.provideNotifInteractionLogBufferProvider);
            this.notificationClickerLoggerProvider = create6;
            this.builderProvider2 = NotificationClicker_Builder_Factory.create(create6);
            this.animatedImageNotificationManagerProvider = DoubleCheck.provider(AnimatedImageNotificationManager_Factory.create(this.notifPipelineProvider, this.bindEventManagerImplProvider, this.provideHeadsUpManagerPhoneProvider, this.statusBarStateControllerImplProvider));
            this.setBubblesProvider = InstanceFactory.create(optional4);
            this.peopleSpaceWidgetManagerProvider = DoubleCheck.provider(PeopleSpaceWidgetManager_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideLauncherAppsProvider, this.notifPipelineProvider, DaggerReferenceGlobalRootComponent.this.providePackageManagerProvider, this.setBubblesProvider, DaggerReferenceGlobalRootComponent.this.provideUserManagerProvider, DaggerReferenceGlobalRootComponent.this.provideNotificationManagerProvider, this.broadcastDispatcherProvider, this.provideBackgroundExecutorProvider));
            this.provideIndividualSensorPrivacyControllerProvider = DoubleCheck.provider(ReferenceSystemUIModule_ProvideIndividualSensorPrivacyControllerFactory.create(DaggerReferenceGlobalRootComponent.this.provideSensorPrivacyManagerProvider));
            Provider<AppOpsControllerImpl> provider10 = DoubleCheck.provider(AppOpsControllerImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, DaggerReferenceGlobalRootComponent.this.provideAudioManagerProvider, this.provideIndividualSensorPrivacyControllerProvider, this.broadcastDispatcherProvider, this.bindSystemClockProvider));
            this.appOpsControllerImplProvider = provider10;
            this.foregroundServiceControllerProvider = DoubleCheck.provider(ForegroundServiceController_Factory.create(provider10, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider));
            this.foregroundServiceNotificationListenerProvider = DoubleCheck.provider(ForegroundServiceNotificationListener_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.foregroundServiceControllerProvider, this.notifPipelineProvider));
            this.notificationMemoryDumperProvider = DoubleCheck.provider(NotificationMemoryDumper_Factory.create(DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.notifPipelineProvider));
            this.mainDispatcherProvider = DoubleCheck.provider(CoroutinesModule_MainDispatcherFactory.create());
            Provider<NotificationMemoryLogger> provider11 = DoubleCheck.provider(NotificationMemoryLogger_Factory.create(this.notifPipelineProvider, DaggerReferenceGlobalRootComponent.this.provideStatsManagerProvider, this.mainDispatcherProvider, this.provideBackgroundExecutorProvider));
            this.notificationMemoryLoggerProvider = provider11;
            Provider<NotificationMemoryMonitor> provider12 = DoubleCheck.provider(NotificationMemoryMonitor_Factory.create(this.featureFlagsDebugProvider, this.notificationMemoryDumperProvider, provider11));
            this.notificationMemoryMonitorProvider = provider12;
            Provider<NotificationListener> provider13 = this.notificationListenerProvider;
            Provider<NotifPipeline> provider14 = this.notifPipelineProvider;
            this.notificationsControllerImplProvider = DoubleCheck.provider(NotificationsControllerImpl_Factory.create(provider13, provider14, provider14, this.notifLiveDataStoreImplProvider, this.targetSdkResolverProvider, this.notifPipelineInitializerProvider, this.notifBindPipelineInitializerProvider, this.provideNotificationLoggerProvider, this.notificationRowBinderImplProvider, this.provideNotificationMediaManagerProvider, this.headsUpViewBinderProvider, this.builderProvider2, this.animatedImageNotificationManagerProvider, this.peopleSpaceWidgetManagerProvider, this.setBubblesProvider, this.foregroundServiceNotificationListenerProvider, provider12, this.featureFlagsDebugProvider));
            this.notificationsControllerStubProvider = NotificationsControllerStub_Factory.create(this.notificationListenerProvider);
            this.provideNotificationsControllerProvider = DoubleCheck.provider(NotificationsModule_ProvideNotificationsControllerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.notificationsControllerImplProvider, this.notificationsControllerStubProvider));
            Provider<FragmentService.FragmentCreator.Factory> provider15 = new Provider<FragmentService.FragmentCreator.Factory>() { // from class: com.android.systemui.dagger.DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.4
                /* JADX DEBUG: Method merged with bridge method */
                /* renamed from: get */
                public FragmentService.FragmentCreator.Factory m2241get() {
                    return new FragmentCreatorFactory();
                }
            };
            this.fragmentCreatorFactoryProvider = provider15;
            this.fragmentServiceProvider = DoubleCheck.provider(FragmentService_Factory.create(provider15, this.configurationControllerImplProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
            LightBarTransitionsController_Factory create7 = LightBarTransitionsController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideCommandQueueProvider, this.keyguardStateControllerImplProvider, this.statusBarStateControllerImplProvider);
            this.lightBarTransitionsControllerProvider = create7;
            this.factoryProvider = LightBarTransitionsController_Factory_Impl.create(create7);
            this.darkIconDispatcherImplProvider = DoubleCheck.provider(DarkIconDispatcherImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.factoryProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
            this.lightBarControllerProvider = DoubleCheck.provider(LightBarController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.darkIconDispatcherImplProvider, this.provideBatteryControllerProvider, this.navigationModeControllerProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
            this.autoHideControllerProvider = DoubleCheck.provider(AutoHideController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, DaggerReferenceGlobalRootComponent.this.provideIWindowManagerProvider));
            this.providesStatusBarWindowViewProvider = DoubleCheck.provider(StatusBarWindowModule_ProvidesStatusBarWindowViewFactory.create(DaggerReferenceGlobalRootComponent.this.providerLayoutInflaterProvider));
            this.statusBarContentInsetsProvider = DoubleCheck.provider(StatusBarContentInsetsProvider_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.configurationControllerImplProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
            this.statusBarWindowControllerProvider = DoubleCheck.provider(StatusBarWindowController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.providesStatusBarWindowViewProvider, DaggerReferenceGlobalRootComponent.this.provideWindowManagerProvider, DaggerReferenceGlobalRootComponent.this.provideIWindowManagerProvider, this.statusBarContentInsetsProvider, DaggerReferenceGlobalRootComponent.this.provideResourcesProvider, DaggerReferenceGlobalRootComponent.this.unfoldTransitionProgressProvider));
            this.statusBarWindowStateControllerProvider = DoubleCheck.provider(StatusBarWindowStateController_Factory.create(DaggerReferenceGlobalRootComponent.this.provideDisplayIdProvider, this.provideCommandQueueProvider));
            this.provideStatusBarIconListProvider = DoubleCheck.provider(CentralSurfacesDependenciesModule_ProvideStatusBarIconListFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider));
            this.statusBarPipelineFlagsProvider = DoubleCheck.provider(StatusBarPipelineFlags_Factory.create(this.featureFlagsDebugProvider));
            this.statusBarIconControllerImplProvider = DoubleCheck.provider(StatusBarIconControllerImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideCommandQueueProvider, this.provideDemoModeControllerProvider, this.configurationControllerImplProvider, this.tunerServiceImplProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.provideStatusBarIconListProvider, this.statusBarPipelineFlagsProvider));
            this.carrierConfigTrackerProvider = DoubleCheck.provider(CarrierConfigTracker_Factory.create(DaggerReferenceGlobalRootComponent.this.provideCarrierConfigManagerProvider, this.broadcastDispatcherProvider));
            this.callbackHandlerProvider = DoubleCheck.provider(CallbackHandler_Factory.create(GlobalConcurrencyModule_ProvideMainLooperFactory.create()));
            this.telephonyListenerManagerProvider = DoubleCheck.provider(TelephonyListenerManager_Factory.create(DaggerReferenceGlobalRootComponent.this.provideTelephonyManagerProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, TelephonyCallback_Factory.create()));
            this.wifiPickerTrackerFactoryProvider = DoubleCheck.provider(AccessPointControllerImpl_WifiPickerTrackerFactory_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideWifiManagerProvider, DaggerReferenceGlobalRootComponent.this.provideConnectivityManagagerProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.provideBgHandlerProvider));
            this.provideAccessPointControllerImplProvider = DoubleCheck.provider(StatusBarPolicyModule_ProvideAccessPointControllerImplFactory.create(DaggerReferenceGlobalRootComponent.this.provideUserManagerProvider, this.provideUserTrackerProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.wifiPickerTrackerFactoryProvider));
        }

        public final void initialize3(LeakModule leakModule, NightDisplayListenerModule nightDisplayListenerModule, SharedLibraryModule sharedLibraryModule, KeyguardModule keyguardModule, SysUIUnfoldModule sysUIUnfoldModule, ShellInterface shellInterface, Optional<Pip> optional, Optional<SplitScreen> optional2, Optional<OneHanded> optional3, Optional<Bubbles> optional4, Optional<TaskViewFactory> optional5, ShellTransitions shellTransitions, Optional<StartingSurface> optional6, Optional<DisplayAreaHelper> optional7, Optional<RecentTasks> optional8, Optional<BackAnimation> optional9, Optional<DesktopMode> optional10) {
            this.wifiStatusTrackerFactoryProvider = WifiStatusTrackerFactory_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideWifiManagerProvider, DaggerReferenceGlobalRootComponent.this.provideNetworkScoreManagerProvider, DaggerReferenceGlobalRootComponent.this.provideConnectivityManagagerProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider);
            this.mobileSignalControllerFactoryProvider = DoubleCheck.provider(MobileSignalControllerFactory_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.callbackHandlerProvider, this.carrierConfigTrackerProvider, MobileMappingsProxyImpl_Factory.create()));
            this.toastFactoryProvider = DoubleCheck.provider(ToastFactory_Factory.create(DaggerReferenceGlobalRootComponent.this.providerLayoutInflaterProvider, DaggerReferenceGlobalRootComponent.this.providesPluginManagerProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
            this.locationControllerImplProvider = DoubleCheck.provider(LocationControllerImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.appOpsControllerImplProvider, this.deviceConfigProxyProvider, GlobalConcurrencyModule_ProvideMainLooperFactory.create(), this.provideBgHandlerProvider, this.broadcastDispatcherProvider, this.bootCompleteCacheImplProvider, this.provideUserTrackerProvider, DaggerReferenceGlobalRootComponent.this.providePackageManagerProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, this.secureSettingsImplProvider));
            this.statusBarKeyguardViewManagerProvider = new DelegateFactory();
            this.provideDialogLaunchAnimatorProvider = DoubleCheck.provider(CentralSurfacesDependenciesModule_ProvideDialogLaunchAnimatorFactory.create(DaggerReferenceGlobalRootComponent.this.provideIDreamManagerProvider, this.keyguardStateControllerImplProvider, this.statusBarKeyguardViewManagerProvider, DaggerReferenceGlobalRootComponent.this.provideInteractionJankMonitorProvider));
            Provider<DelayableExecutor> provider = DoubleCheck.provider(SysUIConcurrencyModule_ProvideBackgroundDelayableExecutorFactory.create(this.provideBgLooperProvider));
            this.provideBackgroundDelayableExecutorProvider = provider;
            this.wifiStateWorkerProvider = DoubleCheck.provider(WifiStateWorker_Factory.create(this.broadcastDispatcherProvider, provider, DaggerReferenceGlobalRootComponent.this.provideWifiManagerProvider));
            this.internetDialogControllerProvider = InternetDialogController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, this.provideActivityStarterProvider, this.provideAccessPointControllerImplProvider, DaggerReferenceGlobalRootComponent.this.provideSubscriptionManagerProvider, DaggerReferenceGlobalRootComponent.this.provideTelephonyManagerProvider, DaggerReferenceGlobalRootComponent.this.provideWifiManagerProvider, DaggerReferenceGlobalRootComponent.this.provideConnectivityManagagerProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.broadcastDispatcherProvider, this.keyguardUpdateMonitorProvider, this.globalSettingsImplProvider, this.keyguardStateControllerImplProvider, DaggerReferenceGlobalRootComponent.this.provideWindowManagerProvider, this.toastFactoryProvider, this.provideBgHandlerProvider, this.carrierConfigTrackerProvider, this.locationControllerImplProvider, this.provideDialogLaunchAnimatorProvider, this.wifiStateWorkerProvider, this.featureFlagsDebugProvider);
            this.internetDialogFactoryProvider = DoubleCheck.provider(InternetDialogFactory_Factory.create(DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.provideBackgroundExecutorProvider, this.internetDialogControllerProvider, DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, this.provideDialogLaunchAnimatorProvider, this.keyguardStateControllerImplProvider));
            this.provideStatusBarNetworkControllerBufferProvider = DoubleCheck.provider(LogModule_ProvideStatusBarNetworkControllerBufferFactory.create(this.logBufferFactoryProvider));
            this.networkControllerImplProvider = DoubleCheck.provider(NetworkControllerImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideBgLooperProvider, this.provideBackgroundExecutorProvider, DaggerReferenceGlobalRootComponent.this.provideSubscriptionManagerProvider, this.callbackHandlerProvider, this.bindDeviceProvisionedControllerProvider, this.broadcastDispatcherProvider, this.provideUserTrackerProvider, DaggerReferenceGlobalRootComponent.this.provideConnectivityManagagerProvider, DaggerReferenceGlobalRootComponent.this.provideTelephonyManagerProvider, this.telephonyListenerManagerProvider, DaggerReferenceGlobalRootComponent.this.provideWifiManagerProvider, this.provideAccessPointControllerImplProvider, this.statusBarPipelineFlagsProvider, this.provideDemoModeControllerProvider, this.carrierConfigTrackerProvider, this.wifiStatusTrackerFactoryProvider, this.mobileSignalControllerFactoryProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.internetDialogFactoryProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.provideStatusBarNetworkControllerBufferProvider));
            this.securityControllerImplProvider = DoubleCheck.provider(SecurityControllerImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideUserTrackerProvider, this.provideBgHandlerProvider, this.broadcastDispatcherProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.provideBackgroundExecutorProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
            this.statusBarSignalPolicyProvider = DoubleCheck.provider(StatusBarSignalPolicy_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.statusBarIconControllerImplProvider, this.carrierConfigTrackerProvider, this.networkControllerImplProvider, this.securityControllerImplProvider, this.tunerServiceImplProvider));
            this.dozeParametersProvider = new DelegateFactory();
            this.screenOffAnimationControllerProvider = new DelegateFactory();
            this.notificationWakeUpCoordinatorLoggerProvider = NotificationWakeUpCoordinatorLogger_Factory.create(this.provideNotificationsLogBufferProvider);
            this.notificationWakeUpCoordinatorProvider = DoubleCheck.provider(NotificationWakeUpCoordinator_Factory.create(DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.provideHeadsUpManagerPhoneProvider, this.statusBarStateControllerImplProvider, this.keyguardBypassControllerProvider, this.dozeParametersProvider, this.screenOffAnimationControllerProvider, this.notificationWakeUpCoordinatorLoggerProvider));
            Provider<LogBuffer> provider2 = DoubleCheck.provider(LogModule_ProvideNotificationRenderLogBufferFactory.create(this.logBufferFactoryProvider));
            this.provideNotificationRenderLogBufferProvider = provider2;
            NotificationRoundnessLogger_Factory create = NotificationRoundnessLogger_Factory.create(provider2);
            this.notificationRoundnessLoggerProvider = create;
            this.notificationRoundnessManagerProvider = DoubleCheck.provider(NotificationRoundnessManager_Factory.create(this.notificationSectionsFeatureManagerProvider, create, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.featureFlagsDebugProvider));
            this.provideLSShadeTransitionControllerBufferProvider = DoubleCheck.provider(LogModule_ProvideLSShadeTransitionControllerBufferFactory.create(this.logBufferFactoryProvider));
            Provider<LockscreenGestureLogger> provider3 = DoubleCheck.provider(LockscreenGestureLogger_Factory.create(DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider));
            this.lockscreenGestureLoggerProvider = provider3;
            this.lSShadeTransitionLoggerProvider = LSShadeTransitionLogger_Factory.create(this.provideLSShadeTransitionControllerBufferProvider, provider3, DaggerReferenceGlobalRootComponent.this.provideDisplayMetricsProvider);
            this.builderProvider3 = WakeLock_Builder_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider);
            this.broadcastSenderProvider = DoubleCheck.provider(BroadcastSender_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.builderProvider3, this.provideBackgroundExecutorProvider));
            this.mediaHostStatesManagerProvider = DoubleCheck.provider(MediaHostStatesManager_Factory.create());
            Provider<LogBuffer> provider4 = DoubleCheck.provider(LogModule_ProvideMediaViewLogBufferFactory.create(this.logBufferFactoryProvider));
            this.provideMediaViewLogBufferProvider = provider4;
            this.mediaViewLoggerProvider = DoubleCheck.provider(MediaViewLogger_Factory.create(provider4));
            this.mediaViewControllerProvider = MediaViewController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.configurationControllerImplProvider, this.mediaHostStatesManagerProvider, this.mediaViewLoggerProvider);
            Provider<RepeatableExecutor> provider5 = DoubleCheck.provider(SysUIConcurrencyModule_ProvideBackgroundRepeatableExecutorFactory.create(this.provideBackgroundDelayableExecutorProvider));
            this.provideBackgroundRepeatableExecutorProvider = provider5;
            this.seekBarViewModelProvider = SeekBarViewModel_Factory.create(provider5, this.falsingManagerProxyProvider);
            this.mediaControllerFactoryProvider = MediaControllerFactory_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider);
            Provider<LogBuffer> provider6 = DoubleCheck.provider(LogModule_ProvidesMediaTimeoutListenerLogBufferFactory.create(this.logBufferFactoryProvider));
            this.providesMediaTimeoutListenerLogBufferProvider = provider6;
            this.mediaTimeoutLoggerProvider = DoubleCheck.provider(MediaTimeoutLogger_Factory.create(provider6));
            this.mediaTimeoutListenerProvider = DoubleCheck.provider(MediaTimeoutListener_Factory.create(this.mediaControllerFactoryProvider, DaggerReferenceGlobalRootComponent.this.provideMainDelayableExecutorProvider, this.mediaTimeoutLoggerProvider, this.statusBarStateControllerImplProvider, this.bindSystemClockProvider));
            this.mediaBrowserFactoryProvider = MediaBrowserFactory_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider);
            Provider<LogBuffer> provider7 = DoubleCheck.provider(LogModule_ProvideMediaBrowserBufferFactory.create(this.logBufferFactoryProvider));
            this.provideMediaBrowserBufferProvider = provider7;
            this.resumeMediaBrowserLoggerProvider = DoubleCheck.provider(ResumeMediaBrowserLogger_Factory.create(provider7));
            this.resumeMediaBrowserFactoryProvider = ResumeMediaBrowserFactory_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.mediaBrowserFactoryProvider, this.resumeMediaBrowserLoggerProvider);
            this.mediaResumeListenerProvider = DoubleCheck.provider(MediaResumeListener_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.broadcastDispatcherProvider, this.provideUserTrackerProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.provideBackgroundExecutorProvider, this.tunerServiceImplProvider, this.resumeMediaBrowserFactoryProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.bindSystemClockProvider));
            this.mediaSessionBasedFilterProvider = MediaSessionBasedFilter_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideMediaSessionManagerProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.provideBackgroundExecutorProvider);
            this.provideLocalBluetoothControllerProvider = DoubleCheck.provider(SettingsLibraryModule_ProvideLocalBluetoothControllerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideBgHandlerProvider));
            this.localMediaManagerFactoryProvider = LocalMediaManagerFactory_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideLocalBluetoothControllerProvider);
            this.mediaFlagsProvider = DoubleCheck.provider(MediaFlags_Factory.create(this.featureFlagsDebugProvider));
            Provider<LogBuffer> provider8 = DoubleCheck.provider(LogModule_ProvideMediaMuteAwaitLogBufferFactory.create(this.logBufferFactoryProvider));
            this.provideMediaMuteAwaitLogBufferProvider = provider8;
            this.mediaMuteAwaitLoggerProvider = DoubleCheck.provider(MediaMuteAwaitLogger_Factory.create(provider8));
            this.mediaMuteAwaitConnectionManagerFactoryProvider = DoubleCheck.provider(MediaMuteAwaitConnectionManagerFactory_Factory.create(this.mediaFlagsProvider, DaggerReferenceGlobalRootComponent.this.contextProvider, this.mediaMuteAwaitLoggerProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider));
            this.mediaDeviceManagerProvider = MediaDeviceManager_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.mediaControllerFactoryProvider, this.localMediaManagerFactoryProvider, DaggerReferenceGlobalRootComponent.this.provideMediaRouter2ManagerProvider, this.mediaMuteAwaitConnectionManagerFactoryProvider, this.configurationControllerImplProvider, this.provideLocalBluetoothControllerProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.provideBackgroundExecutorProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider);
            this.mediaUiEventLoggerProvider = DoubleCheck.provider(MediaUiEventLogger_Factory.create(DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider));
            this.mediaDataFilterProvider = MediaDataFilter_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideUserTrackerProvider, this.broadcastSenderProvider, this.notificationLockscreenUserManagerImplProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.bindSystemClockProvider, this.mediaUiEventLoggerProvider);
            this.mediaDataManagerProvider = DoubleCheck.provider(MediaDataManager_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideBackgroundExecutorProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, DaggerReferenceGlobalRootComponent.this.provideMainDelayableExecutorProvider, this.mediaControllerFactoryProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.broadcastDispatcherProvider, this.mediaTimeoutListenerProvider, this.mediaResumeListenerProvider, this.mediaSessionBasedFilterProvider, this.mediaDeviceManagerProvider, MediaDataCombineLatest_Factory.create(), this.mediaDataFilterProvider, this.provideActivityStarterProvider, SmartspaceMediaDataProvider_Factory.create(), this.bindSystemClockProvider, this.tunerServiceImplProvider, this.mediaFlagsProvider, this.mediaUiEventLoggerProvider, DaggerReferenceGlobalRootComponent.this.provideSmartspaceManagerProvider));
            Provider<LogBuffer> provider9 = DoubleCheck.provider(LogModule_ProvideNearbyMediaDevicesLogBufferFactory.create(this.logBufferFactoryProvider));
            this.provideNearbyMediaDevicesLogBufferProvider = provider9;
            Provider<NearbyMediaDevicesLogger> provider10 = DoubleCheck.provider(NearbyMediaDevicesLogger_Factory.create(provider9));
            this.nearbyMediaDevicesLoggerProvider = provider10;
            Provider<NearbyMediaDevicesManager> provider11 = DoubleCheck.provider(NearbyMediaDevicesManager_Factory.create(this.provideCommandQueueProvider, provider10));
            this.nearbyMediaDevicesManagerProvider = provider11;
            this.providesNearbyMediaDevicesManagerProvider = DoubleCheck.provider(MediaModule_ProvidesNearbyMediaDevicesManagerFactory.create(this.mediaFlagsProvider, provider11));
            this.mediaOutputDialogFactoryProvider = MediaOutputDialogFactory_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideMediaSessionManagerProvider, this.provideLocalBluetoothControllerProvider, this.provideActivityStarterProvider, this.broadcastSenderProvider, this.notifPipelineProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, this.provideDialogLaunchAnimatorProvider, this.providesNearbyMediaDevicesManagerProvider, DaggerReferenceGlobalRootComponent.this.provideAudioManagerProvider, DaggerReferenceGlobalRootComponent.this.providePowerExemptionManagerProvider, DaggerReferenceGlobalRootComponent.this.provideKeyguardManagerProvider, this.featureFlagsDebugProvider);
            this.mediaCarouselControllerProvider = new DelegateFactory();
            this.activityIntentHelperProvider = DoubleCheck.provider(ActivityIntentHelper_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider));
            this.broadcastDialogControllerProvider = DoubleCheck.provider(BroadcastDialogController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, this.provideDialogLaunchAnimatorProvider, this.mediaOutputDialogFactoryProvider));
            this.mediaControlPanelProvider = MediaControlPanel_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideBackgroundExecutorProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.provideActivityStarterProvider, this.broadcastSenderProvider, this.mediaViewControllerProvider, this.seekBarViewModelProvider, this.mediaDataManagerProvider, this.mediaOutputDialogFactoryProvider, this.mediaCarouselControllerProvider, this.falsingManagerProxyProvider, this.bindSystemClockProvider, this.mediaUiEventLoggerProvider, this.keyguardStateControllerImplProvider, this.activityIntentHelperProvider, this.notificationLockscreenUserManagerImplProvider, this.broadcastDialogControllerProvider, this.featureFlagsDebugProvider);
            Provider<LogBuffer> provider12 = DoubleCheck.provider(LogModule_ProvideMediaCarouselControllerBufferFactory.create(this.logBufferFactoryProvider));
            this.provideMediaCarouselControllerBufferProvider = provider12;
            this.mediaCarouselControllerLoggerProvider = DoubleCheck.provider(MediaCarouselControllerLogger_Factory.create(provider12));
            DelegateFactory.setDelegate(this.mediaCarouselControllerProvider, DoubleCheck.provider(MediaCarouselController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.mediaControlPanelProvider, this.visualStabilityProvider, this.mediaHostStatesManagerProvider, this.provideActivityStarterProvider, this.bindSystemClockProvider, DaggerReferenceGlobalRootComponent.this.provideMainDelayableExecutorProvider, this.mediaDataManagerProvider, this.configurationControllerImplProvider, this.falsingCollectorImplProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.mediaUiEventLoggerProvider, this.mediaCarouselControllerLoggerProvider)));
            this.mediaHierarchyManagerProvider = DoubleCheck.provider(MediaHierarchyManager_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.statusBarStateControllerImplProvider, this.keyguardStateControllerImplProvider, this.keyguardBypassControllerProvider, this.mediaCarouselControllerProvider, this.statusBarKeyguardViewManagerProvider, this.dreamOverlayStateControllerProvider, this.configurationControllerImplProvider, this.wakefulnessLifecycleProvider, this.shadeExpansionStateManagerProvider, this.secureSettingsImplProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider));
            Provider<MediaHost> provider13 = DoubleCheck.provider(MediaModule_ProvidesKeyguardMediaHostFactory.create(MediaHost_MediaHostStateHolder_Factory.create(), this.mediaHierarchyManagerProvider, this.mediaDataManagerProvider, this.mediaHostStatesManagerProvider));
            this.providesKeyguardMediaHostProvider = provider13;
            this.keyguardMediaControllerProvider = DoubleCheck.provider(KeyguardMediaController_Factory.create(provider13, this.keyguardBypassControllerProvider, this.statusBarStateControllerImplProvider, DaggerReferenceGlobalRootComponent.this.contextProvider, this.secureSettingsImplProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.configurationControllerImplProvider));
            Provider<SectionHeaderControllerSubcomponent.Builder> provider14 = new Provider<SectionHeaderControllerSubcomponent.Builder>() { // from class: com.android.systemui.dagger.DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.5
                /* JADX DEBUG: Method merged with bridge method */
                /* renamed from: get */
                public SectionHeaderControllerSubcomponent.Builder m2242get() {
                    return new SectionHeaderControllerSubcomponentBuilder();
                }
            };
            this.sectionHeaderControllerSubcomponentBuilderProvider = provider14;
            Provider<SectionHeaderControllerSubcomponent> provider15 = DoubleCheck.provider(NotificationSectionHeadersModule_ProvidesIncomingHeaderSubcomponentFactory.create(provider14));
            this.providesIncomingHeaderSubcomponentProvider = provider15;
            this.providesIncomingHeaderControllerProvider = NotificationSectionHeadersModule_ProvidesIncomingHeaderControllerFactory.create(provider15);
            Provider<SectionHeaderControllerSubcomponent> provider16 = DoubleCheck.provider(NotificationSectionHeadersModule_ProvidesPeopleHeaderSubcomponentFactory.create(this.sectionHeaderControllerSubcomponentBuilderProvider));
            this.providesPeopleHeaderSubcomponentProvider = provider16;
            this.providesPeopleHeaderControllerProvider = NotificationSectionHeadersModule_ProvidesPeopleHeaderControllerFactory.create(provider16);
            Provider<SectionHeaderControllerSubcomponent> provider17 = DoubleCheck.provider(NotificationSectionHeadersModule_ProvidesAlertingHeaderSubcomponentFactory.create(this.sectionHeaderControllerSubcomponentBuilderProvider));
            this.providesAlertingHeaderSubcomponentProvider = provider17;
            this.providesAlertingHeaderControllerProvider = NotificationSectionHeadersModule_ProvidesAlertingHeaderControllerFactory.create(provider17);
            Provider<SectionHeaderControllerSubcomponent> provider18 = DoubleCheck.provider(NotificationSectionHeadersModule_ProvidesSilentHeaderSubcomponentFactory.create(this.sectionHeaderControllerSubcomponentBuilderProvider));
            this.providesSilentHeaderSubcomponentProvider = provider18;
            NotificationSectionHeadersModule_ProvidesSilentHeaderControllerFactory create2 = NotificationSectionHeadersModule_ProvidesSilentHeaderControllerFactory.create(provider18);
            this.providesSilentHeaderControllerProvider = create2;
            this.notificationSectionsManagerProvider = NotificationSectionsManager_Factory.create(this.configurationControllerImplProvider, this.keyguardMediaControllerProvider, this.notificationSectionsFeatureManagerProvider, this.mediaContainerControllerProvider, this.notificationRoundnessManagerProvider, this.providesIncomingHeaderControllerProvider, this.providesPeopleHeaderControllerProvider, this.providesAlertingHeaderControllerProvider, create2, this.featureFlagsDebugProvider);
            this.ambientStateProvider = DoubleCheck.provider(AmbientState_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.notificationSectionsManagerProvider, this.keyguardBypassControllerProvider, this.statusBarKeyguardViewManagerProvider));
            this.builderProvider4 = DelayedWakeLock_Builder_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider);
            Provider<LogBuffer> provider19 = DoubleCheck.provider(LogModule_ProvideDozeLogBufferFactory.create(this.logBufferFactoryProvider));
            this.provideDozeLogBufferProvider = provider19;
            this.dozeLoggerProvider = DozeLogger_Factory.create(provider19);
            Provider<DozeLog> provider20 = DoubleCheck.provider(DozeLog_Factory.create(this.keyguardUpdateMonitorProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.dozeLoggerProvider));
            this.dozeLogProvider = provider20;
            this.dozeScrimControllerProvider = DoubleCheck.provider(DozeScrimController_Factory.create(this.dozeParametersProvider, provider20, this.statusBarStateControllerImplProvider));
            this.provideAssistUtilsProvider = DoubleCheck.provider(AssistModule_ProvideAssistUtilsFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider));
            this.phoneStateMonitorProvider = DoubleCheck.provider(PhoneStateMonitor_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.broadcastDispatcherProvider, this.optionalOfCentralSurfacesProvider, this.bootCompleteCacheImplProvider, this.statusBarStateControllerImplProvider));
            this.assistLoggerProvider = DoubleCheck.provider(AssistLogger_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, this.provideAssistUtilsProvider, this.phoneStateMonitorProvider, this.provideUserTrackerProvider));
            this.assistManagerProvider = new DelegateFactory();
            this.defaultUiControllerProvider = DoubleCheck.provider(DefaultUiController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.assistLoggerProvider, DaggerReferenceGlobalRootComponent.this.provideWindowManagerProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.assistManagerProvider));
            DelegateFactory.setDelegate(this.assistManagerProvider, DoubleCheck.provider(AssistManager_Factory.create(this.bindDeviceProvisionedControllerProvider, DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideAssistUtilsProvider, this.provideCommandQueueProvider, this.phoneStateMonitorProvider, this.overviewProxyServiceProvider, this.provideSysUiStateProvider, this.defaultUiControllerProvider, this.assistLoggerProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider)));
            Provider<ExtensionControllerImpl> provider21 = DoubleCheck.provider(ExtensionControllerImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.providesLeakDetectorProvider, DaggerReferenceGlobalRootComponent.this.providesPluginManagerProvider, this.tunerServiceImplProvider, this.configurationControllerImplProvider));
            this.extensionControllerImplProvider = provider21;
            Provider<NotificationPersonExtractorPluginBoundary> provider22 = DoubleCheck.provider(NotificationPersonExtractorPluginBoundary_Factory.create(provider21));
            this.notificationPersonExtractorPluginBoundaryProvider = provider22;
            Provider<PeopleNotificationIdentifierImpl> provider23 = DoubleCheck.provider(PeopleNotificationIdentifierImpl_Factory.create(provider22, this.provideGroupMembershipManagerProvider));
            this.peopleNotificationIdentifierImplProvider = provider23;
            this.highPriorityProvider = DoubleCheck.provider(HighPriorityProvider_Factory.create(provider23, this.provideGroupMembershipManagerProvider));
            this.channelEditorDialogControllerProvider = DoubleCheck.provider(ChannelEditorDialogController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideINotificationManagerProvider, ChannelEditorDialog_Builder_Factory.create()));
            this.assistantFeedbackControllerProvider = DoubleCheck.provider(AssistantFeedbackController_Factory.create(DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, DaggerReferenceGlobalRootComponent.this.contextProvider, this.deviceConfigProxyProvider));
            this.shadeControllerImplProvider = new DelegateFactory();
            Provider<LogBuffer> provider24 = DoubleCheck.provider(LogModule_ProvideNotificationInterruptLogBufferFactory.create(this.logBufferFactoryProvider));
            this.provideNotificationInterruptLogBufferProvider = provider24;
            this.notificationInterruptLoggerProvider = NotificationInterruptLogger_Factory.create(provider24);
            this.keyguardNotificationVisibilityProviderImplProvider = new DelegateFactory();
            this.notificationInterruptStateProviderImplProvider = DoubleCheck.provider(NotificationInterruptStateProviderImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.provideContentResolverProvider, DaggerReferenceGlobalRootComponent.this.providePowerManagerProvider, DaggerReferenceGlobalRootComponent.this.provideIDreamManagerProvider, DaggerReferenceGlobalRootComponent.this.provideAmbientDisplayConfigurationProvider, this.provideBatteryControllerProvider, this.statusBarStateControllerImplProvider, this.keyguardStateControllerImplProvider, this.provideHeadsUpManagerPhoneProvider, this.notificationInterruptLoggerProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.notifPipelineFlagsProvider, this.keyguardNotificationVisibilityProviderImplProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider));
        }

        public final void initialize4(LeakModule leakModule, NightDisplayListenerModule nightDisplayListenerModule, SharedLibraryModule sharedLibraryModule, KeyguardModule keyguardModule, SysUIUnfoldModule sysUIUnfoldModule, ShellInterface shellInterface, Optional<Pip> optional, Optional<SplitScreen> optional2, Optional<OneHanded> optional3, Optional<Bubbles> optional4, Optional<TaskViewFactory> optional5, ShellTransitions shellTransitions, Optional<StartingSurface> optional6, Optional<DisplayAreaHelper> optional7, Optional<RecentTasks> optional8, Optional<BackAnimation> optional9, Optional<DesktopMode> optional10) {
            this.zenModeControllerImplProvider = DoubleCheck.provider(ZenModeControllerImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.broadcastDispatcherProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.globalSettingsImplProvider, this.provideUserTrackerProvider));
            Provider provider = DaggerReferenceGlobalRootComponent.this.contextProvider;
            Provider<Optional<Bubbles>> provider2 = this.setBubblesProvider;
            Provider<NotificationShadeWindowControllerImpl> provider3 = this.notificationShadeWindowControllerImplProvider;
            Provider<KeyguardStateControllerImpl> provider4 = this.keyguardStateControllerImplProvider;
            Provider<ShadeControllerImpl> provider5 = this.shadeControllerImplProvider;
            Provider provider6 = DaggerReferenceGlobalRootComponent.this.provideIStatusBarServiceProvider;
            Provider provider7 = DaggerReferenceGlobalRootComponent.this.provideINotificationManagerProvider;
            Provider provider8 = DaggerReferenceGlobalRootComponent.this.provideIDreamManagerProvider;
            Provider<NotificationVisibilityProviderImpl> provider9 = this.notificationVisibilityProviderImplProvider;
            Provider<NotificationInterruptStateProviderImpl> provider10 = this.notificationInterruptStateProviderImplProvider;
            Provider<ZenModeControllerImpl> provider11 = this.zenModeControllerImplProvider;
            Provider<NotificationLockscreenUserManagerImpl> provider12 = this.notificationLockscreenUserManagerImplProvider;
            Provider<NotifPipeline> provider13 = this.notifPipelineProvider;
            this.provideBubblesManagerProvider = DoubleCheck.provider(SystemUIModule_ProvideBubblesManagerFactory.create(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider13, this.provideSysUiStateProvider, this.featureFlagsDebugProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider));
            this.provideDelayableExecutorProvider = DoubleCheck.provider(SysUIConcurrencyModule_ProvideDelayableExecutorFactory.create(this.provideBgLooperProvider));
            this.visibilityLocationProviderDelegatorProvider = DoubleCheck.provider(VisibilityLocationProviderDelegator_Factory.create());
            Provider<VisualStabilityCoordinator> provider14 = DoubleCheck.provider(VisualStabilityCoordinator_Factory.create(this.provideDelayableExecutorProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.provideHeadsUpManagerPhoneProvider, this.shadeExpansionStateManagerProvider, this.statusBarStateControllerImplProvider, this.visibilityLocationProviderDelegatorProvider, this.visualStabilityProvider, this.wakefulnessLifecycleProvider));
            this.visualStabilityCoordinatorProvider = provider14;
            this.onUserInteractionCallbackImplProvider = DoubleCheck.provider(OnUserInteractionCallbackImpl_Factory.create(this.notificationVisibilityProviderImplProvider, this.notifCollectionProvider, this.provideHeadsUpManagerPhoneProvider, this.statusBarStateControllerImplProvider, provider14));
            this.provideNotificationGutsManagerProvider = DoubleCheck.provider(NotificationsModule_ProvideNotificationGutsManagerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.optionalOfCentralSurfacesProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.provideBgHandlerProvider, DaggerReferenceGlobalRootComponent.this.provideAccessibilityManagerProvider, this.highPriorityProvider, DaggerReferenceGlobalRootComponent.this.provideINotificationManagerProvider, this.peopleSpaceWidgetManagerProvider, DaggerReferenceGlobalRootComponent.this.provideLauncherAppsProvider, DaggerReferenceGlobalRootComponent.this.provideShortcutManagerProvider, this.channelEditorDialogControllerProvider, this.provideUserTrackerProvider, this.assistantFeedbackControllerProvider, this.provideBubblesManagerProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, this.onUserInteractionCallbackImplProvider, this.shadeControllerImplProvider));
            DelegateFactory.setDelegate(this.shadeControllerImplProvider, DoubleCheck.provider(ShadeControllerImpl_Factory.create(this.provideCommandQueueProvider, this.keyguardStateControllerImplProvider, this.statusBarStateControllerImplProvider, this.statusBarKeyguardViewManagerProvider, this.statusBarWindowControllerProvider, this.notificationShadeWindowControllerImplProvider, DaggerReferenceGlobalRootComponent.this.provideWindowManagerProvider, this.assistManagerProvider, this.provideNotificationGutsManagerProvider)));
            Provider<LogBuffer> provider15 = DoubleCheck.provider(LogModule_ProvideBiometricLogBufferFactory.create(this.logBufferFactoryProvider));
            this.provideBiometricLogBufferProvider = provider15;
            this.biometricUnlockLoggerProvider = DoubleCheck.provider(BiometricUnlockLogger_Factory.create(provider15));
            this.authControllerProvider = new DelegateFactory();
            this.keyguardUnlockAnimationControllerProvider = new DelegateFactory();
            this.sessionTrackerProvider = DoubleCheck.provider(SessionTracker_Factory.create(DaggerReferenceGlobalRootComponent.this.provideIStatusBarServiceProvider, this.authControllerProvider, this.keyguardUpdateMonitorProvider, this.keyguardStateControllerImplProvider));
            this.vibratorHelperProvider = DoubleCheck.provider(VibratorHelper_Factory.create(DaggerReferenceGlobalRootComponent.this.provideVibratorProvider));
            this.biometricUnlockControllerProvider = DoubleCheck.provider(BiometricUnlockController_Factory.create(this.dozeScrimControllerProvider, this.newKeyguardViewMediatorProvider, this.shadeControllerImplProvider, this.notificationShadeWindowControllerImplProvider, this.keyguardStateControllerImplProvider, GlobalConcurrencyModule_ProvideHandlerFactory.create(), this.keyguardUpdateMonitorProvider, DaggerReferenceGlobalRootComponent.this.provideResourcesProvider, this.keyguardBypassControllerProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, DaggerReferenceGlobalRootComponent.this.providePowerManagerProvider, this.biometricUnlockLoggerProvider, this.provideNotificationMediaManagerProvider, this.wakefulnessLifecycleProvider, DaggerReferenceGlobalRootComponent.this.screenLifecycleProvider, this.authControllerProvider, this.statusBarStateControllerImplProvider, this.keyguardUnlockAnimationControllerProvider, this.sessionTrackerProvider, DaggerReferenceGlobalRootComponent.this.provideLatencyTrackerProvider, this.screenOffAnimationControllerProvider, this.vibratorHelperProvider));
            DelegateFactory.setDelegate(this.keyguardUnlockAnimationControllerProvider, DoubleCheck.provider(KeyguardUnlockAnimationController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.keyguardStateControllerImplProvider, this.newKeyguardViewMediatorProvider, this.statusBarKeyguardViewManagerProvider, this.featureFlagsDebugProvider, this.biometricUnlockControllerProvider, this.statusBarStateControllerImplProvider, this.notificationShadeWindowControllerImplProvider, DaggerReferenceGlobalRootComponent.this.providePowerManagerProvider)));
            Provider<ScrimController> provider16 = DoubleCheck.provider(ScrimController_Factory.create(this.lightBarControllerProvider, this.dozeParametersProvider, DaggerReferenceGlobalRootComponent.this.provideAlarmManagerProvider, this.keyguardStateControllerImplProvider, this.builderProvider4, GlobalConcurrencyModule_ProvideHandlerFactory.create(), this.keyguardUpdateMonitorProvider, this.dockManagerImplProvider, this.configurationControllerImplProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.screenOffAnimationControllerProvider, this.keyguardUnlockAnimationControllerProvider, this.statusBarKeyguardViewManagerProvider));
            this.scrimControllerProvider = provider16;
            this.lockscreenShadeScrimTransitionControllerProvider = LockscreenShadeScrimTransitionController_Factory.create(provider16, DaggerReferenceGlobalRootComponent.this.contextProvider, this.configurationControllerImplProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider);
            LockscreenShadeKeyguardTransitionController_Factory create = LockscreenShadeKeyguardTransitionController_Factory.create(this.mediaHierarchyManagerProvider, DaggerReferenceGlobalRootComponent.this.contextProvider, this.configurationControllerImplProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider);
            this.lockscreenShadeKeyguardTransitionControllerProvider = create;
            this.factoryProvider2 = LockscreenShadeKeyguardTransitionController_Factory_Impl.create(create);
            this.blurUtilsProvider = DoubleCheck.provider(BlurUtils_Factory.create(DaggerReferenceGlobalRootComponent.this.provideResourcesProvider, DaggerReferenceGlobalRootComponent.this.provideCrossWindowBlurListenersProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
            this.wallpaperControllerProvider = DoubleCheck.provider(WallpaperController_Factory.create(DaggerReferenceGlobalRootComponent.this.provideWallpaperManagerProvider));
            this.notificationShadeDepthControllerProvider = DoubleCheck.provider(NotificationShadeDepthController_Factory.create(this.statusBarStateControllerImplProvider, this.blurUtilsProvider, this.biometricUnlockControllerProvider, this.keyguardStateControllerImplProvider, DaggerReferenceGlobalRootComponent.this.providesChoreographerProvider, this.wallpaperControllerProvider, this.notificationShadeWindowControllerImplProvider, this.dozeParametersProvider, DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.configurationControllerImplProvider));
            SplitShadeLockScreenOverScroller_Factory create2 = SplitShadeLockScreenOverScroller_Factory.create(this.configurationControllerImplProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, DaggerReferenceGlobalRootComponent.this.contextProvider, this.scrimControllerProvider, this.statusBarStateControllerImplProvider);
            this.splitShadeLockScreenOverScrollerProvider = create2;
            this.factoryProvider3 = SplitShadeLockScreenOverScroller_Factory_Impl.create(create2);
            SingleShadeLockScreenOverScroller_Factory create3 = SingleShadeLockScreenOverScroller_Factory.create(this.configurationControllerImplProvider, DaggerReferenceGlobalRootComponent.this.contextProvider, this.statusBarStateControllerImplProvider);
            this.singleShadeLockScreenOverScrollerProvider = create3;
            this.factoryProvider4 = SingleShadeLockScreenOverScroller_Factory_Impl.create(create3);
            LockscreenShadeQsTransitionController_Factory create4 = LockscreenShadeQsTransitionController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.configurationControllerImplProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider);
            this.lockscreenShadeQsTransitionControllerProvider = create4;
            this.factoryProvider5 = LockscreenShadeQsTransitionController_Factory_Impl.create(create4);
            this.lockscreenShadeTransitionControllerProvider = DoubleCheck.provider(LockscreenShadeTransitionController_Factory.create(this.statusBarStateControllerImplProvider, this.lSShadeTransitionLoggerProvider, this.keyguardBypassControllerProvider, this.notificationLockscreenUserManagerImplProvider, this.falsingCollectorImplProvider, this.ambientStateProvider, this.mediaHierarchyManagerProvider, this.lockscreenShadeScrimTransitionControllerProvider, this.factoryProvider2, this.notificationShadeDepthControllerProvider, DaggerReferenceGlobalRootComponent.this.contextProvider, this.factoryProvider3, this.factoryProvider4, this.wakefulnessLifecycleProvider, this.configurationControllerImplProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.factoryProvider5));
            this.pulseExpansionHandlerProvider = DoubleCheck.provider(PulseExpansionHandler_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.notificationWakeUpCoordinatorProvider, this.keyguardBypassControllerProvider, this.provideHeadsUpManagerPhoneProvider, this.notificationRoundnessManagerProvider, this.configurationControllerImplProvider, this.statusBarStateControllerImplProvider, this.falsingManagerProxyProvider, this.shadeExpansionStateManagerProvider, this.lockscreenShadeTransitionControllerProvider, this.falsingCollectorImplProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
            this.dynamicPrivacyControllerProvider = DoubleCheck.provider(DynamicPrivacyController_Factory.create(this.notificationLockscreenUserManagerImplProvider, this.keyguardStateControllerImplProvider, this.statusBarStateControllerImplProvider));
            this.applicationScopeProvider = DoubleCheck.provider(CoroutinesModule_ApplicationScopeFactory.create(this.mainDispatcherProvider));
            this.bgDispatcherProvider = DoubleCheck.provider(CoroutinesModule_BgDispatcherFactory.create());
            this.userRepositoryImplProvider = DoubleCheck.provider(UserRepositoryImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.provideApplicationContextProvider, DaggerReferenceGlobalRootComponent.this.provideUserManagerProvider, this.applicationScopeProvider, this.mainDispatcherProvider, this.bgDispatcherProvider, this.globalSettingsImplProvider, this.provideUserTrackerProvider));
            this.notificationIconAreaControllerProvider = DoubleCheck.provider(NotificationIconAreaController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.statusBarStateControllerImplProvider, this.notificationWakeUpCoordinatorProvider, this.keyguardBypassControllerProvider, this.provideNotificationMediaManagerProvider, this.notificationListenerProvider, this.dozeParametersProvider, this.setBubblesProvider, this.provideDemoModeControllerProvider, this.darkIconDispatcherImplProvider, this.statusBarWindowControllerProvider, this.screenOffAnimationControllerProvider));
            this.dozeServiceHostProvider = DoubleCheck.provider(DozeServiceHost_Factory.create(this.dozeLogProvider, DaggerReferenceGlobalRootComponent.this.providePowerManagerProvider, this.wakefulnessLifecycleProvider, this.statusBarStateControllerImplProvider, this.bindDeviceProvisionedControllerProvider, this.provideHeadsUpManagerPhoneProvider, this.provideBatteryControllerProvider, this.scrimControllerProvider, this.biometricUnlockControllerProvider, this.assistManagerProvider, this.dozeScrimControllerProvider, this.keyguardUpdateMonitorProvider, this.pulseExpansionHandlerProvider, this.notificationShadeWindowControllerImplProvider, this.notificationWakeUpCoordinatorProvider, this.authControllerProvider, this.notificationIconAreaControllerProvider));
            this.dozeTransitionListenerProvider = DoubleCheck.provider(DozeTransitionListener_Factory.create());
            Provider<DreamOverlayCallbackController> provider17 = DoubleCheck.provider(DreamOverlayCallbackController_Factory.create());
            this.dreamOverlayCallbackControllerProvider = provider17;
            Provider<KeyguardRepositoryImpl> provider18 = DoubleCheck.provider(KeyguardRepositoryImpl_Factory.create(this.statusBarStateControllerImplProvider, this.dozeServiceHostProvider, this.wakefulnessLifecycleProvider, this.biometricUnlockControllerProvider, this.keyguardStateControllerImplProvider, this.keyguardUpdateMonitorProvider, this.dozeTransitionListenerProvider, this.authControllerProvider, provider17));
            this.keyguardRepositoryImplProvider = provider18;
            this.keyguardInteractorProvider = DoubleCheck.provider(KeyguardInteractor_Factory.create(provider18));
            Provider<TelephonyRepositoryImpl> provider19 = DoubleCheck.provider(TelephonyRepositoryImpl_Factory.create(this.telephonyListenerManagerProvider));
            this.telephonyRepositoryImplProvider = provider19;
            this.telephonyInteractorProvider = DoubleCheck.provider(TelephonyInteractor_Factory.create(provider19));
            this.refreshUsersSchedulerProvider = DoubleCheck.provider(RefreshUsersScheduler_Factory.create(this.applicationScopeProvider, this.mainDispatcherProvider, this.userRepositoryImplProvider));
            this.guestSessionNotificationProvider = GuestSessionNotification_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideNotificationManagerProvider);
            this.userSwitcherControllerProvider = new DelegateFactory();
            C0052GuestResumeSessionReceiver_ResetSessionDialog_Factory create5 = C0052GuestResumeSessionReceiver_ResetSessionDialog_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.userSwitcherControllerProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider);
            this.resetSessionDialogProvider = create5;
            this.factoryProvider6 = GuestResumeSessionReceiver_ResetSessionDialog_Factory_Impl.create(create5);
            this.guestResumeSessionReceiverProvider = GuestResumeSessionReceiver_Factory.create(DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.provideUserTrackerProvider, this.secureSettingsImplProvider, this.guestSessionNotificationProvider, this.factoryProvider6);
            C0051GuestResetOrExitSessionReceiver_ResetSessionDialog_Factory create6 = C0051GuestResetOrExitSessionReceiver_ResetSessionDialog_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.userSwitcherControllerProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider);
            this.resetSessionDialogProvider2 = create6;
            this.factoryProvider7 = GuestResetOrExitSessionReceiver_ResetSessionDialog_Factory_Impl.create(create6);
            C0050GuestResetOrExitSessionReceiver_ExitSessionDialog_Factory create7 = C0050GuestResetOrExitSessionReceiver_ExitSessionDialog_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.userSwitcherControllerProvider);
            this.exitSessionDialogProvider = create7;
            Provider<GuestResetOrExitSessionReceiver.ExitSessionDialog.Factory> create8 = GuestResetOrExitSessionReceiver_ExitSessionDialog_Factory_Impl.create(create7);
            this.factoryProvider8 = create8;
            this.guestResetOrExitSessionReceiverProvider = GuestResetOrExitSessionReceiver_Factory.create(this.provideUserTrackerProvider, this.broadcastDispatcherProvider, this.factoryProvider7, create8);
            this.guestUserInteractorProvider = DoubleCheck.provider(GuestUserInteractor_Factory.create(DaggerReferenceGlobalRootComponent.this.provideApplicationContextProvider, this.applicationScopeProvider, this.mainDispatcherProvider, this.bgDispatcherProvider, DaggerReferenceGlobalRootComponent.this.provideUserManagerProvider, this.userRepositoryImplProvider, this.bindDeviceProvisionedControllerProvider, DaggerReferenceGlobalRootComponent.this.provideDevicePolicyManagerProvider, this.refreshUsersSchedulerProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, this.guestResumeSessionReceiverProvider, this.guestResetOrExitSessionReceiverProvider));
            this.userInteractorProvider = DoubleCheck.provider(UserInteractor_Factory.create(DaggerReferenceGlobalRootComponent.this.provideApplicationContextProvider, this.userRepositoryImplProvider, this.provideActivityStarterProvider, this.keyguardInteractorProvider, this.featureFlagsDebugProvider, DaggerReferenceGlobalRootComponent.this.provideUserManagerProvider, this.applicationScopeProvider, this.telephonyInteractorProvider, this.broadcastDispatcherProvider, this.bgDispatcherProvider, DaggerReferenceGlobalRootComponent.this.provideActivityManagerProvider, this.refreshUsersSchedulerProvider, this.guestUserInteractorProvider));
            DelegateFactory.setDelegate(this.userSwitcherControllerProvider, DoubleCheck.provider(UserSwitcherController_Factory.create(DaggerReferenceGlobalRootComponent.this.provideApplicationContextProvider, this.userInteractorProvider, this.guestUserInteractorProvider, this.keyguardInteractorProvider, this.provideActivityStarterProvider)));
            this.sysuiColorExtractorProvider = DoubleCheck.provider(SysuiColorExtractor_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.configurationControllerImplProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
            this.navigationBarControllerProvider = new DelegateFactory();
            this.accessibilityFloatingMenuControllerProvider = DoubleCheck.provider(AccessibilityFloatingMenuController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.accessibilityButtonTargetsObserverProvider, this.accessibilityButtonModeObserverProvider, this.keyguardUpdateMonitorProvider));
            this.lockscreenWallpaperProvider = DoubleCheck.provider(LockscreenWallpaper_Factory.create(DaggerReferenceGlobalRootComponent.this.provideWallpaperManagerProvider, FrameworkServicesModule_ProvideIWallPaperManagerFactory.create(), this.keyguardUpdateMonitorProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.provideNotificationMediaManagerProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.provideUserTrackerProvider));
            this.screenPinningRequestProvider = ScreenPinningRequest_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.optionalOfCentralSurfacesProvider, this.navigationModeControllerProvider, this.broadcastDispatcherProvider, this.provideUserTrackerProvider);
            this.ringerModeTrackerImplProvider = DoubleCheck.provider(RingerModeTrackerImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.provideAudioManagerProvider, this.broadcastDispatcherProvider, this.provideBackgroundExecutorProvider));
            this.volumeDialogControllerImplProvider = DoubleCheck.provider(VolumeDialogControllerImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.broadcastDispatcherProvider, this.ringerModeTrackerImplProvider, ThreadFactoryImpl_Factory.create(), DaggerReferenceGlobalRootComponent.this.provideAudioManagerProvider, DaggerReferenceGlobalRootComponent.this.provideNotificationManagerProvider, this.vibratorHelperProvider, DaggerReferenceGlobalRootComponent.this.provideIAudioServiceProvider, DaggerReferenceGlobalRootComponent.this.provideAccessibilityManagerProvider, DaggerReferenceGlobalRootComponent.this.providePackageManagerProvider, this.wakefulnessLifecycleProvider, DaggerReferenceGlobalRootComponent.this.provideCaptioningManagerProvider, DaggerReferenceGlobalRootComponent.this.provideKeyguardManagerProvider, DaggerReferenceGlobalRootComponent.this.provideActivityManagerProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
            this.volumePanelFactoryProvider = DoubleCheck.provider(VolumePanelFactory_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideActivityStarterProvider, this.provideDialogLaunchAnimatorProvider));
            this.provideVolumeDialogProvider = VolumeModule_ProvideVolumeDialogFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.volumeDialogControllerImplProvider, this.accessibilityManagerWrapperProvider, this.bindDeviceProvisionedControllerProvider, this.configurationControllerImplProvider, this.mediaOutputDialogFactoryProvider, this.volumePanelFactoryProvider, this.provideActivityStarterProvider, DaggerReferenceGlobalRootComponent.this.provideInteractionJankMonitorProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider);
            this.volumeDialogComponentProvider = DoubleCheck.provider(VolumeDialogComponent_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.newKeyguardViewMediatorProvider, this.provideActivityStarterProvider, this.volumeDialogControllerImplProvider, this.provideDemoModeControllerProvider, DaggerReferenceGlobalRootComponent.this.pluginDependencyProvider, this.extensionControllerImplProvider, this.tunerServiceImplProvider, this.provideVolumeDialogProvider));
            this.centralSurfacesComponentFactoryProvider = new Provider<CentralSurfacesComponent.Factory>() { // from class: com.android.systemui.dagger.DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.6
                /* JADX DEBUG: Method merged with bridge method */
                /* renamed from: get */
                public CentralSurfacesComponent.Factory m2243get() {
                    return new CentralSurfacesComponentFactory();
                }
            };
            this.initControllerProvider = DoubleCheck.provider(InitController_Factory.create());
            this.provideTimeTickHandlerProvider = DoubleCheck.provider(SysUIConcurrencyModule_ProvideTimeTickHandlerFactory.create());
            this.userInfoControllerImplProvider = DoubleCheck.provider(UserInfoControllerImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.provideUserTrackerProvider));
            this.castControllerImplProvider = DoubleCheck.provider(CastControllerImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
            this.hotspotControllerImplProvider = DoubleCheck.provider(HotspotControllerImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideUserTrackerProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.provideBgHandlerProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
            Provider<LogBuffer> provider20 = DoubleCheck.provider(LogModule_ProviderBluetoothLogBufferFactory.create(this.logBufferFactoryProvider));
            this.providerBluetoothLogBufferProvider = provider20;
            this.bluetoothLoggerProvider = DoubleCheck.provider(BluetoothLogger_Factory.create(provider20));
            this.bluetoothControllerImplProvider = DoubleCheck.provider(BluetoothControllerImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideUserTrackerProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.bluetoothLoggerProvider, this.provideBgLooperProvider, GlobalConcurrencyModule_ProvideMainLooperFactory.create(), this.provideLocalBluetoothControllerProvider));
            this.nextAlarmControllerImplProvider = DoubleCheck.provider(NextAlarmControllerImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, DaggerReferenceGlobalRootComponent.this.provideAlarmManagerProvider, this.broadcastDispatcherProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.provideUserTrackerProvider));
            RotationPolicyWrapperImpl_Factory create9 = RotationPolicyWrapperImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.secureSettingsImplProvider);
            this.rotationPolicyWrapperImplProvider = create9;
            this.bindRotationPolicyWrapperProvider = DoubleCheck.provider(create9);
            this.provideAutoRotateSettingsManagerProvider = DoubleCheck.provider(StatusBarPolicyModule_ProvideAutoRotateSettingsManagerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider));
            this.deviceStateRotationLockSettingControllerProvider = DoubleCheck.provider(DeviceStateRotationLockSettingController_Factory.create(this.bindRotationPolicyWrapperProvider, DaggerReferenceGlobalRootComponent.this.provideDeviceStateManagerProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.provideAutoRotateSettingsManagerProvider));
            StatusBarPolicyModule_ProvidesDeviceStateRotationLockDefaultsFactory create10 = StatusBarPolicyModule_ProvidesDeviceStateRotationLockDefaultsFactory.create(DaggerReferenceGlobalRootComponent.this.provideResourcesProvider);
            this.providesDeviceStateRotationLockDefaultsProvider = create10;
            this.rotationLockControllerImplProvider = DoubleCheck.provider(RotationLockControllerImpl_Factory.create(this.bindRotationPolicyWrapperProvider, this.deviceStateRotationLockSettingControllerProvider, create10));
            this.provideDataSaverControllerProvider = DoubleCheck.provider(StatusBarPolicyModule_ProvideDataSaverControllerFactory.create(this.networkControllerImplProvider));
            this.provideSensorPrivacyControllerProvider = DoubleCheck.provider(ReferenceSystemUIModule_ProvideSensorPrivacyControllerFactory.create(DaggerReferenceGlobalRootComponent.this.provideSensorPrivacyManagerProvider));
            Provider provider21 = DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider;
            Provider<BroadcastDispatcher> provider22 = this.broadcastDispatcherProvider;
            Provider<UserTracker> provider23 = this.provideUserTrackerProvider;
            this.recordingControllerProvider = DoubleCheck.provider(RecordingController_Factory.create(provider21, provider22, provider23, provider23));
            this.dateFormatUtilProvider = DateFormatUtil_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideUserTrackerProvider);
            this.privacyConfigProvider = DoubleCheck.provider(PrivacyConfig_Factory.create(DaggerReferenceGlobalRootComponent.this.provideMainDelayableExecutorProvider, this.deviceConfigProxyProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
            Provider<LogBuffer> provider24 = DoubleCheck.provider(LogModule_ProvidePrivacyLogBufferFactory.create(this.logBufferFactoryProvider));
            this.providePrivacyLogBufferProvider = provider24;
            PrivacyLogger_Factory create11 = PrivacyLogger_Factory.create(provider24);
            this.privacyLoggerProvider = create11;
            this.appOpsPrivacyItemMonitorProvider = DoubleCheck.provider(AppOpsPrivacyItemMonitor_Factory.create(this.appOpsControllerImplProvider, this.provideUserTrackerProvider, this.privacyConfigProvider, this.provideBackgroundDelayableExecutorProvider, create11));
            this.setOfPrivacyItemMonitorProvider = SetFactory.builder(1, 0).addProvider(this.appOpsPrivacyItemMonitorProvider).build();
            this.privacyItemControllerProvider = DoubleCheck.provider(PrivacyItemController_Factory.create(DaggerReferenceGlobalRootComponent.this.provideMainDelayableExecutorProvider, this.provideBackgroundDelayableExecutorProvider, this.privacyConfigProvider, this.setOfPrivacyItemMonitorProvider, this.privacyLoggerProvider, this.bindSystemClockProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
            this.phoneStatusBarPolicyProvider = PhoneStatusBarPolicy_Factory.create(this.statusBarIconControllerImplProvider, this.provideCommandQueueProvider, this.broadcastDispatcherProvider, DaggerReferenceGlobalRootComponent.this.provideUiBackgroundExecutorProvider, GlobalConcurrencyModule_ProvideMainLooperFactory.create(), DaggerReferenceGlobalRootComponent.this.provideResourcesProvider, this.castControllerImplProvider, this.hotspotControllerImplProvider, this.bluetoothControllerImplProvider, this.nextAlarmControllerImplProvider, this.userInfoControllerImplProvider, this.rotationLockControllerImplProvider, this.provideDataSaverControllerProvider, this.zenModeControllerImplProvider, this.bindDeviceProvisionedControllerProvider, this.keyguardStateControllerImplProvider, this.locationControllerImplProvider, this.provideSensorPrivacyControllerProvider, DaggerReferenceGlobalRootComponent.this.provideIActivityManagerProvider, DaggerReferenceGlobalRootComponent.this.provideAlarmManagerProvider, DaggerReferenceGlobalRootComponent.this.provideUserManagerProvider, DaggerReferenceGlobalRootComponent.this.provideDevicePolicyManagerProvider, this.recordingControllerProvider, DaggerReferenceGlobalRootComponent.this.provideTelecomManagerProvider, DaggerReferenceGlobalRootComponent.this.provideDisplayIdProvider, DaggerReferenceGlobalRootComponent.this.provideSharePreferencesProvider, this.dateFormatUtilProvider, this.ringerModeTrackerImplProvider, this.privacyItemControllerProvider, this.privacyLoggerProvider);
            this.faceMessageDeferralLoggerProvider = DoubleCheck.provider(FaceMessageDeferralLogger_Factory.create(this.provideBiometricLogBufferProvider));
            this.faceHelpMessageDeferralProvider = DoubleCheck.provider(FaceHelpMessageDeferral_Factory.create(DaggerReferenceGlobalRootComponent.this.provideResourcesProvider, this.faceMessageDeferralLoggerProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
            Provider<LogBuffer> provider25 = DoubleCheck.provider(LogModule_ProvideKeyguardLogBufferFactory.create(this.logBufferFactoryProvider));
            this.provideKeyguardLogBufferProvider = provider25;
            this.keyguardLoggerProvider = KeyguardLogger_Factory.create(provider25);
            this.keyguardIndicationControllerProvider = DoubleCheck.provider(KeyguardIndicationController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, GlobalConcurrencyModule_ProvideMainLooperFactory.create(), this.builderProvider3, this.keyguardStateControllerImplProvider, this.statusBarStateControllerImplProvider, this.keyguardUpdateMonitorProvider, this.dockManagerImplProvider, this.broadcastDispatcherProvider, DaggerReferenceGlobalRootComponent.this.provideDevicePolicyManagerProvider, DaggerReferenceGlobalRootComponent.this.provideIBatteryStatsProvider, DaggerReferenceGlobalRootComponent.this.provideUserManagerProvider, DaggerReferenceGlobalRootComponent.this.provideMainDelayableExecutorProvider, this.provideBackgroundDelayableExecutorProvider, this.falsingManagerProxyProvider, this.authControllerProvider, DaggerReferenceGlobalRootComponent.this.provideLockPatternUtilsProvider, DaggerReferenceGlobalRootComponent.this.screenLifecycleProvider, this.keyguardBypassControllerProvider, DaggerReferenceGlobalRootComponent.this.provideAccessibilityManagerProvider, this.faceHelpMessageDeferralProvider, this.keyguardLoggerProvider));
            this.unlockedScreenOffAnimationControllerProvider = new DelegateFactory();
        }

        public final void initialize5(LeakModule leakModule, NightDisplayListenerModule nightDisplayListenerModule, SharedLibraryModule sharedLibraryModule, KeyguardModule keyguardModule, SysUIUnfoldModule sysUIUnfoldModule, ShellInterface shellInterface, Optional<Pip> optional, Optional<SplitScreen> optional2, Optional<OneHanded> optional3, Optional<Bubbles> optional4, Optional<TaskViewFactory> optional5, ShellTransitions shellTransitions, Optional<StartingSurface> optional6, Optional<DisplayAreaHelper> optional7, Optional<RecentTasks> optional8, Optional<BackAnimation> optional9, Optional<DesktopMode> optional10) {
            this.statusBarTouchableRegionManagerProvider = DoubleCheck.provider(StatusBarTouchableRegionManager_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.notificationShadeWindowControllerImplProvider, this.configurationControllerImplProvider, this.provideHeadsUpManagerPhoneProvider, this.shadeExpansionStateManagerProvider, this.unlockedScreenOffAnimationControllerProvider));
            this.factoryProvider9 = new DelegateFactory();
            this.ongoingCallLoggerProvider = DoubleCheck.provider(OngoingCallLogger_Factory.create(DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider));
            Provider<LogBuffer> provider = DoubleCheck.provider(LogModule_ProvideSwipeAwayGestureLogBufferFactory.create(this.logBufferFactoryProvider));
            this.provideSwipeAwayGestureLogBufferProvider = provider;
            this.swipeStatusBarAwayGestureLoggerProvider = SwipeStatusBarAwayGestureLogger_Factory.create(provider);
            this.swipeStatusBarAwayGestureHandlerProvider = DoubleCheck.provider(SwipeStatusBarAwayGestureHandler_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.statusBarWindowControllerProvider, this.swipeStatusBarAwayGestureLoggerProvider));
            this.ongoingCallFlagsProvider = DoubleCheck.provider(OngoingCallFlags_Factory.create(this.featureFlagsDebugProvider));
            this.provideOngoingCallControllerProvider = DoubleCheck.provider(CentralSurfacesDependenciesModule_ProvideOngoingCallControllerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.notifPipelineProvider, this.bindSystemClockProvider, this.provideActivityStarterProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, DaggerReferenceGlobalRootComponent.this.provideIActivityManagerProvider, this.ongoingCallLoggerProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.statusBarWindowControllerProvider, this.swipeStatusBarAwayGestureHandlerProvider, this.statusBarStateControllerImplProvider, this.ongoingCallFlagsProvider));
            this.statusBarHideIconsForBouncerManagerProvider = DoubleCheck.provider(StatusBarHideIconsForBouncerManager_Factory.create(this.provideCommandQueueProvider, DaggerReferenceGlobalRootComponent.this.provideMainDelayableExecutorProvider, this.statusBarWindowStateControllerProvider, this.shadeExpansionStateManagerProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
            this.providesMainMessageRouterProvider = SysUIConcurrencyModule_ProvidesMainMessageRouterFactory.create(DaggerReferenceGlobalRootComponent.this.provideMainDelayableExecutorProvider);
            this.setStartingSurfaceProvider = InstanceFactory.create(optional6);
            this.provideActivityLaunchAnimatorProvider = DoubleCheck.provider(CentralSurfacesDependenciesModule_ProvideActivityLaunchAnimatorFactory.create());
            this.wiredChargingRippleControllerProvider = DoubleCheck.provider(WiredChargingRippleController_Factory.create(this.commandRegistryProvider, this.provideBatteryControllerProvider, this.configurationControllerImplProvider, this.featureFlagsDebugProvider, DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideWindowManagerProvider, this.bindSystemClockProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider));
            this.centralSurfacesImplProvider = new DelegateFactory();
            this.cameraIntentsWrapperProvider = CameraIntentsWrapper_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider);
            CameraGestureHelper_Factory create = CameraGestureHelper_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.centralSurfacesImplProvider, this.keyguardStateControllerImplProvider, DaggerReferenceGlobalRootComponent.this.providePackageManagerProvider, DaggerReferenceGlobalRootComponent.this.provideActivityManagerProvider, this.provideActivityStarterProvider, this.activityIntentHelperProvider, DaggerReferenceGlobalRootComponent.this.provideIActivityTaskManagerProvider, this.cameraIntentsWrapperProvider, DaggerReferenceGlobalRootComponent.this.provideContentResolverProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider);
            this.cameraGestureHelperProvider = create;
            this.cameraLauncherProvider = DoubleCheck.provider(CameraLauncher_Factory.create(create, this.keyguardBypassControllerProvider));
            Provider<KeyguardTransitionRepositoryImpl> provider2 = DoubleCheck.provider(KeyguardTransitionRepositoryImpl_Factory.create());
            this.keyguardTransitionRepositoryImplProvider = provider2;
            this.keyguardTransitionInteractorProvider = DoubleCheck.provider(KeyguardTransitionInteractor_Factory.create(provider2));
            Provider<LightRevealScrimRepositoryImpl> provider3 = DoubleCheck.provider(LightRevealScrimRepositoryImpl_Factory.create(this.keyguardRepositoryImplProvider, DaggerReferenceGlobalRootComponent.this.contextProvider));
            this.lightRevealScrimRepositoryImplProvider = provider3;
            Provider<LightRevealScrimInteractor> provider4 = DoubleCheck.provider(LightRevealScrimInteractor_Factory.create(this.keyguardTransitionRepositoryImplProvider, this.keyguardTransitionInteractorProvider, provider3));
            this.lightRevealScrimInteractorProvider = provider4;
            this.lightRevealScrimViewModelProvider = LightRevealScrimViewModel_Factory.create(provider4);
            DelegateFactory.setDelegate(this.centralSurfacesImplProvider, DoubleCheck.provider(CentralSurfacesImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideNotificationsControllerProvider, this.fragmentServiceProvider, this.lightBarControllerProvider, this.autoHideControllerProvider, this.statusBarWindowControllerProvider, this.statusBarWindowStateControllerProvider, this.keyguardUpdateMonitorProvider, this.statusBarSignalPolicyProvider, this.pulseExpansionHandlerProvider, this.notificationWakeUpCoordinatorProvider, this.keyguardBypassControllerProvider, this.keyguardStateControllerImplProvider, this.provideHeadsUpManagerPhoneProvider, this.dynamicPrivacyControllerProvider, this.falsingManagerProxyProvider, this.falsingCollectorImplProvider, this.broadcastDispatcherProvider, this.provideNotificationGutsManagerProvider, this.provideNotificationLoggerProvider, this.notificationInterruptStateProviderImplProvider, this.shadeExpansionStateManagerProvider, this.newKeyguardViewMediatorProvider, DaggerReferenceGlobalRootComponent.this.provideDisplayMetricsProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, DaggerReferenceGlobalRootComponent.this.provideUiBackgroundExecutorProvider, this.provideNotificationMediaManagerProvider, this.notificationLockscreenUserManagerImplProvider, this.provideNotificationRemoteInputManagerProvider, this.userSwitcherControllerProvider, this.provideBatteryControllerProvider, this.sysuiColorExtractorProvider, DaggerReferenceGlobalRootComponent.this.screenLifecycleProvider, this.wakefulnessLifecycleProvider, this.statusBarStateControllerImplProvider, this.setBubblesProvider, this.bindDeviceProvisionedControllerProvider, this.navigationBarControllerProvider, this.accessibilityFloatingMenuControllerProvider, this.assistManagerProvider, this.configurationControllerImplProvider, this.notificationShadeWindowControllerImplProvider, this.dozeParametersProvider, this.scrimControllerProvider, this.lockscreenWallpaperProvider, this.biometricUnlockControllerProvider, this.dozeServiceHostProvider, DaggerReferenceGlobalRootComponent.this.providePowerManagerProvider, this.screenPinningRequestProvider, this.dozeScrimControllerProvider, this.volumeDialogComponentProvider, this.provideCommandQueueProvider, this.centralSurfacesComponentFactoryProvider, DaggerReferenceGlobalRootComponent.this.providesPluginManagerProvider, this.shadeControllerImplProvider, this.statusBarKeyguardViewManagerProvider, this.providesViewMediatorCallbackProvider, this.initControllerProvider, this.provideTimeTickHandlerProvider, DaggerReferenceGlobalRootComponent.this.pluginDependencyProvider, this.keyguardDismissUtilProvider, this.extensionControllerImplProvider, this.userInfoControllerImplProvider, this.phoneStatusBarPolicyProvider, this.keyguardIndicationControllerProvider, this.provideDemoModeControllerProvider, this.notificationShadeDepthControllerProvider, this.statusBarTouchableRegionManagerProvider, this.notificationIconAreaControllerProvider, this.factoryProvider9, this.screenOffAnimationControllerProvider, this.wallpaperControllerProvider, this.provideOngoingCallControllerProvider, this.statusBarHideIconsForBouncerManagerProvider, this.lockscreenShadeTransitionControllerProvider, this.featureFlagsDebugProvider, this.keyguardUnlockAnimationControllerProvider, DaggerReferenceGlobalRootComponent.this.provideMainDelayableExecutorProvider, this.providesMainMessageRouterProvider, DaggerReferenceGlobalRootComponent.this.provideWallpaperManagerProvider, this.setStartingSurfaceProvider, this.provideActivityLaunchAnimatorProvider, DaggerReferenceGlobalRootComponent.this.provideInteractionJankMonitorProvider, DaggerReferenceGlobalRootComponent.this.provideDeviceStateManagerProvider, this.wiredChargingRippleControllerProvider, DaggerReferenceGlobalRootComponent.this.provideIDreamManagerProvider, this.cameraLauncherProvider, this.lightRevealScrimViewModelProvider, this.tunerServiceImplProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider)));
            DelegateFactory.setDelegate(this.optionalOfCentralSurfacesProvider, PresentJdkOptionalInstanceProvider.of(this.centralSurfacesImplProvider));
            this.mediaArtworkProcessorProvider = DoubleCheck.provider(MediaArtworkProcessor_Factory.create());
            DelegateFactory.setDelegate(this.provideNotificationMediaManagerProvider, DoubleCheck.provider(CentralSurfacesDependenciesModule_ProvideNotificationMediaManagerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.optionalOfCentralSurfacesProvider, this.notificationShadeWindowControllerImplProvider, this.notificationVisibilityProviderImplProvider, this.mediaArtworkProcessorProvider, this.keyguardBypassControllerProvider, this.notifPipelineProvider, this.notifCollectionProvider, DaggerReferenceGlobalRootComponent.this.provideMainDelayableExecutorProvider, this.mediaDataManagerProvider, this.statusBarStateControllerImplProvider, this.sysuiColorExtractorProvider, this.keyguardStateControllerImplProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.tunerServiceImplProvider)));
            this.dismissCallbackRegistryProvider = DoubleCheck.provider(DismissCallbackRegistry_Factory.create(DaggerReferenceGlobalRootComponent.this.provideUiBackgroundExecutorProvider));
            this.keyguardSecurityModelProvider = DoubleCheck.provider(KeyguardSecurityModel_Factory.create(DaggerReferenceGlobalRootComponent.this.provideResourcesProvider, DaggerReferenceGlobalRootComponent.this.provideLockPatternUtilsProvider, this.keyguardUpdateMonitorProvider));
            this.keyguardBouncerComponentFactoryProvider = new Provider<KeyguardBouncerComponent.Factory>() { // from class: com.android.systemui.dagger.DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.7
                /* JADX DEBUG: Method merged with bridge method */
                /* renamed from: get */
                public KeyguardBouncerComponent.Factory m2244get() {
                    return new KeyguardBouncerComponentFactory();
                }
            };
            this.factoryProvider10 = KeyguardBouncer_Factory_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.providesViewMediatorCallbackProvider, this.dismissCallbackRegistryProvider, this.falsingCollectorImplProvider, this.keyguardStateControllerImplProvider, this.keyguardUpdateMonitorProvider, this.keyguardBypassControllerProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.keyguardSecurityModelProvider, this.keyguardBouncerComponentFactoryProvider);
            this.factoryProvider11 = KeyguardMessageAreaController_Factory_Factory.create(this.keyguardUpdateMonitorProvider, this.configurationControllerImplProvider);
            this.primaryBouncerCallbackInteractorProvider = DoubleCheck.provider(PrimaryBouncerCallbackInteractor_Factory.create());
            Provider<TableLogBufferFactory> provider5 = DoubleCheck.provider(TableLogBufferFactory_Factory.create(DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.bindSystemClockProvider));
            this.tableLogBufferFactoryProvider = provider5;
            Provider<TableLogBuffer> provider6 = DoubleCheck.provider(LogModule_ProvideBouncerLogBufferFactory.create(provider5));
            this.provideBouncerLogBufferProvider = provider6;
            this.keyguardBouncerRepositoryProvider = DoubleCheck.provider(KeyguardBouncerRepository_Factory.create(this.providesViewMediatorCallbackProvider, this.applicationScopeProvider, provider6));
            Provider<BouncerViewImpl> provider7 = DoubleCheck.provider(BouncerViewImpl_Factory.create());
            this.bouncerViewImplProvider = provider7;
            this.primaryBouncerInteractorProvider = DoubleCheck.provider(PrimaryBouncerInteractor_Factory.create(this.keyguardBouncerRepositoryProvider, provider7, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.keyguardStateControllerImplProvider, this.keyguardSecurityModelProvider, this.primaryBouncerCallbackInteractorProvider, this.falsingCollectorImplProvider, this.dismissCallbackRegistryProvider, this.keyguardBypassControllerProvider, this.keyguardUpdateMonitorProvider));
            DelegateFactory.setDelegate(this.statusBarKeyguardViewManagerProvider, DoubleCheck.provider(StatusBarKeyguardViewManager_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.providesViewMediatorCallbackProvider, DaggerReferenceGlobalRootComponent.this.provideLockPatternUtilsProvider, this.statusBarStateControllerImplProvider, this.configurationControllerImplProvider, this.keyguardUpdateMonitorProvider, this.dreamOverlayStateControllerProvider, this.navigationModeControllerProvider, this.dockManagerImplProvider, this.notificationShadeWindowControllerImplProvider, this.keyguardStateControllerImplProvider, this.provideNotificationMediaManagerProvider, this.factoryProvider10, this.factoryProvider11, this.provideSysUIUnfoldComponentProvider, this.shadeControllerImplProvider, DaggerReferenceGlobalRootComponent.this.provideLatencyTrackerProvider, this.keyguardSecurityModelProvider, this.featureFlagsDebugProvider, this.primaryBouncerCallbackInteractorProvider, this.primaryBouncerInteractorProvider, this.bouncerViewImplProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider)));
            this.keyguardStatusViewComponentFactoryProvider = new Provider<KeyguardStatusViewComponent.Factory>() { // from class: com.android.systemui.dagger.DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.8
                /* JADX DEBUG: Method merged with bridge method */
                /* renamed from: get */
                public KeyguardStatusViewComponent.Factory m2245get() {
                    return new KeyguardStatusViewComponentFactory();
                }
            };
            this.keyguardDisplayManagerProvider = KeyguardDisplayManager_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.navigationBarControllerProvider, this.keyguardStatusViewComponentFactoryProvider, DaggerReferenceGlobalRootComponent.this.provideUiBackgroundExecutorProvider);
            this.screenOnCoordinatorProvider = DoubleCheck.provider(ScreenOnCoordinator_Factory.create(this.provideSysUIUnfoldComponentProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider));
            DelegateFactory.setDelegate(this.newKeyguardViewMediatorProvider, DoubleCheck.provider(KeyguardModule_NewKeyguardViewMediatorFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideUserTrackerProvider, this.falsingCollectorImplProvider, DaggerReferenceGlobalRootComponent.this.provideLockPatternUtilsProvider, this.broadcastDispatcherProvider, this.statusBarKeyguardViewManagerProvider, this.dismissCallbackRegistryProvider, this.keyguardUpdateMonitorProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, DaggerReferenceGlobalRootComponent.this.providePowerManagerProvider, DaggerReferenceGlobalRootComponent.this.provideTrustManagerProvider, this.userSwitcherControllerProvider, DaggerReferenceGlobalRootComponent.this.provideUiBackgroundExecutorProvider, this.deviceConfigProxyProvider, this.navigationModeControllerProvider, this.keyguardDisplayManagerProvider, this.dozeParametersProvider, this.statusBarStateControllerImplProvider, this.keyguardStateControllerImplProvider, this.keyguardUnlockAnimationControllerProvider, this.screenOffAnimationControllerProvider, this.notificationShadeDepthControllerProvider, this.screenOnCoordinatorProvider, DaggerReferenceGlobalRootComponent.this.provideInteractionJankMonitorProvider, this.dreamOverlayStateControllerProvider, this.shadeControllerImplProvider, this.notificationShadeWindowControllerImplProvider, this.provideActivityLaunchAnimatorProvider, this.scrimControllerProvider)));
            DelegateFactory.setDelegate(this.unlockedScreenOffAnimationControllerProvider, DoubleCheck.provider(UnlockedScreenOffAnimationController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.wakefulnessLifecycleProvider, this.statusBarStateControllerImplProvider, this.newKeyguardViewMediatorProvider, this.keyguardStateControllerImplProvider, this.dozeParametersProvider, this.globalSettingsImplProvider, DaggerReferenceGlobalRootComponent.this.provideInteractionJankMonitorProvider, DaggerReferenceGlobalRootComponent.this.providePowerManagerProvider, GlobalConcurrencyModule_ProvideHandlerFactory.create())));
            DelegateFactory.setDelegate(this.screenOffAnimationControllerProvider, DoubleCheck.provider(ScreenOffAnimationController_Factory.create(this.provideSysUIUnfoldComponentProvider, this.unlockedScreenOffAnimationControllerProvider, this.wakefulnessLifecycleProvider)));
            DelegateFactory.setDelegate(this.dozeParametersProvider, DoubleCheck.provider(DozeParameters_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideBgHandlerProvider, DaggerReferenceGlobalRootComponent.this.provideResourcesProvider, DaggerReferenceGlobalRootComponent.this.provideAmbientDisplayConfigurationProvider, this.alwaysOnDisplayPolicyProvider, DaggerReferenceGlobalRootComponent.this.providePowerManagerProvider, this.provideBatteryControllerProvider, this.tunerServiceImplProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.featureFlagsDebugProvider, this.screenOffAnimationControllerProvider, this.provideSysUIUnfoldComponentProvider, this.unlockedScreenOffAnimationControllerProvider, this.keyguardUpdateMonitorProvider, this.configurationControllerImplProvider, this.statusBarStateControllerImplProvider)));
            Provider<LogBuffer> provider8 = DoubleCheck.provider(LogModule_ProvideShadeWindowLogBufferFactory.create(this.logBufferFactoryProvider));
            this.provideShadeWindowLogBufferProvider = provider8;
            this.shadeWindowLoggerProvider = ShadeWindowLogger_Factory.create(provider8);
            DelegateFactory.setDelegate(this.notificationShadeWindowControllerImplProvider, DoubleCheck.provider(NotificationShadeWindowControllerImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideWindowManagerProvider, DaggerReferenceGlobalRootComponent.this.provideIActivityManagerProvider, this.dozeParametersProvider, this.statusBarStateControllerImplProvider, this.configurationControllerImplProvider, this.newKeyguardViewMediatorProvider, this.keyguardBypassControllerProvider, this.sysuiColorExtractorProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.keyguardStateControllerImplProvider, this.screenOffAnimationControllerProvider, this.authControllerProvider, this.shadeExpansionStateManagerProvider, this.shadeWindowLoggerProvider)));
            this.contextComponentResolverProvider = new DelegateFactory();
            this.provideRecentsImplProvider = RecentsModule_ProvideRecentsImplFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.contextComponentResolverProvider);
            Provider<Recents> provider9 = DoubleCheck.provider(ReferenceSystemUIModule_ProvideRecentsFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideRecentsImplProvider, this.provideCommandQueueProvider));
            this.provideRecentsProvider = provider9;
            this.optionalOfRecentsProvider = PresentJdkOptionalInstanceProvider.of(provider9);
            this.systemActionsProvider = DoubleCheck.provider(SystemActions_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.notificationShadeWindowControllerImplProvider, this.shadeControllerImplProvider, this.optionalOfCentralSurfacesProvider, this.optionalOfRecentsProvider));
            this.navBarHelperProvider = DoubleCheck.provider(NavBarHelper_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideAccessibilityManagerProvider, this.accessibilityButtonModeObserverProvider, this.accessibilityButtonTargetsObserverProvider, this.systemActionsProvider, this.overviewProxyServiceProvider, this.assistManagerProvider, this.optionalOfCentralSurfacesProvider, this.keyguardStateControllerImplProvider, this.navigationModeControllerProvider, this.provideUserTrackerProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.provideCommandQueueProvider));
            this.factoryProvider12 = BackPanelController_Factory_Factory.create(DaggerReferenceGlobalRootComponent.this.provideWindowManagerProvider, DaggerReferenceGlobalRootComponent.this.provideViewConfigurationProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.vibratorHelperProvider, this.configurationControllerImplProvider, DaggerReferenceGlobalRootComponent.this.provideLatencyTrackerProvider);
            this.setPipProvider = InstanceFactory.create(optional);
            this.navigationBarEdgePanelProvider = NavigationBarEdgePanel_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideLatencyTrackerProvider, this.vibratorHelperProvider, this.provideBackgroundExecutorProvider);
            this.factoryProvider13 = EdgeBackGestureHandler_Factory_Factory.create(this.overviewProxyServiceProvider, this.provideSysUiStateProvider, DaggerReferenceGlobalRootComponent.this.providesPluginManagerProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.provideBackgroundExecutorProvider, this.provideUserTrackerProvider, this.protoTracerProvider, this.navigationModeControllerProvider, this.factoryProvider12, DaggerReferenceGlobalRootComponent.this.provideViewConfigurationProvider, DaggerReferenceGlobalRootComponent.this.provideWindowManagerProvider, DaggerReferenceGlobalRootComponent.this.provideIWindowManagerProvider, this.setPipProvider, this.falsingManagerProxyProvider, this.navigationBarEdgePanelProvider, GestureModule_ProvidsBackGestureTfClassifierProviderFactory.create(), this.featureFlagsDebugProvider);
            this.taskbarDelegateProvider = DoubleCheck.provider(TaskbarDelegate_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.factoryProvider13, this.factoryProvider));
            this.navigationBarComponentFactoryProvider = new Provider<NavigationBarComponent.Factory>() { // from class: com.android.systemui.dagger.DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.9
                /* JADX DEBUG: Method merged with bridge method */
                /* renamed from: get */
                public NavigationBarComponent.Factory m2246get() {
                    return new NavigationBarComponentFactory();
                }
            };
            this.setBackAnimationProvider = InstanceFactory.create(optional9);
            DelegateFactory.setDelegate(this.navigationBarControllerProvider, DoubleCheck.provider(NavigationBarController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.overviewProxyServiceProvider, this.navigationModeControllerProvider, this.provideSysUiStateProvider, this.provideCommandQueueProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.configurationControllerImplProvider, this.navBarHelperProvider, this.taskbarDelegateProvider, this.navigationBarComponentFactoryProvider, this.statusBarKeyguardViewManagerProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.autoHideControllerProvider, this.lightBarControllerProvider, this.setPipProvider, this.setBackAnimationProvider, this.featureFlagsDebugProvider)));
            DelegateFactory.setDelegate(this.overviewProxyServiceProvider, DoubleCheck.provider(OverviewProxyService_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.provideCommandQueueProvider, this.setShellProvider, this.navigationBarControllerProvider, this.optionalOfCentralSurfacesProvider, this.navigationModeControllerProvider, this.notificationShadeWindowControllerImplProvider, this.provideSysUiStateProvider, this.provideUserTrackerProvider, DaggerReferenceGlobalRootComponent.this.screenLifecycleProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, this.keyguardUnlockAnimationControllerProvider, this.provideAssistUtilsProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider)));
            DelegateFactory.setDelegate(this.notificationLockscreenUserManagerImplProvider, DoubleCheck.provider(NotificationLockscreenUserManagerImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.broadcastDispatcherProvider, DaggerReferenceGlobalRootComponent.this.provideDevicePolicyManagerProvider, DaggerReferenceGlobalRootComponent.this.provideUserManagerProvider, this.provideUserTrackerProvider, this.notificationVisibilityProviderImplProvider, this.notifPipelineProvider, this.notificationClickNotifierProvider, this.overviewProxyServiceProvider, DaggerReferenceGlobalRootComponent.this.provideKeyguardManagerProvider, this.statusBarStateControllerImplProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.bindDeviceProvisionedControllerProvider, this.keyguardStateControllerImplProvider, this.secureSettingsImplProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider)));
            DelegateFactory.setDelegate(this.keyguardNotificationVisibilityProviderImplProvider, DoubleCheck.provider(KeyguardNotificationVisibilityProviderImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.keyguardStateControllerImplProvider, this.notificationLockscreenUserManagerImplProvider, this.keyguardUpdateMonitorProvider, this.highPriorityProvider, this.statusBarStateControllerImplProvider, this.provideUserTrackerProvider, this.secureSettingsImplProvider, this.globalSettingsImplProvider)));
            Provider<ShadeRepositoryImpl> provider10 = DoubleCheck.provider(ShadeRepositoryImpl_Factory.create(this.shadeExpansionStateManagerProvider));
            this.shadeRepositoryImplProvider = provider10;
            this.fromBouncerTransitionInteractorProvider = DoubleCheck.provider(FromBouncerTransitionInteractor_Factory.create(this.applicationScopeProvider, this.keyguardInteractorProvider, provider10, this.keyguardTransitionRepositoryImplProvider, this.keyguardTransitionInteractorProvider));
            this.fromLockscreenTransitionInteractorProvider = DoubleCheck.provider(FromLockscreenTransitionInteractor_Factory.create(this.applicationScopeProvider, this.keyguardInteractorProvider, this.shadeRepositoryImplProvider, this.keyguardTransitionInteractorProvider, this.keyguardTransitionRepositoryImplProvider));
            this.fromAodTransitionInteractorProvider = DoubleCheck.provider(FromAodTransitionInteractor_Factory.create(this.applicationScopeProvider, this.keyguardInteractorProvider, this.keyguardTransitionRepositoryImplProvider, this.keyguardTransitionInteractorProvider));
            this.fromGoneTransitionInteractorProvider = DoubleCheck.provider(FromGoneTransitionInteractor_Factory.create(this.applicationScopeProvider, this.keyguardInteractorProvider, this.keyguardTransitionRepositoryImplProvider, this.keyguardTransitionInteractorProvider));
            this.fromDreamingTransitionInteractorProvider = DoubleCheck.provider(FromDreamingTransitionInteractor_Factory.create(this.applicationScopeProvider, this.keyguardInteractorProvider, this.keyguardTransitionRepositoryImplProvider, this.keyguardTransitionInteractorProvider));
            this.fromOccludedTransitionInteractorProvider = DoubleCheck.provider(FromOccludedTransitionInteractor_Factory.create(this.applicationScopeProvider, this.keyguardInteractorProvider, this.keyguardTransitionRepositoryImplProvider, this.keyguardTransitionInteractorProvider));
            this.fromDozingTransitionInteractorProvider = DoubleCheck.provider(FromDozingTransitionInteractor_Factory.create(this.applicationScopeProvider, this.keyguardInteractorProvider, this.keyguardTransitionRepositoryImplProvider, this.keyguardTransitionInteractorProvider));
            this.setOfTransitionInteractorProvider = SetFactory.builder(7, 0).addProvider(this.fromBouncerTransitionInteractorProvider).addProvider(this.fromLockscreenTransitionInteractorProvider).addProvider(this.fromAodTransitionInteractorProvider).addProvider(this.fromGoneTransitionInteractorProvider).addProvider(this.fromDreamingTransitionInteractorProvider).addProvider(this.fromOccludedTransitionInteractorProvider).addProvider(this.fromDozingTransitionInteractorProvider).build();
            Provider<KeyguardTransitionAuditLogger> provider11 = DoubleCheck.provider(KeyguardTransitionAuditLogger_Factory.create(this.applicationScopeProvider, this.keyguardTransitionInteractorProvider, this.keyguardInteractorProvider, this.keyguardLoggerProvider));
            this.keyguardTransitionAuditLoggerProvider = provider11;
            this.keyguardTransitionCoreStartableProvider = DoubleCheck.provider(KeyguardTransitionCoreStartable_Factory.create(this.setOfTransitionInteractorProvider, provider11));
            this.flagCommandProvider = FlagCommand_Factory.create(this.featureFlagsDebugProvider, FlagsCommonModule_ProvidesAllFlagsFactory.create());
            this.featureFlagsDebugStartableProvider = FeatureFlagsDebugStartable_Factory.create(DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.commandRegistryProvider, this.flagCommandProvider, this.featureFlagsDebugProvider, this.broadcastSenderProvider);
            MotionToolModule_Companion_ProvideMotionToolManagerFactory create2 = MotionToolModule_Companion_ProvideMotionToolManagerFactory.create(MotionToolModule_Companion_ProvideViewCaptureFactory.create(), MotionToolModule_Companion_ProvideWindowManagerGlobalFactory.create());
            this.provideMotionToolManagerProvider = create2;
            MotionToolModule_Companion_ProvideDdmHandleMotionToolFactory create3 = MotionToolModule_Companion_ProvideDdmHandleMotionToolFactory.create(create2);
            this.provideDdmHandleMotionToolProvider = create3;
            this.motionToolStartableProvider = DoubleCheck.provider(MotionToolStartable_Factory.create(create3));
            this.userFileManagerImplProvider = DoubleCheck.provider(UserFileManagerImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideUserManagerProvider, this.broadcastDispatcherProvider, this.provideBackgroundDelayableExecutorProvider));
            Provider<LogBuffer> provider12 = DoubleCheck.provider(LogModule_ProvideStatusBarConnectivityBufferFactory.create(this.logBufferFactoryProvider));
            this.provideStatusBarConnectivityBufferProvider = provider12;
            this.connectivityPipelineLoggerProvider = DoubleCheck.provider(ConnectivityPipelineLogger_Factory.create(provider12));
            this.factoryProvider14 = MobileConnectionRepositoryImpl_Factory_Factory.create(this.broadcastDispatcherProvider, DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideTelephonyManagerProvider, this.connectivityPipelineLoggerProvider, this.globalSettingsImplProvider, MobileMappingsProxyImpl_Factory.create(), this.tableLogBufferFactoryProvider, this.bgDispatcherProvider, this.applicationScopeProvider);
            this.mobileConnectionsRepositoryImplProvider = DoubleCheck.provider(MobileConnectionsRepositoryImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.provideConnectivityManagagerProvider, DaggerReferenceGlobalRootComponent.this.provideSubscriptionManagerProvider, DaggerReferenceGlobalRootComponent.this.provideTelephonyManagerProvider, this.connectivityPipelineLoggerProvider, MobileMappingsProxyImpl_Factory.create(), this.broadcastDispatcherProvider, this.globalSettingsImplProvider, DaggerReferenceGlobalRootComponent.this.contextProvider, this.bgDispatcherProvider, this.applicationScopeProvider, this.factoryProvider14));
            Provider<DemoModeMobileConnectionDataSource> provider13 = DoubleCheck.provider(DemoModeMobileConnectionDataSource_Factory.create(this.provideDemoModeControllerProvider, this.applicationScopeProvider));
            this.demoModeMobileConnectionDataSourceProvider = provider13;
            DemoMobileConnectionsRepository_Factory create4 = DemoMobileConnectionsRepository_Factory.create(provider13, this.applicationScopeProvider, DaggerReferenceGlobalRootComponent.this.contextProvider, this.tableLogBufferFactoryProvider);
            this.demoMobileConnectionsRepositoryProvider = create4;
            this.mobileRepositorySwitcherProvider = MobileRepositorySwitcher_Factory.create(this.applicationScopeProvider, this.mobileConnectionsRepositoryImplProvider, create4, this.provideDemoModeControllerProvider);
            Provider<UserSetupRepositoryImpl> provider14 = DoubleCheck.provider(UserSetupRepositoryImpl_Factory.create(this.bindDeviceProvisionedControllerProvider, this.bgDispatcherProvider, this.applicationScopeProvider));
            this.userSetupRepositoryImplProvider = provider14;
            this.mobileIconsInteractorImplProvider = DoubleCheck.provider(MobileIconsInteractorImpl_Factory.create(this.mobileRepositorySwitcherProvider, this.carrierConfigTrackerProvider, provider14, this.applicationScopeProvider));
            Provider<ConnectivityConstants> provider15 = DoubleCheck.provider(ConnectivityConstants_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, DaggerReferenceGlobalRootComponent.this.provideTelephonyManagerProvider));
            this.connectivityConstantsProvider = provider15;
            MobileIconsViewModel_Factory_Factory create5 = MobileIconsViewModel_Factory_Factory.create(this.mobileIconsInteractorImplProvider, this.connectivityPipelineLoggerProvider, provider15, this.applicationScopeProvider, this.statusBarPipelineFlagsProvider);
            this.factoryProvider15 = create5;
            this.mobileUiAdapterProvider = DoubleCheck.provider(MobileUiAdapter_Factory.create(this.mobileIconsInteractorImplProvider, this.statusBarIconControllerImplProvider, create5, this.applicationScopeProvider, this.statusBarPipelineFlagsProvider));
            this.adapterProvider = UserDetailView_Adapter_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.userSwitcherControllerProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, this.falsingManagerProxyProvider);
            this.userSwitcherDialogCoordinatorProvider = DoubleCheck.provider(UserSwitcherDialogCoordinator_Factory.create(DaggerReferenceGlobalRootComponent.this.provideApplicationContextProvider, this.applicationScopeProvider, this.falsingManagerProxyProvider, this.broadcastSenderProvider, this.provideDialogLaunchAnimatorProvider, this.userInteractorProvider, this.adapterProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, this.provideActivityStarterProvider));
            this.chooserSelectorProvider = DoubleCheck.provider(ChooserSelector_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideUserTrackerProvider, this.featureFlagsDebugProvider, this.applicationScopeProvider, this.bgDispatcherProvider));
            ClipboardOverlayModule_ProvideWindowContextFactory create6 = ClipboardOverlayModule_ProvideWindowContextFactory.create(DaggerReferenceGlobalRootComponent.this.provideDisplayManagerProvider, DaggerReferenceGlobalRootComponent.this.contextProvider);
            this.provideWindowContextProvider = create6;
            this.provideClipboardOverlayViewProvider = ClipboardOverlayModule_ProvideClipboardOverlayViewFactory.create(create6);
            this.clipboardOverlayWindowProvider = ClipboardOverlayWindow_Factory.create(this.provideWindowContextProvider);
        }

        public final void initialize6(LeakModule leakModule, NightDisplayListenerModule nightDisplayListenerModule, SharedLibraryModule sharedLibraryModule, KeyguardModule keyguardModule, SysUIUnfoldModule sysUIUnfoldModule, ShellInterface shellInterface, Optional<Pip> optional, Optional<SplitScreen> optional2, Optional<OneHanded> optional3, Optional<Bubbles> optional4, Optional<TaskViewFactory> optional5, ShellTransitions shellTransitions, Optional<StartingSurface> optional6, Optional<DisplayAreaHelper> optional7, Optional<RecentTasks> optional8, Optional<BackAnimation> optional9, Optional<DesktopMode> optional10) {
            this.timeoutHandlerProvider = TimeoutHandler_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider);
            ClipboardOverlayUtils_Factory create = ClipboardOverlayUtils_Factory.create(DaggerReferenceGlobalRootComponent.this.provideTextClassificationManagerProvider);
            this.clipboardOverlayUtilsProvider = create;
            this.clipboardOverlayControllerProvider = ClipboardOverlayController_Factory.create(this.provideWindowContextProvider, this.provideClipboardOverlayViewProvider, this.clipboardOverlayWindowProvider, this.broadcastDispatcherProvider, this.broadcastSenderProvider, this.timeoutHandlerProvider, this.featureFlagsDebugProvider, create, this.provideBackgroundExecutorProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider);
            this.clipboardOverlayControllerLegacyFactoryProvider = DoubleCheck.provider(ClipboardOverlayControllerLegacyFactory_Factory.create(this.broadcastDispatcherProvider, this.broadcastSenderProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider));
            this.clipboardToastProvider = ClipboardToast_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider);
            this.clipboardListenerProvider = DoubleCheck.provider(ClipboardListener_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.deviceConfigProxyProvider, this.clipboardOverlayControllerProvider, this.clipboardOverlayControllerLegacyFactoryProvider, this.clipboardToastProvider, DaggerReferenceGlobalRootComponent.this.provideClipboardManagerProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, this.featureFlagsDebugProvider));
            this.launchFullScreenIntentProvider = DoubleCheck.provider(LaunchFullScreenIntentProvider_Factory.create());
            this.provideExecutorProvider = DoubleCheck.provider(SysUIConcurrencyModule_ProvideExecutorFactory.create(this.provideBgLooperProvider));
            this.fsiChromeRepoProvider = DoubleCheck.provider(FsiChromeRepo_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.providePackageManagerProvider, this.keyguardRepositoryImplProvider, this.launchFullScreenIntentProvider, this.featureFlagsDebugProvider, this.provideExecutorProvider, DaggerReferenceGlobalRootComponent.this.provideIDreamManagerProvider, this.centralSurfacesImplProvider));
            Factory create2 = InstanceFactory.create(optional5);
            this.setTaskViewFactoryProvider = create2;
            this.fsiChromeViewModelFactoryProvider = DoubleCheck.provider(FsiChromeViewModelFactory_Factory.create(this.fsiChromeRepoProvider, create2, DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider));
            this.fsiChromeViewBinderProvider = DoubleCheck.provider(FsiChromeViewBinder_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideWindowManagerProvider, this.fsiChromeViewModelFactoryProvider, DaggerReferenceGlobalRootComponent.this.providerLayoutInflaterProvider, this.centralSurfacesImplProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.applicationScopeProvider));
            this.providesBackgroundMessageRouterProvider = SysUIConcurrencyModule_ProvidesBackgroundMessageRouterFactory.create(this.provideBackgroundDelayableExecutorProvider);
            this.provideLeakReportEmailProvider = DoubleCheck.provider(ReferenceSystemUIModule_ProvideLeakReportEmailFactory.create());
            this.leakReporterProvider = DoubleCheck.provider(LeakReporter_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.providesLeakDetectorProvider, this.provideLeakReportEmailProvider));
            this.garbageMonitorProvider = DoubleCheck.provider(GarbageMonitor_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideBackgroundDelayableExecutorProvider, this.providesBackgroundMessageRouterProvider, this.providesLeakDetectorProvider, this.leakReporterProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
            this.serviceProvider = DoubleCheck.provider(GarbageMonitor_Service_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.garbageMonitorProvider));
            this.globalActionsComponentProvider = new DelegateFactory();
            this.providesControlsFeatureEnabledProvider = DoubleCheck.provider(ControlsModule_ProvidesControlsFeatureEnabledFactory.create(DaggerReferenceGlobalRootComponent.this.providePackageManagerProvider));
            this.controlsControllerImplProvider = new DelegateFactory();
            this.controlsListingControllerImplProvider = DoubleCheck.provider(ControlsListingControllerImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideBackgroundExecutorProvider, this.provideUserTrackerProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.featureFlagsDebugProvider));
            this.controlsMetricsLoggerImplProvider = DoubleCheck.provider(ControlsMetricsLoggerImpl_Factory.create());
            Provider<ControlsSettingsRepositoryImpl> provider = DoubleCheck.provider(ControlsSettingsRepositoryImpl_Factory.create(this.applicationScopeProvider, this.bgDispatcherProvider, this.userRepositoryImplProvider, this.secureSettingsImplProvider));
            this.controlsSettingsRepositoryImplProvider = provider;
            this.controlsSettingsDialogManagerImplProvider = DoubleCheck.provider(ControlsSettingsDialogManagerImpl_Factory.create(this.secureSettingsImplProvider, this.userFileManagerImplProvider, provider, this.provideUserTrackerProvider, this.provideActivityStarterProvider));
            this.controlActionCoordinatorImplProvider = DoubleCheck.provider(ControlActionCoordinatorImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideDelayableExecutorProvider, DaggerReferenceGlobalRootComponent.this.provideMainDelayableExecutorProvider, this.provideActivityStarterProvider, this.broadcastSenderProvider, this.keyguardStateControllerImplProvider, this.setTaskViewFactoryProvider, this.controlsMetricsLoggerImplProvider, this.vibratorHelperProvider, this.controlsSettingsRepositoryImplProvider, this.controlsSettingsDialogManagerImplProvider, this.featureFlagsDebugProvider));
            this.customIconCacheProvider = DoubleCheck.provider(CustomIconCache_Factory.create());
            this.controlsUiControllerImplProvider = DoubleCheck.provider(ControlsUiControllerImpl_Factory.create(this.controlsControllerImplProvider, DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideMainDelayableExecutorProvider, this.provideBackgroundDelayableExecutorProvider, this.controlsListingControllerImplProvider, this.controlActionCoordinatorImplProvider, this.provideActivityStarterProvider, this.customIconCacheProvider, this.controlsMetricsLoggerImplProvider, this.keyguardStateControllerImplProvider, this.userFileManagerImplProvider, this.provideUserTrackerProvider, this.setTaskViewFactoryProvider, this.controlsSettingsRepositoryImplProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
            this.controlsBindingControllerImplProvider = DoubleCheck.provider(ControlsBindingControllerImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideBackgroundDelayableExecutorProvider, this.controlsControllerImplProvider, this.provideUserTrackerProvider));
            this.optionalOfControlsFavoritePersistenceWrapperProvider = DaggerReferenceGlobalRootComponent.m2000$$Nest$smabsentJdkOptionalProvider();
            DelegateFactory.setDelegate(this.controlsControllerImplProvider, DoubleCheck.provider(ControlsControllerImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideBackgroundDelayableExecutorProvider, this.controlsUiControllerImplProvider, this.controlsBindingControllerImplProvider, this.controlsListingControllerImplProvider, this.userFileManagerImplProvider, this.provideUserTrackerProvider, this.optionalOfControlsFavoritePersistenceWrapperProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider)));
            this.optionalOfControlsTileResourceConfigurationProvider = DaggerReferenceGlobalRootComponent.m2000$$Nest$smabsentJdkOptionalProvider();
            this.controlsComponentProvider = DoubleCheck.provider(ControlsComponent_Factory.create(this.providesControlsFeatureEnabledProvider, DaggerReferenceGlobalRootComponent.this.contextProvider, this.controlsControllerImplProvider, this.controlsUiControllerImplProvider, this.controlsListingControllerImplProvider, DaggerReferenceGlobalRootComponent.this.provideLockPatternUtilsProvider, this.keyguardStateControllerImplProvider, this.provideUserTrackerProvider, this.controlsSettingsRepositoryImplProvider, this.optionalOfControlsTileResourceConfigurationProvider));
            this.globalActionsDialogLiteProvider = GlobalActionsDialogLite_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.globalActionsComponentProvider, DaggerReferenceGlobalRootComponent.this.provideAudioManagerProvider, DaggerReferenceGlobalRootComponent.this.provideIDreamManagerProvider, DaggerReferenceGlobalRootComponent.this.provideDevicePolicyManagerProvider, DaggerReferenceGlobalRootComponent.this.provideLockPatternUtilsProvider, this.broadcastDispatcherProvider, this.globalSettingsImplProvider, this.secureSettingsImplProvider, this.vibratorHelperProvider, DaggerReferenceGlobalRootComponent.this.provideResourcesProvider, this.configurationControllerImplProvider, this.provideUserTrackerProvider, this.keyguardStateControllerImplProvider, DaggerReferenceGlobalRootComponent.this.provideUserManagerProvider, DaggerReferenceGlobalRootComponent.this.provideTrustManagerProvider, DaggerReferenceGlobalRootComponent.this.provideIActivityManagerProvider, DaggerReferenceGlobalRootComponent.this.provideTelecomManagerProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.sysuiColorExtractorProvider, DaggerReferenceGlobalRootComponent.this.provideIStatusBarServiceProvider, this.notificationShadeWindowControllerImplProvider, DaggerReferenceGlobalRootComponent.this.provideIWindowManagerProvider, this.provideBackgroundExecutorProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, this.ringerModeTrackerImplProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, DaggerReferenceGlobalRootComponent.this.providePackageManagerProvider, this.optionalOfCentralSurfacesProvider, this.keyguardUpdateMonitorProvider, this.provideDialogLaunchAnimatorProvider, this.controlsComponentProvider, this.provideActivityStarterProvider);
            GlobalActionsImpl_Factory create3 = GlobalActionsImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideCommandQueueProvider, this.globalActionsDialogLiteProvider, this.blurUtilsProvider, this.keyguardStateControllerImplProvider, this.bindDeviceProvisionedControllerProvider);
            this.globalActionsImplProvider = create3;
            DelegateFactory.setDelegate(this.globalActionsComponentProvider, DoubleCheck.provider(GlobalActionsComponent_Factory.create(this.provideCommandQueueProvider, this.extensionControllerImplProvider, create3, this.statusBarKeyguardViewManagerProvider)));
            this.instantAppNotifierProvider = DoubleCheck.provider(InstantAppNotifier_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideCommandQueueProvider, DaggerReferenceGlobalRootComponent.this.provideUiBackgroundExecutorProvider, this.keyguardStateControllerImplProvider));
            this.keyboardUIProvider = DoubleCheck.provider(KeyboardUI_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideLocalBluetoothControllerProvider));
            this.keyguardBiometricLockoutLoggerProvider = DoubleCheck.provider(KeyguardBiometricLockoutLogger_Factory.create(DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, this.keyguardUpdateMonitorProvider, this.sessionTrackerProvider));
            this.latencyTesterProvider = DoubleCheck.provider(LatencyTester_Factory.create(this.biometricUnlockControllerProvider, this.broadcastDispatcherProvider, this.deviceConfigProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMainDelayableExecutorProvider));
            this.powerNotificationWarningsProvider = DoubleCheck.provider(PowerNotificationWarnings_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideActivityStarterProvider, this.broadcastSenderProvider, this.provideBatteryControllerProvider, this.provideDialogLaunchAnimatorProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider));
            this.powerUIProvider = DoubleCheck.provider(PowerUI_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.broadcastDispatcherProvider, this.provideCommandQueueProvider, this.optionalOfCentralSurfacesProvider, this.powerNotificationWarningsProvider, this.enhancedEstimatesImplProvider, this.wakefulnessLifecycleProvider, DaggerReferenceGlobalRootComponent.this.providePowerManagerProvider, this.provideUserTrackerProvider));
            this.ringtonePlayerProvider = DoubleCheck.provider(RingtonePlayer_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider));
            this.systemEventCoordinatorProvider = DoubleCheck.provider(SystemEventCoordinator_Factory.create(this.bindSystemClockProvider, this.provideBatteryControllerProvider, this.privacyItemControllerProvider, DaggerReferenceGlobalRootComponent.this.contextProvider));
            SystemEventChipAnimationController_Factory create4 = SystemEventChipAnimationController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.statusBarWindowControllerProvider, this.statusBarContentInsetsProvider);
            this.systemEventChipAnimationControllerProvider = create4;
            this.systemStatusAnimationSchedulerProvider = DoubleCheck.provider(SystemStatusAnimationScheduler_Factory.create(this.systemEventCoordinatorProvider, create4, this.statusBarWindowControllerProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.bindSystemClockProvider, DaggerReferenceGlobalRootComponent.this.provideMainDelayableExecutorProvider));
            this.privacyDotViewControllerProvider = DoubleCheck.provider(PrivacyDotViewController_Factory.create(DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.statusBarStateControllerImplProvider, this.configurationControllerImplProvider, this.statusBarContentInsetsProvider, this.systemStatusAnimationSchedulerProvider, this.shadeExpansionStateManagerProvider));
            this.privacyDotDecorProviderFactoryProvider = DoubleCheck.provider(PrivacyDotDecorProviderFactory_Factory.create(DaggerReferenceGlobalRootComponent.this.provideResourcesProvider));
            this.faceScanningProviderFactoryProvider = DoubleCheck.provider(FaceScanningProviderFactory_Factory.create(this.authControllerProvider, DaggerReferenceGlobalRootComponent.this.contextProvider, this.statusBarStateControllerImplProvider, this.keyguardUpdateMonitorProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider));
            this.screenDecorationsProvider = DoubleCheck.provider(ScreenDecorations_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.secureSettingsImplProvider, this.tunerServiceImplProvider, this.provideUserTrackerProvider, this.privacyDotViewControllerProvider, ThreadFactoryImpl_Factory.create(), this.privacyDotDecorProviderFactoryProvider, this.faceScanningProviderFactoryProvider));
            this.shortcutKeyDispatcherProvider = DoubleCheck.provider(ShortcutKeyDispatcher_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider));
            this.sliceBroadcastRelayHandlerProvider = DoubleCheck.provider(SliceBroadcastRelayHandler_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.broadcastDispatcherProvider));
            this.storageNotificationProvider = DoubleCheck.provider(StorageNotification_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.broadcastDispatcherProvider, DaggerReferenceGlobalRootComponent.this.provideNotificationManagerProvider, DaggerReferenceGlobalRootComponent.this.provideStorageManagerProvider));
            this.provideLauncherPackageProvider = ThemeModule_ProvideLauncherPackageFactory.create(DaggerReferenceGlobalRootComponent.this.provideResourcesProvider);
            this.provideThemePickerPackageProvider = ThemeModule_ProvideThemePickerPackageFactory.create(DaggerReferenceGlobalRootComponent.this.provideResourcesProvider);
            this.themeOverlayApplierProvider = DoubleCheck.provider(ThemeOverlayApplier_Factory.create(DaggerReferenceGlobalRootComponent.this.provideOverlayManagerProvider, this.provideBackgroundExecutorProvider, this.provideLauncherPackageProvider, this.provideThemePickerPackageProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
            this.systemSettingsImplProvider = SystemSettingsImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.provideContentResolverProvider);
            this.themeOverlayControllerProvider = DoubleCheck.provider(ThemeOverlayController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.broadcastDispatcherProvider, this.provideBgHandlerProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.provideBackgroundExecutorProvider, this.themeOverlayApplierProvider, this.secureSettingsImplProvider, DaggerReferenceGlobalRootComponent.this.provideWallpaperManagerProvider, DaggerReferenceGlobalRootComponent.this.provideUserManagerProvider, this.bindDeviceProvisionedControllerProvider, this.provideUserTrackerProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.featureFlagsDebugProvider, DaggerReferenceGlobalRootComponent.this.provideResourcesProvider, this.wakefulnessLifecycleProvider, this.systemSettingsImplProvider));
            Provider<LogBuffer> provider2 = DoubleCheck.provider(LogModule_ProvideToastLogBufferFactory.create(this.logBufferFactoryProvider));
            this.provideToastLogBufferProvider = provider2;
            this.toastLoggerProvider = ToastLogger_Factory.create(provider2);
            this.toastUIProvider = DoubleCheck.provider(ToastUI_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideCommandQueueProvider, this.toastFactoryProvider, this.toastLoggerProvider));
            this.volumeUIProvider = DoubleCheck.provider(VolumeUI_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.volumeDialogComponentProvider));
            this.modeSwitchesControllerProvider = DoubleCheck.provider(ModeSwitchesController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider));
            this.windowMagnificationProvider = DoubleCheck.provider(WindowMagnification_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.provideCommandQueueProvider, this.modeSwitchesControllerProvider, this.provideSysUiStateProvider, this.overviewProxyServiceProvider));
            this.setSplitScreenProvider = InstanceFactory.create(optional2);
            this.setOneHandedProvider = InstanceFactory.create(optional3);
            this.setDesktopModeProvider = InstanceFactory.create(optional10);
            this.noteTaskIntentResolverProvider = NoteTaskIntentResolver_Factory.create(DaggerReferenceGlobalRootComponent.this.providePackageManagerProvider);
            this.provideOptionalKeyguardManagerProvider = NoteTaskModule_Companion_ProvideOptionalKeyguardManagerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider);
            this.provideOptionalUserManagerProvider = NoteTaskModule_Companion_ProvideOptionalUserManagerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider);
            this.provideIsNoteTaskEnabledProvider = NoteTaskModule_Companion_ProvideIsNoteTaskEnabledFactory.create(this.featureFlagsDebugProvider);
            Provider<NoteTaskController> provider3 = DoubleCheck.provider(NoteTaskController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.noteTaskIntentResolverProvider, this.setBubblesProvider, this.provideOptionalKeyguardManagerProvider, this.provideOptionalUserManagerProvider, this.provideIsNoteTaskEnabledProvider));
            this.noteTaskControllerProvider = provider3;
            this.noteTaskInitializerProvider = NoteTaskInitializer_Factory.create(this.setBubblesProvider, provider3, this.provideCommandQueueProvider, this.provideIsNoteTaskEnabledProvider);
            this.wMShellProvider = DoubleCheck.provider(WMShell_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.setShellProvider, this.setPipProvider, this.setSplitScreenProvider, this.setOneHandedProvider, this.setDesktopModeProvider, this.provideCommandQueueProvider, this.configurationControllerImplProvider, this.keyguardStateControllerImplProvider, this.keyguardUpdateMonitorProvider, DaggerReferenceGlobalRootComponent.this.screenLifecycleProvider, this.provideSysUiStateProvider, this.protoTracerProvider, this.wakefulnessLifecycleProvider, this.provideUserTrackerProvider, this.noteTaskInitializerProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider));
            this.keyguardLiftControllerProvider = DoubleCheck.provider(KeyguardLiftController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.statusBarStateControllerImplProvider, this.asyncSensorManagerProvider, this.keyguardUpdateMonitorProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
            Provider<LogBuffer> provider4 = DoubleCheck.provider(TemporaryDisplayModule_ProvideChipbarLogBufferFactory.create(this.logBufferFactoryProvider));
            this.provideChipbarLogBufferProvider = provider4;
            this.chipbarLoggerProvider = DoubleCheck.provider(ChipbarLogger_Factory.create(provider4));
            this.viewUtilProvider = DoubleCheck.provider(ViewUtil_Factory.create());
            this.chipbarCoordinatorProvider = DoubleCheck.provider(ChipbarCoordinator_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.chipbarLoggerProvider, DaggerReferenceGlobalRootComponent.this.provideWindowManagerProvider, DaggerReferenceGlobalRootComponent.this.provideMainDelayableExecutorProvider, DaggerReferenceGlobalRootComponent.this.provideAccessibilityManagerProvider, this.configurationControllerImplProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, DaggerReferenceGlobalRootComponent.this.providePowerManagerProvider, this.falsingManagerProxyProvider, this.falsingCollectorImplProvider, this.viewUtilProvider, this.vibratorHelperProvider, this.builderProvider3, this.bindSystemClockProvider));
            Provider<LogBuffer> provider5 = DoubleCheck.provider(LogModule_ProvideMediaTttSenderLogBufferFactory.create(this.logBufferFactoryProvider));
            this.provideMediaTttSenderLogBufferProvider = provider5;
            this.providesMediaTttSenderLoggerProvider = DoubleCheck.provider(MediaModule_ProvidesMediaTttSenderLoggerFactory.create(provider5));
            this.mediaTttFlagsProvider = DoubleCheck.provider(MediaTttFlags_Factory.create(this.featureFlagsDebugProvider));
            this.mediaTttSenderUiEventLoggerProvider = DoubleCheck.provider(MediaTttSenderUiEventLogger_Factory.create(DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider));
            this.mediaTttSenderCoordinatorProvider = DoubleCheck.provider(MediaTttSenderCoordinator_Factory.create(this.chipbarCoordinatorProvider, this.provideCommandQueueProvider, DaggerReferenceGlobalRootComponent.this.contextProvider, this.providesMediaTttSenderLoggerProvider, this.mediaTttFlagsProvider, this.mediaTttSenderUiEventLoggerProvider));
            Provider<LogBuffer> provider6 = DoubleCheck.provider(LogModule_ProvideMediaTttReceiverLogBufferFactory.create(this.logBufferFactoryProvider));
            this.provideMediaTttReceiverLogBufferProvider = provider6;
            this.providesMediaTttReceiverLoggerProvider = DoubleCheck.provider(MediaModule_ProvidesMediaTttReceiverLoggerFactory.create(provider6));
            this.mediaTttReceiverUiEventLoggerProvider = DoubleCheck.provider(MediaTttReceiverUiEventLogger_Factory.create(DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider));
            this.mediaTttChipControllerReceiverProvider = DoubleCheck.provider(MediaTttChipControllerReceiver_Factory.create(this.provideCommandQueueProvider, DaggerReferenceGlobalRootComponent.this.contextProvider, this.providesMediaTttReceiverLoggerProvider, DaggerReferenceGlobalRootComponent.this.provideWindowManagerProvider, this.provideDelayableExecutorProvider, DaggerReferenceGlobalRootComponent.this.provideAccessibilityManagerProvider, this.configurationControllerImplProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, DaggerReferenceGlobalRootComponent.this.providePowerManagerProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.mediaTttFlagsProvider, this.mediaTttReceiverUiEventLoggerProvider, this.viewUtilProvider, this.builderProvider3, this.bindSystemClockProvider));
            this.mediaTttCommandLineHelperProvider = DoubleCheck.provider(MediaTttCommandLineHelper_Factory.create(this.commandRegistryProvider, DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider));
            this.rearDisplayDialogControllerProvider = DoubleCheck.provider(RearDisplayDialogController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideCommandQueueProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider));
            this.stylusManagerProvider = DoubleCheck.provider(StylusManager_Factory.create(DaggerReferenceGlobalRootComponent.this.provideInputManagerProvider, DaggerReferenceGlobalRootComponent.this.provideBluetoothAdapterProvider, this.provideBgHandlerProvider, this.provideBackgroundExecutorProvider));
            this.stylusUsiPowerUIProvider = DoubleCheck.provider(StylusUsiPowerUI_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideNotificationManagerCompatProvider, DaggerReferenceGlobalRootComponent.this.provideInputManagerProvider, this.provideBgHandlerProvider));
            this.stylusUsiPowerStartableProvider = DoubleCheck.provider(StylusUsiPowerStartable_Factory.create(this.stylusManagerProvider, DaggerReferenceGlobalRootComponent.this.provideInputManagerProvider, this.stylusUsiPowerUIProvider, this.featureFlagsDebugProvider, this.provideBackgroundExecutorProvider));
            this.mapOfClassOfAndProviderOfCoreStartableProvider = MapProviderFactory.builder(43).put(BroadcastDispatcherStartable.class, this.broadcastDispatcherStartableProvider).put(KeyguardNotificationVisibilityProvider.class, this.keyguardNotificationVisibilityProviderImplProvider).put(KeyguardTransitionCoreStartable.class, this.keyguardTransitionCoreStartableProvider).put(FeatureFlagsDebugStartable.class, this.featureFlagsDebugStartableProvider).put(MotionToolStartable.class, this.motionToolStartableProvider).put(UserFileManagerImpl.class, this.userFileManagerImplProvider).put(MobileUiAdapter.class, this.mobileUiAdapterProvider).put(UserSwitcherDialogCoordinator.class, this.userSwitcherDialogCoordinatorProvider).put(AuthController.class, this.authControllerProvider).put(ChooserSelector.class, this.chooserSelectorProvider).put(ClipboardListener.class, this.clipboardListenerProvider).put(FsiChromeRepo.class, this.fsiChromeRepoProvider).put(FsiChromeViewModelFactory.class, this.fsiChromeViewModelFactoryProvider).put(FsiChromeViewBinder.class, this.fsiChromeViewBinderProvider).put(GarbageMonitor.class, this.serviceProvider).put(GlobalActionsComponent.class, this.globalActionsComponentProvider).put(InstantAppNotifier.class, this.instantAppNotifierProvider).put(KeyboardUI.class, this.keyboardUIProvider).put(KeyguardBiometricLockoutLogger.class, this.keyguardBiometricLockoutLoggerProvider).put(KeyguardViewMediator.class, this.newKeyguardViewMediatorProvider).put(LatencyTester.class, this.latencyTesterProvider).put(PowerUI.class, this.powerUIProvider).put(Recents.class, this.provideRecentsProvider).put(RingtonePlayer.class, this.ringtonePlayerProvider).put(ScreenDecorations.class, this.screenDecorationsProvider).put(SessionTracker.class, this.sessionTrackerProvider).put(ShortcutKeyDispatcher.class, this.shortcutKeyDispatcherProvider).put(SliceBroadcastRelayHandler.class, this.sliceBroadcastRelayHandlerProvider).put(StorageNotification.class, this.storageNotificationProvider).put(SystemActions.class, this.systemActionsProvider).put(ThemeOverlayController.class, this.themeOverlayControllerProvider).put(ToastUI.class, this.toastUIProvider).put(VolumeUI.class, this.volumeUIProvider).put(WindowMagnification.class, this.windowMagnificationProvider).put(WMShell.class, this.wMShellProvider).put(KeyguardLiftController.class, this.keyguardLiftControllerProvider).put(MediaTttSenderCoordinator.class, this.mediaTttSenderCoordinatorProvider).put(MediaTttChipControllerReceiver.class, this.mediaTttChipControllerReceiverProvider).put(MediaTttCommandLineHelper.class, this.mediaTttCommandLineHelperProvider).put(ChipbarCoordinator.class, this.chipbarCoordinatorProvider).put(RearDisplayDialogController.class, this.rearDisplayDialogControllerProvider).put(StylusUsiPowerStartable.class, this.stylusUsiPowerStartableProvider).put(CentralSurfaces.class, this.centralSurfacesImplProvider).build();
            this.dumpHandlerProvider = DumpHandler_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.logBufferEulogizerProvider, this.mapOfClassOfAndProviderOfCoreStartableProvider, DaggerReferenceGlobalRootComponent.this.uncaughtExceptionPreHandlerManagerProvider);
            DelegateFactory.setDelegate(this.provideCommandQueueProvider, DoubleCheck.provider(CentralSurfacesDependenciesModule_ProvideCommandQueueFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.protoTracerProvider, this.commandRegistryProvider, this.dumpHandlerProvider)));
            this.udfpsHapticsSimulatorProvider = DoubleCheck.provider(UdfpsHapticsSimulator_Factory.create(this.commandRegistryProvider, this.vibratorHelperProvider, this.keyguardUpdateMonitorProvider));
            this.udfpsShellProvider = DoubleCheck.provider(UdfpsShell_Factory.create(this.commandRegistryProvider));
            this.systemUIDialogManagerProvider = DoubleCheck.provider(SystemUIDialogManager_Factory.create(DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.statusBarKeyguardViewManagerProvider));
            this.optionalOfProviderOfAlternateUdfpsTouchProvider = DaggerReferenceGlobalRootComponent.m2000$$Nest$smabsentJdkOptionalProvider();
            this.providesPluginExecutorProvider = DoubleCheck.provider(BiometricsModule_ProvidesPluginExecutorFactory.create(ThreadFactoryImpl_Factory.create()));
        }

        public final void initialize7(LeakModule leakModule, NightDisplayListenerModule nightDisplayListenerModule, SharedLibraryModule sharedLibraryModule, KeyguardModule keyguardModule, SysUIUnfoldModule sysUIUnfoldModule, ShellInterface shellInterface, Optional<Pip> optional, Optional<SplitScreen> optional2, Optional<OneHanded> optional3, Optional<Bubbles> optional4, Optional<TaskViewFactory> optional5, ShellTransitions shellTransitions, Optional<StartingSurface> optional6, Optional<DisplayAreaHelper> optional7, Optional<RecentTasks> optional8, Optional<BackAnimation> optional9, Optional<DesktopMode> optional10) {
            Provider<OverlapDetector> provider = DoubleCheck.provider(UdfpsModule_Companion_ProvidesOverlapDetectorFactory.create(this.featureFlagsDebugProvider));
            this.providesOverlapDetectorProvider = provider;
            this.singlePointerTouchProcessorProvider = DoubleCheck.provider(SinglePointerTouchProcessor_Factory.create(provider));
            this.udfpsControllerProvider = DoubleCheck.provider(UdfpsController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideExecutionProvider, DaggerReferenceGlobalRootComponent.this.providerLayoutInflaterProvider, DaggerReferenceGlobalRootComponent.this.providesFingerprintManagerProvider, DaggerReferenceGlobalRootComponent.this.provideWindowManagerProvider, this.statusBarStateControllerImplProvider, DaggerReferenceGlobalRootComponent.this.provideMainDelayableExecutorProvider, this.shadeExpansionStateManagerProvider, this.statusBarKeyguardViewManagerProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.keyguardUpdateMonitorProvider, this.featureFlagsDebugProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.providePowerManagerProvider, DaggerReferenceGlobalRootComponent.this.provideAccessibilityManagerProvider, this.lockscreenShadeTransitionControllerProvider, DaggerReferenceGlobalRootComponent.this.screenLifecycleProvider, this.vibratorHelperProvider, this.udfpsHapticsSimulatorProvider, this.udfpsShellProvider, this.keyguardStateControllerImplProvider, DaggerReferenceGlobalRootComponent.this.provideDisplayManagerProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.configurationControllerImplProvider, this.bindSystemClockProvider, this.unlockedScreenOffAnimationControllerProvider, this.systemUIDialogManagerProvider, DaggerReferenceGlobalRootComponent.this.provideLatencyTrackerProvider, this.provideActivityLaunchAnimatorProvider, this.optionalOfProviderOfAlternateUdfpsTouchProvider, this.providesPluginExecutorProvider, this.primaryBouncerInteractorProvider, this.singlePointerTouchProcessorProvider, this.secureSettingsImplProvider));
            this.sideFpsControllerProvider = DoubleCheck.provider(SideFpsController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.providerLayoutInflaterProvider, DaggerReferenceGlobalRootComponent.this.providesFingerprintManagerProvider, DaggerReferenceGlobalRootComponent.this.provideWindowManagerProvider, DaggerReferenceGlobalRootComponent.this.provideActivityTaskManagerProvider, this.overviewProxyServiceProvider, DaggerReferenceGlobalRootComponent.this.provideDisplayManagerProvider, DaggerReferenceGlobalRootComponent.this.provideMainDelayableExecutorProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
            DelegateFactory.setDelegate(this.authControllerProvider, DoubleCheck.provider(AuthController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideExecutionProvider, this.provideCommandQueueProvider, DaggerReferenceGlobalRootComponent.this.provideActivityTaskManagerProvider, DaggerReferenceGlobalRootComponent.this.provideWindowManagerProvider, DaggerReferenceGlobalRootComponent.this.providesFingerprintManagerProvider, DaggerReferenceGlobalRootComponent.this.provideFaceManagerProvider, this.udfpsControllerProvider, this.sideFpsControllerProvider, DaggerReferenceGlobalRootComponent.this.provideDisplayManagerProvider, this.wakefulnessLifecycleProvider, DaggerReferenceGlobalRootComponent.this.provideUserManagerProvider, DaggerReferenceGlobalRootComponent.this.provideLockPatternUtilsProvider, this.statusBarStateControllerImplProvider, DaggerReferenceGlobalRootComponent.this.provideInteractionJankMonitorProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.provideBackgroundDelayableExecutorProvider, this.vibratorHelperProvider)));
            this.activeUnlockConfigProvider = DoubleCheck.provider(ActiveUnlockConfig_Factory.create(DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.secureSettingsImplProvider, DaggerReferenceGlobalRootComponent.this.provideContentResolverProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
            Provider<LogBuffer> provider2 = DoubleCheck.provider(LogModule_ProvideKeyguardUpdateMonitorLogBufferFactory.create(this.logBufferFactoryProvider));
            this.provideKeyguardUpdateMonitorLogBufferProvider = provider2;
            this.keyguardUpdateMonitorLoggerProvider = KeyguardUpdateMonitorLogger_Factory.create(provider2);
            this.faceWakeUpTriggersConfigProvider = DoubleCheck.provider(FaceWakeUpTriggersConfig_Factory.create(DaggerReferenceGlobalRootComponent.this.provideResourcesProvider, this.globalSettingsImplProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
            DelegateFactory.setDelegate(this.keyguardUpdateMonitorProvider, DoubleCheck.provider(KeyguardUpdateMonitor_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideUserTrackerProvider, GlobalConcurrencyModule_ProvideMainLooperFactory.create(), this.broadcastDispatcherProvider, this.secureSettingsImplProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.provideBackgroundExecutorProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.statusBarStateControllerImplProvider, DaggerReferenceGlobalRootComponent.this.provideLockPatternUtilsProvider, this.authControllerProvider, this.telephonyListenerManagerProvider, DaggerReferenceGlobalRootComponent.this.provideInteractionJankMonitorProvider, DaggerReferenceGlobalRootComponent.this.provideLatencyTrackerProvider, this.activeUnlockConfigProvider, this.keyguardUpdateMonitorLoggerProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, this.sessionTrackerProvider, DaggerReferenceGlobalRootComponent.this.providePowerManagerProvider, DaggerReferenceGlobalRootComponent.this.provideTrustManagerProvider, DaggerReferenceGlobalRootComponent.this.provideSubscriptionManagerProvider, DaggerReferenceGlobalRootComponent.this.provideUserManagerProvider, DaggerReferenceGlobalRootComponent.this.provideIDreamManagerProvider, DaggerReferenceGlobalRootComponent.this.provideDevicePolicyManagerProvider, DaggerReferenceGlobalRootComponent.this.provideSensorPrivacyManagerProvider, DaggerReferenceGlobalRootComponent.this.provideTelephonyManagerProvider, DaggerReferenceGlobalRootComponent.this.providePackageManagerProvider, DaggerReferenceGlobalRootComponent.this.provideFaceManagerProvider, DaggerReferenceGlobalRootComponent.this.providesFingerprintManagerProvider, DaggerReferenceGlobalRootComponent.this.providesBiometricManagerProvider, this.faceWakeUpTriggersConfigProvider)));
            DelegateFactory.setDelegate(this.keyguardStateControllerImplProvider, DoubleCheck.provider(KeyguardStateControllerImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.keyguardUpdateMonitorProvider, DaggerReferenceGlobalRootComponent.this.provideLockPatternUtilsProvider, this.keyguardUnlockAnimationControllerProvider, this.keyguardUpdateMonitorLoggerProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider)));
            this.brightLineFalsingManagerProvider = BrightLineFalsingManager_Factory.create(this.falsingDataProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.namedSetOfFalsingClassifierProvider, this.singleTapClassifierProvider, this.longTapClassifierProvider, this.doubleTapClassifierProvider, this.historyTrackerProvider, this.keyguardStateControllerImplProvider, DaggerReferenceGlobalRootComponent.this.provideAccessibilityManagerProvider, DaggerReferenceGlobalRootComponent.this.provideIsTestHarnessProvider, this.featureFlagsDebugProvider);
            DelegateFactory.setDelegate(this.falsingManagerProxyProvider, DoubleCheck.provider(FalsingManagerProxy_Factory.create(DaggerReferenceGlobalRootComponent.this.providesPluginManagerProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.deviceConfigProxyProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.brightLineFalsingManagerProvider)));
            DelegateFactory.setDelegate(this.factoryProvider9, BrightnessSliderController_Factory_Factory.create(this.falsingManagerProxyProvider));
            this.brightnessDialogProvider = BrightnessDialog_Factory.create(this.provideUserTrackerProvider, this.factoryProvider9, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.provideBgHandlerProvider);
            this.usbDebuggingActivityProvider = UsbDebuggingActivity_Factory.create(this.broadcastDispatcherProvider);
            this.usbDebuggingSecondaryUserActivityProvider = UsbDebuggingSecondaryUserActivity_Factory.create(this.broadcastDispatcherProvider);
            this.usbPermissionActivityProvider = UsbPermissionActivity_Factory.create(UsbAudioWarningDialogMessage_Factory.create());
            this.usbConfirmActivityProvider = UsbConfirmActivity_Factory.create(UsbAudioWarningDialogMessage_Factory.create());
            UserCreator_Factory create = UserCreator_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideUserManagerProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.provideBackgroundExecutorProvider);
            this.userCreatorProvider = create;
            this.createUserActivityProvider = CreateUserActivity_Factory.create(create, UserModule_ProvideEditUserInfoControllerFactory.create(), DaggerReferenceGlobalRootComponent.this.provideIActivityManagerProvider, this.provideActivityStarterProvider);
            Provider<TvNotificationHandler> provider3 = DoubleCheck.provider(TvNotificationHandler_Factory.create(this.notificationListenerProvider));
            this.tvNotificationHandlerProvider = provider3;
            this.tvNotificationPanelActivityProvider = TvNotificationPanelActivity_Factory.create(provider3);
            this.peopleTileRepositoryImplProvider = DoubleCheck.provider(PeopleTileRepositoryImpl_Factory.create(this.peopleSpaceWidgetManagerProvider));
            this.peopleWidgetRepositoryImplProvider = DoubleCheck.provider(PeopleWidgetRepositoryImpl_Factory.create(this.peopleSpaceWidgetManagerProvider));
            PeopleViewModel_Factory_Factory create2 = PeopleViewModel_Factory_Factory.create(DaggerReferenceGlobalRootComponent.this.provideApplicationContextProvider, this.peopleTileRepositoryImplProvider, this.peopleWidgetRepositoryImplProvider);
            this.factoryProvider16 = create2;
            this.peopleSpaceActivityProvider = PeopleSpaceActivity_Factory.create(create2);
            this.imageExporterProvider = ImageExporter_Factory.create(DaggerReferenceGlobalRootComponent.this.provideContentResolverProvider, this.featureFlagsDebugProvider);
            this.longScreenshotDataProvider = DoubleCheck.provider(LongScreenshotData_Factory.create());
            this.actionIntentExecutorProvider = DoubleCheck.provider(ActionIntentExecutor_Factory.create(this.applicationScopeProvider, this.mainDispatcherProvider, DaggerReferenceGlobalRootComponent.this.contextProvider));
            this.longScreenshotActivityProvider = LongScreenshotActivity_Factory.create(DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, this.imageExporterProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.provideBackgroundExecutorProvider, this.longScreenshotDataProvider, this.actionIntentExecutorProvider, this.featureFlagsDebugProvider);
            this.launchConversationActivityProvider = LaunchConversationActivity_Factory.create(this.notificationVisibilityProviderImplProvider, this.notifPipelineProvider, this.provideBubblesManagerProvider, DaggerReferenceGlobalRootComponent.this.provideUserManagerProvider, this.provideCommandQueueProvider, this.provideBackgroundExecutorProvider);
            this.sensorUseStartedActivityProvider = SensorUseStartedActivity_Factory.create(this.provideIndividualSensorPrivacyControllerProvider, this.keyguardStateControllerImplProvider, this.keyguardDismissUtilProvider, this.provideBgHandlerProvider);
            this.tvUnblockSensorActivityProvider = TvUnblockSensorActivity_Factory.create(this.provideIndividualSensorPrivacyControllerProvider);
            Provider<HdmiCecSetMenuLanguageHelper> provider4 = DoubleCheck.provider(HdmiCecSetMenuLanguageHelper_Factory.create(this.provideBackgroundExecutorProvider, this.secureSettingsImplProvider));
            this.hdmiCecSetMenuLanguageHelperProvider = provider4;
            this.hdmiCecSetMenuLanguageActivityProvider = HdmiCecSetMenuLanguageActivity_Factory.create(provider4);
            this.controlsProviderSelectorActivityProvider = ControlsProviderSelectorActivity_Factory.create(DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.provideBackgroundExecutorProvider, this.controlsListingControllerImplProvider, this.controlsControllerImplProvider, this.provideUserTrackerProvider, this.controlsUiControllerImplProvider);
            this.controlsFavoritingActivityProvider = ControlsFavoritingActivity_Factory.create(DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.controlsControllerImplProvider, this.controlsListingControllerImplProvider, this.provideUserTrackerProvider, this.controlsUiControllerImplProvider);
            this.controlsEditingActivityProvider = ControlsEditingActivity_Factory.create(DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.controlsControllerImplProvider, this.provideUserTrackerProvider, this.customIconCacheProvider, this.controlsUiControllerImplProvider);
            this.controlsRequestDialogProvider = ControlsRequestDialog_Factory.create(DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.controlsControllerImplProvider, this.provideUserTrackerProvider, this.controlsListingControllerImplProvider);
            this.controlsActivityProvider = ControlsActivity_Factory.create(this.controlsUiControllerImplProvider, this.broadcastDispatcherProvider, DaggerReferenceGlobalRootComponent.this.provideIDreamManagerProvider, this.featureFlagsDebugProvider, this.controlsSettingsDialogManagerImplProvider, this.keyguardStateControllerImplProvider);
            this.mediaProjectionAppSelectorComponentFactoryProvider = new Provider<MediaProjectionAppSelectorComponent.Factory>() { // from class: com.android.systemui.dagger.DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.10
                /* JADX DEBUG: Method merged with bridge method */
                /* renamed from: get */
                public MediaProjectionAppSelectorComponent.Factory m2232get() {
                    return new MediaProjectionAppSelectorComponentFactory();
                }
            };
            AsyncActivityLauncher_Factory create3 = AsyncActivityLauncher_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideIActivityTaskManagerProvider, DaggerReferenceGlobalRootComponent.this.provideUiBackgroundExecutorProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider);
            this.asyncActivityLauncherProvider = create3;
            this.mediaProjectionAppSelectorActivityProvider = MediaProjectionAppSelectorActivity_Factory.create(this.mediaProjectionAppSelectorComponentFactoryProvider, create3);
            Provider<PowerRepositoryImpl> provider5 = DoubleCheck.provider(PowerRepositoryImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.providePowerManagerProvider, this.broadcastDispatcherProvider));
            this.powerRepositoryImplProvider = provider5;
            Provider<PowerInteractor> provider6 = DoubleCheck.provider(PowerInteractor_Factory.create(provider5));
            this.powerInteractorProvider = provider6;
            UserSwitcherViewModel_Factory_Factory create4 = UserSwitcherViewModel_Factory_Factory.create(this.userInteractorProvider, this.guestUserInteractorProvider, provider6);
            this.factoryProvider17 = create4;
            this.userSwitcherActivityProvider = UserSwitcherActivity_Factory.create(this.falsingCollectorImplProvider, create4);
            this.launchNoteTaskActivityProvider = LaunchNoteTaskActivity_Factory.create(this.noteTaskControllerProvider);
            this.walletActivityProvider = WalletActivity_Factory.create(this.keyguardStateControllerImplProvider, this.keyguardDismissUtilProvider, this.provideActivityStarterProvider, this.provideBackgroundExecutorProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, this.falsingCollectorImplProvider, this.provideUserTrackerProvider, this.keyguardUpdateMonitorProvider, this.statusBarKeyguardViewManagerProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider);
            this.mapOfClassOfAndProviderOfActivityProvider = MapProviderFactory.builder(26).put(TunerActivity.class, this.tunerActivityProvider).put(ForegroundServicesDialog.class, this.foregroundServicesDialogProvider).put(WorkLockActivity.class, this.workLockActivityProvider).put(BrightnessDialog.class, this.brightnessDialogProvider).put(UsbDebuggingActivity.class, this.usbDebuggingActivityProvider).put(UsbDebuggingSecondaryUserActivity.class, this.usbDebuggingSecondaryUserActivityProvider).put(UsbPermissionActivity.class, this.usbPermissionActivityProvider).put(UsbConfirmActivity.class, this.usbConfirmActivityProvider).put(CreateUserActivity.class, this.createUserActivityProvider).put(TvNotificationPanelActivity.class, this.tvNotificationPanelActivityProvider).put(PeopleSpaceActivity.class, this.peopleSpaceActivityProvider).put(LongScreenshotActivity.class, this.longScreenshotActivityProvider).put(LaunchConversationActivity.class, this.launchConversationActivityProvider).put(SensorUseStartedActivity.class, this.sensorUseStartedActivityProvider).put(TvUnblockSensorActivity.class, this.tvUnblockSensorActivityProvider).put(HdmiCecSetMenuLanguageActivity.class, this.hdmiCecSetMenuLanguageActivityProvider).put(ControlsProviderSelectorActivity.class, this.controlsProviderSelectorActivityProvider).put(ControlsFavoritingActivity.class, this.controlsFavoritingActivityProvider).put(ControlsEditingActivity.class, this.controlsEditingActivityProvider).put(ControlsRequestDialog.class, this.controlsRequestDialogProvider).put(ControlsActivity.class, this.controlsActivityProvider).put(MediaProjectionAppSelectorActivity.class, this.mediaProjectionAppSelectorActivityProvider).put(UserSwitcherActivity.class, this.userSwitcherActivityProvider).put(LaunchNoteTaskActivity.class, this.launchNoteTaskActivityProvider).put(CreateNoteTaskShortcutActivity.class, CreateNoteTaskShortcutActivity_Factory.create()).put(WalletActivity.class, this.walletActivityProvider).build();
            Provider<DozeComponent.Builder> provider7 = new Provider<DozeComponent.Builder>() { // from class: com.android.systemui.dagger.DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.11
                /* JADX DEBUG: Method merged with bridge method */
                /* renamed from: get */
                public DozeComponent.Builder m2233get() {
                    return new DozeComponentFactory();
                }
            };
            this.dozeComponentBuilderProvider = provider7;
            this.dozeServiceProvider = DozeService_Factory.create(provider7, DaggerReferenceGlobalRootComponent.this.providesPluginManagerProvider);
            this.imageWallpaperProvider = ImageWallpaper_Factory.create(this.provideBackgroundDelayableExecutorProvider);
            this.keyguardLifecyclesDispatcherProvider = DoubleCheck.provider(KeyguardLifecyclesDispatcher_Factory.create(DaggerReferenceGlobalRootComponent.this.screenLifecycleProvider, this.wakefulnessLifecycleProvider));
            Factory create5 = InstanceFactory.create(shellTransitions);
            this.setTransitionsProvider = create5;
            this.keyguardServiceProvider = KeyguardService_Factory.create(this.newKeyguardViewMediatorProvider, this.keyguardLifecyclesDispatcherProvider, this.screenOnCoordinatorProvider, create5);
            this.dreamOverlayComponentFactoryProvider = new Provider<DreamOverlayComponent.Factory>() { // from class: com.android.systemui.dagger.DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.12
                /* JADX DEBUG: Method merged with bridge method */
                /* renamed from: get */
                public DreamOverlayComponent.Factory m2234get() {
                    return new DreamOverlayComponentFactory();
                }
            };
            this.providesLowLightDreamComponentProvider = LowLightDreamModule_ProvidesLowLightDreamComponentFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider);
            this.dreamOverlayServiceProvider = DreamOverlayService_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideMainDelayableExecutorProvider, DaggerReferenceGlobalRootComponent.this.provideWindowManagerProvider, this.dreamOverlayComponentFactoryProvider, this.dreamOverlayStateControllerProvider, this.keyguardUpdateMonitorProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, this.providesLowLightDreamComponentProvider, this.dreamOverlayCallbackControllerProvider);
            this.notificationListenerWithPluginsProvider = NotificationListenerWithPlugins_Factory.create(DaggerReferenceGlobalRootComponent.this.providesPluginManagerProvider);
            this.logBufferFreezerProvider = LogBufferFreezer_Factory.create(DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, DaggerReferenceGlobalRootComponent.this.provideMainDelayableExecutorProvider);
            this.batteryStateNotifierProvider = BatteryStateNotifier_Factory.create(this.provideBatteryControllerProvider, DaggerReferenceGlobalRootComponent.this.provideNotificationManagerProvider, this.provideDelayableExecutorProvider, DaggerReferenceGlobalRootComponent.this.contextProvider);
            this.systemUIServiceProvider = SystemUIService_Factory.create(DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.dumpHandlerProvider, this.broadcastDispatcherProvider, this.logBufferFreezerProvider, this.batteryStateNotifierProvider);
            this.systemUIAuxiliaryDumpServiceProvider = SystemUIAuxiliaryDumpService_Factory.create(this.dumpHandlerProvider);
            Provider<Looper> provider8 = DoubleCheck.provider(SysUIConcurrencyModule_ProvideLongRunningLooperFactory.create());
            this.provideLongRunningLooperProvider = provider8;
            Provider<Executor> provider9 = DoubleCheck.provider(SysUIConcurrencyModule_ProvideLongRunningExecutorFactory.create(provider8));
            this.provideLongRunningExecutorProvider = provider9;
            this.recordingServiceProvider = RecordingService_Factory.create(this.recordingControllerProvider, provider9, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, DaggerReferenceGlobalRootComponent.this.provideNotificationManagerProvider, this.provideUserTrackerProvider, this.keyguardDismissUtilProvider);
            this.screenshotSmartActionsProvider = DoubleCheck.provider(ScreenshotSmartActions_Factory.create(ReferenceScreenshotModule_ProvidesScrnshtNotifSmartActionsProviderFactory.create()));
            this.screenshotNotificationsControllerProvider = ScreenshotNotificationsController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideWindowManagerProvider);
            this.scrollCaptureClientProvider = ScrollCaptureClient_Factory.create(DaggerReferenceGlobalRootComponent.this.provideIWindowManagerProvider, this.provideBackgroundExecutorProvider, DaggerReferenceGlobalRootComponent.this.contextProvider);
            this.imageCaptureImplProvider = DoubleCheck.provider(ImageCaptureImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.provideDisplayManagerProvider, DaggerReferenceGlobalRootComponent.this.provideIActivityTaskManagerProvider, this.bgDispatcherProvider));
            this.imageTileSetProvider = ImageTileSet_Factory.create(GlobalConcurrencyModule_ProvideHandlerFactory.create());
            this.scrollCaptureControllerProvider = ScrollCaptureController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideBackgroundExecutorProvider, this.scrollCaptureClientProvider, this.imageTileSetProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider);
            this.screenshotControllerProvider = ScreenshotController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.featureFlagsDebugProvider, this.screenshotSmartActionsProvider, this.screenshotNotificationsControllerProvider, this.scrollCaptureClientProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, this.imageExporterProvider, this.imageCaptureImplProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.scrollCaptureControllerProvider, DaggerReferenceGlobalRootComponent.this.provideIStatusBarServiceProvider, this.longScreenshotDataProvider, DaggerReferenceGlobalRootComponent.this.provideActivityManagerProvider, this.timeoutHandlerProvider, this.broadcastSenderProvider, ReferenceScreenshotModule_ProvidesScrnshtNotifSmartActionsProviderFactory.create(), this.actionIntentExecutorProvider, DaggerReferenceGlobalRootComponent.this.provideUserManagerProvider);
            Provider<ScreenshotPolicyImpl> provider10 = DoubleCheck.provider(ScreenshotPolicyImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideUserManagerProvider, DaggerReferenceGlobalRootComponent.this.provideIActivityTaskManagerProvider, this.bgDispatcherProvider));
            this.screenshotPolicyImplProvider = provider10;
            this.requestProcessorProvider = DoubleCheck.provider(RequestProcessor_Factory.create(this.imageCaptureImplProvider, provider10, this.featureFlagsDebugProvider, this.applicationScopeProvider));
            this.takeScreenshotServiceProvider = TakeScreenshotService_Factory.create(this.screenshotControllerProvider, DaggerReferenceGlobalRootComponent.this.provideUserManagerProvider, DaggerReferenceGlobalRootComponent.this.provideDevicePolicyManagerProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, this.screenshotNotificationsControllerProvider, DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideBackgroundExecutorProvider, this.featureFlagsDebugProvider, this.requestProcessorProvider);
            this.screenshotProxyServiceProvider = ScreenshotProxyService_Factory.create(this.shadeExpansionStateManagerProvider, this.optionalOfCentralSurfacesProvider, this.mainDispatcherProvider);
            this.mapOfClassOfAndProviderOfServiceProvider = MapProviderFactory.builder(10).put(DozeService.class, this.dozeServiceProvider).put(ImageWallpaper.class, this.imageWallpaperProvider).put(KeyguardService.class, this.keyguardServiceProvider).put(DreamOverlayService.class, this.dreamOverlayServiceProvider).put(NotificationListenerWithPlugins.class, this.notificationListenerWithPluginsProvider).put(SystemUIService.class, this.systemUIServiceProvider).put(SystemUIAuxiliaryDumpService.class, this.systemUIAuxiliaryDumpServiceProvider).put(RecordingService.class, this.recordingServiceProvider).put(TakeScreenshotService.class, this.takeScreenshotServiceProvider).put(ScreenshotProxyService.class, this.screenshotProxyServiceProvider).build();
            this.overviewProxyRecentsImplProvider = DoubleCheck.provider(OverviewProxyRecentsImpl_Factory.create(this.optionalOfCentralSurfacesProvider, this.overviewProxyServiceProvider));
            this.mapOfClassOfAndProviderOfRecentsImplementationProvider = MapProviderFactory.builder(1).put(OverviewProxyRecentsImpl.class, this.overviewProxyRecentsImplProvider).build();
            this.actionProxyReceiverProvider = ActionProxyReceiver_Factory.create(this.optionalOfCentralSurfacesProvider, this.provideActivityManagerWrapperProvider, this.screenshotSmartActionsProvider);
            this.deleteScreenshotReceiverProvider = DeleteScreenshotReceiver_Factory.create(this.screenshotSmartActionsProvider, this.provideBackgroundExecutorProvider);
            this.smartActionsReceiverProvider = SmartActionsReceiver_Factory.create(this.screenshotSmartActionsProvider);
            MediaOutputBroadcastDialogFactory_Factory create6 = MediaOutputBroadcastDialogFactory_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideMediaSessionManagerProvider, this.provideLocalBluetoothControllerProvider, this.provideActivityStarterProvider, this.broadcastSenderProvider, this.notifPipelineProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, this.provideDialogLaunchAnimatorProvider, this.providesNearbyMediaDevicesManagerProvider, DaggerReferenceGlobalRootComponent.this.provideAudioManagerProvider, DaggerReferenceGlobalRootComponent.this.providePowerExemptionManagerProvider, DaggerReferenceGlobalRootComponent.this.provideKeyguardManagerProvider, this.featureFlagsDebugProvider);
            this.mediaOutputBroadcastDialogFactoryProvider = create6;
            this.mediaOutputDialogReceiverProvider = MediaOutputDialogReceiver_Factory.create(this.mediaOutputDialogFactoryProvider, create6);
            this.volumePanelDialogReceiverProvider = VolumePanelDialogReceiver_Factory.create(this.volumePanelFactoryProvider);
            this.peopleSpaceWidgetPinnedReceiverProvider = PeopleSpaceWidgetPinnedReceiver_Factory.create(this.peopleSpaceWidgetManagerProvider);
            this.peopleSpaceWidgetProvider = PeopleSpaceWidgetProvider_Factory.create(this.peopleSpaceWidgetManagerProvider);
            MapProviderFactory build = MapProviderFactory.builder(8).put(ActionProxyReceiver.class, this.actionProxyReceiverProvider).put(DeleteScreenshotReceiver.class, this.deleteScreenshotReceiverProvider).put(SmartActionsReceiver.class, this.smartActionsReceiverProvider).put(MediaOutputDialogReceiver.class, this.mediaOutputDialogReceiverProvider).put(VolumePanelDialogReceiver.class, this.volumePanelDialogReceiverProvider).put(PeopleSpaceWidgetPinnedReceiver.class, this.peopleSpaceWidgetPinnedReceiverProvider).put(PeopleSpaceWidgetProvider.class, this.peopleSpaceWidgetProvider).put(GuestResetOrExitSessionReceiver.class, this.guestResetOrExitSessionReceiverProvider).build();
            this.mapOfClassOfAndProviderOfBroadcastReceiverProvider = build;
            DelegateFactory.setDelegate(this.contextComponentResolverProvider, DoubleCheck.provider(ContextComponentResolver_Factory.create(this.mapOfClassOfAndProviderOfActivityProvider, this.mapOfClassOfAndProviderOfServiceProvider, this.mapOfClassOfAndProviderOfRecentsImplementationProvider, build)));
            this.unfoldLatencyTrackerProvider = DoubleCheck.provider(UnfoldLatencyTracker_Factory.create(DaggerReferenceGlobalRootComponent.this.provideLatencyTrackerProvider, DaggerReferenceGlobalRootComponent.this.provideDeviceStateManagerProvider, DaggerReferenceGlobalRootComponent.this.unfoldTransitionProgressProvider, DaggerReferenceGlobalRootComponent.this.provideUiBackgroundExecutorProvider, DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideContentResolverProvider, DaggerReferenceGlobalRootComponent.this.screenLifecycleProvider));
            this.flashlightControllerImplProvider = DoubleCheck.provider(FlashlightControllerImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, DaggerReferenceGlobalRootComponent.this.provideCameraManagerProvider, this.provideBackgroundExecutorProvider, this.secureSettingsImplProvider, this.broadcastSenderProvider, DaggerReferenceGlobalRootComponent.this.providePackageManagerProvider));
            this.provideNightDisplayListenerProvider = NightDisplayListenerModule_ProvideNightDisplayListenerFactory.create(nightDisplayListenerModule, DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideBgHandlerProvider);
            this.reduceBrightColorsControllerProvider = DoubleCheck.provider(ReduceBrightColorsController_Factory.create(this.provideUserTrackerProvider, this.provideBgHandlerProvider, DaggerReferenceGlobalRootComponent.this.provideColorDisplayManagerProvider, this.secureSettingsImplProvider));
            this.managedProfileControllerImplProvider = DoubleCheck.provider(ManagedProfileControllerImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.provideUserTrackerProvider, DaggerReferenceGlobalRootComponent.this.provideUserManagerProvider));
            this.accessibilityControllerProvider = DoubleCheck.provider(AccessibilityController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider));
            this.tunablePaddingServiceProvider = DoubleCheck.provider(TunablePadding_TunablePaddingService_Factory.create(this.tunerServiceImplProvider));
            this.uiOffloadThreadProvider = DoubleCheck.provider(UiOffloadThread_Factory.create());
        }

        public final void initialize8(LeakModule leakModule, NightDisplayListenerModule nightDisplayListenerModule, SharedLibraryModule sharedLibraryModule, KeyguardModule keyguardModule, SysUIUnfoldModule sysUIUnfoldModule, ShellInterface shellInterface, Optional<Pip> optional, Optional<SplitScreen> optional2, Optional<OneHanded> optional3, Optional<Bubbles> optional4, Optional<TaskViewFactory> optional5, ShellTransitions shellTransitions, Optional<StartingSurface> optional6, Optional<DisplayAreaHelper> optional7, Optional<RecentTasks> optional8, Optional<BackAnimation> optional9, Optional<DesktopMode> optional10) {
            this.groupExpansionManagerImplProvider = DoubleCheck.provider(GroupExpansionManagerImpl_Factory.create(this.provideGroupMembershipManagerProvider));
            this.statusBarRemoteInputCallbackProvider = DoubleCheck.provider(StatusBarRemoteInputCallback_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.groupExpansionManagerImplProvider, this.notificationLockscreenUserManagerImplProvider, this.keyguardStateControllerImplProvider, this.statusBarStateControllerImplProvider, this.statusBarKeyguardViewManagerProvider, this.provideActivityStarterProvider, this.shadeControllerImplProvider, this.provideCommandQueueProvider, this.actionClickLoggerProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider));
            this.remoteInputQuickSettingsDisablerProvider = DoubleCheck.provider(RemoteInputQuickSettingsDisabler_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideCommandQueueProvider, this.configurationControllerImplProvider));
            this.clockManagerProvider = DoubleCheck.provider(ClockManager_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.providerLayoutInflaterProvider, DaggerReferenceGlobalRootComponent.this.providesPluginManagerProvider, this.sysuiColorExtractorProvider, this.dockManagerImplProvider, this.provideUserTrackerProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider));
            this.dependencyProvider = DoubleCheck.provider(Dependency_Factory.create(DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.provideActivityStarterProvider, this.broadcastDispatcherProvider, this.asyncSensorManagerProvider, this.bluetoothControllerImplProvider, this.locationControllerImplProvider, this.rotationLockControllerImplProvider, this.zenModeControllerImplProvider, this.hdmiCecSetMenuLanguageHelperProvider, this.hotspotControllerImplProvider, this.castControllerImplProvider, this.flashlightControllerImplProvider, this.userSwitcherControllerProvider, this.userInfoControllerImplProvider, this.keyguardStateControllerImplProvider, this.keyguardUpdateMonitorProvider, this.provideNightDisplayListenerProvider, this.reduceBrightColorsControllerProvider, this.managedProfileControllerImplProvider, this.nextAlarmControllerImplProvider, this.provideDataSaverControllerProvider, this.accessibilityControllerProvider, this.bindDeviceProvisionedControllerProvider, DaggerReferenceGlobalRootComponent.this.providesPluginManagerProvider, this.assistManagerProvider, this.securityControllerImplProvider, this.providesLeakDetectorProvider, this.leakReporterProvider, this.garbageMonitorProvider, this.tunerServiceImplProvider, this.notificationShadeWindowControllerImplProvider, this.statusBarWindowControllerProvider, this.darkIconDispatcherImplProvider, this.configurationControllerImplProvider, this.statusBarIconControllerImplProvider, DaggerReferenceGlobalRootComponent.this.screenLifecycleProvider, this.wakefulnessLifecycleProvider, this.fragmentServiceProvider, this.extensionControllerImplProvider, DaggerReferenceGlobalRootComponent.this.pluginDependencyProvider, this.provideLocalBluetoothControllerProvider, this.volumeDialogControllerImplProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.accessibilityManagerWrapperProvider, this.sysuiColorExtractorProvider, this.tunablePaddingServiceProvider, this.foregroundServiceControllerProvider, this.uiOffloadThreadProvider, this.powerNotificationWarningsProvider, this.lightBarControllerProvider, DaggerReferenceGlobalRootComponent.this.provideIWindowManagerProvider, this.overviewProxyServiceProvider, this.navigationModeControllerProvider, this.accessibilityButtonModeObserverProvider, this.accessibilityButtonTargetsObserverProvider, this.enhancedEstimatesImplProvider, this.vibratorHelperProvider, DaggerReferenceGlobalRootComponent.this.provideIStatusBarServiceProvider, DaggerReferenceGlobalRootComponent.this.provideDisplayMetricsProvider, this.lockscreenGestureLoggerProvider, this.shadeControllerImplProvider, this.statusBarRemoteInputCallbackProvider, this.appOpsControllerImplProvider, this.navigationBarControllerProvider, this.accessibilityFloatingMenuControllerProvider, this.statusBarStateControllerImplProvider, this.notificationLockscreenUserManagerImplProvider, this.provideNotificationGutsManagerProvider, this.provideNotificationMediaManagerProvider, this.provideNotificationRemoteInputManagerProvider, this.smartReplyConstantsProvider, this.notificationListenerProvider, this.provideNotificationLoggerProvider, this.keyguardDismissUtilProvider, this.provideSmartReplyControllerProvider, this.remoteInputQuickSettingsDisablerProvider, DaggerReferenceGlobalRootComponent.this.provideSensorPrivacyManagerProvider, this.autoHideControllerProvider, this.privacyItemControllerProvider, this.provideBgLooperProvider, this.provideBgHandlerProvider, GlobalConcurrencyModule_ProvideMainLooperFactory.create(), DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.provideTimeTickHandlerProvider, this.provideLeakReportEmailProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.provideBackgroundExecutorProvider, this.clockManagerProvider, this.provideActivityManagerWrapperProvider, this.provideDevicePolicyManagerWrapperProvider, DaggerReferenceGlobalRootComponent.this.providePackageManagerWrapperProvider, this.provideSensorPrivacyControllerProvider, this.dockManagerImplProvider, DaggerReferenceGlobalRootComponent.this.provideINotificationManagerProvider, this.provideSysUiStateProvider, DaggerReferenceGlobalRootComponent.this.provideAlarmManagerProvider, this.keyguardSecurityModelProvider, this.dozeParametersProvider, FrameworkServicesModule_ProvideIWallPaperManagerFactory.create(), this.provideCommandQueueProvider, this.recordingControllerProvider, this.protoTracerProvider, this.mediaOutputDialogFactoryProvider, this.deviceConfigProxyProvider, this.telephonyListenerManagerProvider, this.systemStatusAnimationSchedulerProvider, this.privacyDotViewControllerProvider, this.factoryProvider13, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, this.statusBarContentInsetsProvider, this.internetDialogFactoryProvider, this.featureFlagsDebugProvider, this.notificationSectionsManagerProvider, this.screenOffAnimationControllerProvider, this.ambientStateProvider, this.provideGroupMembershipManagerProvider, this.groupExpansionManagerImplProvider, this.systemUIDialogManagerProvider, this.provideDialogLaunchAnimatorProvider, this.provideUserTrackerProvider));
            Provider<MediaMuteAwaitConnectionCli> provider = DoubleCheck.provider(MediaMuteAwaitConnectionCli_Factory.create(this.commandRegistryProvider, DaggerReferenceGlobalRootComponent.this.contextProvider));
            this.mediaMuteAwaitConnectionCliProvider = provider;
            this.providesMediaMuteAwaitConnectionCliProvider = DoubleCheck.provider(MediaModule_ProvidesMediaMuteAwaitConnectionCliFactory.create(this.mediaFlagsProvider, provider));
            this.notificationChannelsProvider = NotificationChannels_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider);
            this.provideClockInfoListProvider = ClockInfoModule_ProvideClockInfoListFactory.create(this.clockManagerProvider);
            this.homeControlsKeyguardQuickAffordanceConfigProvider = DoubleCheck.provider(HomeControlsKeyguardQuickAffordanceConfig_Factory.create(DaggerReferenceGlobalRootComponent.this.provideApplicationContextProvider, this.controlsComponentProvider));
            this.provideQuickAccessWalletClientProvider = DoubleCheck.provider(WalletModule_ProvideQuickAccessWalletClientFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideBackgroundExecutorProvider));
            this.quickAccessWalletControllerProvider = DoubleCheck.provider(QuickAccessWalletController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.provideBackgroundExecutorProvider, this.secureSettingsImplProvider, this.provideQuickAccessWalletClientProvider, this.bindSystemClockProvider));
            this.quickAccessWalletKeyguardQuickAffordanceConfigProvider = DoubleCheck.provider(QuickAccessWalletKeyguardQuickAffordanceConfig_Factory.create(DaggerReferenceGlobalRootComponent.this.provideApplicationContextProvider, this.quickAccessWalletControllerProvider, this.provideActivityStarterProvider));
            this.qRCodeScannerControllerProvider = DoubleCheck.provider(QRCodeScannerController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideBackgroundExecutorProvider, this.secureSettingsImplProvider, this.deviceConfigProxyProvider, this.provideUserTrackerProvider));
            Provider<QrCodeScannerKeyguardQuickAffordanceConfig> provider2 = DoubleCheck.provider(QrCodeScannerKeyguardQuickAffordanceConfig_Factory.create(DaggerReferenceGlobalRootComponent.this.provideApplicationContextProvider, this.qRCodeScannerControllerProvider));
            this.qrCodeScannerKeyguardQuickAffordanceConfigProvider = provider2;
            this.keyguardQuickAffordanceRegistryImplProvider = KeyguardQuickAffordanceRegistryImpl_Factory.create(this.homeControlsKeyguardQuickAffordanceConfigProvider, this.quickAccessWalletKeyguardQuickAffordanceConfigProvider, provider2);
            this.keyguardQuickAffordanceLocalUserSelectionManagerProvider = DoubleCheck.provider(KeyguardQuickAffordanceLocalUserSelectionManager_Factory.create(DaggerReferenceGlobalRootComponent.this.provideApplicationContextProvider, this.userFileManagerImplProvider, this.provideUserTrackerProvider, this.broadcastDispatcherProvider));
            KeyguardQuickAffordanceProviderClientFactoryImpl_Factory create = KeyguardQuickAffordanceProviderClientFactoryImpl_Factory.create(this.provideUserTrackerProvider, this.bgDispatcherProvider);
            this.keyguardQuickAffordanceProviderClientFactoryImplProvider = create;
            this.keyguardQuickAffordanceRemoteUserSelectionManagerProvider = DoubleCheck.provider(KeyguardQuickAffordanceRemoteUserSelectionManager_Factory.create(this.applicationScopeProvider, this.provideUserTrackerProvider, create, UserModule_ProvideUserHandleFactory.create()));
            this.keyguardQuickAffordanceLegacySettingSyncerProvider = DoubleCheck.provider(KeyguardQuickAffordanceLegacySettingSyncer_Factory.create(this.applicationScopeProvider, this.bgDispatcherProvider, this.secureSettingsImplProvider, this.keyguardQuickAffordanceLocalUserSelectionManagerProvider));
            this.doNotDisturbQuickAffordanceConfigProvider = DoubleCheck.provider(DoNotDisturbQuickAffordanceConfig_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.zenModeControllerImplProvider, this.secureSettingsImplProvider, this.provideUserTrackerProvider, this.bgDispatcherProvider));
            this.flashlightQuickAffordanceConfigProvider = DoubleCheck.provider(FlashlightQuickAffordanceConfig_Factory.create(DaggerReferenceGlobalRootComponent.this.provideApplicationContextProvider, this.flashlightControllerImplProvider));
            Provider<CameraQuickAffordanceConfig> provider3 = DoubleCheck.provider(CameraQuickAffordanceConfig_Factory.create(DaggerReferenceGlobalRootComponent.this.provideApplicationContextProvider, this.cameraGestureHelperProvider));
            this.cameraQuickAffordanceConfigProvider = provider3;
            this.quickAffordanceConfigsProvider = KeyguardDataQuickAffordanceModule_Companion_QuickAffordanceConfigsFactory.create(this.doNotDisturbQuickAffordanceConfigProvider, this.flashlightQuickAffordanceConfigProvider, this.homeControlsKeyguardQuickAffordanceConfigProvider, this.quickAccessWalletKeyguardQuickAffordanceConfigProvider, this.qrCodeScannerKeyguardQuickAffordanceConfigProvider, provider3);
            this.setOfKeyguardQuickAffordanceConfigProvider = SetFactory.builder(0, 1).addCollectionProvider(this.quickAffordanceConfigsProvider).build();
            this.keyguardQuickAffordanceRepositoryProvider = DoubleCheck.provider(KeyguardQuickAffordanceRepository_Factory.create(DaggerReferenceGlobalRootComponent.this.provideApplicationContextProvider, this.applicationScopeProvider, this.keyguardQuickAffordanceLocalUserSelectionManagerProvider, this.keyguardQuickAffordanceRemoteUserSelectionManagerProvider, this.provideUserTrackerProvider, this.keyguardQuickAffordanceLegacySettingSyncerProvider, this.setOfKeyguardQuickAffordanceConfigProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, UserModule_ProvideUserHandleFactory.create()));
            this.keyguardQuickAffordanceInteractorProvider = DoubleCheck.provider(KeyguardQuickAffordanceInteractor_Factory.create(this.keyguardInteractorProvider, this.keyguardQuickAffordanceRegistryImplProvider, DaggerReferenceGlobalRootComponent.this.provideLockPatternUtilsProvider, this.keyguardStateControllerImplProvider, this.provideUserTrackerProvider, this.provideActivityStarterProvider, this.featureFlagsDebugProvider, this.keyguardQuickAffordanceRepositoryProvider, this.provideDialogLaunchAnimatorProvider));
            Provider<KeyguardBottomAreaInteractor> provider4 = DoubleCheck.provider(KeyguardBottomAreaInteractor_Factory.create(this.keyguardRepositoryImplProvider));
            this.keyguardBottomAreaInteractorProvider = provider4;
            this.keyguardBottomAreaViewModelProvider = KeyguardBottomAreaViewModel_Factory.create(this.keyguardInteractorProvider, this.keyguardQuickAffordanceInteractorProvider, provider4, BurnInHelperWrapper_Factory.create());
            this.provideKeyguardClockLogProvider = DoubleCheck.provider(LogModule_ProvideKeyguardClockLogFactory.create(this.logBufferFactoryProvider));
            this.clockEventControllerProvider = ClockEventController_Factory.create(this.keyguardInteractorProvider, this.keyguardTransitionInteractorProvider, this.broadcastDispatcherProvider, this.provideBatteryControllerProvider, this.keyguardUpdateMonitorProvider, this.configurationControllerImplProvider, DaggerReferenceGlobalRootComponent.this.provideResourcesProvider, DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.provideBackgroundExecutorProvider, this.provideKeyguardClockLogProvider, this.featureFlagsDebugProvider);
            this.getClockRegistryProvider = DoubleCheck.provider(ClockRegistryModule_GetClockRegistryFactory.create(DaggerReferenceGlobalRootComponent.this.provideApplicationContextProvider, DaggerReferenceGlobalRootComponent.this.providesPluginManagerProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.featureFlagsDebugProvider, DaggerReferenceGlobalRootComponent.this.provideResourcesProvider, DaggerReferenceGlobalRootComponent.this.providerLayoutInflaterProvider));
            KeyguardPreviewRenderer_Factory create2 = KeyguardPreviewRenderer_Factory.create(DaggerReferenceGlobalRootComponent.this.provideApplicationContextProvider, this.mainDispatcherProvider, this.keyguardBottomAreaViewModelProvider, DaggerReferenceGlobalRootComponent.this.provideDisplayManagerProvider, DaggerReferenceGlobalRootComponent.this.provideWindowManagerProvider, this.clockEventControllerProvider, this.getClockRegistryProvider, this.broadcastDispatcherProvider);
            this.keyguardPreviewRendererProvider = create2;
            Provider<KeyguardPreviewRendererFactory> create3 = KeyguardPreviewRendererFactory_Impl.create(create2);
            this.keyguardPreviewRendererFactoryProvider = create3;
            this.keyguardRemotePreviewManagerProvider = DoubleCheck.provider(KeyguardRemotePreviewManager_Factory.create(create3, this.mainDispatcherProvider, this.provideBgHandlerProvider));
            this.setDisplayAreaHelperProvider = InstanceFactory.create(optional7);
            this.seenNotificationsProviderImplProvider = DoubleCheck.provider(SeenNotificationsProviderImpl_Factory.create());
            this.sectionStyleProvider = DoubleCheck.provider(SectionStyleProvider_Factory.create());
            this.providesAlertingHeaderNodeControllerProvider = NotificationSectionHeadersModule_ProvidesAlertingHeaderNodeControllerFactory.create(this.providesAlertingHeaderSubcomponentProvider);
            this.providesSilentHeaderNodeControllerProvider = NotificationSectionHeadersModule_ProvidesSilentHeaderNodeControllerFactory.create(this.providesSilentHeaderSubcomponentProvider);
            this.providesIncomingHeaderNodeControllerProvider = NotificationSectionHeadersModule_ProvidesIncomingHeaderNodeControllerFactory.create(this.providesIncomingHeaderSubcomponentProvider);
            this.providesPeopleHeaderNodeControllerProvider = NotificationSectionHeadersModule_ProvidesPeopleHeaderNodeControllerFactory.create(this.providesPeopleHeaderSubcomponentProvider);
            this.debugModeFilterProvider = DoubleCheck.provider(DebugModeFilterProvider_Factory.create(this.commandRegistryProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
            this.notifUiAdjustmentProvider = DoubleCheck.provider(NotifUiAdjustmentProvider_Factory.create(DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.secureSettingsImplProvider, this.notificationLockscreenUserManagerImplProvider, this.sectionStyleProvider));
            this.remoteInputNotificationRebuilderProvider = DoubleCheck.provider(RemoteInputNotificationRebuilder_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider));
            this.shadeEventCoordinatorLoggerProvider = ShadeEventCoordinatorLogger_Factory.create(this.provideNotificationsLogBufferProvider);
            this.shadeEventCoordinatorProvider = DoubleCheck.provider(ShadeEventCoordinator_Factory.create(DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.shadeEventCoordinatorLoggerProvider));
            this.optionalOfBcSmartspaceDataPluginProvider = DaggerReferenceGlobalRootComponent.m2000$$Nest$smabsentJdkOptionalProvider();
            this.lockscreenSmartspaceControllerProvider = DoubleCheck.provider(LockscreenSmartspaceController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.featureFlagsDebugProvider, DaggerReferenceGlobalRootComponent.this.provideSmartspaceManagerProvider, this.provideActivityStarterProvider, this.falsingManagerProxyProvider, this.secureSettingsImplProvider, this.provideUserTrackerProvider, DaggerReferenceGlobalRootComponent.this.provideContentResolverProvider, this.configurationControllerImplProvider, this.statusBarStateControllerImplProvider, this.bindDeviceProvisionedControllerProvider, this.keyguardBypassControllerProvider, DaggerReferenceGlobalRootComponent.this.provideExecutionProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.provideBackgroundExecutorProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.optionalOfBcSmartspaceDataPluginProvider));
            this.provideAllowNotificationLongPressProvider = DoubleCheck.provider(ReferenceSystemUIModule_ProvideAllowNotificationLongPressFactory.create());
            this.qSTileHostProvider = new DelegateFactory();
            Provider<LogBuffer> provider5 = DoubleCheck.provider(LogModule_ProvideQuickSettingsLogBufferFactory.create(this.logBufferFactoryProvider));
            this.provideQuickSettingsLogBufferProvider = provider5;
            this.qSLoggerProvider = QSLogger_Factory.create(provider5);
            this.customTileStatePersisterProvider = CustomTileStatePersister_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider);
            this.tileServicesProvider = DoubleCheck.provider(TileServices_Factory.create(this.qSTileHostProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.broadcastDispatcherProvider, this.provideUserTrackerProvider, this.keyguardStateControllerImplProvider, this.provideCommandQueueProvider));
            this.builderProvider5 = CustomTile_Builder_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.customTileStatePersisterProvider, this.tileServicesProvider);
            this.wifiTileProvider = WifiTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.networkControllerImplProvider, this.provideAccessPointControllerImplProvider, this.keyguardStateControllerImplProvider);
            this.internetTileProvider = InternetTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.networkControllerImplProvider, this.provideAccessPointControllerImplProvider, this.internetDialogFactoryProvider, this.keyguardStateControllerImplProvider);
            this.bluetoothTileProvider = BluetoothTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.bluetoothControllerImplProvider);
            this.cellularTileProvider = CellularTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.networkControllerImplProvider, this.keyguardStateControllerImplProvider);
            this.dndTileProvider = DndTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.zenModeControllerImplProvider, DaggerReferenceGlobalRootComponent.this.provideSharePreferencesProvider, this.secureSettingsImplProvider, this.provideDialogLaunchAnimatorProvider);
            this.colorInversionTileProvider = ColorInversionTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.provideUserTrackerProvider, this.secureSettingsImplProvider);
            this.airplaneModeTileProvider = AirplaneModeTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.broadcastDispatcherProvider, DaggerReferenceGlobalRootComponent.this.provideConnectivityManagagerProvider, this.globalSettingsImplProvider, this.provideUserTrackerProvider, this.keyguardStateControllerImplProvider);
            this.workModeTileProvider = WorkModeTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.managedProfileControllerImplProvider);
            this.rotationLockTileProvider = RotationLockTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.rotationLockControllerImplProvider, DaggerReferenceGlobalRootComponent.this.provideSensorPrivacyManagerProvider, this.provideBatteryControllerProvider, this.secureSettingsImplProvider);
            this.flashlightTileProvider = FlashlightTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.flashlightControllerImplProvider);
            this.locationTileProvider = LocationTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.locationControllerImplProvider, this.keyguardStateControllerImplProvider);
            this.castTileProvider = CastTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.castControllerImplProvider, this.keyguardStateControllerImplProvider, this.networkControllerImplProvider, this.hotspotControllerImplProvider, this.provideDialogLaunchAnimatorProvider);
            this.hotspotTileProvider = HotspotTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.hotspotControllerImplProvider, this.provideDataSaverControllerProvider, this.keyguardStateControllerImplProvider);
            this.batterySaverTileProvider = BatterySaverTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.provideBatteryControllerProvider, this.secureSettingsImplProvider);
            this.dataSaverTileProvider = DataSaverTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.provideDataSaverControllerProvider, this.provideDialogLaunchAnimatorProvider);
            this.builderProvider6 = NightDisplayListenerModule_Builder_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideBgHandlerProvider);
            this.nightDisplayTileProvider = NightDisplayTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.locationControllerImplProvider, DaggerReferenceGlobalRootComponent.this.provideColorDisplayManagerProvider, this.builderProvider6);
            this.nfcTileProvider = NfcTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.broadcastDispatcherProvider);
            this.memoryTileProvider = GarbageMonitor_MemoryTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.garbageMonitorProvider);
            this.uiModeNightTileProvider = UiModeNightTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.configurationControllerImplProvider, this.provideBatteryControllerProvider, this.locationControllerImplProvider);
            this.screenRecordTileProvider = ScreenRecordTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.featureFlagsDebugProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.recordingControllerProvider, this.keyguardDismissUtilProvider, this.keyguardStateControllerImplProvider, this.provideDialogLaunchAnimatorProvider);
            Provider<Boolean> provider6 = DoubleCheck.provider(QSFlagsModule_IsReduceBrightColorsAvailableFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider));
            this.isReduceBrightColorsAvailableProvider = provider6;
            this.reduceBrightColorsTileProvider = ReduceBrightColorsTile_Factory.create(provider6, this.reduceBrightColorsControllerProvider, this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider);
            this.cameraToggleTileProvider = CameraToggleTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.falsingManagerProxyProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.provideIndividualSensorPrivacyControllerProvider, this.keyguardStateControllerImplProvider);
            this.microphoneToggleTileProvider = MicrophoneToggleTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.falsingManagerProxyProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.provideIndividualSensorPrivacyControllerProvider, this.keyguardStateControllerImplProvider);
            this.deviceControlsTileProvider = DeviceControlsTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.controlsComponentProvider);
            this.alarmTileProvider = AlarmTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.provideUserTrackerProvider, this.nextAlarmControllerImplProvider);
            this.quickAccessWalletTileProvider = QuickAccessWalletTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.keyguardStateControllerImplProvider, DaggerReferenceGlobalRootComponent.this.providePackageManagerProvider, this.secureSettingsImplProvider, this.quickAccessWalletControllerProvider);
            this.qRCodeScannerTileProvider = QRCodeScannerTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.qRCodeScannerControllerProvider);
            this.oneHandedModeTileProvider = OneHandedModeTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.provideUserTrackerProvider, this.secureSettingsImplProvider);
            this.colorCorrectionTileProvider = ColorCorrectionTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.provideUserTrackerProvider, this.secureSettingsImplProvider);
            this.providesDreamSupportedProvider = DreamModule_ProvidesDreamSupportedFactory.create(DaggerReferenceGlobalRootComponent.this.provideResourcesProvider);
            this.providesDreamOnlyEnabledForDockUserProvider = DreamModule_ProvidesDreamOnlyEnabledForDockUserFactory.create(DaggerReferenceGlobalRootComponent.this.provideResourcesProvider);
            this.dreamTileProvider = DreamTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, DaggerReferenceGlobalRootComponent.this.provideIDreamManagerProvider, this.secureSettingsImplProvider, this.broadcastDispatcherProvider, this.provideUserTrackerProvider, this.providesDreamSupportedProvider, this.providesDreamOnlyEnabledForDockUserProvider);
            this.powerShareTileProvider = PowerShareTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.provideBatteryControllerProvider);
            this.caffeineTileProvider = CaffeineTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider);
            this.headsUpTileProvider = HeadsUpTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.globalSettingsImplProvider, this.provideUserTrackerProvider);
            this.syncTileProvider = SyncTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider);
            this.ambientDisplayTileProvider = AmbientDisplayTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.provideUserTrackerProvider, this.secureSettingsImplProvider);
            this.usbTetherTileProvider = UsbTetherTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider);
            this.aODTileProvider = AODTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.secureSettingsImplProvider, this.provideBatteryControllerProvider, this.provideUserTrackerProvider);
            this.vpnTileProvider = VpnTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider, this.securityControllerImplProvider, this.keyguardStateControllerImplProvider);
            this.liveDisplayTileProvider = LiveDisplayTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider);
            this.readingModeTileProvider = ReadingModeTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider);
        }

        public final void initialize9(LeakModule leakModule, NightDisplayListenerModule nightDisplayListenerModule, SharedLibraryModule sharedLibraryModule, KeyguardModule keyguardModule, SysUIUnfoldModule sysUIUnfoldModule, ShellInterface shellInterface, Optional<Pip> optional, Optional<SplitScreen> optional2, Optional<OneHanded> optional3, Optional<Bubbles> optional4, Optional<TaskViewFactory> optional5, ShellTransitions shellTransitions, Optional<StartingSurface> optional6, Optional<DisplayAreaHelper> optional7, Optional<RecentTasks> optional8, Optional<BackAnimation> optional9, Optional<DesktopMode> optional10) {
            AntiFlickerTile_Factory create = AntiFlickerTile_Factory.create(this.qSTileHostProvider, this.provideBgLooperProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.falsingManagerProxyProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, this.statusBarStateControllerImplProvider, this.provideActivityStarterProvider, this.qSLoggerProvider);
            this.antiFlickerTileProvider = create;
            this.qSFactoryImplProvider = DoubleCheck.provider(QSFactoryImpl_Factory.create(this.qSTileHostProvider, this.builderProvider5, this.wifiTileProvider, this.internetTileProvider, this.bluetoothTileProvider, this.cellularTileProvider, this.dndTileProvider, this.colorInversionTileProvider, this.airplaneModeTileProvider, this.workModeTileProvider, this.rotationLockTileProvider, this.flashlightTileProvider, this.locationTileProvider, this.castTileProvider, this.hotspotTileProvider, this.batterySaverTileProvider, this.dataSaverTileProvider, this.nightDisplayTileProvider, this.nfcTileProvider, this.memoryTileProvider, this.uiModeNightTileProvider, this.screenRecordTileProvider, this.reduceBrightColorsTileProvider, this.cameraToggleTileProvider, this.microphoneToggleTileProvider, this.deviceControlsTileProvider, this.alarmTileProvider, this.quickAccessWalletTileProvider, this.qRCodeScannerTileProvider, this.oneHandedModeTileProvider, this.colorCorrectionTileProvider, this.dreamTileProvider, this.powerShareTileProvider, this.caffeineTileProvider, this.headsUpTileProvider, this.syncTileProvider, this.ambientDisplayTileProvider, this.usbTetherTileProvider, this.aODTileProvider, this.vpnTileProvider, this.liveDisplayTileProvider, this.readingModeTileProvider, create));
            this.builderProvider7 = DoubleCheck.provider(AutoAddTracker_Builder_Factory.create(this.secureSettingsImplProvider, this.broadcastDispatcherProvider, this.qSTileHostProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.provideBackgroundExecutorProvider));
            this.deviceControlsControllerImplProvider = DoubleCheck.provider(DeviceControlsControllerImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.controlsComponentProvider, this.provideUserTrackerProvider, this.secureSettingsImplProvider));
            this.walletControllerImplProvider = DoubleCheck.provider(WalletControllerImpl_Factory.create(this.provideQuickAccessWalletClientProvider));
            this.safetyControllerProvider = SafetyController_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.providePackageManagerProvider, DaggerReferenceGlobalRootComponent.this.provideSafetyCenterManagerProvider, this.provideBgHandlerProvider);
            this.provideAutoTileManagerProvider = QSModule_ProvideAutoTileManagerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.builderProvider7, this.qSTileHostProvider, this.provideBgHandlerProvider, this.secureSettingsImplProvider, this.hotspotControllerImplProvider, this.provideDataSaverControllerProvider, this.managedProfileControllerImplProvider, this.provideNightDisplayListenerProvider, this.castControllerImplProvider, this.reduceBrightColorsControllerProvider, this.deviceControlsControllerImplProvider, this.walletControllerImplProvider, this.safetyControllerProvider, this.isReduceBrightColorsAvailableProvider);
            this.builderProvider8 = DoubleCheck.provider(TileServiceRequestController_Builder_Factory.create(this.provideCommandQueueProvider, this.commandRegistryProvider));
            this.packageManagerAdapterProvider = PackageManagerAdapter_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider);
            C0055TileLifecycleManager_Factory create2 = C0055TileLifecycleManager_Factory.create(DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, DaggerReferenceGlobalRootComponent.this.contextProvider, this.tileServicesProvider, this.packageManagerAdapterProvider, this.broadcastDispatcherProvider);
            this.tileLifecycleManagerProvider = create2;
            this.factoryProvider18 = TileLifecycleManager_Factory_Impl.create(create2);
            DelegateFactory.setDelegate(this.qSTileHostProvider, DoubleCheck.provider(QSTileHost_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.statusBarIconControllerImplProvider, this.qSFactoryImplProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, DaggerReferenceGlobalRootComponent.this.providesPluginManagerProvider, this.tunerServiceImplProvider, this.provideAutoTileManagerProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.optionalOfCentralSurfacesProvider, this.qSLoggerProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, this.provideUserTrackerProvider, this.secureSettingsImplProvider, this.customTileStatePersisterProvider, this.builderProvider8, this.factoryProvider18, this.userFileManagerImplProvider)));
            this.providesQSMediaHostProvider = DoubleCheck.provider(MediaModule_ProvidesQSMediaHostFactory.create(MediaHost_MediaHostStateHolder_Factory.create(), this.mediaHierarchyManagerProvider, this.mediaDataManagerProvider, this.mediaHostStatesManagerProvider));
            this.providesQuickQSMediaHostProvider = DoubleCheck.provider(MediaModule_ProvidesQuickQSMediaHostFactory.create(MediaHost_MediaHostStateHolder_Factory.create(), this.mediaHierarchyManagerProvider, this.mediaDataManagerProvider, this.mediaHostStatesManagerProvider));
            this.provideQSFragmentDisableLogBufferProvider = DoubleCheck.provider(LogModule_ProvideQSFragmentDisableLogBufferFactory.create(this.logBufferFactoryProvider));
            this.disableFlagsLoggerProvider = DoubleCheck.provider(DisableFlagsLogger_Factory.create());
            Provider<FgsManagerControllerImpl> provider = DoubleCheck.provider(FgsManagerControllerImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.provideBackgroundExecutorProvider, this.bindSystemClockProvider, DaggerReferenceGlobalRootComponent.this.provideIActivityManagerProvider, DaggerReferenceGlobalRootComponent.this.provideJobSchedulerProvider, DaggerReferenceGlobalRootComponent.this.providePackageManagerProvider, this.provideUserTrackerProvider, this.deviceConfigProxyProvider, this.provideDialogLaunchAnimatorProvider, this.broadcastDispatcherProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
            this.fgsManagerControllerImplProvider = provider;
            this.footerActionsControllerProvider = DoubleCheck.provider(FooterActionsController_Factory.create(provider));
            this.qSSecurityFooterUtilsProvider = DoubleCheck.provider(QSSecurityFooterUtils_Factory.create(DaggerReferenceGlobalRootComponent.this.provideApplicationContextProvider, DaggerReferenceGlobalRootComponent.this.provideDevicePolicyManagerProvider, this.provideUserTrackerProvider, DaggerReferenceGlobalRootComponent.this.provideMainHandlerProvider, this.provideActivityStarterProvider, this.securityControllerImplProvider, this.provideBgLooperProvider, this.provideDialogLaunchAnimatorProvider));
            this.securityRepositoryImplProvider = DoubleCheck.provider(SecurityRepositoryImpl_Factory.create(this.securityControllerImplProvider, this.bgDispatcherProvider));
            this.foregroundServicesRepositoryImplProvider = DoubleCheck.provider(ForegroundServicesRepositoryImpl_Factory.create(this.fgsManagerControllerImplProvider));
            this.userSwitcherRepositoryImplProvider = DoubleCheck.provider(UserSwitcherRepositoryImpl_Factory.create(DaggerReferenceGlobalRootComponent.this.provideApplicationContextProvider, this.provideBgHandlerProvider, this.bgDispatcherProvider, DaggerReferenceGlobalRootComponent.this.provideUserManagerProvider, this.provideUserTrackerProvider, this.userSwitcherControllerProvider, this.userInfoControllerImplProvider, this.globalSettingsImplProvider));
            this.footerActionsInteractorImplProvider = DoubleCheck.provider(FooterActionsInteractorImpl_Factory.create(this.provideActivityStarterProvider, DaggerReferenceGlobalRootComponent.this.provideMetricsLoggerProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, this.bindDeviceProvisionedControllerProvider, this.qSSecurityFooterUtilsProvider, this.fgsManagerControllerImplProvider, this.userInteractorProvider, this.securityRepositoryImplProvider, this.foregroundServicesRepositoryImplProvider, this.userSwitcherRepositoryImplProvider, this.broadcastDispatcherProvider, this.bgDispatcherProvider));
            this.isPMLiteEnabledProvider = DoubleCheck.provider(QSFlagsModule_IsPMLiteEnabledFactory.create(this.featureFlagsDebugProvider, this.globalSettingsImplProvider));
            this.factoryProvider19 = DoubleCheck.provider(FooterActionsViewModel_Factory_Factory.create(DaggerReferenceGlobalRootComponent.this.provideApplicationContextProvider, this.falsingManagerProxyProvider, this.footerActionsInteractorImplProvider, this.globalActionsDialogLiteProvider, this.isPMLiteEnabledProvider));
            this.notificationShelfComponentBuilderProvider = new Provider<NotificationShelfComponent.Builder>() { // from class: com.android.systemui.dagger.DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.13
                /* JADX DEBUG: Method merged with bridge method */
                /* renamed from: get */
                public NotificationShelfComponent.Builder m2235get() {
                    return new NotificationShelfComponentBuilder();
                }
            };
            SplitShadeOverScroller_Factory create3 = SplitShadeOverScroller_Factory.create(this.configurationControllerImplProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, DaggerReferenceGlobalRootComponent.this.contextProvider, this.scrimControllerProvider);
            this.splitShadeOverScrollerProvider = create3;
            this.factoryProvider20 = SplitShadeOverScroller_Factory_Impl.create(create3);
            this.scrimShadeTransitionControllerProvider = DoubleCheck.provider(ScrimShadeTransitionController_Factory.create(this.configurationControllerImplProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.scrimControllerProvider, DaggerReferenceGlobalRootComponent.this.provideResourcesProvider, this.statusBarStateControllerImplProvider, this.provideHeadsUpManagerPhoneProvider));
            this.shadeTransitionControllerProvider = DoubleCheck.provider(ShadeTransitionController_Factory.create(this.configurationControllerImplProvider, this.shadeExpansionStateManagerProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, DaggerReferenceGlobalRootComponent.this.contextProvider, this.factoryProvider20, NoOpOverScroller_Factory.create(), this.scrimShadeTransitionControllerProvider, this.statusBarStateControllerImplProvider));
            this.notificationStackSizeCalculatorProvider = DoubleCheck.provider(NotificationStackSizeCalculator_Factory.create(this.statusBarStateControllerImplProvider, this.lockscreenShadeTransitionControllerProvider, DaggerReferenceGlobalRootComponent.this.provideResourcesProvider));
            this.notificationTargetsHelperProvider = DoubleCheck.provider(NotificationTargetsHelper_Factory.create(this.featureFlagsDebugProvider));
            this.provideShadeLogBufferProvider = DoubleCheck.provider(LogModule_ProvideShadeLogBufferFactory.create(this.logBufferFactoryProvider));
            this.provideShadeHeightLogBufferProvider = DoubleCheck.provider(LogModule_ProvideShadeHeightLogBufferFactory.create(this.logBufferFactoryProvider));
            Provider<TableLogBuffer> provider2 = DoubleCheck.provider(StatusBarPipelineModule_Companion_ProvideAirplaneTableLogBufferFactory.create(this.tableLogBufferFactoryProvider));
            this.provideAirplaneTableLogBufferProvider = provider2;
            this.airplaneModeRepositoryImplProvider = DoubleCheck.provider(AirplaneModeRepositoryImpl_Factory.create(this.provideBgHandlerProvider, this.globalSettingsImplProvider, provider2, this.applicationScopeProvider));
            Provider<ConnectivitySlots> provider3 = DoubleCheck.provider(ConnectivitySlots_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider));
            this.connectivitySlotsProvider = provider3;
            Provider<ConnectivityRepositoryImpl> provider4 = DoubleCheck.provider(ConnectivityRepositoryImpl_Factory.create(provider3, DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.connectivityPipelineLoggerProvider, this.applicationScopeProvider, this.tunerServiceImplProvider));
            this.connectivityRepositoryImplProvider = provider4;
            Provider<AirplaneModeInteractor> provider5 = DoubleCheck.provider(AirplaneModeInteractor_Factory.create(this.airplaneModeRepositoryImplProvider, provider4));
            this.airplaneModeInteractorProvider = provider5;
            this.airplaneModeViewModelImplProvider = DoubleCheck.provider(AirplaneModeViewModelImpl_Factory.create(provider5, this.connectivityPipelineLoggerProvider, this.applicationScopeProvider));
            this.provideWifiTableLogBufferProvider = DoubleCheck.provider(StatusBarPipelineModule_Companion_ProvideWifiTableLogBufferFactory.create(this.tableLogBufferFactoryProvider));
            this.disabledWifiRepositoryProvider = DoubleCheck.provider(DisabledWifiRepository_Factory.create());
            this.factoryProvider21 = DoubleCheck.provider(WifiRepositoryImpl_Factory_Factory.create(this.broadcastDispatcherProvider, DaggerReferenceGlobalRootComponent.this.provideConnectivityManagagerProvider, this.connectivityPipelineLoggerProvider, this.provideWifiTableLogBufferProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.applicationScopeProvider));
            this.provideRealWifiRepositoryProvider = DoubleCheck.provider(StatusBarPipelineModule_Companion_ProvideRealWifiRepositoryFactory.create(DaggerReferenceGlobalRootComponent.this.provideWifiManagerProvider, this.disabledWifiRepositoryProvider, this.factoryProvider21));
            Provider<DemoModeWifiDataSource> provider6 = DoubleCheck.provider(DemoModeWifiDataSource_Factory.create(this.provideDemoModeControllerProvider, this.applicationScopeProvider));
            this.demoModeWifiDataSourceProvider = provider6;
            DemoWifiRepository_Factory create4 = DemoWifiRepository_Factory.create(provider6, this.applicationScopeProvider);
            this.demoWifiRepositoryProvider = create4;
            WifiRepositorySwitcher_Factory create5 = WifiRepositorySwitcher_Factory.create(this.provideRealWifiRepositoryProvider, create4, this.provideDemoModeControllerProvider, this.applicationScopeProvider);
            this.wifiRepositorySwitcherProvider = create5;
            this.wifiInteractorImplProvider = DoubleCheck.provider(WifiInteractorImpl_Factory.create(this.connectivityRepositoryImplProvider, create5));
            this.wifiConstantsProvider = DoubleCheck.provider(WifiConstants_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider));
            Provider<WifiViewModel> provider7 = DoubleCheck.provider(WifiViewModel_Factory.create(this.airplaneModeViewModelImplProvider, this.connectivityConstantsProvider, DaggerReferenceGlobalRootComponent.this.contextProvider, this.connectivityPipelineLoggerProvider, this.provideWifiTableLogBufferProvider, this.wifiInteractorImplProvider, this.applicationScopeProvider, this.statusBarPipelineFlagsProvider, this.wifiConstantsProvider));
            this.wifiViewModelProvider = provider7;
            this.wifiUiAdapterProvider = DoubleCheck.provider(WifiUiAdapter_Factory.create(this.statusBarIconControllerImplProvider, provider7, this.statusBarPipelineFlagsProvider));
            Provider<MobileContextProvider> provider8 = DoubleCheck.provider(MobileContextProvider_Factory.create(this.networkControllerImplProvider, DaggerReferenceGlobalRootComponent.this.dumpManagerProvider, this.provideDemoModeControllerProvider));
            this.mobileContextProvider = provider8;
            this.factoryProvider22 = DoubleCheck.provider(StatusBarIconController_TintedIconManager_Factory_Factory.create(this.statusBarPipelineFlagsProvider, this.wifiUiAdapterProvider, this.mobileUiAdapterProvider, provider8));
            this.privacyDialogControllerProvider = DoubleCheck.provider(PrivacyDialogController_Factory.create(DaggerReferenceGlobalRootComponent.this.providePermissionManagerProvider, DaggerReferenceGlobalRootComponent.this.providePackageManagerProvider, this.privacyItemControllerProvider, this.provideUserTrackerProvider, this.provideActivityStarterProvider, this.provideBackgroundExecutorProvider, DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider, this.privacyLoggerProvider, this.keyguardStateControllerImplProvider, this.appOpsControllerImplProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider));
            this.subscriptionManagerSlotIndexResolverProvider = DoubleCheck.provider(QSCarrierGroupController_SubscriptionManagerSlotIndexResolver_Factory.create());
            this.keyguardQsUserSwitchComponentFactoryProvider = new Provider<KeyguardQsUserSwitchComponent.Factory>() { // from class: com.android.systemui.dagger.DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.14
                /* JADX DEBUG: Method merged with bridge method */
                /* renamed from: get */
                public KeyguardQsUserSwitchComponent.Factory m2236get() {
                    return new KeyguardQsUserSwitchComponentFactory();
                }
            };
            this.keyguardUserSwitcherComponentFactoryProvider = new Provider<KeyguardUserSwitcherComponent.Factory>() { // from class: com.android.systemui.dagger.DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.15
                /* JADX DEBUG: Method merged with bridge method */
                /* renamed from: get */
                public KeyguardUserSwitcherComponent.Factory m2237get() {
                    return new KeyguardUserSwitcherComponentFactory();
                }
            };
            this.keyguardStatusBarViewComponentFactoryProvider = new Provider<KeyguardStatusBarViewComponent.Factory>() { // from class: com.android.systemui.dagger.DaggerReferenceGlobalRootComponent.ReferenceSysUIComponentImpl.16
                /* JADX DEBUG: Method merged with bridge method */
                /* renamed from: get */
                public KeyguardStatusBarViewComponent.Factory m2238get() {
                    return new KeyguardStatusBarViewComponentFactory();
                }
            };
            this.qsFrameTranslateImplProvider = DoubleCheck.provider(QsFrameTranslateImpl_Factory.create(this.centralSurfacesImplProvider));
            this.dreamingToLockscreenTransitionViewModelProvider = DoubleCheck.provider(DreamingToLockscreenTransitionViewModel_Factory.create(this.keyguardTransitionInteractorProvider));
            this.occludedToLockscreenTransitionViewModelProvider = DoubleCheck.provider(OccludedToLockscreenTransitionViewModel_Factory.create(this.keyguardTransitionInteractorProvider));
            this.notificationInsetsImplProvider = DoubleCheck.provider(NotificationInsetsImpl_Factory.create());
            this.statusBarLocationPublisherProvider = DoubleCheck.provider(StatusBarLocationPublisher_Factory.create());
            this.factoryProvider23 = DoubleCheck.provider(StatusBarIconController_DarkIconManager_Factory_Factory.create(this.statusBarPipelineFlagsProvider, this.wifiUiAdapterProvider, this.mobileContextProvider, this.mobileUiAdapterProvider, this.darkIconDispatcherImplProvider));
            this.provideCollapsedSbFragmentLogBufferProvider = DoubleCheck.provider(LogModule_ProvideCollapsedSbFragmentLogBufferFactory.create(this.logBufferFactoryProvider));
            this.setRecentTasksProvider = InstanceFactory.create(optional8);
            this.provideProximityCheckProvider = SensorModule_ProvideProximityCheckFactory.create(this.provideProximitySensorProvider, DaggerReferenceGlobalRootComponent.this.provideMainDelayableExecutorProvider);
            this.providesDreamOverlayNotificationCountProvider = DoubleCheck.provider(DreamModule_ProvidesDreamOverlayNotificationCountProviderFactory.create());
            this.dreamOverlayStatusBarItemsProvider = DoubleCheck.provider(DreamOverlayStatusBarItemsProvider_Factory.create(DaggerReferenceGlobalRootComponent.this.provideMainExecutorProvider));
            this.userSwitchDialogControllerProvider = DoubleCheck.provider(UserSwitchDialogController_Factory.create(this.adapterProvider, this.provideActivityStarterProvider, this.falsingManagerProxyProvider, this.provideDialogLaunchAnimatorProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider));
        }

        public void inject(ClockOptionsProvider clockOptionsProvider) {
            injectClockOptionsProvider(clockOptionsProvider);
        }

        @Override // com.android.systemui.dagger.SysUIComponent
        public void inject(SystemUIAppComponentFactoryBase systemUIAppComponentFactoryBase) {
            injectSystemUIAppComponentFactoryBase(systemUIAppComponentFactoryBase);
        }

        public void inject(CustomizationProvider customizationProvider) {
            injectCustomizationProvider(customizationProvider);
        }

        public void inject(KeyguardSliceProvider keyguardSliceProvider) {
            injectKeyguardSliceProvider(keyguardSliceProvider);
        }

        public void inject(PeopleProvider peopleProvider) {
            injectPeopleProvider(peopleProvider);
        }

        @CanIgnoreReturnValue
        public final ClockOptionsProvider injectClockOptionsProvider(ClockOptionsProvider clockOptionsProvider) {
            ClockOptionsProvider_MembersInjector.injectMClockInfosProvider(clockOptionsProvider, this.provideClockInfoListProvider);
            return clockOptionsProvider;
        }

        @CanIgnoreReturnValue
        public final CustomizationProvider injectCustomizationProvider(CustomizationProvider customizationProvider) {
            CustomizationProvider_MembersInjector.injectInteractor(customizationProvider, (KeyguardQuickAffordanceInteractor) this.keyguardQuickAffordanceInteractorProvider.get());
            CustomizationProvider_MembersInjector.injectPreviewManager(customizationProvider, (KeyguardRemotePreviewManager) this.keyguardRemotePreviewManagerProvider.get());
            return customizationProvider;
        }

        @CanIgnoreReturnValue
        public final KeyguardSliceProvider injectKeyguardSliceProvider(KeyguardSliceProvider keyguardSliceProvider) {
            KeyguardSliceProvider_MembersInjector.injectMDozeParameters(keyguardSliceProvider, (DozeParameters) this.dozeParametersProvider.get());
            KeyguardSliceProvider_MembersInjector.injectMZenModeController(keyguardSliceProvider, (ZenModeController) this.zenModeControllerImplProvider.get());
            KeyguardSliceProvider_MembersInjector.injectMNextAlarmController(keyguardSliceProvider, (NextAlarmController) this.nextAlarmControllerImplProvider.get());
            KeyguardSliceProvider_MembersInjector.injectMAlarmManager(keyguardSliceProvider, (AlarmManager) DaggerReferenceGlobalRootComponent.this.provideAlarmManagerProvider.get());
            KeyguardSliceProvider_MembersInjector.injectMContentResolver(keyguardSliceProvider, (ContentResolver) DaggerReferenceGlobalRootComponent.this.provideContentResolverProvider.get());
            KeyguardSliceProvider_MembersInjector.injectMMediaManager(keyguardSliceProvider, (NotificationMediaManager) this.provideNotificationMediaManagerProvider.get());
            KeyguardSliceProvider_MembersInjector.injectMStatusBarStateController(keyguardSliceProvider, (StatusBarStateController) this.statusBarStateControllerImplProvider.get());
            KeyguardSliceProvider_MembersInjector.injectMKeyguardBypassController(keyguardSliceProvider, (KeyguardBypassController) this.keyguardBypassControllerProvider.get());
            KeyguardSliceProvider_MembersInjector.injectMKeyguardUpdateMonitor(keyguardSliceProvider, (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get());
            KeyguardSliceProvider_MembersInjector.injectMUserTracker(keyguardSliceProvider, (UserTracker) this.provideUserTrackerProvider.get());
            return keyguardSliceProvider;
        }

        @CanIgnoreReturnValue
        public final PeopleProvider injectPeopleProvider(PeopleProvider peopleProvider) {
            PeopleProvider_MembersInjector.injectMPeopleSpaceWidgetManager(peopleProvider, (PeopleSpaceWidgetManager) this.peopleSpaceWidgetManagerProvider.get());
            return peopleProvider;
        }

        @CanIgnoreReturnValue
        public final SystemUIAppComponentFactoryBase injectSystemUIAppComponentFactoryBase(SystemUIAppComponentFactoryBase systemUIAppComponentFactoryBase) {
            SystemUIAppComponentFactoryBase_MembersInjector.injectSetComponentHelper(systemUIAppComponentFactoryBase, (ContextComponentHelper) this.contextComponentResolverProvider.get());
            return systemUIAppComponentFactoryBase;
        }

        public final KeyguardLogger keyguardLogger() {
            return new KeyguardLogger((LogBuffer) this.provideKeyguardLogBufferProvider.get());
        }

        @Override // com.android.systemui.dagger.SysUIComponent
        public BootCompleteCacheImpl provideBootCacheImpl() {
            return (BootCompleteCacheImpl) this.bootCompleteCacheImplProvider.get();
        }

        public final Object secureSettingsImpl() {
            return SecureSettingsImpl_Factory.newInstance((ContentResolver) DaggerReferenceGlobalRootComponent.this.provideContentResolverProvider.get());
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$WMComponentBuilder.class */
    public final class WMComponentBuilder implements WMComponent.Builder {
        public HandlerThread setShellMainThread;

        public WMComponentBuilder() {
        }

        @Override // com.android.systemui.dagger.WMComponent.Builder
        public WMComponent build() {
            return new WMComponentImpl(this.setShellMainThread);
        }

        /* JADX DEBUG: Method merged with bridge method */
        @Override // com.android.systemui.dagger.WMComponent.Builder
        public WMComponentBuilder setShellMainThread(HandlerThread handlerThread) {
            this.setShellMainThread = handlerThread;
            return this;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/dagger/DaggerReferenceGlobalRootComponent$WMComponentImpl.class */
    public final class WMComponentImpl implements WMComponent {
        public Provider<DockStateReader> dockStateReaderProvider;
        public Provider<Optional<FreeformComponents>> dynamicOverrideOptionalOfFreeformComponentsProvider;
        public Provider<Optional<FullscreenTaskListener>> dynamicOverrideOptionalOfFullscreenTaskListenerProvider;
        public Provider<Optional<Lazy<DesktopModeController>>> dynamicOverrideOptionalOfLazyOfDesktopModeControllerProvider;
        public Provider<Optional<Lazy<DesktopModeTaskRepository>>> dynamicOverrideOptionalOfLazyOfDesktopModeTaskRepositoryProvider;
        public Provider<Optional<Lazy<DesktopTasksController>>> dynamicOverrideOptionalOfLazyOfDesktopTasksControllerProvider;
        public Provider<Optional<OneHandedController>> dynamicOverrideOptionalOfOneHandedControllerProvider;
        public Provider<Optional<SplitScreenController>> dynamicOverrideOptionalOfSplitScreenControllerProvider;
        public Provider<Optional<StartingWindowTypeAlgorithm>> dynamicOverrideOptionalOfStartingWindowTypeAlgorithmProvider;
        public Provider<Optional<UnfoldAnimationController>> dynamicOverrideOptionalOfUnfoldAnimationControllerProvider;
        public Provider<Optional<UnfoldTransitionHandler>> dynamicOverrideOptionalOfUnfoldTransitionHandlerProvider;
        public Provider<Optional<BubbleController>> optionalOfBubbleControllerProvider;
        public Provider<Optional<PipTouchHandler>> optionalOfPipTouchHandlerProvider;
        public Provider<Optional<ShellUnfoldProgressProvider>> optionalOfShellUnfoldProgressProvider;
        public Provider<Optional<WindowDecorViewModel>> optionalOfWindowDecorViewModelProvider;
        public Provider<Optional<ActivityEmbeddingController>> provideActivityEmbeddingControllerProvider;
        public Provider<Optional<BackAnimationController>> provideBackAnimationControllerProvider;
        public Provider<Optional<BackAnimation>> provideBackAnimationProvider;
        public Provider<BubbleController> provideBubbleControllerProvider;
        public Provider<BubbleData> provideBubbleDataProvider;
        public Provider<BubbleLogger> provideBubbleLoggerProvider;
        public Provider<BubblePositioner> provideBubblePositionerProvider;
        public Provider<Optional<Bubbles>> provideBubblesProvider;
        public Provider<CompatUIController> provideCompatUIControllerProvider;
        public Provider<DefaultMixedHandler> provideDefaultMixedHandlerProvider;
        public Provider<DesktopModeController> provideDesktopModeControllerProvider;
        public Provider<Optional<DesktopModeController>> provideDesktopModeControllerProvider2;
        public Provider<Optional<DesktopMode>> provideDesktopModeProvider;
        public Provider<DesktopModeTaskRepository> provideDesktopModeTaskRepositoryProvider;
        public Provider<Optional<DesktopModeTaskRepository>> provideDesktopTaskRepositoryProvider;
        public Provider<DesktopTasksController> provideDesktopTasksControllerProvider;
        public Provider<Optional<DisplayAreaHelper>> provideDisplayAreaHelperProvider;
        public Provider<DisplayController> provideDisplayControllerProvider;
        public Provider<DisplayImeController> provideDisplayImeControllerProvider;
        public Provider<DisplayInsetsController> provideDisplayInsetsControllerProvider;
        public Provider<DisplayLayout> provideDisplayLayoutProvider;
        public Provider<DragAndDropController> provideDragAndDropControllerProvider;
        public Provider<FloatingContentCoordinator> provideFloatingContentCoordinatorProvider;
        public Provider<FreeformComponents> provideFreeformComponentsProvider;
        public Provider<Optional<FreeformComponents>> provideFreeformComponentsProvider2;
        public Provider<FreeformTaskListener> provideFreeformTaskListenerProvider;
        public Provider<FreeformTaskTransitionHandler> provideFreeformTaskTransitionHandlerProvider;
        public Provider<FreeformTaskTransitionObserver> provideFreeformTaskTransitionObserverProvider;
        public Provider<FullscreenTaskListener> provideFullscreenTaskListenerProvider;
        public Provider<FullscreenUnfoldTaskAnimator> provideFullscreenUnfoldTaskAnimatorProvider;
        public Provider<Optional<HideDisplayCutoutController>> provideHideDisplayCutoutControllerProvider;
        public Provider<IconProvider> provideIconProvider;
        public Provider<Object> provideIndependentShellComponentsToCreateProvider;
        public Provider<Object> provideIndependentShellComponentsToCreateProvider2;
        public Provider<KidsModeTaskOrganizer> provideKidsModeTaskOrganizerProvider;
        public Provider<OneHandedController> provideOneHandedControllerProvider;
        public Provider<Optional<OneHanded>> provideOneHandedProvider;
        public Provider<PhonePipKeepClearAlgorithm> providePhonePipKeepClearAlgorithmProvider;
        public Provider<PipAnimationController> providePipAnimationControllerProvider;
        public Provider<PipAppOpsListener> providePipAppOpsListenerProvider;
        public Provider<PipBoundsState> providePipBoundsStateProvider;
        public Provider<PipMediaController> providePipMediaControllerProvider;
        public Provider<PipMotionHelper> providePipMotionHelperProvider;
        public Provider<PipParamsChangedForwarder> providePipParamsChangedForwarderProvider;
        public Provider<Optional<Pip>> providePipProvider;
        public Provider<PipSnapAlgorithm> providePipSnapAlgorithmProvider;
        public Provider<PipSurfaceTransactionHelper> providePipSurfaceTransactionHelperProvider;
        public Provider<PipTaskOrganizer> providePipTaskOrganizerProvider;
        public Provider<PipTouchHandler> providePipTouchHandlerProvider;
        public Provider<PipTransitionController> providePipTransitionControllerProvider;
        public Provider<PipTransitionState> providePipTransitionStateProvider;
        public Provider<PipUiEventLogger> providePipUiEventLoggerProvider;
        public Provider<ProtoLogController> provideProtoLogControllerProvider;
        public Provider<Optional<RecentTasksController>> provideRecentTasksControllerProvider;
        public Provider<Optional<RecentTasks>> provideRecentTasksProvider;
        public Provider<ShellTransitions> provideRemoteTransitionsProvider;
        public Provider<RootDisplayAreaOrganizer> provideRootDisplayAreaOrganizerProvider;
        public Provider<RootTaskDisplayAreaOrganizer> provideRootTaskDisplayAreaOrganizerProvider;
        public Provider<ShellExecutor> provideSharedBackgroundExecutorProvider;
        public Provider<Handler> provideSharedBackgroundHandlerProvider;
        public Provider<ShellExecutor> provideShellAnimationExecutorProvider;
        public Provider<ShellCommandHandler> provideShellCommandHandlerProvider;
        public Provider<ShellController> provideShellControllerProvider;
        public Provider<ShellInit> provideShellInitProvider;
        public Provider<Choreographer> provideShellMainChoreographerProvider;
        public Provider<ShellExecutor> provideShellMainExecutorProvider;
        public Provider<Handler> provideShellMainHandlerProvider;
        public Provider<SplitTaskUnfoldAnimator> provideShellSplitTaskUnfoldAnimatorProvider;
        public Provider<ShellInterface> provideShellSysuiCallbacksProvider;
        public Provider<ShellTaskOrganizer> provideShellTaskOrganizerProvider;
        public Provider<ShellExecutor> provideSplashScreenExecutorProvider;
        public Provider<SplitScreenController> provideSplitScreenControllerProvider;
        public Provider<Optional<SplitScreen>> provideSplitScreenProvider;
        public Provider<SplitTaskUnfoldAnimator> provideSplitTaskUnfoldAnimatorBaseProvider;
        public Provider<SplitTaskUnfoldAnimator> provideSplitTaskUnfoldAnimatorProvider;
        public Provider<Optional<StartingSurface>> provideStartingSurfaceProvider;
        public Provider<StartingWindowController> provideStartingWindowControllerProvider;
        public Provider<StartingWindowTypeAlgorithm> provideStartingWindowTypeAlgorithmProvider;
        public Provider<SyncTransactionQueue> provideSyncTransactionQueueProvider;
        public Provider<ShellExecutor> provideSysUIMainExecutorProvider;
        public Provider<SystemWindows> provideSystemWindowsProvider;
        public Provider<TaskViewFactoryController> provideTaskViewFactoryControllerProvider;
        public Provider<Optional<TaskViewFactory>> provideTaskViewFactoryProvider;
        public Provider<TaskViewTransitions> provideTaskViewTransitionsProvider;
        public Provider<TransactionPool> provideTransactionPoolProvider;
        public Provider<Transitions> provideTransitionsProvider;
        public Provider<UnfoldAnimationController> provideUnfoldAnimationControllerProvider;
        public Provider<UnfoldBackgroundController> provideUnfoldBackgroundControllerProvider;
        public Provider<Optional<UnfoldAnimationController>> provideUnfoldControllerProvider;
        public Provider<UnfoldTransitionHandler> provideUnfoldTransitionHandlerProvider;
        public Provider<Optional<UnfoldTransitionHandler>> provideUnfoldTransitionHandlerProvider2;
        public Provider<WindowDecorViewModel> provideWindowDecorViewModelProvider;
        public Provider<WindowManagerShellWrapper> provideWindowManagerShellWrapperProvider;
        public Provider<TaskStackListenerImpl> providerTaskStackListenerImplProvider;
        public Provider<Optional<DesktopTasksController>> providesDesktopTasksControllerProvider;
        public Provider<Optional<OneHandedController>> providesOneHandedControllerProvider;
        public Provider<PipBoundsAlgorithm> providesPipBoundsAlgorithmProvider;
        public Provider<PhonePipMenuController> providesPipPhoneMenuControllerProvider;
        public Provider<Optional<SplitScreenController>> providesSplitScreenControllerProvider;
        public Provider<HandlerThread> setShellMainThreadProvider;
        public Provider<Optional<Object>> shellCreateTriggerOverrideOptionalOfObjectProvider;

        public WMComponentImpl(HandlerThread handlerThread) {
            initialize(handlerThread);
            initialize2(handlerThread);
        }

        @Override // com.android.systemui.dagger.WMComponent
        public Optional<BackAnimation> getBackAnimation() {
            return (Optional) this.provideBackAnimationProvider.get();
        }

        @Override // com.android.systemui.dagger.WMComponent
        public Optional<Bubbles> getBubbles() {
            return (Optional) this.provideBubblesProvider.get();
        }

        @Override // com.android.systemui.dagger.WMComponent
        public Optional<DesktopMode> getDesktopMode() {
            return (Optional) this.provideDesktopModeProvider.get();
        }

        @Override // com.android.systemui.dagger.WMComponent
        public Optional<DisplayAreaHelper> getDisplayAreaHelper() {
            return (Optional) this.provideDisplayAreaHelperProvider.get();
        }

        @Override // com.android.systemui.dagger.WMComponent
        public Optional<OneHanded> getOneHanded() {
            return (Optional) this.provideOneHandedProvider.get();
        }

        @Override // com.android.systemui.dagger.WMComponent
        public Optional<Pip> getPip() {
            return (Optional) this.providePipProvider.get();
        }

        @Override // com.android.systemui.dagger.WMComponent
        public Optional<RecentTasks> getRecentTasks() {
            return (Optional) this.provideRecentTasksProvider.get();
        }

        @Override // com.android.systemui.dagger.WMComponent
        public ShellInterface getShell() {
            return (ShellInterface) this.provideShellSysuiCallbacksProvider.get();
        }

        @Override // com.android.systemui.dagger.WMComponent
        public Optional<SplitScreen> getSplitScreen() {
            return (Optional) this.provideSplitScreenProvider.get();
        }

        @Override // com.android.systemui.dagger.WMComponent
        public Optional<StartingSurface> getStartingSurface() {
            return (Optional) this.provideStartingSurfaceProvider.get();
        }

        @Override // com.android.systemui.dagger.WMComponent
        public Optional<TaskViewFactory> getTaskViewFactory() {
            return (Optional) this.provideTaskViewFactoryProvider.get();
        }

        @Override // com.android.systemui.dagger.WMComponent
        public ShellTransitions getTransitions() {
            return (ShellTransitions) this.provideRemoteTransitionsProvider.get();
        }

        @Override // com.android.systemui.dagger.WMComponent
        public /* bridge */ /* synthetic */ void init() {
            super.init();
        }

        public final void initialize(HandlerThread handlerThread) {
            this.setShellMainThreadProvider = InstanceFactory.createNullable(handlerThread);
            this.provideShellMainHandlerProvider = DoubleCheck.provider(WMShellConcurrencyModule_ProvideShellMainHandlerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.setShellMainThreadProvider, WMShellConcurrencyModule_ProvideMainHandlerFactory.create()));
            this.provideSysUIMainExecutorProvider = DoubleCheck.provider(WMShellConcurrencyModule_ProvideSysUIMainExecutorFactory.create(WMShellConcurrencyModule_ProvideMainHandlerFactory.create()));
            Provider<ShellExecutor> provider = DoubleCheck.provider(WMShellConcurrencyModule_ProvideShellMainExecutorFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideShellMainHandlerProvider, this.provideSysUIMainExecutorProvider));
            this.provideShellMainExecutorProvider = provider;
            this.provideShellInitProvider = DoubleCheck.provider(WMShellBaseModule_ProvideShellInitFactory.create(provider));
            this.provideDisplayControllerProvider = DoubleCheck.provider(WMShellBaseModule_ProvideDisplayControllerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideIWindowManagerProvider, this.provideShellInitProvider, this.provideShellMainExecutorProvider));
            this.provideDisplayInsetsControllerProvider = DoubleCheck.provider(WMShellBaseModule_ProvideDisplayInsetsControllerFactory.create(DaggerReferenceGlobalRootComponent.this.provideIWindowManagerProvider, this.provideShellInitProvider, this.provideDisplayControllerProvider, this.provideShellMainExecutorProvider));
            this.provideTransactionPoolProvider = DoubleCheck.provider(WMShellBaseModule_ProvideTransactionPoolFactory.create());
            this.provideDisplayImeControllerProvider = DoubleCheck.provider(WMShellBaseModule_ProvideDisplayImeControllerFactory.create(DaggerReferenceGlobalRootComponent.this.provideIWindowManagerProvider, this.provideShellInitProvider, this.provideDisplayControllerProvider, this.provideDisplayInsetsControllerProvider, this.provideTransactionPoolProvider, this.provideShellMainExecutorProvider));
            Provider<ShellCommandHandler> provider2 = DoubleCheck.provider(WMShellBaseModule_ProvideShellCommandHandlerFactory.create());
            this.provideShellCommandHandlerProvider = provider2;
            this.provideShellControllerProvider = DoubleCheck.provider(WMShellBaseModule_ProvideShellControllerFactory.create(this.provideShellInitProvider, provider2, this.provideShellMainExecutorProvider));
            this.provideIconProvider = DoubleCheck.provider(WMShellBaseModule_ProvideIconProviderFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider));
            this.provideDragAndDropControllerProvider = DoubleCheck.provider(WMShellBaseModule_ProvideDragAndDropControllerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideShellInitProvider, this.provideShellControllerProvider, this.provideDisplayControllerProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, this.provideIconProvider, this.provideShellMainExecutorProvider));
            this.provideSyncTransactionQueueProvider = DoubleCheck.provider(WMShellBaseModule_ProvideSyncTransactionQueueFactory.create(this.provideTransactionPoolProvider, this.provideShellMainExecutorProvider));
            this.provideShellTaskOrganizerProvider = new DelegateFactory();
            this.provideShellAnimationExecutorProvider = DoubleCheck.provider(WMShellConcurrencyModule_ProvideShellAnimationExecutorFactory.create());
            this.provideTransitionsProvider = DoubleCheck.provider(WMShellBaseModule_ProvideTransitionsFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideShellInitProvider, this.provideShellControllerProvider, this.provideShellTaskOrganizerProvider, this.provideTransactionPoolProvider, this.provideDisplayControllerProvider, this.provideShellMainExecutorProvider, this.provideShellMainHandlerProvider, this.provideShellAnimationExecutorProvider));
            this.dockStateReaderProvider = DoubleCheck.provider(DockStateReader_Factory.create(DaggerReferenceGlobalRootComponent.this.contextProvider));
            this.provideCompatUIControllerProvider = DoubleCheck.provider(WMShellBaseModule_ProvideCompatUIControllerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideShellInitProvider, this.provideShellControllerProvider, this.provideDisplayControllerProvider, this.provideDisplayInsetsControllerProvider, this.provideDisplayImeControllerProvider, this.provideSyncTransactionQueueProvider, this.provideShellMainExecutorProvider, this.provideTransitionsProvider, this.dockStateReaderProvider));
            this.optionalOfShellUnfoldProgressProvider = PresentJdkOptionalInstanceProvider.of(DaggerReferenceGlobalRootComponent.this.provideShellProgressProvider);
            Provider<RootTaskDisplayAreaOrganizer> provider3 = DoubleCheck.provider(WMShellBaseModule_ProvideRootTaskDisplayAreaOrganizerFactory.create(this.provideShellMainExecutorProvider, DaggerReferenceGlobalRootComponent.this.contextProvider));
            this.provideRootTaskDisplayAreaOrganizerProvider = provider3;
            this.provideUnfoldBackgroundControllerProvider = DoubleCheck.provider(WMShellModule_ProvideUnfoldBackgroundControllerFactory.create(provider3, DaggerReferenceGlobalRootComponent.this.contextProvider));
            this.providerTaskStackListenerImplProvider = DoubleCheck.provider(WMShellBaseModule_ProviderTaskStackListenerImplFactory.create(this.provideShellMainHandlerProvider));
            Provider<DesktopModeTaskRepository> provider4 = DoubleCheck.provider(WMShellModule_ProvideDesktopModeTaskRepositoryFactory.create());
            this.provideDesktopModeTaskRepositoryProvider = provider4;
            Provider<Optional<Lazy<DesktopModeTaskRepository>>> of = PresentJdkOptionalLazyProvider.of(provider4);
            this.dynamicOverrideOptionalOfLazyOfDesktopModeTaskRepositoryProvider = of;
            this.provideDesktopTaskRepositoryProvider = DoubleCheck.provider(WMShellBaseModule_ProvideDesktopTaskRepositoryFactory.create(of));
            this.provideRecentTasksControllerProvider = DoubleCheck.provider(WMShellBaseModule_ProvideRecentTasksControllerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideShellInitProvider, this.provideShellControllerProvider, this.provideShellCommandHandlerProvider, this.providerTaskStackListenerImplProvider, DaggerReferenceGlobalRootComponent.this.provideActivityTaskManagerProvider, this.provideDesktopTaskRepositoryProvider, this.provideShellMainExecutorProvider));
            Provider<SplitScreenController> provider5 = DoubleCheck.provider(WMShellModule_ProvideSplitScreenControllerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideShellInitProvider, this.provideShellCommandHandlerProvider, this.provideShellControllerProvider, this.provideShellTaskOrganizerProvider, this.provideSyncTransactionQueueProvider, this.provideRootTaskDisplayAreaOrganizerProvider, this.provideDisplayControllerProvider, this.provideDisplayImeControllerProvider, this.provideDisplayInsetsControllerProvider, this.provideDragAndDropControllerProvider, this.provideTransitionsProvider, this.provideTransactionPoolProvider, this.provideIconProvider, this.provideRecentTasksControllerProvider, this.provideShellMainExecutorProvider));
            this.provideSplitScreenControllerProvider = provider5;
            Provider<Optional<SplitScreenController>> of2 = PresentJdkOptionalInstanceProvider.of(provider5);
            this.dynamicOverrideOptionalOfSplitScreenControllerProvider = of2;
            this.providesSplitScreenControllerProvider = DoubleCheck.provider(WMShellBaseModule_ProvidesSplitScreenControllerFactory.create(of2, DaggerReferenceGlobalRootComponent.this.contextProvider));
            WMShellModule_ProvideSplitTaskUnfoldAnimatorBaseFactory create = WMShellModule_ProvideSplitTaskUnfoldAnimatorBaseFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideUnfoldBackgroundControllerProvider, this.provideShellMainExecutorProvider, this.providesSplitScreenControllerProvider, this.provideDisplayInsetsControllerProvider);
            this.provideSplitTaskUnfoldAnimatorBaseProvider = create;
            this.provideSplitTaskUnfoldAnimatorProvider = DoubleCheck.provider(create);
            this.provideFullscreenUnfoldTaskAnimatorProvider = WMShellModule_ProvideFullscreenUnfoldTaskAnimatorFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideUnfoldBackgroundControllerProvider, this.provideDisplayInsetsControllerProvider);
            Provider<SplitTaskUnfoldAnimator> provider6 = DoubleCheck.provider(this.provideSplitTaskUnfoldAnimatorBaseProvider);
            this.provideShellSplitTaskUnfoldAnimatorProvider = provider6;
            Provider<UnfoldTransitionHandler> provider7 = DoubleCheck.provider(WMShellModule_ProvideUnfoldTransitionHandlerFactory.create(this.optionalOfShellUnfoldProgressProvider, this.provideFullscreenUnfoldTaskAnimatorProvider, provider6, this.provideTransactionPoolProvider, this.provideTransitionsProvider, this.provideShellMainExecutorProvider, this.provideShellInitProvider));
            this.provideUnfoldTransitionHandlerProvider = provider7;
            Provider<Optional<UnfoldTransitionHandler>> of3 = PresentJdkOptionalInstanceProvider.of(provider7);
            this.dynamicOverrideOptionalOfUnfoldTransitionHandlerProvider = of3;
            Provider<Optional<UnfoldTransitionHandler>> provider8 = DoubleCheck.provider(WMShellBaseModule_ProvideUnfoldTransitionHandlerFactory.create(this.optionalOfShellUnfoldProgressProvider, of3));
            this.provideUnfoldTransitionHandlerProvider2 = provider8;
            Provider<UnfoldAnimationController> provider9 = DoubleCheck.provider(WMShellModule_ProvideUnfoldAnimationControllerFactory.create(this.optionalOfShellUnfoldProgressProvider, this.provideTransactionPoolProvider, this.provideSplitTaskUnfoldAnimatorProvider, this.provideFullscreenUnfoldTaskAnimatorProvider, provider8, this.provideShellInitProvider, this.provideShellMainExecutorProvider));
            this.provideUnfoldAnimationControllerProvider = provider9;
            Provider<Optional<UnfoldAnimationController>> of4 = PresentJdkOptionalInstanceProvider.of(provider9);
            this.dynamicOverrideOptionalOfUnfoldAnimationControllerProvider = of4;
            this.provideUnfoldControllerProvider = DoubleCheck.provider(WMShellBaseModule_ProvideUnfoldControllerFactory.create(of4, this.optionalOfShellUnfoldProgressProvider));
            DelegateFactory.setDelegate(this.provideShellTaskOrganizerProvider, DoubleCheck.provider(WMShellBaseModule_ProvideShellTaskOrganizerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideShellInitProvider, this.provideShellCommandHandlerProvider, this.provideCompatUIControllerProvider, this.provideUnfoldControllerProvider, this.provideRecentTasksControllerProvider, this.provideShellMainExecutorProvider)));
            this.provideBubbleLoggerProvider = DoubleCheck.provider(WMShellModule_ProvideBubbleLoggerFactory.create(DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider));
            this.provideBubblePositionerProvider = DoubleCheck.provider(WMShellModule_ProvideBubblePositionerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, DaggerReferenceGlobalRootComponent.this.provideWindowManagerProvider));
            this.provideBubbleDataProvider = DoubleCheck.provider(WMShellModule_ProvideBubbleDataFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideBubbleLoggerProvider, this.provideBubblePositionerProvider, this.provideShellMainExecutorProvider));
            this.provideFloatingContentCoordinatorProvider = DoubleCheck.provider(WMShellBaseModule_ProvideFloatingContentCoordinatorFactory.create());
            this.provideWindowManagerShellWrapperProvider = DoubleCheck.provider(WMShellBaseModule_ProvideWindowManagerShellWrapperFactory.create(this.provideShellMainExecutorProvider));
            this.provideDisplayLayoutProvider = DoubleCheck.provider(WMShellBaseModule_ProvideDisplayLayoutFactory.create());
            Provider<OneHandedController> provider10 = DoubleCheck.provider(WMShellModule_ProvideOneHandedControllerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideShellInitProvider, this.provideShellCommandHandlerProvider, this.provideShellControllerProvider, DaggerReferenceGlobalRootComponent.this.provideWindowManagerProvider, this.provideDisplayControllerProvider, this.provideDisplayLayoutProvider, this.providerTaskStackListenerImplProvider, DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, DaggerReferenceGlobalRootComponent.this.provideInteractionJankMonitorProvider, this.provideShellMainExecutorProvider, this.provideShellMainHandlerProvider));
            this.provideOneHandedControllerProvider = provider10;
            this.dynamicOverrideOptionalOfOneHandedControllerProvider = PresentJdkOptionalInstanceProvider.of(provider10);
            Provider<Handler> provider11 = DoubleCheck.provider(WMShellConcurrencyModule_ProvideSharedBackgroundHandlerFactory.create());
            this.provideSharedBackgroundHandlerProvider = provider11;
            this.provideSharedBackgroundExecutorProvider = DoubleCheck.provider(WMShellConcurrencyModule_ProvideSharedBackgroundExecutorFactory.create(provider11));
            this.provideTaskViewTransitionsProvider = DoubleCheck.provider(WMShellBaseModule_ProvideTaskViewTransitionsFactory.create(this.provideTransitionsProvider));
            Provider<BubbleController> provider12 = DoubleCheck.provider(WMShellModule_ProvideBubbleControllerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideShellInitProvider, this.provideShellCommandHandlerProvider, this.provideShellControllerProvider, this.provideBubbleDataProvider, this.provideFloatingContentCoordinatorProvider, DaggerReferenceGlobalRootComponent.this.provideIStatusBarServiceProvider, DaggerReferenceGlobalRootComponent.this.provideWindowManagerProvider, this.provideWindowManagerShellWrapperProvider, DaggerReferenceGlobalRootComponent.this.provideUserManagerProvider, DaggerReferenceGlobalRootComponent.this.provideLauncherAppsProvider, this.providerTaskStackListenerImplProvider, this.provideBubbleLoggerProvider, this.provideShellTaskOrganizerProvider, this.provideBubblePositionerProvider, this.provideDisplayControllerProvider, this.dynamicOverrideOptionalOfOneHandedControllerProvider, this.provideDragAndDropControllerProvider, this.provideShellMainExecutorProvider, this.provideShellMainHandlerProvider, this.provideSharedBackgroundExecutorProvider, this.provideTaskViewTransitionsProvider, this.provideSyncTransactionQueueProvider));
            this.provideBubbleControllerProvider = provider12;
            this.optionalOfBubbleControllerProvider = PresentJdkOptionalInstanceProvider.of(provider12);
            Provider<PipSurfaceTransactionHelper> provider13 = DoubleCheck.provider(WMShellBaseModule_ProvidePipSurfaceTransactionHelperFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider));
            this.providePipSurfaceTransactionHelperProvider = provider13;
            this.providePipAnimationControllerProvider = DoubleCheck.provider(WMShellModule_ProvidePipAnimationControllerFactory.create(provider13));
            this.providePipBoundsStateProvider = DoubleCheck.provider(WMShellModule_ProvidePipBoundsStateFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider));
            this.providePipMediaControllerProvider = DoubleCheck.provider(WMShellBaseModule_ProvidePipMediaControllerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideShellMainHandlerProvider));
            this.provideSystemWindowsProvider = DoubleCheck.provider(WMShellBaseModule_ProvideSystemWindowsFactory.create(this.provideDisplayControllerProvider, DaggerReferenceGlobalRootComponent.this.provideIWindowManagerProvider));
            this.providePipUiEventLoggerProvider = DoubleCheck.provider(WMShellBaseModule_ProvidePipUiEventLoggerFactory.create(DaggerReferenceGlobalRootComponent.this.provideUiEventLoggerProvider, DaggerReferenceGlobalRootComponent.this.providePackageManagerProvider));
            this.providesPipPhoneMenuControllerProvider = DoubleCheck.provider(WMShellModule_ProvidesPipPhoneMenuControllerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.providePipBoundsStateProvider, this.providePipMediaControllerProvider, this.provideSystemWindowsProvider, this.providesSplitScreenControllerProvider, this.providePipUiEventLoggerProvider, this.provideShellMainExecutorProvider, this.provideShellMainHandlerProvider));
            this.providePipSnapAlgorithmProvider = DoubleCheck.provider(WMShellModule_ProvidePipSnapAlgorithmFactory.create());
            this.providePhonePipKeepClearAlgorithmProvider = DoubleCheck.provider(WMShellModule_ProvidePhonePipKeepClearAlgorithmFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider));
            this.providesPipBoundsAlgorithmProvider = DoubleCheck.provider(WMShellModule_ProvidesPipBoundsAlgorithmFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.providePipBoundsStateProvider, this.providePipSnapAlgorithmProvider, this.providePhonePipKeepClearAlgorithmProvider));
            this.providePipTransitionStateProvider = DoubleCheck.provider(WMShellModule_ProvidePipTransitionStateFactory.create());
            this.providePipTransitionControllerProvider = DoubleCheck.provider(WMShellModule_ProvidePipTransitionControllerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideShellInitProvider, this.provideShellTaskOrganizerProvider, this.provideTransitionsProvider, this.providePipAnimationControllerProvider, this.providesPipBoundsAlgorithmProvider, this.providePipBoundsStateProvider, this.providePipTransitionStateProvider, this.providesPipPhoneMenuControllerProvider, this.providePipSurfaceTransactionHelperProvider, this.providesSplitScreenControllerProvider));
            this.providePipParamsChangedForwarderProvider = DoubleCheck.provider(WMShellModule_ProvidePipParamsChangedForwarderFactory.create());
            this.providePipTaskOrganizerProvider = DoubleCheck.provider(WMShellModule_ProvidePipTaskOrganizerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideSyncTransactionQueueProvider, this.providePipTransitionStateProvider, this.providePipBoundsStateProvider, this.providesPipBoundsAlgorithmProvider, this.providesPipPhoneMenuControllerProvider, this.providePipAnimationControllerProvider, this.providePipSurfaceTransactionHelperProvider, this.providePipTransitionControllerProvider, this.providePipParamsChangedForwarderProvider, this.providesSplitScreenControllerProvider, this.provideDisplayControllerProvider, this.providePipUiEventLoggerProvider, this.provideShellTaskOrganizerProvider, this.provideShellMainExecutorProvider));
            this.providePipMotionHelperProvider = DoubleCheck.provider(WMShellModule_ProvidePipMotionHelperFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.providePipBoundsStateProvider, this.providePipTaskOrganizerProvider, this.providesPipPhoneMenuControllerProvider, this.providePipSnapAlgorithmProvider, this.providePipTransitionControllerProvider, this.provideFloatingContentCoordinatorProvider));
            this.providePipTouchHandlerProvider = DoubleCheck.provider(WMShellModule_ProvidePipTouchHandlerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideShellInitProvider, this.providesPipPhoneMenuControllerProvider, this.providesPipBoundsAlgorithmProvider, this.providePipBoundsStateProvider, this.providePipTaskOrganizerProvider, this.providePipMotionHelperProvider, this.provideFloatingContentCoordinatorProvider, this.providePipUiEventLoggerProvider, this.provideShellMainExecutorProvider));
            this.providePipAppOpsListenerProvider = DoubleCheck.provider(WMShellModule_ProvidePipAppOpsListenerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.providePipTouchHandlerProvider, this.provideShellMainExecutorProvider));
            this.providesOneHandedControllerProvider = DoubleCheck.provider(WMShellBaseModule_ProvidesOneHandedControllerFactory.create(this.dynamicOverrideOptionalOfOneHandedControllerProvider));
            this.providePipProvider = DoubleCheck.provider(WMShellModule_ProvidePipFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideShellInitProvider, this.provideShellCommandHandlerProvider, this.provideShellControllerProvider, this.provideDisplayControllerProvider, this.providePipAnimationControllerProvider, this.providePipAppOpsListenerProvider, this.providesPipBoundsAlgorithmProvider, this.providePhonePipKeepClearAlgorithmProvider, this.providePipBoundsStateProvider, this.providePipMotionHelperProvider, this.providePipMediaControllerProvider, this.providesPipPhoneMenuControllerProvider, this.providePipTaskOrganizerProvider, this.providePipTransitionStateProvider, this.providePipTouchHandlerProvider, this.providePipTransitionControllerProvider, this.provideWindowManagerShellWrapperProvider, this.providerTaskStackListenerImplProvider, this.providePipParamsChangedForwarderProvider, this.provideDisplayInsetsControllerProvider, this.providesOneHandedControllerProvider, this.provideShellMainExecutorProvider));
            this.optionalOfPipTouchHandlerProvider = PresentJdkOptionalInstanceProvider.of(this.providePipTouchHandlerProvider);
            this.dynamicOverrideOptionalOfFullscreenTaskListenerProvider = DaggerReferenceGlobalRootComponent.m2000$$Nest$smabsentJdkOptionalProvider();
            this.provideShellMainChoreographerProvider = DoubleCheck.provider(WMShellConcurrencyModule_ProvideShellMainChoreographerFactory.create(this.provideShellMainExecutorProvider));
            Provider<DesktopModeController> provider14 = DoubleCheck.provider(WMShellModule_ProvideDesktopModeControllerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideShellInitProvider, this.provideShellControllerProvider, this.provideShellTaskOrganizerProvider, this.provideRootTaskDisplayAreaOrganizerProvider, this.provideTransitionsProvider, this.provideDesktopModeTaskRepositoryProvider, this.provideShellMainHandlerProvider, this.provideShellMainExecutorProvider));
            this.provideDesktopModeControllerProvider = provider14;
            Provider<Optional<Lazy<DesktopModeController>>> of5 = PresentJdkOptionalLazyProvider.of(provider14);
            this.dynamicOverrideOptionalOfLazyOfDesktopModeControllerProvider = of5;
            this.provideDesktopModeControllerProvider2 = DoubleCheck.provider(WMShellBaseModule_ProvideDesktopModeControllerFactory.create(of5));
            Provider<DesktopTasksController> provider15 = DoubleCheck.provider(WMShellModule_ProvideDesktopTasksControllerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideShellInitProvider, this.provideShellControllerProvider, this.provideShellTaskOrganizerProvider, this.provideTransitionsProvider, this.provideDesktopModeTaskRepositoryProvider, this.provideShellMainExecutorProvider));
            this.provideDesktopTasksControllerProvider = provider15;
            Provider<Optional<Lazy<DesktopTasksController>>> of6 = PresentJdkOptionalLazyProvider.of(provider15);
            this.dynamicOverrideOptionalOfLazyOfDesktopTasksControllerProvider = of6;
            this.providesDesktopTasksControllerProvider = DoubleCheck.provider(WMShellBaseModule_ProvidesDesktopTasksControllerFactory.create(of6));
            Provider<WindowDecorViewModel> provider16 = DoubleCheck.provider(WMShellModule_ProvideWindowDecorViewModelFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideShellMainHandlerProvider, this.provideShellMainChoreographerProvider, this.provideShellTaskOrganizerProvider, this.provideDisplayControllerProvider, this.provideSyncTransactionQueueProvider, this.provideDesktopModeControllerProvider2, this.providesDesktopTasksControllerProvider));
            this.provideWindowDecorViewModelProvider = provider16;
            Provider<Optional<WindowDecorViewModel>> of7 = PresentJdkOptionalInstanceProvider.of(provider16);
            this.optionalOfWindowDecorViewModelProvider = of7;
            this.provideFullscreenTaskListenerProvider = DoubleCheck.provider(WMShellBaseModule_ProvideFullscreenTaskListenerFactory.create(this.dynamicOverrideOptionalOfFullscreenTaskListenerProvider, this.provideShellInitProvider, this.provideShellTaskOrganizerProvider, this.provideSyncTransactionQueueProvider, this.provideRecentTasksControllerProvider, of7));
            this.provideFreeformTaskListenerProvider = DoubleCheck.provider(WMShellModule_ProvideFreeformTaskListenerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideShellInitProvider, this.provideShellTaskOrganizerProvider, this.provideDesktopTaskRepositoryProvider, this.provideWindowDecorViewModelProvider));
            this.provideFreeformTaskTransitionHandlerProvider = DoubleCheck.provider(WMShellModule_ProvideFreeformTaskTransitionHandlerFactory.create(this.provideShellInitProvider, this.provideTransitionsProvider, this.provideWindowDecorViewModelProvider));
            Provider<FreeformTaskTransitionObserver> provider17 = DoubleCheck.provider(WMShellModule_ProvideFreeformTaskTransitionObserverFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideShellInitProvider, this.provideTransitionsProvider, this.provideWindowDecorViewModelProvider));
            this.provideFreeformTaskTransitionObserverProvider = provider17;
            Provider<FreeformComponents> provider18 = DoubleCheck.provider(WMShellModule_ProvideFreeformComponentsFactory.create(this.provideFreeformTaskListenerProvider, this.provideFreeformTaskTransitionHandlerProvider, provider17));
            this.provideFreeformComponentsProvider = provider18;
            Provider<Optional<FreeformComponents>> of8 = PresentJdkOptionalInstanceProvider.of(provider18);
            this.dynamicOverrideOptionalOfFreeformComponentsProvider = of8;
            this.provideFreeformComponentsProvider2 = DoubleCheck.provider(WMShellBaseModule_ProvideFreeformComponentsFactory.create(of8, DaggerReferenceGlobalRootComponent.this.contextProvider));
            this.provideHideDisplayCutoutControllerProvider = DoubleCheck.provider(WMShellBaseModule_ProvideHideDisplayCutoutControllerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideShellInitProvider, this.provideShellCommandHandlerProvider, this.provideShellControllerProvider, this.provideDisplayControllerProvider, this.provideShellMainExecutorProvider));
            this.provideActivityEmbeddingControllerProvider = DoubleCheck.provider(WMShellBaseModule_ProvideActivityEmbeddingControllerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideShellInitProvider, this.provideTransitionsProvider));
            this.provideSplashScreenExecutorProvider = DoubleCheck.provider(WMShellConcurrencyModule_ProvideSplashScreenExecutorFactory.create());
            Provider<Optional<StartingWindowTypeAlgorithm>> m2000$$Nest$smabsentJdkOptionalProvider = DaggerReferenceGlobalRootComponent.m2000$$Nest$smabsentJdkOptionalProvider();
            this.dynamicOverrideOptionalOfStartingWindowTypeAlgorithmProvider = m2000$$Nest$smabsentJdkOptionalProvider;
            this.provideStartingWindowTypeAlgorithmProvider = DoubleCheck.provider(WMShellBaseModule_ProvideStartingWindowTypeAlgorithmFactory.create(m2000$$Nest$smabsentJdkOptionalProvider));
            this.provideStartingWindowControllerProvider = DoubleCheck.provider(WMShellBaseModule_ProvideStartingWindowControllerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideShellInitProvider, this.provideShellControllerProvider, this.provideShellTaskOrganizerProvider, this.provideSplashScreenExecutorProvider, this.provideStartingWindowTypeAlgorithmProvider, this.provideIconProvider, this.provideTransactionPoolProvider));
            this.provideProtoLogControllerProvider = DoubleCheck.provider(WMShellBaseModule_ProvideProtoLogControllerFactory.create(this.provideShellInitProvider, this.provideShellCommandHandlerProvider));
            this.provideDefaultMixedHandlerProvider = DoubleCheck.provider(WMShellModule_ProvideDefaultMixedHandlerFactory.create(this.provideShellInitProvider, this.providesSplitScreenControllerProvider, this.optionalOfPipTouchHandlerProvider, this.provideTransitionsProvider));
            this.provideKidsModeTaskOrganizerProvider = DoubleCheck.provider(WMShellModule_ProvideKidsModeTaskOrganizerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideShellInitProvider, this.provideShellCommandHandlerProvider, this.provideSyncTransactionQueueProvider, this.provideDisplayControllerProvider, this.provideDisplayInsetsControllerProvider, this.provideUnfoldControllerProvider, this.provideRecentTasksControllerProvider, this.provideShellMainExecutorProvider, this.provideShellMainHandlerProvider));
        }

        public final void initialize2(HandlerThread handlerThread) {
            Provider<Object> provider = DoubleCheck.provider(WMShellModule_ProvideIndependentShellComponentsToCreateFactory.create(this.provideDefaultMixedHandlerProvider, this.provideKidsModeTaskOrganizerProvider, this.provideDesktopModeControllerProvider2));
            this.provideIndependentShellComponentsToCreateProvider = provider;
            Provider<Optional<Object>> of = PresentJdkOptionalInstanceProvider.of(provider);
            this.shellCreateTriggerOverrideOptionalOfObjectProvider = of;
            Provider<Object> provider2 = DoubleCheck.provider(WMShellBaseModule_ProvideIndependentShellComponentsToCreateFactory.create(this.provideDisplayControllerProvider, this.provideDisplayImeControllerProvider, this.provideDisplayInsetsControllerProvider, this.provideDragAndDropControllerProvider, this.provideShellTaskOrganizerProvider, this.optionalOfBubbleControllerProvider, this.providesSplitScreenControllerProvider, this.providePipProvider, this.optionalOfPipTouchHandlerProvider, this.provideFullscreenTaskListenerProvider, this.provideUnfoldControllerProvider, this.provideUnfoldTransitionHandlerProvider2, this.provideFreeformComponentsProvider2, this.provideRecentTasksControllerProvider, this.providesOneHandedControllerProvider, this.provideHideDisplayCutoutControllerProvider, this.provideActivityEmbeddingControllerProvider, this.provideTransitionsProvider, this.provideStartingWindowControllerProvider, this.provideProtoLogControllerProvider, of));
            this.provideIndependentShellComponentsToCreateProvider2 = provider2;
            this.provideShellSysuiCallbacksProvider = DoubleCheck.provider(WMShellBaseModule_ProvideShellSysuiCallbacksFactory.create(provider2, this.provideShellControllerProvider));
            this.provideOneHandedProvider = DoubleCheck.provider(WMShellBaseModule_ProvideOneHandedFactory.create(this.providesOneHandedControllerProvider));
            this.provideSplitScreenProvider = DoubleCheck.provider(WMShellBaseModule_ProvideSplitScreenFactory.create(this.providesSplitScreenControllerProvider));
            this.provideBubblesProvider = DoubleCheck.provider(WMShellBaseModule_ProvideBubblesFactory.create(this.optionalOfBubbleControllerProvider));
            Provider<TaskViewFactoryController> provider3 = DoubleCheck.provider(WMShellBaseModule_ProvideTaskViewFactoryControllerFactory.create(this.provideShellTaskOrganizerProvider, this.provideShellMainExecutorProvider, this.provideSyncTransactionQueueProvider, this.provideTaskViewTransitionsProvider));
            this.provideTaskViewFactoryControllerProvider = provider3;
            this.provideTaskViewFactoryProvider = DoubleCheck.provider(WMShellBaseModule_ProvideTaskViewFactoryFactory.create(provider3));
            this.provideRemoteTransitionsProvider = DoubleCheck.provider(WMShellBaseModule_ProvideRemoteTransitionsFactory.create(this.provideTransitionsProvider));
            this.provideStartingSurfaceProvider = DoubleCheck.provider(WMShellBaseModule_ProvideStartingSurfaceFactory.create(this.provideStartingWindowControllerProvider));
            Provider<RootDisplayAreaOrganizer> provider4 = DoubleCheck.provider(WMShellBaseModule_ProvideRootDisplayAreaOrganizerFactory.create(this.provideShellMainExecutorProvider));
            this.provideRootDisplayAreaOrganizerProvider = provider4;
            this.provideDisplayAreaHelperProvider = DoubleCheck.provider(WMShellBaseModule_ProvideDisplayAreaHelperFactory.create(this.provideShellMainExecutorProvider, provider4));
            this.provideRecentTasksProvider = DoubleCheck.provider(WMShellBaseModule_ProvideRecentTasksFactory.create(this.provideRecentTasksControllerProvider));
            Provider<Optional<BackAnimationController>> provider5 = DoubleCheck.provider(WMShellBaseModule_ProvideBackAnimationControllerFactory.create(DaggerReferenceGlobalRootComponent.this.contextProvider, this.provideShellInitProvider, this.provideShellControllerProvider, this.provideShellMainExecutorProvider, this.provideSharedBackgroundHandlerProvider));
            this.provideBackAnimationControllerProvider = provider5;
            this.provideBackAnimationProvider = DoubleCheck.provider(WMShellBaseModule_ProvideBackAnimationFactory.create(provider5));
            this.provideDesktopModeProvider = DoubleCheck.provider(WMShellBaseModule_ProvideDesktopModeFactory.create(this.provideDesktopModeControllerProvider2, this.providesDesktopTasksControllerProvider));
        }
    }

    /* renamed from: -$$Nest$smabsentJdkOptionalProvider  reason: not valid java name */
    public static /* bridge */ /* synthetic */ Provider m2000$$Nest$smabsentJdkOptionalProvider() {
        return absentJdkOptionalProvider();
    }

    public DaggerReferenceGlobalRootComponent(GlobalModule globalModule, AndroidInternalsModule androidInternalsModule, FrameworkServicesModule frameworkServicesModule, UnfoldTransitionModule unfoldTransitionModule, UnfoldSharedModule unfoldSharedModule, Context context, Boolean bool) {
        this.instrumentationTest = bool;
        this.context = context;
        this.globalModule = globalModule;
        initialize(globalModule, androidInternalsModule, frameworkServicesModule, unfoldTransitionModule, unfoldSharedModule, context, bool);
        initialize2(globalModule, androidInternalsModule, frameworkServicesModule, unfoldTransitionModule, unfoldSharedModule, context, bool);
    }

    public /* synthetic */ DaggerReferenceGlobalRootComponent(GlobalModule globalModule, AndroidInternalsModule androidInternalsModule, FrameworkServicesModule frameworkServicesModule, UnfoldTransitionModule unfoldTransitionModule, UnfoldSharedModule unfoldSharedModule, Context context, Boolean bool, DaggerReferenceGlobalRootComponent-IA r17) {
        this(globalModule, androidInternalsModule, frameworkServicesModule, unfoldTransitionModule, unfoldSharedModule, context, bool);
    }

    public static <T> Provider<Optional<T>> absentJdkOptionalProvider() {
        return ABSENT_JDK_OPTIONAL_PROVIDER;
    }

    public static ReferenceGlobalRootComponent.Builder builder() {
        return new Builder();
    }

    public final Context applicationContext() {
        return GlobalModule_ProvideApplicationContextFactory.provideApplicationContext(this.globalModule, this.context);
    }

    public final DisplayMetrics displayMetrics() {
        return GlobalModule_ProvideDisplayMetricsFactory.provideDisplayMetrics(this.globalModule, this.context);
    }

    @Override // com.android.systemui.dagger.GlobalRootComponent
    public InitializationChecker getInitializationChecker() {
        return new InitializationChecker(this.instrumentationTest.booleanValue());
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // com.android.systemui.dagger.GlobalRootComponent
    public ReferenceSysUIComponent.Builder getSysUIComponent() {
        return new ReferenceSysUIComponentBuilder();
    }

    @Override // com.android.systemui.dagger.GlobalRootComponent
    public WMComponent.Builder getWMComponentBuilder() {
        return new WMComponentBuilder();
    }

    public final void initialize(GlobalModule globalModule, AndroidInternalsModule androidInternalsModule, FrameworkServicesModule frameworkServicesModule, UnfoldTransitionModule unfoldTransitionModule, UnfoldSharedModule unfoldSharedModule, Context context, Boolean bool) {
        this.contextProvider = InstanceFactory.create(context);
        this.provideIWindowManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideIWindowManagerFactory.create());
        this.provideUiEventLoggerProvider = DoubleCheck.provider(AndroidInternalsModule_ProvideUiEventLoggerFactory.create());
        this.resourceUnfoldTransitionConfigProvider = DoubleCheck.provider(ResourceUnfoldTransitionConfig_Factory.create());
        Provider<ContentResolver> provider = DoubleCheck.provider(FrameworkServicesModule_ProvideContentResolverFactory.create(this.contextProvider));
        this.provideContentResolverProvider = provider;
        ScaleAwareTransitionProgressProvider_Factory create = ScaleAwareTransitionProgressProvider_Factory.create(provider);
        this.scaleAwareTransitionProgressProvider = create;
        this.factoryProvider = ScaleAwareTransitionProgressProvider_Factory_Impl.create(create);
        UnfoldTransitionModule_TracingTagPrefixFactory create2 = UnfoldTransitionModule_TracingTagPrefixFactory.create(unfoldTransitionModule);
        this.tracingTagPrefixProvider = create2;
        this.aTraceLoggerTransitionProgressListenerProvider = ATraceLoggerTransitionProgressListener_Factory.create(create2);
        this.providesSensorManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvidesSensorManagerFactory.create(this.contextProvider));
        Provider<Executor> provider2 = DoubleCheck.provider(GlobalConcurrencyModule_ProvideUiBackgroundExecutorFactory.create());
        this.provideUiBackgroundExecutorProvider = provider2;
        this.hingeAngleProvider = UnfoldSharedModule_HingeAngleProviderFactory.create(unfoldSharedModule, this.resourceUnfoldTransitionConfigProvider, this.providesSensorManagerProvider, provider2);
        Provider<DumpManager> provider3 = DoubleCheck.provider(DumpManager_Factory.create());
        this.dumpManagerProvider = provider3;
        Provider<ScreenLifecycle> provider4 = DoubleCheck.provider(ScreenLifecycle_Factory.create(provider3));
        this.screenLifecycleProvider = provider4;
        Provider<LifecycleScreenStatusProvider> provider5 = DoubleCheck.provider(LifecycleScreenStatusProvider_Factory.create(provider4));
        this.lifecycleScreenStatusProvider = provider5;
        this.screenStatusProvider = UnfoldTransitionModule_ScreenStatusProviderFactory.create(unfoldTransitionModule, provider5);
        Provider<DeviceStateManager> provider6 = DoubleCheck.provider(FrameworkServicesModule_ProvideDeviceStateManagerFactory.create(this.contextProvider));
        this.provideDeviceStateManagerProvider = provider6;
        this.deviceStateManagerFoldProvider = DoubleCheck.provider(DeviceStateManagerFoldProvider_Factory.create(provider6, this.contextProvider));
        Provider<ActivityManager> provider7 = DoubleCheck.provider(FrameworkServicesModule_ProvideActivityManagerFactory.create(this.contextProvider));
        this.provideActivityManagerProvider = provider7;
        this.activityManagerActivityTypeProvider = DoubleCheck.provider(ActivityManagerActivityTypeProvider_Factory.create(provider7));
        Provider<UnfoldKeyguardVisibilityManagerImpl> provider8 = DoubleCheck.provider(UnfoldKeyguardVisibilityManagerImpl_Factory.create());
        this.unfoldKeyguardVisibilityManagerImplProvider = provider8;
        this.unfoldKeyguardVisibilityProvider = DoubleCheck.provider(UnfoldSharedModule_UnfoldKeyguardVisibilityProviderFactory.create(unfoldSharedModule, provider8));
        Provider<Executor> provider9 = DoubleCheck.provider(GlobalConcurrencyModule_ProvideMainExecutorFactory.create(this.contextProvider));
        this.provideMainExecutorProvider = provider9;
        this.rotationChangeProvider = RotationChangeProvider_Factory.create(this.provideIWindowManagerProvider, this.contextProvider, provider9);
        GlobalConcurrencyModule_ProvideMainHandlerFactory create3 = GlobalConcurrencyModule_ProvideMainHandlerFactory.create(GlobalConcurrencyModule_ProvideMainLooperFactory.create());
        this.provideMainHandlerProvider = create3;
        DeviceFoldStateProvider_Factory create4 = DeviceFoldStateProvider_Factory.create(this.resourceUnfoldTransitionConfigProvider, this.hingeAngleProvider, this.screenStatusProvider, this.deviceStateManagerFoldProvider, this.activityManagerActivityTypeProvider, this.unfoldKeyguardVisibilityProvider, this.rotationChangeProvider, this.provideMainExecutorProvider, create3);
        this.deviceFoldStateProvider = create4;
        Provider<FoldStateProvider> provider10 = DoubleCheck.provider(UnfoldSharedModule_ProvideFoldStateProviderFactory.create(unfoldSharedModule, create4));
        this.provideFoldStateProvider = provider10;
        Provider<Optional<UnfoldTransitionProgressProvider>> provider11 = DoubleCheck.provider(UnfoldSharedModule_UnfoldTransitionProgressProviderFactory.create(unfoldSharedModule, this.resourceUnfoldTransitionConfigProvider, this.factoryProvider, this.aTraceLoggerTransitionProgressListenerProvider, provider10));
        this.unfoldTransitionProgressProvider = provider11;
        this.provideShellProgressProvider = DoubleCheck.provider(UnfoldTransitionModule_ProvideShellProgressProviderFactory.create(unfoldTransitionModule, this.resourceUnfoldTransitionConfigProvider, provider11));
        this.provideActivityTaskManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideActivityTaskManagerFactory.create());
        this.provideWindowManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideWindowManagerFactory.create(this.contextProvider));
        this.provideIStatusBarServiceProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideIStatusBarServiceFactory.create());
        this.provideUserManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideUserManagerFactory.create(this.contextProvider));
        this.provideLauncherAppsProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideLauncherAppsFactory.create(this.contextProvider));
        this.provideInteractionJankMonitorProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideInteractionJankMonitorFactory.create());
        this.providePackageManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvidePackageManagerFactory.create(this.contextProvider));
        this.provideMetricsLoggerProvider = DoubleCheck.provider(AndroidInternalsModule_ProvideMetricsLoggerFactory.create(androidInternalsModule));
        this.providesPluginExecutorProvider = DoubleCheck.provider(PluginsModule_ProvidesPluginExecutorFactory.create(ThreadFactoryImpl_Factory.create()));
        this.provideNotificationManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideNotificationManagerFactory.create(this.contextProvider));
        this.pluginEnablerImplProvider = DoubleCheck.provider(PluginEnablerImpl_Factory.create(this.contextProvider, this.providePackageManagerProvider));
        PluginsModule_ProvidesPrivilegedPluginsFactory create5 = PluginsModule_ProvidesPrivilegedPluginsFactory.create(this.contextProvider);
        this.providesPrivilegedPluginsProvider = create5;
        Provider<PluginInstance.Factory> provider12 = DoubleCheck.provider(PluginsModule_ProvidesPluginInstanceFactoryFactory.create(create5, PluginsModule_ProvidesPluginDebugFactory.create()));
        this.providesPluginInstanceFactoryProvider = provider12;
        this.providePluginInstanceManagerFactoryProvider = DoubleCheck.provider(PluginsModule_ProvidePluginInstanceManagerFactoryFactory.create(this.contextProvider, this.providePackageManagerProvider, this.provideMainExecutorProvider, this.providesPluginExecutorProvider, this.provideNotificationManagerProvider, this.pluginEnablerImplProvider, this.providesPrivilegedPluginsProvider, provider12));
        this.uncaughtExceptionPreHandlerManagerProvider = DoubleCheck.provider(UncaughtExceptionPreHandlerManager_Factory.create());
        this.providesPluginPrefsProvider = PluginsModule_ProvidesPluginPrefsFactory.create(this.contextProvider);
        this.providesPluginManagerProvider = DoubleCheck.provider(PluginsModule_ProvidesPluginManagerFactory.create(this.contextProvider, this.providePluginInstanceManagerFactoryProvider, PluginsModule_ProvidesPluginDebugFactory.create(), this.uncaughtExceptionPreHandlerManagerProvider, this.pluginEnablerImplProvider, this.providesPluginPrefsProvider, this.providesPrivilegedPluginsProvider));
        this.provideDisplayMetricsProvider = GlobalModule_ProvideDisplayMetricsFactory.create(globalModule, this.contextProvider);
        this.providePowerManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvidePowerManagerFactory.create(this.contextProvider));
        this.provideViewConfigurationProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideViewConfigurationFactory.create(this.contextProvider));
        this.provideResourcesProvider = FrameworkServicesModule_ProvideResourcesFactory.create(this.contextProvider);
        this.provideLockPatternUtilsProvider = DoubleCheck.provider(AndroidInternalsModule_ProvideLockPatternUtilsFactory.create(androidInternalsModule, this.contextProvider));
        this.provideExecutionProvider = DoubleCheck.provider(ExecutionImpl_Factory.create());
        this.provideDevicePolicyManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideDevicePolicyManagerFactory.create(this.contextProvider));
        this.providesChoreographerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvidesChoreographerFactory.create(frameworkServicesModule));
        this.provideMainDelayableExecutorProvider = DoubleCheck.provider(GlobalConcurrencyModule_ProvideMainDelayableExecutorFactory.create(GlobalConcurrencyModule_ProvideMainLooperFactory.create()));
        this.provideAccessibilityManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideAccessibilityManagerFactory.create(this.contextProvider));
        this.provideIActivityManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideIActivityManagerFactory.create());
        this.provideAmbientDisplayConfigurationProvider = FrameworkServicesModule_ProvideAmbientDisplayConfigurationFactory.create(frameworkServicesModule, this.contextProvider);
        Provider<Optional<NaturalRotationUnfoldProgressProvider>> provider13 = DoubleCheck.provider(UnfoldTransitionModule_ProvideNaturalRotationProgressProviderFactory.create(unfoldTransitionModule, this.contextProvider, this.rotationChangeProvider, this.unfoldTransitionProgressProvider));
        this.provideNaturalRotationProgressProvider = provider13;
        this.provideStatusBarScopedTransitionProvider = DoubleCheck.provider(UnfoldTransitionModule_ProvideStatusBarScopedTransitionProviderFactory.create(unfoldTransitionModule, provider13));
        this.providerLayoutInflaterProvider = DoubleCheck.provider(FrameworkServicesModule_ProviderLayoutInflaterFactory.create(frameworkServicesModule, this.contextProvider));
        this.providePackageManagerWrapperProvider = DoubleCheck.provider(FrameworkServicesModule_ProvidePackageManagerWrapperFactory.create());
        this.pluginDependencyProvider = DoubleCheck.provider(PluginDependencyProvider_Factory.create(this.providesPluginManagerProvider));
        this.provideNotificationMessagingUtilProvider = AndroidInternalsModule_ProvideNotificationMessagingUtilFactory.create(androidInternalsModule, this.contextProvider);
        this.provideAudioManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideAudioManagerFactory.create(this.contextProvider));
        this.provideSensorPrivacyManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideSensorPrivacyManagerFactory.create(this.contextProvider));
        this.provideStatsManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideStatsManagerFactory.create(this.contextProvider));
        this.provideDisplayIdProvider = FrameworkServicesModule_ProvideDisplayIdFactory.create(this.contextProvider);
        this.provideCarrierConfigManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideCarrierConfigManagerFactory.create(this.contextProvider));
        this.provideSubscriptionManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideSubscriptionManagerFactory.create(this.contextProvider));
        this.provideConnectivityManagagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideConnectivityManagagerFactory.create(this.contextProvider));
        this.provideTelephonyManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideTelephonyManagerFactory.create(this.contextProvider));
        this.provideWifiManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideWifiManagerFactory.create(this.contextProvider));
        this.provideNetworkScoreManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideNetworkScoreManagerFactory.create(this.contextProvider));
        this.provideIDreamManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideIDreamManagerFactory.create());
        this.provideMediaSessionManagerProvider = FrameworkServicesModule_ProvideMediaSessionManagerFactory.create(this.contextProvider);
        this.provideMediaRouter2ManagerProvider = FrameworkServicesModule_ProvideMediaRouter2ManagerFactory.create(this.contextProvider);
        this.provideSmartspaceManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideSmartspaceManagerFactory.create(this.contextProvider));
        this.providePowerExemptionManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvidePowerExemptionManagerFactory.create(this.contextProvider));
        this.provideKeyguardManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideKeyguardManagerFactory.create(this.contextProvider));
        this.provideAlarmManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideAlarmManagerFactory.create(this.contextProvider));
        this.provideINotificationManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideINotificationManagerFactory.create(frameworkServicesModule));
        this.provideShortcutManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideShortcutManagerFactory.create(this.contextProvider));
        this.provideLatencyTrackerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideLatencyTrackerFactory.create(this.contextProvider));
        this.provideVibratorProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideVibratorFactory.create(this.contextProvider));
        this.provideCrossWindowBlurListenersProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideCrossWindowBlurListenersFactory.create());
        this.provideWallpaperManagerProvider = FrameworkServicesModule_ProvideWallpaperManagerFactory.create(this.contextProvider);
        this.provideApplicationContextProvider = GlobalModule_ProvideApplicationContextFactory.create(globalModule, this.contextProvider);
        this.provideIAudioServiceProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideIAudioServiceFactory.create());
        this.provideCaptioningManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideCaptioningManagerFactory.create(this.contextProvider));
        this.provideTelecomManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideTelecomManagerFactory.create(this.contextProvider));
        this.provideSharePreferencesProvider = FrameworkServicesModule_ProvideSharePreferencesFactory.create(frameworkServicesModule, this.contextProvider);
        this.provideIBatteryStatsProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideIBatteryStatsFactory.create());
        this.provideIActivityTaskManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideIActivityTaskManagerFactory.create());
        this.provideTrustManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideTrustManagerFactory.create(this.contextProvider));
        this.provideDisplayManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideDisplayManagerFactory.create(this.contextProvider));
        this.provideTextClassificationManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideTextClassificationManagerFactory.create(this.contextProvider));
        this.provideClipboardManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideClipboardManagerFactory.create(this.contextProvider));
        this.provideStorageManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideStorageManagerFactory.create(this.contextProvider));
        this.provideOverlayManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideOverlayManagerFactory.create(this.contextProvider));
    }

    public final void initialize2(GlobalModule globalModule, AndroidInternalsModule androidInternalsModule, FrameworkServicesModule frameworkServicesModule, UnfoldTransitionModule unfoldTransitionModule, UnfoldSharedModule unfoldSharedModule, Context context, Boolean bool) {
        this.provideInputManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideInputManagerFactory.create(this.contextProvider));
        Provider<BluetoothManager> provider = DoubleCheck.provider(FrameworkServicesModule_ProvideBluetoothManagerFactory.create(this.contextProvider));
        this.provideBluetoothManagerProvider = provider;
        this.provideBluetoothAdapterProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideBluetoothAdapterFactory.create(provider));
        this.provideNotificationManagerCompatProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideNotificationManagerCompatFactory.create(this.contextProvider));
        this.providesFingerprintManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvidesFingerprintManagerFactory.create(this.contextProvider));
        Provider<FaceManager> provider2 = DoubleCheck.provider(FrameworkServicesModule_ProvideFaceManagerFactory.create(this.contextProvider));
        this.provideFaceManagerProvider = provider2;
        this.providesBiometricManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvidesBiometricManagerFactory.create(this.contextProvider, provider2, this.providesFingerprintManagerProvider));
        this.provideIsTestHarnessProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideIsTestHarnessFactory.create());
        Provider<Optional<FoldStateLoggingProvider>> provider3 = DoubleCheck.provider(UnfoldTransitionModule_ProvidesFoldStateLoggingProviderFactory.create(unfoldTransitionModule, this.resourceUnfoldTransitionConfigProvider, this.provideFoldStateProvider));
        this.providesFoldStateLoggingProvider = provider3;
        this.providesFoldStateLoggerProvider = DoubleCheck.provider(UnfoldTransitionModule_ProvidesFoldStateLoggerFactory.create(unfoldTransitionModule, provider3));
        this.provideCameraManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideCameraManagerFactory.create(this.contextProvider));
        this.provideColorDisplayManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideColorDisplayManagerFactory.create(this.contextProvider));
        this.unfoldKeyguardVisibilityManagerProvider = DoubleCheck.provider(UnfoldSharedModule_UnfoldKeyguardVisibilityManagerFactory.create(unfoldSharedModule, this.unfoldKeyguardVisibilityManagerImplProvider));
        this.provideIPackageManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideIPackageManagerFactory.create());
        this.provideSafetyCenterManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideSafetyCenterManagerFactory.create(this.contextProvider));
        this.provideJobSchedulerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideJobSchedulerFactory.create(this.contextProvider));
        this.providePermissionManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvidePermissionManagerFactory.create(this.contextProvider));
        this.provideOptionalVibratorProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideOptionalVibratorFactory.create(this.contextProvider));
        this.provideInputMethodManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideInputMethodManagerFactory.create(this.contextProvider));
        this.provideOptionalTelecomManagerProvider = DoubleCheck.provider(FrameworkServicesModule_ProvideOptionalTelecomManagerFactory.create(this.contextProvider));
        this.qSExpansionPathInterpolatorProvider = DoubleCheck.provider(QSExpansionPathInterpolator_Factory.create());
    }

    public final Handler mainHandler() {
        return GlobalConcurrencyModule_ProvideMainHandlerFactory.provideMainHandler(GlobalConcurrencyModule_ProvideMainLooperFactory.provideMainLooper());
    }

    public final Resources mainResources() {
        return FrameworkServicesModule_ProvideResourcesFactory.provideResources(this.context);
    }
}