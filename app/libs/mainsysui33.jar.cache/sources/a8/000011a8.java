package com.android.systemui.biometrics;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Point;
import android.hardware.biometrics.BiometricFingerprintConstants;
import android.hardware.biometrics.BiometricSourceType;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.view.View;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$attr;
import com.android.systemui.R$bool;
import com.android.systemui.R$integer;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.AuthRippleController;
import com.android.systemui.biometrics.UdfpsController;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.CircleReveal;
import com.android.systemui.statusbar.LiftReveal;
import com.android.systemui.statusbar.LightRevealEffect;
import com.android.systemui.statusbar.LightRevealScrim;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.commandline.Command;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.ViewController;
import java.io.PrintWriter;
import java.util.List;
import javax.inject.Provider;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__StringNumberConversionsJVMKt;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/AuthRippleController.class */
public final class AuthRippleController extends ViewController<AuthRippleView> implements KeyguardStateController.Callback, WakefulnessLifecycle.Observer {
    public static final Companion Companion = new Companion(null);
    public final AuthController authController;
    public final AuthRippleController$authControllerCallback$1 authControllerCallback;
    public final BiometricUnlockController biometricUnlockController;
    public final KeyguardBypassController bypassController;
    public final CentralSurfaces centralSurfaces;
    public LightRevealEffect circleReveal;
    public final CommandRegistry commandRegistry;
    public final AuthRippleController$configurationChangedListener$1 configurationChangedListener;
    public final ConfigurationController configurationController;
    public Point faceSensorLocation;
    public final FeatureFlags featureFlags;
    public Point fingerprintSensorLocation;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final AuthRippleController$keyguardUpdateMonitorCallback$1 keyguardUpdateMonitorCallback;
    public ValueAnimator lightRevealScrimAnimator;
    public final NotificationShadeWindowController notificationShadeWindowController;
    public boolean startLightRevealScrimOnKeyguardFadingAway;
    public final StatusBarStateController statusBarStateController;
    public final Context sysuiContext;
    public UdfpsController udfpsController;
    public final AuthRippleController$udfpsControllerCallback$1 udfpsControllerCallback;
    public final Provider<UdfpsController> udfpsControllerProvider;
    public float udfpsRadius;
    public boolean unlockAnimationEnabled;
    public final WakefulnessLifecycle wakefulnessLifecycle;

    /* loaded from: mainsysui33.jar:com/android/systemui/biometrics/AuthRippleController$AuthRippleCommand.class */
    public final class AuthRippleCommand implements Command {
        public AuthRippleCommand() {
            AuthRippleController.this = r4;
        }

        public void execute(PrintWriter printWriter, List<String> list) {
            if (list.isEmpty()) {
                invalidCommand(printWriter);
                return;
            }
            String str = list.get(0);
            switch (str.hashCode()) {
                case -1375934236:
                    if (str.equals("fingerprint")) {
                        Point fingerprintSensorLocation = AuthRippleController.this.getFingerprintSensorLocation();
                        printWriter.println("fingerprint ripple sensorLocation=" + fingerprintSensorLocation);
                        AuthRippleController.this.showUnlockRipple(BiometricSourceType.FINGERPRINT);
                        return;
                    }
                    break;
                case -1349088399:
                    if (str.equals("custom")) {
                        if (list.size() != 3 || StringsKt__StringNumberConversionsJVMKt.toFloatOrNull(list.get(1)) == null || StringsKt__StringNumberConversionsJVMKt.toFloatOrNull(list.get(2)) == null) {
                            invalidCommand(printWriter);
                            return;
                        }
                        String str2 = list.get(1);
                        String str3 = list.get(2);
                        printWriter.println("custom ripple sensorLocation=" + ((Object) str2) + ", " + ((Object) str3));
                        ((AuthRippleView) ((ViewController) AuthRippleController.this).mView).setSensorLocation(new Point(Integer.parseInt(list.get(1)), Integer.parseInt(list.get(2))));
                        AuthRippleController.this.showUnlockedRipple();
                        return;
                    }
                    break;
                case 3135069:
                    if (str.equals("face")) {
                        Point point = AuthRippleController.this.faceSensorLocation;
                        printWriter.println("face ripple sensorLocation=" + point);
                        AuthRippleController.this.showUnlockRipple(BiometricSourceType.FACE);
                        return;
                    }
                    break;
                case 95997746:
                    if (str.equals("dwell")) {
                        AuthRippleController.this.showDwellRipple();
                        Point fingerprintSensorLocation2 = AuthRippleController.this.getFingerprintSensorLocation();
                        float f = AuthRippleController.this.udfpsRadius;
                        printWriter.println("lock screen dwell ripple: \n\tsensorLocation=" + fingerprintSensorLocation2 + "\n\tudfpsRadius=" + f);
                        return;
                    }
                    break;
            }
            invalidCommand(printWriter);
        }

