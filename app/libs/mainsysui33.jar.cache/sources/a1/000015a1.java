package com.android.systemui.dagger;

import android.content.Context;
import android.hardware.SensorPrivacyManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideSensorPrivacyManagerFactory.class */
public final class FrameworkServicesModule_ProvideSensorPrivacyManagerFactory implements Factory<SensorPrivacyManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideSensorPrivacyManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideSensorPrivacyManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideSensorPrivacyManagerFactory(provider);
    }

    public static SensorPrivacyManager provideSensorPrivacyManager(Context context) {
        return (SensorPrivacyManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideSensorPrivacyManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public SensorPrivacyManager m2347get() {
        return provideSensorPrivacyManager((Context) this.contextProvider.get());
    }
}