package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/log/dagger/LogModule_ProvideMediaCarouselControllerBufferFactory.class */
public final class LogModule_ProvideMediaCarouselControllerBufferFactory implements Factory<LogBuffer> {
    public final Provider<LogBufferFactory> factoryProvider;

    public LogModule_ProvideMediaCarouselControllerBufferFactory(Provider<LogBufferFactory> provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvideMediaCarouselControllerBufferFactory create(Provider<LogBufferFactory> provider) {
        return new LogModule_ProvideMediaCarouselControllerBufferFactory(provider);
    }

    public static LogBuffer provideMediaCarouselControllerBuffer(LogBufferFactory logBufferFactory) {
        return (LogBuffer) Preconditions.checkNotNullFromProvides(LogModule.provideMediaCarouselControllerBuffer(logBufferFactory));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LogBuffer m3114get() {
        return provideMediaCarouselControllerBuffer((LogBufferFactory) this.factoryProvider.get());
    }
}