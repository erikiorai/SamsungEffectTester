package com.samsung.android.visualeffect.lock.indigodiffusion;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
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
public class IndigoDiffusionEffect extends GLTextureView implements IEffectView {
    private static final String TAG = "IndigoDiffusionView";
    private IndigoDiffusionRenderer mRenderer;
    private Context veContext;

    public IndigoDiffusionEffect(Context context) {
        super(context);
        this.mRenderer = null;
        Log.d(TAG, "IndigoDiffusion Constructor");
        this.veContext = context;
        this.mRenderer = new IndigoDiffusionRenderer(this.veContext, this);
        if (detectOpenGLES20()) {
            setEGLContextClientVersion(2);
            setEGLConfigChooser(8, 8, 8, 8, 16, 0);
            setRenderer(this.mRenderer);
            setRenderMode(0);
            return;
        }
        Log.e(TAG, "this machine does not support OpenGL ES2.0");
    }

    private boolean detectOpenGLES20() {
        ActivityManager am = (ActivityManager) this.veContext.getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        return info != null && info.reqGlEsVersion >= 131072;
    }

    private void setResourcesBitmap(Bitmap bmp) {
        this.mRenderer.setResourcesBitmap(bmp);
    }

    public void changeBackground(Bitmap mBG) {
        Log.d(TAG, "changeBackground");
        this.mRenderer.changeBackground(mBG);
        if (getRenderMode() == 0) {
            setRenderMode(1);
        }
    }

    public void onConfigurationChanged() {
        this.mRenderer.onConfigurationChanged();
    }

    public boolean handleHoverEvent(MotionEvent event) {
        return true;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.samsung.android.visualeffect.common.GLTextureView, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG, "onDetachedFromWindow");
        this.mRenderer.onDestroy();
    }

    public void startAnimation() {
        Log.d(TAG, "startAnimation");
        setRenderMode(1);
    }

    public void stopAnimation() {
        Log.d(TAG, "stopAnimation");
        setRenderMode(0);
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void init(EffectDataObj data) {
        Log.d(TAG, "setInitValues");
        this.mRenderer.setRippleConfiguration(data.indigoDiffuseData.windowWidth, data.indigoDiffuseData.windowHeight);
        setResourcesBitmap(data.indigoDiffuseData.reflectionBitmap);
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void reInit(EffectDataObj data) {
        this.mRenderer.changeColor(data.indigoDiffuseData.red, data.indigoDiffuseData.green, data.indigoDiffuseData.blue);
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void handleCustomEvent(int cmd, HashMap<?, ?> params) {
        if (cmd == 0) {
            this.mRenderer.changeBackground((Bitmap) params.get("Bitmap"));
        } else if (cmd == 1) {
            this.mRenderer.showUnlockAffordance(((Long) params.get("StartDelay")).longValue(), (Rect) params.get("Rect"));
        }
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void setListener(IEffectListener listener) {
        this.mRenderer.setListener(listener);
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void removeListener() {
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void handleTouchEvent(MotionEvent event, View view) {
        this.mRenderer.handleTouchEvent(event.getActionMasked(), event.getSource(), event.getRawX(), event.getRawY(), event.getPressure());
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void clearScreen() {
        this.mRenderer.clearAllEffect();
    }
}