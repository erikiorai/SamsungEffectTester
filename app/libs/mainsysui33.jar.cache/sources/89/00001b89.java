package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/log/dagger/LogModule_ProvideCollapsedSbFragmentLogBufferFactory.class */
public final class LogModule_ProvideCollapsedSbFragmentLogBufferFactory implements Factory<LogBuffer> {
    public final Provider<LogBufferFactory> factoryProvider;

    public LogModule_ProvideCollapsedSbFragmentLogBufferFactory(Provider<LogBufferFactory> provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvideCollapsedSbFragmentLogBufferFactory create(Provider<LogBufferFactory> provider) {
        return new LogModule_ProvideCollapsedSbFragmentLogBufferFactory(provider);
    }

    public static LogBuffer provideCollapsedSbFragmentLogBuffer(LogBufferFactory logBufferFactory) {
        return (LogBuffer) Preconditions.checkNotNullFromProvides(LogModule.provideCollapsedSbFragmentLogBuffer(logBufferFactory));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LogBuffer m3106get() {
        return provideCollapsedSbFragmentLogBuffer((LogBufferFactory) this.factoryProvider.get());
    }
}