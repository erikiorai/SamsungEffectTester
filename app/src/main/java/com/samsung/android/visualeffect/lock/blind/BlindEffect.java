package com.samsung.android.visualeffect.lock.blind;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.BaseInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.aj.effect.QuintEaseOut;
import com.samsung.android.visualeffect.EffectDataObj;
import com.samsung.android.visualeffect.IEffectListener;
import com.samsung.android.visualeffect.IEffectView;
import com.samsung.android.visualeffect.common.ImageViewBlended;

import java.util.HashMap;

class QuadEaseIn extends BaseInterpolator {
    public QuadEaseIn() {
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float t) {
        return in(t);
    }

    private float in(float t) {
        return t * t;
    }
}

/* loaded from: classes.dex */
public class BlindEffect extends FrameLayout implements IEffectView {
    private final boolean DBG;
    private final int DOWN_ANIMATION_DURATION;
    private final String TAG;
    private final int TOTAL_COLUMN_LANDSCAPE;
    private final int TOTAL_COLUMN_PORTRAIT;
    private int UNLOCK_ALPHA_ANIMATION_DURATION;
    private final int UP_ANIMATION_DURATION;
    private Runnable affordanceRunnableDown;
    private Runnable affordanceRunnableUp;
    float affordanceX;
    float affordanceY;
    private float animationValue;
    private Bitmap bitmapLandscape;
    private Bitmap bitmapPortrait;
    private Blind[] blindLandscape;
    private Blind[] blindPortrait;
    private ColorMatrix cm;
    private float currentX;
    private float currentY;
    private DisplayMetrics dm;
    private ValueAnimator downAnimator;
    private boolean isInitialized;
    private boolean isLandscape;
    private float lastX;
    private float lastY;
    private FrameLayout layoutLandscape;
    private FrameLayout layoutPortrait;
    private Bitmap light;
    private ImageViewBlended lightView;
    private int longWidth;
    private Context mContext;
    private ValueAnimator moveAnimator;
    private float point2X;
    private float pointX;
    private float pointY;
    private float pushAnimationMax;
    private float pushAnimationMin;
    private int shortWidth;
    private int stageHeight;
    private int stageWidth;
    private int totalColumn;
    private ValueAnimator unlockAlphaAnimator;
    private ValueAnimator upAnimator;

    static /* synthetic */ float chang1(BlindEffect x0, float x1) {
        float f = x0.pointX + x1;
        x0.pointX = f;
        return f;
    }

    static /* synthetic */ float chang3(BlindEffect x0, float x1) {
        float f = x0.point2X + x1;
        x0.point2X = f;
        return f;
    }

    static /* synthetic */ float chang2(BlindEffect x0, float x1) {
        float f = x0.pointY + x1;
        x0.pointY = f;
        return f;
    }

    public BlindEffect(Context context) {
        super(context);
        this.TAG = "BlindEffect";
        this.DBG = true;
        this.light = null;
        this.pushAnimationMin = 0.0f;
        this.pointX = -1.0f;
        this.pointY = -1.0f;
        this.point2X = -1.0f;
        this.dm = new DisplayMetrics();
        WindowManager mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.getDefaultDisplay().getRealMetrics(dm);
        this.totalColumn = 0;
        this.isLandscape = true;
        this.isInitialized = false;
        this.UNLOCK_ALPHA_ANIMATION_DURATION = 500;
        this.DOWN_ANIMATION_DURATION = 200;
        this.UP_ANIMATION_DURATION = 1000;
        this.TOTAL_COLUMN_LANDSCAPE = 40;
        this.TOTAL_COLUMN_PORTRAIT = 25;
        constructor(context);
    }

    public BlindEffect(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.TAG = "BlindEffect";
        this.DBG = true;
        this.light = null;
        this.pushAnimationMin = 0.0f;
        this.pointX = -1.0f;
        this.pointY = -1.0f;
        this.point2X = -1.0f;
        this.dm = new DisplayMetrics();
        WindowManager mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.getDefaultDisplay().getRealMetrics(dm);
        this.totalColumn = 0;
        this.isLandscape = true;
        this.isInitialized = false;
        this.UNLOCK_ALPHA_ANIMATION_DURATION = 500;
        this.DOWN_ANIMATION_DURATION = 200;
        this.UP_ANIMATION_DURATION = 1000;
        this.TOTAL_COLUMN_LANDSCAPE = 40;
        this.TOTAL_COLUMN_PORTRAIT = 25;
        constructor(context);
    }

