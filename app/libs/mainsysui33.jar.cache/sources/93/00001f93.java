package com.android.systemui.plugins;

import android.content.Context;
import android.content.pm.PackageManager;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/plugins/PluginEnablerImpl_Factory.class */
public final class PluginEnablerImpl_Factory implements Factory<PluginEnablerImpl> {
    private final Provider<Context> contextProvider;
    private final Provider<PackageManager> pmProvider;

    public PluginEnablerImpl_Factory(Provider<Context> provider, Provider<PackageManager> provider2) {
        this.contextProvider = provider;
        this.pmProvider = provider2;
    }

    public static PluginEnablerImpl_Factory create(Provider<Context> provider, Provider<PackageManager> provider2) {
        return new PluginEnablerImpl_Factory(provider, provider2);
    }

    public static PluginEnablerImpl newInstance(Context context, PackageManager packageManager) {
        return new PluginEnablerImpl(context, packageManager);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public PluginEnablerImpl m3579get() {
        return newInstance((Context) this.contextProvider.get(), (PackageManager) this.pmProvider.get());
    }
}