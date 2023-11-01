package com.android.systemui.biometrics;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.MathUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.airbnb.lottie.value.LottieFrameInfo;
import com.airbnb.lottie.value.SimpleLottieValueCallback;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$dimen;
import com.android.systemui.R$drawable;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.biometrics.UdfpsKeyguardView;
import com.android.systemui.doze.util.BurnInHelperKt;
import java.io.PrintWriter;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsKeyguardView.class */
public class UdfpsKeyguardView extends UdfpsAnimationView {
    public int mAlpha;
    public int mAnimationType;
    public LottieAnimationView mAodFp;
    public AnimatorSet mBackgroundInAnimator;
    public ImageView mBgProtection;
    public float mBurnInOffsetX;
    public float mBurnInOffsetY;
    public float mBurnInProgress;
    public UdfpsDrawable mFingerprintDrawable;
    public boolean mFullyInflated;
    public float mInterpolatedDarkAmount;
    public final AsyncLayoutInflater.OnInflateFinishedListener mLayoutInflaterFinishListener;
    public LottieAnimationView mLockScreenFp;
    public final int mMaxBurnInOffsetX;
    public final int mMaxBurnInOffsetY;
    public FrameLayout.LayoutParams mParams;
    public float mScaleFactor;
    public int mTextColorPrimary;
    public boolean mUdfpsRequested;

    /* renamed from: com.android.systemui.biometrics.UdfpsKeyguardView$2 */
    /* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsKeyguardView$2.class */
    public class AnonymousClass2 implements AsyncLayoutInflater.OnInflateFinishedListener {
        /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.UdfpsKeyguardView$2$$ExternalSyntheticLambda0.getValue(com.airbnb.lottie.value.LottieFrameInfo):java.lang.Object] */
        public static /* synthetic */ ColorFilter $r8$lambda$xWNJP_xdiyi3TZ1q9DbWrPH8KCE(AnonymousClass2 anonymousClass2, LottieFrameInfo lottieFrameInfo) {
            return anonymousClass2.lambda$onInflateFinished$0(lottieFrameInfo);
        }

        public AnonymousClass2() {
            UdfpsKeyguardView.this = r4;
        }

        public /* synthetic */ ColorFilter lambda$onInflateFinished$0(LottieFrameInfo lottieFrameInfo) {
            return new PorterDuffColorFilter(UdfpsKeyguardView.this.mTextColorPrimary, PorterDuff.Mode.SRC_ATOP);
        }

