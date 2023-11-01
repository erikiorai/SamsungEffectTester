package com.android.systemui.dagger;

import android.content.Context;
import android.net.wifi.WifiManager;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideWifiManagerFactory.class */
public final class FrameworkServicesModule_ProvideWifiManagerFactory implements Factory<WifiManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideWifiManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideWifiManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideWifiManagerFactory(provider);
    }

    public static WifiManager provideWifiManager(Context context) {
        return FrameworkServicesModule.provideWifiManager(context);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public WifiManager m2362get() {
        return provideWifiManager((Context) this.contextProvider.get());
    }
}