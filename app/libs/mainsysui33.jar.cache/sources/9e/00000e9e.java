package com.android.settingslib.qrcode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import com.android.settingslib.R$color;
import com.android.settingslib.widget.ActionBarShadowController;

/* loaded from: mainsysui33.jar:com/android/settingslib/qrcode/QrDecorateView.class */
public class QrDecorateView extends View {
    public final int mBackgroundColor;
    public final Paint mBackgroundPaint;
    public final int mCornerColor;
    public boolean mFocused;
    public final int mFocusedCornerColor;
    public RectF mInnerFrame;
    public final float mInnerRidus;
    public Bitmap mMaskBitmap;
    public Canvas mMaskCanvas;
    public RectF mOuterFrame;
    public final float mRadius;
    public final Paint mStrokePaint;
    public final Paint mTransparentPaint;

    public QrDecorateView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public QrDecorateView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public QrDecorateView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mFocused = false;
        this.mRadius = TypedValue.applyDimension(1, 16.0f, getResources().getDisplayMetrics());
        this.mInnerRidus = TypedValue.applyDimension(1, 12.0f, getResources().getDisplayMetrics());
        this.mCornerColor = context.getResources().getColor(R$color.qr_corner_line_color);
        this.mFocusedCornerColor = context.getResources().getColor(R$color.qr_focused_corner_line_color);
        int color = context.getResources().getColor(R$color.qr_background_color);
        this.mBackgroundColor = color;
        Paint paint = new Paint();
        this.mStrokePaint = paint;
        paint.setAntiAlias(true);
        Paint paint2 = new Paint();
        this.mTransparentPaint = paint2;
        paint2.setAntiAlias(true);
        paint2.setColor(getResources().getColor(17170445));
        paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        Paint paint3 = new Paint();
        this.mBackgroundPaint = paint3;
        paint3.setColor(color);
    }

    public final void calculateFramePos() {
        int width = getWidth() / 2;
        int height = getHeight() / 2;
        float applyDimension = TypedValue.applyDimension(1, 264.0f, getResources().getDisplayMetrics()) / 2.0f;
        float applyDimension2 = TypedValue.applyDimension(1, 4.0f, getResources().getDisplayMetrics());
        float f = width;
        float f2 = height;
        this.mOuterFrame = new RectF(f - applyDimension, f2 - applyDimension, f + applyDimension, f2 + applyDimension);
        RectF rectF = this.mOuterFrame;
        this.mInnerFrame = new RectF(rectF.left + applyDimension2, rectF.top + applyDimension2, rectF.right - applyDimension2, rectF.bottom - applyDimension2);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        this.mStrokePaint.setColor(this.mFocused ? this.mFocusedCornerColor : this.mCornerColor);
        this.mMaskCanvas.drawColor(this.mBackgroundColor);
        Canvas canvas2 = this.mMaskCanvas;
        RectF rectF = this.mOuterFrame;
        float f = this.mRadius;
        canvas2.drawRoundRect(rectF, f, f, this.mStrokePaint);
        Canvas canvas3 = this.mMaskCanvas;
        RectF rectF2 = this.mInnerFrame;
        float f2 = this.mInnerRidus;
        canvas3.drawRoundRect(rectF2, f2, f2, this.mTransparentPaint);
        canvas.drawBitmap(this.mMaskBitmap, ActionBarShadowController.ELEVATION_LOW, ActionBarShadowController.ELEVATION_LOW, this.mBackgroundPaint);
        super.onDraw(canvas);
    }

    @Override // android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.mMaskBitmap == null) {
            this.mMaskBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            this.mMaskCanvas = new Canvas(this.mMaskBitmap);
        }
        calculateFramePos();
    }
}