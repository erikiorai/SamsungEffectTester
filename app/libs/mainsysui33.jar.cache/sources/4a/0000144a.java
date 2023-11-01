package com.android.systemui.controls.management;

import android.animation.Animator;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;
import com.android.settingslib.widget.ActionBarShadowController;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/management/WindowTransition.class */
public final class WindowTransition extends Transition {
    public final Function1<View, Animator> animator;

    /* JADX DEBUG: Multi-variable search result rejected for r4v0, resolved type: kotlin.jvm.functions.Function1<? super android.view.View, ? extends android.animation.Animator> */
    /* JADX WARN: Multi-variable type inference failed */
    public WindowTransition(Function1<? super View, ? extends Animator> function1) {
        this.animator = function1;
    }

    @Override // android.transition.Transition
    public void captureEndValues(TransitionValues transitionValues) {
        transitionValues.values.put("item", Float.valueOf(1.0f));
    }

    @Override // android.transition.Transition
    public void captureStartValues(TransitionValues transitionValues) {
        transitionValues.values.put("item", Float.valueOf((float) ActionBarShadowController.ELEVATION_LOW));
    }

    @Override // android.transition.Transition
    public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        Function1<View, Animator> function1 = this.animator;
        Intrinsics.checkNotNull(transitionValues);
        return (Animator) function1.invoke(transitionValues.view);
    }
}