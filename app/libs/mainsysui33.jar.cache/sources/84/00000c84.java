package com.android.keyguard;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.hardware.biometrics.BiometricSourceType;
import android.os.Process;
import android.os.VibrationAttributes;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.MathUtils;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.keyguard.LockIconViewController;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.Dumpable;
import com.android.systemui.R$dimen;
import com.android.systemui.R$drawable;
import com.android.systemui.R$integer;
import com.android.systemui.R$string;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.AuthRippleController;
import com.android.systemui.biometrics.UdfpsController;
import com.android.systemui.doze.util.BurnInHelperKt;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.StatusBarState;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.kotlin.JavaAdapterKt;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.function.Consumer;

/* loaded from: mainsysui33.jar:com/android/keyguard/LockIconViewController.class */
public class LockIconViewController extends ViewController<LockIconView> implements Dumpable {
    public static final VibrationAttributes TOUCH_VIBRATION_ATTRIBUTES = VibrationAttributes.createForUsage(18);
    public static final float sDefaultDensity;
    public static final int sLockIconRadiusPx;
    public final View.OnClickListener mA11yClickListener;
    public final View.AccessibilityDelegate mAccessibilityDelegate;
    public final AccessibilityManager mAccessibilityManager;
    public final AccessibilityManager.AccessibilityStateChangeListener mAccessibilityStateChangeListener;
    public int mActivePointerId;
    public final AuthController mAuthController;
    public final AuthController.Callback mAuthControllerCallback;
    public final AuthRippleController mAuthRippleController;
    public int mBottomPaddingPx;
    public boolean mCanDismissLockScreen;
    public Runnable mCancelDelayedUpdateVisibilityRunnable;
    public final ConfigurationController mConfigurationController;
    public final ConfigurationController.ConfigurationListener mConfigurationListener;
    public int mDefaultPaddingPx;
    public boolean mDownDetected;
    public final Consumer<TransitionStep> mDozeTransitionCallback;
    public final DelayableExecutor mExecutor;
    public final FalsingManager mFalsingManager;
    public final FeatureFlags mFeatureFlags;
    public float mHeightPixels;
    public final AnimatedStateListDrawable mIcon;
    public float mInterpolatedDarkAmount;
    public boolean mIsBouncerShowing;
    public boolean mIsDozing;
    public final Consumer<Boolean> mIsDozingCallback;
    public boolean mIsKeyguardShowing;
    public final KeyguardInteractor mKeyguardInteractor;
    public final KeyguardStateController.Callback mKeyguardStateCallback;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback;
    public final KeyguardViewController mKeyguardViewController;
    public CharSequence mLockedLabel;
    public Runnable mLongPressCancelRunnable;
    public final long mLongPressTimeout;
    public final int mMaxBurnInOffsetX;
    public final int mMaxBurnInOffsetY;
    public Runnable mOnGestureDetectedRunnable;
    public boolean mRunningFPS;
    public final Rect mSensorTouchLocation;
    public boolean mShowAodLockIcon;
    public boolean mShowAodUnlockedIcon;
    public boolean mShowLockIcon;
    public boolean mShowUnlockIcon;
    public int mStatusBarState;
    public final StatusBarStateController mStatusBarStateController;
    public StatusBarStateController.StateListener mStatusBarStateListener;
    public final KeyguardTransitionInteractor mTransitionInteractor;
    public boolean mUdfpsEnrolled;
    public boolean mUdfpsSupported;
    public CharSequence mUnlockedLabel;
    public boolean mUserUnlockedWithBiometric;
    public VelocityTracker mVelocityTracker;
    public final VibratorHelper mVibrator;
    public float mWidthPixels;

    /* renamed from: com.android.keyguard.LockIconViewController$3 */
    /* loaded from: mainsysui33.jar:com/android/keyguard/LockIconViewController$3.class */
    public class AnonymousClass3 extends KeyguardUpdateMonitorCallback {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.LockIconViewController$3$$ExternalSyntheticLambda0.run():void] */
        public static /* synthetic */ void $r8$lambda$Q2cjvog0DrTnEqEiMvX76t4xQNc(AnonymousClass3 anonymousClass3) {
            anonymousClass3.lambda$onBiometricRunningStateChanged$0();
        }

        public AnonymousClass3() {
            LockIconViewController.this = r4;
        }

