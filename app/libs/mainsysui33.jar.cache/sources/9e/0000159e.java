package com.android.systemui.dagger;

import android.content.Context;
import android.os.PowerManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvidePowerManagerFactory.class */
public final class FrameworkServicesModule_ProvidePowerManagerFactory implements Factory<PowerManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvidePowerManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvidePowerManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvidePowerManagerFactory(provider);
    }

    public static PowerManager providePowerManager(Context context) {
        return (PowerManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.providePowerManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public PowerManager m2344get() {
        return providePowerManager((Context) this.contextProvider.get());
    }
}