package com.android.keyguard;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import androidx.core.graphics.drawable.DrawableCompat;
import com.android.systemui.R$id;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSimInputView.class */
public abstract class KeyguardSimInputView extends KeyguardPinBasedInputView {
    public KeyguardEsimArea disableESimButton;
    public ImageView simImageView;

    public KeyguardSimInputView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputView, com.android.keyguard.KeyguardAbsKeyInputView, android.view.View
    public void onFinishInflate() {
        this.simImageView = (ImageView) findViewById(R$id.keyguard_sim);
        this.disableESimButton = (KeyguardEsimArea) findViewById(R$id.keyguard_esim_area);
        super.onFinishInflate();
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputView
    public void reloadColors() {
        super.reloadColors();
        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(new int[]{16842808});
        int color = obtainStyledAttributes.getColor(0, 0);
        obtainStyledAttributes.recycle();
        ImageView imageView = this.simImageView;
        if (imageView != null) {
            DrawableCompat.setTint(DrawableCompat.wrap(imageView.getDrawable()), color);
        }
    }

    public final void setESimLocked(boolean z, int i) {
        KeyguardEsimArea keyguardEsimArea = this.disableESimButton;
        if (keyguardEsimArea != null) {
            keyguardEsimArea.setSubscriptionId(i);
        }
        KeyguardEsimArea keyguardEsimArea2 = this.disableESimButton;
        if (keyguardEsimArea2 != null) {
            keyguardEsimArea2.setVisibility(z ? 0 : 8);
        }
        ImageView imageView = this.simImageView;
        if (imageView == null) {
            return;
        }
        int i2 = 0;
        if (z) {
            i2 = 8;
        }
        imageView.setVisibility(i2);
    }
}