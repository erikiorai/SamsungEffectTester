package com.android.systemui.animation;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.PendingIntent;
import android.app.TaskInfo;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationAdapter;
import android.view.RemoteAnimationTarget;
import android.view.SyncRtSurfaceTransactionApplier;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.animation.LaunchAnimator;
import java.util.LinkedHashSet;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;

/* loaded from: mainsysui33.jar:com/android/systemui/animation/ActivityLaunchAnimator.class */
public final class ActivityLaunchAnimator {
    public static final long ANIMATION_DELAY_NAV_FADE_IN;
    public static final Companion Companion;
    public static final LaunchAnimator DEFAULT_DIALOG_TO_APP_ANIMATOR;
    public static final LaunchAnimator DEFAULT_LAUNCH_ANIMATOR;
    public static final LaunchAnimator.Timings DIALOG_TIMINGS;
    public static final LaunchAnimator.Interpolators INTERPOLATORS;
    public static final Interpolator NAV_FADE_IN_INTERPOLATOR;
    public static final PathInterpolator NAV_FADE_OUT_INTERPOLATOR;
    public static final LaunchAnimator.Timings TIMINGS;
    public Callback callback;
    public final LaunchAnimator dialogToAppAnimator;
    public final LaunchAnimator launchAnimator;
    public final ActivityLaunchAnimator$lifecycleListener$1 lifecycleListener;
    public final LinkedHashSet<Listener> listeners;

    /* loaded from: mainsysui33.jar:com/android/systemui/animation/ActivityLaunchAnimator$Callback.class */
    public interface Callback {
        int getBackgroundColor(TaskInfo taskInfo);

        void hideKeyguardWithAnimation(IRemoteAnimationRunner iRemoteAnimationRunner);

        boolean isOnKeyguard();
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/animation/ActivityLaunchAnimator$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final Interpolator createPositionXInterpolator() {
            Path path = new Path();
            path.moveTo(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW);
            path.cubicTo(0.1217f, 0.0462f, 0.15f, 0.4686f, 0.1667f, 0.66f);
            path.cubicTo(0.1834f, 0.8878f, 0.1667f, 1.0f, 1.0f, 1.0f);
            return new PathInterpolator(path);
        }

