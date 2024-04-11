package com.android.keyguard.sec.festivaleffect.unlockeffect.summer;

import android.content.Context;

import com.aj.effect.R;
import com.android.keyguard.sec.festivaleffect.unlockeffect.KeyguardEffectViewFestival;

public class KeyguardEffectViewSummer extends KeyguardEffectViewFestival {
    public KeyguardEffectViewSummer(Context context) {
        super(context, new SummerEffect(context));
        TAG = "KeyguardEffectViewSummer";
        tap = R.raw.summer_tap;
        drag = R.raw.summer_drag;
        unlock = R.raw.summer_unlock;
        lock = R.raw.summer_lock;
    }
}