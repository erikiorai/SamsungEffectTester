package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/log/dagger/LogModule_ProvideStatusBarNetworkControllerBufferFactory.class */
public final class LogModule_ProvideStatusBarNetworkControllerBufferFactory implements Factory<LogBuffer> {
    public final Provider<LogBufferFactory> factoryProvider;

    public LogModule_ProvideStatusBarNetworkControllerBufferFactory(Provider<LogBufferFactory> provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvideStatusBarNetworkControllerBufferFactory create(Provider<LogBufferFactory> provider) {
        return new LogModule_ProvideStatusBarNetworkControllerBufferFactory(provider);
    }

    public static LogBuffer provideStatusBarNetworkControllerBuffer(LogBufferFactory logBufferFactory) {
        return (LogBuffer) Preconditions.checkNotNullFromProvides(LogModule.provideStatusBarNetworkControllerBuffer(logBufferFactory));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LogBuffer m3132get() {
        return provideStatusBarNetworkControllerBuffer((LogBufferFactory) this.factoryProvider.get());
    }
}