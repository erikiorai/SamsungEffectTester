package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/log/dagger/LogModule_ProviderBluetoothLogBufferFactory.class */
public final class LogModule_ProviderBluetoothLogBufferFactory implements Factory<LogBuffer> {
    public final Provider<LogBufferFactory> factoryProvider;

    public LogModule_ProviderBluetoothLogBufferFactory(Provider<LogBufferFactory> provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProviderBluetoothLogBufferFactory create(Provider<LogBufferFactory> provider) {
        return new LogModule_ProviderBluetoothLogBufferFactory(provider);
    }

    public static LogBuffer providerBluetoothLogBuffer(LogBufferFactory logBufferFactory) {
        return (LogBuffer) Preconditions.checkNotNullFromProvides(LogModule.providerBluetoothLogBuffer(logBufferFactory));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LogBuffer m3135get() {
        return providerBluetoothLogBuffer((LogBufferFactory) this.factoryProvider.get());
    }
}