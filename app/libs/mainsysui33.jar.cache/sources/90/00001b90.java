package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/log/dagger/LogModule_ProvideMediaBrowserBufferFactory.class */
public final class LogModule_ProvideMediaBrowserBufferFactory implements Factory<LogBuffer> {
    public final Provider<LogBufferFactory> factoryProvider;

    public LogModule_ProvideMediaBrowserBufferFactory(Provider<LogBufferFactory> provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvideMediaBrowserBufferFactory create(Provider<LogBufferFactory> provider) {
        return new LogModule_ProvideMediaBrowserBufferFactory(provider);
    }

    public static LogBuffer provideMediaBrowserBuffer(LogBufferFactory logBufferFactory) {
        return (LogBuffer) Preconditions.checkNotNullFromProvides(LogModule.provideMediaBrowserBuffer(logBufferFactory));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LogBuffer m3113get() {
        return provideMediaBrowserBuffer((LogBufferFactory) this.factoryProvider.get());
    }
}