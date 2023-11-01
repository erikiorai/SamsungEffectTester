package com.airbnb.lottie.model.animatable;

import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.value.Keyframe;
import java.util.List;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/model/animatable/AnimatableValue.class */
public interface AnimatableValue<K, A> {
    BaseKeyframeAnimation<K, A> createAnimation();

    List<Keyframe<K>> getKeyframes();

    boolean isStatic();
}