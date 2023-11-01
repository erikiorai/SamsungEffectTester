package com.android.keyguard;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;

/* loaded from: mainsysui33.jar:com/android/keyguard/AuthKeyguardMessageArea.class */
public final class AuthKeyguardMessageArea extends KeyguardMessageArea {
    public AuthKeyguardMessageArea(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.keyguard.KeyguardMessageArea
    public void updateTextColor() {
        setTextColor(ColorStateList.valueOf(-1));
    }
}