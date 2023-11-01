package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/log/dagger/LogModule_ProvideMediaTttReceiverLogBufferFactory.class */
public final class LogModule_ProvideMediaTttReceiverLogBufferFactory implements Factory<LogBuffer> {
    public final Provider<LogBufferFactory> factoryProvider;

    public LogModule_ProvideMediaTttReceiverLogBufferFactory(Provider<LogBufferFactory> provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvideMediaTttReceiverLogBufferFactory create(Provider<LogBufferFactory> provider) {
        return new LogModule_ProvideMediaTttReceiverLogBufferFactory(provider);
    }

    public static LogBuffer provideMediaTttReceiverLogBuffer(LogBufferFactory logBufferFactory) {
        return (LogBuffer) Preconditions.checkNotNullFromProvides(LogModule.provideMediaTttReceiverLogBuffer(logBufferFactory));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LogBuffer m3116get() {
        return provideMediaTttReceiverLogBuffer((LogBufferFactory) this.factoryProvider.get());
    }
}