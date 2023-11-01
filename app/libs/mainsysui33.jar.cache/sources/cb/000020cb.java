package com.android.systemui.qs;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.drawable.TransitionDrawable;
import android.view.View;
import android.view.ViewAnimationUtils;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/QSDetailClipper.class */
public class QSDetailClipper {
    public Animator mAnimator;
    public final TransitionDrawable mBackground;
    public final View mDetail;
    public final Runnable mReverseBackground = new Runnable() { // from class: com.android.systemui.qs.QSDetailClipper.1
        @Override // java.lang.Runnable
        public void run() {
            if (QSDetailClipper.this.mAnimator != null) {
                QSDetailClipper.this.mBackground.reverseTransition((int) (QSDetailClipper.this.mAnimator.getDuration() * 0.35d));
            }
        }
    };
    public final AnimatorListenerAdapter mVisibleOnStart = new AnimatorListenerAdapter() { // from class: com.android.systemui.qs.QSDetailClipper.2
        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            QSDetailClipper.this.mAnimator = null;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
            QSDetailClipper.this.mDetail.setVisibility(0);
        }
    };
    public final AnimatorListenerAdapter mGoneOnEnd = new AnimatorListenerAdapter() { // from class: com.android.systemui.qs.QSDetailClipper.3
        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            QSDetailClipper.this.mDetail.setVisibility(8);
            QSDetailClipper.this.mBackground.resetTransition();
            QSDetailClipper.this.mAnimator = null;
        }
    };

    public QSDetailClipper(View view) {
        this.mDetail = view;
        this.mBackground = (TransitionDrawable) view.getBackground();
    }

    public long animateCircularClip(int i, int i2, boolean z, Animator.AnimatorListener animatorListener) {
        return updateCircularClip(true, i, i2, z, animatorListener);
    }

    public void cancelAnimator() {
        Animator animator = this.mAnimator;
        if (animator != null) {
            animator.cancel();
        }
    }

    public void showBackground() {
        this.mBackground.showSecondLayer();
    }

    public long updateCircularClip(boolean z, int i, int i2, boolean z2, Animator.AnimatorListener animatorListener) {
        Animator animator;
        Animator animator2 = this.mAnimator;
        if (animator2 != null) {
            animator2.cancel();
        }
        int width = this.mDetail.getWidth() - i;
        int height = this.mDetail.getHeight() - i2;
        int min = (i < 0 || width < 0 || i2 < 0 || height < 0) ? Math.min(Math.min(Math.min(Math.abs(i), Math.abs(i2)), Math.abs(width)), Math.abs(height)) : 0;
        int i3 = i * i;
        int i4 = i2 * i2;
        double ceil = (int) Math.ceil(Math.sqrt(i3 + i4));
        int i5 = width * width;
        double max = (int) Math.max(ceil, Math.ceil(Math.sqrt(i4 + i5)));
        int i6 = height * height;
        int max2 = (int) Math.max((int) Math.max(max, Math.ceil(Math.sqrt(i5 + i6))), Math.ceil(Math.sqrt(i3 + i6)));
        if (z2) {
            this.mAnimator = ViewAnimationUtils.createCircularReveal(this.mDetail, i, i2, min, max2);
        } else {
            this.mAnimator = ViewAnimationUtils.createCircularReveal(this.mDetail, i, i2, max2, min);
        }
        this.mAnimator.setDuration(z ? (long) (animator.getDuration() * 1.5d) : 0L);
        if (animatorListener != null) {
            this.mAnimator.addListener(animatorListener);
        }
        if (z2) {
            TransitionDrawable transitionDrawable = this.mBackground;
            int i7 = 0;
            if (z) {
                i7 = (int) (this.mAnimator.getDuration() * 0.6d);
            }
            transitionDrawable.startTransition(i7);
            this.mAnimator.addListener(this.mVisibleOnStart);
        } else {
            View view = this.mDetail;
            Runnable runnable = this.mReverseBackground;
            long j = 0;
            if (z) {
                j = (long) (this.mAnimator.getDuration() * 0.65d);
            }
            view.postDelayed(runnable, j);
            this.mAnimator.addListener(this.mGoneOnEnd);
        }
        this.mAnimator.start();
        return this.mAnimator.getDuration();
    }
}