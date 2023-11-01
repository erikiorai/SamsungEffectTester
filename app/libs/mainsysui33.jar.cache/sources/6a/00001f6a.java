package com.android.systemui.plugins;

import android.graphics.Rect;

/* loaded from: mainsysui33.jar:com/android/systemui/plugins/ClockFaceEvents.class */
public interface ClockFaceEvents {

    /* loaded from: mainsysui33.jar:com/android/systemui/plugins/ClockFaceEvents$DefaultImpls.class */
    public static final class DefaultImpls {
        public static void onFontSettingChanged(ClockFaceEvents clockFaceEvents, float f) {
        }

        public static void onRegionDarknessChanged(ClockFaceEvents clockFaceEvents, boolean z) {
        }

        public static void onTargetRegionChanged(ClockFaceEvents clockFaceEvents, Rect rect) {
        }
    }

    void onFontSettingChanged(float f);

    void onRegionDarknessChanged(boolean z);

    void onTargetRegionChanged(Rect rect);
}