package com.android.systemui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import com.android.settingslib.Utils;

/* loaded from: mainsysui33.jar:com/android/systemui/HardwareBgDrawable.class */
public class HardwareBgDrawable extends LayerDrawable {
    public final Drawable[] mLayers;
    public final Paint mPaint;
    public int mPoint;
    public boolean mRotatedBackground;
    public final boolean mRoundTop;

    public HardwareBgDrawable(boolean z, boolean z2, Context context) {
        this(z, getLayers(context, z, z2));
    }

    public HardwareBgDrawable(boolean z, Drawable[] drawableArr) {
        super(drawableArr);
        this.mPaint = new Paint();
        if (drawableArr.length != 2) {
            throw new IllegalArgumentException("Need 2 layers");
        }
        this.mRoundTop = z;
        this.mLayers = drawableArr;
    }

    public static Drawable[] getLayers(Context context, boolean z, boolean z2) {
        Drawable[] drawableArr;
        int i = z2 ? R$drawable.rounded_bg_full : R$drawable.rounded_bg;
        if (z) {
            drawableArr = new Drawable[]{context.getDrawable(i).mutate(), context.getDrawable(i).mutate()};
        } else {
            drawableArr = new Drawable[2];
            drawableArr[0] = context.getDrawable(i).mutate();
            drawableArr[1] = context.getDrawable(z2 ? R$drawable.rounded_full_bg_bottom : R$drawable.rounded_bg_bottom).mutate();
        }
        drawableArr[1].setTintList(Utils.getColorAttr(context, 16843827));
        return drawableArr;
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (this.mPoint < 0 || this.mRotatedBackground) {
            this.mLayers[0].draw(canvas);
            return;
        }
        Rect bounds = getBounds();
        int i = bounds.top;
        int i2 = this.mPoint + i;
        int i3 = bounds.bottom;
        int i4 = i2;
        if (i2 > i3) {
            i4 = i3;
        }
        if (this.mRoundTop) {
            this.mLayers[0].setBounds(bounds.left, i, bounds.right, i4);
        } else {
            this.mLayers[1].setBounds(bounds.left, i4, bounds.right, i3);
        }
        if (this.mRoundTop) {
            this.mLayers[1].draw(canvas);
            this.mLayers[0].draw(canvas);
            return;
        }
        this.mLayers[0].draw(canvas);
        this.mLayers[1].draw(canvas);
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public int getOpacity() {
        return -1;
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mPaint.setAlpha(i);
    }

    @Override // android.graphics.drawable.LayerDrawable, android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }
}