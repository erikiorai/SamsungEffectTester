package com.samsung.android.visualeffect.liquid;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.MotionEvent;

import com.aj.effect.Utils;
import com.samsung.android.visualeffect.common.GLTextureView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/* loaded from: classes.dex */
public class LiquidRenderer implements GLTextureView.Renderer {
    public static final int QUALITY_LEVEL_0 = 0;
    public static final int QUALITY_LEVEL_1 = 1;
    public static final int QUALITY_LEVEL_2 = 2;
    public static final int QUALITY_LEVEL_3 = 3;
    private static JniLiquidLockRenderer mJniLiquidLock;
    boolean isFirstSurfaceChanged;
    Context mContext;
    GLTextureView mGlView;
    private Bitmap mLLKernel512;
    private int mQualityLevel;
    int windowHeight;
    int windowWidth;
    private final String TAG = "Liquid_LiquidLockRenderer";
    Bitmap mPortraitBG = null;
    Bitmap mLandscapeBG = null;
    final int[] pointer_xpos = new int[10];
    final int[] pointer_ypos = new int[10];
    boolean isTouched = false;
    private final int EVENT_CLEAR = 90;
    private final int EVENT_UNLOCK = 91;
    private final int EVENT_AFFORDANCE = 92;
    boolean isSurfaceCreated = true;
    int mCountOfDirtyMode = 0;
    private boolean isOrientationChanged = false;
    private int isOrientationChangCount = 0;
    private boolean isSurfaceChanged = false;
    private int mDrawCount = 0;
    private boolean isFpsChecked = true;
    private int fpsCount = 0;
    private long startTime = 0;

    public LiquidRenderer(Context context, GLTextureView view, int qualityLevel, int pWidth, int pHeight) {
        this.windowWidth = 0;
        this.windowHeight = 0;
        this.isFirstSurfaceChanged = true;
        this.mQualityLevel = 2;
        Log.d(TAG, "LiquidLockRenderer Constructor 2014-01-08, preSetBGTexture test");
        this.mContext = context;
        this.mGlView = view;
        this.mQualityLevel = qualityLevel;
        mJniLiquidLock = new JniLiquidLockRenderer();
        mJniLiquidLock.Init_PhysicsEngineJNI();
        this.isFirstSurfaceChanged = true;
        this.windowWidth = pWidth;
        this.windowHeight = pHeight;
        Log.d(TAG, "windowWidth = " + this.windowWidth + ", windowHeight = " + this.windowHeight);
    }

