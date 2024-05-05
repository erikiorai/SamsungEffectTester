package com.samsung.android.visualeffect.lock.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.SensorEvent;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.WindowManager;

import com.aj.effect.Utils;
import com.samsung.android.visualeffect.IEffectListener;
import com.samsung.android.visualeffect.utils.BitmapTools;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/* loaded from: classes.dex */
public class SPhysicsRenderer_GL implements GLSurfaceView.Renderer, ISPhysicsRenderer {
    protected String TAG;
    protected GLSurfaceView mGlView;
    protected IEffectListener mIEffectListener;
    protected Context veContext;
    protected ISPhysicsJniRenderer mIJniRenderer = null;
    protected Bitmap mBitmapRes1 = null;
    protected Bitmap mBitmapRes2 = null;
    protected String mBitmapResStr1 = null;
    protected String mBitmapResStr2 = null;
    protected Bitmap mPortraitBG = null;
    protected Bitmap mLandscapeBG = null;
    protected Queue<Boolean> mBgChangeCheckQueue = null;
    protected int windowWidth = 0;
    protected int windowHeight = 0;
    protected int renderWidth = 0;
    protected int renderHeight = 0;
    protected int[] pointer_xpos = new int[10];
    protected int[] pointer_ypos = new int[10];
    protected boolean isTouched = false;
    protected int EVENT_CLEAR = 90;
    protected int EVENT_UNLOCK = 91;
    protected int EVENT_AFFORDANCE = 92;
    protected int EVENT_MOBILEKEY_ON = 93;
    protected int EVENT_MOBILEKEY_OFF = 94;
    protected int EVENT_INIT_RESOLUTION = 95;
    protected int EVENT_RESET_BGSCALE = 96;
    protected int EVENT_CHANGE_BGIMAGE = 97;
    protected boolean isFirstSurfaceChanged = true;
    protected boolean isSurfaceCreated = true;
    protected int mCountOfDirtyMode = -1;
    protected int mCountOfScaleDown = 0;
    protected boolean isOrientationChanged = false;
    protected int isOrientationChangCount = 0;
    protected boolean isSurfaceChanged = false;
    protected boolean isWrongSurfaceChanged = false;
    private boolean isMobileKeyboard = false;
    protected int QUALITY_LEVEL_0 = 0;
    protected int QUALITY_LEVEL_1 = 1;
    protected int QUALITY_LEVEL_2 = 2;
    protected int QUALITY_LEVEL_3 = 3;
    protected int PHONE_PROJECT = 0;
    protected int TABLET_PROJECT = 1;
    protected int mQualityLevel = this.QUALITY_LEVEL_2;
    protected int mDrawCount = 0;
    private boolean isFpsChecked = true;
    private int fpsCount = 0;
    private long startTime = 0;
    protected HashMap<Object, Object> map = new HashMap<>();

    /* JADX INFO: Access modifiers changed from: protected */
    public void initRender() {
        this.isFirstSurfaceChanged = true;
        this.mBgChangeCheckQueue = new LinkedList<>();
    }

