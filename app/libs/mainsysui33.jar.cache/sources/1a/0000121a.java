package com.android.systemui.biometrics;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import com.android.systemui.R$id;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/UdfpsFpmOtherView.class */
public final class UdfpsFpmOtherView extends UdfpsAnimationView {
    public final UdfpsFpDrawable fingerprintDrawable;
    public ImageView fingerprintView;

    public UdfpsFpmOtherView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.fingerprintDrawable = new UdfpsFpDrawable(context);
    }

    @Override // com.android.systemui.biometrics.UdfpsAnimationView
    public UdfpsDrawable getDrawable() {
        return this.fingerprintDrawable;
    }

    @Override // android.view.View
    public void onFinishInflate() {
        View findViewById = findViewById(R$id.udfps_fpm_other_fp_view);
        Intrinsics.checkNotNull(findViewById);
        ImageView imageView = (ImageView) findViewById;
        this.fingerprintView = imageView;
        ImageView imageView2 = imageView;
        if (imageView == null) {
            imageView2 = null;
        }
        imageView2.setImageDrawable(this.fingerprintDrawable);
    }
}