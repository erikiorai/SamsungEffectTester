package com.android.systemui.dagger;

import android.content.Context;
import android.telephony.CarrierConfigManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideCarrierConfigManagerFactory.class */
public final class FrameworkServicesModule_ProvideCarrierConfigManagerFactory implements Factory<CarrierConfigManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideCarrierConfigManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideCarrierConfigManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideCarrierConfigManagerFactory(provider);
    }

    public static CarrierConfigManager provideCarrierConfigManager(Context context) {
        return (CarrierConfigManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideCarrierConfigManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public CarrierConfigManager m2276get() {
        return provideCarrierConfigManager((Context) this.contextProvider.get());
    }
}