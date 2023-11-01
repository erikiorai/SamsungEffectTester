package com.android.systemui.assist;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Interpolator;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$dimen;
import com.android.systemui.animation.Interpolators;

/* loaded from: mainsysui33.jar:com/android/systemui/assist/AssistDisclosure.class */
public class AssistDisclosure {
    public final Context mContext;
    public final Handler mHandler;
    public Runnable mShowRunnable = new Runnable() { // from class: com.android.systemui.assist.AssistDisclosure.1
        @Override // java.lang.Runnable
        public void run() {
            AssistDisclosure.this.show();
        }
    };
    public AssistDisclosureView mView;
    public boolean mViewAdded;
    public final WindowManager mWm;

    /* loaded from: mainsysui33.jar:com/android/systemui/assist/AssistDisclosure$AssistDisclosureView.class */
    public class AssistDisclosureView extends View implements ValueAnimator.AnimatorUpdateListener {
        public int mAlpha;
        public final ValueAnimator mAlphaInAnimator;
        public final ValueAnimator mAlphaOutAnimator;
        public final AnimatorSet mAnimator;
        public final Paint mPaint;
        public final Paint mShadowPaint;
        public float mShadowThickness;
        public float mThickness;

        public AssistDisclosureView(Context context) {
            super(context);
            Paint paint = new Paint();
            this.mPaint = paint;
            Paint paint2 = new Paint();
            this.mShadowPaint = paint2;
            this.mAlpha = 0;
            ValueAnimator duration = ValueAnimator.ofInt(0, 222).setDuration(400L);
            this.mAlphaInAnimator = duration;
            duration.addUpdateListener(this);
            Interpolator interpolator = Interpolators.CUSTOM_40_40;
            duration.setInterpolator(interpolator);
            ValueAnimator duration2 = ValueAnimator.ofInt(222, 0).setDuration(300L);
            this.mAlphaOutAnimator = duration2;
            duration2.addUpdateListener(this);
            duration2.setInterpolator(interpolator);
            AnimatorSet animatorSet = new AnimatorSet();
            this.mAnimator = animatorSet;
            animatorSet.play(duration).before(duration2);
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.assist.AssistDisclosure.AssistDisclosureView.1
                public boolean mCancelled;

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    this.mCancelled = true;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (this.mCancelled) {
                        return;
                    }
                    AssistDisclosure.this.hide();
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    this.mCancelled = false;
                }
            });
            PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC);
            paint.setColor(-1);
            paint.setXfermode(porterDuffXfermode);
            paint2.setColor(-12303292);
            paint2.setXfermode(porterDuffXfermode);
            this.mThickness = getResources().getDimension(R$dimen.assist_disclosure_thickness);
            this.mShadowThickness = getResources().getDimension(R$dimen.assist_disclosure_shadow_thickness);
        }

        public final void drawBeam(Canvas canvas, float f, float f2, float f3, float f4, Paint paint, float f5) {
            canvas.drawRect(f - f5, f2 - f5, f3 + f5, f4 + f5, paint);
        }

        public final void drawGeometry(Canvas canvas, Paint paint, float f) {
            int width = getWidth();
            int height = getHeight();
            float f2 = this.mThickness;
            float f3 = height;
            float f4 = f3 - f2;
            float f5 = width;
            drawBeam(canvas, ActionBarShadowController.ELEVATION_LOW, f4, f5, f3, paint, f);
            drawBeam(canvas, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, f2, f4, paint, f);
            float f6 = f5 - f2;
            drawBeam(canvas, f6, ActionBarShadowController.ELEVATION_LOW, f5, f4, paint, f);
            drawBeam(canvas, f2, ActionBarShadowController.ELEVATION_LOW, f6, f2, paint, f);
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            ValueAnimator valueAnimator2 = this.mAlphaOutAnimator;
            if (valueAnimator == valueAnimator2) {
                this.mAlpha = ((Integer) valueAnimator2.getAnimatedValue()).intValue();
            } else {
                ValueAnimator valueAnimator3 = this.mAlphaInAnimator;
                if (valueAnimator == valueAnimator3) {
                    this.mAlpha = ((Integer) valueAnimator3.getAnimatedValue()).intValue();
                }
            }
            invalidate();
        }

        @Override // android.view.View
        public void onAttachedToWindow() {
            super.onAttachedToWindow();
            startAnimation();
            sendAccessibilityEvent(16777216);
        }

        @Override // android.view.View
        public void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            this.mAnimator.cancel();
            this.mAlpha = 0;
        }

        @Override // android.view.View
        public void onDraw(Canvas canvas) {
            this.mPaint.setAlpha(this.mAlpha);
            this.mShadowPaint.setAlpha(this.mAlpha / 4);
            drawGeometry(canvas, this.mShadowPaint, this.mShadowThickness);
            drawGeometry(canvas, this.mPaint, ActionBarShadowController.ELEVATION_LOW);
        }

        public final void startAnimation() {
            this.mAnimator.cancel();
            this.mAnimator.start();
        }
    }

    public AssistDisclosure(Context context, Handler handler) {
        this.mContext = context;
        this.mHandler = handler;
        this.mWm = (WindowManager) context.getSystemService(WindowManager.class);
    }

    public final void hide() {
        if (this.mViewAdded) {
            this.mWm.removeView(this.mView);
            this.mViewAdded = false;
        }
    }

    public void postShow() {
        this.mHandler.removeCallbacks(this.mShowRunnable);
        this.mHandler.post(this.mShowRunnable);
    }

    public final void show() {
        if (this.mView == null) {
            this.mView = new AssistDisclosureView(this.mContext);
        }
        if (this.mViewAdded) {
            return;
        }
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(2015, 525576, -3);
        layoutParams.setTitle("AssistDisclosure");
        layoutParams.setFitInsetsTypes(0);
        this.mWm.addView(this.mView, layoutParams);
        this.mViewAdded = true;
    }
}