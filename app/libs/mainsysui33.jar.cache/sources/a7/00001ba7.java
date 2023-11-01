package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/log/dagger/LogModule_ProvidesMediaTimeoutListenerLogBufferFactory.class */
public final class LogModule_ProvidesMediaTimeoutListenerLogBufferFactory implements Factory<LogBuffer> {
    public final Provider<LogBufferFactory> factoryProvider;

    public LogModule_ProvidesMediaTimeoutListenerLogBufferFactory(Provider<LogBufferFactory> provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvidesMediaTimeoutListenerLogBufferFactory create(Provider<LogBufferFactory> provider) {
        return new LogModule_ProvidesMediaTimeoutListenerLogBufferFactory(provider);
    }

    public static LogBuffer providesMediaTimeoutListenerLogBuffer(LogBufferFactory logBufferFactory) {
        return (LogBuffer) Preconditions.checkNotNullFromProvides(LogModule.providesMediaTimeoutListenerLogBuffer(logBufferFactory));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LogBuffer m3136get() {
        return providesMediaTimeoutListenerLogBuffer((LogBufferFactory) this.factoryProvider.get());
    }
}