    @Override // com.samsung.android.visualeffect.lock.common.ISPhysicsRenderer
    public void initConfig(int pWidth, int pHeight, IEffectListener callback) {
        this.windowWidth = pWidth;
        this.windowHeight = pHeight;
        Log.d(this.TAG, "windowWidth = " + this.windowWidth + ", windowHeight = " + this.windowHeight);
        this.mIEffectListener = callback;
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.d(this.TAG, "onSurfaceCreated");
        this.isSurfaceCreated = true;
        this.isWrongSurfaceChanged = false;
        this.mDrawCount = 0;
        this.mIJniRenderer.Init_PhysicsEngineJNI();
        preSetTexture();
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.d(this.TAG, "onSurfaceChanged, width = " + width + ", height = " + height);
        int miniMumLenth = Math.max(this.windowWidth, this.windowHeight) / 5;
        if (width < miniMumLenth || height < miniMumLenth) {
            Log.i(this.TAG, "onSurfaceChanged problem width" + width + " " + height + " disp " + this.windowWidth + " " + this.windowHeight);
            this.isWrongSurfaceChanged = true;
            return;
        }
        this.isSurfaceChanged = true;
        this.isWrongSurfaceChanged = false;
        this.mGlView.setRenderMode(1);
        this.renderWidth = width;
        this.renderHeight = height;
        Log.d(this.TAG, "Mobile Keyboard FEATURE is set");
        if (this.windowWidth == width && this.windowHeight > height) {
            Log.d(this.TAG, "Mobile Keyboard is attached");
            this.isMobileKeyboard = true;
            if (this.mIEffectListener != null) {
                this.mIEffectListener.onReceive(1, this.map);
            }
        } else if (this.windowWidth == width && this.windowHeight == height) {
            Log.d(this.TAG, "Mobile Keyboard is deattached");
            if (this.isMobileKeyboard && this.mIEffectListener != null) {
                this.mIEffectListener.onReceive(1, this.map);
            }
            this.isMobileKeyboard = false;
        }
        if (this.isSurfaceCreated) {
            Log.d(this.TAG, "First onSurfaceChanged, Call the Init_PhysicsEngine");
            checkBackground();
            InitPhysics(getProjectKind(), width, height);
            this.isSurfaceCreated = false;
        } else {
            Log.d(this.TAG, "2nd, 3rd..... onSurfaceChanged");
            clearEffect();
            checkBackground();
            this.mIJniRenderer.onSurfaceChangedEvent(width, height);
            this.mCountOfScaleDown = 100;
        }
        Log.d(this.TAG, "native_onKeyEvent EVENT_RESET_BGSCALE");
        this.mIJniRenderer.onKeyEvent(this.EVENT_RESET_BGSCALE);
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onDrawFrame(GL10 gl) {
        if (this.isWrongSurfaceChanged) {
            Log.d(this.TAG, "onSurfaceChanged is wrong, so we return and goto DIRTY MODE");
            this.mGlView.setRenderMode(0);
            return;
        }
        if (this.mDrawCount == 1 && this.mIEffectListener != null) {
            Log.d(this.TAG, "mDrawCount == 1, call mIEffectListener.onReceive()");
            this.mIEffectListener.onReceive(0, this.map);
        }
        checkOrientation();
        if (this.mDrawCount >= 1) {
            checkBackground();
        }
        drawEffect();
        checkDirtyMode();
        if (this.isFpsChecked) {
            this.fpsCount++;
            long endTime = System.currentTimeMillis();
            if (endTime - this.startTime >= 1000) {
                Log.d(this.TAG, "FPS = " + this.fpsCount);
                this.startTime = endTime;
                this.fpsCount = 0;
            }
        }
    }

    protected void checkOrientation() {
        if (this.isOrientationChanged) {
            if (this.isSurfaceChanged || this.isOrientationChangCount > 20) {
                Log.d(this.TAG, "= onConfigurationChanged = onDrawFrame isSurfaceChanged == true && isOrientationChanged == true, isOrientationChangCount = " + this.isOrientationChangCount);
                this.isOrientationChanged = false;
                return;
            }
            this.isOrientationChangCount++;
        }
    }

    protected void checkDirtyMode() {
        if (this.mIJniRenderer.isEmpty() == 1 && !this.isTouched && !this.isOrientationChanged && this.mBgChangeCheckQueue.isEmpty() && this.mCountOfScaleDown <= 0) {
            Log.d(this.TAG, "mJniFluidAmoeba is Empty");
            this.mCountOfDirtyMode++;
            if (this.mCountOfDirtyMode >= 2) {
                Log.d(this.TAG, "checkDirtyMode() Drity Mode");
                this.mGlView.setRenderMode(0);
                this.mCountOfDirtyMode = 0;
            }
        }
    }

    protected void checkBackground() {
        if (!this.mBgChangeCheckQueue.isEmpty()) {
            Log.d(this.TAG, "Change opengl BG Texture, size = " + this.mBgChangeCheckQueue.size());
            preSetPortraitBGTexture();
            preSetLandscapeBGTexture();
            while (!this.mBgChangeCheckQueue.isEmpty()) {
                this.mBgChangeCheckQueue.remove();
            }
        }
    }

    protected void drawEffect() {
        this.mIJniRenderer.Draw_PhysicsEngine();
        this.mDrawCount++;
        this.mCountOfScaleDown--;
        this.isSurfaceChanged = false;
    }

    protected void onConfigurationChanged() {
        Log.d(this.TAG, "= onConfigurationChanged = Renderer onConfigurationChanged");
        this.isOrientationChanged = true;
        this.isOrientationChangCount = 0;
        this.mGlView.setRenderMode(1);
    }

    @Override // com.samsung.android.visualeffect.lock.common.ISPhysicsRenderer
    public void setResourcesBitmap1(Bitmap resBmp) {
        Log.d(this.TAG, "setResourcesBitmap1()");
        this.mBitmapRes1 = resBmp;
    }

    @Override // com.samsung.android.visualeffect.lock.common.ISPhysicsRenderer
    public void setResourcesBitmap2(Bitmap resBmp) {
        Log.d(this.TAG, "setResourcesBitmap2()");
        this.mBitmapRes2 = resBmp;
    }

    // TODO: make bitmap funcs
    protected void setBackground(Bitmap bg, int mode) {
        if (mode == 0) {
            this.mPortraitBG = BitmapTools.getCenterCropBitmap(bg, Math.min(this.windowWidth, this.windowHeight), Math.max(this.windowWidth, this.windowHeight));
            this.mBgChangeCheckQueue.offer(true);
            this.mLandscapeBG = BitmapTools.getCenterCropBitmap(bg, Math.max(this.windowWidth, this.windowHeight), Math.min(this.windowWidth, this.windowHeight));
            this.mBgChangeCheckQueue.offer(true);
            return;
        }
        Log.d(this.TAG, "renderWidth = " + this.renderWidth + ", renderHeight = " + this.renderHeight);
        this.mPortraitBG = BitmapTools.getCenterCropBitmap(bg, this.renderWidth, this.renderHeight);
        this.mBgChangeCheckQueue.offer(true);
    }

    @Override // com.samsung.android.visualeffect.lock.common.ISPhysicsRenderer
    public boolean onTouchEvent(MotionEvent event) {
        if (this.mDrawCount <= 1) {
            Log.d(this.TAG, "Return onTouchEvent, Because of mDrawCount = " + this.mDrawCount);
        } else {
            int action = event.getActionMasked();
            this.pointer_xpos[0] = (int) event.getX();
            this.pointer_ypos[0] = (int) event.getY();
            this.mGlView.setRenderMode(1);
            IsExistBubbles();
            switch (action) {
                case 0:
                    Log.d(this.TAG, "ACTION_DOWN, renderWidth = " + this.renderWidth + ", renderHeight" + this.renderHeight);
                    this.mIJniRenderer.onTouchEvent(0, 1, 0, this.pointer_xpos, this.pointer_ypos);
                    this.isOrientationChanged = false;
                    this.isTouched = true;
                    break;
                case 1:
                case 3:
                    Log.d(this.TAG, "ACTION_UP");
                    this.mIJniRenderer.onTouchEvent(0, 1, 1, this.pointer_xpos, this.pointer_ypos);
                    this.isTouched = false;
                    break;
                case 2:
                    this.mIJniRenderer.onTouchEvent(0, 1, 2, this.pointer_xpos, this.pointer_ypos);
                    this.isTouched = true;
                    break;
            }
        }
        return true;
    }

    protected void preSetPortraitBGTexture() {
        if (this.mPortraitBG != null) {
            Log.d(this.TAG, "SetTexture(PortraitBG)");
            this.mIJniRenderer.SetTexture("PortraitBG", this.mPortraitBG);
            recyclePortraitBG();
        }
    }

    protected void preSetLandscapeBGTexture() {
        if (this.mLandscapeBG != null) {
            Log.d(this.TAG, "SetTexture(LandscapeBG)");
            this.mIJniRenderer.SetTexture("LandscapeBG", this.mLandscapeBG);
            recycleLandscapeBG();
        }
    }

    protected void preSetTexture() {
    }

    @Override // com.samsung.android.visualeffect.lock.common.ISPhysicsRenderer
    public void changeBackground(Bitmap bg, int mode) {
        Log.d(this.TAG, "changeBackground mode = " + mode);
        if (bg == null) {
            Log.d(this.TAG, "bg is null");
        } else {
            setBackground(bg, mode);
        }
    }

    protected void recyclePortraitBG() {
        if (this.mPortraitBG != null) {
            this.mPortraitBG = null;
            Log.d(this.TAG, "mPortraitBG => null");
        }
    }

    protected void recycleLandscapeBG() {
        if (this.mLandscapeBG != null) {
            this.mLandscapeBG = null;
            Log.d(this.TAG, "mLandscapeBG => null");
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void recycleResource() {
    }

    @Override // com.samsung.android.visualeffect.lock.common.ISPhysicsRenderer
    public void unlockEffect() {
        if (this.mDrawCount > 1) {
            Log.d(this.TAG, "native_onKeyEvent EVENT_UNLOCK");
            this.mIJniRenderer.onKeyEvent(this.EVENT_UNLOCK);
            this.mGlView.setRenderMode(1);
        }
    }

    @Override // com.samsung.android.visualeffect.lock.common.ISPhysicsRenderer
    public void affordanceEffect(float x, float y) {
        Log.d(this.TAG, "affordanceEffect(EVENT_AFFORDANCE), mDrawCount = " + this.mDrawCount + ", renderWidth = " + this.renderWidth + ", renderHeight =" + this.renderHeight);
        this.mIJniRenderer.onCustomEvent(this.EVENT_AFFORDANCE, x, y, 0.0f);
        this.mGlView.setRenderMode(1);
    }

    @Override // com.samsung.android.visualeffect.lock.common.ISPhysicsRenderer
    public void clearEffect() {
        if (this.mDrawCount > 1) {
            Log.d(this.TAG, "native_onKeyEvent EVENT_CLEAR");
            this.mIJniRenderer.onKeyEvent(this.EVENT_CLEAR);
            if (this.mGlView.getRenderMode() == 1) {
                this.mGlView.requestRender();
            }
            this.isTouched = false;
            this.isOrientationChanged = false;
        }
    }

    @Override // com.samsung.android.visualeffect.lock.common.ISPhysicsRenderer
    public void show() {
        Log.d(this.TAG, "show");
    }

    @Override // com.samsung.android.visualeffect.lock.common.ISPhysicsRenderer
    public void screenTurnedOn() {
        Log.d(this.TAG, "screenTurnedOn");
        this.mCountOfScaleDown = 100;
        this.mGlView.setRenderMode(1);
    }

    @Override // com.samsung.android.visualeffect.lock.common.ISPhysicsRenderer
    public void screenTurnedOff() {
        Log.d(this.TAG, "screenTurnedOff");
        Log.d(this.TAG, "native_onKeyEvent EVENT_RESET_BGSCALE");
        this.mIJniRenderer.onKeyEvent(this.EVENT_RESET_BGSCALE);
    }

    @Override // com.samsung.android.visualeffect.lock.common.ISPhysicsRenderer
    public void reset() {
        Log.d(this.TAG, "reset");
    }

    @Override // com.samsung.android.visualeffect.lock.common.ISPhysicsRenderer
    public void onDestroy() {
        Log.d(this.TAG, "destroyed");
        if (!this.isSurfaceCreated) {
            Log.d(this.TAG, "native DeInit_PhysicsEngineJNI");
            this.mIJniRenderer.DeInit_PhysicsEngineJNI();
        } else {
            Log.d(this.TAG, "Surface isn't created");
        }
        this.mDrawCount = 0;
    }

    @Override // com.samsung.android.visualeffect.lock.common.ISPhysicsRenderer
    public void onSensorChanged(SensorEvent event) {
        if (event.values[0] > 10.0f) {
            event.values[0] = 10.0f;
        } else if (event.values[0] < -10.0f) {
            event.values[0] = -10.0f;
        }
        if (event.values[1] > 10.0f) {
            event.values[1] = 10.0f;
        } else if (event.values[1] < -10.0f) {
            event.values[1] = -10.0f;
        }
        int rotation = ((WindowManager) this.veContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
        switch (rotation) {
            case Surface.ROTATION_0:
                onSensorEvent(event.sensor.getType(), event.values[0], event.values[1], event.values[2]);
                return;
            case Surface.ROTATION_90:
                onSensorEvent(event.sensor.getType(), event.values[1] * (-1.0f), event.values[0], event.values[2]);
                return;
            case Surface.ROTATION_180:
                onSensorEvent(event.sensor.getType(), event.values[0] * (-1.0f), event.values[1] * (-1.0f), event.values[2]);
                return;
            case Surface.ROTATION_270:
                onSensorEvent(event.sensor.getType(), event.values[1], event.values[0] * (-1.0f), event.values[2]);
                return;
            default:
        }
    }

    protected int getProjectKind() {
        /*int i = this.PHONE_PROJECT;
        String mDeviceType = SystemProperties.get("ro.build.characteristics");
        if (mDeviceType != null && mDeviceType.contains("tablet")) {
            Log.d(this.TAG, "isTablet() - true");
            int projectKind = this.TABLET_PROJECT;
            return projectKind;
        }
        Log.d(this.TAG, "isTablet() - false");
        int projectKind2 = this.PHONE_PROJECT;
        return projectKind2;*/
        if (Utils.isTablet(veContext)) {
            return TABLET_PROJECT;
        }
        return PHONE_PROJECT;
    }

    @Override // com.samsung.android.visualeffect.lock.common.ISPhysicsRenderer
    public int getDrawCount() {
        return this.mDrawCount;
    }

    protected void InitPhysics(int projectKind, int width, int height) {
    }

    protected void IsExistBubbles() {
    }

    protected void onSensorEvent(int sensorType, float xValue, float yValue, float zValue) {
    }
}