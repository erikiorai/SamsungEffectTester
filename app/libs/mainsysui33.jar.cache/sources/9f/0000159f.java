package com.android.systemui.dagger;

import android.content.Context;
import android.content.res.Resources;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideResourcesFactory.class */
public final class FrameworkServicesModule_ProvideResourcesFactory implements Factory<Resources> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideResourcesFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideResourcesFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideResourcesFactory(provider);
    }

    public static Resources provideResources(Context context) {
        return (Resources) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideResources(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Resources m2345get() {
        return provideResources((Context) this.contextProvider.get());
    }
}