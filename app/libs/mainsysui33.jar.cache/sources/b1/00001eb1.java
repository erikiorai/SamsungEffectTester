package com.android.systemui.navigationbar.gestural;

import android.view.animation.DecelerateInterpolator;
import android.view.animation.PathInterpolator;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/gestural/BackPanelControllerKt.class */
public final class BackPanelControllerKt {
    public static final PathInterpolator RUBBER_BAND_INTERPOLATOR = new PathInterpolator(0.2f, 1.0f, 1.0f, 1.0f);
    public static final DecelerateInterpolator DECELERATE_INTERPOLATOR = new DecelerateInterpolator();
    public static final DecelerateInterpolator DECELERATE_INTERPOLATOR_SLOW = new DecelerateInterpolator(0.7f);
}