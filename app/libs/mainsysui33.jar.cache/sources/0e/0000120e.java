package com.android.systemui.biometrics;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Process;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$attr;
import com.android.systemui.R$drawable;
import com.android.systemui.R$style;
import com.android.systemui.R$styleable;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsEnrollProgressBarDrawable.class */
public class UdfpsEnrollProgressBarDrawable extends Drawable {
    public boolean mAfterFirstTouch;
    public ValueAnimator mBackgroundColorAnimator;
    public final ValueAnimator.AnimatorUpdateListener mBackgroundColorUpdateListener;
    public final Paint mBackgroundPaint;
    public ValueAnimator mCheckmarkAnimator;
    public final Drawable mCheckmarkDrawable;
    public final Interpolator mCheckmarkInterpolator;
    public final ValueAnimator.AnimatorUpdateListener mCheckmarkUpdateListener;
    public final Context mContext;
    public int mEnrollProgress;
    public int mEnrollProgressHelp;
    public int mEnrollProgressHelpWithTalkback;
    public ValueAnimator mFillColorAnimator;
    public final ValueAnimator.AnimatorUpdateListener mFillColorUpdateListener;
    public final Paint mFillPaint;
    public final int mHelpColor;
    public final boolean mIsAccessibilityEnabled;
    public int mMovingTargetFill;
    public int mMovingTargetFillError;
    public final int mOnFirstBucketFailedColor;
    public ValueAnimator mProgressAnimator;
    public final int mProgressColor;
    public final ValueAnimator.AnimatorUpdateListener mProgressUpdateListener;
    public final float mStrokeWidthPx;
    public final Vibrator mVibrator;
    public static final Interpolator DEACCEL = new DecelerateInterpolator();
    public static final VibrationEffect VIBRATE_EFFECT_ERROR = VibrationEffect.createWaveform(new long[]{0, 5, 55, 60}, -1);
    public static final VibrationAttributes FINGERPRINT_ENROLLING_SONFICATION_ATTRIBUTES = VibrationAttributes.createForUsage(66);
    public static final VibrationAttributes HARDWARE_FEEDBACK_VIBRATION_ATTRIBUTES = VibrationAttributes.createForUsage(50);
    public static final VibrationEffect SUCCESS_VIBRATION_EFFECT = VibrationEffect.get(0);
    public int mRemainingSteps = 0;
    public int mTotalSteps = 0;
    public float mProgress = ActionBarShadowController.ELEVATION_LOW;
    public boolean mShowingHelp = false;
    public boolean mComplete = false;
    public float mCheckmarkScale = ActionBarShadowController.ELEVATION_LOW;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsEnrollProgressBarDrawable$$ExternalSyntheticLambda0.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$QuF4aU4rKy4Seup1XvknLQvv9os(UdfpsEnrollProgressBarDrawable udfpsEnrollProgressBarDrawable, ValueAnimator valueAnimator) {
        udfpsEnrollProgressBarDrawable.lambda$new$0(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsEnrollProgressBarDrawable$$ExternalSyntheticLambda2.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$pUH1NyJyx0vTHQnAiayq_xvF0nk(UdfpsEnrollProgressBarDrawable udfpsEnrollProgressBarDrawable, ValueAnimator valueAnimator) {
        udfpsEnrollProgressBarDrawable.lambda$new$2(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsEnrollProgressBarDrawable$$ExternalSyntheticLambda1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$vIik3M3fPGv3_LFfEy5XpHpHGoA(UdfpsEnrollProgressBarDrawable udfpsEnrollProgressBarDrawable, ValueAnimator valueAnimator) {
        udfpsEnrollProgressBarDrawable.lambda$new$1(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsEnrollProgressBarDrawable$$ExternalSyntheticLambda3.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$zJzd5FlAu5Z3PW81NQFYkOHDi40(UdfpsEnrollProgressBarDrawable udfpsEnrollProgressBarDrawable, ValueAnimator valueAnimator) {
        udfpsEnrollProgressBarDrawable.lambda$new$3(valueAnimator);
    }

    public UdfpsEnrollProgressBarDrawable(Context context, AttributeSet attributeSet) {
        this.mContext = context;
        loadResources(context, attributeSet);
        float dpToPixels = Utils.dpToPixels(context, 12.0f);
        this.mStrokeWidthPx = dpToPixels;
        int i = this.mEnrollProgress;
        this.mProgressColor = i;
        boolean isTouchExplorationEnabled = ((AccessibilityManager) context.getSystemService(AccessibilityManager.class)).isTouchExplorationEnabled();
        this.mIsAccessibilityEnabled = isTouchExplorationEnabled;
        if (isTouchExplorationEnabled) {
            int i2 = this.mEnrollProgressHelpWithTalkback;
            this.mHelpColor = i2;
            this.mOnFirstBucketFailedColor = i2;
        } else {
            this.mHelpColor = this.mEnrollProgressHelp;
            this.mOnFirstBucketFailedColor = this.mMovingTargetFillError;
        }
        Drawable drawable = context.getDrawable(R$drawable.udfps_enroll_checkmark);
        this.mCheckmarkDrawable = drawable;
        drawable.mutate();
        this.mCheckmarkInterpolator = new OvershootInterpolator();
        Paint paint = new Paint();
        this.mBackgroundPaint = paint;
        paint.setStrokeWidth(dpToPixels);
        paint.setColor(this.mMovingTargetFill);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        Paint paint2 = new Paint();
        this.mFillPaint = paint2;
        paint2.setStrokeWidth(dpToPixels);
        paint2.setColor(i);
        paint2.setAntiAlias(true);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeCap(Paint.Cap.ROUND);
        this.mVibrator = (Vibrator) context.getSystemService(Vibrator.class);
        this.mProgressUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.UdfpsEnrollProgressBarDrawable$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                UdfpsEnrollProgressBarDrawable.$r8$lambda$QuF4aU4rKy4Seup1XvknLQvv9os(UdfpsEnrollProgressBarDrawable.this, valueAnimator);
            }
        };
        this.mFillColorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.UdfpsEnrollProgressBarDrawable$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                UdfpsEnrollProgressBarDrawable.$r8$lambda$vIik3M3fPGv3_LFfEy5XpHpHGoA(UdfpsEnrollProgressBarDrawable.this, valueAnimator);
            }
        };
        this.mCheckmarkUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.UdfpsEnrollProgressBarDrawable$$ExternalSyntheticLambda2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                UdfpsEnrollProgressBarDrawable.$r8$lambda$pUH1NyJyx0vTHQnAiayq_xvF0nk(UdfpsEnrollProgressBarDrawable.this, valueAnimator);
            }
        };
        this.mBackgroundColorUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.UdfpsEnrollProgressBarDrawable$$ExternalSyntheticLambda3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                UdfpsEnrollProgressBarDrawable.$r8$lambda$zJzd5FlAu5Z3PW81NQFYkOHDi40(UdfpsEnrollProgressBarDrawable.this, valueAnimator);
            }
        };
    }

    public /* synthetic */ void lambda$new$0(ValueAnimator valueAnimator) {
        this.mProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidateSelf();
    }

    public /* synthetic */ void lambda$new$1(ValueAnimator valueAnimator) {
        this.mFillPaint.setColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
        invalidateSelf();
    }

    public /* synthetic */ void lambda$new$2(ValueAnimator valueAnimator) {
        this.mCheckmarkScale = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidateSelf();
    }

    public /* synthetic */ void lambda$new$3(ValueAnimator valueAnimator) {
        this.mBackgroundPaint.setColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
        invalidateSelf();
    }

    public final void animateBackgroundColor() {
        ValueAnimator valueAnimator = this.mBackgroundColorAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mBackgroundColorAnimator.end();
        }
        ValueAnimator ofArgb = ValueAnimator.ofArgb(this.mBackgroundPaint.getColor(), this.mOnFirstBucketFailedColor);
        this.mBackgroundColorAnimator = ofArgb;
        ofArgb.setDuration(350L);
        this.mBackgroundColorAnimator.setRepeatCount(1);
        this.mBackgroundColorAnimator.setRepeatMode(2);
        this.mBackgroundColorAnimator.setInterpolator(DEACCEL);
        this.mBackgroundColorAnimator.addUpdateListener(this.mBackgroundColorUpdateListener);
        this.mBackgroundColorAnimator.start();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.rotate(-90.0f, getBounds().centerX(), getBounds().centerY());
        float f = this.mStrokeWidthPx / 2.0f;
        if (this.mProgress < 1.0f) {
            canvas.drawArc(f, f, getBounds().right - f, getBounds().bottom - f, ActionBarShadowController.ELEVATION_LOW, 360.0f, false, this.mBackgroundPaint);
        }
        if (this.mProgress > ActionBarShadowController.ELEVATION_LOW) {
            canvas.drawArc(f, f, getBounds().right - f, getBounds().bottom - f, ActionBarShadowController.ELEVATION_LOW, this.mProgress * 360.0f, false, this.mFillPaint);
        }
        canvas.restore();
        if (this.mCheckmarkScale > ActionBarShadowController.ELEVATION_LOW) {
            float sqrt = ((float) Math.sqrt(2.0d)) / 2.0f;
            float width = (getBounds().width() - this.mStrokeWidthPx) / 2.0f;
            float height = (getBounds().height() - this.mStrokeWidthPx) / 2.0f;
            float centerX = getBounds().centerX() + (width * sqrt);
            float centerY = getBounds().centerY() + (height * sqrt);
            float intrinsicWidth = (this.mCheckmarkDrawable.getIntrinsicWidth() / 2.0f) * this.mCheckmarkScale;
            float intrinsicHeight = (this.mCheckmarkDrawable.getIntrinsicHeight() / 2.0f) * this.mCheckmarkScale;
            this.mCheckmarkDrawable.setBounds(Math.round(centerX - intrinsicWidth), Math.round(centerY - intrinsicHeight), Math.round(centerX + intrinsicWidth), Math.round(centerY + intrinsicHeight));
            this.mCheckmarkDrawable.draw(canvas);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return 0;
    }

    public void loadResources(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.BiometricsEnrollView, R$attr.biometricsEnrollStyle, R$style.BiometricsEnrollStyle);
        this.mMovingTargetFill = obtainStyledAttributes.getColor(R$styleable.BiometricsEnrollView_biometricsMovingTargetFill, 0);
        this.mMovingTargetFillError = obtainStyledAttributes.getColor(R$styleable.BiometricsEnrollView_biometricsMovingTargetFillError, 0);
        this.mEnrollProgress = obtainStyledAttributes.getColor(R$styleable.BiometricsEnrollView_biometricsEnrollProgress, 0);
        this.mEnrollProgressHelp = obtainStyledAttributes.getColor(R$styleable.BiometricsEnrollView_biometricsEnrollProgressHelp, 0);
        this.mEnrollProgressHelpWithTalkback = obtainStyledAttributes.getColor(R$styleable.BiometricsEnrollView_biometricsEnrollProgressHelpWithTalkback, 0);
        obtainStyledAttributes.recycle();
    }

    public void onEnrollmentHelp(int i, int i2) {
        updateState(i, i2, true);
    }

    public void onEnrollmentProgress(int i, int i2) {
        this.mAfterFirstTouch = true;
        updateState(i, i2, false);
    }

    public void onLastStepAcquired() {
        updateState(0, this.mTotalSteps, false);
    }

    public final void rollBackCompletionAnimation() {
        if (this.mComplete) {
            this.mComplete = false;
            ValueAnimator valueAnimator = this.mCheckmarkAnimator;
            long round = Math.round((valueAnimator != null ? valueAnimator.getAnimatedFraction() : 0.0f) * 200.0f);
            ValueAnimator valueAnimator2 = this.mCheckmarkAnimator;
            if (valueAnimator2 != null && valueAnimator2.isRunning()) {
                this.mCheckmarkAnimator.cancel();
            }
            ValueAnimator ofFloat = ValueAnimator.ofFloat(this.mCheckmarkScale, ActionBarShadowController.ELEVATION_LOW);
            this.mCheckmarkAnimator = ofFloat;
            ofFloat.setDuration(round);
            this.mCheckmarkAnimator.addUpdateListener(this.mCheckmarkUpdateListener);
            this.mCheckmarkAnimator.start();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    public final void startCompletionAnimation() {
        if (this.mComplete) {
            return;
        }
        this.mComplete = true;
        ValueAnimator valueAnimator = this.mCheckmarkAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mCheckmarkAnimator.cancel();
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.mCheckmarkScale, 1.0f);
        this.mCheckmarkAnimator = ofFloat;
        ofFloat.setStartDelay(200L);
        this.mCheckmarkAnimator.setDuration(300L);
        this.mCheckmarkAnimator.setInterpolator(this.mCheckmarkInterpolator);
        this.mCheckmarkAnimator.addUpdateListener(this.mCheckmarkUpdateListener);
        this.mCheckmarkAnimator.start();
    }

    public final void updateFillColor(boolean z) {
        if (!this.mAfterFirstTouch && z) {
            animateBackgroundColor();
            return;
        }
        ValueAnimator valueAnimator = this.mFillColorAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mFillColorAnimator.end();
        }
        ValueAnimator ofArgb = ValueAnimator.ofArgb(this.mFillPaint.getColor(), z ? this.mHelpColor : this.mProgressColor);
        this.mFillColorAnimator = ofArgb;
        ofArgb.setDuration(350L);
        this.mFillColorAnimator.setRepeatCount(1);
        this.mFillColorAnimator.setRepeatMode(2);
        this.mFillColorAnimator.setInterpolator(DEACCEL);
        this.mFillColorAnimator.addUpdateListener(this.mFillColorUpdateListener);
        this.mFillColorAnimator.start();
    }

    public final void updateProgress(int i, int i2, boolean z) {
        if (this.mRemainingSteps == i && this.mTotalSteps == i2) {
            return;
        }
        if (this.mShowingHelp) {
            Vibrator vibrator = this.mVibrator;
            if (vibrator != null && this.mIsAccessibilityEnabled) {
                int myUid = Process.myUid();
                String opPackageName = this.mContext.getOpPackageName();
                VibrationEffect vibrationEffect = VIBRATE_EFFECT_ERROR;
                vibrator.vibrate(myUid, opPackageName, vibrationEffect, getClass().getSimpleName() + "::onEnrollmentHelp", FINGERPRINT_ENROLLING_SONFICATION_ATTRIBUTES);
            }
        } else {
            Vibrator vibrator2 = this.mVibrator;
            if (vibrator2 != null) {
                if (i == -1 && this.mIsAccessibilityEnabled) {
                    int myUid2 = Process.myUid();
                    String opPackageName2 = this.mContext.getOpPackageName();
                    VibrationEffect vibrationEffect2 = VIBRATE_EFFECT_ERROR;
                    vibrator2.vibrate(myUid2, opPackageName2, vibrationEffect2, getClass().getSimpleName() + "::onFirstTouchError", FINGERPRINT_ENROLLING_SONFICATION_ATTRIBUTES);
                } else if (i != -1 && !this.mIsAccessibilityEnabled) {
                    int myUid3 = Process.myUid();
                    String opPackageName3 = this.mContext.getOpPackageName();
                    VibrationEffect vibrationEffect3 = SUCCESS_VIBRATION_EFFECT;
                    vibrator2.vibrate(myUid3, opPackageName3, vibrationEffect3, getClass().getSimpleName() + "::OnEnrollmentProgress", HARDWARE_FEEDBACK_VIBRATION_ATTRIBUTES);
                }
            }
        }
        this.mRemainingSteps = i;
        this.mTotalSteps = i2;
        int max = Math.max(0, i2 - i);
        boolean z2 = this.mAfterFirstTouch;
        int i3 = max;
        if (z2) {
            i3 = max + 1;
        }
        float min = Math.min(1.0f, i3 / (z2 ? this.mTotalSteps + 1 : this.mTotalSteps));
        ValueAnimator valueAnimator = this.mProgressAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mProgressAnimator.cancel();
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.mProgress, min);
        this.mProgressAnimator = ofFloat;
        ofFloat.setDuration(400L);
        this.mProgressAnimator.addUpdateListener(this.mProgressUpdateListener);
        this.mProgressAnimator.start();
        if (i == 0) {
            startCompletionAnimation();
        } else if (i > 0) {
            rollBackCompletionAnimation();
        }
    }

    public final void updateState(int i, int i2, boolean z) {
        updateProgress(i, i2, z);
        updateFillColor(z);
    }
}