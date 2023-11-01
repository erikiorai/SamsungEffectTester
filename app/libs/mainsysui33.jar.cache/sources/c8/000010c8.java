package com.android.systemui.animation;

import android.graphics.Path;
import android.util.MathUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import com.android.settingslib.widget.ActionBarShadowController;

/* loaded from: mainsysui33.jar:com/android/systemui/animation/Interpolators.class */
public class Interpolators {
    public static final Interpolator ACCELERATE;
    public static final Interpolator ACCELERATE_DECELERATE;
    public static final Interpolator ALPHA_IN;
    public static final Interpolator ALPHA_OUT;
    public static final Interpolator BOUNCE;
    public static final Interpolator CONTROL_STATE;
    public static final Interpolator CUSTOM_40_40;
    public static final Interpolator DECELERATE_QUINT;
    public static final Interpolator FAST_OUT_LINEAR_IN;
    public static final Interpolator FAST_OUT_SLOW_IN;
    public static final Interpolator FAST_OUT_SLOW_IN_REVERSE;
    public static final Interpolator ICON_OVERSHOT;
    public static final Interpolator ICON_OVERSHOT_LESS;
    public static final Interpolator LEGACY;
    public static final Interpolator LEGACY_ACCELERATE;
    public static final Interpolator LEGACY_DECELERATE;
    public static final Interpolator LINEAR;
    public static final Interpolator LINEAR_OUT_SLOW_IN;
    public static final Interpolator PANEL_CLOSE_ACCELERATED;
    public static final Interpolator SLOW_OUT_LINEAR_IN;
    public static final Interpolator TOUCH_RESPONSE;
    public static final Interpolator TOUCH_RESPONSE_REVERSE;
    public static final Interpolator EMPHASIZED = createEmphasizedInterpolator();
    public static final Interpolator EMPHASIZED_ACCELERATE = new PathInterpolator(0.3f, ActionBarShadowController.ELEVATION_LOW, 0.8f, 0.15f);
    public static final Interpolator EMPHASIZED_DECELERATE = new PathInterpolator(0.05f, 0.7f, 0.1f, 1.0f);
    public static final Interpolator STANDARD = new PathInterpolator(0.2f, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, 1.0f);
    public static final Interpolator STANDARD_ACCELERATE = new PathInterpolator(0.3f, ActionBarShadowController.ELEVATION_LOW, 1.0f, 1.0f);
    public static final Interpolator STANDARD_DECELERATE = new PathInterpolator(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, 1.0f);

    static {
        PathInterpolator pathInterpolator = new PathInterpolator(0.4f, ActionBarShadowController.ELEVATION_LOW, 0.2f, 1.0f);
        LEGACY = pathInterpolator;
        PathInterpolator pathInterpolator2 = new PathInterpolator(0.4f, ActionBarShadowController.ELEVATION_LOW, 1.0f, 1.0f);
        LEGACY_ACCELERATE = pathInterpolator2;
        PathInterpolator pathInterpolator3 = new PathInterpolator(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, 0.2f, 1.0f);
        LEGACY_DECELERATE = pathInterpolator3;
        LINEAR = new LinearInterpolator();
        FAST_OUT_SLOW_IN = pathInterpolator;
        FAST_OUT_LINEAR_IN = pathInterpolator2;
        LINEAR_OUT_SLOW_IN = pathInterpolator3;
        FAST_OUT_SLOW_IN_REVERSE = new PathInterpolator(0.8f, ActionBarShadowController.ELEVATION_LOW, 0.6f, 1.0f);
        SLOW_OUT_LINEAR_IN = new PathInterpolator(0.8f, ActionBarShadowController.ELEVATION_LOW, 1.0f, 1.0f);
        ALPHA_IN = new PathInterpolator(0.4f, ActionBarShadowController.ELEVATION_LOW, 1.0f, 1.0f);
        ALPHA_OUT = new PathInterpolator(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, 0.8f, 1.0f);
        ACCELERATE = new AccelerateInterpolator();
        ACCELERATE_DECELERATE = new AccelerateDecelerateInterpolator();
        DECELERATE_QUINT = new DecelerateInterpolator(2.5f);
        CUSTOM_40_40 = new PathInterpolator(0.4f, ActionBarShadowController.ELEVATION_LOW, 0.6f, 1.0f);
        ICON_OVERSHOT = new PathInterpolator(0.4f, ActionBarShadowController.ELEVATION_LOW, 0.2f, 1.4f);
        ICON_OVERSHOT_LESS = new PathInterpolator(0.4f, ActionBarShadowController.ELEVATION_LOW, 0.2f, 1.1f);
        PANEL_CLOSE_ACCELERATED = new PathInterpolator(0.3f, ActionBarShadowController.ELEVATION_LOW, 0.5f, 1.0f);
        BOUNCE = new BounceInterpolator();
        CONTROL_STATE = new PathInterpolator(0.4f, ActionBarShadowController.ELEVATION_LOW, 0.2f, 1.0f);
        TOUCH_RESPONSE = new PathInterpolator(0.3f, ActionBarShadowController.ELEVATION_LOW, 0.1f, 1.0f);
        TOUCH_RESPONSE_REVERSE = new PathInterpolator(0.9f, ActionBarShadowController.ELEVATION_LOW, 0.7f, 1.0f);
    }

    public static PathInterpolator createEmphasizedInterpolator() {
        Path path = new Path();
        path.moveTo(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW);
        path.cubicTo(0.05f, ActionBarShadowController.ELEVATION_LOW, 0.133333f, 0.06f, 0.166666f, 0.4f);
        path.cubicTo(0.208333f, 0.82f, 0.25f, 1.0f, 1.0f, 1.0f);
        return new PathInterpolator(path);
    }

    public static float getOvershootInterpolation(float f) {
        return MathUtils.max((float) ActionBarShadowController.ELEVATION_LOW, (float) (1.0d - Math.exp(f * (-4.0f))));
    }

    public static float getOvershootInterpolation(float f, float f2, float f3) {
        if (f2 == ActionBarShadowController.ELEVATION_LOW || f3 == ActionBarShadowController.ELEVATION_LOW) {
            throw new IllegalArgumentException("Invalid values for overshoot");
        }
        float f4 = 1.0f + f2;
        return MathUtils.max((float) ActionBarShadowController.ELEVATION_LOW, ((float) (1.0d - Math.exp((-(MathUtils.log(f4 / f2) / f3)) * f))) * f4);
    }
}