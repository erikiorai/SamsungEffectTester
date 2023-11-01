package com.android.keyguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$dimen;
import com.android.systemui.animation.Interpolators;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Ref;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSecurityViewTransition.class */
public final class KeyguardSecurityViewTransition extends Transition {
    public static final Companion Companion = new Companion(null);

    /* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSecurityViewTransition$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Override // android.transition.Transition
    public void captureEndValues(TransitionValues transitionValues) {
        if (transitionValues != null) {
            captureValues(transitionValues);
        }
    }

    @Override // android.transition.Transition
    public void captureStartValues(TransitionValues transitionValues) {
        if (transitionValues != null) {
            captureValues(transitionValues);
        }
    }

    public final void captureValues(TransitionValues transitionValues) {
        Rect rect = new Rect();
        rect.left = transitionValues.view.getLeft();
        rect.top = transitionValues.view.getTop();
        rect.right = transitionValues.view.getRight();
        rect.bottom = transitionValues.view.getBottom();
        transitionValues.values.put("securityViewLocation:bounds", rect);
    }

    @Override // android.transition.Transition
    public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        Animator animator = null;
        if (viewGroup != null) {
            animator = null;
            if (transitionValues != null) {
                if (transitionValues2 == null) {
                    animator = null;
                } else {
                    final Interpolator loadInterpolator = AnimationUtils.loadInterpolator(viewGroup.getContext(), 17563674);
                    final Interpolator interpolator = Interpolators.FAST_OUT_LINEAR_IN;
                    final Interpolator interpolator2 = Interpolators.LINEAR_OUT_SLOW_IN;
                    final Ref.ObjectRef objectRef = new Ref.ObjectRef();
                    ValueAnimator ofFloat = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
                    objectRef.element = ofFloat;
                    ofFloat.setDuration(500L);
                    ((ValueAnimator) objectRef.element).setInterpolator(Interpolators.LINEAR);
                    final Rect rect = (Rect) transitionValues.values.get("securityViewLocation:bounds");
                    final Rect rect2 = (Rect) transitionValues2.values.get("securityViewLocation:bounds");
                    final View view = transitionValues.view;
                    final int dimension = (int) viewGroup.getResources().getDimension(R$dimen.security_shift_animation_translation);
                    boolean z = view.hasOverlappingRendering() && view.getLayerType() != 2;
                    if (z) {
                        view.setLayerType(2, null);
                    }
                    final float alpha = view.getAlpha();
                    final boolean z2 = z;
                    ((ValueAnimator) objectRef.element).addListener(new AnimatorListenerAdapter() { // from class: com.android.keyguard.KeyguardSecurityViewTransition$createAnimator$1
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator2) {
                            objectRef.element = null;
                            if (z2) {
                                view.setLayerType(0, null);
                            }
                        }
                    });
                    ((ValueAnimator) objectRef.element).addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.keyguard.KeyguardSecurityViewTransition$createAnimator$2
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            boolean z3 = true;
                            boolean z4 = valueAnimator.getAnimatedFraction() < 0.2f;
                            float interpolation = loadInterpolator.getInterpolation(valueAnimator.getAnimatedFraction());
                            int i = dimension;
                            int i2 = (int) (interpolation * i);
                            int i3 = i - i2;
                            if (rect2.left >= rect.left) {
                                z3 = false;
                            }
                            int i4 = i2;
                            int i5 = i3;
                            if (z3) {
                                i4 = -i2;
                                i5 = -i3;
                            }
                            if (z4) {
                                view.setAlpha(interpolator.getInterpolation(MathUtils.constrainedMap(1.0f, (float) ActionBarShadowController.ELEVATION_LOW, (float) ActionBarShadowController.ELEVATION_LOW, 0.2f, valueAnimator.getAnimatedFraction())) * alpha);
                                View view2 = view;
                                if (view2 instanceof KeyguardSecurityViewFlipper) {
                                    Rect rect3 = rect;
                                    view2.setLeftTopRightBottom(rect3.left + i4, rect3.top, rect3.right + i4, rect3.bottom);
                                    return;
                                }
                                Rect rect4 = rect;
                                view2.setLeftTopRightBottom(rect4.left, rect4.top, rect4.right, rect4.bottom);
                                return;
                            }
                            view.setAlpha(interpolator2.getInterpolation(MathUtils.constrainedMap((float) ActionBarShadowController.ELEVATION_LOW, 1.0f, 0.2f, 1.0f, valueAnimator.getAnimatedFraction())));
                            View view3 = view;
                            if (view3 instanceof KeyguardSecurityViewFlipper) {
                                Rect rect5 = rect2;
                                view3.setLeftTopRightBottom(rect5.left - i5, rect5.top, rect5.right - i5, rect5.bottom);
                                return;
                            }
                            Rect rect6 = rect2;
                            view3.setLeftTopRightBottom(rect6.left, rect6.top, rect6.right, rect6.bottom);
                        }
                    });
                    ((ValueAnimator) objectRef.element).start();
                    animator = (Animator) objectRef.element;
                }
            }
        }
        return animator;
    }

    @Override // android.transition.Transition
    public String[] getTransitionProperties() {
        return new String[]{"securityViewLocation:bounds"};
    }
}