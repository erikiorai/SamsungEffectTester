package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/log/dagger/LogModule_ProvideNotificationRenderLogBufferFactory.class */
public final class LogModule_ProvideNotificationRenderLogBufferFactory implements Factory<LogBuffer> {
    public final Provider<LogBufferFactory> factoryProvider;

    public LogModule_ProvideNotificationRenderLogBufferFactory(Provider<LogBufferFactory> provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvideNotificationRenderLogBufferFactory create(Provider<LogBufferFactory> provider) {
        return new LogModule_ProvideNotificationRenderLogBufferFactory(provider);
    }

    public static LogBuffer provideNotificationRenderLogBuffer(LogBufferFactory logBufferFactory) {
        return (LogBuffer) Preconditions.checkNotNullFromProvides(LogModule.provideNotificationRenderLogBuffer(logBufferFactory));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LogBuffer m3123get() {
        return provideNotificationRenderLogBuffer((LogBufferFactory) this.factoryProvider.get());
    }
}