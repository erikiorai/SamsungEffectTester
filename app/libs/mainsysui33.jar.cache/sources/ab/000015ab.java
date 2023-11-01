package com.android.systemui.dagger;

import android.app.trust.TrustManager;
import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideTrustManagerFactory.class */
public final class FrameworkServicesModule_ProvideTrustManagerFactory implements Factory<TrustManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideTrustManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideTrustManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideTrustManagerFactory(provider);
    }

    public static TrustManager provideTrustManager(Context context) {
        return (TrustManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideTrustManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public TrustManager m2357get() {
        return provideTrustManager((Context) this.contextProvider.get());
    }
}