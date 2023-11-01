package com.android.systemui.media.controls.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.MathUtils;
import com.android.internal.graphics.ColorUtils;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.animation.Interpolators;
import kotlin.jvm.functions.Function2;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/SquigglyProgress.class */
public final class SquigglyProgress extends Drawable {
    public boolean animate;
    public ValueAnimator heightAnimator;
    public float heightFraction;
    public long lastFrameTime;
    public float lineAmplitude;
    public final Paint linePaint;
    public final float matchedWaveEndpoint;
    public final float minWaveEndpoint;
    public final Path path;
    public float phaseOffset;
    public float phaseSpeed;
    public float strokeWidth;
    public boolean transitionEnabled;
    public final float transitionPeriods;
    public float waveLength;
    public final Paint wavePaint;

    public SquigglyProgress() {
        Paint paint = new Paint();
        this.wavePaint = paint;
        Paint paint2 = new Paint();
        this.linePaint = paint2;
        this.path = new Path();
        this.lastFrameTime = -1L;
        this.transitionPeriods = 1.5f;
        this.minWaveEndpoint = 0.2f;
        this.matchedWaveEndpoint = 0.6f;
        this.transitionEnabled = true;
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint2.setStrokeCap(Paint.Cap.ROUND);
        paint2.setStyle(Paint.Style.STROKE);
        paint.setStyle(Paint.Style.STROKE);
        paint2.setAlpha(77);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.SquigglyProgress$draw$computeAmplitude$1.invoke(float, float):java.lang.Float] */
    public static final /* synthetic */ float access$getHeightFraction$p(SquigglyProgress squigglyProgress) {
        return squigglyProgress.heightFraction;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.SquigglyProgress$draw$computeAmplitude$1.invoke(float, float):java.lang.Float] */
    public static final /* synthetic */ float access$getTransitionPeriods$p(SquigglyProgress squigglyProgress) {
        return squigglyProgress.transitionPeriods;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.SquigglyProgress$animate$1$2.onAnimationEnd(android.animation.Animator):void] */
    public static final /* synthetic */ void access$setHeightAnimator$p(SquigglyProgress squigglyProgress, ValueAnimator valueAnimator) {
        squigglyProgress.heightAnimator = valueAnimator;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.media.controls.ui.SquigglyProgress$animate$1$1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static final /* synthetic */ void access$setHeightFraction$p(SquigglyProgress squigglyProgress, float f) {
        squigglyProgress.heightFraction = f;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (this.animate) {
            invalidateSelf();
            long uptimeMillis = SystemClock.uptimeMillis();
            this.phaseOffset = (this.phaseOffset + ((((float) (uptimeMillis - this.lastFrameTime)) / 1000.0f) * this.phaseSpeed)) % this.waveLength;
            this.lastFrameTime = uptimeMillis;
        }
        float level = getLevel() / 10000.0f;
        float width = getBounds().width();
        float f = width * level;
        float f2 = level;
        if (this.transitionEnabled) {
            float f3 = this.matchedWaveEndpoint;
            f2 = level > f3 ? level : MathUtils.lerp(this.minWaveEndpoint, f3, MathUtils.lerpInv((float) ActionBarShadowController.ELEVATION_LOW, f3, level));
        }
        final float f4 = f2 * width;
        float f5 = (-this.phaseOffset) - (this.waveLength / 2.0f);
        float f6 = this.transitionEnabled ? width : f4;
        Function2<Float, Float, Float> function2 = new Function2<Float, Float, Float>() { // from class: com.android.systemui.media.controls.ui.SquigglyProgress$draw$computeAmplitude$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            public final Float invoke(float f7, float f8) {
                float access$getHeightFraction$p;
                if (SquigglyProgress.this.getTransitionEnabled()) {
                    float access$getTransitionPeriods$p = SquigglyProgress.access$getTransitionPeriods$p(SquigglyProgress.this);
                    float waveLength = SquigglyProgress.this.getWaveLength();
                    float f9 = f4;
                    float f10 = (access$getTransitionPeriods$p * waveLength) / 2.0f;
                    access$getHeightFraction$p = f8 * SquigglyProgress.access$getHeightFraction$p(SquigglyProgress.this) * SquigglyProgress.this.getLineAmplitude() * MathUtils.lerpInvSat(f9 + f10, f9 - f10, f7);
                } else {
                    access$getHeightFraction$p = f8 * SquigglyProgress.access$getHeightFraction$p(SquigglyProgress.this) * SquigglyProgress.this.getLineAmplitude();
                }
                return Float.valueOf(access$getHeightFraction$p);
            }

            public /* bridge */ /* synthetic */ Object invoke(Object obj, Object obj2) {
                return invoke(((Number) obj).floatValue(), ((Number) obj2).floatValue());
            }
        };
        this.path.rewind();
        this.path.moveTo(f5, ActionBarShadowController.ELEVATION_LOW);
        float f7 = 1.0f;
        float floatValue = ((Number) function2.invoke(Float.valueOf(f5), Float.valueOf(1.0f))).floatValue();
        float f8 = this.waveLength / 2.0f;
        float f9 = f5;
        while (true) {
            float f10 = f9;
            if (f10 >= f6) {
                break;
            }
            f7 = -f7;
            float f11 = f10 + f8;
            float f12 = f10 + (f8 / 2);
            float floatValue2 = ((Number) function2.invoke(Float.valueOf(f11), Float.valueOf(f7))).floatValue();
            this.path.cubicTo(f12, floatValue, f12, floatValue2, f11, floatValue2);
            floatValue = floatValue2;
            f9 = f11;
        }
        float f13 = this.lineAmplitude + this.strokeWidth;
        canvas.save();
        canvas.translate(getBounds().left, getBounds().centerY());
        canvas.save();
        float f14 = (-1.0f) * f13;
        canvas.clipRect(ActionBarShadowController.ELEVATION_LOW, f14, f, f13);
        canvas.drawPath(this.path, this.wavePaint);
        canvas.restore();
        if (this.transitionEnabled) {
            canvas.save();
            canvas.clipRect(f, f14, width, f13);
            canvas.drawPath(this.path, this.linePaint);
            canvas.restore();
        } else {
            canvas.drawLine(f, ActionBarShadowController.ELEVATION_LOW, width, ActionBarShadowController.ELEVATION_LOW, this.linePaint);
        }
        canvas.drawPoint(ActionBarShadowController.ELEVATION_LOW, ((float) Math.cos((Math.abs(f5) / this.waveLength) * 6.2831855f)) * this.lineAmplitude * this.heightFraction, this.wavePaint);
        canvas.restore();
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.wavePaint.getAlpha();
    }

