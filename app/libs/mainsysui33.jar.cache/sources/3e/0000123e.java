package com.android.systemui.biometrics.dagger;

import com.android.systemui.util.concurrency.ThreadFactory;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/dagger/BiometricsModule_ProvidesPluginExecutorFactory.class */
public final class BiometricsModule_ProvidesPluginExecutorFactory implements Factory<Executor> {
    public final Provider<ThreadFactory> threadFactoryProvider;

    public BiometricsModule_ProvidesPluginExecutorFactory(Provider<ThreadFactory> provider) {
        this.threadFactoryProvider = provider;
    }

    public static BiometricsModule_ProvidesPluginExecutorFactory create(Provider<ThreadFactory> provider) {
        return new BiometricsModule_ProvidesPluginExecutorFactory(provider);
    }

    public static Executor providesPluginExecutor(ThreadFactory threadFactory) {
        return (Executor) Preconditions.checkNotNullFromProvides(BiometricsModule.providesPluginExecutor(threadFactory));
    }

    /* JADX DEBUG: Method merged with bridge method */
    public Executor get() {
        return providesPluginExecutor((ThreadFactory) this.threadFactoryProvider.get());
    }
}