        public void help(PrintWriter printWriter) {
            printWriter.println("Usage: adb shell cmd statusbar auth-ripple <command>");
            printWriter.println("Available commands:");
            printWriter.println("  dwell");
            printWriter.println("  fingerprint");
            printWriter.println("  face");
            printWriter.println("  custom <x-location: int> <y-location: int>");
        }

        public final void invalidCommand(PrintWriter printWriter) {
            printWriter.println("invalid command");
            help(printWriter);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/biometrics/AuthRippleController$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Type inference failed for: r1v17, types: [com.android.systemui.biometrics.AuthRippleController$keyguardUpdateMonitorCallback$1] */
    /* JADX WARN: Type inference failed for: r1v18, types: [com.android.systemui.biometrics.AuthRippleController$configurationChangedListener$1] */
    /* JADX WARN: Type inference failed for: r1v19, types: [com.android.systemui.biometrics.AuthRippleController$udfpsControllerCallback$1] */
    /* JADX WARN: Type inference failed for: r1v20, types: [com.android.systemui.biometrics.AuthRippleController$authControllerCallback$1] */
    public AuthRippleController(CentralSurfaces centralSurfaces, Context context, AuthController authController, ConfigurationController configurationController, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardStateController keyguardStateController, WakefulnessLifecycle wakefulnessLifecycle, CommandRegistry commandRegistry, NotificationShadeWindowController notificationShadeWindowController, KeyguardBypassController keyguardBypassController, BiometricUnlockController biometricUnlockController, Provider<UdfpsController> provider, StatusBarStateController statusBarStateController, FeatureFlags featureFlags, AuthRippleView authRippleView) {
        super(authRippleView);
        this.centralSurfaces = centralSurfaces;
        this.sysuiContext = context;
        this.authController = authController;
        this.configurationController = configurationController;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.keyguardStateController = keyguardStateController;
        this.wakefulnessLifecycle = wakefulnessLifecycle;
        this.commandRegistry = commandRegistry;
        this.notificationShadeWindowController = notificationShadeWindowController;
        this.bypassController = keyguardBypassController;
        this.biometricUnlockController = biometricUnlockController;
        this.udfpsControllerProvider = provider;
        this.statusBarStateController = statusBarStateController;
        this.featureFlags = featureFlags;
        this.udfpsRadius = -1.0f;
        this.unlockAnimationEnabled = true;
        this.keyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.biometrics.AuthRippleController$keyguardUpdateMonitorCallback$1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onBiometricAcquired(BiometricSourceType biometricSourceType, int i) {
                if (biometricSourceType == BiometricSourceType.FINGERPRINT && BiometricFingerprintConstants.shouldDisableUdfpsDisplayMode(i) && i != 0) {
                    ((AuthRippleView) AuthRippleController.m1541access$getMView$p$s1871381439(AuthRippleController.this)).retractDwellRipple();
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onBiometricAuthFailed(BiometricSourceType biometricSourceType) {
                if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
                    ((AuthRippleView) AuthRippleController.m1541access$getMView$p$s1871381439(AuthRippleController.this)).retractDwellRipple();
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onBiometricAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) {
                if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
                    ((AuthRippleView) AuthRippleController.m1541access$getMView$p$s1871381439(AuthRippleController.this)).fadeDwellRipple();
                }
                AuthRippleController.this.showUnlockRipple(biometricSourceType);
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onKeyguardBouncerStateChanged(boolean z) {
                if (z) {
                    ((AuthRippleView) AuthRippleController.m1541access$getMView$p$s1871381439(AuthRippleController.this)).fadeDwellRipple();
                }
            }
        };
        this.configurationChangedListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.biometrics.AuthRippleController$configurationChangedListener$1
            public void onThemeChanged() {
                AuthRippleController.access$updateRippleColor(AuthRippleController.this);
            }

            public void onUiModeChanged() {
                AuthRippleController.access$updateRippleColor(AuthRippleController.this);
            }
        };
        this.udfpsControllerCallback = new UdfpsController.Callback() { // from class: com.android.systemui.biometrics.AuthRippleController$udfpsControllerCallback$1
            @Override // com.android.systemui.biometrics.UdfpsController.Callback
            public void onFingerDown() {
                AuthRippleController.access$showDwellRipple(AuthRippleController.this);
            }

            @Override // com.android.systemui.biometrics.UdfpsController.Callback
            public void onFingerUp() {
                ((AuthRippleView) AuthRippleController.m1541access$getMView$p$s1871381439(AuthRippleController.this)).retractDwellRipple();
            }
        };
        this.authControllerCallback = new AuthController.Callback() { // from class: com.android.systemui.biometrics.AuthRippleController$authControllerCallback$1
            @Override // com.android.systemui.biometrics.AuthController.Callback
            public void onAllAuthenticatorsRegistered() {
                AuthRippleController.access$updateUdfpsDependentParams(AuthRippleController.this);
            }

            @Override // com.android.systemui.biometrics.AuthController.Callback
            public void onUdfpsLocationChanged() {
                AuthRippleController.access$updateUdfpsDependentParams(AuthRippleController.this);
            }
        };
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthRippleController$onKeyguardFadingAwayChanged$1$1.onAnimationUpdate(android.animation.ValueAnimator):void, com.android.systemui.biometrics.AuthRippleController$onKeyguardFadingAwayChanged$1$2.onAnimationEnd(android.animation.Animator):void] */
    public static final /* synthetic */ LightRevealEffect access$getCircleReveal$p(AuthRippleController authRippleController) {
        return authRippleController.circleReveal;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthRippleController$keyguardUpdateMonitorCallback$1.onBiometricAcquired(android.hardware.biometrics.BiometricSourceType, int):void, com.android.systemui.biometrics.AuthRippleController$keyguardUpdateMonitorCallback$1.onBiometricAuthFailed(android.hardware.biometrics.BiometricSourceType):void, com.android.systemui.biometrics.AuthRippleController$keyguardUpdateMonitorCallback$1.onBiometricAuthenticated(int, android.hardware.biometrics.BiometricSourceType, boolean):void, com.android.systemui.biometrics.AuthRippleController$keyguardUpdateMonitorCallback$1.onKeyguardBouncerStateChanged(boolean):void, com.android.systemui.biometrics.AuthRippleController$udfpsControllerCallback$1.onFingerUp():void] */
    /* renamed from: access$getMView$p$s-1871381439 */
    public static final /* synthetic */ View m1541access$getMView$p$s1871381439(AuthRippleController authRippleController) {
        return ((ViewController) authRippleController).mView;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthRippleController$showUnlockedRipple$2.run():void] */
    public static final /* synthetic */ NotificationShadeWindowController access$getNotificationShadeWindowController$p(AuthRippleController authRippleController) {
        return authRippleController.notificationShadeWindowController;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthRippleController$udfpsControllerCallback$1.onFingerDown():void] */
    public static final /* synthetic */ void access$showDwellRipple(AuthRippleController authRippleController) {
        authRippleController.showDwellRipple();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthRippleController$configurationChangedListener$1.onThemeChanged():void, com.android.systemui.biometrics.AuthRippleController$configurationChangedListener$1.onUiModeChanged():void] */
    public static final /* synthetic */ void access$updateRippleColor(AuthRippleController authRippleController) {
        authRippleController.updateRippleColor();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthRippleController$authControllerCallback$1.onAllAuthenticatorsRegistered():void, com.android.systemui.biometrics.AuthRippleController$authControllerCallback$1.onUdfpsLocationChanged():void] */
    public static final /* synthetic */ void access$updateUdfpsDependentParams(AuthRippleController authRippleController) {
        authRippleController.updateUdfpsDependentParams();
    }

    public static /* synthetic */ void getStartLightRevealScrimOnKeyguardFadingAway$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }

