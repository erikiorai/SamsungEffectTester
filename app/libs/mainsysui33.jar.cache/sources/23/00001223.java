package com.android.systemui.biometrics;

import android.animation.ValueAnimator;
import android.content.res.Configuration;
import android.util.MathUtils;
import android.view.MotionEvent;
import android.view.View;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.RepeatOnLifecycleKt;
import com.android.keyguard.BouncerPanelExpansionCalculator;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$string;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeExpansionChangeEvent;
import com.android.systemui.shade.ShadeExpansionListener;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import com.android.systemui.statusbar.StatusBarState;
import com.android.systemui.statusbar.phone.KeyguardBouncer;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.phone.SystemUIDialogManager;
import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.time.SystemClock;
import java.io.PrintWriter;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Job;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsKeyguardViewController.class */
public class UdfpsKeyguardViewController extends UdfpsAnimationViewController<UdfpsKeyguardView> {
    public static final Companion Companion = new Companion(null);
    public final ActivityLaunchAnimator activityLaunchAnimator;
    public final ActivityLaunchAnimator.Listener activityLaunchAnimatorListener;
    public float activityLaunchProgress;
    public final ConfigurationController configurationController;
    public final ConfigurationController.ConfigurationListener configurationListener;
    public boolean faceDetectRunning;
    public float inputBouncerExpansion;
    public float inputBouncerHiddenAmount;
    public boolean isLaunchingActivity;
    public final boolean isModernBouncerEnabled;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardStateController.Callback keyguardStateControllerCallback;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final StatusBarKeyguardViewManager keyguardViewManager;
    public float lastDozeAmount;
    public long lastUdfpsBouncerShowTime;
    public boolean launchTransitionFadingAway;
    public final LockscreenShadeTransitionController lockScreenShadeTransitionController;
    public final StatusBarKeyguardViewManager.AlternateBouncer mAlternateBouncer;
    public final KeyguardBouncer.PrimaryBouncerExpansionCallback mPrimaryBouncerExpansionCallback;
    public float panelExpansionFraction;
    public final PrimaryBouncerInteractor primaryBouncerInteractor;
    public float qsExpansion;
    public final ShadeExpansionListener shadeExpansionListener;
    public boolean showingUdfpsBouncer;
    public final StatusBarStateController.StateListener stateListener;
    public final StatusBarKeyguardViewManager.KeyguardViewManagerCallback statusBarKeyguardViewManagerCallback;
    public int statusBarState;
    public final SystemClock systemClock;
    public float transitionToFullShadeProgress;
    public final UdfpsController udfpsController;
    public boolean udfpsRequested;
    public final UnlockedScreenOffAnimationController unlockedScreenOffAnimationController;
    public final ValueAnimator unlockedScreenOffDozeAnimator;
    public final boolean useExpandedOverlay;
    public final UdfpsKeyguardView view;

    @DebugMetadata(c = "com.android.systemui.biometrics.UdfpsKeyguardViewController$1", f = "UdfpsKeyguardViewController.kt", l = {287}, m = "invokeSuspend")
    /* renamed from: com.android.systemui.biometrics.UdfpsKeyguardViewController$1 */
    /* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsKeyguardViewController$1.class */
    public static final class AnonymousClass1 extends SuspendLambda implements Function3<LifecycleOwner, View, Continuation<? super Unit>, Object> {
        private /* synthetic */ Object L$0;
        public int label;

        @DebugMetadata(c = "com.android.systemui.biometrics.UdfpsKeyguardViewController$1$1", f = "UdfpsKeyguardViewController.kt", l = {287}, m = "invokeSuspend")
        /* renamed from: com.android.systemui.biometrics.UdfpsKeyguardViewController$1$1 */
        /* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsKeyguardViewController$1$1.class */
        public static final class C00041 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
            private /* synthetic */ Object L$0;
            public int label;
            public final /* synthetic */ UdfpsKeyguardViewController this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public C00041(UdfpsKeyguardViewController udfpsKeyguardViewController, Continuation<? super C00041> continuation) {
                super(2, continuation);
                this.this$0 = udfpsKeyguardViewController;
            }

            public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
                C00041 c00041 = new C00041(this.this$0, continuation);
                c00041.L$0 = obj;
                return c00041;
            }

            public final Object invoke(CoroutineScope coroutineScope, Continuation<? super Unit> continuation) {
                return create(coroutineScope, continuation).invokeSuspend(Unit.INSTANCE);
            }

