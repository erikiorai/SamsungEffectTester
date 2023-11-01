package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.plugins.log.LogBuffer;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/log/dagger/LogModule_ProvideNotificationsLogBufferFactory.class */
public final class LogModule_ProvideNotificationsLogBufferFactory implements Factory<LogBuffer> {
    public final Provider<LogBufferFactory> factoryProvider;
    public final Provider<NotifPipelineFlags> notifPipelineFlagsProvider;

    public LogModule_ProvideNotificationsLogBufferFactory(Provider<LogBufferFactory> provider, Provider<NotifPipelineFlags> provider2) {
        this.factoryProvider = provider;
        this.notifPipelineFlagsProvider = provider2;
    }

    public static LogModule_ProvideNotificationsLogBufferFactory create(Provider<LogBufferFactory> provider, Provider<NotifPipelineFlags> provider2) {
        return new LogModule_ProvideNotificationsLogBufferFactory(provider, provider2);
    }

    public static LogBuffer provideNotificationsLogBuffer(LogBufferFactory logBufferFactory, NotifPipelineFlags notifPipelineFlags) {
        return (LogBuffer) Preconditions.checkNotNullFromProvides(LogModule.provideNotificationsLogBuffer(logBufferFactory, notifPipelineFlags));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LogBuffer m3124get() {
        return provideNotificationsLogBuffer((LogBufferFactory) this.factoryProvider.get(), (NotifPipelineFlags) this.notifPipelineFlagsProvider.get());
    }
}