    public BlindEffect(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.TAG = "BlindEffect";
        this.DBG = true;
        this.light = null;
        this.pushAnimationMin = 0.0f;
        this.pointX = -1.0f;
        this.pointY = -1.0f;
        this.point2X = -1.0f;
        this.dm = new DisplayMetrics();
        WindowManager mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.getDefaultDisplay().getRealMetrics(dm);
        this.totalColumn = 0;
        this.isLandscape = true;
        this.isInitialized = false;
        this.UNLOCK_ALPHA_ANIMATION_DURATION = 500;
        this.DOWN_ANIMATION_DURATION = 200;
        this.UP_ANIMATION_DURATION = 1000;
        this.TOTAL_COLUMN_LANDSCAPE = 40;
        this.TOTAL_COLUMN_PORTRAIT = 25;
        constructor(context);
    }

    private void constructor(Context context) {
        Log.d("BlindEffect", "Constructor");
        this.mContext = context;
        this.stageWidth = this.dm.widthPixels;
        this.stageHeight = this.dm.heightPixels;
        this.longWidth = Math.max(this.stageWidth, this.stageHeight);
        this.shortWidth = Math.min(this.stageWidth, this.stageHeight);
    }

    private void setBackgroundImage(Bitmap backgroundImage) {
        Log.d("BlindEffect", "setBackgroundImage : " + backgroundImage.getWidth() + " x " + backgroundImage.getHeight());
        this.bitmapLandscape = getScaledBitmap(backgroundImage, this.longWidth, this.shortWidth);
        this.bitmapPortrait = getScaledBitmap(backgroundImage, this.shortWidth, this.longWidth);
        if (this.isInitialized) {
            backgroundImageUpdate();
        }
    }

    private void setUnlockDelayDuration(long time) {
        this.UNLOCK_ALPHA_ANIMATION_DURATION = (int) time;
    }

    private Bitmap getScaledBitmap(Bitmap bitmap, int width, int height) {
        Bitmap finalBitmap;
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        float ratio =  (float) width / height;
        float bitmapRatio = (float) bitmapWidth / bitmapHeight;
        if (bitmapRatio > ratio) {
            int targetWidth = (int) (bitmapHeight * ratio);
            finalBitmap = Bitmap.createBitmap(bitmap, (bitmapWidth - targetWidth) / 2, 0, targetWidth, bitmapHeight);
        } else {
            int targetHeight = (int) (bitmapWidth / ratio);
            finalBitmap = Bitmap.createBitmap(bitmap, 0, (bitmapHeight - targetHeight) / 2, bitmapWidth, targetHeight);
        }
        return Bitmap.createScaledBitmap(finalBitmap, width, height, true);
    }

    private void setLightImage(Bitmap lightImage) {
        Log.d("BlindEffect", "setLightImage : " + lightImage.getWidth() + " x " + lightImage.getHeight());
        this.light = lightImage;
    }

    private void blindEffectInit() {
        Log.d("BlindEffect", "blindEffectInit");
        this.cm = new ColorMatrix();
        this.layoutLandscape = new FrameLayout(this.mContext);
        this.layoutLandscape.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        addView(this.layoutLandscape);
        this.layoutPortrait = new FrameLayout(this.mContext);
        this.layoutPortrait.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        addView(this.layoutPortrait);
        resetOrientation(true);
        setBlind();
        setLight();
        setAnimator();
        setAffordanceRunnable();
        this.isInitialized = true;
    }

    private void resetOrientation(boolean isWindowFocused) {
        clearEffect();
        this.stageWidth = this.dm.widthPixels;
        this.stageHeight = this.dm.heightPixels;
        this.isLandscape = this.stageWidth > this.stageHeight;
        if (isWindowFocused) {
            Log.d("BlindEffect", "resetOrientation : isLandscape = " + this.isLandscape + ", " + this.stageWidth + " x " + this.stageHeight);
        }
        this.totalColumn = this.isLandscape ? 40 : 25;
        if (this.isLandscape) {
            this.layoutLandscape.setVisibility(View.VISIBLE);
            this.layoutPortrait.setVisibility(View.GONE);
        } else {
            this.layoutLandscape.setVisibility(View.GONE);
            this.layoutPortrait.setVisibility(View.VISIBLE);
        }
    }

