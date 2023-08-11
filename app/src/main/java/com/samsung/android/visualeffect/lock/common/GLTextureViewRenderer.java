package com.samsung.android.visualeffect.lock.common;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.samsung.android.visualeffect.IEffectListener;
import com.samsung.android.visualeffect.common.GLTextureView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/* loaded from: classes.dex */
public class GLTextureViewRenderer implements GLTextureView.Renderer {
    protected String TAG;
    protected IEffectListener callBackListener;
    protected boolean isAffordanceOccur;
    protected Context mContext;
    protected GLTextureView mGlView;
    protected String mLibName;
    protected int mWidth = 0;
    protected int mHeight = 0;
    protected int mAffordancePosX = 0;
    protected int mAffordancePosY = 0;
    protected long timeStart = 0;
    protected int framecounter = -1;
    protected int[] mBackgroundPixels = null;
    protected int mBackgroundWidth = 0;
    protected int mBackgroundHeight = 0;
    protected boolean mIsNeedToReinit = false;
    protected boolean mIsNeedToReinit2 = false;
    private String mLibDir = null;
    protected Native mNative = new Native();
    protected int drawCount = 0;
    protected int drawInitNum = 3;
    String[] mSpecialTextures = null;

    @Override // com.samsung.android.visualeffect.common.GLTextureView.Renderer
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.i(TAG, "onSurfaceCreated");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.getDefaultDisplay().getRealMetrics(displayMetrics);
        mWidth = displayMetrics.widthPixels;
        mHeight = displayMetrics.heightPixels;
        mIsNeedToReinit = true;
        drawCount = 0;
        ApplicationInfo m1 = mContext.getApplicationInfo();
        mLibDir = m1.nativeLibraryDir;
        /*if (mLibDir.contains("64")) {
            mLibDir = "/system/lib64";
        } else {
            mLibDir = "/system/lib";
        }*/
        mLibName = mLibDir + "/" + mLibName;
        Log.d(TAG, "mLibName = " + mLibName);
    }

    @Override // com.samsung.android.visualeffect.common.GLTextureView.Renderer
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        int miniMumLenth = Math.max(mWidth, mHeight) / 5;
        if (width < miniMumLenth || height < miniMumLenth) {
            Log.i(TAG, "onSurfaceChanged problem width " + width + " " + height + "  disp " + mWidth + " " + mHeight);
            return;
        }
        if (mWidth != width || mHeight != height) {
            mIsNeedToReinit = true;
        }
        mWidth = width;
        mHeight = height;
        mGlView.setRenderMode(GLTextureView.RENDERMODE_CONTINUOUSLY);
        Log.i(TAG, "onSurfaceChanged " + width + " " + height);
    }

    //AlphaAnimation anim;

    @Override // com.samsung.android.visualeffect.common.GLTextureView.Renderer
    public void onDrawFrame(GL10 gl) {
        if (mIsNeedToReinit) {
            //anim = new AlphaAnimation(1.0f, 0.8f);
            //anim.setDuration(1000);
            //MainActivity.fps.setAnimation(anim);
            mSpecialTextures = mNative.loadEffect(mLibName);
            framecounter = -1;
        }
        if (framecounter == -1) {
            timeStart = System.currentTimeMillis();
        }
        if (mBackgroundPixels != null) {
            Log.d(TAG, "mNative.loadTexture");
            mNative.loadTexture("bg", mBackgroundPixels, mBackgroundWidth, mBackgroundHeight);
            mBackgroundPixels = null;
        }
        if (mIsNeedToReinit) {
            mNative.drawBgOnly(mWidth, mHeight);
            mIsNeedToReinit = false;
            mIsNeedToReinit2 = true;
            return;
        }
        if (mIsNeedToReinit2) {
            loadSpecialTexture(mSpecialTextures);
            mNative.init(mWidth, mHeight, true);
            mIsNeedToReinit2 = false;
        }
        if (isAffordanceOccur) {
            mNative.showAffordance(mAffordancePosX, mAffordancePosY);
            isAffordanceOccur = false;
        }
        boolean isStopDrawing = false;
        if (!mNative.draw() && drawCount > drawInitNum) {
            Log.i(TAG, "dirty mode");
            isStopDrawing = true;
            mGlView.setRenderMode(GLTextureView.RENDERMODE_WHEN_DIRTY);
        }
        framecounter++;
        long now = System.currentTimeMillis();
        float spent = (float) (now - timeStart);
        if (spent >= 1000.0f || isStopDrawing) {
            if (framecounter > 2) {
                float fps = (framecounter * 1000.0f) / spent;
                Log.i(TAG, "fps " + fps);
                //anim.startNow();
                //MainActivity.fps.startAnimation(anim);
            }
            framecounter = -1;
        }
        if (drawCount <= drawInitNum) {
            if (drawCount == 0) {
                Log.i(TAG, "onDrawFrame, First Rendering!");
            }
            if (drawCount == drawInitNum && callBackListener != null) {
                Log.i(TAG, "callBackListener.onReceive(EffectStatus.READY");
                callBackListener.onReceive(0, null);
            }
            drawCount++;
        }
    }

    public void setParameters(int[] aNums, float[] aValues) {
        mNative.setParameters(aNums, aValues);
    }

    public void handleTouchEvent(int action, int x, int y) {
        if (drawCount <= drawInitNum) {
            Log.d(TAG, "drawCount = " + drawCount + ", Touch Return");
            return;
        }
        if (action != MotionEvent.ACTION_MOVE && action != MotionEvent.ACTION_HOVER_MOVE) {
            Log.i(TAG, "Renderer handleTouchEvent action = " + action);
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mNative.onTouch(x, y, MotionEvent.ACTION_DOWN);
                break;
            case MotionEvent.ACTION_UP:
                mNative.onTouch(x, y, MotionEvent.ACTION_UP);
                break;
            case MotionEvent.ACTION_MOVE:
                mNative.onTouch(x, y, MotionEvent.ACTION_MOVE);
                break;
            case MotionEvent.ACTION_HOVER_MOVE:
                mNative.onTouch(x, y, MotionEvent.ACTION_POINTER_DOWN);
                break;
            case MotionEvent.ACTION_HOVER_ENTER:
                mNative.onTouch(x, y, MotionEvent.ACTION_CANCEL);
                break;
            case MotionEvent.ACTION_HOVER_EXIT:
                mNative.onTouch(x, y, MotionEvent.ACTION_OUTSIDE);
                break;
        }
        mGlView.setRenderMode(GLTextureView.RENDERMODE_CONTINUOUSLY);
    }

    public void clearEffect() {
        Log.i(TAG, "clearEffect()");
        if (drawCount > drawInitNum) {
            mNative.clear();
            mGlView.setRenderMode(GLTextureView.RENDERMODE_CONTINUOUSLY);
            return;
        }
        Log.i(TAG, "Ignore! because isRendered is false");
    }

    @Override // com.samsung.android.visualeffect.common.GLTextureView.Renderer
    public void onDestroy() {
        Log.i(TAG, "onDestroy()");
        if (drawCount > drawInitNum) {
            mNative.destroy();
        } else {
            Log.i(TAG, "Ignore! because isRendered is false");
        }
    }

    public void showUnlockAffordance(int posX, int posY) {
        Log.i(TAG, "showUnlockAffordance");
        mAffordancePosX = posX;
        mAffordancePosY = posY;
        isAffordanceOccur = true;
        mGlView.setRenderMode(GLTextureView.RENDERMODE_CONTINUOUSLY);
    }

    public void setBackgroundBitmap(int[] pixels, int width, int height) {
        Log.i(TAG, "setBackgroundBitmap");
        mBackgroundPixels = pixels;
        mBackgroundWidth = width;
        mBackgroundHeight = height;
        mGlView.setRenderMode(GLTextureView.RENDERMODE_CONTINUOUSLY);
    }

    public void loadSpecialTexture(String[] aTexture) {
        if (aTexture != null) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;
            String packageName = mContext.getPackageName();
            Resources res = mContext.getResources();
            for (int i = 0; i < aTexture.length; i++) {
                try {
                    int id = res.getIdentifier(aTexture[i], "drawable", packageName);
                    Bitmap bitmap = BitmapFactory.decodeResource(res, id);
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    int[] pixels = new int[width * height];
                    bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
                    Log.i("", "Adding texture Width'" + width + "'");
                    Log.i("", "Adding texture Height'" + height + "'");
                    mNative.loadTexture(aTexture[i], pixels, width, height);
                    bitmap.recycle();
                } catch (Exception e) {
                    Log.e("", "There is no image '" + aTexture[i] + "'");
                }
            }
            mGlView.setRenderMode(GLTextureView.RENDERMODE_CONTINUOUSLY);
        }
    }

    public void showUnlock() {
        Log.i(TAG, "showUnlock()");
        if (drawCount > drawInitNum) {
            mNative.showUnlock();
            mGlView.setRenderMode(GLTextureView.RENDERMODE_CONTINUOUSLY);
            return;
        }
        Log.i(TAG, "Ignore! because isRendered is false");
    }

    public void setListener(IEffectListener listener) {
        callBackListener = listener;
    }

    public static void pauseAnimation() {
        Native.pauseAnimation();
    }

    public static void resumeAnimation() {
        Native.resumeAnimation();
    }

    public boolean isReadyRendering() {
        Log.i(TAG, "called isReadyRendering(), drawCount = " + drawCount);
        return drawCount > drawInitNum;
    }
}