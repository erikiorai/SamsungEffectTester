package com.android.systemui.biometrics;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.airbnb.lottie.LottieAnimationView;
import com.android.systemui.R$dimen;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/AuthBiometricFaceIconController.class */
public final class AuthBiometricFaceIconController extends AuthIconController {
    public boolean lastPulseLightToDark;
    public int state;

    public AuthBiometricFaceIconController(Context context, LottieAnimationView lottieAnimationView) {
        super(context, lottieAnimationView);
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R$dimen.biometric_dialog_face_icon_size);
        lottieAnimationView.getLayoutParams().width = dimensionPixelSize;
        lottieAnimationView.getLayoutParams().height = dimensionPixelSize;
        showStaticDrawable(R$drawable.face_dialog_pulse_dark_to_light);
    }

    @Override // com.android.systemui.biometrics.AuthIconController
    public void handleAnimationEnd(Drawable drawable) {
        int i = this.state;
        if (i == 2 || i == 3) {
            pulseInNextDirection();
        }
    }

    public final void pulseInNextDirection() {
        animateIcon(this.lastPulseLightToDark ? R$drawable.face_dialog_pulse_dark_to_light : R$drawable.face_dialog_pulse_light_to_dark, true);
        this.lastPulseLightToDark = !this.lastPulseLightToDark;
    }

    public final void startPulsing() {
        this.lastPulseLightToDark = false;
        animateIcon(R$drawable.face_dialog_pulse_dark_to_light, true);
    }

    @Override // com.android.systemui.biometrics.AuthIconController
    public void updateIcon(int i, int i2) {
        boolean z = i == 4 || i == 3;
        if (i2 == 1) {
            showStaticDrawable(R$drawable.face_dialog_pulse_dark_to_light);
            getIconView().setContentDescription(getContext().getString(R$string.biometric_dialog_face_icon_description_authenticating));
        } else if (i2 == 2) {
            startPulsing();
            getIconView().setContentDescription(getContext().getString(R$string.biometric_dialog_face_icon_description_authenticating));
        } else if (i == 5 && i2 == 6) {
            animateIconOnce(R$drawable.face_dialog_dark_to_checkmark);
            getIconView().setContentDescription(getContext().getString(R$string.biometric_dialog_face_icon_description_confirmed));
        } else if (z && i2 == 0) {
            animateIconOnce(R$drawable.face_dialog_error_to_idle);
            getIconView().setContentDescription(getContext().getString(R$string.biometric_dialog_face_icon_description_idle));
        } else if (z && i2 == 6) {
            animateIconOnce(R$drawable.face_dialog_dark_to_checkmark);
            getIconView().setContentDescription(getContext().getString(R$string.biometric_dialog_face_icon_description_authenticated));
        } else if (i2 == 4 && i != 4) {
            animateIconOnce(R$drawable.face_dialog_dark_to_error);
        } else if (i == 2 && i2 == 6) {
            animateIconOnce(R$drawable.face_dialog_dark_to_checkmark);
            getIconView().setContentDescription(getContext().getString(R$string.biometric_dialog_face_icon_description_authenticated));
        } else if (i2 == 5) {
            animateIconOnce(R$drawable.face_dialog_wink_from_dark);
            getIconView().setContentDescription(getContext().getString(R$string.biometric_dialog_face_icon_description_authenticated));
        } else if (i2 == 0) {
            showStaticDrawable(R$drawable.face_dialog_idle_static);
            getIconView().setContentDescription(getContext().getString(R$string.biometric_dialog_face_icon_description_idle));
        } else {
            Log.w("AuthBiometricFaceIconController", "Unhandled state: " + i2);
        }
        this.state = i2;
    }
}