            public final Object invokeSuspend(Object obj) {
                Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
                int i = this.label;
                if (i == 0) {
                    ResultKt.throwOnFailure(obj);
                    CoroutineScope coroutineScope = (CoroutineScope) this.L$0;
                    UdfpsKeyguardViewController udfpsKeyguardViewController = this.this$0;
                    this.label = 1;
                    if (udfpsKeyguardViewController.listenForBouncerExpansion$frameworks__base__packages__SystemUI__android_common__SystemUI_core(coroutineScope, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                } else if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                } else {
                    ResultKt.throwOnFailure(obj);
                }
                return Unit.INSTANCE;
            }
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Continuation<? super AnonymousClass1> continuation) {
            super(3, continuation);
            UdfpsKeyguardViewController.this = r5;
        }

        public final Object invoke(LifecycleOwner lifecycleOwner, View view, Continuation<? super Unit> continuation) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(continuation);
            anonymousClass1.L$0 = lifecycleOwner;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt__IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LifecycleOwner lifecycleOwner = (LifecycleOwner) this.L$0;
                Lifecycle.State state = Lifecycle.State.CREATED;
                C00041 c00041 = new C00041(UdfpsKeyguardViewController.this, null);
                this.label = 1;
                if (RepeatOnLifecycleKt.repeatOnLifecycle(lifecycleOwner, state, (Function2<? super CoroutineScope, ? super Continuation<? super Unit>, ? extends Object>) c00041, (Continuation<? super Unit>) this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            } else {
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsKeyguardViewController$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public UdfpsKeyguardViewController(UdfpsKeyguardView udfpsKeyguardView, StatusBarStateController statusBarStateController, ShadeExpansionStateManager shadeExpansionStateManager, StatusBarKeyguardViewManager statusBarKeyguardViewManager, KeyguardUpdateMonitor keyguardUpdateMonitor, DumpManager dumpManager, LockscreenShadeTransitionController lockscreenShadeTransitionController, ConfigurationController configurationController, SystemClock systemClock, KeyguardStateController keyguardStateController, UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, SystemUIDialogManager systemUIDialogManager, UdfpsController udfpsController, ActivityLaunchAnimator activityLaunchAnimator, FeatureFlags featureFlags, PrimaryBouncerInteractor primaryBouncerInteractor) {
        super(udfpsKeyguardView, statusBarStateController, shadeExpansionStateManager, systemUIDialogManager, dumpManager);
        this.view = udfpsKeyguardView;
        this.keyguardViewManager = statusBarKeyguardViewManager;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.lockScreenShadeTransitionController = lockscreenShadeTransitionController;
        this.configurationController = configurationController;
        this.systemClock = systemClock;
        this.keyguardStateController = keyguardStateController;
        this.unlockedScreenOffAnimationController = unlockedScreenOffAnimationController;
        this.udfpsController = udfpsController;
        this.activityLaunchAnimator = activityLaunchAnimator;
        this.primaryBouncerInteractor = primaryBouncerInteractor;
        this.useExpandedOverlay = featureFlags.isEnabled(Flags.UDFPS_NEW_TOUCH_DETECTION);
        boolean isEnabled = featureFlags.isEnabled(Flags.MODERN_BOUNCER);
        this.isModernBouncerEnabled = isEnabled;
        this.lastUdfpsBouncerShowTime = -1L;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
        ofFloat.setDuration(360L);
        ofFloat.setInterpolator(Interpolators.ALPHA_IN);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.UdfpsKeyguardViewController$unlockedScreenOffDozeAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                UdfpsKeyguardViewController.access$getView$p(UdfpsKeyguardViewController.this).onDozeAmountChanged(valueAnimator.getAnimatedFraction(), ((Float) valueAnimator.getAnimatedValue()).floatValue(), 2);
            }
        });
        this.unlockedScreenOffDozeAnimator = ofFloat;
        this.inputBouncerHiddenAmount = 1.0f;
        this.stateListener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.biometrics.UdfpsKeyguardViewController$stateListener$1
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public void onDozeAmountChanged(float f, float f2) {
                boolean z = false;
                if (UdfpsKeyguardViewController.access$getLastDozeAmount$p(UdfpsKeyguardViewController.this) < f) {
                    UdfpsKeyguardViewController.access$showUdfpsBouncer(UdfpsKeyguardViewController.this, false);
                }
                UdfpsKeyguardViewController.access$getUnlockedScreenOffDozeAnimator$p(UdfpsKeyguardViewController.this).cancel();
                if (UdfpsKeyguardViewController.access$getUnlockedScreenOffAnimationController$p(UdfpsKeyguardViewController.this).isAnimationPlaying()) {
                    if (f == ActionBarShadowController.ELEVATION_LOW) {
                        z = true;
                    }
                    if (!z) {
                        UdfpsKeyguardViewController.access$getUnlockedScreenOffDozeAnimator$p(UdfpsKeyguardViewController.this).start();
                        UdfpsKeyguardViewController.access$setLastDozeAmount$p(UdfpsKeyguardViewController.this, f);
                        UdfpsKeyguardViewController.this.updatePauseAuth();
                    }
                }
                UdfpsKeyguardViewController.access$getView$p(UdfpsKeyguardViewController.this).onDozeAmountChanged(f, f2, 1);
                UdfpsKeyguardViewController.access$setLastDozeAmount$p(UdfpsKeyguardViewController.this, f);
                UdfpsKeyguardViewController.this.updatePauseAuth();
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public void onStateChanged(int i) {
                UdfpsKeyguardViewController.access$setStatusBarState$p(UdfpsKeyguardViewController.this, i);
                UdfpsKeyguardViewController.this.updateAlpha();
                UdfpsKeyguardViewController.this.updatePauseAuth();
            }
        };
        this.mPrimaryBouncerExpansionCallback = new KeyguardBouncer.PrimaryBouncerExpansionCallback() { // from class: com.android.systemui.biometrics.UdfpsKeyguardViewController$mPrimaryBouncerExpansionCallback$1
            public void onExpansionChanged(float f) {
                UdfpsKeyguardViewController.access$setInputBouncerHiddenAmount$p(UdfpsKeyguardViewController.this, f);
                UdfpsKeyguardViewController.this.updateAlpha();
                UdfpsKeyguardViewController.this.updatePauseAuth();
            }

            public void onVisibilityChanged(boolean z) {
                UdfpsKeyguardViewController.access$updateBouncerHiddenAmount(UdfpsKeyguardViewController.this);
                UdfpsKeyguardViewController.this.updateAlpha();
                UdfpsKeyguardViewController.this.updatePauseAuth();
            }
        };
        this.configurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.biometrics.UdfpsKeyguardViewController$configurationListener$1
            public void onConfigChanged(Configuration configuration) {
                UdfpsKeyguardViewController.access$updateScaleFactor(UdfpsKeyguardViewController.this);
                UdfpsKeyguardViewController.access$getView$p(UdfpsKeyguardViewController.this).updatePadding();
                UdfpsKeyguardViewController.access$getView$p(UdfpsKeyguardViewController.this).updateColor();
            }

