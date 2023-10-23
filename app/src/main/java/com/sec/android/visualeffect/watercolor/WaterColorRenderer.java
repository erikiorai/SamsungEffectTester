package com.sec.android.visualeffect.watercolor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.opengl.GLSurfaceView;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/* loaded from: classes.dex */
public class WaterColorRenderer implements GLSurfaceView.Renderer {
    private static JniWaterColorRenderer mJniWaterColor;
    boolean isFirstSurfaceChanged;
    ArrayList<Boolean> mBgChangeCheckArray;
    ArrayList<Boolean> mClearEffectArray;
    Context mContext;
    private Runnable mDefaultRunnable;
    private Bitmap mMask1;
    private Bitmap mMask2;
    private Bitmap mMask3;
    private Bitmap mNoise;
    GLSurfaceView mParent;
    private int mQualityLevel;
    private int mTabletMode;
    private Bitmap mTube;
    ArrayList<Boolean> mUnLockEffectArray;
    int windowHeight;
    int windowWidth;
    private final boolean DBG = true;
    private final String TAG = "WaterColor_WaterColorRenderer";
    Bitmap mPortraitBG = null;
    Bitmap mLandscapeBG = null;
    private boolean calledScreenTurnedOn = false;
    final int[] pointer_xpos = new int[10];
    final int[] pointer_ypos = new int[10];
    private long prevPressTime = 0;
    private int prevTouchMoveX = 0;
    private int prevTouchMoveY = 0;
    private boolean isDraged = false;
    private int DISTANCE_MAX_OF_DRAG = 50;
    private final int EVENT_CLEAR = 90;
    private final int EVENT_UNLOCK = 91;
    private final int EVENT_DELETE_BACKGROUND = 92;
    private final int EVENT_RELOAD_BACKGROUND = 94;
    private final int EVENT_DELETE_FBO = 93;
    private final int EVENT_RELOAD_FBO = 95;
    boolean isSurfaceCreated = true;
    int mCountOfDirtyMode = 0;
    int logCount = 0;
    private boolean isCleanup = false;
    boolean isPreSetTextureCalled = false;
    private boolean isOrientationChanged = false;
    private int isOrientationChangCount = 0;
    private boolean isSurfaceChanged = false;
    private int isPrevSurfaceWidth = 0;
    private int mDrawCount = 0;

