package com.android.keyguard.sec.festivaleffect.unlockeffect.spring;

import android.content.Context;

import com.aj.effect.R;
import com.android.keyguard.sec.festivaleffect.unlockeffect.KeyguardEffectViewFestival;

public class KeyguardEffectViewSpring extends KeyguardEffectViewFestival {
    public KeyguardEffectViewSpring(Context context) {
        super(context, new SpringEffect(context));
        TAG = "KeyguardEffectViewSpring";
        tap = R.raw.spring_tap;
        drag = R.raw.spring_drag;
        unlock = R.raw.spring_unlock;
        lock = R.raw.spring_lock;
    }
}