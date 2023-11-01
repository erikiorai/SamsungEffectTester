package com.android.systemui.dagger;

import android.content.Context;
import android.hardware.SensorManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvidesSensorManagerFactory.class */
public final class FrameworkServicesModule_ProvidesSensorManagerFactory implements Factory<SensorManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvidesSensorManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvidesSensorManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvidesSensorManagerFactory(provider);
    }

    public static SensorManager providesSensorManager(Context context) {
        return (SensorManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.providesSensorManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public SensorManager m2368get() {
        return providesSensorManager((Context) this.contextProvider.get());
    }
}