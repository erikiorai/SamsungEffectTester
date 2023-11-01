package com.android.systemui.privacy.television;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import androidx.annotation.Keep;
import com.android.systemui.R$anim;
import com.android.systemui.R$color;
import com.android.systemui.R$dimen;

/* loaded from: mainsysui33.jar:com/android/systemui/privacy/television/PrivacyChipDrawable.class */
public class PrivacyChipDrawable extends Drawable {
    public static final String TAG = PrivacyChipDrawable.class.getSimpleName();
    public int mBgAlpha;
    public final int mBgHeight;
    public final Paint mBgPaint;
    public final int mBgRadius;
    public final int mBgWidth;
    public final Paint mChipPaint;
    public final AnimatorSet mCollapse;
    public int mDotAlpha;
    public final int mDotSize;
    public final AnimatorSet mExpand;
    public final AnimatorSet mFadeIn;
    public final AnimatorSet mFadeOut;
    public float mHeight;
    public final int mIconPadding;
    public final int mIconWidth;
    public boolean mIsExpanded = true;
    public boolean mIsRtl;
    public PrivacyChipDrawableListener mListener;
    public float mMarginEnd;
    public final int mMinWidth;
    public float mRadius;
    public float mTargetWidth;
    public float mWidth;
    public Animator mWidthAnimator;

    /* loaded from: mainsysui33.jar:com/android/systemui/privacy/television/PrivacyChipDrawable$PrivacyChipDrawableListener.class */
    public interface PrivacyChipDrawableListener {
        void onFadeOutFinished();
    }

