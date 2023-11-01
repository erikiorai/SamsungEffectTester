package com.android.systemui.dagger;

import android.content.Context;
import android.hardware.devicestate.DeviceStateManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideDeviceStateManagerFactory.class */
public final class FrameworkServicesModule_ProvideDeviceStateManagerFactory implements Factory<DeviceStateManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideDeviceStateManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideDeviceStateManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideDeviceStateManagerFactory(provider);
    }

    public static DeviceStateManager provideDeviceStateManager(Context context) {
        return (DeviceStateManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideDeviceStateManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DeviceStateManager m2285get() {
        return provideDeviceStateManager((Context) this.contextProvider.get());
    }
}