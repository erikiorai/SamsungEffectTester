package com.android.systemui.keyguard.data.repository;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/LightRevealScrimRepositoryImpl_Factory.class */
public final class LightRevealScrimRepositoryImpl_Factory implements Factory<LightRevealScrimRepositoryImpl> {
    public final Provider<Context> contextProvider;
    public final Provider<KeyguardRepository> keyguardRepositoryProvider;

    public LightRevealScrimRepositoryImpl_Factory(Provider<KeyguardRepository> provider, Provider<Context> provider2) {
        this.keyguardRepositoryProvider = provider;
        this.contextProvider = provider2;
    }

    public static LightRevealScrimRepositoryImpl_Factory create(Provider<KeyguardRepository> provider, Provider<Context> provider2) {
        return new LightRevealScrimRepositoryImpl_Factory(provider, provider2);
    }

    public static LightRevealScrimRepositoryImpl newInstance(KeyguardRepository keyguardRepository, Context context) {
        return new LightRevealScrimRepositoryImpl(keyguardRepository, context);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LightRevealScrimRepositoryImpl m2994get() {
        return newInstance((KeyguardRepository) this.keyguardRepositoryProvider.get(), (Context) this.contextProvider.get());
    }
}