package com.android.systemui.navigationbar;

import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.app.StatusBarManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.telecom.TelecomManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.InsetsFrameProvider;
import android.view.InsetsState;
import android.view.InsetsVisibilities;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
import androidx.mediarouter.media.MediaRoute2Provider$$ExternalSyntheticLambda0;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.accessibility.dialog.AccessibilityButtonChooserActivity;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.statusbar.LetterboxDetails;
import com.android.internal.util.LatencyTracker;
import com.android.internal.view.AppearanceRegion;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.R$dimen;
import com.android.systemui.R$id;
import com.android.systemui.R$string;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda9;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.NavBarHelper;
import com.android.systemui.navigationbar.NavigationBar;
import com.android.systemui.navigationbar.NavigationBarTransitions;
import com.android.systemui.navigationbar.NavigationBarView;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.buttons.ButtonDispatcher;
import com.android.systemui.navigationbar.buttons.DeadZone;
import com.android.systemui.navigationbar.buttons.KeyButtonView;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.recents.Recents;
import com.android.systemui.settings.UserContextProvider;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shared.navigationbar.RegionSamplingHelper;
import com.android.systemui.shared.recents.utilities.Utilities;
import com.android.systemui.shared.rotation.RotationButtonController;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.statusbar.AutoHideUiElement;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.NotificationShadeDepthController;
import com.android.systemui.statusbar.phone.AutoHideController;
import com.android.systemui.statusbar.phone.BarTransitions;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.util.DeviceConfigProxy;
import com.android.systemui.util.Utils;
import com.android.systemui.util.ViewController;
import com.android.wm.shell.back.BackAnimation;
import com.android.wm.shell.pip.Pip;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/NavigationBar.class */
public class NavigationBar extends ViewController<NavigationBarView> implements CommandQueue.Callbacks {
    public final AccessibilityManager mAccessibilityManager;
    public int mAppearance;
    public final Lazy<AssistManager> mAssistManagerLazy;
    public final Runnable mAutoDim;
    public AutoHideController mAutoHideController;
    public final AutoHideController.Factory mAutoHideControllerFactory;
    public final AutoHideUiElement mAutoHideUiElement;
    public final Optional<BackAnimation> mBackAnimation;
    public int mBehavior;
    public final Lazy<Optional<CentralSurfaces>> mCentralSurfacesOptionalLazy;
    public final CommandQueue mCommandQueue;
    public final Context mContext;
    public final DeadZone mDeadZone;
    public final NotificationShadeDepthController.DepthListener mDepthListener;
    public final DeviceConfigProxy mDeviceConfigProxy;
    public final DeviceProvisionedController mDeviceProvisionedController;
    public int mDisabledFlags1;
    public int mDisabledFlags2;
    public int mDisplayId;
    public final EdgeBackGestureHandler mEdgeBackGestureHandler;
    public final Runnable mEnableLayoutTransitions;
    public NavigationBarFrame mFrame;
    public final Handler mHandler;
    public boolean mHomeBlockedThisTouch;
    public Optional<Long> mHomeButtonLongPressDurationMs;
    public boolean mImeVisible;
    public final InputMethodManager mInputMethodManager;
    public boolean mIsOnDefaultDisplay;
    public long mLastLockToAppLongPress;
    public int mLayoutDirection;
    public LightBarController mLightBarController;
    public final LightBarController.Factory mLightBarControllerFactory;
    public Locale mLocale;
    public boolean mLongPressHomeEnabled;
    public final AutoHideController mMainAutoHideController;
    public final LightBarController mMainLightBarController;
    public final MetricsLogger mMetricsLogger;
    public final NavigationModeController.ModeChangedListener mModeChangedListener;
    public final NavBarHelper mNavBarHelper;
    public int mNavBarMode;
    public final int mNavColorSampleMargin;
    public final NavBarHelper.NavbarTaskbarStateUpdater mNavbarTaskbarStateUpdater;
    public final NavigationBarTransitions mNavigationBarTransitions;
    public int mNavigationBarWindowState;
    public int mNavigationIconHints;
    public final NavigationModeController mNavigationModeController;
    public final NotificationRemoteInputManager mNotificationRemoteInputManager;
    public final NotificationShadeDepthController mNotificationShadeDepthController;
    public final ViewTreeObserver.OnComputeInternalInsetsListener mOnComputeInternalInsetsListener;
    public final DeviceConfig.OnPropertiesChangedListener mOnPropertiesChangedListener;
    public final Runnable mOnVariableDurationHomeLongClick;
    public final OverviewProxyService.OverviewProxyListener mOverviewProxyListener;
    public final OverviewProxyService mOverviewProxyService;
    public final Optional<Pip> mPipOptional;
    public final Optional<Recents> mRecentsOptional;
    public final RegionSamplingHelper mRegionSamplingHelper;
    public final Consumer<Integer> mRotationWatcher;
    public final Rect mSamplingBounds;
    public final Bundle mSavedState;
    public final ShadeController mShadeController;
    public final StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public final StatusBarStateController mStatusBarStateController;
    public final ViewRootImpl.SurfaceChangedCallback mSurfaceChangedCallback;
    public final SysUiState mSysUiFlagsContainer;
    public final Optional<TelecomManager> mTelecomManagerOptional;
    public final Gefingerpoken mTouchHandler;
    public boolean mTransientShown;
    public boolean mTransientShownFromGestureOnSystemBar;
    public int mTransitionMode;
    public final UiEventLogger mUiEventLogger;
    public final UserTracker.Callback mUserChangedCallback;
    public final UserContextProvider mUserContextProvider;
    public final UserTracker mUserTracker;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public final WakefulnessLifecycle.Observer mWakefulnessObserver;
    public final WindowManager mWindowManager;

    /* renamed from: com.android.systemui.navigationbar.NavigationBar$4 */
    /* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/NavigationBar$4.class */
    public class AnonymousClass4 implements DeviceConfig.OnPropertiesChangedListener {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.NavigationBar$4$$ExternalSyntheticLambda0.test(java.lang.Object):boolean] */
        public static /* synthetic */ boolean $r8$lambda$HiXTXgi37lByfqq_GOqob5nCOm0(Long l) {
            return lambda$onPropertiesChanged$0(l);
        }

        public AnonymousClass4() {
            NavigationBar.this = r4;
        }

        public static /* synthetic */ boolean lambda$onPropertiesChanged$0(Long l) {
            return l.longValue() != 0;
        }

