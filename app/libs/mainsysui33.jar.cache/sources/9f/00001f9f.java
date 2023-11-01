package com.android.systemui.plugins;

import android.content.Context;
import com.android.systemui.shared.plugins.PluginActionManager;
import com.android.systemui.shared.plugins.PluginEnabler;
import com.android.systemui.shared.plugins.PluginPrefs;
import com.android.systemui.shared.system.UncaughtExceptionPreHandlerManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.util.List;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/plugins/PluginsModule_ProvidesPluginManagerFactory.class */
public final class PluginsModule_ProvidesPluginManagerFactory implements Factory<PluginManager> {
    private final Provider<Context> contextProvider;
    private final Provider<Boolean> debugProvider;
    private final Provider<PluginActionManager.Factory> instanceManagerFactoryProvider;
    private final Provider<PluginEnabler> pluginEnablerProvider;
    private final Provider<PluginPrefs> pluginPrefsProvider;
    private final Provider<UncaughtExceptionPreHandlerManager> preHandlerManagerProvider;
    private final Provider<List<String>> privilegedPluginsProvider;

    public PluginsModule_ProvidesPluginManagerFactory(Provider<Context> provider, Provider<PluginActionManager.Factory> provider2, Provider<Boolean> provider3, Provider<UncaughtExceptionPreHandlerManager> provider4, Provider<PluginEnabler> provider5, Provider<PluginPrefs> provider6, Provider<List<String>> provider7) {
        this.contextProvider = provider;
        this.instanceManagerFactoryProvider = provider2;
        this.debugProvider = provider3;
        this.preHandlerManagerProvider = provider4;
        this.pluginEnablerProvider = provider5;
        this.pluginPrefsProvider = provider6;
        this.privilegedPluginsProvider = provider7;
    }

    public static PluginsModule_ProvidesPluginManagerFactory create(Provider<Context> provider, Provider<PluginActionManager.Factory> provider2, Provider<Boolean> provider3, Provider<UncaughtExceptionPreHandlerManager> provider4, Provider<PluginEnabler> provider5, Provider<PluginPrefs> provider6, Provider<List<String>> provider7) {
        return new PluginsModule_ProvidesPluginManagerFactory(provider, provider2, provider3, provider4, provider5, provider6, provider7);
    }

    public static PluginManager providesPluginManager(Context context, PluginActionManager.Factory factory, boolean z, UncaughtExceptionPreHandlerManager uncaughtExceptionPreHandlerManager, PluginEnabler pluginEnabler, PluginPrefs pluginPrefs, List<String> list) {
        return (PluginManager) Preconditions.checkNotNullFromProvides(PluginsModule.providesPluginManager(context, factory, z, uncaughtExceptionPreHandlerManager, pluginEnabler, pluginPrefs, list));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public PluginManager m3586get() {
        return providesPluginManager((Context) this.contextProvider.get(), (PluginActionManager.Factory) this.instanceManagerFactoryProvider.get(), ((Boolean) this.debugProvider.get()).booleanValue(), (UncaughtExceptionPreHandlerManager) this.preHandlerManagerProvider.get(), (PluginEnabler) this.pluginEnablerProvider.get(), (PluginPrefs) this.pluginPrefsProvider.get(), (List) this.privilegedPluginsProvider.get());
    }
}