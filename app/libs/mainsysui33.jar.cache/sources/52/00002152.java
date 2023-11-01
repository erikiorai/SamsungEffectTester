package com.android.systemui.qs;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.FloatProperty;
import com.android.settingslib.widget.ActionBarShadowController;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/SlashDrawable.class */
public class SlashDrawable extends Drawable {
    public float mCurrentSlashLength;
    public Drawable mDrawable;
    public float mRotation;
    public boolean mSlashed;
    public ColorStateList mTintList;
    public PorterDuff.Mode mTintMode;
    public final Path mPath = new Path();
    public final Paint mPaint = new Paint(1);
    public final RectF mSlashRect = new RectF(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW);
    public boolean mAnimationEnabled = true;
    public final FloatProperty mSlashLengthProp = new FloatProperty<SlashDrawable>("slashLength") { // from class: com.android.systemui.qs.SlashDrawable.1
        /* JADX DEBUG: Method merged with bridge method */
        @Override // android.util.Property
        public Float get(SlashDrawable slashDrawable) {
            return Float.valueOf(slashDrawable.mCurrentSlashLength);
        }

        /* JADX DEBUG: Method merged with bridge method */
        @Override // android.util.FloatProperty
        public void setValue(SlashDrawable slashDrawable, float f) {
            slashDrawable.mCurrentSlashLength = f;
        }
    };

    public SlashDrawable(Drawable drawable) {
        this.mDrawable = drawable;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setSlashed$0(ValueAnimator valueAnimator) {
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        canvas.save();
        Matrix matrix = new Matrix();
        int width = getBounds().width();
        int height = getBounds().height();
        float scale = scale(1.0f, width);
        float scale2 = scale(1.0f, height);
        updateRect(scale(0.40544835f, width), scale(-0.088781714f, height), scale(0.4820516f, width), scale(this.mCurrentSlashLength - 0.088781714f, height));
        this.mPath.reset();
        this.mPath.addRoundRect(this.mSlashRect, scale, scale2, Path.Direction.CW);
        float f = width / 2;
        float f2 = height / 2;
        matrix.setRotate(this.mRotation - 45.0f, f, f2);
        this.mPath.transform(matrix);
        canvas.drawPath(this.mPath, this.mPaint);
        matrix.setRotate((-this.mRotation) + 45.0f, f, f2);
        this.mPath.transform(matrix);
        matrix.setTranslate(this.mSlashRect.width(), ActionBarShadowController.ELEVATION_LOW);
        this.mPath.transform(matrix);
        this.mPath.addRoundRect(this.mSlashRect, width * 1.0f, height * 1.0f, Path.Direction.CW);
        matrix.setRotate(this.mRotation - 45.0f, f, f2);
        this.mPath.transform(matrix);
        canvas.clipOutPath(this.mPath);
        this.mDrawable.draw(canvas);
        canvas.restore();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        Drawable drawable = this.mDrawable;
        return drawable != null ? drawable.getIntrinsicHeight() : 0;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        Drawable drawable = this.mDrawable;
        return drawable != null ? drawable.getIntrinsicWidth() : 0;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return 255;
    }

    @Override // android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.mDrawable.setBounds(rect);
    }

    public final float scale(float f, int i) {
        return f * i;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mDrawable.setAlpha(i);
        this.mPaint.setAlpha(i);
    }

    public void setAnimationEnabled(boolean z) {
        this.mAnimationEnabled = z;
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mDrawable.setColorFilter(colorFilter);
        this.mPaint.setColorFilter(colorFilter);
    }

    public void setDrawable(Drawable drawable) {
        this.mDrawable = drawable;
        drawable.setCallback(getCallback());
        this.mDrawable.setBounds(getBounds());
        PorterDuff.Mode mode = this.mTintMode;
        if (mode != null) {
            this.mDrawable.setTintMode(mode);
        }
        ColorStateList colorStateList = this.mTintList;
        if (colorStateList != null) {
            this.mDrawable.setTintList(colorStateList);
        }
        invalidateSelf();
    }

    public void setDrawableTintList(ColorStateList colorStateList) {
        this.mDrawable.setTintList(colorStateList);
    }

    public void setRotation(float f) {
        if (this.mRotation == f) {
            return;
        }
        this.mRotation = f;
        invalidateSelf();
    }

    public void setSlashed(boolean z) {
        if (this.mSlashed == z) {
            return;
        }
        this.mSlashed = z;
        float f = 1.1666666f;
        float f2 = z ? 1.1666666f : 0.0f;
        if (z) {
            f = 0.0f;
        }
        if (!this.mAnimationEnabled) {
            this.mCurrentSlashLength = f2;
            invalidateSelf();
            return;
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, this.mSlashLengthProp, f, f2);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.qs.SlashDrawable$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                SlashDrawable.this.lambda$setSlashed$0(valueAnimator);
            }
        });
        ofFloat.setDuration(350L);
        ofFloat.start();
    }

    @Override // android.graphics.drawable.Drawable
    public void setTint(int i) {
        super.setTint(i);
        this.mDrawable.setTint(i);
        this.mPaint.setColor(i);
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(ColorStateList colorStateList) {
        this.mTintList = colorStateList;
        super.setTintList(colorStateList);
        setDrawableTintList(colorStateList);
        this.mPaint.setColor(colorStateList.getDefaultColor());
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintMode(PorterDuff.Mode mode) {
        this.mTintMode = mode;
        super.setTintMode(mode);
        this.mDrawable.setTintMode(mode);
    }

    public final void updateRect(float f, float f2, float f3, float f4) {
        RectF rectF = this.mSlashRect;
        rectF.left = f;
        rectF.top = f2;
        rectF.right = f3;
        rectF.bottom = f4;
    }
}