package com.android.keyguard;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.ContextThemeWrapper;
import android.view.animation.Interpolator;
import android.widget.TextView;
import com.android.settingslib.Utils;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.util.ColorUtilKt;

/* loaded from: mainsysui33.jar:com/android/keyguard/NumPadAnimator.class */
public class NumPadAnimator {
    public GradientDrawable mBackground;
    public ValueAnimator mContractAnimator;
    public AnimatorSet mContractAnimatorSet;
    public TextView mDigitTextView;
    public float mEndRadius;
    public ValueAnimator mExpandAnimator;
    public AnimatorSet mExpandAnimatorSet;
    public int mHeight;
    public Drawable mImageButton;
    public int mNormalBackgroundColor;
    public int mPressedBackgroundColor;
    public float mStartRadius;
    public int mStyle;
    public int mTextColorPressed;
    public int mTextColorPrimary;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.NumPadAnimator$$ExternalSyntheticLambda5.onAnimationUpdate(android.animation.ValueAnimator):void] */
    /* renamed from: $r8$lambda$1WB2i-9ckXbnFUafWUB8_1E9Zqs */
    public static /* synthetic */ void m805$r8$lambda$1WB2i9ckXbnFUafWUB8_1E9Zqs(NumPadAnimator numPadAnimator, ValueAnimator valueAnimator) {
        numPadAnimator.lambda$createAnimators$5(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.NumPadAnimator$$ExternalSyntheticLambda3.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$8F5mU7RSC9LckeVSmbHzlhLwMAI(NumPadAnimator numPadAnimator, ValueAnimator valueAnimator) {
        numPadAnimator.lambda$createAnimators$3(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.NumPadAnimator$$ExternalSyntheticLambda4.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$ZwXUhaViJkeeFtVRhI0kulHSJe8(NumPadAnimator numPadAnimator, ValueAnimator valueAnimator) {
        numPadAnimator.lambda$createAnimators$4(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.NumPadAnimator$$ExternalSyntheticLambda1.onAnimationUpdate(android.animation.ValueAnimator):void] */
    /* renamed from: $r8$lambda$j16D-J7UcKHklUvtRezecgog0Vw */
    public static /* synthetic */ void m806$r8$lambda$j16DJ7UcKHklUvtRezecgog0Vw(NumPadAnimator numPadAnimator, ValueAnimator valueAnimator) {
        numPadAnimator.lambda$createAnimators$1(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.NumPadAnimator$$ExternalSyntheticLambda0.onAnimationUpdate(android.animation.ValueAnimator):void] */
    /* renamed from: $r8$lambda$rBKbV9dPC2WSskPCamA-xJq1ayY */
    public static /* synthetic */ void m807$r8$lambda$rBKbV9dPC2WSskPCamAxJq1ayY(NumPadAnimator numPadAnimator, ValueAnimator valueAnimator) {
        numPadAnimator.lambda$createAnimators$0(valueAnimator);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.NumPadAnimator$$ExternalSyntheticLambda2.onAnimationUpdate(android.animation.ValueAnimator):void] */
    public static /* synthetic */ void $r8$lambda$sIjRV_1c_mr40pO1b0Q2xEpJoo0(NumPadAnimator numPadAnimator, ValueAnimator valueAnimator) {
        numPadAnimator.lambda$createAnimators$2(valueAnimator);
    }

    public NumPadAnimator(Context context, Drawable drawable, int i, Drawable drawable2) {
        this(context, drawable, i, null, drawable2);
    }

    public NumPadAnimator(Context context, Drawable drawable, int i, TextView textView, Drawable drawable2) {
        this.mStyle = i;
        this.mBackground = (GradientDrawable) drawable;
        this.mDigitTextView = textView;
        this.mImageButton = drawable2;
        reloadColors(context);
    }

    public /* synthetic */ void lambda$createAnimators$0(ValueAnimator valueAnimator) {
        this.mBackground.setCornerRadius(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    public /* synthetic */ void lambda$createAnimators$1(ValueAnimator valueAnimator) {
        this.mBackground.setColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
    }

    public /* synthetic */ void lambda$createAnimators$2(ValueAnimator valueAnimator) {
        TextView textView = this.mDigitTextView;
        if (textView != null) {
            textView.setTextColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
        }
        Drawable drawable = this.mImageButton;
        if (drawable != null) {
            drawable.setTint(((Integer) valueAnimator.getAnimatedValue()).intValue());
        }
    }

    public /* synthetic */ void lambda$createAnimators$3(ValueAnimator valueAnimator) {
        this.mBackground.setCornerRadius(((Float) valueAnimator.getAnimatedValue()).floatValue());
    }

    public /* synthetic */ void lambda$createAnimators$4(ValueAnimator valueAnimator) {
        this.mBackground.setColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
    }

    public /* synthetic */ void lambda$createAnimators$5(ValueAnimator valueAnimator) {
        TextView textView = this.mDigitTextView;
        if (textView != null) {
            textView.setTextColor(((Integer) valueAnimator.getAnimatedValue()).intValue());
        }
        Drawable drawable = this.mImageButton;
        if (drawable != null) {
            drawable.setTint(((Integer) valueAnimator.getAnimatedValue()).intValue());
        }
    }

    public void contract() {
        this.mExpandAnimatorSet.cancel();
        this.mContractAnimatorSet.cancel();
        this.mContractAnimatorSet.start();
    }

    public final void createAnimators() {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
        this.mExpandAnimator = ofFloat;
        ofFloat.setDuration(100L);
        ValueAnimator valueAnimator = this.mExpandAnimator;
        Interpolator interpolator = Interpolators.LINEAR;
        valueAnimator.setInterpolator(interpolator);
        this.mExpandAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.keyguard.NumPadAnimator$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                NumPadAnimator.m807$r8$lambda$rBKbV9dPC2WSskPCamAxJq1ayY(NumPadAnimator.this, valueAnimator2);
            }
        });
        ValueAnimator ofObject = ValueAnimator.ofObject(new ArgbEvaluator(), Integer.valueOf(this.mNormalBackgroundColor), Integer.valueOf(this.mPressedBackgroundColor));
        ofObject.setDuration(50L);
        ofObject.setInterpolator(interpolator);
        ofObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.keyguard.NumPadAnimator$$ExternalSyntheticLambda1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                NumPadAnimator.m806$r8$lambda$j16DJ7UcKHklUvtRezecgog0Vw(NumPadAnimator.this, valueAnimator2);
            }
        });
        ValueAnimator ofObject2 = ValueAnimator.ofObject(new ArgbEvaluator(), Integer.valueOf(this.mTextColorPrimary), Integer.valueOf(this.mTextColorPressed));
        ofObject2.setInterpolator(interpolator);
        ofObject2.setDuration(50L);
        ofObject2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.keyguard.NumPadAnimator$$ExternalSyntheticLambda2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                NumPadAnimator.$r8$lambda$sIjRV_1c_mr40pO1b0Q2xEpJoo0(NumPadAnimator.this, valueAnimator2);
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        this.mExpandAnimatorSet = animatorSet;
        animatorSet.playTogether(this.mExpandAnimator, ofObject, ofObject2);
        ValueAnimator ofFloat2 = ValueAnimator.ofFloat(1.0f, ActionBarShadowController.ELEVATION_LOW);
        this.mContractAnimator = ofFloat2;
        ofFloat2.setStartDelay(33L);
        this.mContractAnimator.setDuration(417L);
        this.mContractAnimator.setInterpolator(Interpolators.FAST_OUT_SLOW_IN);
        this.mContractAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.keyguard.NumPadAnimator$$ExternalSyntheticLambda3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                NumPadAnimator.$r8$lambda$8F5mU7RSC9LckeVSmbHzlhLwMAI(NumPadAnimator.this, valueAnimator2);
            }
        });
        ValueAnimator ofObject3 = ValueAnimator.ofObject(new ArgbEvaluator(), Integer.valueOf(this.mPressedBackgroundColor), Integer.valueOf(this.mNormalBackgroundColor));
        ofObject3.setInterpolator(interpolator);
        ofObject3.setStartDelay(33L);
        ofObject3.setDuration(417L);
        ofObject3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.keyguard.NumPadAnimator$$ExternalSyntheticLambda4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                NumPadAnimator.$r8$lambda$ZwXUhaViJkeeFtVRhI0kulHSJe8(NumPadAnimator.this, valueAnimator2);
            }
        });
        ValueAnimator ofObject4 = ValueAnimator.ofObject(new ArgbEvaluator(), Integer.valueOf(this.mTextColorPressed), Integer.valueOf(this.mTextColorPrimary));
        ofObject4.setInterpolator(interpolator);
        ofObject4.setStartDelay(33L);
        ofObject4.setDuration(417L);
        ofObject4.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.keyguard.NumPadAnimator$$ExternalSyntheticLambda5
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                NumPadAnimator.m805$r8$lambda$1WB2i9ckXbnFUafWUB8_1E9Zqs(NumPadAnimator.this, valueAnimator2);
            }
        });
        AnimatorSet animatorSet2 = new AnimatorSet();
        this.mContractAnimatorSet = animatorSet2;
        animatorSet2.playTogether(this.mContractAnimator, ofObject3, ofObject4);
    }

    public void expand() {
        this.mExpandAnimatorSet.cancel();
        this.mContractAnimatorSet.cancel();
        this.mExpandAnimatorSet.start();
    }

    public void onLayout(int i) {
        this.mHeight = i;
        float f = i;
        float f2 = f / 2.0f;
        this.mStartRadius = f2;
        this.mEndRadius = f / 4.0f;
        this.mBackground.setCornerRadius(f2);
        this.mExpandAnimator.setFloatValues(this.mStartRadius, this.mEndRadius);
        this.mContractAnimator.setFloatValues(this.mEndRadius, this.mStartRadius);
    }

    public void reloadColors(Context context) {
        boolean z = this.mImageButton == null;
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, this.mStyle);
        TypedArray obtainStyledAttributes = contextThemeWrapper.obtainStyledAttributes(new int[]{16843817});
        this.mNormalBackgroundColor = ColorUtilKt.getPrivateAttrColorIfUnset(contextThemeWrapper, obtainStyledAttributes, 0, 0, 17956909);
        obtainStyledAttributes.recycle();
        this.mBackground.setColor(this.mNormalBackgroundColor);
        this.mPressedBackgroundColor = context.getColor(17170491);
        this.mTextColorPrimary = z ? Utils.getColorAttrDefaultColor(context, 16842806) : Utils.getColorAttrDefaultColor(context, 16842809);
        this.mTextColorPressed = Utils.getColorAttrDefaultColor(context, 17957107);
        createAnimators();
    }

    public void setProgress(float f) {
        GradientDrawable gradientDrawable = this.mBackground;
        float f2 = this.mEndRadius;
        gradientDrawable.setCornerRadius(f2 + ((this.mStartRadius - f2) * f));
        int i = this.mHeight;
        int i2 = (i - ((int) ((i * 0.7f) + ((i * 0.3d) * f)))) / 2;
        this.mBackground.setBounds(0, i2, i, i - i2);
    }
}