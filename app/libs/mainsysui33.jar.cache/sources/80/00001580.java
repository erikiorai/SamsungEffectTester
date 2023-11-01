package com.android.systemui.dagger;

import com.android.internal.statusbar.IStatusBarService;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideIStatusBarServiceFactory.class */
public final class FrameworkServicesModule_ProvideIStatusBarServiceFactory implements Factory<IStatusBarService> {

    /* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideIStatusBarServiceFactory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final FrameworkServicesModule_ProvideIStatusBarServiceFactory INSTANCE = new FrameworkServicesModule_ProvideIStatusBarServiceFactory();
    }

    public static FrameworkServicesModule_ProvideIStatusBarServiceFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static IStatusBarService provideIStatusBarService() {
        return (IStatusBarService) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideIStatusBarService());
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public IStatusBarService m2308get() {
        return provideIStatusBarService();
    }
}