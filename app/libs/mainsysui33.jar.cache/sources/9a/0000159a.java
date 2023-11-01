package com.android.systemui.dagger;

import com.android.systemui.shared.system.PackageManagerWrapper;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvidePackageManagerWrapperFactory.class */
public final class FrameworkServicesModule_ProvidePackageManagerWrapperFactory implements Factory<PackageManagerWrapper> {

    /* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvidePackageManagerWrapperFactory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final FrameworkServicesModule_ProvidePackageManagerWrapperFactory INSTANCE = new FrameworkServicesModule_ProvidePackageManagerWrapperFactory();
    }

    public static FrameworkServicesModule_ProvidePackageManagerWrapperFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static PackageManagerWrapper providePackageManagerWrapper() {
        return (PackageManagerWrapper) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.providePackageManagerWrapper());
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public PackageManagerWrapper m2339get() {
        return providePackageManagerWrapper();
    }
}