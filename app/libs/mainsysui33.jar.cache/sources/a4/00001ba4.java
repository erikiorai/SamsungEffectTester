package com.android.systemui.log.dagger;

import com.android.systemui.log.LogBufferFactory;
import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/log/dagger/LogModule_ProvideSwipeAwayGestureLogBufferFactory.class */
public final class LogModule_ProvideSwipeAwayGestureLogBufferFactory implements Factory<LogBuffer> {
    public final Provider<LogBufferFactory> factoryProvider;

    public LogModule_ProvideSwipeAwayGestureLogBufferFactory(Provider<LogBufferFactory> provider) {
        this.factoryProvider = provider;
    }

    public static LogModule_ProvideSwipeAwayGestureLogBufferFactory create(Provider<LogBufferFactory> provider) {
        return new LogModule_ProvideSwipeAwayGestureLogBufferFactory(provider);
    }

    public static LogBuffer provideSwipeAwayGestureLogBuffer(LogBufferFactory logBufferFactory) {
        return (LogBuffer) Preconditions.checkNotNullFromProvides(LogModule.provideSwipeAwayGestureLogBuffer(logBufferFactory));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LogBuffer m3133get() {
        return provideSwipeAwayGestureLogBuffer((LogBufferFactory) this.factoryProvider.get());
    }
}