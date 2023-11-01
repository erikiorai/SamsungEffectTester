package com.android.systemui.dreams;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.android.systemui.R$styleable;
import com.android.systemui.statusbar.AlphaOptimizedImageView;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/DreamOverlayDotImageView.class */
public class DreamOverlayDotImageView extends AlphaOptimizedImageView {
    public final int mDotColor;

    /* loaded from: mainsysui33.jar:com/android/systemui/dreams/DreamOverlayDotImageView$DotDrawable.class */
    public static class DotDrawable extends Drawable {
        public Bitmap mDotBitmap;
        public final int mDotColor;
        public final Paint mPaint = new Paint(1);
        public final Rect mBounds = new Rect();

        public DotDrawable(int i) {
            this.mDotColor = i;
        }

        public final Bitmap createBitmap(int i, int i2) {
            Bitmap createBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            Paint paint = new Paint(1);
            paint.setColor(this.mDotColor);
            canvas.drawCircle(i / 2.0f, i2 / 2.0f, Math.min(i, i2) / 2.0f, paint);
            return createBitmap;
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            if (this.mBounds.isEmpty()) {
                return;
            }
            if (this.mDotBitmap == null) {
                this.mDotBitmap = createBitmap(this.mBounds.width(), this.mBounds.height());
            }
            canvas.drawBitmap(this.mDotBitmap, (Rect) null, this.mBounds, this.mPaint);
        }

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            return 0;
        }

        @Override // android.graphics.drawable.Drawable
        public void onBoundsChange(Rect rect) {
            super.onBoundsChange(rect);
            this.mBounds.set(rect.left, rect.top, rect.right, rect.bottom);
            this.mDotBitmap = null;
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int i) {
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(ColorFilter colorFilter) {
        }
    }

    public DreamOverlayDotImageView(Context context) {
        this(context, null);
    }

    public DreamOverlayDotImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DreamOverlayDotImageView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public DreamOverlayDotImageView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R$styleable.DreamOverlayDotImageView, 0, 0);
        try {
            this.mDotColor = obtainStyledAttributes.getColor(R$styleable.DreamOverlayDotImageView_dotColor, -1);
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r5v0, resolved type: com.android.systemui.dreams.DreamOverlayDotImageView */
    /* JADX WARN: Multi-variable type inference failed */
    public void onFinishInflate() {
        super/*android.widget.ImageView*/.onFinishInflate();
        setImageDrawable(new DotDrawable(this.mDotColor));
    }
}