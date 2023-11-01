package androidx.core.animation;

import android.content.Context;
import android.util.AttributeSet;

/* loaded from: mainsysui33.jar:androidx/core/animation/LinearInterpolator.class */
public class LinearInterpolator implements Interpolator {
    public LinearInterpolator() {
    }

    public LinearInterpolator(Context context, AttributeSet attributeSet) {
    }

    @Override // androidx.core.animation.Interpolator
    public float getInterpolation(float f) {
        return f;
    }
}