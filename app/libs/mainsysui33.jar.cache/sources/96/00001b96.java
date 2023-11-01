package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/log/dagger/LogModule_ProvideNearbyMediaDevicesLogBufferFactory.class */
public final class LogModule_ProvideNearbyMediaDevicesLogBufferFactory implements Factory<LogBuffer> {
    public final Provider<LogBufferFactory> factoryProvider;

    public LogModule_ProvideNearbyMediaDevicesLogBufferFactory(Provider<LogBufferFactory> provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvideNearbyMediaDevicesLogBufferFactory create(Provider<LogBufferFactory> provider) {
        return new LogModule_ProvideNearbyMediaDevicesLogBufferFactory(provider);
    }

    public static LogBuffer provideNearbyMediaDevicesLogBuffer(LogBufferFactory logBufferFactory) {
        return (LogBuffer) Preconditions.checkNotNullFromProvides(LogModule.provideNearbyMediaDevicesLogBuffer(logBufferFactory));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LogBuffer m3119get() {
        return provideNearbyMediaDevicesLogBuffer((LogBufferFactory) this.factoryProvider.get());
    }
}