        @Override // androidx.asynclayoutinflater.view.AsyncLayoutInflater.OnInflateFinishedListener
        public void onInflateFinished(View view, int i, ViewGroup viewGroup) {
            UdfpsKeyguardView.this.mFullyInflated = true;
            UdfpsKeyguardView.this.mAodFp = (LottieAnimationView) view.findViewById(R$id.udfps_aod_fp);
            UdfpsKeyguardView.this.mLockScreenFp = (LottieAnimationView) view.findViewById(R$id.udfps_lockscreen_fp);
            UdfpsKeyguardView.this.mBgProtection = (ImageView) view.findViewById(R$id.udfps_keyguard_fp_bg);
            UdfpsKeyguardView.this.updatePadding();
            UdfpsKeyguardView.this.updateColor();
            UdfpsKeyguardView.this.updateAlpha();
            UdfpsKeyguardView udfpsKeyguardView = UdfpsKeyguardView.this;
            if (udfpsKeyguardView.mUseExpandedOverlay) {
                viewGroup.addView(view, udfpsKeyguardView.mParams);
            } else {
                viewGroup.addView(view);
            }
            UdfpsKeyguardView.this.mLockScreenFp.addValueCallback(new KeyPath("**"), (KeyPath) LottieProperty.COLOR_FILTER, (SimpleLottieValueCallback<KeyPath>) new SimpleLottieValueCallback() { // from class: com.android.systemui.biometrics.UdfpsKeyguardView$2$$ExternalSyntheticLambda0
                @Override // com.airbnb.lottie.value.SimpleLottieValueCallback
                public final Object getValue(LottieFrameInfo lottieFrameInfo) {
                    return UdfpsKeyguardView.AnonymousClass2.$r8$lambda$xWNJP_xdiyi3TZ1q9DbWrPH8KCE(UdfpsKeyguardView.AnonymousClass2.this, lottieFrameInfo);
                }
            });
        }
    }

    public UdfpsKeyguardView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mBackgroundInAnimator = new AnimatorSet();
        this.mScaleFactor = 1.0f;
        this.mAnimationType = 0;
        this.mLayoutInflaterFinishListener = new AnonymousClass2();
        this.mFingerprintDrawable = new UdfpsFpDrawable(context);
        this.mMaxBurnInOffsetX = context.getResources().getDimensionPixelSize(R$dimen.udfps_burn_in_offset_x);
        this.mMaxBurnInOffsetY = context.getResources().getDimensionPixelSize(R$dimen.udfps_burn_in_offset_y);
    }

    public void animateInUdfpsBouncer(final Runnable runnable) {
        if (this.mBackgroundInAnimator.isRunning() || !this.mFullyInflated) {
            return;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        this.mBackgroundInAnimator = animatorSet;
        animatorSet.playTogether(ObjectAnimator.ofFloat(this.mBgProtection, View.ALPHA, ActionBarShadowController.ELEVATION_LOW, 1.0f), ObjectAnimator.ofFloat(this.mBgProtection, View.SCALE_X, ActionBarShadowController.ELEVATION_LOW, 1.0f), ObjectAnimator.ofFloat(this.mBgProtection, View.SCALE_Y, ActionBarShadowController.ELEVATION_LOW, 1.0f));
        this.mBackgroundInAnimator.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        this.mBackgroundInAnimator.setDuration(500L);
        this.mBackgroundInAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.biometrics.UdfpsKeyguardView.1
            {
                UdfpsKeyguardView.this = this;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
            }
        });
        this.mBackgroundInAnimator.start();
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationView
    public int calculateAlpha() {
        if (this.mPauseAuth) {
            return 0;
        }
        return this.mAlpha;
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationView
    public boolean dozeTimeTick() {
        updateBurnInOffsets();
        return true;
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println("UdfpsKeyguardView (" + this + ")");
        StringBuilder sb = new StringBuilder();
        sb.append("    mPauseAuth=");
        sb.append(this.mPauseAuth);
        printWriter.println(sb.toString());
        printWriter.println("    mUnpausedAlpha=" + getUnpausedAlpha());
        printWriter.println("    mUdfpsRequested=" + this.mUdfpsRequested);
        printWriter.println("    mInterpolatedDarkAmount=" + this.mInterpolatedDarkAmount);
        printWriter.println("    mAnimationType=" + this.mAnimationType);
        printWriter.println("    mUseExpandedOverlay=" + this.mUseExpandedOverlay);
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationView
    public UdfpsDrawable getDrawable() {
        return this.mFingerprintDrawable;
    }

    public int getUnpausedAlpha() {
        return this.mAlpha;
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationView
    public void onDisplayConfiguring() {
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationView
    public void onDisplayUnconfigured() {
    }

    public void onDozeAmountChanged(float f, float f2, int i) {
        this.mAnimationType = i;
        this.mInterpolatedDarkAmount = f2;
        updateAlpha();
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        new AsyncLayoutInflater(((FrameLayout) this).mContext).inflate(R$layout.udfps_keyguard_view_internal, this, this.mLayoutInflaterFinishListener);
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationView
    public void onSensorRectUpdated(RectF rectF) {
        super.onSensorRectUpdated(rectF);
        if (this.mUseExpandedOverlay) {
            this.mParams = new FrameLayout.LayoutParams((int) rectF.width(), (int) rectF.height());
            RectF boundsRelativeToView = getBoundsRelativeToView(rectF);
            this.mParams.setMargins((int) boundsRelativeToView.left, (int) boundsRelativeToView.top, (int) boundsRelativeToView.right, (int) boundsRelativeToView.bottom);
        }
    }

    public void requestUdfps(boolean z, int i) {
        this.mUdfpsRequested = z;
    }

    public void setScaleFactor(float f) {
        this.mScaleFactor = f;
    }

    public void setUnpausedAlpha(int i) {
        this.mAlpha = i;
        updateAlpha();
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationView
    public int updateAlpha() {
        int updateAlpha = super.updateAlpha();
        updateBurnInOffsets();
        return updateAlpha;
    }

    public final void updateBurnInOffsets() {
        boolean z;
        if (this.mFullyInflated) {
            float f = this.mAnimationType == 2 ? 1.0f : this.mInterpolatedDarkAmount;
            boolean z2 = true;
            this.mBurnInOffsetX = MathUtils.lerp((float) ActionBarShadowController.ELEVATION_LOW, BurnInHelperKt.getBurnInOffset(this.mMaxBurnInOffsetX * 2, true) - this.mMaxBurnInOffsetX, f);
            this.mBurnInOffsetY = MathUtils.lerp((float) ActionBarShadowController.ELEVATION_LOW, BurnInHelperKt.getBurnInOffset(this.mMaxBurnInOffsetY * 2, false) - this.mMaxBurnInOffsetY, f);
            this.mBurnInProgress = MathUtils.lerp((float) ActionBarShadowController.ELEVATION_LOW, BurnInHelperKt.getBurnInProgressOffset(), f);
            if (this.mAnimationType == 1 && !this.mPauseAuth) {
                this.mLockScreenFp.setTranslationX(this.mBurnInOffsetX);
                this.mLockScreenFp.setTranslationY(this.mBurnInOffsetY);
                this.mBgProtection.setAlpha(1.0f - this.mInterpolatedDarkAmount);
                this.mLockScreenFp.setAlpha(1.0f - this.mInterpolatedDarkAmount);
            } else if (f == ActionBarShadowController.ELEVATION_LOW) {
                this.mLockScreenFp.setTranslationX(ActionBarShadowController.ELEVATION_LOW);
                this.mLockScreenFp.setTranslationY(ActionBarShadowController.ELEVATION_LOW);
                this.mBgProtection.setAlpha(this.mAlpha / 255.0f);
                this.mLockScreenFp.setAlpha(this.mAlpha / 255.0f);
            } else {
                this.mBgProtection.setAlpha(ActionBarShadowController.ELEVATION_LOW);
                this.mLockScreenFp.setAlpha(ActionBarShadowController.ELEVATION_LOW);
            }
            this.mLockScreenFp.setProgress(1.0f - this.mInterpolatedDarkAmount);
            this.mAodFp.setTranslationX(this.mBurnInOffsetX);
            this.mAodFp.setTranslationY(this.mBurnInOffsetY);
            this.mAodFp.setProgress(this.mBurnInProgress);
            this.mAodFp.setAlpha(this.mInterpolatedDarkAmount);
            int i = this.mAnimationType;
            if (i == 1) {
                float f2 = this.mInterpolatedDarkAmount;
                if (f2 == ActionBarShadowController.ELEVATION_LOW || f2 == 1.0f) {
                    z = true;
                    if (i == 2 || this.mInterpolatedDarkAmount != 1.0f) {
                        z2 = false;
                    }
                    if (!z || z2) {
                        this.mAnimationType = 0;
                    }
                    return;
                }
            }
            z = false;
            if (i == 2) {
            }
            z2 = false;
            if (z) {
            }
            this.mAnimationType = 0;
        }
    }

    public void updateColor() {
        if (this.mFullyInflated) {
            this.mTextColorPrimary = com.android.settingslib.Utils.getColorAttrDefaultColor(((FrameLayout) this).mContext, 16842806);
            this.mBgProtection.setImageDrawable(getContext().getDrawable(R$drawable.fingerprint_bg));
            this.mLockScreenFp.invalidate();
        }
    }

    public void updatePadding() {
        if (this.mLockScreenFp == null || this.mAodFp == null) {
            return;
        }
        int dimensionPixelSize = (int) (getResources().getDimensionPixelSize(R$dimen.lock_icon_padding) * this.mScaleFactor);
        this.mLockScreenFp.setPadding(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
        this.mAodFp.setPadding(dimensionPixelSize, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
    }
}