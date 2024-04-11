package com.android.keyguard.sec.festivaleffect.unlockeffect.autumn;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.widget.ImageView;

import com.aj.effect.R;
import com.android.keyguard.sec.festivaleffect.common.DensityUtil;
import com.android.keyguard.sec.festivaleffect.unlockeffect.FestivalEffect;

import java.util.Random;

public class AutumnEffect extends FestivalEffect {
    protected static final String TAG = "AutumnEffect";
    private ValueAnimator alpha;
    private ValueAnimator alphaBack;
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
    private int rotateAngel;
    private ValueAnimator rotation;
    private ValueAnimator scaleX;
    private ValueAnimator scaleXBack;
    private ValueAnimator scaleXBlank;
    private ValueAnimator scaleY;
    private ValueAnimator scaleYBack;
    private ValueAnimator scaleYBlank;
    private ValueAnimator touchAlpha;
    private ValueAnimator touchAlpha2;
    private ValueAnimator touchScaleX;
    private ValueAnimator touchScaleX2;
    private ValueAnimator touchScaleX2Move;
    private ValueAnimator touchScaleXMove;
    private ValueAnimator touchScaleY;
    private ValueAnimator touchScaleY2;
    private ValueAnimator touchScaleY2Move;
    private ValueAnimator touchScaleYMove;

    public AutumnEffect(Context context) {
        super(context);
        this.maxRand = 200.0f;
        this.m = 0;
        this.rotateAngel = 0;
        this.resId = new int[]{R.drawable.unlock_autumn_particle_01, R.drawable.unlock_autumn_particle_02, R.drawable.unlock_autumn_particle_03, R.drawable.unlock_autumn_particle_04, R.drawable.unlock_autumn_particle_05};
        this.resIdTouch = new int[]{R.drawable.unlock_spring_touch_01, R.drawable.unlock_spring_touch_02};
        this.mHasAddImage = false;
        this.mContext = context;
        this.mScreenAdjust = this.mContext.getResources().getDisplayMetrics().density;
        init();
    }

    public void add(float f, float f2) {
        AnimatorSet animatorSet = new AnimatorSet();
        this.mImageTouch = new ImageView(this.mContext);
        this.mImageTouch.setImageResource(this.resIdTouch[1]);
        this.mImageTouch2 = new ImageView(this.mContext);
        this.mImageTouch2.setImageResource(this.resIdTouch[0]);
        addView(this.mImageTouch, -2, -2);
        addView(this.mImageTouch2, -2, -2);
        this.mImageTouch.setX(f - (72.0f * this.mScreenAdjust));
        this.mImageTouch.setY(f2 - (72.0f * this.mScreenAdjust));
        this.mImageTouch2.setX(f - (this.mScreenAdjust * 35.0f));
        this.mImageTouch2.setY(f2 - (this.mScreenAdjust * 35.0f));
        this.touchScaleX = ObjectAnimator.ofFloat(this.mImageTouch, "ScaleX", 0.75f, 0.95f);
        this.touchScaleY = ObjectAnimator.ofFloat(this.mImageTouch, "ScaleY", 0.75f, 0.95f);
        this.touchScaleX.setDuration(1470L);
        this.touchScaleY.setDuration(1470L);
        this.touchAlpha = ObjectAnimator.ofFloat(this.mImageTouch, "alpha", 0.0f, 1.0f);
        this.touchAlpha.setDuration(1470L);
        this.touchScaleXMove = ObjectAnimator.ofFloat(this.mImageTouch, "ScaleX", 0.95f, 1.0f);
        this.touchScaleYMove = ObjectAnimator.ofFloat(this.mImageTouch, "ScaleY", 0.95f, 1.0f);
        this.touchScaleXMove.setDuration(330L);
        this.touchScaleYMove.setDuration(330L);
        animatorSet.play(this.touchScaleX).with(this.touchScaleY);
        animatorSet.play(this.touchScaleXMove).with(this.touchScaleYMove).after(this.touchScaleX);
        this.touchScaleX2 = ObjectAnimator.ofFloat(this.mImageTouch2, "ScaleX", 0.4f, 0.5f);
        this.touchScaleY2 = ObjectAnimator.ofFloat(this.mImageTouch2, "ScaleY", 0.4f, 0.5f);
        this.touchScaleX2.setDuration(330L);
        this.touchScaleY2.setDuration(330L);
        this.touchAlpha2 = ObjectAnimator.ofFloat(this.mImageTouch2, "alpha", 0.0f, 1.0f);
        this.touchAlpha2.setDuration(330L);
        this.touchScaleX2Move = ObjectAnimator.ofFloat(this.mImageTouch2, "ScaleX", 0.5f, 1.0f);
        this.touchScaleY2Move = ObjectAnimator.ofFloat(this.mImageTouch2, "ScaleY", 0.5f, 1.0f);
        this.touchScaleX2Move.setDuration(1470L);
        this.touchScaleY2Move.setDuration(1470L);
        animatorSet.play(this.touchScaleX2).with(this.touchScaleY2);
        animatorSet.play(this.touchScaleX2Move).with(this.touchScaleY2Move).after(this.touchScaleX2);
        animatorSet.start();
        this.mHasAddImage = true;
    }

