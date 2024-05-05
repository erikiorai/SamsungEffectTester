package com.samsung.android.visualeffect.liquid;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.MotionEvent;

import com.samsung.android.visualeffect.common.GLTextureView;

/* loaded from: classes.dex */
public class LiquidEffect extends GLTextureView {
    private final String TAG;
    private Context mContext;
    private LiquidRenderer mRenderer;

    public LiquidEffect(Context context, int windowWidth, int windowHeight) {
        super(context);
        this.TAG = "Liquid_LiquidLockView";
        Log.d(TAG, "LiquidLockView Constructor");
        this.mContext = context;
        this.mRenderer = new LiquidRenderer(this.mContext, this, 2, windowWidth, windowHeight);
        setEGLContextClientVersion(2);
        setRenderer(this.mRenderer);
        setRenderMode(1);
    }

    public void setResourcesBitmap1(final Bitmap LLKernel512) {
        Log.d(TAG, "setResourcesBitmap1");
        // from class: com.samsung.android.visualeffect.liquid.LiquidEffect.1
// java.lang.Runnable
        queueEvent(() -> LiquidEffect.this.mRenderer.setResourcesBitmap1(LLKernel512));
    }

    public void setBackground(final Bitmap mBG) {
        Log.d(TAG, "setBackground");
        // from class: com.samsung.android.visualeffect.liquid.LiquidEffect.2
// java.lang.Runnable
        queueEvent(() -> LiquidEffect.this.mRenderer.setBackground(mBG));
    }

    public void changeBackground(final Bitmap mBG) {
        Log.d(TAG, "changeBackground");
        // from class: com.samsung.android.visualeffect.liquid.LiquidEffect.3
// java.lang.Runnable
        queueEvent(() -> {
            LiquidEffect.this.mRenderer.changeBackground(mBG);
            if (LiquidEffect.this.getRenderMode() == 0) {
                LiquidEffect.this.setRenderMode(1);
            }
        });
    }

    public void onConfigurationChanged() {
        this.mRenderer.onConfigurationChanged();
    }

    public void screenTurnedOn() {
        Log.d(TAG, "screenTurnedOn");
        this.mRenderer.screenTurnedOn();
    }

    public void unlockEffect() {
        // from class: com.samsung.android.visualeffect.liquid.LiquidEffect.4
// java.lang.Runnable
        queueEvent(() -> {
            if (LiquidEffect.this.getRenderMode() == 0) {
                LiquidEffect.this.setRenderMode(1);
            }
            LiquidEffect.this.mRenderer.unlockEffect();
        });
    }

    public boolean handleTouchEvent(MotionEvent event) {
        Log.d(TAG, "handleTouchEvent event : " + event.getAction());
        final MotionEvent mEvent = MotionEvent.obtain(event);
        // from class: com.samsung.android.visualeffect.liquid.LiquidEffect.5
// java.lang.Runnable
        queueEvent(() -> {
            if (LiquidEffect.this.getRenderMode() == 0) {
                LiquidEffect.this.setRenderMode(1);
            }
            LiquidEffect.this.mRenderer.onTouchEvent(mEvent);
            mEvent.recycle();
        });
        return true;
    }

    public void cleanUp() {
        Log.d(TAG, "cleanUp");
        // from class: com.samsung.android.visualeffect.liquid.LiquidEffect.6
// java.lang.Runnable
        queueEvent(() -> {
            if (LiquidEffect.this.getRenderMode() == 0) {
                LiquidEffect.this.setRenderMode(1);
            }
            LiquidEffect.this.mRenderer.cleanUp();
        });
    }

    public void show() {
        Log.d(TAG, "show");
        // from class: com.samsung.android.visualeffect.liquid.LiquidEffect.7
// java.lang.Runnable
        queueEvent(() -> {
            if (LiquidEffect.this.getRenderMode() == 0) {
                LiquidEffect.this.setRenderMode(1);
            }
            LiquidEffect.this.mRenderer.show();
        });
    }

    public void reset() {
        Log.d(TAG, "reset");
        // from class: com.samsung.android.visualeffect.liquid.LiquidEffect.8
// java.lang.Runnable
        queueEvent(() -> {
            if (LiquidEffect.this.getRenderMode() == 0) {
                LiquidEffect.this.setRenderMode(1);
            }
            LiquidEffect.this.mRenderer.reset();
        });
    }

    public void showUnlockAffordance() {
        Log.d(TAG, "showUnlockAffordance");
        // from class: com.samsung.android.visualeffect.liquid.LiquidEffect.9
// java.lang.Runnable
        queueEvent(() -> {
            if (LiquidEffect.this.getRenderMode() == 0) {
                LiquidEffect.this.setRenderMode(1);
            }
            LiquidEffect.this.mRenderer.affordanceEffect();
        });
    }

    @Override // android.view.View
    protected void onWindowVisibilityChanged(int visibility) {
    }
}