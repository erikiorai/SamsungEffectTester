package com.android.systemui.biometrics;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.PathInterpolator;
import com.android.internal.graphics.ColorUtils;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.surfaceeffects.ripple.RippleShader;
import kotlin.comparisons.ComparisonsKt___ComparisonsJvmKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/AuthRippleView.class */
public final class AuthRippleView extends View {
    public long alphaInDuration;
    public boolean drawDwell;
    public boolean drawRipple;
    public final long dwellExpandDuration;
    public Point dwellOrigin;
    public final Paint dwellPaint;
    public final long dwellPulseDuration;
    public Animator dwellPulseOutAnimator;
    public float dwellRadius;
    public final DwellRippleShader dwellShader;
    public final long fadeDuration;
    public Animator fadeDwellAnimator;
    public int lockScreenColorVal;
    public Point origin;
    public float radius;
    public final long retractDuration;
    public Animator retractDwellAnimator;
    public final PathInterpolator retractInterpolator;
    public final Paint ripplePaint;
    public final RippleShader rippleShader;
    public boolean unlockedRippleInProgress;

    /* JADX DEBUG: Multi-variable search result rejected for r0v9, resolved type: android.graphics.Paint */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.systemui.biometrics.DwellRippleShader, android.graphics.Shader] */
    public AuthRippleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.retractInterpolator = new PathInterpolator(0.05f, 0.93f, 0.1f, 1.0f);
        this.dwellPulseDuration = 100L;
        this.dwellExpandDuration = 2000 - 100;
        this.lockScreenColorVal = -1;
        this.fadeDuration = 83L;
        this.retractDuration = 400L;
        ?? dwellRippleShader = new DwellRippleShader();
        this.dwellShader = dwellRippleShader;
        Paint paint = new Paint();
        this.dwellPaint = paint;
        Shader rippleShader = new RippleShader((RippleShader.RippleShape) null, 1, (DefaultConstructorMarker) null);
        this.rippleShader = rippleShader;
        Paint paint2 = new Paint();
        this.ripplePaint = paint2;
        this.dwellOrigin = new Point();
        this.origin = new Point();
        rippleShader.setColor(com.android.settingslib.Utils.getColorAttr(context, 16843829).getDefaultColor());
        rippleShader.setProgress((float) ActionBarShadowController.ELEVATION_LOW);
        rippleShader.setSparkleStrength(0.4f);
        paint2.setShader(rippleShader);
        dwellRippleShader.setColor(-1);
        dwellRippleShader.setProgress(ActionBarShadowController.ELEVATION_LOW);
        dwellRippleShader.setDistortionStrength(0.4f);
        paint.setShader(dwellRippleShader);
        setVisibility(8);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthRippleView$fadeDwellRipple$1$2.onAnimationStart(android.animation.Animator):void, com.android.systemui.biometrics.AuthRippleView$retractDwellRipple$1$1.onAnimationStart(android.animation.Animator):void] */
    public static final /* synthetic */ Animator access$getDwellPulseOutAnimator$p(AuthRippleView authRippleView) {
        return authRippleView.dwellPulseOutAnimator;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthRippleView$fadeDwellRipple$1$1.onAnimationUpdate(android.animation.ValueAnimator):void, com.android.systemui.biometrics.AuthRippleView$retractDwellRipple$retractAlphaAnimator$1$1.onAnimationUpdate(android.animation.ValueAnimator):void, com.android.systemui.biometrics.AuthRippleView$retractDwellRipple$retractDwellRippleAnimator$1$1.onAnimationUpdate(android.animation.ValueAnimator):void, com.android.systemui.biometrics.AuthRippleView$startDwellRipple$dwellPulseOutRippleAnimator$1$1.onAnimationUpdate(android.animation.ValueAnimator):void, com.android.systemui.biometrics.AuthRippleView$startDwellRipple$expandDwellRippleAnimator$1$1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static final /* synthetic */ DwellRippleShader access$getDwellShader$p(AuthRippleView authRippleView) {
        return authRippleView.dwellShader;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthRippleView$startDwellRipple$1$1.onAnimationStart(android.animation.Animator):void] */
    public static final /* synthetic */ Animator access$getFadeDwellAnimator$p(AuthRippleView authRippleView) {
        return authRippleView.fadeDwellAnimator;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthRippleView$fadeDwellRipple$1$2.onAnimationStart(android.animation.Animator):void, com.android.systemui.biometrics.AuthRippleView$startDwellRipple$1$1.onAnimationStart(android.animation.Animator):void] */
    public static final /* synthetic */ Animator access$getRetractDwellAnimator$p(AuthRippleView authRippleView) {
        return authRippleView.retractDwellAnimator;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthRippleView$startUnlockedRipple$alphaInAnimator$1$1.onAnimationUpdate(android.animation.ValueAnimator):void, com.android.systemui.biometrics.AuthRippleView$startUnlockedRipple$animatorSet$1$1.onAnimationStart(android.animation.Animator):void, com.android.systemui.biometrics.AuthRippleView$startUnlockedRipple$rippleAnimator$1$1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static final /* synthetic */ RippleShader access$getRippleShader$p(AuthRippleView authRippleView) {
        return authRippleView.rippleShader;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthRippleView$fadeDwellRipple$1$2.onAnimationEnd(android.animation.Animator):void, com.android.systemui.biometrics.AuthRippleView$fadeDwellRipple$1$2.onAnimationStart(android.animation.Animator):void, com.android.systemui.biometrics.AuthRippleView$retractDwellRipple$1$1.onAnimationEnd(android.animation.Animator):void, com.android.systemui.biometrics.AuthRippleView$retractDwellRipple$1$1.onAnimationStart(android.animation.Animator):void, com.android.systemui.biometrics.AuthRippleView$startDwellRipple$1$1.onAnimationEnd(android.animation.Animator):void, com.android.systemui.biometrics.AuthRippleView$startDwellRipple$1$1.onAnimationStart(android.animation.Animator):void] */
    public static final /* synthetic */ void access$setDrawDwell$p(AuthRippleView authRippleView, boolean z) {
        authRippleView.drawDwell = z;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthRippleView$startUnlockedRipple$animatorSet$1$1.onAnimationEnd(android.animation.Animator):void, com.android.systemui.biometrics.AuthRippleView$startUnlockedRipple$animatorSet$1$1.onAnimationStart(android.animation.Animator):void] */
    public static final /* synthetic */ void access$setDrawRipple$p(AuthRippleView authRippleView, boolean z) {
        authRippleView.drawRipple = z;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthRippleView$startUnlockedRipple$animatorSet$1$1.onAnimationEnd(android.animation.Animator):void, com.android.systemui.biometrics.AuthRippleView$startUnlockedRipple$animatorSet$1$1.onAnimationStart(android.animation.Animator):void] */
    public static final /* synthetic */ void access$setUnlockedRippleInProgress$p(AuthRippleView authRippleView, boolean z) {
        authRippleView.unlockedRippleInProgress = z;
    }

    public final void fadeDwellRipple() {
        Animator animator = this.fadeDwellAnimator;
        if (animator != null && animator.isRunning()) {
            return;
        }
        Animator animator2 = this.dwellPulseOutAnimator;
        if (!(animator2 != null && animator2.isRunning())) {
            Animator animator3 = this.retractDwellAnimator;
            if (!(animator3 != null && animator3.isRunning())) {
                return;
            }
        }
        ValueAnimator ofInt = ValueAnimator.ofInt(Color.alpha(this.dwellShader.getColor()), 0);
        ofInt.setInterpolator(Interpolators.LINEAR);
        ofInt.setDuration(this.fadeDuration);
        ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.AuthRippleView$fadeDwellRipple$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                AuthRippleView.access$getDwellShader$p(AuthRippleView.this).setColor(ColorUtils.setAlphaComponent(AuthRippleView.access$getDwellShader$p(AuthRippleView.this).getColor(), ((Integer) valueAnimator.getAnimatedValue()).intValue()));
                AuthRippleView.this.invalidate();
            }
        });
        ofInt.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.biometrics.AuthRippleView$fadeDwellRipple$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator4) {
                AuthRippleView.access$setDrawDwell$p(AuthRippleView.this, false);
                AuthRippleView.this.resetDwellAlpha();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator4) {
                Animator access$getRetractDwellAnimator$p = AuthRippleView.access$getRetractDwellAnimator$p(AuthRippleView.this);
                if (access$getRetractDwellAnimator$p != null) {
                    access$getRetractDwellAnimator$p.cancel();
                }
                Animator access$getDwellPulseOutAnimator$p = AuthRippleView.access$getDwellPulseOutAnimator$p(AuthRippleView.this);
                if (access$getDwellPulseOutAnimator$p != null) {
                    access$getDwellPulseOutAnimator$p.cancel();
                }
                AuthRippleView.access$setDrawDwell$p(AuthRippleView.this, true);
            }
        });
        ofInt.start();
        this.fadeDwellAnimator = ofInt;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        if (this.drawDwell) {
            float f = 1;
            float progress = this.dwellShader.getProgress();
            float progress2 = this.dwellShader.getProgress();
            float progress3 = this.dwellShader.getProgress();
            float f2 = this.dwellRadius;
            if (canvas != null) {
                Point point = this.dwellOrigin;
                canvas.drawCircle(point.x, point.y, (f - (((f - progress) * (f - progress2)) * (f - progress3))) * f2 * 2.0f, this.dwellPaint);
            }
        }
        if (this.drawRipple) {
            float f3 = 1;
            float progress4 = this.rippleShader.getProgress();
            float progress5 = this.rippleShader.getProgress();
            float progress6 = this.rippleShader.getProgress();
            float f4 = this.radius;
            if (canvas != null) {
                Point point2 = this.origin;
                canvas.drawCircle(point2.x, point2.y, (f3 - (((f3 - progress4) * (f3 - progress5)) * (f3 - progress6))) * f4 * 2.0f, this.ripplePaint);
            }
        }
    }

    public final void resetDwellAlpha() {
        DwellRippleShader dwellRippleShader = this.dwellShader;
        dwellRippleShader.setColor(ColorUtils.setAlphaComponent(dwellRippleShader.getColor(), 255));
    }

    public final void resetRippleAlpha() {
        RippleShader rippleShader = this.rippleShader;
        rippleShader.setColor(ColorUtils.setAlphaComponent(rippleShader.getColor(), 255));
    }

    public final void retractDwellRipple() {
        Animator animator = this.retractDwellAnimator;
        if (animator != null && animator.isRunning()) {
            return;
        }
        Animator animator2 = this.fadeDwellAnimator;
        if (animator2 != null && animator2.isRunning()) {
            return;
        }
        Animator animator3 = this.dwellPulseOutAnimator;
        if (animator3 != null && animator3.isRunning()) {
            ValueAnimator ofFloat = ValueAnimator.ofFloat(this.dwellShader.getProgress(), ActionBarShadowController.ELEVATION_LOW);
            ofFloat.setInterpolator(this.retractInterpolator);
            ofFloat.setDuration(this.retractDuration);
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.AuthRippleView$retractDwellRipple$retractDwellRippleAnimator$1$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    long currentPlayTime = valueAnimator.getCurrentPlayTime();
                    AuthRippleView.access$getDwellShader$p(AuthRippleView.this).setProgress(((Float) valueAnimator.getAnimatedValue()).floatValue());
                    AuthRippleView.access$getDwellShader$p(AuthRippleView.this).setTime((float) currentPlayTime);
                    AuthRippleView.this.invalidate();
                }
            });
            ValueAnimator ofInt = ValueAnimator.ofInt(255, 0);
            ofInt.setInterpolator(Interpolators.LINEAR);
            ofInt.setDuration(this.retractDuration);
            ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.AuthRippleView$retractDwellRipple$retractAlphaAnimator$1$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    AuthRippleView.access$getDwellShader$p(AuthRippleView.this).setColor(ColorUtils.setAlphaComponent(AuthRippleView.access$getDwellShader$p(AuthRippleView.this).getColor(), ((Integer) valueAnimator.getAnimatedValue()).intValue()));
                    AuthRippleView.this.invalidate();
                }
            });
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(ofFloat, ofInt);
            animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.biometrics.AuthRippleView$retractDwellRipple$1$1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator4) {
                    AuthRippleView.access$setDrawDwell$p(AuthRippleView.this, false);
                    AuthRippleView.this.resetDwellAlpha();
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator4) {
                    Animator access$getDwellPulseOutAnimator$p = AuthRippleView.access$getDwellPulseOutAnimator$p(AuthRippleView.this);
                    if (access$getDwellPulseOutAnimator$p != null) {
                        access$getDwellPulseOutAnimator$p.cancel();
                    }
                    AuthRippleView.access$setDrawDwell$p(AuthRippleView.this, true);
                }
            });
            animatorSet.start();
            this.retractDwellAnimator = animatorSet;
        }
    }

    public final void setAlphaInDuration(long j) {
        this.alphaInDuration = j;
    }

    public final void setDwellOrigin(Point point) {
        this.dwellShader.setOrigin(point);
        this.dwellOrigin = point;
    }

    public final void setDwellRadius(float f) {
        this.dwellShader.setMaxRadius(f);
        this.dwellRadius = f;
    }

    public final void setFingerprintSensorLocation(Point point, float f) {
        setOrigin(point);
        setRadius(ComparisonsKt___ComparisonsJvmKt.maxOf(point.x, new int[]{point.y, getWidth() - point.x, getHeight() - point.y}));
        setDwellOrigin(point);
        setDwellRadius(f * 1.5f);
    }

    public final void setLockScreenColor(int i) {
        this.lockScreenColorVal = i;
        this.rippleShader.setColor(i);
        resetRippleAlpha();
    }

    public final void setOrigin(Point point) {
        this.rippleShader.setCenter(point.x, point.y);
        this.origin = point;
    }

    public final void setRadius(float f) {
        float f2 = 2.0f * f;
        this.rippleShader.setMaxSize(f2, f2);
        this.radius = f;
    }

    public final void setSensorLocation(Point point) {
        setOrigin(point);
        setRadius(ComparisonsKt___ComparisonsJvmKt.maxOf(point.x, new int[]{point.y, getWidth() - point.x, getHeight() - point.y}));
    }

    public final void startDwellRipple(boolean z) {
        if (this.unlockedRippleInProgress) {
            return;
        }
        Animator animator = this.dwellPulseOutAnimator;
        if (animator != null && animator.isRunning()) {
            return;
        }
        updateDwellRippleColor(z);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 0.8f);
        ofFloat.setInterpolator(Interpolators.LINEAR);
        ofFloat.setDuration(this.dwellPulseDuration);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.AuthRippleView$startDwellRipple$dwellPulseOutRippleAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                long currentPlayTime = valueAnimator.getCurrentPlayTime();
                AuthRippleView.access$getDwellShader$p(AuthRippleView.this).setProgress(((Float) valueAnimator.getAnimatedValue()).floatValue());
                AuthRippleView.access$getDwellShader$p(AuthRippleView.this).setTime((float) currentPlayTime);
                AuthRippleView.this.invalidate();
            }
        });
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.8f, 1.0f);
        ofFloat2.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
        ofFloat2.setDuration(this.dwellExpandDuration);
        ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.AuthRippleView$startDwellRipple$expandDwellRippleAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                long currentPlayTime = valueAnimator.getCurrentPlayTime();
                AuthRippleView.access$getDwellShader$p(AuthRippleView.this).setProgress(((Float) valueAnimator.getAnimatedValue()).floatValue());
                AuthRippleView.access$getDwellShader$p(AuthRippleView.this).setTime((float) currentPlayTime);
                AuthRippleView.this.invalidate();
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(ofFloat, ofFloat2);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.biometrics.AuthRippleView$startDwellRipple$1$1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator2) {
                AuthRippleView.access$setDrawDwell$p(AuthRippleView.this, false);
                AuthRippleView.this.resetRippleAlpha();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator2) {
                Animator access$getRetractDwellAnimator$p = AuthRippleView.access$getRetractDwellAnimator$p(AuthRippleView.this);
                if (access$getRetractDwellAnimator$p != null) {
                    access$getRetractDwellAnimator$p.cancel();
                }
                Animator access$getFadeDwellAnimator$p = AuthRippleView.access$getFadeDwellAnimator$p(AuthRippleView.this);
                if (access$getFadeDwellAnimator$p != null) {
                    access$getFadeDwellAnimator$p.cancel();
                }
                AuthRippleView.this.setVisibility(0);
                AuthRippleView.access$setDrawDwell$p(AuthRippleView.this, true);
            }
        });
        animatorSet.start();
        this.dwellPulseOutAnimator = animatorSet;
    }

    public final void startUnlockedRipple(final Runnable runnable) {
        if (this.unlockedRippleInProgress) {
            return;
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
        ofFloat.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
        ofFloat.setDuration(1533L);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.AuthRippleView$startUnlockedRipple$rippleAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                long currentPlayTime = valueAnimator.getCurrentPlayTime();
                AuthRippleView.access$getRippleShader$p(AuthRippleView.this).setProgress(((Float) valueAnimator.getAnimatedValue()).floatValue());
                AuthRippleView.access$getRippleShader$p(AuthRippleView.this).setTime((float) currentPlayTime);
                AuthRippleView.this.invalidate();
            }
        });
        ValueAnimator ofInt = ValueAnimator.ofInt(0, 255);
        ofInt.setDuration(this.alphaInDuration);
        ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.AuthRippleView$startUnlockedRipple$alphaInAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                AuthRippleView.access$getRippleShader$p(AuthRippleView.this).setColor(ColorUtils.setAlphaComponent(AuthRippleView.access$getRippleShader$p(AuthRippleView.this).getColor(), ((Integer) valueAnimator.getAnimatedValue()).intValue()));
                AuthRippleView.this.invalidate();
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat, ofInt);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.biometrics.AuthRippleView$startUnlockedRipple$animatorSet$1$1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
                AuthRippleView.access$setUnlockedRippleInProgress$p(AuthRippleView.this, false);
                AuthRippleView.access$setDrawRipple$p(AuthRippleView.this, false);
                AuthRippleView.this.setVisibility(8);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                AuthRippleView.access$setUnlockedRippleInProgress$p(AuthRippleView.this, true);
                AuthRippleView.access$getRippleShader$p(AuthRippleView.this).setRippleFill(false);
                AuthRippleView.access$setDrawRipple$p(AuthRippleView.this, true);
                AuthRippleView.this.setVisibility(0);
            }
        });
        animatorSet.start();
    }

    public final void updateDwellRippleColor(boolean z) {
        if (z) {
            this.dwellShader.setColor(-1);
        } else {
            this.dwellShader.setColor(this.lockScreenColorVal);
        }
        resetDwellAlpha();
    }
}