            public void onThemeChanged() {
                UdfpsKeyguardViewController.access$getView$p(UdfpsKeyguardViewController.this).updateColor();
            }

            public void onUiModeChanged() {
                UdfpsKeyguardViewController.access$getView$p(UdfpsKeyguardViewController.this).updateColor();
            }
        };
        this.shadeExpansionListener = new ShadeExpansionListener() { // from class: com.android.systemui.biometrics.UdfpsKeyguardViewController$shadeExpansionListener$1
            public final void onPanelExpansionChanged(ShadeExpansionChangeEvent shadeExpansionChangeEvent) {
                float component1 = shadeExpansionChangeEvent.component1();
                UdfpsKeyguardViewController udfpsKeyguardViewController = UdfpsKeyguardViewController.this;
                float f = component1;
                if (UdfpsKeyguardViewController.access$getKeyguardViewManager$p(udfpsKeyguardViewController).isPrimaryBouncerInTransit()) {
                    f = BouncerPanelExpansionCalculator.aboutToShowBouncerProgress(component1);
                }
                UdfpsKeyguardViewController.access$setPanelExpansionFraction$p(udfpsKeyguardViewController, f);
                UdfpsKeyguardViewController.this.updateAlpha();
                UdfpsKeyguardViewController.this.updatePauseAuth();
            }
        };
        this.keyguardStateControllerCallback = new KeyguardStateController.Callback() { // from class: com.android.systemui.biometrics.UdfpsKeyguardViewController$keyguardStateControllerCallback$1
            public void onLaunchTransitionFadingAwayChanged() {
                UdfpsKeyguardViewController udfpsKeyguardViewController = UdfpsKeyguardViewController.this;
                UdfpsKeyguardViewController.access$setLaunchTransitionFadingAway$p(udfpsKeyguardViewController, UdfpsKeyguardViewController.access$getKeyguardStateController$p(udfpsKeyguardViewController).isLaunchTransitionFadingAway());
                UdfpsKeyguardViewController.this.updatePauseAuth();
            }
        };
        this.activityLaunchAnimatorListener = new ActivityLaunchAnimator.Listener() { // from class: com.android.systemui.biometrics.UdfpsKeyguardViewController$activityLaunchAnimatorListener$1
            @Override // com.android.systemui.animation.ActivityLaunchAnimator.Listener
            public void onLaunchAnimationEnd() {
                UdfpsKeyguardViewController.access$setLaunchingActivity$p(UdfpsKeyguardViewController.this, false);
                UdfpsKeyguardViewController.this.updateAlpha();
            }

            @Override // com.android.systemui.animation.ActivityLaunchAnimator.Listener
            public void onLaunchAnimationProgress(float f) {
                UdfpsKeyguardViewController.access$setActivityLaunchProgress$p(UdfpsKeyguardViewController.this, f);
                UdfpsKeyguardViewController.this.updateAlpha();
            }

            @Override // com.android.systemui.animation.ActivityLaunchAnimator.Listener
            public void onLaunchAnimationStart() {
                UdfpsKeyguardViewController.access$setLaunchingActivity$p(UdfpsKeyguardViewController.this, true);
                UdfpsKeyguardViewController.access$setActivityLaunchProgress$p(UdfpsKeyguardViewController.this, ActionBarShadowController.ELEVATION_LOW);
                UdfpsKeyguardViewController.this.updateAlpha();
            }
        };
        this.statusBarKeyguardViewManagerCallback = new StatusBarKeyguardViewManager.KeyguardViewManagerCallback() { // from class: com.android.systemui.biometrics.UdfpsKeyguardViewController$statusBarKeyguardViewManagerCallback$1
            public void onQSExpansionChanged(float f) {
                UdfpsKeyguardViewController.this.qsExpansion = f;
                UdfpsKeyguardViewController.this.updateAlpha();
                UdfpsKeyguardViewController.this.updatePauseAuth();
            }

            public void onTouch(MotionEvent motionEvent) {
                float f;
                boolean z;
                UdfpsController udfpsController2;
                f = UdfpsKeyguardViewController.this.transitionToFullShadeProgress;
                if (f == ActionBarShadowController.ELEVATION_LOW) {
                    z = UdfpsKeyguardViewController.this.useExpandedOverlay;
                    if (z) {
                        return;
                    }
                    udfpsController2 = UdfpsKeyguardViewController.this.udfpsController;
                    udfpsController2.onTouch(motionEvent);
                }
            }
        };
        this.mAlternateBouncer = new StatusBarKeyguardViewManager.AlternateBouncer() { // from class: com.android.systemui.biometrics.UdfpsKeyguardViewController$mAlternateBouncer$1
            public void dump(PrintWriter printWriter) {
                printWriter.println(UdfpsKeyguardViewController.this.getTag());
            }

            public boolean hideAlternateBouncer() {
                return UdfpsKeyguardViewController.access$showUdfpsBouncer(UdfpsKeyguardViewController.this, false);
            }

            public boolean isShowingAlternateBouncer() {
                return UdfpsKeyguardViewController.access$getShowingUdfpsBouncer$p(UdfpsKeyguardViewController.this);
            }

            public void requestUdfps(boolean z, int i) {
                UdfpsKeyguardViewController.access$setUdfpsRequested$p(UdfpsKeyguardViewController.this, z);
                UdfpsKeyguardViewController.access$getView$p(UdfpsKeyguardViewController.this).requestUdfps(z, i);
                UdfpsKeyguardViewController.this.updateAlpha();
                UdfpsKeyguardViewController.this.updatePauseAuth();
            }

            public boolean showAlternateBouncer() {
                return UdfpsKeyguardViewController.access$showUdfpsBouncer(UdfpsKeyguardViewController.this, true);
            }
        };
        if (isEnabled) {
            RepeatWhenAttachedKt.repeatWhenAttached$default(udfpsKeyguardView, null, new AnonymousClass1(null), 1, null);
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsKeyguardViewController$keyguardStateControllerCallback$1.onLaunchTransitionFadingAwayChanged():void] */
    public static final /* synthetic */ KeyguardStateController access$getKeyguardStateController$p(UdfpsKeyguardViewController udfpsKeyguardViewController) {
        return udfpsKeyguardViewController.keyguardStateController;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsKeyguardViewController$shadeExpansionListener$1.onPanelExpansionChanged(com.android.systemui.shade.ShadeExpansionChangeEvent):void] */
    public static final /* synthetic */ StatusBarKeyguardViewManager access$getKeyguardViewManager$p(UdfpsKeyguardViewController udfpsKeyguardViewController) {
        return udfpsKeyguardViewController.keyguardViewManager;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsKeyguardViewController$stateListener$1.onDozeAmountChanged(float, float):void] */
    public static final /* synthetic */ float access$getLastDozeAmount$p(UdfpsKeyguardViewController udfpsKeyguardViewController) {
        return udfpsKeyguardViewController.lastDozeAmount;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsKeyguardViewController$mAlternateBouncer$1.isShowingAlternateBouncer():boolean] */
    public static final /* synthetic */ boolean access$getShowingUdfpsBouncer$p(UdfpsKeyguardViewController udfpsKeyguardViewController) {
        return udfpsKeyguardViewController.showingUdfpsBouncer;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsKeyguardViewController$stateListener$1.onDozeAmountChanged(float, float):void] */
    public static final /* synthetic */ UnlockedScreenOffAnimationController access$getUnlockedScreenOffAnimationController$p(UdfpsKeyguardViewController udfpsKeyguardViewController) {
        return udfpsKeyguardViewController.unlockedScreenOffAnimationController;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsKeyguardViewController$stateListener$1.onDozeAmountChanged(float, float):void] */
    public static final /* synthetic */ ValueAnimator access$getUnlockedScreenOffDozeAnimator$p(UdfpsKeyguardViewController udfpsKeyguardViewController) {
        return udfpsKeyguardViewController.unlockedScreenOffDozeAnimator;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsKeyguardViewController$configurationListener$1.onConfigChanged(android.content.res.Configuration):void, com.android.systemui.biometrics.UdfpsKeyguardViewController$configurationListener$1.onThemeChanged():void, com.android.systemui.biometrics.UdfpsKeyguardViewController$configurationListener$1.onUiModeChanged():void, com.android.systemui.biometrics.UdfpsKeyguardViewController$mAlternateBouncer$1.requestUdfps(boolean, int):void, com.android.systemui.biometrics.UdfpsKeyguardViewController$stateListener$1.onDozeAmountChanged(float, float):void, com.android.systemui.biometrics.UdfpsKeyguardViewController$unlockedScreenOffDozeAnimator$1$1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static final /* synthetic */ UdfpsKeyguardView access$getView$p(UdfpsKeyguardViewController udfpsKeyguardViewController) {
        return udfpsKeyguardViewController.view;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsKeyguardViewController$activityLaunchAnimatorListener$1.onLaunchAnimationProgress(float):void, com.android.systemui.biometrics.UdfpsKeyguardViewController$activityLaunchAnimatorListener$1.onLaunchAnimationStart():void] */
    public static final /* synthetic */ void access$setActivityLaunchProgress$p(UdfpsKeyguardViewController udfpsKeyguardViewController, float f) {
        udfpsKeyguardViewController.activityLaunchProgress = f;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsKeyguardViewController$mPrimaryBouncerExpansionCallback$1.onExpansionChanged(float):void] */
    public static final /* synthetic */ void access$setInputBouncerHiddenAmount$p(UdfpsKeyguardViewController udfpsKeyguardViewController, float f) {
        udfpsKeyguardViewController.inputBouncerHiddenAmount = f;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsKeyguardViewController$stateListener$1.onDozeAmountChanged(float, float):void] */
    public static final /* synthetic */ void access$setLastDozeAmount$p(UdfpsKeyguardViewController udfpsKeyguardViewController, float f) {
        udfpsKeyguardViewController.lastDozeAmount = f;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsKeyguardViewController$keyguardStateControllerCallback$1.onLaunchTransitionFadingAwayChanged():void] */
    public static final /* synthetic */ void access$setLaunchTransitionFadingAway$p(UdfpsKeyguardViewController udfpsKeyguardViewController, boolean z) {
        udfpsKeyguardViewController.launchTransitionFadingAway = z;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsKeyguardViewController$activityLaunchAnimatorListener$1.onLaunchAnimationEnd():void, com.android.systemui.biometrics.UdfpsKeyguardViewController$activityLaunchAnimatorListener$1.onLaunchAnimationStart():void] */
    public static final /* synthetic */ void access$setLaunchingActivity$p(UdfpsKeyguardViewController udfpsKeyguardViewController, boolean z) {
        udfpsKeyguardViewController.isLaunchingActivity = z;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsKeyguardViewController$shadeExpansionListener$1.onPanelExpansionChanged(com.android.systemui.shade.ShadeExpansionChangeEvent):void] */
    public static final /* synthetic */ void access$setPanelExpansionFraction$p(UdfpsKeyguardViewController udfpsKeyguardViewController, float f) {
        udfpsKeyguardViewController.panelExpansionFraction = f;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsKeyguardViewController$stateListener$1.onStateChanged(int):void] */
    public static final /* synthetic */ void access$setStatusBarState$p(UdfpsKeyguardViewController udfpsKeyguardViewController, int i) {
        udfpsKeyguardViewController.statusBarState = i;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsKeyguardViewController$mAlternateBouncer$1.requestUdfps(boolean, int):void] */
    public static final /* synthetic */ void access$setUdfpsRequested$p(UdfpsKeyguardViewController udfpsKeyguardViewController, boolean z) {
        udfpsKeyguardViewController.udfpsRequested = z;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsKeyguardViewController$mAlternateBouncer$1.hideAlternateBouncer():boolean, com.android.systemui.biometrics.UdfpsKeyguardViewController$mAlternateBouncer$1.showAlternateBouncer():boolean, com.android.systemui.biometrics.UdfpsKeyguardViewController$stateListener$1.onDozeAmountChanged(float, float):void] */
    public static final /* synthetic */ boolean access$showUdfpsBouncer(UdfpsKeyguardViewController udfpsKeyguardViewController, boolean z) {
        return udfpsKeyguardViewController.showUdfpsBouncer(z);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsKeyguardViewController$mPrimaryBouncerExpansionCallback$1.onVisibilityChanged(boolean):void] */
    public static final /* synthetic */ void access$updateBouncerHiddenAmount(UdfpsKeyguardViewController udfpsKeyguardViewController) {
        udfpsKeyguardViewController.updateBouncerHiddenAmount();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsKeyguardViewController$configurationListener$1.onConfigChanged(android.content.res.Configuration):void] */
    public static final /* synthetic */ void access$updateScaleFactor(UdfpsKeyguardViewController udfpsKeyguardViewController) {
        udfpsKeyguardViewController.updateScaleFactor();
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationViewController, com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        super.dump(printWriter, strArr);
        boolean z = this.isModernBouncerEnabled;
        printWriter.println("isModernBouncerEnabled=" + z);
        boolean z2 = this.showingUdfpsBouncer;
        printWriter.println("showingUdfpsAltBouncer=" + z2);
        boolean z3 = this.faceDetectRunning;
        printWriter.println("faceDetectRunning=" + z3);
        String statusBarState = StatusBarState.toString(this.statusBarState);
        printWriter.println("statusBarState=" + statusBarState);
        float f = this.transitionToFullShadeProgress;
        printWriter.println("transitionToFullShadeProgress=" + f);
        float f2 = this.qsExpansion;
        printWriter.println("qsExpansion=" + f2);
        float f3 = this.panelExpansionFraction;
        printWriter.println("panelExpansionFraction=" + f3);
        int unpausedAlpha = this.view.getUnpausedAlpha();
        printWriter.println("unpausedAlpha=" + unpausedAlpha);
        boolean z4 = this.udfpsRequested;
        printWriter.println("udfpsRequestedByApp=" + z4);
        boolean z5 = this.launchTransitionFadingAway;
        printWriter.println("launchTransitionFadingAway=" + z5);
        float f4 = this.lastDozeAmount;
        printWriter.println("lastDozeAmount=" + f4);
        if (this.isModernBouncerEnabled) {
            float f5 = this.inputBouncerExpansion;
            printWriter.println("inputBouncerExpansion=" + f5);
        } else {
            float f6 = this.inputBouncerHiddenAmount;
            printWriter.println("inputBouncerHiddenAmount=" + f6);
        }
        this.view.dump(printWriter);
    }

    public final float getInputBouncerHiddenAmt() {
        return this.isModernBouncerEnabled ? 1.0f - this.inputBouncerExpansion : this.inputBouncerHiddenAmount;
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationViewController
    public String getTag() {
        return "UdfpsKeyguardViewController";
    }

    public final boolean hasUdfpsBouncerShownWithMinTime() {
        return this.systemClock.uptimeMillis() - this.lastUdfpsBouncerShowTime > 200;
    }

    public final boolean isBouncerExpansionGreaterThan(float f) {
        boolean z = true;
        if (!this.isModernBouncerEnabled ? this.inputBouncerHiddenAmount >= f : this.inputBouncerExpansion < f) {
            z = false;
        }
        return z;
    }

    public final boolean isInputBouncerFullyVisible() {
        boolean z = true;
        if (!this.isModernBouncerEnabled ? !this.keyguardViewManager.isBouncerShowing() || this.keyguardViewManager.isShowingAlternateBouncer() : this.inputBouncerExpansion != 1.0f) {
            z = false;
        }
        return z;
    }

    public final Object listenForBouncerExpansion$frameworks__base__packages__SystemUI__android_common__SystemUI_core(CoroutineScope coroutineScope, Continuation<? super Job> continuation) {
        return BuildersKt.launch$default(coroutineScope, (CoroutineContext) null, (CoroutineStart) null, new UdfpsKeyguardViewController$listenForBouncerExpansion$2(this, null), 3, (Object) null);
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationViewController
    public boolean listenForTouchesOutsideView() {
        return true;
    }

    public final void maybeShowInputBouncer() {
        if (this.showingUdfpsBouncer && hasUdfpsBouncerShownWithMinTime()) {
            this.keyguardViewManager.showPrimaryBouncer(true);
        }
    }

    public void onInit() {
        super.onInit();
        this.keyguardViewManager.setAlternateBouncer(this.mAlternateBouncer);
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationViewController
    public void onTouchOutsideView() {
        maybeShowInputBouncer();
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationViewController
    public void onViewAttached() {
        super.onViewAttached();
        float dozeAmount = getStatusBarStateController().getDozeAmount();
        this.lastDozeAmount = dozeAmount;
        this.stateListener.onDozeAmountChanged(dozeAmount, dozeAmount);
        getStatusBarStateController().addCallback(this.stateListener);
        this.udfpsRequested = false;
        this.launchTransitionFadingAway = this.keyguardStateController.isLaunchTransitionFadingAway();
        this.keyguardStateController.addCallback(this.keyguardStateControllerCallback);
        this.statusBarState = getStatusBarStateController().getState();
        this.qsExpansion = this.keyguardViewManager.getQsExpansion();
        this.keyguardViewManager.addCallback(this.statusBarKeyguardViewManagerCallback);
        if (!this.isModernBouncerEnabled) {
            KeyguardBouncer primaryBouncer = this.keyguardViewManager.getPrimaryBouncer();
            if (primaryBouncer != null) {
                this.mPrimaryBouncerExpansionCallback.onExpansionChanged(primaryBouncer.getExpansion());
                primaryBouncer.addBouncerExpansionCallback(this.mPrimaryBouncerExpansionCallback);
            }
            updateBouncerHiddenAmount();
        }
        this.configurationController.addCallback(this.configurationListener);
        getShadeExpansionStateManager().addExpansionListener(this.shadeExpansionListener);
        updateScaleFactor();
        this.view.updatePadding();
        updateAlpha();
        updatePauseAuth();
        this.keyguardViewManager.setAlternateBouncer(this.mAlternateBouncer);
        this.lockScreenShadeTransitionController.setUdfpsKeyguardViewController(this);
        this.activityLaunchAnimator.addListener(this.activityLaunchAnimatorListener);
        this.view.mUseExpandedOverlay = this.useExpandedOverlay;
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationViewController
    public void onViewDetached() {
        KeyguardBouncer primaryBouncer;
        super.onViewDetached();
        this.faceDetectRunning = false;
        this.keyguardStateController.removeCallback(this.keyguardStateControllerCallback);
        getStatusBarStateController().removeCallback(this.stateListener);
        this.keyguardViewManager.removeAlternateAuthInterceptor(this.mAlternateBouncer);
        this.keyguardUpdateMonitor.requestFaceAuthOnOccludingApp(false);
        this.configurationController.removeCallback(this.configurationListener);
        getShadeExpansionStateManager().removeExpansionListener(this.shadeExpansionListener);
        if (this.lockScreenShadeTransitionController.getUdfpsKeyguardViewController() == this) {
            this.lockScreenShadeTransitionController.setUdfpsKeyguardViewController((UdfpsKeyguardViewController) null);
        }
        this.activityLaunchAnimator.removeListener(this.activityLaunchAnimatorListener);
        this.keyguardViewManager.removeCallback(this.statusBarKeyguardViewManagerCallback);
        if (this.isModernBouncerEnabled || (primaryBouncer = this.keyguardViewManager.getPrimaryBouncer()) == null) {
            return;
        }
        primaryBouncer.removeBouncerExpansionCallback(this.mPrimaryBouncerExpansionCallback);
    }

    public final void setTransitionToFullShadeProgress(float f) {
        this.transitionToFullShadeProgress = f;
        updateAlpha();
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationViewController
    public boolean shouldPauseAuth() {
        boolean z = false;
        if (this.showingUdfpsBouncer) {
            return false;
        }
        if (!this.udfpsRequested || getNotificationShadeVisible() || isInputBouncerFullyVisible() || !this.keyguardStateController.isShowing()) {
            if (this.launchTransitionFadingAway) {
                return true;
            }
            if (this.statusBarState != 1) {
                if (this.lastDozeAmount == ActionBarShadowController.ELEVATION_LOW) {
                    return true;
                }
            }
            if (isBouncerExpansionGreaterThan(0.5f)) {
                return true;
            }
            if (this.view.getUnpausedAlpha() < 25.5d) {
                z = true;
            }
            return z;
        }
        return false;
    }

    public final boolean showUdfpsBouncer(boolean z) {
        if (this.showingUdfpsBouncer == z) {
            return false;
        }
        boolean shouldPauseAuth = shouldPauseAuth();
        this.showingUdfpsBouncer = z;
        if (z) {
            this.lastUdfpsBouncerShowTime = this.systemClock.uptimeMillis();
        }
        if (this.showingUdfpsBouncer) {
            if (shouldPauseAuth) {
                this.view.animateInUdfpsBouncer(null);
            }
            if (this.keyguardStateController.isOccluded()) {
                this.keyguardUpdateMonitor.requestFaceAuthOnOccludingApp(true);
            }
            UdfpsKeyguardView udfpsKeyguardView = this.view;
            udfpsKeyguardView.announceForAccessibility(udfpsKeyguardView.getContext().getString(R$string.accessibility_fingerprint_bouncer));
        } else {
            this.keyguardUpdateMonitor.requestFaceAuthOnOccludingApp(false);
        }
        updateBouncerHiddenAmount();
        updateAlpha();
        updatePauseAuth();
        return true;
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationViewController
    public void updateAlpha() {
        int constrain = this.showingUdfpsBouncer ? 255 : (int) MathUtils.constrain(MathUtils.map(0.5f, 0.9f, (float) ActionBarShadowController.ELEVATION_LOW, 255.0f, this.udfpsRequested ? getInputBouncerHiddenAmt() : this.panelExpansionFraction), (float) ActionBarShadowController.ELEVATION_LOW, 255.0f);
        int i = constrain;
        if (!this.showingUdfpsBouncer) {
            int interpolation = (int) (((int) (constrain * (1.0f - Interpolators.EMPHASIZED_DECELERATE.getInterpolation(this.qsExpansion)))) * (1.0f - this.transitionToFullShadeProgress));
            int i2 = interpolation;
            if (this.isLaunchingActivity) {
                i2 = interpolation;
                if (!this.udfpsRequested) {
                    i2 = (int) (interpolation * (1.0f - this.activityLaunchProgress));
                }
            }
            i = (int) (i2 * this.view.getDialogSuggestedAlpha());
        }
        this.view.setUnpausedAlpha(i);
    }

    public final void updateBouncerHiddenAmount() {
        if (this.isModernBouncerEnabled) {
            return;
        }
        if (this.keyguardViewManager.isShowingAlternateBouncer() || !this.keyguardViewManager.primaryBouncerIsOrWillBeShowing()) {
            this.inputBouncerHiddenAmount = 1.0f;
        } else if (this.keyguardViewManager.isBouncerShowing()) {
            this.inputBouncerHiddenAmount = ActionBarShadowController.ELEVATION_LOW;
        }
    }

    public final void updateScaleFactor() {
        UdfpsOverlayParams udfpsOverlayParams = this.udfpsController.mOverlayParams;
        if (udfpsOverlayParams != null) {
            this.view.setScaleFactor(udfpsOverlayParams.getScaleFactor());
        }
    }
}