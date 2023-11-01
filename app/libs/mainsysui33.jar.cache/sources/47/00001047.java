package com.android.systemui.accessibility;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.os.RemoteException;
import android.util.Log;
import android.view.accessibility.IRemoteMagnificationAnimationCallback;
import android.view.animation.AccelerateInterpolator;
import com.android.internal.annotations.VisibleForTesting;
import com.android.settingslib.widget.ActionBarShadowController;
import com.android.systemui.R$integer;

/* loaded from: mainsysui33.jar:com/android/systemui/accessibility/WindowMagnificationAnimationController.class */
public class WindowMagnificationAnimationController implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {
    public static final boolean DEBUG = Log.isLoggable("WindowMagnificationAnimationController", 3);
    public IRemoteMagnificationAnimationCallback mAnimationCallback;
    public final Context mContext;
    public WindowMagnificationController mController;
    public boolean mEndAnimationCanceled;
    public final AnimationSpec mEndSpec;
    public float mMagnificationFrameOffsetRatioX;
    public float mMagnificationFrameOffsetRatioY;
    public final AnimationSpec mStartSpec;
    public int mState;
    public final ValueAnimator mValueAnimator;

    /* loaded from: mainsysui33.jar:com/android/systemui/accessibility/WindowMagnificationAnimationController$AnimationSpec.class */
    public static class AnimationSpec {
        public float mCenterX;
        public float mCenterY;
        public float mScale;

        public AnimationSpec() {
            this.mScale = Float.NaN;
            this.mCenterX = Float.NaN;
            this.mCenterY = Float.NaN;
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            AnimationSpec animationSpec = (AnimationSpec) obj;
            if (this.mScale != animationSpec.mScale || this.mCenterX != animationSpec.mCenterX || this.mCenterY != animationSpec.mCenterY) {
                z = false;
            }
            return z;
        }

        public int hashCode() {
            float f = this.mScale;
            int i = 0;
            int floatToIntBits = f != ActionBarShadowController.ELEVATION_LOW ? Float.floatToIntBits(f) : 0;
            float f2 = this.mCenterX;
            int floatToIntBits2 = f2 != ActionBarShadowController.ELEVATION_LOW ? Float.floatToIntBits(f2) : 0;
            float f3 = this.mCenterY;
            if (f3 != ActionBarShadowController.ELEVATION_LOW) {
                i = Float.floatToIntBits(f3);
            }
            return (((floatToIntBits * 31) + floatToIntBits2) * 31) + i;
        }

        public void set(float f, float f2, float f3) {
            this.mScale = f;
            this.mCenterX = f2;
            this.mCenterY = f3;
        }

        public String toString() {
            return "AnimationSpec{mScale=" + this.mScale + ", mCenterX=" + this.mCenterX + ", mCenterY=" + this.mCenterY + '}';
        }
    }

    public WindowMagnificationAnimationController(Context context) {
        this(context, newValueAnimator(context.getResources()));
    }

    @VisibleForTesting
    public WindowMagnificationAnimationController(Context context, ValueAnimator valueAnimator) {
        this.mStartSpec = new AnimationSpec();
        this.mEndSpec = new AnimationSpec();
        this.mMagnificationFrameOffsetRatioX = ActionBarShadowController.ELEVATION_LOW;
        this.mMagnificationFrameOffsetRatioY = ActionBarShadowController.ELEVATION_LOW;
        this.mEndAnimationCanceled = false;
        this.mState = 0;
        this.mContext = context;
        this.mValueAnimator = valueAnimator;
        valueAnimator.addUpdateListener(this);
        valueAnimator.addListener(this);
    }

    public static ValueAnimator newValueAnimator(Resources resources) {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setDuration(resources.getInteger(17694722));
        valueAnimator.setInterpolator(new AccelerateInterpolator(2.5f));
        valueAnimator.setFloatValues(ActionBarShadowController.ELEVATION_LOW, 1.0f);
        return valueAnimator;
    }

    public void deleteWindowMagnification(IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) {
        if (this.mController == null) {
            return;
        }
        sendAnimationCallback(false);
        if (iRemoteMagnificationAnimationCallback == null) {
            int i = this.mState;
            if (i == 3 || i == 2) {
                this.mValueAnimator.cancel();
            }
            this.mController.deleteWindowMagnification();
            setState(0);
            return;
        }
        this.mAnimationCallback = iRemoteMagnificationAnimationCallback;
        int i2 = this.mState;
        if (i2 == 0 || i2 == 2) {
            if (i2 == 0) {
                sendAnimationCallback(true);
                return;
            }
            return;
        }
        this.mStartSpec.set(1.0f, Float.NaN, Float.NaN);
        this.mEndSpec.set(this.mController.getScale(), Float.NaN, Float.NaN);
        this.mValueAnimator.reverse();
        setState(2);
    }

