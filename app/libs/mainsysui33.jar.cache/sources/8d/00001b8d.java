package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/log/dagger/LogModule_ProvideKeyguardUpdateMonitorLogBufferFactory.class */
public final class LogModule_ProvideKeyguardUpdateMonitorLogBufferFactory implements Factory<LogBuffer> {
    public final Provider<LogBufferFactory> factoryProvider;

    public LogModule_ProvideKeyguardUpdateMonitorLogBufferFactory(Provider<LogBufferFactory> provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvideKeyguardUpdateMonitorLogBufferFactory create(Provider<LogBufferFactory> provider) {
        return new LogModule_ProvideKeyguardUpdateMonitorLogBufferFactory(provider);
    }

    public static LogBuffer provideKeyguardUpdateMonitorLogBuffer(LogBufferFactory logBufferFactory) {
        return (LogBuffer) Preconditions.checkNotNullFromProvides(LogModule.provideKeyguardUpdateMonitorLogBuffer(logBufferFactory));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LogBuffer m3110get() {
        return provideKeyguardUpdateMonitorLogBuffer((LogBufferFactory) this.factoryProvider.get());
    }
}