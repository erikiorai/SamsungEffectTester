package com.android.systemui.dagger;

import android.content.Context;
import android.net.NetworkScoreManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideNetworkScoreManagerFactory.class */
public final class FrameworkServicesModule_ProvideNetworkScoreManagerFactory implements Factory<NetworkScoreManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideNetworkScoreManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideNetworkScoreManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideNetworkScoreManagerFactory(provider);
    }

    public static NetworkScoreManager provideNetworkScoreManager(Context context) {
        return (NetworkScoreManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideNetworkScoreManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public NetworkScoreManager m2332get() {
        return provideNetworkScoreManager((Context) this.contextProvider.get());
    }
}