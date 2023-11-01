package com.android.systemui.controls.ui;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/CornerDrawable.class */
public final class CornerDrawable extends DrawableWrapper {
    public final float cornerRadius;
    public final Path path;
    public final Drawable wrapped;

    public CornerDrawable(Drawable drawable, float f) {
        super(drawable);
        this.wrapped = drawable;
        this.cornerRadius = f;
        this.path = new Path();
        updatePath(new RectF(getBounds()));
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        canvas.clipPath(this.path);
        super.draw(canvas);
    }

    @Override // android.graphics.drawable.Drawable
    public void setBounds(int i, int i2, int i3, int i4) {
        updatePath(new RectF(i, i2, i3, i4));
        super.setBounds(i, i2, i3, i4);
    }

    @Override // android.graphics.drawable.Drawable
    public void setBounds(Rect rect) {
        updatePath(new RectF(rect));
        super.setBounds(rect);
    }

    public final void updatePath(RectF rectF) {
        this.path.reset();
        Path path = this.path;
        float f = this.cornerRadius;
        path.addRoundRect(rectF, f, f, Path.Direction.CW);
    }
}