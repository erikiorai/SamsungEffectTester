package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/log/dagger/LogModule_ProvideMediaViewLogBufferFactory.class */
public final class LogModule_ProvideMediaViewLogBufferFactory implements Factory<LogBuffer> {
    public final Provider<LogBufferFactory> factoryProvider;

    public LogModule_ProvideMediaViewLogBufferFactory(Provider<LogBufferFactory> provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvideMediaViewLogBufferFactory create(Provider<LogBufferFactory> provider) {
        return new LogModule_ProvideMediaViewLogBufferFactory(provider);
    }

    public static LogBuffer provideMediaViewLogBuffer(LogBufferFactory logBufferFactory) {
        return (LogBuffer) Preconditions.checkNotNullFromProvides(LogModule.provideMediaViewLogBuffer(logBufferFactory));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LogBuffer m3118get() {
        return provideMediaViewLogBuffer((LogBufferFactory) this.factoryProvider.get());
    }
}