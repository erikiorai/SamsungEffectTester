package com.android.systemui;

import android.app.NotificationManager;
import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/GuestSessionNotification_Factory.class */
public final class GuestSessionNotification_Factory implements Factory<GuestSessionNotification> {
    public final Provider<Context> contextProvider;
    public final Provider<NotificationManager> notificationManagerProvider;

    public GuestSessionNotification_Factory(Provider<Context> provider, Provider<NotificationManager> provider2) {
        this.contextProvider = provider;
        this.notificationManagerProvider = provider2;
    }

    public static GuestSessionNotification_Factory create(Provider<Context> provider, Provider<NotificationManager> provider2) {
        return new GuestSessionNotification_Factory(provider, provider2);
    }

    public static GuestSessionNotification newInstance(Context context, NotificationManager notificationManager) {
        return new GuestSessionNotification(context, notificationManager);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public GuestSessionNotification m1275get() {
        return newInstance((Context) this.contextProvider.get(), (NotificationManager) this.notificationManagerProvider.get());
    }
}