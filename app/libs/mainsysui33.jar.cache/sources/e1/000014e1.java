package com.android.systemui.dagger;

import android.content.Context;
import com.android.internal.util.NotificationMessagingUtil;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/AndroidInternalsModule_ProvideNotificationMessagingUtilFactory.class */
public final class AndroidInternalsModule_ProvideNotificationMessagingUtilFactory implements Factory<NotificationMessagingUtil> {
    public final Provider<Context> contextProvider;
    public final AndroidInternalsModule module;

    public AndroidInternalsModule_ProvideNotificationMessagingUtilFactory(AndroidInternalsModule androidInternalsModule, Provider<Context> provider) {
        this.module = androidInternalsModule;
        this.contextProvider = provider;
    }

    public static AndroidInternalsModule_ProvideNotificationMessagingUtilFactory create(AndroidInternalsModule androidInternalsModule, Provider<Context> provider) {
        return new AndroidInternalsModule_ProvideNotificationMessagingUtilFactory(androidInternalsModule, provider);
    }

    public static NotificationMessagingUtil provideNotificationMessagingUtil(AndroidInternalsModule androidInternalsModule, Context context) {
        return (NotificationMessagingUtil) Preconditions.checkNotNullFromProvides(androidInternalsModule.provideNotificationMessagingUtil(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public NotificationMessagingUtil m1890get() {
        return provideNotificationMessagingUtil(this.module, (Context) this.contextProvider.get());
    }
}