    public final float getLineAmplitude() {
        return this.lineAmplitude;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    public final boolean getTransitionEnabled() {
        return this.transitionEnabled;
    }

    public final float getWaveLength() {
        return this.waveLength;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean onLevelChange(int i) {
        return this.animate;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        updateColors(this.wavePaint.getColor(), i);
    }

    public final void setAnimate(boolean z) {
        if (this.animate == z) {
            return;
        }
        this.animate = z;
        if (z) {
            this.lastFrameTime = SystemClock.uptimeMillis();
        }
        ValueAnimator valueAnimator = this.heightAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.heightFraction, this.animate ? 1.0f : 0.0f);
        if (this.animate) {
            ofFloat.setStartDelay(60L);
            ofFloat.setDuration(800L);
            ofFloat.setInterpolator(Interpolators.EMPHASIZED_DECELERATE);
        } else {
            ofFloat.setDuration(550L);
            ofFloat.setInterpolator(Interpolators.STANDARD_DECELERATE);
        }
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.media.controls.ui.SquigglyProgress$animate$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                SquigglyProgress.access$setHeightFraction$p(SquigglyProgress.this, ((Float) valueAnimator2.getAnimatedValue()).floatValue());
                SquigglyProgress.this.invalidateSelf();
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.media.controls.ui.SquigglyProgress$animate$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                SquigglyProgress.access$setHeightAnimator$p(SquigglyProgress.this, null);
            }
        });
        ofFloat.start();
        this.heightAnimator = ofFloat;
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.wavePaint.setColorFilter(colorFilter);
        this.linePaint.setColorFilter(colorFilter);
    }

    public final void setLineAmplitude(float f) {
        this.lineAmplitude = f;
    }

    public final void setPhaseSpeed(float f) {
        this.phaseSpeed = f;
    }

    public final void setStrokeWidth(float f) {
        if (this.strokeWidth == f) {
            return;
        }
        this.strokeWidth = f;
        this.wavePaint.setStrokeWidth(f);
        this.linePaint.setStrokeWidth(f);
    }

    @Override // android.graphics.drawable.Drawable
    public void setTint(int i) {
        updateColors(i, getAlpha());
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(ColorStateList colorStateList) {
        if (colorStateList == null) {
            return;
        }
        updateColors(colorStateList.getDefaultColor(), getAlpha());
    }

    public final void setTransitionEnabled(boolean z) {
        this.transitionEnabled = z;
        invalidateSelf();
    }

    public final void setWaveLength(float f) {
        this.waveLength = f;
    }

    public final void updateColors(int i, int i2) {
        this.wavePaint.setColor(ColorUtils.setAlphaComponent(i, i2));
        this.linePaint.setColor(ColorUtils.setAlphaComponent(i, (int) (77 * (i2 / 255.0f))));
    }
}