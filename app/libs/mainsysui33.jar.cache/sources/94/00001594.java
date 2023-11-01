package com.android.systemui.dagger;

import android.content.Context;
import androidx.core.app.NotificationManagerCompat;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideNotificationManagerCompatFactory.class */
public final class FrameworkServicesModule_ProvideNotificationManagerCompatFactory implements Factory<NotificationManagerCompat> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideNotificationManagerCompatFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideNotificationManagerCompatFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideNotificationManagerCompatFactory(provider);
    }

    public static NotificationManagerCompat provideNotificationManagerCompat(Context context) {
        return (NotificationManagerCompat) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideNotificationManagerCompat(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public NotificationManagerCompat m2333get() {
        return provideNotificationManagerCompat((Context) this.contextProvider.get());
    }
}