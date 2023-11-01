package com.android.systemui.navigationbar.gestural;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Handler;
import android.os.SystemClock;
import android.util.MathUtils;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.PathInterpolator;
import androidx.dynamicanimation.animation.DynamicAnimation;
import com.android.internal.util.LatencyTracker;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.navigationbar.gestural.BackPanelController;
import com.android.systemui.navigationbar.gestural.EdgePanelParams;
import com.android.systemui.plugins.NavigationEdgeBackPlugin;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.ViewController;
import java.io.PrintWriter;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/gestural/BackPanelController.class */
public final class BackPanelController extends ViewController<BackPanel> implements NavigationEdgeBackPlugin {
    public NavigationEdgeBackPlugin.BackCallback backCallback;
    public final ConfigurationController configurationController;
    public final BackPanelController$configurationListener$1 configurationListener;
    public GestureState currentState;
    public final Point displaySize;
    public final Runnable failsafeRunnable;
    public float fullyStretchedThreshold;
    public long gestureStartTime;
    public boolean hasHapticPlayed;
    public boolean hasPassedDragSlop;
    public WindowManager.LayoutParams layoutParams;
    public final Handler mainHandler;
    public EdgePanelParams params;
    public GestureState previousState;
    public float previousXTranslation;
    public final DelayedOnAnimationEndListener setCommittedEndListener;
    public final DelayedOnAnimationEndListener setGoneEndListener;
    public float startX;
    public float startY;
    public float totalTouchDelta;
    public VelocityTracker velocityTracker;
    public long vibrationTime;
    public final VibratorHelper vibratorHelper;
    public final ViewConfiguration viewConfiguration;
    public final WindowManager windowManager;

    /* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/gestural/BackPanelController$DelayedOnAnimationEndListener.class */
    public final class DelayedOnAnimationEndListener implements DynamicAnimation.OnAnimationEndListener {
        public final Handler handler;
        public final long minDuration;
        public final Runnable runnable;

        public DelayedOnAnimationEndListener(Handler handler, Runnable runnable, long j) {
            BackPanelController.this = r5;
            this.handler = handler;
            this.runnable = runnable;
            this.minDuration = j;
        }

        @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
        public void onAnimationEnd(DynamicAnimation<?> dynamicAnimation, boolean z, float f, float f2) {
            dynamicAnimation.removeEndListener(this);
            if (z) {
                return;
            }
            this.handler.postDelayed(this.runnable, Math.max(0L, this.minDuration - (SystemClock.uptimeMillis() - BackPanelController.this.gestureStartTime)));
        }

