package com.android.systemui.dagger;

import android.content.Context;
import android.hardware.biometrics.BiometricManager;
import android.hardware.face.FaceManager;
import android.hardware.fingerprint.FingerprintManager;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvidesBiometricManagerFactory.class */
public final class FrameworkServicesModule_ProvidesBiometricManagerFactory implements Factory<BiometricManager> {
    public final Provider<Context> contextProvider;
    public final Provider<FaceManager> faceManagerProvider;
    public final Provider<FingerprintManager> fingerprintManagerProvider;

    public FrameworkServicesModule_ProvidesBiometricManagerFactory(Provider<Context> provider, Provider<FaceManager> provider2, Provider<FingerprintManager> provider3) {
        this.contextProvider = provider;
        this.faceManagerProvider = provider2;
        this.fingerprintManagerProvider = provider3;
    }

    public static FrameworkServicesModule_ProvidesBiometricManagerFactory create(Provider<Context> provider, Provider<FaceManager> provider2, Provider<FingerprintManager> provider3) {
        return new FrameworkServicesModule_ProvidesBiometricManagerFactory(provider, provider2, provider3);
    }

    public static BiometricManager providesBiometricManager(Context context, FaceManager faceManager, FingerprintManager fingerprintManager) {
        return FrameworkServicesModule.providesBiometricManager(context, faceManager, fingerprintManager);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public BiometricManager m2365get() {
        return providesBiometricManager((Context) this.contextProvider.get(), (FaceManager) this.faceManagerProvider.get(), (FingerprintManager) this.fingerprintManagerProvider.get());
    }
}