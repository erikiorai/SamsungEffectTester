package com.android.systemui.biometrics;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Outline;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.android.systemui.R$dimen;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/AuthPanelController.class */
public class AuthPanelController extends ViewOutlineProvider {
    public int mContainerHeight;
    public int mContainerWidth;
    public int mContentHeight;
    public int mContentWidth;
    public final Context mContext;
    public float mCornerRadius;
    public int mMargin;
    public final View mPanelView;
    public int mPosition = 1;
    public boolean mUseFullScreen;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthPanelController$$ExternalSyntheticLambda0.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$IdZLzrepYfUqxEEq4TkSMVyXaVE(AuthPanelController authPanelController, ValueAnimator valueAnimator) {
        authPanelController.lambda$updateForContentDimensions$0(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthPanelController$$ExternalSyntheticLambda2.onAnimationUpdate(android.animation.ValueAnimator):void] */
    /* renamed from: $r8$lambda$Sa1F1wJ3gLG9zxl4DJc-WLwNHGg */
    public static /* synthetic */ void m1539$r8$lambda$Sa1F1wJ3gLG9zxl4DJcWLwNHGg(AuthPanelController authPanelController, ValueAnimator valueAnimator) {
        authPanelController.lambda$updateForContentDimensions$2(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthPanelController$$ExternalSyntheticLambda3.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$bqvcZaXlv1g34WNwZHupKRr_oAw(AuthPanelController authPanelController, ValueAnimator valueAnimator) {
        authPanelController.lambda$updateForContentDimensions$3(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.biometrics.AuthPanelController$$ExternalSyntheticLambda1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$gwVceKNQ2nk712ldx2z8yz5JG98(AuthPanelController authPanelController, ValueAnimator valueAnimator) {
        authPanelController.lambda$updateForContentDimensions$1(valueAnimator);
    }

    public AuthPanelController(Context context, View view) {
        this.mContext = context;
        this.mPanelView = view;
        this.mCornerRadius = context.getResources().getDimension(R$dimen.biometric_dialog_corner_size);
        this.mMargin = (int) context.getResources().getDimension(R$dimen.biometric_dialog_border_padding);
        view.setOutlineProvider(this);
        view.setClipToOutline(true);
    }

    public /* synthetic */ void lambda$updateForContentDimensions$0(ValueAnimator valueAnimator) {
        this.mMargin = ((Integer) valueAnimator.getAnimatedValue()).intValue();
    }

    public /* synthetic */ void lambda$updateForContentDimensions$1(ValueAnimator valueAnimator) {
        this.mCornerRadius = ((Float) valueAnimator.getAnimatedValue()).floatValue();
    }

    public /* synthetic */ void lambda$updateForContentDimensions$2(ValueAnimator valueAnimator) {
        this.mContentHeight = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        this.mPanelView.invalidateOutline();
    }

    public /* synthetic */ void lambda$updateForContentDimensions$3(ValueAnimator valueAnimator) {
        this.mContentWidth = ((Integer) valueAnimator.getAnimatedValue()).intValue();
    }

    public int getContainerHeight() {
        return this.mContainerHeight;
    }

    public int getContainerWidth() {
        return this.mContainerWidth;
    }

    public final int getLeftBound(int i) {
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    Log.e("BiometricPrompt/AuthPanelController", "Unrecognized position: " + i);
                    return getLeftBound(1);
                }
                return (this.mContainerWidth - this.mContentWidth) - this.mMargin;
            }
            return this.mMargin;
        }
        return (this.mContainerWidth - this.mContentWidth) / 2;
    }

    @Override // android.view.ViewOutlineProvider
    public void getOutline(View view, Outline outline) {
        int leftBound = getLeftBound(this.mPosition);
        int i = this.mContentWidth;
        int topBound = getTopBound(this.mPosition);
        outline.setRoundRect(leftBound, topBound, leftBound + i, Math.min(this.mContentHeight + topBound, this.mContainerHeight - this.mMargin), this.mCornerRadius);
    }

    public final int getTopBound(int i) {
        if (i == 1) {
            int i2 = this.mContainerHeight;
            int i3 = this.mContentHeight;
            int i4 = this.mMargin;
            return Math.max((i2 - i3) - i4, i4);
        } else if (i == 2 || i == 3) {
            return Math.max((this.mContainerHeight - this.mContentHeight) / 2, this.mMargin);
        } else {
            Log.e("BiometricPrompt/AuthPanelController", "Unrecognized position: " + i);
            return getTopBound(1);
        }
    }

    public void setContainerDimensions(int i, int i2) {
        this.mContainerWidth = i;
        this.mContainerHeight = i2;
    }

    public void setPosition(int i) {
        this.mPosition = i;
    }

    public void setUseFullScreen(boolean z) {
        this.mUseFullScreen = z;
    }

    public void updateForContentDimensions(int i, int i2, int i3) {
        if (this.mContainerWidth == 0 || this.mContainerHeight == 0) {
            Log.w("BiometricPrompt/AuthPanelController", "Not done measuring yet");
            return;
        }
        int dimension = this.mUseFullScreen ? 0 : (int) this.mContext.getResources().getDimension(R$dimen.biometric_dialog_border_padding);
        float dimension2 = this.mUseFullScreen ? 0.0f : this.mContext.getResources().getDimension(R$dimen.biometric_dialog_corner_size);
        if (i3 <= 0) {
            this.mMargin = dimension;
            this.mCornerRadius = dimension2;
            this.mContentWidth = i;
            this.mContentHeight = i2;
            this.mPanelView.invalidateOutline();
            return;
        }
        ValueAnimator ofInt = ValueAnimator.ofInt(this.mMargin, dimension);
        ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.AuthPanelController$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                AuthPanelController.$r8$lambda$IdZLzrepYfUqxEEq4TkSMVyXaVE(AuthPanelController.this, valueAnimator);
            }
        });
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.mCornerRadius, dimension2);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.AuthPanelController$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                AuthPanelController.$r8$lambda$gwVceKNQ2nk712ldx2z8yz5JG98(AuthPanelController.this, valueAnimator);
            }
        });
        ValueAnimator ofInt2 = ValueAnimator.ofInt(this.mContentHeight, i2);
        ofInt2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.AuthPanelController$$ExternalSyntheticLambda2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                AuthPanelController.m1539$r8$lambda$Sa1F1wJ3gLG9zxl4DJcWLwNHGg(AuthPanelController.this, valueAnimator);
            }
        });
        ValueAnimator ofInt3 = ValueAnimator.ofInt(this.mContentWidth, i);
        ofInt3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.biometrics.AuthPanelController$$ExternalSyntheticLambda3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                AuthPanelController.$r8$lambda$bqvcZaXlv1g34WNwZHupKRr_oAw(AuthPanelController.this, valueAnimator);
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(i3);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.playTogether(ofFloat, ofInt2, ofInt3, ofInt);
        animatorSet.start();
    }
}