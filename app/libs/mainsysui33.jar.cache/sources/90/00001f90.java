package com.android.systemui.plugins;

import android.util.ArrayMap;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.PluginDependency;
import dagger.Lazy;

/* loaded from: mainsysui33.jar:com/android/systemui/plugins/PluginDependencyProvider.class */
public class PluginDependencyProvider extends PluginDependency.DependencyProvider {
    private final ArrayMap<Class<?>, Object> mDependencies = new ArrayMap<>();
    private final Lazy<PluginManager> mManagerLazy;

    public PluginDependencyProvider(Lazy<PluginManager> lazy) {
        this.mManagerLazy = lazy;
        PluginDependency.sProvider = this;
    }

    /* JADX DEBUG: Multi-variable search result rejected for r4v0, resolved type: com.android.systemui.plugins.PluginDependencyProvider */
    /* JADX WARN: Multi-variable type inference failed */
    public <T> void allowPluginDependency(Class<T> cls) {
        allowPluginDependency(cls, Dependency.get(cls));
    }

    public <T> void allowPluginDependency(Class<T> cls, T t) {
        synchronized (this.mDependencies) {
            this.mDependencies.put(cls, t);
        }
    }

    @Override // com.android.systemui.plugins.PluginDependency.DependencyProvider
    public <T> T get(Plugin plugin, Class<T> cls) {
        T t;
        if (!((PluginManager) this.mManagerLazy.get()).dependsOn(plugin, cls)) {
            throw new IllegalArgumentException(plugin.getClass() + " does not depend on " + cls);
        }
        synchronized (this.mDependencies) {
            if (!this.mDependencies.containsKey(cls)) {
                throw new IllegalArgumentException("Unknown dependency " + cls);
            }
            t = (T) this.mDependencies.get(cls);
        }
        return t;
    }
}