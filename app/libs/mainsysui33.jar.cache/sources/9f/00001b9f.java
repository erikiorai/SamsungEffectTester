package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/log/dagger/LogModule_ProvideShadeHeightLogBufferFactory.class */
public final class LogModule_ProvideShadeHeightLogBufferFactory implements Factory<LogBuffer> {
    public final Provider<LogBufferFactory> factoryProvider;

    public LogModule_ProvideShadeHeightLogBufferFactory(Provider<LogBufferFactory> provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvideShadeHeightLogBufferFactory create(Provider<LogBufferFactory> provider) {
        return new LogModule_ProvideShadeHeightLogBufferFactory(provider);
    }

    public static LogBuffer provideShadeHeightLogBuffer(LogBufferFactory logBufferFactory) {
        return (LogBuffer) Preconditions.checkNotNullFromProvides(LogModule.provideShadeHeightLogBuffer(logBufferFactory));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LogBuffer m3128get() {
        return provideShadeHeightLogBuffer((LogBufferFactory) this.factoryProvider.get());
    }
}