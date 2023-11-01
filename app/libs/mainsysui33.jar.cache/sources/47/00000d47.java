package com.android.launcher3.icons;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.FloatProperty;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import com.android.settingslib.widget.ActionBarShadowController;

/* loaded from: mainsysui33.jar:com/android/launcher3/icons/FastBitmapDrawable.class */
public class FastBitmapDrawable extends Drawable implements Drawable.Callback {
    public static final Interpolator ACCEL = new AccelerateInterpolator();
    public static final Interpolator DEACCEL = new DecelerateInterpolator();
    public static final FloatProperty<FastBitmapDrawable> SCALE = new FloatProperty<FastBitmapDrawable>("scale") { // from class: com.android.launcher3.icons.FastBitmapDrawable.1
        /* JADX DEBUG: Method merged with bridge method */
        @Override // android.util.Property
        public Float get(FastBitmapDrawable fastBitmapDrawable) {
            return Float.valueOf(fastBitmapDrawable.mScale);
        }

        /* JADX DEBUG: Method merged with bridge method */
        @Override // android.util.FloatProperty
        public void setValue(FastBitmapDrawable fastBitmapDrawable, float f) {
            fastBitmapDrawable.mScale = f;
            fastBitmapDrawable.invalidateSelf();
        }
    };
    public int mAlpha;
    public Drawable mBadge;
    public final Bitmap mBitmap;
    public ColorFilter mColorFilter;
    public float mDisabledAlpha;
    public final int mIconColor;
    public boolean mIsDisabled;
    public boolean mIsPressed;
    public final Paint mPaint;
    public float mScale;
    public ObjectAnimator mScaleAnimation;

    /* loaded from: mainsysui33.jar:com/android/launcher3/icons/FastBitmapDrawable$FastBitmapConstantState.class */
    public static class FastBitmapConstantState extends Drawable.ConstantState {
        public Drawable.ConstantState mBadgeConstantState;
        public final Bitmap mBitmap;
        public final int mIconColor;
        public boolean mIsDisabled;

        public FastBitmapConstantState(Bitmap bitmap, int i) {
            this.mBitmap = bitmap;
            this.mIconColor = i;
        }

        public FastBitmapDrawable createDrawable() {
            return new FastBitmapDrawable(this.mBitmap, this.mIconColor);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return 0;
        }

        /* JADX DEBUG: Method merged with bridge method */
        @Override // android.graphics.drawable.Drawable.ConstantState
        public final FastBitmapDrawable newDrawable() {
            FastBitmapDrawable createDrawable = createDrawable();
            createDrawable.setIsDisabled(this.mIsDisabled);
            Drawable.ConstantState constantState = this.mBadgeConstantState;
            if (constantState != null) {
                createDrawable.setBadge(constantState.newDrawable());
            }
            return createDrawable;
        }
    }

    public FastBitmapDrawable(Bitmap bitmap, int i) {
        this.mPaint = new Paint(3);
        this.mDisabledAlpha = 1.0f;
        this.mScale = 1.0f;
        this.mAlpha = 255;
        this.mBitmap = bitmap;
        this.mIconColor = i;
        setFilterBitmap(true);
    }

    public FastBitmapDrawable(BitmapInfo bitmapInfo) {
        this(bitmapInfo.icon, bitmapInfo.color);
    }

    public static final int getDisabledColor(int i) {
        int min = Math.min(Math.round(((((Color.red(i) + Color.green(i)) + Color.blue(i)) / 3) * 0.5f) + 127), 255);
        return Color.rgb(min, min, min);
    }

    public static ColorFilter getDisabledColorFilter() {
        return getDisabledColorFilter(1.0f);
    }

    public static ColorFilter getDisabledColorFilter(float f) {
        ColorMatrix colorMatrix = new ColorMatrix();
        ColorMatrix colorMatrix2 = new ColorMatrix();
        colorMatrix2.setSaturation(ActionBarShadowController.ELEVATION_LOW);
        float[] array = colorMatrix.getArray();
        array[0] = 0.5f;
        array[6] = 0.5f;
        array[12] = 0.5f;
        float f2 = 127;
        array[4] = f2;
        array[9] = f2;
        array[14] = f2;
        array[18] = f;
        colorMatrix2.preConcat(colorMatrix);
        return new ColorMatrixColorFilter(colorMatrix2);
    }

