package com.android.keyguard.sec.festivaleffect.unlockeffect.summer;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.widget.ImageView;

import com.aj.effect.R;
import com.android.keyguard.sec.festivaleffect.unlockeffect.FestivalEffect;

import java.util.Random;

public class SummerEffect extends FestivalEffect {
    protected static final String TAG = "SummerEffect";
    private ValueAnimator alpha;
    private ValueAnimator alphaBack;
    private ValueAnimator alphaBlank;
    private float dk;
    private float dm;
    private float dn;
    private float dx;
    private int m;
    private Context mContext;
    private boolean mHasAddImage;
    private ImageView mImageEffect;
    private ImageView mImageTouch;
    private ImageView mImageTouch2;
    private ImageView mImageTouch3;
    private float mScreenAdjust;
    private float maxRand;
    private int[] resId;
    private int[] resIdTouch;
    private ValueAnimator rotation;
    private ValueAnimator scaleX;
    private ValueAnimator scaleXBack;
    private ValueAnimator scaleXBlank;
    private ValueAnimator scaleY;
    private ValueAnimator scaleYBack;
    private ValueAnimator scaleYBlank;
    private ValueAnimator touchAlpha;
    private ValueAnimator touchAlpha2;
    private ValueAnimator touchAlpha3;
    private ValueAnimator touchScaleX;
    private ValueAnimator touchScaleY;

    public SummerEffect(Context context) {
        super(context);
        this.maxRand = 200.0f;
        this.dm = 0.0f;
        this.dn = 0.0f;
        this.dk = 1.0f;
        this.m = 0;
        this.resId = new int[]{R.drawable.unlock_summer_particle_01, R.drawable.unlock_summer_particle_02, R.drawable.unlock_summer_particle_03, R.drawable.unlock_summer_particle_04, R.drawable.unlock_summer_particle_05, R.drawable.unlock_summer_particle_06};
        this.resIdTouch = new int[]{R.drawable.unlock_summer_touch_01, R.drawable.unlock_summer_touch_02, R.drawable.unlock_summer_touch_03};
        this.mHasAddImage = false;
        this.mContext = context;
        this.mScreenAdjust = this.mContext.getResources().getDisplayMetrics().density;
        init();
    }

    public void add(float f, float f2) {
        AnimatorSet animatorSet = new AnimatorSet();
        this.mImageTouch = new ImageView(this.mContext);
        this.mImageTouch.setImageResource(this.resIdTouch[0]);
        this.mImageTouch2 = new ImageView(this.mContext);
        this.mImageTouch2.setImageResource(this.resIdTouch[1]);
        this.mImageTouch3 = new ImageView(this.mContext);
        this.mImageTouch3.setImageResource(this.resIdTouch[2]);
        addView(this.mImageTouch, -2, -2);
        addView(this.mImageTouch2, -2, -2);
        addView(this.mImageTouch3, -2, -2);
        this.mImageTouch.setX(f - (this.mScreenAdjust * 92.0f));
        this.mImageTouch.setY(f2 - (this.mScreenAdjust * 92.0f));
        this.mImageTouch2.setX(f - (14.0f * this.mScreenAdjust));
        this.mImageTouch2.setY(f2 - (13.0f * this.mScreenAdjust));
        this.mImageTouch3.setX(f - (29.0f * this.mScreenAdjust));
        this.mImageTouch3.setY(f2 - (25.0f * this.mScreenAdjust));
        this.touchScaleX = ObjectAnimator.ofFloat(this.mImageTouch, "ScaleX", 0.3f, 0.95f);
        this.touchScaleY = ObjectAnimator.ofFloat(this.mImageTouch, "ScaleY", 0.3f, 0.95f);
        this.touchScaleX.setDuration(2130L);
        this.touchScaleY.setDuration(2130L);
        this.touchAlpha = ObjectAnimator.ofFloat(this.mImageTouch, "alpha", 0.0f, 1.0f);
        this.touchAlpha.setDuration(1800L);
        animatorSet.play(this.touchScaleX).with(this.touchScaleY);
        this.touchAlpha2 = ObjectAnimator.ofFloat(this.mImageTouch2, "alpha", 0.0f, 1.0f);
        this.touchAlpha2.setDuration(330L);
        animatorSet.play(this.touchAlpha2);
        this.touchAlpha3 = ObjectAnimator.ofFloat(this.mImageTouch3, "alpha", 0.0f, 1.0f);
        this.touchAlpha3.setDuration(330L);
        animatorSet.play(this.touchAlpha3);
        animatorSet.start();
        this.mHasAddImage = true;
    }

    public void clearEffect() {
        this.dm = 0.0f;
        this.dn = 0.0f;
        removeView(this.mImageTouch);
        removeView(this.mImageTouch2);
        removeView(this.mImageTouch3);
        removeAllViews();
        this.mHasAddImage = false;
    }

