package com.android.systemui.battery;

/* loaded from: mainsysui33.jar:com/android/systemui/battery/BatterySpecs.class */
public final class BatterySpecs {
    public static final BatterySpecs INSTANCE = new BatterySpecs();

    public static final float getFullBatteryHeight(float f, boolean z) {
        if (z) {
            f = (f / 20.0f) * 23.0f;
        }
        return f;
    }

    public static final float getFullBatteryWidth(float f, boolean z) {
        if (z) {
            f = (f / 12.0f) * 18.0f;
        }
        return f;
    }

    public static final float getMainBatteryHeight(float f, boolean z) {
        return !z ? f : f * 0.8695652f;
    }

    public static final float getMainBatteryWidth(float f, boolean z) {
        return !z ? f : f * 0.6666667f;
    }
}