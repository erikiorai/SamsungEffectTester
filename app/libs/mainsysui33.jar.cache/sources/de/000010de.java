package com.android.systemui.animation;

import android.util.MathUtils;
import com.android.settingslib.widget.ActionBarShadowController;

/* loaded from: mainsysui33.jar:com/android/systemui/animation/ShadeInterpolation.class */
public final class ShadeInterpolation {
    public static final ShadeInterpolation INSTANCE = new ShadeInterpolation();

    public static final float getContentAlpha(float f) {
        return INSTANCE.interpolateEaseInOut(MathUtils.constrainedMap((float) ActionBarShadowController.ELEVATION_LOW, 1.0f, 0.3f, 1.0f, f));
    }

    public static final float getNotificationScrimAlpha(float f) {
        return INSTANCE.interpolateEaseInOut(MathUtils.constrainedMap((float) ActionBarShadowController.ELEVATION_LOW, 1.0f, (float) ActionBarShadowController.ELEVATION_LOW, 0.5f, f));
    }

    public final float interpolateEaseInOut(float f) {
        float f2 = (f * 1.2f) - 0.2f;
        float f3 = 0.0f;
        if (f2 > ActionBarShadowController.ELEVATION_LOW) {
            float f4 = 1.0f - f2;
            double d = 1.0f;
            f3 = (float) (d - (0.5f * (d - Math.cos((3.14159f * f4) * f4))));
        }
        return f3;
    }
}