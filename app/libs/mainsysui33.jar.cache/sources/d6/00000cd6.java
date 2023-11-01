package com.android.keyguard.dagger;

import android.hardware.fingerprint.FingerprintManager;
import com.android.systemui.biometrics.SideFpsController;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.util.Optional;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/keyguard/dagger/KeyguardBouncerModule_ProvidesOptionalSidefpsControllerFactory.class */
public final class KeyguardBouncerModule_ProvidesOptionalSidefpsControllerFactory implements Factory<Optional<SideFpsController>> {
    public final Provider<FingerprintManager> fingerprintManagerProvider;
    public final Provider<SideFpsController> sidefpsControllerProvider;

    public KeyguardBouncerModule_ProvidesOptionalSidefpsControllerFactory(Provider<FingerprintManager> provider, Provider<SideFpsController> provider2) {
        this.fingerprintManagerProvider = provider;
        this.sidefpsControllerProvider = provider2;
    }

    public static KeyguardBouncerModule_ProvidesOptionalSidefpsControllerFactory create(Provider<FingerprintManager> provider, Provider<SideFpsController> provider2) {
        return new KeyguardBouncerModule_ProvidesOptionalSidefpsControllerFactory(provider, provider2);
    }

    public static Optional<SideFpsController> providesOptionalSidefpsController(FingerprintManager fingerprintManager, Provider<SideFpsController> provider) {
        return (Optional) Preconditions.checkNotNullFromProvides(KeyguardBouncerModule.providesOptionalSidefpsController(fingerprintManager, provider));
    }

    /* JADX DEBUG: Method merged with bridge method */
    public Optional<SideFpsController> get() {
        return providesOptionalSidefpsController((FingerprintManager) this.fingerprintManagerProvider.get(), this.sidefpsControllerProvider);
    }
}