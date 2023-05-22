package com.samsung.android.visualeffect.lock.common;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.samsung.android.visualeffect.IEffectListener;
import com.samsung.android.visualeffect.common.GLTextureView;

import java.util.Arrays;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

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
    protected final Native mNative = new Native();
    protected int drawCount = 0;
    protected int drawInitNum = 3;
    String[] mSpecialTextures = null;

    @Override // com.samsung.android.visualeffect.common.GLTextureView.Renderer
    public void onDestroy() {
        Log.i(this.TAG, "onDestroy()");
        if (this.drawCount > 0) {
            this.mNative.destroy();
        } else {
            Log.i(this.TAG, "Ignore! because isRendered is false");
        }
    }

    public void showUnlockAffordance(int posX, int posY) {
        Log.i(this.TAG, "showUnlockAffordance");
        this.mAffordancePosX = posX;
        this.mAffordancePosY = posY;
        this.isAffordanceOccur = true;
        this.mGlView.setRenderMode(1);
    }

    public void handleTouchEvent(int action, int x, int y) {
        //Log.i(TAG, "handleTouchEvent");
        if (this.drawCount <= this.drawInitNum) {
            Log.d(this.TAG, "drawCount = " + this.drawCount + ", Touch Return");
            return;
        }
        if (!(action == 2 || action == 7)) {
            Log.i(this.TAG, "Renderer handleTouchEvent action = " + action);
        }
        switch (action) {
            case 0:
                this.mNative.onTouch(x, y, 0);
                break;
            case 1:
                this.mNative.onTouch(x, y, 1);
                break;
            case 2:
                this.mNative.onTouch(x, y, 2);
                break;
            case 7:
                this.mNative.onTouch(x, y, 5);
                break;
            case 9:
                this.mNative.onTouch(x, y, 3);
                break;
            case 10:
                this.mNative.onTouch(x, y, 4);
                break;
        }
        this.mGlView.setRenderMode(1);
    }

    public void setListener(IEffectListener listener) {
        Log.i(TAG, "setListener");
        this.callBackListener = listener;
    }

