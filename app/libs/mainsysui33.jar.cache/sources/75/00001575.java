package com.android.systemui.dagger;

import android.app.IActivityTaskManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideIActivityTaskManagerFactory.class */
public final class FrameworkServicesModule_ProvideIActivityTaskManagerFactory implements Factory<IActivityTaskManager> {

    /* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideIActivityTaskManagerFactory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final FrameworkServicesModule_ProvideIActivityTaskManagerFactory INSTANCE = new FrameworkServicesModule_ProvideIActivityTaskManagerFactory();
    }

    public static FrameworkServicesModule_ProvideIActivityTaskManagerFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static IActivityTaskManager provideIActivityTaskManager() {
        return (IActivityTaskManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideIActivityTaskManager());
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public IActivityTaskManager m2292get() {
        return provideIActivityTaskManager();
    }
}