    public PrivacyChipDrawable(Context context) {
        Paint paint = new Paint();
        this.mChipPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(context.getColor(R$color.privacy_circle));
        paint.setAlpha(this.mDotAlpha);
        paint.setFlags(1);
        Paint paint2 = new Paint();
        this.mBgPaint = paint2;
        paint2.setStyle(Paint.Style.FILL);
        paint2.setColor(context.getColor(R$color.privacy_chip_dot_bg_tint));
        paint2.setAlpha(this.mBgAlpha);
        paint2.setFlags(1);
        this.mBgWidth = context.getResources().getDimensionPixelSize(R$dimen.privacy_chip_dot_bg_width);
        this.mBgHeight = context.getResources().getDimensionPixelSize(R$dimen.privacy_chip_dot_bg_height);
        this.mBgRadius = context.getResources().getDimensionPixelSize(R$dimen.privacy_chip_dot_bg_radius);
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R$dimen.privacy_chip_min_width);
        this.mMinWidth = dimensionPixelSize;
        this.mIconWidth = context.getResources().getDimensionPixelSize(R$dimen.privacy_chip_icon_size);
        this.mIconPadding = context.getResources().getDimensionPixelSize(R$dimen.privacy_chip_icon_margin_in_between);
        this.mDotSize = context.getResources().getDimensionPixelSize(R$dimen.privacy_chip_dot_size);
        this.mWidth = dimensionPixelSize;
        this.mHeight = context.getResources().getDimensionPixelSize(R$dimen.privacy_chip_height);
        this.mRadius = context.getResources().getDimensionPixelSize(R$dimen.privacy_chip_radius);
        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(context, R$anim.tv_privacy_chip_expand);
        this.mExpand = animatorSet;
        animatorSet.setTarget(this);
        AnimatorSet animatorSet2 = (AnimatorSet) AnimatorInflater.loadAnimator(context, R$anim.tv_privacy_chip_collapse);
        this.mCollapse = animatorSet2;
        animatorSet2.setTarget(this);
        AnimatorSet animatorSet3 = (AnimatorSet) AnimatorInflater.loadAnimator(context, R$anim.tv_privacy_chip_fade_in);
        this.mFadeIn = animatorSet3;
        animatorSet3.setTarget(this);
        AnimatorSet animatorSet4 = (AnimatorSet) AnimatorInflater.loadAnimator(context, R$anim.tv_privacy_chip_fade_out);
        this.mFadeOut = animatorSet4;
        animatorSet4.setTarget(this);
        animatorSet4.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.privacy.television.PrivacyChipDrawable.1
            public boolean mCancelled;

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                this.mCancelled = true;
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                if (this.mCancelled || PrivacyChipDrawable.this.mListener == null) {
                    return;
                }
                PrivacyChipDrawable.this.mListener.onFadeOutFinished();
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                this.mCancelled = false;
            }
        });
    }

    public final void animateToNewTargetWidth(float f) {
        if (f != this.mTargetWidth) {
            this.mTargetWidth = f;
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "width", f);
            ofFloat.start();
            Animator animator = this.mWidthAnimator;
            if (animator != null) {
                animator.cancel();
            }
            this.mWidthAnimator = ofFloat;
        }
    }

    public void collapse() {
        if (this.mIsExpanded) {
            this.mIsExpanded = false;
            animateToNewTargetWidth(this.mDotSize);
            this.mCollapse.start();
            this.mExpand.cancel();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        int i = (bounds.bottom - bounds.top) / 2;
        boolean z = this.mIsRtl;
        float f = z ? bounds.left : bounds.right - this.mBgWidth;
        float f2 = i;
        int i2 = this.mBgHeight;
        RectF rectF = new RectF(f, f2 - (i2 / 2.0f), z ? bounds.left + this.mBgWidth : bounds.right, (i2 / 2.0f) + f2);
        int i3 = this.mBgRadius;
        canvas.drawRoundRect(rectF, i3, i3, this.mBgPaint);
        boolean z2 = this.mIsRtl;
        float f3 = z2 ? bounds.left + this.mMarginEnd : (bounds.right - this.mWidth) - this.mMarginEnd;
        float f4 = this.mHeight;
        RectF rectF2 = new RectF(f3, f2 - (f4 / 2.0f), z2 ? bounds.left + this.mWidth + this.mMarginEnd : bounds.right - this.mMarginEnd, f2 + (f4 / 2.0f));
        float f5 = this.mRadius;
        canvas.drawRoundRect(rectF2, f5, f5, this.mChipPaint);
    }

    public final void expand() {
        if (this.mIsExpanded) {
            return;
        }
        this.mIsExpanded = true;
        this.mExpand.start();
        this.mCollapse.cancel();
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mDotAlpha;
    }

    @Keep
    public int getBgAlpha() {
        return this.mBgAlpha;
    }

    @Keep
    public int getDotAlpha() {
        return this.mDotAlpha;
    }

    @Keep
    public float getHeight() {
        return this.mHeight;
    }

    @Keep
    public float getMarginEnd() {
        return this.mMarginEnd;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Keep
    public float getRadius() {
        return this.mRadius;
    }

    @Keep
    public float getWidth() {
        return this.mWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        setDotAlpha(i);
        setBgAlpha(i);
    }

    @Keep
    public void setBgAlpha(int i) {
        this.mBgAlpha = i;
        this.mBgPaint.setAlpha(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    @Keep
    public void setDotAlpha(int i) {
        this.mDotAlpha = i;
        this.mChipPaint.setAlpha(i);
    }

    @Keep
    public void setHeight(float f) {
        this.mHeight = f;
        invalidateSelf();
    }

    public void setListener(PrivacyChipDrawableListener privacyChipDrawableListener) {
        this.mListener = privacyChipDrawableListener;
    }

    @Keep
    public void setMarginEnd(float f) {
        this.mMarginEnd = f;
        invalidateSelf();
    }

    @Keep
    public void setRadius(float f) {
        this.mRadius = f;
        invalidateSelf();
    }

    public void setRtl(boolean z) {
        this.mIsRtl = z;
    }

    @Keep
    public void setWidth(float f) {
        this.mWidth = f;
        invalidateSelf();
    }

    public void startInitialFadeIn() {
        this.mFadeIn.start();
    }

    public void updateIcons(int i) {
        if (i != 0) {
            this.mFadeOut.cancel();
            expand();
            animateToNewTargetWidth(this.mMinWidth + ((i - 1) * (this.mIconWidth + this.mIconPadding)));
            return;
        }
        this.mFadeOut.start();
        this.mWidthAnimator.cancel();
        this.mFadeIn.cancel();
        this.mExpand.cancel();
        this.mCollapse.cancel();
    }
}