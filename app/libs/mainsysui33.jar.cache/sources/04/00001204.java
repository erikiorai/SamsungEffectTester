package com.android.systemui.biometrics;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import kotlin.jvm.functions.Function1;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsDrawable.class */
public abstract class UdfpsDrawable extends Drawable {
    public int _alpha;
    public final Context context;
    public final ShapeDrawable fingerprintDrawable;
    public boolean isDisplayConfigured;
    public float strokeWidth;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public UdfpsDrawable(Context context) {
        this(context, r2);
        Function1 function1;
        function1 = UdfpsDrawableKt.defaultFactory;
    }

    public UdfpsDrawable(Context context, Function1<? super Context, ? extends ShapeDrawable> function1) {
        this.context = context;
        ShapeDrawable shapeDrawable = (ShapeDrawable) function1.invoke(context);
        this.fingerprintDrawable = shapeDrawable;
        this._alpha = 255;
        this.strokeWidth = shapeDrawable.getPaint().getStrokeWidth();
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this._alpha;
    }

    public final ShapeDrawable getFingerprintDrawable() {
        return this.fingerprintDrawable;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return 0;
    }

    public final boolean isDisplayConfigured() {
        return this.isDisplayConfigured;
    }

    public void onSensorRectUpdated(RectF rectF) {
        int height = ((int) rectF.height()) / 8;
        updateFingerprintIconBounds(new Rect(((int) rectF.left) + height, ((int) rectF.top) + height, ((int) rectF.right) - height, ((int) rectF.bottom) - height));
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this._alpha = i;
        this.fingerprintDrawable.setAlpha(i);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }

    public final void setDisplayConfigured(boolean z) {
        if (this.isDisplayConfigured == z) {
            return;
        }
        this.isDisplayConfigured = z;
        invalidateSelf();
    }

    public void updateFingerprintIconBounds(Rect rect) {
        this.fingerprintDrawable.setBounds(rect);
        invalidateSelf();
    }
}