/*    @Override // com.samsung.android.visualeffect.common.GLTextureView.Renderer
    public void onDrawFrame(GL10 gl10) {
        Log.i(TAG, "onDrawFrame");
        boolean z = true;
        if (this.mIsNeedToReinit) {
            this.mSpecialTextures = this.mNative.loadEffect(this.mLibName);
            Log.d(TAG, "onDrawFrame: special textures: " + Arrays.toString(mSpecialTextures));
            this.framecounter = -1;
        }
        if (this.framecounter == -1) {
            this.timeStart = System.currentTimeMillis();
        }
        if (this.mBackgroundPixels != null) {
            Log.d(this.TAG, "mNative.loadTexture");
            this.mNative.loadTexture("bg", this.mBackgroundPixels, this.mBackgroundWidth, this.mBackgroundHeight);
            Log.d(TAG, "onDrawFrame: width " + mBackgroundWidth + ", height " + mBackgroundHeight);
            this.mBackgroundPixels = null;
        }
        if (this.mIsNeedToReinit) {
            this.mNative.drawBgOnly(this.mWidth, this.mHeight);
            this.mIsNeedToReinit = false;
            this.mIsNeedToReinit2 = true;
            return;
        }
        if (this.mIsNeedToReinit2) {
            loadSpecialTexture(this.mSpecialTextures);
            this.mNative.init(this.mWidth, this.mHeight, true);
            this.mIsNeedToReinit2 = false;
        }
        if (this.isAffordanceOccur) {
            this.mNative.showAffordance(this.mAffordancePosX, this.mAffordancePosY);
            this.isAffordanceOccur = false;
        }
        if (this.mNative.draw() || this.drawCount <= this.drawInitNum) {
            z = false;
        } else {
            Log.i(this.TAG, "dirty mode");
            this.mGlView.setRenderMode(0);
        }
        this.framecounter++;
        float currentTimeMillis = (float) (System.currentTimeMillis() - this.timeStart);
        if (currentTimeMillis >= 1000.0f || z) {
            if (this.framecounter > 2) {
                Log.i(this.TAG, "fps " + ((this.framecounter * 1000.0f) / currentTimeMillis));
            }
            this.framecounter = -1;
        }
        if (this.drawCount <= this.drawInitNum) {
            if (this.drawCount == 0) {
                Log.i(this.TAG, "onDrawFrame, First Rendering!");
            }
            if (this.drawCount == this.drawInitNum && this.callBackListener != null) {
                Log.i(this.TAG, "callBackListener.onReceive(EffectStatus.READY");
                this.callBackListener.onReceive(0, null);
            }
            this.drawCount++;
        }
    } */

    @Override // com.samsung.android.visualeffect.common.GLTextureView.Renderer
    public void onDrawFrame(GL10 gl) {
        if (this.mIsNeedToReinit) {
            loadSpecialTexture(this.mNative.loadEffect(this.mLibName));
            this.framecounter = -1;
        }
        if (this.framecounter == -1) {
            this.timeStart = System.currentTimeMillis();
        }
        if (this.mBackgroundPixels != null) {
            Log.d(this.TAG, "mNative.loadTexture");
            this.mNative.loadTexture("bg", this.mBackgroundPixels, this.mBackgroundWidth, this.mBackgroundHeight);
            this.mBackgroundPixels = null;
        }
        if (this.mIsNeedToReinit) {
            this.mNative.init(this.mWidth, this.mHeight, true);
            this.mIsNeedToReinit = false;
        }
        if (this.isAffordanceOccur) {
            this.mNative.showAffordance(this.mAffordancePosX, this.mAffordancePosY);
            this.isAffordanceOccur = false;
        }
        boolean isStopDrawing = false;
        if (!this.mNative.draw() && this.drawCount > this.drawInitNum) {
            Log.i(this.TAG, "dirty mode");
            isStopDrawing = true;
            this.mGlView.setRenderMode(0);
        }
        this.framecounter++;
        long now = System.currentTimeMillis();
        float spent = (float) (now - this.timeStart);
        if (spent >= 1000.0f || isStopDrawing) {
            if (this.framecounter > 2) {
                float fps = (this.framecounter * 1000.0f) / spent;
                Log.i(this.TAG, "fps " + fps);
            }
            this.framecounter = -1;
        }
        if (this.drawCount <= this.drawInitNum) {
            if (this.drawCount == 0) {
                Log.i(this.TAG, "onDrawFrame, First Rendering!");
            }
            if (this.drawCount == this.drawInitNum && this.callBackListener != null) {
                Log.i(this.TAG, "callBackListener.onReceive(EffectStatus.READY");
                this.callBackListener.onReceive(0, null);
            }
            this.drawCount++;
        }
    }


    @Override // com.samsung.android.visualeffect.common.GLTextureView.Renderer
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        Log.i(TAG, "onSurfaceChanged");
        int max = Math.max(this.mWidth, this.mHeight) / 5;
        if (width < max || height < max) {
            Log.i(this.TAG, "onSurfaceChanged problem width " + width + " " + height + "  disp " + this.mWidth + " " + this.mHeight);
            return;
        }
        if (!(this.mWidth == width && this.mHeight == height)) {
            this.mIsNeedToReinit = true;
        }
        this.mWidth = width;
        this.mHeight = height;
        this.mGlView.setRenderMode(1);
        Log.i(this.TAG, "onSurfaceChanged " + width + " " + height);
    }

    @Override // com.samsung.android.visualeffect.common.GLTextureView.Renderer
    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        Log.i(this.TAG, "onSurfaceCreated");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRealMetrics(displayMetrics);
        this.mWidth = displayMetrics.widthPixels;
        this.mHeight = displayMetrics.heightPixels;
        this.mIsNeedToReinit = true;
        this.drawCount = 0;
        String mLibDir = this.mContext.getApplicationInfo().nativeLibraryDir;
        this.mLibName = mLibDir + "/" + this.mLibName;
        Log.d(this.TAG, "mLibName = " + this.mLibName);
    }

    public void setBackgroundBitmap(int[] pixels, int width, int height) {
        Log.i(this.TAG, "setBackgroundBitmap");
        this.mBackgroundPixels = pixels;
        this.mBackgroundWidth = width;
        this.mBackgroundHeight = height;
        this.mGlView.setRenderMode(1);
    }

    public void setParameters(int[] aNums, float[] aValues) {
        Log.i(TAG, "setParameters");
        this.mNative.setParameters(aNums, aValues);
    }

    public void loadSpecialTexture(String[] aTexture) {
        Log.i(TAG, "loadSpecialTexture: " + Arrays.toString(aTexture));
        if (aTexture != null) {
            new BitmapFactory.Options().inScaled = false;
            if (this.mContext != null) {
                Resources resources = this.mContext.getResources();
                for (int i = 0; i < aTexture.length; i++) {
                    try {
                        Bitmap decodeResource = BitmapFactory.decodeResource(resources, resources.getIdentifier(aTexture[i], "drawable", this.mContext.getPackageName()));
                        int width = decodeResource.getWidth();
                        int height = decodeResource.getHeight();
                        int[] pixels = new int[width * height];
                        decodeResource.getPixels(pixels, 0, width, 0, 0, width, height);
                        Log.i("", "Adding texture Width'" + width + "'");
                        Log.i("", "Adding texture Height'" + height + "'");
                        this.mNative.loadTexture(aTexture[i], pixels, width, height);
                        decodeResource.recycle();
                    } catch (Exception e) {
                        Log.e("", "There is no image '" + aTexture[i] + "'");
                    }
                }
                this.mGlView.setRenderMode(1);
            }
        }
    }

    public void clearEffect() {
        Log.i(this.TAG, "clearEffect()");
        if (this.drawCount > this.drawInitNum) {
            this.mNative.clear();
            this.mGlView.setRenderMode(1);
            return;
        }
        Log.i(this.TAG, "Ignore! because isRendered is false");
    }

    public void showUnlock() {
        Log.i(this.TAG, "showUnlock()");
        if (this.drawCount > this.drawInitNum) {
            this.mNative.showUnlock();
            this.mGlView.setRenderMode(1);
            return;
        }
        Log.i(this.TAG, "Ignore! because isRendered is false");
    }

}
