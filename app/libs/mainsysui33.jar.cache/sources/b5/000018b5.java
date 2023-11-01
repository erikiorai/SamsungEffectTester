package com.android.systemui.keyguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Handler;
import android.os.PowerManager;
import android.os.RemoteException;
import android.util.Log;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.View;
import androidx.core.math.MathUtils;
import com.android.keyguard.KeyguardViewController;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.shared.recents.utilities.Utilities;
import com.android.systemui.shared.system.ActivityManagerWrapper;
import com.android.systemui.shared.system.smartspace.ILauncherUnlockAnimationController;
import com.android.systemui.shared.system.smartspace.ISysuiUnlockAnimationController;
import com.android.systemui.shared.system.smartspace.SmartspaceState;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.Lazy;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/KeyguardUnlockAnimationController.class */
public final class KeyguardUnlockAnimationController extends ISysuiUnlockAnimationController.Stub implements KeyguardStateController.Callback {
    public static final Companion Companion = new Companion(null);
    public final Lazy<BiometricUnlockController> biometricUnlockControllerLazy;
    public final Context context;
    public final FeatureFlags featureFlags;
    public final Handler handler;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardViewController keyguardViewController;
    public final Lazy<KeyguardViewMediator> keyguardViewMediator;
    public boolean launcherPreparedForUnlock;
    public SmartspaceState launcherSmartspaceState;
    public ILauncherUnlockAnimationController launcherUnlockController;
    public View lockscreenSmartspace;
    public final NotificationShadeWindowController notificationShadeWindowController;
    public boolean playingCannedUnlockAnimation;
    public final PowerManager powerManager;
    public float roundedCornerRadius;
    public final SysuiStatusBarStateController statusBarStateController;
    public final ValueAnimator surfaceBehindEntryAnimator;
    public long surfaceBehindRemoteAnimationStartTime;
    public RemoteAnimationTarget[] surfaceBehindRemoteAnimationTargets;
    public SyncRtSurfaceTransactionApplier surfaceTransactionApplier;
    public final float[] tmpFloat;
    public boolean willUnlockWithInWindowLauncherAnimations;
    public boolean willUnlockWithSmartspaceTransition;
    public final ArrayList<KeyguardUnlockAnimationListener> listeners = new ArrayList<>();
    public float surfaceBehindAlpha = 1.0f;
    public ValueAnimator surfaceBehindAlphaAnimator = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
    public final Matrix surfaceBehindMatrix = new Matrix();

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/KeyguardUnlockAnimationController$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final boolean isFoldable(Context context) {
            return !(context.getResources().getIntArray(17236075).length == 0);
        }

