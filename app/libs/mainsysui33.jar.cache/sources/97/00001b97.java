package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/log/dagger/LogModule_ProvideNotifInteractionLogBufferFactory.class */
public final class LogModule_ProvideNotifInteractionLogBufferFactory implements Factory<LogBuffer> {
    public final Provider<LogBufferFactory> factoryProvider;

    public LogModule_ProvideNotifInteractionLogBufferFactory(Provider<LogBufferFactory> provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvideNotifInteractionLogBufferFactory create(Provider<LogBufferFactory> provider) {
        return new LogModule_ProvideNotifInteractionLogBufferFactory(provider);
    }

    public static LogBuffer provideNotifInteractionLogBuffer(LogBufferFactory logBufferFactory) {
        return (LogBuffer) Preconditions.checkNotNullFromProvides(LogModule.provideNotifInteractionLogBuffer(logBufferFactory));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LogBuffer m3120get() {
        return provideNotifInteractionLogBuffer((LogBufferFactory) this.factoryProvider.get());
    }
}