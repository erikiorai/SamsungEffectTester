package com.airbnb.lottie.utils;

import android.view.Choreographer;
import com.airbnb.lottie.L;
import com.airbnb.lottie.LottieComposition;
import com.android.settingslib.widget.ActionBarShadowController;

/* loaded from: mainsysui33.jar:com/airbnb/lottie/utils/LottieValueAnimator.class */
public class LottieValueAnimator extends BaseLottieAnimator implements Choreographer.FrameCallback {
    public LottieComposition composition;
    public float speed = 1.0f;
    public boolean speedReversedForRepeatMode = false;
    public long lastFrameTimeNs = 0;
    public float frame = ActionBarShadowController.ELEVATION_LOW;
    public int repeatCount = 0;
    public float minFrame = -2.1474836E9f;
    public float maxFrame = 2.1474836E9f;
    public boolean running = false;

    @Override // android.animation.ValueAnimator, android.animation.Animator
    public void cancel() {
        notifyCancel();
        removeFrameCallback();
    }

    public void clearComposition() {
        this.composition = null;
        this.minFrame = -2.1474836E9f;
        this.maxFrame = 2.1474836E9f;
    }

    @Override // android.view.Choreographer.FrameCallback
    public void doFrame(long j) {
        postFrameCallback();
        if (this.composition == null || !isRunning()) {
            return;
        }
        L.beginSection("LottieValueAnimator#doFrame");
        long j2 = this.lastFrameTimeNs;
        long j3 = 0;
        if (j2 != 0) {
            j3 = j - j2;
        }
        float frameDurationNs = ((float) j3) / getFrameDurationNs();
        float f = this.frame;
        float f2 = frameDurationNs;
        if (isReversed()) {
            f2 = -frameDurationNs;
        }
        float f3 = f + f2;
        this.frame = f3;
        boolean contains = MiscUtils.contains(f3, getMinFrame(), getMaxFrame());
        this.frame = MiscUtils.clamp(this.frame, getMinFrame(), getMaxFrame());
        this.lastFrameTimeNs = j;
        notifyUpdate();
        if (!contains) {
            if (getRepeatCount() == -1 || this.repeatCount < getRepeatCount()) {
                notifyRepeat();
                this.repeatCount++;
                if (getRepeatMode() == 2) {
                    this.speedReversedForRepeatMode = !this.speedReversedForRepeatMode;
                    reverseAnimationSpeed();
                } else {
                    this.frame = isReversed() ? getMaxFrame() : getMinFrame();
                }
                this.lastFrameTimeNs = j;
            } else {
                this.frame = this.speed < ActionBarShadowController.ELEVATION_LOW ? getMinFrame() : getMaxFrame();
                removeFrameCallback();
                notifyEnd(isReversed());
            }
        }
        verifyFrame();
        L.endSection("LottieValueAnimator#doFrame");
    }

    public void endAnimation() {
        removeFrameCallback();
        notifyEnd(isReversed());
    }

    @Override // android.animation.ValueAnimator
    public float getAnimatedFraction() {
        float minFrame;
        float maxFrame;
        float minFrame2;
        if (this.composition == null) {
            return ActionBarShadowController.ELEVATION_LOW;
        }
        if (isReversed()) {
            minFrame = getMaxFrame() - this.frame;
            maxFrame = getMaxFrame();
            minFrame2 = getMinFrame();
        } else {
            minFrame = this.frame - getMinFrame();
            maxFrame = getMaxFrame();
            minFrame2 = getMinFrame();
        }
        return minFrame / (maxFrame - minFrame2);
    }

    @Override // android.animation.ValueAnimator
    public Object getAnimatedValue() {
        return Float.valueOf(getAnimatedValueAbsolute());
    }

    public float getAnimatedValueAbsolute() {
        LottieComposition lottieComposition = this.composition;
        return lottieComposition == null ? ActionBarShadowController.ELEVATION_LOW : (this.frame - lottieComposition.getStartFrame()) / (this.composition.getEndFrame() - this.composition.getStartFrame());
    }

    @Override // android.animation.ValueAnimator, android.animation.Animator
    public long getDuration() {
        LottieComposition lottieComposition = this.composition;
        return lottieComposition == null ? 0L : lottieComposition.getDuration();
    }

    public float getFrame() {
        return this.frame;
    }