        public void onPropertiesChanged(DeviceConfig.Properties properties) {
            if (properties.getKeyset().contains("home_button_long_press_duration_ms")) {
                NavigationBar.this.mHomeButtonLongPressDurationMs = Optional.of(Long.valueOf(properties.getLong("home_button_long_press_duration_ms", 0L))).filter(new Predicate() { // from class: com.android.systemui.navigationbar.NavigationBar$4$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return NavigationBar.AnonymousClass4.$r8$lambda$HiXTXgi37lByfqq_GOqob5nCOm0((Long) obj);
                    }
                });
                if (((ViewController) NavigationBar.this).mView != null) {
                    NavigationBar.this.reconfigureHomeLongClick();
                }
            }
        }
    }

    @VisibleForTesting
    /* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/NavigationBar$NavBarActionEvent.class */
    public enum NavBarActionEvent implements UiEventLogger.UiEventEnum {
        NAVBAR_ASSIST_LONGPRESS(550);
        
        private final int mId;

        NavBarActionEvent(int i) {
            this.mId = i;
        }

        public int getId() {
            return this.mId;
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda9.run():void] */
    /* renamed from: $r8$lambda$-2dOL7JH8PTcwu4od8_x9VF3EiQ */
    public static /* synthetic */ void m3398$r8$lambda$2dOL7JH8PTcwu4od8_x9VF3EiQ(NavigationBar navigationBar) {
        navigationBar.lambda$new$2();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda10.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$1ikVut1IKywCdr39DwIhxxpGYBw(NavigationBar navigationBar, Integer num) {
        navigationBar.lambda$new$7(num);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda8.run():void] */
    public static /* synthetic */ void $r8$lambda$51uSrByCGmAv1mf5SuHfy8PrEF4(NavigationBar navigationBar) {
        navigationBar.lambda$new$1();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda11.onComputeInternalInsets(android.view.ViewTreeObserver$InternalInsetsInfo):void] */
    /* renamed from: $r8$lambda$CdZ5sCBkrQcLIS-mmVdzRtqzZEM */
    public static /* synthetic */ void m3399$r8$lambda$CdZ5sCBkrQcLISmmVdzRtqzZEM(NavigationBar navigationBar, ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
        navigationBar.lambda$new$3(internalInsetsInfo);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda5.test(java.lang.Object):boolean] */
    /* renamed from: $r8$lambda$EQzxHXfbVmOfriudM3PL-AwFMWU */
    public static /* synthetic */ boolean m3400$r8$lambda$EQzxHXfbVmOfriudM3PLAwFMWU(Long l) {
        return lambda$onInit$4(l);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda22.onClick(android.view.View):void] */
    /* renamed from: $r8$lambda$GMUrRGBTIpUp-9f8LwOrFGf5HW8 */
    public static /* synthetic */ void m3401$r8$lambda$GMUrRGBTIpUp9f8LwOrFGf5HW8(NavigationBar navigationBar, View view) {
        navigationBar.onAccessibilityClick(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda18.onLongClick(android.view.View):boolean] */
    public static /* synthetic */ boolean $r8$lambda$HPyc8leQZCHqPWbDVB3bTeaa9y4(NavigationBar navigationBar, View view) {
        return navigationBar.onLongPressBackHome(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda23.onLongClick(android.view.View):boolean] */
    public static /* synthetic */ boolean $r8$lambda$IJ8n67jK2SaOovl5IoUoNpdsAns(NavigationBar navigationBar, View view) {
        return navigationBar.onAccessibilityLongClick(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda20.onTouch(android.view.View, android.view.MotionEvent):boolean] */
    public static /* synthetic */ boolean $r8$lambda$J0Eup4yr5Un7SiVZkodFWTR5yRU(NavigationBar navigationBar, View view, MotionEvent motionEvent) {
        return navigationBar.onRecentsTouch(view, motionEvent);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda17.onLongClick(android.view.View):boolean] */
    public static /* synthetic */ boolean $r8$lambda$K7I0ugGK4L7biKOJxfyYbDfOVmU(NavigationBar navigationBar, View view) {
        return navigationBar.onLongPressBackRecents(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda19.onClick(android.view.View):void] */
    public static /* synthetic */ void $r8$lambda$Lc81b84naHtaxRAuqpMeFxBuvqs(NavigationBar navigationBar, View view) {
        navigationBar.onRecentsClick(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda24.onClick(android.view.View):void] */
    /* renamed from: $r8$lambda$SgxIBUBj_3hUjZ4QyQB1Bu-OclU */
    public static /* synthetic */ void m3402$r8$lambda$SgxIBUBj_3hUjZ4QyQB1BuOclU(NavigationBar navigationBar, View view) {
        navigationBar.onImeSwitcherClick(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda7.run():void] */
    public static /* synthetic */ void $r8$lambda$T2khzvKue7NVudfy9B8kqnm8JdU(NavigationBar navigationBar) {
        navigationBar.lambda$new$0();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda13.onTouch(android.view.View, android.view.MotionEvent):boolean] */
    /* renamed from: $r8$lambda$h1tdKzbIORPPk7SyQu-hJ3_4vdc */
    public static /* synthetic */ boolean m3403$r8$lambda$h1tdKzbIORPPk7SyQuhJ3_4vdc(NavigationBar navigationBar, View view, MotionEvent motionEvent) {
        return navigationBar.onNavigationTouch(view, motionEvent);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda0.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$hdo7wkgWMUqO5ETDcqTRbK1CmO8(NavigationBar navigationBar, Long l) {
        navigationBar.lambda$onHomeTouch$6(l);
    }

    public NavigationBar(NavigationBarView navigationBarView, NavigationBarFrame navigationBarFrame, Bundle bundle, Context context, WindowManager windowManager, Lazy<AssistManager> lazy, AccessibilityManager accessibilityManager, DeviceProvisionedController deviceProvisionedController, MetricsLogger metricsLogger, OverviewProxyService overviewProxyService, NavigationModeController navigationModeController, StatusBarStateController statusBarStateController, StatusBarKeyguardViewManager statusBarKeyguardViewManager, SysUiState sysUiState, UserTracker userTracker, CommandQueue commandQueue, Optional<Pip> optional, Optional<Recents> optional2, Lazy<Optional<CentralSurfaces>> lazy2, ShadeController shadeController, NotificationRemoteInputManager notificationRemoteInputManager, NotificationShadeDepthController notificationShadeDepthController, Handler handler, Executor executor, Executor executor2, UiEventLogger uiEventLogger, NavBarHelper navBarHelper, LightBarController lightBarController, LightBarController.Factory factory, AutoHideController autoHideController, AutoHideController.Factory factory2, Optional<TelecomManager> optional3, InputMethodManager inputMethodManager, DeadZone deadZone, DeviceConfigProxy deviceConfigProxy, NavigationBarTransitions navigationBarTransitions, EdgeBackGestureHandler edgeBackGestureHandler, Optional<BackAnimation> optional4, UserContextProvider userContextProvider, WakefulnessLifecycle wakefulnessLifecycle) {
        super(navigationBarView);
        this.mNavigationBarWindowState = 0;
        this.mNavigationIconHints = 0;
        this.mNavBarMode = 0;
        this.mSamplingBounds = new Rect();
        this.mAutoHideUiElement = new AutoHideUiElement() { // from class: com.android.systemui.navigationbar.NavigationBar.1
            {
                NavigationBar.this = this;
            }

            public void hide() {
                NavigationBar.this.clearTransient();
            }

            public boolean isVisible() {
                return NavigationBar.this.isTransientShown();
            }

            public boolean shouldHideOnTouch() {
                return !NavigationBar.this.mNotificationRemoteInputManager.isRemoteInputActive();
            }

            public void synchronizeState() {
                NavigationBar.this.checkNavBarModes();
            }
        };
        this.mNavbarTaskbarStateUpdater = new NavBarHelper.NavbarTaskbarStateUpdater() { // from class: com.android.systemui.navigationbar.NavigationBar.2
            {
                NavigationBar.this = this;
            }

            @Override // com.android.systemui.navigationbar.NavBarHelper.NavbarTaskbarStateUpdater
            public void updateAccessibilityServicesState() {
                NavigationBar.this.updateAccessibilityStateFlags();
            }

            @Override // com.android.systemui.navigationbar.NavBarHelper.NavbarTaskbarStateUpdater
            public void updateAssistantAvailable(boolean z, boolean z2) {
                if (((ViewController) NavigationBar.this).mView == null) {
                    return;
                }
                NavigationBar.this.mLongPressHomeEnabled = z2;
                NavigationBar.this.updateAssistantEntrypoints(z, z2);
            }
        };
        this.mOverviewProxyListener = new OverviewProxyService.OverviewProxyListener() { // from class: com.android.systemui.navigationbar.NavigationBar.3
            {
                NavigationBar.this = this;
            }

            @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
            public void onConnectionChanged(boolean z) {
                ((NavigationBarView) ((ViewController) NavigationBar.this).mView).onOverviewProxyConnectionChange(NavigationBar.this.mOverviewProxyService.isEnabled());
                ((NavigationBarView) ((ViewController) NavigationBar.this).mView).setShouldShowSwipeUpUi(NavigationBar.this.mOverviewProxyService.shouldShowSwipeUpUI());
                NavigationBar.this.updateScreenPinningGestures();
            }

            @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
            public void onHomeRotationEnabled(boolean z) {
                ((NavigationBarView) ((ViewController) NavigationBar.this).mView).getRotationButtonController().setHomeRotationEnabled(z);
            }

            @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
            public void onOverviewShown(boolean z) {
                ((NavigationBarView) ((ViewController) NavigationBar.this).mView).getRotationButtonController().setSkipOverrideUserLockPrefsOnce();
            }

            @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
            public void onTaskbarStatusUpdated(boolean z, boolean z2) {
                ((NavigationBarView) ((ViewController) NavigationBar.this).mView).getFloatingRotationButton().onTaskbarStateChanged(z, z2);
            }

            @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
            public void onToggleRecentApps() {
                ((NavigationBarView) ((ViewController) NavigationBar.this).mView).getRotationButtonController().setSkipOverrideUserLockPrefsOnce();
            }

            @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
            public void startAssistant(Bundle bundle2) {
                ((AssistManager) NavigationBar.this.mAssistManagerLazy.get()).startAssist(bundle2);
            }
        };
        this.mAutoDim = new Runnable() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                NavigationBar.$r8$lambda$T2khzvKue7NVudfy9B8kqnm8JdU(NavigationBar.this);
            }
        };
        this.mEnableLayoutTransitions = new Runnable() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda8
            @Override // java.lang.Runnable
            public final void run() {
                NavigationBar.$r8$lambda$51uSrByCGmAv1mf5SuHfy8PrEF4(NavigationBar.this);
            }
        };
        this.mOnVariableDurationHomeLongClick = new Runnable() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                NavigationBar.m3398$r8$lambda$2dOL7JH8PTcwu4od8_x9VF3EiQ(NavigationBar.this);
            }
        };
        this.mOnPropertiesChangedListener = new AnonymousClass4();
        this.mDepthListener = new NotificationShadeDepthController.DepthListener() { // from class: com.android.systemui.navigationbar.NavigationBar.5
            public boolean mHasBlurs;

            {
                NavigationBar.this = this;
            }

            public void onBlurRadiusChanged(int i) {
                boolean z = i != 0;
                if (z == this.mHasBlurs) {
                    return;
                }
                this.mHasBlurs = z;
                NavigationBar.this.mRegionSamplingHelper.setWindowHasBlurs(z);
            }

            public void onWallpaperZoomOutChanged(float f) {
            }
        };
        this.mWakefulnessObserver = new WakefulnessLifecycle.Observer() { // from class: com.android.systemui.navigationbar.NavigationBar.6
            {
                NavigationBar.this = this;
            }

            public final void notifyScreenStateChanged(boolean z) {
                NavigationBar.this.notifyNavigationBarScreenOn();
                ((NavigationBarView) ((ViewController) NavigationBar.this).mView).onScreenStateChanged(z);
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public void onFinishedGoingToSleep() {
                notifyScreenStateChanged(false);
                NavigationBar.this.mRegionSamplingHelper.stop();
            }

            @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
            public void onStartedWakingUp() {
                notifyScreenStateChanged(true);
                if (Utils.isGesturalModeOnDefaultDisplay(NavigationBar.this.getContext(), NavigationBar.this.mNavBarMode)) {
                    NavigationBar.this.mRegionSamplingHelper.start(NavigationBar.this.mSamplingBounds);
                }
            }
        };
        this.mSurfaceChangedCallback = new ViewRootImpl.SurfaceChangedCallback() { // from class: com.android.systemui.navigationbar.NavigationBar.7
            {
                NavigationBar.this = this;
            }

            public void surfaceCreated(SurfaceControl.Transaction transaction) {
                NavigationBar.this.notifyNavigationBarSurface();
            }

            public void surfaceDestroyed() {
                NavigationBar.this.notifyNavigationBarSurface();
            }

            public void surfaceReplaced(SurfaceControl.Transaction transaction) {
                NavigationBar.this.notifyNavigationBarSurface();
            }
        };
        this.mRotationWatcher = new Consumer() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda10
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NavigationBar.$r8$lambda$1ikVut1IKywCdr39DwIhxxpGYBw(NavigationBar.this, (Integer) obj);
            }
        };
        this.mUserChangedCallback = new UserTracker.Callback() { // from class: com.android.systemui.navigationbar.NavigationBar.9
            {
                NavigationBar.this = this;
            }

            public void onUserChanged(int i, Context context2) {
                NavigationBar.this.updateAccessibilityStateFlags();
            }
        };
        NavigationModeController.ModeChangedListener modeChangedListener = new NavigationModeController.ModeChangedListener() { // from class: com.android.systemui.navigationbar.NavigationBar.10
            {
                NavigationBar.this = this;
            }

            @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
            public void onNavigationModeChanged(int i) {
                NavigationBar.this.mNavBarMode = i;
                if (!QuickStepContract.isGesturalMode(i) && NavigationBar.this.getBarTransitions() != null) {
                    NavigationBar.this.getBarTransitions().setBackgroundOverrideAlpha(1.0f);
                }
                NavigationBar.this.updateScreenPinningGestures();
                NavigationBar.this.setNavBarMode(i);
                ((NavigationBarView) ((ViewController) NavigationBar.this).mView).setShouldShowSwipeUpUi(NavigationBar.this.mOverviewProxyService.shouldShowSwipeUpUI());
            }

            @Override // com.android.systemui.navigationbar.NavigationModeController.ModeChangedListener
            public void onSettingsChanged() {
                NavigationBar.this.mEdgeBackGestureHandler.onSettingsChanged();
            }
        };
        this.mModeChangedListener = modeChangedListener;
        this.mTouchHandler = new Gefingerpoken() { // from class: com.android.systemui.navigationbar.NavigationBar.11
            public boolean mDeadZoneConsuming;

            {
                NavigationBar.this = this;
            }

            @Override // com.android.systemui.Gefingerpoken
            public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
                if (QuickStepContract.isGesturalMode(NavigationBar.this.mNavBarMode) && NavigationBar.this.mImeVisible && motionEvent.getAction() == 0) {
                    SysUiStatsLog.write(304, (int) motionEvent.getX(), (int) motionEvent.getY());
                }
                return shouldDeadZoneConsumeTouchEvents(motionEvent);
            }

            @Override // com.android.systemui.Gefingerpoken
            public boolean onTouchEvent(MotionEvent motionEvent) {
                shouldDeadZoneConsumeTouchEvents(motionEvent);
                return false;
            }

            public final boolean shouldDeadZoneConsumeTouchEvents(MotionEvent motionEvent) {
                int actionMasked = motionEvent.getActionMasked();
                if (actionMasked == 0) {
                    this.mDeadZoneConsuming = false;
                }
                if (NavigationBar.this.mDeadZone.onTouchEvent(motionEvent) || this.mDeadZoneConsuming) {
                    if (actionMasked == 0) {
                        ((NavigationBarView) ((ViewController) NavigationBar.this).mView).setSlippery(true);
                        this.mDeadZoneConsuming = true;
                        return true;
                    } else if (actionMasked == 1 || actionMasked == 3) {
                        ((NavigationBarView) ((ViewController) NavigationBar.this).mView).updateSlippery();
                        this.mDeadZoneConsuming = false;
                        return true;
                    } else {
                        return true;
                    }
                }
                return false;
            }
        };
        this.mFrame = navigationBarFrame;
        this.mContext = context;
        this.mSavedState = bundle;
        this.mWindowManager = windowManager;
        this.mAccessibilityManager = accessibilityManager;
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mStatusBarStateController = statusBarStateController;
        this.mMetricsLogger = metricsLogger;
        this.mAssistManagerLazy = lazy;
        this.mStatusBarKeyguardViewManager = statusBarKeyguardViewManager;
        this.mSysUiFlagsContainer = sysUiState;
        this.mCentralSurfacesOptionalLazy = lazy2;
        this.mShadeController = shadeController;
        this.mNotificationRemoteInputManager = notificationRemoteInputManager;
        this.mOverviewProxyService = overviewProxyService;
        this.mNavigationModeController = navigationModeController;
        this.mUserTracker = userTracker;
        this.mCommandQueue = commandQueue;
        this.mPipOptional = optional;
        this.mRecentsOptional = optional2;
        this.mDeadZone = deadZone;
        this.mDeviceConfigProxy = deviceConfigProxy;
        this.mNavigationBarTransitions = navigationBarTransitions;
        this.mEdgeBackGestureHandler = edgeBackGestureHandler;
        this.mBackAnimation = optional4;
        this.mHandler = handler;
        this.mUiEventLogger = uiEventLogger;
        this.mNavBarHelper = navBarHelper;
        this.mNotificationShadeDepthController = notificationShadeDepthController;
        this.mMainLightBarController = lightBarController;
        this.mLightBarControllerFactory = factory;
        this.mMainAutoHideController = autoHideController;
        this.mAutoHideControllerFactory = factory2;
        this.mTelecomManagerOptional = optional3;
        this.mInputMethodManager = inputMethodManager;
        this.mUserContextProvider = userContextProvider;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mNavColorSampleMargin = getResources().getDimensionPixelSize(R$dimen.navigation_handle_sample_horizontal_margin);
        this.mOnComputeInternalInsetsListener = new ViewTreeObserver.OnComputeInternalInsetsListener() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda11
            public final void onComputeInternalInsets(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
                NavigationBar.m3399$r8$lambda$CdZ5sCBkrQcLISmmVdzRtqzZEM(NavigationBar.this, internalInsetsInfo);
            }
        };
        this.mRegionSamplingHelper = new RegionSamplingHelper(((ViewController) this).mView, new RegionSamplingHelper.SamplingCallback() { // from class: com.android.systemui.navigationbar.NavigationBar.8
            {
                NavigationBar.this = this;
            }

            public Rect getSampledRegion(View view) {
                return NavigationBar.this.calculateSamplingRect();
            }

            public boolean isSamplingEnabled() {
                return Utils.isGesturalModeOnDefaultDisplay(NavigationBar.this.getContext(), NavigationBar.this.mNavBarMode);
            }

            public void onRegionDarknessChanged(boolean z) {
                NavigationBar.this.getBarTransitions().getLightTransitionsController().setIconsDark(!z, true);
            }
        }, executor, executor2);
        ((NavigationBarView) ((ViewController) this).mView).setBackgroundExecutor(executor2);
        ((NavigationBarView) ((ViewController) this).mView).setEdgeBackGestureHandler(edgeBackGestureHandler);
        this.mNavBarMode = navigationModeController.addListener(modeChangedListener);
    }

    public /* synthetic */ void lambda$new$0() {
        getBarTransitions().setAutoDim(true);
    }

    public /* synthetic */ void lambda$new$1() {
        ((NavigationBarView) ((ViewController) this).mView).setLayoutTransitionsEnabled(true);
    }

    public /* synthetic */ void lambda$new$2() {
        if (onHomeLongClick(((NavigationBarView) ((ViewController) this).mView).getHomeButton().getCurrentView())) {
            ((NavigationBarView) ((ViewController) this).mView).getHomeButton().getCurrentView().performHapticFeedback(0, 1);
        }
    }

    public /* synthetic */ void lambda$new$3(ViewTreeObserver.InternalInsetsInfo internalInsetsInfo) {
        if (!this.mEdgeBackGestureHandler.isHandlingGestures()) {
            if (!this.mImeVisible) {
                internalInsetsInfo.setTouchableInsets(0);
                return;
            } else if (!((NavigationBarView) ((ViewController) this).mView).isImeRenderingNavButtons()) {
                internalInsetsInfo.setTouchableInsets(0);
                return;
            }
        }
        internalInsetsInfo.setTouchableInsets(3);
        internalInsetsInfo.touchableRegion.set(getButtonLocations(false, false, false));
    }

    public /* synthetic */ void lambda$new$7(Integer num) {
        View view = ((ViewController) this).mView;
        if (view == null || !((NavigationBarView) view).needsReorient(num.intValue())) {
            return;
        }
        repositionNavigationBar(num.intValue());
    }

    public /* synthetic */ void lambda$onHomeTouch$6(Long l) {
        this.mHandler.postDelayed(this.mOnVariableDurationHomeLongClick, l.longValue());
    }

    public static /* synthetic */ boolean lambda$onInit$4(Long l) {
        return l.longValue() != 0;
    }

    public /* synthetic */ void lambda$onViewAttached$5() {
        this.mOverviewProxyService.onActiveNavBarRegionChanges(getButtonLocations(true, true, true));
    }

    public void abortTransient(int i, int[] iArr) {
        if (i == this.mDisplayId && InsetsState.containsType(iArr, 1)) {
            clearTransient();
        }
    }

    public final boolean allowSystemGestureIgnoringBarVisibility() {
        return this.mBehavior != 2;
    }

    public final Rect calculateSamplingRect() {
        this.mSamplingBounds.setEmpty();
        View currentView = ((NavigationBarView) ((ViewController) this).mView).getHomeHandle().getCurrentView();
        if (currentView != null) {
            int[] iArr = new int[2];
            currentView.getLocationOnScreen(iArr);
            Point point = new Point();
            currentView.getContext().getDisplay().getRealSize(point);
            this.mSamplingBounds.set(new Rect(iArr[0] - this.mNavColorSampleMargin, point.y - ((NavigationBarView) ((ViewController) this).mView).getNavBarHeight(), iArr[0] + currentView.getWidth() + this.mNavColorSampleMargin, point.y));
        }
        return this.mSamplingBounds;
    }

    public final void checkBarModes() {
        if (this.mIsOnDefaultDisplay) {
            ((Optional) this.mCentralSurfacesOptionalLazy.get()).ifPresent(new Consumer() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda25
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((CentralSurfaces) obj).checkBarModes();
                }
            });
        } else {
            checkNavBarModes();
        }
    }

    public void checkNavBarModes() {
        getBarTransitions().transitionTo(this.mTransitionMode, ((Boolean) ((Optional) this.mCentralSurfacesOptionalLazy.get()).map(new Function() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Boolean.valueOf(((CentralSurfaces) obj).isDeviceInteractive());
            }
        }).orElse(Boolean.FALSE)).booleanValue() && this.mNavigationBarWindowState != 2);
    }

    public final void clearTransient() {
        if (this.mTransientShown) {
            this.mTransientShown = false;
            this.mTransientShownFromGestureOnSystemBar = false;
            handleTransientChanged();
        }
    }

    public void destroyView() {
        setAutoHideController(null);
        this.mCommandQueue.removeCallback(this);
        this.mWindowManager.removeViewImmediate(((NavigationBarView) ((ViewController) this).mView).getRootView());
        this.mNavigationModeController.removeListener(this.mModeChangedListener);
        this.mNavBarHelper.removeNavTaskStateUpdater(this.mNavbarTaskbarStateUpdater);
        this.mNavBarHelper.destroy();
        this.mNotificationShadeDepthController.removeListener(this.mDepthListener);
        this.mDeviceConfigProxy.removeOnPropertiesChangedListener(this.mOnPropertiesChangedListener);
    }

    public void disable(int i, int i2, int i3, boolean z) {
        int i4;
        if (i != this.mDisplayId) {
            return;
        }
        int i5 = 56623104 & i2;
        if (i5 != this.mDisabledFlags1) {
            this.mDisabledFlags1 = i5;
            ((NavigationBarView) ((ViewController) this).mView).setDisabledFlags(i2, this.mSysUiFlagsContainer);
            updateScreenPinningGestures();
        }
        if (!this.mIsOnDefaultDisplay || (i4 = i3 & 16) == this.mDisabledFlags2) {
            return;
        }
        this.mDisabledFlags2 = i4;
        setDisabled2Flags(i4);
    }

    public void disableAnimationsDuringHide(long j) {
        ((NavigationBarView) ((ViewController) this).mView).setLayoutTransitionsEnabled(false);
        this.mHandler.postDelayed(this.mEnableLayoutTransitions, j + 448);
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println("NavigationBar (displayId=" + this.mDisplayId + "):");
        StringBuilder sb = new StringBuilder();
        sb.append("  mHomeButtonLongPressDurationMs=");
        sb.append(this.mHomeButtonLongPressDurationMs);
        printWriter.println(sb.toString());
        printWriter.println("  mLongPressHomeEnabled=" + this.mLongPressHomeEnabled);
        printWriter.println("  mNavigationBarWindowState=" + StatusBarManager.windowStateToString(this.mNavigationBarWindowState));
        printWriter.println("  mTransitionMode=" + BarTransitions.modeToString(this.mTransitionMode));
        printWriter.println("  mTransientShown=" + this.mTransientShown);
        printWriter.println("  mTransientShownFromGestureOnSystemBar=" + this.mTransientShownFromGestureOnSystemBar);
        CentralSurfaces.dumpBarTransitions(printWriter, "mNavigationBarView", getBarTransitions());
        ((NavigationBarView) ((ViewController) this).mView).dump(printWriter);
        this.mRegionSamplingHelper.dump(printWriter);
    }

    public void finishBarAnimations() {
        getBarTransitions().finishAnimations();
    }

    public final WindowManager.LayoutParams getBarLayoutParams(int i) {
        WindowManager.LayoutParams barLayoutParamsForRotation = getBarLayoutParamsForRotation(i);
        barLayoutParamsForRotation.paramsForRotation = new WindowManager.LayoutParams[4];
        for (int i2 = 0; i2 <= 3; i2++) {
            barLayoutParamsForRotation.paramsForRotation[i2] = getBarLayoutParamsForRotation(i2);
        }
        return barLayoutParamsForRotation;
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0107  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0125  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final WindowManager.LayoutParams getBarLayoutParamsForRotation(int i) {
        boolean z;
        int dimensionPixelSize;
        int dimensionPixelSize2;
        int i2;
        int dimensionPixelSize3;
        int i3;
        Context createCurrentUserContext = this.mUserContextProvider.createCurrentUserContext(this.mContext);
        WindowManager windowManager = this.mWindowManager;
        if (windowManager != null && windowManager.getCurrentWindowMetrics() != null) {
            Rect bounds = this.mWindowManager.getCurrentWindowMetrics().getBounds();
            if (bounds.width() == bounds.height() || !createCurrentUserContext.getResources().getBoolean(17891736)) {
                z = false;
                int i4 = 80;
                if (z) {
                    dimensionPixelSize = createCurrentUserContext.getResources().getDimensionPixelSize(17105361);
                    dimensionPixelSize2 = createCurrentUserContext.getResources().getDimensionPixelSize(17105365);
                } else {
                    if (i != -1 && i != 0) {
                        if (i == 1) {
                            i2 = 5;
                            dimensionPixelSize3 = createCurrentUserContext.getResources().getDimensionPixelSize(17105370);
                        } else if (i != 2) {
                            if (i != 3) {
                                dimensionPixelSize2 = -1;
                                dimensionPixelSize3 = -1;
                                i3 = -1;
                                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(dimensionPixelSize3, i3, 2019, 545521768, -3);
                                layoutParams.gravity = i4;
                                if (dimensionPixelSize2 == -1) {
                                    layoutParams.providedInsets = new InsetsFrameProvider[]{new InsetsFrameProvider(1, Insets.of(0, 0, 0, dimensionPixelSize2))};
                                } else {
                                    layoutParams.providedInsets = new InsetsFrameProvider[]{new InsetsFrameProvider(1)};
                                }
                                layoutParams.token = new Binder();
                                layoutParams.accessibilityTitle = createCurrentUserContext.getString(R$string.nav_bar);
                                layoutParams.privateFlags |= 16785408;
                                layoutParams.layoutInDisplayCutoutMode = 3;
                                layoutParams.windowAnimations = 0;
                                layoutParams.setTitle("NavigationBar" + createCurrentUserContext.getDisplayId());
                                layoutParams.setFitInsetsTypes(0);
                                layoutParams.setTrustedOverlay();
                                return layoutParams;
                            }
                            dimensionPixelSize3 = createCurrentUserContext.getResources().getDimensionPixelSize(17105370);
                            i2 = 3;
                        }
                        i3 = -1;
                        i4 = i2;
                        dimensionPixelSize2 = -1;
                        WindowManager.LayoutParams layoutParams2 = new WindowManager.LayoutParams(dimensionPixelSize3, i3, 2019, 545521768, -3);
                        layoutParams2.gravity = i4;
                        if (dimensionPixelSize2 == -1) {
                        }
                        layoutParams2.token = new Binder();
                        layoutParams2.accessibilityTitle = createCurrentUserContext.getString(R$string.nav_bar);
                        layoutParams2.privateFlags |= 16785408;
                        layoutParams2.layoutInDisplayCutoutMode = 3;
                        layoutParams2.windowAnimations = 0;
                        layoutParams2.setTitle("NavigationBar" + createCurrentUserContext.getDisplayId());
                        layoutParams2.setFitInsetsTypes(0);
                        layoutParams2.setTrustedOverlay();
                        return layoutParams2;
                    }
                    dimensionPixelSize = createCurrentUserContext.getResources().getDimensionPixelSize(17105361);
                    dimensionPixelSize2 = createCurrentUserContext.getResources().getDimensionPixelSize(17105365);
                }
                i3 = dimensionPixelSize;
                dimensionPixelSize3 = -1;
                WindowManager.LayoutParams layoutParams22 = new WindowManager.LayoutParams(dimensionPixelSize3, i3, 2019, 545521768, -3);
                layoutParams22.gravity = i4;
                if (dimensionPixelSize2 == -1) {
                }
                layoutParams22.token = new Binder();
                layoutParams22.accessibilityTitle = createCurrentUserContext.getString(R$string.nav_bar);
                layoutParams22.privateFlags |= 16785408;
                layoutParams22.layoutInDisplayCutoutMode = 3;
                layoutParams22.windowAnimations = 0;
                layoutParams22.setTitle("NavigationBar" + createCurrentUserContext.getDisplayId());
                layoutParams22.setFitInsetsTypes(0);
                layoutParams22.setTrustedOverlay();
                return layoutParams22;
            }
        }
        z = true;
        int i42 = 80;
        if (z) {
        }
        i3 = dimensionPixelSize;
        dimensionPixelSize3 = -1;
        WindowManager.LayoutParams layoutParams222 = new WindowManager.LayoutParams(dimensionPixelSize3, i3, 2019, 545521768, -3);
        layoutParams222.gravity = i42;
        if (dimensionPixelSize2 == -1) {
        }
        layoutParams222.token = new Binder();
        layoutParams222.accessibilityTitle = createCurrentUserContext.getString(R$string.nav_bar);
        layoutParams222.privateFlags |= 16785408;
        layoutParams222.layoutInDisplayCutoutMode = 3;
        layoutParams222.windowAnimations = 0;
        layoutParams222.setTitle("NavigationBar" + createCurrentUserContext.getDisplayId());
        layoutParams222.setFitInsetsTypes(0);
        layoutParams222.setTrustedOverlay();
        return layoutParams222;
    }

    public NavigationBarTransitions getBarTransitions() {
        return this.mNavigationBarTransitions;
    }

    public Region getButtonLocations(boolean z, boolean z2, boolean z3) {
        boolean z4 = z3;
        if (z3) {
            z4 = z3;
            if (!z2) {
                z4 = false;
            }
        }
        Region region = new Region();
        Map<View, Rect> buttonTouchRegionCache = ((NavigationBarView) ((ViewController) this).mView).getButtonTouchRegionCache();
        updateButtonLocation(region, buttonTouchRegionCache, ((NavigationBarView) ((ViewController) this).mView).getBackButton(), z2, z4);
        updateButtonLocation(region, buttonTouchRegionCache, ((NavigationBarView) ((ViewController) this).mView).getHomeButton(), z2, z4);
        updateButtonLocation(region, buttonTouchRegionCache, ((NavigationBarView) ((ViewController) this).mView).getRecentsButton(), z2, z4);
        updateButtonLocation(region, buttonTouchRegionCache, ((NavigationBarView) ((ViewController) this).mView).getImeSwitchButton(), z2, z4);
        updateButtonLocation(region, buttonTouchRegionCache, ((NavigationBarView) ((ViewController) this).mView).getAccessibilityButton(), z2, z4);
        updateButtonLocation(region, buttonTouchRegionCache, ((NavigationBarView) ((ViewController) this).mView).getCursorLeftButton(), z2, z4);
        updateButtonLocation(region, buttonTouchRegionCache, ((NavigationBarView) ((ViewController) this).mView).getCursorRightButton(), z2, z4);
        if (z && ((NavigationBarView) ((ViewController) this).mView).getFloatingRotationButton().isVisible()) {
            updateButtonLocation(region, ((NavigationBarView) ((ViewController) this).mView).getFloatingRotationButton().getCurrentView(), z2);
        } else {
            updateButtonLocation(region, buttonTouchRegionCache, ((NavigationBarView) ((ViewController) this).mView).getRotateSuggestionButton(), z2, z4);
        }
        return region;
    }

    public int getNavigationIconHints() {
        return this.mNavigationIconHints;
    }

    public NavigationBarView getView() {
        return (NavigationBarView) ((ViewController) this).mView;
    }

    public final void handleTransientChanged() {
        LightBarController lightBarController;
        this.mEdgeBackGestureHandler.onNavBarTransientStateChanged(this.mTransientShown);
        int transitionMode = NavBarHelper.transitionMode(this.mTransientShown, this.mAppearance);
        if (!updateTransitionMode(transitionMode) || (lightBarController = this.mLightBarController) == null) {
            return;
        }
        lightBarController.onNavigationBarModeChanged(transitionMode);
    }

    public boolean isNavBarWindowVisible() {
        return this.mNavigationBarWindowState == 0;
    }

    public final boolean isTransientShown() {
        return this.mTransientShown;
    }

    public final void notifyNavigationBarScreenOn() {
        ((NavigationBarView) ((ViewController) this).mView).updateNavButtonIcons();
    }

    public final void notifyNavigationBarSurface() {
        ViewRootImpl viewRootImpl = ((NavigationBarView) ((ViewController) this).mView).getViewRootImpl();
        this.mOverviewProxyService.onNavigationBarSurfaceChanged(viewRootImpl != null ? viewRootImpl.getSurfaceControl() : null);
    }

    public final void onAccessibilityClick(View view) {
        Display display = view.getDisplay();
        this.mAccessibilityManager.notifyAccessibilityButtonClicked(display != null ? display.getDisplayId() : 0);
    }

    public final boolean onAccessibilityLongClick(View view) {
        Intent intent = new Intent("com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON");
        intent.addFlags(268468224);
        intent.setClassName("android", AccessibilityButtonChooserActivity.class.getName());
        this.mContext.startActivityAsUser(intent, UserHandle.CURRENT);
        return true;
    }

    public void onBarTransition(int i) {
        if (i != 4) {
            this.mRegionSamplingHelper.start(this.mSamplingBounds);
            return;
        }
        this.mRegionSamplingHelper.stop();
        getBarTransitions().getLightTransitionsController().setIconsDark(false, true);
    }

    public void onConfigurationChanged(Configuration configuration) {
        int rotation = configuration.windowConfiguration.getRotation();
        Locale locale = this.mContext.getResources().getConfiguration().locale;
        int layoutDirectionFromLocale = TextUtils.getLayoutDirectionFromLocale(locale);
        if (!locale.equals(this.mLocale) || layoutDirectionFromLocale != this.mLayoutDirection) {
            this.mLocale = locale;
            this.mLayoutDirection = layoutDirectionFromLocale;
            refreshLayout(layoutDirectionFromLocale);
        }
        repositionNavigationBar(rotation);
        this.mEdgeBackGestureHandler.onConfigurationChanged(configuration);
    }

    public boolean onHomeLongClick(View view) {
        if (((NavigationBarView) ((ViewController) this).mView).isRecentsButtonVisible() || !ActivityManagerWrapper.getInstance().isScreenPinningActive()) {
            KeyButtonView keyButtonView = (KeyButtonView) view;
            keyButtonView.sendEvent(0, RecyclerView.ViewHolder.FLAG_IGNORE);
            keyButtonView.sendAccessibilityEvent(2);
            return true;
        }
        return onLongPressBackHome(view);
    }

    public boolean onHomeTouch(View view, MotionEvent motionEvent) {
        if (!this.mHomeBlockedThisTouch || motionEvent.getActionMasked() == 0) {
            Optional optional = (Optional) this.mCentralSurfacesOptionalLazy.get();
            int action = motionEvent.getAction();
            if (action != 0) {
                if (action == 1 || action == 3) {
                    this.mHandler.removeCallbacks(this.mOnVariableDurationHomeLongClick);
                    optional.ifPresent(new NavigationBar$$ExternalSyntheticLambda1());
                    return false;
                }
                return false;
            }
            this.mHomeBlockedThisTouch = false;
            if (this.mTelecomManagerOptional.isPresent() && this.mTelecomManagerOptional.get().isRinging() && ((Boolean) optional.map(new GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda9()).orElse(Boolean.FALSE)).booleanValue()) {
                Log.i("NavigationBar", "Ignoring HOME; there's a ringing incoming call. No heads up");
                this.mHomeBlockedThisTouch = true;
                return true;
            } else if (this.mLongPressHomeEnabled) {
                this.mHomeButtonLongPressDurationMs.ifPresent(new Consumer() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        NavigationBar.$r8$lambda$hdo7wkgWMUqO5ETDcqTRbK1CmO8(NavigationBar.this, (Long) obj);
                    }
                });
                return false;
            } else {
                return false;
            }
        }
        return true;
    }

    public final void onImeSwitcherClick(View view) {
        this.mInputMethodManager.showInputMethodPickerFromSystem(true, this.mDisplayId);
        this.mUiEventLogger.log(KeyButtonView.NavBarButtonEvent.NAVBAR_IME_SWITCHER_BUTTON_TAP);
    }

    public void onInit() {
        ((NavigationBarView) ((ViewController) this).mView).setBarTransitions(this.mNavigationBarTransitions);
        ((NavigationBarView) ((ViewController) this).mView).setTouchHandler(this.mTouchHandler);
        setNavBarMode(this.mNavBarMode);
        EdgeBackGestureHandler edgeBackGestureHandler = this.mEdgeBackGestureHandler;
        final NavigationBarView navigationBarView = (NavigationBarView) ((ViewController) this).mView;
        Objects.requireNonNull(navigationBarView);
        edgeBackGestureHandler.setStateChangeCallback(new Runnable() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                NavigationBarView.this.updateStates();
            }
        });
        this.mNavigationBarTransitions.addListener(new NavigationBarTransitions.Listener() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda4
            @Override // com.android.systemui.navigationbar.NavigationBarTransitions.Listener
            public final void onTransition(int i) {
                NavigationBar.this.onBarTransition(i);
            }
        });
        ((NavigationBarView) ((ViewController) this).mView).updateRotationButton();
        ((NavigationBarView) ((ViewController) this).mView).setVisibility(this.mStatusBarKeyguardViewManager.isNavBarVisible() ? 0 : 4);
        this.mWindowManager.addView(this.mFrame, getBarLayoutParams(this.mContext.getResources().getConfiguration().windowConfiguration.getRotation()));
        int displayId = this.mContext.getDisplayId();
        this.mDisplayId = displayId;
        this.mIsOnDefaultDisplay = displayId == 0;
        parseCurrentSysuiState();
        this.mCommandQueue.addCallback(this);
        this.mLongPressHomeEnabled = this.mNavBarHelper.getLongPressHomeEnabled();
        this.mNavBarHelper.init();
        this.mHomeButtonLongPressDurationMs = Optional.of(Long.valueOf(this.mDeviceConfigProxy.getLong("systemui", "home_button_long_press_duration_ms", 0L))).filter(new Predicate() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda5
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return NavigationBar.m3400$r8$lambda$EQzxHXfbVmOfriudM3PLAwFMWU((Long) obj);
            }
        });
        DeviceConfigProxy deviceConfigProxy = this.mDeviceConfigProxy;
        Handler handler = this.mHandler;
        Objects.requireNonNull(handler);
        deviceConfigProxy.addOnPropertiesChangedListener("systemui", new MediaRoute2Provider$$ExternalSyntheticLambda0(handler), this.mOnPropertiesChangedListener);
        Bundle bundle = this.mSavedState;
        if (bundle != null) {
            this.mDisabledFlags1 = bundle.getInt("disabled_state", 0);
            this.mDisabledFlags2 = this.mSavedState.getInt("disabled2_state", 0);
            this.mAppearance = this.mSavedState.getInt("appearance", 0);
            this.mBehavior = this.mSavedState.getInt("behavior", 0);
            this.mTransientShown = this.mSavedState.getBoolean("transient_state", false);
        }
        this.mCommandQueue.recomputeDisableFlags(this.mDisplayId, false);
        this.mNotificationShadeDepthController.addListener(this.mDepthListener);
    }

    public final boolean onLongPressBackHome(View view) {
        return onLongPressNavigationButtons(view, R$id.back, R$id.home);
    }

    public final boolean onLongPressBackRecents(View view) {
        return onLongPressNavigationButtons(view, R$id.back, R$id.recent_apps);
    }

    public final boolean onLongPressNavigationButtons(View view, int i, int i2) {
        boolean z;
        boolean z2 = false;
        try {
            IActivityTaskManager service = ActivityTaskManager.getService();
            boolean isTouchExplorationEnabled = this.mAccessibilityManager.isTouchExplorationEnabled();
            boolean isInLockTaskMode = service.isInLockTaskMode();
            if (isInLockTaskMode && !isTouchExplorationEnabled) {
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis - this.mLastLockToAppLongPress < 200) {
                    service.stopSystemLockTaskMode();
                    ((NavigationBarView) ((ViewController) this).mView).updateNavButtonIcons();
                    return true;
                }
                if (view.getId() == i) {
                    if (!(i2 == R$id.recent_apps ? ((NavigationBarView) ((ViewController) this).mView).getRecentsButton() : ((NavigationBarView) ((ViewController) this).mView).getHomeButton()).getCurrentView().isPressed()) {
                        z = true;
                        this.mLastLockToAppLongPress = currentTimeMillis;
                    }
                }
                z = false;
                this.mLastLockToAppLongPress = currentTimeMillis;
            } else if (view.getId() == i) {
                z = true;
            } else if (isTouchExplorationEnabled && isInLockTaskMode) {
                service.stopSystemLockTaskMode();
                ((NavigationBarView) ((ViewController) this).mView).updateNavButtonIcons();
                return true;
            } else if (view.getId() == i2) {
                if (i2 != R$id.recent_apps) {
                    z2 = onHomeLongClick(((NavigationBarView) ((ViewController) this).mView).getHomeButton().getCurrentView());
                }
                return z2;
            } else {
                z = false;
            }
            if (z) {
                KeyButtonView keyButtonView = (KeyButtonView) view;
                keyButtonView.sendEvent(0, RecyclerView.ViewHolder.FLAG_IGNORE);
                keyButtonView.sendAccessibilityEvent(2);
                return true;
            }
            return false;
        } catch (RemoteException e) {
            Log.d("NavigationBar", "Unable to reach activity manager", e);
            return false;
        }
    }

    public final boolean onNavigationTouch(View view, MotionEvent motionEvent) {
        AutoHideController autoHideController = this.mAutoHideController;
        if (autoHideController != null) {
            autoHideController.checkUserAutoHide(motionEvent);
            return false;
        }
        return false;
    }

    public void onRecentsAnimationStateChanged(boolean z) {
        ((NavigationBarView) ((ViewController) this).mView).getRotationButtonController().setRecentsAnimationRunning(z);
    }

    public final void onRecentsClick(View view) {
        if (LatencyTracker.isEnabled(this.mContext)) {
            LatencyTracker.getInstance(this.mContext).onActionStart(1);
        }
        ((Optional) this.mCentralSurfacesOptionalLazy.get()).ifPresent(new NavigationBar$$ExternalSyntheticLambda1());
        this.mCommandQueue.toggleRecentApps();
    }

    public final boolean onRecentsTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        if (action == 0) {
            this.mCommandQueue.preloadRecentApps();
            return false;
        } else if (action == 3) {
            this.mCommandQueue.cancelPreloadRecentApps();
            return false;
        } else if (action != 1 || view.isPressed()) {
            return false;
        } else {
            this.mCommandQueue.cancelPreloadRecentApps();
            return false;
        }
    }

    public void onRotationProposal(int i, boolean z) {
        if (((NavigationBarView) ((ViewController) this).mView).isAttachedToWindow()) {
            boolean hasDisable2RotateSuggestionFlag = RotationButtonController.hasDisable2RotateSuggestionFlag(this.mDisabledFlags2);
            RotationButtonController rotationButtonController = ((NavigationBarView) ((ViewController) this).mView).getRotationButtonController();
            rotationButtonController.getRotationButton();
            if (hasDisable2RotateSuggestionFlag) {
                return;
            }
            rotationButtonController.onRotationProposal(i, z);
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putInt("disabled_state", this.mDisabledFlags1);
        bundle.putInt("disabled2_state", this.mDisabledFlags2);
        bundle.putInt("appearance", this.mAppearance);
        bundle.putInt("behavior", this.mBehavior);
        bundle.putBoolean("transient_state", this.mTransientShown);
        getBarTransitions().getLightTransitionsController().saveState(bundle);
    }

    public void onSystemBarAttributesChanged(int i, int i2, AppearanceRegion[] appearanceRegionArr, boolean z, int i3, InsetsVisibilities insetsVisibilities, String str, LetterboxDetails[] letterboxDetailsArr) {
        if (i != this.mDisplayId) {
            return;
        }
        boolean z2 = false;
        if (this.mAppearance != i2) {
            this.mAppearance = i2;
            z2 = updateTransitionMode(NavBarHelper.transitionMode(this.mTransientShown, i2));
        }
        LightBarController lightBarController = this.mLightBarController;
        if (lightBarController != null) {
            lightBarController.onNavigationBarAppearanceChanged(i2, z2, this.mTransitionMode, z);
        }
        if (this.mBehavior != i3) {
            this.mBehavior = i3;
            ((NavigationBarView) ((ViewController) this).mView).setBehavior(i3);
            updateSystemUiStateFlags();
        }
    }

    public final void onVerticalChanged(boolean z) {
        Optional optional = (Optional) this.mCentralSurfacesOptionalLazy.get();
        if (!optional.isPresent() || ((CentralSurfaces) optional.get()).getNotificationPanelViewController() == null) {
            return;
        }
        ((CentralSurfaces) optional.get()).getNotificationPanelViewController().setQsScrimEnabled(!z);
    }

    public void onViewAttached() {
        Display display = ((NavigationBarView) ((ViewController) this).mView).getDisplay();
        ((NavigationBarView) ((ViewController) this).mView).setComponents(this.mRecentsOptional);
        if (((Optional) this.mCentralSurfacesOptionalLazy.get()).isPresent()) {
            ((NavigationBarView) ((ViewController) this).mView).setComponents(((CentralSurfaces) ((Optional) this.mCentralSurfacesOptionalLazy.get()).get()).getNotificationPanelViewController());
        }
        ((NavigationBarView) ((ViewController) this).mView).setDisabledFlags(this.mDisabledFlags1, this.mSysUiFlagsContainer);
        ((NavigationBarView) ((ViewController) this).mView).setOnVerticalChangedListener(new NavigationBarView.OnVerticalChangedListener() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda12
            @Override // com.android.systemui.navigationbar.NavigationBarView.OnVerticalChangedListener
            public final void onVerticalChanged(boolean z) {
                NavigationBar.this.onVerticalChanged(z);
            }
        });
        ((NavigationBarView) ((ViewController) this).mView).setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda13
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return NavigationBar.m3403$r8$lambda$h1tdKzbIORPPk7SyQuhJ3_4vdc(NavigationBar.this, view, motionEvent);
            }
        });
        if (this.mSavedState != null) {
            getBarTransitions().getLightTransitionsController().restoreState(this.mSavedState);
        }
        setNavigationIconHints(this.mNavigationIconHints);
        setWindowVisible(isNavBarWindowVisible());
        ((NavigationBarView) ((ViewController) this).mView).setBehavior(this.mBehavior);
        setNavBarMode(this.mNavBarMode);
        ((NavigationBarView) ((ViewController) this).mView).setUpdateActiveTouchRegionsCallback(new NavigationBarView.UpdateActiveTouchRegionsCallback() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda14
            @Override // com.android.systemui.navigationbar.NavigationBarView.UpdateActiveTouchRegionsCallback
            public final void update() {
                NavigationBar.this.lambda$onViewAttached$5();
            }
        });
        ((NavigationBarView) ((ViewController) this).mView).getViewTreeObserver().addOnComputeInternalInsetsListener(this.mOnComputeInternalInsetsListener);
        ((NavigationBarView) ((ViewController) this).mView).getViewRootImpl().addSurfaceChangedCallback(this.mSurfaceChangedCallback);
        notifyNavigationBarSurface();
        this.mNavBarHelper.registerNavTaskStateUpdater(this.mNavbarTaskbarStateUpdater);
        Optional<Pip> optional = this.mPipOptional;
        final NavigationBarView navigationBarView = (NavigationBarView) ((ViewController) this).mView;
        Objects.requireNonNull(navigationBarView);
        optional.ifPresent(new Consumer() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda15
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NavigationBarView.this.addPipExclusionBoundsChangeListener((Pip) obj);
            }
        });
        Optional<BackAnimation> optional2 = this.mBackAnimation;
        final NavigationBarView navigationBarView2 = (NavigationBarView) ((ViewController) this).mView;
        Objects.requireNonNull(navigationBarView2);
        optional2.ifPresent(new Consumer() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda16
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NavigationBarView.this.registerBackAnimation((BackAnimation) obj);
            }
        });
        prepareNavigationBarView();
        checkNavBarModes();
        this.mUserTracker.addCallback(this.mUserChangedCallback, this.mContext.getMainExecutor());
        this.mWakefulnessLifecycle.addObserver(this.mWakefulnessObserver);
        notifyNavigationBarScreenOn();
        this.mOverviewProxyService.addCallback(this.mOverviewProxyListener);
        updateSystemUiStateFlags();
        if (this.mIsOnDefaultDisplay) {
            RotationButtonController rotationButtonController = ((NavigationBarView) ((ViewController) this).mView).getRotationButtonController();
            rotationButtonController.setRotationCallback(this.mRotationWatcher);
            if (display != null && rotationButtonController.isRotationLocked()) {
                rotationButtonController.setRotationLockedAtAngle(display.getRotation());
            }
        } else {
            this.mDisabledFlags2 |= 16;
        }
        setDisabled2Flags(this.mDisabledFlags2);
        setLightBarController(this.mIsOnDefaultDisplay ? this.mMainLightBarController : this.mLightBarControllerFactory.create(this.mContext));
        setAutoHideController(this.mIsOnDefaultDisplay ? this.mMainAutoHideController : this.mAutoHideControllerFactory.create(this.mContext));
        restoreAppearanceAndTransientState();
    }

    public void onViewDetached() {
        ((NavigationBarView) ((ViewController) this).mView).getRotationButtonController().setRotationCallback((Consumer) null);
        ((NavigationBarView) ((ViewController) this).mView).setUpdateActiveTouchRegionsCallback(null);
        getBarTransitions().destroy();
        this.mOverviewProxyService.removeCallback(this.mOverviewProxyListener);
        this.mUserTracker.removeCallback(this.mUserChangedCallback);
        this.mWakefulnessLifecycle.removeObserver(this.mWakefulnessObserver);
        ((NavigationBarView) ((ViewController) this).mView).getViewTreeObserver().removeOnComputeInternalInsetsListener(this.mOnComputeInternalInsetsListener);
        this.mHandler.removeCallbacks(this.mAutoDim);
        this.mHandler.removeCallbacks(this.mOnVariableDurationHomeLongClick);
        this.mHandler.removeCallbacks(this.mEnableLayoutTransitions);
        this.mNavBarHelper.removeNavTaskStateUpdater(this.mNavbarTaskbarStateUpdater);
        Optional<Pip> optional = this.mPipOptional;
        final NavigationBarView navigationBarView = (NavigationBarView) ((ViewController) this).mView;
        Objects.requireNonNull(navigationBarView);
        optional.ifPresent(new Consumer() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NavigationBarView.this.removePipExclusionBoundsChangeListener((Pip) obj);
            }
        });
        ViewRootImpl viewRootImpl = ((NavigationBarView) ((ViewController) this).mView).getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.removeSurfaceChangedCallback(this.mSurfaceChangedCallback);
        }
        this.mFrame = null;
    }

    public final void parseCurrentSysuiState() {
        NavBarHelper.CurrentSysuiState currentSysuiState = this.mNavBarHelper.getCurrentSysuiState();
        if (currentSysuiState.mWindowStateDisplayId == this.mDisplayId) {
            this.mNavigationBarWindowState = currentSysuiState.mWindowState;
        }
    }

    public final void prepareNavigationBarView() {
        ((NavigationBarView) ((ViewController) this).mView).reorient();
        ButtonDispatcher recentsButton = ((NavigationBarView) ((ViewController) this).mView).getRecentsButton();
        recentsButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda19
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NavigationBar.$r8$lambda$Lc81b84naHtaxRAuqpMeFxBuvqs(NavigationBar.this, view);
            }
        });
        recentsButton.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda20
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return NavigationBar.$r8$lambda$J0Eup4yr5Un7SiVZkodFWTR5yRU(NavigationBar.this, view, motionEvent);
            }
        });
        ButtonDispatcher homeButton = ((NavigationBarView) ((ViewController) this).mView).getHomeButton();
        homeButton.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda21
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return NavigationBar.this.onHomeTouch(view, motionEvent);
            }
        });
        homeButton.setLongClickable(true);
        reconfigureHomeLongClick();
        ButtonDispatcher accessibilityButton = ((NavigationBarView) ((ViewController) this).mView).getAccessibilityButton();
        accessibilityButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda22
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NavigationBar.m3401$r8$lambda$GMUrRGBTIpUp9f8LwOrFGf5HW8(NavigationBar.this, view);
            }
        });
        accessibilityButton.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda23
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return NavigationBar.$r8$lambda$IJ8n67jK2SaOovl5IoUoNpdsAns(NavigationBar.this, view);
            }
        });
        updateAccessibilityStateFlags();
        ((NavigationBarView) ((ViewController) this).mView).getImeSwitchButton().setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda24
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                NavigationBar.m3402$r8$lambda$SgxIBUBj_3hUjZ4QyQB1BuOclU(NavigationBar.this, view);
            }
        });
        updateScreenPinningGestures();
    }

    public final void reconfigureHomeLongClick() {
        if (((NavigationBarView) ((ViewController) this).mView).getHomeButton().getCurrentView() == null) {
            return;
        }
        if (this.mHomeButtonLongPressDurationMs.isPresent() || !this.mLongPressHomeEnabled) {
            ((NavigationBarView) ((ViewController) this).mView).getHomeButton().getCurrentView().setLongClickable(false);
            ((NavigationBarView) ((ViewController) this).mView).getHomeButton().getCurrentView().setHapticFeedbackEnabled(false);
            ((NavigationBarView) ((ViewController) this).mView).getHomeButton().setOnLongClickListener(null);
            return;
        }
        ((NavigationBarView) ((ViewController) this).mView).getHomeButton().getCurrentView().setLongClickable(true);
        ((NavigationBarView) ((ViewController) this).mView).getHomeButton().getCurrentView().setHapticFeedbackEnabled(true);
        ((NavigationBarView) ((ViewController) this).mView).getHomeButton().setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda26
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                return NavigationBar.this.onHomeLongClick(view);
            }
        });
    }

    public final void refreshLayout(int i) {
        ((NavigationBarView) ((ViewController) this).mView).setLayoutDirection(i);
    }

    public final void repositionNavigationBar(int i) {
        View view = ((ViewController) this).mView;
        if (view == null || !((NavigationBarView) view).isAttachedToWindow()) {
            return;
        }
        prepareNavigationBarView();
        this.mWindowManager.updateViewLayout(this.mFrame, getBarLayoutParams(i));
    }

    public void restoreAppearanceAndTransientState() {
        int transitionMode = NavBarHelper.transitionMode(this.mTransientShown, this.mAppearance);
        this.mTransitionMode = transitionMode;
        checkNavBarModes();
        AutoHideController autoHideController = this.mAutoHideController;
        if (autoHideController != null) {
            autoHideController.touchAutoHide();
        }
        LightBarController lightBarController = this.mLightBarController;
        if (lightBarController != null) {
            lightBarController.onNavigationBarAppearanceChanged(this.mAppearance, true, transitionMode, false);
        }
    }

    public final void setAutoHideController(AutoHideController autoHideController) {
        this.mAutoHideController = autoHideController;
        if (autoHideController != null) {
            autoHideController.setNavigationBar(this.mAutoHideUiElement);
        }
        ((NavigationBarView) ((ViewController) this).mView).setAutoHideController(autoHideController);
    }

    public final void setDisabled2Flags(int i) {
        ((NavigationBarView) ((ViewController) this).mView).getRotationButtonController().onDisable2FlagChanged(i);
    }

    public void setImeWindowStatus(int i, IBinder iBinder, int i2, int i3, boolean z) {
        if (i != this.mDisplayId) {
            return;
        }
        boolean isImeShown = this.mNavBarHelper.isImeShown(i2);
        int calculateBackDispositionHints = Utilities.calculateBackDispositionHints(this.mNavigationIconHints, i3, isImeShown, isImeShown && z);
        if (calculateBackDispositionHints == this.mNavigationIconHints) {
            return;
        }
        setNavigationIconHints(calculateBackDispositionHints);
        checkBarModes();
        updateSystemUiStateFlags();
    }

    public void setLightBarController(LightBarController lightBarController) {
        this.mLightBarController = lightBarController;
        if (lightBarController != null) {
            lightBarController.setNavigationBar(getBarTransitions().getLightTransitionsController());
        }
    }

    public final void setNavBarMode(int i) {
        ((NavigationBarView) ((ViewController) this).mView).setNavBarMode(i, this.mNavigationModeController.getImeDrawsImeNavBar());
        if (QuickStepContract.isGesturalMode(i)) {
            this.mRegionSamplingHelper.start(this.mSamplingBounds);
        } else {
            this.mRegionSamplingHelper.stop();
        }
    }

    public void setNavigationBarLumaSamplingEnabled(boolean z) {
        if (z) {
            this.mRegionSamplingHelper.start(this.mSamplingBounds);
        } else {
            this.mRegionSamplingHelper.stop();
        }
    }

    public final void setNavigationIconHints(int i) {
        if (i == this.mNavigationIconHints) {
            return;
        }
        if (!Utilities.isTablet(this.mContext)) {
            boolean z = false;
            boolean z2 = (i & 1) != 0;
            if ((this.mNavigationIconHints & 1) != 0) {
                z = true;
            }
            if (z2 != z) {
                ((NavigationBarView) ((ViewController) this).mView).onImeVisibilityChanged(z2);
                this.mImeVisible = z2;
            }
            ((NavigationBarView) ((ViewController) this).mView).setNavigationIconHints(i);
        }
        this.mNavigationIconHints = i;
    }

    public void setWindowState(int i, int i2, int i3) {
        if (i == this.mDisplayId && i2 == 2 && this.mNavigationBarWindowState != i3) {
            this.mNavigationBarWindowState = i3;
            updateSystemUiStateFlags();
            setWindowVisible(isNavBarWindowVisible());
        }
    }

    public final void setWindowVisible(boolean z) {
        this.mRegionSamplingHelper.setWindowVisible(z);
        ((NavigationBarView) ((ViewController) this).mView).setWindowVisible(z);
    }

    public void showTransient(int i, int[] iArr, boolean z) {
        if (i == this.mDisplayId && InsetsState.containsType(iArr, 1) && !this.mTransientShown) {
            this.mTransientShown = true;
            this.mTransientShownFromGestureOnSystemBar = z;
            handleTransientChanged();
        }
    }

    public void touchAutoDim() {
        getBarTransitions().setAutoDim(false);
        this.mHandler.removeCallbacks(this.mAutoDim);
        int state = this.mStatusBarStateController.getState();
        if (state == 1 || state == 2) {
            return;
        }
        this.mHandler.postDelayed(this.mAutoDim, 2250L);
    }

    public void transitionTo(int i, boolean z) {
        getBarTransitions().transitionTo(i, z);
    }

    public void updateAccessibilityStateFlags() {
        if (((ViewController) this).mView != null) {
            int a11yButtonState = this.mNavBarHelper.getA11yButtonState();
            boolean z = true;
            boolean z2 = (a11yButtonState & 16) != 0;
            if ((a11yButtonState & 32) == 0) {
                z = false;
            }
            ((NavigationBarView) ((ViewController) this).mView).setAccessibilityButtonState(z2, z);
        }
        updateSystemUiStateFlags();
    }

    public final void updateAssistantEntrypoints(boolean z, boolean z2) {
        if (this.mOverviewProxyService.getProxy() != null) {
            try {
                this.mOverviewProxyService.getProxy().onAssistantAvailable(z, z2);
            } catch (RemoteException e) {
                Log.w("NavigationBar", "Unable to send assistant availability data to launcher");
            }
        }
        reconfigureHomeLongClick();
    }

    public final void updateButtonLocation(Region region, View view, boolean z) {
        Rect rect = new Rect();
        if (z) {
            view.getBoundsOnScreen(rect);
        } else {
            int[] iArr = new int[2];
            view.getLocationInWindow(iArr);
            int i = iArr[0];
            rect.set(i, iArr[1], view.getWidth() + i, iArr[1] + view.getHeight());
        }
        region.op(rect, Region.Op.UNION);
    }

    public final void updateButtonLocation(Region region, Map<View, Rect> map, ButtonDispatcher buttonDispatcher, boolean z, boolean z2) {
        View currentView;
        if (buttonDispatcher == null || (currentView = buttonDispatcher.getCurrentView()) == null || !buttonDispatcher.isVisible()) {
            return;
        }
        if (z2 && map.containsKey(currentView)) {
            region.op(map.get(currentView), Region.Op.UNION);
        } else {
            updateButtonLocation(region, currentView, z);
        }
    }

    public final void updateScreenPinningGestures() {
        boolean isScreenPinningActive = ActivityManagerWrapper.getInstance().isScreenPinningActive();
        ButtonDispatcher backButton = ((NavigationBarView) ((ViewController) this).mView).getBackButton();
        ButtonDispatcher recentsButton = ((NavigationBarView) ((ViewController) this).mView).getRecentsButton();
        if (isScreenPinningActive) {
            backButton.setOnLongClickListener(((NavigationBarView) ((ViewController) this).mView).isRecentsButtonVisible() ? new View.OnLongClickListener() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda17
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    return NavigationBar.$r8$lambda$K7I0ugGK4L7biKOJxfyYbDfOVmU(NavigationBar.this, view);
                }
            } : new View.OnLongClickListener() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda18
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    return NavigationBar.$r8$lambda$HPyc8leQZCHqPWbDVB3bTeaa9y4(NavigationBar.this, view);
                }
            });
            recentsButton.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.navigationbar.NavigationBar$$ExternalSyntheticLambda17
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    return NavigationBar.$r8$lambda$K7I0ugGK4L7biKOJxfyYbDfOVmU(NavigationBar.this, view);
                }
            });
        } else {
            backButton.setOnLongClickListener(null);
            recentsButton.setOnLongClickListener(null);
        }
        backButton.setLongClickable(isScreenPinningActive);
        recentsButton.setLongClickable(isScreenPinningActive);
    }

    public void updateSystemUiStateFlags() {
        int a11yButtonState = this.mNavBarHelper.getA11yButtonState();
        SysUiState flag = this.mSysUiFlagsContainer.setFlag(16, (a11yButtonState & 16) != 0).setFlag(32, (a11yButtonState & 32) != 0).setFlag(2, !isNavBarWindowVisible()).setFlag(262144, (this.mNavigationIconHints & 1) != 0);
        boolean z = false;
        if ((this.mNavigationIconHints & 4) != 0) {
            z = true;
        }
        flag.setFlag(1048576, z).setFlag(131072, allowSystemGestureIgnoringBarVisibility()).commitUpdate(this.mDisplayId);
    }

    public final boolean updateTransitionMode(int i) {
        if (this.mTransitionMode != i) {
            this.mTransitionMode = i;
            checkNavBarModes();
            AutoHideController autoHideController = this.mAutoHideController;
            if (autoHideController != null) {
                autoHideController.touchAutoHide();
                return true;
            }
            return true;
        }
        return false;
    }
}