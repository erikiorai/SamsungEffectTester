package com.android.systemui.biometrics.udfps;

import android.graphics.Rect;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/udfps/OverlapDetector.class */
public interface OverlapDetector {
    boolean isGoodOverlap(NormalizedTouchData normalizedTouchData, Rect rect);
}