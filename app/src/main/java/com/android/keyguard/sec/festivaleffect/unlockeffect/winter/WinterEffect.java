package com.android.keyguard.sec.festivaleffect.unlockeffect.winter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.widget.ImageView;

import com.aj.effect.R;
import com.android.keyguard.sec.festivaleffect.unlockeffect.FestivalEffect;

import java.util.Random;

public class WinterEffect extends FestivalEffect {
    protected static final String TAG = "WinterEffect";
    private ValueAnimator alpha;
    private ValueAnimator alphaBack;
    private float dk;
    private float dm;
    private float dx;
    private int m;
    private Context mContext;
    private boolean mHasAddImage;
    private ImageView mImageEffect;
    private ImageView mImageTouch;
    private ImageView mImageTouch2;
    private float mScreenAdjust;
    private float maxRand;
    private int[] resId;
    private int[] resIdTouch;
    private ValueAnimator rotation;
    private ValueAnimator scaleX;
    private ValueAnimator scaleXback;
    private ValueAnimator scaleXblank;
    private ValueAnimator scaleY;
    private ValueAnimator scaleYback;
    private ValueAnimator scaleYblank;

    public WinterEffect(Context context) {
        super(context);
        this.maxRand = 100.0f;
        this.dm = 0.0f;
        this.dk = 1.0f;
        this.m = 0;
        this.resId = new int[]{R.drawable.festival_unlock_effect_01, R.drawable.festival_unlock_effect_02, R.drawable.festival_unlock_effect_03};
        this.resIdTouch = new int[]{R.drawable.festival_unlock_touch_01, R.drawable.festival_unlock_touch_02};
        this.mHasAddImage = false;
        this.mContext = context;
        this.mScreenAdjust = this.mContext.getResources().getDisplayMetrics().density;
        init();
    }

    public void add(float f, float f2) {
        this.mImageTouch = new ImageView(this.mContext);
        this.mImageTouch.setImageResource(this.resIdTouch[0]);
        this.mImageTouch2 = new ImageView(this.mContext);
        this.mImageTouch2.setImageResource(this.resIdTouch[1]);
        addView(this.mImageTouch, -2, -2);
        addView(this.mImageTouch2, -2, -2);
        this.mImageTouch.setX(f - (this.mScreenAdjust * 30.0f));
        this.mImageTouch.setY(f2 - (this.mScreenAdjust * 30.0f));
        this.mImageTouch2.setX((f - (4.0f * this.mScreenAdjust)) - (this.mScreenAdjust * 40.0f));
        this.mImageTouch2.setY(((9.0f * this.mScreenAdjust) + f2) - (this.mScreenAdjust * 40.0f));
        this.mHasAddImage = true;
    }

