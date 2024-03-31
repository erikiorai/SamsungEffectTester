package com.aj.effect.interpolator;

import android.view.animation.BaseInterpolator;

public class QuintEaseIn extends BaseInterpolator {
    public QuintEaseIn() {
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float t) {
        return in(t);
    }

    private float in(float t) {
        return t * t * t * t * t;
    }
}