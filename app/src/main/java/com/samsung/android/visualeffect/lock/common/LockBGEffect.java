package com.samsung.android.visualeffect.lock.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.samsung.android.visualeffect.EffectDataObj;
import com.samsung.android.visualeffect.IEffectListener;
import com.samsung.android.visualeffect.IEffectView;
import com.samsung.android.visualeffect.common.GLTextureView;
import java.util.HashMap;

/* loaded from: classes.dex */
public class LockBGEffect extends GLTextureView implements IEffectView {
    protected String TAG;
    protected IEffectListener callBackListener;
    protected GLTextureViewRenderer mRenderer;
    protected Context veContext;

    public LockBGEffect(Context context) {
        super(context);
        this.mRenderer = null;
        this.veContext = null;
    }

    public void setEffectRenderer(int effect) {
        if (this.mRenderer != null) {
            this.mRenderer = null;
        }
        setEGLContextClientVersion(2);
        this.mRenderer = RenderManager.getInstance(veContext, effect, this);
        setRenderer(this.mRenderer);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.samsung.android.visualeffect.common.GLTextureView, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.i(this.TAG, "onDetachedFromWindow");
    }

    private void clearEffect() {
        if (this.mRenderer == null) {
            Log.i(this.TAG, "clearEffect renderer is null");
        } else {
            // from class: com.samsung.android.visualeffect.lock.common.LockBGEffect.1
// java.lang.Runnable
            queueEvent(() -> LockBGEffect.this.mRenderer.clearEffect());
        }
    }

    protected void setBGBitmap(Bitmap bitmap) {
        if (this.mRenderer == null) {
            Log.i(this.TAG, "setBGBitmap renderer is null");
            return;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        this.mRenderer.setBackgroundBitmap(pixels, width, height);
        requestRender();
    }

    protected void showAffordanceEffect(long startDelay, Rect rect) {
        Log.i(this.TAG, "showAffordanceEffect");
        if (this.mRenderer == null) {
            Log.i(this.TAG, "showUnlockAffordance renderer is null");
            return;
        }
        final int affordanceX = rect.left + ((rect.right - rect.left) / 2);
        final int affordanceY = rect.top + ((rect.bottom - rect.top) / 2);
        // from class: com.samsung.android.visualeffect.lock.common.LockBGEffect.2
// java.lang.Runnable
        postDelayed(() -> LockBGEffect.this.mRenderer.showUnlockAffordance(affordanceX, affordanceY), startDelay);
    }

    protected void showUnlockEffect() {
        if (this.mRenderer == null) {
            Log.i(this.TAG, "clearEffect renderer is null");
        } else {
            this.mRenderer.showUnlock();
        }
    }

    protected void setParameters(int[] aNums, float[] aValues) {
        if (this.mRenderer == null) {
            Log.i(this.TAG, "clearEffect renderer is null");
        } else {
            this.mRenderer.setParameters(aNums, aValues);
        }
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void init(EffectDataObj data) {
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void reInit(EffectDataObj data) {
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void handleCustomEvent(final int cmd, final HashMap<?, ?> params) {
        Log.d(this.TAG, "handleCustomEvent() cmd = " + cmd);
        if (cmd != 1) {
            // from class: com.samsung.android.visualeffect.lock.common.LockBGEffect.5
// java.lang.Runnable
            queueEvent(() -> {
                Log.d(LockBGEffect.this.TAG, "run!!!");
                if (cmd == 0) {
                    LockBGEffect.this.setBGBitmap((Bitmap) params.get("Bitmap"));
                } else if (cmd == 2) {
                    Log.i("unlock", "lockBGEffect unlock");
                    LockBGEffect.this.showUnlockEffect();
                } else if (cmd == 99) {
                    LockBGEffect.this.setParameters((int[]) params.get("Nums"), (float[]) params.get("Values"));
                }
            });
        } else if (this.mRenderer != null && this.mRenderer.isReadyRendering()) {
            // from class: com.samsung.android.visualeffect.lock.common.LockBGEffect.3
// java.lang.Runnable
            queueEvent(() -> {
                Log.d(LockBGEffect.this.TAG, "run!!!");
                LockBGEffect.this.showAffordanceEffect((Long) params.get("StartDelay"), (Rect) params.get("Rect"));
            });
        } else {
            Log.d(this.TAG, "mRenderer isn't ReadyRendering, so call handleCustomEvent after 100 ms");
            // from class: com.samsung.android.visualeffect.lock.common.LockBGEffect.4
// java.lang.Runnable
            postDelayed(() -> LockBGEffect.this.handleCustomEvent(cmd, params), 100L);
        }
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void setListener(IEffectListener listener) {
        this.callBackListener = listener;
        this.mRenderer.setListener(this.callBackListener);
    }

    @Override
    public boolean handleHoverEvent(MotionEvent event) {
        handleTouchEvent(event, null);
        return true;
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void removeListener() {
        this.callBackListener = null;
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void handleTouchEvent(MotionEvent event, View view) {
        if (this.mRenderer == null) {
            Log.i(this.TAG, "handleTouchEvent renderer is null");
            return;
        }
        final int action = event.getActionMasked();
        final int normalizedX = (int) event.getX();
        final int normalizedY = (int) event.getY();
        // from class: com.samsung.android.visualeffect.lock.common.LockBGEffect.6
// java.lang.Runnable
        queueEvent(() -> LockBGEffect.this.mRenderer.handleTouchEvent(action, normalizedX, normalizedY));
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void clearScreen() {
        clearEffect();
    }

}