package com.android.keyguard;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.android.systemui.R$id;
import com.android.systemui.R$string;
import com.android.systemui.util.PluralMessageFormaterKt;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardSimPukView.class */
public class KeyguardSimPukView extends KeyguardSimInputView {
    public static final boolean DEBUG = KeyguardConstants.DEBUG;

    public KeyguardSimPukView(Context context) {
        this(context, null);
    }

    public KeyguardSimPukView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputView
    public int getPasswordTextViewId() {
        return R$id.pukEntry;
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputView, com.android.keyguard.KeyguardAbsKeyInputView
    public int getPromptReasonStringRes(int i) {
        return 0;
    }

    public String getPukPasswordErrorMessage(int i, boolean z, boolean z2) {
        String string;
        if (i == 0) {
            string = getContext().getString(R$string.kg_password_wrong_puk_code_dead);
        } else if (i > 0) {
            string = PluralMessageFormaterKt.icuMessageFormat(getResources(), z ? R$string.kg_password_default_puk_message : R$string.kg_password_wrong_puk_code, i);
        } else {
            string = getContext().getString(z ? R$string.kg_puk_enter_puk_hint : R$string.kg_password_puk_failed);
        }
        String str = string;
        if (z2) {
            str = getResources().getString(R$string.kg_sim_lock_esim_instructions, string);
        }
        if (DEBUG) {
            Log.d("KeyguardSimPukView", "getPukPasswordErrorMessage: attemptsRemaining=" + i + " displayMessage=" + str);
        }
        return str;
    }

    @Override // com.android.keyguard.KeyguardPinBasedInputView, com.android.keyguard.KeyguardInputView
    public CharSequence getTitle() {
        return getContext().getString(17040542);
    }

    @Override // com.android.keyguard.KeyguardSimInputView, com.android.keyguard.KeyguardPinBasedInputView, com.android.keyguard.KeyguardAbsKeyInputView, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        View view = this.mEcaView;
        if (view instanceof EmergencyCarrierArea) {
            ((EmergencyCarrierArea) view).setCarrierTextVisible(true);
        }
    }

    @Override // com.android.keyguard.KeyguardInputView
    public void startAppearAnimation() {
    }
}