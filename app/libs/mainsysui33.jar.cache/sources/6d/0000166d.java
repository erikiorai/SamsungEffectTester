package com.android.systemui.doze;

import com.android.systemui.doze.dagger.DozeComponent;
import com.android.systemui.plugins.PluginManager;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeService_Factory.class */
public final class DozeService_Factory implements Factory<DozeService> {
    public final Provider<DozeComponent.Builder> dozeComponentBuilderProvider;
    public final Provider<PluginManager> pluginManagerProvider;

    public DozeService_Factory(Provider<DozeComponent.Builder> provider, Provider<PluginManager> provider2) {
        this.dozeComponentBuilderProvider = provider;
        this.pluginManagerProvider = provider2;
    }

    public static DozeService_Factory create(Provider<DozeComponent.Builder> provider, Provider<PluginManager> provider2) {
        return new DozeService_Factory(provider, provider2);
    }

    public static DozeService newInstance(DozeComponent.Builder builder, PluginManager pluginManager) {
        return new DozeService(builder, pluginManager);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DozeService m2504get() {
        return newInstance((DozeComponent.Builder) this.dozeComponentBuilderProvider.get(), (PluginManager) this.pluginManagerProvider.get());
    }
}