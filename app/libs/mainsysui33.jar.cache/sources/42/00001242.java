package com.android.systemui.biometrics.udfps;

import android.graphics.Rect;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/udfps/BoundingBoxOverlapDetector.class */
public final class BoundingBoxOverlapDetector implements OverlapDetector {
    @Override // com.android.systemui.biometrics.udfps.OverlapDetector
    public boolean isGoodOverlap(NormalizedTouchData normalizedTouchData, Rect rect) {
        return normalizedTouchData.isWithinSensor(rect);
    }
}