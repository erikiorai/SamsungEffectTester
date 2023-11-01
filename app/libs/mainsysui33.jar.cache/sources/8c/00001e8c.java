package com.android.systemui.navigationbar.buttons;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.CanvasProperty;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Trace;
import android.view.RenderNodeAnimator;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import androidx.annotation.Keep;
import com.android.settingslib.widget.ActionBarShadowController;
import java.util.ArrayList;
import java.util.HashSet;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/buttons/KeyButtonRipple.class */
public class KeyButtonRipple extends Drawable {
    public static final Interpolator ALPHA_OUT_INTERPOLATOR = new PathInterpolator(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, 0.8f, 1.0f);
    public CanvasProperty<Float> mBottomProp;
    public boolean mDark;
    public boolean mDelayTouchFeedback;
    public boolean mDrawingHardwareGlow;
    public boolean mLastDark;
    public CanvasProperty<Float> mLeftProp;
    public int mMaxWidth;
    public final int mMaxWidthResource;
    public CanvasProperty<Paint> mPaintProp;
    public boolean mPressed;
    public CanvasProperty<Float> mRightProp;
    public Paint mRipplePaint;
    public CanvasProperty<Float> mRxProp;
    public CanvasProperty<Float> mRyProp;
    public boolean mSupportHardware;
    public final View mTargetView;
    public CanvasProperty<Float> mTopProp;
    public boolean mVisible;
    public float mGlowAlpha = ActionBarShadowController.ELEVATION_LOW;
    public float mGlowScale = 1.0f;
    public final Interpolator mInterpolator = new LogInterpolator();
    public final Handler mHandler = new Handler();
    public final HashSet<Animator> mRunningAnimations = new HashSet<>();
    public final ArrayList<Animator> mTmpArray = new ArrayList<>();
    public final TraceAnimatorListener mExitHwTraceAnimator = new TraceAnimatorListener("exitHardware");
    public final TraceAnimatorListener mEnterHwTraceAnimator = new TraceAnimatorListener("enterHardware");
    public Type mType = Type.ROUNDED_RECT;
    public final AnimatorListenerAdapter mAnimatorListener = new AnimatorListenerAdapter() { // from class: com.android.systemui.navigationbar.buttons.KeyButtonRipple.1
        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            KeyButtonRipple.this.mRunningAnimations.remove(animator);
            if (!KeyButtonRipple.this.mRunningAnimations.isEmpty() || KeyButtonRipple.this.mPressed) {
                return;
            }
            KeyButtonRipple.this.mVisible = false;
            KeyButtonRipple.this.mDrawingHardwareGlow = false;
            KeyButtonRipple.this.invalidateSelf();
        }
    };

    /* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/buttons/KeyButtonRipple$LogInterpolator.class */
    public static final class LogInterpolator implements Interpolator {
        public LogInterpolator() {
        }

        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f) {
            return 1.0f - ((float) Math.pow(400.0d, (-f) * 1.4d));
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/buttons/KeyButtonRipple$TraceAnimatorListener.class */
    public static final class TraceAnimatorListener extends AnimatorListenerAdapter {
        public final String mName;

        public TraceAnimatorListener(String str) {
            this.mName = str;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            if (Trace.isEnabled()) {
                Trace.instant(4096L, "KeyButtonRipple.cancel." + this.mName);
            }
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            if (Trace.isEnabled()) {
                Trace.instant(4096L, "KeyButtonRipple.end." + this.mName);
            }
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
            if (Trace.isEnabled()) {
                Trace.instant(4096L, "KeyButtonRipple.start." + this.mName);
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/buttons/KeyButtonRipple$Type.class */
    public enum Type {
        OVAL,
        ROUNDED_RECT
    }

    public KeyButtonRipple(Context context, View view, int i) {
        this.mMaxWidthResource = i;
        this.mMaxWidth = context.getResources().getDimensionPixelSize(i);
        this.mTargetView = view;
    }

    public void abortDelayedRipple() {
        this.mHandler.removeCallbacksAndMessages(null);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        boolean isHardwareAccelerated = canvas.isHardwareAccelerated();
        this.mSupportHardware = isHardwareAccelerated;
        if (isHardwareAccelerated) {
            drawHardware((RecordingCanvas) canvas);
        } else {
            drawSoftware(canvas);
        }
    }

    public final void drawHardware(RecordingCanvas recordingCanvas) {
        if (this.mDrawingHardwareGlow) {
            if (this.mType == Type.ROUNDED_RECT) {
                recordingCanvas.drawRoundRect(this.mLeftProp, this.mTopProp, this.mRightProp, this.mBottomProp, this.mRxProp, this.mRyProp, this.mPaintProp);
            } else {
                recordingCanvas.drawCircle(CanvasProperty.createFloat(getBounds().width() / 2), CanvasProperty.createFloat(getBounds().height() / 2), CanvasProperty.createFloat((Math.min(getBounds().width(), getBounds().height()) * 1.0f) / 2.0f), this.mPaintProp);
            }
        }
    }

    public final void drawSoftware(Canvas canvas) {
        if (this.mGlowAlpha > ActionBarShadowController.ELEVATION_LOW) {
            Paint ripplePaint = getRipplePaint();
            ripplePaint.setAlpha((int) (this.mGlowAlpha * 255.0f));
            float width = getBounds().width();
            float height = getBounds().height();
            boolean z = width > height;
            float rippleSize = getRippleSize() * this.mGlowScale * 0.5f;
            float f = width * 0.5f;
            float f2 = height * 0.5f;
            float f3 = z ? rippleSize : f;
            if (z) {
                rippleSize = f2;
            }
            float f4 = z ? f2 : f;
            if (this.mType == Type.ROUNDED_RECT) {
                canvas.drawRoundRect(f - f3, f2 - rippleSize, f3 + f, f2 + rippleSize, f4, f4, ripplePaint);
                return;
            }
            canvas.save();
            canvas.translate(f, f2);
            float min = Math.min(f3, rippleSize);
            float f5 = -min;
            canvas.drawOval(f5, f5, min, min, ripplePaint);
            canvas.restore();
        }
    }

    public final void endAnimations(String str, boolean z) {
        if (Trace.isEnabled()) {
            Trace.instant(4096L, "KeyButtonRipple.endAnim: reason=" + str + " cancel=" + z);
        }
        this.mVisible = false;
        this.mTmpArray.addAll(this.mRunningAnimations);
        int size = this.mTmpArray.size();
        for (int i = 0; i < size; i++) {
            Animator animator = this.mTmpArray.get(i);
            if (z) {
                animator.cancel();
            } else {
                animator.end();
            }
        }
        this.mTmpArray.clear();
        this.mRunningAnimations.clear();
        this.mHandler.removeCallbacksAndMessages(null);
    }

    public final void enterHardware() {
        endAnimations("enterHardware", true);
        this.mVisible = true;
        this.mDrawingHardwareGlow = true;
        setExtendStart(CanvasProperty.createFloat(getExtendSize() / 2));
        Animator renderNodeAnimator = new RenderNodeAnimator(getExtendStart(), (getExtendSize() / 2) - ((getRippleSize() * 1.35f) / 2.0f));
        renderNodeAnimator.setDuration(350L);
        renderNodeAnimator.setInterpolator(this.mInterpolator);
        renderNodeAnimator.addListener(this.mAnimatorListener);
        renderNodeAnimator.setTarget(this.mTargetView);
        setExtendEnd(CanvasProperty.createFloat(getExtendSize() / 2));
        Animator renderNodeAnimator2 = new RenderNodeAnimator(getExtendEnd(), (getExtendSize() / 2) + ((getRippleSize() * 1.35f) / 2.0f));
        renderNodeAnimator2.setDuration(350L);
        renderNodeAnimator2.setInterpolator(this.mInterpolator);
        renderNodeAnimator2.addListener(this.mAnimatorListener);
        renderNodeAnimator2.addListener(this.mEnterHwTraceAnimator);
        renderNodeAnimator2.setTarget(this.mTargetView);
        if (isHorizontal()) {
            this.mTopProp = CanvasProperty.createFloat((float) ActionBarShadowController.ELEVATION_LOW);
            this.mBottomProp = CanvasProperty.createFloat(getBounds().height());
            this.mRxProp = CanvasProperty.createFloat(getBounds().height() / 2);
            this.mRyProp = CanvasProperty.createFloat(getBounds().height() / 2);
        } else {
            this.mLeftProp = CanvasProperty.createFloat((float) ActionBarShadowController.ELEVATION_LOW);
            this.mRightProp = CanvasProperty.createFloat(getBounds().width());
            this.mRxProp = CanvasProperty.createFloat(getBounds().width() / 2);
            this.mRyProp = CanvasProperty.createFloat(getBounds().width() / 2);
        }
        this.mGlowScale = 1.35f;
        this.mGlowAlpha = getMaxGlowAlpha();
        Paint ripplePaint = getRipplePaint();
        this.mRipplePaint = ripplePaint;
        ripplePaint.setAlpha((int) (this.mGlowAlpha * 255.0f));
        this.mPaintProp = CanvasProperty.createPaint(this.mRipplePaint);
        renderNodeAnimator.start();
        renderNodeAnimator2.start();
        this.mRunningAnimations.add(renderNodeAnimator);
        this.mRunningAnimations.add(renderNodeAnimator2);
        invalidateSelf();
        if (!this.mDelayTouchFeedback || this.mPressed) {
            return;
        }
        exitHardware();
    }

    public final void enterSoftware() {
        endAnimations("enterSoftware", true);
        this.mVisible = true;
        this.mGlowAlpha = getMaxGlowAlpha();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "glowScale", ActionBarShadowController.ELEVATION_LOW, 1.35f);
        ofFloat.setInterpolator(this.mInterpolator);
        ofFloat.setDuration(350L);
        ofFloat.addListener(this.mAnimatorListener);
        ofFloat.start();
        this.mRunningAnimations.add(ofFloat);
        if (!this.mDelayTouchFeedback || this.mPressed) {
            return;
        }
        exitSoftware();
    }

    public final void exitHardware() {
        this.mPaintProp = CanvasProperty.createPaint(getRipplePaint());
        Animator renderNodeAnimator = new RenderNodeAnimator(this.mPaintProp, 1, (float) ActionBarShadowController.ELEVATION_LOW);
        renderNodeAnimator.setDuration(450L);
        renderNodeAnimator.setInterpolator(ALPHA_OUT_INTERPOLATOR);
        renderNodeAnimator.addListener(this.mAnimatorListener);
        renderNodeAnimator.addListener(this.mExitHwTraceAnimator);
        renderNodeAnimator.setTarget(this.mTargetView);
        renderNodeAnimator.start();
        this.mRunningAnimations.add(renderNodeAnimator);
        invalidateSelf();
    }

    public final void exitSoftware() {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "glowAlpha", this.mGlowAlpha, ActionBarShadowController.ELEVATION_LOW);
        ofFloat.setInterpolator(ALPHA_OUT_INTERPOLATOR);
        ofFloat.setDuration(450L);
        ofFloat.addListener(this.mAnimatorListener);
        ofFloat.start();
        this.mRunningAnimations.add(ofFloat);
    }

    public final CanvasProperty<Float> getExtendEnd() {
        return isHorizontal() ? this.mRightProp : this.mBottomProp;
    }

    public final int getExtendSize() {
        boolean isHorizontal = isHorizontal();
        Rect bounds = getBounds();
        return isHorizontal ? bounds.width() : bounds.height();
    }

    public final CanvasProperty<Float> getExtendStart() {
        return isHorizontal() ? this.mLeftProp : this.mTopProp;
    }

    @Keep
    public float getGlowAlpha() {
        return this.mGlowAlpha;
    }

    @Keep
    public float getGlowScale() {
        return this.mGlowScale;
    }

    public final float getMaxGlowAlpha() {
        return this.mLastDark ? 0.1f : 0.2f;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    public final Paint getRipplePaint() {
        if (this.mRipplePaint == null) {
            Paint paint = new Paint();
            this.mRipplePaint = paint;
            paint.setAntiAlias(true);
            this.mRipplePaint.setColor(this.mLastDark ? -16777216 : -1);
        }
        return this.mRipplePaint;
    }

    public final int getRippleSize() {
        return Math.min(isHorizontal() ? getBounds().width() : getBounds().height(), this.mMaxWidth);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean hasFocusStateSpecified() {
        return true;
    }

    public final boolean isHorizontal() {
        return getBounds().width() > getBounds().height();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public void jumpToCurrentState() {
        endAnimations("jumpToCurrentState", false);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean onStateChange(int[] iArr) {
        boolean z;
        int i = 0;
        while (true) {
            if (i >= iArr.length) {
                z = false;
                break;
            } else if (iArr[i] == 16842919) {
                z = true;
                break;
            } else {
                i++;
            }
        }
        if (z != this.mPressed) {
            setPressed(z);
            this.mPressed = z;
            return true;
        }
        return false;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    public void setDarkIntensity(float f) {
        this.mDark = f >= 0.5f;
    }

    public void setDelayTouchFeedback(boolean z) {
        this.mDelayTouchFeedback = z;
    }

    public final void setExtendEnd(CanvasProperty<Float> canvasProperty) {
        if (isHorizontal()) {
            this.mRightProp = canvasProperty;
        } else {
            this.mBottomProp = canvasProperty;
        }
    }

    public final void setExtendStart(CanvasProperty<Float> canvasProperty) {
        if (isHorizontal()) {
            this.mLeftProp = canvasProperty;
        } else {
            this.mTopProp = canvasProperty;
        }
    }

    @Keep
    public void setGlowAlpha(float f) {
        this.mGlowAlpha = f;
        invalidateSelf();
    }

    @Keep
    public void setGlowScale(float f) {
        this.mGlowScale = f;
        invalidateSelf();
    }

    public void setPressed(boolean z) {
        boolean z2 = this.mDark;
        if (z2 != this.mLastDark && z) {
            this.mRipplePaint = null;
            this.mLastDark = z2;
        }
        if (this.mSupportHardware) {
            setPressedHardware(z);
        } else {
            setPressedSoftware(z);
        }
    }

    public final void setPressedHardware(boolean z) {
        if (!z) {
            exitHardware();
        } else if (!this.mDelayTouchFeedback) {
            enterHardware();
        } else if (this.mRunningAnimations.isEmpty()) {
            this.mHandler.removeCallbacksAndMessages(null);
            this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.navigationbar.buttons.KeyButtonRipple$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    KeyButtonRipple.this.enterHardware();
                }
            }, ViewConfiguration.getTapTimeout());
        } else if (this.mVisible) {
            enterHardware();
        }
    }

    public final void setPressedSoftware(boolean z) {
        if (!z) {
            exitSoftware();
        } else if (!this.mDelayTouchFeedback) {
            enterSoftware();
        } else if (this.mRunningAnimations.isEmpty()) {
            this.mHandler.removeCallbacksAndMessages(null);
            this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.navigationbar.buttons.KeyButtonRipple$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    KeyButtonRipple.this.enterSoftware();
                }
            }, ViewConfiguration.getTapTimeout());
        } else if (this.mVisible) {
            enterSoftware();
        }
    }

    public void setType(Type type) {
        this.mType = type;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean setVisible(boolean z, boolean z2) {
        boolean visible = super.setVisible(z, z2);
        if (visible) {
            jumpToCurrentState();
        }
        return visible;
    }

    public void updateResources() {
        this.mMaxWidth = this.mTargetView.getContext().getResources().getDimensionPixelSize(this.mMaxWidthResource);
        invalidateSelf();
    }
}