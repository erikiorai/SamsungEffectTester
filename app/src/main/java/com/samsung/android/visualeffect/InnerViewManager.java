package com.samsung.android.visualeffect;

import android.content.Context;
import com.samsung.android.visualeffect.lock.brilliantring.BrilliantRingEffect;

public class InnerViewManager {
    public static IEffectView getInstance(Context context, int argv) {
        return new BrilliantRingEffect(context);
    }
}
