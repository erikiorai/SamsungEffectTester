package com.android.systemui.qs;

import android.view.animation.Interpolator;
import com.android.settingslib.widget.ActionBarShadowController;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/QSExpansionPathInterpolator.class */
public final class QSExpansionPathInterpolator {
    public PathInterpolatorBuilder pathInterpolatorBuilder = new PathInterpolatorBuilder(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, 1.0f);

    public final Interpolator getXInterpolator() {
        return this.pathInterpolatorBuilder.getXInterpolator();
    }

    public final Interpolator getYInterpolator() {
        return this.pathInterpolatorBuilder.getYInterpolator();
    }
}