    private void setAnimator() {
        this.downAnimator = ValueAnimator.ofFloat(0.3f, 1.0f);
        this.downAnimator.setInterpolator(new QuintEaseOut());
        this.downAnimator.setDuration(200L);
        this.downAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.samsung.android.visualeffect.lock.blind.BlindEffect.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                BlindEffect.this.animationValue = (((Float) animation.getAnimatedValue()).floatValue() * (1.0f - BlindEffect.this.pushAnimationMin)) + BlindEffect.this.pushAnimationMin;
                BlindEffect.this.lightView.setAlpha(BlindEffect.this.animationValue * 0.15f);
            }
        });
        this.upAnimator = ValueAnimator.ofFloat(1.0f, 0.0f);
        this.upAnimator.setInterpolator(new QuintEaseOut());
        this.upAnimator.setDuration(1000L);
        this.upAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.samsung.android.visualeffect.lock.blind.BlindEffect.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = ((Float) animation.getAnimatedValue()).floatValue();
                BlindEffect.this.animationValue = BlindEffect.this.pushAnimationMax * value;
                BlindEffect.this.lightView.setAlpha(BlindEffect.this.animationValue * 0.15f);
                BlindEffect.this.pointX -= (1.0f - BlindEffect.this.animationValue) * 50.0f;
                BlindEffect.this.point2X += (1.0f - BlindEffect.this.animationValue) * 50.0f;
            }
        });
        this.upAnimator.addListener(new Animator.AnimatorListener() { // from class: com.samsung.android.visualeffect.lock.blind.BlindEffect.3
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                BlindEffect.this.cancelAllAnimator();
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
            }
        });
        this.moveAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.moveAnimator.setInterpolator(new LinearInterpolator());
        this.moveAnimator.setRepeatCount(-1);
        this.moveAnimator.setDuration(3600000L);
        this.moveAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.samsung.android.visualeffect.lock.blind.BlindEffect.4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                BlindEffect.chang1(BlindEffect.this, (BlindEffect.this.currentX - BlindEffect.this.pointX) * 0.17f);
                BlindEffect.chang2(BlindEffect.this, (BlindEffect.this.currentY - BlindEffect.this.pointY) * 0.17f);
                BlindEffect.chang3(BlindEffect.this, (BlindEffect.this.currentX - BlindEffect.this.point2X) * 0.17f);
                for (int i = 0; i < BlindEffect.this.totalColumn; i++) {
                    BlindEffect.this.setScale(i);
                }
            }
        });
        this.unlockAlphaAnimator = ValueAnimator.ofFloat(1.0f, 0.0f);
        this.unlockAlphaAnimator.setInterpolator(new QuadEaseIn());
        this.unlockAlphaAnimator.setDuration(this.UNLOCK_ALPHA_ANIMATION_DURATION);
        this.unlockAlphaAnimator.addListener(new Animator.AnimatorListener() { // from class: com.samsung.android.visualeffect.lock.blind.BlindEffect.5
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                BlindEffect.this.unlockFinished();
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setScale(int i) {
        float scale;
        float distance = getDistance(0.0f, this.pointX, i);
        float distance2 = this.upAnimator.isRunning() ? getDistance(0.0f, this.point2X, i) : 0.0f;
        if (distance2 > distance) {
            distance = distance2;
        }
        if (!this.upAnimator.isRunning()) {
            this.lightView.setX(this.pointX - (this.light.getWidth() / 2.0f));
            this.lightView.setY(this.currentY - (this.light.getHeight() / 2.0f));
        }
        Blind[] blind = this.isLandscape ? this.blindLandscape : this.blindPortrait;
        if (this.isLandscape) {
            scale = 1.0f + (this.animationValue * distance);
        } else {
            scale = 1.0f + (this.animationValue * distance * 0.625f);
        }
        blind[i].setScaleX(scale);
        blind[i].setScaleY(scale);
        blind[i].setColorFilter((ColorFilter) null);
        setBrightness(this.cm, this.animationValue * distance);
        blind[i].setColorFilter(new ColorMatrixColorFilter(this.cm));
    }

    private float getDistance(float dis, float point, int i) {
        float dis2 = this.isLandscape ? this.blindLandscape[i].getMidPoint() - point : this.blindPortrait[i].getMidPoint() - point;
        float brightRange = this.isLandscape ? 8.0f : 5.0f;
        if (dis2 < 0.0f) {
            dis2 *= -1.0f;
        }
        float dis3 = ((this.stageWidth / brightRange) - dis2) / 1000.0f;
        if (dis3 < 0.0f) {
            return 0.0f;
        }
        return dis3;
    }

    private void setBrightness(ColorMatrix cm, float scale) {
        float brightnessScale = scale * 200.0f;
        cm.set(new float[]{1.0f, 0.0f, 0.0f, 0.0f, brightnessScale, 0.0f, 1.0f, 0.0f, 0.0f, brightnessScale, 0.0f, 0.0f, 1.0f, 0.0f, brightnessScale, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
    }

    private void setBlind() {
        this.blindLandscape = new Blind[40];
        int blindX = 0;
        for (int i = 0; i < 40; i++) {
            int nextX = getBlindX(i + 1, true);
            int blindWidth = nextX - blindX;
            Bitmap pieceBitmap = Bitmap.createBitmap(this.bitmapLandscape, blindX, 0, blindWidth, this.shortWidth);
            Blind blind = new Blind(this.mContext, pieceBitmap, blindX, blindWidth);
            this.blindLandscape[i] = blind;
            blind.setX(blindX);
            blind.setScaleType(ImageView.ScaleType.FIT_XY);
            this.layoutLandscape.addView(blind, blindWidth, this.shortWidth);
            blindX = nextX;
        }
        this.blindPortrait = new Blind[25];
        int blindX2 = 0;
        for (int i2 = 0; i2 < 25; i2++) {
            int nextX2 = getBlindX(i2 + 1, false);
            int blindWidth2 = nextX2 - blindX2;
            Bitmap pieceBitmap2 = Bitmap.createBitmap(this.bitmapPortrait, blindX2, 0, blindWidth2, this.longWidth);
            Blind blind2 = new Blind(this.mContext, pieceBitmap2, blindX2, blindWidth2);
            this.blindPortrait[i2] = blind2;
            blind2.setX(blindX2);
            blind2.setScaleType(ImageView.ScaleType.FIT_XY);
            this.layoutPortrait.addView(blind2, blindWidth2, this.longWidth);
            blindX2 = nextX2;
        }
        this.bitmapLandscape.recycle();
        this.bitmapPortrait.recycle();
        this.bitmapLandscape = null;
        this.bitmapPortrait = null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void startAffordanceRunnableUp(long startDelay) {
        postDelayed(this.affordanceRunnableUp, startDelay);
    }

    private void setAffordanceRunnable() {
        this.affordanceRunnableDown = new Runnable() { // from class: com.samsung.android.visualeffect.lock.blind.BlindEffect.6
            @Override // java.lang.Runnable
            public void run() {
                Log.d("BlindEffect", "affordanceRunnableDown");
                BlindEffect.this.playDownAnimator(BlindEffect.this.affordanceX, BlindEffect.this.affordanceY);
                BlindEffect.this.startAffordanceRunnableUp(100L);
            }
        };
        this.affordanceRunnableUp = new Runnable() { // from class: com.samsung.android.visualeffect.lock.blind.BlindEffect.7
            @Override // java.lang.Runnable
            public void run() {
                Log.d("BlindEffect", "affordanceRunnableUp");
                BlindEffect.this.playUpAnimator();
            }
        };
    }

    private void backgroundImageUpdate() {
        Log.d("BlindEffect", "backgroundImageUpdate");
        for (int i = 0; i < 40; i++) {
            Blind blind = this.blindLandscape[i];
            Bitmap pieceBitmap = Bitmap.createBitmap(this.bitmapLandscape, blind.getBlindX(), 0, blind.getBlindWidth(), this.shortWidth);
            blind.changeBitmap(pieceBitmap);
        }
        for (int i2 = 0; i2 < 25; i2++) {
            Blind blind2 = this.blindPortrait[i2];
            Bitmap pieceBitmap2 = Bitmap.createBitmap(this.bitmapPortrait, blind2.getBlindX(), 0, blind2.getBlindWidth(), this.longWidth);
            blind2.changeBitmap(pieceBitmap2);
        }
        this.bitmapLandscape.recycle();
        this.bitmapPortrait.recycle();
        this.bitmapLandscape = null;
        this.bitmapPortrait = null;
    }

    private void setLight() {
        this.lightView = new ImageViewBlended(this.mContext);
        this.light = Bitmap.createScaledBitmap(this.light, this.stageWidth / 2, this.stageWidth / 2, false);
        this.lightView.setImageBitmap(this.light);
        this.lightView.setAlpha(0.0f);
        addView(this.lightView, this.light.getWidth(), this.light.getHeight());
    }

    private int getBlindX(int i, boolean isLandscape) {
        int column = isLandscape ? 40 : 25;
        int width = isLandscape ? this.longWidth : this.shortWidth;
        int x = Math.round((i * width) / column);
        return x;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playDownAnimator(float x, float y) {
        this.currentX = x;
        this.currentY = y;
        this.lastX = x;
        this.lastY = y;
        if (this.animationValue < 0.3d) {
            this.pointX = x;
            this.pointY = y;
            this.point2X = x;
        }
        clearEffect();
        cancelAllAnimator();
        this.pushAnimationMin = this.animationValue;
        this.downAnimator.start();
        this.moveAnimator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void playUpAnimator() {
        if (!this.upAnimator.isRunning()) {
            cancelAnimator(this.downAnimator);
            cancelAnimator(this.upAnimator);
            this.pushAnimationMax = this.animationValue;
            this.upAnimator.start();
        }
    }

    private void unlockEffect() {
        Log.d("BlindEffect", "unlockEffect");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unlockFinished() {
        Log.d("BlindEffect", "unlockFinished");
    }

    private void clearEffect() {
        cancelAllAnimator();
        if (this.blindLandscape != null && this.blindPortrait != null) {
            this.layoutLandscape.setAlpha(1.0f);
            this.layoutPortrait.setAlpha(1.0f);
            for (int i = 0; i < 40; i++) {
                this.blindLandscape[i].setScaleX(1.0f);
                this.blindLandscape[i].setScaleY(1.0f);
                this.blindLandscape[i].setColorFilter((ColorFilter) null);
            }
            for (int i2 = 0; i2 < 25; i2++) {
                this.blindPortrait[i2].setScaleX(1.0f);
                this.blindPortrait[i2].setScaleY(1.0f);
                this.blindPortrait[i2].setColorFilter((ColorFilter) null);
            }
        }
        initAnimationValue();
    }

    private void initAnimationValue() {
        this.animationValue = 0.0f;
        if (this.lightView != null) {
            this.lightView.setAlpha(0.0f);
        }
    }

    private void show() {
        Log.d("BlindEffect", "show (BlindEffect)");
        clearEffect();
        resetOrientation(true);
    }

    private void destroy() {
        Log.d("BlindEffect", "destroy");
        cancelAllAnimator();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void cancelAllAnimator() {
        cancelAnimator(this.downAnimator);
        cancelAnimator(this.upAnimator);
        cancelAnimator(this.moveAnimator);
    }

    private void cancelAnimator(Animator animator) {
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
    }

    private void showAffordanceEffect(long startDelay, Rect rect) {
        Log.d("BlindEffect", "showUnlockAffordance : " + rect.left + ", " + rect.right + ", " + rect.top + ", " + rect.bottom + ", startDelay : " + startDelay);
        this.affordanceX = rect.left + ((rect.right - rect.left) / 2);
        this.affordanceY = rect.top + ((rect.bottom - rect.top) / 2);
        removeCallbacks(this.affordanceRunnableDown);
        removeCallbacks(this.affordanceRunnableUp);
        postDelayed(this.affordanceRunnableDown, startDelay);
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void init(EffectDataObj data) {
        blindEffectInit();
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void reInit(EffectDataObj data) {
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void handleCustomEvent(int cmd, HashMap<?, ?> params) {
        if (cmd == 1) {
            showAffordanceEffect(((Long) params.get("StartDelay")).longValue(), (Rect) params.get("Rect"));
        } else if (cmd == 2) {
            if (params.get("unlockDelay") != null) {
                setUnlockDelayDuration(((Long) params.get("unlockDelay")).longValue());
            } else if (params.get("unlock") != null) {
                unlockEffect();
            }
        } else if (cmd == 0) {
            if (params.get("background") != null) {
                setBackgroundImage((Bitmap) params.get("background"));
            }
            if (params.get("light") != null) {
                setLightImage((Bitmap) params.get("light"));
            }
        } else if (cmd == 99) {
            if (params.get("onConfigurationChanged") != null) {
                resetOrientation(((Boolean) params.get("onConfigurationChanged")).booleanValue());
            } else if (params.get("show") != null) {
                show();
            } else if (params.get("destroy") != null) {
                destroy();
            } else if (params.get("initAnimationValue") != null) {
                initAnimationValue();
            }
        }
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void setListener(IEffectListener listener) {
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void removeListener() {
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void clearScreen() {
        clearEffect();
    }

    @Override // com.samsung.android.visualeffect.IEffectView
    public void handleTouchEvent(MotionEvent event, View view) {
        float x = event.getRawX();
        float y = event.getRawY();
        if (event.getActionMasked() == 0) {
            Log.d("BlindEffect", "handleTouchEvent : ACTION_DOWN");
            removeCallbacks(this.affordanceRunnableDown);
            removeCallbacks(this.affordanceRunnableUp);
            playDownAnimator(x, y);
        } else if (event.getActionMasked() == 2 && event.getActionIndex() == 0) {
            float dX = x - this.lastX;
            float dY = y - this.lastY;
            this.currentX += dX;
            this.currentY += dY;
            this.lastX = x;
            this.lastY = y;
        } else if (event.getActionMasked() == 1 || event.getActionMasked() == 3) {
            Log.d("BlindEffect", "handleTouchEvent : ACTION_UP || ACTION_CANCEL");
            playUpAnimator();
        }
    }
}