package com.android.systemui.navigationbar.gestural;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.input.InputManager;
import android.os.Looper;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Choreographer;
import android.view.ISystemGestureExclusionListener;
import android.view.IWindowManager;
import android.view.InputEvent;
import android.view.InputMonitor;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import com.android.internal.policy.GestureNavigationSettingsObserver;
import com.android.internal.util.custom.hwkeys.DeviceKeysConstants;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.Dependency;
import com.android.systemui.R$dimen;
import com.android.systemui.R$string;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.model.SysUiState;
import com.android.systemui.navigationbar.NavigationModeController;
import com.android.systemui.navigationbar.gestural.BackPanelController;
import com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.NavigationEdgeBackPlugin;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.recents.OverviewProxyService;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.InputChannelCompat;
import com.android.systemui.shared.system.QuickStepContract;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.shared.tracing.ProtoTraceable;
import com.android.systemui.tracing.ProtoTracer;
import com.android.systemui.tracing.nano.EdgeBackGestureHandlerProto;
import com.android.systemui.tracing.nano.SystemUiTraceProto;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.Assert;
import com.android.wm.shell.back.BackAnimation;
import com.android.wm.shell.pip.Pip;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/gestural/EdgeBackGestureHandler.class */
public class EdgeBackGestureHandler implements PluginListener<NavigationEdgeBackPlugin>, ProtoTraceable<SystemUiTraceProto>, TunerService.Tunable {
    public static final int MAX_LONG_PRESS_TIMEOUT = SystemProperties.getInt("gestures.back_timeout", 250);
    public boolean mAllowGesture;
    public BackAnimation mBackAnimation;
    public final NavigationEdgeBackPlugin.BackCallback mBackCallback;
    public BackGestureTfClassifierProvider mBackGestureTfClassifierProvider;
    public final Provider<BackGestureTfClassifierProvider> mBackGestureTfClassifierProviderProvider;
    public final BackPanelController.Factory mBackPanelControllerFactory;
    public float mBackSwipeProgressThreshold;
    public float mBackSwipeTriggerThreshold;
    public final Executor mBackgroundExecutor;
    public float mBottomGestureHeight;
    public final Context mContext;
    public boolean mDisabledForQuickstep;
    public final int mDisplayId;
    public final Point mDisplaySize;
    public final PointF mDownPoint;
    public NavigationEdgeBackPlugin mEdgeBackPlugin;
    public int mEdgeHeight;
    public int mEdgeWidthLeft;
    public int mEdgeWidthRight;
    public final PointF mEndPoint;
    public final Region mExcludeRegion;
    public final FalsingManager mFalsingManager;
    public final FeatureFlags mFeatureFlags;
    public final List<ComponentName> mGestureBlockingActivities;
    public boolean mGestureBlockingActivityRunning;
    public LogArray mGestureLogInsideInsets;
    public LogArray mGestureLogOutsideInsets;
    public final GestureNavigationSettingsObserver mGestureNavigationSettingsObserver;
    public boolean mInRejectedExclusion;
    public InputChannelCompat.InputEventReceiver mInputEventReceiver;
    public InputMonitor mInputMonitor;
    public boolean mIsAttached;
    public boolean mIsBackGestureAllowed;
    public boolean mIsEnabled;
    public boolean mIsGesturalModeEnabled;
    public boolean mIsInPip;
    public boolean mIsLongSwipeEnabled;
    public boolean mIsNavBarShownTransiently;
    public boolean mIsNewBackAffordanceEnabled;
    public boolean mIsOnLeftEdge;
    public final Configuration mLastReportedConfig;
    public int mLeftInset;
    public boolean mLogGesture;
    public final int mLongPressTimeout;
    public int mMLEnableWidth;
    public boolean mMLModelIsLoading;
    public float mMLModelThreshold;
    public float mMLResults;
    public final Executor mMainExecutor;
    public final Provider<NavigationBarEdgePanel> mNavBarEdgePanelProvider;
    public final Rect mNavBarOverlayExcludedBounds;
    public final NavigationModeController mNavigationModeController;
    public final Consumer<Boolean> mOnIsInPipStateChangedListener;
    public final OverviewProxyService mOverviewProxyService;
    public String mPackageName;
    public final Rect mPipExcludedBounds;
    public final Optional<Pip> mPipOptional;
    public final PluginManager mPluginManager;
    public LogArray mPredictionLog;
    public final ProtoTracer mProtoTracer;
    public int mRightInset;
    public int mStartingQuickstepRotation;
    public Runnable mStateChangeCallback;
    public int mSysUiFlags;
    public final SysUiState mSysUiState;
    public final SysUiState.SysUiStateCallback mSysUiStateCallback;
    public boolean mThresholdCrossed;
    public float mTouchSlop;
    public final Region mUnrestrictedExcludeRegion;
    public boolean mUseMLModel;
    public final UserTracker.Callback mUserChangedCallback;
    public final UserTracker mUserTracker;
    public final ViewConfiguration mViewConfiguration;
    public Map<String, Integer> mVocab;
    public final WindowManager mWindowManager;
    public final IWindowManager mWindowManagerService;
    public ISystemGestureExclusionListener mGestureExclusionListener = new AnonymousClass1();
    public OverviewProxyService.OverviewProxyListener mQuickSwitchListener = new OverviewProxyService.OverviewProxyListener() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler.2
        {
            EdgeBackGestureHandler.this = this;
        }

