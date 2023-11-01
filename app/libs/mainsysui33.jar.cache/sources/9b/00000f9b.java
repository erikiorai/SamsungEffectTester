package com.android.systemui;

import android.content.Context;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/ForegroundServiceNotificationListener_Factory.class */
public final class ForegroundServiceNotificationListener_Factory implements Factory<ForegroundServiceNotificationListener> {
    public final Provider<Context> contextProvider;
    public final Provider<ForegroundServiceController> foregroundServiceControllerProvider;
    public final Provider<NotifPipeline> notifPipelineProvider;

    public ForegroundServiceNotificationListener_Factory(Provider<Context> provider, Provider<ForegroundServiceController> provider2, Provider<NotifPipeline> provider3) {
        this.contextProvider = provider;
        this.foregroundServiceControllerProvider = provider2;
        this.notifPipelineProvider = provider3;
    }

    public static ForegroundServiceNotificationListener_Factory create(Provider<Context> provider, Provider<ForegroundServiceController> provider2, Provider<NotifPipeline> provider3) {
        return new ForegroundServiceNotificationListener_Factory(provider, provider2, provider3);
    }

    public static ForegroundServiceNotificationListener newInstance(Context context, ForegroundServiceController foregroundServiceController, NotifPipeline notifPipeline) {
        return new ForegroundServiceNotificationListener(context, foregroundServiceController, notifPipeline);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ForegroundServiceNotificationListener m1263get() {
        return newInstance((Context) this.contextProvider.get(), (ForegroundServiceController) this.foregroundServiceControllerProvider.get(), (NotifPipeline) this.notifPipelineProvider.get());
    }
}