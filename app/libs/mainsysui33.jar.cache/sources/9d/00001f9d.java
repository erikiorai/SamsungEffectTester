package com.android.systemui.plugins;

import com.android.systemui.util.concurrency.ThreadFactory;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/plugins/PluginsModule_ProvidesPluginExecutorFactory.class */
public final class PluginsModule_ProvidesPluginExecutorFactory implements Factory<Executor> {
    private final Provider<ThreadFactory> threadFactoryProvider;

    public PluginsModule_ProvidesPluginExecutorFactory(Provider<ThreadFactory> provider) {
        this.threadFactoryProvider = provider;
    }

    public static PluginsModule_ProvidesPluginExecutorFactory create(Provider<ThreadFactory> provider) {
        return new PluginsModule_ProvidesPluginExecutorFactory(provider);
    }

    public static Executor providesPluginExecutor(ThreadFactory threadFactory) {
        return (Executor) Preconditions.checkNotNullFromProvides(PluginsModule.providesPluginExecutor(threadFactory));
    }

    /* JADX DEBUG: Method merged with bridge method */
    public Executor get() {
        return providesPluginExecutor((ThreadFactory) this.threadFactoryProvider.get());
    }
}