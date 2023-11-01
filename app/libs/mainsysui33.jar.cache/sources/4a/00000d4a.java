package com.android.launcher3.icons;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.DrawableWrapper;
import android.util.AttributeSet;
import com.android.settingslib.widget.ActionBarShadowController;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: mainsysui33.jar:com/android/launcher3/icons/FixedScaleDrawable.class */
public class FixedScaleDrawable extends DrawableWrapper {
    public float mScaleX;
    public float mScaleY;

    public FixedScaleDrawable() {
        super(new ColorDrawable());
        this.mScaleX = 0.46669f;
        this.mScaleY = 0.46669f;
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        int save = canvas.save();
        canvas.scale(this.mScaleX, this.mScaleY, getBounds().exactCenterX(), getBounds().exactCenterY());
        super.draw(canvas);
        canvas.restoreToCount(save);
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) {
    }

    @Override // android.graphics.drawable.DrawableWrapper, android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
    }

    public void setScale(float f) {
        float intrinsicHeight = getIntrinsicHeight();
        float intrinsicWidth = getIntrinsicWidth();
        float f2 = f * 0.46669f;
        this.mScaleX = f2;
        this.mScaleY = f2;
        if (intrinsicHeight > intrinsicWidth && intrinsicWidth > ActionBarShadowController.ELEVATION_LOW) {
            this.mScaleX = f2 * (intrinsicWidth / intrinsicHeight);
        } else if (intrinsicWidth <= intrinsicHeight || intrinsicHeight <= ActionBarShadowController.ELEVATION_LOW) {
        } else {
            this.mScaleY = f2 * (intrinsicHeight / intrinsicWidth);
        }
    }
}