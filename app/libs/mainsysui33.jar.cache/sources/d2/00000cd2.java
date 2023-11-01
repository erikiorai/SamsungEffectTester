package com.android.keyguard.dagger;

import android.hardware.fingerprint.FingerprintManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.android.keyguard.KeyguardHostView;
import com.android.keyguard.KeyguardSecurityContainer;
import com.android.keyguard.KeyguardSecurityViewFlipper;
import com.android.systemui.R$id;
import com.android.systemui.R$layout;
import com.android.systemui.biometrics.SideFpsController;
import com.android.systemui.biometrics.SideFpsControllerKt;
import java.util.Optional;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/keyguard/dagger/KeyguardBouncerModule.class */
public interface KeyguardBouncerModule {
    static KeyguardHostView providesKeyguardHostView(ViewGroup viewGroup, LayoutInflater layoutInflater) {
        KeyguardHostView keyguardHostView = (KeyguardHostView) layoutInflater.inflate(R$layout.keyguard_host_view, viewGroup, false);
        viewGroup.addView(keyguardHostView);
        return keyguardHostView;
    }

    static KeyguardSecurityContainer providesKeyguardSecurityContainer(KeyguardHostView keyguardHostView) {
        return (KeyguardSecurityContainer) keyguardHostView.findViewById(R$id.keyguard_security_container);
    }

    static KeyguardSecurityViewFlipper providesKeyguardSecurityViewFlipper(KeyguardSecurityContainer keyguardSecurityContainer) {
        return (KeyguardSecurityViewFlipper) keyguardSecurityContainer.findViewById(R$id.view_flipper);
    }

    static Optional<SideFpsController> providesOptionalSidefpsController(FingerprintManager fingerprintManager, Provider<SideFpsController> provider) {
        return !SideFpsControllerKt.hasSideFpsSensor(fingerprintManager) ? Optional.empty() : Optional.of((SideFpsController) provider.get());
    }
}