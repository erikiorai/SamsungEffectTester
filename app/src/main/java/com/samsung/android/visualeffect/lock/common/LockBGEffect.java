package com.samsung.android.visualeffect.lock.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.samsung.android.visualeffect.IEffectListener;
import com.samsung.android.visualeffect.IEffectView;
import com.samsung.android.visualeffect.common.GLTextureView;
import java.util.HashMap;

public class LockBGEffect extends GLTextureView implements IEffectView {

    protected String TAG;
    protected IEffectListener callBackListener;
    public GLTextureViewRenderer mRenderer = null;
    protected Context mContext = null;

    public LockBGEffect(Context context) {
        super(context);
    }

    private void clearEffect() {
        if (this.mRenderer == null) {
            Log.i(this.TAG, "clearEffect renderer is null");
        } else {
            queueEvent(new Runnable() { // from class: com.samsung.android.visualeffect.lock.common.LockBGEffect.1
                @Override // java.lang.Runnable
                public void run() {
                    LockBGEffect.this.mRenderer.clearEffect();
                }
            });
        }
    }

    public GLTextureViewRenderer renderer() {
        return mRenderer;
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void handleCustomEvent(final int cmd, final HashMap<?, ?> param) {
        queueEvent(new Runnable() { // from class: com.samsung.android.visualeffect.lock.common.LockBGEffect.2
            @Override // java.lang.Runnable
            public void run() {
                Log.d("TEST", "run!!!");
                if (cmd == 0) {
                    LockBGEffect.this.setBGBitmap((Bitmap) param.get("Bitmap"));
                } else if (cmd == 1) {
                    LockBGEffect.this.showAffordanceEffect((Long) param.get("StartDelay"), (Rect) param.get("Rect"));
                } else if (cmd == 2) {
                    Log.i("unlock", "lockBGEffect unlock");
                    LockBGEffect.this.showUnlockEffect();
                } else if (cmd == 3) {
                    LockBGEffect.this.setParameters((int[]) param.get("Nums"), (float[]) param.get("Values"));
                }
            }
        });
    }

    protected void showAffordanceEffect(long startDelay, final Rect rect) {
        Log.i(this.TAG, "showAffordanceEffect");
        if (this.mRenderer == null) {
            Log.i(this.TAG, "showUnlockAffordance renderer is null");
        } else {
            postDelayed(new Runnable() { // from class: com.samsung.android.visualeffect.lock.common.b.4

                final int affordanceX;
                final int affordanceY;

                {
                    this.affordanceX = rect.left + ((rect.right - rect.left) / 2);
                    this.affordanceY = rect.top + ((rect.bottom - rect.top) / 2);
                }

                @Override // java.lang.Runnable
                public void run() {
                    LockBGEffect.this.mRenderer.showUnlockAffordance(this.affordanceX, this.affordanceY);
                }
            }, startDelay);
        }
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void handleTouchEvent(final MotionEvent motionEvent, View view) {
        if (this.mRenderer == null) {
            Log.i(this.TAG, "handleTouchEvent renderer is null");
        } else {
            queueEvent(new Runnable() { // from class: com.samsung.android.visualeffect.lock.common.LockBGEffect.3

                final int action = motionEvent.getActionMasked();
                final int normalizedX = (int) motionEvent.getX();
                final int normalizedY = (int) motionEvent.getY();

                @Override // java.lang.Runnable
                public void run() {
                    renderer().handleTouchEvent(action, normalizedX, normalizedY);
                }
            });
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    protected void setParameters(int[] aNums, float[] aValues) {
        if (this.mRenderer == null) {
            Log.i(this.TAG, "clearEffect renderer is null");
        } else {
            this.mRenderer.setParameters(aNums, aValues);
        }
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void clearScreen() {
        clearEffect();
    }

    protected void showUnlockEffect() {
        if (this.mRenderer == null) {
            Log.i(this.TAG, "clearEffect renderer is null");
        } else {
            this.mRenderer.showUnlock();
        }
    }

    @Override // com.samsung.android.visualeffect.common.GLTextureView, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.i(this.TAG, "onDetachedFromWindow");
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

    public void setEffectRenderer(int effect) {
        if (this.mRenderer != null) {
            this.mRenderer = null;
        }
        setEGLContextClientVersion(2);
        GLTextureViewRenderer a = RenderMangaer.getInstance(mContext, effect, this);
        this.mRenderer = a;
        setRenderer(a);
    }

    @Override // com.samsung.android.visualeffect.IEffectListener
    public void setListener(IEffectListener iEffectListener) {
        this.callBackListener = iEffectListener;
        this.mRenderer.setListener(this.callBackListener);
    }
}