    public void clearEffect() {
        this.dm = 0.0f;
        removeView(this.mImageTouch);
        removeView(this.mImageTouch2);
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
        this.mImageTouch.setX(f - (30.0f * this.mScreenAdjust));
        this.mImageTouch.setY(f2 - (30.0f * this.mScreenAdjust));
        this.dm += 2.0f;
        if (this.dm >= 10.0f) {
            this.dm = 10.0f;
        }
        this.mImageTouch2.setX(((f - (4.0f * this.mScreenAdjust)) - (40.0f * this.mScreenAdjust)) - (this.dm * this.mScreenAdjust));
        this.mImageTouch2.setY((((9.0f * this.mScreenAdjust) + f2) - (40.0f * this.mScreenAdjust)) - (this.dm * this.mScreenAdjust));
        this.m = new Random().nextInt(5);
        if (this.m < 3) {
            this.mImageEffect = new ImageView(this.mContext);
            this.mImageEffect.setImageResource(this.resId[this.m]);
            addView(this.mImageEffect, -2, -2);
            if (this.m == 0) {
                this.dk = (float) (0.6000000238418579d + (0.1d * new Random().nextInt(9)));
                this.mImageEffect.setX(f - this.dx);
                this.mImageEffect.setY(this.dx + f2);
                this.scaleX = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleX", 0.0f, this.dk);
                this.scaleY = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleY", 0.0f, this.dk);
                this.scaleX.setDuration(80L);
                this.scaleY.setDuration(80L);
                this.scaleXblank = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleX", this.dk, this.dk);
                this.scaleYblank = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleY", this.dk, this.dk);
                this.scaleXblank.setDuration(70L);
                this.scaleYblank.setDuration(70L);
                this.scaleXback = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleX", this.dk, 0.0f);
                this.scaleYback = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleY", this.dk, 0.0f);
                this.scaleXback.setDuration(450L);
                this.scaleYback.setDuration(450L);
                this.alpha = ObjectAnimator.ofFloat(this.mImageEffect, "alpha", 0.0f, 1.0f);
                this.alpha.setDuration(150L);
                this.alphaBack = ObjectAnimator.ofFloat(this.mImageEffect, "alpha", 1.0f, 0.0f);
                this.alphaBack.setDuration(450L);
                animatorSet.play(this.scaleX).with(this.scaleY);
                animatorSet.play(this.scaleXblank).with(this.scaleYblank).after(this.scaleX);
                animatorSet.play(this.scaleXback).with(this.scaleYback).after(this.scaleXblank);
                animatorSet.play(this.alphaBack).after(this.alpha);
            } else if (this.m == 1) {
                this.dk = (float) (0.5d + (0.1d * new Random().nextInt(7)));
                this.mImageEffect.setX(f - this.dx);
                this.mImageEffect.setY(this.dx + f2);
                this.scaleX = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleX", 0.0f, this.dk);
                this.scaleY = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleY", 0.0f, this.dk);
                this.scaleX.setDuration(130L);
                this.scaleY.setDuration(130L);
                this.scaleXblank = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleX", this.dk, this.dk);
                this.scaleYblank = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleY", this.dk, this.dk);
                this.scaleXblank.setDuration(120L);
                this.scaleYblank.setDuration(120L);
                this.scaleXback = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleX", this.dk, 0.0f);
                this.scaleYback = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleY", this.dk, 0.0f);
                this.scaleXback.setDuration(750L);
                this.scaleYback.setDuration(750L);
                this.alpha = ObjectAnimator.ofFloat(this.mImageEffect, "alpha", 0.0f, 1.0f);
                this.alpha.setDuration(250L);
                this.alphaBack = ObjectAnimator.ofFloat(this.mImageEffect, "alpha", 1.0f, 0.0f);
                this.alphaBack.setDuration(750L);
                animatorSet.play(this.scaleX).with(this.scaleY);
                animatorSet.play(this.scaleXblank).with(this.scaleYblank).after(this.scaleX);
                animatorSet.play(this.scaleXback).with(this.scaleYback).after(this.scaleXblank);
                animatorSet.play(this.alphaBack).after(this.alpha);
            } else if (this.m == 2) {
                this.dk = (float) (0.5d + (0.1d * new Random().nextInt(11)));
                this.mImageEffect.setX(f - this.dx);
                this.mImageEffect.setY(this.dx + f2);
                this.scaleX = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleX", 0.0f, this.dk);
                this.scaleY = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleY", 0.0f, this.dk);
                this.scaleX.setDuration(250L);
                this.scaleY.setDuration(250L);
                this.scaleXblank = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleX", this.dk, this.dk);
                this.scaleYblank = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleY", this.dk, this.dk);
                this.scaleXblank.setDuration(250L);
                this.scaleYblank.setDuration(250L);
                this.scaleXback = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleX", this.dk, 0.0f);
                this.scaleYback = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleY", this.dk, 0.0f);
                this.scaleXback.setDuration(500L);
                this.scaleYback.setDuration(500L);
                this.alpha = ObjectAnimator.ofFloat(this.mImageEffect, "alpha", 0.0f, 1.0f);
                this.alpha.setDuration(250L);
                this.alphaBack = ObjectAnimator.ofFloat(this.mImageEffect, "alpha", 1.0f, 0.0f);
                this.alphaBack.setDuration(750L);
                this.rotation = ObjectAnimator.ofFloat(this.mImageEffect, "rotation", 0.0f, 340.0f);
                this.rotation.setDuration(1000L);
                animatorSet.play(this.scaleX).with(this.scaleY);
                animatorSet.play(this.scaleXblank).with(this.scaleYblank).after(this.scaleX);
                animatorSet.play(this.scaleXback).with(this.scaleYback).after(this.scaleXblank);
                animatorSet.play(this.alphaBack).after(this.alpha);
                animatorSet.play(this.rotation);
            }
            animatorSet.start();
        }
    }
}