    public WaterColorRenderer(Context context, GLSurfaceView view, int tabletmode, JniWaterColorRenderer mJni, int qualityLevel, int pWidth, int pHeight) {
        this.windowWidth = 0;
        this.windowHeight = 0;
        this.isFirstSurfaceChanged = true;
        this.mTabletMode = 0;
        this.mBgChangeCheckArray = null;
        this.mClearEffectArray = null;
        this.mUnLockEffectArray = null;
        this.mQualityLevel = 0;
        Log.d("WaterColor_WaterColorRenderer", "WaterColorRender Constructor 2014-08-21, onSurfaceChanged edited");
        this.mContext = context;
        this.mParent = view;
        mJniWaterColor = mJni;
        this.mTabletMode = tabletmode;
        this.mQualityLevel = qualityLevel;
        mJniWaterColor.Init_PhysicsEngineJNI();
        this.isFirstSurfaceChanged = true;
        this.mBgChangeCheckArray = new ArrayList<>();
        this.mClearEffectArray = new ArrayList<>();
        this.mUnLockEffectArray = new ArrayList<>();
        this.windowWidth = pWidth;
        this.windowHeight = pHeight;
        Log.d("WaterColor_WaterColorRenderer", "windowWidth = " + this.windowWidth + ", windowHeight = " + this.windowHeight);
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.d("WaterColor_WaterColorRenderer", "onSurfaceCreated");
        this.isSurfaceCreated = true;
        this.isPreSetTextureCalled = false;
        this.isPrevSurfaceWidth = 0;
        this.mDrawCount = 0;
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.d("WaterColor_WaterColorRenderer", "onSurfaceChanged, width = " + width + ", height = " + height);
        this.isSurfaceChanged = true;
        this.logCount = 0;
        if (this.isPrevSurfaceWidth == width) {
            Log.d("WaterColor_WaterColorRenderer", "isPrevSurfaceWidth == width");
            return;
        }
        this.isPrevSurfaceWidth = width;
        if (this.mTabletMode == 0) {
            if (this.isFirstSurfaceChanged) {
                mJniWaterColor.Init_PhysicsEngine(this.mTabletMode, this.mQualityLevel, Math.min(width, height), Math.max(width, height));
                this.isFirstSurfaceChanged = false;
            }
        } else if (this.isSurfaceCreated) {
            Log.d("WaterColor_WaterColorRenderer", "First onSurfaceChanged");
            mJniWaterColor.Init_PhysicsEngine(this.mTabletMode, this.mQualityLevel, width, height);
            this.isSurfaceCreated = false;
        } else {
            Log.d("WaterColor_WaterColorRenderer", "2nd, 3rd..... onSurfaceChanged");
            if (!this.isPreSetTextureCalled) {
                Log.d("WaterColor_WaterColorRenderer", "isPreSetTextureCalled == false");
                preSetTexture();
            } else {
                Log.d("WaterColor_WaterColorRenderer", "changeNoiseTexture()");
                changeNoiseTexture();
            }
            mJniWaterColor.onSurfaceChangedEvent(width, height);
            this.mParent.setRenderMode(1);
            this.mCountOfDirtyMode = 0;
        }
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onDrawFrame(GL10 gl) {
        checkBackground();
        if (this.mClearEffectArray.size() != 0) {
            Log.d("WaterColor_WaterColorRenderer", "onDrawFrame, mJniWaterColor.onKeyEvent(EVENT_CLEAR)");
            mJniWaterColor.onKeyEvent(90);
            while (this.mClearEffectArray.size() != 0) {
                this.mClearEffectArray.remove(0);
            }
        }
        if (this.mUnLockEffectArray.size() != 0) {
            Log.d("WaterColor_WaterColorRenderer", "onDrawFrame, mJniWaterColor.onKeyEvent(EVENT_UNLOCK)");
            mJniWaterColor.onKeyEvent(91);
            while (this.mUnLockEffectArray.size() != 0) {
                this.mUnLockEffectArray.remove(0);
            }
        }
        if (this.isOrientationChanged) {
            if (this.isSurfaceChanged || this.isOrientationChangCount > 20) {
                Log.d("WaterColor_WaterColorRenderer", "= onConfigurationChanged = onDrawFrame isSurfaceChanged == true && isOrientationChanged == true, isOrientationChangCount = " + this.isOrientationChangCount);
                this.isOrientationChanged = false;
            } else {
                this.isOrientationChangCount++;
            }
        }
        this.isSurfaceChanged = false;
        if (this.mDrawCount < 2) {
            if (this.mDrawCount == 1 && !this.isPreSetTextureCalled) {
                preSetTexture();
            }
            mJniWaterColor.Draw_PhysicsEngine();
            this.mDrawCount++;
        } else {
            mJniWaterColor.Draw_PhysicsEngine();
        }
        if (mJniWaterColor.isEmpty() == 1 && !this.isOrientationChanged && this.mBgChangeCheckArray.size() == 0 && this.mClearEffectArray.size() == 0 && this.mUnLockEffectArray.size() == 0) {
            Log.d("WaterColor_WaterColorRenderer", "mJniWaterColor is Empty");
            this.mCountOfDirtyMode++;
            if (this.mCountOfDirtyMode >= 2) {
                Log.d("WaterColor_WaterColorRenderer", "Drity Mode");
                this.mParent.setRenderMode(0);
                this.mCountOfDirtyMode = 0;
            }
        } else if (this.logCount > 100) {
            Log.d("WaterColor_WaterColorRenderer", "mJniWaterColor is not Empty");
            this.logCount = 0;
        }
        this.logCount++;
    }

    public void onConfigurationChanged() {
        Log.d("WaterColor_WaterColorRenderer", "= onConfigurationChanged = Renderer onConfigurationChanged");
        this.isOrientationChanged = true;
        this.isOrientationChangCount = 0;
        this.mParent.setRenderMode(1);
    }

    public void setResourcesBitmap(Bitmap mask1, Bitmap mask2, Bitmap mask3, Bitmap tube, Bitmap noise) {
        this.mMask1 = mask1;
        this.mMask2 = mask2;
        this.mMask3 = mask3;
        this.mTube = tube;
        this.mNoise = noise;
    }

    public void setBackground(Bitmap bg) {
        Log.d("WaterColor_WaterColorRenderer", "setBackground");
        this.mPortraitBG = getCenterCropBitmap(bg, Math.min(this.windowWidth, this.windowHeight), Math.max(this.windowWidth, this.windowHeight));
        if (this.mTabletMode == 1) {
            this.mLandscapeBG = getCenterCropBitmap(bg, Math.max(this.windowWidth, this.windowHeight), Math.min(this.windowWidth, this.windowHeight));
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        boolean returnValue = false;
        this.pointer_xpos[0] = (int) event.getX();
        this.pointer_ypos[0] = (int) event.getY();
        Log.d("WaterColor_WaterColorRenderer", "onTouchEvent action = " + action);
        if (this.isCleanup || this.mDrawCount < 2) {
            Log.d("WaterColor_WaterColorRenderer", "isCleanup = " + this.isCleanup + ", mDrawCount = " + this.mDrawCount + "!!! return");
            return false;
        }
        this.mParent.setRenderMode(1);
        switch (action) {
            case 0:
                Log.d("WaterColor_WaterColorRenderer", "ACTION_DOWN");
                mJniWaterColor.onTouchEvent(0, 1, 0, this.pointer_xpos, this.pointer_ypos);
                returnValue = true;
                this.prevPressTime = SystemClock.uptimeMillis();
                this.prevTouchMoveX = this.pointer_xpos[0];
                this.prevTouchMoveY = this.pointer_ypos[0];
                this.isDraged = false;
                this.isOrientationChanged = false;
                break;
            case 1:
                Log.d("WaterColor_WaterColorRenderer", "ACTION_UP");
                long differTime = SystemClock.uptimeMillis() - this.prevPressTime;
                if (!this.isDraged && differTime > 600) {
                    Log.d("WaterColor_WaterColorRenderer", "LONG PRESS UP");
                    mJniWaterColor.onTouchEvent(0, 1, 1, this.pointer_xpos, this.pointer_ypos);
                    returnValue = true;
                    break;
                }
                break;
            case 2:
                if (!this.isDraged) {
                    float dx = this.pointer_xpos[0] - this.prevTouchMoveX;
                    float dy = this.pointer_ypos[0] - this.prevTouchMoveY;
                    int distanceForDragSound = (int) Math.sqrt(Math.pow(dx, 2.0d) + Math.pow(dy, 2.0d));
                    if (distanceForDragSound > this.DISTANCE_MAX_OF_DRAG) {
                        Log.d("WaterColor_WaterColorRenderer", "distanceForDragSound = " + distanceForDragSound);
                        this.isDraged = true;
                    }
                }
                mJniWaterColor.onTouchEvent(0, 1, 2, this.pointer_xpos, this.pointer_ypos);
                break;
            case 7:
                Log.d("WaterColor_WaterColorRenderer", "ACTION_HOVER_MOVE");
                mJniWaterColor.onTouchEvent(0, 1, 7, this.pointer_xpos, this.pointer_ypos);
                break;
            case 9:
                Log.d("WaterColor_WaterColorRenderer", "ACTION_HOVER_ENTER");
                mJniWaterColor.onTouchEvent(0, 1, 9, this.pointer_xpos, this.pointer_ypos);
                break;
            case 10:
                Log.d("WaterColor_WaterColorRenderer", "ACTION_HOVER_EXIT");
                mJniWaterColor.onTouchEvent(0, 1, 10, this.pointer_xpos, this.pointer_ypos);
                break;
        }
        return returnValue;
    }

    public void onTouchEventForPatternLock(MotionEvent event) {
        int action = event.getActionMasked();
        this.pointer_xpos[0] = (int) event.getX();
        this.pointer_ypos[0] = (int) event.getY();
        Log.d("WaterColor_WaterColorRenderer", "onTouchEventForPatternLock action = " + action);
        if (this.isCleanup || this.mDrawCount < 2) {
            Log.d("WaterColor_WaterColorRenderer", "isCleanup = " + this.isCleanup + ", mDrawCount = " + this.mDrawCount + "!!! return");
            return;
        }
        this.mParent.setRenderMode(1);
        switch (action) {
            case 0:
                Log.d("WaterColor_WaterColorRenderer", "ACTION_DOWN => ACTION_HOVER_ENTER");
                mJniWaterColor.onTouchEvent(0, 1, 9, this.pointer_xpos, this.pointer_ypos);
                this.isOrientationChanged = false;
                return;
            case 1:
                Log.d("WaterColor_WaterColorRenderer", "ACTION_UP");
                long differTime = SystemClock.uptimeMillis() - this.prevPressTime;
                if (differTime > 600) {
                    Log.d("WaterColor_WaterColorRenderer", "LONG PRESS, ACTION_UP => ACTION_HOVER_EXIT");
                    mJniWaterColor.onTouchEvent(0, 1, 10, this.pointer_xpos, this.pointer_ypos);
                    return;
                }
                return;
            case 2:
                Log.d("WaterColor_WaterColorRenderer", "ACTION_MOVE => ACTION_HOVER_MOVE");
                mJniWaterColor.onTouchEvent(0, 1, 7, this.pointer_xpos, this.pointer_ypos);
                return;
            default:
                return;
        }
    }

    public void preSetBGTexture() {
        Log.d("WaterColor_WaterColorRenderer", "preSetBGTexture");
        if (this.mPortraitBG != null) {
            mJniWaterColor.SetTexture("PortraitBG", this.mPortraitBG, false);
        } else {
            Log.d("WaterColor_WaterColorRenderer", "mPortraitBG is null ");
        }
        if (this.mTabletMode == 1) {
            if (this.mLandscapeBG != null) {
                mJniWaterColor.SetTexture("LandscapeBG", this.mLandscapeBG, false);
            } else {
                Log.d("WaterColor_WaterColorRenderer", "mLandscapeBG is null ");
            }
        }
        recycleBG();
    }

    public void preSetTexture() {
        Log.e("WaterColor_WaterColorRenderer", "preSetTexture()");
        if (this.isPreSetTextureCalled) {
            Log.e("WaterColor_WaterColorRenderer", "isPreSetTextureCalled == true!!! return");
            return;
        }
        mJniWaterColor.SetTexture("Mask1", this.mMask1, false);
        mJniWaterColor.SetTexture("Mask2", this.mMask2, false);
        mJniWaterColor.SetTexture("Mask3", this.mMask3, false);
        mJniWaterColor.SetTexture("Tube", this.mTube, false);
        mJniWaterColor.SetTexture("Noise", this.mNoise, false);
        this.isPreSetTextureCalled = true;
        recycleResource();
    }

    public void changeNoiseTexture() {
        Log.e("WaterColor_WaterColorRenderer", "changeNoiseTexture()");
        mJniWaterColor.SetTexture("Noise", this.mNoise, false);
    }

    public synchronized void changeBackground(Bitmap bg) {
        Log.d("WaterColor_WaterColorRenderer", "changeBackground");
        if (bg == null) {
            Log.d("WaterColor_WaterColorRenderer", "bg is null");
        } else {
            while (this.mBgChangeCheckArray.size() != 0) {
                this.mBgChangeCheckArray.remove(0);
            }
            setBackground(bg);
            this.mBgChangeCheckArray.add(true);
        }
    }

    private Bitmap getCenterCropBitmap(Bitmap bitmap, int width, int height) {
        Log.d("WaterColor_WaterColorRenderer", "getCenterCropBitmap()");
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        float ratio = width / height;
        float bitmapRatio = bitmapWidth / bitmapHeight;
        if (bitmapRatio > ratio) {
            Log.d("WaterColor_WaterColorRenderer", "bmp is horizontally");
            int targetWidth = (int) (bitmapHeight * ratio);
            Bitmap finalBitmap = Bitmap.createBitmap(bitmap, (bitmapWidth - targetWidth) / 2, 0, targetWidth, bitmapHeight);
            return finalBitmap;
        }
        Log.d("WaterColor_WaterColorRenderer", "bmp is vertically");
        int targetHeight = (int) (bitmapWidth / ratio);
        Bitmap finalBitmap2 = Bitmap.createBitmap(bitmap, 0, (bitmapHeight - targetHeight) / 2, bitmapWidth, targetHeight);
        return finalBitmap2;
    }

    private void recycleBG() {
        if (this.mPortraitBG != null) {
            this.mPortraitBG.recycle();
            this.mPortraitBG = null;
        }
        if (this.mTabletMode == 1 && this.mLandscapeBG != null) {
            this.mLandscapeBG.recycle();
            this.mLandscapeBG = null;
        }
    }

    private void recycleResource() {
        if (this.mMask1 != null) {
            this.mMask1.recycle();
            this.mMask1 = null;
        }
        if (this.mMask2 != null) {
            this.mMask2.recycle();
            this.mMask2 = null;
        }
        if (this.mMask3 != null) {
            this.mMask3.recycle();
            this.mMask3 = null;
        }
        if (this.mTube != null) {
            this.mTube.recycle();
            this.mTube = null;
        }
    }

    public synchronized void checkBackground() {
        if (this.mBgChangeCheckArray.size() != 0) {
            Log.d("WaterColor_WaterColorRenderer", "Change opengl BG Texture");
            preSetBGTexture();
            while (this.mBgChangeCheckArray.size() != 0) {
                this.mBgChangeCheckArray.remove(0);
            }
        }
    }

    public void unlockEffect() {
        if (this.mDrawCount > 1) {
            Log.d("WaterColor_WaterColorRenderer", "unlockWaterColor");
            this.mUnLockEffectArray.add(true);
            this.mParent.setRenderMode(1);
        }
    }

    public void clearWaterColor() {
        if (this.mDrawCount > 1) {
            Log.d("WaterColor_WaterColorRenderer", "clearWaterColor");
            this.mClearEffectArray.add(true);
            this.mParent.setRenderMode(1);
        }
    }

    public void onKeyEvent(int keyCode) {
        mJniWaterColor.onKeyEvent(keyCode);
    }

    public void show() {
        Log.d("WaterColor_WaterColorRenderer", "show");
        removeDefaultRunnable();
        this.isCleanup = false;
        this.isOrientationChanged = false;
    }

    public void screenTurnedOn() {
        Log.d("WaterColor_WaterColorRenderer", "screenTurnedOn");
        this.calledScreenTurnedOn = true;
    }

    public void cleanUp() {
        Log.d("WaterColor_WaterColorRenderer", "cleanUp");
        this.isCleanup = true;
        this.calledScreenTurnedOn = false;
        this.isOrientationChanged = false;
        if (this.mDrawCount > 1) {
            this.mParent.postDelayed(new Runnable() { // from class: com.sec.android.visualeffect.watercolor.WaterColorRenderer.1
                @Override // java.lang.Runnable
                public void run() {
                    Log.d("WaterColor_WaterColorRenderer", "postDelayed");
                    WaterColorRenderer.this.clearWaterColor();
                }
            }, 300L);
        }
    }

    public void reset() {
        Log.d("WaterColor_WaterColorRenderer", "reset");
        removeDefaultRunnable();
        clearWaterColor();
        this.calledScreenTurnedOn = false;
        this.isOrientationChanged = false;
    }

    public void showUnlockAffordance(long startDelay, Rect rect) {
        Log.d("WaterColor_WaterColorRenderer", "showUnlockAffordance()");
        Log.d("WaterColor_WaterColorRenderer", "calledScreenTurnedOn = " + this.calledScreenTurnedOn);
        removeDefaultRunnable();
        if (this.calledScreenTurnedOn) {
            this.pointer_xpos[0] = rect.left + ((rect.right - rect.left) / 2);
            this.pointer_ypos[0] = rect.top + ((rect.bottom - rect.top) / 2);
            if (this.mDefaultRunnable == null) {
                Log.d("WaterColor_WaterColorRenderer", "mDefaultRunnable,  new Runnable()!!!");
                this.mDefaultRunnable = new Runnable() { // from class: com.sec.android.visualeffect.watercolor.WaterColorRenderer.2
                    @Override // java.lang.Runnable
                    public void run() {
                        if (WaterColorRenderer.this.mDrawCount > 1) {
                            Log.d("WaterColor_WaterColorRenderer", "mDefaultRunnable run, and toucn down()");
                            WaterColorRenderer.mJniWaterColor.onTouchEvent(0, 1, 0, WaterColorRenderer.this.pointer_xpos, WaterColorRenderer.this.pointer_ypos);
                            WaterColorRenderer.this.mParent.setRenderMode(1);
                            WaterColorRenderer.this.mDefaultRunnable = null;
                            WaterColorRenderer.this.calledScreenTurnedOn = false;
                            return;
                        }
                        Log.d("WaterColor_WaterColorRenderer", "Because mDrawCount = " + WaterColorRenderer.this.mDrawCount + ", mDefaultRunnable, postDelayed()!!!");
                        WaterColorRenderer.this.mParent.postDelayed(WaterColorRenderer.this.mDefaultRunnable, 250L);
                    }
                };
            }
            Log.d("WaterColor_WaterColorRenderer", "mDefaultRunnable, postDelayed()!!!");
            this.mParent.postDelayed(this.mDefaultRunnable, startDelay);
        }
    }

    private void removeDefaultRunnable() {
        if (this.mDefaultRunnable != null) {
            Log.d("WaterColor_WaterColorRenderer", "mDefaultRunnable isn't null, mParent.removeCallbacks(mDefaultRunnable)");
            this.mParent.removeCallbacks(this.mDefaultRunnable);
            this.mDefaultRunnable = null;
        }
    }

    public void destroyed() {
        Log.d("WaterColor_WaterColorRenderer", "destroyed");
    }
}