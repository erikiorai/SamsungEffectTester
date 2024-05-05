package com.aj.effect;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowMetrics;

import java.util.Random;

public class Utils {
    public static int defaultUnlock = 0;

    public static boolean isTablet(Context context) {
        return context.getResources().getBoolean(R.bool.large);
    }

    public static boolean ConnectedMobileKeypad(Context context) {
        // well no???????//
        return false;
    }

    public static boolean hasPackage(Context context, String packageName) {
        return false; // TODO: well, maybe later. montblanc
    }

    public static boolean isRTL(Context context) {
        int layout_dir = context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_LAYOUTDIR_MASK;
        return layout_dir == Configuration.SCREENLAYOUT_LAYOUTDIR_RTL;
    }

    public static boolean usePCColorPalette = true;
    public static boolean customActions = true;
    public static boolean particleRatioLock = false;
    
    public static int getPoppingHue(int color, Random random) {
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        if (usePCColorPalette) {
            float[] hsvOrigin = new float[3];
            Color.RGBToHSV(r,g,b, hsvOrigin);
            hsvOrigin[1] = (float) (hsvOrigin[1] * (1.0d - (0.7d * Math.random())));
            hsvOrigin[2] = (float) (hsvOrigin[2] + ((1.0f - hsvOrigin[2]) * Math.random()));
            return Color.HSVToColor(hsvOrigin);
        } else {
            int colorAdjust = random.nextInt(40) - 20;
            int r2 = r + colorAdjust;
            if (r2 < 0) {
                r2 = 0;
            }
            if (r2 > 255) {
                r2 = 255;
            }
            int g2 = g + colorAdjust;
            if (g2 < 0) {
                g2 = 0;
            }
            if (g2 > 255) {
                g2 = 255;
            }
            int b2 = b + colorAdjust;
            if (b2 < 0) {
                b2 = 0;
            }
            if (b2 > 255) {
                b2 = 255;
            }
            return Color.argb(255, r2, g2, b2);
        }
    }

    public static int getViewWidthModern(View view) {
        Rect outRect = new Rect(0, 0, 0, 0);
        boolean isValidRect = view.getGlobalVisibleRect(outRect);
        if (isValidRect) {
            return outRect.width();
        } else {
            return 0;
        }
    }

    public static Rect getViewRect(DisplayMetrics dm, WindowManager mWindowManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowMetrics metrics = mWindowManager.getCurrentWindowMetrics();
            return metrics.getBounds();
        } else {
            Rect rect = new Rect();
            mWindowManager.getDefaultDisplay().getRectSize(rect);
            if (rect.width() == 0 || rect.height() == 0) {
                mWindowManager.getDefaultDisplay().getRealMetrics(dm);
                rect.right = dm.widthPixels;
                rect.bottom = dm.heightPixels;
                rect.top = 0;
                rect.left = 0;
            }
            return rect;
        }
    }

    /*public static long handleUnlockAndGetDelay (boolean mIsUnlockStarted, KeyguardEffectViewBase mUnlockView) {
        mIsUnlockStarted = true;
        if (mUnlockView != null) {
            mUnlockView.handleUnlock(view, event);
            return mUnlockView.getUnlockDelay();
        }
    }*/
}

