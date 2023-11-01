package com.android.systemui.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.GradientDrawable;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.ViewOverlay;
import android.view.animation.Interpolator;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.animation.LaunchAnimator;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.math.MathKt__MathJVMKt;

/* loaded from: mainsysui33.jar:com/android/systemui/animation/LaunchAnimator.class */
public final class LaunchAnimator {
    public static final Companion Companion = new Companion(null);
    public static final PorterDuffXfermode SRC_MODE = new PorterDuffXfermode(PorterDuff.Mode.SRC);
    public final Interpolators interpolators;
    public final Timings timings;
    public final int[] launchContainerLocation = new int[2];
    public final float[] cornerRadii = new float[8];

    /* loaded from: mainsysui33.jar:com/android/systemui/animation/LaunchAnimator$Animation.class */
    public interface Animation {
        void cancel();
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/animation/LaunchAnimator$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final float getProgress(Timings timings, float f, long j, long j2) {
            return MathUtils.constrain(((f * ((float) timings.getTotalDuration())) - ((float) j)) / ((float) j2), (float) ActionBarShadowController.ELEVATION_LOW, 1.0f);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/animation/LaunchAnimator$Controller.class */
    public interface Controller {
        State createAnimatorState();

        ViewGroup getLaunchContainer();

        default View getOpeningWindowSyncView() {
            return null;
        }

        default void onLaunchAnimationEnd(boolean z) {
        }

        default void onLaunchAnimationProgress(State state, float f, float f2) {
        }

        default void onLaunchAnimationStart(boolean z) {
        }

        void setLaunchContainer(ViewGroup viewGroup);
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/animation/LaunchAnimator$Interpolators.class */
    public static final class Interpolators {
        public final Interpolator contentAfterFadeInInterpolator;
        public final Interpolator contentBeforeFadeOutInterpolator;
        public final Interpolator positionInterpolator;
        public final Interpolator positionXInterpolator;

        public Interpolators(Interpolator interpolator, Interpolator interpolator2, Interpolator interpolator3, Interpolator interpolator4) {
            this.positionInterpolator = interpolator;
            this.positionXInterpolator = interpolator2;
            this.contentBeforeFadeOutInterpolator = interpolator3;
            this.contentAfterFadeInInterpolator = interpolator4;
        }

        public static /* synthetic */ Interpolators copy$default(Interpolators interpolators, Interpolator interpolator, Interpolator interpolator2, Interpolator interpolator3, Interpolator interpolator4, int i, Object obj) {
            if ((i & 1) != 0) {
                interpolator = interpolators.positionInterpolator;
            }
            if ((i & 2) != 0) {
                interpolator2 = interpolators.positionXInterpolator;
            }
            if ((i & 4) != 0) {
                interpolator3 = interpolators.contentBeforeFadeOutInterpolator;
            }
            if ((i & 8) != 0) {
                interpolator4 = interpolators.contentAfterFadeInInterpolator;
            }
            return interpolators.copy(interpolator, interpolator2, interpolator3, interpolator4);
        }

        public final Interpolators copy(Interpolator interpolator, Interpolator interpolator2, Interpolator interpolator3, Interpolator interpolator4) {
            return new Interpolators(interpolator, interpolator2, interpolator3, interpolator4);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof Interpolators) {
                Interpolators interpolators = (Interpolators) obj;
                return Intrinsics.areEqual(this.positionInterpolator, interpolators.positionInterpolator) && Intrinsics.areEqual(this.positionXInterpolator, interpolators.positionXInterpolator) && Intrinsics.areEqual(this.contentBeforeFadeOutInterpolator, interpolators.contentBeforeFadeOutInterpolator) && Intrinsics.areEqual(this.contentAfterFadeInInterpolator, interpolators.contentAfterFadeInInterpolator);
            }
            return false;
        }

        public final Interpolator getContentAfterFadeInInterpolator() {
            return this.contentAfterFadeInInterpolator;
        }

        public final Interpolator getContentBeforeFadeOutInterpolator() {
            return this.contentBeforeFadeOutInterpolator;
        }

        public final Interpolator getPositionInterpolator() {
            return this.positionInterpolator;
        }

        public final Interpolator getPositionXInterpolator() {
            return this.positionXInterpolator;
        }

        public int hashCode() {
            return (((((this.positionInterpolator.hashCode() * 31) + this.positionXInterpolator.hashCode()) * 31) + this.contentBeforeFadeOutInterpolator.hashCode()) * 31) + this.contentAfterFadeInInterpolator.hashCode();
        }

        public String toString() {
            Interpolator interpolator = this.positionInterpolator;
            Interpolator interpolator2 = this.positionXInterpolator;
            Interpolator interpolator3 = this.contentBeforeFadeOutInterpolator;
            Interpolator interpolator4 = this.contentAfterFadeInInterpolator;
            return "Interpolators(positionInterpolator=" + interpolator + ", positionXInterpolator=" + interpolator2 + ", contentBeforeFadeOutInterpolator=" + interpolator3 + ", contentAfterFadeInInterpolator=" + interpolator4 + ")";
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/animation/LaunchAnimator$State.class */
    public static class State {
        public int bottom;
        public float bottomCornerRadius;
        public int left;
        public int right;
        public final int startTop;
        public int top;
        public float topCornerRadius;
        public boolean visible;

        public State(int i, int i2, int i3, int i4, float f, float f2) {
            this.top = i;
            this.bottom = i2;
            this.left = i3;
            this.right = i4;
            this.topCornerRadius = f;
            this.bottomCornerRadius = f2;
            this.startTop = i;
            this.visible = true;
        }

        public /* synthetic */ State(int i, int i2, int i3, int i4, float f, float f2, int i5, DefaultConstructorMarker defaultConstructorMarker) {
            this((i5 & 1) != 0 ? 0 : i, (i5 & 2) != 0 ? 0 : i2, (i5 & 4) != 0 ? 0 : i3, (i5 & 8) != 0 ? 0 : i4, (i5 & 16) != 0 ? 0.0f : f, (i5 & 32) != 0 ? 0.0f : f2);
        }

        public final int getBottom() {
            return this.bottom;
        }

        public final float getBottomCornerRadius() {
            return this.bottomCornerRadius;
        }

        public final float getCenterX() {
            return this.left + (getWidth() / 2.0f);
        }

        public final float getCenterY() {
            return this.top + (getHeight() / 2.0f);
        }

        public final int getHeight() {
            return this.bottom - this.top;
        }

        public final int getLeft() {
            return this.left;
        }

        public final int getRight() {
            return this.right;
        }

        public final int getTop() {
            return this.top;
        }

        public final float getTopCornerRadius() {
            return this.topCornerRadius;
        }

        public final boolean getVisible() {
            return this.visible;
        }

        public final int getWidth() {
            return this.right - this.left;
        }

        public final void setBottom(int i) {
            this.bottom = i;
        }

        public final void setBottomCornerRadius(float f) {
            this.bottomCornerRadius = f;
        }

        public final void setLeft(int i) {
            this.left = i;
        }

        public final void setRight(int i) {
            this.right = i;
        }

        public final void setTop(int i) {
            this.top = i;
        }

        public final void setTopCornerRadius(float f) {
            this.topCornerRadius = f;
        }

        public final void setVisible(boolean z) {
            this.visible = z;
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/animation/LaunchAnimator$Timings.class */
    public static final class Timings {
        public final long contentAfterFadeInDelay;
        public final long contentAfterFadeInDuration;
        public final long contentBeforeFadeOutDelay;
        public final long contentBeforeFadeOutDuration;
        public final long totalDuration;

        public Timings(long j, long j2, long j3, long j4, long j5) {
            this.totalDuration = j;
            this.contentBeforeFadeOutDelay = j2;
            this.contentBeforeFadeOutDuration = j3;
            this.contentAfterFadeInDelay = j4;
            this.contentAfterFadeInDuration = j5;
        }

        public static /* synthetic */ Timings copy$default(Timings timings, long j, long j2, long j3, long j4, long j5, int i, Object obj) {
            if ((i & 1) != 0) {
                j = timings.totalDuration;
            }
            if ((i & 2) != 0) {
                j2 = timings.contentBeforeFadeOutDelay;
            }
            if ((i & 4) != 0) {
                j3 = timings.contentBeforeFadeOutDuration;
            }
            if ((i & 8) != 0) {
                j4 = timings.contentAfterFadeInDelay;
            }
            if ((i & 16) != 0) {
                j5 = timings.contentAfterFadeInDuration;
            }
            return timings.copy(j, j2, j3, j4, j5);
        }

        public final Timings copy(long j, long j2, long j3, long j4, long j5) {
            return new Timings(j, j2, j3, j4, j5);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof Timings) {
                Timings timings = (Timings) obj;
                return this.totalDuration == timings.totalDuration && this.contentBeforeFadeOutDelay == timings.contentBeforeFadeOutDelay && this.contentBeforeFadeOutDuration == timings.contentBeforeFadeOutDuration && this.contentAfterFadeInDelay == timings.contentAfterFadeInDelay && this.contentAfterFadeInDuration == timings.contentAfterFadeInDuration;
            }
            return false;
        }

        public final long getContentAfterFadeInDelay() {
            return this.contentAfterFadeInDelay;
        }

        public final long getContentAfterFadeInDuration() {
            return this.contentAfterFadeInDuration;
        }

        public final long getContentBeforeFadeOutDelay() {
            return this.contentBeforeFadeOutDelay;
        }

        public final long getContentBeforeFadeOutDuration() {
            return this.contentBeforeFadeOutDuration;
        }

        public final long getTotalDuration() {
            return this.totalDuration;
        }

        public int hashCode() {
            return (((((((Long.hashCode(this.totalDuration) * 31) + Long.hashCode(this.contentBeforeFadeOutDelay)) * 31) + Long.hashCode(this.contentBeforeFadeOutDuration)) * 31) + Long.hashCode(this.contentAfterFadeInDelay)) * 31) + Long.hashCode(this.contentAfterFadeInDuration);
        }

        public String toString() {
            long j = this.totalDuration;
            long j2 = this.contentBeforeFadeOutDelay;
            long j3 = this.contentBeforeFadeOutDuration;
            long j4 = this.contentAfterFadeInDelay;
            long j5 = this.contentAfterFadeInDuration;
            return "Timings(totalDuration=" + j + ", contentBeforeFadeOutDelay=" + j2 + ", contentBeforeFadeOutDuration=" + j3 + ", contentAfterFadeInDelay=" + j4 + ", contentAfterFadeInDuration=" + j5 + ")";
        }
    }

    public LaunchAnimator(Timings timings, Interpolators interpolators) {
        this.timings = timings;
        this.interpolators = interpolators;
    }

    public static final float getProgress(Timings timings, float f, long j, long j2) {
        return Companion.getProgress(timings, f, j, j2);
    }

    public static /* synthetic */ Animation startAnimation$default(LaunchAnimator launchAnimator, Controller controller, State state, int i, boolean z, boolean z2, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            z = true;
        }
        if ((i2 & 16) != 0) {
            z2 = false;
        }
        return launchAnimator.startAnimation(controller, state, i, z, z2);
    }

    public static final void startAnimation$maybeUpdateEndState(Ref.IntRef intRef, State state, Ref.IntRef intRef2, Ref.IntRef intRef3, Ref.IntRef intRef4, Ref.FloatRef floatRef, Ref.IntRef intRef5) {
        if (intRef.element == state.getTop() && intRef2.element == state.getBottom() && intRef3.element == state.getLeft() && intRef4.element == state.getRight()) {
            return;
        }
        intRef.element = state.getTop();
        intRef2.element = state.getBottom();
        intRef3.element = state.getLeft();
        int right = state.getRight();
        intRef4.element = right;
        int i = intRef3.element;
        floatRef.element = (i + right) / 2.0f;
        intRef5.element = right - i;
    }

    public final void applyStateToWindowBackgroundLayer(GradientDrawable gradientDrawable, State state, float f, View view, boolean z, boolean z2) {
        view.getLocationOnScreen(this.launchContainerLocation);
        gradientDrawable.setBounds(state.getLeft() - this.launchContainerLocation[0], state.getTop() - this.launchContainerLocation[1], state.getRight() - this.launchContainerLocation[0], state.getBottom() - this.launchContainerLocation[1]);
        this.cornerRadii[0] = state.getTopCornerRadius();
        this.cornerRadii[1] = state.getTopCornerRadius();
        this.cornerRadii[2] = state.getTopCornerRadius();
        this.cornerRadii[3] = state.getTopCornerRadius();
        this.cornerRadii[4] = state.getBottomCornerRadius();
        this.cornerRadii[5] = state.getBottomCornerRadius();
        this.cornerRadii[6] = state.getBottomCornerRadius();
        this.cornerRadii[7] = state.getBottomCornerRadius();
        gradientDrawable.setCornerRadii(this.cornerRadii);
        Companion companion = Companion;
        Timings timings = this.timings;
        float progress = companion.getProgress(timings, f, timings.getContentBeforeFadeOutDelay(), this.timings.getContentBeforeFadeOutDuration());
        if (progress < 1.0f) {
            gradientDrawable.setAlpha(MathKt__MathJVMKt.roundToInt(this.interpolators.getContentBeforeFadeOutInterpolator().getInterpolation(progress) * 255));
        } else if (!z) {
            gradientDrawable.setAlpha(255);
        } else {
            Timings timings2 = this.timings;
            gradientDrawable.setAlpha(MathKt__MathJVMKt.roundToInt((1 - this.interpolators.getContentAfterFadeInInterpolator().getInterpolation(companion.getProgress(timings2, f, timings2.getContentAfterFadeInDelay(), this.timings.getContentAfterFadeInDuration()))) * 255));
            if (z2) {
                gradientDrawable.setXfermode(SRC_MODE);
            }
        }
    }

    public final boolean isExpandingFullyAbove$frameworks__base__packages__SystemUI__animation__android_common__SystemUIAnimationLib(View view, State state) {
        view.getLocationOnScreen(this.launchContainerLocation);
        boolean z = true;
        if (state.getTop() > this.launchContainerLocation[1] || state.getBottom() < this.launchContainerLocation[1] + view.getHeight() || state.getLeft() > this.launchContainerLocation[0] || state.getRight() < this.launchContainerLocation[0] + view.getWidth()) {
            z = false;
        }
        return z;
    }

    public final Animation startAnimation(final Controller controller, final State state, int i, final boolean z, final boolean z2) {
        final State createAnimatorState = controller.createAnimatorState();
        final int top = createAnimatorState.getTop();
        final int bottom = createAnimatorState.getBottom();
        int left = createAnimatorState.getLeft();
        int right = createAnimatorState.getRight();
        final float f = (left + right) / 2.0f;
        final float topCornerRadius = createAnimatorState.getTopCornerRadius();
        final float bottomCornerRadius = createAnimatorState.getBottomCornerRadius();
        final Ref.IntRef intRef = new Ref.IntRef();
        intRef.element = state.getTop();
        final Ref.IntRef intRef2 = new Ref.IntRef();
        intRef2.element = state.getBottom();
        final Ref.IntRef intRef3 = new Ref.IntRef();
        intRef3.element = state.getLeft();
        final Ref.IntRef intRef4 = new Ref.IntRef();
        intRef4.element = state.getRight();
        final Ref.FloatRef floatRef = new Ref.FloatRef();
        floatRef.element = (intRef3.element + intRef4.element) / 2.0f;
        final Ref.IntRef intRef5 = new Ref.IntRef();
        intRef5.element = intRef4.element - intRef3.element;
        final float topCornerRadius2 = state.getTopCornerRadius();
        final float bottomCornerRadius2 = state.getBottomCornerRadius();
        final ViewGroup launchContainer = controller.getLaunchContainer();
        final boolean isExpandingFullyAbove$frameworks__base__packages__SystemUI__animation__android_common__SystemUIAnimationLib = isExpandingFullyAbove$frameworks__base__packages__SystemUI__animation__android_common__SystemUIAnimationLib(launchContainer, state);
        final GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(i);
        gradientDrawable.setAlpha(0);
        final ValueAnimator ofFloat = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
        ofFloat.setDuration(this.timings.getTotalDuration());
        ofFloat.setInterpolator(com.android.systemui.animation.Interpolators.LINEAR);
        final View openingWindowSyncView = controller.getOpeningWindowSyncView();
        ViewOverlay overlay = openingWindowSyncView != null ? openingWindowSyncView.getOverlay() : null;
        boolean z3 = (openingWindowSyncView == null || Intrinsics.areEqual(openingWindowSyncView.getViewRootImpl(), controller.getLaunchContainer().getViewRootImpl())) ? false : true;
        final ViewGroupOverlay overlay2 = launchContainer.getOverlay();
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        final Ref.BooleanRef booleanRef2 = new Ref.BooleanRef();
        final boolean z4 = z3;
        final ViewOverlay viewOverlay = overlay;
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.animation.LaunchAnimator$startAnimation$1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                ViewOverlay viewOverlay2;
                LaunchAnimator.Controller.this.onLaunchAnimationEnd(isExpandingFullyAbove$frameworks__base__packages__SystemUI__animation__android_common__SystemUIAnimationLib);
                overlay2.remove(gradientDrawable);
                if (!z4 || (viewOverlay2 = viewOverlay) == null) {
                    return;
                }
                viewOverlay2.remove(gradientDrawable);
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator, boolean z5) {
                LaunchAnimator.Controller.this.onLaunchAnimationStart(isExpandingFullyAbove$frameworks__base__packages__SystemUI__animation__android_common__SystemUIAnimationLib);
                overlay2.add(gradientDrawable);
            }
        });
        final int i2 = right - left;
        final boolean z5 = z3;
        final ViewOverlay viewOverlay2 = overlay;
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.animation.LaunchAnimator$startAnimation$2
            /* JADX WARN: Multi-variable type inference failed */
            /* JADX WARN: Type inference failed for: r0v56, types: [android.view.View] */
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                LaunchAnimator.Interpolators interpolators;
                LaunchAnimator.Interpolators interpolators2;
                LaunchAnimator.Timings timings;
                LaunchAnimator.Timings timings2;
                LaunchAnimator.Timings timings3;
                ViewGroup launchContainer2;
                if (booleanRef.element) {
                    return;
                }
                LaunchAnimator.startAnimation$maybeUpdateEndState(intRef, state, intRef2, intRef3, intRef4, floatRef, intRef5);
                float animatedFraction = valueAnimator.getAnimatedFraction();
                interpolators = this.interpolators;
                float interpolation = interpolators.getPositionInterpolator().getInterpolation(animatedFraction);
                interpolators2 = this.interpolators;
                float lerp = MathUtils.lerp(f, floatRef.element, interpolators2.getPositionXInterpolator().getInterpolation(animatedFraction));
                float lerp2 = MathUtils.lerp(i2, intRef5.element, interpolation) / 2.0f;
                createAnimatorState.setTop(MathKt__MathJVMKt.roundToInt(MathUtils.lerp(top, intRef.element, interpolation)));
                createAnimatorState.setBottom(MathKt__MathJVMKt.roundToInt(MathUtils.lerp(bottom, intRef2.element, interpolation)));
                createAnimatorState.setLeft(MathKt__MathJVMKt.roundToInt(lerp - lerp2));
                createAnimatorState.setRight(MathKt__MathJVMKt.roundToInt(lerp + lerp2));
                createAnimatorState.setTopCornerRadius(MathUtils.lerp(topCornerRadius, topCornerRadius2, interpolation));
                createAnimatorState.setBottomCornerRadius(MathUtils.lerp(bottomCornerRadius, bottomCornerRadius2, interpolation));
                LaunchAnimator.State state2 = createAnimatorState;
                LaunchAnimator.Companion companion = LaunchAnimator.Companion;
                timings = this.timings;
                timings2 = this.timings;
                long contentBeforeFadeOutDelay = timings2.getContentBeforeFadeOutDelay();
                timings3 = this.timings;
                state2.setVisible(companion.getProgress(timings, animatedFraction, contentBeforeFadeOutDelay, timings3.getContentBeforeFadeOutDuration()) < 1.0f);
                if (z5 && !createAnimatorState.getVisible()) {
                    Ref.BooleanRef booleanRef3 = booleanRef2;
                    if (!booleanRef3.element) {
                        booleanRef3.element = true;
                        overlay2.remove(gradientDrawable);
                        ViewOverlay viewOverlay3 = viewOverlay2;
                        Intrinsics.checkNotNull(viewOverlay3);
                        viewOverlay3.add(gradientDrawable);
                        ViewRootSync.INSTANCE.synchronizeNextDraw(launchContainer, openingWindowSyncView, new Function0<Unit>() { // from class: com.android.systemui.animation.LaunchAnimator$startAnimation$2.1
                            public /* bridge */ /* synthetic */ Object invoke() {
                                m1445invoke();
                                return Unit.INSTANCE;
                            }

                            /* renamed from: invoke  reason: collision with other method in class */
                            public final void m1445invoke() {
                            }
                        });
                    }
                }
                if (booleanRef2.element) {
                    launchContainer2 = openingWindowSyncView;
                    Intrinsics.checkNotNull(launchContainer2);
                } else {
                    launchContainer2 = controller.getLaunchContainer();
                }
                this.applyStateToWindowBackgroundLayer(gradientDrawable, createAnimatorState, animatedFraction, launchContainer2, z, z2);
                controller.onLaunchAnimationProgress(createAnimatorState, interpolation, animatedFraction);
            }
        });
        ofFloat.start();
        return new Animation() { // from class: com.android.systemui.animation.LaunchAnimator$startAnimation$3
            @Override // com.android.systemui.animation.LaunchAnimator.Animation
            public void cancel() {
                booleanRef.element = true;
                ofFloat.cancel();
            }
        };
    }
}