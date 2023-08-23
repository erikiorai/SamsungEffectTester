package com.samsung.android.visualeffect.lock.brilliantcut;

import android.content.Context;
import com.samsung.android.visualeffect.lock.common.LockBGEffect;

/* loaded from: classes.dex */
public class BrilliantCutEffect extends LockBGEffect {
    public BrilliantCutEffect(Context context) {
        super(context);
        this.TAG = "BrilliantCutEffect_View";
        this.veContext = context;
        setEffectRenderer(6);
    }
}