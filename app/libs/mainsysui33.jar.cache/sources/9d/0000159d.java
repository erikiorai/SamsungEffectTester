package com.android.systemui.dagger;

import android.content.Context;
import android.os.PowerExemptionManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvidePowerExemptionManagerFactory.class */
public final class FrameworkServicesModule_ProvidePowerExemptionManagerFactory implements Factory<PowerExemptionManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvidePowerExemptionManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvidePowerExemptionManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvidePowerExemptionManagerFactory(provider);
    }

    public static PowerExemptionManager providePowerExemptionManager(Context context) {
        return (PowerExemptionManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.providePowerExemptionManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public PowerExemptionManager m2343get() {
        return providePowerExemptionManager((Context) this.contextProvider.get());
    }
}