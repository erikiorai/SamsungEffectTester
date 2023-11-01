package com.android.systemui.dreams.dagger;

import android.content.res.Resources;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/dagger/DreamModule_ProvidesDreamSupportedFactory.class */
public final class DreamModule_ProvidesDreamSupportedFactory implements Factory<Boolean> {
    public final Provider<Resources> resourcesProvider;

    public DreamModule_ProvidesDreamSupportedFactory(Provider<Resources> provider) {
        this.resourcesProvider = provider;
    }

    public static DreamModule_ProvidesDreamSupportedFactory create(Provider<Resources> provider) {
        return new DreamModule_ProvidesDreamSupportedFactory(provider);
    }

    public static boolean providesDreamSupported(Resources resources) {
        return DreamModule.providesDreamSupported(resources);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Boolean m2613get() {
        return Boolean.valueOf(providesDreamSupported((Resources) this.resourcesProvider.get()));
    }
}