        public final void runNow$frameworks__base__packages__SystemUI__android_common__SystemUI_core() {
            this.runnable.run();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/gestural/BackPanelController$Factory.class */
    public static final class Factory {
        public final ConfigurationController configurationController;
        public final LatencyTracker latencyTracker;
        public final Handler mainHandler;
        public final VibratorHelper vibratorHelper;
        public final ViewConfiguration viewConfiguration;
        public final WindowManager windowManager;

        public Factory(WindowManager windowManager, ViewConfiguration viewConfiguration, Handler handler, VibratorHelper vibratorHelper, ConfigurationController configurationController, LatencyTracker latencyTracker) {
            this.windowManager = windowManager;
            this.viewConfiguration = viewConfiguration;
            this.mainHandler = handler;
            this.vibratorHelper = vibratorHelper;
            this.configurationController = configurationController;
            this.latencyTracker = latencyTracker;
        }

        public final BackPanelController create(Context context) {
            BackPanelController backPanelController = new BackPanelController(context, this.windowManager, this.viewConfiguration, this.mainHandler, this.vibratorHelper, this.configurationController, this.latencyTracker, null);
            backPanelController.init();
            return backPanelController;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/gestural/BackPanelController$GestureState.class */
    public enum GestureState {
        GONE,
        ENTRY,
        ACTIVE,
        INACTIVE,
        FLUNG,
        COMMITTED,
        CANCELLED;

        /* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/gestural/BackPanelController$GestureState$WhenMappings.class */
        public final /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[GestureState.values().length];
                try {
                    iArr[GestureState.ENTRY.ordinal()] = 1;
                } catch (NoSuchFieldError e) {
                }
                try {
                    iArr[GestureState.ACTIVE.ordinal()] = 2;
                } catch (NoSuchFieldError e2) {
                }
                try {
                    iArr[GestureState.INACTIVE.ordinal()] = 3;
                } catch (NoSuchFieldError e3) {
                }
                try {
                    iArr[GestureState.GONE.ordinal()] = 4;
                } catch (NoSuchFieldError e4) {
                }
                try {
                    iArr[GestureState.FLUNG.ordinal()] = 5;
                } catch (NoSuchFieldError e5) {
                }
                try {
                    iArr[GestureState.COMMITTED.ordinal()] = 6;
                } catch (NoSuchFieldError e6) {
                }
                try {
                    iArr[GestureState.CANCELLED.ordinal()] = 7;
                } catch (NoSuchFieldError e7) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public final boolean isInteractive() {
            boolean z;
            switch (WhenMappings.$EnumSwitchMapping$0[ordinal()]) {
                case 1:
                case 2:
                case 3:
                    z = true;
                    break;
                case 4:
                case 5:
                case 6:
                case 7:
                    z = false;
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
            return z;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/gestural/BackPanelController$WhenMappings.class */
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[GestureState.values().length];
            try {
                iArr[GestureState.ENTRY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[GestureState.ACTIVE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[GestureState.INACTIVE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[GestureState.FLUNG.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[GestureState.CANCELLED.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                iArr[GestureState.GONE.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                iArr[GestureState.COMMITTED.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    /* JADX WARN: Type inference failed for: r1v13, types: [com.android.systemui.navigationbar.gestural.BackPanelController$configurationListener$1] */
    public BackPanelController(Context context, WindowManager windowManager, ViewConfiguration viewConfiguration, Handler handler, VibratorHelper vibratorHelper, ConfigurationController configurationController, LatencyTracker latencyTracker) {
        super(new BackPanel(context, latencyTracker));
        this.windowManager = windowManager;
        this.viewConfiguration = viewConfiguration;
        this.mainHandler = handler;
        this.vibratorHelper = vibratorHelper;
        this.configurationController = configurationController;
        this.params = new EdgePanelParams(getResources());
        GestureState gestureState = GestureState.GONE;
        this.currentState = gestureState;
        this.previousState = gestureState;
        this.displaySize = new Point();
        this.failsafeRunnable = new Runnable() { // from class: com.android.systemui.navigationbar.gestural.BackPanelController$failsafeRunnable$1
            @Override // java.lang.Runnable
            public final void run() {
                BackPanelController.access$onFailsafe(BackPanelController.this);
            }
        };
        this.setCommittedEndListener = new DelayedOnAnimationEndListener(handler, new Runnable() { // from class: com.android.systemui.navigationbar.gestural.BackPanelController$setCommittedEndListener$1
            @Override // java.lang.Runnable
            public final void run() {
                BackPanelController.updateArrowState$default(BackPanelController.this, BackPanelController.GestureState.COMMITTED, false, 2, null);
            }
        }, 235L);
        this.setGoneEndListener = new DelayedOnAnimationEndListener(handler, new Runnable() { // from class: com.android.systemui.navigationbar.gestural.BackPanelController$setGoneEndListener$1
            @Override // java.lang.Runnable
            public final void run() {
                BackPanelController.access$cancelFailsafe(BackPanelController.this);
                BackPanelController.updateArrowState$default(BackPanelController.this, BackPanelController.GestureState.GONE, false, 2, null);
            }
        }, 0L);
        this.configurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.systemui.navigationbar.gestural.BackPanelController$configurationListener$1
            public void onConfigChanged(Configuration configuration) {
                BackPanelController.access$updateConfiguration(BackPanelController.this);
            }

            public void onLayoutDirectionChanged(boolean z) {
                BackPanelController.access$updateArrowDirection(BackPanelController.this, z);
            }
        };
    }

    public /* synthetic */ BackPanelController(Context context, WindowManager windowManager, ViewConfiguration viewConfiguration, Handler handler, VibratorHelper vibratorHelper, ConfigurationController configurationController, LatencyTracker latencyTracker, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, windowManager, viewConfiguration, handler, vibratorHelper, configurationController, latencyTracker);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.gestural.BackPanelController$setGoneEndListener$1.run():void] */
    public static final /* synthetic */ void access$cancelFailsafe(BackPanelController backPanelController) {
        backPanelController.cancelFailsafe();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.gestural.BackPanelController$failsafeRunnable$1.run():void] */
    public static final /* synthetic */ void access$onFailsafe(BackPanelController backPanelController) {
        backPanelController.onFailsafe();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.gestural.BackPanelController$configurationListener$1.onLayoutDirectionChanged(boolean):void] */
    public static final /* synthetic */ void access$updateArrowDirection(BackPanelController backPanelController, boolean z) {
        backPanelController.updateArrowDirection(z);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.navigationbar.gestural.BackPanelController$configurationListener$1.onConfigChanged(android.content.res.Configuration):void] */
    public static final /* synthetic */ void access$updateConfiguration(BackPanelController backPanelController) {
        backPanelController.updateConfiguration();
    }

    public static /* synthetic */ void updateArrowState$default(BackPanelController backPanelController, GestureState gestureState, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        backPanelController.updateArrowState(gestureState, z);
    }

    public final void cancelFailsafe() {
        this.mainHandler.removeCallbacks(this.failsafeRunnable);
    }

    public final boolean dragSlopExceeded(float f, float f2) {
        if (this.hasPassedDragSlop) {
            return true;
        }
        if (Math.abs(f - f2) > this.viewConfiguration.getScaledTouchSlop()) {
            WindowManager.LayoutParams layoutParams = null;
            updateArrowState$default(this, GestureState.ENTRY, false, 2, null);
            WindowManager windowManager = this.windowManager;
            View view = ((ViewController) this).mView;
            WindowManager.LayoutParams layoutParams2 = this.layoutParams;
            if (layoutParams2 != null) {
                layoutParams = layoutParams2;
            }
            windowManager.updateViewLayout(view, layoutParams);
            ((BackPanel) ((ViewController) this).mView).startTrackingShowBackArrowLatency();
            this.hasPassedDragSlop = true;
        }
        return this.hasPassedDragSlop;
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public void dump(PrintWriter printWriter) {
        printWriter.println("BackPanelController:");
        GestureState gestureState = this.currentState;
        printWriter.println("  currentState=" + gestureState);
        View view = ((ViewController) this).mView;
        printWriter.println("  isLeftPanel=" + view + ".isLeftPanel");
    }

    public final float fullScreenStretchProgress(float f) {
        return MathUtils.saturate((f - this.params.getSwipeTriggerThreshold()) / (this.fullyStretchedThreshold - this.params.getSwipeTriggerThreshold()));
    }

    public final VelocityTracker getVelocityTracker() {
        if (this.velocityTracker == null) {
            this.velocityTracker = VelocityTracker.obtain();
        }
        return this.velocityTracker;
    }

    public final void handleMoveEvent(MotionEvent motionEvent) {
        if (this.currentState.isInteractive()) {
            float x = motionEvent.getX();
            float y = motionEvent.getY() - this.startY;
            float abs = Math.abs(y);
            float max = Math.max((float) ActionBarShadowController.ELEVATION_LOW, ((BackPanel) ((ViewController) this).mView).isLeftPanel() ? x - this.startX : this.startX - x);
            float f = max - this.previousXTranslation;
            this.previousXTranslation = max;
            if (Math.abs(f) > ActionBarShadowController.ELEVATION_LOW) {
                if (Math.signum(f) == Math.signum(this.totalTouchDelta)) {
                    this.totalTouchDelta += f;
                } else {
                    this.totalTouchDelta = f;
                }
            }
            updateArrowStateOnMove(abs, max);
            int i = WhenMappings.$EnumSwitchMapping$0[this.currentState.ordinal()];
            if (i == 1) {
                stretchEntryBackIndicator(preThresholdStretchProgress(max));
            } else if (i == 2) {
                stretchActiveBackIndicator(fullScreenStretchProgress(max));
            } else if (i == 3) {
                ((BackPanel) ((ViewController) this).mView).resetStretch();
            }
            setVerticalTranslation(y);
        }
    }

    public final boolean isFlung() {
        VelocityTracker velocityTracker = getVelocityTracker();
        Intrinsics.checkNotNull(velocityTracker);
        velocityTracker.computeCurrentVelocity(1000);
        return Math.abs(velocityTracker.getXVelocity()) > 3000.0f;
    }

    @Override // com.android.systemui.plugins.Plugin
    public void onDestroy() {
        cancelFailsafe();
        this.windowManager.removeView(((ViewController) this).mView);
    }

    public final void onFailsafe() {
        updateArrowState(GestureState.GONE, true);
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public void onMotionEvent(MotionEvent motionEvent) {
        VelocityTracker velocityTracker = getVelocityTracker();
        Intrinsics.checkNotNull(velocityTracker);
        velocityTracker.addMovement(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            resetOnDown();
            this.startX = motionEvent.getX();
            this.startY = motionEvent.getY();
            this.gestureStartTime = SystemClock.uptimeMillis();
        } else if (actionMasked == 1) {
            GestureState gestureState = this.currentState;
            if (gestureState == GestureState.ACTIVE) {
                updateArrowState$default(this, isFlung() ? GestureState.FLUNG : GestureState.COMMITTED, false, 2, null);
            } else if (gestureState != GestureState.GONE) {
                updateArrowState$default(this, GestureState.CANCELLED, false, 2, null);
            }
            setVelocityTracker(null);
        } else if (actionMasked == 2) {
            if (dragSlopExceeded(motionEvent.getX(), this.startX)) {
                handleMoveEvent(motionEvent);
            }
        } else if (actionMasked != 3) {
        } else {
            updateArrowState$default(this, GestureState.GONE, false, 2, null);
            setVelocityTracker(null);
        }
    }

    public void onViewAttached() {
        updateConfiguration();
        updateArrowDirection(this.configurationController.isLayoutRtl());
        updateArrowState(GestureState.GONE, true);
        updateRestingArrowDimens(false, this.currentState);
        this.configurationController.addCallback(this.configurationListener);
    }

    public void onViewDetached() {
        this.configurationController.removeCallback(this.configurationListener);
    }

    public final void playAnimation(DelayedOnAnimationEndListener delayedOnAnimationEndListener) {
        updateRestingArrowDimens(true, this.currentState);
        if (((BackPanel) ((ViewController) this).mView).addEndListener(delayedOnAnimationEndListener)) {
            return;
        }
        scheduleFailsafe();
    }

    public final void playCancelBackAnimation() {
        NavigationEdgeBackPlugin.BackCallback backCallback = this.backCallback;
        NavigationEdgeBackPlugin.BackCallback backCallback2 = backCallback;
        if (backCallback == null) {
            backCallback2 = null;
        }
        backCallback2.cancelBack();
        playAnimation(this.setGoneEndListener);
    }

    public final void playCommitBackAnimation() {
        if (this.previousState != GestureState.FLUNG) {
            VelocityTracker velocityTracker = getVelocityTracker();
            Intrinsics.checkNotNull(velocityTracker);
            velocityTracker.computeCurrentVelocity(1000);
            VelocityTracker velocityTracker2 = getVelocityTracker();
            Intrinsics.checkNotNull(velocityTracker2);
            boolean z = true;
            boolean z2 = Math.abs(velocityTracker2.getXVelocity()) < 500.0f;
            if (SystemClock.uptimeMillis() - this.vibrationTime < 400) {
                z = false;
            }
            if (z2 || z) {
                this.vibratorHelper.vibrate(0);
            }
        }
        NavigationEdgeBackPlugin.BackCallback backCallback = this.backCallback;
        NavigationEdgeBackPlugin.BackCallback backCallback2 = backCallback;
        if (backCallback == null) {
            backCallback2 = null;
        }
        backCallback2.triggerBack(false);
        playAnimation(this.setGoneEndListener);
    }

    public final void playFlingBackAnimation() {
        playAnimation(this.setCommittedEndListener);
    }

    public final float preThresholdStretchProgress(float f) {
        return MathUtils.saturate(f / this.params.getSwipeTriggerThreshold());
    }

    public final void resetOnDown() {
        this.hasPassedDragSlop = false;
        this.hasHapticPlayed = false;
        this.totalTouchDelta = ActionBarShadowController.ELEVATION_LOW;
        this.vibrationTime = 0L;
        cancelFailsafe();
    }

    public final void scheduleFailsafe() {
        cancelFailsafe();
        this.mainHandler.postDelayed(this.failsafeRunnable, 350L);
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public void setBackCallback(NavigationEdgeBackPlugin.BackCallback backCallback) {
        this.backCallback = backCallback;
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public void setDisplaySize(Point point) {
        this.displaySize.set(point.x, point.y);
        this.fullyStretchedThreshold = Math.min(point.x, this.params.getSwipeProgressThreshold());
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public void setInsets(int i, int i2) {
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public void setIsLeftPanel(boolean z) {
        ((BackPanel) ((ViewController) this).mView).setLeftPanel(z);
        WindowManager.LayoutParams layoutParams = this.layoutParams;
        WindowManager.LayoutParams layoutParams2 = layoutParams;
        if (layoutParams == null) {
            layoutParams2 = null;
        }
        layoutParams2.gravity = z ? 51 : 53;
    }

    @Override // com.android.systemui.plugins.NavigationEdgeBackPlugin
    public void setLayoutParams(WindowManager.LayoutParams layoutParams) {
        this.layoutParams = layoutParams;
        this.windowManager.addView(((ViewController) this).mView, layoutParams);
    }

    public final void setVelocityTracker(VelocityTracker velocityTracker) {
        VelocityTracker velocityTracker2;
        if (!Intrinsics.areEqual(this.velocityTracker, velocityTracker) && (velocityTracker2 = this.velocityTracker) != null) {
            velocityTracker2.recycle();
        }
        this.velocityTracker = velocityTracker;
    }

    public final void setVerticalTranslation(float f) {
        PathInterpolator pathInterpolator;
        float abs = Math.abs(f);
        float height = (((BackPanel) ((ViewController) this).mView).getHeight() - this.params.getEntryIndicator().getBackgroundDimens().getHeight()) / 2.0f;
        float saturate = MathUtils.saturate(abs / (15 * height));
        pathInterpolator = BackPanelControllerKt.RUBBER_BAND_INTERPOLATOR;
        ((BackPanel) ((ViewController) this).mView).animateVertically(pathInterpolator.getInterpolation(saturate) * height * Math.signum(f));
    }

    public final void stretchActiveBackIndicator(float f) {
        PathInterpolator pathInterpolator;
        DecelerateInterpolator decelerateInterpolator;
        pathInterpolator = BackPanelControllerKt.RUBBER_BAND_INTERPOLATOR;
        float interpolation = pathInterpolator.getInterpolation(f);
        decelerateInterpolator = BackPanelControllerKt.DECELERATE_INTERPOLATOR_SLOW;
        ((BackPanel) ((ViewController) this).mView).setStretch(interpolation, interpolation, decelerateInterpolator.getInterpolation(f), this.params.getFullyStretchedIndicator());
    }

    public final void stretchEntryBackIndicator(float f) {
        PathInterpolator pathInterpolator;
        DecelerateInterpolator decelerateInterpolator;
        pathInterpolator = BackPanelControllerKt.RUBBER_BAND_INTERPOLATOR;
        float interpolation = pathInterpolator.getInterpolation(f);
        decelerateInterpolator = BackPanelControllerKt.DECELERATE_INTERPOLATOR;
        ((BackPanel) ((ViewController) this).mView).setStretch(ActionBarShadowController.ELEVATION_LOW, interpolation, decelerateInterpolator.getInterpolation(f), this.params.getPreThresholdIndicator());
    }

    public final void updateArrowDirection(boolean z) {
        ((BackPanel) ((ViewController) this).mView).setArrowsPointLeft(z);
    }

    public final void updateArrowState(GestureState gestureState, boolean z) {
        if (z || this.currentState != gestureState) {
            this.previousState = this.currentState;
            this.currentState = gestureState;
            if (gestureState == GestureState.GONE) {
                ((BackPanel) ((ViewController) this).mView).cancelAlphaAnimations();
                ((BackPanel) ((ViewController) this).mView).setVisibility(8);
            } else {
                ((BackPanel) ((ViewController) this).mView).setVisibility(0);
            }
            switch (WhenMappings.$EnumSwitchMapping$0[this.currentState.ordinal()]) {
                case 1:
                    updateYPosition(this.startY);
                    updateRestingArrowDimens(true, this.currentState);
                    return;
                case 2:
                    updateRestingArrowDimens(true, this.currentState);
                    if (this.hasHapticPlayed) {
                        return;
                    }
                    this.hasHapticPlayed = true;
                    this.vibrationTime = SystemClock.uptimeMillis();
                    this.vibratorHelper.vibrate(2);
                    return;
                case 3:
                    updateRestingArrowDimens(true, this.currentState);
                    return;
                case 4:
                    playFlingBackAnimation();
                    return;
                case 5:
                    playCancelBackAnimation();
                    return;
                case 6:
                    updateRestingArrowDimens(false, this.currentState);
                    return;
                case 7:
                    playCommitBackAnimation();
                    return;
                default:
                    return;
            }
        }
    }

    public final void updateArrowStateOnMove(float f, float f2) {
        if (this.currentState.isInteractive()) {
            int i = WhenMappings.$EnumSwitchMapping$0[this.currentState.ordinal()];
            if (i == 1) {
                if (f2 > this.params.getSwipeTriggerThreshold()) {
                    updateArrowState$default(this, GestureState.ACTIVE, false, 2, null);
                }
            } else if (i == 2) {
                float f3 = this.totalTouchDelta;
                if ((f3 >= ActionBarShadowController.ELEVATION_LOW || (-f3) <= this.params.getMinDeltaForSwitch()) && f <= f2 * 2) {
                    return;
                }
                updateArrowState$default(this, GestureState.INACTIVE, false, 2, null);
            } else if (i != 3) {
            } else {
                float f4 = this.totalTouchDelta;
                if (f4 <= ActionBarShadowController.ELEVATION_LOW || f4 <= this.params.getMinDeltaForSwitch()) {
                    return;
                }
                updateArrowState$default(this, GestureState.ACTIVE, false, 2, null);
            }
        }
    }

    public final void updateConfiguration() {
        this.params.update(getResources());
        ((BackPanel) ((ViewController) this).mView).updateArrowPaint$frameworks__base__packages__SystemUI__android_common__SystemUI_core(this.params.getArrowThickness());
    }

    public final void updateRestingArrowDimens(boolean z, GestureState gestureState) {
        float horizontalTranslation;
        EdgePanelParams.ArrowDimens arrowDimens;
        EdgePanelParams.BackgroundDimens backgroundDimens;
        if (z) {
            int i = WhenMappings.$EnumSwitchMapping$0[gestureState.ordinal()];
            if (i == 1 || i == 2 || i == 4) {
                ((BackPanel) ((ViewController) this).mView).setArrowStiffness(600.0f, 0.4f);
            } else if (i != 5) {
                ((BackPanel) ((ViewController) this).mView).setArrowStiffness(1200.0f, 1.0f);
            } else {
                ((BackPanel) ((ViewController) this).mView).fadeOut();
            }
        }
        BackPanel backPanel = (BackPanel) ((ViewController) this).mView;
        int[] iArr = WhenMappings.$EnumSwitchMapping$0;
        switch (iArr[gestureState.ordinal()]) {
            case 1:
            case 3:
            case 5:
                horizontalTranslation = this.params.getEntryIndicator().getHorizontalTranslation();
                break;
            case 2:
                horizontalTranslation = this.params.getActiveIndicator().getHorizontalTranslation();
                break;
            case 4:
                horizontalTranslation = this.params.getFullyStretchedIndicator().getHorizontalTranslation();
                break;
            case 6:
                horizontalTranslation = -this.params.getActiveIndicator().getBackgroundDimens().getWidth();
                break;
            case 7:
                horizontalTranslation = (-this.params.getActiveIndicator().getBackgroundDimens().getWidth()) * 1.5f;
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        switch (iArr[gestureState.ordinal()]) {
            case 1:
            case 6:
                arrowDimens = this.params.getEntryIndicator().getArrowDimens();
                break;
            case 2:
            case 3:
            case 4:
            case 7:
                arrowDimens = this.params.getActiveIndicator().getArrowDimens();
                break;
            case 5:
                arrowDimens = this.params.getCancelledArrowDimens();
                break;
            default:
                throw new NoWhenBranchMatchedException();
        }
        int i2 = iArr[gestureState.ordinal()];
        if (i2 == 1 || i2 == 6) {
            backgroundDimens = this.params.getEntryIndicator().getBackgroundDimens();
        } else {
            backgroundDimens = EdgePanelParams.BackgroundDimens.copy$default(this.params.getActiveIndicator().getBackgroundDimens(), ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, (gestureState == GestureState.INACTIVE || gestureState == GestureState.CANCELLED) ? this.params.getCancelledEdgeCornerRadius() : this.params.getActiveIndicator().getBackgroundDimens().getEdgeCornerRadius(), ActionBarShadowController.ELEVATION_LOW, 11, null);
        }
        backPanel.setRestingDimens$frameworks__base__packages__SystemUI__android_common__SystemUI_core(new EdgePanelParams.BackIndicatorDimens(horizontalTranslation, arrowDimens, backgroundDimens), z);
    }

    public final void updateYPosition(float f) {
        float max = Math.max(f - this.params.getFingerOffset(), this.params.getMinArrowYPosition());
        WindowManager.LayoutParams layoutParams = this.layoutParams;
        WindowManager.LayoutParams layoutParams2 = layoutParams;
        if (layoutParams == null) {
            layoutParams2 = null;
        }
        float f2 = layoutParams2.height / 2.0f;
        WindowManager.LayoutParams layoutParams3 = this.layoutParams;
        if (layoutParams3 == null) {
            layoutParams3 = null;
        }
        layoutParams3.y = MathUtils.constrain((int) (max - f2), 0, this.displaySize.y);
    }
}