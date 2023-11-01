package com.android.systemui.dagger;

import android.content.Context;
import android.view.LayoutInflater;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProviderLayoutInflaterFactory.class */
public final class FrameworkServicesModule_ProviderLayoutInflaterFactory implements Factory<LayoutInflater> {
    public final Provider<Context> contextProvider;
    public final FrameworkServicesModule module;

    public FrameworkServicesModule_ProviderLayoutInflaterFactory(FrameworkServicesModule frameworkServicesModule, Provider<Context> provider) {
        this.module = frameworkServicesModule;
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProviderLayoutInflaterFactory create(FrameworkServicesModule frameworkServicesModule, Provider<Context> provider) {
        return new FrameworkServicesModule_ProviderLayoutInflaterFactory(frameworkServicesModule, provider);
    }

    public static LayoutInflater providerLayoutInflater(FrameworkServicesModule frameworkServicesModule, Context context) {
        return (LayoutInflater) Preconditions.checkNotNullFromProvides(frameworkServicesModule.providerLayoutInflater(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LayoutInflater m2364get() {
        return providerLayoutInflater(this.module, (Context) this.contextProvider.get());
    }
}