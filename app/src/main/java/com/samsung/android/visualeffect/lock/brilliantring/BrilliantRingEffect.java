package com.samsung.android.visualeffect.lock.brilliantring;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import com.samsung.android.visualeffect.lock.common.LockBGEffect;

/* loaded from: classes.dex */
public class BrilliantRingEffect extends LockBGEffect {
    public BrilliantRingEffect(Context context) {
        super(context);
        this.TAG = "BrilliantRingEffect_View";
        this.veContext = context;
        setEffectRenderer(7);
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
        // from class: com.samsung.android.visualeffect.lock.brilliantring.BrilliantRingEffect.1
// java.lang.Runnable
        postDelayed(() -> {
            Log.i(BrilliantRingEffect.this.TAG, "showAffordanceEffect Renderer.handleTouchEvent(0, " + normalizedX + ", " + normalizedY);
            BrilliantRingEffect.this.mRenderer.handleTouchEvent(0, normalizedX, normalizedY);
        }, startDelay);
    }
}