        public final boolean isNexusLauncherUnderneath() {
            ComponentName componentName;
            String className;
            ActivityManager.RunningTaskInfo runningTask = ActivityManagerWrapper.getInstance().getRunningTask();
            return (runningTask == null || (componentName = runningTask.topActivity) == null || (className = componentName.getClassName()) == null) ? false : className.equals("com.google.android.apps.nexuslauncher.NexusLauncherActivity");
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/KeyguardUnlockAnimationController$KeyguardUnlockAnimationListener.class */
    public interface KeyguardUnlockAnimationListener {
        default void onUnlockAnimationFinished() {
        }

        default void onUnlockAnimationStarted(boolean z, boolean z2, long j, long j2) {
        }
    }

    public KeyguardUnlockAnimationController(Context context, KeyguardStateController keyguardStateController, Lazy<KeyguardViewMediator> lazy, KeyguardViewController keyguardViewController, FeatureFlags featureFlags, Lazy<BiometricUnlockController> lazy2, SysuiStatusBarStateController sysuiStatusBarStateController, NotificationShadeWindowController notificationShadeWindowController, PowerManager powerManager) {
        this.context = context;
        this.keyguardStateController = keyguardStateController;
        this.keyguardViewMediator = lazy;
        this.keyguardViewController = keyguardViewController;
        this.featureFlags = featureFlags;
        this.biometricUnlockControllerLazy = lazy2;
        this.statusBarStateController = sysuiStatusBarStateController;
        this.notificationShadeWindowController = notificationShadeWindowController;
        this.powerManager = powerManager;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
        this.surfaceBehindEntryAnimator = ofFloat;
        this.handler = new Handler();
        this.tmpFloat = new float[9];
        ValueAnimator valueAnimator = this.surfaceBehindAlphaAnimator;
        valueAnimator.setDuration(175L);
        valueAnimator.setInterpolator(Interpolators.LINEAR);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.KeyguardUnlockAnimationController$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                KeyguardUnlockAnimationController.access$setSurfaceBehindAlpha$p(KeyguardUnlockAnimationController.this, ((Float) valueAnimator2.getAnimatedValue()).floatValue());
                KeyguardUnlockAnimationController.access$updateSurfaceBehindAppearAmount(KeyguardUnlockAnimationController.this);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.KeyguardUnlockAnimationController$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                float f;
                float f2;
                Lazy lazy3;
                f = KeyguardUnlockAnimationController.this.surfaceBehindAlpha;
                if (f == ActionBarShadowController.ELEVATION_LOW) {
                    Log.d("KeyguardUnlock", "surfaceBehindAlphaAnimator#onAnimationEnd");
                    KeyguardUnlockAnimationController.this.surfaceBehindRemoteAnimationTargets = null;
                    lazy3 = KeyguardUnlockAnimationController.this.keyguardViewMediator;
                    ((KeyguardViewMediator) lazy3.get()).finishSurfaceBehindRemoteAnimation(false);
                    return;
                }
                f2 = KeyguardUnlockAnimationController.this.surfaceBehindAlpha;
                Log.d("KeyguardUnlock", "skip finishSurfaceBehindRemoteAnimation surfaceBehindAlpha=" + f2);
            }
        });
        ofFloat.setDuration(200L);
        ofFloat.setStartDelay(75L);
        ofFloat.setInterpolator(Interpolators.TOUCH_RESPONSE);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.keyguard.KeyguardUnlockAnimationController$2$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                KeyguardUnlockAnimationController.access$setSurfaceBehindAlpha$p(KeyguardUnlockAnimationController.this, ((Float) valueAnimator2.getAnimatedValue()).floatValue());
                KeyguardUnlockAnimationController.this.setSurfaceBehindAppearAmount(((Float) valueAnimator2.getAnimatedValue()).floatValue());
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.keyguard.KeyguardUnlockAnimationController$2$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                Lazy lazy3;
                Log.d("KeyguardUnlock", "surfaceBehindEntryAnimator#onAnimationEnd");
                KeyguardUnlockAnimationController.this.setPlayingCannedUnlockAnimation(false);
                lazy3 = KeyguardUnlockAnimationController.this.keyguardViewMediator;
                ((KeyguardViewMediator) lazy3.get()).exitKeyguardAndFinishSurfaceBehindRemoteAnimation(false);
            }
        });
        keyguardStateController.addCallback(this);
        this.roundedCornerRadius = context.getResources().getDimensionPixelSize(17105515);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.KeyguardUnlockAnimationController$1$1.onAnimationUpdate(android.animation.ValueAnimator):void, com.android.systemui.keyguard.KeyguardUnlockAnimationController$2$1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static final /* synthetic */ void access$setSurfaceBehindAlpha$p(KeyguardUnlockAnimationController keyguardUnlockAnimationController, float f) {
        keyguardUnlockAnimationController.surfaceBehindAlpha = f;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.keyguard.KeyguardUnlockAnimationController$1$1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static final /* synthetic */ void access$updateSurfaceBehindAppearAmount(KeyguardUnlockAnimationController keyguardUnlockAnimationController) {
        keyguardUnlockAnimationController.updateSurfaceBehindAppearAmount();
    }

    public static /* synthetic */ void getSurfaceBehindAlphaAnimator$annotations() {
    }

    public static /* synthetic */ void getSurfaceBehindEntryAnimator$annotations() {
    }

    public static /* synthetic */ void getSurfaceTransactionApplier$annotations() {
    }

    public static /* synthetic */ void getWillUnlockWithInWindowLauncherAnimations$annotations() {
    }

    public final void addKeyguardUnlockAnimationListener(KeyguardUnlockAnimationListener keyguardUnlockAnimationListener) {
        this.listeners.add(keyguardUnlockAnimationListener);
    }

    public final void applyParamsToSurface(SyncRtSurfaceTransactionApplier.SurfaceParams surfaceParams) {
        SyncRtSurfaceTransactionApplier syncRtSurfaceTransactionApplier = this.surfaceTransactionApplier;
        Intrinsics.checkNotNull(syncRtSurfaceTransactionApplier);
        syncRtSurfaceTransactionApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{surfaceParams});
    }

    public final boolean canPerformInWindowLauncherAnimations() {
        Companion companion = Companion;
        return (!companion.isNexusLauncherUnderneath() || this.notificationShadeWindowController.isLaunchingActivity() || this.launcherUnlockController == null || companion.isFoldable(this.context)) ? false : true;
    }

    public final void fadeInSurfaceBehind() {
        Log.d("KeyguardUnlock", "fadeInSurfaceBehind");
        this.surfaceBehindAlphaAnimator.cancel();
        this.surfaceBehindAlphaAnimator.start();
    }

    public final void fadeOutSurfaceBehind() {
        Log.d("KeyguardUnlock", "fadeOutSurfaceBehind");
        this.surfaceBehindAlphaAnimator.cancel();
        this.surfaceBehindAlphaAnimator.reverse();
    }

    public final void finishKeyguardExitRemoteAnimationIfReachThreshold() {
        if (KeyguardService.sEnableRemoteKeyguardGoingAwayAnimation && this.keyguardStateController.isShowing() && ((KeyguardViewMediator) this.keyguardViewMediator.get()).requestedShowSurfaceBehindKeyguard() && ((KeyguardViewMediator) this.keyguardViewMediator.get()).isAnimatingBetweenKeyguardAndSurfaceBehindOrWillBe()) {
            float dismissAmount = this.keyguardStateController.getDismissAmount();
            if (dismissAmount >= 1.0f || (this.keyguardStateController.isDismissingFromSwipe() && !this.keyguardStateController.isFlingingToDismissKeyguardDuringSwipeGesture() && dismissAmount >= 0.3f)) {
                setSurfaceBehindAppearAmount(1.0f);
                ((KeyguardViewMediator) this.keyguardViewMediator.get()).exitKeyguardAndFinishSurfaceBehindRemoteAnimation(false);
            }
        }
    }

    public final void hideKeyguardViewAfterRemoteAnimation() {
        if (this.keyguardStateController.isShowing()) {
            this.keyguardViewController.hide(this.surfaceBehindRemoteAnimationStartTime, 0L);
        } else {
            Log.e("KeyguardUnlock", "#hideKeyguardViewAfterRemoteAnimation called when keyguard view is not showing. Ignoring...");
        }
    }

    public final boolean isAnimatingBetweenKeyguardAndSurfaceBehind() {
        return ((KeyguardViewMediator) this.keyguardViewMediator.get()).isAnimatingBetweenKeyguardAndSurfaceBehind();
    }

    public final boolean isPlayingCannedUnlockAnimation() {
        return this.playingCannedUnlockAnimation;
    }

    public final boolean isUnlockingWithSmartSpaceTransition() {
        return this.willUnlockWithSmartspaceTransition;
    }

    public final void logInWindowAnimationConditions() {
        Log.wtf("KeyguardUnlock", "canPerformInWindowLauncherAnimations expected all of these to be true: ");
        Companion companion = Companion;
        boolean isNexusLauncherUnderneath = companion.isNexusLauncherUnderneath();
        Log.wtf("KeyguardUnlock", "  isNexusLauncherUnderneath: " + isNexusLauncherUnderneath);
        boolean isLaunchingActivity = this.notificationShadeWindowController.isLaunchingActivity();
        StringBuilder sb = new StringBuilder();
        sb.append("  !notificationShadeWindowController.isLaunchingActivity: ");
        sb.append(!isLaunchingActivity);
        Log.wtf("KeyguardUnlock", sb.toString());
        boolean z = this.launcherUnlockController != null;
        Log.wtf("KeyguardUnlock", "  launcherUnlockController != null: " + z);
        boolean isFoldable = companion.isFoldable(this.context);
        StringBuilder sb2 = new StringBuilder();
        sb2.append("  !isFoldable(context): ");
        sb2.append(!isFoldable);
        Log.wtf("KeyguardUnlock", sb2.toString());
    }

    public final void notifyFinishedKeyguardExitAnimation(boolean z) {
        this.handler.removeCallbacksAndMessages(null);
        this.surfaceBehindAlpha = 1.0f;
        setSurfaceBehindAppearAmount(1.0f);
        this.surfaceBehindAlphaAnimator.cancel();
        this.surfaceBehindEntryAnimator.cancel();
        try {
            ILauncherUnlockAnimationController iLauncherUnlockAnimationController = this.launcherUnlockController;
            if (iLauncherUnlockAnimationController != null) {
                iLauncherUnlockAnimationController.setUnlockAmount(1.0f, false);
            }
        } catch (RemoteException e) {
            Log.e("KeyguardUnlock", "Remote exception in notifyFinishedKeyguardExitAnimation", e);
        }
        this.surfaceBehindRemoteAnimationTargets = null;
        this.playingCannedUnlockAnimation = false;
        this.willUnlockWithInWindowLauncherAnimations = false;
        this.willUnlockWithSmartspaceTransition = false;
        View view = this.lockscreenSmartspace;
        if (view != null) {
            view.setVisibility(0);
        }
        for (KeyguardUnlockAnimationListener keyguardUnlockAnimationListener : this.listeners) {
            keyguardUnlockAnimationListener.onUnlockAnimationFinished();
        }
    }

    public final void notifyStartSurfaceBehindRemoteAnimation(RemoteAnimationTarget[] remoteAnimationTargetArr, long j, boolean z) {
        if (this.surfaceTransactionApplier == null) {
            this.surfaceTransactionApplier = new SyncRtSurfaceTransactionApplier(this.keyguardViewController.getViewRootImpl().getView());
        }
        this.surfaceBehindRemoteAnimationTargets = remoteAnimationTargetArr;
        this.surfaceBehindRemoteAnimationStartTime = j;
        if (!z) {
            playCannedUnlockAnimation();
        } else if (this.keyguardStateController.isFlingingToDismissKeyguard()) {
            playCannedUnlockAnimation();
        } else if (this.keyguardStateController.isDismissingFromSwipe() && this.willUnlockWithInWindowLauncherAnimations) {
            this.surfaceBehindAlpha = 1.0f;
            setSurfaceBehindAppearAmount(1.0f);
            ILauncherUnlockAnimationController iLauncherUnlockAnimationController = this.launcherUnlockController;
            if (iLauncherUnlockAnimationController != null) {
                iLauncherUnlockAnimationController.playUnlockAnimation(true, 300L, 0L);
            }
            this.launcherPreparedForUnlock = false;
        } else {
            fadeInSurfaceBehind();
        }
        for (KeyguardUnlockAnimationListener keyguardUnlockAnimationListener : this.listeners) {
            keyguardUnlockAnimationListener.onUnlockAnimationStarted(this.playingCannedUnlockAnimation, ((BiometricUnlockController) this.biometricUnlockControllerLazy.get()).isWakeAndUnlock(), 100L, 633L);
        }
        finishKeyguardExitRemoteAnimationIfReachThreshold();
    }

    public void onKeyguardDismissAmountChanged() {
        if (willHandleUnlockAnimation() && this.keyguardStateController.isShowing() && !this.playingCannedUnlockAnimation) {
            showOrHideSurfaceIfDismissAmountThresholdsReached();
            if ((((KeyguardViewMediator) this.keyguardViewMediator.get()).requestedShowSurfaceBehindKeyguard() || ((KeyguardViewMediator) this.keyguardViewMediator.get()).isAnimatingBetweenKeyguardAndSurfaceBehindOrWillBe()) && !this.playingCannedUnlockAnimation) {
                updateSurfaceBehindAppearAmount();
            }
        }
    }

    public void onKeyguardGoingAwayChanged() {
        ILauncherUnlockAnimationController iLauncherUnlockAnimationController;
        if (this.keyguardStateController.isKeyguardGoingAway() && !this.statusBarStateController.leaveOpenOnKeyguardHide()) {
            prepareForInWindowLauncherAnimations();
        }
        if (this.keyguardStateController.isKeyguardGoingAway() || !this.willUnlockWithInWindowLauncherAnimations || (iLauncherUnlockAnimationController = this.launcherUnlockController) == null) {
            return;
        }
        iLauncherUnlockAnimationController.setUnlockAmount(1.0f, true);
    }

    public void onLauncherSmartspaceStateUpdated(SmartspaceState smartspaceState) {
        this.launcherSmartspaceState = smartspaceState;
    }

    public final void playCannedUnlockAnimation() {
        Log.d("KeyguardUnlock", "playCannedUnlockAnimation");
        this.playingCannedUnlockAnimation = true;
        if (this.willUnlockWithInWindowLauncherAnimations) {
            Log.d("KeyguardUnlock", "playCannedUnlockAnimation, unlockToLauncherWithInWindowAnimations");
            unlockToLauncherWithInWindowAnimations();
        } else if (((BiometricUnlockController) this.biometricUnlockControllerLazy.get()).isWakeAndUnlock()) {
            Log.d("KeyguardUnlock", "playCannedUnlockAnimation, isWakeAndUnlock");
            setSurfaceBehindAppearAmount(1.0f);
            ((KeyguardViewMediator) this.keyguardViewMediator.get()).exitKeyguardAndFinishSurfaceBehindRemoteAnimation(false);
        } else {
            Log.d("KeyguardUnlock", "playCannedUnlockAnimation, surfaceBehindEntryAnimator#start");
            this.surfaceBehindEntryAnimator.start();
        }
        if (!this.launcherPreparedForUnlock || this.willUnlockWithInWindowLauncherAnimations) {
            return;
        }
        Log.wtf("KeyguardUnlock", "Launcher is prepared for unlock, so we should have started the in-window animation, however we apparently did not.");
        logInWindowAnimationConditions();
    }

    public final void prepareForInWindowLauncherAnimations() {
        boolean canPerformInWindowLauncherAnimations = canPerformInWindowLauncherAnimations();
        this.willUnlockWithInWindowLauncherAnimations = canPerformInWindowLauncherAnimations;
        if (canPerformInWindowLauncherAnimations) {
            this.willUnlockWithSmartspaceTransition = shouldPerformSmartspaceTransition();
            Rect rect = new Rect();
            if (this.willUnlockWithSmartspaceTransition) {
                Rect rect2 = new Rect();
                View view = this.lockscreenSmartspace;
                Intrinsics.checkNotNull(view);
                view.getBoundsOnScreen(rect2);
                View view2 = this.lockscreenSmartspace;
                Intrinsics.checkNotNull(view2);
                int paddingLeft = view2.getPaddingLeft();
                View view3 = this.lockscreenSmartspace;
                Intrinsics.checkNotNull(view3);
                rect2.offset(paddingLeft, view3.getPaddingTop());
                View view4 = this.lockscreenSmartspace;
                BcSmartspaceDataPlugin.SmartspaceView smartspaceView = view4 instanceof BcSmartspaceDataPlugin.SmartspaceView ? (BcSmartspaceDataPlugin.SmartspaceView) view4 : null;
                rect2.offset(0, smartspaceView != null ? smartspaceView.getCurrentCardTopPadding() : 0);
                rect = rect2;
            }
            BcSmartspaceDataPlugin.SmartspaceView smartspaceView2 = (BcSmartspaceDataPlugin.SmartspaceView) this.lockscreenSmartspace;
            int selectedPage = smartspaceView2 != null ? smartspaceView2.getSelectedPage() : -1;
            try {
                ILauncherUnlockAnimationController iLauncherUnlockAnimationController = this.launcherUnlockController;
                if (iLauncherUnlockAnimationController != null) {
                    iLauncherUnlockAnimationController.prepareForUnlock(this.willUnlockWithSmartspaceTransition, rect, selectedPage);
                }
                this.launcherPreparedForUnlock = true;
            } catch (RemoteException e) {
                Log.e("KeyguardUnlock", "Remote exception in prepareForInWindowUnlockAnimations.", e);
            }
        }
    }

    public final void removeKeyguardUnlockAnimationListener(KeyguardUnlockAnimationListener keyguardUnlockAnimationListener) {
        this.listeners.remove(keyguardUnlockAnimationListener);
    }

    public void setLauncherUnlockController(ILauncherUnlockAnimationController iLauncherUnlockAnimationController) {
        this.launcherUnlockController = iLauncherUnlockAnimationController;
    }

    public final void setLockscreenSmartspace(View view) {
        this.lockscreenSmartspace = view;
    }

    public final void setPlayingCannedUnlockAnimation(boolean z) {
        this.playingCannedUnlockAnimation = z;
    }

    public final void setSurfaceBehindAppearAmount(float f) {
        RemoteAnimationTarget[] remoteAnimationTargetArr = this.surfaceBehindRemoteAnimationTargets;
        if (remoteAnimationTargetArr != null) {
            for (RemoteAnimationTarget remoteAnimationTarget : remoteAnimationTargetArr) {
                int height = remoteAnimationTarget.screenSpaceBounds.height();
                float clamp = (MathUtils.clamp(f, (float) ActionBarShadowController.ELEVATION_LOW, 1.0f) * 0.050000012f) + 0.95f;
                float f2 = clamp;
                if (this.keyguardStateController.isDismissingFromSwipe()) {
                    f2 = clamp;
                    if (this.willUnlockWithInWindowLauncherAnimations) {
                        f2 = 1.0f;
                    }
                }
                Matrix matrix = this.surfaceBehindMatrix;
                float f3 = remoteAnimationTarget.screenSpaceBounds.left;
                float f4 = height;
                matrix.setTranslate(f3, 0.05f * f4 * (1.0f - f));
                this.surfaceBehindMatrix.postScale(f2, f2, this.keyguardViewController.getViewRootImpl().getWidth() / 2.0f, f4 * 0.66f);
                float f5 = this.keyguardStateController.isSnappingKeyguardBackAfterSwipe() ? f : !this.powerManager.isInteractive() ? 0.0f : this.surfaceBehindAlpha;
                SurfaceControl surfaceControl = remoteAnimationTarget.leash;
                View view = this.keyguardViewController.getViewRootImpl().getView();
                if (!(view != null && view.getVisibility() == 0)) {
                    if (surfaceControl != null && surfaceControl.isValid()) {
                        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
                        transaction.setMatrix(surfaceControl, this.surfaceBehindMatrix, this.tmpFloat);
                        transaction.setCornerRadius(surfaceControl, this.roundedCornerRadius);
                        transaction.setAlpha(surfaceControl, f5);
                        transaction.apply();
                    }
                }
                applyParamsToSurface(new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(remoteAnimationTarget.leash).withMatrix(this.surfaceBehindMatrix).withCornerRadius(this.roundedCornerRadius).withAlpha(f5).build());
            }
        }
    }

    public final boolean shouldPerformSmartspaceTransition() {
        SmartspaceState smartspaceState;
        if (!this.featureFlags.isEnabled(Flags.INSTANCE.getSMARTSPACE_SHARED_ELEMENT_TRANSITION_ENABLED()) || this.launcherUnlockController == null || this.lockscreenSmartspace == null || (smartspaceState = this.launcherSmartspaceState) == null) {
            return false;
        }
        if ((smartspaceState != null && smartspaceState.getVisibleOnScreen()) && Companion.isNexusLauncherUnderneath() && !((BiometricUnlockController) this.biometricUnlockControllerLazy.get()).isWakeAndUnlock()) {
            return ((!this.keyguardStateController.canDismissLockScreen() && !((BiometricUnlockController) this.biometricUnlockControllerLazy.get()).isBiometricUnlock()) || this.keyguardStateController.isBouncerShowing() || this.keyguardStateController.isFlingingToDismissKeyguardDuringSwipeGesture() || this.keyguardStateController.isDismissingFromSwipe() || Utilities.isTablet(this.context)) ? false : true;
        }
        return false;
    }

    public final void showOrHideSurfaceIfDismissAmountThresholdsReached() {
        if (this.featureFlags.isEnabled(Flags.INSTANCE.getNEW_UNLOCK_SWIPE_ANIMATION()) && !this.playingCannedUnlockAnimation && this.keyguardStateController.isShowing()) {
            float dismissAmount = this.keyguardStateController.getDismissAmount();
            if (dismissAmount >= 0.15f && !((KeyguardViewMediator) this.keyguardViewMediator.get()).requestedShowSurfaceBehindKeyguard()) {
                ((KeyguardViewMediator) this.keyguardViewMediator.get()).showSurfaceBehindKeyguard();
            } else if (dismissAmount < 0.15f && ((KeyguardViewMediator) this.keyguardViewMediator.get()).requestedShowSurfaceBehindKeyguard()) {
                ((KeyguardViewMediator) this.keyguardViewMediator.get()).hideSurfaceBehindKeyguard();
                fadeOutSurfaceBehind();
            }
            finishKeyguardExitRemoteAnimationIfReachThreshold();
        }
    }

    public final void unlockToLauncherWithInWindowAnimations() {
        setSurfaceBehindAppearAmount(1.0f);
        ILauncherUnlockAnimationController iLauncherUnlockAnimationController = this.launcherUnlockController;
        if (iLauncherUnlockAnimationController != null) {
            iLauncherUnlockAnimationController.playUnlockAnimation(true, 633L, 100L);
        }
        this.launcherPreparedForUnlock = false;
        View view = this.lockscreenSmartspace;
        if (view != null) {
            view.setVisibility(4);
        }
        this.handler.postDelayed(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardUnlockAnimationController$unlockToLauncherWithInWindowAnimations$1
            @Override // java.lang.Runnable
            public final void run() {
                Lazy lazy;
                Lazy lazy2;
                KeyguardStateController keyguardStateController;
                lazy = KeyguardUnlockAnimationController.this.keyguardViewMediator;
                if (((KeyguardViewMediator) lazy.get()).isShowingAndNotOccluded()) {
                    keyguardStateController = KeyguardUnlockAnimationController.this.keyguardStateController;
                    if (!keyguardStateController.isKeyguardGoingAway()) {
                        Log.e("KeyguardUnlock", "Finish keyguard exit animation delayed Runnable ran, but we are showing and not going away.");
                        return;
                    }
                }
                lazy2 = KeyguardUnlockAnimationController.this.keyguardViewMediator;
                ((KeyguardViewMediator) lazy2.get()).exitKeyguardAndFinishSurfaceBehindRemoteAnimation(false);
            }
        }, 100L);
    }

    public final void updateSurfaceBehindAppearAmount() {
        if (this.surfaceBehindRemoteAnimationTargets == null || this.playingCannedUnlockAnimation) {
            return;
        }
        if (this.keyguardStateController.isFlingingToDismissKeyguard()) {
            setSurfaceBehindAppearAmount(this.keyguardStateController.getDismissAmount());
        } else if (this.keyguardStateController.isDismissingFromSwipe() || this.keyguardStateController.isSnappingKeyguardBackAfterSwipe()) {
            setSurfaceBehindAppearAmount((this.keyguardStateController.getDismissAmount() - 0.15f) / 0.15f);
        }
    }

    public final boolean willHandleUnlockAnimation() {
        return KeyguardService.sEnableRemoteKeyguardGoingAwayAnimation;
    }
}