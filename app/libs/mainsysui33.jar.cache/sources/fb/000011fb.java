package com.android.systemui.biometrics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.IUdfpsOverlayControllerCallback;
import android.os.Build;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import android.util.RotationUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$array;
import com.android.systemui.R$layout;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.flags.UnreleasedFlag;
import com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.phone.SystemUIDialogManager;
import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.time.SystemClock;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsControllerOverlay.class */
public final class UdfpsControllerOverlay {
    public final AccessibilityManager accessibilityManager;
    public final ActivityLaunchAnimator activityLaunchAnimator;
    public final ConfigurationController configurationController;
    public final Context context;
    public final IUdfpsOverlayControllerCallback controllerCallback;
    public final WindowManager.LayoutParams coreLayoutParams;
    public final SystemUIDialogManager dialogManager;
    public final DumpManager dumpManager;
    public final UdfpsEnrollHelper enrollHelper;
    public final FeatureFlags featureFlags;
    public final LayoutInflater inflater;
    public final boolean isDebuggable;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final Function3<View, MotionEvent, Boolean, Boolean> onTouch;
    public UdfpsOverlayParams overlayParams;
    public AccessibilityManager.TouchExplorationStateChangeListener overlayTouchListener;
    public UdfpsView overlayView;
    public final PrimaryBouncerInteractor primaryBouncerInteractor;
    public final long requestId;
    public final int requestReason;
    public Rect sensorBounds;
    public final ShadeExpansionStateManager shadeExpansionStateManager;
    public final StatusBarKeyguardViewManager statusBarKeyguardViewManager;
    public final StatusBarStateController statusBarStateController;
    public final SystemClock systemClock;
    public boolean touchExplorationEnabled;
    public final LockscreenShadeTransitionController transitionController;
    public UdfpsDisplayModeProvider udfpsDisplayModeProvider;
    public final UnlockedScreenOffAnimationController unlockedScreenOffAnimationController;
    public final WindowManager windowManager;

