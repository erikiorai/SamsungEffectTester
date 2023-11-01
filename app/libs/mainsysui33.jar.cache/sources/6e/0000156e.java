package com.android.systemui.dagger;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideDevicePolicyManagerFactory.class */
public final class FrameworkServicesModule_ProvideDevicePolicyManagerFactory implements Factory<DevicePolicyManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideDevicePolicyManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideDevicePolicyManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideDevicePolicyManagerFactory(provider);
    }

    public static DevicePolicyManager provideDevicePolicyManager(Context context) {
        return (DevicePolicyManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideDevicePolicyManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DevicePolicyManager m2284get() {
        return provideDevicePolicyManager((Context) this.contextProvider.get());
    }
}