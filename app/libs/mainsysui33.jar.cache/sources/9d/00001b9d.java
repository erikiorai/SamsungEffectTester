package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/log/dagger/LogModule_ProvideQSFragmentDisableLogBufferFactory.class */
public final class LogModule_ProvideQSFragmentDisableLogBufferFactory implements Factory<LogBuffer> {
    public final Provider<LogBufferFactory> factoryProvider;

    public LogModule_ProvideQSFragmentDisableLogBufferFactory(Provider<LogBufferFactory> provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvideQSFragmentDisableLogBufferFactory create(Provider<LogBufferFactory> provider) {
        return new LogModule_ProvideQSFragmentDisableLogBufferFactory(provider);
    }

    public static LogBuffer provideQSFragmentDisableLogBuffer(LogBufferFactory logBufferFactory) {
        return (LogBuffer) Preconditions.checkNotNullFromProvides(LogModule.provideQSFragmentDisableLogBuffer(logBufferFactory));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LogBuffer m3126get() {
        return provideQSFragmentDisableLogBuffer((LogBufferFactory) this.factoryProvider.get());
    }
}