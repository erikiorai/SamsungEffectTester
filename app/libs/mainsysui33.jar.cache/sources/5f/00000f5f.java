package com.android.systemui;

import android.app.AlarmManager;
import android.app.INotificationManager;
import android.app.IWallpaperManager;
import android.hardware.SensorPrivacyManager;
import android.hardware.display.NightDisplayListener;
import android.os.Handler;
import android.os.Looper;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.view.IWindowManager;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.util.Preconditions;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.clock.ClockManager;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.systemui.accessibility.AccessibilityButtonModeObserver;
import com.android.systemui.accessibility.AccessibilityButtonTargetsObserver;
import com.android.systemui.accessibility.floatingmenu.AccessibilityFloatingMenuController;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.appops.AppOpsController;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.dock.DockManager;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.fragments.FragmentService;
import com.android.systemui.hdmi.HdmiCecSetMenuLanguageHelper;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.media.dialog.MediaOutputDialogFactory;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.plugins.PluginDependencyProvider;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.plugins.VolumeDialogController;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.power.EnhancedEstimates;
import com.android.systemui.power.PowerUI;
import com.android.systemui.privacy.PrivacyItemController;
import com.android.systemui.qs.ReduceBrightColorsController;
import com.android.systemui.qs.tiles.dialog.InternetDialogFactory;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.screenrecord.RecordingController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.DevicePolicyManagerWrapper;
import com.android.systemui.shared.system.PackageManagerWrapper;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.NotificationListener;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.events.PrivacyDotViewController;
import com.android.systemui.statusbar.events.SystemStatusAnimationScheduler;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.notification.stack.AmbientState;
import com.android.systemui.statusbar.notification.stack.NotificationSectionsManager;
import com.android.systemui.statusbar.phone.AutoHideController;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.KeyguardDismissUtil;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.phone.LockscreenGestureLogger;
import com.android.systemui.statusbar.phone.ManagedProfileController;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.phone.SystemUIDialogManager;
import com.android.systemui.statusbar.policy.AccessibilityController;
import com.android.systemui.statusbar.policy.AccessibilityManagerWrapper;
import com.android.systemui.statusbar.policy.BluetoothController;
import com.android.systemui.statusbar.policy.CastController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DataSaverController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.ExtensionController;
import com.android.systemui.statusbar.policy.FlashlightController;
import com.android.systemui.statusbar.policy.HotspotController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.LocationController;
import com.android.systemui.statusbar.policy.NextAlarmController;
import com.android.systemui.statusbar.policy.RemoteInputQuickSettingsDisabler;
import com.android.systemui.statusbar.policy.RotationLockController;
import com.android.systemui.statusbar.policy.SecurityController;
import com.android.systemui.statusbar.policy.SensorPrivacyController;
import com.android.systemui.statusbar.policy.SmartReplyConstants;
import com.android.systemui.statusbar.policy.UserInfoController;
import com.android.systemui.statusbar.policy.UserSwitcherController;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.telephony.TelephonyListenerManager;
import com.android.systemui.tracing.ProtoTracer;
import com.android.systemui.tuner.TunablePadding;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.leak.GarbageMonitor;
import com.android.systemui.util.leak.LeakDetector;
import com.android.systemui.util.leak.LeakReporter;
import com.android.systemui.util.sensors.AsyncSensorManager;
import dagger.Lazy;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* loaded from: mainsysui33.jar:com/android/systemui/Dependency.class */
public class Dependency {
    public static Dependency sDependency;
    public Lazy<AccessibilityButtonTargetsObserver> mAccessibilityButtonListController;
    public Lazy<AccessibilityButtonModeObserver> mAccessibilityButtonModeObserver;
    public Lazy<AccessibilityController> mAccessibilityController;
    public Lazy<AccessibilityFloatingMenuController> mAccessibilityFloatingMenuController;
    public Lazy<AccessibilityManagerWrapper> mAccessibilityManagerWrapper;
    public Lazy<ActivityManagerWrapper> mActivityManagerWrapper;
    public Lazy<ActivityStarter> mActivityStarter;
    public Lazy<AlarmManager> mAlarmManager;
    public Lazy<AmbientState> mAmbientStateLazy;
    public Lazy<AppOpsController> mAppOpsController;
    public Lazy<AssistManager> mAssistManager;
    public Lazy<AsyncSensorManager> mAsyncSensorManager;
    public Lazy<AutoHideController> mAutoHideController;
    public Lazy<Executor> mBackgroundExecutor;
    public Lazy<Handler> mBgHandler;
    public Lazy<Looper> mBgLooper;
    public Lazy<BluetoothController> mBluetoothController;
    public Lazy<BroadcastDispatcher> mBroadcastDispatcher;
    public Lazy<CastController> mCastController;
    public Lazy<ClockManager> mClockManager;
    public Lazy<CommandQueue> mCommandQueue;
    public Lazy<ConfigurationController> mConfigurationController;
    public Lazy<StatusBarContentInsetsProvider> mContentInsetsProviderLazy;
    public Lazy<DarkIconDispatcher> mDarkIconDispatcher;
    public Lazy<DataSaverController> mDataSaverController;
    public Lazy<DeviceConfigProxy> mDeviceConfigProxy;
    public Lazy<DevicePolicyManagerWrapper> mDevicePolicyManagerWrapper;
    public Lazy<DeviceProvisionedController> mDeviceProvisionedController;
    public Lazy<DialogLaunchAnimator> mDialogLaunchAnimatorLazy;
    public Lazy<DisplayMetrics> mDisplayMetrics;
    public Lazy<DockManager> mDockManager;
    public Lazy<DozeParameters> mDozeParameters;
    public DumpManager mDumpManager;
    public Lazy<EdgeBackGestureHandler.Factory> mEdgeBackGestureHandlerFactoryLazy;
    public Lazy<EnhancedEstimates> mEnhancedEstimates;
    public Lazy<ExtensionController> mExtensionController;
    public Lazy<FeatureFlags> mFeatureFlagsLazy;
    public Lazy<FlashlightController> mFlashlightController;
    public Lazy<ForegroundServiceController> mForegroundServiceController;
    public Lazy<FragmentService> mFragmentService;
    public Lazy<GarbageMonitor> mGarbageMonitor;
    public Lazy<GroupExpansionManager> mGroupExpansionManagerLazy;
    public Lazy<GroupMembershipManager> mGroupMembershipManagerLazy;
    public Lazy<HdmiCecSetMenuLanguageHelper> mHdmiCecSetMenuLanguageHelper;
    public Lazy<HotspotController> mHotspotController;
    public Lazy<INotificationManager> mINotificationManager;
    public Lazy<IStatusBarService> mIStatusBarService;
    public Lazy<IWindowManager> mIWindowManager;
    public Lazy<InternetDialogFactory> mInternetDialogFactory;
    public Lazy<KeyguardDismissUtil> mKeyguardDismissUtil;
    public Lazy<KeyguardStateController> mKeyguardMonitor;
    public Lazy<KeyguardSecurityModel> mKeyguardSecurityModel;
    public Lazy<KeyguardUpdateMonitor> mKeyguardUpdateMonitor;
    public Lazy<LeakDetector> mLeakDetector;
    public Lazy<String> mLeakReportEmail;
    public Lazy<LeakReporter> mLeakReporter;
    public Lazy<LightBarController> mLightBarController;
    public Lazy<LocalBluetoothManager> mLocalBluetoothManager;
    public Lazy<LocationController> mLocationController;
    public Lazy<LockscreenGestureLogger> mLockscreenGestureLogger;
    public Lazy<Executor> mMainExecutor;
    public Lazy<Handler> mMainHandler;
    public Lazy<Looper> mMainLooper;
    public Lazy<ManagedProfileController> mManagedProfileController;
    public Lazy<MediaOutputDialogFactory> mMediaOutputDialogFactory;
    public Lazy<MetricsLogger> mMetricsLogger;
    public Lazy<NavigationModeController> mNavBarModeController;
    public Lazy<NavigationBarController> mNavigationBarController;
    public Lazy<NextAlarmController> mNextAlarmController;
    public Lazy<NightDisplayListener> mNightDisplayListener;
    public Lazy<NotificationGutsManager> mNotificationGutsManager;
    public Lazy<NotificationListener> mNotificationListener;
    public Lazy<NotificationLockscreenUserManager> mNotificationLockscreenUserManager;
    public Lazy<NotificationLogger> mNotificationLogger;
    public Lazy<NotificationMediaManager> mNotificationMediaManager;
    public Lazy<NotificationRemoteInputManager> mNotificationRemoteInputManager;
    public Lazy<NotificationRemoteInputManager.Callback> mNotificationRemoteInputManagerCallback;
    public Lazy<NotificationSectionsManager> mNotificationSectionsManagerLazy;
    public Lazy<NotificationShadeWindowController> mNotificationShadeWindowController;
    public Lazy<OverviewProxyService> mOverviewProxyService;
    public Lazy<PackageManagerWrapper> mPackageManagerWrapper;
    public Lazy<PluginDependencyProvider> mPluginDependencyProvider;
    public Lazy<PluginManager> mPluginManager;
    public Lazy<PrivacyDotViewController> mPrivacyDotViewControllerLazy;
    public Lazy<PrivacyItemController> mPrivacyItemController;
    public Lazy<ProtoTracer> mProtoTracer;
    public Lazy<RecordingController> mRecordingController;
    public Lazy<ReduceBrightColorsController> mReduceBrightColorsController;
    public Lazy<RemoteInputQuickSettingsDisabler> mRemoteInputQuickSettingsDisabler;
    public Lazy<RotationLockController> mRotationLockController;
    public Lazy<ScreenLifecycle> mScreenLifecycle;
    public Lazy<ScreenOffAnimationController> mScreenOffAnimationController;
    public Lazy<SecurityController> mSecurityController;
    public Lazy<SensorPrivacyController> mSensorPrivacyController;
    public Lazy<SensorPrivacyManager> mSensorPrivacyManager;
    public Lazy<ShadeController> mShadeController;
    public Lazy<SmartReplyConstants> mSmartReplyConstants;
    public Lazy<SmartReplyController> mSmartReplyController;
    public Lazy<StatusBarIconController> mStatusBarIconController;
    public Lazy<StatusBarStateController> mStatusBarStateController;
    public Lazy<SysUiState> mSysUiStateFlagsContainer;
    public Lazy<SystemStatusAnimationScheduler> mSystemStatusAnimationSchedulerLazy;
    public Lazy<SystemUIDialogManager> mSystemUIDialogManagerLazy;
    public Lazy<SysuiColorExtractor> mSysuiColorExtractor;
    public Lazy<TelephonyListenerManager> mTelephonyListenerManager;
    public Lazy<StatusBarWindowController> mTempStatusBarWindowController;
    public Lazy<Handler> mTimeTickHandler;
    public Lazy<TunablePadding.TunablePaddingService> mTunablePaddingService;
    public Lazy<TunerService> mTunerService;
    public Lazy<UiEventLogger> mUiEventLogger;
    public Lazy<UiOffloadThread> mUiOffloadThread;
    public Lazy<UserInfoController> mUserInfoController;
    public Lazy<UserSwitcherController> mUserSwitcherController;
    public Lazy<UserTracker> mUserTrackerLazy;
    public Lazy<VibratorHelper> mVibratorHelper;
    public Lazy<VolumeDialogController> mVolumeDialogController;
    public Lazy<WakefulnessLifecycle> mWakefulnessLifecycle;
    public Lazy<IWallpaperManager> mWallpaperManager;
    public Lazy<PowerUI.WarningsUI> mWarningsUI;
    public Lazy<ZenModeController> mZenModeController;
    public static final DependencyKey<Looper> BG_LOOPER = new DependencyKey<>("background_looper");
    public static final DependencyKey<Looper> MAIN_LOOPER = new DependencyKey<>("main_looper");
    public static final DependencyKey<Handler> TIME_TICK_HANDLER = new DependencyKey<>("time_tick_handler");
    public static final DependencyKey<Handler> MAIN_HANDLER = new DependencyKey<>("main_handler");
    public static final DependencyKey<Executor> MAIN_EXECUTOR = new DependencyKey<>("main_executor");
    public static final DependencyKey<Executor> BACKGROUND_EXECUTOR = new DependencyKey<>("background_executor");
    public static final DependencyKey<String> LEAK_REPORT_EMAIL = new DependencyKey<>("leak_report_email");
    public final ArrayMap<Object, Object> mDependencies = new ArrayMap<>();
    public final ArrayMap<Object, LazyDependencyCreator> mProviders = new ArrayMap<>();

