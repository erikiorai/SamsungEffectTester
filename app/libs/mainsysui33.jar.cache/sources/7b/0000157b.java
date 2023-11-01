package com.android.systemui.dagger;

import android.service.dreams.IDreamManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideIDreamManagerFactory.class */
public final class FrameworkServicesModule_ProvideIDreamManagerFactory implements Factory<IDreamManager> {

    /* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideIDreamManagerFactory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final FrameworkServicesModule_ProvideIDreamManagerFactory INSTANCE = new FrameworkServicesModule_ProvideIDreamManagerFactory();
    }

    public static FrameworkServicesModule_ProvideIDreamManagerFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static IDreamManager provideIDreamManager() {
        return (IDreamManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideIDreamManager());
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public IDreamManager m2301get() {
        return provideIDreamManager();
    }
}