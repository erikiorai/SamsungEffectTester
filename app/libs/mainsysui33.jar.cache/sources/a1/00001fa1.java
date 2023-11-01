package com.android.systemui.plugins;

import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.util.List;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/plugins/PluginsModule_ProvidesPrivilegedPluginsFactory.class */
public final class PluginsModule_ProvidesPrivilegedPluginsFactory implements Factory<List<String>> {
    private final Provider<Context> contextProvider;

    public PluginsModule_ProvidesPrivilegedPluginsFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static PluginsModule_ProvidesPrivilegedPluginsFactory create(Provider<Context> provider) {
        return new PluginsModule_ProvidesPrivilegedPluginsFactory(provider);
    }

    public static List<String> providesPrivilegedPlugins(Context context) {
        return (List) Preconditions.checkNotNullFromProvides(PluginsModule.providesPrivilegedPlugins(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    public List<String> get() {
        return providesPrivilegedPlugins((Context) this.contextProvider.get());
    }
}