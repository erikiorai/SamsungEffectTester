package com.android.systemui.dagger;

import android.content.Context;
import android.content.pm.PackageManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvidePackageManagerFactory.class */
public final class FrameworkServicesModule_ProvidePackageManagerFactory implements Factory<PackageManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvidePackageManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvidePackageManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvidePackageManagerFactory(provider);
    }

    public static PackageManager providePackageManager(Context context) {
        return (PackageManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.providePackageManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public PackageManager m2338get() {
        return providePackageManager((Context) this.contextProvider.get());
    }
}