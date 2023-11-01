package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/log/dagger/LogModule_ProvideKeyguardLogBufferFactory.class */
public final class LogModule_ProvideKeyguardLogBufferFactory implements Factory<LogBuffer> {
    public final Provider<LogBufferFactory> factoryProvider;

    public LogModule_ProvideKeyguardLogBufferFactory(Provider<LogBufferFactory> provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvideKeyguardLogBufferFactory create(Provider<LogBufferFactory> provider) {
        return new LogModule_ProvideKeyguardLogBufferFactory(provider);
    }

    public static LogBuffer provideKeyguardLogBuffer(LogBufferFactory logBufferFactory) {
        return (LogBuffer) Preconditions.checkNotNullFromProvides(LogModule.provideKeyguardLogBuffer(logBufferFactory));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LogBuffer m3109get() {
        return provideKeyguardLogBuffer((LogBufferFactory) this.factoryProvider.get());
    }
}