package com.sec.android.visualeffect.watercolor;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

/* loaded from: classes.dex */
public class WaterColorView extends GLSurfaceView {
    public static final int PORTRAIT_MODE = 0;
    public static final int QUALITY_LEVEL_0 = 0;
    public static final int QUALITY_LEVEL_1 = 1;
    public static final int QUALITY_LEVEL_2 = 2;
    public static final int QUALITY_LEVEL_3 = 3;
    public static final int TABLET_MODE = 1;
    private final boolean DBG;
    private final String TAG;
    private Context mContext;
    JniWaterColorRenderer mJniWaterColor;
    private WaterColorRenderer mRenderer;

    public WaterColorView(Context context, int phoneMode, int qualityLevel, int windowWidth, int windowHeight) {
        super(context);
        this.DBG = true;
        this.TAG = "WaterColor_WaterColorView";
        Log.d("WaterColor_WaterColorView", "WaterColorView Constructor");
        this.mContext = context;
        this.mJniWaterColor = new JniWaterColorRenderer();
        this.mRenderer = new WaterColorRenderer(this.mContext, this, phoneMode, this.mJniWaterColor, qualityLevel, windowWidth, windowHeight);
        if (detectOpenGLES20()) {
            setEGLContextClientVersion(2);
            setEGLConfigChooser(8, 8, 8, 8, 16, 0);
            setRenderer(this.mRenderer);
            getHolder().setFormat(3);
            return;
        }
        Log.e("WaterEffect", "this machine does not support OpenGL ES2.0");
    }

    private boolean detectOpenGLES20() {
        ActivityManager am = (ActivityManager) this.mContext.getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        return info != null && info.reqGlEsVersion >= 131072;
    }

    public void setResourcesBitmap(Bitmap mask1, Bitmap mask2, Bitmap mask3, Bitmap tube, Bitmap noise) {
        this.mRenderer.setResourcesBitmap(mask1, mask2, mask3, tube, noise);
    }

    public void setBackground(Bitmap mBG) {
        Log.d("WaterColor_WaterColorView", "setBackground");
        this.mRenderer.changeBackground(mBG);
    }

    public void changeBackground(Bitmap mBG) {
        Log.d("WaterColor_WaterColorView", "changeBackground");
        this.mRenderer.changeBackground(mBG);
        if (getRenderMode() == 0) {
            setRenderMode(1);
        }
    }

    public void onConfigurationChanged() {
        this.mRenderer.onConfigurationChanged();
    }

    public void screenTurnedOn() {
        Log.d("WaterColor_WaterColorView", "screenTurnedOn");
        this.mRenderer.screenTurnedOn();
    }

    public void unlockEffect() {
        this.mRenderer.unlockEffect();
    }

    public boolean handleTouchEvent(MotionEvent event) {
        Log.d("WaterColor_WaterColorView", "handleTouchEvent event : " + event.getAction());
        if (getRenderMode() == 0) {
            setRenderMode(1);
        }
        return this.mRenderer.onTouchEvent(event);
    }

    public boolean handleHoverEvent(MotionEvent event) {
        Log.d("WaterColor_WaterColorView", "handleHoverEvent event : " + event.getAction());
        if (getRenderMode() == 0) {
            setRenderMode(1);
        }
        this.mRenderer.onTouchEvent(event);
        return false;
    }

    public boolean handleTouchEventForPatternLock(MotionEvent event) {
        Log.d("WaterColor_WaterColorView", "handleTouchEventForPatternLock event : " + event.getAction());
        if (getRenderMode() == 0) {
            setRenderMode(1);
        }
        this.mRenderer.onTouchEventForPatternLock(event);
        return true;
    }

    public void cleanUp() {
        Log.d("WaterColor_WaterColorView", "cleanUp");
        this.mRenderer.cleanUp();
    }

    public void show() {
        Log.d("WaterColor_WaterColorView", "show");
        this.mRenderer.show();
    }

    public void reset() {
        Log.d("WaterColor_WaterColorView", "reset");
        this.mRenderer.reset();
        Log.d("WaterColor_WaterColorView", "requestRender()");
    }

    public void showUnlockAffordance(long startDelay, Rect rect) {
        this.mRenderer.showUnlockAffordance(startDelay, rect);
    }

    @Override // android.view.SurfaceView, android.view.View
    protected void onWindowVisibilityChanged(int visibility) {
        if (visibility == 0) {
            super.onWindowVisibilityChanged(visibility);
        }
    }

    @Override // android.opengl.GLSurfaceView, android.view.SurfaceView, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d("WaterColor_WaterColorView", "onDetachedFromWindow");
        this.mRenderer.destroyed();
    }

    public void startAnimation() {
        Log.d("WaterColor_WaterColorView", "startAnimation");
        setRenderMode(1);
    }

    public void stopAnimation() {
        Log.d("WaterColor_WaterColorView", "stopAnimation");
        setRenderMode(0);
    }
}