package com.android.keyguard;

import android.view.View;
import android.view.ViewPropertyAnimator;
import androidx.appcompat.R$styleable;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.animation.Interpolators;
import com.android.systemui.statusbar.notification.AnimatableProperty;
import com.android.systemui.statusbar.notification.PropertyAnimator;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.phone.ScreenOffAnimationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardVisibilityHelper.class */
public class KeyguardVisibilityHelper {
    public boolean mAnimateYPos;
    public final DozeParameters mDozeParameters;
    public final KeyguardStateController mKeyguardStateController;
    public boolean mKeyguardViewVisibilityAnimating;
    public final ScreenOffAnimationController mScreenOffAnimationController;
    public View mView;
    public boolean mLastOccludedState = false;
    public boolean mIsUnoccludeTransitionFlagEnabled = false;
    public final AnimationProperties mAnimationProperties = new AnimationProperties();
    public final Runnable mAnimateKeyguardStatusViewInvisibleEndRunnable = new Runnable() { // from class: com.android.keyguard.KeyguardVisibilityHelper$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            KeyguardVisibilityHelper.$r8$lambda$_LKGqgB9rtsYChysUNvvAaVh1uo(KeyguardVisibilityHelper.this);
        }
    };
    public final Runnable mAnimateKeyguardStatusViewGoneEndRunnable = new Runnable() { // from class: com.android.keyguard.KeyguardVisibilityHelper$$ExternalSyntheticLambda1
        @Override // java.lang.Runnable
        public final void run() {
            KeyguardVisibilityHelper.$r8$lambda$clLsUyjaESrz144F6Tu8QZo1h1Y(KeyguardVisibilityHelper.this);
        }
    };
    public final Runnable mAnimateKeyguardStatusViewVisibleEndRunnable = new Runnable() { // from class: com.android.keyguard.KeyguardVisibilityHelper$$ExternalSyntheticLambda2
        @Override // java.lang.Runnable
        public final void run() {
            KeyguardVisibilityHelper.$r8$lambda$m13VDioBxfHFL5OFUGNyOM1xFSM(KeyguardVisibilityHelper.this);
        }
    };

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardVisibilityHelper$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$_LKGqgB9rtsYChysUNvvAaVh1uo(KeyguardVisibilityHelper keyguardVisibilityHelper) {
        keyguardVisibilityHelper.lambda$new$0();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardVisibilityHelper$$ExternalSyntheticLambda1.run():void] */
    public static /* synthetic */ void $r8$lambda$clLsUyjaESrz144F6Tu8QZo1h1Y(KeyguardVisibilityHelper keyguardVisibilityHelper) {
        keyguardVisibilityHelper.lambda$new$1();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.KeyguardVisibilityHelper$$ExternalSyntheticLambda2.run():void] */
    public static /* synthetic */ void $r8$lambda$m13VDioBxfHFL5OFUGNyOM1xFSM(KeyguardVisibilityHelper keyguardVisibilityHelper) {
        keyguardVisibilityHelper.lambda$new$2();
    }

    public KeyguardVisibilityHelper(View view, KeyguardStateController keyguardStateController, DozeParameters dozeParameters, ScreenOffAnimationController screenOffAnimationController, boolean z) {
        this.mView = view;
        this.mKeyguardStateController = keyguardStateController;
        this.mDozeParameters = dozeParameters;
        this.mScreenOffAnimationController = screenOffAnimationController;
        this.mAnimateYPos = z;
    }

    public /* synthetic */ void lambda$new$0() {
        this.mKeyguardViewVisibilityAnimating = false;
        this.mView.setVisibility(4);
    }

    public /* synthetic */ void lambda$new$1() {
        this.mKeyguardViewVisibilityAnimating = false;
        this.mView.setVisibility(8);
    }

    public /* synthetic */ void lambda$new$2() {
        this.mKeyguardViewVisibilityAnimating = false;
    }

    public boolean isVisibilityAnimating() {
        return this.mKeyguardViewVisibilityAnimating;
    }

    public void setOcclusionTransitionFlagEnabled(boolean z) {
        this.mIsUnoccludeTransitionFlagEnabled = z;
    }

    public void setViewVisibility(int i, boolean z, boolean z2, int i2) {
        this.mView.animate().cancel();
        boolean isOccluded = this.mKeyguardStateController.isOccluded();
        this.mKeyguardViewVisibilityAnimating = false;
        if ((!z && i2 == 1 && i != 1) || z2) {
            this.mKeyguardViewVisibilityAnimating = true;
            this.mView.animate().alpha(ActionBarShadowController.ELEVATION_LOW).setStartDelay(0L).setDuration(160L).setInterpolator(Interpolators.ALPHA_OUT).withEndAction(this.mAnimateKeyguardStatusViewGoneEndRunnable);
            if (z) {
                this.mView.animate().setStartDelay(this.mKeyguardStateController.getKeyguardFadingAwayDelay()).setDuration(this.mKeyguardStateController.getShortenedFadingAwayDuration()).start();
            }
        } else if (i2 == 2 && i == 1) {
            this.mView.setVisibility(0);
            this.mKeyguardViewVisibilityAnimating = true;
            this.mView.setAlpha(ActionBarShadowController.ELEVATION_LOW);
            this.mView.animate().alpha(1.0f).setStartDelay(0L).setDuration(320L).setInterpolator(Interpolators.ALPHA_IN).withEndAction(this.mAnimateKeyguardStatusViewVisibleEndRunnable);
        } else if (i != 1) {
            this.mView.setVisibility(8);
            this.mView.setAlpha(1.0f);
        } else if (z) {
            this.mKeyguardViewVisibilityAnimating = true;
            ViewPropertyAnimator withEndAction = this.mView.animate().alpha(ActionBarShadowController.ELEVATION_LOW).setInterpolator(Interpolators.FAST_OUT_LINEAR_IN).withEndAction(this.mAnimateKeyguardStatusViewInvisibleEndRunnable);
            if (this.mAnimateYPos) {
                float y = this.mView.getY();
                float height = this.mView.getHeight();
                AnimationProperties animationProperties = this.mAnimationProperties;
                long j = (long) R$styleable.AppCompatTheme_windowMinWidthMinor;
                AnimationProperties duration = animationProperties.setDuration(j);
                long j2 = 0;
                duration.setDelay(j2);
                View view = this.mView;
                AnimatableProperty animatableProperty = AnimatableProperty.Y;
                PropertyAnimator.cancelAnimation(view, animatableProperty);
                PropertyAnimator.setProperty(this.mView, animatableProperty, y - (height * 0.05f), this.mAnimationProperties, true);
                withEndAction.setDuration(j).setStartDelay(j2);
            }
            withEndAction.start();
        } else if (this.mScreenOffAnimationController.shouldAnimateInKeyguard()) {
            this.mKeyguardViewVisibilityAnimating = true;
            this.mScreenOffAnimationController.animateInKeyguard(this.mView, this.mAnimateKeyguardStatusViewVisibleEndRunnable);
        } else if (this.mIsUnoccludeTransitionFlagEnabled || !this.mLastOccludedState || isOccluded) {
            this.mView.setVisibility(0);
            if (!this.mIsUnoccludeTransitionFlagEnabled) {
                this.mView.setAlpha(1.0f);
            }
        } else {
            this.mView.setVisibility(0);
            this.mView.setAlpha(ActionBarShadowController.ELEVATION_LOW);
            this.mView.animate().setDuration(500L).setInterpolator(Interpolators.FAST_OUT_SLOW_IN).alpha(1.0f).withEndAction(this.mAnimateKeyguardStatusViewVisibleEndRunnable).start();
        }
        this.mLastOccludedState = isOccluded;
    }
}