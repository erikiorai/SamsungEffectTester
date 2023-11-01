package com.android.keyguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.animation.Interpolators;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/keyguard/BouncerKeyguardMessageArea.class */
public class BouncerKeyguardMessageArea extends KeyguardMessageArea {
    public final int DEFAULT_COLOR;
    public final long HIDE_DURATION_MILLIS;
    public final long SHOW_DURATION_MILLIS;
    public final AnimatorSet animatorSet;
    public ColorStateList mDefaultColorState;
    public ColorStateList mNextMessageColorState;
    public CharSequence textAboutToShow;

    public BouncerKeyguardMessageArea(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.DEFAULT_COLOR = -1;
        this.mNextMessageColorState = ColorStateList.valueOf(-1);
        this.animatorSet = new AnimatorSet();
        this.SHOW_DURATION_MILLIS = 150L;
        this.HIDE_DURATION_MILLIS = 200L;
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.BouncerKeyguardMessageArea$setMessage$1.onAnimationEnd(android.animation.Animator):void] */
    /* renamed from: access$setMessage$s-1109913202 */
    public static final /* synthetic */ void m530access$setMessage$s1109913202(BouncerKeyguardMessageArea bouncerKeyguardMessageArea, CharSequence charSequence, boolean z) {
        super.setMessage(charSequence, z);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.BouncerKeyguardMessageArea$setMessage$2.onAnimationEnd(android.animation.Animator):void] */
    public static final /* synthetic */ void access$setTextAboutToShow$p(BouncerKeyguardMessageArea bouncerKeyguardMessageArea, CharSequence charSequence) {
        bouncerKeyguardMessageArea.textAboutToShow = charSequence;
    }

    public long getHIDE_DURATION_MILLIS() {
        return this.HIDE_DURATION_MILLIS;
    }

    public long getSHOW_DURATION_MILLIS() {
        return this.SHOW_DURATION_MILLIS;
    }

    @Override // com.android.keyguard.KeyguardMessageArea
    public void onThemeChanged() {
        TypedArray obtainStyledAttributes = ((TextView) this).mContext.obtainStyledAttributes(new int[]{16842806});
        ColorStateList valueOf = ColorStateList.valueOf(obtainStyledAttributes.getColor(0, -65536));
        obtainStyledAttributes.recycle();
        this.mDefaultColorState = valueOf;
        super.onThemeChanged();
    }

    @Override // com.android.keyguard.KeyguardMessageArea
    public void setMessage(final CharSequence charSequence, final boolean z) {
        if ((!Intrinsics.areEqual(charSequence, this.textAboutToShow) || charSequence == null) && !Intrinsics.areEqual(charSequence, getText())) {
            if (!z) {
                super.setMessage(charSequence, z);
                return;
            }
            this.textAboutToShow = charSequence;
            if (this.animatorSet.isRunning()) {
                this.animatorSet.cancel();
                this.textAboutToShow = null;
            }
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, View.ALPHA, 1.0f, ActionBarShadowController.ELEVATION_LOW);
            ofFloat.setDuration(getHIDE_DURATION_MILLIS());
            ofFloat.setInterpolator(Interpolators.STANDARD_ACCELERATE);
            ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.keyguard.BouncerKeyguardMessageArea$setMessage$1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    BouncerKeyguardMessageArea.m530access$setMessage$s1109913202(BouncerKeyguardMessageArea.this, charSequence, z);
                }
            });
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this, View.ALPHA, ActionBarShadowController.ELEVATION_LOW, 1.0f);
            ofFloat2.setDuration(getSHOW_DURATION_MILLIS());
            ofFloat2.setInterpolator(Interpolators.STANDARD_DECELERATE);
            ofFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.android.keyguard.BouncerKeyguardMessageArea$setMessage$2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    BouncerKeyguardMessageArea.access$setTextAboutToShow$p(BouncerKeyguardMessageArea.this, null);
                }
            });
            this.animatorSet.playSequentially(ofFloat, ofFloat2);
            this.animatorSet.start();
        }
    }

    @Override // com.android.keyguard.SecurityMessageDisplay
    public void setNextMessageColor(ColorStateList colorStateList) {
        this.mNextMessageColorState = colorStateList;
    }

    @Override // com.android.keyguard.KeyguardMessageArea
    public void updateTextColor() {
        ColorStateList colorStateList = this.mDefaultColorState;
        ColorStateList colorStateList2 = this.mNextMessageColorState;
        ColorStateList colorStateList3 = colorStateList;
        if (colorStateList2 != null) {
            int defaultColor = colorStateList2.getDefaultColor();
            int i = this.DEFAULT_COLOR;
            colorStateList3 = colorStateList;
            if (defaultColor != i) {
                colorStateList3 = this.mNextMessageColorState;
                this.mNextMessageColorState = ColorStateList.valueOf(i);
            }
        }
        setTextColor(colorStateList3);
    }
}