    public UdfpsControllerOverlay(Context context, FingerprintManager fingerprintManager, LayoutInflater layoutInflater, WindowManager windowManager, AccessibilityManager accessibilityManager, StatusBarStateController statusBarStateController, ShadeExpansionStateManager shadeExpansionStateManager, StatusBarKeyguardViewManager statusBarKeyguardViewManager, KeyguardUpdateMonitor keyguardUpdateMonitor, SystemUIDialogManager systemUIDialogManager, DumpManager dumpManager, LockscreenShadeTransitionController lockscreenShadeTransitionController, ConfigurationController configurationController, SystemClock systemClock, KeyguardStateController keyguardStateController, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, UdfpsDisplayModeProvider udfpsDisplayModeProvider, long j, int i, IUdfpsOverlayControllerCallback iUdfpsOverlayControllerCallback, Function3<? super View, ? super MotionEvent, ? super Boolean, Boolean> function3, ActivityLaunchAnimator activityLaunchAnimator, FeatureFlags featureFlags, PrimaryBouncerInteractor primaryBouncerInteractor) {
        this(context, fingerprintManager, layoutInflater, windowManager, accessibilityManager, statusBarStateController, shadeExpansionStateManager, statusBarKeyguardViewManager, keyguardUpdateMonitor, systemUIDialogManager, dumpManager, lockscreenShadeTransitionController, configurationController, systemClock, keyguardStateController, unlockedScreenOffAnimationController, udfpsDisplayModeProvider, j, i, iUdfpsOverlayControllerCallback, function3, activityLaunchAnimator, featureFlags, primaryBouncerInteractor, false, 16777216, null);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r34v0, resolved type: kotlin.jvm.functions.Function3<? super android.view.View, ? super android.view.MotionEvent, ? super java.lang.Boolean, java.lang.Boolean> */
    /* JADX WARN: Multi-variable type inference failed */
    public UdfpsControllerOverlay(Context context, FingerprintManager fingerprintManager, LayoutInflater layoutInflater, WindowManager windowManager, AccessibilityManager accessibilityManager, StatusBarStateController statusBarStateController, ShadeExpansionStateManager shadeExpansionStateManager, StatusBarKeyguardViewManager statusBarKeyguardViewManager, KeyguardUpdateMonitor keyguardUpdateMonitor, SystemUIDialogManager systemUIDialogManager, DumpManager dumpManager, LockscreenShadeTransitionController lockscreenShadeTransitionController, ConfigurationController configurationController, SystemClock systemClock, KeyguardStateController keyguardStateController, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, UdfpsDisplayModeProvider udfpsDisplayModeProvider, long j, int i, IUdfpsOverlayControllerCallback iUdfpsOverlayControllerCallback, Function3<? super View, ? super MotionEvent, ? super Boolean, Boolean> function3, ActivityLaunchAnimator activityLaunchAnimator, FeatureFlags featureFlags, PrimaryBouncerInteractor primaryBouncerInteractor, boolean z) {
        boolean isEnrollmentReason;
        this.context = context;
        this.inflater = layoutInflater;
        this.windowManager = windowManager;
        this.accessibilityManager = accessibilityManager;
        this.statusBarStateController = statusBarStateController;
        this.shadeExpansionStateManager = shadeExpansionStateManager;
        this.statusBarKeyguardViewManager = statusBarKeyguardViewManager;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.dialogManager = systemUIDialogManager;
        this.dumpManager = dumpManager;
        this.transitionController = lockscreenShadeTransitionController;
        this.configurationController = configurationController;
        this.systemClock = systemClock;
        this.keyguardStateController = keyguardStateController;
        this.unlockedScreenOffAnimationController = unlockedScreenOffAnimationController;
        this.udfpsDisplayModeProvider = udfpsDisplayModeProvider;
        this.requestId = j;
        this.requestReason = i;
        this.controllerCallback = iUdfpsOverlayControllerCallback;
        this.onTouch = function3;
        this.activityLaunchAnimator = activityLaunchAnimator;
        this.featureFlags = featureFlags;
        this.primaryBouncerInteractor = primaryBouncerInteractor;
        this.isDebuggable = z;
        this.overlayParams = new UdfpsOverlayParams(null, null, 0, 0, ActionBarShadowController.ELEVATION_LOW, 0, 63, null);
        this.sensorBounds = new Rect();
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(2009, 0, -3);
        layoutParams.setTitle("UdfpsControllerOverlay");
        layoutParams.setFitInsetsTypes(0);
        layoutParams.gravity = 51;
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.flags = 25166120;
        layoutParams.privateFlags = 536870912;
        layoutParams.accessibilityTitle = " ";
        if (featureFlags.isEnabled(Flags.UDFPS_NEW_TOUCH_DETECTION)) {
            layoutParams.inputFeatures = 4;
        }
        this.coreLayoutParams = layoutParams;
        isEnrollmentReason = UdfpsControllerOverlayKt.isEnrollmentReason(i);
        this.enrollHelper = (!isEnrollmentReason || shouldRemoveEnrollmentUi()) ? null : new UdfpsEnrollHelper(context, fingerprintManager, i);
    }

    public /* synthetic */ UdfpsControllerOverlay(Context context, FingerprintManager fingerprintManager, LayoutInflater layoutInflater, WindowManager windowManager, AccessibilityManager accessibilityManager, StatusBarStateController statusBarStateController, ShadeExpansionStateManager shadeExpansionStateManager, StatusBarKeyguardViewManager statusBarKeyguardViewManager, KeyguardUpdateMonitor keyguardUpdateMonitor, SystemUIDialogManager systemUIDialogManager, DumpManager dumpManager, LockscreenShadeTransitionController lockscreenShadeTransitionController, ConfigurationController configurationController, SystemClock systemClock, KeyguardStateController keyguardStateController, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, UdfpsDisplayModeProvider udfpsDisplayModeProvider, long j, int i, IUdfpsOverlayControllerCallback iUdfpsOverlayControllerCallback, Function3 function3, ActivityLaunchAnimator activityLaunchAnimator, FeatureFlags featureFlags, PrimaryBouncerInteractor primaryBouncerInteractor, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, fingerprintManager, layoutInflater, windowManager, accessibilityManager, statusBarStateController, shadeExpansionStateManager, statusBarKeyguardViewManager, keyguardUpdateMonitor, systemUIDialogManager, dumpManager, lockscreenShadeTransitionController, configurationController, systemClock, keyguardStateController, unlockedScreenOffAnimationController, udfpsDisplayModeProvider, j, i, iUdfpsOverlayControllerCallback, function3, activityLaunchAnimator, featureFlags, primaryBouncerInteractor, (i2 & 16777216) != 0 ? Build.IS_DEBUGGABLE : z);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsControllerOverlay$show$1$1.onTouchExplorationStateChanged(boolean):void] */
    public static final /* synthetic */ AccessibilityManager access$getAccessibilityManager$p(UdfpsControllerOverlay udfpsControllerOverlay) {
        return udfpsControllerOverlay.accessibilityManager;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsControllerOverlay$show$1$1.1.onHover(android.view.View, android.view.MotionEvent):boolean, com.android.systemui.biometrics.UdfpsControllerOverlay$show$1$1.2.onTouch(android.view.View, android.view.MotionEvent):boolean] */
    public static final /* synthetic */ Function3 access$getOnTouch$p(UdfpsControllerOverlay udfpsControllerOverlay) {
        return udfpsControllerOverlay.onTouch;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsControllerOverlay$show$1$1.onTouchExplorationStateChanged(boolean):void] */
    public static final /* synthetic */ void access$setTouchExplorationEnabled$p(UdfpsControllerOverlay udfpsControllerOverlay, boolean z) {
        udfpsControllerOverlay.touchExplorationEnabled = z;
    }