    @Override // com.samsung.android.visualeffect.liquid.GLTextureView.Renderer
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.d(TAG, "onSurfaceCreated");
        this.isSurfaceCreated = true;
        this.mDrawCount = 0;
    }

    @Override // com.samsung.android.visualeffect.liquid.GLTextureView.Renderer
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.d(TAG, "onSurfaceChanged, width = " + width + ", height = " + height);
        this.isSurfaceChanged = true;
        if (this.isSurfaceCreated) {
            preSetTexture();
            Log.d(TAG, "First onSurfaceChanged");
            mJniLiquidLock.Init_PhysicsEngine(Utils.isTablet(mContext) ? 1 : 0, this.mQualityLevel, width, height); //todo tablet mode always
            this.isSurfaceCreated = false;
            return;
        }
        Log.d(TAG, "2nd, 3rd..... onSurfaceChanged");
        preSetTexture();
        mJniLiquidLock.onSurfaceChangedEvent(width, height);
    }

    @Override // com.samsung.android.visualeffect.liquid.GLTextureView.Renderer
    public void onDrawFrame(GL10 gl) {
        checkOrientation();
        drawBrilliantRing();
        checkDirtyMode();
        if (this.isFpsChecked) {
            this.fpsCount++;
            long endTime = System.currentTimeMillis();
            if (endTime - this.startTime >= 1000) {
                this.startTime = endTime;
                this.fpsCount = 0;
            }
        }
    }

    public void checkOrientation() {
        if (this.isOrientationChanged) {
            if (this.isSurfaceChanged || this.isOrientationChangCount > 20) {
                Log.d(TAG, "= onConfigurationChanged = onDrawFrame isSurfaceChanged == true && isOrientationChanged == true, isOrientationChangCount = " + this.isOrientationChangCount);
                this.isOrientationChanged = false;
                return;
            }
            this.isOrientationChangCount++;
        }
    }

    private void checkDirtyMode() {
        if (mJniLiquidLock.isEmpty() == 1 && !this.isTouched && !this.isOrientationChanged) {
            Log.d(TAG, "mJniLiquidLock is Empty");
            this.mCountOfDirtyMode++;
            if (this.mCountOfDirtyMode >= 2) {
                Log.d(TAG, "checkDirtyMode() Drity Mode");
                this.mGlView.setRenderMode(0);
                this.mCountOfDirtyMode = 0;
            }
        } else if (this.mDrawCount % 100 == 11) {
            Log.d(TAG, "mJniLiquidLock is not Empty");
        }
    }

    public void drawBrilliantRing() {
        mJniLiquidLock.Draw_PhysicsEngine();
        this.mDrawCount++;
        this.isSurfaceChanged = false;
    }

    public void onConfigurationChanged() {
        Log.d(TAG, "= onConfigurationChanged = Renderer onConfigurationChanged");
        this.isOrientationChanged = true;
        this.isOrientationChangCount = 0;
        this.mGlView.setRenderMode(1);
    }

    public void setResourcesBitmap1(Bitmap LLKernel512) {
        Log.d(TAG, "setResourcesBitmap1()");
        this.mLLKernel512 = LLKernel512;
    }

    public void setBackground(Bitmap bg) {
        Log.d(TAG, "setBackground");
        this.mPortraitBG = getCenterCropBitmap(bg, Math.min(this.windowWidth, this.windowHeight), Math.max(this.windowWidth, this.windowHeight));
        this.mLandscapeBG = getCenterCropBitmap(bg, Math.max(this.windowWidth, this.windowHeight), Math.min(this.windowWidth, this.windowHeight));
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        this.pointer_xpos[0] = (int) event.getRawX();
        this.pointer_ypos[0] = (int) event.getRawY();
        Log.d(TAG, "onTouchEvent action = " + action);
        this.mGlView.setRenderMode(1);
        switch (action) {
            case 0:
                Log.d(TAG, "ACTION_DOWN");
                mJniLiquidLock.onTouchEvent(0, 1, 0, this.pointer_xpos, this.pointer_ypos);
                this.isOrientationChanged = false;
                this.isTouched = true;
                break;
            case 1:
            case 3:
                Log.d(TAG, "ACTION_UP");
                mJniLiquidLock.onTouchEvent(0, 1, 1, this.pointer_xpos, this.pointer_ypos);
                this.isTouched = false;
                break;
            case 2:
                Log.d(TAG, "ACTION_MOVE");
                mJniLiquidLock.onTouchEvent(0, 1, 2, this.pointer_xpos, this.pointer_ypos);
                this.isTouched = true;
                break;
        }
        return true;
    }

    public void preSetBGTexture() {
        Log.d(TAG, "preSetBGTexture");
        if (this.mDrawCount >= 1) {
            mJniLiquidLock.SetTexture("PortraitBG", this.mPortraitBG);
            mJniLiquidLock.SetTexture("LandscapeBG", this.mLandscapeBG);
            recycleBG();
        }
    }

    public void preSetTexture() {
        mJniLiquidLock.SetTexture("LLKernel512", this.mLLKernel512);
        mJniLiquidLock.SetTexture("PortraitBG", this.mPortraitBG);
        mJniLiquidLock.SetTexture("LandscapeBG", this.mLandscapeBG);
        recycleBG();
    }

    public void changeBackground(Bitmap bg) {
        Log.d(TAG, "changeBackground");
        if (bg == null) {
            Log.d(TAG, "bg is null");
            return;
        }
        setBackground(bg);
        preSetBGTexture();
    }

    private Bitmap getCenterCropBitmap(Bitmap bitmap, int width, int height) {
        Log.d(TAG, "getCenterCropBitmap()");
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        float ratio = (float) width / height;
        float bitmapRatio = (float) bitmapWidth / bitmapHeight;
        if (bitmapRatio > ratio) {
            Log.d(TAG, "bmp is horizontally");
            int targetWidth = (int) (bitmapHeight * ratio);
            Bitmap finalBitmap = Bitmap.createBitmap(bitmap, (bitmapWidth - targetWidth) / 2, 0, targetWidth, bitmapHeight);
            return finalBitmap;
        }
        Log.d(TAG, "bmp is vertically");
        int targetHeight = (int) (bitmapWidth / ratio);
        Bitmap finalBitmap2 = Bitmap.createBitmap(bitmap, 0, (bitmapHeight - targetHeight) / 2, bitmapWidth, targetHeight);
        return finalBitmap2;
    }

    private void recycleBG() {
        if (this.mPortraitBG != null) {
            this.mPortraitBG.recycle();
            this.mPortraitBG = null;
        }
        if (this.mLandscapeBG != null) {
            this.mLandscapeBG.recycle();
            this.mLandscapeBG = null;
        }
    }

    private void recycleResource() {
    }

    public void unlockEffect() {
        if (this.mDrawCount > 1) {
            Log.d(TAG, "test unlockWaterColor");
            mJniLiquidLock.onKeyEvent(EVENT_UNLOCK);
            this.mGlView.setRenderMode(1);
        }
    }

    public void affordanceEffect() {
        Log.d(TAG, "test affordanceEffect");
        if (this.mDrawCount > 1) {
            Log.d(TAG, "mJniLiquidLock.onKeyEvent(EVENT_AFFORDANCE);");
            mJniLiquidLock.onKeyEvent(EVENT_AFFORDANCE);
            this.mGlView.setRenderMode(1);
        }
    }

    public void clearWaterColor() {
    }

    public void show() {
        Log.d(TAG, "show");
        this.isTouched = false;
        this.isOrientationChanged = false;
        if (this.mDrawCount > 1) {
            clearWaterColor();
        }
    }

    public void screenTurnedOn() {
        Log.d(TAG, "screenTurnedOn");
    }

    public void cleanUp() {
        Log.d(TAG, "cleanUp");
        this.isTouched = false;
        this.isOrientationChanged = false;
        if (this.mDrawCount > 1) {
            clearWaterColor();
        }
    }

    public void reset() {
        Log.d(TAG, "reset");
        this.isTouched = false;
        this.isOrientationChanged = false;
        if (this.mDrawCount > 1) {
            clearWaterColor();
        }
    }

    @Override // com.samsung.android.visualeffect.liquid.GLTextureView.Renderer
    public void onDestroy() {
        Log.d(TAG, "destroyed");
        mJniLiquidLock.DeInit_PhysicsEngineJNI();
    }
}