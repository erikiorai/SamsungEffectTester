package com.android.systemui.biometrics;

import android.content.Context;
import com.airbnb.lottie.LottieAnimationView;
import com.android.systemui.R$raw;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/AuthBiometricFingerprintAndFaceIconController.class */
public final class AuthBiometricFingerprintAndFaceIconController extends AuthBiometricFingerprintIconController {
    public final boolean actsAsConfirmButton;

    public AuthBiometricFingerprintAndFaceIconController(Context context, LottieAnimationView lottieAnimationView, LottieAnimationView lottieAnimationView2) {
        super(context, lottieAnimationView, lottieAnimationView2);
        this.actsAsConfirmButton = true;
    }

    @Override // com.android.systemui.biometrics.AuthIconController
    public boolean getActsAsConfirmButton() {
        return this.actsAsConfirmButton;
    }

    @Override // com.android.systemui.biometrics.AuthBiometricFingerprintIconController
    public Integer getAnimationForTransition(int i, int i2) {
        Integer valueOf;
        if (i2 != 5) {
            valueOf = i2 != 6 ? super.getAnimationForTransition(i, i2) : null;
        } else {
            valueOf = Integer.valueOf((i == 3 || i == 4) ? R$raw.fingerprint_dialogue_error_to_unlock_lottie : R$raw.fingerprint_dialogue_fingerprint_to_unlock_lottie);
        }
        return valueOf;
    }

    @Override // com.android.systemui.biometrics.AuthBiometricFingerprintIconController
    public boolean shouldAnimateForTransition(int i, int i2) {
        return i2 != 5 ? i2 != 6 ? super.shouldAnimateForTransition(i, i2) : false : true;
    }
}