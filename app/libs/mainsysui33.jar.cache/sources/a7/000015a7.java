package com.android.systemui.dagger;

import android.content.Context;
import android.telephony.SubscriptionManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideSubscriptionManagerFactory.class */
public final class FrameworkServicesModule_ProvideSubscriptionManagerFactory implements Factory<SubscriptionManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideSubscriptionManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideSubscriptionManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideSubscriptionManagerFactory(provider);
    }

    public static SubscriptionManager provideSubscriptionManager(Context context) {
        return (SubscriptionManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideSubscriptionManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public SubscriptionManager m2353get() {
        return provideSubscriptionManager((Context) this.contextProvider.get());
    }
}