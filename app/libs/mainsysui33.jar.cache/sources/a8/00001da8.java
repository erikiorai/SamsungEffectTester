package com.android.systemui.media.taptotransfer.receiver;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.surfaceeffects.ripple.RippleShader;
import com.android.systemui.surfaceeffects.ripple.RippleView;

/* loaded from: mainsysui33.jar:com/android/systemui/media/taptotransfer/receiver/ReceiverChipRippleView.class */
public final class ReceiverChipRippleView extends RippleView {
    public boolean isStarted;

    public ReceiverChipRippleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setupShader(RippleShader.RippleShape.ELLIPSE);
        setRippleFill(true);
        setSparkleStrength((float) ActionBarShadowController.ELEVATION_LOW);
        setDuration(3000L);
        this.isStarted = false;
    }

    public static /* synthetic */ void expandRipple$default(ReceiverChipRippleView receiverChipRippleView, Runnable runnable, int i, Object obj) {
        if ((i & 1) != 0) {
            runnable = null;
        }
        receiverChipRippleView.expandRipple(runnable);
    }

    public final float calculateStartingPercentage(float f) {
        float f2;
        return 1 - ((float) Math.pow(f2 - (getRippleShader().getCurrentHeight() / f), 0.3333333333333333d));
    }

    public final void collapseRipple(final Runnable runnable) {
        if (this.isStarted) {
            getAnimator().removeAllListeners();
            getAnimator().addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.media.taptotransfer.receiver.ReceiverChipRippleView$collapseRipple$1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    Runnable runnable2 = runnable;
                    if (runnable2 != null) {
                        runnable2.run();
                    }
                    this.isStarted = false;
                }
            });
            getAnimator().reverse();
        }
    }

    public final void expandRipple(Runnable runnable) {
        this.isStarted = true;
        super.startRipple(runnable);
    }

    public final void expandToFull(float f, final Runnable runnable) {
        if (this.isStarted) {
            getAnimator().removeAllListeners();
            getAnimator().removeAllUpdateListeners();
            setRippleFill(false);
            final float calculateStartingPercentage = calculateStartingPercentage(f);
            getAnimator().addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.media.taptotransfer.receiver.ReceiverChipRippleView$expandToFull$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    RippleShader rippleShader;
                    RippleShader rippleShader2;
                    RippleShader rippleShader3;
                    RippleShader rippleShader4;
                    RippleShader rippleShader5;
                    RippleShader rippleShader6;
                    long currentPlayTime = valueAnimator.getCurrentPlayTime();
                    float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    rippleShader = ReceiverChipRippleView.this.getRippleShader();
                    float f2 = calculateStartingPercentage;
                    float f3 = 1;
                    rippleShader.setProgress(f2 + (floatValue * (f3 - f2)));
                    rippleShader2 = ReceiverChipRippleView.this.getRippleShader();
                    rippleShader3 = ReceiverChipRippleView.this.getRippleShader();
                    rippleShader2.setDistortionStrength(f3 - rippleShader3.getProgress());
                    rippleShader4 = ReceiverChipRippleView.this.getRippleShader();
                    rippleShader5 = ReceiverChipRippleView.this.getRippleShader();
                    rippleShader4.setPixelDensity(f3 - rippleShader5.getProgress());
                    rippleShader6 = ReceiverChipRippleView.this.getRippleShader();
                    rippleShader6.setTime((float) currentPlayTime);
                    ReceiverChipRippleView.this.invalidate();
                }
            });
            getAnimator().addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.media.taptotransfer.receiver.ReceiverChipRippleView$expandToFull$2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (animator != null) {
                        this.setVisibility(8);
                    }
                    Runnable runnable2 = runnable;
                    if (runnable2 != null) {
                        runnable2.run();
                    }
                    this.isStarted = false;
                }
            });
            getAnimator().start();
        }
    }
}