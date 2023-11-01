package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/log/dagger/LogModule_ProvideShadeWindowLogBufferFactory.class */
public final class LogModule_ProvideShadeWindowLogBufferFactory implements Factory<LogBuffer> {
    public final Provider<LogBufferFactory> factoryProvider;

    public LogModule_ProvideShadeWindowLogBufferFactory(Provider<LogBufferFactory> provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvideShadeWindowLogBufferFactory create(Provider<LogBufferFactory> provider) {
        return new LogModule_ProvideShadeWindowLogBufferFactory(provider);
    }

    public static LogBuffer provideShadeWindowLogBuffer(LogBufferFactory logBufferFactory) {
        return (LogBuffer) Preconditions.checkNotNullFromProvides(LogModule.provideShadeWindowLogBuffer(logBufferFactory));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LogBuffer m3130get() {
        return provideShadeWindowLogBuffer((LogBufferFactory) this.factoryProvider.get());
    }
}