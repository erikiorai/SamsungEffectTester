package com.android.systemui.dagger;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvidesFingerprintManagerFactory.class */
public final class FrameworkServicesModule_ProvidesFingerprintManagerFactory implements Factory<FingerprintManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvidesFingerprintManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvidesFingerprintManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvidesFingerprintManagerFactory(provider);
    }

    public static FingerprintManager providesFingerprintManager(Context context) {
        return FrameworkServicesModule.providesFingerprintManager(context);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public FingerprintManager m2367get() {
        return providesFingerprintManager((Context) this.contextProvider.get());
    }
}