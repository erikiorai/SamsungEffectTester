package com.samsung.android.visualeffect.lock.brilliantring;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import com.samsung.android.visualeffect.lock.common.LockBGEffect;

public class BrilliantRingEffect extends LockBGEffect {
    public BrilliantRingEffect(Context context) {
        super(context);
        this.TAG = "BrilliantRingEffect_View";
        Log.d(TAG, "init");
        this.mContext = context;
        setEffectRenderer(7);

    }

    public void showAffordanceEffect(long startDelay, Rect rect) {
        Log.i(this.TAG, "showAffordanceEffect 2014-12-02");
        if (this.mRenderer == null) {
            Log.i(this.TAG, "showUnlockAffordance renderer is null");
            return;
        }
        final int normalizedX = rect.centerX();
        final int normalizedY = rect.centerY();
        postDelayed(new Runnable() { // from class: com.samsung.android.visualeffect.lock.brilliantring.BrilliantRingEffect.1
            @Override // java.lang.Runnable
            public void run() {
                Log.i(BrilliantRingEffect.this.TAG, "showAffordanceEffect Renderer.handleTouchEvent(0, " + normalizedX + ", " + normalizedY);
                BrilliantRingEffect.this.mRenderer.handleTouchEvent(0, normalizedX, normalizedY);
            }
        }, startDelay);
    }
}