    /* loaded from: mainsysui33.jar:com/android/systemui/Dependency$DependencyKey.class */
    public static final class DependencyKey<V> {
        public final String mDisplayName;

        public DependencyKey(String str) {
            this.mDisplayName = str;
        }

        public String toString() {
            return this.mDisplayName;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/Dependency$LazyDependencyCreator.class */
    public interface LazyDependencyCreator<T> {
        T createDependency();
    }

    public static <T> void destroy(Class<T> cls, Consumer<T> consumer) {
        sDependency.destroyDependency(cls, consumer);
    }

    @Deprecated
    public static <T> T get(DependencyKey<T> dependencyKey) {
        return (T) sDependency.getDependency(dependencyKey);
    }

    @Deprecated
    public static <T> T get(Class<T> cls) {
        return (T) sDependency.getDependency(cls);
    }

    @VisibleForTesting
    public static void setInstance(Dependency dependency) {
        sDependency = dependency;
    }

    @VisibleForTesting
    public <T> T createDependency(Object obj) {
        Preconditions.checkArgument((obj instanceof DependencyKey) || (obj instanceof Class));
        LazyDependencyCreator lazyDependencyCreator = this.mProviders.get(obj);
        if (lazyDependencyCreator != null) {
            return (T) lazyDependencyCreator.createDependency();
        }
        throw new IllegalArgumentException("Unsupported dependency " + obj + ". " + this.mProviders.size() + " providers known.");
    }

    /* JADX DEBUG: Multi-variable search result rejected for r5v0, resolved type: java.util.function.Consumer<T> */
    /* JADX WARN: Multi-variable type inference failed */
    public final <T> void destroyDependency(Class<T> cls, Consumer<T> consumer) {
        Object remove = this.mDependencies.remove(cls);
        if (remove instanceof Dumpable) {
            this.mDumpManager.unregisterDumpable(remove.getClass().getName());
        }
        if (remove == null || consumer == 0) {
            return;
        }
        consumer.accept(remove);
    }

    public final <T> T getDependency(DependencyKey<T> dependencyKey) {
        return (T) getDependencyInner(dependencyKey);
    }

    public final <T> T getDependency(Class<T> cls) {
        return (T) getDependencyInner(cls);
    }

    public final <T> T getDependencyInner(Object obj) {
        Object obj2;
        synchronized (this) {
            Object obj3 = this.mDependencies.get(obj);
            obj2 = obj3;
            if (obj3 == null) {
                obj2 = createDependency(obj);
                this.mDependencies.put(obj, obj2);
            }
        }
        return (T) obj2;
    }

    public void start() {
        ArrayMap<Object, LazyDependencyCreator> arrayMap = this.mProviders;
        DependencyKey<Handler> dependencyKey = TIME_TICK_HANDLER;
        final Lazy<Handler> lazy = this.mTimeTickHandler;
        Objects.requireNonNull(lazy);
        arrayMap.put(dependencyKey, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap2 = this.mProviders;
        DependencyKey<Looper> dependencyKey2 = BG_LOOPER;
        final Lazy<Looper> lazy2 = this.mBgLooper;
        Objects.requireNonNull(lazy2);
        arrayMap2.put(dependencyKey2, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy2.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap3 = this.mProviders;
        DependencyKey<Looper> dependencyKey3 = MAIN_LOOPER;
        final Lazy<Looper> lazy3 = this.mMainLooper;
        Objects.requireNonNull(lazy3);
        arrayMap3.put(dependencyKey3, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy3.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap4 = this.mProviders;
        DependencyKey<Handler> dependencyKey4 = MAIN_HANDLER;
        final Lazy<Handler> lazy4 = this.mMainHandler;
        Objects.requireNonNull(lazy4);
        arrayMap4.put(dependencyKey4, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy4.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap5 = this.mProviders;
        DependencyKey<Executor> dependencyKey5 = MAIN_EXECUTOR;
        final Lazy<Executor> lazy5 = this.mMainExecutor;
        Objects.requireNonNull(lazy5);
        arrayMap5.put(dependencyKey5, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy5.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap6 = this.mProviders;
        DependencyKey<Executor> dependencyKey6 = BACKGROUND_EXECUTOR;
        final Lazy<Executor> lazy6 = this.mBackgroundExecutor;
        Objects.requireNonNull(lazy6);
        arrayMap6.put(dependencyKey6, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy6.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap7 = this.mProviders;
        final Lazy<ActivityStarter> lazy7 = this.mActivityStarter;
        Objects.requireNonNull(lazy7);
        arrayMap7.put(ActivityStarter.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy7.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap8 = this.mProviders;
        final Lazy<BroadcastDispatcher> lazy8 = this.mBroadcastDispatcher;
        Objects.requireNonNull(lazy8);
        arrayMap8.put(BroadcastDispatcher.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy8.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap9 = this.mProviders;
        final Lazy<AsyncSensorManager> lazy9 = this.mAsyncSensorManager;
        Objects.requireNonNull(lazy9);
        arrayMap9.put(AsyncSensorManager.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy9.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap10 = this.mProviders;
        final Lazy<BluetoothController> lazy10 = this.mBluetoothController;
        Objects.requireNonNull(lazy10);
        arrayMap10.put(BluetoothController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy10.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap11 = this.mProviders;
        final Lazy<SensorPrivacyManager> lazy11 = this.mSensorPrivacyManager;
        Objects.requireNonNull(lazy11);
        arrayMap11.put(SensorPrivacyManager.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy11.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap12 = this.mProviders;
        final Lazy<LocationController> lazy12 = this.mLocationController;
        Objects.requireNonNull(lazy12);
        arrayMap12.put(LocationController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy12.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap13 = this.mProviders;
        final Lazy<RotationLockController> lazy13 = this.mRotationLockController;
        Objects.requireNonNull(lazy13);
        arrayMap13.put(RotationLockController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy13.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap14 = this.mProviders;
        final Lazy<ZenModeController> lazy14 = this.mZenModeController;
        Objects.requireNonNull(lazy14);
        arrayMap14.put(ZenModeController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy14.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap15 = this.mProviders;
        final Lazy<HotspotController> lazy15 = this.mHotspotController;
        Objects.requireNonNull(lazy15);
        arrayMap15.put(HotspotController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy15.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap16 = this.mProviders;
        final Lazy<CastController> lazy16 = this.mCastController;
        Objects.requireNonNull(lazy16);
        arrayMap16.put(CastController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy16.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap17 = this.mProviders;
        final Lazy<FlashlightController> lazy17 = this.mFlashlightController;
        Objects.requireNonNull(lazy17);
        arrayMap17.put(FlashlightController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy17.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap18 = this.mProviders;
        final Lazy<KeyguardStateController> lazy18 = this.mKeyguardMonitor;
        Objects.requireNonNull(lazy18);
        arrayMap18.put(KeyguardStateController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy18.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap19 = this.mProviders;
        final Lazy<KeyguardUpdateMonitor> lazy19 = this.mKeyguardUpdateMonitor;
        Objects.requireNonNull(lazy19);
        arrayMap19.put(KeyguardUpdateMonitor.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy19.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap20 = this.mProviders;
        final Lazy<UserSwitcherController> lazy20 = this.mUserSwitcherController;
        Objects.requireNonNull(lazy20);
        arrayMap20.put(UserSwitcherController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy20.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap21 = this.mProviders;
        final Lazy<UserInfoController> lazy21 = this.mUserInfoController;
        Objects.requireNonNull(lazy21);
        arrayMap21.put(UserInfoController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy21.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap22 = this.mProviders;
        final Lazy<NightDisplayListener> lazy22 = this.mNightDisplayListener;
        Objects.requireNonNull(lazy22);
        arrayMap22.put(NightDisplayListener.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy22.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap23 = this.mProviders;
        final Lazy<ReduceBrightColorsController> lazy23 = this.mReduceBrightColorsController;
        Objects.requireNonNull(lazy23);
        arrayMap23.put(ReduceBrightColorsController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy23.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap24 = this.mProviders;
        final Lazy<ManagedProfileController> lazy24 = this.mManagedProfileController;
        Objects.requireNonNull(lazy24);
        arrayMap24.put(ManagedProfileController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy24.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap25 = this.mProviders;
        final Lazy<NextAlarmController> lazy25 = this.mNextAlarmController;
        Objects.requireNonNull(lazy25);
        arrayMap25.put(NextAlarmController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy25.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap26 = this.mProviders;
        final Lazy<DataSaverController> lazy26 = this.mDataSaverController;
        Objects.requireNonNull(lazy26);
        arrayMap26.put(DataSaverController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy26.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap27 = this.mProviders;
        final Lazy<AccessibilityController> lazy27 = this.mAccessibilityController;
        Objects.requireNonNull(lazy27);
        arrayMap27.put(AccessibilityController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy27.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap28 = this.mProviders;
        final Lazy<DeviceProvisionedController> lazy28 = this.mDeviceProvisionedController;
        Objects.requireNonNull(lazy28);
        arrayMap28.put(DeviceProvisionedController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy28.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap29 = this.mProviders;
        final Lazy<PluginManager> lazy29 = this.mPluginManager;
        Objects.requireNonNull(lazy29);
        arrayMap29.put(PluginManager.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy29.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap30 = this.mProviders;
        final Lazy<AssistManager> lazy30 = this.mAssistManager;
        Objects.requireNonNull(lazy30);
        arrayMap30.put(AssistManager.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy30.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap31 = this.mProviders;
        final Lazy<SecurityController> lazy31 = this.mSecurityController;
        Objects.requireNonNull(lazy31);
        arrayMap31.put(SecurityController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy31.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap32 = this.mProviders;
        final Lazy<LeakDetector> lazy32 = this.mLeakDetector;
        Objects.requireNonNull(lazy32);
        arrayMap32.put(LeakDetector.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy32.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap33 = this.mProviders;
        DependencyKey<String> dependencyKey7 = LEAK_REPORT_EMAIL;
        final Lazy<String> lazy33 = this.mLeakReportEmail;
        Objects.requireNonNull(lazy33);
        arrayMap33.put(dependencyKey7, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy33.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap34 = this.mProviders;
        final Lazy<LeakReporter> lazy34 = this.mLeakReporter;
        Objects.requireNonNull(lazy34);
        arrayMap34.put(LeakReporter.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy34.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap35 = this.mProviders;
        final Lazy<GarbageMonitor> lazy35 = this.mGarbageMonitor;
        Objects.requireNonNull(lazy35);
        arrayMap35.put(GarbageMonitor.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy35.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap36 = this.mProviders;
        final Lazy<TunerService> lazy36 = this.mTunerService;
        Objects.requireNonNull(lazy36);
        arrayMap36.put(TunerService.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy36.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap37 = this.mProviders;
        final Lazy<NotificationShadeWindowController> lazy37 = this.mNotificationShadeWindowController;
        Objects.requireNonNull(lazy37);
        arrayMap37.put(NotificationShadeWindowController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy37.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap38 = this.mProviders;
        final Lazy<StatusBarWindowController> lazy38 = this.mTempStatusBarWindowController;
        Objects.requireNonNull(lazy38);
        arrayMap38.put(StatusBarWindowController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy38.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap39 = this.mProviders;
        final Lazy<DarkIconDispatcher> lazy39 = this.mDarkIconDispatcher;
        Objects.requireNonNull(lazy39);
        arrayMap39.put(DarkIconDispatcher.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy39.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap40 = this.mProviders;
        final Lazy<ConfigurationController> lazy40 = this.mConfigurationController;
        Objects.requireNonNull(lazy40);
        arrayMap40.put(ConfigurationController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy40.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap41 = this.mProviders;
        final Lazy<StatusBarIconController> lazy41 = this.mStatusBarIconController;
        Objects.requireNonNull(lazy41);
        arrayMap41.put(StatusBarIconController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy41.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap42 = this.mProviders;
        final Lazy<ScreenLifecycle> lazy42 = this.mScreenLifecycle;
        Objects.requireNonNull(lazy42);
        arrayMap42.put(ScreenLifecycle.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy42.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap43 = this.mProviders;
        final Lazy<WakefulnessLifecycle> lazy43 = this.mWakefulnessLifecycle;
        Objects.requireNonNull(lazy43);
        arrayMap43.put(WakefulnessLifecycle.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy43.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap44 = this.mProviders;
        final Lazy<FragmentService> lazy44 = this.mFragmentService;
        Objects.requireNonNull(lazy44);
        arrayMap44.put(FragmentService.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy44.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap45 = this.mProviders;
        final Lazy<ExtensionController> lazy45 = this.mExtensionController;
        Objects.requireNonNull(lazy45);
        arrayMap45.put(ExtensionController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy45.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap46 = this.mProviders;
        final Lazy<PluginDependencyProvider> lazy46 = this.mPluginDependencyProvider;
        Objects.requireNonNull(lazy46);
        arrayMap46.put(PluginDependencyProvider.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy46.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap47 = this.mProviders;
        final Lazy<LocalBluetoothManager> lazy47 = this.mLocalBluetoothManager;
        Objects.requireNonNull(lazy47);
        arrayMap47.put(LocalBluetoothManager.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy47.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap48 = this.mProviders;
        final Lazy<VolumeDialogController> lazy48 = this.mVolumeDialogController;
        Objects.requireNonNull(lazy48);
        arrayMap48.put(VolumeDialogController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy48.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap49 = this.mProviders;
        final Lazy<MetricsLogger> lazy49 = this.mMetricsLogger;
        Objects.requireNonNull(lazy49);
        arrayMap49.put(MetricsLogger.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy49.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap50 = this.mProviders;
        final Lazy<AccessibilityManagerWrapper> lazy50 = this.mAccessibilityManagerWrapper;
        Objects.requireNonNull(lazy50);
        arrayMap50.put(AccessibilityManagerWrapper.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy50.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap51 = this.mProviders;
        final Lazy<SysuiColorExtractor> lazy51 = this.mSysuiColorExtractor;
        Objects.requireNonNull(lazy51);
        arrayMap51.put(SysuiColorExtractor.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy51.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap52 = this.mProviders;
        final Lazy<TunablePadding.TunablePaddingService> lazy52 = this.mTunablePaddingService;
        Objects.requireNonNull(lazy52);
        arrayMap52.put(TunablePadding.TunablePaddingService.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy52.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap53 = this.mProviders;
        final Lazy<ForegroundServiceController> lazy53 = this.mForegroundServiceController;
        Objects.requireNonNull(lazy53);
        arrayMap53.put(ForegroundServiceController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy53.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap54 = this.mProviders;
        final Lazy<UiOffloadThread> lazy54 = this.mUiOffloadThread;
        Objects.requireNonNull(lazy54);
        arrayMap54.put(UiOffloadThread.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy54.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap55 = this.mProviders;
        final Lazy<PowerUI.WarningsUI> lazy55 = this.mWarningsUI;
        Objects.requireNonNull(lazy55);
        arrayMap55.put(PowerUI.WarningsUI.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy55.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap56 = this.mProviders;
        final Lazy<LightBarController> lazy56 = this.mLightBarController;
        Objects.requireNonNull(lazy56);
        arrayMap56.put(LightBarController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy56.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap57 = this.mProviders;
        final Lazy<IWindowManager> lazy57 = this.mIWindowManager;
        Objects.requireNonNull(lazy57);
        arrayMap57.put(IWindowManager.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy57.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap58 = this.mProviders;
        final Lazy<OverviewProxyService> lazy58 = this.mOverviewProxyService;
        Objects.requireNonNull(lazy58);
        arrayMap58.put(OverviewProxyService.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy58.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap59 = this.mProviders;
        final Lazy<NavigationModeController> lazy59 = this.mNavBarModeController;
        Objects.requireNonNull(lazy59);
        arrayMap59.put(NavigationModeController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy59.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap60 = this.mProviders;
        final Lazy<AccessibilityButtonModeObserver> lazy60 = this.mAccessibilityButtonModeObserver;
        Objects.requireNonNull(lazy60);
        arrayMap60.put(AccessibilityButtonModeObserver.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy60.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap61 = this.mProviders;
        final Lazy<AccessibilityButtonTargetsObserver> lazy61 = this.mAccessibilityButtonListController;
        Objects.requireNonNull(lazy61);
        arrayMap61.put(AccessibilityButtonTargetsObserver.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy61.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap62 = this.mProviders;
        final Lazy<EnhancedEstimates> lazy62 = this.mEnhancedEstimates;
        Objects.requireNonNull(lazy62);
        arrayMap62.put(EnhancedEstimates.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy62.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap63 = this.mProviders;
        final Lazy<VibratorHelper> lazy63 = this.mVibratorHelper;
        Objects.requireNonNull(lazy63);
        arrayMap63.put(VibratorHelper.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy63.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap64 = this.mProviders;
        final Lazy<IStatusBarService> lazy64 = this.mIStatusBarService;
        Objects.requireNonNull(lazy64);
        arrayMap64.put(IStatusBarService.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy64.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap65 = this.mProviders;
        final Lazy<DisplayMetrics> lazy65 = this.mDisplayMetrics;
        Objects.requireNonNull(lazy65);
        arrayMap65.put(DisplayMetrics.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy65.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap66 = this.mProviders;
        final Lazy<LockscreenGestureLogger> lazy66 = this.mLockscreenGestureLogger;
        Objects.requireNonNull(lazy66);
        arrayMap66.put(LockscreenGestureLogger.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy66.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap67 = this.mProviders;
        final Lazy<ShadeController> lazy67 = this.mShadeController;
        Objects.requireNonNull(lazy67);
        arrayMap67.put(ShadeController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy67.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap68 = this.mProviders;
        final Lazy<NotificationRemoteInputManager.Callback> lazy68 = this.mNotificationRemoteInputManagerCallback;
        Objects.requireNonNull(lazy68);
        arrayMap68.put(NotificationRemoteInputManager.Callback.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy68.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap69 = this.mProviders;
        final Lazy<AppOpsController> lazy69 = this.mAppOpsController;
        Objects.requireNonNull(lazy69);
        arrayMap69.put(AppOpsController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy69.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap70 = this.mProviders;
        final Lazy<NavigationBarController> lazy70 = this.mNavigationBarController;
        Objects.requireNonNull(lazy70);
        arrayMap70.put(NavigationBarController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy70.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap71 = this.mProviders;
        final Lazy<AccessibilityFloatingMenuController> lazy71 = this.mAccessibilityFloatingMenuController;
        Objects.requireNonNull(lazy71);
        arrayMap71.put(AccessibilityFloatingMenuController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy71.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap72 = this.mProviders;
        final Lazy<StatusBarStateController> lazy72 = this.mStatusBarStateController;
        Objects.requireNonNull(lazy72);
        arrayMap72.put(StatusBarStateController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy72.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap73 = this.mProviders;
        final Lazy<NotificationLockscreenUserManager> lazy73 = this.mNotificationLockscreenUserManager;
        Objects.requireNonNull(lazy73);
        arrayMap73.put(NotificationLockscreenUserManager.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy73.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap74 = this.mProviders;
        final Lazy<NotificationMediaManager> lazy74 = this.mNotificationMediaManager;
        Objects.requireNonNull(lazy74);
        arrayMap74.put(NotificationMediaManager.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy74.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap75 = this.mProviders;
        final Lazy<NotificationGutsManager> lazy75 = this.mNotificationGutsManager;
        Objects.requireNonNull(lazy75);
        arrayMap75.put(NotificationGutsManager.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy75.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap76 = this.mProviders;
        final Lazy<NotificationRemoteInputManager> lazy76 = this.mNotificationRemoteInputManager;
        Objects.requireNonNull(lazy76);
        arrayMap76.put(NotificationRemoteInputManager.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy76.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap77 = this.mProviders;
        final Lazy<SmartReplyConstants> lazy77 = this.mSmartReplyConstants;
        Objects.requireNonNull(lazy77);
        arrayMap77.put(SmartReplyConstants.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy77.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap78 = this.mProviders;
        final Lazy<NotificationListener> lazy78 = this.mNotificationListener;
        Objects.requireNonNull(lazy78);
        arrayMap78.put(NotificationListener.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy78.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap79 = this.mProviders;
        final Lazy<NotificationLogger> lazy79 = this.mNotificationLogger;
        Objects.requireNonNull(lazy79);
        arrayMap79.put(NotificationLogger.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy79.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap80 = this.mProviders;
        final Lazy<KeyguardDismissUtil> lazy80 = this.mKeyguardDismissUtil;
        Objects.requireNonNull(lazy80);
        arrayMap80.put(KeyguardDismissUtil.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy80.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap81 = this.mProviders;
        final Lazy<SmartReplyController> lazy81 = this.mSmartReplyController;
        Objects.requireNonNull(lazy81);
        arrayMap81.put(SmartReplyController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy81.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap82 = this.mProviders;
        final Lazy<RemoteInputQuickSettingsDisabler> lazy82 = this.mRemoteInputQuickSettingsDisabler;
        Objects.requireNonNull(lazy82);
        arrayMap82.put(RemoteInputQuickSettingsDisabler.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy82.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap83 = this.mProviders;
        final Lazy<ClockManager> lazy83 = this.mClockManager;
        Objects.requireNonNull(lazy83);
        arrayMap83.put(ClockManager.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy83.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap84 = this.mProviders;
        final Lazy<PrivacyItemController> lazy84 = this.mPrivacyItemController;
        Objects.requireNonNull(lazy84);
        arrayMap84.put(PrivacyItemController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy84.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap85 = this.mProviders;
        final Lazy<ActivityManagerWrapper> lazy85 = this.mActivityManagerWrapper;
        Objects.requireNonNull(lazy85);
        arrayMap85.put(ActivityManagerWrapper.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy85.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap86 = this.mProviders;
        final Lazy<DevicePolicyManagerWrapper> lazy86 = this.mDevicePolicyManagerWrapper;
        Objects.requireNonNull(lazy86);
        arrayMap86.put(DevicePolicyManagerWrapper.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy86.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap87 = this.mProviders;
        final Lazy<PackageManagerWrapper> lazy87 = this.mPackageManagerWrapper;
        Objects.requireNonNull(lazy87);
        arrayMap87.put(PackageManagerWrapper.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy87.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap88 = this.mProviders;
        final Lazy<SensorPrivacyController> lazy88 = this.mSensorPrivacyController;
        Objects.requireNonNull(lazy88);
        arrayMap88.put(SensorPrivacyController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy88.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap89 = this.mProviders;
        final Lazy<DockManager> lazy89 = this.mDockManager;
        Objects.requireNonNull(lazy89);
        arrayMap89.put(DockManager.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy89.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap90 = this.mProviders;
        final Lazy<INotificationManager> lazy90 = this.mINotificationManager;
        Objects.requireNonNull(lazy90);
        arrayMap90.put(INotificationManager.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy90.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap91 = this.mProviders;
        final Lazy<SysUiState> lazy91 = this.mSysUiStateFlagsContainer;
        Objects.requireNonNull(lazy91);
        arrayMap91.put(SysUiState.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy91.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap92 = this.mProviders;
        final Lazy<AlarmManager> lazy92 = this.mAlarmManager;
        Objects.requireNonNull(lazy92);
        arrayMap92.put(AlarmManager.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy92.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap93 = this.mProviders;
        final Lazy<KeyguardSecurityModel> lazy93 = this.mKeyguardSecurityModel;
        Objects.requireNonNull(lazy93);
        arrayMap93.put(KeyguardSecurityModel.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy93.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap94 = this.mProviders;
        final Lazy<DozeParameters> lazy94 = this.mDozeParameters;
        Objects.requireNonNull(lazy94);
        arrayMap94.put(DozeParameters.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy94.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap95 = this.mProviders;
        final Lazy<IWallpaperManager> lazy95 = this.mWallpaperManager;
        Objects.requireNonNull(lazy95);
        arrayMap95.put(IWallpaperManager.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy95.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap96 = this.mProviders;
        final Lazy<CommandQueue> lazy96 = this.mCommandQueue;
        Objects.requireNonNull(lazy96);
        arrayMap96.put(CommandQueue.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy96.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap97 = this.mProviders;
        final Lazy<ProtoTracer> lazy97 = this.mProtoTracer;
        Objects.requireNonNull(lazy97);
        arrayMap97.put(ProtoTracer.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy97.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap98 = this.mProviders;
        final Lazy<DeviceConfigProxy> lazy98 = this.mDeviceConfigProxy;
        Objects.requireNonNull(lazy98);
        arrayMap98.put(DeviceConfigProxy.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy98.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap99 = this.mProviders;
        final Lazy<TelephonyListenerManager> lazy99 = this.mTelephonyListenerManager;
        Objects.requireNonNull(lazy99);
        arrayMap99.put(TelephonyListenerManager.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy99.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap100 = this.mProviders;
        final Lazy<AutoHideController> lazy100 = this.mAutoHideController;
        Objects.requireNonNull(lazy100);
        arrayMap100.put(AutoHideController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy100.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap101 = this.mProviders;
        final Lazy<RecordingController> lazy101 = this.mRecordingController;
        Objects.requireNonNull(lazy101);
        arrayMap101.put(RecordingController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy101.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap102 = this.mProviders;
        final Lazy<MediaOutputDialogFactory> lazy102 = this.mMediaOutputDialogFactory;
        Objects.requireNonNull(lazy102);
        arrayMap102.put(MediaOutputDialogFactory.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy102.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap103 = this.mProviders;
        final Lazy<SystemStatusAnimationScheduler> lazy103 = this.mSystemStatusAnimationSchedulerLazy;
        Objects.requireNonNull(lazy103);
        arrayMap103.put(SystemStatusAnimationScheduler.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy103.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap104 = this.mProviders;
        final Lazy<PrivacyDotViewController> lazy104 = this.mPrivacyDotViewControllerLazy;
        Objects.requireNonNull(lazy104);
        arrayMap104.put(PrivacyDotViewController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy104.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap105 = this.mProviders;
        final Lazy<InternetDialogFactory> lazy105 = this.mInternetDialogFactory;
        Objects.requireNonNull(lazy105);
        arrayMap105.put(InternetDialogFactory.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy105.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap106 = this.mProviders;
        final Lazy<EdgeBackGestureHandler.Factory> lazy106 = this.mEdgeBackGestureHandlerFactoryLazy;
        Objects.requireNonNull(lazy106);
        arrayMap106.put(EdgeBackGestureHandler.Factory.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy106.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap107 = this.mProviders;
        final Lazy<UiEventLogger> lazy107 = this.mUiEventLogger;
        Objects.requireNonNull(lazy107);
        arrayMap107.put(UiEventLogger.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy107.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap108 = this.mProviders;
        final Lazy<FeatureFlags> lazy108 = this.mFeatureFlagsLazy;
        Objects.requireNonNull(lazy108);
        arrayMap108.put(FeatureFlags.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy108.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap109 = this.mProviders;
        final Lazy<StatusBarContentInsetsProvider> lazy109 = this.mContentInsetsProviderLazy;
        Objects.requireNonNull(lazy109);
        arrayMap109.put(StatusBarContentInsetsProvider.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy109.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap110 = this.mProviders;
        final Lazy<NotificationSectionsManager> lazy110 = this.mNotificationSectionsManagerLazy;
        Objects.requireNonNull(lazy110);
        arrayMap110.put(NotificationSectionsManager.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy110.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap111 = this.mProviders;
        final Lazy<ScreenOffAnimationController> lazy111 = this.mScreenOffAnimationController;
        Objects.requireNonNull(lazy111);
        arrayMap111.put(ScreenOffAnimationController.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy111.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap112 = this.mProviders;
        final Lazy<AmbientState> lazy112 = this.mAmbientStateLazy;
        Objects.requireNonNull(lazy112);
        arrayMap112.put(AmbientState.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy112.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap113 = this.mProviders;
        final Lazy<GroupMembershipManager> lazy113 = this.mGroupMembershipManagerLazy;
        Objects.requireNonNull(lazy113);
        arrayMap113.put(GroupMembershipManager.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy113.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap114 = this.mProviders;
        final Lazy<GroupExpansionManager> lazy114 = this.mGroupExpansionManagerLazy;
        Objects.requireNonNull(lazy114);
        arrayMap114.put(GroupExpansionManager.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy114.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap115 = this.mProviders;
        final Lazy<SystemUIDialogManager> lazy115 = this.mSystemUIDialogManagerLazy;
        Objects.requireNonNull(lazy115);
        arrayMap115.put(SystemUIDialogManager.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy115.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap116 = this.mProviders;
        final Lazy<DialogLaunchAnimator> lazy116 = this.mDialogLaunchAnimatorLazy;
        Objects.requireNonNull(lazy116);
        arrayMap116.put(DialogLaunchAnimator.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy116.get();
            }
        });
        ArrayMap<Object, LazyDependencyCreator> arrayMap117 = this.mProviders;
        final Lazy<UserTracker> lazy117 = this.mUserTrackerLazy;
        Objects.requireNonNull(lazy117);
        arrayMap117.put(UserTracker.class, new LazyDependencyCreator() { // from class: com.android.systemui.Dependency$$ExternalSyntheticLambda0
            @Override // com.android.systemui.Dependency.LazyDependencyCreator
            public final Object createDependency() {
                return lazy117.get();
            }
        });
        setInstance(this);
    }
}