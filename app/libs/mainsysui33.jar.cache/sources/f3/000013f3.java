package com.android.systemui.controls.management;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Interpolator;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$dimen;
import com.android.systemui.animation.Interpolators;
import kotlin.jvm.functions.Function1;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ControlsAnimations.class */
public final class ControlsAnimations {
    public static final ControlsAnimations INSTANCE = new ControlsAnimations();
    public static float translationY = -1.0f;

    public static final Animator exitAnimation(View view, final Runnable runnable) {
        Log.d("ControlsUiController", "Exit animation for " + view);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "transitionAlpha", ActionBarShadowController.ELEVATION_LOW);
        Interpolator interpolator = Interpolators.ACCELERATE;
        ofFloat.setInterpolator(interpolator);
        ofFloat.setDuration(183L);
        view.setTranslationY(ActionBarShadowController.ELEVATION_LOW);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, "translationY", -translationY);
        ofFloat2.setInterpolator(interpolator);
        ofFloat2.setDuration(183L);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat, ofFloat2);
        if (runnable != null) {
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.controls.management.ControlsAnimations$exitAnimation$1$1$1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    runnable.run();
                }
            });
        }
        return animatorSet;
    }

    public static /* synthetic */ Animator exitAnimation$default(View view, Runnable runnable, int i, Object obj) {
        if ((i & 2) != 0) {
            runnable = null;
        }
        return exitAnimation(view, runnable);
    }

    public static /* synthetic */ LifecycleObserver observerForAnimations$default(ControlsAnimations controlsAnimations, ViewGroup viewGroup, Window window, Intent intent, boolean z, int i, Object obj) {
        if ((i & 8) != 0) {
            z = true;
        }
        return controlsAnimations.observerForAnimations(viewGroup, window, intent, z);
    }

    public final Animator enterAnimation(View view) {
        Log.d("ControlsUiController", "Enter animation for " + view);
        view.setTransitionAlpha(ActionBarShadowController.ELEVATION_LOW);
        view.setAlpha(1.0f);
        view.setTranslationY(translationY);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "transitionAlpha", ActionBarShadowController.ELEVATION_LOW, 1.0f);
        Interpolator interpolator = Interpolators.DECELERATE_QUINT;
        ofFloat.setInterpolator(interpolator);
        ofFloat.setStartDelay(183L);
        ofFloat.setDuration(167L);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, "translationY", ActionBarShadowController.ELEVATION_LOW);
        ofFloat2.setInterpolator(interpolator);
        ofFloat2.setStartDelay(217L);
        ofFloat2.setDuration(217L);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat, ofFloat2);
        return animatorSet;
    }

    public final WindowTransition enterWindowTransition(int i) {
        WindowTransition windowTransition = new WindowTransition(new Function1<View, Animator>() { // from class: com.android.systemui.controls.management.ControlsAnimations$enterWindowTransition$1
            /* JADX DEBUG: Method merged with bridge method */
            public final Animator invoke(View view) {
                return ControlsAnimations.INSTANCE.enterAnimation(view);
            }
        });
        windowTransition.addTarget(i);
        return windowTransition;
    }

    public final WindowTransition exitWindowTransition(int i) {
        WindowTransition windowTransition = new WindowTransition(new Function1<View, Animator>() { // from class: com.android.systemui.controls.management.ControlsAnimations$exitWindowTransition$1
            /* JADX DEBUG: Method merged with bridge method */
            public final Animator invoke(View view) {
                return ControlsAnimations.exitAnimation$default(view, null, 2, null);
            }
        });
        windowTransition.addTarget(i);
        return windowTransition;
    }

    public final LifecycleObserver observerForAnimations(ViewGroup viewGroup, Window window, Intent intent, boolean z) {
        return new LifecycleObserver(intent, viewGroup, z, window) { // from class: com.android.systemui.controls.management.ControlsAnimations$observerForAnimations$1
            public final /* synthetic */ ViewGroup $view;
            public final /* synthetic */ Window $window;
            public boolean showAnimation;

            {
                float f;
                this.$view = viewGroup;
                this.$window = window;
                boolean z2 = false;
                this.showAnimation = intent.getBooleanExtra("extra_animate", false);
                viewGroup.setTransitionGroup(true);
                viewGroup.setTransitionAlpha(ActionBarShadowController.ELEVATION_LOW);
                f = ControlsAnimations.translationY;
                if (f == -1.0f ? true : z2) {
                    if (z) {
                        ControlsAnimations controlsAnimations = ControlsAnimations.INSTANCE;
                        ControlsAnimations.translationY = viewGroup.getContext().getResources().getDimensionPixelSize(R$dimen.global_actions_controls_y_translation);
                        return;
                    }
                    ControlsAnimations controlsAnimations2 = ControlsAnimations.INSTANCE;
                    ControlsAnimations.translationY = ActionBarShadowController.ELEVATION_LOW;
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            public final void enterAnimation() {
                if (this.showAnimation) {
                    ControlsAnimations.INSTANCE.enterAnimation(this.$view).start();
                    this.showAnimation = false;
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            public final void resetAnimation() {
                this.$view.setTranslationY(ActionBarShadowController.ELEVATION_LOW);
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            public final void setup() {
                Window window2 = this.$window;
                ViewGroup viewGroup2 = this.$view;
                window2.setAllowEnterTransitionOverlap(true);
                ControlsAnimations controlsAnimations = ControlsAnimations.INSTANCE;
                window2.setEnterTransition(controlsAnimations.enterWindowTransition(viewGroup2.getId()));
                window2.setExitTransition(controlsAnimations.exitWindowTransition(viewGroup2.getId()));
                window2.setReenterTransition(controlsAnimations.enterWindowTransition(viewGroup2.getId()));
                window2.setReturnTransition(controlsAnimations.exitWindowTransition(viewGroup2.getId()));
            }
        };
    }
}