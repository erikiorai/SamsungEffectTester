package com.android.keyguard.sec;

import android.content.Context;

import com.android.systemui.opensesame.lockscreen.effect.particle.EffectViewRectangleTraveller;

public class KeyguardEffectViewRectangleTraveller extends EffectViewRectangleTraveller {
    public KeyguardEffectViewRectangleTraveller(Context context) {
        super(context);
    }

    public static boolean isBackgroundEffect() {
        return true;
    }

    public static String getCounterEffectName() {
        return null;
    }
}