        public final LaunchAnimator.Interpolators getINTERPOLATORS() {
            return ActivityLaunchAnimator.INTERPOLATORS;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/animation/ActivityLaunchAnimator$Controller.class */
    public interface Controller extends LaunchAnimator.Controller {
        public static final Companion Companion = Companion.$$INSTANCE;

        /* loaded from: mainsysui33.jar:com/android/systemui/animation/ActivityLaunchAnimator$Controller$Companion.class */
        public static final class Companion {
            public static final /* synthetic */ Companion $$INSTANCE = new Companion();

            public static /* synthetic */ Controller fromView$default(Companion companion, View view, Integer num, int i, Object obj) {
                if ((i & 2) != 0) {
                    num = null;
                }
                return companion.fromView(view, num);
            }

            public final Controller fromView(View view, Integer num) {
                if (view.getParent() instanceof ViewGroup) {
                    return new GhostedViewLaunchAnimatorController(view, num, null, 4, null);
                }
                Log.e("ActivityLaunchAnimator", "Skipping animation as view " + view + " is not attached to a ViewGroup", new Exception());
                return null;
            }
        }

        static Controller fromView(View view, Integer num) {
            return Companion.fromView(view, num);
        }

        static /* synthetic */ void onLaunchAnimationCancelled$default(Controller controller, Boolean bool, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: onLaunchAnimationCancelled");
            }
            if ((i & 1) != 0) {
                bool = null;
            }
            controller.onLaunchAnimationCancelled(bool);
        }

        default boolean isBelowAnimatingWindow() {
            return false;
        }

        default boolean isDialogLaunch() {
            return false;
        }

        default void onIntentStarted(boolean z) {
        }

        default void onLaunchAnimationCancelled(Boolean bool) {
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/animation/ActivityLaunchAnimator$Listener.class */
    public interface Listener {
        default void onLaunchAnimationEnd() {
        }

        default void onLaunchAnimationProgress(float f) {
        }

        default void onLaunchAnimationStart() {
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/animation/ActivityLaunchAnimator$PendingIntentStarter.class */
    public interface PendingIntentStarter {
        int startPendingIntent(RemoteAnimationAdapter remoteAnimationAdapter) throws PendingIntent.CanceledException;
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/animation/ActivityLaunchAnimator$Runner.class */
    public static final class Runner extends IRemoteAnimationRunner.Stub {
        public LaunchAnimator.Animation animation;
        public final Callback callback;
        public boolean cancelled;
        public final Context context;
        public final Controller controller;
        public final Matrix invertMatrix;
        public final LaunchAnimator launchAnimator;
        public final ViewGroup launchContainer;
        public final Listener listener;
        public final Matrix matrix;
        public Runnable onTimeout;
        public boolean timedOut;
        public final SyncRtSurfaceTransactionApplier transactionApplier;
        public final View transactionApplierView;
        public Rect windowCrop;
        public RectF windowCropF;

        public Runner(Controller controller, Callback callback, LaunchAnimator launchAnimator, Listener listener) {
            this.controller = controller;
            this.callback = callback;
            this.launchAnimator = launchAnimator;
            this.listener = listener;
            ViewGroup launchContainer = controller.getLaunchContainer();
            this.launchContainer = launchContainer;
            this.context = launchContainer.getContext();
            View openingWindowSyncView = controller.getOpeningWindowSyncView();
            ViewGroup launchContainer2 = openingWindowSyncView == null ? controller.getLaunchContainer() : openingWindowSyncView;
            this.transactionApplierView = launchContainer2;
            this.transactionApplier = new SyncRtSurfaceTransactionApplier(launchContainer2);
            this.matrix = new Matrix();
            this.invertMatrix = new Matrix();
            this.windowCrop = new Rect();
            this.windowCropF = new RectF();
            this.onTimeout = new Runnable() { // from class: com.android.systemui.animation.ActivityLaunchAnimator$Runner$onTimeout$1
                @Override // java.lang.Runnable
                public final void run() {
                    ActivityLaunchAnimator.Runner.access$onAnimationTimedOut(ActivityLaunchAnimator.Runner.this);
                }
            };
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.animation.ActivityLaunchAnimator$Runner$onTimeout$1.run():void] */
        public static final /* synthetic */ void access$onAnimationTimedOut(Runner runner) {
            runner.onAnimationTimedOut();
        }

        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.animation.ActivityLaunchAnimator$Runner$onAnimationStart$1.run():void] */
        public static final /* synthetic */ void access$startAnimation(Runner runner, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            runner.startAnimation(remoteAnimationTargetArr, remoteAnimationTargetArr2, iRemoteAnimationFinishedCallback);
        }

        public final void applyStateToNavigationBar(RemoteAnimationTarget remoteAnimationTarget, LaunchAnimator.State state, float f) {
            if (this.transactionApplierView.getViewRootImpl() == null || !remoteAnimationTarget.leash.isValid()) {
                return;
            }
            LaunchAnimator.Companion companion = LaunchAnimator.Companion;
            LaunchAnimator.Timings timings = ActivityLaunchAnimator.TIMINGS;
            float progress = companion.getProgress(timings, f, ActivityLaunchAnimator.ANIMATION_DELAY_NAV_FADE_IN, 133L);
            SyncRtSurfaceTransactionApplier.SurfaceParams.Builder builder = new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(remoteAnimationTarget.leash);
            if (progress > ActionBarShadowController.ELEVATION_LOW) {
                this.matrix.reset();
                this.matrix.setTranslate(ActionBarShadowController.ELEVATION_LOW, state.getTop() - remoteAnimationTarget.sourceContainerBounds.top);
                this.windowCrop.set(state.getLeft(), 0, state.getRight(), state.getHeight());
                builder.withAlpha(ActivityLaunchAnimator.NAV_FADE_IN_INTERPOLATOR.getInterpolation(progress)).withMatrix(this.matrix).withWindowCrop(this.windowCrop).withVisibility(true);
            } else {
                builder.withAlpha(1.0f - ActivityLaunchAnimator.NAV_FADE_OUT_INTERPOLATOR.getInterpolation(companion.getProgress(timings, f, 0L, 133L)));
            }
            this.transactionApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{builder.build()});
        }

        public final void applyStateToWindow(RemoteAnimationTarget remoteAnimationTarget, LaunchAnimator.State state, float f) {
            float f2;
            if (this.transactionApplierView.getViewRootImpl() == null || !remoteAnimationTarget.leash.isValid()) {
                return;
            }
            Rect rect = remoteAnimationTarget.screenSpaceBounds;
            int i = rect.left;
            int i2 = rect.right;
            float f3 = (i + i2) / 2.0f;
            int i3 = rect.top;
            int i4 = rect.bottom;
            float f4 = (i3 + i4) / 2.0f;
            float f5 = i4 - i3;
            float max = Math.max(state.getWidth() / (i2 - i), state.getHeight() / f5);
            this.matrix.reset();
            this.matrix.setScale(max, max, f3, f4);
            this.matrix.postTranslate(state.getCenterX() - f3, (state.getTop() - rect.top) + (((f5 * max) - f5) / 2.0f));
            float left = state.getLeft() - rect.left;
            float top = state.getTop() - rect.top;
            this.windowCropF.set(left, top, state.getWidth() + left, state.getHeight() + top);
            this.matrix.invert(this.invertMatrix);
            this.invertMatrix.mapRect(this.windowCropF);
            this.windowCrop.set(MathKt__MathJVMKt.roundToInt(this.windowCropF.left), MathKt__MathJVMKt.roundToInt(this.windowCropF.top), MathKt__MathJVMKt.roundToInt(this.windowCropF.right), MathKt__MathJVMKt.roundToInt(this.windowCropF.bottom));
            if (this.controller.isBelowAnimatingWindow()) {
                LaunchAnimator.Companion companion = LaunchAnimator.Companion;
                LaunchAnimator.Timings timings = ActivityLaunchAnimator.TIMINGS;
                f2 = ActivityLaunchAnimator.Companion.getINTERPOLATORS().getContentAfterFadeInInterpolator().getInterpolation(companion.getProgress(timings, f, timings.getContentAfterFadeInDelay(), timings.getContentAfterFadeInDuration()));
            } else {
                f2 = 1.0f;
            }
            this.transactionApplier.scheduleApply(new SyncRtSurfaceTransactionApplier.SurfaceParams[]{new SyncRtSurfaceTransactionApplier.SurfaceParams.Builder(remoteAnimationTarget.leash).withAlpha(f2).withMatrix(this.matrix).withWindowCrop(this.windowCrop).withCornerRadius(Math.max(state.getTopCornerRadius(), state.getBottomCornerRadius()) / max).withVisibility(true).build()});
        }

        public final void invoke(IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            try {
                iRemoteAnimationFinishedCallback.onAnimationFinished();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public void onAnimationCancelled(final boolean z) {
            if (this.timedOut) {
                return;
            }
            Log.i("ActivityLaunchAnimator", "Remote animation was cancelled");
            this.cancelled = true;
            removeTimeout();
            this.context.getMainExecutor().execute(new Runnable() { // from class: com.android.systemui.animation.ActivityLaunchAnimator$Runner$onAnimationCancelled$1
                @Override // java.lang.Runnable
                public final void run() {
                    LaunchAnimator.Animation animation;
                    ActivityLaunchAnimator.Controller controller;
                    animation = ActivityLaunchAnimator.Runner.this.animation;
                    if (animation != null) {
                        animation.cancel();
                    }
                    controller = ActivityLaunchAnimator.Runner.this.controller;
                    controller.onLaunchAnimationCancelled(Boolean.valueOf(z));
                }
            });
        }

        public void onAnimationStart(int i, final RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, final RemoteAnimationTarget[] remoteAnimationTargetArr3, final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            removeTimeout();
            if (this.timedOut) {
                if (iRemoteAnimationFinishedCallback != null) {
                    invoke(iRemoteAnimationFinishedCallback);
                }
            } else if (this.cancelled) {
            } else {
                this.context.getMainExecutor().execute(new Runnable() { // from class: com.android.systemui.animation.ActivityLaunchAnimator$Runner$onAnimationStart$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ActivityLaunchAnimator.Runner.access$startAnimation(ActivityLaunchAnimator.Runner.this, remoteAnimationTargetArr, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback);
                    }
                });
            }
        }

        public final void onAnimationTimedOut() {
            if (this.cancelled) {
                return;
            }
            Log.i("ActivityLaunchAnimator", "Remote animation timed out");
            this.timedOut = true;
            Controller.onLaunchAnimationCancelled$default(this.controller, null, 1, null);
        }

        public final void postTimeout$frameworks__base__packages__SystemUI__animation__android_common__SystemUIAnimationLib() {
            this.launchContainer.postDelayed(this.onTimeout, 1000L);
        }

        public final void removeTimeout() {
            this.launchContainer.removeCallbacks(this.onTimeout);
        }

        public final void startAnimation(RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
            RemoteAnimationTarget remoteAnimationTarget;
            RemoteAnimationTarget remoteAnimationTarget2;
            Controller controller;
            if (remoteAnimationTargetArr != null) {
                for (RemoteAnimationTarget remoteAnimationTarget3 : remoteAnimationTargetArr) {
                    if (remoteAnimationTarget3.mode == 0) {
                        remoteAnimationTarget = remoteAnimationTarget3;
                        break;
                    }
                }
            }
            remoteAnimationTarget = null;
            if (remoteAnimationTarget == null) {
                Log.i("ActivityLaunchAnimator", "Aborting the animation as no window is opening");
                removeTimeout();
                if (iRemoteAnimationFinishedCallback != null) {
                    invoke(iRemoteAnimationFinishedCallback);
                }
                Controller.onLaunchAnimationCancelled$default(this.controller, null, 1, null);
                return;
            }
            if (remoteAnimationTargetArr2 != null) {
                for (RemoteAnimationTarget remoteAnimationTarget4 : remoteAnimationTargetArr2) {
                    if (remoteAnimationTarget4.windowType == 2019) {
                        remoteAnimationTarget2 = remoteAnimationTarget4;
                        break;
                    }
                }
            }
            remoteAnimationTarget2 = null;
            Rect rect = remoteAnimationTarget.screenSpaceBounds;
            LaunchAnimator.State state = new LaunchAnimator.State(rect.top, rect.bottom, rect.left, rect.right, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, 48, null);
            ActivityManager.RunningTaskInfo runningTaskInfo = remoteAnimationTarget.taskInfo;
            int backgroundColor = runningTaskInfo != null ? this.callback.getBackgroundColor(runningTaskInfo) : remoteAnimationTarget.backgroundColor;
            float windowCornerRadius = this.launchAnimator.isExpandingFullyAbove$frameworks__base__packages__SystemUI__animation__android_common__SystemUIAnimationLib(this.controller.getLaunchContainer(), state) ? ScreenDecorationsUtils.getWindowCornerRadius(this.context) : 0.0f;
            state.setTopCornerRadius(windowCornerRadius);
            state.setBottomCornerRadius(windowCornerRadius);
            this.animation = this.launchAnimator.startAnimation(new Controller(this, iRemoteAnimationFinishedCallback, remoteAnimationTarget, remoteAnimationTarget2) { // from class: com.android.systemui.animation.ActivityLaunchAnimator$Runner$startAnimation$controller$1
                public final /* synthetic */ ActivityLaunchAnimator.Controller $$delegate_0;
                public final /* synthetic */ IRemoteAnimationFinishedCallback $iCallback;
                public final /* synthetic */ RemoteAnimationTarget $navigationBar;
                public final /* synthetic */ RemoteAnimationTarget $window;
                public final /* synthetic */ ActivityLaunchAnimator.Runner this$0;

                {
                    this.this$0 = this;
                    this.$iCallback = iRemoteAnimationFinishedCallback;
                    this.$window = remoteAnimationTarget;
                    this.$navigationBar = remoteAnimationTarget2;
                    this.$$delegate_0 = ActivityLaunchAnimator.Controller.this;
                }

                @Override // com.android.systemui.animation.LaunchAnimator.Controller
                public LaunchAnimator.State createAnimatorState() {
                    return this.$$delegate_0.createAnimatorState();
                }

                @Override // com.android.systemui.animation.LaunchAnimator.Controller
                public ViewGroup getLaunchContainer() {
                    return this.$$delegate_0.getLaunchContainer();
                }

                @Override // com.android.systemui.animation.LaunchAnimator.Controller
                public View getOpeningWindowSyncView() {
                    return this.$$delegate_0.getOpeningWindowSyncView();
                }

                @Override // com.android.systemui.animation.ActivityLaunchAnimator.Controller
                public boolean isBelowAnimatingWindow() {
                    return this.$$delegate_0.isBelowAnimatingWindow();
                }

                @Override // com.android.systemui.animation.ActivityLaunchAnimator.Controller
                public boolean isDialogLaunch() {
                    return this.$$delegate_0.isDialogLaunch();
                }

                @Override // com.android.systemui.animation.ActivityLaunchAnimator.Controller
                public void onIntentStarted(boolean z) {
                    this.$$delegate_0.onIntentStarted(z);
                }

                @Override // com.android.systemui.animation.ActivityLaunchAnimator.Controller
                public void onLaunchAnimationCancelled(Boolean bool) {
                    this.$$delegate_0.onLaunchAnimationCancelled(bool);
                }

                @Override // com.android.systemui.animation.LaunchAnimator.Controller
                public void onLaunchAnimationEnd(boolean z) {
                    ActivityLaunchAnimator.Listener listener;
                    listener = this.this$0.listener;
                    if (listener != null) {
                        listener.onLaunchAnimationEnd();
                    }
                    IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback2 = this.$iCallback;
                    if (iRemoteAnimationFinishedCallback2 != null) {
                        this.this$0.invoke(iRemoteAnimationFinishedCallback2);
                    }
                    ActivityLaunchAnimator.Controller.this.onLaunchAnimationEnd(z);
                }

                @Override // com.android.systemui.animation.LaunchAnimator.Controller
                public void onLaunchAnimationProgress(LaunchAnimator.State state2, float f, float f2) {
                    ActivityLaunchAnimator.Listener listener;
                    if (!state2.getVisible()) {
                        this.this$0.applyStateToWindow(this.$window, state2, f2);
                    }
                    RemoteAnimationTarget remoteAnimationTarget5 = this.$navigationBar;
                    if (remoteAnimationTarget5 != null) {
                        this.this$0.applyStateToNavigationBar(remoteAnimationTarget5, state2, f2);
                    }
                    listener = this.this$0.listener;
                    if (listener != null) {
                        listener.onLaunchAnimationProgress(f2);
                    }
                    ActivityLaunchAnimator.Controller.this.onLaunchAnimationProgress(state2, f, f2);
                }

                @Override // com.android.systemui.animation.LaunchAnimator.Controller
                public void onLaunchAnimationStart(boolean z) {
                    ActivityLaunchAnimator.Listener listener;
                    listener = this.this$0.listener;
                    if (listener != null) {
                        listener.onLaunchAnimationStart();
                    }
                    ActivityLaunchAnimator.Controller.this.onLaunchAnimationStart(z);
                }

                @Override // com.android.systemui.animation.LaunchAnimator.Controller
                public void setLaunchContainer(ViewGroup viewGroup) {
                    this.$$delegate_0.setLaunchContainer(viewGroup);
                }
            }, state, backgroundColor, !controller.isBelowAnimatingWindow(), !controller.isBelowAnimatingWindow());
        }
    }

    static {
        Companion companion = new Companion(null);
        Companion = companion;
        LaunchAnimator.Timings timings = new LaunchAnimator.Timings(500L, 0L, 150L, 150L, 183L);
        TIMINGS = timings;
        LaunchAnimator.Timings copy$default = LaunchAnimator.Timings.copy$default(timings, 0L, 0L, 200L, 200L, 0L, 19, null);
        DIALOG_TIMINGS = copy$default;
        LaunchAnimator.Interpolators interpolators = new LaunchAnimator.Interpolators(Interpolators.EMPHASIZED, companion.createPositionXInterpolator(), Interpolators.LINEAR_OUT_SLOW_IN, new PathInterpolator(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, 0.6f, 1.0f));
        INTERPOLATORS = interpolators;
        DEFAULT_LAUNCH_ANIMATOR = new LaunchAnimator(timings, interpolators);
        DEFAULT_DIALOG_TO_APP_ANIMATOR = new LaunchAnimator(copy$default, interpolators);
        ANIMATION_DELAY_NAV_FADE_IN = timings.getTotalDuration() - 266;
        NAV_FADE_IN_INTERPOLATOR = Interpolators.STANDARD_DECELERATE;
        NAV_FADE_OUT_INTERPOLATOR = new PathInterpolator(0.2f, ActionBarShadowController.ELEVATION_LOW, 1.0f, 1.0f);
    }

    public ActivityLaunchAnimator() {
        this(null, null, 3, null);
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.animation.ActivityLaunchAnimator$lifecycleListener$1] */
    public ActivityLaunchAnimator(LaunchAnimator launchAnimator, LaunchAnimator launchAnimator2) {
        this.launchAnimator = launchAnimator;
        this.dialogToAppAnimator = launchAnimator2;
        this.listeners = new LinkedHashSet<>();
        this.lifecycleListener = new Listener() { // from class: com.android.systemui.animation.ActivityLaunchAnimator$lifecycleListener$1
            @Override // com.android.systemui.animation.ActivityLaunchAnimator.Listener
            public void onLaunchAnimationEnd() {
                for (ActivityLaunchAnimator.Listener listener : ActivityLaunchAnimator.access$getListeners$p(ActivityLaunchAnimator.this)) {
                    listener.onLaunchAnimationEnd();
                }
            }

            @Override // com.android.systemui.animation.ActivityLaunchAnimator.Listener
            public void onLaunchAnimationProgress(float f) {
                for (ActivityLaunchAnimator.Listener listener : ActivityLaunchAnimator.access$getListeners$p(ActivityLaunchAnimator.this)) {
                    listener.onLaunchAnimationProgress(f);
                }
            }

            @Override // com.android.systemui.animation.ActivityLaunchAnimator.Listener
            public void onLaunchAnimationStart() {
                for (ActivityLaunchAnimator.Listener listener : ActivityLaunchAnimator.access$getListeners$p(ActivityLaunchAnimator.this)) {
                    listener.onLaunchAnimationStart();
                }
            }
        };
    }

    public /* synthetic */ ActivityLaunchAnimator(LaunchAnimator launchAnimator, LaunchAnimator launchAnimator2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? DEFAULT_LAUNCH_ANIMATOR : launchAnimator, (i & 2) != 0 ? DEFAULT_DIALOG_TO_APP_ANIMATOR : launchAnimator2);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.animation.ActivityLaunchAnimator$lifecycleListener$1.onLaunchAnimationEnd():void, com.android.systemui.animation.ActivityLaunchAnimator$lifecycleListener$1.onLaunchAnimationProgress(float):void, com.android.systemui.animation.ActivityLaunchAnimator$lifecycleListener$1.onLaunchAnimationStart():void] */
    public static final /* synthetic */ LinkedHashSet access$getListeners$p(ActivityLaunchAnimator activityLaunchAnimator) {
        return activityLaunchAnimator.listeners;
    }

    public static /* synthetic */ void startIntentWithAnimation$default(ActivityLaunchAnimator activityLaunchAnimator, Controller controller, boolean z, String str, boolean z2, Function1 function1, int i, Object obj) {
        if ((i & 2) != 0) {
            z = true;
        }
        if ((i & 4) != 0) {
            str = null;
        }
        if ((i & 8) != 0) {
            z2 = false;
        }
        activityLaunchAnimator.startIntentWithAnimation(controller, z, str, z2, function1);
    }

    public final void addListener(Listener listener) {
        this.listeners.add(listener);
    }

    public final void callOnIntentStartedOnMainThread(final Controller controller, final boolean z) {
        if (Intrinsics.areEqual(Looper.myLooper(), Looper.getMainLooper())) {
            controller.onIntentStarted(z);
        } else {
            controller.getLaunchContainer().getContext().getMainExecutor().execute(new Runnable() { // from class: com.android.systemui.animation.ActivityLaunchAnimator$callOnIntentStartedOnMainThread$1
                @Override // java.lang.Runnable
                public final void run() {
                    ActivityLaunchAnimator.Controller.this.onIntentStarted(z);
                }
            });
        }
    }

    @VisibleForTesting
    public final Runner createRunner(Controller controller) {
        LaunchAnimator launchAnimator = controller.isDialogLaunch() ? this.dialogToAppAnimator : this.launchAnimator;
        Callback callback = this.callback;
        Intrinsics.checkNotNull(callback);
        return new Runner(controller, callback, launchAnimator, this.lifecycleListener);
    }

    public final void removeListener(Listener listener) {
        this.listeners.remove(listener);
    }

    public final void setCallback(Callback callback) {
        this.callback = callback;
    }

    public final void startIntentWithAnimation(Controller controller, boolean z, String str, Function1<? super RemoteAnimationAdapter, Integer> function1) {
        startIntentWithAnimation$default(this, controller, z, str, false, function1, 8, null);
    }

    public final void startIntentWithAnimation(Controller controller, boolean z, String str, boolean z2, Function1<? super RemoteAnimationAdapter, Integer> function1) {
        RemoteAnimationAdapter remoteAnimationAdapter;
        if (controller == null || !z) {
            Log.i("ActivityLaunchAnimator", "Starting intent with no animation");
            function1.invoke((Object) null);
            if (controller != null) {
                callOnIntentStartedOnMainThread(controller, false);
                return;
            }
            return;
        }
        Callback callback = this.callback;
        if (callback == null) {
            throw new IllegalStateException("ActivityLaunchAnimator.callback must be set before using this animator");
        }
        Runner createRunner = createRunner(controller);
        boolean z3 = callback.isOnKeyguard() && !z2;
        if (z3) {
            remoteAnimationAdapter = null;
        } else {
            LaunchAnimator.Timings timings = TIMINGS;
            remoteAnimationAdapter = new RemoteAnimationAdapter(createRunner, timings.getTotalDuration(), timings.getTotalDuration() - 150);
        }
        boolean z4 = z3;
        if (str != null && remoteAnimationAdapter != null) {
            try {
                ActivityTaskManager.getService().registerRemoteAnimationForNextActivityStart(str, remoteAnimationAdapter, (IBinder) null);
            } catch (RemoteException e) {
                Log.w("ActivityLaunchAnimator", "Unable to register the remote animation", e);
            }
        }
        int intValue = ((Number) function1.invoke(remoteAnimationAdapter)).intValue();
        boolean z5 = intValue == 2 || intValue == 0 || (intValue == 3 && z4);
        Log.i("ActivityLaunchAnimator", "launchResult=" + intValue + " willAnimate=" + z5 + " hideKeyguardWithAnimation=" + z4);
        callOnIntentStartedOnMainThread(controller, z5);
        if (z5) {
            createRunner.postTimeout$frameworks__base__packages__SystemUI__animation__android_common__SystemUIAnimationLib();
            if (z4) {
                callback.hideKeyguardWithAnimation(createRunner);
            }
        }
    }

    public final void startPendingIntentWithAnimation(Controller controller, boolean z, String str, final PendingIntentStarter pendingIntentStarter) throws PendingIntent.CanceledException {
        startIntentWithAnimation$default(this, controller, z, str, false, new Function1<RemoteAnimationAdapter, Integer>() { // from class: com.android.systemui.animation.ActivityLaunchAnimator$startPendingIntentWithAnimation$1
            {
                super(1);
            }

            /* JADX DEBUG: Method merged with bridge method */
            public final Integer invoke(RemoteAnimationAdapter remoteAnimationAdapter) {
                return Integer.valueOf(ActivityLaunchAnimator.PendingIntentStarter.this.startPendingIntent(remoteAnimationAdapter));
            }
        }, 8, null);
    }
}