    public final void cancel() {
        try {
            this.controllerCallback.onUserCanceled();
        } catch (RemoteException e) {
            Log.e("UdfpsControllerOverlay", "Remote exception", e);
        }
    }

    public final UdfpsAnimationViewController<?> getAnimationViewController() {
        UdfpsView udfpsView = this.overlayView;
        return udfpsView != null ? udfpsView.getAnimationViewController() : null;
    }

    public final UdfpsView getOverlayView() {
        return this.overlayView;
    }

    public final long getRequestId() {
        return this.requestId;
    }

    public final int getRequestReason() {
        return this.requestReason;
    }

    public final boolean hide() {
        boolean isShowing = isShowing();
        UdfpsView udfpsView = this.overlayView;
        if (udfpsView != null) {
            if (udfpsView.isDisplayConfigured()) {
                udfpsView.unconfigureDisplay();
            }
            this.windowManager.removeView(udfpsView);
            udfpsView.setOnTouchListener(null);
            udfpsView.setOnHoverListener(null);
            udfpsView.setAnimationViewController(null);
            AccessibilityManager.TouchExplorationStateChangeListener touchExplorationStateChangeListener = this.overlayTouchListener;
            if (touchExplorationStateChangeListener != null) {
                this.accessibilityManager.removeTouchExplorationStateChangeListener(touchExplorationStateChangeListener);
            }
        }
        this.overlayView = null;
        this.overlayTouchListener = null;
        return isShowing;
    }

