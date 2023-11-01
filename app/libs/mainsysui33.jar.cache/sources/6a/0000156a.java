package com.android.systemui.dagger;

import android.content.Context;
import android.net.ConnectivityManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideConnectivityManagagerFactory.class */
public final class FrameworkServicesModule_ProvideConnectivityManagagerFactory implements Factory<ConnectivityManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideConnectivityManagagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideConnectivityManagagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideConnectivityManagagerFactory(provider);
    }

    public static ConnectivityManager provideConnectivityManagager(Context context) {
        return (ConnectivityManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideConnectivityManagager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ConnectivityManager m2279get() {
        return provideConnectivityManagager((Context) this.contextProvider.get());
    }
}