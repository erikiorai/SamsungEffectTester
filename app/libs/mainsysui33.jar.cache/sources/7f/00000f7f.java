package com.android.systemui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.biometrics.BiometricSourceType;
import android.view.View;
import androidx.core.graphics.ColorUtils;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.settingslib.Utils;
import com.android.systemui.ScreenDecorations;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: mainsysui33.jar:com/android/systemui/FaceScanningOverlay.class */
public final class FaceScanningOverlay extends ScreenDecorations.DisplayCutoutView {
    public static final Companion Companion = new Companion(null);
    public ValueAnimator cameraProtectionAnimator;
    public int cameraProtectionColor;
    public boolean faceAuthSucceeded;
    public int faceScanningAnimColor;
    public Runnable hideOverlayRunnable;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final FaceScanningOverlay$keyguardUpdateMonitorCallback$1 keyguardUpdateMonitorCallback;
    public final Executor mainExecutor;
    public AnimatorSet rimAnimator;
    public final Paint rimPaint;
    public float rimProgress;
    public final RectF rimRect;
    public boolean showScanningAnim;
    public final StatusBarStateController statusBarStateController;

    /* loaded from: mainsysui33.jar:com/android/systemui/FaceScanningOverlay$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void scalePath(Path path, float f) {
            Matrix matrix = new Matrix();
            RectF rectF = new RectF();
            path.computeBounds(rectF, true);
            matrix.setScale(f, f, rectF.centerX(), rectF.centerY());
            path.transform(matrix);
        }
    }

    /* JADX WARN: Type inference failed for: r1v11, types: [com.android.systemui.FaceScanningOverlay$keyguardUpdateMonitorCallback$1] */
    public FaceScanningOverlay(Context context, int i, StatusBarStateController statusBarStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, Executor executor) {
        super(context, i);
        this.statusBarStateController = statusBarStateController;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mainExecutor = executor;
        this.rimPaint = new Paint();
        this.rimProgress = 0.5f;
        this.rimRect = new RectF();
        this.cameraProtectionColor = -16777216;
        this.faceScanningAnimColor = Utils.getColorAttrDefaultColor(context, R$attr.wallpaperTextColorAccent);
        setVisibility(4);
        this.keyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.FaceScanningOverlay$keyguardUpdateMonitorCallback$1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onBiometricAcquired(BiometricSourceType biometricSourceType, int i2) {
                if (biometricSourceType == BiometricSourceType.FACE) {
                    final FaceScanningOverlay faceScanningOverlay = FaceScanningOverlay.this;
                    faceScanningOverlay.post(new Runnable() { // from class: com.android.systemui.FaceScanningOverlay$keyguardUpdateMonitorCallback$1$onBiometricAcquired$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            FaceScanningOverlay.this.setFaceAuthSucceeded(false);
                        }
                    });
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onBiometricAuthFailed(BiometricSourceType biometricSourceType) {
                if (biometricSourceType == BiometricSourceType.FACE) {
                    final FaceScanningOverlay faceScanningOverlay = FaceScanningOverlay.this;
                    faceScanningOverlay.post(new Runnable() { // from class: com.android.systemui.FaceScanningOverlay$keyguardUpdateMonitorCallback$1$onBiometricAuthFailed$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            FaceScanningOverlay.this.setFaceAuthSucceeded(false);
                            FaceScanningOverlay.this.enableShowProtection(false);
                        }
                    });
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onBiometricAuthenticated(int i2, BiometricSourceType biometricSourceType, boolean z) {
                if (biometricSourceType == BiometricSourceType.FACE) {
                    final FaceScanningOverlay faceScanningOverlay = FaceScanningOverlay.this;
                    faceScanningOverlay.post(new Runnable() { // from class: com.android.systemui.FaceScanningOverlay$keyguardUpdateMonitorCallback$1$onBiometricAuthenticated$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            FaceScanningOverlay.this.setFaceAuthSucceeded(true);
                            FaceScanningOverlay.this.enableShowProtection(true);
                        }
                    });
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onBiometricError(int i2, String str, BiometricSourceType biometricSourceType) {
                if (biometricSourceType == BiometricSourceType.FACE) {
                    final FaceScanningOverlay faceScanningOverlay = FaceScanningOverlay.this;
                    faceScanningOverlay.post(new Runnable() { // from class: com.android.systemui.FaceScanningOverlay$keyguardUpdateMonitorCallback$1$onBiometricError$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            FaceScanningOverlay.this.setFaceAuthSucceeded(false);
                            FaceScanningOverlay.this.enableShowProtection(false);
                        }
                    });
                }
            }
        };
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.FaceScanningOverlay$createSuccessOpacityAnimator$1$2.onAnimationEnd(android.animation.Animator):void] */
    public static final /* synthetic */ Paint access$getRimPaint$p(FaceScanningOverlay faceScanningOverlay) {
        return faceScanningOverlay.rimPaint;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.FaceScanningOverlay$enableShowProtection$1$2.onAnimationEnd(android.animation.Animator):void, com.android.systemui.FaceScanningOverlay$enableShowProtection$2$1.onAnimationEnd(android.animation.Animator):void] */
    public static final /* synthetic */ boolean access$getShowScanningAnim$p(FaceScanningOverlay faceScanningOverlay) {
        return faceScanningOverlay.showScanningAnim;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.FaceScanningOverlay$enableShowProtection$1$2.onAnimationEnd(android.animation.Animator):void] */
    public static final /* synthetic */ void access$hide(FaceScanningOverlay faceScanningOverlay) {
        faceScanningOverlay.hide();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.FaceScanningOverlay$enableShowProtection$1$2.onAnimationEnd(android.animation.Animator):void] */
    public static final /* synthetic */ void access$setCameraProtectionAnimator$p(FaceScanningOverlay faceScanningOverlay, ValueAnimator valueAnimator) {
        faceScanningOverlay.cameraProtectionAnimator = valueAnimator;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.FaceScanningOverlay$enableShowProtection$2$1.onAnimationEnd(android.animation.Animator):void] */
    public static final /* synthetic */ void access$setRimAnimator$p(FaceScanningOverlay faceScanningOverlay, AnimatorSet animatorSet) {
        faceScanningOverlay.rimAnimator = animatorSet;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.FaceScanningOverlay$createRimDisappearAnimator$1$2.onAnimationEnd(android.animation.Animator):void] */
    public static final /* synthetic */ void access$setRimProgress$p(FaceScanningOverlay faceScanningOverlay, float f) {
        faceScanningOverlay.rimProgress = f;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.FaceScanningOverlay$enableShowProtection$1$1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static final /* synthetic */ void access$updateCameraProtectionProgress(FaceScanningOverlay faceScanningOverlay, ValueAnimator valueAnimator) {
        faceScanningOverlay.updateCameraProtectionProgress(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.FaceScanningOverlay$createSuccessOpacityAnimator$1$1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static final /* synthetic */ void access$updateRimAlpha(FaceScanningOverlay faceScanningOverlay, ValueAnimator valueAnimator) {
        faceScanningOverlay.updateRimAlpha(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.FaceScanningOverlay$createPulseAnimator$1$1.onAnimationUpdate(android.animation.ValueAnimator):void, com.android.systemui.FaceScanningOverlay$createRimAppearAnimator$1$1.onAnimationUpdate(android.animation.ValueAnimator):void, com.android.systemui.FaceScanningOverlay$createRimDisappearAnimator$1$1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static final /* synthetic */ void access$updateRimProgress(FaceScanningOverlay faceScanningOverlay, ValueAnimator valueAnimator) {
        faceScanningOverlay.updateRimProgress(valueAnimator);
    }

    public final AnimatorSet createFaceNotSuccessRimAnimator() {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(createRimDisappearAnimator(1.0f, 200L, Interpolators.STANDARD), this.cameraProtectionAnimator);
        return animatorSet;
    }

    public final AnimatorSet createFaceScanningRimAnimator() {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(this.cameraProtectionAnimator, createRimAppearAnimator(), createPulseAnimator());
        return animatorSet;
    }

    public final AnimatorSet createFaceSuccessRimAnimator() {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(createRimDisappearAnimator(1.25f, 400L, Interpolators.STANDARD_DECELERATE), createSuccessOpacityAnimator());
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(animatorSet, this.cameraProtectionAnimator);
        return animatorSet2;
    }

    public final ValueAnimator createPulseAnimator() {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(1.125f, 1.1f);
        ofFloat.setDuration(500L);
        ofFloat.setInterpolator(Interpolators.STANDARD);
        ofFloat.setRepeatCount(11);
        ofFloat.setRepeatMode(2);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.FaceScanningOverlay$createPulseAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                FaceScanningOverlay.access$updateRimProgress(FaceScanningOverlay.this, valueAnimator);
            }
        });
        return ofFloat;
    }

    public final ValueAnimator createRimAppearAnimator() {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 1.125f);
        ofFloat.setDuration(250L);
        ofFloat.setInterpolator(Interpolators.STANDARD_DECELERATE);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.FaceScanningOverlay$createRimAppearAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                FaceScanningOverlay.access$updateRimProgress(FaceScanningOverlay.this, valueAnimator);
            }
        });
        return ofFloat;
    }

    public final ValueAnimator createRimDisappearAnimator(float f, long j, TimeInterpolator timeInterpolator) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.rimProgress, f);
        ofFloat.setDuration(j);
        ofFloat.setInterpolator(timeInterpolator);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.FaceScanningOverlay$createRimDisappearAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                FaceScanningOverlay.access$updateRimProgress(FaceScanningOverlay.this, valueAnimator);
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.FaceScanningOverlay$createRimDisappearAnimator$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                FaceScanningOverlay.access$setRimProgress$p(FaceScanningOverlay.this, 0.5f);
                FaceScanningOverlay.this.invalidate();
            }
        });
        return ofFloat;
    }

    public final ValueAnimator createSuccessOpacityAnimator() {
        ValueAnimator ofInt = ValueAnimator.ofInt(255, 0);
        ofInt.setDuration(400L);
        ofInt.setInterpolator(Interpolators.LINEAR);
        ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.FaceScanningOverlay$createSuccessOpacityAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                FaceScanningOverlay.access$updateRimAlpha(FaceScanningOverlay.this, valueAnimator);
            }
        });
        ofInt.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.FaceScanningOverlay$createSuccessOpacityAnimator$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                FaceScanningOverlay.access$getRimPaint$p(FaceScanningOverlay.this).setAlpha(255);
                FaceScanningOverlay.this.invalidate();
            }
        });
        return ofInt;
    }

    public final void drawCameraProtection(Canvas canvas) {
        Path path = new Path(this.protectionPath);
        Companion.scalePath(path, getCameraProtectionProgress());
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setColor(this.cameraProtectionColor);
        canvas.drawPath(path, this.paint);
    }

    @Override // com.android.systemui.DisplayCutoutBaseView
    public void drawCutoutProtection(Canvas canvas) {
        if (this.protectionRect.isEmpty()) {
            return;
        }
        if (this.rimProgress > 0.5f) {
            drawFaceScanningRim(canvas);
        }
        if (getCameraProtectionProgress() > 0.5f) {
            drawCameraProtection(canvas);
        }
    }

    public final void drawFaceScanningRim(Canvas canvas) {
        Path path = new Path(this.protectionPath);
        Companion.scalePath(path, this.rimProgress);
        this.rimPaint.setStyle(Paint.Style.FILL);
        int alpha = this.rimPaint.getAlpha();
        this.rimPaint.setColor(ColorUtils.blendARGB(this.faceScanningAnimColor, -1, this.statusBarStateController.getDozeAmount()));
        this.rimPaint.setAlpha(alpha);
        canvas.drawPath(path, this.rimPaint);
    }

    @Override // com.android.systemui.DisplayCutoutBaseView
    public void enableShowProtection(boolean z) {
        boolean z2 = this.keyguardUpdateMonitor.isFaceDetectionRunning() && z;
        if (z2 == this.showScanningAnim) {
            return;
        }
        this.showScanningAnim = z2;
        updateProtectionBoundingPath();
        if (this.showScanningAnim) {
            setVisibility(0);
            requestLayout();
        }
        ValueAnimator valueAnimator = this.cameraProtectionAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(getCameraProtectionProgress(), z2 ? 1.0f : 0.5f);
        ofFloat.setStartDelay(this.showScanningAnim ? 0L : this.faceAuthSucceeded ? 400L : 200L);
        ofFloat.setDuration(this.showScanningAnim ? 250L : this.faceAuthSucceeded ? 500L : 300L);
        ofFloat.setInterpolator(this.showScanningAnim ? Interpolators.STANDARD_ACCELERATE : this.faceAuthSucceeded ? Interpolators.STANDARD : Interpolators.STANDARD_DECELERATE);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.FaceScanningOverlay$enableShowProtection$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                FaceScanningOverlay.access$updateCameraProtectionProgress(FaceScanningOverlay.this, valueAnimator2);
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.FaceScanningOverlay$enableShowProtection$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                FaceScanningOverlay.access$setCameraProtectionAnimator$p(FaceScanningOverlay.this, (ValueAnimator) null);
                if (FaceScanningOverlay.access$getShowScanningAnim$p(FaceScanningOverlay.this)) {
                    return;
                }
                FaceScanningOverlay.access$hide(FaceScanningOverlay.this);
            }
        });
        this.cameraProtectionAnimator = ofFloat;
        AnimatorSet animatorSet = this.rimAnimator;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        AnimatorSet createFaceScanningRimAnimator = this.showScanningAnim ? createFaceScanningRimAnimator() : this.faceAuthSucceeded ? createFaceSuccessRimAnimator() : createFaceNotSuccessRimAnimator();
        this.rimAnimator = createFaceScanningRimAnimator;
        if (createFaceScanningRimAnimator != null) {
            createFaceScanningRimAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.FaceScanningOverlay$enableShowProtection$2$1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    FaceScanningOverlay.access$setRimAnimator$p(FaceScanningOverlay.this, null);
                    if (FaceScanningOverlay.access$getShowScanningAnim$p(FaceScanningOverlay.this)) {
                        return;
                    }
                    FaceScanningOverlay.this.requestLayout();
                }
            });
        }
        AnimatorSet animatorSet2 = this.rimAnimator;
        if (animatorSet2 != null) {
            animatorSet2.start();
        }
    }

    public final KeyguardUpdateMonitor getKeyguardUpdateMonitor() {
        return this.keyguardUpdateMonitor;
    }

    public final void hide() {
        setVisibility(4);
        Runnable runnable = this.hideOverlayRunnable;
        if (runnable != null) {
            runnable.run();
        }
        this.hideOverlayRunnable = null;
        requestLayout();
    }

    @Override // com.android.systemui.DisplayCutoutBaseView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.FaceScanningOverlay$onAttachedToWindow$1
            @Override // java.lang.Runnable
            public final void run() {
                FaceScanningOverlay$keyguardUpdateMonitorCallback$1 faceScanningOverlay$keyguardUpdateMonitorCallback$1;
                KeyguardUpdateMonitor keyguardUpdateMonitor = FaceScanningOverlay.this.getKeyguardUpdateMonitor();
                faceScanningOverlay$keyguardUpdateMonitorCallback$1 = FaceScanningOverlay.this.keyguardUpdateMonitorCallback;
                keyguardUpdateMonitor.registerCallback(faceScanningOverlay$keyguardUpdateMonitorCallback$1);
            }
        });
    }

    @Override // android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.FaceScanningOverlay$onDetachedFromWindow$1
            @Override // java.lang.Runnable
            public final void run() {
                FaceScanningOverlay$keyguardUpdateMonitorCallback$1 faceScanningOverlay$keyguardUpdateMonitorCallback$1;
                KeyguardUpdateMonitor keyguardUpdateMonitor = FaceScanningOverlay.this.getKeyguardUpdateMonitor();
                faceScanningOverlay$keyguardUpdateMonitorCallback$1 = FaceScanningOverlay.this.keyguardUpdateMonitorCallback;
                keyguardUpdateMonitor.removeCallback(faceScanningOverlay$keyguardUpdateMonitorCallback$1);
            }
        });
    }

    @Override // com.android.systemui.ScreenDecorations.DisplayCutoutView, android.view.View
    public void onMeasure(int i, int i2) {
        if (this.mBounds.isEmpty()) {
            super.onMeasure(i, i2);
        } else if (!this.showScanningAnim) {
            setMeasuredDimension(View.resolveSizeAndState(this.mBoundingRect.width(), i, 0), View.resolveSizeAndState(this.mBoundingRect.height(), i2, 0));
        } else {
            this.mTotalBounds.union(this.mBoundingRect);
            Rect rect = this.mTotalBounds;
            RectF rectF = this.rimRect;
            rect.union((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
            setMeasuredDimension(View.resolveSizeAndState(this.mTotalBounds.width(), i, 0), View.resolveSizeAndState(this.mTotalBounds.height(), i2, 0));
        }
    }

    @Override // com.android.systemui.ScreenDecorations.DisplayCutoutView
    public void setColor(int i) {
        this.cameraProtectionColor = i;
        invalidate();
    }

    public final void setFaceAuthSucceeded(boolean z) {
        this.faceAuthSucceeded = z;
    }

    public final void setFaceScanningAnimColor(int i) {
        this.faceScanningAnimColor = i;
    }

    public final void setHideOverlayRunnable(Runnable runnable) {
        this.hideOverlayRunnable = runnable;
    }

    public final void updateCameraProtectionProgress(ValueAnimator valueAnimator) {
        setCameraProtectionProgress(((Float) valueAnimator.getAnimatedValue()).floatValue());
        invalidate();
    }

    @Override // com.android.systemui.DisplayCutoutBaseView
    public void updateProtectionBoundingPath() {
        super.updateProtectionBoundingPath();
        this.rimRect.set(this.protectionRect);
        this.rimRect.scale(this.rimProgress);
    }

    public final void updateRimAlpha(ValueAnimator valueAnimator) {
        this.rimPaint.setAlpha(((Integer) valueAnimator.getAnimatedValue()).intValue());
        invalidate();
    }

    public final void updateRimProgress(ValueAnimator valueAnimator) {
        this.rimProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        invalidate();
    }

    @Override // com.android.systemui.ScreenDecorations.DisplayCutoutView
    public boolean updateVisOnUpdateCutout() {
        return false;
    }
}