    public final UdfpsAnimationViewController<?> inflateUdfpsAnimation(UdfpsView udfpsView, UdfpsController udfpsController) {
        UdfpsAnimationViewController udfpsEnrollViewController;
        int i = this.requestReason;
        boolean z = true;
        if (i != 1) {
            z = true;
            if (i != 2) {
                z = false;
            }
        }
        switch ((z && shouldRemoveEnrollmentUi()) ? 5 : this.requestReason) {
            case 1:
            case 2:
                View inflate = this.inflater.inflate(R$layout.udfps_enroll_view, (ViewGroup) null);
                if (inflate != null) {
                    UdfpsEnrollView udfpsEnrollView = (UdfpsEnrollView) inflate;
                    udfpsView.addView(udfpsEnrollView);
                    udfpsEnrollView.updateSensorLocation(this.sensorBounds);
                    UdfpsEnrollHelper udfpsEnrollHelper = this.enrollHelper;
                    if (udfpsEnrollHelper != null) {
                        udfpsEnrollViewController = new UdfpsEnrollViewController(udfpsEnrollView, udfpsEnrollHelper, this.statusBarStateController, this.shadeExpansionStateManager, this.dialogManager, this.dumpManager, this.featureFlags, this.overlayParams.getScaleFactor());
                        break;
                    } else {
                        throw new IllegalStateException("no enrollment helper");
                    }
                } else {
                    throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.biometrics.UdfpsEnrollView");
                }
            case 3:
                View inflate2 = this.inflater.inflate(R$layout.udfps_bp_view, (ViewGroup) null);
                if (inflate2 != null) {
                    UdfpsBpView udfpsBpView = (UdfpsBpView) inflate2;
                    udfpsView.addView(udfpsBpView);
                    udfpsEnrollViewController = new UdfpsBpViewController(udfpsBpView, this.statusBarStateController, this.shadeExpansionStateManager, this.dialogManager, this.dumpManager);
                    break;
                } else {
                    throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.biometrics.UdfpsBpView");
                }
            case 4:
                View inflate3 = this.inflater.inflate(R$layout.udfps_keyguard_view, (ViewGroup) null);
                if (inflate3 != null) {
                    UdfpsKeyguardView udfpsKeyguardView = (UdfpsKeyguardView) inflate3;
                    udfpsView.addView(udfpsKeyguardView);
                    udfpsEnrollViewController = new UdfpsKeyguardViewController(udfpsKeyguardView, this.statusBarStateController, this.shadeExpansionStateManager, this.statusBarKeyguardViewManager, this.keyguardUpdateMonitor, this.dumpManager, this.transitionController, this.configurationController, this.systemClock, this.keyguardStateController, this.unlockedScreenOffAnimationController, this.dialogManager, udfpsController, this.activityLaunchAnimator, this.featureFlags, this.primaryBouncerInteractor);
                    break;
                } else {
                    throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.biometrics.UdfpsKeyguardView");
                }
            case 5:
            case 6:
                View inflate4 = this.inflater.inflate(R$layout.udfps_fpm_other_view, (ViewGroup) null);
                if (inflate4 != null) {
                    UdfpsFpmOtherView udfpsFpmOtherView = (UdfpsFpmOtherView) inflate4;
                    udfpsView.addView(udfpsFpmOtherView);
                    udfpsEnrollViewController = new UdfpsFpmOtherViewController(udfpsFpmOtherView, this.statusBarStateController, this.shadeExpansionStateManager, this.dialogManager, this.dumpManager);
                    break;
                } else {
                    throw new NullPointerException("null cannot be cast to non-null type com.android.systemui.biometrics.UdfpsFpmOtherView");
                }
            default:
                Log.e("UdfpsControllerOverlay", "Animation for reason " + this.requestReason + " not supported yet");
                udfpsEnrollViewController = null;
                break;
        }
        return udfpsEnrollViewController;
    }

    public final boolean isHiding() {
        return this.overlayView == null;
    }

    public final boolean isShowing() {
        return this.overlayView != null;
    }

    public final boolean matchesRequestId(long j) {
        long j2 = this.requestId;
        return j2 == -1 || j2 == j;
    }

    public final void onAcquiredGood() {
        UdfpsEnrollHelper udfpsEnrollHelper = this.enrollHelper;
        if (udfpsEnrollHelper != null) {
            udfpsEnrollHelper.animateIfLastStep();
        }
    }

    public final void onEnrollmentHelp() {
        UdfpsEnrollHelper udfpsEnrollHelper = this.enrollHelper;
        if (udfpsEnrollHelper != null) {
            udfpsEnrollHelper.onEnrollmentHelp();
        }
    }

    public final void onEnrollmentProgress(int i) {
        UdfpsEnrollHelper udfpsEnrollHelper = this.enrollHelper;
        if (udfpsEnrollHelper != null) {
            udfpsEnrollHelper.onEnrollmentProgress(i);
        }
    }

    public final void onTouchOutsideOfSensorArea(float f, float f2, float f3, float f4, int i) {
        if (this.touchExplorationEnabled) {
            String[] stringArray = this.context.getResources().getStringArray(R$array.udfps_accessibility_touch_hints);
            if (stringArray.length != 4) {
                Log.e("UdfpsControllerOverlay", "expected exactly 4 touch hints, got " + stringArray + ".size?");
                return;
            }
            String onTouchOutsideOfSensorAreaImpl = onTouchOutsideOfSensorAreaImpl(f, f2, f3, f4, i);
            Log.v("UdfpsControllerOverlay", "Announcing touch outside : " + onTouchOutsideOfSensorAreaImpl);
            UdfpsAnimationViewController<?> animationViewController = getAnimationViewController();
            if (animationViewController != null) {
                animationViewController.doAnnounceForAccessibility(onTouchOutsideOfSensorAreaImpl);
            }
        }
    }

