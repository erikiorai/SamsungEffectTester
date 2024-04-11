package com.android.keyguard.sec.festivaleffect.unlockeffect.autumn;

import android.content.Context;

import com.aj.effect.R;
import com.android.keyguard.sec.festivaleffect.unlockeffect.KeyguardEffectViewFestival;

public class KeyguardEffectViewAutumn extends KeyguardEffectViewFestival {
    public KeyguardEffectViewAutumn(Context context) {
        super(context, new AutumnEffect(context));
        TAG = "KeyguardEffectViewAutumn";
        tap = R.raw.autumn_tap;
        drag = R.raw.autumn_drag;
        unlock = R.raw.autumn_unlock;
        lock = R.raw.autumn_lock;
    }
}