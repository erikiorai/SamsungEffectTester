package com.android.systemui.dagger;

import android.content.pm.IPackageManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideIPackageManagerFactory.class */
public final class FrameworkServicesModule_ProvideIPackageManagerFactory implements Factory<IPackageManager> {

    /* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideIPackageManagerFactory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final FrameworkServicesModule_ProvideIPackageManagerFactory INSTANCE = new FrameworkServicesModule_ProvideIPackageManagerFactory();
    }

    public static FrameworkServicesModule_ProvideIPackageManagerFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static IPackageManager provideIPackageManager() {
        return (IPackageManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideIPackageManager());
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public IPackageManager m2305get() {
        return provideIPackageManager();
    }
}