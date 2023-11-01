package com.android.keyguard;

import android.util.MathUtils;
import com.android.settingslib.widget.ActionBarShadowController;

/* loaded from: mainsysui33.jar:com/android/keyguard/BouncerPanelExpansionCalculator.class */
public final class BouncerPanelExpansionCalculator {
    public static final BouncerPanelExpansionCalculator INSTANCE = new BouncerPanelExpansionCalculator();

    public static final float aboutToShowBouncerProgress(float f) {
        return MathUtils.constrain((f - 0.9f) / 0.1f, (float) ActionBarShadowController.ELEVATION_LOW, 1.0f);
    }

    public static final float getDreamAlphaScaledExpansion(float f) {
        return MathUtils.constrain((f - 0.94f) / 0.06f, (float) ActionBarShadowController.ELEVATION_LOW, 1.0f);
    }

    public static final float getDreamYPositionScaledExpansion(float f) {
        return f >= 0.98f ? 1.0f : ((double) f) < 0.93d ? 0.0f : (f - 0.93f) / 0.05f;
    }

    public static final float getKeyguardClockScaledExpansion(float f) {
        return MathUtils.constrain((f - 0.7f) / 0.3f, (float) ActionBarShadowController.ELEVATION_LOW, 1.0f);
    }

    public static final float showBouncerProgress(float f) {
        return f >= 0.9f ? 1.0f : ((double) f) < 0.6d ? 0.0f : (f - 0.6f) / 0.3f;
    }
}