package com.android.systemui.biometrics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.graphics.Point;
import android.hardware.biometrics.BiometricFingerprintConstants;
import android.hardware.display.AmbientDisplayConfiguration;
import android.hardware.display.DisplayManager;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.hardware.fingerprint.IUdfpsOverlayController;
import android.hardware.fingerprint.IUdfpsOverlayControllerCallback;
import android.net.Uri;
import android.os.Handler;
import android.os.PowerManager;
import android.os.Process;
import android.os.Trace;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.util.Log;
import android.util.RotationUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.LatencyTracker;
import com.android.internal.util.Preconditions;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.Dumpable;
import com.android.systemui.R$integer;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.biometrics.BiometricDisplayListener;
import com.android.systemui.biometrics.UdfpsController;
import com.android.systemui.biometrics.udfps.InteractionEvent;
import com.android.systemui.biometrics.udfps.NormalizedTouchData;
import com.android.systemui.biometrics.udfps.SinglePointerTouchProcessor;
import com.android.systemui.biometrics.udfps.TouchProcessor;
import com.android.systemui.biometrics.udfps.TouchProcessorResult;
import com.android.systemui.doze.DozeReceiver;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Function;
import javax.inject.Provider;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsController.class */
public class UdfpsController implements DozeReceiver, Dumpable {
    public final AccessibilityManager mAccessibilityManager;
    public boolean mAcquiredReceived;
    public final ActivityLaunchAnimator mActivityLaunchAnimator;
    public final AlternateUdfpsTouchProvider mAlternateTouchProvider;
    public final AmbientDisplayConfiguration mAmbientDisplayConfiguration;
    public Runnable mAodInterruptRunnable;
    public boolean mAttemptedToDismissKeyguard;
    public Runnable mAuthControllerUpdateUdfpsLocation;
    public final Executor mBiometricExecutor;
    public final BroadcastReceiver mBroadcastReceiver;
    public Runnable mCancelAodFingerUpAction;
    public final ConfigurationController mConfigurationController;
    public final Context mContext;
    public final SystemUIDialogManager mDialogManager;
    public final DumpManager mDumpManager;
    public final Execution mExecution;
    public final FalsingManager mFalsingManager;
    public final FeatureFlags mFeatureFlags;
    public final DelayableExecutor mFgExecutor;
    public final FingerprintManager mFingerprintManager;
    public final LayoutInflater mInflater;
    public boolean mIsAodInterruptActive;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final StatusBarKeyguardViewManager mKeyguardViewManager;
    public final LatencyTracker mLatencyTracker;
    public final LockscreenShadeTransitionController mLockscreenShadeTransitionController;
    public boolean mOnFingerDown;
    @VisibleForTesting
    public final BiometricDisplayListener mOrientationListener;
    public UdfpsControllerOverlay mOverlay;
    public final PowerManager mPowerManager;
    public final PrimaryBouncerInteractor mPrimaryBouncerInteractor;
    public final ScreenLifecycle.Observer mScreenObserver;
    public boolean mScreenOffFod;
    public boolean mScreenOn;
    public final SecureSettings mSecureSettings;
    @VisibleForTesting
    public FingerprintSensorPropertiesInternal mSensorProps;
    public final ShadeExpansionStateManager mShadeExpansionStateManager;
    public final StatusBarStateController mStatusBarStateController;
    public final SystemClock mSystemClock;
    public long mTouchLogTime;
    public final TouchProcessor mTouchProcessor;
    public UdfpsDisplayModeProvider mUdfpsDisplayMode;
    public final int mUdfpsVendorCode;
    public final UnlockedScreenOffAnimationController mUnlockedScreenOffAnimationController;
    public VelocityTracker mVelocityTracker;
    public final VibratorHelper mVibrator;
    public final WindowManager mWindowManager;
    @VisibleForTesting
    public static final VibrationAttributes UDFPS_VIBRATION_ATTRIBUTES = new VibrationAttributes.Builder().setUsage(65).build();
    @VisibleForTesting
    public static final VibrationAttributes LOCK_ICON_VIBRATION_ATTRIBUTES = new VibrationAttributes.Builder().setUsage(18).build();
    public static final VibrationEffect EFFECT_CLICK = VibrationEffect.get(0);
    @VisibleForTesting
    public UdfpsOverlayParams mOverlayParams = new UdfpsOverlayParams();
    public int mActivePointerId = -1;
    public final Set<Callback> mCallbacks = new HashSet();

