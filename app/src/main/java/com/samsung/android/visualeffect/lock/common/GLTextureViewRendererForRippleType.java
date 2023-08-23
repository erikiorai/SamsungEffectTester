package com.samsung.android.visualeffect.lock.common;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;
import com.samsung.android.visualeffect.IEffectListener;
import com.samsung.android.visualeffect.common.GLTextureView;
import com.samsung.android.visualeffect.lock.rippleink.RippleInkStatusParams;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Queue;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/* loaded from: classes.dex */
public class GLTextureViewRendererForRippleType implements GLTextureView.Renderer {
    protected static final int PORTRAIT_MODE = 0;
    protected static final int TABLET_MODE = 1;
    protected int framecounter;
    protected Runnable mDefaultRunnable;
    protected GLTextureView mParent;
    protected long timeStart;
    protected final boolean DBG = true;
    protected String TAG = "RippleTypes";
    protected int NUM_DETAILS_WIDTH = 104;
    protected int NUM_DETAILS_HEIGHT = 104;
    protected int MESH_SIZE_WIDTH = 50;
    protected int MESH_SIZE_HEIGHT = 50;
    protected int SURFACE_DETAILS_WIDTH = 0;
    protected int SURFACE_DETAILS_HEIGHT = 0;
    protected int max = 0;
    protected int VCOUNT = 0;
    protected float unitCellWidth = 0.0f;
    protected float unitCellHeight = 0.0f;
    protected float[] heights = null;
    protected float[] velocity = null;
    protected float[] heightsSub1 = null;
    protected float[] velocitySub1 = new float[0];
    protected float[] heightsSub2 = new float[0];
    protected float[] velocitySub2 = new float[0];
    protected float[] vertices = new float[0];
    protected float[] gpuHeights = new float[0];
    protected short[] indices = new short[0];
    protected int[] textures0 = new int[0];
    protected int[] textures1 = new int[0];
    protected float[] view = new float[16];
    protected float[] proj = new float[16];
    protected float[] world = new float[16];
    protected float[] wvp = new float[16];
    protected int mScreenWidth = 0;
    protected int mScreenHeight = 0;
    protected float mouseX = 0.0f;
    protected float mouseY = 0.0f;
    protected final float REDUCTION_RATE_NORMAL = 0.94f;
    protected float mBitmapRatio = 0.0f;
    protected float TOUCH_FRESENL = 0.1f;
    protected float TOUCH_SPECULAR = 0.5f;
    protected float TOUCH_EXPONENT = 20.0f;
    protected float mFresnelRatio = this.TOUCH_FRESENL;
    protected float mSpecularRatio = this.TOUCH_SPECULAR;
    protected float mExponentRatio = this.TOUCH_EXPONENT;
    protected boolean mLandscape = false;
    protected float mReductionRate = 0.94f;
    protected float refractiveIndex = 0.93f;
    protected float reflectionRatio = 0.13f;
    protected float alphaRatio1 = 1.0f;
    protected float alphaRatio2 = 1.0f;
    protected Bitmap bitmapPortraitBG = null;
    protected Bitmap bitmapLandscapeBG = null;
    protected Bitmap bitmapEnvironmentBG = null;
    protected float glY = 0.0f;
    protected float glX = 0.0f;
    protected float intensityForLandscape = 0.35f;
    protected float intensityForPortrait = 0.5f;
    protected float translateXForLandscape = 0.0f;
    protected float translateXForPortrait = 0.0f;
    protected float translateYForLandscape = 0.0f;
    protected float translateYForPortrait = 0.0f;
    protected float translateZForPortrait = -43.05f;
    protected float translateZForLandscape = -23.8f;
    protected int spanXForLandscape = 3;
    protected int spanYForLandscape = 21;
    protected int spanXForPortrait = 21;
    protected int spanYForPortrait = 3;
    protected float XRatioForLandscape = 45.0f;
    protected float YRatioForLandscape = 25.0f;
    protected float XRatioForPortrait = 30.0f;
    protected float YRatioForPortrait = 46.0f;
    protected float XRatioAdjustPortrait = 0.0f;
    protected float XRatioAdjustLandscape = 0.0f;
    protected float intensityForRipple = 1.0f;
    protected int windowWidth = 0;
    protected int windowHeight = 0;
    protected int minLength = 0;
    protected float downX = 0.0f;
    protected float downY = 0.0f;
    protected double rippleDragThreshold = 150.0d;
    protected int rippleDistance = 0;
    protected long diffPressTime = 0;
    protected long prevPressTime = 0;
    protected int drawCount = 0;
    IEffectListener mListener = null;
    protected boolean isMakedASpenToucdUp = true;
    protected float defaultX = 0.0f;
    protected float defaultY = 0.0f;
    protected boolean isTouched = false;
    protected final int INK_DISABLE = 0;
    protected int mInkEffectColor = 0;
    protected float[] inkColors = new float[3];
    protected int spenUspLevel = 0;
    protected boolean isSPenSupport = false;
    protected float[][] inkColorFromSetting = {new float[]{0.0f, 0.4f, 1.0f}, new float[]{0.764705f, 0.470588f, 0.549019f}, new float[]{0.745098f, 0.431372f, 0.117647f}, new float[]{0.274509f, 0.509803f, 0.117647f}, new float[]{0.039215f, 0.333333f, 0.980392f}, new float[]{0.0f, 0.117647f, 0.352941f}, new float[]{0.352941f, 0.235294f, 0.705882f}, new float[]{0.215686f, 0.098039f, 0.039215f}, new float[]{0.313725f, 0.62745f, 0.705882f}};
    protected Queue<Boolean> mBgChangeCheckQueue = null;
    protected Queue<Boolean> mEffectChangeCheckQueue = null;
    protected boolean isOrientationChanged = false;
    protected int isOrientationChangCount = 0;
    protected boolean isSurfaceChanged = false;
    protected final int RIPPLE_LIGHT = 0;
    protected final int RIPPLE_LIGHT_WITH_INK = 1;
    protected int effectType = 0;
    protected int preEffectType = 0;
    protected final int UPDATE_TYPE_USER_SWITCHING = 2;
    protected final int BMP_ENVIRONMENTIMG = 0;
    protected final int BMP_PORTRAITIMG = 1;
    protected final int BMP_LANDSCAPEIMG = 2;
    protected boolean isIndigoDiffusion = false;

