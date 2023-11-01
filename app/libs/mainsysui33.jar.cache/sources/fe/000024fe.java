package com.android.systemui.scrim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.view.animation.DecelerateInterpolator;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.graphics.ColorUtils;
import com.android.settingslib.widget.ActionBarShadowController;

/* loaded from: mainsysui33.jar:com/android/systemui/scrim/ScrimDrawable.class */
public class ScrimDrawable extends Drawable {
    public int mAlpha = 255;
    public int mBottomEdgePosition;
    public ValueAnimator mColorAnimation;
    public ConcaveInfo mConcaveInfo;
    public float mCornerRadius;
    public boolean mCornerRadiusEnabled;
    public int mMainColor;
    public int mMainColorTo;
    public final Paint mPaint;

    /* loaded from: mainsysui33.jar:com/android/systemui/scrim/ScrimDrawable$ConcaveInfo.class */
    public static class ConcaveInfo {
        public float mPathOverlap;
        public final Path mPath = new Path();
        public final float[] mCornerRadii = {ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW};

        public void setCornerRadius(float f) {
            this.mPathOverlap = f;
            float[] fArr = this.mCornerRadii;
            fArr[0] = f;
            fArr[1] = f;
            fArr[2] = f;
            fArr[3] = f;
        }
    }

    public ScrimDrawable() {
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setStyle(Paint.Style.FILL);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setColor$0(int i, int i2, ValueAnimator valueAnimator) {
        this.mMainColor = ColorUtils.blendARGB(i, i2, ((Float) valueAnimator.getAnimatedValue()).floatValue());
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        this.mPaint.setColor(this.mMainColor);
        this.mPaint.setAlpha(this.mAlpha);
        if (this.mConcaveInfo != null) {
            drawConcave(canvas);
        } else if (!this.mCornerRadiusEnabled || this.mCornerRadius <= ActionBarShadowController.ELEVATION_LOW) {
            canvas.drawRect(getBounds().left, getBounds().top, getBounds().right, getBounds().bottom, this.mPaint);
        } else {
            float f = getBounds().left;
            float f2 = getBounds().top;
            float f3 = getBounds().right;
            float f4 = getBounds().bottom;
            float f5 = this.mCornerRadius;
            canvas.drawRoundRect(f, f2, f3, f4, f5, f5, this.mPaint);
        }
    }

    public final void drawConcave(Canvas canvas) {
        canvas.clipOutPath(this.mConcaveInfo.mPath);
        canvas.drawRect(getBounds().left, getBounds().top, getBounds().right, this.mBottomEdgePosition + this.mConcaveInfo.mPathOverlap, this.mPaint);
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mAlpha;
    }

    @Override // android.graphics.drawable.Drawable
    public ColorFilter getColorFilter() {
        return this.mPaint.getColorFilter();
    }

    @VisibleForTesting
    public int getMainColor() {
        return this.mMainColor;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        updatePath();
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        if (i != this.mAlpha) {
            this.mAlpha = i;
            invalidateSelf();
        }
    }

    public void setBottomEdgeConcave(boolean z) {
        if (!z || this.mConcaveInfo == null) {
            if (z) {
                ConcaveInfo concaveInfo = new ConcaveInfo();
                this.mConcaveInfo = concaveInfo;
                concaveInfo.setCornerRadius(this.mCornerRadius);
            } else {
                this.mConcaveInfo = null;
            }
            invalidateSelf();
        }
    }

    public void setBottomEdgePosition(int i) {
        if (this.mBottomEdgePosition == i) {
            return;
        }
        this.mBottomEdgePosition = i;
        if (this.mConcaveInfo == null) {
            return;
        }
        updatePath();
        invalidateSelf();
    }

    public void setColor(final int i, boolean z) {
        if (i == this.mMainColorTo) {
            return;
        }
        ValueAnimator valueAnimator = this.mColorAnimation;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mColorAnimation.cancel();
        }
        this.mMainColorTo = i;
        if (!z) {
            this.mMainColor = i;
            invalidateSelf();
            return;
        }
        final int i2 = this.mMainColor;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(ActionBarShadowController.ELEVATION_LOW, 1.0f);
        ofFloat.setDuration(360L);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.scrim.ScrimDrawable$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                ScrimDrawable.this.lambda$setColor$0(i2, i, valueAnimator2);
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.scrim.ScrimDrawable.1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator, boolean z2) {
                if (ScrimDrawable.this.mColorAnimation == animator) {
                    ScrimDrawable.this.mColorAnimation = null;
                }
            }
        });
        ofFloat.setInterpolator(new DecelerateInterpolator());
        ofFloat.start();
        this.mColorAnimation = ofFloat;
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    public void setRoundedCorners(float f) {
        if (f == this.mCornerRadius) {
            return;
        }
        this.mCornerRadius = f;
        ConcaveInfo concaveInfo = this.mConcaveInfo;
        if (concaveInfo != null) {
            concaveInfo.setCornerRadius(f);
            updatePath();
        }
        invalidateSelf();
    }

    public void setRoundedCornersEnabled(boolean z) {
        if (this.mCornerRadiusEnabled == z) {
            return;
        }
        this.mCornerRadiusEnabled = z;
        invalidateSelf();
    }

    public void setXfermode(Xfermode xfermode) {
        this.mPaint.setXfermode(xfermode);
        invalidateSelf();
    }

    public final void updatePath() {
        ConcaveInfo concaveInfo = this.mConcaveInfo;
        if (concaveInfo == null) {
            return;
        }
        concaveInfo.mPath.reset();
        int i = this.mBottomEdgePosition;
        this.mConcaveInfo.mPath.addRoundRect(getBounds().left, i, getBounds().right, i + this.mConcaveInfo.mPathOverlap, this.mConcaveInfo.mCornerRadii, Path.Direction.CW);
    }
}