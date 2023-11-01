package com.airbnb.lottie.model.animatable;

import android.graphics.PointF;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.PathKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.PointKeyframeAnimation;
import com.airbnb.lottie.value.Keyframe;
import java.util.List;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/model/animatable/AnimatablePathValue.class */
public class AnimatablePathValue implements AnimatableValue<PointF, PointF> {
    public final List<Keyframe<PointF>> keyframes;

    public AnimatablePathValue(List<Keyframe<PointF>> list) {
        this.keyframes = list;
    }

    @Override // com.airbnb.lottie.model.animatable.AnimatableValue
    public BaseKeyframeAnimation<PointF, PointF> createAnimation() {
        return this.keyframes.get(0).isStatic() ? new PointKeyframeAnimation(this.keyframes) : new PathKeyframeAnimation(this.keyframes);
    }

    @Override // com.airbnb.lottie.model.animatable.AnimatableValue
    public List<Keyframe<PointF>> getKeyframes() {
        return this.keyframes;
    }

    @Override // com.airbnb.lottie.model.animatable.AnimatableValue
    public boolean isStatic() {
        boolean z = false;
        if (this.keyframes.size() == 1) {
            z = false;
            if (this.keyframes.get(0).isStatic()) {
                z = true;
            }
        }
        return z;
    }
}