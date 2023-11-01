package com.android.settingslib.drawable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import com.android.settingslib.widget.ActionBarShadowController;

/* loaded from: mainsysui33.jar:com/android/settingslib/drawable/CircleFramedDrawable.class */
public class CircleFramedDrawable extends Drawable {
    public final Bitmap mBitmap;
    public RectF mDstRect;
    public Paint mIconPaint;
    public float mScale;
    public final int mSize;
    public Rect mSrcRect;

    public CircleFramedDrawable(Bitmap bitmap, int i) {
        this.mSize = i;
        Bitmap createBitmap = Bitmap.createBitmap(i, i, Bitmap.Config.ARGB_8888);
        this.mBitmap = createBitmap;
        Canvas canvas = new Canvas(createBitmap);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int min = Math.min(width, height);
        Rect rect = new Rect((width - min) / 2, (height - min) / 2, min, min);
        RectF rectF = new RectF(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, i, i);
        Path path = new Path();
        path.addArc(rectF, ActionBarShadowController.ELEVATION_LOW, 360.0f);
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(-16777216);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rectF, paint);
        paint.setXfermode(null);
        this.mScale = 1.0f;
        this.mSrcRect = new Rect(0, 0, i, i);
        this.mDstRect = new RectF(ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, i, i);
    }

    public static CircleFramedDrawable getInstance(Context context, Bitmap bitmap) {
        return new CircleFramedDrawable(bitmap, context.getResources().getDimensionPixelSize(17105625));
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        float f = this.mScale;
        int i = this.mSize;
        float f2 = (i - (f * i)) / 2.0f;
        this.mDstRect.set(f2, f2, i - f2, i - f2);
        canvas.drawBitmap(this.mBitmap, this.mSrcRect, this.mDstRect, this.mIconPaint);
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mSize;
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mSize;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        if (this.mIconPaint == null) {
            this.mIconPaint = new Paint();
        }
        this.mIconPaint.setColorFilter(colorFilter);
    }
}