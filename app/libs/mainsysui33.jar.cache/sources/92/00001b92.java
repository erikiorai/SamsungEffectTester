package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/log/dagger/LogModule_ProvideMediaMuteAwaitLogBufferFactory.class */
public final class LogModule_ProvideMediaMuteAwaitLogBufferFactory implements Factory<LogBuffer> {
    public final Provider<LogBufferFactory> factoryProvider;

    public LogModule_ProvideMediaMuteAwaitLogBufferFactory(Provider<LogBufferFactory> provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvideMediaMuteAwaitLogBufferFactory create(Provider<LogBufferFactory> provider) {
        return new LogModule_ProvideMediaMuteAwaitLogBufferFactory(provider);
    }

    public static LogBuffer provideMediaMuteAwaitLogBuffer(LogBufferFactory logBufferFactory) {
        return (LogBuffer) Preconditions.checkNotNullFromProvides(LogModule.provideMediaMuteAwaitLogBuffer(logBufferFactory));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LogBuffer m3115get() {
        return provideMediaMuteAwaitLogBuffer((LogBufferFactory) this.factoryProvider.get());
    }
}