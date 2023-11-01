package com.android.systemui.assist.ui;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import com.android.systemui.R$dimen;

/* loaded from: mainsysui33.jar:com/android/systemui/assist/ui/DisplayUtils.class */
public class DisplayUtils {
    public static int convertDpToPx(float f, Context context) {
        Display display = context.getDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getRealMetrics(displayMetrics);
        return (int) Math.ceil(f * displayMetrics.density);
    }

    public static int getCornerRadiusBottom(Context context) {
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R$dimen.config_rounded_mask_size_bottom);
        int i = dimensionPixelSize;
        if (dimensionPixelSize == 0) {
            i = getCornerRadiusDefault(context);
        }
        return i;
    }

    public static int getCornerRadiusDefault(Context context) {
        return context.getResources().getDimensionPixelSize(R$dimen.config_rounded_mask_size);
    }

    public static int getCornerRadiusTop(Context context) {
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R$dimen.config_rounded_mask_size_top);
        int i = dimensionPixelSize;
        if (dimensionPixelSize == 0) {
            i = getCornerRadiusDefault(context);
        }
        return i;
    }

    public static int getHeight(Context context) {
        Display display = context.getDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getRealMetrics(displayMetrics);
        int rotation = display.getRotation();
        return (rotation == 0 || rotation == 2) ? displayMetrics.heightPixels : displayMetrics.widthPixels;
    }

    public static int getWidth(Context context) {
        Display display = context.getDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getRealMetrics(displayMetrics);
        int rotation = display.getRotation();
        return (rotation == 0 || rotation == 2) ? displayMetrics.widthPixels : displayMetrics.heightPixels;
    }
}