        public /* synthetic */ void lambda$onBiometricRunningStateChanged$0() {
            LockIconViewController.this.updateVisibility();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onBiometricRunningStateChanged(boolean z, BiometricSourceType biometricSourceType) {
            boolean z2 = LockIconViewController.this.mRunningFPS;
            boolean z3 = LockIconViewController.this.mUserUnlockedWithBiometric;
            LockIconViewController lockIconViewController = LockIconViewController.this;
            lockIconViewController.mUserUnlockedWithBiometric = lockIconViewController.mKeyguardUpdateMonitor.getUserUnlockedWithBiometric(KeyguardUpdateMonitor.getCurrentUser());
            if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
                LockIconViewController.this.mRunningFPS = z;
                if (z2 && !LockIconViewController.this.mRunningFPS) {
                    if (LockIconViewController.this.mCancelDelayedUpdateVisibilityRunnable != null) {
                        LockIconViewController.this.mCancelDelayedUpdateVisibilityRunnable.run();
                    }
                    LockIconViewController lockIconViewController2 = LockIconViewController.this;
                    lockIconViewController2.mCancelDelayedUpdateVisibilityRunnable = lockIconViewController2.mExecutor.executeDelayed(new Runnable() { // from class: com.android.keyguard.LockIconViewController$3$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            LockIconViewController.AnonymousClass3.$r8$lambda$Q2cjvog0DrTnEqEiMvX76t4xQNc(LockIconViewController.AnonymousClass3.this);
                        }
                    }, 50L);
                    return;
                }
            }
            if (z3 == LockIconViewController.this.mUserUnlockedWithBiometric && z2 == LockIconViewController.this.mRunningFPS) {
                return;
            }
            LockIconViewController.this.updateVisibility();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onBiometricsCleared() {
            boolean z = LockIconViewController.this.mUserUnlockedWithBiometric;
            LockIconViewController lockIconViewController = LockIconViewController.this;
            lockIconViewController.mUserUnlockedWithBiometric = lockIconViewController.mKeyguardUpdateMonitor.getUserUnlockedWithBiometric(KeyguardUpdateMonitor.getCurrentUser());
            if (z != LockIconViewController.this.mUserUnlockedWithBiometric) {
                LockIconViewController.this.updateVisibility();
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onKeyguardBouncerStateChanged(boolean z) {
            LockIconViewController.this.mIsBouncerShowing = z;
            LockIconViewController.this.updateVisibility();
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda1.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$RIK63YthUkJn5ijafWKkawWMQoU(LockIconViewController lockIconViewController, Boolean bool) {
        lockIconViewController.lambda$new$1(bool);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda3.onAccessibilityStateChanged(boolean):void] */
    public static /* synthetic */ void $r8$lambda$V2qgSMJAJbEvXZkF6ir_A7XtahA(LockIconViewController lockIconViewController, boolean z) {
        lockIconViewController.lambda$new$4(z);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda4.run():void] */
    public static /* synthetic */ void $r8$lambda$h98ceOtiS5JD1Nfnu1Y0fyk_1uo(LockIconViewController lockIconViewController) {
        lockIconViewController.onLongPress();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda5.run():void] */
    public static /* synthetic */ void $r8$lambda$j0iuivExaZHbw_xY6zE8jdtH6y8(LockIconViewController lockIconViewController) {
        lockIconViewController.lambda$updateUdfpsConfig$2();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda2.onClick(android.view.View):void] */
    /* renamed from: $r8$lambda$npfG-JJY25MEAQDdOiVh12qc0b4 */
    public static /* synthetic */ void m776$r8$lambda$npfGJJY25MEAQDdOiVh12qc0b4(LockIconViewController lockIconViewController, View view) {
        lockIconViewController.lambda$new$3(view);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda0.accept(java.lang.Object):void] */
    public static /* synthetic */ void $r8$lambda$pNdRdg8dr6bU4mdgVFhgcl7g1HQ(LockIconViewController lockIconViewController, TransitionStep transitionStep) {
        lockIconViewController.lambda$new$0(transitionStep);
    }

    static {
        float f = DisplayMetrics.DENSITY_DEVICE_STABLE / 160.0f;
        sDefaultDensity = f;
        sLockIconRadiusPx = (int) (f * 36.0f);
    }

    public LockIconViewController(LockIconView lockIconView, StatusBarStateController statusBarStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardViewController keyguardViewController, KeyguardStateController keyguardStateController, FalsingManager falsingManager, AuthController authController, DumpManager dumpManager, AccessibilityManager accessibilityManager, ConfigurationController configurationController, DelayableExecutor delayableExecutor, VibratorHelper vibratorHelper, AuthRippleController authRippleController, Resources resources, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardInteractor keyguardInteractor, FeatureFlags featureFlags) {
        super(lockIconView);
        this.mActivePointerId = -1;
        this.mSensorTouchLocation = new Rect();
        this.mDozeTransitionCallback = new Consumer() { // from class: com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                LockIconViewController.$r8$lambda$pNdRdg8dr6bU4mdgVFhgcl7g1HQ(LockIconViewController.this, (TransitionStep) obj);
            }
        };
        this.mIsDozingCallback = new Consumer() { // from class: com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                LockIconViewController.$r8$lambda$RIK63YthUkJn5ijafWKkawWMQoU(LockIconViewController.this, (Boolean) obj);
            }
        };
        this.mAccessibilityDelegate = new View.AccessibilityDelegate() { // from class: com.android.keyguard.LockIconViewController.1
            public final AccessibilityNodeInfo.AccessibilityAction mAccessibilityAuthenticateHint;
            public final AccessibilityNodeInfo.AccessibilityAction mAccessibilityEnterHint;

            {
                LockIconViewController.this = this;
                this.mAccessibilityAuthenticateHint = new AccessibilityNodeInfo.AccessibilityAction(16, this.getResources().getString(R$string.accessibility_authenticate_hint));
                this.mAccessibilityEnterHint = new AccessibilityNodeInfo.AccessibilityAction(16, this.getResources().getString(R$string.accessibility_enter_hint));
            }

            @Override // android.view.View.AccessibilityDelegate
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                if (LockIconViewController.this.isActionable()) {
                    if (LockIconViewController.this.mShowLockIcon) {
                        accessibilityNodeInfo.addAction(this.mAccessibilityAuthenticateHint);
                    } else if (LockIconViewController.this.mShowUnlockIcon) {
                        accessibilityNodeInfo.addAction(this.mAccessibilityEnterHint);
                    }
                }
            }
        };
        this.mStatusBarStateListener = new StatusBarStateController.StateListener() { // from class: com.android.keyguard.LockIconViewController.2
            {
                LockIconViewController.this = this;
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public void onDozeAmountChanged(float f, float f2) {
                if (LockIconViewController.this.mFeatureFlags.isEnabled(Flags.DOZING_MIGRATION_1)) {
                    return;
                }
                LockIconViewController.this.mInterpolatedDarkAmount = f2;
                ((LockIconView) ((ViewController) LockIconViewController.this).mView).setDozeAmount(f2);
                LockIconViewController.this.updateBurnInOffsets();
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public void onDozingChanged(boolean z) {
                if (LockIconViewController.this.mFeatureFlags.isEnabled(Flags.DOZING_MIGRATION_1)) {
                    return;
                }
                LockIconViewController.this.mIsDozing = z;
                LockIconViewController.this.updateBurnInOffsets();
                LockIconViewController.this.updateVisibility();
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public void onStateChanged(int i) {
                LockIconViewController.this.mStatusBarState = i;
                LockIconViewController.this.updateVisibility();
            }
        };
        this.mKeyguardUpdateMonitorCallback = new AnonymousClass3();
        this.mKeyguardStateCallback = new KeyguardStateController.Callback() { // from class: com.android.keyguard.LockIconViewController.4
            {
                LockIconViewController.this = this;
            }

            public void onKeyguardFadingAwayChanged() {
                LockIconViewController.this.updateKeyguardShowing();
                LockIconViewController.this.updateVisibility();
            }

            public void onKeyguardShowingChanged() {
                LockIconViewController lockIconViewController = LockIconViewController.this;
                lockIconViewController.mCanDismissLockScreen = lockIconViewController.mKeyguardStateController.canDismissLockScreen();
                LockIconViewController lockIconViewController2 = LockIconViewController.this;
                lockIconViewController2.mIsBouncerShowing = lockIconViewController2.mKeyguardViewController.isBouncerShowing();
                LockIconViewController.this.updateKeyguardShowing();
                if (LockIconViewController.this.mIsKeyguardShowing) {
                    LockIconViewController lockIconViewController3 = LockIconViewController.this;
                    lockIconViewController3.mUserUnlockedWithBiometric = lockIconViewController3.mKeyguardUpdateMonitor.getUserUnlockedWithBiometric(KeyguardUpdateMonitor.getCurrentUser());
                }
                LockIconViewController.this.updateVisibility();
            }

            public void onUnlockedChanged() {
                LockIconViewController lockIconViewController = LockIconViewController.this;
                lockIconViewController.mCanDismissLockScreen = lockIconViewController.mKeyguardStateController.canDismissLockScreen();
                LockIconViewController.this.updateKeyguardShowing();
                LockIconViewController.this.updateVisibility();
            }
        };
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.LockIconViewController.5
            {
                LockIconViewController.this = this;
            }

            public void onConfigChanged(Configuration configuration) {
                LockIconViewController.this.updateConfiguration();
                LockIconViewController.this.updateColors();
            }

            public void onThemeChanged() {
                LockIconViewController.this.updateColors();
            }

            public void onUiModeChanged() {
                LockIconViewController.this.updateColors();
            }
        };
        this.mAuthControllerCallback = new AuthController.Callback() { // from class: com.android.keyguard.LockIconViewController.6
            {
                LockIconViewController.this = this;
            }

            @Override // com.android.systemui.biometrics.AuthController.Callback
            public void onAllAuthenticatorsRegistered() {
                LockIconViewController.this.updateUdfpsConfig();
            }

            @Override // com.android.systemui.biometrics.AuthController.Callback
            public void onEnrollmentsChanged() {
                LockIconViewController.this.updateUdfpsConfig();
            }

            @Override // com.android.systemui.biometrics.AuthController.Callback
            public void onUdfpsLocationChanged() {
                LockIconViewController.this.updateUdfpsConfig();
            }
        };
        this.mA11yClickListener = new View.OnClickListener() { // from class: com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LockIconViewController.m776$r8$lambda$npfGJJY25MEAQDdOiVh12qc0b4(LockIconViewController.this, view);
            }
        };
        this.mAccessibilityStateChangeListener = new AccessibilityManager.AccessibilityStateChangeListener() { // from class: com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda3
            @Override // android.view.accessibility.AccessibilityManager.AccessibilityStateChangeListener
            public final void onAccessibilityStateChanged(boolean z) {
                LockIconViewController.$r8$lambda$V2qgSMJAJbEvXZkF6ir_A7XtahA(LockIconViewController.this, z);
            }
        };
        this.mStatusBarStateController = statusBarStateController;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mAuthController = authController;
        this.mKeyguardViewController = keyguardViewController;
        this.mKeyguardStateController = keyguardStateController;
        this.mFalsingManager = falsingManager;
        this.mAccessibilityManager = accessibilityManager;
        this.mConfigurationController = configurationController;
        this.mExecutor = delayableExecutor;
        this.mVibrator = vibratorHelper;
        this.mAuthRippleController = authRippleController;
        this.mTransitionInteractor = keyguardTransitionInteractor;
        this.mKeyguardInteractor = keyguardInteractor;
        this.mFeatureFlags = featureFlags;
        this.mMaxBurnInOffsetX = resources.getDimensionPixelSize(R$dimen.udfps_burn_in_offset_x);
        this.mMaxBurnInOffsetY = resources.getDimensionPixelSize(R$dimen.udfps_burn_in_offset_y);
        AnimatedStateListDrawable animatedStateListDrawable = (AnimatedStateListDrawable) resources.getDrawable(R$drawable.super_lock_icon, ((LockIconView) ((ViewController) this).mView).getContext().getTheme());
        this.mIcon = animatedStateListDrawable;
        ((LockIconView) ((ViewController) this).mView).setImageDrawable(animatedStateListDrawable);
        this.mUnlockedLabel = resources.getString(R$string.accessibility_unlock_button);
        this.mLockedLabel = resources.getString(R$string.accessibility_lock_icon);
        this.mLongPressTimeout = resources.getInteger(R$integer.config_lockIconLongPress);
        dumpManager.registerDumpable("LockIconViewController", this);
    }

    public /* synthetic */ void lambda$new$0(TransitionStep transitionStep) {
        this.mInterpolatedDarkAmount = transitionStep.getValue();
        ((LockIconView) ((ViewController) this).mView).setDozeAmount(transitionStep.getValue());
        updateBurnInOffsets();
    }

    public /* synthetic */ void lambda$new$1(Boolean bool) {
        this.mIsDozing = bool.booleanValue();
        updateBurnInOffsets();
        updateVisibility();
    }

    public /* synthetic */ void lambda$new$3(View view) {
        onLongPress();
    }

    public /* synthetic */ void lambda$new$4(boolean z) {
        updateAccessibility();
    }

    public /* synthetic */ void lambda$updateUdfpsConfig$2() {
        updateIsUdfpsEnrolled();
        updateConfiguration();
    }

    public final void cancelTouches() {
        this.mDownDetected = false;
        Runnable runnable = this.mLongPressCancelRunnable;
        if (runnable != null) {
            runnable.run();
        }
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    public void dozeTimeTick() {
        updateBurnInOffsets();
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("mUdfpsSupported: " + this.mUdfpsSupported);
        printWriter.println("mUdfpsEnrolled: " + this.mUdfpsEnrolled);
        printWriter.println("mIsKeyguardShowing: " + this.mIsKeyguardShowing);
        printWriter.println(" mIcon: ");
        for (int i : this.mIcon.getState()) {
            printWriter.print(" " + i);
        }
        printWriter.println();
        printWriter.println(" mShowUnlockIcon: " + this.mShowUnlockIcon);
        printWriter.println(" mShowLockIcon: " + this.mShowLockIcon);
        printWriter.println(" mShowAodUnlockedIcon: " + this.mShowAodUnlockedIcon);
        printWriter.println();
        printWriter.println(" mIsDozing: " + this.mIsDozing);
        printWriter.println(" isFlagEnabled(DOZING_MIGRATION_1): " + this.mFeatureFlags.isEnabled(Flags.DOZING_MIGRATION_1));
        printWriter.println(" mIsBouncerShowing: " + this.mIsBouncerShowing);
        printWriter.println(" mUserUnlockedWithBiometric: " + this.mUserUnlockedWithBiometric);
        printWriter.println(" mRunningFPS: " + this.mRunningFPS);
        printWriter.println(" mCanDismissLockScreen: " + this.mCanDismissLockScreen);
        printWriter.println(" mStatusBarState: " + StatusBarState.toString(this.mStatusBarState));
        printWriter.println(" mInterpolatedDarkAmount: " + this.mInterpolatedDarkAmount);
        printWriter.println(" mSensorTouchLocation: " + this.mSensorTouchLocation);
        View view = ((ViewController) this).mView;
        if (view != null) {
            ((LockIconView) view).dump(printWriter, strArr);
        }
    }

    public float getBottom() {
        return ((LockIconView) ((ViewController) this).mView).getLocationBottom();
    }

    public float getTop() {
        return ((LockIconView) ((ViewController) this).mView).getLocationTop();
    }

    public final boolean inLockIconArea(MotionEvent motionEvent) {
        ((LockIconView) ((ViewController) this).mView).getHitRect(this.mSensorTouchLocation);
        return this.mSensorTouchLocation.contains((int) motionEvent.getX(), (int) motionEvent.getY()) && ((LockIconView) ((ViewController) this).mView).getVisibility() == 0;
    }

    public final boolean isActionable() {
        boolean z = false;
        if (this.mIsBouncerShowing) {
            Log.v("LockIconViewController", "lock icon long-press ignored, bouncer already showing.");
            return false;
        }
        if (this.mUdfpsSupported || this.mShowUnlockIcon) {
            z = true;
        }
        return z;
    }

    public final boolean isLockScreen() {
        boolean z = true;
        if (this.mIsDozing || this.mIsBouncerShowing || this.mStatusBarState != 1) {
            z = false;
        }
        return z;
    }

    public void onInit() {
        ((LockIconView) ((ViewController) this).mView).setAccessibilityDelegate(this.mAccessibilityDelegate);
        if (this.mFeatureFlags.isEnabled(Flags.DOZING_MIGRATION_1)) {
            JavaAdapterKt.collectFlow(((ViewController) this).mView, this.mTransitionInteractor.getDozeAmountTransition(), this.mDozeTransitionCallback);
            JavaAdapterKt.collectFlow(((ViewController) this).mView, this.mKeyguardInteractor.isDozing(), this.mIsDozingCallback);
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (inLockIconArea(motionEvent) && isActionable()) {
            if (motionEvent.getActionMasked() == 0) {
                return true;
            }
            return this.mDownDetected;
        }
        return false;
    }

    public final void onLongPress() {
        AuthRippleController authRippleController;
        cancelTouches();
        if (this.mFalsingManager.isFalseTouch(14)) {
            Log.v("LockIconViewController", "lock icon long-press rejected by the falsing manager.");
            return;
        }
        this.mIsBouncerShowing = true;
        if (this.mUdfpsSupported && this.mShowUnlockIcon && (authRippleController = this.mAuthRippleController) != null) {
            authRippleController.showUnlockRipple(BiometricSourceType.FINGERPRINT);
        }
        updateVisibility();
        Runnable runnable = this.mOnGestureDetectedRunnable;
        if (runnable != null) {
            runnable.run();
        }
        this.mVibrator.vibrate(Process.myUid(), getContext().getOpPackageName(), UdfpsController.EFFECT_CLICK, "lock-screen-lock-icon-longpress", TOUCH_VIBRATION_ATTRIBUTES);
        this.mKeyguardViewController.showPrimaryBouncer(true);
    }

    public boolean onTouchEvent(MotionEvent motionEvent, Runnable runnable) {
        if (!onInterceptTouchEvent(motionEvent)) {
            cancelTouches();
            return false;
        }
        this.mOnGestureDetectedRunnable = runnable;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 0) {
            if (actionMasked != 1) {
                if (actionMasked != 2) {
                    if (actionMasked != 3) {
                        if (actionMasked != 7) {
                            if (actionMasked != 9) {
                                if (actionMasked != 10) {
                                    return true;
                                }
                            }
                        }
                    }
                }
                this.mVelocityTracker.addMovement(motionEvent);
                this.mVelocityTracker.computeCurrentVelocity(1000);
                float computePointerSpeed = UdfpsController.computePointerSpeed(this.mVelocityTracker, this.mActivePointerId);
                if (motionEvent.getClassification() == 2 || !UdfpsController.exceedsVelocityThreshold(computePointerSpeed)) {
                    return true;
                }
                Log.v("LockIconViewController", "lock icon long-press rescheduled due to high pointer velocity=" + computePointerSpeed);
                this.mLongPressCancelRunnable.run();
                this.mLongPressCancelRunnable = this.mExecutor.executeDelayed(new Runnable() { // from class: com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        LockIconViewController.$r8$lambda$h98ceOtiS5JD1Nfnu1Y0fyk_1uo(LockIconViewController.this);
                    }
                }, this.mLongPressTimeout);
                return true;
            }
            cancelTouches();
            return true;
        }
        if (!this.mDownDetected && this.mAccessibilityManager.isTouchExplorationEnabled()) {
            this.mVibrator.vibrate(Process.myUid(), getContext().getOpPackageName(), UdfpsController.EFFECT_CLICK, "lock-icon-down", TOUCH_VIBRATION_ATTRIBUTES);
        }
        this.mActivePointerId = motionEvent.getPointerId(0);
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        } else {
            velocityTracker.clear();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        this.mDownDetected = true;
        this.mLongPressCancelRunnable = this.mExecutor.executeDelayed(new Runnable() { // from class: com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                LockIconViewController.$r8$lambda$h98ceOtiS5JD1Nfnu1Y0fyk_1uo(LockIconViewController.this);
            }
        }, this.mLongPressTimeout);
        return true;
    }

