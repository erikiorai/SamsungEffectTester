package com.aj.effect.interpolator;

import android.view.animation.BaseInterpolator;

public class QuintEaseOut extends BaseInterpolator {
    public QuintEaseOut() {
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float t) {
        return out(t);
    }

    private float out(float t) {
        float t2 = t - 1.0f;
        return (t2 * t2 * t2 * t2 * t2) + 1.0f;
    }

}
