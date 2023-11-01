package com.android.systemui.dreams;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.Interpolator;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$dimen;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.dreams.complication.ComplicationHostViewController;
import com.android.systemui.dreams.complication.ComplicationLayoutParams;
import com.android.systemui.keyguard.ui.viewmodel.DreamingToLockscreenTransitionViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.BlurUtils;
import com.android.systemui.statusbar.CrossFadeHelper;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;
import kotlin.jvm.functions.Function0;
import kotlin.time.Duration;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/DreamOverlayAnimationsController.class */
public final class DreamOverlayAnimationsController {
    public final ConfigurationController configController;
    public Animator mAnimator;
    public final BlurUtils mBlurUtils;
    public final ComplicationHostViewController mComplicationHostViewController;
    public Map<Integer, Float> mCurrentAlphaAtPosition = new LinkedHashMap();
    public float mCurrentBlurRadius;
    public final int mDreamBlurRadius;
    public final long mDreamInBlurAnimDurationMs;
    public final long mDreamInComplicationsAnimDurationMs;
    public final int mDreamInTranslationYDistance;
    public final long mDreamInTranslationYDurationMs;
    public final DreamOverlayStateController mOverlayStateController;
    public final DreamOverlayStatusBarViewController mStatusBarViewController;
    public final DreamingToLockscreenTransitionViewModel transitionViewModel;
    public View view;

    /* loaded from: mainsysui33.jar:com/android/systemui/dreams/DreamOverlayAnimationsController$ConfigurationBasedDimensions.class */
    public static final class ConfigurationBasedDimensions {
        public final int translationYPx;

        public ConfigurationBasedDimensions(int i) {
            this.translationYPx = i;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof ConfigurationBasedDimensions) && this.translationYPx == ((ConfigurationBasedDimensions) obj).translationYPx;
        }

        public final int getTranslationYPx() {
            return this.translationYPx;
        }

        public int hashCode() {
            return Integer.hashCode(this.translationYPx);
        }