    public void onViewAttached() {
        updateIsUdfpsEnrolled();
        updateConfiguration();
        updateKeyguardShowing();
        this.mUserUnlockedWithBiometric = false;
        this.mIsBouncerShowing = this.mKeyguardViewController.isBouncerShowing();
        this.mIsDozing = this.mStatusBarStateController.isDozing();
        this.mInterpolatedDarkAmount = this.mStatusBarStateController.getDozeAmount();
        this.mRunningFPS = this.mKeyguardUpdateMonitor.isFingerprintDetectionRunning();
        this.mCanDismissLockScreen = this.mKeyguardStateController.canDismissLockScreen();
        this.mStatusBarState = this.mStatusBarStateController.getState();
        updateColors();
        this.mConfigurationController.addCallback(this.mConfigurationListener);
        this.mAuthController.addCallback(this.mAuthControllerCallback);
        this.mKeyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
        this.mStatusBarStateController.addCallback(this.mStatusBarStateListener);
        this.mKeyguardStateController.addCallback(this.mKeyguardStateCallback);
        this.mDownDetected = false;
        updateBurnInOffsets();
        updateVisibility();
        this.mAccessibilityManager.addAccessibilityStateChangeListener(this.mAccessibilityStateChangeListener);
        updateAccessibility();
    }

