package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/log/dagger/LogModule_ProvideStatusBarConnectivityBufferFactory.class */
public final class LogModule_ProvideStatusBarConnectivityBufferFactory implements Factory<LogBuffer> {
    public final Provider<LogBufferFactory> factoryProvider;

    public LogModule_ProvideStatusBarConnectivityBufferFactory(Provider<LogBufferFactory> provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvideStatusBarConnectivityBufferFactory create(Provider<LogBufferFactory> provider) {
        return new LogModule_ProvideStatusBarConnectivityBufferFactory(provider);
    }

    public static LogBuffer provideStatusBarConnectivityBuffer(LogBufferFactory logBufferFactory) {
        return (LogBuffer) Preconditions.checkNotNullFromProvides(LogModule.provideStatusBarConnectivityBuffer(logBufferFactory));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LogBuffer m3131get() {
        return provideStatusBarConnectivityBuffer((LogBufferFactory) this.factoryProvider.get());
    }
}