    public void setRippleConfiguration(int w, int h) {
        Log.d(this.TAG, "setRippleConfiguration, w = " + w + ", h = " + h);
        this.windowWidth = w;
        this.windowHeight = h;
        this.minLength = Math.max(this.windowWidth, this.windowHeight) / 5;
        this.rippleDragThreshold = (int) (0.2f * Math.min(this.windowWidth, this.windowHeight));
        Log.d(this.TAG, "rippleDragThreshold = " + this.rippleDragThreshold);
        if ((this.windowWidth != 2560 || this.windowHeight != 1600) && (this.windowWidth != 1600 || this.windowHeight != 2560)) {
            if ((this.windowWidth == 1280 && this.windowHeight == 800) || (this.windowWidth == 800 && this.windowHeight == 1280)) {
                this.translateZForLandscape = -24.933f;
                this.spanXForLandscape = 3;
                this.spanYForLandscape = 21;
                this.spanXForPortrait = 19;
                this.spanYForPortrait = 3;
                this.XRatioForLandscape = 48.0f;
                this.YRatioForLandscape = 27.0f;
            } else if ((this.windowWidth != 720 || this.windowHeight != 1280) && ((this.windowWidth != 1280 || this.windowHeight != 720) && ((this.windowWidth != 540 || this.windowHeight != 960) && ((this.windowWidth != 960 || this.windowHeight != 540) && ((this.windowWidth == 480 && this.windowHeight == 800) || (this.windowWidth == 800 && this.windowHeight == 480)))))) {
                this.NUM_DETAILS_WIDTH = 74;
                this.NUM_DETAILS_HEIGHT = 74;
                this.spanXForLandscape = 2;
                this.spanYForLandscape = 14;
                this.spanXForPortrait = 14;
                this.spanYForPortrait = 2;
                this.XRatioForPortrait = 28.0f;
                this.YRatioForPortrait = 45.0f;
            }
        }
        this.SURFACE_DETAILS_WIDTH = this.NUM_DETAILS_WIDTH - 4;
        this.SURFACE_DETAILS_HEIGHT = this.NUM_DETAILS_HEIGHT - 4;
        this.VCOUNT = this.SURFACE_DETAILS_WIDTH * this.SURFACE_DETAILS_HEIGHT;
        this.max = this.NUM_DETAILS_WIDTH * this.NUM_DETAILS_HEIGHT;
        initWaters();
        setRippleVersion(false);
    }

