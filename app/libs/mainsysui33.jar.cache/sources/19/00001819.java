package com.android.systemui.globalactions;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.Dialog;
import android.app.IActivityManager;
import android.app.admin.DevicePolicyManager;
import android.app.trust.TrustManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.service.dreams.IDreamManager;
import android.sysprop.TelephonyProperties;
import android.telecom.TelecomManager;
import android.util.ArraySet;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.GestureDetector;
import android.view.IWindowManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.colorextraction.ColorExtractor;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.util.EmergencyAffordanceManager;
import com.android.internal.util.ScreenshotHelper;
import com.android.internal.util.UserIcons;
import com.android.internal.util.custom.globalactions.CustomGlobalActions;
import com.android.internal.util.custom.globalactions.PowerMenuUtils;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.Dependency;
import com.android.systemui.MultiListLayout;
import com.android.systemui.R$color;
import com.android.systemui.R$dimen;
import com.android.systemui.R$drawable;
import com.android.systemui.R$id;
import com.android.systemui.R$integer;
import com.android.systemui.R$layout;
import com.android.systemui.R$string;
import com.android.systemui.R$style;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.controls.dagger.ControlsComponent;
import com.android.systemui.controls.ui.ControlsActivity;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.GlobalActions;
import com.android.systemui.plugins.GlobalActionsPanelPlugin;
import com.android.systemui.scrim.ScrimDrawable;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.RingerModeTracker;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.settings.SecureSettings;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsDialogLite.class */
public class GlobalActionsDialogLite implements DialogInterface.OnDismissListener, DialogInterface.OnShowListener, ConfigurationController.ConfigurationListener, GlobalActionsPanelPlugin.Callbacks, LifecycleOwner, TunerService.Tunable {
    public String[] mActions;
    public final ActivityStarter mActivityStarter;
    public MyAdapter mAdapter;
    public final ContentObserver mAirplaneModeObserver;
    public ToggleAction mAirplaneModeOn;
    public final AudioManager mAudioManager;
    public final Executor mBackgroundExecutor;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final Optional<CentralSurfaces> mCentralSurfacesOptional;
    public final ConfigurationController mConfigurationController;
    public final Context mContext;
    public final ControlsComponent mControlsComponent;
    public final CustomGlobalActions mCustomGlobalActions;
    public final DevicePolicyManager mDevicePolicyManager;
    @VisibleForTesting
    public ActionsDialogLite mDialog;
    public final DialogLaunchAnimator mDialogLaunchAnimator;
    public final IDreamManager mDreamManager;
    public final EmergencyAffordanceManager mEmergencyAffordanceManager;
    public final GlobalSettings mGlobalSettings;
    public Handler mHandler;
    public boolean mHasTelephony;
    public boolean mHasVibrator;
    public final IActivityManager mIActivityManager;
    public final IWindowManager mIWindowManager;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final LockPatternUtils mLockPatternUtils;
    public Handler mMainHandler;
    public final MetricsLogger mMetricsLogger;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public MyOverflowAdapter mOverflowAdapter;
    public MyPowerOptionsAdapter mPowerAdapter;
    public final Resources mResources;
    public MyRestartOptionsAdapter mRestartAdapter;
    public final RingerModeTracker mRingerModeTracker;
    public final ScreenshotHelper mScreenshotHelper;
    public final SecureSettings mSecureSettings;
    public final boolean mShowSilentToggle;
    public Action mSilentModeAction;
    public int mSmallestScreenWidthDp;
    public final IStatusBarService mStatusBarService;
    public final SysuiColorExtractor mSysuiColorExtractor;
    public final TelecomManager mTelecomManager;
    public final TrustManager mTrustManager;
    public final UiEventLogger mUiEventLogger;
    public final UserManager mUserManager;
    public final UserTracker mUserTracker;
    public MyUsersAdapter mUsersAdapter;
    public final GlobalActions.GlobalActionsManager mWindowManagerFuncs;
    public final LifecycleRegistry mLifecycle = new LifecycleRegistry(this);
    @VisibleForTesting
    public final ArrayList<Action> mItems = new ArrayList<>();
    @VisibleForTesting
    public final ArrayList<Action> mOverflowItems = new ArrayList<>();
    @VisibleForTesting
    public final ArrayList<Action> mPowerItems = new ArrayList<>();
    @VisibleForTesting
    public final ArrayList<Action> mRestartItems = new ArrayList<>();
    @VisibleForTesting
    public final ArrayList<Action> mUsersItems = new ArrayList<>();
    public boolean mKeyguardShowing = false;
    public boolean mDeviceProvisioned = false;
    public ToggleState mAirplaneState = ToggleState.Off;
    public boolean mIsWaitingForEcmExit = false;
    public int mDialogPressDelay = 850;
    public BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.5
        {
            GlobalActionsDialogLite.this = this;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(action) || "android.intent.action.SCREEN_OFF".equals(action)) {
                String stringExtra = intent.getStringExtra("reason");
                if ("globalactions".equals(stringExtra)) {
                    return;
                }
                GlobalActionsDialogLite.this.mHandler.sendMessage(GlobalActionsDialogLite.this.mHandler.obtainMessage(0, stringExtra));
            } else if ("android.intent.action.EMERGENCY_CALLBACK_MODE_CHANGED".equals(action) && !intent.getBooleanExtra("android.telephony.extra.PHONE_IN_ECM_STATE", false) && GlobalActionsDialogLite.this.mIsWaitingForEcmExit) {
                GlobalActionsDialogLite.this.mIsWaitingForEcmExit = false;
                GlobalActionsDialogLite.this.changeAirplaneModeSystemSetting(true);
            }
        }
    };

    /* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsDialogLite$Action.class */
    public interface Action {
        View create(Context context, View view, ViewGroup viewGroup, LayoutInflater layoutInflater);

        Drawable getIcon(Context context);

        CharSequence getMessage();

        int getMessageResId();

        boolean isEnabled();

        void onPress();

        default boolean shouldBeSeparated() {
            return false;
        }

        default boolean shouldShow() {
            return true;
        }

        boolean showBeforeProvisioning();

        boolean showDuringKeyguard();
    }

    @VisibleForTesting
    /* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsDialogLite$ActionsDialogLite.class */
    public static class ActionsDialogLite extends SystemUIDialog implements ColorExtractor.OnColorsChangedListener {
        public final MyAdapter mAdapter;
        public Drawable mBackgroundDrawable;
        public Optional<CentralSurfaces> mCentralSurfacesOptional;
        public final SysuiColorExtractor mColorExtractor;
        public ViewGroup mContainer;
        public final Context mContext;
        public GestureDetector mGestureDetector;
        @VisibleForTesting
        public GestureDetector.SimpleOnGestureListener mGestureListener;
        public MultiListLayout mGlobalActionsLayout;
        public boolean mKeyguardShowing;
        public KeyguardUpdateMonitor mKeyguardUpdateMonitor;
        public LockPatternUtils mLockPatternUtils;
        public final NotificationShadeWindowController mNotificationShadeWindowController;
        public final OnBackInvokedCallback mOnBackInvokedCallback;
        public final Runnable mOnRefreshCallback;
        public final MyOverflowAdapter mOverflowAdapter;
        public ListPopupWindow mOverflowPopup;
        public OnBackInvokedDispatcher mOverriddenBackDispatcher;
        public final MyPowerOptionsAdapter mPowerOptionsAdapter;
        public Dialog mPowerOptionsDialog;
        public final MyRestartOptionsAdapter mRestartOptionsAdapter;
        public Dialog mRestartOptionsDialog;
        public float mScrimAlpha;
        public final IStatusBarService mStatusBarService;
        public final IBinder mToken;
        public UiEventLogger mUiEventLogger;
        public final MyUsersAdapter mUsersAdapter;
        public Dialog mUsersDialog;
        public float mWindowDimAmount;

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda11.accept(java.lang.Object):void] */
        /* renamed from: $r8$lambda$-XxguCL7wOS_Xbf9nmyOZRRlEow */
        public static /* synthetic */ void m2765$r8$lambda$XxguCL7wOS_Xbf9nmyOZRRlEow(CentralSurfaces centralSurfaces) {
            centralSurfaces.animateExpandNotificationsPanel();
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda2.onClick(android.view.View):void] */
        /* renamed from: $r8$lambda$1tiLhu5niDlqASoxBepix-ordm8 */
        public static /* synthetic */ void m2766$r8$lambda$1tiLhu5niDlqASoxBepixordm8(ActionsDialogLite actionsDialogLite, View view) {
            actionsDialogLite.lambda$initializeLayout$6(view);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda7.onItemLongClick(android.widget.AdapterView, android.view.View, int, long):boolean] */
        public static /* synthetic */ boolean $r8$lambda$8T7TUmDrC16GiXzgsqw4HdeE4TI(ActionsDialogLite actionsDialogLite, AdapterView adapterView, View view, int i, long j) {
            return actionsDialogLite.lambda$createPowerOverflowPopup$4(adapterView, view, i, j);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda6.onItemClick(android.widget.AdapterView, android.view.View, int, long):void] */
        public static /* synthetic */ void $r8$lambda$F6sqswkWgKsNtNsbXIc1257sCa0(ActionsDialogLite actionsDialogLite, AdapterView adapterView, View view, int i, long j) {
            actionsDialogLite.lambda$createPowerOverflowPopup$3(adapterView, view, i, j);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda8.onBackInvoked():void] */
        public static /* synthetic */ void $r8$lambda$F9owy5PIMnX5kjmtdg6e0ztHS58(ActionsDialogLite actionsDialogLite) {
            actionsDialogLite.lambda$new$0();
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda5.run():void] */
        public static /* synthetic */ void $r8$lambda$MQBe3CzoObsiOR1e0ETw9qnsvVk(ActionsDialogLite actionsDialogLite) {
            actionsDialogLite.lambda$show$7();
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda1.onTouch(android.view.View, android.view.MotionEvent):boolean] */
        /* renamed from: $r8$lambda$litMA2ER4bOa5MXo-uMdzGS3R4Q */
        public static /* synthetic */ boolean m2767$r8$lambda$litMA2ER4bOa5MXouMdzGS3R4Q(ActionsDialogLite actionsDialogLite, View view, MotionEvent motionEvent) {
            return actionsDialogLite.lambda$initializeLayout$5(view, motionEvent);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda4.onAnimationUpdate(android.animation.ValueAnimator):void] */
        public static /* synthetic */ void $r8$lambda$mFU8RFvM0zTauZ5BjDbM4oykiI4(ActionsDialogLite actionsDialogLite, boolean z, Window window, float f, int i, ValueAnimator valueAnimator) {
            actionsDialogLite.lambda$startAnimation$9(z, window, f, i, valueAnimator);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda3.run():void] */
        /* renamed from: $r8$lambda$oPDzQvslp7-JlhETc2E1jKwWM9E */
        public static /* synthetic */ void m2768$r8$lambda$oPDzQvslp7JlhETc2E1jKwWM9E(ActionsDialogLite actionsDialogLite) {
            actionsDialogLite.lambda$show$8();
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda10.accept(java.lang.Object):void] */
        public static /* synthetic */ void $r8$lambda$y4EUetiLJ66fi1Rqhy_nRne8488(CentralSurfaces centralSurfaces) {
            centralSurfaces.animateExpandSettingsPanel(null);
        }

        public ActionsDialogLite(Context context, int i, MyAdapter myAdapter, MyOverflowAdapter myOverflowAdapter, SysuiColorExtractor sysuiColorExtractor, IStatusBarService iStatusBarService, NotificationShadeWindowController notificationShadeWindowController, Runnable runnable, boolean z, MyPowerOptionsAdapter myPowerOptionsAdapter, MyRestartOptionsAdapter myRestartOptionsAdapter, MyUsersAdapter myUsersAdapter, UiEventLogger uiEventLogger, Optional<CentralSurfaces> optional, KeyguardUpdateMonitor keyguardUpdateMonitor, LockPatternUtils lockPatternUtils) {
            super(context, i, false);
            this.mToken = new Binder();
            this.mOnBackInvokedCallback = new OnBackInvokedCallback() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda8
                public final void onBackInvoked() {
                    GlobalActionsDialogLite.ActionsDialogLite.$r8$lambda$F9owy5PIMnX5kjmtdg6e0ztHS58(GlobalActionsDialogLite.ActionsDialogLite.this);
                }
            };
            this.mGestureListener = new GestureDetector.SimpleOnGestureListener() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.ActionsDialogLite.1
                {
                    ActionsDialogLite.this = this;
                }

                @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
                public boolean onDown(MotionEvent motionEvent) {
                    return true;
                }

                @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
                public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                    if (f2 <= ActionBarShadowController.ELEVATION_LOW || Math.abs(f2) <= Math.abs(f) || motionEvent.getY() > ((Integer) ActionsDialogLite.this.mCentralSurfacesOptional.map(new GlobalActionsDialogLite$ActionsDialogLite$1$$ExternalSyntheticLambda0()).orElse(0)).intValue()) {
                        return false;
                    }
                    ActionsDialogLite.this.openShadeAndDismiss();
                    return true;
                }

                @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
                public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                    if (f2 >= ActionBarShadowController.ELEVATION_LOW || f2 <= f || motionEvent.getY() > ((Integer) ActionsDialogLite.this.mCentralSurfacesOptional.map(new GlobalActionsDialogLite$ActionsDialogLite$1$$ExternalSyntheticLambda0()).orElse(0)).intValue()) {
                        return false;
                    }
                    ActionsDialogLite.this.openShadeAndDismiss();
                    return true;
                }

                @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
                public boolean onSingleTapUp(MotionEvent motionEvent) {
                    ActionsDialogLite.this.mUiEventLogger.log(GlobalActionsEvent.GA_CLOSE_TAP_OUTSIDE);
                    ActionsDialogLite.this.cancel();
                    return false;
                }
            };
            this.mContext = context;
            this.mAdapter = myAdapter;
            this.mOverflowAdapter = myOverflowAdapter;
            this.mPowerOptionsAdapter = myPowerOptionsAdapter;
            this.mRestartOptionsAdapter = myRestartOptionsAdapter;
            this.mUsersAdapter = myUsersAdapter;
            this.mColorExtractor = sysuiColorExtractor;
            this.mStatusBarService = iStatusBarService;
            this.mNotificationShadeWindowController = notificationShadeWindowController;
            this.mOnRefreshCallback = runnable;
            this.mKeyguardShowing = z;
            this.mUiEventLogger = uiEventLogger;
            this.mCentralSurfacesOptional = optional;
            this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
            this.mLockPatternUtils = lockPatternUtils;
            this.mGestureDetector = new GestureDetector(context, this.mGestureListener);
        }

        public /* synthetic */ void lambda$createPowerOverflowPopup$3(AdapterView adapterView, View view, int i, long j) {
            this.mOverflowAdapter.onClickItem(i);
        }

        public /* synthetic */ boolean lambda$createPowerOverflowPopup$4(AdapterView adapterView, View view, int i, long j) {
            return this.mOverflowAdapter.onLongClickItem(i);
        }

        public /* synthetic */ boolean lambda$initializeLayout$5(View view, MotionEvent motionEvent) {
            this.mGestureDetector.onTouchEvent(motionEvent);
            return view.onTouchEvent(motionEvent);
        }

        public /* synthetic */ void lambda$initializeLayout$6(View view) {
            showPowerOverflowMenu();
        }

        public /* synthetic */ void lambda$new$0() {
            logOnBackInvocation();
            dismiss();
        }

        /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite */
        /* JADX WARN: Multi-variable type inference failed */
        public /* synthetic */ void lambda$show$7() {
            setDismissOverride(null);
            hide();
            dismiss();
        }

        public /* synthetic */ void lambda$show$8() {
            startAnimation(false, new Runnable() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    GlobalActionsDialogLite.ActionsDialogLite.$r8$lambda$MQBe3CzoObsiOR1e0ETw9qnsvVk(GlobalActionsDialogLite.ActionsDialogLite.this);
                }
            });
        }

        public /* synthetic */ void lambda$startAnimation$9(boolean z, Window window, float f, int i, ValueAnimator valueAnimator) {
            float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            float f2 = z ? floatValue : 1.0f - floatValue;
            this.mGlobalActionsLayout.setAlpha(f2);
            window.setDimAmount(this.mWindowDimAmount * f2);
            float f3 = z ? f * (1.0f - floatValue) : f * floatValue;
            if (i == 0) {
                this.mGlobalActionsLayout.setTranslationX(f3);
            } else if (i == 1) {
                this.mGlobalActionsLayout.setTranslationY(-f3);
            } else if (i == 2) {
                this.mGlobalActionsLayout.setTranslationX(-f3);
            } else if (i != 3) {
            } else {
                this.mGlobalActionsLayout.setTranslationY(f3);
            }
        }

        /* JADX DEBUG: Multi-variable search result rejected for r7v0, resolved type: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite */
        /* JADX WARN: Multi-variable type inference failed */
        public final ListPopupWindow createPowerOverflowPopup() {
            GlobalActionsPopupMenu globalActionsPopupMenu = new GlobalActionsPopupMenu(new ContextThemeWrapper(this.mContext, R$style.Control_ListPopupWindow), false);
            globalActionsPopupMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda6
                @Override // android.widget.AdapterView.OnItemClickListener
                public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
                    GlobalActionsDialogLite.ActionsDialogLite.$r8$lambda$F6sqswkWgKsNtNsbXIc1257sCa0(GlobalActionsDialogLite.ActionsDialogLite.this, adapterView, view, i, j);
                }
            });
            globalActionsPopupMenu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda7
                @Override // android.widget.AdapterView.OnItemLongClickListener
                public final boolean onItemLongClick(AdapterView adapterView, View view, int i, long j) {
                    return GlobalActionsDialogLite.ActionsDialogLite.$r8$lambda$8T7TUmDrC16GiXzgsqw4HdeE4TI(GlobalActionsDialogLite.ActionsDialogLite.this, adapterView, view, i, j);
                }
            });
            globalActionsPopupMenu.setAnchorView(findViewById(R$id.global_actions_overflow_button));
            globalActionsPopupMenu.setAdapter(this.mOverflowAdapter);
            return globalActionsPopupMenu;
        }

        /* JADX DEBUG: Multi-variable search result rejected for r4v0, resolved type: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite */
        /* JADX WARN: Multi-variable type inference failed */
        public void dismiss() {
            dismissOverflow();
            dismissPowerOptions();
            dismissRestartOptions();
            dismissUsers();
            this.mNotificationShadeWindowController.setRequestTopUi(false, "GlobalActionsDialogLite");
            super/*android.app.AlertDialog*/.dismiss();
        }

        public final void dismissOverflow() {
            ListPopupWindow listPopupWindow = this.mOverflowPopup;
            if (listPopupWindow != null) {
                listPopupWindow.dismiss();
            }
        }

        public final void dismissPowerOptions() {
            Dialog dialog = this.mPowerOptionsDialog;
            if (dialog != null) {
                dialog.dismiss();
            }
        }

        public final void dismissRestartOptions() {
            Dialog dialog = this.mRestartOptionsDialog;
            if (dialog != null) {
                dialog.dismiss();
            }
        }

        public final void dismissUsers() {
            Dialog dialog = this.mUsersDialog;
            if (dialog != null) {
                dialog.dismiss();
            }
        }

        /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite */
        /* JADX WARN: Multi-variable type inference failed */
        public void fixNavBarClipping() {
            ViewGroup viewGroup = (ViewGroup) findViewById(16908290);
            viewGroup.setClipChildren(false);
            viewGroup.setClipToPadding(false);
            ViewGroup viewGroup2 = (ViewGroup) viewGroup.getParent();
            viewGroup2.setClipChildren(false);
            viewGroup2.setClipToPadding(false);
        }

        public int getHeight() {
            return -1;
        }

        public int getLayoutResource() {
            return R$layout.global_actions_grid_lite;
        }

        /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite */
        /* JADX WARN: Multi-variable type inference failed */
        @VisibleForTesting
        public OnBackInvokedDispatcher getOnBackInvokedDispatcher() {
            OnBackInvokedDispatcher onBackInvokedDispatcher = this.mOverriddenBackDispatcher;
            return onBackInvokedDispatcher != null ? onBackInvokedDispatcher : super/*android.app.AlertDialog*/.getOnBackInvokedDispatcher();
        }

        public int getWidth() {
            return -1;
        }

        /* JADX DEBUG: Multi-variable search result rejected for r5v0, resolved type: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite */
        /* JADX WARN: Multi-variable type inference failed */
        public void initializeLayout() {
            setContentView(getLayoutResource());
            fixNavBarClipping();
            MultiListLayout multiListLayout = (MultiListLayout) findViewById(R$id.global_actions_view);
            this.mGlobalActionsLayout = multiListLayout;
            multiListLayout.setListViewAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.ActionsDialogLite.2
                {
                    ActionsDialogLite.this = this;
                }

                @Override // android.view.View.AccessibilityDelegate
                public boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
                    accessibilityEvent.getText().add(ActionsDialogLite.this.mContext.getString(17040431));
                    return true;
                }
            });
            this.mGlobalActionsLayout.setRotationListener(new MultiListLayout.RotationListener() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda0
                @Override // com.android.systemui.MultiListLayout.RotationListener
                public final void onRotate(int i, int i2) {
                    GlobalActionsDialogLite.ActionsDialogLite.this.onRotate(i, i2);
                }
            });
            this.mGlobalActionsLayout.setAdapter(this.mAdapter);
            ViewGroup viewGroup = (ViewGroup) findViewById(R$id.global_actions_container);
            this.mContainer = viewGroup;
            viewGroup.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda1
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    return GlobalActionsDialogLite.ActionsDialogLite.m2767$r8$lambda$litMA2ER4bOa5MXouMdzGS3R4Q(GlobalActionsDialogLite.ActionsDialogLite.this, view, motionEvent);
                }
            });
            View findViewById = findViewById(R$id.global_actions_overflow_button);
            if (findViewById != null) {
                if (this.mOverflowAdapter.getCount() > 0) {
                    findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda2
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            GlobalActionsDialogLite.ActionsDialogLite.m2766$r8$lambda$1tiLhu5niDlqASoxBepixordm8(GlobalActionsDialogLite.ActionsDialogLite.this, view);
                        }
                    });
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mGlobalActionsLayout.getLayoutParams();
                    layoutParams.setMarginEnd(0);
                    this.mGlobalActionsLayout.setLayoutParams(layoutParams);
                } else {
                    findViewById.setVisibility(8);
                    LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mGlobalActionsLayout.getLayoutParams();
                    layoutParams2.setMarginEnd(this.mContext.getResources().getDimensionPixelSize(R$dimen.global_actions_side_margin));
                    this.mGlobalActionsLayout.setLayoutParams(layoutParams2);
                }
            }
            if (this.mBackgroundDrawable == null) {
                this.mBackgroundDrawable = new ScrimDrawable();
                this.mScrimAlpha = 1.0f;
            }
            boolean userHasTrust = this.mKeyguardUpdateMonitor.getUserHasTrust(KeyguardUpdateMonitor.getCurrentUser());
            if (this.mKeyguardShowing && userHasTrust) {
                this.mLockPatternUtils.requireCredentialEntry(KeyguardUpdateMonitor.getCurrentUser());
                showSmartLockDisabledMessage();
            }
        }

        public final void logOnBackInvocation() {
            this.mUiEventLogger.log(GlobalActionsEvent.GA_CLOSE_BACK);
        }

        /* JADX DEBUG: Multi-variable search result rejected for r2v0, resolved type: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite */
        /* JADX WARN: Multi-variable type inference failed */
        public void onBackPressed() {
            super/*android.app.AlertDialog*/.onBackPressed();
            logOnBackInvocation();
        }

        public void onColorsChanged(ColorExtractor colorExtractor, int i) {
            if (this.mKeyguardShowing) {
                if ((i & 2) != 0) {
                    updateColors(colorExtractor.getColors(2), true);
                }
            } else if ((i & 1) != 0) {
                updateColors(colorExtractor.getColors(1), true);
            }
        }

        /* JADX DEBUG: Multi-variable search result rejected for r4v0, resolved type: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite */
        /* JADX WARN: Multi-variable type inference failed */
        public void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            initializeLayout();
            this.mWindowDimAmount = getWindow().getAttributes().dimAmount;
            getOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, this.mOnBackInvokedCallback);
        }

        public void onDetachedFromWindow() {
            getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(this.mOnBackInvokedCallback);
        }

        public void onRotate(int i, int i2) {
            refreshDialog();
        }

        public void onStart() {
            super.onStart();
            this.mGlobalActionsLayout.updateList();
            if (this.mBackgroundDrawable instanceof ScrimDrawable) {
                this.mColorExtractor.addOnColorsChangedListener(this);
                updateColors(this.mColorExtractor.getNeutralColors(), false);
            }
        }

        public void onStop() {
            super.onStop();
            this.mColorExtractor.removeOnColorsChangedListener(this);
        }

        /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite */
        /* JADX WARN: Multi-variable type inference failed */
        public boolean onTouchEvent(MotionEvent motionEvent) {
            return this.mGestureDetector.onTouchEvent(motionEvent) || super/*android.app.AlertDialog*/.onTouchEvent(motionEvent);
        }

        public final void openShadeAndDismiss() {
            this.mUiEventLogger.log(GlobalActionsEvent.GA_CLOSE_TAP_OUTSIDE);
            if (((Boolean) this.mCentralSurfacesOptional.map(new GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda9()).orElse(Boolean.FALSE)).booleanValue()) {
                this.mCentralSurfacesOptional.ifPresent(new Consumer() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda10
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        GlobalActionsDialogLite.ActionsDialogLite.$r8$lambda$y4EUetiLJ66fi1Rqhy_nRne8488((CentralSurfaces) obj);
                    }
                });
            } else {
                this.mCentralSurfacesOptional.ifPresent(new Consumer() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda11
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        GlobalActionsDialogLite.ActionsDialogLite.m2765$r8$lambda$XxguCL7wOS_Xbf9nmyOZRRlEow((CentralSurfaces) obj);
                    }
                });
            }
            dismiss();
        }

        public void refreshDialog() {
            this.mOnRefreshCallback.run();
            dismissOverflow();
            dismissPowerOptions();
            dismissRestartOptions();
            dismissUsers();
            this.mGlobalActionsLayout.updateList();
        }

        @VisibleForTesting
        public void setBackDispatcherOverride(OnBackInvokedDispatcher onBackInvokedDispatcher) {
            this.mOverriddenBackDispatcher = onBackInvokedDispatcher;
        }

        /* JADX DEBUG: Multi-variable search result rejected for r5v0, resolved type: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite */
        /* JADX WARN: Multi-variable type inference failed */
        public void show() {
            super/*android.app.AlertDialog*/.show();
            this.mNotificationShadeWindowController.setRequestTopUi(true, "GlobalActionsDialogLite");
            if (getWindow().getAttributes().windowAnimations == 0) {
                startAnimation(true, null);
                setDismissOverride(new Runnable() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        GlobalActionsDialogLite.ActionsDialogLite.m2768$r8$lambda$oPDzQvslp7JlhETc2E1jKwWM9E(GlobalActionsDialogLite.ActionsDialogLite.this);
                    }
                });
            }
        }

        public void showPowerOptionsMenu() {
            Dialog create = GlobalActionsPowerDialog.create(this.mContext, this.mPowerOptionsAdapter);
            this.mPowerOptionsDialog = create;
            create.show();
        }

        public void showPowerOverflowMenu() {
            ListPopupWindow createPowerOverflowPopup = createPowerOverflowPopup();
            this.mOverflowPopup = createPowerOverflowPopup;
            createPowerOverflowPopup.show();
        }

        public void showRestartOptionsMenu() {
            Dialog create = GlobalActionsPowerDialog.create(this.mContext, this.mRestartOptionsAdapter);
            this.mRestartOptionsDialog = create;
            create.show();
        }

        /* JADX DEBUG: Multi-variable search result rejected for r7v0, resolved type: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite */
        /* JADX WARN: Multi-variable type inference failed */
        public final void showSmartLockDisabledMessage() {
            final View inflate = LayoutInflater.from(this.mContext).inflate(R$layout.global_actions_toast, this.mContainer, false);
            final int recommendedTimeoutMillis = ((AccessibilityManager) getContext().getSystemService("accessibility")).getRecommendedTimeoutMillis(3500, 2);
            inflate.setVisibility(0);
            inflate.setAlpha(ActionBarShadowController.ELEVATION_LOW);
            this.mContainer.addView(inflate);
            inflate.animate().alpha(1.0f).setDuration(333L).setListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.ActionsDialogLite.3
                {
                    ActionsDialogLite.this = this;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    inflate.animate().alpha(ActionBarShadowController.ELEVATION_LOW).setDuration(333L).setStartDelay(recommendedTimeoutMillis).setListener(null);
                }
            });
        }

        public void showUsersMenu() {
            Dialog create = GlobalActionsPowerDialog.create(this.mContext, this.mUsersAdapter);
            this.mUsersDialog = create;
            create.show();
        }

        /* JADX DEBUG: Multi-variable search result rejected for r9v0, resolved type: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite */
        /* JADX WARN: Multi-variable type inference failed */
        public final void startAnimation(final boolean z, final Runnable runnable) {
            float dimension;
            ValueAnimator ofFloat = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
            Resources resources = getContext().getResources();
            if (z) {
                dimension = resources.getDimension(17105459);
                ofFloat.setInterpolator(Interpolators.STANDARD);
                ofFloat.setDuration(resources.getInteger(17694730));
            } else {
                dimension = resources.getDimension(17105460);
                ofFloat.setInterpolator(Interpolators.STANDARD_ACCELERATE);
                ofFloat.setDuration(resources.getInteger(17694731));
            }
            final Window window = getWindow();
            final float f = dimension;
            final int rotation = window.getWindowManager().getDefaultDisplay().getRotation();
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda4
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    GlobalActionsDialogLite.ActionsDialogLite.$r8$lambda$mFU8RFvM0zTauZ5BjDbM4oykiI4(GlobalActionsDialogLite.ActionsDialogLite.this, z, window, f, rotation, valueAnimator);
                }
            });
            ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.ActionsDialogLite.4
                public int mPreviousLayerType;

                {
                    ActionsDialogLite.this = this;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    ActionsDialogLite.this.mGlobalActionsLayout.setLayerType(this.mPreviousLayerType, null);
                    Runnable runnable2 = runnable;
                    if (runnable2 != null) {
                        runnable2.run();
                    }
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator, boolean z2) {
                    this.mPreviousLayerType = ActionsDialogLite.this.mGlobalActionsLayout.getLayerType();
                    ActionsDialogLite.this.mGlobalActionsLayout.setLayerType(2, null);
                }
            });
            ofFloat.start();
        }

        /* JADX DEBUG: Multi-variable search result rejected for r4v0, resolved type: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite */
        /* JADX WARN: Multi-variable type inference failed */
        public final void updateColors(ColorExtractor.GradientColors gradientColors, boolean z) {
            Drawable drawable = this.mBackgroundDrawable;
            if (drawable instanceof ScrimDrawable) {
                ((ScrimDrawable) drawable).setColor(-16777216, z);
                View decorView = getWindow().getDecorView();
                if (gradientColors.supportsDarkText()) {
                    decorView.setSystemUiVisibility(8208);
                } else {
                    decorView.setSystemUiVisibility(0);
                }
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsDialogLite$AirplaneModeAction.class */
    public class AirplaneModeAction extends ToggleAction {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AirplaneModeAction() {
            super(R$drawable.ic_lock_airplane_mode_enabled, R$drawable.ic_lock_airplane_mode_disabled, 17040434, 17040433, 17040432);
            GlobalActionsDialogLite.this = r9;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.ToggleAction
        public void changeStateFromPress(boolean z) {
            if (GlobalActionsDialogLite.this.mHasTelephony && !((Boolean) TelephonyProperties.in_ecm_mode().orElse(Boolean.FALSE)).booleanValue()) {
                ToggleState toggleState = z ? ToggleState.TurningOn : ToggleState.TurningOff;
                this.mState = toggleState;
                GlobalActionsDialogLite.this.mAirplaneState = toggleState;
            }
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.ToggleAction
        public void onToggle(boolean z) {
            if (!GlobalActionsDialogLite.this.mHasTelephony || !((Boolean) TelephonyProperties.in_ecm_mode().orElse(Boolean.FALSE)).booleanValue()) {
                GlobalActionsDialogLite.this.changeAirplaneModeSystemSetting(z);
                return;
            }
            GlobalActionsDialogLite.this.mIsWaitingForEcmExit = true;
            Intent intent = new Intent("android.telephony.action.SHOW_NOTICE_ECM_BLOCK_OTHERS", (Uri) null);
            intent.addFlags(268435456);
            GlobalActionsDialogLite.this.mContext.startActivity(intent);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean showBeforeProvisioning() {
            return false;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean showDuringKeyguard() {
            return true;
        }
    }

    @VisibleForTesting
    /* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsDialogLite$BugReportAction.class */
    public class BugReportAction extends SinglePressAction implements LongPressAction {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public BugReportAction() {
            super(17302502, 17039836);
            GlobalActionsDialogLite.this = r6;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.LongPressAction
        public boolean onLongPress() {
            if (ActivityManager.isUserAMonkey()) {
                return false;
            }
            try {
                GlobalActionsDialogLite.this.mMetricsLogger.action(293);
                GlobalActionsDialogLite.this.mUiEventLogger.log(GlobalActionsEvent.GA_BUGREPORT_LONG_PRESS);
                GlobalActionsDialogLite.this.mIActivityManager.requestFullBugReport();
                GlobalActionsDialogLite.this.mCentralSurfacesOptional.ifPresent(new GlobalActionsDialogLite$BugReportAction$$ExternalSyntheticLambda0());
                return false;
            } catch (RemoteException e) {
                return false;
            }
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public void onPress() {
            if (ActivityManager.isUserAMonkey()) {
                return;
            }
            GlobalActionsDialogLite.this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.BugReportAction.1
                {
                    BugReportAction.this = this;
                }

                @Override // java.lang.Runnable
                public void run() {
                    try {
                        GlobalActionsDialogLite.this.mMetricsLogger.action(292);
                        GlobalActionsDialogLite.this.mUiEventLogger.log(GlobalActionsEvent.GA_BUGREPORT_PRESS);
                        if (!GlobalActionsDialogLite.this.mIActivityManager.launchBugReportHandlerApp()) {
                            Log.w("GlobalActionsDialogLite", "Bugreport handler could not be launched");
                            GlobalActionsDialogLite.this.mIActivityManager.requestInteractiveBugReport();
                        }
                        GlobalActionsDialogLite.this.mCentralSurfacesOptional.ifPresent(new GlobalActionsDialogLite$BugReportAction$$ExternalSyntheticLambda0());
                    } catch (RemoteException e) {
                    }
                }
            }, GlobalActionsDialogLite.this.mDialogPressDelay);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean showBeforeProvisioning() {
            boolean z = false;
            if (Build.isDebuggable()) {
                z = false;
                if (GlobalActionsDialogLite.this.mGlobalSettings.getInt("bugreport_in_power_menu", 0) != 0) {
                    z = true;
                }
            }
            return z;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean showDuringKeyguard() {
            return true;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsDialogLite$CurrentUserProvider.class */
    public class CurrentUserProvider {
        public boolean mFetched;
        public UserInfo mUserInfo;

        public CurrentUserProvider() {
            GlobalActionsDialogLite.this = r4;
            this.mUserInfo = null;
            this.mFetched = false;
        }

        public UserInfo get() {
            if (!this.mFetched) {
                this.mFetched = true;
                this.mUserInfo = GlobalActionsDialogLite.this.getCurrentUser();
            }
            return this.mUserInfo;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsDialogLite$DeviceControlsAction.class */
    public final class DeviceControlsAction extends SinglePressAction {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public DeviceControlsAction() {
            super(R$drawable.controls_icon, R$string.quick_controls_title);
            GlobalActionsDialogLite.this = r6;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public void onPress() {
            GlobalActionsDialogLite.this.mContext.startActivity(new Intent(GlobalActionsDialogLite.this.mContext, ControlsActivity.class).addFlags(335544320).putExtra("extra_animate", true));
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean shouldShow() {
            return super.shouldShow() && GlobalActionsDialogLite.this.mControlsComponent.getControlsListingController().isPresent();
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean showBeforeProvisioning() {
            return false;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean showDuringKeyguard() {
            return true;
        }
    }

    @VisibleForTesting
    /* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsDialogLite$EmergencyAction.class */
    public abstract class EmergencyAction extends SinglePressAction {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public EmergencyAction(int i, int i2) {
            super(i, i2);
            GlobalActionsDialogLite.this = r6;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.SinglePressAction, com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public View create(Context context, View view, ViewGroup viewGroup, LayoutInflater layoutInflater) {
            View create = super.create(context, view, viewGroup, layoutInflater);
            int emergencyTextColor = GlobalActionsDialogLite.this.getEmergencyTextColor(context);
            int emergencyIconColor = GlobalActionsDialogLite.this.getEmergencyIconColor(context);
            int emergencyBackgroundColor = GlobalActionsDialogLite.this.getEmergencyBackgroundColor(context);
            TextView textView = (TextView) create.findViewById(16908299);
            textView.setTextColor(emergencyTextColor);
            textView.setSelected(true);
            ImageView imageView = (ImageView) create.findViewById(16908294);
            imageView.getDrawable().setTint(emergencyIconColor);
            imageView.setBackgroundTintList(ColorStateList.valueOf(emergencyBackgroundColor));
            create.setBackgroundTintList(ColorStateList.valueOf(emergencyBackgroundColor));
            return create;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean shouldBeSeparated() {
            return false;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean shouldShow() {
            return GlobalActionsDialogLite.this.mHasTelephony;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean showBeforeProvisioning() {
            return true;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean showDuringKeyguard() {
            return true;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsDialogLite$EmergencyAffordanceAction.class */
    public class EmergencyAffordanceAction extends EmergencyAction {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public EmergencyAffordanceAction() {
            super(17302212, 17040418);
            GlobalActionsDialogLite.this = r6;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public void onPress() {
            GlobalActionsDialogLite.this.mEmergencyAffordanceManager.performEmergencyCall();
        }
    }

    @VisibleForTesting
    /* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsDialogLite$EmergencyDialerAction.class */
    public class EmergencyDialerAction extends EmergencyAction {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public EmergencyDialerAction() {
            super(R$drawable.ic_emergency_star, 17040418);
            GlobalActionsDialogLite.this = r6;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public void onPress() {
            GlobalActionsDialogLite.this.mMetricsLogger.action(1569);
            GlobalActionsDialogLite.this.mUiEventLogger.log(GlobalActionsEvent.GA_EMERGENCY_DIALER_PRESS);
            if (GlobalActionsDialogLite.this.mTelecomManager != null) {
                GlobalActionsDialogLite.this.mCentralSurfacesOptional.ifPresent(new Consumer() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$EmergencyDialerAction$$ExternalSyntheticLambda0
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ((CentralSurfaces) obj).collapseShade();
                    }
                });
                Intent createLaunchEmergencyDialerIntent = GlobalActionsDialogLite.this.mTelecomManager.createLaunchEmergencyDialerIntent(null);
                createLaunchEmergencyDialerIntent.addFlags(343932928);
                createLaunchEmergencyDialerIntent.putExtra("com.android.phone.EmergencyDialer.extra.ENTRY_TYPE", 2);
                GlobalActionsDialogLite.this.mContext.startActivityAsUser(createLaunchEmergencyDialerIntent, UserHandle.CURRENT);
            }
        }
    }

    @VisibleForTesting
    /* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsDialogLite$GlobalActionsEvent.class */
    public enum GlobalActionsEvent implements UiEventLogger.UiEventEnum {
        GA_POWER_MENU_OPEN(337),
        GA_POWER_MENU_CLOSE(471),
        GA_BUGREPORT_PRESS(344),
        GA_BUGREPORT_LONG_PRESS(345),
        GA_EMERGENCY_DIALER_PRESS(346),
        GA_SCREENSHOT_PRESS(347),
        GA_SCREENSHOT_LONG_PRESS(348),
        GA_SHUTDOWN_PRESS(802),
        GA_SHUTDOWN_LONG_PRESS(803),
        GA_REBOOT_PRESS(349),
        GA_REBOOT_LONG_PRESS(804),
        GA_LOCKDOWN_PRESS(354),
        GA_OPEN_QS(805),
        GA_OPEN_POWER_VOLUP(806),
        GA_OPEN_LONG_PRESS_POWER(807),
        GA_CLOSE_LONG_PRESS_POWER(808),
        GA_CLOSE_BACK(809),
        GA_CLOSE_TAP_OUTSIDE(810),
        GA_CLOSE_POWER_VOLUP(811);
        
        private final int mId;

        GlobalActionsEvent(int i) {
            this.mId = i;
        }

        public int getId() {
            return this.mId;
        }
    }

    @VisibleForTesting
    /* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsDialogLite$LockDownAction.class */
    public class LockDownAction extends SinglePressAction {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.globalactions.GlobalActionsDialogLite$LockDownAction$$ExternalSyntheticLambda0.run():void] */
        public static /* synthetic */ void $r8$lambda$OGZvxFca16HTsOFZGUhbWh4pk44(LockDownAction lockDownAction) {
            lockDownAction.lambda$onPress$0();
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public LockDownAction() {
            super(17302505, 17040420);
            GlobalActionsDialogLite.this = r6;
        }

        public /* synthetic */ void lambda$onPress$0() {
            GlobalActionsDialogLite.this.lockProfiles();
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public void onPress() {
            GlobalActionsDialogLite.this.mLockPatternUtils.requireStrongAuth(32, -1);
            GlobalActionsDialogLite.this.mUiEventLogger.log(GlobalActionsEvent.GA_LOCKDOWN_PRESS);
            try {
                GlobalActionsDialogLite.this.mIWindowManager.lockNow((Bundle) null);
                GlobalActionsDialogLite.this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$LockDownAction$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        GlobalActionsDialogLite.LockDownAction.$r8$lambda$OGZvxFca16HTsOFZGUhbWh4pk44(GlobalActionsDialogLite.LockDownAction.this);
                    }
                });
            } catch (RemoteException e) {
                Log.e("GlobalActionsDialogLite", "Error while trying to lock device.", e);
            }
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean showBeforeProvisioning() {
            return false;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean showDuringKeyguard() {
            return true;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsDialogLite$LogoutAction.class */
    public final class LogoutAction extends SinglePressAction {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.globalactions.GlobalActionsDialogLite$LogoutAction$$ExternalSyntheticLambda0.run():void] */
        /* renamed from: $r8$lambda$iVmeCOrREnFZQWC-XiZjNF142OU */
        public static /* synthetic */ void m2773$r8$lambda$iVmeCOrREnFZQWCXiZjNF142OU(LogoutAction logoutAction) {
            logoutAction.lambda$onPress$0();
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public LogoutAction() {
            super(17302552, 17040421);
            GlobalActionsDialogLite.this = r6;
        }

        public /* synthetic */ void lambda$onPress$0() {
            GlobalActionsDialogLite.this.mDevicePolicyManager.logoutUser();
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public void onPress() {
            GlobalActionsDialogLite.this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$LogoutAction$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    GlobalActionsDialogLite.LogoutAction.m2773$r8$lambda$iVmeCOrREnFZQWCXiZjNF142OU(GlobalActionsDialogLite.LogoutAction.this);
                }
            }, GlobalActionsDialogLite.this.mDialogPressDelay);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean showBeforeProvisioning() {
            return false;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean showDuringKeyguard() {
            return true;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsDialogLite$LongPressAction.class */
    public interface LongPressAction extends Action {
        boolean onLongPress();
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsDialogLite$MyAdapter.class */
    public class MyAdapter extends MultiListLayout.MultiListAdapter {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.globalactions.GlobalActionsDialogLite$MyAdapter$$ExternalSyntheticLambda0.onClick(android.view.View):void] */
        public static /* synthetic */ void $r8$lambda$1M2oDGn1POL7NDViudwX49He1ok(MyAdapter myAdapter, int i, View view) {
            myAdapter.lambda$getView$0(i, view);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.globalactions.GlobalActionsDialogLite$MyAdapter$$ExternalSyntheticLambda1.onLongClick(android.view.View):boolean] */
        /* renamed from: $r8$lambda$vDxPpsm4CWjGhFbhTUSLnLfg-co */
        public static /* synthetic */ boolean m2774$r8$lambda$vDxPpsm4CWjGhFbhTUSLnLfgco(MyAdapter myAdapter, int i, View view) {
            return myAdapter.lambda$getView$1(i, view);
        }

        public MyAdapter() {
            GlobalActionsDialogLite.this = r4;
        }

        public /* synthetic */ void lambda$getView$0(int i, View view) {
            onClickItem(i);
        }

        public /* synthetic */ boolean lambda$getView$1(int i, View view) {
            return onLongClickItem(i);
        }

        @Override // android.widget.BaseAdapter, android.widget.ListAdapter
        public boolean areAllItemsEnabled() {
            return false;
        }

        public final int countItems(boolean z) {
            int i = 0;
            int i2 = 0;
            while (true) {
                int i3 = i2;
                if (i >= GlobalActionsDialogLite.this.mItems.size()) {
                    return i3;
                }
                int i4 = i3;
                if (GlobalActionsDialogLite.this.mItems.get(i).shouldBeSeparated() == z) {
                    i4 = i3 + 1;
                }
                i++;
                i2 = i4;
            }
        }

        @Override // com.android.systemui.MultiListLayout.MultiListAdapter
        public int countListItems() {
            return countItems(false);
        }

        @Override // com.android.systemui.MultiListLayout.MultiListAdapter
        public int countSeparatedItems() {
            return countItems(true);
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return countSeparatedItems() + countListItems();
        }

        @Override // android.widget.Adapter
        public Action getItem(int i) {
            int i2 = 0;
            for (int i3 = 0; i3 < GlobalActionsDialogLite.this.mItems.size(); i3++) {
                Action action = GlobalActionsDialogLite.this.mItems.get(i3);
                if (GlobalActionsDialogLite.this.shouldShowAction(action)) {
                    if (i2 == i) {
                        return action;
                    }
                    i2++;
                }
            }
            throw new IllegalArgumentException("position " + i + " out of range of showable actions, filtered count=" + getCount() + ", keyguardshowing=" + GlobalActionsDialogLite.this.mKeyguardShowing + ", provisioned=" + GlobalActionsDialogLite.this.mDeviceProvisioned);
        }

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        @Override // android.widget.Adapter
        public View getView(final int i, View view, ViewGroup viewGroup) {
            Action item = getItem(i);
            View create = item.create(GlobalActionsDialogLite.this.mContext, view, viewGroup, LayoutInflater.from(GlobalActionsDialogLite.this.mContext));
            create.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$MyAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    GlobalActionsDialogLite.MyAdapter.$r8$lambda$1M2oDGn1POL7NDViudwX49He1ok(GlobalActionsDialogLite.MyAdapter.this, i, view2);
                }
            });
            if (item instanceof LongPressAction) {
                create.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$MyAdapter$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnLongClickListener
                    public final boolean onLongClick(View view2) {
                        return GlobalActionsDialogLite.MyAdapter.m2774$r8$lambda$vDxPpsm4CWjGhFbhTUSLnLfgco(GlobalActionsDialogLite.MyAdapter.this, i, view2);
                    }
                });
            }
            return create;
        }

        @Override // android.widget.BaseAdapter, android.widget.ListAdapter
        public boolean isEnabled(int i) {
            return getItem(i).isEnabled();
        }

        public void onClickItem(int i) {
            Action item = GlobalActionsDialogLite.this.mAdapter.getItem(i);
            if (item instanceof SilentModeTriStateAction) {
                return;
            }
            GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
            if (globalActionsDialogLite.mDialog == null) {
                Log.w("GlobalActionsDialogLite", "Action clicked while mDialog is null.");
            } else if (!(item instanceof PowerOptionsAction) && ((!(item instanceof RestartAction) || !globalActionsDialogLite.shouldShowRestartSubmenu()) && !(item instanceof UsersAction))) {
                GlobalActionsDialogLite.this.mDialogLaunchAnimator.disableAllCurrentDialogsExitAnimations();
                GlobalActionsDialogLite.this.mDialog.dismiss();
            }
            item.onPress();
        }

        public boolean onLongClickItem(int i) {
            Action item = GlobalActionsDialogLite.this.mAdapter.getItem(i);
            if (item instanceof LongPressAction) {
                GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
                if (globalActionsDialogLite.mDialog != null) {
                    globalActionsDialogLite.mDialogLaunchAnimator.disableAllCurrentDialogsExitAnimations();
                    GlobalActionsDialogLite.this.mDialog.dismiss();
                } else {
                    Log.w("GlobalActionsDialogLite", "Action long-clicked while mDialog is null.");
                }
                return ((LongPressAction) item).onLongPress();
            }
            return false;
        }

        @Override // com.android.systemui.MultiListLayout.MultiListAdapter
        public boolean shouldBeSeparated(int i) {
            return getItem(i).shouldBeSeparated();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsDialogLite$MyOverflowAdapter.class */
    public class MyOverflowAdapter extends BaseAdapter {
        public MyOverflowAdapter() {
            GlobalActionsDialogLite.this = r4;
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return GlobalActionsDialogLite.this.mOverflowItems.size();
        }

        @Override // android.widget.Adapter
        public Action getItem(int i) {
            return GlobalActionsDialogLite.this.mOverflowItems.get(i);
        }

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            Action item = getItem(i);
            if (item == null) {
                Log.w("GlobalActionsDialogLite", "No overflow action found at position: " + i);
                return null;
            }
            int i2 = R$layout.controls_more_item;
            if (view == null) {
                view = LayoutInflater.from(GlobalActionsDialogLite.this.mContext).inflate(i2, viewGroup, false);
            }
            TextView textView = (TextView) view;
            if (item.getMessageResId() != 0) {
                textView.setText(item.getMessageResId());
            } else {
                textView.setText(item.getMessage());
            }
            return textView;
        }

        public void onClickItem(int i) {
            Action item = getItem(i);
            if (item instanceof SilentModeTriStateAction) {
                return;
            }
            GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
            if (globalActionsDialogLite.mDialog != null) {
                globalActionsDialogLite.mDialogLaunchAnimator.disableAllCurrentDialogsExitAnimations();
                GlobalActionsDialogLite.this.mDialog.dismiss();
            } else {
                Log.w("GlobalActionsDialogLite", "Action clicked while mDialog is null.");
            }
            item.onPress();
        }

        public boolean onLongClickItem(int i) {
            Action item = getItem(i);
            if (item instanceof LongPressAction) {
                GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
                if (globalActionsDialogLite.mDialog != null) {
                    globalActionsDialogLite.mDialogLaunchAnimator.disableAllCurrentDialogsExitAnimations();
                    GlobalActionsDialogLite.this.mDialog.dismiss();
                } else {
                    Log.w("GlobalActionsDialogLite", "Action long-clicked while mDialog is null.");
                }
                return ((LongPressAction) item).onLongPress();
            }
            return false;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsDialogLite$MyPowerOptionsAdapter.class */
    public class MyPowerOptionsAdapter extends BaseAdapter {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.globalactions.GlobalActionsDialogLite$MyPowerOptionsAdapter$$ExternalSyntheticLambda1.onLongClick(android.view.View):boolean] */
        public static /* synthetic */ boolean $r8$lambda$8XsTIRZc0FYy9aMS8OFa2AcVbdI(MyPowerOptionsAdapter myPowerOptionsAdapter, int i, View view) {
            return myPowerOptionsAdapter.lambda$getView$1(i, view);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.globalactions.GlobalActionsDialogLite$MyPowerOptionsAdapter$$ExternalSyntheticLambda0.onClick(android.view.View):void] */
        /* renamed from: $r8$lambda$hoYMzKnzG-yZTdmWCMiiJLzuG0Q */
        public static /* synthetic */ void m2775$r8$lambda$hoYMzKnzGyZTdmWCMiiJLzuG0Q(MyPowerOptionsAdapter myPowerOptionsAdapter, int i, View view) {
            myPowerOptionsAdapter.lambda$getView$0(i, view);
        }

        public MyPowerOptionsAdapter() {
            GlobalActionsDialogLite.this = r4;
        }

        public /* synthetic */ void lambda$getView$0(int i, View view) {
            onClickItem(i);
        }

        public /* synthetic */ boolean lambda$getView$1(int i, View view) {
            return onLongClickItem(i);
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return GlobalActionsDialogLite.this.mPowerItems.size();
        }

        @Override // android.widget.Adapter
        public Action getItem(int i) {
            return GlobalActionsDialogLite.this.mPowerItems.get(i);
        }

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        @Override // android.widget.Adapter
        public View getView(final int i, View view, ViewGroup viewGroup) {
            Action item = getItem(i);
            if (item == null) {
                Log.w("GlobalActionsDialogLite", "No power options action found at position: " + i);
                return null;
            }
            int i2 = R$layout.global_actions_grid_item_lite;
            if (view == null) {
                view = LayoutInflater.from(GlobalActionsDialogLite.this.mContext).inflate(i2, viewGroup, false);
            }
            view.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$MyPowerOptionsAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    GlobalActionsDialogLite.MyPowerOptionsAdapter.m2775$r8$lambda$hoYMzKnzGyZTdmWCMiiJLzuG0Q(GlobalActionsDialogLite.MyPowerOptionsAdapter.this, i, view2);
                }
            });
            if (item instanceof LongPressAction) {
                view.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$MyPowerOptionsAdapter$$ExternalSyntheticLambda1
                    @Override // android.view.View.OnLongClickListener
                    public final boolean onLongClick(View view2) {
                        return GlobalActionsDialogLite.MyPowerOptionsAdapter.$r8$lambda$8XsTIRZc0FYy9aMS8OFa2AcVbdI(GlobalActionsDialogLite.MyPowerOptionsAdapter.this, i, view2);
                    }
                });
            }
            ImageView imageView = (ImageView) view.findViewById(16908294);
            TextView textView = (TextView) view.findViewById(16908299);
            textView.setSelected(true);
            imageView.setImageDrawable(item.getIcon(GlobalActionsDialogLite.this.mContext));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (item.getMessage() != null) {
                textView.setText(item.getMessage());
            } else {
                textView.setText(item.getMessageResId());
            }
            return view;
        }

        public final void onClickItem(int i) {
            Action item = getItem(i);
            if (item instanceof SilentModeTriStateAction) {
                return;
            }
            GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
            if (globalActionsDialogLite.mDialog == null || ((item instanceof RestartAction) && globalActionsDialogLite.shouldShowRestartSubmenu())) {
                Log.w("GlobalActionsDialogLite", "Action clicked while mDialog is null.");
            } else {
                GlobalActionsDialogLite.this.mDialogLaunchAnimator.disableAllCurrentDialogsExitAnimations();
                GlobalActionsDialogLite.this.mDialog.dismiss();
            }
            item.onPress();
        }

        public final boolean onLongClickItem(int i) {
            Action item = getItem(i);
            if (item instanceof LongPressAction) {
                GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
                if (globalActionsDialogLite.mDialog != null) {
                    globalActionsDialogLite.mDialogLaunchAnimator.disableAllCurrentDialogsExitAnimations();
                    GlobalActionsDialogLite.this.mDialog.dismiss();
                } else {
                    Log.w("GlobalActionsDialogLite", "Action long-clicked while mDialog is null.");
                }
                return ((LongPressAction) item).onLongPress();
            }
            return false;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsDialogLite$MyRestartOptionsAdapter.class */
    public class MyRestartOptionsAdapter extends MyPowerOptionsAdapter {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MyRestartOptionsAdapter() {
            super();
            GlobalActionsDialogLite.this = r4;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.MyPowerOptionsAdapter, android.widget.Adapter
        public int getCount() {
            return GlobalActionsDialogLite.this.mRestartItems.size();
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.MyPowerOptionsAdapter, android.widget.Adapter
        public Action getItem(int i) {
            return GlobalActionsDialogLite.this.mRestartItems.get(i);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsDialogLite$MyUsersAdapter.class */
    public class MyUsersAdapter extends BaseAdapter {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.globalactions.GlobalActionsDialogLite$MyUsersAdapter$$ExternalSyntheticLambda0.onClick(android.view.View):void] */
        public static /* synthetic */ void $r8$lambda$qvZmeZ6cURjicMv9DC6nb9BaH7A(MyUsersAdapter myUsersAdapter, int i, View view) {
            myUsersAdapter.lambda$getView$0(i, view);
        }

        public MyUsersAdapter() {
            GlobalActionsDialogLite.this = r4;
        }

        public /* synthetic */ void lambda$getView$0(int i, View view) {
            onClickItem(i);
        }

        @Override // android.widget.Adapter
        public int getCount() {
            return GlobalActionsDialogLite.this.mUsersItems.size();
        }

        @Override // android.widget.Adapter
        public Action getItem(int i) {
            return GlobalActionsDialogLite.this.mUsersItems.get(i);
        }

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        @Override // android.widget.Adapter
        public View getView(final int i, View view, ViewGroup viewGroup) {
            Action item = getItem(i);
            if (item == null) {
                Log.w("GlobalActionsDialogLite", "No users action found at position: " + i);
                return null;
            }
            int i2 = R$layout.global_actions_power_item;
            if (view == null) {
                view = LayoutInflater.from(GlobalActionsDialogLite.this.mContext).inflate(i2, viewGroup, false);
            }
            view.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$MyUsersAdapter$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view2) {
                    GlobalActionsDialogLite.MyUsersAdapter.$r8$lambda$qvZmeZ6cURjicMv9DC6nb9BaH7A(GlobalActionsDialogLite.MyUsersAdapter.this, i, view2);
                }
            });
            ImageView imageView = (ImageView) view.findViewById(16908294);
            TextView textView = (TextView) view.findViewById(16908299);
            textView.setSelected(true);
            imageView.setImageDrawable(item.getIcon(GlobalActionsDialogLite.this.mContext));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (item.getMessage() != null) {
                textView.setText(item.getMessage());
            } else {
                textView.setText(item.getMessageResId());
            }
            return view;
        }

        public final void onClickItem(int i) {
            Action item = getItem(i);
            if (item instanceof SilentModeTriStateAction) {
                return;
            }
            ActionsDialogLite actionsDialogLite = GlobalActionsDialogLite.this.mDialog;
            if (actionsDialogLite == null || (item instanceof UsersAction)) {
                Log.w("GlobalActionsDialogLite", "Action clicked while mDialog is null.");
            } else {
                actionsDialogLite.dismiss();
            }
            item.onPress();
        }
    }

    @VisibleForTesting
    /* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsDialogLite$PowerOptionsAction.class */
    public final class PowerOptionsAction extends SinglePressAction {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public PowerOptionsAction() {
            super(R$drawable.ic_settings_power, 17040423);
            GlobalActionsDialogLite.this = r6;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public void onPress() {
            ActionsDialogLite actionsDialogLite = GlobalActionsDialogLite.this.mDialog;
            if (actionsDialogLite != null) {
                actionsDialogLite.showPowerOptionsMenu();
            }
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean showBeforeProvisioning() {
            return true;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean showDuringKeyguard() {
            return true;
        }
    }

    @VisibleForTesting
    /* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsDialogLite$RestartAction.class */
    public final class RestartAction extends SinglePressAction implements LongPressAction {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public RestartAction() {
            super(17302842, 17040424);
            GlobalActionsDialogLite.this = r6;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.LongPressAction
        public boolean onLongPress() {
            GlobalActionsDialogLite.this.mUiEventLogger.log(GlobalActionsEvent.GA_REBOOT_LONG_PRESS);
            if (GlobalActionsDialogLite.this.mUserManager.hasUserRestriction("no_safe_boot")) {
                return false;
            }
            GlobalActionsDialogLite.this.rebootAction(true);
            return true;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public void onPress() {
            GlobalActionsDialogLite.this.mUiEventLogger.log(GlobalActionsEvent.GA_REBOOT_PRESS);
            GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
            if (globalActionsDialogLite.mDialog == null || !globalActionsDialogLite.shouldShowRestartSubmenu()) {
                GlobalActionsDialogLite.this.rebootAction(false);
            } else {
                GlobalActionsDialogLite.this.mDialog.showRestartOptionsMenu();
            }
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean showBeforeProvisioning() {
            return true;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean showDuringKeyguard() {
            return true;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsDialogLite$RestartBootloaderAction.class */
    public final class RestartBootloaderAction extends SinglePressAction {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public RestartBootloaderAction() {
            super(R$drawable.ic_lock_restart_bootloader, R$string.global_action_restart_bootloader);
            GlobalActionsDialogLite.this = r6;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public void onPress() {
            GlobalActionsDialogLite.this.rebootAction(false, "bootloader");
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean showBeforeProvisioning() {
            return true;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean showDuringKeyguard() {
            return true;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsDialogLite$RestartDownloadAction.class */
    public final class RestartDownloadAction extends SinglePressAction {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public RestartDownloadAction() {
            super(R$drawable.ic_lock_restart_bootloader, R$string.global_action_restart_download);
            GlobalActionsDialogLite.this = r6;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public void onPress() {
            GlobalActionsDialogLite.this.rebootAction(false, "download");
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean showBeforeProvisioning() {
            return true;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean showDuringKeyguard() {
            return true;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsDialogLite$RestartFastbootAction.class */
    public final class RestartFastbootAction extends SinglePressAction {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public RestartFastbootAction() {
            super(R$drawable.ic_lock_restart_fastboot, R$string.global_action_restart_fastboot);
            GlobalActionsDialogLite.this = r6;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public void onPress() {
            GlobalActionsDialogLite.this.rebootAction(false, "fastboot");
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean showBeforeProvisioning() {
            return true;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean showDuringKeyguard() {
            return true;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsDialogLite$RestartRecoveryAction.class */
    public final class RestartRecoveryAction extends SinglePressAction {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public RestartRecoveryAction() {
            super(R$drawable.ic_lock_restart_recovery, R$string.global_action_restart_recovery);
            GlobalActionsDialogLite.this = r6;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public void onPress() {
            GlobalActionsDialogLite.this.rebootAction(false, "recovery");
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean showBeforeProvisioning() {
            return true;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean showDuringKeyguard() {
            return true;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsDialogLite$RestartSystemAction.class */
    public final class RestartSystemAction extends SinglePressAction implements LongPressAction {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public RestartSystemAction() {
            super(17302842, R$string.global_action_restart_system);
            GlobalActionsDialogLite.this = r6;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.LongPressAction
        public boolean onLongPress() {
            if (GlobalActionsDialogLite.this.mUserManager.hasUserRestriction("no_safe_boot")) {
                return false;
            }
            GlobalActionsDialogLite.this.rebootAction(true);
            return true;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public void onPress() {
            GlobalActionsDialogLite.this.rebootAction(false);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean showBeforeProvisioning() {
            return true;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean showDuringKeyguard() {
            return true;
        }
    }

    @VisibleForTesting
    /* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsDialogLite$ScreenshotAction.class */
    public class ScreenshotAction extends SinglePressAction implements LongPressAction {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ScreenshotAction() {
            super(17302844, 17040425);
            GlobalActionsDialogLite.this = r6;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.LongPressAction
        public boolean onLongPress() {
            takeScreenshot(2);
            return true;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public void onPress() {
            takeScreenshot(1);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean showBeforeProvisioning() {
            return false;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean showDuringKeyguard() {
            return true;
        }

        public final void takeScreenshot(final int i) {
            GlobalActionsDialogLite.this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.ScreenshotAction.1
                {
                    ScreenshotAction.this = this;
                }

                @Override // java.lang.Runnable
                public void run() {
                    GlobalActionsDialogLite.this.mScreenshotHelper.takeScreenshot(i, 0, GlobalActionsDialogLite.this.mHandler, (Consumer) null);
                    GlobalActionsDialogLite.this.mMetricsLogger.action(1282);
                    GlobalActionsDialogLite.this.mUiEventLogger.log(GlobalActionsEvent.GA_SCREENSHOT_PRESS);
                }
            }, GlobalActionsDialogLite.this.mDialogPressDelay);
        }
    }

    @VisibleForTesting
    /* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsDialogLite$ShutDownAction.class */
    public final class ShutDownAction extends SinglePressAction implements LongPressAction {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.globalactions.GlobalActionsDialogLite$ShutDownAction$$ExternalSyntheticLambda0.run():void] */
        public static /* synthetic */ void $r8$lambda$r6k6vTDpm75zu8wDhEtFndCrX7k(ShutDownAction shutDownAction) {
            shutDownAction.lambda$onPress$0();
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ShutDownAction() {
            super(17301552, 17040422);
            GlobalActionsDialogLite.this = r6;
        }

        public /* synthetic */ void lambda$onPress$0() {
            GlobalActionsDialogLite.this.mWindowManagerFuncs.shutdown();
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.LongPressAction
        public boolean onLongPress() {
            GlobalActionsDialogLite.this.mUiEventLogger.log(GlobalActionsEvent.GA_SHUTDOWN_LONG_PRESS);
            if (GlobalActionsDialogLite.this.mUserManager.hasUserRestriction("no_safe_boot")) {
                return false;
            }
            GlobalActionsDialogLite.this.rebootAction(true);
            return true;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public void onPress() {
            GlobalActionsDialogLite.this.mUiEventLogger.log(GlobalActionsEvent.GA_SHUTDOWN_PRESS);
            if (GlobalActionsDialogLite.this.mKeyguardStateController.isMethodSecure() && GlobalActionsDialogLite.this.mKeyguardStateController.isShowing()) {
                GlobalActionsDialogLite.this.mActivityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$ShutDownAction$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        GlobalActionsDialogLite.ShutDownAction.$r8$lambda$r6k6vTDpm75zu8wDhEtFndCrX7k(GlobalActionsDialogLite.ShutDownAction.this);
                    }
                });
            } else {
                GlobalActionsDialogLite.this.mWindowManagerFuncs.shutdown();
            }
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean showBeforeProvisioning() {
            return true;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean showDuringKeyguard() {
            return true;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsDialogLite$SilentModeToggleAction.class */
    public class SilentModeToggleAction extends ToggleAction {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SilentModeToggleAction() {
            super(17302329, 17302328, 17040429, 17040428, 17040427);
            GlobalActionsDialogLite.this = r9;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.ToggleAction
        public void onToggle(boolean z) {
            if (z) {
                GlobalActionsDialogLite.this.mAudioManager.setRingerMode(0);
            } else {
                GlobalActionsDialogLite.this.mAudioManager.setRingerMode(2);
            }
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean showBeforeProvisioning() {
            return false;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean showDuringKeyguard() {
            return true;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsDialogLite$SilentModeTriStateAction.class */
    public static class SilentModeTriStateAction implements Action, View.OnClickListener {
        public static final int[] ITEM_IDS = {16909315, 16909316, 16909317};
        public final AudioManager mAudioManager;
        public final Handler mHandler;

        public SilentModeTriStateAction(AudioManager audioManager, Handler handler) {
            this.mAudioManager = audioManager;
            this.mHandler = handler;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public View create(Context context, View view, ViewGroup viewGroup, LayoutInflater layoutInflater) {
            View inflate = layoutInflater.inflate(17367173, viewGroup, false);
            int ringerModeToIndex = ringerModeToIndex(this.mAudioManager.getRingerMode());
            int i = 0;
            while (i < 3) {
                View findViewById = inflate.findViewById(ITEM_IDS[i]);
                findViewById.setSelected(ringerModeToIndex == i);
                findViewById.setTag(Integer.valueOf(i));
                findViewById.setOnClickListener(this);
                i++;
            }
            return inflate;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public Drawable getIcon(Context context) {
            return null;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public CharSequence getMessage() {
            return null;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public int getMessageResId() {
            return 0;
        }

        public final int indexToRingerMode(int i) {
            return i;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean isEnabled() {
            return true;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (view.getTag() instanceof Integer) {
                this.mAudioManager.setRingerMode(indexToRingerMode(((Integer) view.getTag()).intValue()));
                this.mHandler.sendEmptyMessageDelayed(0, 300L);
            }
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public void onPress() {
        }

        public final int ringerModeToIndex(int i) {
            return i;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean showBeforeProvisioning() {
            return false;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean showDuringKeyguard() {
            return true;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsDialogLite$SinglePressAction.class */
    public abstract class SinglePressAction implements Action {
        public final Drawable mIcon;
        public final int mIconResId;
        public final CharSequence mMessage;
        public final int mMessageResId;
        public CharSequence mStatusMessage;

        public SinglePressAction(int i, int i2) {
            GlobalActionsDialogLite.this = r4;
            this.mIconResId = i;
            this.mMessageResId = i2;
            this.mMessage = null;
            this.mIcon = null;
        }

        public SinglePressAction(int i, Drawable drawable, CharSequence charSequence) {
            GlobalActionsDialogLite.this = r4;
            this.mIconResId = i;
            this.mMessageResId = 0;
            this.mMessage = charSequence;
            this.mIcon = drawable;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public View create(Context context, View view, ViewGroup viewGroup, LayoutInflater layoutInflater) {
            View inflate = layoutInflater.inflate(GlobalActionsDialogLite.this.getGridItemLayoutResource(), viewGroup, false);
            inflate.setId(View.generateViewId());
            ImageView imageView = (ImageView) inflate.findViewById(16908294);
            TextView textView = (TextView) inflate.findViewById(16908299);
            textView.setSelected(true);
            imageView.setImageDrawable(getIcon(context));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            CharSequence charSequence = this.mMessage;
            if (charSequence != null) {
                textView.setText(charSequence);
            } else {
                textView.setText(this.mMessageResId);
            }
            return inflate;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public Drawable getIcon(Context context) {
            Drawable drawable = this.mIcon;
            return drawable != null ? drawable : context.getDrawable(this.mIconResId);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public CharSequence getMessage() {
            return this.mMessage;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public int getMessageResId() {
            return this.mMessageResId;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean isEnabled() {
            return true;
        }

        public void setStatus(CharSequence charSequence) {
            this.mStatusMessage = charSequence;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsDialogLite$ToggleAction.class */
    public abstract class ToggleAction implements Action {
        public int mDisabledIconResid;
        public int mDisabledStatusMessageResId;
        public int mEnabledIconResId;
        public int mEnabledStatusMessageResId;
        public int mMessageResId;
        public ToggleState mState = ToggleState.Off;

        public ToggleAction(int i, int i2, int i3, int i4, int i5) {
            GlobalActionsDialogLite.this = r4;
            this.mEnabledIconResId = i;
            this.mDisabledIconResid = i2;
            this.mMessageResId = i3;
            this.mEnabledStatusMessageResId = i4;
            this.mDisabledStatusMessageResId = i5;
        }

        public void changeStateFromPress(boolean z) {
            this.mState = z ? ToggleState.On : ToggleState.Off;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public View create(Context context, View view, ViewGroup viewGroup, LayoutInflater layoutInflater) {
            willCreate();
            View inflate = layoutInflater.inflate(GlobalActionsDialogLite.this.getGridItemLayoutResource(), viewGroup, false);
            inflate.setId(View.generateViewId());
            ImageView imageView = (ImageView) inflate.findViewById(16908294);
            TextView textView = (TextView) inflate.findViewById(16908299);
            boolean isEnabled = isEnabled();
            if (textView != null) {
                textView.setText(getMessageResId());
                textView.setEnabled(isEnabled);
                textView.setSelected(true);
            }
            if (imageView != null) {
                imageView.setImageDrawable(context.getDrawable(getIconResId()));
                imageView.setEnabled(isEnabled);
            }
            inflate.setEnabled(isEnabled);
            return inflate;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public Drawable getIcon(Context context) {
            return context.getDrawable(getIconResId());
        }

        public final int getIconResId() {
            return isOn() ? this.mEnabledIconResId : this.mDisabledIconResid;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public CharSequence getMessage() {
            return null;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public int getMessageResId() {
            return isOn() ? this.mEnabledStatusMessageResId : this.mDisabledStatusMessageResId;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean isEnabled() {
            return !this.mState.inTransition();
        }

        public final boolean isOn() {
            ToggleState toggleState = this.mState;
            return toggleState == ToggleState.On || toggleState == ToggleState.TurningOn;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final void onPress() {
            if (this.mState.inTransition()) {
                Log.w("GlobalActionsDialogLite", "shouldn't be able to toggle when in transition");
                return;
            }
            boolean z = this.mState != ToggleState.On;
            onToggle(z);
            changeStateFromPress(z);
        }

        public abstract void onToggle(boolean z);

        public void updateState(ToggleState toggleState) {
            this.mState = toggleState;
        }

        public void willCreate() {
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsDialogLite$ToggleState.class */
    public enum ToggleState {
        Off(false),
        TurningOn(true),
        TurningOff(true),
        On(false);
        
        private final boolean mInTransition;

        ToggleState(boolean z) {
            this.mInTransition = z;
        }

        public boolean inTransition() {
            return this.mInTransition;
        }
    }

    @VisibleForTesting
    /* loaded from: mainsysui33.jar:com/android/systemui/globalactions/GlobalActionsDialogLite$UsersAction.class */
    public final class UsersAction extends SinglePressAction {
        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public UsersAction() {
            super(R$drawable.ic_lock_user, R$string.global_action_users);
            GlobalActionsDialogLite.this = r6;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public void onPress() {
            ActionsDialogLite actionsDialogLite = GlobalActionsDialogLite.this.mDialog;
            if (actionsDialogLite != null) {
                actionsDialogLite.showUsersMenu();
            }
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean showBeforeProvisioning() {
            return false;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public boolean showDuringKeyguard() {
            return true;
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.globalactions.GlobalActionsDialogLite$$ExternalSyntheticLambda1.onChanged(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$93LbT6sYYOZ8gpPM7Wpocw09xG8(GlobalActionsDialogLite globalActionsDialogLite, Integer num) {
        globalActionsDialogLite.lambda$new$0(num);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.globalactions.GlobalActionsDialogLite$$ExternalSyntheticLambda2.run():void] */
    /* renamed from: $r8$lambda$oPQrpJM-2SKyhFBKjROVZ4cYG5U */
    public static /* synthetic */ void m2730$r8$lambda$oPQrpJM2SKyhFBKjROVZ4cYG5U(GlobalActionsDialogLite globalActionsDialogLite, boolean z, String str) {
        globalActionsDialogLite.lambda$rebootAction$1(z, str);
    }

    public GlobalActionsDialogLite(Context context, GlobalActions.GlobalActionsManager globalActionsManager, AudioManager audioManager, IDreamManager iDreamManager, DevicePolicyManager devicePolicyManager, LockPatternUtils lockPatternUtils, BroadcastDispatcher broadcastDispatcher, GlobalSettings globalSettings, SecureSettings secureSettings, VibratorHelper vibratorHelper, Resources resources, ConfigurationController configurationController, UserTracker userTracker, KeyguardStateController keyguardStateController, UserManager userManager, TrustManager trustManager, IActivityManager iActivityManager, TelecomManager telecomManager, MetricsLogger metricsLogger, SysuiColorExtractor sysuiColorExtractor, IStatusBarService iStatusBarService, NotificationShadeWindowController notificationShadeWindowController, IWindowManager iWindowManager, Executor executor, UiEventLogger uiEventLogger, RingerModeTracker ringerModeTracker, Handler handler, PackageManager packageManager, Optional<CentralSurfaces> optional, KeyguardUpdateMonitor keyguardUpdateMonitor, DialogLaunchAnimator dialogLaunchAnimator, ControlsComponent controlsComponent, ActivityStarter activityStarter) {
        ContentObserver contentObserver = new ContentObserver(this.mMainHandler) { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.6
            {
                GlobalActionsDialogLite.this = this;
            }

            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                GlobalActionsDialogLite.this.onAirplaneModeChanged();
            }
        };
        this.mAirplaneModeObserver = contentObserver;
        this.mHandler = new Handler() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.7
            {
                GlobalActionsDialogLite.this = this;
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i = message.what;
                if (i != 0) {
                    if (i != 1) {
                        return;
                    }
                    GlobalActionsDialogLite.this.refreshSilentMode();
                    GlobalActionsDialogLite.this.mAdapter.notifyDataSetChanged();
                } else if (GlobalActionsDialogLite.this.mDialog != null) {
                    if (BcSmartspaceDataPlugin.UI_SURFACE_DREAM.equals(message.obj)) {
                        GlobalActionsDialogLite.this.mDialog.hide();
                        GlobalActionsDialogLite.this.mDialog.dismiss();
                    } else {
                        GlobalActionsDialogLite.this.mDialog.dismiss();
                    }
                    GlobalActionsDialogLite.this.mDialog = null;
                }
            }
        };
        this.mContext = context;
        this.mWindowManagerFuncs = globalActionsManager;
        this.mAudioManager = audioManager;
        this.mDreamManager = iDreamManager;
        this.mDevicePolicyManager = devicePolicyManager;
        this.mLockPatternUtils = lockPatternUtils;
        this.mKeyguardStateController = keyguardStateController;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mGlobalSettings = globalSettings;
        this.mSecureSettings = secureSettings;
        this.mResources = resources;
        this.mConfigurationController = configurationController;
        this.mUserTracker = userTracker;
        this.mUserManager = userManager;
        this.mTrustManager = trustManager;
        this.mIActivityManager = iActivityManager;
        this.mTelecomManager = telecomManager;
        this.mMetricsLogger = metricsLogger;
        this.mUiEventLogger = uiEventLogger;
        this.mControlsComponent = controlsComponent;
        CustomGlobalActions customGlobalActions = CustomGlobalActions.getInstance(context);
        this.mCustomGlobalActions = customGlobalActions;
        this.mSysuiColorExtractor = sysuiColorExtractor;
        this.mStatusBarService = iStatusBarService;
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        this.mIWindowManager = iWindowManager;
        this.mBackgroundExecutor = executor;
        this.mRingerModeTracker = ringerModeTracker;
        this.mMainHandler = handler;
        this.mSmallestScreenWidthDp = resources.getConfiguration().smallestScreenWidthDp;
        this.mCentralSurfacesOptional = optional;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mDialogLaunchAnimator = dialogLaunchAnimator;
        this.mActivityStarter = activityStarter;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.EMERGENCY_CALLBACK_MODE_CHANGED");
        broadcastDispatcher.registerReceiver(this.mBroadcastReceiver, intentFilter);
        this.mHasTelephony = packageManager.hasSystemFeature("android.hardware.telephony");
        globalSettings.registerContentObserver(Settings.Global.getUriFor("airplane_mode_on"), true, contentObserver);
        this.mHasVibrator = vibratorHelper.hasVibrator();
        boolean z = !resources.getBoolean(17891849);
        this.mShowSilentToggle = z;
        if (z) {
            ringerModeTracker.getRingerMode().observe(this, new Observer() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$$ExternalSyntheticLambda1
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    GlobalActionsDialogLite.$r8$lambda$93LbT6sYYOZ8gpPM7Wpocw09xG8(GlobalActionsDialogLite.this, (Integer) obj);
                }
            });
        }
        this.mEmergencyAffordanceManager = new EmergencyAffordanceManager(context);
        this.mScreenshotHelper = new ScreenshotHelper(context);
        configurationController.addCallback(this);
        ((TunerService) Dependency.get(TunerService.class)).addTunable(this, new String[]{"customsecure:power_menu_actions"});
        this.mActions = customGlobalActions.getUserActionsArray();
    }

    public static Bitmap createCircularClip(Bitmap bitmap, int i, int i2) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        Shader.TileMode tileMode = Shader.TileMode.CLAMP;
        paint.setShader(new BitmapShader(bitmap, tileMode, tileMode));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, width, height);
        RectF rectF2 = new RectF(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, i, i2);
        Matrix matrix = new Matrix();
        matrix.setRectToRect(rectF, rectF2, Matrix.ScaleToFit.CENTER);
        canvas.setMatrix(matrix);
        float f = width / 2;
        canvas.drawCircle(f, height / 2, f, paint);
        return createBitmap;
    }

    public /* synthetic */ void lambda$new$0(Integer num) {
        this.mHandler.sendEmptyMessage(1);
    }

    public /* synthetic */ void lambda$rebootAction$1(boolean z, String str) {
        this.mWindowManagerFuncs.reboot(z, str);
    }

    public final void addActionItem(Action action) {
        if (this.mItems.size() < getMaxShownPowerItems()) {
            this.mItems.add(action);
        } else {
            this.mOverflowItems.add(action);
        }
    }

    public final void addIfShouldShowAction(List<Action> list, Action action) {
        if (shouldShowAction(action)) {
            list.add(action);
        }
    }

    public final void addUserActions(List<Action> list, UserInfo userInfo) {
        if (this.mUserManager.isUserSwitcherEnabled()) {
            List<UserInfo> users = this.mUserManager.getUsers();
            int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R$dimen.global_actions_avatar_size);
            for (final UserInfo userInfo2 : users) {
                if (userInfo2.supportsSwitchToByUser()) {
                    boolean z = true;
                    if (userInfo != null ? userInfo.id != userInfo2.id : userInfo2.id != 0) {
                        z = false;
                    }
                    Bitmap userIcon = this.mUserManager.getUserIcon(userInfo2.id);
                    Bitmap bitmap = userIcon;
                    if (userIcon == null) {
                        bitmap = UserIcons.convertToBitmap(UserIcons.getDefaultUserIcon(this.mContext.getResources(), userInfo2.isGuest() ? -10000 : userInfo2.id, false));
                    }
                    BitmapDrawable bitmapDrawable = new BitmapDrawable(this.mContext.getResources(), createCircularClip(bitmap, dimensionPixelSize, dimensionPixelSize));
                    int i = R$drawable.ic_lock_user;
                    StringBuilder sb = new StringBuilder();
                    String str = userInfo2.name;
                    if (str == null) {
                        str = "Primary";
                    }
                    sb.append(str);
                    sb.append(z ? " ✔" : "");
                    SinglePressAction singlePressAction = new SinglePressAction(i, bitmapDrawable, sb.toString()) { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.4
                        {
                            GlobalActionsDialogLite.this = this;
                        }

                        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
                        public void onPress() {
                            try {
                                GlobalActionsDialogLite.this.mIActivityManager.switchUser(userInfo2.id);
                            } catch (RemoteException e) {
                                Log.e("GlobalActionsDialogLite", "Couldn't switch user " + e);
                            }
                        }

                        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
                        public boolean showBeforeProvisioning() {
                            return false;
                        }

                        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
                        public boolean showDuringKeyguard() {
                            return true;
                        }
                    };
                    if (z) {
                        singlePressAction.setStatus(this.mContext.getString(R$string.global_action_current_user));
                    }
                    addIfShouldShowAction(list, singlePressAction);
                }
            }
        }
    }

    public void awakenIfNecessary() {
        IDreamManager iDreamManager = this.mDreamManager;
        if (iDreamManager != null) {
            try {
                if (iDreamManager.isDreaming()) {
                    this.mDreamManager.awaken();
                }
            } catch (RemoteException e) {
            }
        }
    }

    public final void changeAirplaneModeSystemSetting(boolean z) {
        this.mGlobalSettings.putInt("airplane_mode_on", z ? 1 : 0);
        Intent intent = new Intent("android.intent.action.AIRPLANE_MODE");
        intent.addFlags(536870912);
        intent.putExtra("state", z);
        this.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
        if (this.mHasTelephony) {
            return;
        }
        this.mAirplaneState = z ? ToggleState.On : ToggleState.Off;
    }

    @VisibleForTesting
    public void createActionItems() {
        if (this.mHasVibrator) {
            this.mSilentModeAction = new SilentModeTriStateAction(this.mAudioManager, this.mHandler);
        } else {
            this.mSilentModeAction = new SilentModeToggleAction();
        }
        this.mAirplaneModeOn = new AirplaneModeAction();
        onAirplaneModeChanged();
        this.mItems.clear();
        this.mOverflowItems.clear();
        this.mPowerItems.clear();
        this.mRestartItems.clear();
        this.mUsersItems.clear();
        String[] restartActions = getRestartActions();
        ShutDownAction shutDownAction = new ShutDownAction();
        RestartAction restartAction = new RestartAction();
        RestartSystemAction restartSystemAction = new RestartSystemAction();
        RestartRecoveryAction restartRecoveryAction = new RestartRecoveryAction();
        RestartBootloaderAction restartBootloaderAction = new RestartBootloaderAction();
        RestartDownloadAction restartDownloadAction = new RestartDownloadAction();
        RestartFastbootAction restartFastbootAction = new RestartFastbootAction();
        ArraySet arraySet = new ArraySet();
        ArraySet arraySet2 = new ArraySet();
        ArrayList<Action> arrayList = new ArrayList();
        CurrentUserProvider currentUserProvider = new CurrentUserProvider();
        if (this.mEmergencyAffordanceManager.needsEmergencyAffordance()) {
            addIfShouldShowAction(arrayList, new EmergencyAffordanceAction());
            arraySet.add("emergency");
        }
        int i = 0;
        while (true) {
            String[] strArr = this.mActions;
            if (i >= strArr.length) {
                break;
            }
            String str = strArr[i];
            if (!arraySet.contains(str)) {
                if ("power".equals(str)) {
                    addIfShouldShowAction(arrayList, shutDownAction);
                } else if ("airplane".equals(str)) {
                    if (!isInLockTaskMode()) {
                        addIfShouldShowAction(arrayList, this.mAirplaneModeOn);
                    }
                } else if ("silent".equals(str)) {
                    if (this.mShowSilentToggle) {
                        addIfShouldShowAction(arrayList, this.mSilentModeAction);
                    }
                } else if ("users".equals(str)) {
                    if (!isInLockTaskMode() && this.mUserManager.getUsers().size() > 1) {
                        addUserActions(this.mUsersItems, currentUserProvider.get());
                        addIfShouldShowAction(arrayList, new UsersAction());
                    }
                } else if ("settings".equals(str)) {
                    addIfShouldShowAction(arrayList, getSettingsAction());
                } else if ("lockdown".equals(str)) {
                    if (!isInLockTaskMode() && shouldDisplayLockdown(currentUserProvider.get())) {
                        addIfShouldShowAction(arrayList, new LockDownAction());
                    }
                } else if ("voiceassist".equals(str)) {
                    addIfShouldShowAction(arrayList, getVoiceAssistAction());
                } else if ("assist".equals(str)) {
                    addIfShouldShowAction(arrayList, getAssistAction());
                } else if ("restart".equals(str)) {
                    addIfShouldShowAction(arrayList, restartAction);
                } else if ("screenshot".equals(str)) {
                    addIfShouldShowAction(arrayList, new ScreenshotAction());
                } else if ("logout".equals(str)) {
                    if (this.mDevicePolicyManager.isLogoutEnabled() && currentUserProvider.get() != null && currentUserProvider.get().id != 0) {
                        addIfShouldShowAction(arrayList, new LogoutAction());
                    }
                } else if ("emergency".equals(str)) {
                    if (shouldDisplayEmergency()) {
                        addIfShouldShowAction(arrayList, new EmergencyDialerAction());
                    }
                } else if ("devicecontrols".equals(str)) {
                    addIfShouldShowAction(arrayList, new DeviceControlsAction());
                } else {
                    Log.e("GlobalActionsDialogLite", "Invalid global action key " + str);
                }
                arraySet.add(str);
            }
            i++;
        }
        for (String str2 : restartActions) {
            if (!arraySet2.contains(str2)) {
                if ("restart".equals(str2)) {
                    addIfShouldShowAction(this.mRestartItems, restartSystemAction);
                } else if ("restart_recovery".equals(str2)) {
                    addIfShouldShowAction(this.mRestartItems, restartRecoveryAction);
                } else if ("restart_bootloader".equals(str2)) {
                    addIfShouldShowAction(this.mRestartItems, restartBootloaderAction);
                } else if ("restart_download".equals(str2)) {
                    addIfShouldShowAction(this.mRestartItems, restartDownloadAction);
                } else if ("restart_fastboot".equals(str2)) {
                    addIfShouldShowAction(this.mRestartItems, restartFastbootAction);
                }
                arraySet2.add(str2);
            }
        }
        if (arrayList.contains(shutDownAction) && arrayList.contains(restartAction) && arrayList.size() > getMaxShownPowerItems()) {
            int min = Math.min(arrayList.indexOf(restartAction), arrayList.indexOf(shutDownAction));
            arrayList.remove(shutDownAction);
            arrayList.remove(restartAction);
            this.mPowerItems.add(shutDownAction);
            this.mPowerItems.add(restartAction);
            arrayList.add(min, new PowerOptionsAction());
        }
        for (Action action : arrayList) {
            addActionItem(action);
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [android.app.AlertDialog, com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite] */
    public ActionsDialogLite createDialog() {
        initDialogItems();
        ?? actionsDialogLite = new ActionsDialogLite(this.mContext, R$style.Theme_SystemUI_Dialog_GlobalActionsLite, this.mAdapter, this.mOverflowAdapter, this.mSysuiColorExtractor, this.mStatusBarService, this.mNotificationShadeWindowController, new Runnable() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                GlobalActionsDialogLite.this.onRefresh();
            }
        }, this.mKeyguardShowing, this.mPowerAdapter, this.mRestartAdapter, this.mUsersAdapter, this.mUiEventLogger, this.mCentralSurfacesOptional, this.mKeyguardUpdateMonitor, this.mLockPatternUtils);
        actionsDialogLite.setOnDismissListener(this);
        actionsDialogLite.setOnShowListener(this);
        return actionsDialogLite;
    }

    public void destroy() {
        this.mBroadcastDispatcher.unregisterReceiver(this.mBroadcastReceiver);
        this.mGlobalSettings.unregisterContentObserver(this.mAirplaneModeObserver);
        this.mConfigurationController.removeCallback(this);
    }

    public void dismissDialog() {
        this.mHandler.removeMessages(0);
        this.mHandler.sendEmptyMessage(0);
    }

    @Override // com.android.systemui.plugins.GlobalActionsPanelPlugin.Callbacks
    public void dismissGlobalActionsMenu() {
        dismissDialog();
    }

    public final Action getAssistAction() {
        return new SinglePressAction(17302310, 17040416) { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.2
            {
                GlobalActionsDialogLite.this = this;
            }

            @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
            public void onPress() {
                Intent intent = new Intent("android.intent.action.ASSIST");
                intent.addFlags(335544320);
                GlobalActionsDialogLite.this.mContext.startActivity(intent);
            }

            @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
            public boolean showBeforeProvisioning() {
                return true;
            }

            @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
            public boolean showDuringKeyguard() {
                return true;
            }
        };
    }

    public UserInfo getCurrentUser() {
        return this.mUserTracker.getUserInfo();
    }

    @VisibleForTesting
    public String[] getDefaultActions() {
        return this.mResources.getStringArray(17236078);
    }

    public int getEmergencyBackgroundColor(Context context) {
        return context.getResources().getColor(R$color.global_actions_lite_emergency_background);
    }

    public int getEmergencyIconColor(Context context) {
        return context.getResources().getColor(R$color.global_actions_lite_emergency_icon);
    }

    public int getEmergencyTextColor(Context context) {
        return context.getResources().getColor(R$color.global_actions_lite_text);
    }

    public int getGridItemLayoutResource() {
        return R$layout.global_actions_grid_item_lite;
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public Lifecycle getLifecycle() {
        return this.mLifecycle;
    }

    @VisibleForTesting
    public int getMaxShownPowerItems() {
        return this.mResources.getInteger(R$integer.power_menu_lite_max_columns) * this.mResources.getInteger(R$integer.power_menu_lite_max_rows);
    }

    @VisibleForTesting
    public String[] getRestartActions() {
        return this.mResources.getStringArray(17236122);
    }

    public final Action getSettingsAction() {
        return new SinglePressAction(17302851, 17040426) { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.1
            {
                GlobalActionsDialogLite.this = this;
            }

            @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
            public void onPress() {
                Intent intent = new Intent("android.settings.SETTINGS");
                intent.addFlags(335544320);
                GlobalActionsDialogLite.this.mContext.startActivity(intent);
            }

            @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
            public boolean showBeforeProvisioning() {
                return true;
            }

            @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
            public boolean showDuringKeyguard() {
                return true;
            }
        };
    }

    public final Action getVoiceAssistAction() {
        return new SinglePressAction(17302893, 17040430) { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.3
            {
                GlobalActionsDialogLite.this = this;
            }

            @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
            public void onPress() {
                Intent intent = new Intent("android.intent.action.VOICE_ASSIST");
                intent.addFlags(335544320);
                GlobalActionsDialogLite.this.mContext.startActivity(intent);
            }

            @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
            public boolean showBeforeProvisioning() {
                return true;
            }

            @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
            public boolean showDuringKeyguard() {
                return true;
            }
        };
    }

    public void handleShow(Expandable expandable) {
        awakenIfNecessary();
        this.mDialog = createDialog();
        prepareDialog();
        WindowManager.LayoutParams attributes = this.mDialog.getWindow().getAttributes();
        attributes.setTitle("ActionsDialog");
        attributes.layoutInDisplayCutoutMode = 3;
        this.mDialog.getWindow().setAttributes(attributes);
        this.mDialog.getWindow().addFlags(131072);
        DialogLaunchAnimator.Controller dialogLaunchController = expandable != null ? expandable.dialogLaunchController(new DialogCuj(58, "global_actions")) : null;
        if (dialogLaunchController != null) {
            this.mDialogLaunchAnimator.show(this.mDialog, dialogLaunchController);
        } else {
            this.mDialog.show();
        }
        this.mWindowManagerFuncs.onGlobalActionsShown();
    }

    public void initDialogItems() {
        createActionItems();
        this.mAdapter = new MyAdapter();
        this.mOverflowAdapter = new MyOverflowAdapter();
        this.mPowerAdapter = new MyPowerOptionsAdapter();
        this.mRestartAdapter = new MyRestartOptionsAdapter();
        this.mUsersAdapter = new MyUsersAdapter();
    }

    public final boolean isInLockTaskMode() {
        try {
            return ActivityTaskManager.getService().isInLockTaskMode();
        } catch (RemoteException e) {
            return false;
        }
    }

    public final void lockProfiles() {
        int[] enabledProfileIds;
        int i = getCurrentUser().id;
        for (int i2 : this.mUserManager.getEnabledProfileIds(i)) {
            if (i2 != i) {
                this.mTrustManager.setDeviceLockedForUser(i2, true);
            }
        }
    }

    @VisibleForTesting
    public BugReportAction makeBugReportActionForTesting() {
        return new BugReportAction();
    }

    @VisibleForTesting
    public EmergencyDialerAction makeEmergencyDialerActionForTesting() {
        return new EmergencyDialerAction();
    }

    @VisibleForTesting
    public ScreenshotAction makeScreenshotActionForTesting() {
        return new ScreenshotAction();
    }

    public final void onAirplaneModeChanged() {
        if (this.mAirplaneModeOn == null) {
            return;
        }
        boolean z = false;
        if (this.mGlobalSettings.getInt("airplane_mode_on", 0) == 1) {
            z = true;
        }
        ToggleState toggleState = z ? ToggleState.On : ToggleState.Off;
        this.mAirplaneState = toggleState;
        this.mAirplaneModeOn.updateState(toggleState);
    }

    public void onConfigChanged(Configuration configuration) {
        int i;
        ColorExtractor.OnColorsChangedListener onColorsChangedListener = this.mDialog;
        if (onColorsChangedListener == null || !onColorsChangedListener.isShowing() || (i = configuration.smallestScreenWidthDp) == this.mSmallestScreenWidthDp) {
            return;
        }
        this.mSmallestScreenWidthDp = i;
        this.mDialog.refreshDialog();
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialogInterface) {
        if (this.mDialog == dialogInterface) {
            this.mDialog = null;
        }
        this.mUiEventLogger.log(GlobalActionsEvent.GA_POWER_MENU_CLOSE);
        this.mWindowManagerFuncs.onGlobalActionsHidden();
        this.mLifecycle.setCurrentState(Lifecycle.State.CREATED);
    }

    public void onRefresh() {
        createActionItems();
    }

    @Override // android.content.DialogInterface.OnShowListener
    public void onShow(DialogInterface dialogInterface) {
        this.mMetricsLogger.visible(1568);
        this.mUiEventLogger.log(GlobalActionsEvent.GA_POWER_MENU_OPEN);
    }

    public void onTuningChanged(String str, String str2) {
        if ("customsecure:power_menu_actions".equals(str)) {
            this.mActions = this.mCustomGlobalActions.getUserActionsArray();
        }
    }

    public void prepareDialog() {
        refreshSilentMode();
        this.mAirplaneModeOn.updateState(this.mAirplaneState);
        this.mAdapter.notifyDataSetChanged();
        this.mLifecycle.setCurrentState(Lifecycle.State.RESUMED);
    }

    public final boolean rebootAction(boolean z) {
        return rebootAction(z, null);
    }

    public final boolean rebootAction(final boolean z, final String str) {
        if (this.mKeyguardStateController.isMethodSecure() && this.mKeyguardStateController.isShowing()) {
            this.mActivityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    GlobalActionsDialogLite.m2730$r8$lambda$oPQrpJM2SKyhFBKjROVZ4cYG5U(GlobalActionsDialogLite.this, z, str);
                }
            });
            return true;
        }
        this.mWindowManagerFuncs.reboot(z, str);
        return true;
    }

    public final void refreshSilentMode() {
        if (this.mHasVibrator) {
            return;
        }
        Integer num = (Integer) this.mRingerModeTracker.getRingerMode().getValue();
        ((ToggleAction) this.mSilentModeAction).updateState(num != null && num.intValue() != 2 ? ToggleState.On : ToggleState.Off);
    }

    @VisibleForTesting
    public void setZeroDialogPressDelayForTesting() {
        this.mDialogPressDelay = 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0022, code lost:
        if (r5.isPrimary() != false) goto L7;
     */
    @VisibleForTesting
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean shouldDisplayBugReport(UserInfo userInfo) {
        boolean z = false;
        if (this.mGlobalSettings.getInt("bugreport_in_power_menu", 0) != 0) {
            if (userInfo != null) {
                z = false;
            }
            z = true;
        }
        return z;
    }

    @VisibleForTesting
    public boolean shouldDisplayEmergency() {
        return this.mHasTelephony;
    }

    @VisibleForTesting
    public boolean shouldDisplayLockdown(UserInfo userInfo) {
        boolean z = false;
        if (userInfo == null) {
            return false;
        }
        int i = userInfo.id;
        if (this.mKeyguardStateController.isMethodSecure()) {
            int strongAuthForUser = this.mLockPatternUtils.getStrongAuthForUser(i);
            if (strongAuthForUser == 0 || strongAuthForUser == 4) {
                z = true;
            }
            return z;
        }
        return false;
    }

    @VisibleForTesting
    public boolean shouldShowAction(Action action) {
        if (!this.mKeyguardShowing || action.showDuringKeyguard()) {
            if (this.mDeviceProvisioned || action.showBeforeProvisioning()) {
                return action.shouldShow();
            }
            return false;
        }
        return false;
    }

    public final boolean shouldShowRestartSubmenu() {
        return PowerMenuUtils.isAdvancedRestartPossible(this.mContext);
    }

    public void showOrHideDialog(boolean z, boolean z2, Expandable expandable) {
        this.mKeyguardShowing = z;
        this.mDeviceProvisioned = z2;
        ColorExtractor.OnColorsChangedListener onColorsChangedListener = this.mDialog;
        if (onColorsChangedListener == null || !onColorsChangedListener.isShowing()) {
            handleShow(expandable);
            return;
        }
        this.mWindowManagerFuncs.onGlobalActionsShown();
        this.mDialog.dismiss();
        this.mDialog = null;
    }
}