    public void onViewDetached() {
        this.mAuthController.removeCallback(this.mAuthControllerCallback);
        this.mConfigurationController.removeCallback(this.mConfigurationListener);
        this.mKeyguardUpdateMonitor.removeCallback(this.mKeyguardUpdateMonitorCallback);
        this.mStatusBarStateController.removeCallback(this.mStatusBarStateListener);
        this.mKeyguardStateController.removeCallback(this.mKeyguardStateCallback);
        Runnable runnable = this.mCancelDelayedUpdateVisibilityRunnable;
        if (runnable != null) {
            runnable.run();
            this.mCancelDelayedUpdateVisibilityRunnable = null;
        }
        this.mAccessibilityManager.removeAccessibilityStateChangeListener(this.mAccessibilityStateChangeListener);
    }

    public void setAlpha(float f) {
        ((LockIconView) ((ViewController) this).mView).setAlpha(f);
    }

    public final void updateAccessibility() {
        if (this.mAccessibilityManager.isEnabled()) {
            ((LockIconView) ((ViewController) this).mView).setOnClickListener(this.mA11yClickListener);
        } else {
            ((LockIconView) ((ViewController) this).mView).setOnClickListener(null);
        }
    }

    public final void updateBurnInOffsets() {
        float lerp = MathUtils.lerp((float) ActionBarShadowController.ELEVATION_LOW, BurnInHelperKt.getBurnInOffset(this.mMaxBurnInOffsetX * 2, true) - this.mMaxBurnInOffsetX, this.mInterpolatedDarkAmount);
        float lerp2 = MathUtils.lerp((float) ActionBarShadowController.ELEVATION_LOW, BurnInHelperKt.getBurnInOffset(this.mMaxBurnInOffsetY * 2, false) - this.mMaxBurnInOffsetY, this.mInterpolatedDarkAmount);
        ((LockIconView) ((ViewController) this).mView).setTranslationX(lerp);
        ((LockIconView) ((ViewController) this).mView).setTranslationY(lerp2);
    }

