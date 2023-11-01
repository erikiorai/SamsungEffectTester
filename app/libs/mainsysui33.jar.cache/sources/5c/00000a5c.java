package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.utils.GammaEvaluator;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieValueCallback;
import com.android.settingslib.widget.ActionBarShadowController;
import java.util.List;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/animation/keyframe/ColorKeyframeAnimation.class */
public class ColorKeyframeAnimation extends KeyframeAnimation<Integer> {
    public ColorKeyframeAnimation(List<Keyframe<Integer>> list) {
        super(list);
    }

    public int getIntValue() {
        return getIntValue(getCurrentKeyframe(), getInterpolatedCurrentKeyframeProgress());
    }

    public int getIntValue(Keyframe<Integer> keyframe, float f) {
        Integer num;
        Integer num2 = keyframe.startValue;
        if (num2 == null || keyframe.endValue == null) {
            throw new IllegalStateException("Missing values for keyframe.");
        }
        int intValue = num2.intValue();
        int intValue2 = keyframe.endValue.intValue();
        LottieValueCallback<A> lottieValueCallback = this.valueCallback;
        return (lottieValueCallback == 0 || (num = (Integer) lottieValueCallback.getValueInternal(keyframe.startFrame, keyframe.endFrame.floatValue(), Integer.valueOf(intValue), Integer.valueOf(intValue2), f, getLinearCurrentKeyframeProgress(), getProgress())) == null) ? GammaEvaluator.evaluate(MiscUtils.clamp(f, (float) ActionBarShadowController.ELEVATION_LOW, 1.0f), intValue, intValue2) : num.intValue();
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public Integer getValue(Keyframe<Integer> keyframe, float f) {
        return Integer.valueOf(getIntValue(keyframe, f));
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public /* bridge */ /* synthetic */ Object getValue(Keyframe keyframe, float f) {
        return getValue((Keyframe<Integer>) keyframe, f);
    }
}