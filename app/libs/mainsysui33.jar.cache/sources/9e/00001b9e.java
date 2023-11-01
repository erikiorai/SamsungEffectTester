package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/log/dagger/LogModule_ProvideQuickSettingsLogBufferFactory.class */
public final class LogModule_ProvideQuickSettingsLogBufferFactory implements Factory<LogBuffer> {
    public final Provider<LogBufferFactory> factoryProvider;

    public LogModule_ProvideQuickSettingsLogBufferFactory(Provider<LogBufferFactory> provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvideQuickSettingsLogBufferFactory create(Provider<LogBufferFactory> provider) {
        return new LogModule_ProvideQuickSettingsLogBufferFactory(provider);
    }

    public static LogBuffer provideQuickSettingsLogBuffer(LogBufferFactory logBufferFactory) {
        return (LogBuffer) Preconditions.checkNotNullFromProvides(LogModule.provideQuickSettingsLogBuffer(logBufferFactory));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LogBuffer m3127get() {
        return provideQuickSettingsLogBuffer((LogBufferFactory) this.factoryProvider.get());
    }
}