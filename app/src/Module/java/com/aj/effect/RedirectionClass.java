package com.aj.effect;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;

public class RedirectionClass {
    public static class KeyguardEffectViewUtil {
        public static BitmapDrawable getCurrentWallpaper(Context mContext) {
            return new BitmapDrawable(mContext.getResources(), Hook.wallpaper); // todo inside init()
        }
    }
}
