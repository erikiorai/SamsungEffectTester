package androidx.core.graphics.drawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import com.android.settingslib.widget.ActionBarShadowController;

/* loaded from: mainsysui33.jar:androidx/core/graphics/drawable/RoundedBitmapDrawable.class */
public abstract class RoundedBitmapDrawable extends Drawable {
    public final Bitmap mBitmap;
    public int mBitmapHeight;
    public final BitmapShader mBitmapShader;
    public int mBitmapWidth;
    public float mCornerRadius;
    public boolean mIsCircular;
    public int mTargetDensity;
    public int mGravity = 119;
    public final Paint mPaint = new Paint(3);
    public final Matrix mShaderMatrix = new Matrix();
    public final Rect mDstRect = new Rect();
    public final RectF mDstRectF = new RectF();
    public boolean mApplyGravity = true;

    public RoundedBitmapDrawable(Resources resources, Bitmap bitmap) {
        this.mTargetDensity = 160;
        if (resources != null) {
            this.mTargetDensity = resources.getDisplayMetrics().densityDpi;
        }
        this.mBitmap = bitmap;
        if (bitmap != null) {
            computeBitmapSize();
            Shader.TileMode tileMode = Shader.TileMode.CLAMP;
            this.mBitmapShader = new BitmapShader(bitmap, tileMode, tileMode);
            return;
        }
        this.mBitmapHeight = -1;
        this.mBitmapWidth = -1;
        this.mBitmapShader = null;
    }

    public static boolean isGreaterThanZero(float f) {
        return f > 0.05f;
    }

    public final void computeBitmapSize() {
        this.mBitmapWidth = this.mBitmap.getScaledWidth(this.mTargetDensity);
        this.mBitmapHeight = this.mBitmap.getScaledHeight(this.mTargetDensity);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Bitmap bitmap = this.mBitmap;
        if (bitmap == null) {
            return;
        }
        updateDstRect();
        if (this.mPaint.getShader() == null) {
            canvas.drawBitmap(bitmap, (Rect) null, this.mDstRect, this.mPaint);
            return;
        }
        RectF rectF = this.mDstRectF;
        float f = this.mCornerRadius;
        canvas.drawRoundRect(rectF, f, f, this.mPaint);
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mPaint.getAlpha();
    }

    @Override // android.graphics.drawable.Drawable
    public ColorFilter getColorFilter() {
        return this.mPaint.getColorFilter();
    }

    public float getCornerRadius() {
        return this.mCornerRadius;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mBitmapHeight;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mBitmapWidth;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        int i = -3;
        if (this.mGravity == 119) {
            if (this.mIsCircular) {
                i = -3;
            } else {
                Bitmap bitmap = this.mBitmap;
                i = -3;
                if (bitmap != null) {
                    i = -3;
                    if (!bitmap.hasAlpha()) {
                        i = -3;
                        if (this.mPaint.getAlpha() >= 255) {
                            i = isGreaterThanZero(this.mCornerRadius) ? -3 : -1;
                        }
                    }
                }
            }
        }
        return i;
    }

    public abstract void gravityCompatApply(int i, int i2, int i3, Rect rect, Rect rect2);

    @Override // android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        if (this.mIsCircular) {
            updateCircularCornerRadius();
        }
        this.mApplyGravity = true;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        if (i != this.mPaint.getAlpha()) {
            this.mPaint.setAlpha(i);
            invalidateSelf();
        }
    }

    public void setAntiAlias(boolean z) {
        this.mPaint.setAntiAlias(z);
        invalidateSelf();
    }

    public void setCircular(boolean z) {
        this.mIsCircular = z;
        this.mApplyGravity = true;
        if (!z) {
            setCornerRadius(ActionBarShadowController.ELEVATION_LOW);
            return;
        }
        updateCircularCornerRadius();
        this.mPaint.setShader(this.mBitmapShader);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    public void setCornerRadius(float f) {
        if (this.mCornerRadius == f) {
            return;
        }
        this.mIsCircular = false;
        if (isGreaterThanZero(f)) {
            this.mPaint.setShader(this.mBitmapShader);
        } else {
            this.mPaint.setShader(null);
        }
        this.mCornerRadius = f;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setDither(boolean z) {
        this.mPaint.setDither(z);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setFilterBitmap(boolean z) {
        this.mPaint.setFilterBitmap(z);
        invalidateSelf();
    }

    public final void updateCircularCornerRadius() {
        this.mCornerRadius = Math.min(this.mBitmapHeight, this.mBitmapWidth) / 2;
    }

    public void updateDstRect() {
        if (this.mApplyGravity) {
            if (this.mIsCircular) {
                int min = Math.min(this.mBitmapWidth, this.mBitmapHeight);
                gravityCompatApply(this.mGravity, min, min, getBounds(), this.mDstRect);
                int min2 = Math.min(this.mDstRect.width(), this.mDstRect.height());
                this.mDstRect.inset(Math.max(0, (this.mDstRect.width() - min2) / 2), Math.max(0, (this.mDstRect.height() - min2) / 2));
                this.mCornerRadius = min2 * 0.5f;
            } else {
                gravityCompatApply(this.mGravity, this.mBitmapWidth, this.mBitmapHeight, getBounds(), this.mDstRect);
            }
            this.mDstRectF.set(this.mDstRect);
            if (this.mBitmapShader != null) {
                Matrix matrix = this.mShaderMatrix;
                RectF rectF = this.mDstRectF;
                matrix.setTranslate(rectF.left, rectF.top);
                this.mShaderMatrix.preScale(this.mDstRectF.width() / this.mBitmap.getWidth(), this.mDstRectF.height() / this.mBitmap.getHeight());
                this.mBitmapShader.setLocalMatrix(this.mShaderMatrix);
                this.mPaint.setShader(this.mBitmapShader);
            }
            this.mApplyGravity = false;
        }
    }
}