    public final void updateColors() {
        ((LockIconView) ((ViewController) this).mView).updateColorAndBackgroundVisibility();
    }

    public final void updateConfiguration() {
        Rect bounds = ((WindowManager) getContext().getSystemService(WindowManager.class)).getCurrentWindowMetrics().getBounds();
        this.mWidthPixels = bounds.right;
        this.mHeightPixels = bounds.bottom;
        this.mBottomPaddingPx = getResources().getDimensionPixelSize(R$dimen.lock_icon_margin_bottom);
        this.mDefaultPaddingPx = getResources().getDimensionPixelSize(R$dimen.lock_icon_padding);
        this.mUnlockedLabel = ((LockIconView) ((ViewController) this).mView).getContext().getResources().getString(R$string.accessibility_unlock_button);
        this.mLockedLabel = ((LockIconView) ((ViewController) this).mView).getContext().getResources().getString(R$string.accessibility_lock_icon);
        updateLockIconLocation();
    }

    public final void updateIsUdfpsEnrolled() {
        boolean z = this.mUdfpsSupported;
        boolean z2 = this.mUdfpsEnrolled;
        boolean isUdfpsSupported = this.mKeyguardUpdateMonitor.isUdfpsSupported();
        this.mUdfpsSupported = isUdfpsSupported;
        ((LockIconView) ((ViewController) this).mView).setUseBackground(isUdfpsSupported);
        boolean isUdfpsEnrolled = this.mKeyguardUpdateMonitor.isUdfpsEnrolled();
        this.mUdfpsEnrolled = isUdfpsEnrolled;
        if (z == this.mUdfpsSupported && z2 == isUdfpsEnrolled) {
            return;
        }
        updateVisibility();
    }

