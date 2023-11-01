package com.airbnb.lottie.animation.keyframe;

import android.graphics.PointF;
import com.airbnb.lottie.value.Keyframe;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.List;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/animation/keyframe/PointKeyframeAnimation.class */
public class PointKeyframeAnimation extends KeyframeAnimation<PointF> {
    public final PointF point;

    public PointKeyframeAnimation(List<Keyframe<PointF>> list) {
        super(list);
        this.point = new PointF();
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public PointF getValue(Keyframe<PointF> keyframe, float f) {
        PointF pointF;
        PointF pointF2;
        PointF pointF3 = keyframe.startValue;
        if (pointF3 == null || (pointF = keyframe.endValue) == null) {
            throw new IllegalStateException("Missing values for keyframe.");
        }
        PointF pointF4 = pointF3;
        PointF pointF5 = pointF;
        LottieValueCallback<A> lottieValueCallback = this.valueCallback;
        if (lottieValueCallback == 0 || (pointF2 = (PointF) lottieValueCallback.getValueInternal(keyframe.startFrame, keyframe.endFrame.floatValue(), pointF4, pointF5, f, getLinearCurrentKeyframeProgress(), getProgress())) == null) {
            PointF pointF6 = this.point;
            float f2 = pointF4.x;
            float f3 = pointF5.x;
            float f4 = pointF4.y;
            pointF6.set(f2 + ((f3 - f2) * f), f4 + (f * (pointF5.y - f4)));
            return this.point;
        }
        return pointF2;
    }

    @Override // com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation
    public /* bridge */ /* synthetic */ Object getValue(Keyframe keyframe, float f) {
        return getValue((Keyframe<PointF>) keyframe, f);
    }
}