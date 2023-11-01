package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/log/dagger/LogModule_ProvideMediaTttSenderLogBufferFactory.class */
public final class LogModule_ProvideMediaTttSenderLogBufferFactory implements Factory<LogBuffer> {
    public final Provider<LogBufferFactory> factoryProvider;

    public LogModule_ProvideMediaTttSenderLogBufferFactory(Provider<LogBufferFactory> provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvideMediaTttSenderLogBufferFactory create(Provider<LogBufferFactory> provider) {
        return new LogModule_ProvideMediaTttSenderLogBufferFactory(provider);
    }

    public static LogBuffer provideMediaTttSenderLogBuffer(LogBufferFactory logBufferFactory) {
        return (LogBuffer) Preconditions.checkNotNullFromProvides(LogModule.provideMediaTttSenderLogBuffer(logBufferFactory));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LogBuffer m3117get() {
        return provideMediaTttSenderLogBuffer((LogBufferFactory) this.factoryProvider.get());
    }
}