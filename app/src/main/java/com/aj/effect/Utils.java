package com.aj.effect;

import static com.android.keyguard.sec.KeyguardEffectViewController.mRes;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowMetrics;

import java.lang.reflect.Field;
import java.util.Random;

public class Utils {
    public static Integer defaultUnlock = 0;

    public static boolean isTablet() {
        return mRes.getBoolean(R.bool.large);
    }

    public static boolean ConnectedMobileKeypad(Context context) {
        // well no???????//
        return false;
    }

    public static boolean hasPackage(Context context, String packageName) {
        return false; // TODO: well, maybe later. montblanc
    }

    public static boolean isRTL() {
        int layout_dir = mRes.getConfiguration().screenLayout & Configuration.SCREENLAYOUT_LAYOUTDIR_MASK;
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

    public static String getSecUiVersionStg() {
        Field semPlatformIntField;
        int version;
        try {
            semPlatformIntField = Build.VERSION.class.getDeclaredField("SEM_PLATFORM_INT");
            version = semPlatformIntField.getInt(null);
        } catch (Exception e) {
            if (e instanceof NoSuchFieldException) {
                return "Device is not using Samsung ROM";
            } else if (e instanceof IllegalAccessException) {
                return "No access to UI Version field";
            } else {
                return "Exception when getting UI Version field: " + e.getMessage();
            }
        }
        StringBuilder ui = new StringBuilder("Running on ");
        if (version < 90000) {
            if (version < 80000)
                ui.append("TouchWiz");
            else
                ui.append("Samsung Experience");
        } else {
            ui.append("One UI");
            version =- 90000;
        }
        return ui.append(" ").append((version / 10000)).append(".").append((version % 10000) / 100).toString();
    }

    public static long getSecUiVersion() {
        Field semPlatformIntField;
        int version;
        try {
            semPlatformIntField = Build.VERSION.class.getDeclaredField("SEM_PLATFORM_INT");
            version = semPlatformIntField.getInt(null);
        } catch (Exception e) {
            Log.d("EffectsUtils", "getSecUiVersion: " + e.getMessage());
            return 0;
        }
        if (version > 90000) {
            version =- 90000;
        }
        return (version / 10000) + ((version % 10000) / 100);
    }

    /*public static long handleUnlockAndGetDelay (boolean mIsUnlockStarted, KeyguardEffectViewBase mUnlockView) {
        mIsUnlockStarted = true;
        if (mUnlockView != null) {
            mUnlockView.handleUnlock(view, event);
            return mUnlockView.getUnlockDelay();
        }
    }*/
}

