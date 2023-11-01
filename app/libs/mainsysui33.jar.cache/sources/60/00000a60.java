package com.airbnb.lottie.animation.keyframe;

import com.airbnb.lottie.value.Keyframe;
import java.util.List;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/animation/keyframe/KeyframeAnimation.class */
public abstract class KeyframeAnimation<T> extends BaseKeyframeAnimation<T, T> {
    public KeyframeAnimation(List<? extends Keyframe<T>> list) {
        super(list);
    }
}