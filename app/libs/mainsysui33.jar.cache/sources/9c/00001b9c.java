package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/log/dagger/LogModule_ProvidePrivacyLogBufferFactory.class */
public final class LogModule_ProvidePrivacyLogBufferFactory implements Factory<LogBuffer> {
    public final Provider<LogBufferFactory> factoryProvider;

    public LogModule_ProvidePrivacyLogBufferFactory(Provider<LogBufferFactory> provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvidePrivacyLogBufferFactory create(Provider<LogBufferFactory> provider) {
        return new LogModule_ProvidePrivacyLogBufferFactory(provider);
    }

    public static LogBuffer providePrivacyLogBuffer(LogBufferFactory logBufferFactory) {
        return (LogBuffer) Preconditions.checkNotNullFromProvides(LogModule.providePrivacyLogBuffer(logBufferFactory));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LogBuffer m3125get() {
        return providePrivacyLogBuffer((LogBufferFactory) this.factoryProvider.get());
    }
}