package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/log/dagger/LogModule_ProvideBroadcastDispatcherLogBufferFactory.class */
public final class LogModule_ProvideBroadcastDispatcherLogBufferFactory implements Factory<LogBuffer> {
    public final Provider<LogBufferFactory> factoryProvider;

    public LogModule_ProvideBroadcastDispatcherLogBufferFactory(Provider<LogBufferFactory> provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvideBroadcastDispatcherLogBufferFactory create(Provider<LogBufferFactory> provider) {
        return new LogModule_ProvideBroadcastDispatcherLogBufferFactory(provider);
    }

    public static LogBuffer provideBroadcastDispatcherLogBuffer(LogBufferFactory logBufferFactory) {
        return (LogBuffer) Preconditions.checkNotNullFromProvides(LogModule.provideBroadcastDispatcherLogBuffer(logBufferFactory));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LogBuffer m3105get() {
        return provideBroadcastDispatcherLogBuffer((LogBufferFactory) this.factoryProvider.get());
    }
}