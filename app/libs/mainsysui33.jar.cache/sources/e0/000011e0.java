package com.android.systemui.biometrics;

import android.content.Context;
import android.util.AttributeSet;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsBpView.class */
public final class UdfpsBpView extends UdfpsAnimationView {
    public final UdfpsFpDrawable fingerprintDrawable;

    public UdfpsBpView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.fingerprintDrawable = new UdfpsFpDrawable(context);
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationView
    public UdfpsDrawable getDrawable() {
        return this.fingerprintDrawable;
    }
}