    @Override // com.samsung.android.visualeffect.common.GLTextureView.Renderer
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.d(this.TAG, "onSurfaceCreated");
        native_onInitUVHBuffer();
        loadEnvironmentTexture();
    }

    public void native_onInitUVHBuffer() {
    }

    @Override // com.samsung.android.visualeffect.common.GLTextureView.Renderer
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        float translateX;
        float translateY;
        float translateZ;
        Log.d(this.TAG, "onSurfaceChanged, width = " + width + ", height = " + height);
        this.isSurfaceChanged = true;
        this.drawCount = 0;
        this.mScreenWidth = width;
        this.mScreenHeight = height;
        if (this.mScreenWidth < this.minLength || this.mScreenHeight < this.minLength) {
            Log.i(this.TAG, "onSurfaceChanged problem width " + this.mScreenWidth + " " + this.mScreenHeight + "  disp " + this.windowWidth + " " + this.windowHeight);
            return;
        }
        float ratio = this.mScreenWidth / (float) this.mScreenHeight;
        Matrix.setLookAtM(this.view, 0, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
        perspectiveM(this.proj, 45.0f, ratio, 0.1f, 500.0f);
        if (this.mScreenWidth > this.mScreenHeight) {
            this.mLandscape = true;
            this.intensityForRipple = this.intensityForLandscape;
            this.mBitmapRatio = (float) Math.min(this.windowWidth, this.windowHeight) / Math.max(this.windowWidth, this.windowHeight);
            translateX = this.translateXForLandscape;
            translateY = this.translateYForLandscape;
            translateZ = this.translateZForLandscape;
        } else {
            this.mLandscape = false;
            this.intensityForRipple = this.intensityForPortrait;
            this.mBitmapRatio = (float) Math.max(this.windowWidth, this.windowHeight) / Math.min(this.windowWidth, this.windowHeight);
            translateX = this.translateXForPortrait;
            translateY = this.translateYForPortrait;
            translateZ = this.translateZForPortrait;
        }
        Matrix.setIdentityM(this.world, 0);
        Matrix.multiplyMM(this.wvp, 0, this.view, 0, this.world, 0);
        Matrix.translateM(this.wvp, 0, translateX, translateY, translateZ);
        Matrix.multiplyMM(this.wvp, 0, this.proj, 0, this.wvp, 0);
        loadShaderSetting();
        this.framecounter = -1;
    }

    public void loadShaderSetting() {
        boolean isInk = false;
        if (this.effectType == 1) {
            isInk = true;
        }
        Log.d(this.TAG, "isInk = " + isInk);
        native_loadShaderSetting(isInk);
    }

    public void native_loadShaderSetting(boolean isInk) {
    }

    @Override // com.samsung.android.visualeffect.common.GLTextureView.Renderer
    public void onDrawFrame(GL10 gl) {
        if (this.mScreenWidth < this.minLength || this.mScreenHeight < this.minLength) {
            Log.i(this.TAG, "onDrawFrame problem width " + this.mScreenWidth + " " + this.mScreenHeight + "  disp " + this.windowWidth + " " + this.windowHeight);
            return;
        }
        if (!this.isIndigoDiffusion) {
            checkEffectChange();
        }
        checkBackground();
        native_onDrawFrame();
        this.isSurfaceChanged = false;
        if (this.drawCount > 0 && !this.isOrientationChanged) {
            move();
        }
        if (this.drawCount < 2) {
            this.drawCount++;
        }
        if (this.framecounter == -1) {
            this.timeStart = System.currentTimeMillis();
        }
        this.framecounter++;
        long now = System.currentTimeMillis();
        float spent = (float) (now - this.timeStart);
        if (spent >= 1000.0f) {
            if (this.framecounter > 2) {
                float fps = (this.framecounter * 1000.0f) / spent;
                Log.i(this.TAG, "fps " + fps);
            }
            this.framecounter = -1;
        }
    }

    public void checkOrientationChanged() {
        if (this.isOrientationChanged) {
            if (this.isSurfaceChanged || this.isOrientationChangCount > 20) {
                Log.d(this.TAG, "= onConfigurationChanged = onDrawFrame isSurfaceChanged == true && isOrientationChanged == true, isOrientationChangCount = " + this.isOrientationChangCount);
                this.isOrientationChanged = false;
                return;
            }
            this.isOrientationChangCount++;
        }
    }

    public void native_onDrawFrame() {
    }

    public void onConfigurationChanged() {
        Log.d(this.TAG, "= onConfigurationChanged = Renderer onConfigurationChanged");
        this.isOrientationChanged = true;
        this.isOrientationChangCount = 0;
        this.mParent.setRenderMode(1);
    }

    public void handleTouchEvent(int action, int sourceType, float x, float y, float pressure) {
        if (this.drawCount == 0) {
            Log.d(this.TAG, "drawCount == 0 Touch Return");
            return;
        }
        this.mouseX = x;
        this.mouseY = y;
        if (this.mLandscape) {
            this.glX = (this.mouseX - (this.mScreenWidth / 2.0f)) - this.XRatioAdjustLandscape;
            this.glX *= this.XRatioForLandscape / this.mScreenWidth;
            this.glY = (this.mScreenHeight - this.mouseY) - (this.mScreenHeight / 2.0f);
            this.glY = (-this.glY) * (this.YRatioForLandscape / this.mScreenHeight);
        } else {
            this.glX = (this.mouseX - (this.mScreenWidth / 2.0f)) - this.XRatioAdjustPortrait;
            this.glX *= this.XRatioForPortrait / this.mScreenWidth;
            this.glY = (this.mScreenHeight - this.mouseY) - (this.mScreenHeight / 2.0f);
            this.glY = (-this.glY) * (this.YRatioForPortrait / this.mScreenHeight);
        }
        if (this.effectType == 1) {
            float mPressure = pressure;
            mPressure = (this.isIndigoDiffusion || ((double) mPressure) >= 1.0d) ? 1.0f : 1.0f;
            if (action == 3 || action == 1) {
                native_onTouch((int) this.mouseX, (int) this.mouseY, 1, mPressure);
                this.isMakedASpenToucdUp = true;
            } else if (this.isIndigoDiffusion || (sourceType & 16386) == 16386) {
                native_onTouch((int) this.mouseX, (int) this.mouseY, action, mPressure);
                this.isMakedASpenToucdUp = false;
            }
        }
        if (action == 0) {
            this.isTouched = true;
            this.isOrientationChanged = false;
            this.downX = this.mouseX;
            this.downY = this.mouseY;
            this.rippleDistance = 0;
            this.prevPressTime = SystemClock.uptimeMillis();
            this.diffPressTime = 0L;
            ripple(this.glY, this.glX, this.intensityForRipple * 4.0f);
            HashMap<String, String> map = new HashMap<>();
            map.put(RippleInkStatusParams.KEY_SOUND, RippleInkStatusParams.SOUND_DOWN);
            this.mListener.onReceive(1, map);
        } else if (action == 2) {
            this.isTouched = true;
            float dx = this.mouseX - this.downX;
            float dy = this.mouseY - this.downY;
            int distForwWave = (int) Math.sqrt(Math.pow(dx, 2.0d) + Math.pow(dy, 2.0d));
            this.rippleDistance += distForwWave;
            this.downX = this.mouseX;
            this.downY = this.mouseY;
            if (this.rippleDistance > this.rippleDragThreshold) {
                this.rippleDistance = 0;
                ripple(this.glY, this.glX, this.intensityForRipple * 3.0f);
                HashMap<String, String> map2 = new HashMap<>();
                map2.put(RippleInkStatusParams.KEY_SOUND, RippleInkStatusParams.SOUND_DRAG);
                this.mListener.onReceive(1, map2);
            }
        } else if (action == 1) {
            this.isTouched = false;
            this.diffPressTime = SystemClock.uptimeMillis() - this.prevPressTime;
            if (this.diffPressTime > 600) {
                ripple(this.glY, this.glX, this.intensityForRipple * 4.0f);
                HashMap<String, String> map3 = new HashMap<>();
                map3.put(RippleInkStatusParams.KEY_SOUND, RippleInkStatusParams.SOUND_DOWN);
                this.mListener.onReceive(1, map3);
            }
        } else if (action == 3) {
            this.isTouched = false;
        }
    }

    public void native_onTouch(int x, int y, int action, float pressure) {
    }

    public void setListener(IEffectListener listener) {
        this.mListener = listener;
    }

    public void initWaters() {
        Log.d(this.TAG, "initWaters");
        this.vertices = new float[this.VCOUNT * 3];
        this.indices = new short[(this.SURFACE_DETAILS_WIDTH - 1) * (this.SURFACE_DETAILS_HEIGHT - 1) * 6];
        this.heights = new float[this.max];
        this.velocity = new float[this.max];
        this.heightsSub1 = new float[this.max];
        this.velocitySub1 = new float[this.max];
        this.heightsSub2 = new float[this.max];
        this.velocitySub2 = new float[this.max];
        this.gpuHeights = new float[this.VCOUNT * 3];
        native_initWaters();
        Arrays.fill(this.heights, 0.0f);
        Arrays.fill(this.velocity, 0.0f);
        Arrays.fill(this.heightsSub1, 0.0f);
        Arrays.fill(this.velocitySub1, 0.0f);
        Arrays.fill(this.heightsSub2, 0.0f);
        Arrays.fill(this.velocitySub2, 0.0f);
        Arrays.fill(this.gpuHeights, 0.0f);
    }

    public void native_initWaters() {
    }

    public synchronized void move() {
        int xSpan;
        int ySpan;
        int imax;
        int jmax;
        if (this.heights == null || this.velocity == null || this.heightsSub1 == null || this.velocitySub1 == null || this.heightsSub2 == null || this.velocitySub2 == null || this.gpuHeights == null) {
            Log.d(this.TAG, "initWaters don't called");
        } else {
            if (this.mLandscape) {
                xSpan = this.spanXForLandscape;
                ySpan = this.spanYForLandscape;
                imax = this.NUM_DETAILS_WIDTH - ySpan;
                jmax = this.NUM_DETAILS_HEIGHT - xSpan;
            } else {
                xSpan = this.spanXForPortrait;
                ySpan = this.spanYForPortrait;
                imax = this.NUM_DETAILS_WIDTH - ySpan;
                jmax = this.NUM_DETAILS_HEIGHT - xSpan;
            }
            if (native_move(ySpan, xSpan, imax, jmax, true, 0.5f) != 0 && 1 != 0 && 1 != 0 && this.drawCount >= 2 && this.mBgChangeCheckQueue.size() == 0) {
                if (this.effectType == 1) {
                    if (!this.isTouched) {
                        setRenderModeDirty();
                    }
                } else {
                    setRenderModeDirty();
                }
            }
            for (int i = 0; i < this.SURFACE_DETAILS_HEIGHT; i++) {
                for (int j = 0; j < this.SURFACE_DETAILS_WIDTH; j++) {
                    int idx = ((this.SURFACE_DETAILS_HEIGHT * j) + i) * 3;
                    this.gpuHeights[idx + 0] = this.heights[((j + 2) * this.NUM_DETAILS_WIDTH) + i + 2];
                    this.gpuHeights[idx + 1] = this.heights[((j + 2) * this.NUM_DETAILS_WIDTH) + i + 1];
                    this.gpuHeights[idx + 2] = this.heights[((j + 1) * this.NUM_DETAILS_WIDTH) + i + 2];
                    if (i - 7 > 0) {
                        float[] fArr = this.gpuHeights;
                        int i2 = idx + 0;
                        fArr[i2] = fArr[i2] + this.heightsSub1[(((j + 2) * this.NUM_DETAILS_WIDTH) + i) - 6];
                        float[] fArr2 = this.gpuHeights;
                        int i3 = idx + 1;
                        fArr2[i3] = fArr2[i3] + this.heightsSub1[(((j + 2) * this.NUM_DETAILS_WIDTH) + i) - 7];
                        float[] fArr3 = this.gpuHeights;
                        int i4 = idx + 2;
                        fArr3[i4] = fArr3[i4] + this.heightsSub1[(((j + 1) * this.NUM_DETAILS_WIDTH) + i) - 6];
                        float[] fArr4 = this.gpuHeights;
                        int i5 = idx + 0;
                        fArr4[i5] = fArr4[i5] + this.heightsSub2[(((j + 2) * this.NUM_DETAILS_WIDTH) + i) - 6];
                        float[] fArr5 = this.gpuHeights;
                        int i6 = idx + 1;
                        fArr5[i6] = fArr5[i6] + this.heightsSub2[(((j + 2) * this.NUM_DETAILS_WIDTH) + i) - 7];
                        float[] fArr6 = this.gpuHeights;
                        int i7 = idx + 2;
                        fArr6[i7] = fArr6[i7] + this.heightsSub2[(((j + 1) * this.NUM_DETAILS_WIDTH) + i) - 6];
                    }
                }
            }
        }
    }

    public int native_move(int ySpan, int xSpan, int imax, int jmax, boolean control, float speed) {
        return 0;
    }

    public void setRenderModeDirty() {
        this.mParent.setRenderMode(0);
        Log.d(this.TAG, "RENDERMODE_WHEN_DIRTY!!!");
    }

    public synchronized void ripple(float mx, float my, float intensity) {
        Log.d(this.TAG, "ripple(), mx = " + mx + ", my = " + my + ", intensity = " + intensity);
        native_ripple(mx, my, intensity);
        this.mParent.setRenderMode(1);
    }

    public void native_ripple(float mx, float my, float intensity) {
    }

    public void perspectiveM(float[] m, float angle, float aspect, float near, float far) {
        float f = (float) Math.tan(0.5d * (3.141592653589793d - angle));
        float range = near - far;
        m[0] = f / aspect;
        m[1] = 0.0f;
        m[2] = 0.0f;
        m[3] = 0.0f;
        m[4] = 0.0f;
        m[5] = f;
        m[6] = 0.0f;
        m[7] = 0.0f;
        m[8] = 0.0f;
        m[9] = 0.0f;
        m[10] = far / range;
        m[11] = -1.0f;
        m[12] = 0.0f;
        m[13] = 0.0f;
        m[14] = (near * far) / range;
        m[15] = 0.0f;
    }

    public void clearRipple() {
        Log.d(this.TAG, "clearRipple");
        if (this.NUM_DETAILS_WIDTH != 0 && this.velocity != null && this.heights != null && this.velocity.length >= this.max && this.heights.length >= this.max) {
            Arrays.fill(this.heights, 0.0f);
            Arrays.fill(this.velocity, 0.0f);
            Arrays.fill(this.heightsSub1, 0.0f);
            Arrays.fill(this.velocitySub1, 0.0f);
            Arrays.fill(this.heightsSub2, 0.0f);
            Arrays.fill(this.velocitySub2, 0.0f);
            Arrays.fill(this.gpuHeights, 0.0f);
        }
    }

    public synchronized void clearAllEffect() {
        clearRipple();
        this.isTouched = false;
        this.isOrientationChanged = false;
        if (this.effectType == 1) {
            Log.d(this.TAG, "clearInkValue");
            native_clear();
        }
        this.mParent.setRenderMode(1);
    }

    public void native_clear() {
    }

    public void setRippleVersion(boolean isCheckEffectChange) {
        Log.d(this.TAG, "setRippleVersion");
        if (this.isIndigoDiffusion) {
            this.effectType = 1;
        } else if (this.isSPenSupport) {
            Log.d(this.TAG, "mInkEffectColor = " + this.mInkEffectColor);
            if (this.mInkEffectColor != 0) {
                Log.d(this.TAG, "Def.MODE = ModeType.RIPPLE_LIGHT_WITH_INK");
                this.effectType = 1;
                this.inkColors[0] = this.inkColorFromSetting[this.mInkEffectColor][0];
                this.inkColors[1] = this.inkColorFromSetting[this.mInkEffectColor][1];
                this.inkColors[2] = this.inkColorFromSetting[this.mInkEffectColor][2];
            } else {
                Log.d(this.TAG, "ModeType.RIPPLE_LIGHT");
                this.effectType = 0;
            }
            if (isCheckEffectChange && this.preEffectType != this.effectType) {
                Log.d(this.TAG, "effectType = " + this.effectType + ", preEffectType = " + this.preEffectType);
                this.mEffectChangeCheckQueue.offer(true);
                this.mParent.setRenderMode(1);
            }
            this.preEffectType = this.effectType;
        } else {
            this.mInkEffectColor = 0;
            this.effectType = 0;
        }
    }

    public synchronized void setResourcesBitmap(Bitmap bmp) {
        Log.d(this.TAG, "setResourcesBitmap");
        this.bitmapEnvironmentBG = bmp;
    }

    public void setBackground(Bitmap bmp) {
        Log.d(this.TAG, "setBackground");
        if (bmp == null) {
            Log.d(this.TAG, "bmp is null");
            return;
        }
        Log.d(this.TAG, "TABLET_MODE, getCenterCropBitmap bitmapPortraitBG, bitmapLandscapeBG");
        this.bitmapPortraitBG = getCenterCropBitmap(bmp, Math.min(this.windowWidth, this.windowHeight), Math.max(this.windowWidth, this.windowHeight));
        this.bitmapLandscapeBG = getCenterCropBitmap(bmp, Math.max(this.windowWidth, this.windowHeight), Math.min(this.windowWidth, this.windowHeight));
    }

    public synchronized void loadBGTexture() {
        Log.d(this.TAG, "loadBGTexture");
        if (this.bitmapPortraitBG != null) {
            Log.d(this.TAG, "transferBitmap bitmapPortraitBG");
            native_LoadTextures(this.bitmapPortraitBG, 1);
            this.bitmapPortraitBG = null;
        } else {
            Log.d(this.TAG, "bitmapPortraitBG is null");
        }
        if (this.bitmapLandscapeBG != null) {
            Log.d(this.TAG, "transferBitmap bitmapLandscapeBG");
            native_LoadTextures(this.bitmapLandscapeBG, 2);
            this.bitmapLandscapeBG = null;
        } else {
            Log.d(this.TAG, "bitmapLandscapeBG is null");
        }
    }

    public synchronized void loadEnvironmentTexture() {
        Log.d(this.TAG, "loadEnvironmentTexture");
        if (this.bitmapEnvironmentBG != null) {
            Log.d(this.TAG, "transferWaterBitmap");
            native_LoadTextures(this.bitmapEnvironmentBG, 0);
            this.bitmapEnvironmentBG.recycle();
            this.bitmapEnvironmentBG = null;
        } else {
            Log.d(this.TAG, "bitmapEnvironmentBG is null");
        }
    }

    public void native_LoadTextures(Bitmap bmp, int type) {
    }

    public Bitmap getCenterCropBitmap(Bitmap bitmap, int width, int height) {
        Log.d(this.TAG, "getCenterCropBitmap()");
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        float ratio = (float) width / height;
        float bitmapRatio = (float) bitmapWidth / bitmapHeight;
        if (bitmapRatio > ratio) {
            Log.d(this.TAG, "bmp is horizontally");
            int targetWidth = (int) (bitmapHeight * ratio);
            return Bitmap.createBitmap(bitmap, (bitmapWidth - targetWidth) / 2, 0, targetWidth, bitmapHeight);
        }
        Log.d(this.TAG, "bmp is vertically");
        int targetHeight = (int) (bitmapWidth / ratio);
        return Bitmap.createBitmap(bitmap, 0, (bitmapHeight - targetHeight) / 2, bitmapWidth, targetHeight);
    }

    public synchronized void changeBackground(Bitmap bmp) {
        Log.i(this.TAG, "changeBackground()");
        if (bmp == null) {
            Log.i(this.TAG, "bmp == null");
        } else {
            setBackground(bmp);
            this.mBgChangeCheckQueue.offer(true);
            if (!this.isIndigoDiffusion) {
                setRippleVersion(true);
            }
            this.mParent.setRenderMode(1);
        }
    }

    public synchronized void checkEffectChange() {
        if (!this.mEffectChangeCheckQueue.isEmpty()) {
            Log.d(this.TAG, "Change Effect Type and loadShaderSetting()");
            loadShaderSetting();
            while (!this.mEffectChangeCheckQueue.isEmpty()) {
                this.mEffectChangeCheckQueue.remove();
            }
        }
    }

    public synchronized void checkBackground() {
        if (!this.mBgChangeCheckQueue.isEmpty()) {
            Log.d(this.TAG, "Change opengl BG Texture");
            loadBGTexture();
            while (!this.mBgChangeCheckQueue.isEmpty()) {
                this.mBgChangeCheckQueue.remove();
            }
        }
    }

    public void show() {
        Log.d(this.TAG, "show");
        if (!this.isIndigoDiffusion) {
            setRippleVersion(true);
        }
    }

    public void showUnlockAffordance(long startDelay, Rect rect) {
        Log.d(this.TAG, "showUnlockAffordance()");
        this.defaultX = rect.centerX();
        this.defaultY = rect.centerY();
        if (this.mDefaultRunnable == null) {
            Log.d(this.TAG, "mDefaultRunnable,  new Runnable()!!!");
            this.mDefaultRunnable = new Runnable() { // from class: com.samsung.android.visualeffect.lock.common.GLTextureViewRendererForRippleType.1
                @Override // java.lang.Runnable
                public void run() {
                    if (GLTextureViewRendererForRippleType.this.drawCount > 0) {
                        if (GLTextureViewRendererForRippleType.this.mLandscape) {
                            GLTextureViewRendererForRippleType.this.glX = (GLTextureViewRendererForRippleType.this.defaultX - (GLTextureViewRendererForRippleType.this.mScreenWidth / 2.0f)) - GLTextureViewRendererForRippleType.this.XRatioAdjustLandscape;
                            GLTextureViewRendererForRippleType.this.glX *= GLTextureViewRendererForRippleType.this.XRatioForLandscape / GLTextureViewRendererForRippleType.this.mScreenWidth;
                            GLTextureViewRendererForRippleType.this.glY = (GLTextureViewRendererForRippleType.this.mScreenHeight - GLTextureViewRendererForRippleType.this.defaultY) - (GLTextureViewRendererForRippleType.this.mScreenHeight / 2.0f);
                            GLTextureViewRendererForRippleType.this.glY = (-GLTextureViewRendererForRippleType.this.glY) * (GLTextureViewRendererForRippleType.this.YRatioForLandscape / GLTextureViewRendererForRippleType.this.mScreenHeight);
                        } else {
                            GLTextureViewRendererForRippleType.this.glX = (GLTextureViewRendererForRippleType.this.defaultX - (GLTextureViewRendererForRippleType.this.mScreenWidth / 2.0f)) - GLTextureViewRendererForRippleType.this.XRatioAdjustPortrait;
                            GLTextureViewRendererForRippleType.this.glX *= GLTextureViewRendererForRippleType.this.XRatioForPortrait / GLTextureViewRendererForRippleType.this.mScreenWidth;
                            GLTextureViewRendererForRippleType.this.glY = (GLTextureViewRendererForRippleType.this.mScreenHeight - GLTextureViewRendererForRippleType.this.defaultY) - (GLTextureViewRendererForRippleType.this.mScreenHeight / 2.0f);
                            GLTextureViewRendererForRippleType.this.glY = (-GLTextureViewRendererForRippleType.this.glY) * (GLTextureViewRendererForRippleType.this.YRatioForPortrait / GLTextureViewRendererForRippleType.this.mScreenHeight);
                        }
                        Log.d(GLTextureViewRendererForRippleType.this.TAG, "mDefaultRunnable run, and ripple()");
                        GLTextureViewRendererForRippleType.this.ripple(GLTextureViewRendererForRippleType.this.glY, GLTextureViewRendererForRippleType.this.glX, GLTextureViewRendererForRippleType.this.intensityForRipple * 4.0f);
                        if (GLTextureViewRendererForRippleType.this.isIndigoDiffusion) {
                            GLTextureViewRendererForRippleType.this.native_onTouch((int) GLTextureViewRendererForRippleType.this.defaultX, (int) GLTextureViewRendererForRippleType.this.defaultY, 0, 1.0f);
                        }
                        GLTextureViewRendererForRippleType.this.mDefaultRunnable = null;
                        return;
                    }
                    Log.d(GLTextureViewRendererForRippleType.this.TAG, "Because drawCount = " + GLTextureViewRendererForRippleType.this.drawCount + ", mDefaultRunnable, postDelayed()!!!");
                    GLTextureViewRendererForRippleType.this.mParent.postDelayed(GLTextureViewRendererForRippleType.this.mDefaultRunnable, 250L);
                }
            };
        }
        Log.d(this.TAG, "mDefaultRunnable, postDelayed()!!!");
        this.mParent.postDelayed(this.mDefaultRunnable, startDelay);
    }

    @Override // com.samsung.android.visualeffect.common.GLTextureView.Renderer
    public void onDestroy() {
        Log.d(this.TAG, "destroyed");
    }
}