        @Override // com.android.systemui.recents.OverviewProxyService.OverviewProxyListener
        public void onPrioritizedRotation(int i) {
            EdgeBackGestureHandler.this.mStartingQuickstepRotation = i;
            EdgeBackGestureHandler edgeBackGestureHandler = EdgeBackGestureHandler.this;
            edgeBackGestureHandler.updateDisabledForQuickstep(edgeBackGestureHandler.mLastReportedConfig);
        }
    };
    public TaskStackChangeListener mTaskStackListener = new TaskStackChangeListener() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler.3
        {
            EdgeBackGestureHandler.this = this;
        }

        public void onTaskCreated(int i, ComponentName componentName) {
            if (componentName == null) {
                EdgeBackGestureHandler.this.mPackageName = "_UNKNOWN";
                return;
            }
            EdgeBackGestureHandler.this.mPackageName = componentName.getPackageName();
        }

        public void onTaskStackChanged() {
            EdgeBackGestureHandler edgeBackGestureHandler = EdgeBackGestureHandler.this;
            edgeBackGestureHandler.mGestureBlockingActivityRunning = edgeBackGestureHandler.isGestureBlockingActivityRunning();
        }
    };
    public DeviceConfig.OnPropertiesChangedListener mOnPropertiesChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler.4
        {
            EdgeBackGestureHandler.this = this;
        }

        public void onPropertiesChanged(DeviceConfig.Properties properties) {
            if ("systemui".equals(properties.getNamespace())) {
                if (properties.getKeyset().contains("back_gesture_ml_model_threshold") || properties.getKeyset().contains("use_back_gesture_ml_model") || properties.getKeyset().contains("back_gesture_ml_model_name")) {
                    EdgeBackGestureHandler.this.updateMLModelState();
                }
            }
        }
    };

    /* renamed from: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$1 */
    /* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/gestural/EdgeBackGestureHandler$1.class */
    public class AnonymousClass1 extends ISystemGestureExclusionListener.Stub {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$1$$ExternalSyntheticLambda0.run():void] */
        /* renamed from: $r8$lambda$mTkqEjmSSci_Q3iHOkbxr6-0w7Y */
        public static /* synthetic */ void m3497$r8$lambda$mTkqEjmSSci_Q3iHOkbxr60w7Y(AnonymousClass1 anonymousClass1, Region region, Region region2) {
            anonymousClass1.lambda$onSystemGestureExclusionChanged$0(region, region2);
        }

        public AnonymousClass1() {
            EdgeBackGestureHandler.this = r4;
        }

        public /* synthetic */ void lambda$onSystemGestureExclusionChanged$0(Region region, Region region2) {
            EdgeBackGestureHandler.this.mExcludeRegion.set(region);
            Region region3 = EdgeBackGestureHandler.this.mUnrestrictedExcludeRegion;
            if (region2 != null) {
                region = region2;
            }
            region3.set(region);
        }

        public void onSystemGestureExclusionChanged(int i, final Region region, final Region region2) {
            if (i == EdgeBackGestureHandler.this.mDisplayId) {
                EdgeBackGestureHandler.this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        EdgeBackGestureHandler.AnonymousClass1.m3497$r8$lambda$mTkqEjmSSci_Q3iHOkbxr60w7Y(EdgeBackGestureHandler.AnonymousClass1.this, region, region2);
                    }
                });
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/gestural/EdgeBackGestureHandler$Factory.class */
    public static class Factory {
        public final Provider<BackGestureTfClassifierProvider> mBackGestureTfClassifierProviderProvider;
        public final BackPanelController.Factory mBackPanelControllerFactory;
        public final Executor mBackgroundExecutor;
        public final Executor mExecutor;
        public final FalsingManager mFalsingManager;
        public final FeatureFlags mFeatureFlags;
        public final Provider<NavigationBarEdgePanel> mNavBarEdgePanelProvider;
        public final NavigationModeController mNavigationModeController;
        public final OverviewProxyService mOverviewProxyService;
        public final Optional<Pip> mPipOptional;
        public final PluginManager mPluginManager;
        public final ProtoTracer mProtoTracer;
        public final SysUiState mSysUiState;
        public final UserTracker mUserTracker;
        public final ViewConfiguration mViewConfiguration;
        public final WindowManager mWindowManager;
        public final IWindowManager mWindowManagerService;

        public Factory(OverviewProxyService overviewProxyService, SysUiState sysUiState, PluginManager pluginManager, Executor executor, Executor executor2, UserTracker userTracker, ProtoTracer protoTracer, NavigationModeController navigationModeController, BackPanelController.Factory factory, ViewConfiguration viewConfiguration, WindowManager windowManager, IWindowManager iWindowManager, Optional<Pip> optional, FalsingManager falsingManager, Provider<NavigationBarEdgePanel> provider, Provider<BackGestureTfClassifierProvider> provider2, FeatureFlags featureFlags) {
            this.mOverviewProxyService = overviewProxyService;
            this.mSysUiState = sysUiState;
            this.mPluginManager = pluginManager;
            this.mExecutor = executor;
            this.mBackgroundExecutor = executor2;
            this.mUserTracker = userTracker;
            this.mProtoTracer = protoTracer;
            this.mNavigationModeController = navigationModeController;
            this.mBackPanelControllerFactory = factory;
            this.mViewConfiguration = viewConfiguration;
            this.mWindowManager = windowManager;
            this.mWindowManagerService = iWindowManager;
            this.mPipOptional = optional;
            this.mFalsingManager = falsingManager;
            this.mNavBarEdgePanelProvider = provider;
            this.mBackGestureTfClassifierProviderProvider = provider2;
            this.mFeatureFlags = featureFlags;
        }

        public EdgeBackGestureHandler create(Context context) {
            return new EdgeBackGestureHandler(context, this.mOverviewProxyService, this.mSysUiState, this.mPluginManager, this.mExecutor, this.mBackgroundExecutor, this.mUserTracker, this.mProtoTracer, this.mNavigationModeController, this.mBackPanelControllerFactory, this.mViewConfiguration, this.mWindowManager, this.mWindowManagerService, this.mPipOptional, this.mFalsingManager, this.mNavBarEdgePanelProvider, this.mBackGestureTfClassifierProviderProvider, this.mFeatureFlags);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/gestural/EdgeBackGestureHandler$LogArray.class */
    public static class LogArray extends ArrayDeque<String> {
        private final int mLength;

        public LogArray(int i) {
            this.mLength = i;
        }

        public void log(String str) {
            if (size() >= this.mLength) {
                removeFirst();
            }
            addLast(str);
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda4.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$6YYa8XbhWWeUASLwoobF7I617dM(EdgeBackGestureHandler edgeBackGestureHandler, Pip pip) {
        edgeBackGestureHandler.lambda$updateIsEnabled$2(pip);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda7.run():void] */
    public static /* synthetic */ void $r8$lambda$Ccs5JueYV60wUE6G_ABFDExkeuk(EdgeBackGestureHandler edgeBackGestureHandler, BackGestureTfClassifierProvider backGestureTfClassifierProvider, Map map, float f) {
        edgeBackGestureHandler.lambda$loadMLModel$4(backGestureTfClassifierProvider, map, f);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda2.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$DDyljqrqdjyQEsyPkZ5CFT7W60k(Pip pip) {
        pip.setOnIsInPipStateChangedListener(null);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda0.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$MBjTQiAqxeEIIYXyEXqRKR02nMQ(EdgeBackGestureHandler edgeBackGestureHandler, Boolean bool) {
        edgeBackGestureHandler.lambda$new$0(bool);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda6.run():void] */
    /* renamed from: $r8$lambda$l9RrYkiM-nYt9XKW6ik8Rc4mDck */
    public static /* synthetic */ void m3477$r8$lambda$l9RrYkiMnYt9XKW6ik8Rc4mDck(EdgeBackGestureHandler edgeBackGestureHandler) {
        edgeBackGestureHandler.lambda$updateMLModelState$3();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda5.onInputEvent(android.view.InputEvent):void] */
    public static /* synthetic */ void $r8$lambda$pnKD_sJ_AQoKVUXkvmwEaDoHMYs(EdgeBackGestureHandler edgeBackGestureHandler, InputEvent inputEvent) {
        edgeBackGestureHandler.onInputEvent(inputEvent);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda1.run():void] */
    public static /* synthetic */ void $r8$lambda$tLJhVVQ9rTLXNzujC13aXAWGRDc(EdgeBackGestureHandler edgeBackGestureHandler) {
        edgeBackGestureHandler.onNavigationSettingsChanged();
    }

    public EdgeBackGestureHandler(Context context, OverviewProxyService overviewProxyService, SysUiState sysUiState, PluginManager pluginManager, Executor executor, Executor executor2, UserTracker userTracker, ProtoTracer protoTracer, NavigationModeController navigationModeController, BackPanelController.Factory factory, ViewConfiguration viewConfiguration, WindowManager windowManager, IWindowManager iWindowManager, Optional<Pip> optional, FalsingManager falsingManager, Provider<NavigationBarEdgePanel> provider, Provider<BackGestureTfClassifierProvider> provider2, FeatureFlags featureFlags) {
        Configuration configuration = new Configuration();
        this.mLastReportedConfig = configuration;
        this.mGestureBlockingActivities = new ArrayList();
        this.mDisplaySize = new Point();
        this.mPipExcludedBounds = new Rect();
        this.mNavBarOverlayExcludedBounds = new Rect();
        this.mExcludeRegion = new Region();
        this.mUnrestrictedExcludeRegion = new Region();
        this.mStartingQuickstepRotation = -1;
        this.mDownPoint = new PointF();
        this.mEndPoint = new PointF();
        this.mThresholdCrossed = false;
        this.mAllowGesture = false;
        this.mLogGesture = false;
        this.mInRejectedExclusion = false;
        this.mPredictionLog = new LogArray(10);
        this.mGestureLogInsideInsets = new LogArray(10);
        this.mGestureLogOutsideInsets = new LogArray(10);
        this.mBackCallback = new NavigationEdgeBackPlugin.BackCallback() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler.5
            {
                EdgeBackGestureHandler.this = this;
            }

            @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin.BackCallback
            public void cancelBack() {
                if (EdgeBackGestureHandler.this.mBackAnimation != null) {
                    EdgeBackGestureHandler.this.mBackAnimation.setTriggerBack(false);
                }
                EdgeBackGestureHandler.this.logGesture(4);
            }

            @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin.BackCallback
            public void setTriggerBack(boolean z) {
                if (EdgeBackGestureHandler.this.mBackAnimation != null) {
                    EdgeBackGestureHandler.this.mBackAnimation.setTriggerBack(z);
                }
            }

            @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin.BackCallback
            public void setTriggerLongSwipe(boolean z) {
                if (EdgeBackGestureHandler.this.mBackAnimation != null) {
                    EdgeBackGestureHandler.this.mBackAnimation.setTriggerLongSwipe(z);
                }
            }

            @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin.BackCallback
            public void triggerBack(boolean z) {
                EdgeBackGestureHandler.this.mFalsingManager.isFalseTouch(16);
                if (EdgeBackGestureHandler.this.mBackAnimation == null) {
                    EdgeBackGestureHandler.this.sendEvent(0, 4, z ? 2048 : 0);
                    EdgeBackGestureHandler.this.sendEvent(1, 4, z ? 2048 : 0);
                } else {
                    EdgeBackGestureHandler.this.mBackAnimation.setTriggerBack(true);
                }
                EdgeBackGestureHandler edgeBackGestureHandler = EdgeBackGestureHandler.this;
                int i = 1;
                if (edgeBackGestureHandler.mInRejectedExclusion) {
                    i = 2;
                }
                edgeBackGestureHandler.logGesture(i);
            }
        };
        this.mSysUiStateCallback = new SysUiState.SysUiStateCallback() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler.6
            {
                EdgeBackGestureHandler.this = this;
            }

            @Override // com.android.systemui.model.SysUiState.SysUiStateCallback
            public void onSystemUiStateChanged(int i) {
                EdgeBackGestureHandler.this.mSysUiFlags = i;
            }
        };
        this.mOnIsInPipStateChangedListener = new Consumer() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                EdgeBackGestureHandler.$r8$lambda$MBjTQiAqxeEIIYXyEXqRKR02nMQ(EdgeBackGestureHandler.this, (Boolean) obj);
            }
        };
        this.mUserChangedCallback = new UserTracker.Callback() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler.7
            {
                EdgeBackGestureHandler.this = this;
            }

            public void onUserChanged(int i, Context context2) {
                EdgeBackGestureHandler.this.updateIsEnabled();
                EdgeBackGestureHandler.this.updateCurrentUserResources();
            }
        };
        this.mContext = context;
        this.mDisplayId = context.getDisplayId();
        this.mMainExecutor = executor;
        this.mBackgroundExecutor = executor2;
        this.mUserTracker = userTracker;
        this.mOverviewProxyService = overviewProxyService;
        this.mSysUiState = sysUiState;
        this.mPluginManager = pluginManager;
        this.mProtoTracer = protoTracer;
        this.mNavigationModeController = navigationModeController;
        this.mBackPanelControllerFactory = factory;
        this.mViewConfiguration = viewConfiguration;
        this.mWindowManager = windowManager;
        this.mWindowManagerService = iWindowManager;
        this.mPipOptional = optional;
        this.mFalsingManager = falsingManager;
        this.mNavBarEdgePanelProvider = provider;
        this.mBackGestureTfClassifierProviderProvider = provider2;
        this.mFeatureFlags = featureFlags;
        configuration.setTo(context.getResources().getConfiguration());
        ComponentName unflattenFromString = ComponentName.unflattenFromString(context.getString(17040039));
        if (unflattenFromString != null) {
            String packageName = unflattenFromString.getPackageName();
            PackageManager packageManager = context.getPackageManager();
            try {
                Resources resourcesForApplication = packageManager.getResourcesForApplication(packageManager.getApplicationInfo(packageName, 9728));
                int identifier = resourcesForApplication.getIdentifier("gesture_blocking_activities", "array", packageName);
                if (identifier == 0) {
                    Log.e("EdgeBackGestureHandler", "No resource found for gesture-blocking activities");
                } else {
                    for (String str : resourcesForApplication.getStringArray(identifier)) {
                        this.mGestureBlockingActivities.add(ComponentName.unflattenFromString(str));
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.e("EdgeBackGestureHandler", "Failed to add gesture blocking activities", e);
            }
        }
        this.mLongPressTimeout = Math.min(MAX_LONG_PRESS_TIMEOUT, ViewConfiguration.getLongPressTimeout());
        this.mGestureNavigationSettingsObserver = new GestureNavigationSettingsObserver(this.mContext.getMainThreadHandler(), this.mContext, new Runnable() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                EdgeBackGestureHandler.$r8$lambda$tLJhVVQ9rTLXNzujC13aXAWGRDc(EdgeBackGestureHandler.this);
            }
        });
        updateCurrentUserResources();
    }

    public /* synthetic */ void lambda$new$0(Boolean bool) {
        this.mIsInPip = bool.booleanValue();
    }

    public /* synthetic */ void lambda$updateIsEnabled$2(Pip pip) {
        pip.setOnIsInPipStateChangedListener(this.mOnIsInPipStateChangedListener);
    }

    public final void cancelGesture(MotionEvent motionEvent) {
        this.mAllowGesture = false;
        this.mLogGesture = false;
        this.mInRejectedExclusion = false;
        MotionEvent obtain = MotionEvent.obtain(motionEvent);
        obtain.setAction(3);
        this.mEdgeBackPlugin.onMotionEvent(obtain);
        dispatchToBackAnimation(obtain);
        obtain.recycle();
    }

    public final WindowManager.LayoutParams createLayoutParams() {
        Resources resources = this.mContext.getResources();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(resources.getDimensionPixelSize(R$dimen.navigation_edge_panel_width), resources.getDimensionPixelSize(R$dimen.navigation_edge_panel_height), 2024, 280, -3);
        layoutParams.accessibilityTitle = this.mContext.getString(R$string.nav_bar_edge_panel);
        layoutParams.windowAnimations = 0;
        layoutParams.privateFlags |= 2097168;
        layoutParams.setTitle("EdgeBackGestureHandler" + this.mContext.getDisplayId());
        layoutParams.setFitInsetsTypes(0);
        layoutParams.setTrustedOverlay();
        return layoutParams;
    }

    public final void dispatchToBackAnimation(MotionEvent motionEvent) {
        BackAnimation backAnimation = this.mBackAnimation;
        if (backAnimation != null) {
            backAnimation.onBackMotion(motionEvent.getX(), motionEvent.getY(), motionEvent.getActionMasked(), !this.mIsOnLeftEdge ? 1 : 0);
        }
    }

    public final void disposeInputChannel() {
        InputChannelCompat.InputEventReceiver inputEventReceiver = this.mInputEventReceiver;
        if (inputEventReceiver != null) {
            inputEventReceiver.dispose();
            this.mInputEventReceiver = null;
        }
        InputMonitor inputMonitor = this.mInputMonitor;
        if (inputMonitor != null) {
            inputMonitor.dispose();
            this.mInputMonitor = null;
        }
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println("EdgeBackGestureHandler:");
        printWriter.println("  mIsEnabled=" + this.mIsEnabled);
        printWriter.println("  mIsAttached=" + this.mIsAttached);
        printWriter.println("  mIsBackGestureAllowed=" + this.mIsBackGestureAllowed);
        printWriter.println("  mIsGesturalModeEnabled=" + this.mIsGesturalModeEnabled);
        printWriter.println("  mIsNavBarShownTransiently=" + this.mIsNavBarShownTransiently);
        printWriter.println("  mGestureBlockingActivityRunning=" + this.mGestureBlockingActivityRunning);
        printWriter.println("  mAllowGesture=" + this.mAllowGesture);
        printWriter.println("  mUseMLModel=" + this.mUseMLModel);
        printWriter.println("  mDisabledForQuickstep=" + this.mDisabledForQuickstep);
        printWriter.println("  mStartingQuickstepRotation=" + this.mStartingQuickstepRotation);
        printWriter.println("  mInRejectedExclusion=" + this.mInRejectedExclusion);
        printWriter.println("  mExcludeRegion=" + this.mExcludeRegion);
        printWriter.println("  mUnrestrictedExcludeRegion=" + this.mUnrestrictedExcludeRegion);
        printWriter.println("  mIsInPip=" + this.mIsInPip);
        printWriter.println("  mPipExcludedBounds=" + this.mPipExcludedBounds);
        printWriter.println("  mNavBarOverlayExcludedBounds=" + this.mNavBarOverlayExcludedBounds);
        printWriter.println("  mEdgeWidthLeft=" + this.mEdgeWidthLeft);
        printWriter.println("  mEdgeWidthRight=" + this.mEdgeWidthRight);
        printWriter.println("  mLeftInset=" + this.mLeftInset);
        printWriter.println("  mRightInset=" + this.mRightInset);
        printWriter.println("  mMLEnableWidth=" + this.mMLEnableWidth);
        printWriter.println("  mMLModelThreshold=" + this.mMLModelThreshold);
        printWriter.println("  mTouchSlop=" + this.mTouchSlop);
        printWriter.println("  mBottomGestureHeight=" + this.mBottomGestureHeight);
        printWriter.println("  mPredictionLog=" + String.join("\n", this.mPredictionLog));
        printWriter.println("  mGestureLogInsideInsets=" + String.join("\n", this.mGestureLogInsideInsets));
        printWriter.println("  mGestureLogOutsideInsets=" + String.join("\n", this.mGestureLogOutsideInsets));
        printWriter.println("  mEdgeBackPlugin=" + this.mEdgeBackPlugin);
        NavigationEdgeBackPlugin navigationEdgeBackPlugin = this.mEdgeBackPlugin;
        if (navigationEdgeBackPlugin != null) {
            navigationEdgeBackPlugin.dump(printWriter);
        }
    }

    public final int getBackGesturePredictionsCategory(int i, int i2, int i3) {
        int i4;
        BackGestureTfClassifierProvider backGestureTfClassifierProvider = this.mBackGestureTfClassifierProvider;
        if (backGestureTfClassifierProvider == null || i3 == -1) {
            return -1;
        }
        int i5 = this.mDisplaySize.x;
        if (i <= i5 / 2.0d) {
            i4 = 1;
        } else {
            i = i5 - i;
            i4 = 2;
        }
        float predict = backGestureTfClassifierProvider.predict(new Object[]{new long[]{i5}, new long[]{i}, new long[]{i4}, new long[]{i3}, new long[]{i2}});
        this.mMLResults = predict;
        if (predict == -1.0f) {
            return -1;
        }
        return predict >= this.mMLModelThreshold ? 1 : 0;
    }

    public final boolean isGestureBlockingActivityRunning() {
        ActivityManager.RunningTaskInfo runningTask = ActivityManagerWrapper.getInstance().getRunningTask();
        ComponentName componentName = runningTask == null ? null : runningTask.topActivity;
        if (componentName != null) {
            this.mPackageName = componentName.getPackageName();
        } else {
            this.mPackageName = "_UNKNOWN";
        }
        return componentName != null && this.mGestureBlockingActivities.contains(componentName);
    }

    public boolean isHandlingGestures() {
        return this.mIsEnabled && this.mIsBackGestureAllowed;
    }

    public final boolean isWithinInsets(int i, int i2) {
        float f = i2;
        Point point = this.mDisplaySize;
        int i3 = point.y;
        float f2 = i3;
        float f3 = this.mBottomGestureHeight;
        if (f >= f2 - f3) {
            return false;
        }
        int i4 = this.mEdgeHeight;
        if (i4 == 0 || f >= (i3 - f3) - i4) {
            return i <= (this.mEdgeWidthLeft + this.mLeftInset) * 2 || i >= point.x - ((this.mEdgeWidthRight + this.mRightInset) * 2);
        }
        return false;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v30, resolved type: boolean */
    /* JADX DEBUG: Multi-variable search result rejected for r0v41, resolved type: boolean */
    /* JADX WARN: Multi-variable type inference failed */
    public final boolean isWithinTouchRegion(int i, int i2) {
        if ((this.mIsInPip && this.mPipExcludedBounds.contains(i, i2)) || this.mNavBarOverlayExcludedBounds.contains(i, i2)) {
            return false;
        }
        Map<String, Integer> map = this.mVocab;
        int intValue = map != null ? map.getOrDefault(this.mPackageName, -1).intValue() : -1;
        int i3 = this.mEdgeWidthLeft;
        int i4 = this.mLeftInset;
        int i5 = (i < i3 + i4 || i >= (this.mDisplaySize.x - this.mEdgeWidthRight) - this.mRightInset) ? 1 : 0;
        int i6 = i5;
        if (i5 != 0) {
            int i7 = this.mMLEnableWidth;
            i6 = i5;
            if (!(i < i4 + i7 || i >= (this.mDisplaySize.x - i7) - this.mRightInset)) {
                i6 = i5;
                if (this.mUseMLModel) {
                    i6 = i5;
                    if (!this.mMLModelIsLoading) {
                        int backGesturePredictionsCategory = getBackGesturePredictionsCategory(i, i2, intValue);
                        i6 = i5;
                        if (backGesturePredictionsCategory != -1) {
                            i6 = backGesturePredictionsCategory == 1 ? 1 : 0;
                        }
                    }
                }
            }
        }
        this.mPredictionLog.log(String.format("Prediction [%d,%d,%d,%d,%f,%d]", Long.valueOf(System.currentTimeMillis()), Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(intValue), Float.valueOf(this.mMLResults), Integer.valueOf(i6)));
        if (this.mIsNavBarShownTransiently) {
            this.mLogGesture = true;
            return i6;
        } else if (!this.mExcludeRegion.contains(i, i2)) {
            this.mInRejectedExclusion = this.mUnrestrictedExcludeRegion.contains(i, i2);
            this.mLogGesture = true;
            return i6;
        } else if (i6 != 0) {
            PointF pointF = this.mEndPoint;
            pointF.x = -1.0f;
            pointF.y = -1.0f;
            this.mLogGesture = true;
            logGesture(3);
            return false;
        } else {
            return false;
        }
    }

    /* renamed from: loadMLModel */
    public final void lambda$updateMLModelState$3() {
        BackGestureTfClassifierProvider backGestureTfClassifierProvider = (BackGestureTfClassifierProvider) this.mBackGestureTfClassifierProviderProvider.get();
        final float f = DeviceConfig.getFloat("systemui", "back_gesture_ml_model_threshold", 0.9f);
        Map<String, Integer> map = null;
        BackGestureTfClassifierProvider backGestureTfClassifierProvider2 = backGestureTfClassifierProvider;
        if (backGestureTfClassifierProvider != null) {
            backGestureTfClassifierProvider2 = backGestureTfClassifierProvider;
            if (!backGestureTfClassifierProvider.isActive()) {
                backGestureTfClassifierProvider.release();
                Log.w("EdgeBackGestureHandler", "Cannot load model because it isn't active");
                backGestureTfClassifierProvider2 = null;
            }
        }
        if (backGestureTfClassifierProvider2 != null) {
            Trace.beginSection("EdgeBackGestureHandler#loadVocab");
            map = backGestureTfClassifierProvider2.loadVocab(this.mContext.getAssets());
            Trace.endSection();
        }
        final BackGestureTfClassifierProvider backGestureTfClassifierProvider3 = backGestureTfClassifierProvider2;
        final Map<String, Integer> map2 = map;
        this.mMainExecutor.execute(new Runnable() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                EdgeBackGestureHandler.$r8$lambda$Ccs5JueYV60wUE6G_ABFDExkeuk(EdgeBackGestureHandler.this, backGestureTfClassifierProvider3, map2, f);
            }
        });
    }

    public final void logGesture(int i) {
        if (this.mLogGesture) {
            this.mLogGesture = false;
            Map<String, Integer> map = this.mVocab;
            String str = (!this.mUseMLModel || map == null || !map.containsKey(this.mPackageName) || map.get(this.mPackageName).intValue() >= 100) ? "" : this.mPackageName;
            PointF pointF = this.mDownPoint;
            float f = pointF.y;
            int i2 = (int) f;
            int i3 = this.mIsOnLeftEdge ? 1 : 2;
            int i4 = (int) pointF.x;
            int i5 = (int) f;
            PointF pointF2 = this.mEndPoint;
            SysUiStatsLog.write(224, i, i2, i3, i4, i5, (int) pointF2.x, (int) pointF2.y, this.mEdgeWidthLeft + this.mLeftInset, this.mDisplaySize.x - (this.mEdgeWidthRight + this.mRightInset), this.mUseMLModel ? this.mMLResults : -2.0f, str);
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        if (this.mStartingQuickstepRotation > -1) {
            updateDisabledForQuickstep(configuration);
        }
        Log.i("NoBackGesture", "Config changed: newConfig=" + configuration + " lastReportedConfig=" + this.mLastReportedConfig);
        this.mLastReportedConfig.updateFrom(configuration);
        updateDisplaySize();
    }

    public final void onInputEvent(InputEvent inputEvent) {
        if (inputEvent instanceof MotionEvent) {
            onMotionEvent((MotionEvent) inputEvent);
        }
    }

    /* renamed from: onMLModelLoadFinished */
    public final void lambda$loadMLModel$4(BackGestureTfClassifierProvider backGestureTfClassifierProvider, Map<String, Integer> map, float f) {
        Assert.isMainThread();
        this.mMLModelIsLoading = false;
        if (this.mUseMLModel) {
            this.mBackGestureTfClassifierProvider = backGestureTfClassifierProvider;
            this.mVocab = map;
            this.mMLModelThreshold = f;
            return;
        }
        if (backGestureTfClassifierProvider != null) {
            backGestureTfClassifierProvider.release();
        }
        Log.d("EdgeBackGestureHandler", "Model finished loading but isn't needed.");
    }

    public final void onMotionEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mInputEventReceiver.setBatchingEnabled(false);
            this.mIsOnLeftEdge = motionEvent.getX() <= ((float) (this.mEdgeWidthLeft + this.mLeftInset));
            this.mMLResults = ActionBarShadowController.ELEVATION_LOW;
            this.mLogGesture = false;
            this.mInRejectedExclusion = false;
            boolean isWithinInsets = isWithinInsets((int) motionEvent.getX(), (int) motionEvent.getY());
            boolean z = !this.mDisabledForQuickstep && this.mIsBackGestureAllowed && isWithinInsets && !this.mGestureBlockingActivityRunning && !QuickStepContract.isBackGestureDisabled(this.mSysUiFlags) && isWithinTouchRegion((int) motionEvent.getX(), (int) motionEvent.getY());
            this.mAllowGesture = z;
            if (z) {
                this.mEdgeBackPlugin.setIsLeftPanel(this.mIsOnLeftEdge);
                this.mEdgeBackPlugin.onMotionEvent(motionEvent);
                dispatchToBackAnimation(motionEvent);
            }
            if (this.mLogGesture) {
                this.mDownPoint.set(motionEvent.getX(), motionEvent.getY());
                this.mEndPoint.set(-1.0f, -1.0f);
                this.mThresholdCrossed = false;
            }
            (isWithinInsets ? this.mGestureLogInsideInsets : this.mGestureLogOutsideInsets).log(String.format("Gesture [%d,alw=%B,%B,%B,%B,disp=%s,wl=%d,il=%d,wr=%d,ir=%d,excl=%s]", Long.valueOf(System.currentTimeMillis()), Boolean.valueOf(this.mAllowGesture), Boolean.valueOf(this.mIsOnLeftEdge), Boolean.valueOf(this.mIsBackGestureAllowed), Boolean.valueOf(QuickStepContract.isBackGestureDisabled(this.mSysUiFlags)), this.mDisplaySize, Integer.valueOf(this.mEdgeWidthLeft), Integer.valueOf(this.mLeftInset), Integer.valueOf(this.mEdgeWidthRight), Integer.valueOf(this.mRightInset), this.mExcludeRegion));
        } else if (this.mAllowGesture || this.mLogGesture) {
            if (!this.mThresholdCrossed) {
                this.mEndPoint.x = (int) motionEvent.getX();
                this.mEndPoint.y = (int) motionEvent.getY();
                if (actionMasked == 5) {
                    if (this.mAllowGesture) {
                        logGesture(6);
                        cancelGesture(motionEvent);
                    }
                    this.mLogGesture = false;
                    return;
                } else if (actionMasked == 2) {
                    if (motionEvent.getEventTime() - motionEvent.getDownTime() > this.mLongPressTimeout) {
                        if (this.mAllowGesture) {
                            logGesture(7);
                            cancelGesture(motionEvent);
                        }
                        this.mLogGesture = false;
                        return;
                    }
                    float abs = Math.abs(motionEvent.getX() - this.mDownPoint.x);
                    float abs2 = Math.abs(motionEvent.getY() - this.mDownPoint.y);
                    if (abs2 > abs && abs2 > this.mTouchSlop) {
                        if (this.mAllowGesture) {
                            logGesture(8);
                            cancelGesture(motionEvent);
                        }
                        this.mLogGesture = false;
                        return;
                    } else if (abs > abs2 && abs > this.mTouchSlop) {
                        if (this.mAllowGesture) {
                            this.mThresholdCrossed = true;
                            this.mInputMonitor.pilferPointers();
                            this.mInputEventReceiver.setBatchingEnabled(true);
                        } else {
                            logGesture(5);
                        }
                    }
                }
            }
            if (this.mAllowGesture) {
                this.mEdgeBackPlugin.onMotionEvent(motionEvent);
                dispatchToBackAnimation(motionEvent);
            }
        }
        this.mProtoTracer.scheduleFrameUpdate();
    }

    public void onNavBarAttached() {
        this.mIsAttached = true;
        this.mProtoTracer.add(this);
        this.mOverviewProxyService.addCallback(this.mQuickSwitchListener);
        this.mSysUiState.addCallback(this.mSysUiStateCallback);
        updateIsEnabled();
        this.mUserTracker.addCallback(this.mUserChangedCallback, this.mMainExecutor);
    }

    public void onNavBarDetached() {
        this.mIsAttached = false;
        this.mProtoTracer.remove(this);
        this.mOverviewProxyService.removeCallback(this.mQuickSwitchListener);
        this.mSysUiState.removeCallback(this.mSysUiStateCallback);
        updateIsEnabled();
        this.mUserTracker.removeCallback(this.mUserChangedCallback);
    }

    public void onNavBarTransientStateChanged(boolean z) {
        this.mIsNavBarShownTransiently = z;
    }

    public void onNavigationModeChanged(int i) {
        this.mIsGesturalModeEnabled = QuickStepContract.isGesturalMode(i);
        updateIsEnabled();
        updateCurrentUserResources();
    }

    public final void onNavigationSettingsChanged() {
        boolean isHandlingGestures = isHandlingGestures();
        updateCurrentUserResources();
        if (this.mStateChangeCallback == null || isHandlingGestures == isHandlingGestures()) {
            return;
        }
        this.mStateChangeCallback.run();
    }

    @Override // com.android.systemui.plugins.PluginListener
    public void onPluginConnected(NavigationEdgeBackPlugin navigationEdgeBackPlugin, Context context) {
        setEdgeBackPlugin(navigationEdgeBackPlugin);
    }

    @Override // com.android.systemui.plugins.PluginListener
    public void onPluginDisconnected(NavigationEdgeBackPlugin navigationEdgeBackPlugin) {
        resetEdgeBackPlugin();
    }

    public void onSettingsChanged() {
        updateEdgeHeightValue();
    }

    public void onTuningChanged(String str, String str2) {
        if ("customsystem:key_edge_long_swipe_action".equals(str)) {
            boolean z = false;
            if (DeviceKeysConstants.Action.fromIntSafe(TunerService.parseInteger(str2, 0)) != DeviceKeysConstants.Action.NOTHING) {
                z = true;
            }
            this.mIsLongSwipeEnabled = z;
            updateLongSwipeWidth();
        }
    }

    public final void resetEdgeBackPlugin() {
        if (this.mIsNewBackAffordanceEnabled) {
            setEdgeBackPlugin(this.mBackPanelControllerFactory.create(this.mContext));
        } else {
            setEdgeBackPlugin((NavigationEdgeBackPlugin) this.mNavBarEdgePanelProvider.get());
        }
    }

    public final boolean sendEvent(int i, int i2, int i3) {
        long uptimeMillis = SystemClock.uptimeMillis();
        KeyEvent keyEvent = new KeyEvent(uptimeMillis, uptimeMillis, i, i2, 0, 0, -1, 0, i3 | 8 | 64, 257);
        keyEvent.setDisplayId(this.mContext.getDisplay().getDisplayId());
        return InputManager.getInstance().injectInputEvent(keyEvent, 0);
    }

    public void setBackAnimation(BackAnimation backAnimation) {
        this.mBackAnimation = backAnimation;
        updateBackAnimationThresholds();
    }

    public final void setEdgeBackPlugin(NavigationEdgeBackPlugin navigationEdgeBackPlugin) {
        NavigationEdgeBackPlugin navigationEdgeBackPlugin2 = this.mEdgeBackPlugin;
        if (navigationEdgeBackPlugin2 != null) {
            navigationEdgeBackPlugin2.onDestroy();
        }
        this.mEdgeBackPlugin = navigationEdgeBackPlugin;
        navigationEdgeBackPlugin.setBackCallback(this.mBackCallback);
        this.mEdgeBackPlugin.setLayoutParams(createLayoutParams());
        updateDisplaySize();
    }

    public void setInsets(int i, int i2) {
        this.mLeftInset = i;
        this.mRightInset = i2;
        NavigationEdgeBackPlugin navigationEdgeBackPlugin = this.mEdgeBackPlugin;
        if (navigationEdgeBackPlugin != null) {
            navigationEdgeBackPlugin.setInsets(i, i2);
        }
    }

    public void setPipStashExclusionBounds(Rect rect) {
        this.mPipExcludedBounds.set(rect);
    }

    public void setStateChangeCallback(Runnable runnable) {
        this.mStateChangeCallback = runnable;
    }

    public final void updateBackAnimationThresholds() {
        BackAnimation backAnimation = this.mBackAnimation;
        if (backAnimation == null) {
            return;
        }
        backAnimation.setSwipeThresholds(this.mBackSwipeTriggerThreshold, Math.min(this.mDisplaySize.x, this.mBackSwipeProgressThreshold));
    }

    public void updateCurrentUserResources() {
        Resources resources = this.mNavigationModeController.getCurrentUserContext().getResources();
        this.mEdgeWidthLeft = this.mGestureNavigationSettingsObserver.getLeftSensitivity(resources);
        this.mEdgeWidthRight = this.mGestureNavigationSettingsObserver.getRightSensitivity(resources);
        this.mIsBackGestureAllowed = !this.mGestureNavigationSettingsObserver.areNavigationButtonForcedVisible();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        this.mBottomGestureHeight = TypedValue.applyDimension(1, DeviceConfig.getFloat("systemui", "back_gesture_bottom_height", resources.getDimension(17105363) / displayMetrics.density), displayMetrics);
        int applyDimension = (int) TypedValue.applyDimension(1, 12.0f, displayMetrics);
        this.mMLEnableWidth = applyDimension;
        int i = this.mEdgeWidthRight;
        if (applyDimension > i) {
            this.mMLEnableWidth = i;
        }
        int i2 = this.mMLEnableWidth;
        int i3 = this.mEdgeWidthLeft;
        if (i2 > i3) {
            this.mMLEnableWidth = i3;
        }
        ((TunerService) Dependency.get(TunerService.class)).addTunable(this, new String[]{"customsystem:key_edge_long_swipe_action"});
        this.mTouchSlop = this.mViewConfiguration.getScaledTouchSlop() * DeviceConfig.getFloat("systemui", "back_gesture_slop_multiplier", 0.75f);
        this.mBackSwipeTriggerThreshold = resources.getDimension(R$dimen.navigation_edge_action_drag_threshold);
        this.mBackSwipeProgressThreshold = resources.getDimension(R$dimen.navigation_edge_action_progress_threshold);
        updateBackAnimationThresholds();
    }

    public final void updateDisabledForQuickstep(Configuration configuration) {
        int rotation = configuration.windowConfiguration.getRotation();
        int i = this.mStartingQuickstepRotation;
        this.mDisabledForQuickstep = i > -1 && i != rotation;
    }

    public final void updateDisplaySize() {
        Rect maxBounds = this.mLastReportedConfig.windowConfiguration.getMaxBounds();
        this.mDisplaySize.set(maxBounds.width(), maxBounds.height());
        NavigationEdgeBackPlugin navigationEdgeBackPlugin = this.mEdgeBackPlugin;
        if (navigationEdgeBackPlugin != null) {
            navigationEdgeBackPlugin.setDisplaySize(this.mDisplaySize);
        }
        updateBackAnimationThresholds();
        updateLongSwipeWidth();
        updateEdgeHeightValue();
    }

    public final void updateEdgeHeightValue() {
        if (this.mDisplaySize == null) {
            return;
        }
        int intForUser = Settings.System.getIntForUser(this.mContext.getContentResolver(), "back_gesture_height", 0, -2);
        if (intForUser == 0) {
            this.mEdgeHeight = this.mDisplaySize.y;
        } else if (intForUser == 1) {
            this.mEdgeHeight = this.mDisplaySize.y / 2;
        } else if (intForUser == 2) {
            this.mEdgeHeight = this.mDisplaySize.y / 3;
        } else {
            this.mEdgeHeight = this.mDisplaySize.y / 6;
        }
    }

    public final void updateIsEnabled() {
        boolean z = this.mIsAttached && this.mIsGesturalModeEnabled;
        if (z == this.mIsEnabled) {
            return;
        }
        this.mIsEnabled = z;
        disposeInputChannel();
        NavigationEdgeBackPlugin navigationEdgeBackPlugin = this.mEdgeBackPlugin;
        if (navigationEdgeBackPlugin != null) {
            navigationEdgeBackPlugin.onDestroy();
            this.mEdgeBackPlugin = null;
        }
        if (this.mIsEnabled) {
            this.mGestureNavigationSettingsObserver.register();
            updateDisplaySize();
            TaskStackChangeListeners.getInstance().registerTaskStackListener(this.mTaskStackListener);
            final Executor executor = this.mMainExecutor;
            Objects.requireNonNull(executor);
            DeviceConfig.addOnPropertiesChangedListener("systemui", new Executor() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda3
                @Override // java.util.concurrent.Executor
                public final void execute(Runnable runnable) {
                    executor.execute(runnable);
                }
            }, this.mOnPropertiesChangedListener);
            this.mPipOptional.ifPresent(new Consumer() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda4
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    EdgeBackGestureHandler.$r8$lambda$6YYa8XbhWWeUASLwoobF7I617dM(EdgeBackGestureHandler.this, (Pip) obj);
                }
            });
            try {
                this.mWindowManagerService.registerSystemGestureExclusionListener(this.mGestureExclusionListener, this.mDisplayId);
            } catch (RemoteException | IllegalArgumentException e) {
                Log.e("EdgeBackGestureHandler", "Failed to register window manager callbacks", e);
            }
            InputMonitor monitorGestureInput = InputManager.getInstance().monitorGestureInput("edge-swipe", this.mDisplayId);
            this.mInputMonitor = monitorGestureInput;
            this.mInputEventReceiver = new InputChannelCompat.InputEventReceiver(monitorGestureInput.getInputChannel(), Looper.getMainLooper(), Choreographer.getInstance(), new InputChannelCompat.InputEventListener() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda5
                public final void onInputEvent(InputEvent inputEvent) {
                    EdgeBackGestureHandler.$r8$lambda$pnKD_sJ_AQoKVUXkvmwEaDoHMYs(EdgeBackGestureHandler.this, inputEvent);
                }
            });
            this.mIsNewBackAffordanceEnabled = this.mFeatureFlags.isEnabled(Flags.NEW_BACK_AFFORDANCE);
            resetEdgeBackPlugin();
            this.mPluginManager.addPluginListener((PluginListener) this, NavigationEdgeBackPlugin.class, false);
            updateLongSwipeWidth();
        } else {
            this.mGestureNavigationSettingsObserver.unregister();
            this.mPluginManager.removePluginListener(this);
            TaskStackChangeListeners.getInstance().unregisterTaskStackListener(this.mTaskStackListener);
            DeviceConfig.removeOnPropertiesChangedListener(this.mOnPropertiesChangedListener);
            this.mPipOptional.ifPresent(new Consumer() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    EdgeBackGestureHandler.$r8$lambda$DDyljqrqdjyQEsyPkZ5CFT7W60k((Pip) obj);
                }
            });
            try {
                this.mWindowManagerService.unregisterSystemGestureExclusionListener(this.mGestureExclusionListener, this.mDisplayId);
            } catch (RemoteException | IllegalArgumentException e2) {
                Log.e("EdgeBackGestureHandler", "Failed to unregister window manager callbacks", e2);
            }
        }
        updateMLModelState();
    }

    public final void updateLongSwipeWidth() {
        NavigationEdgeBackPlugin navigationEdgeBackPlugin;
        if (!this.mIsEnabled || (navigationEdgeBackPlugin = this.mEdgeBackPlugin) == null) {
            return;
        }
        navigationEdgeBackPlugin.setLongSwipeEnabled(this.mIsLongSwipeEnabled);
    }

    public final void updateMLModelState() {
        boolean z = false;
        if (this.mIsGesturalModeEnabled) {
            z = false;
            if (DeviceConfig.getBoolean("systemui", "use_back_gesture_ml_model", false)) {
                z = true;
            }
        }
        if (z == this.mUseMLModel) {
            return;
        }
        this.mUseMLModel = z;
        if (z) {
            Assert.isMainThread();
            if (this.mMLModelIsLoading) {
                Log.d("EdgeBackGestureHandler", "Model tried to load while already loading.");
                return;
            }
            this.mMLModelIsLoading = true;
            this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.navigationbar.gestural.EdgeBackGestureHandler$$ExternalSyntheticLambda6
                @Override // java.lang.Runnable
                public final void run() {
                    EdgeBackGestureHandler.m3477$r8$lambda$l9RrYkiMnYt9XKW6ik8Rc4mDck(EdgeBackGestureHandler.this);
                }
            });
            return;
        }
        BackGestureTfClassifierProvider backGestureTfClassifierProvider = this.mBackGestureTfClassifierProvider;
        if (backGestureTfClassifierProvider != null) {
            backGestureTfClassifierProvider.release();
            this.mBackGestureTfClassifierProvider = null;
            this.mVocab = null;
        }
    }

    public void writeToProto(SystemUiTraceProto systemUiTraceProto) {
        if (systemUiTraceProto.edgeBackGestureHandler == null) {
            systemUiTraceProto.edgeBackGestureHandler = new EdgeBackGestureHandlerProto();
        }
        systemUiTraceProto.edgeBackGestureHandler.allowGesture = this.mAllowGesture;
    }
}