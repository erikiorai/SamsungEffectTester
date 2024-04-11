package com.android.keyguard.sec.festivaleffect.unlockeffect.winter;

import android.content.Context;

import com.aj.effect.R;
import com.android.keyguard.sec.festivaleffect.unlockeffect.KeyguardEffectViewFestival;

public class KeyguardEffectViewWinter extends KeyguardEffectViewFestival {
    public KeyguardEffectViewWinter(Context context) {
        super(context, new WinterEffect(context));
        TAG = "KeyguardEffectViewWinter";
        tap = R.raw.winter_tap;
        drag = R.raw.winter_drag;
        unlock = R.raw.winter_unlock;
        lock = R.raw.winter_lock;
    }
}