package com.android.systemui.media.controls.ui;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import com.android.internal.annotations.VisibleForTesting;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.monet.ColorScheme;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/AnimatingColorTransition.class */
public class AnimatingColorTransition implements ValueAnimator.AnimatorUpdateListener {
    public final Function1<Integer, Unit> applyColor;
    public int currentColor;
    public final int defaultColor;
    public final Function1<ColorScheme, Integer> extractColor;
    public int sourceColor;
    public int targetColor;
    public final ArgbEvaluator argbEvaluator = new ArgbEvaluator();
    public final ValueAnimator valueAnimator = buildAnimator();

    /* JADX DEBUG: Multi-variable search result rejected for r6v0, resolved type: kotlin.jvm.functions.Function1<? super com.android.systemui.monet.ColorScheme, java.lang.Integer> */
    /* JADX DEBUG: Multi-variable search result rejected for r7v0, resolved type: kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> */
    /* JADX WARN: Multi-variable type inference failed */
    public AnimatingColorTransition(int i, Function1<? super ColorScheme, Integer> function1, Function1<? super Integer, Unit> function12) {
        this.defaultColor = i;
        this.extractColor = function1;
        this.applyColor = function12;
        this.sourceColor = i;
        this.currentColor = i;
        this.targetColor = i;
        function12.invoke(Integer.valueOf(i));
    }

    @VisibleForTesting
    public ValueAnimator buildAnimator() {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
        ofFloat.setDuration(333L);
        ofFloat.addUpdateListener(this);
        return ofFloat;
    }

    public final int getCurrentColor() {
        return this.currentColor;
    }

    public final int getTargetColor() {
        return this.targetColor;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        int intValue = ((Integer) this.argbEvaluator.evaluate(valueAnimator.getAnimatedFraction(), Integer.valueOf(this.sourceColor), Integer.valueOf(this.targetColor))).intValue();
        this.currentColor = intValue;
        this.applyColor.invoke(Integer.valueOf(intValue));
    }

    public boolean updateColorScheme(ColorScheme colorScheme) {
        int intValue = colorScheme == null ? this.defaultColor : ((Number) this.extractColor.invoke(colorScheme)).intValue();
        if (intValue != this.targetColor) {
            this.sourceColor = this.currentColor;
            this.targetColor = intValue;
            this.valueAnimator.cancel();
            this.valueAnimator.start();
            return true;
        }
        return false;
    }
}