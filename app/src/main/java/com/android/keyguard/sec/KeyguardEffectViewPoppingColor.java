package com.android.keyguard.sec;

import android.content.Context;

import com.android.systemui.opensesame.lockscreen.effect.particle.EffectViewPoppingColor;

public class KeyguardEffectViewPoppingColor extends EffectViewPoppingColor {

    public KeyguardEffectViewPoppingColor(Context context) {
        super(context);
    }

    public static boolean isBackgroundEffect() {
        return true;
    }

    public static String getCounterEffectName() {
        return null;
    }

}
