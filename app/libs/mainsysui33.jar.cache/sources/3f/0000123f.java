package com.android.systemui.biometrics.dagger;

import com.android.systemui.biometrics.udfps.BoundingBoxOverlapDetector;
import com.android.systemui.biometrics.udfps.EllipseOverlapDetector;
import com.android.systemui.biometrics.udfps.OverlapDetector;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/dagger/UdfpsModule.class */
public interface UdfpsModule {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* loaded from: mainsysui33.jar:com/android/systemui/biometrics/dagger/UdfpsModule$Companion.class */
    public static final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();

        public final OverlapDetector providesOverlapDetector(FeatureFlags featureFlags) {
            return featureFlags.isEnabled(Flags.UDFPS_ELLIPSE_DETECTION) ? new EllipseOverlapDetector(0, 1, null) : new BoundingBoxOverlapDetector();
        }
    }
}