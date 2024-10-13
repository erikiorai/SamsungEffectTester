package com.android.keyguard.sec.festivaleffect.common;

import static com.android.keyguard.sec.KeyguardEffectViewController.mRes;

public class DensityUtil {
    public static final int BASE_DENSITY_DPI = 480;
    public static final int DEFAULT_DEVICE_HEIGHT = 640;
    public static final int DEFAULT_DEVICE_WIDTH = 360;

    public static int dip2px(float f) {
        return (int) ((f * mRes.getDisplayMetrics().density) + 0.5f);
    }

    public static int getDefaultDpi() {
        return mRes.getDisplayMetrics().densityDpi;
    }

    public static int px2dip(float f) {
        return (int) ((f / mRes.getDisplayMetrics().density) + 0.5f);
    }
}