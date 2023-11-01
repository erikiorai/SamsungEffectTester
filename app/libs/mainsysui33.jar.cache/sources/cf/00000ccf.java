package com.android.keyguard.dagger;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.view.LayoutInflater;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.shared.clocks.ClockRegistry;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/keyguard/dagger/ClockRegistryModule_GetClockRegistryFactory.class */
public final class ClockRegistryModule_GetClockRegistryFactory implements Factory<ClockRegistry> {
    public final Provider<Context> contextProvider;
    public final Provider<FeatureFlags> featureFlagsProvider;
    public final Provider<Handler> handlerProvider;
    public final Provider<LayoutInflater> layoutInflaterProvider;
    public final Provider<PluginManager> pluginManagerProvider;
    public final Provider<Resources> resourcesProvider;

    public ClockRegistryModule_GetClockRegistryFactory(Provider<Context> provider, Provider<PluginManager> provider2, Provider<Handler> provider3, Provider<FeatureFlags> provider4, Provider<Resources> provider5, Provider<LayoutInflater> provider6) {
        this.contextProvider = provider;
        this.pluginManagerProvider = provider2;
        this.handlerProvider = provider3;
        this.featureFlagsProvider = provider4;
        this.resourcesProvider = provider5;
        this.layoutInflaterProvider = provider6;
    }

    public static ClockRegistryModule_GetClockRegistryFactory create(Provider<Context> provider, Provider<PluginManager> provider2, Provider<Handler> provider3, Provider<FeatureFlags> provider4, Provider<Resources> provider5, Provider<LayoutInflater> provider6) {
        return new ClockRegistryModule_GetClockRegistryFactory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    public static ClockRegistry getClockRegistry(Context context, PluginManager pluginManager, Handler handler, FeatureFlags featureFlags, Resources resources, LayoutInflater layoutInflater) {
        return (ClockRegistry) Preconditions.checkNotNullFromProvides(ClockRegistryModule.getClockRegistry(context, pluginManager, handler, featureFlags, resources, layoutInflater));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ClockRegistry m836get() {
        return getClockRegistry((Context) this.contextProvider.get(), (PluginManager) this.pluginManagerProvider.get(), (Handler) this.handlerProvider.get(), (FeatureFlags) this.featureFlagsProvider.get(), (Resources) this.resourcesProvider.get(), (LayoutInflater) this.layoutInflaterProvider.get());
    }
}