    /* renamed from: com.android.systemui.biometrics.UdfpsController$4 */
    /* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsController$4.class */
    public static /* synthetic */ class AnonymousClass4 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$biometrics$udfps$InteractionEvent;

        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:31:0x002b -> B:37:0x0014). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:33:0x002f -> B:41:0x001f). Please submit an issue!!! */
        static {
            int[] iArr = new int[InteractionEvent.values().length];
            $SwitchMap$com$android$systemui$biometrics$udfps$InteractionEvent = iArr;
            try {
                iArr[InteractionEvent.DOWN.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$android$systemui$biometrics$udfps$InteractionEvent[InteractionEvent.UP.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$android$systemui$biometrics$udfps$InteractionEvent[InteractionEvent.CANCEL.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsController$Callback.class */
    public interface Callback {
        void onFingerDown();

        void onFingerUp();
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsController$UdfpsOverlayController.class */
    public class UdfpsOverlayController extends IUdfpsOverlayController.Stub {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda1.run():void] */
        public static /* synthetic */ void $r8$lambda$1cG3RI2ubmSNkIWa8q0J0H4hT_U(UdfpsOverlayController udfpsOverlayController, String str) {
            udfpsOverlayController.lambda$setDebugMessage$6(str);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda3.run():void] */
        public static /* synthetic */ void $r8$lambda$75m8iMIiAQoUGBLKawH2LZxXD98(UdfpsOverlayController udfpsOverlayController, int i) {
            udfpsOverlayController.lambda$onEnrollmentProgress$4(i);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda2.run():void] */
        /* renamed from: $r8$lambda$Kn-5w4wJvXdwNaD7BW05UbIsnr0 */
        public static /* synthetic */ void m1589$r8$lambda$Kn5w4wJvXdwNaD7BW05UbIsnr0(UdfpsOverlayController udfpsOverlayController, long j, int i, IUdfpsOverlayControllerCallback iUdfpsOverlayControllerCallback) {
            udfpsOverlayController.lambda$showUdfpsOverlay$1(j, i, iUdfpsOverlayControllerCallback);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda0.run():void] */
        /* renamed from: $r8$lambda$PwXeW0_4maP-gXQdrE_K-AWx4dc */
        public static /* synthetic */ void m1590$r8$lambda$PwXeW0_4maPgXQdrE_KAWx4dc(UdfpsOverlayController udfpsOverlayController) {
            udfpsOverlayController.lambda$hideUdfpsOverlay$2();
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda6.invoke(java.lang.Object, java.lang.Object, java.lang.Object):java.lang.Object] */
        public static /* synthetic */ Boolean $r8$lambda$lG2XaZ1a1XdyICwnxDZ2qOjLmf0(UdfpsOverlayController udfpsOverlayController, long j, View view, MotionEvent motionEvent, Boolean bool) {
            return udfpsOverlayController.lambda$showUdfpsOverlay$0(j, view, motionEvent, bool);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda4.run():void] */
        public static /* synthetic */ void $r8$lambda$mVXvFjFMoQNvmLkMjG8zFL22xpU(UdfpsOverlayController udfpsOverlayController, int i, int i2, boolean z) {
            udfpsOverlayController.lambda$onAcquired$3(i, i2, z);
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda5.run():void] */
        public static /* synthetic */ void $r8$lambda$myWRiKsb67bgv4j3Vwn5lUX_7bg(UdfpsOverlayController udfpsOverlayController) {
            udfpsOverlayController.lambda$onEnrollmentHelp$5();
        }

        public UdfpsOverlayController() {
            UdfpsController.this = r4;
        }

        public /* synthetic */ void lambda$hideUdfpsOverlay$2() {
            if (UdfpsController.this.mKeyguardUpdateMonitor.isFingerprintDetectionRunning()) {
                Log.d("UdfpsController", "hiding udfps overlay when mKeyguardUpdateMonitor.isFingerprintDetectionRunning()=true");
            }
            UdfpsController.this.hideUdfpsOverlay();
        }

        public /* synthetic */ void lambda$onAcquired$3(int i, int i2, boolean z) {
            UdfpsController udfpsController = UdfpsController.this;
            if (udfpsController.mOverlay == null) {
                Log.e("UdfpsController", "Null request when onAcquired for sensorId: " + i + " acquiredInfo=" + i2);
                return;
            }
            udfpsController.mAcquiredReceived = true;
            UdfpsView overlayView = UdfpsController.this.mOverlay.getOverlayView();
            if (overlayView != null && UdfpsController.this.isOptical()) {
                UdfpsController.this.unconfigureDisplay(overlayView);
            }
            UdfpsController.this.tryAodSendFingerUp();
            if (z) {
                UdfpsController.this.mOverlay.onAcquiredGood();
            }
        }

        public /* synthetic */ void lambda$onEnrollmentHelp$5() {
            UdfpsControllerOverlay udfpsControllerOverlay = UdfpsController.this.mOverlay;
            if (udfpsControllerOverlay == null) {
                Log.e("UdfpsController", "onEnrollmentHelp received but serverRequest is null");
            } else {
                udfpsControllerOverlay.onEnrollmentHelp();
            }
        }

        public /* synthetic */ void lambda$onEnrollmentProgress$4(int i) {
            UdfpsControllerOverlay udfpsControllerOverlay = UdfpsController.this.mOverlay;
            if (udfpsControllerOverlay == null) {
                Log.e("UdfpsController", "onEnrollProgress received but serverRequest is null");
            } else {
                udfpsControllerOverlay.onEnrollmentProgress(i);
            }
        }

        public /* synthetic */ void lambda$setDebugMessage$6(String str) {
            UdfpsControllerOverlay udfpsControllerOverlay = UdfpsController.this.mOverlay;
            if (udfpsControllerOverlay == null || udfpsControllerOverlay.isHiding()) {
                return;
            }
            UdfpsController.this.mOverlay.getOverlayView().setDebugMessage(str);
        }

        public /* synthetic */ Boolean lambda$showUdfpsOverlay$0(long j, View view, MotionEvent motionEvent, Boolean bool) {
            return Boolean.valueOf(UdfpsController.this.onTouch(j, motionEvent, bool.booleanValue()));
        }

        public /* synthetic */ void lambda$showUdfpsOverlay$1(final long j, int i, IUdfpsOverlayControllerCallback iUdfpsOverlayControllerCallback) {
            UdfpsController udfpsController = UdfpsController.this;
            udfpsController.showUdfpsOverlay(new UdfpsControllerOverlay(udfpsController.mContext, UdfpsController.this.mFingerprintManager, UdfpsController.this.mInflater, UdfpsController.this.mWindowManager, UdfpsController.this.mAccessibilityManager, UdfpsController.this.mStatusBarStateController, UdfpsController.this.mShadeExpansionStateManager, UdfpsController.this.mKeyguardViewManager, UdfpsController.this.mKeyguardUpdateMonitor, UdfpsController.this.mDialogManager, UdfpsController.this.mDumpManager, UdfpsController.this.mLockscreenShadeTransitionController, UdfpsController.this.mConfigurationController, UdfpsController.this.mSystemClock, UdfpsController.this.mKeyguardStateController, UdfpsController.this.mUnlockedScreenOffAnimationController, UdfpsController.this.mUdfpsDisplayMode, j, i, iUdfpsOverlayControllerCallback, new Function3() { // from class: com.android.systemui.biometrics.UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda6
                public final Object invoke(Object obj, Object obj2, Object obj3) {
                    return UdfpsController.UdfpsOverlayController.$r8$lambda$lG2XaZ1a1XdyICwnxDZ2qOjLmf0(UdfpsController.UdfpsOverlayController.this, j, (View) obj, (MotionEvent) obj2, (Boolean) obj3);
                }
            }, UdfpsController.this.mActivityLaunchAnimator, UdfpsController.this.mFeatureFlags, UdfpsController.this.mPrimaryBouncerInteractor));
        }

        public void hideUdfpsOverlay(int i) {
            UdfpsController.this.mFgExecutor.execute(new Runnable() { // from class: com.android.systemui.biometrics.UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    UdfpsController.UdfpsOverlayController.m1590$r8$lambda$PwXeW0_4maPgXQdrE_KAWx4dc(UdfpsController.UdfpsOverlayController.this);
                }
            });
        }

        public void onAcquired(final int i, final int i2, int i3) {
            boolean z = true;
            if (BiometricFingerprintConstants.shouldDisableUdfpsDisplayMode(i2)) {
                if (i2 != 0) {
                    z = false;
                }
                final boolean z2 = z;
                UdfpsController.this.mFgExecutor.execute(new Runnable() { // from class: com.android.systemui.biometrics.UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        UdfpsController.UdfpsOverlayController.$r8$lambda$mVXvFjFMoQNvmLkMjG8zFL22xpU(UdfpsController.UdfpsOverlayController.this, i, i2, z2);
                    }
                });
                return;
            }
            boolean z3 = i2 == 6;
            boolean alwaysOnEnabled = UdfpsController.this.mAmbientDisplayConfiguration.alwaysOnEnabled(-2);
            boolean z4 = UdfpsController.this.mStatusBarStateController.isDozing() && UdfpsController.this.mScreenOn;
            if (z3) {
                if (((!UdfpsController.this.mScreenOffFod || UdfpsController.this.mScreenOn) && !(alwaysOnEnabled && z4)) || i3 != UdfpsController.this.mUdfpsVendorCode) {
                    return;
                }
                UdfpsController.this.mPowerManager.wakeUp(UdfpsController.this.mSystemClock.uptimeMillis(), 4, "UdfpsController");
                UdfpsController.this.onAodInterrupt(0, 0, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW);
            }
        }

        public void onEnrollmentHelp(int i) {
            UdfpsController.this.mFgExecutor.execute(new Runnable() { // from class: com.android.systemui.biometrics.UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda5
                @Override // java.lang.Runnable
                public final void run() {
                    UdfpsController.UdfpsOverlayController.$r8$lambda$myWRiKsb67bgv4j3Vwn5lUX_7bg(UdfpsController.UdfpsOverlayController.this);
                }
            });
        }

        public void onEnrollmentProgress(int i, final int i2) {
            UdfpsController.this.mFgExecutor.execute(new Runnable() { // from class: com.android.systemui.biometrics.UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda3
                @Override // java.lang.Runnable
                public final void run() {
                    UdfpsController.UdfpsOverlayController.$r8$lambda$75m8iMIiAQoUGBLKawH2LZxXD98(UdfpsController.UdfpsOverlayController.this, i2);
                }
            });
        }

        public void setDebugMessage(int i, final String str) {
            UdfpsController.this.mFgExecutor.execute(new Runnable() { // from class: com.android.systemui.biometrics.UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    UdfpsController.UdfpsOverlayController.$r8$lambda$1cG3RI2ubmSNkIWa8q0J0H4hT_U(UdfpsController.UdfpsOverlayController.this, str);
                }
            });
        }

        public void showUdfpsOverlay(final long j, int i, final int i2, final IUdfpsOverlayControllerCallback iUdfpsOverlayControllerCallback) {
            UdfpsController.this.mFgExecutor.execute(new Runnable() { // from class: com.android.systemui.biometrics.UdfpsController$UdfpsOverlayController$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    UdfpsController.UdfpsOverlayController.m1589$r8$lambda$Kn5w4wJvXdwNaD7BW05UbIsnr0(UdfpsController.UdfpsOverlayController.this, j, i2, iUdfpsOverlayControllerCallback);
                }
            });
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsController$$ExternalSyntheticLambda3.run():void] */
    public static /* synthetic */ void $r8$lambda$1gpiS_aphz9rcLmLzW4qvOfmE68(UdfpsController udfpsController, long j) {
        udfpsController.lambda$onFingerUp$7(j);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsController$$ExternalSyntheticLambda2.invoke():java.lang.Object] */
    public static /* synthetic */ Unit $r8$lambda$Dy8sE632DfsIKKc8fP9SJQmODGw(UdfpsController udfpsController) {
        return udfpsController.lambda$new$1();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsController$$ExternalSyntheticLambda4.run():void] */
    public static /* synthetic */ void $r8$lambda$Ek9oR0gDkNJ9N8Bl9YLu5rLTHNs(UdfpsController udfpsController, long j) {
        udfpsController.lambda$onFingerUp$8(j);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsController$$ExternalSyntheticLambda6.run():void] */
    public static /* synthetic */ void $r8$lambda$LRBgrJmT5dMk71wUD0VPKaMtvsI(UdfpsController udfpsController, long j) {
        udfpsController.lambda$onFingerDown$4(j);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsController$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$U1bYbtIAKMHSDYTmplaaKWpnjm4(UdfpsController udfpsController, Point point) {
        udfpsController.lambda$oldOnTouch$0(point);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsController$$ExternalSyntheticLambda7.run():void] */
    public static /* synthetic */ void $r8$lambda$WERdWA8o71izTk1nzd9_YDsZjT4(UdfpsController udfpsController, long j) {
        udfpsController.lambda$onFingerDown$6(j);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsController$$ExternalSyntheticLambda9.run():void] */
    public static /* synthetic */ void $r8$lambda$ZrEcczuBva_FV7Agk55jhztpyQM(UdfpsController udfpsController, long j, int i, int i2, float f, float f2) {
        udfpsController.lambda$onAodInterrupt$2(j, i, i2, f, f2);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsController$$ExternalSyntheticLambda5.run():void] */
    public static /* synthetic */ void $r8$lambda$s7tV045St_891RqzOjmwvqVPDBw(UdfpsController udfpsController, long j, float f, float f2, float f3, float f4) {
        udfpsController.lambda$onFingerDown$3(j, f, f2, f3, f4);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsController$$ExternalSyntheticLambda8.run():void] */
    /* renamed from: $r8$lambda$zr7gLvj-3y7jXN2vEL9zkfbh12U */
    public static /* synthetic */ void m1551$r8$lambda$zr7gLvj3y7jXN2vEL9zkfbh12U(UdfpsController udfpsController) {
        udfpsController.lambda$onFingerDown$5();
    }

    public UdfpsController(Context context, Execution execution, LayoutInflater layoutInflater, FingerprintManager fingerprintManager, WindowManager windowManager, StatusBarStateController statusBarStateController, DelayableExecutor delayableExecutor, ShadeExpansionStateManager shadeExpansionStateManager, StatusBarKeyguardViewManager statusBarKeyguardViewManager, DumpManager dumpManager, KeyguardUpdateMonitor keyguardUpdateMonitor, FeatureFlags featureFlags, FalsingManager falsingManager, PowerManager powerManager, AccessibilityManager accessibilityManager, LockscreenShadeTransitionController lockscreenShadeTransitionController, ScreenLifecycle screenLifecycle, VibratorHelper vibratorHelper, UdfpsHapticsSimulator udfpsHapticsSimulator, UdfpsShell udfpsShell, KeyguardStateController keyguardStateController, DisplayManager displayManager, Handler handler, ConfigurationController configurationController, SystemClock systemClock, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, SystemUIDialogManager systemUIDialogManager, LatencyTracker latencyTracker, ActivityLaunchAnimator activityLaunchAnimator, Optional<Provider<AlternateUdfpsTouchProvider>> optional, Executor executor, PrimaryBouncerInteractor primaryBouncerInteractor, SinglePointerTouchProcessor singlePointerTouchProcessor, SecureSettings secureSettings) {
        ScreenLifecycle.Observer observer = new ScreenLifecycle.Observer() { // from class: com.android.systemui.biometrics.UdfpsController.1
            {
                UdfpsController.this = this;
            }

            @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
            public void onScreenTurnedOff() {
                UdfpsController.this.mScreenOn = false;
            }

            @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
            public void onScreenTurnedOn() {
                UdfpsController.this.mScreenOn = true;
                if (UdfpsController.this.mAodInterruptRunnable != null) {
                    UdfpsController.this.mAodInterruptRunnable.run();
                    UdfpsController.this.mAodInterruptRunnable = null;
                }
            }
        };
        this.mScreenObserver = observer;
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.biometrics.UdfpsController.2
            {
                UdfpsController.this = this;
            }

            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context2, Intent intent) {
                UdfpsControllerOverlay udfpsControllerOverlay = UdfpsController.this.mOverlay;
                if (udfpsControllerOverlay == null || udfpsControllerOverlay.getRequestReason() == 4 || !"android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(intent.getAction())) {
                    return;
                }
                String stringExtra = intent.getStringExtra("reason");
                if (stringExtra == null) {
                    stringExtra = "unknown";
                }
                Log.d("UdfpsController", "ACTION_CLOSE_SYSTEM_DIALOGS received, reason: " + stringExtra + ", mRequestReason: " + UdfpsController.this.mOverlay.getRequestReason());
                UdfpsController.this.mOverlay.cancel();
                UdfpsController.this.hideUdfpsOverlay();
            }
        };
        this.mBroadcastReceiver = broadcastReceiver;
        this.mContext = context;
        this.mExecution = execution;
        this.mVibrator = vibratorHelper;
        this.mInflater = layoutInflater;
        FingerprintManager fingerprintManager2 = (FingerprintManager) Preconditions.checkNotNull(fingerprintManager);
        this.mFingerprintManager = fingerprintManager2;
        this.mWindowManager = windowManager;
        this.mFgExecutor = delayableExecutor;
        this.mShadeExpansionStateManager = shadeExpansionStateManager;
        this.mStatusBarStateController = statusBarStateController;
        this.mKeyguardStateController = keyguardStateController;
        this.mKeyguardViewManager = statusBarKeyguardViewManager;
        this.mDumpManager = dumpManager;
        this.mDialogManager = systemUIDialogManager;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mFeatureFlags = featureFlags;
        this.mFalsingManager = falsingManager;
        this.mPowerManager = powerManager;
        this.mAccessibilityManager = accessibilityManager;
        this.mLockscreenShadeTransitionController = lockscreenShadeTransitionController;
        screenLifecycle.addObserver(observer);
        this.mScreenOn = screenLifecycle.getScreenState() == 2;
        this.mConfigurationController = configurationController;
        this.mSystemClock = systemClock;
        this.mUnlockedScreenOffAnimationController = unlockedScreenOffAnimationController;
        this.mLatencyTracker = latencyTracker;
        this.mActivityLaunchAnimator = activityLaunchAnimator;
        SinglePointerTouchProcessor singlePointerTouchProcessor2 = null;
        this.mAlternateTouchProvider = (AlternateUdfpsTouchProvider) optional.map(new Function() { // from class: com.android.systemui.biometrics.UdfpsController$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (AlternateUdfpsTouchProvider) ((Provider) obj).get();
            }
        }).orElse(null);
        this.mSensorProps = new FingerprintSensorPropertiesInternal(-1, 0, 0, new ArrayList(), 0, false);
        this.mBiometricExecutor = executor;
        this.mPrimaryBouncerInteractor = primaryBouncerInteractor;
        this.mTouchProcessor = featureFlags.isEnabled(Flags.UDFPS_NEW_TOUCH_DETECTION) ? singlePointerTouchProcessor : singlePointerTouchProcessor2;
        dumpManager.registerDumpable("UdfpsController", this);
        this.mOrientationListener = new BiometricDisplayListener(context, displayManager, handler, BiometricDisplayListener.SensorType.UnderDisplayFingerprint.INSTANCE, new Function0() { // from class: com.android.systemui.biometrics.UdfpsController$$ExternalSyntheticLambda2
            public final Object invoke() {
                return UdfpsController.$r8$lambda$Dy8sE632DfsIKKc8fP9SJQmODGw(UdfpsController.this);
            }
        });
        UdfpsOverlayController udfpsOverlayController = new UdfpsOverlayController();
        fingerprintManager2.setUdfpsOverlayController(udfpsOverlayController);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
        context.registerReceiver(broadcastReceiver, intentFilter, 2);
        udfpsHapticsSimulator.setUdfpsController(this);
        udfpsShell.setUdfpsOverlayController(udfpsOverlayController);
        this.mUdfpsVendorCode = context.getResources().getInteger(R$integer.config_udfps_vendor_code);
        this.mAmbientDisplayConfiguration = new AmbientDisplayConfiguration(context);
        this.mSecureSettings = secureSettings;
        updateScreenOffFodState();
        secureSettings.registerContentObserver("screen_off_udfps_enabled", new ContentObserver(handler) { // from class: com.android.systemui.biometrics.UdfpsController.3
            {
                UdfpsController.this = this;
            }

            @Override // android.database.ContentObserver
            public void onChange(boolean z, Uri uri) {
                if (uri.getLastPathSegment().equals("screen_off_udfps_enabled")) {
                    UdfpsController.this.updateScreenOffFodState();
                }
            }
        });
    }

    public static float computePointerSpeed(VelocityTracker velocityTracker, int i) {
        return (float) Math.sqrt(Math.pow(velocityTracker.getXVelocity(i), 2.0d) + Math.pow(velocityTracker.getYVelocity(i), 2.0d));
    }

    public static boolean exceedsVelocityThreshold(float f) {
        return f > 750.0f;
    }

    public /* synthetic */ Unit lambda$new$1() {
        Runnable runnable = this.mAuthControllerUpdateUdfpsLocation;
        if (runnable != null) {
            runnable.run();
        }
        return Unit.INSTANCE;
    }

    public /* synthetic */ void lambda$oldOnTouch$0(Point point) {
        if (this.mOverlay == null) {
            Log.e("UdfpsController", "touch outside sensor area receivedbut serverRequest is null");
            return;
        }
        float scaleFactor = this.mOverlayParams.getScaleFactor();
        this.mOverlay.onTouchOutsideOfSensorArea(point.x, point.y, this.mOverlayParams.getSensorBounds().centerX() / scaleFactor, this.mOverlayParams.getSensorBounds().centerY() / scaleFactor, this.mOverlayParams.getRotation());
    }

    public /* synthetic */ void lambda$onAodInterrupt$2(long j, int i, int i2, float f, float f2) {
        this.mIsAodInterruptActive = true;
        this.mCancelAodFingerUpAction = this.mFgExecutor.executeDelayed(new Runnable() { // from class: com.android.systemui.biometrics.UdfpsController$$ExternalSyntheticLambda10
            @Override // java.lang.Runnable
            public final void run() {
                UdfpsController.this.tryAodSendFingerUp();
            }
        }, 1000L);
        onFingerDown(j, i, i2, f, f2);
    }

    public /* synthetic */ void lambda$onFingerDown$3(long j, float f, float f2, float f3, float f4) {
        this.mAlternateTouchProvider.onPointerDown(j, (int) f, (int) f2, f3, f4);
    }

    public /* synthetic */ void lambda$onFingerDown$4(long j) {
        if (this.mKeyguardUpdateMonitor.isFingerprintDetectionRunning()) {
            this.mKeyguardUpdateMonitor.onUdfpsPointerDown((int) j);
        }
    }

    public /* synthetic */ void lambda$onFingerDown$5() {
        this.mAlternateTouchProvider.onUiReady();
        this.mLatencyTracker.onActionEnd(14);
    }

    public /* synthetic */ void lambda$onFingerDown$6(long j) {
        if (this.mAlternateTouchProvider != null) {
            this.mBiometricExecutor.execute(new Runnable() { // from class: com.android.systemui.biometrics.UdfpsController$$ExternalSyntheticLambda8
                @Override // java.lang.Runnable
                public final void run() {
                    UdfpsController.m1551$r8$lambda$zr7gLvj3y7jXN2vEL9zkfbh12U(UdfpsController.this);
                }
            });
            return;
        }
        this.mFingerprintManager.onUiReady(j, this.mSensorProps.sensorId);
        this.mLatencyTracker.onActionEnd(14);
    }

    public /* synthetic */ void lambda$onFingerUp$7(long j) {
        this.mAlternateTouchProvider.onPointerUp(j);
    }

    public /* synthetic */ void lambda$onFingerUp$8(long j) {
        if (this.mKeyguardUpdateMonitor.isFingerprintDetectionRunning()) {
            this.mKeyguardUpdateMonitor.onUdfpsPointerUp((int) j);
        }
    }

    public void addCallback(Callback callback) {
        this.mCallbacks.add(callback);
    }

    @VisibleForTesting
    public void cancelAodSendFingerUpAction() {
        this.mIsAodInterruptActive = false;
        Runnable runnable = this.mCancelAodFingerUpAction;
        if (runnable != null) {
            runnable.run();
            this.mCancelAodFingerUpAction = null;
        }
    }

    @Override // com.android.systemui.doze.DozeReceiver
    public void dozeTimeTick() {
        UdfpsView overlayView;
        UdfpsControllerOverlay udfpsControllerOverlay = this.mOverlay;
        if (udfpsControllerOverlay == null || (overlayView = udfpsControllerOverlay.getOverlayView()) == null) {
            return;
        }
        overlayView.dozeTimeTick();
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("mSensorProps=(" + this.mSensorProps + ")");
    }

    public final Point getTouchInNativeCoordinates(MotionEvent motionEvent, int i) {
        Point point = new Point((int) motionEvent.getRawX(i), (int) motionEvent.getRawY(i));
        int rotation = this.mOverlayParams.getRotation();
        if (rotation == 1 || rotation == 3) {
            RotationUtils.rotatePoint(point, RotationUtils.deltaRotation(rotation, 0), this.mOverlayParams.getLogicalDisplayWidth(), this.mOverlayParams.getLogicalDisplayHeight());
        }
        float scaleFactor = this.mOverlayParams.getScaleFactor();
        point.x = (int) (point.x / scaleFactor);
        point.y = (int) (point.y / scaleFactor);
        return point;
    }

    public final void hideUdfpsOverlay() {
        this.mExecution.assertIsMainThread();
        UdfpsControllerOverlay udfpsControllerOverlay = this.mOverlay;
        if (udfpsControllerOverlay != null) {
            UdfpsView overlayView = udfpsControllerOverlay.getOverlayView();
            if (overlayView != null) {
                onFingerUp(this.mOverlay.getRequestId(), overlayView);
            }
            boolean hide = this.mOverlay.hide();
            if (this.mKeyguardViewManager.isShowingAlternateBouncer()) {
                this.mKeyguardViewManager.hideAlternateBouncer(true);
            }
            Log.v("UdfpsController", "hideUdfpsOverlay | removing window: " + hide);
        } else {
            Log.v("UdfpsController", "hideUdfpsOverlay | the overlay is already hidden");
        }
        this.mOverlay = null;
        this.mOrientationListener.disable();
    }

    public boolean isFingerDown() {
        return this.mOnFingerDown;
    }

    public final boolean isOptical() {
        return this.mSensorProps.sensorType == 3;
    }

    public final boolean isWithinSensorArea(UdfpsView udfpsView, float f, float f2, boolean z) {
        if (z) {
            return udfpsView.isWithinSensorArea(f, f2);
        }
        UdfpsControllerOverlay udfpsControllerOverlay = this.mOverlay;
        boolean z2 = false;
        if (udfpsControllerOverlay != null) {
            if (udfpsControllerOverlay.getAnimationViewController() == null) {
                z2 = false;
            } else {
                z2 = false;
                if (!this.mOverlay.getAnimationViewController().shouldPauseAuth()) {
                    z2 = false;
                    if (this.mOverlayParams.getSensorBounds().contains((int) f, (int) f2)) {
                        z2 = true;
                    }
                }
            }
        }
        return z2;
    }

    public final boolean newOnTouch(long j, MotionEvent motionEvent, boolean z) {
        if (!z) {
            Log.e("UdfpsController", "ignoring the touch injected from outside of UdfpsView");
            return false;
        }
        UdfpsControllerOverlay udfpsControllerOverlay = this.mOverlay;
        if (udfpsControllerOverlay == null) {
            Log.w("UdfpsController", "ignoring onTouch with null overlay");
            return false;
        } else if (!udfpsControllerOverlay.matchesRequestId(j)) {
            Log.w("UdfpsController", "ignoring stale touch event: " + j + " current: " + this.mOverlay.getRequestId());
            return false;
        } else {
            TouchProcessorResult processTouch = this.mTouchProcessor.processTouch(motionEvent, this.mActivePointerId, this.mOverlayParams);
            if (processTouch instanceof TouchProcessorResult.Failure) {
                Log.w("UdfpsController", ((TouchProcessorResult.Failure) processTouch).getReason());
                return false;
            }
            TouchProcessorResult.ProcessedTouch processedTouch = (TouchProcessorResult.ProcessedTouch) processTouch;
            NormalizedTouchData touchData = processedTouch.getTouchData();
            this.mActivePointerId = processedTouch.getPointerOnSensorId();
            int i = AnonymousClass4.$SwitchMap$com$android$systemui$biometrics$udfps$InteractionEvent[processedTouch.getEvent().ordinal()];
            if (i == 1) {
                if (shouldTryToDismissKeyguard()) {
                    tryDismissingKeyguard();
                }
                onFingerDown(j, touchData.getPointerId(), touchData.getX(), touchData.getY(), touchData.getMinor(), touchData.getMajor(), touchData.getOrientation(), touchData.getTime(), touchData.getGestureStart(), this.mStatusBarStateController.isDozing());
            } else if (i == 2 || i == 3) {
                if (InteractionEvent.CANCEL.equals(processedTouch.getEvent())) {
                    Log.w("UdfpsController", "This is a CANCEL event that's reported as an UP event!");
                }
                this.mAttemptedToDismissKeyguard = false;
                onFingerUp(j, this.mOverlay.getOverlayView(), touchData.getPointerId(), touchData.getX(), touchData.getY(), touchData.getMinor(), touchData.getMajor(), touchData.getOrientation(), touchData.getTime(), touchData.getGestureStart(), this.mStatusBarStateController.isDozing());
                this.mFalsingManager.isFalseTouch(13);
            }
            return processedTouch.getTouchData().isWithinSensor(this.mOverlayParams.getNativeSensorBounds());
        }
    }

    public final boolean oldOnTouch(long j, MotionEvent motionEvent, boolean z) {
        UdfpsControllerOverlay udfpsControllerOverlay = this.mOverlay;
        boolean z2 = false;
        if (udfpsControllerOverlay == null) {
            Log.w("UdfpsController", "ignoring onTouch with null overlay");
            return false;
        } else if (!udfpsControllerOverlay.matchesRequestId(j)) {
            Log.w("UdfpsController", "ignoring stale touch event: " + j + " current: " + this.mOverlay.getRequestId());
            return false;
        } else {
            UdfpsView overlayView = this.mOverlay.getOverlayView();
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked != 0) {
                if (actionMasked != 1) {
                    if (actionMasked != 2) {
                        if (actionMasked != 3) {
                            if (actionMasked == 4) {
                                overlayView.onTouchOutsideView();
                                return true;
                            } else if (actionMasked != 7) {
                                if (actionMasked != 9) {
                                    if (actionMasked != 10) {
                                        z2 = false;
                                        return z2;
                                    }
                                }
                            }
                        }
                    }
                    Trace.beginSection("UdfpsController.onTouch.ACTION_MOVE");
                    int i = this.mActivePointerId;
                    int pointerId = i == -1 ? motionEvent.getPointerId(0) : motionEvent.findPointerIndex(i);
                    z2 = false;
                    if (pointerId == motionEvent.getActionIndex()) {
                        boolean isWithinSensorArea = isWithinSensorArea(overlayView, motionEvent.getX(pointerId), motionEvent.getY(pointerId), z);
                        if ((z || isWithinSensorArea) && shouldTryToDismissKeyguard()) {
                            Log.v("UdfpsController", "onTouch | dismiss keyguard ACTION_MOVE");
                            tryDismissingKeyguard();
                            z2 = false;
                            return z2;
                        }
                        final Point touchInNativeCoordinates = getTouchInNativeCoordinates(motionEvent, pointerId);
                        if (isWithinSensorArea) {
                            if (this.mVelocityTracker == null) {
                                this.mVelocityTracker = VelocityTracker.obtain();
                            }
                            this.mVelocityTracker.addMovement(motionEvent);
                            this.mVelocityTracker.computeCurrentVelocity(1000);
                            float computePointerSpeed = computePointerSpeed(this.mVelocityTracker, this.mActivePointerId);
                            float touchMinor = motionEvent.getTouchMinor(pointerId);
                            float touchMajor = motionEvent.getTouchMajor(pointerId);
                            boolean exceedsVelocityThreshold = exceedsVelocityThreshold(computePointerSpeed);
                            String format = String.format("minor: %.1f, major: %.1f, v: %.1f, exceedsVelocityThreshold: %b", Float.valueOf(touchMinor), Float.valueOf(touchMajor), Float.valueOf(computePointerSpeed), Boolean.valueOf(exceedsVelocityThreshold));
                            long elapsedRealtime = this.mSystemClock.elapsedRealtime();
                            long j2 = this.mTouchLogTime;
                            if (this.mOnFingerDown || this.mAcquiredReceived || exceedsVelocityThreshold) {
                                z2 = false;
                                if (elapsedRealtime - j2 >= 50) {
                                    Log.v("UdfpsController", "onTouch | finger move: " + format);
                                    this.mTouchLogTime = this.mSystemClock.elapsedRealtime();
                                    z2 = false;
                                }
                            } else {
                                float scaleFactor = this.mOverlayParams.getScaleFactor();
                                onFingerDown(j, touchInNativeCoordinates.x, touchInNativeCoordinates.y, touchMinor / scaleFactor, touchMajor / scaleFactor);
                                Log.v("UdfpsController", "onTouch | finger down: " + format);
                                this.mTouchLogTime = this.mSystemClock.elapsedRealtime();
                                z2 = true;
                            }
                        } else {
                            Log.v("UdfpsController", "onTouch | finger outside");
                            onFingerUp(j, overlayView);
                            this.mFgExecutor.execute(new Runnable() { // from class: com.android.systemui.biometrics.UdfpsController$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    UdfpsController.$r8$lambda$U1bYbtIAKMHSDYTmplaaKWpnjm4(UdfpsController.this, touchInNativeCoordinates);
                                }
                            });
                            z2 = false;
                        }
                    }
                    Trace.endSection();
                    return z2;
                }
                Trace.beginSection("UdfpsController.onTouch.ACTION_UP");
                this.mActivePointerId = -1;
                VelocityTracker velocityTracker = this.mVelocityTracker;
                if (velocityTracker != null) {
                    velocityTracker.recycle();
                    this.mVelocityTracker = null;
                }
                Log.v("UdfpsController", "onTouch | finger up");
                this.mAttemptedToDismissKeyguard = false;
                onFingerUp(j, overlayView);
                this.mFalsingManager.isFalseTouch(13);
                Trace.endSection();
                z2 = false;
                return z2;
            }
            Trace.beginSection("UdfpsController.onTouch.ACTION_DOWN");
            VelocityTracker velocityTracker2 = this.mVelocityTracker;
            if (velocityTracker2 == null) {
                this.mVelocityTracker = VelocityTracker.obtain();
            } else {
                velocityTracker2.clear();
            }
            boolean isWithinSensorArea2 = isWithinSensorArea(overlayView, motionEvent.getX(), motionEvent.getY(), z);
            if (isWithinSensorArea2) {
                Trace.beginAsyncSection("UdfpsController.e2e.onPointerDown", 0);
                Log.v("UdfpsController", "onTouch | action down");
                this.mActivePointerId = motionEvent.getPointerId(0);
                this.mVelocityTracker.addMovement(motionEvent);
                this.mAcquiredReceived = false;
                z2 = true;
            }
            if ((isWithinSensorArea2 || z) && shouldTryToDismissKeyguard()) {
                Log.v("UdfpsController", "onTouch | dismiss keyguard ACTION_DOWN");
                tryDismissingKeyguard();
            }
            Trace.endSection();
            return z2;
        }
    }

    public void onAodInterrupt(final int i, final int i2, final float f, final float f2) {
        if (this.mIsAodInterruptActive) {
            return;
        }
        if (!this.mKeyguardUpdateMonitor.isFingerprintDetectionRunning()) {
            if (this.mFalsingManager.isFalseTouch(14)) {
                Log.v("UdfpsController", "aod lock icon long-press rejected by the falsing manager.");
                return;
            }
            this.mKeyguardViewManager.showPrimaryBouncer(true);
            this.mVibrator.vibrate(Process.myUid(), this.mContext.getOpPackageName(), EFFECT_CLICK, "aod-lock-icon-longpress", LOCK_ICON_VIBRATION_ATTRIBUTES);
            return;
        }
        UdfpsControllerOverlay udfpsControllerOverlay = this.mOverlay;
        final long requestId = udfpsControllerOverlay != null ? udfpsControllerOverlay.getRequestId() : -1L;
        Runnable runnable = new Runnable() { // from class: com.android.systemui.biometrics.UdfpsController$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                UdfpsController.$r8$lambda$ZrEcczuBva_FV7Agk55jhztpyQM(UdfpsController.this, requestId, i, i2, f2, f);
            }
        };
        this.mAodInterruptRunnable = runnable;
        if (this.mScreenOn) {
            runnable.run();
            this.mAodInterruptRunnable = null;
        }
    }

    public final void onFingerDown(final long j, int i, final float f, final float f2, final float f3, final float f4, float f5, long j2, long j3, boolean z) {
        this.mExecution.assertIsMainThread();
        UdfpsControllerOverlay udfpsControllerOverlay = this.mOverlay;
        if (udfpsControllerOverlay == null) {
            Log.w("UdfpsController", "Null request in onFingerDown");
        } else if (!udfpsControllerOverlay.matchesRequestId(j)) {
            Log.w("UdfpsController", "Mismatched fingerDown: " + j + " current: " + this.mOverlay.getRequestId());
        } else {
            if (isOptical()) {
                this.mLatencyTracker.onActionStart(14);
            }
            this.mPowerManager.userActivity(this.mSystemClock.uptimeMillis(), 2, 0);
            if (!this.mOnFingerDown) {
                playStartHaptic();
                if (!this.mKeyguardUpdateMonitor.isFaceDetectionRunning()) {
                    this.mKeyguardUpdateMonitor.requestFaceAuth("Face auth triggered due to finger down on UDFPS");
                }
            }
            this.mOnFingerDown = true;
            if (this.mAlternateTouchProvider != null) {
                this.mBiometricExecutor.execute(new Runnable() { // from class: com.android.systemui.biometrics.UdfpsController$$ExternalSyntheticLambda5
                    @Override // java.lang.Runnable
                    public final void run() {
                        UdfpsController.$r8$lambda$s7tV045St_891RqzOjmwvqVPDBw(UdfpsController.this, j, f, f2, f3, f4);
                    }
                });
                this.mFgExecutor.execute(new Runnable() { // from class: com.android.systemui.biometrics.UdfpsController$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        UdfpsController.$r8$lambda$LRBgrJmT5dMk71wUD0VPKaMtvsI(UdfpsController.this, j);
                    }
                });
            } else if (this.mFeatureFlags.isEnabled(Flags.UDFPS_NEW_TOUCH_DETECTION)) {
                this.mFingerprintManager.onPointerDown(j, this.mSensorProps.sensorId, i, f, f2, f3, f4, f5, j2, j3, z);
            } else {
                this.mFingerprintManager.onPointerDown(j, this.mSensorProps.sensorId, (int) f, (int) f2, f3, f4);
            }
            Trace.endAsyncSection("UdfpsController.e2e.onPointerDown", 0);
            UdfpsView overlayView = this.mOverlay.getOverlayView();
            if (overlayView != null && isOptical()) {
                overlayView.configureDisplay(new Runnable() { // from class: com.android.systemui.biometrics.UdfpsController$$ExternalSyntheticLambda7
                    @Override // java.lang.Runnable
                    public final void run() {
                        UdfpsController.$r8$lambda$WERdWA8o71izTk1nzd9_YDsZjT4(UdfpsController.this, j);
                    }
                });
            }
            for (Callback callback : this.mCallbacks) {
                callback.onFingerDown();
            }
        }
    }

    public final void onFingerDown(long j, int i, int i2, float f, float f2) {
        onFingerDown(j, -1, i, i2, f, f2, ActionBarShadowController.ELEVATION_LOW, 0L, 0L, false);
    }

    public final void onFingerUp(long j, UdfpsView udfpsView) {
        onFingerUp(j, udfpsView, -1, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, 0L, 0L, false);
    }

    public final void onFingerUp(final long j, UdfpsView udfpsView, int i, float f, float f2, float f3, float f4, float f5, long j2, long j3, boolean z) {
        this.mExecution.assertIsMainThread();
        this.mActivePointerId = -1;
        this.mAcquiredReceived = false;
        if (this.mOnFingerDown) {
            if (this.mAlternateTouchProvider != null) {
                this.mBiometricExecutor.execute(new Runnable() { // from class: com.android.systemui.biometrics.UdfpsController$$ExternalSyntheticLambda3
                    @Override // java.lang.Runnable
                    public final void run() {
                        UdfpsController.$r8$lambda$1gpiS_aphz9rcLmLzW4qvOfmE68(UdfpsController.this, j);
                    }
                });
                this.mFgExecutor.execute(new Runnable() { // from class: com.android.systemui.biometrics.UdfpsController$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        UdfpsController.$r8$lambda$Ek9oR0gDkNJ9N8Bl9YLu5rLTHNs(UdfpsController.this, j);
                    }
                });
            } else if (this.mFeatureFlags.isEnabled(Flags.UDFPS_NEW_TOUCH_DETECTION)) {
                this.mFingerprintManager.onPointerUp(j, this.mSensorProps.sensorId, i, f, f2, f3, f4, f5, j2, j3, z);
            } else {
                this.mFingerprintManager.onPointerUp(j, this.mSensorProps.sensorId);
            }
            for (Callback callback : this.mCallbacks) {
                callback.onFingerUp();
            }
        }
        this.mOnFingerDown = false;
        if (isOptical()) {
            unconfigureDisplay(udfpsView);
        }
        cancelAodSendFingerUpAction();
    }

    @VisibleForTesting
    public boolean onTouch(long j, MotionEvent motionEvent, boolean z) {
        return this.mFeatureFlags.isEnabled(Flags.UDFPS_NEW_TOUCH_DETECTION) ? newOnTouch(j, motionEvent, z) : oldOnTouch(j, motionEvent, z);
    }

    public boolean onTouch(MotionEvent motionEvent) {
        UdfpsControllerOverlay udfpsControllerOverlay = this.mOverlay;
        if (udfpsControllerOverlay == null || udfpsControllerOverlay.isHiding()) {
            return false;
        }
        return onTouch(this.mOverlay.getRequestId(), motionEvent, false);
    }

    @VisibleForTesting
    public void playStartHaptic() {
        if (this.mAccessibilityManager.isTouchExplorationEnabled()) {
            this.mVibrator.vibrate(Process.myUid(), this.mContext.getOpPackageName(), EFFECT_CLICK, "udfps-onStart-click", UDFPS_VIBRATION_ATTRIBUTES);
        }
    }

    public final void redrawOverlay() {
        UdfpsControllerOverlay udfpsControllerOverlay = this.mOverlay;
        if (udfpsControllerOverlay != null) {
            hideUdfpsOverlay();
            showUdfpsOverlay(udfpsControllerOverlay);
        }
    }

    public void removeCallback(Callback callback) {
        this.mCallbacks.remove(callback);
    }

    public void setAuthControllerUpdateUdfpsLocation(Runnable runnable) {
        this.mAuthControllerUpdateUdfpsLocation = runnable;
    }

    public void setUdfpsDisplayMode(UdfpsDisplayMode udfpsDisplayMode) {
        this.mUdfpsDisplayMode = udfpsDisplayMode;
    }

    public final boolean shouldTryToDismissKeyguard() {
        UdfpsControllerOverlay udfpsControllerOverlay = this.mOverlay;
        return udfpsControllerOverlay != null && (udfpsControllerOverlay.getAnimationViewController() instanceof UdfpsKeyguardViewController) && this.mKeyguardStateController.canDismissLockScreen() && !this.mAttemptedToDismissKeyguard;
    }

    public final void showUdfpsOverlay(UdfpsControllerOverlay udfpsControllerOverlay) {
        this.mExecution.assertIsMainThread();
        this.mOverlay = udfpsControllerOverlay;
        int requestReason = udfpsControllerOverlay.getRequestReason();
        if (requestReason == 4 && !this.mKeyguardUpdateMonitor.isFingerprintDetectionRunning()) {
            Log.d("UdfpsController", "Attempting to showUdfpsOverlay when fingerprint detection isn't running on keyguard. Skip show.");
        } else if (!udfpsControllerOverlay.show(this, this.mOverlayParams)) {
            Log.v("UdfpsController", "showUdfpsOverlay | the overlay is already showing");
        } else {
            Log.v("UdfpsController", "showUdfpsOverlay | adding window reason=" + requestReason);
            this.mOnFingerDown = false;
            this.mAttemptedToDismissKeyguard = false;
            this.mOrientationListener.enable();
        }
    }

    @VisibleForTesting
    public void tryAodSendFingerUp() {
        if (this.mIsAodInterruptActive) {
            cancelAodSendFingerUpAction();
            UdfpsControllerOverlay udfpsControllerOverlay = this.mOverlay;
            if (udfpsControllerOverlay == null || udfpsControllerOverlay.getOverlayView() == null) {
                return;
            }
            onFingerUp(this.mOverlay.getRequestId(), this.mOverlay.getOverlayView());
        }
    }

    public final void tryDismissingKeyguard() {
        if (!this.mOnFingerDown) {
            playStartHaptic();
        }
        this.mKeyguardViewManager.notifyKeyguardAuthenticated(false);
        this.mAttemptedToDismissKeyguard = true;
    }

    public final void unconfigureDisplay(UdfpsView udfpsView) {
        if (udfpsView.isDisplayConfigured()) {
            udfpsView.unconfigureDisplay();
        }
    }

    public void updateOverlayParams(FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal, UdfpsOverlayParams udfpsOverlayParams) {
        if (this.mSensorProps.sensorId != fingerprintSensorPropertiesInternal.sensorId) {
            this.mSensorProps = fingerprintSensorPropertiesInternal;
            Log.w("UdfpsController", "updateUdfpsParams | sensorId has changed");
        }
        if (this.mOverlayParams.equals(udfpsOverlayParams)) {
            return;
        }
        this.mOverlayParams = udfpsOverlayParams;
        boolean isShowingAlternateBouncer = this.mKeyguardViewManager.isShowingAlternateBouncer();
        redrawOverlay();
        if (isShowingAlternateBouncer) {
            this.mKeyguardViewManager.showBouncer(true);
        }
    }

    public final void updateScreenOffFodState() {
        boolean z = true;
        if (!this.mContext.getResources().getBoolean(17891824) || this.mSecureSettings.getInt("screen_off_udfps_enabled", 1) != 1) {
            z = false;
        }
        this.mScreenOffFod = z;
    }
}