    public void destroy() {
    }

    public void init() {
    }

    public void move(float f, float f2) {
        AnimatorSet animatorSet = new AnimatorSet();
        this.dx = (float) (this.maxRand * Math.random());
        if (!this.mHasAddImage) {
            add(f, f2);
        }
        this.mImageTouch.setX(f - (92.0f * this.mScreenAdjust));
        this.mImageTouch.setY(f2 - (92.0f * this.mScreenAdjust));
        this.dm += 2.0f;
        this.dn += 2.0f;
        if (this.dm >= 63.0f) {
            this.dm = 63.0f;
        }
        if (this.dn >= 81.0f) {
            this.dn = 81.0f;
        }
        this.mImageTouch2.setX((f - (this.dm * this.mScreenAdjust)) - (14.0f * this.mScreenAdjust));
        this.mImageTouch2.setY((f2 - (this.dm * this.mScreenAdjust)) - (13.0f * this.mScreenAdjust));
        this.mImageTouch3.setX((f - (this.dn * this.mScreenAdjust)) - (29.0f * this.mScreenAdjust));
        this.mImageTouch3.setY((f2 - (this.dn * this.mScreenAdjust)) - (25.0f * this.mScreenAdjust));
        this.m = new Random().nextInt(11);
        if (this.m < 6) {
            this.mImageEffect = new ImageView(this.mContext);
            this.mImageEffect.setImageResource(this.resId[this.m]);
            addView(this.mImageEffect, -2, -2);
            if (this.m == 0 || this.m == 1) {
                this.dk = (float) (0.6000000238418579d + (0.1d * new Random().nextInt(5)));
                this.mImageEffect.setX(f - this.dx);
                this.mImageEffect.setY(this.dx + f2);
                this.scaleX = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleX", 0.0f, this.dk);
                this.scaleY = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleY", 0.0f, this.dk);
                this.scaleX.setDuration(125L);
                this.scaleY.setDuration(125L);
                this.scaleXBlank = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleX", this.dk, this.dk);
                this.scaleYBlank = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleY", this.dk, this.dk);
                this.scaleXBlank.setDuration(375L);
                this.scaleYBlank.setDuration(375L);
                this.scaleXBack = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleX", this.dk, 0.55f);
                this.scaleYBack = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleY", this.dk, 0.55f);
                this.scaleXBack.setDuration(500L);
                this.scaleYBack.setDuration(500L);
                this.alpha = ObjectAnimator.ofFloat(this.mImageEffect, "alpha", 0.0f, 1.0f);
                this.alpha.setDuration(250L);
                this.alphaBlank = ObjectAnimator.ofFloat(this.mImageEffect, "alpha", 1.0f, 1.0f);
                this.alphaBlank.setDuration(250L);
                this.alphaBack = ObjectAnimator.ofFloat(this.mImageEffect, "alpha", 1.0f, 0.0f);
                this.alphaBack.setDuration(500L);
                this.rotation = ObjectAnimator.ofFloat(this.mImageEffect, "rotation", 0.0f, 359.0f);
                this.rotation.setDuration(1000L);
                animatorSet.play(this.scaleX).with(this.scaleY).with(this.alpha).with(this.rotation);
                animatorSet.play(this.scaleXBlank).with(this.scaleYBlank).after(this.scaleX);
                animatorSet.play(this.scaleXBack).with(this.scaleYBack).after(this.scaleXBlank);
                animatorSet.play(this.alphaBlank).after(this.alpha);
                animatorSet.play(this.alphaBack).after(this.alphaBlank);
            } else if (this.m == 2 || this.m == 3) {
                this.dk = (float) (0.699999988079071d + (0.1d * new Random().nextInt(4)));
                this.mImageEffect.setX(f - this.dx);
                this.mImageEffect.setY(this.dx + f2);
                this.scaleX = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleX", 0.0f, this.dk);
                this.scaleY = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleY", 0.0f, this.dk);
                this.scaleX.setDuration(125L);
                this.scaleY.setDuration(125L);
                this.scaleXBlank = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleX", this.dk, this.dk);
                this.scaleYBlank = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleY", this.dk, this.dk);
                this.scaleXBlank.setDuration(375L);
                this.scaleYBlank.setDuration(375L);
                this.scaleXBack = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleX", this.dk, 0.55f);
                this.scaleYBack = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleY", this.dk, 0.55f);
                this.scaleXBack.setDuration(500L);
                this.scaleYBack.setDuration(500L);
                this.alpha = ObjectAnimator.ofFloat(this.mImageEffect, "alpha", 0.0f, 1.0f);
                this.alpha.setDuration(250L);
                this.alphaBlank = ObjectAnimator.ofFloat(this.mImageEffect, "alpha", 1.0f, 1.0f);
                this.alphaBlank.setDuration(250L);
                this.alphaBack = ObjectAnimator.ofFloat(this.mImageEffect, "alpha", 1.0f, 0.0f);
                this.alphaBack.setDuration(500L);
                this.rotation = ObjectAnimator.ofFloat(this.mImageEffect, "rotation", 0.0f, 359.0f);
                this.rotation.setDuration(1000L);
                animatorSet.play(this.scaleX).with(this.scaleY).with(this.alpha).with(this.rotation);
                animatorSet.play(this.scaleXBlank).with(this.scaleYBlank).after(this.scaleX);
                animatorSet.play(this.scaleXBack).with(this.scaleYBack).after(this.scaleXBlank);
                animatorSet.play(this.alphaBlank).after(this.alpha);
                animatorSet.play(this.alphaBack).after(this.alphaBlank);
            } else if (this.m == 4) {
                this.dk = (float) (1.0d + (0.1d * new Random().nextInt(3)));
                this.mImageEffect.setX(f - this.dx);
                this.mImageEffect.setY(this.dx + f2);
                this.scaleX = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleX", 0.0f, this.dk);
                this.scaleY = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleY", 0.0f, this.dk);
                this.scaleX.setDuration(125L);
                this.scaleY.setDuration(125L);
                this.scaleXBlank = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleX", this.dk, this.dk);
                this.scaleYBlank = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleY", this.dk, this.dk);
                this.scaleXBlank.setDuration(375L);
                this.scaleYBlank.setDuration(375L);
                this.scaleXBack = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleX", this.dk, 0.55f);
                this.scaleYBack = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleY", this.dk, 0.55f);
                this.scaleXBack.setDuration(500L);
                this.scaleYBack.setDuration(500L);
                this.alpha = ObjectAnimator.ofFloat(this.mImageEffect, "alpha", 0.0f, 1.0f);
                this.alpha.setDuration(250L);
                this.alphaBlank = ObjectAnimator.ofFloat(this.mImageEffect, "alpha", 1.0f, 1.0f);
                this.alphaBlank.setDuration(250L);
                this.alphaBack = ObjectAnimator.ofFloat(this.mImageEffect, "alpha", 1.0f, 0.0f);
                this.alphaBack.setDuration(500L);
                this.rotation = ObjectAnimator.ofFloat(this.mImageEffect, "rotation", 0.0f, 359.0f);
                this.rotation.setDuration(1000L);
                animatorSet.play(this.scaleX).with(this.scaleY).with(this.alpha).with(this.rotation);
                animatorSet.play(this.scaleXBlank).with(this.scaleYBlank).after(this.scaleX);
                animatorSet.play(this.scaleXBack).with(this.scaleYBack).after(this.scaleXBlank);
                animatorSet.play(this.alphaBlank).after(this.alpha);
                animatorSet.play(this.alphaBack).after(this.alphaBlank);
            } else if (this.m == 5) {
                this.dk = (float) (0.8999999761581421d + (0.1d * new Random().nextInt(2)));
                this.mImageEffect.setX(f - this.dx);
                this.mImageEffect.setY(this.dx + f2);
                this.scaleX = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleX", 0.0f, this.dk);
                this.scaleY = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleY", 0.0f, this.dk);
                this.scaleX.setDuration(125L);
                this.scaleY.setDuration(125L);
                this.scaleXBlank = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleX", this.dk, this.dk);
                this.scaleYBlank = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleY", this.dk, this.dk);
                this.scaleXBlank.setDuration(375L);
                this.scaleYBlank.setDuration(375L);
                this.scaleXBack = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleX", this.dk, 0.55f);
                this.scaleYBack = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleY", this.dk, 0.55f);
                this.scaleXBack.setDuration(500L);
                this.scaleYBack.setDuration(500L);
                this.alpha = ObjectAnimator.ofFloat(this.mImageEffect, "alpha", 0.0f, 1.0f);
                this.alpha.setDuration(250L);
                this.alphaBlank = ObjectAnimator.ofFloat(this.mImageEffect, "alpha", 1.0f, 1.0f);
                this.alphaBlank.setDuration(250L);
                this.alphaBack = ObjectAnimator.ofFloat(this.mImageEffect, "alpha", 1.0f, 0.0f);
                this.alphaBack.setDuration(500L);
                this.rotation = ObjectAnimator.ofFloat(this.mImageEffect, "rotation", 0.0f, 359.0f);
                this.rotation.setDuration(1000L);
                animatorSet.play(this.scaleX).with(this.scaleY).with(this.alpha).with(this.rotation);
                animatorSet.play(this.scaleXBlank).with(this.scaleYBlank).after(this.scaleX);
                animatorSet.play(this.scaleXBack).with(this.scaleYBack).after(this.scaleXBlank);
                animatorSet.play(this.alphaBlank).after(this.alpha);
                animatorSet.play(this.alphaBack).after(this.alphaBlank);
            }
            animatorSet.start();
        }
    }
}