    public void enableWindowMagnification(float f, float f2, float f3, float f4, float f5, IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) {
        if (this.mController == null) {
            return;
        }
        sendAnimationCallback(false);
        this.mMagnificationFrameOffsetRatioX = f4;
        this.mMagnificationFrameOffsetRatioY = f5;
        if (iRemoteMagnificationAnimationCallback == null) {
            int i = this.mState;
            if (i == 3 || i == 2) {
                this.mValueAnimator.cancel();
            }
            this.mController.enableWindowMagnificationInternal(f, f2, f3, this.mMagnificationFrameOffsetRatioX, this.mMagnificationFrameOffsetRatioY);
            setState(1);
            return;
        }
        this.mAnimationCallback = iRemoteMagnificationAnimationCallback;
        setupEnableAnimationSpecs(f, f2, f3);
        if (!this.mEndSpec.equals(this.mStartSpec)) {
            int i2 = this.mState;
            if (i2 == 2) {
                this.mValueAnimator.reverse();
            } else {
                if (i2 == 3) {
                    this.mValueAnimator.cancel();
                }
                this.mValueAnimator.start();
            }
            setState(3);
            return;
        }
        int i3 = this.mState;
        if (i3 == 0) {
            this.mController.enableWindowMagnificationInternal(f, f2, f3, this.mMagnificationFrameOffsetRatioX, this.mMagnificationFrameOffsetRatioY);
        } else if (i3 == 3 || i3 == 2) {
            this.mValueAnimator.cancel();
        }
        sendAnimationCallback(true);
        setState(1);
    }

    public boolean isAnimating() {
        return this.mValueAnimator.isRunning();
    }

    public void moveWindowMagnifierToPosition(float f, float f2, IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback) {
        int i = this.mState;
        if (i == 1) {
            this.mValueAnimator.setDuration(this.mContext.getResources().getInteger(17694720));
            enableWindowMagnification(Float.NaN, f, f2, Float.NaN, Float.NaN, iRemoteMagnificationAnimationCallback);
        } else if (i == 3) {
            sendAnimationCallback(false);
            this.mAnimationCallback = iRemoteMagnificationAnimationCallback;
            this.mValueAnimator.setDuration(this.mContext.getResources().getInteger(17694720));
            setupEnableAnimationSpecs(Float.NaN, f, f2);
        }
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationCancel(Animator animator) {
        this.mEndAnimationCanceled = true;
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator, boolean z) {
        WindowMagnificationController windowMagnificationController;
        if (this.mEndAnimationCanceled || (windowMagnificationController = this.mController) == null) {
            return;
        }
        if (Float.isNaN(windowMagnificationController.getScale())) {
            setState(0);
        } else {
            setState(1);
        }
        sendAnimationCallback(true);
        this.mValueAnimator.setDuration(this.mContext.getResources().getInteger(17694722));
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationRepeat(Animator animator) {
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {
        this.mEndAnimationCanceled = false;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        if (this.mController == null) {
            return;
        }
        float animatedFraction = valueAnimator.getAnimatedFraction();
        this.mController.enableWindowMagnificationInternal(this.mStartSpec.mScale + ((this.mEndSpec.mScale - this.mStartSpec.mScale) * animatedFraction), this.mStartSpec.mCenterX + ((this.mEndSpec.mCenterX - this.mStartSpec.mCenterX) * animatedFraction), this.mStartSpec.mCenterY + ((this.mEndSpec.mCenterY - this.mStartSpec.mCenterY) * animatedFraction), this.mMagnificationFrameOffsetRatioX, this.mMagnificationFrameOffsetRatioY);
    }

    public final void sendAnimationCallback(boolean z) {
        IRemoteMagnificationAnimationCallback iRemoteMagnificationAnimationCallback = this.mAnimationCallback;
        if (iRemoteMagnificationAnimationCallback != null) {
            try {
                iRemoteMagnificationAnimationCallback.onResult(z);
                if (DEBUG) {
                    Log.d("WindowMagnificationAnimationController", "sendAnimationCallback success = " + z);
                }
            } catch (RemoteException e) {
                Log.w("WindowMagnificationAnimationController", "sendAnimationCallback failed : " + e);
            }
            this.mAnimationCallback = null;
        }
    }

    public final void setState(int i) {
        if (DEBUG) {
            Log.d("WindowMagnificationAnimationController", "setState from " + this.mState + " to " + i);
        }
        this.mState = i;
    }

    public void setWindowMagnificationController(WindowMagnificationController windowMagnificationController) {
        this.mController = windowMagnificationController;
    }

    public final void setupEnableAnimationSpecs(float f, float f2, float f3) {
        WindowMagnificationController windowMagnificationController = this.mController;
        if (windowMagnificationController == null) {
            return;
        }
        float scale = windowMagnificationController.getScale();
        float centerX = this.mController.getCenterX();
        float centerY = this.mController.getCenterY();
        if (this.mState == 0) {
            this.mStartSpec.set(1.0f, f2, f3);
            AnimationSpec animationSpec = this.mEndSpec;
            float f4 = f;
            if (Float.isNaN(f)) {
                f4 = this.mContext.getResources().getInteger(R$integer.magnification_default_scale);
            }
            animationSpec.set(f4, f2, f3);
        } else {
            this.mStartSpec.set(scale, centerX, centerY);
            if (this.mState == 3) {
                scale = this.mEndSpec.mScale;
            }
            if (this.mState == 3) {
                centerX = this.mEndSpec.mCenterX;
            }
            if (this.mState == 3) {
                centerY = this.mEndSpec.mCenterY;
            }
            AnimationSpec animationSpec2 = this.mEndSpec;
            float f5 = f;
            if (Float.isNaN(f)) {
                f5 = scale;
            }
            float f6 = f2;
            if (Float.isNaN(f2)) {
                f6 = centerX;
            }
            float f7 = f3;
            if (Float.isNaN(f3)) {
                f7 = centerY;
            }
            animationSpec2.set(f5, f6, f7);
        }
        if (DEBUG) {
            Log.d("WindowMagnificationAnimationController", "SetupEnableAnimationSpecs : mStartSpec = " + this.mStartSpec + ", endSpec = " + this.mEndSpec);
        }
    }
}