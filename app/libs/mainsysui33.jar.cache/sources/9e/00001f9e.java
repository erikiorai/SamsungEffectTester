package com.android.systemui.plugins;

import com.android.systemui.shared.plugins.PluginInstance;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.util.List;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/plugins/PluginsModule_ProvidesPluginInstanceFactoryFactory.class */
public final class PluginsModule_ProvidesPluginInstanceFactoryFactory implements Factory<PluginInstance.Factory> {
    private final Provider<Boolean> isDebugProvider;
    private final Provider<List<String>> privilegedPluginsProvider;

    public PluginsModule_ProvidesPluginInstanceFactoryFactory(Provider<List<String>> provider, Provider<Boolean> provider2) {
        this.privilegedPluginsProvider = provider;
        this.isDebugProvider = provider2;
    }

    public static PluginsModule_ProvidesPluginInstanceFactoryFactory create(Provider<List<String>> provider, Provider<Boolean> provider2) {
        return new PluginsModule_ProvidesPluginInstanceFactoryFactory(provider, provider2);
    }

    public static PluginInstance.Factory providesPluginInstanceFactory(List<String> list, boolean z) {
        return (PluginInstance.Factory) Preconditions.checkNotNullFromProvides(PluginsModule.providesPluginInstanceFactory(list, z));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public PluginInstance.Factory m3585get() {
        return providesPluginInstanceFactory((List) this.privilegedPluginsProvider.get(), ((Boolean) this.isDebugProvider.get()).booleanValue());
    }
}