    public final void updateKeyguardShowing() {
        this.mIsKeyguardShowing = this.mKeyguardStateController.isShowing() && !this.mKeyguardStateController.isKeyguardGoingAway();
    }

    public final void updateLockIconLocation() {
        float lambda$buildDialog$2 = this.mAuthController.lambda$buildDialog$2();
        int i = (int) (this.mDefaultPaddingPx * lambda$buildDialog$2);
        if (this.mUdfpsSupported) {
            ((LockIconView) ((ViewController) this).mView).setCenterLocation(this.mAuthController.getUdfpsLocation(), this.mAuthController.getUdfpsRadius(), i);
            return;
        }
        LockIconView lockIconView = (LockIconView) ((ViewController) this).mView;
        int i2 = ((int) this.mWidthPixels) / 2;
        float f = this.mHeightPixels;
        int i3 = this.mBottomPaddingPx;
        int i4 = sLockIconRadiusPx;
        lockIconView.setCenterLocation(new Point(i2, (int) (f - ((i3 + i4) * lambda$buildDialog$2))), i4 * lambda$buildDialog$2, i);
    }

    public final void updateUdfpsConfig() {
        this.mExecutor.execute(new Runnable() { // from class: com.android.keyguard.LockIconViewController$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                LockIconViewController.$r8$lambda$j0iuivExaZHbw_xY6zE8jdtH6y8(LockIconViewController.this);
            }
        });
    }

    public final void updateVisibility() {
        Runnable runnable = this.mCancelDelayedUpdateVisibilityRunnable;
        if (runnable != null) {
            runnable.run();
            this.mCancelDelayedUpdateVisibilityRunnable = null;
        }
        if (!this.mIsKeyguardShowing && !this.mIsDozing) {
            ((LockIconView) ((ViewController) this).mView).setVisibility(4);
            return;
        }
        boolean z = (!this.mUdfpsEnrolled || this.mShowUnlockIcon || this.mShowLockIcon || this.mShowAodUnlockedIcon || this.mShowAodLockIcon) ? false : true;
        this.mShowLockIcon = (this.mCanDismissLockScreen || this.mUserUnlockedWithBiometric || !isLockScreen() || (this.mUdfpsEnrolled && this.mRunningFPS)) ? false : true;
        this.mShowUnlockIcon = (this.mCanDismissLockScreen || this.mUserUnlockedWithBiometric) && isLockScreen();
        boolean z2 = this.mIsDozing;
        this.mShowAodUnlockedIcon = z2 && this.mUdfpsEnrolled && !this.mRunningFPS && this.mCanDismissLockScreen;
        this.mShowAodLockIcon = z2 && this.mUdfpsEnrolled && !this.mRunningFPS && !this.mCanDismissLockScreen;
        CharSequence contentDescription = ((LockIconView) ((ViewController) this).mView).getContentDescription();
        if (this.mShowLockIcon) {
            ((LockIconView) ((ViewController) this).mView).updateIcon(0, false);
            ((LockIconView) ((ViewController) this).mView).setContentDescription(this.mLockedLabel);
            ((LockIconView) ((ViewController) this).mView).setVisibility(0);
        } else if (this.mShowUnlockIcon) {
            if (z) {
                ((LockIconView) ((ViewController) this).mView).updateIcon(1, false);
            }
            ((LockIconView) ((ViewController) this).mView).updateIcon(2, false);
            ((LockIconView) ((ViewController) this).mView).setContentDescription(this.mUnlockedLabel);
            ((LockIconView) ((ViewController) this).mView).setVisibility(0);
        } else if (this.mShowAodUnlockedIcon) {
            ((LockIconView) ((ViewController) this).mView).updateIcon(2, true);
            ((LockIconView) ((ViewController) this).mView).setContentDescription(this.mUnlockedLabel);
            ((LockIconView) ((ViewController) this).mView).setVisibility(0);
        } else if (this.mShowAodLockIcon) {
            ((LockIconView) ((ViewController) this).mView).updateIcon(0, true);
            ((LockIconView) ((ViewController) this).mView).setContentDescription(this.mLockedLabel);
            ((LockIconView) ((ViewController) this).mView).setVisibility(0);
        } else {
            ((LockIconView) ((ViewController) this).mView).clearIcon();
            ((LockIconView) ((ViewController) this).mView).setVisibility(4);
            ((LockIconView) ((ViewController) this).mView).setContentDescription(null);
        }
        if (Objects.equals(contentDescription, ((LockIconView) ((ViewController) this).mView).getContentDescription()) || ((LockIconView) ((ViewController) this).mView).getContentDescription() == null) {
            return;
        }
        View view = ((ViewController) this).mView;
        ((LockIconView) view).announceForAccessibility(((LockIconView) view).getContentDescription());
    }
}