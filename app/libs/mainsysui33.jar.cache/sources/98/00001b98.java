package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/log/dagger/LogModule_ProvideNotificationHeadsUpLogBufferFactory.class */
public final class LogModule_ProvideNotificationHeadsUpLogBufferFactory implements Factory<LogBuffer> {
    public final Provider<LogBufferFactory> factoryProvider;

    public LogModule_ProvideNotificationHeadsUpLogBufferFactory(Provider<LogBufferFactory> provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvideNotificationHeadsUpLogBufferFactory create(Provider<LogBufferFactory> provider) {
        return new LogModule_ProvideNotificationHeadsUpLogBufferFactory(provider);
    }

    public static LogBuffer provideNotificationHeadsUpLogBuffer(LogBufferFactory logBufferFactory) {
        return (LogBuffer) Preconditions.checkNotNullFromProvides(LogModule.provideNotificationHeadsUpLogBuffer(logBufferFactory));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LogBuffer m3121get() {
        return provideNotificationHeadsUpLogBuffer((LogBufferFactory) this.factoryProvider.get());
    }
}