        public String toString() {
            int i = this.translationYPx;
            return "ConfigurationBasedDimensions(translationYPx=" + i + ")";
        }
    }

    public DreamOverlayAnimationsController(BlurUtils blurUtils, ComplicationHostViewController complicationHostViewController, DreamOverlayStatusBarViewController dreamOverlayStatusBarViewController, DreamOverlayStateController dreamOverlayStateController, int i, DreamingToLockscreenTransitionViewModel dreamingToLockscreenTransitionViewModel, ConfigurationController configurationController, long j, long j2, int i2, long j3) {
        this.mBlurUtils = blurUtils;
        this.mComplicationHostViewController = complicationHostViewController;
        this.mStatusBarViewController = dreamOverlayStatusBarViewController;
        this.mOverlayStateController = dreamOverlayStateController;
        this.mDreamBlurRadius = i;
        this.transitionViewModel = dreamingToLockscreenTransitionViewModel;
        this.configController = configurationController;
        this.mDreamInBlurAnimDurationMs = j;
        this.mDreamInComplicationsAnimDurationMs = j2;
        this.mDreamInTranslationYDistance = i2;
        this.mDreamInTranslationYDurationMs = j3;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.DreamOverlayAnimationsController$blurAnimator$1$1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static final /* synthetic */ BlurUtils access$getMBlurUtils$p(DreamOverlayAnimationsController dreamOverlayAnimationsController) {
        return dreamOverlayAnimationsController.mBlurUtils;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.DreamOverlayAnimationsController$blurAnimator$1$1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static final /* synthetic */ float access$getMCurrentBlurRadius$p(DreamOverlayAnimationsController dreamOverlayAnimationsController) {
        return dreamOverlayAnimationsController.mCurrentBlurRadius;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.DreamOverlayAnimationsController$alphaAnimator$1$1.1.accept(int):void] */
    public static final /* synthetic */ void access$setElementsAlphaAtPosition(DreamOverlayAnimationsController dreamOverlayAnimationsController, float f, int i, boolean z) {
        dreamOverlayAnimationsController.setElementsAlphaAtPosition(f, i, z);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.DreamOverlayAnimationsController$translationYAnimator$1$1.1.accept(int):void] */
    public static final /* synthetic */ void access$setElementsTranslationYAtPosition(DreamOverlayAnimationsController dreamOverlayAnimationsController, float f, int i) {
        dreamOverlayAnimationsController.setElementsTranslationYAtPosition(f, i);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.dreams.DreamOverlayAnimationsController$blurAnimator$1$1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static final /* synthetic */ void access$setMCurrentBlurRadius$p(DreamOverlayAnimationsController dreamOverlayAnimationsController, float f) {
        dreamOverlayAnimationsController.mCurrentBlurRadius = f;
    }

    public static /* synthetic */ Animator alphaAnimator$default(DreamOverlayAnimationsController dreamOverlayAnimationsController, float f, float f2, long j, long j2, int i, Interpolator interpolator, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            j2 = 0;
        }
        if ((i2 & 16) != 0) {
            i = 3;
        }
        if ((i2 & 32) != 0) {
            interpolator = Interpolators.LINEAR;
        }
        return dreamOverlayAnimationsController.alphaAnimator(f, f2, j, j2, i, interpolator);
    }

    public static /* synthetic */ Animator blurAnimator$default(DreamOverlayAnimationsController dreamOverlayAnimationsController, View view, float f, float f2, long j, long j2, Interpolator interpolator, int i, Object obj) {
        if ((i & 16) != 0) {
            j2 = 0;
        }
        if ((i & 32) != 0) {
            interpolator = Interpolators.LINEAR;
        }
        return dreamOverlayAnimationsController.blurAnimator(view, f, f2, j, j2, interpolator);
    }

    public static /* synthetic */ void startEntryAnimations$default(DreamOverlayAnimationsController dreamOverlayAnimationsController, Function0 function0, int i, Object obj) {
        if ((i & 1) != 0) {
            function0 = new Function0<AnimatorSet>() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController$startEntryAnimations$1
                /* JADX DEBUG: Method merged with bridge method */
                /* renamed from: invoke */
                public final AnimatorSet m2541invoke() {
                    return new AnimatorSet();
                }
            };
        }
        dreamOverlayAnimationsController.startEntryAnimations(function0);
    }

    public static /* synthetic */ Animator translationYAnimator$default(DreamOverlayAnimationsController dreamOverlayAnimationsController, float f, float f2, long j, long j2, int i, Interpolator interpolator, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            j2 = 0;
        }
        if ((i2 & 16) != 0) {
            i = 3;
        }
        if ((i2 & 32) != 0) {
            interpolator = Interpolators.LINEAR;
        }
        return dreamOverlayAnimationsController.translationYAnimator(f, f2, j, j2, i, interpolator);
    }

    public final Animator alphaAnimator(final float f, final float f2, long j, long j2, final int i, Interpolator interpolator) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(f, f2);
        ofFloat.setDuration(j);
        ofFloat.setStartDelay(j2);
        ofFloat.setInterpolator(interpolator);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController$alphaAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(final ValueAnimator valueAnimator) {
                final DreamOverlayAnimationsController dreamOverlayAnimationsController = this;
                final float f3 = f2;
                final float f4 = f;
                ComplicationLayoutParams.iteratePositions(new Consumer() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController$alphaAnimator$1$1.1
                    public final void accept(int i2) {
                        DreamOverlayAnimationsController.access$setElementsAlphaAtPosition(DreamOverlayAnimationsController.this, ((Float) valueAnimator.getAnimatedValue()).floatValue(), i2, f3 < f4);
                    }

                    @Override // java.util.function.Consumer
                    public /* bridge */ /* synthetic */ void accept(Object obj) {
                        accept(((Number) obj).intValue());
                    }
                }, i);
            }
        });
        return ofFloat;
    }

    public final Animator blurAnimator(final View view, float f, float f2, long j, long j2, Interpolator interpolator) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(f, f2);
        ofFloat.setDuration(j);
        ofFloat.setStartDelay(j2);
        ofFloat.setInterpolator(interpolator);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController$blurAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                DreamOverlayAnimationsController.access$setMCurrentBlurRadius$p(DreamOverlayAnimationsController.this, ((Float) valueAnimator.getAnimatedValue()).floatValue());
                DreamOverlayAnimationsController.access$getMBlurUtils$p(DreamOverlayAnimationsController.this).applyBlur(view.getViewRootImpl(), (int) DreamOverlayAnimationsController.access$getMCurrentBlurRadius$p(DreamOverlayAnimationsController.this), false);
            }
        });
        return ofFloat;
    }

    public final void cancelAnimations() {
        Animator animator = this.mAnimator;
        if (animator != null) {
            animator.cancel();
        }
        this.mAnimator = null;
    }

    public final void init(View view) {
        this.view = view;
        RepeatWhenAttachedKt.repeatWhenAttached$default(view, null, new DreamOverlayAnimationsController$init$1(this, view, null), 1, null);
    }

    public final ConfigurationBasedDimensions loadFromResources(View view) {
        return new ConfigurationBasedDimensions(view.getResources().getDimensionPixelSize(R$dimen.dream_overlay_exit_y_offset));
    }

    public final void setElementsAlphaAtPosition(float f, int i, boolean z) {
        this.mCurrentAlphaAtPosition.put(Integer.valueOf(i), Float.valueOf(f));
        for (View view : this.mComplicationHostViewController.getViewsAtPosition(i)) {
            if (z) {
                CrossFadeHelper.fadeOut(view, 1 - f, false);
            } else {
                CrossFadeHelper.fadeIn(view, f, false);
            }
        }
        if (i == 1) {
            this.mStatusBarViewController.setFadeAmount(f, z);
        }
    }

    public final void setElementsTranslationYAtPosition(float f, int i) {
        for (View view : this.mComplicationHostViewController.getViewsAtPosition(i)) {
            view.setTranslationY(f);
        }
        if (i == 1) {
            this.mStatusBarViewController.setTranslationY(f);
        }
    }

    public final void startEntryAnimations() {
        startEntryAnimations$default(this, null, 1, null);
    }

    public final void startEntryAnimations(Function0<AnimatorSet> function0) {
        cancelAnimations();
        Object invoke = function0.invoke();
        AnimatorSet animatorSet = (AnimatorSet) invoke;
        View view = this.view;
        View view2 = view;
        if (view == null) {
            view2 = null;
        }
        float f = this.mDreamBlurRadius;
        long j = this.mDreamInBlurAnimDurationMs;
        Interpolator interpolator = Interpolators.EMPHASIZED_DECELERATE;
        animatorSet.playTogether(blurAnimator$default(this, view2, f, ActionBarShadowController.ELEVATION_LOW, j, 0L, interpolator, 16, null), alphaAnimator$default(this, ActionBarShadowController.ELEVATION_LOW, 1.0f, this.mDreamInComplicationsAnimDurationMs, 0L, 0, Interpolators.LINEAR, 24, null), translationYAnimator$default(this, this.mDreamInTranslationYDistance, ActionBarShadowController.ELEVATION_LOW, this.mDreamInTranslationYDurationMs, 0L, 0, interpolator, 24, null));
        animatorSet.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController$startEntryAnimations$lambda$1$$inlined$doOnEnd$1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                DreamOverlayStateController dreamOverlayStateController;
                DreamOverlayAnimationsController.this.mAnimator = null;
                dreamOverlayStateController = DreamOverlayAnimationsController.this.mOverlayStateController;
                dreamOverlayStateController.setEntryAnimationsFinished(true);
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }
        });
        animatorSet.start();
        this.mAnimator = (Animator) invoke;
    }

    public final Animator translationYAnimator(float f, float f2, long j, long j2, final int i, Interpolator interpolator) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(f, f2);
        ofFloat.setDuration(j);
        ofFloat.setStartDelay(j2);
        ofFloat.setInterpolator(interpolator);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController$translationYAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(final ValueAnimator valueAnimator) {
                final DreamOverlayAnimationsController dreamOverlayAnimationsController = this;
                ComplicationLayoutParams.iteratePositions(new Consumer() { // from class: com.android.systemui.dreams.DreamOverlayAnimationsController$translationYAnimator$1$1.1
                    public final void accept(int i2) {
                        DreamOverlayAnimationsController.access$setElementsTranslationYAtPosition(DreamOverlayAnimationsController.this, ((Float) valueAnimator.getAnimatedValue()).floatValue(), i2);
                    }

                    @Override // java.util.function.Consumer
                    public /* bridge */ /* synthetic */ void accept(Object obj) {
                        accept(((Number) obj).intValue());
                    }
                }, i);
            }
        });
        return ofFloat;
    }

    public final void wakeUp(Runnable runnable, DelayableExecutor delayableExecutor) {
        cancelAnimations();
        delayableExecutor.executeDelayed(runnable, Duration.getInWholeMilliseconds-impl(DreamingToLockscreenTransitionViewModel.Companion.m3085getDREAM_ANIMATION_DURATIONUwyO8pc()));
    }
}