    public final float getFrameDurationNs() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            return Float.MAX_VALUE;
        }
        return (1.0E9f / lottieComposition.getFrameRate()) / Math.abs(this.speed);
    }

    public float getMaxFrame() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            return ActionBarShadowController.ELEVATION_LOW;
        }
        float f = this.maxFrame;
        float f2 = f;
        if (f == 2.1474836E9f) {
            f2 = lottieComposition.getEndFrame();
        }
        return f2;
    }

    public float getMinFrame() {
        LottieComposition lottieComposition = this.composition;
        if (lottieComposition == null) {
            return ActionBarShadowController.ELEVATION_LOW;
        }
        float f = this.minFrame;
        float f2 = f;
        if (f == -2.1474836E9f) {
            f2 = lottieComposition.getStartFrame();
        }
        return f2;
    }

    public float getSpeed() {
        return this.speed;
    }

    public final boolean isReversed() {
        return getSpeed() < ActionBarShadowController.ELEVATION_LOW;
    }

    @Override // android.animation.ValueAnimator, android.animation.Animator
    public boolean isRunning() {
        return this.running;
    }

    public void pauseAnimation() {
        removeFrameCallback();
    }

    public void playAnimation() {
        this.running = true;
        notifyStart(isReversed());
        setFrame((int) (isReversed() ? getMaxFrame() : getMinFrame()));
        this.lastFrameTimeNs = 0L;
        this.repeatCount = 0;
        postFrameCallback();
    }

    public void postFrameCallback() {
        if (isRunning()) {
            removeFrameCallback(false);
            Choreographer.getInstance().postFrameCallback(this);
        }
    }

    public void removeFrameCallback() {
        removeFrameCallback(true);
    }

    public void removeFrameCallback(boolean z) {
        Choreographer.getInstance().removeFrameCallback(this);
        if (z) {
            this.running = false;
        }
    }

    public void resumeAnimation() {
        this.running = true;
        postFrameCallback();
        this.lastFrameTimeNs = 0L;
        if (isReversed() && getFrame() == getMinFrame()) {
            this.frame = getMaxFrame();
        } else if (isReversed() || getFrame() != getMaxFrame()) {
        } else {
            this.frame = getMinFrame();
        }
    }

    public void reverseAnimationSpeed() {
        setSpeed(-getSpeed());
    }

    public void setComposition(LottieComposition lottieComposition) {
        boolean z = this.composition == null;
        this.composition = lottieComposition;
        if (z) {
            setMinAndMaxFrames((int) Math.max(this.minFrame, lottieComposition.getStartFrame()), (int) Math.min(this.maxFrame, lottieComposition.getEndFrame()));
        } else {
            setMinAndMaxFrames((int) lottieComposition.getStartFrame(), (int) lottieComposition.getEndFrame());
        }
        float f = this.frame;
        this.frame = ActionBarShadowController.ELEVATION_LOW;
        setFrame((int) f);
    }

    public void setFrame(float f) {
        if (this.frame == f) {
            return;
        }
        this.frame = MiscUtils.clamp(f, getMinFrame(), getMaxFrame());
        this.lastFrameTimeNs = 0L;
        notifyUpdate();
    }

    public void setMaxFrame(float f) {
        setMinAndMaxFrames(this.minFrame, f);
    }

    public void setMinAndMaxFrames(float f, float f2) {
        if (f > f2) {
            throw new IllegalArgumentException(String.format("minFrame (%s) must be <= maxFrame (%s)", Float.valueOf(f), Float.valueOf(f2)));
        }
        LottieComposition lottieComposition = this.composition;
        float startFrame = lottieComposition == null ? -3.4028235E38f : lottieComposition.getStartFrame();
        LottieComposition lottieComposition2 = this.composition;
        float endFrame = lottieComposition2 == null ? Float.MAX_VALUE : lottieComposition2.getEndFrame();
        this.minFrame = MiscUtils.clamp(f, startFrame, endFrame);
        this.maxFrame = MiscUtils.clamp(f2, startFrame, endFrame);
        setFrame((int) MiscUtils.clamp(this.frame, f, f2));
    }

    public void setMinFrame(int i) {
        setMinAndMaxFrames(i, (int) this.maxFrame);
    }

    @Override // android.animation.ValueAnimator
    public void setRepeatMode(int i) {
        super.setRepeatMode(i);
        if (i == 2 || !this.speedReversedForRepeatMode) {
            return;
        }
        this.speedReversedForRepeatMode = false;
        reverseAnimationSpeed();
    }

    public void setSpeed(float f) {
        this.speed = f;
    }

    public final void verifyFrame() {
        if (this.composition == null) {
            return;
        }
        float f = this.frame;
        if (f < this.minFrame || f > this.maxFrame) {
            throw new IllegalStateException(String.format("Frame must be [%f,%f]. It is %f", Float.valueOf(this.minFrame), Float.valueOf(this.maxFrame), Float.valueOf(this.frame)));
        }
    }
}