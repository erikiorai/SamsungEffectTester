package com.android.systemui.dagger;

import android.app.INotificationManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideINotificationManagerFactory.class */
public final class FrameworkServicesModule_ProvideINotificationManagerFactory implements Factory<INotificationManager> {
    public final FrameworkServicesModule module;

    public FrameworkServicesModule_ProvideINotificationManagerFactory(FrameworkServicesModule frameworkServicesModule) {
        this.module = frameworkServicesModule;
    }

    public static FrameworkServicesModule_ProvideINotificationManagerFactory create(FrameworkServicesModule frameworkServicesModule) {
        return new FrameworkServicesModule_ProvideINotificationManagerFactory(frameworkServicesModule);
    }

    public static INotificationManager provideINotificationManager(FrameworkServicesModule frameworkServicesModule) {
        return (INotificationManager) Preconditions.checkNotNullFromProvides(frameworkServicesModule.provideINotificationManager());
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public INotificationManager m2304get() {
        return provideINotificationManager(this.module);
    }
}