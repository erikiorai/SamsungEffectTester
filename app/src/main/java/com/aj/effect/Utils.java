package com.aj.effect;

import android.content.Context;
import android.content.res.Configuration;

public class Utils {
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

    /*public static long handleUnlockAndGetDelay (boolean mIsUnlockStarted, KeyguardEffectViewBase mUnlockView) {
        mIsUnlockStarted = true;
        if (mUnlockView != null) {
            mUnlockView.handleUnlock(view, event);
            return mUnlockView.getUnlockDelay();
        }
    }*/
}