    public final Point getFingerprintSensorLocation() {
        return this.fingerprintSensorLocation;
    }

    public final boolean isAnimatingLightRevealScrim() {
        ValueAnimator valueAnimator = this.lightRevealScrimAnimator;
        return valueAnimator != null ? valueAnimator.isRunning() : false;
    }

    public void onInit() {
        ((AuthRippleView) ((ViewController) this).mView).setAlphaInDuration(this.sysuiContext.getResources().getInteger(R$integer.auth_ripple_alpha_in_duration));
        this.unlockAnimationEnabled = this.sysuiContext.getResources().getBoolean(R$bool.config_enableUnlockRippleAnimation);
    }

    public void onKeyguardFadingAwayChanged() {
        if (!this.featureFlags.isEnabled(Flags.LIGHT_REVEAL_MIGRATION) && this.keyguardStateController.isKeyguardFadingAway()) {
            final LightRevealScrim lightRevealScrim = this.centralSurfaces.getLightRevealScrim();
            if (!this.startLightRevealScrimOnKeyguardFadingAway || lightRevealScrim == null) {
                return;
            }
            ValueAnimator valueAnimator = this.lightRevealScrimAnimator;
            if (valueAnimator != null) {
                valueAnimator.cancel();
            }
            final ValueAnimator ofFloat = ValueAnimator.ofFloat(0.1f, 1.0f);
            ofFloat.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
            ofFloat.setDuration(1533L);
            ofFloat.setStartDelay(this.keyguardStateController.getKeyguardFadingAwayDelay());
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.AuthRippleController$onKeyguardFadingAwayChanged$1$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    if (Intrinsics.areEqual(lightRevealScrim.getRevealEffect(), AuthRippleController.access$getCircleReveal$p(this))) {
                        lightRevealScrim.setRevealAmount(((Float) valueAnimator2.getAnimatedValue()).floatValue());
                    } else {
                        ofFloat.cancel();
                    }
                }
            });
            ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.biometrics.AuthRippleController$onKeyguardFadingAwayChanged$1$2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (Intrinsics.areEqual(lightRevealScrim.getRevealEffect(), AuthRippleController.access$getCircleReveal$p(this))) {
                        lightRevealScrim.setRevealEffect(LiftReveal.INSTANCE);
                    }
                    this.setLightRevealScrimAnimator(null);
                }
            });
            ofFloat.start();
            this.lightRevealScrimAnimator = ofFloat;
            this.startLightRevealScrimOnKeyguardFadingAway = false;
        }
    }

    @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
    public void onStartedGoingToSleep() {
        this.startLightRevealScrimOnKeyguardFadingAway = false;
    }

    public void onViewAttached() {
        this.authController.addCallback(this.authControllerCallback);
        updateRippleColor();
        updateUdfpsDependentParams();
        UdfpsController udfpsController = this.udfpsController;
        if (udfpsController != null) {
            udfpsController.addCallback(this.udfpsControllerCallback);
        }
        this.configurationController.addCallback(this.configurationChangedListener);
        this.keyguardUpdateMonitor.registerCallback(this.keyguardUpdateMonitorCallback);
        this.keyguardStateController.addCallback(this);
        this.wakefulnessLifecycle.addObserver(this);
        this.commandRegistry.registerCommand("auth-ripple", new Function0<Command>() { // from class: com.android.systemui.biometrics.AuthRippleController$onViewAttached$1
            {
                super(0);
            }

            /* JADX DEBUG: Method merged with bridge method */
            /* renamed from: invoke */
            public final Command m1542invoke() {
                return new AuthRippleController.AuthRippleCommand();
            }
        });
    }

    public void onViewDetached() {
        UdfpsController udfpsController = this.udfpsController;
        if (udfpsController != null) {
            udfpsController.removeCallback(this.udfpsControllerCallback);
        }
        this.authController.removeCallback(this.authControllerCallback);
        this.keyguardUpdateMonitor.removeCallback(this.keyguardUpdateMonitorCallback);
        this.configurationController.removeCallback(this.configurationChangedListener);
        this.keyguardStateController.removeCallback(this);
        this.wakefulnessLifecycle.removeObserver(this);
        this.commandRegistry.unregisterCommand("auth-ripple");
        this.notificationShadeWindowController.setForcePluginOpen(false, this);
    }

    public final void setLightRevealScrimAnimator(ValueAnimator valueAnimator) {
        this.lightRevealScrimAnimator = valueAnimator;
    }

    public final void showDwellRipple() {
        updateSensorLocation();
        Point point = this.fingerprintSensorLocation;
        if (point != null) {
            ((AuthRippleView) ((ViewController) this).mView).setFingerprintSensorLocation(point, this.udfpsRadius);
            ((AuthRippleView) ((ViewController) this).mView).startDwellRipple(this.statusBarStateController.isDozing());
        }
    }

    public final void showUnlockRipple(BiometricSourceType biometricSourceType) {
        Point point;
        if (this.keyguardStateController.isShowing() && this.keyguardUpdateMonitor.isUnlockingWithBiometricAllowed(biometricSourceType)) {
            updateSensorLocation();
            if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
                Point point2 = this.fingerprintSensorLocation;
                if (point2 != null) {
                    ((AuthRippleView) ((ViewController) this).mView).setFingerprintSensorLocation(point2, this.udfpsRadius);
                    int i = point2.x;
                    this.circleReveal = new CircleReveal(i, point2.y, 0, Math.max(Math.max(i, ((int) this.centralSurfaces.getDisplayWidth()) - point2.x), Math.max(point2.y, ((int) this.centralSurfaces.getDisplayHeight()) - point2.y)));
                    showUnlockedRipple();
                }
            } else if (biometricSourceType == BiometricSourceType.FACE) {
                if ((this.bypassController.canBypass() || this.authController.isUdfpsFingerDown()) && (point = this.faceSensorLocation) != null) {
                    ((AuthRippleView) ((ViewController) this).mView).setSensorLocation(point);
                    int i2 = point.x;
                    this.circleReveal = new CircleReveal(i2, point.y, 0, Math.max(Math.max(i2, ((int) this.centralSurfaces.getDisplayWidth()) - point.x), Math.max(point.y, ((int) this.centralSurfaces.getDisplayHeight()) - point.y)));
                    showUnlockedRipple();
                }
            }
        }
    }

    public final void showUnlockedRipple() {
        LightRevealEffect lightRevealEffect;
        if (this.unlockAnimationEnabled) {
            this.notificationShadeWindowController.setForcePluginOpen(true, this);
            if (!this.featureFlags.isEnabled(Flags.LIGHT_REVEAL_MIGRATION)) {
                LightRevealScrim lightRevealScrim = this.centralSurfaces.getLightRevealScrim();
                if ((this.statusBarStateController.isDozing() || this.biometricUnlockController.isWakeAndUnlock()) && (lightRevealEffect = this.circleReveal) != null) {
                    if (lightRevealScrim != null) {
                        lightRevealScrim.setRevealAmount((float) ActionBarShadowController.ELEVATION_LOW);
                    }
                    if (lightRevealScrim != null) {
                        lightRevealScrim.setRevealEffect(lightRevealEffect);
                    }
                    this.startLightRevealScrimOnKeyguardFadingAway = true;
                }
            }
            ((AuthRippleView) ((ViewController) this).mView).startUnlockedRipple(new Runnable() { // from class: com.android.systemui.biometrics.AuthRippleController$showUnlockedRipple$2
                @Override // java.lang.Runnable
                public final void run() {
                    AuthRippleController.access$getNotificationShadeWindowController$p(AuthRippleController.this).setForcePluginOpen(false, AuthRippleController.this);
                }
            });
        }
    }

    public final void updateRippleColor() {
        ((AuthRippleView) ((ViewController) this).mView).setLockScreenColor(com.android.settingslib.Utils.getColorAttrDefaultColor(this.sysuiContext, R$attr.wallpaperTextColorAccent));
    }

    public final void updateSensorLocation() {
        this.fingerprintSensorLocation = this.authController.getFingerprintSensorLocation();
        this.faceSensorLocation = this.authController.getFaceSensorLocation();
    }

    public final void updateUdfpsDependentParams() {
        UdfpsController udfpsController;
        List<FingerprintSensorPropertiesInternal> udfpsProps = this.authController.getUdfpsProps();
        if (udfpsProps == null || udfpsProps.size() <= 0) {
            return;
        }
        this.udfpsController = (UdfpsController) this.udfpsControllerProvider.get();
        this.udfpsRadius = this.authController.getUdfpsRadius();
        if (!((AuthRippleView) ((ViewController) this).mView).isAttachedToWindow() || (udfpsController = this.udfpsController) == null) {
            return;
        }
        udfpsController.addCallback(this.udfpsControllerCallback);
    }
}