package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/log/dagger/LogModule_ProvideBiometricLogBufferFactory.class */
public final class LogModule_ProvideBiometricLogBufferFactory implements Factory<LogBuffer> {
    public final Provider<LogBufferFactory> factoryProvider;

    public LogModule_ProvideBiometricLogBufferFactory(Provider<LogBufferFactory> provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvideBiometricLogBufferFactory create(Provider<LogBufferFactory> provider) {
        return new LogModule_ProvideBiometricLogBufferFactory(provider);
    }

    public static LogBuffer provideBiometricLogBuffer(LogBufferFactory logBufferFactory) {
        return (LogBuffer) Preconditions.checkNotNullFromProvides(LogModule.provideBiometricLogBuffer(logBufferFactory));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LogBuffer m3103get() {
        return provideBiometricLogBuffer((LogBufferFactory) this.factoryProvider.get());
    }
}