    public final String onTouchOutsideOfSensorAreaImpl(float f, float f2, float f3, float f4, int i) {
        String[] stringArray = this.context.getResources().getStringArray(R$array.udfps_accessibility_touch_hints);
        double atan2 = Math.atan2(f4 - f2, f - f3);
        double d = atan2;
        if (atan2 < 0.0d) {
            d = atan2 + 6.283185307179586d;
        }
        double degrees = Math.toDegrees(d);
        double length = 360.0d / stringArray.length;
        int length2 = ((int) (((degrees + (length / 2.0d)) % 360) / length)) % stringArray.length;
        int i2 = length2;
        if (i == 1) {
            i2 = (length2 + 1) % stringArray.length;
        }
        int i3 = i2;
        if (i == 3) {
            i3 = (i2 + 3) % stringArray.length;
        }
        return stringArray[i3];
    }

    public final boolean shouldRemoveEnrollmentUi() {
        boolean z = false;
        if (this.isDebuggable) {
            z = false;
            if (Settings.Global.getInt(this.context.getContentResolver(), "udfps_overlay_remove_enrollment_ui", 0) != 0) {
                z = true;
            }
        }
        return z;
    }

    public final boolean shouldRotate(UdfpsAnimationViewController<?> udfpsAnimationViewController) {
        boolean z = true;
        if (udfpsAnimationViewController instanceof UdfpsKeyguardViewController) {
            if (this.keyguardUpdateMonitor.isGoingToSleep() || !this.keyguardStateController.isOccluded()) {
                z = false;
            }
            return z;
        }
        return true;
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public final boolean show(UdfpsController udfpsController, UdfpsOverlayParams udfpsOverlayParams) {
        boolean isImportantForAccessibility;
        if (this.overlayView != null) {
            Log.v("UdfpsControllerOverlay", "showUdfpsOverlay | the overlay is already showing");
            return false;
        }
        this.overlayParams = udfpsOverlayParams;
        this.sensorBounds = new Rect(udfpsOverlayParams.getSensorBounds());
        try {
            final UdfpsView udfpsView = (UdfpsView) this.inflater.inflate(R$layout.udfps_view, (ViewGroup) null, false);
            udfpsView.setOverlayParams(udfpsOverlayParams);
            udfpsView.setUdfpsDisplayModeProvider(this.udfpsDisplayModeProvider);
            UdfpsAnimationViewController<?> inflateUdfpsAnimation = inflateUdfpsAnimation(udfpsView, udfpsController);
            if (inflateUdfpsAnimation != null) {
                inflateUdfpsAnimation.init();
                udfpsView.setAnimationViewController(inflateUdfpsAnimation);
            }
            isImportantForAccessibility = UdfpsControllerOverlayKt.isImportantForAccessibility(this.requestReason);
            if (isImportantForAccessibility) {
                udfpsView.setImportantForAccessibility(2);
            }
            this.windowManager.addView(udfpsView, updateDimensions(this.coreLayoutParams, inflateUdfpsAnimation));
            udfpsView.setSensorRect(this.sensorBounds);
            this.touchExplorationEnabled = this.accessibilityManager.isTouchExplorationEnabled();
            AccessibilityManager.TouchExplorationStateChangeListener touchExplorationStateChangeListener = new AccessibilityManager.TouchExplorationStateChangeListener() { // from class: com.android.systemui.biometrics.UdfpsControllerOverlay$show$1$1
                @Override // android.view.accessibility.AccessibilityManager.TouchExplorationStateChangeListener
                public final void onTouchExplorationStateChanged(boolean z) {
                    if (UdfpsControllerOverlay.access$getAccessibilityManager$p(UdfpsControllerOverlay.this).isTouchExplorationEnabled()) {
                        UdfpsView udfpsView2 = udfpsView;
                        final UdfpsControllerOverlay udfpsControllerOverlay = UdfpsControllerOverlay.this;
                        udfpsView2.setOnHoverListener(new View.OnHoverListener() { // from class: com.android.systemui.biometrics.UdfpsControllerOverlay$show$1$1.1
                            @Override // android.view.View.OnHoverListener
                            public final boolean onHover(View view, MotionEvent motionEvent) {
                                return ((Boolean) UdfpsControllerOverlay.access$getOnTouch$p(UdfpsControllerOverlay.this).invoke(view, motionEvent, Boolean.TRUE)).booleanValue();
                            }
                        });
                        udfpsView.setOnTouchListener(null);
                        UdfpsControllerOverlay.access$setTouchExplorationEnabled$p(UdfpsControllerOverlay.this, true);
                        return;
                    }
                    udfpsView.setOnHoverListener(null);
                    UdfpsView udfpsView3 = udfpsView;
                    final UdfpsControllerOverlay udfpsControllerOverlay2 = UdfpsControllerOverlay.this;
                    udfpsView3.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.biometrics.UdfpsControllerOverlay$show$1$1.2
                        @Override // android.view.View.OnTouchListener
                        public final boolean onTouch(View view, MotionEvent motionEvent) {
                            return ((Boolean) UdfpsControllerOverlay.access$getOnTouch$p(UdfpsControllerOverlay.this).invoke(view, motionEvent, Boolean.TRUE)).booleanValue();
                        }
                    });
                    UdfpsControllerOverlay.access$setTouchExplorationEnabled$p(UdfpsControllerOverlay.this, false);
                }
            };
            this.overlayTouchListener = touchExplorationStateChangeListener;
            AccessibilityManager accessibilityManager = this.accessibilityManager;
            Intrinsics.checkNotNull(touchExplorationStateChangeListener);
            accessibilityManager.addTouchExplorationStateChangeListener(touchExplorationStateChangeListener);
            AccessibilityManager.TouchExplorationStateChangeListener touchExplorationStateChangeListener2 = this.overlayTouchListener;
            if (touchExplorationStateChangeListener2 != null) {
                touchExplorationStateChangeListener2.onTouchExplorationStateChanged(true);
            }
            udfpsView.setUseExpandedOverlay(this.featureFlags.isEnabled(Flags.UDFPS_NEW_TOUCH_DETECTION));
            this.overlayView = udfpsView;
            return true;
        } catch (RuntimeException e) {
            Log.e("UdfpsControllerOverlay", "showUdfpsOverlay | failed to add window", e);
            return true;
        }
    }

    public final WindowManager.LayoutParams updateDimensions(WindowManager.LayoutParams layoutParams, UdfpsAnimationViewController<?> udfpsAnimationViewController) {
        int i = 0;
        int paddingX = udfpsAnimationViewController != null ? udfpsAnimationViewController.getPaddingX() : 0;
        if (udfpsAnimationViewController != null) {
            i = udfpsAnimationViewController.getPaddingY();
        }
        if (udfpsAnimationViewController != null && udfpsAnimationViewController.listenForTouchesOutsideView()) {
            layoutParams.flags |= 262144;
        }
        FeatureFlags featureFlags = this.featureFlags;
        UnreleasedFlag unreleasedFlag = Flags.UDFPS_NEW_TOUCH_DETECTION;
        Rect rect = featureFlags.isEnabled(unreleasedFlag) ? new Rect(this.overlayParams.getOverlayBounds()) : new Rect(this.overlayParams.getSensorBounds());
        int rotation = this.overlayParams.getRotation();
        if (rotation == 1 || rotation == 3) {
            if (shouldRotate(udfpsAnimationViewController)) {
                Log.v("UdfpsControllerOverlay", "Rotate UDFPS bounds " + Surface.rotationToString(rotation));
                RotationUtils.rotateBounds(rect, this.overlayParams.getNaturalDisplayWidth(), this.overlayParams.getNaturalDisplayHeight(), rotation);
                if (this.featureFlags.isEnabled(unreleasedFlag)) {
                    RotationUtils.rotateBounds(this.sensorBounds, this.overlayParams.getNaturalDisplayWidth(), this.overlayParams.getNaturalDisplayHeight(), rotation);
                }
            } else {
                Log.v("UdfpsControllerOverlay", "Skip rotating UDFPS bounds " + Surface.rotationToString(rotation) + " animation=" + udfpsAnimationViewController + " isGoingToSleep=" + this.keyguardUpdateMonitor.isGoingToSleep() + " isOccluded=" + this.keyguardStateController.isOccluded());
            }
        }
        layoutParams.x = rect.left - paddingX;
        layoutParams.y = rect.top - i;
        layoutParams.height = rect.height() + (paddingX * 2);
        layoutParams.width = rect.width() + (i * 2);
        return layoutParams;
    }
}