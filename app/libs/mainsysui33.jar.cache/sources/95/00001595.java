package com.android.systemui.dagger;

import android.app.NotificationManager;
import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideNotificationManagerFactory.class */
public final class FrameworkServicesModule_ProvideNotificationManagerFactory implements Factory<NotificationManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideNotificationManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideNotificationManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideNotificationManagerFactory(provider);
    }

    public static NotificationManager provideNotificationManager(Context context) {
        return (NotificationManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideNotificationManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public NotificationManager m2334get() {
        return provideNotificationManager((Context) this.contextProvider.get());
    }
}