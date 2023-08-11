package com.samsung.android.visualeffect.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/* loaded from: classes.dex */
public class ImageViewBlended extends ImageView {
    private Bitmap mBitmap;
    private Paint mPaint;
    private Rect mRect;
    private PorterDuff.Mode mode;

    public ImageViewBlended(Context context) {
        super(context);
        this.mPaint = new Paint();
        this.mode = PorterDuff.Mode.ADD;
    }

    public ImageViewBlended(Context context, PorterDuff.Mode xfermode) {
        super(context);
        this.mPaint = new Paint();
        this.mode = PorterDuff.Mode.ADD;
        this.mode = xfermode;
    }

    public ImageViewBlended(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mPaint = new Paint();
        this.mode = PorterDuff.Mode.ADD;
    }

    public ImageViewBlended(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mPaint = new Paint();
        this.mode = PorterDuff.Mode.ADD;
    }

    @Override // android.widget.ImageView, android.view.View
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(this.mBitmap, (Rect) null, this.mRect, this.mPaint);
    }

    @Override // android.widget.ImageView
    public void setImageResource(int resId) {
        super.setImageResource(resId);
        this.mBitmap = ((BitmapDrawable) getDrawable()).getBitmap();
        if (this.mode != null) {
            this.mPaint.setXfermode(new PorterDuffXfermode(this.mode));
        }
        this.mRect = new Rect(0, 0, this.mBitmap.getWidth(), this.mBitmap.getHeight());
    }

    @Override // android.widget.ImageView
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        this.mBitmap = bm;
        if (this.mode != null) {
            this.mPaint.setXfermode(new PorterDuffXfermode(this.mode));
        }
        this.mRect = new Rect(0, 0, this.mBitmap.getWidth(), this.mBitmap.getHeight());
    }
}