package com.android.systemui.dagger;

import android.app.IActivityManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideIActivityManagerFactory.class */
public final class FrameworkServicesModule_ProvideIActivityManagerFactory implements Factory<IActivityManager> {

    /* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideIActivityManagerFactory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final FrameworkServicesModule_ProvideIActivityManagerFactory INSTANCE = new FrameworkServicesModule_ProvideIActivityManagerFactory();
    }

    public static FrameworkServicesModule_ProvideIActivityManagerFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static IActivityManager provideIActivityManager() {
        return (IActivityManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideIActivityManager());
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public IActivityManager m2289get() {
        return provideIActivityManager();
    }
}