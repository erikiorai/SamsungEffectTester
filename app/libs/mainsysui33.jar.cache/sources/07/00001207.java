package com.android.systemui.biometrics;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$attr;
import com.android.systemui.R$drawable;
import com.android.systemui.R$style;
import com.android.systemui.R$styleable;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsEnrollDrawable.class */
public class UdfpsEnrollDrawable extends UdfpsDrawable {
    public final Paint mBlueFill;
    public float mCurrentScale;
    public float mCurrentX;
    public float mCurrentY;
    public UdfpsEnrollHelper mEnrollHelper;
    public int mEnrollIcon;
    public final Handler mHandler;
    public int mMovingTargetFill;
    public final Drawable mMovingTargetFpIcon;
    public final Paint mSensorOutlinePaint;
    public RectF mSensorRect;
    public boolean mShouldShowEdgeHint;
    public boolean mShouldShowTipHint;
    public final Animator.AnimatorListener mTargetAnimListener;
    public AnimatorSet mTargetAnimatorSet;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsEnrollDrawable$$ExternalSyntheticLambda2.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$Dch01jwrr13MKv6EnoiMNuGdtC4(UdfpsEnrollDrawable udfpsEnrollDrawable, ValueAnimator valueAnimator) {
        udfpsEnrollDrawable.lambda$onEnrollmentProgress$2(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsEnrollDrawable$$ExternalSyntheticLambda0.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$cKQtVJYAHrNydjOgzO4dykW_C1Y(UdfpsEnrollDrawable udfpsEnrollDrawable, ValueAnimator valueAnimator) {
        udfpsEnrollDrawable.lambda$onEnrollmentProgress$0(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsEnrollDrawable$$ExternalSyntheticLambda1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    /* renamed from: $r8$lambda$nzQudIMiAm7eDVmCx1Z-3nOfMjc */
    public static /* synthetic */ void m1595$r8$lambda$nzQudIMiAm7eDVmCx1Z3nOfMjc(UdfpsEnrollDrawable udfpsEnrollDrawable, ValueAnimator valueAnimator) {
        udfpsEnrollDrawable.lambda$onEnrollmentProgress$1(valueAnimator);
    }

    public UdfpsEnrollDrawable(Context context, AttributeSet attributeSet) {
        super(context);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mCurrentScale = 1.0f;
        this.mShouldShowTipHint = false;
        this.mShouldShowEdgeHint = false;
        loadResources(context, attributeSet);
        Paint paint = new Paint(0);
        this.mSensorOutlinePaint = paint;
        paint.setAntiAlias(true);
        paint.setColor(this.mMovingTargetFill);
        paint.setStyle(Paint.Style.FILL);
        Paint paint2 = new Paint(0);
        this.mBlueFill = paint2;
        paint2.setAntiAlias(true);
        paint2.setColor(this.mMovingTargetFill);
        paint2.setStyle(Paint.Style.FILL);
        Drawable drawable = context.getResources().getDrawable(R$drawable.ic_kg_fingerprint, null);
        this.mMovingTargetFpIcon = drawable;
        drawable.setTint(this.mEnrollIcon);
        drawable.mutate();
        getFingerprintDrawable().setTint(this.mEnrollIcon);
        this.mTargetAnimListener = new Animator.AnimatorListener() { // from class: com.android.systemui.biometrics.UdfpsEnrollDrawable.1
            {
                UdfpsEnrollDrawable.this = this;
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                UdfpsEnrollDrawable.this.updateTipHintVisibility();
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }
        };
    }

    public /* synthetic */ void lambda$onEnrollmentProgress$0(ValueAnimator valueAnimator) {
        this.mCurrentX = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidateSelf();
    }

    public /* synthetic */ void lambda$onEnrollmentProgress$1(ValueAnimator valueAnimator) {
        this.mCurrentY = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidateSelf();
    }

    public /* synthetic */ void lambda$onEnrollmentProgress$2(ValueAnimator valueAnimator) {
        this.mCurrentScale = (((float) Math.sin(((Float) valueAnimator.getAnimatedValue()).floatValue())) * 0.25f) + 1.0f;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (isDisplayConfigured()) {
            return;
        }
        UdfpsEnrollHelper udfpsEnrollHelper = this.mEnrollHelper;
        if (udfpsEnrollHelper == null || udfpsEnrollHelper.isCenterEnrollmentStage()) {
            RectF rectF = this.mSensorRect;
            if (rectF != null) {
                canvas.drawOval(rectF, this.mSensorOutlinePaint);
            }
            getFingerprintDrawable().draw(canvas);
            getFingerprintDrawable().setAlpha(getAlpha());
            this.mSensorOutlinePaint.setAlpha(getAlpha());
            return;
        }
        canvas.save();
        canvas.translate(this.mCurrentX, this.mCurrentY);
        RectF rectF2 = this.mSensorRect;
        if (rectF2 != null) {
            float f = this.mCurrentScale;
            canvas.scale(f, f, rectF2.centerX(), this.mSensorRect.centerY());
            canvas.drawOval(this.mSensorRect, this.mBlueFill);
        }
        this.mMovingTargetFpIcon.draw(canvas);
        canvas.restore();
    }

    public void loadResources(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.BiometricsEnrollView, R$attr.biometricsEnrollStyle, R$style.BiometricsEnrollStyle);
        this.mEnrollIcon = obtainStyledAttributes.getColor(R$styleable.BiometricsEnrollView_biometricsEnrollIcon, 0);
        this.mMovingTargetFill = obtainStyledAttributes.getColor(R$styleable.BiometricsEnrollView_biometricsMovingTargetFill, 0);
        obtainStyledAttributes.recycle();
    }

    public void onEnrollmentProgress(int i, int i2) {
        UdfpsEnrollHelper udfpsEnrollHelper = this.mEnrollHelper;
        if (udfpsEnrollHelper == null) {
            return;
        }
        if (udfpsEnrollHelper.isCenterEnrollmentStage()) {
            updateTipHintVisibility();
        } else {
            AnimatorSet animatorSet = this.mTargetAnimatorSet;
            if (animatorSet != null && animatorSet.isRunning()) {
                this.mTargetAnimatorSet.end();
            }
            PointF nextGuidedEnrollmentPoint = this.mEnrollHelper.getNextGuidedEnrollmentPoint();
            float f = this.mCurrentX;
            float f2 = nextGuidedEnrollmentPoint.x;
            if (f == f2 && this.mCurrentY == nextGuidedEnrollmentPoint.y) {
                updateTipHintVisibility();
            } else {
                ValueAnimator ofFloat = ValueAnimator.ofFloat(f, f2);
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.UdfpsEnrollDrawable$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        UdfpsEnrollDrawable.$r8$lambda$cKQtVJYAHrNydjOgzO4dykW_C1Y(UdfpsEnrollDrawable.this, valueAnimator);
                    }
                });
                ValueAnimator ofFloat2 = ValueAnimator.ofFloat(this.mCurrentY, nextGuidedEnrollmentPoint.y);
                ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.UdfpsEnrollDrawable$$ExternalSyntheticLambda1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        UdfpsEnrollDrawable.m1595$r8$lambda$nzQudIMiAm7eDVmCx1Z3nOfMjc(UdfpsEnrollDrawable.this, valueAnimator);
                    }
                });
                long j = (nextGuidedEnrollmentPoint.x > ActionBarShadowController.ELEVATION_LOW ? 1 : (nextGuidedEnrollmentPoint.x == ActionBarShadowController.ELEVATION_LOW ? 0 : -1)) == 0 && (nextGuidedEnrollmentPoint.y > ActionBarShadowController.ELEVATION_LOW ? 1 : (nextGuidedEnrollmentPoint.y == ActionBarShadowController.ELEVATION_LOW ? 0 : -1)) == 0 ? 600L : 800L;
                ValueAnimator ofFloat3 = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 3.1415927f);
                ofFloat3.setDuration(j);
                ofFloat3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.UdfpsEnrollDrawable$$ExternalSyntheticLambda2
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        UdfpsEnrollDrawable.$r8$lambda$Dch01jwrr13MKv6EnoiMNuGdtC4(UdfpsEnrollDrawable.this, valueAnimator);
                    }
                });
                AnimatorSet animatorSet2 = new AnimatorSet();
                this.mTargetAnimatorSet = animatorSet2;
                animatorSet2.setInterpolator(new AccelerateDecelerateInterpolator());
                this.mTargetAnimatorSet.setDuration(j);
                this.mTargetAnimatorSet.addListener(this.mTargetAnimListener);
                this.mTargetAnimatorSet.playTogether(ofFloat, ofFloat2, ofFloat3);
                this.mTargetAnimatorSet.start();
            }
        }
        updateEdgeHintVisibility();
    }

    @Override // com.android.systemui.biometrics.UdfpsDrawable
    public void onSensorRectUpdated(RectF rectF) {
        super.onSensorRectUpdated(rectF);
        this.mSensorRect = rectF;
    }

    @Override // com.android.systemui.biometrics.UdfpsDrawable, android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        super.setAlpha(i);
        this.mSensorOutlinePaint.setAlpha(i);
        this.mBlueFill.setAlpha(i);
        this.mMovingTargetFpIcon.setAlpha(i);
        invalidateSelf();
    }

    public void setEnrollHelper(UdfpsEnrollHelper udfpsEnrollHelper) {
        this.mEnrollHelper = udfpsEnrollHelper;
    }

    public final void updateEdgeHintVisibility() {
        UdfpsEnrollHelper udfpsEnrollHelper = this.mEnrollHelper;
        boolean z = udfpsEnrollHelper != null && udfpsEnrollHelper.isEdgeEnrollmentStage();
        if (this.mShouldShowEdgeHint == z) {
            return;
        }
        this.mShouldShowEdgeHint = z;
    }

    @Override // com.android.systemui.biometrics.UdfpsDrawable
    public void updateFingerprintIconBounds(Rect rect) {
        super.updateFingerprintIconBounds(rect);
        this.mMovingTargetFpIcon.setBounds(rect);
        invalidateSelf();
    }

    public final void updateTipHintVisibility() {
        UdfpsEnrollHelper udfpsEnrollHelper = this.mEnrollHelper;
        boolean z = udfpsEnrollHelper != null && udfpsEnrollHelper.isTipEnrollmentStage();
        if (this.mShouldShowTipHint == z) {
            return;
        }
        this.mShouldShowTipHint = z;
    }
}