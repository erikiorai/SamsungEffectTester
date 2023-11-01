package com.android.systemui.plugins;

import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/plugins/PluginDependencyProvider_Factory.class */
public final class PluginDependencyProvider_Factory implements Factory<PluginDependencyProvider> {
    private final Provider<PluginManager> managerLazyProvider;

    public PluginDependencyProvider_Factory(Provider<PluginManager> provider) {
        this.managerLazyProvider = provider;
    }

    public static PluginDependencyProvider_Factory create(Provider<PluginManager> provider) {
        return new PluginDependencyProvider_Factory(provider);
    }

    public static PluginDependencyProvider newInstance(Lazy<PluginManager> lazy) {
        return new PluginDependencyProvider(lazy);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public PluginDependencyProvider m3578get() {
        return newInstance(DoubleCheck.lazy(this.managerLazyProvider));
    }
}