package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/log/dagger/LogModule_ProvideNotificationInterruptLogBufferFactory.class */
public final class LogModule_ProvideNotificationInterruptLogBufferFactory implements Factory<LogBuffer> {
    public final Provider<LogBufferFactory> factoryProvider;

    public LogModule_ProvideNotificationInterruptLogBufferFactory(Provider<LogBufferFactory> provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvideNotificationInterruptLogBufferFactory create(Provider<LogBufferFactory> provider) {
        return new LogModule_ProvideNotificationInterruptLogBufferFactory(provider);
    }

    public static LogBuffer provideNotificationInterruptLogBuffer(LogBufferFactory logBufferFactory) {
        return (LogBuffer) Preconditions.checkNotNullFromProvides(LogModule.provideNotificationInterruptLogBuffer(logBufferFactory));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LogBuffer m3122get() {
        return provideNotificationInterruptLogBuffer((LogBufferFactory) this.factoryProvider.get());
    }
}