    public void clearEffect() {
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
        this.mImageTouch.setX(f - (72.0f * this.mScreenAdjust));
        this.mImageTouch.setY(f2 - (72.0f * this.mScreenAdjust));
        this.mImageTouch2.setX(f - (35.0f * this.mScreenAdjust));
        this.mImageTouch2.setY(f2 - (35.0f * this.mScreenAdjust));
        this.m = new Random().nextInt(9);
        this.rotateAngel = new Random().nextInt(DensityUtil.DEFAULT_DEVICE_WIDTH);
        if (this.m < 5) {
            this.mImageEffect = new ImageView(this.mContext);
            this.mImageEffect.setImageResource(this.resId[this.m]);
            addView(this.mImageEffect, -2, -2);
            this.mImageEffect.setX(f - this.dx);
            this.mImageEffect.setY(this.dx + f2);
            this.scaleX = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleX", 0.0f, 1.0f);
            this.scaleY = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleY", 0.0f, 1.0f);
            this.scaleX.setDuration(125L);
            this.scaleY.setDuration(125L);
            this.scaleXBlank = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleX", 1.0f, 1.0f);
            this.scaleYBlank = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleY", 1.0f, 1.0f);
            this.scaleXBlank.setDuration(375L);
            this.scaleYBlank.setDuration(375L);
            this.scaleXBack = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleX", 1.0f, 0.5f);
            this.scaleYBack = ObjectAnimator.ofFloat(this.mImageEffect, "ScaleY", 1.0f, 0.5f);
            this.scaleXBack.setDuration(500L);
            this.scaleYBack.setDuration(500L);
            this.alpha = ObjectAnimator.ofFloat(this.mImageEffect, "alpha", 0.0f, 1.0f);
            this.alpha.setDuration(250L);
            this.alphaBack = ObjectAnimator.ofFloat(this.mImageEffect, "alpha", 1.0f, 0.0f);
            this.alphaBack.setDuration(750L);
            this.rotation = ObjectAnimator.ofFloat(this.mImageEffect, "rotation", 0.0f, this.rotateAngel);
            this.rotation.setDuration(1000L);
            animatorSet.play(this.scaleX).with(this.scaleY).with(this.alpha).with(this.rotation);
            animatorSet.play(this.scaleXBlank).with(this.scaleYBlank).after(this.scaleX);
            animatorSet.play(this.scaleXBack).with(this.scaleYBack).after(this.scaleXBlank);
            animatorSet.play(this.alphaBack).after(this.alpha);
            animatorSet.start();
        }
    }
}