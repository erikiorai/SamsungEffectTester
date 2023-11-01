package com.android.systemui.biometrics;

import android.content.Context;
import android.graphics.Canvas;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsFpDrawable.class */
public final class UdfpsFpDrawable extends UdfpsDrawable {
    public UdfpsFpDrawable(Context context) {
        super(context);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (isDisplayConfigured()) {
            return;
        }
        getFingerprintDrawable().draw(canvas);
    }
}