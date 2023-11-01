package com.android.systemui.dagger;

import android.app.ActivityTaskManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideActivityTaskManagerFactory.class */
public final class FrameworkServicesModule_ProvideActivityTaskManagerFactory implements Factory<ActivityTaskManager> {

    /* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideActivityTaskManagerFactory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final FrameworkServicesModule_ProvideActivityTaskManagerFactory INSTANCE = new FrameworkServicesModule_ProvideActivityTaskManagerFactory();
    }

    public static FrameworkServicesModule_ProvideActivityTaskManagerFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static ActivityTaskManager provideActivityTaskManager() {
        return (ActivityTaskManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideActivityTaskManager());
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ActivityTaskManager m2266get() {
        return provideActivityTaskManager();
    }
}