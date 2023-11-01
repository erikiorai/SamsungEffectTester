package com.android.systemui.biometrics;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.airbnb.lottie.LottieAnimationView;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/AuthIconController.class */
public abstract class AuthIconController extends Animatable2.AnimationCallback {
    public final boolean actsAsConfirmButton;
    public final Context context;
    public boolean deactivated;
    public final LottieAnimationView iconView;

    public AuthIconController(Context context, LottieAnimationView lottieAnimationView) {
        this.context = context;
        this.iconView = lottieAnimationView;
    }

    public final void animateIcon(int i, boolean z) {
        if (this.deactivated) {
            return;
        }
        AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) this.context.getDrawable(i);
        this.iconView.setImageDrawable(animatedVectorDrawable);
        animatedVectorDrawable.forceAnimationOnUI();
        if (z) {
            animatedVectorDrawable.registerAnimationCallback(this);
        }
        animatedVectorDrawable.start();
    }

    public final void animateIconOnce(int i) {
        animateIcon(i, false);
    }

    public boolean getActsAsConfirmButton() {
        return this.actsAsConfirmButton;
    }

    public final Context getContext() {
        return this.context;
    }

    public final LottieAnimationView getIconView() {
        return this.iconView;
    }

    public void handleAnimationEnd(Drawable drawable) {
    }

    @Override // android.graphics.drawable.Animatable2.AnimationCallback
    public final void onAnimationEnd(Drawable drawable) {
        super.onAnimationEnd(drawable);
        if (this.deactivated) {
            return;
        }
        handleAnimationEnd(drawable);
    }

    @Override // android.graphics.drawable.Animatable2.AnimationCallback
    public final void onAnimationStart(Drawable drawable) {
        super.onAnimationStart(drawable);
    }

    public void onConfigurationChanged(Configuration configuration) {
    }

    public final void setDeactivated(boolean z) {
        this.deactivated = z;
    }

    public final void showStaticDrawable(int i) {
        this.iconView.setImageDrawable(this.context.getDrawable(i));
    }

    public abstract void updateIcon(int i, int i2);

    public final void updateState(int i, int i2) {
        if (!this.deactivated) {
            updateIcon(i, i2);
            return;
        }
        Log.w("AuthIconController", "Ignoring updateState when deactivated: " + i2);
    }
}