    public static void setBadgeBounds(Drawable drawable, Rect rect) {
        int badgeSizeForIconSize = BaseIconFactory.getBadgeSizeForIconSize(rect.width());
        int i = rect.right;
        int i2 = rect.bottom;
        drawable.setBounds(i - badgeSizeForIconSize, i2 - badgeSizeForIconSize, i, i2);
    }

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        if (this.mScale == 1.0f) {
            drawInternal(canvas, getBounds());
            Drawable drawable = this.mBadge;
            if (drawable != null) {
                drawable.draw(canvas);
                return;
            }
            return;
        }
        int save = canvas.save();
        Rect bounds = getBounds();
        float f = this.mScale;
        canvas.scale(f, f, bounds.exactCenterX(), bounds.exactCenterY());
        drawInternal(canvas, bounds);
        Drawable drawable2 = this.mBadge;
        if (drawable2 != null) {
            drawable2.draw(canvas);
        }
        canvas.restoreToCount(save);
    }

    public void drawInternal(Canvas canvas, Rect rect) {
        canvas.drawBitmap(this.mBitmap, (Rect) null, rect, this.mPaint);
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mAlpha;
    }

    @Override // android.graphics.drawable.Drawable
    public ColorFilter getColorFilter() {
        return this.mPaint.getColorFilter();
    }

    @Override // android.graphics.drawable.Drawable
    public final Drawable.ConstantState getConstantState() {
        FastBitmapConstantState newConstantState = newConstantState();
        newConstantState.mIsDisabled = this.mIsDisabled;
        Drawable drawable = this.mBadge;
        if (drawable != null) {
            newConstantState.mBadgeConstantState = drawable.getConstantState();
        }
        return newConstantState;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mBitmap.getHeight();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mBitmap.getWidth();
    }

    @Override // android.graphics.drawable.Drawable
    public int getMinimumHeight() {
        return getBounds().height();
    }

    @Override // android.graphics.drawable.Drawable
    public int getMinimumWidth() {
        return getBounds().width();
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        if (drawable == this.mBadge) {
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return true;
    }

    public FastBitmapConstantState newConstantState() {
        return new FastBitmapConstantState(this.mBitmap, this.mIconColor);
    }

    @Override // android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        updateBadgeBounds(rect);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean onStateChange(int[] iArr) {
        boolean z;
        int length = iArr.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                z = false;
                break;
            } else if (iArr[i] == 16842919) {
                z = true;
                break;
            } else {
                i++;
            }
        }
        if (this.mIsPressed != z) {
            this.mIsPressed = z;
            ObjectAnimator objectAnimator = this.mScaleAnimation;
            if (objectAnimator != null) {
                objectAnimator.cancel();
                this.mScaleAnimation = null;
            }
            if (this.mIsPressed) {
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, SCALE, 1.1f);
                this.mScaleAnimation = ofFloat;
                ofFloat.setDuration(200L);
                this.mScaleAnimation.setInterpolator(ACCEL);
                this.mScaleAnimation.start();
                return true;
            } else if (!isVisible()) {
                this.mScale = 1.0f;
                invalidateSelf();
                return true;
            } else {
                ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this, SCALE, 1.0f);
                this.mScaleAnimation = ofFloat2;
                ofFloat2.setDuration(200L);
                this.mScaleAnimation.setInterpolator(DEACCEL);
                this.mScaleAnimation.start();
                return true;
            }
        }
        return false;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        if (drawable == this.mBadge) {
            scheduleSelf(runnable, j);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        if (this.mAlpha != i) {
            this.mAlpha = i;
            this.mPaint.setAlpha(i);
            invalidateSelf();
        }
    }

    public void setBadge(Drawable drawable) {
        Drawable drawable2 = this.mBadge;
        if (drawable2 != null) {
            drawable2.setCallback(null);
        }
        this.mBadge = drawable;
        if (drawable != null) {
            drawable.setCallback(this);
        }
        updateBadgeBounds(getBounds());
        updateFilter();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mColorFilter = colorFilter;
        updateFilter();
    }

    @Override // android.graphics.drawable.Drawable
    public void setFilterBitmap(boolean z) {
        this.mPaint.setFilterBitmap(z);
        this.mPaint.setAntiAlias(z);
    }

    public void setIsDisabled(boolean z) {
        if (this.mIsDisabled != z) {
            this.mIsDisabled = z;
            updateFilter();
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        unscheduleSelf(runnable);
    }

    public final void updateBadgeBounds(Rect rect) {
        Drawable drawable = this.mBadge;
        if (drawable != null) {
            setBadgeBounds(drawable, rect);
        }
    }

    public void updateFilter() {
        this.mPaint.setColorFilter(this.mIsDisabled ? getDisabledColorFilter(this.mDisabledAlpha) : this.mColorFilter);
        Drawable drawable = this.mBadge;
        if (drawable != null) {
            drawable.setColorFilter(getColorFilter());
        }
        invalidateSelf();
    }
}