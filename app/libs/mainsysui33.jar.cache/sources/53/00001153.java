package com.android.systemui.biometrics;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.android.systemui.R$string;
import java.util.Set;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/AuthBiometricFingerprintAndFaceView.class */
public final class AuthBiometricFingerprintAndFaceView extends AuthBiometricFingerprintView {
    public AuthBiometricFingerprintAndFaceView(Context context) {
        this(context, null);
    }

    public AuthBiometricFingerprintAndFaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.android.systemui.biometrics.AuthBiometricFingerprintView, com.android.systemui.biometrics.AuthBiometricView
    public AuthIconController createIconController() {
        return new AuthBiometricFingerprintAndFaceIconController(((LinearLayout) this).mContext, this.mIconView, this.mIconViewOverlay);
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public boolean forceRequireConfirmation(int i) {
        return i == 8;
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public int getConfirmationPrompt() {
        return R$string.biometric_dialog_tap_confirm_with_face;
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public boolean ignoreUnsuccessfulEventsFrom(int i) {
        return i == 8;
    }

    @Override // com.android.systemui.biometrics.AuthBiometricView
    public boolean onPointerDown(Set<Integer> set) {
        return set.contains(8);
    }
}