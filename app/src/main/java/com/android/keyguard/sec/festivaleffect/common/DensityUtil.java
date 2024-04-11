package com.android.keyguard.sec.festivaleffect.common;

import android.content.Context;

public class DensityUtil {
    public static final int BASE_DENSITY_DPI = 480;
    public static final int DEFAULT_DEVICE_HEIGHT = 640;
    public static final int DEFAULT_DEVICE_WIDTH = 360;

    public static int dip2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int getDefaultDpi(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    public static int px2dip(Context context, float f) {
        return (int) ((f / context.getResources().getDisplayMetrics().density) + 0.5f);
    }
}