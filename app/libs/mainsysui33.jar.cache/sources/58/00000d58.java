package com.android.launcher3.icons;

import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import com.android.launcher3.icons.ShadowGenerator;
import com.android.settingslib.widget.ActionBarShadowController;

/* loaded from: mainsysui33.jar:com/android/launcher3/icons/ShadowGenerator.class */
public class ShadowGenerator {
    public final BlurMaskFilter mDefaultBlurMaskFilter;
    public final int mIconSize;
    public final Paint mBlurPaint = new Paint(3);
    public final Paint mDrawPaint = new Paint(3);

    /* loaded from: mainsysui33.jar:com/android/launcher3/icons/ShadowGenerator$Builder.class */
    public static class Builder {
        public final int color;
        public float keyShadowDistance;
        public float radius;
        public float shadowBlur;
        public final RectF bounds = new RectF();
        public int ambientShadowAlpha = 25;
        public int keyShadowAlpha = 7;

        public Builder(int i) {
            this.color = i;
        }

        public Bitmap createPill(int i, int i2) {
            return createPill(i, i2, i2 / 2.0f);
        }

        public Bitmap createPill(int i, int i2, float f) {
            this.radius = f;
            float f2 = i;
            float f3 = f2 / 2.0f;
            int max = Math.max(Math.round(this.shadowBlur + f3), Math.round(this.radius + this.shadowBlur + this.keyShadowDistance));
            float f4 = i2;
            this.bounds.set(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, f2, f4);
            float f5 = max;
            this.bounds.offsetTo(f5 - f3, f5 - (f4 / 2.0f));
            int i3 = max * 2;
            return BitmapRenderer.createHardwareBitmap(i3, i3, new BitmapRenderer() { // from class: com.android.launcher3.icons.ShadowGenerator$Builder$$ExternalSyntheticLambda0
                @Override // com.android.launcher3.icons.BitmapRenderer
                public final void draw(Canvas canvas) {
                    ShadowGenerator.Builder.this.drawShadow(canvas);
                }
            });
        }

        public void drawShadow(Canvas canvas) {
            Paint paint = new Paint(3);
            paint.setColor(this.color);
            paint.setShadowLayer(this.shadowBlur, ActionBarShadowController.ELEVATION_LOW, this.keyShadowDistance, GraphicsUtils.setColorAlphaBound(-16777216, this.keyShadowAlpha));
            RectF rectF = this.bounds;
            float f = this.radius;
            canvas.drawRoundRect(rectF, f, f, paint);
            paint.setShadowLayer(this.shadowBlur, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, GraphicsUtils.setColorAlphaBound(-16777216, this.ambientShadowAlpha));
            RectF rectF2 = this.bounds;
            float f2 = this.radius;
            canvas.drawRoundRect(rectF2, f2, f2, paint);
            if (Color.alpha(this.color) < 255) {
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                paint.clearShadowLayer();
                paint.setColor(-16777216);
                RectF rectF3 = this.bounds;
                float f3 = this.radius;
                canvas.drawRoundRect(rectF3, f3, f3, paint);
                paint.setXfermode(null);
                paint.setColor(this.color);
                RectF rectF4 = this.bounds;
                float f4 = this.radius;
                canvas.drawRoundRect(rectF4, f4, f4, paint);
            }
        }

        public Builder setupBlurForSize(int i) {
            float f = i * 1.0f;
            this.shadowBlur = f / 24.0f;
            this.keyShadowDistance = f / 16.0f;
            return this;
        }
    }

    public ShadowGenerator(int i) {
        this.mIconSize = i;
        this.mDefaultBlurMaskFilter = new BlurMaskFilter(i * 0.035f, BlurMaskFilter.Blur.NORMAL);
    }

    public static float getScaleForBounds(RectF rectF) {
        float min = Math.min(Math.min(rectF.left, rectF.right), rectF.top);
        float f = min < 0.035f ? 0.465f / (0.5f - min) : 1.0f;
        float f2 = rectF.bottom;
        float f3 = f;
        if (f2 < 0.055833332f) {
            f3 = Math.min(f, 0.44416666f / (0.5f - f2));
        }
        return f3;
    }

    public void addPathShadow(Path path, Canvas canvas) {
        this.mDrawPaint.setMaskFilter(this.mDefaultBlurMaskFilter);
        this.mDrawPaint.setAlpha(25);
        canvas.drawPath(path, this.mDrawPaint);
        int save = canvas.save();
        this.mDrawPaint.setAlpha(7);
        canvas.translate(ActionBarShadowController.ELEVATION_LOW, this.mIconSize * 0.020833334f);
        canvas.drawPath(path, this.mDrawPaint);
        canvas.restoreToCount(save);
        this.mDrawPaint.setMaskFilter(null);
    }

    public void drawShadow(Bitmap bitmap, Canvas canvas) {
        synchronized (this) {
            int[] iArr = new int[2];
            this.mBlurPaint.setMaskFilter(this.mDefaultBlurMaskFilter);
            Bitmap extractAlpha = bitmap.extractAlpha(this.mBlurPaint, iArr);
            this.mDrawPaint.setAlpha(25);
            canvas.drawBitmap(extractAlpha, iArr[0], iArr[1], this.mDrawPaint);
            this.mDrawPaint.setAlpha(7);
            canvas.drawBitmap(extractAlpha, iArr[0], iArr[1] + (this.mIconSize * 0.020833334f), this.mDrawPaint);
        }
    }
}