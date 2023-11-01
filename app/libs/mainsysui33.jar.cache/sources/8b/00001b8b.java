package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/log/dagger/LogModule_ProvideKeyguardClockLogFactory.class */
public final class LogModule_ProvideKeyguardClockLogFactory implements Factory<LogBuffer> {
    public final Provider<LogBufferFactory> factoryProvider;

    public LogModule_ProvideKeyguardClockLogFactory(Provider<LogBufferFactory> provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvideKeyguardClockLogFactory create(Provider<LogBufferFactory> provider) {
        return new LogModule_ProvideKeyguardClockLogFactory(provider);
    }

    public static LogBuffer provideKeyguardClockLog(LogBufferFactory logBufferFactory) {
        return (LogBuffer) Preconditions.checkNotNullFromProvides(LogModule.provideKeyguardClockLog(logBufferFactory));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LogBuffer m3108get() {
        return provideKeyguardClockLog((LogBufferFactory) this.factoryProvider.get());
    }
}