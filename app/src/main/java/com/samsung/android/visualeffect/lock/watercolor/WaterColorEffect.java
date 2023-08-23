package com.samsung.android.visualeffect.lock.watercolor;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import com.samsung.android.visualeffect.lock.common.LockBGEffect;

/* loaded from: classes.dex */
public class WaterColorEffect extends LockBGEffect {
    public WaterColorEffect(Context context) {
        super(context);
        this.TAG = "WaterColorEffect_View";
        this.veContext = context;
        setEffectRenderer(5);
    }

    @Override // com.samsung.android.visualeffect.lock.common.LockBGEffect
    public void showAffordanceEffect(long startDelay, Rect rect) {
        Log.i(this.TAG, "showAffordanceEffect");
        if (this.mRenderer == null) {
            Log.i(this.TAG, "showUnlockAffordance renderer is null");
            return;
        }
        final int normalizedX = rect.centerX();
        final int normalizedY = rect.centerY();
        postDelayed(new Runnable() { // from class: com.samsung.android.visualeffect.lock.watercolor.WaterColorEffect.1
            @Override // java.lang.Runnable
            public void run() {
                Log.i(WaterColorEffect.this.TAG, "showAffordanceEffect Renderer.handleTouchEvent(0, " + normalizedX + ", " + normalizedY);
                WaterColorEffect.this.mRenderer.handleTouchEvent(0, normalizedX, normalizedY);
            }
        }, startDelay);
    }
}