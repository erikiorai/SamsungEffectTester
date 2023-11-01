package com.airbnb.lottie.animation.keyframe;

import android.graphics.Path;
import android.graphics.PointF;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.Keyframe;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/animation/keyframe/PathKeyframe.class */
public class PathKeyframe extends Keyframe<PointF> {
    public Path path;
    public final Keyframe<PointF> pointKeyFrame;

    public PathKeyframe(LottieComposition lottieComposition, Keyframe<PointF> keyframe) {
        super(lottieComposition, keyframe.startValue, keyframe.endValue, keyframe.interpolator, keyframe.startFrame, keyframe.endFrame);
        this.pointKeyFrame = keyframe;
        createPath();
    }

    public void createPath() {
        T t;
        T t2 = this.endValue;
        boolean z = (t2 == 0 || (t = this.startValue) == 0 || !((PointF) t).equals(((PointF) t2).x, ((PointF) t2).y)) ? false : true;
        T t3 = this.endValue;
        if (t3 == 0 || z) {
            return;
        }
        PointF pointF = (PointF) this.startValue;
        PointF pointF2 = (PointF) t3;
        Keyframe<PointF> keyframe = this.pointKeyFrame;
        this.path = Utils.createPath(pointF, pointF2, keyframe.pathCp1, keyframe.pathCp2);
    }

    public Path getPath() {
        return this.path;
    }
}