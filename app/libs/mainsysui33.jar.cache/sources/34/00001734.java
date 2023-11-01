package com.android.systemui.dreams.dagger;

import android.content.res.Resources;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/dagger/DreamModule_ProvidesDreamOnlyEnabledForDockUserFactory.class */
public final class DreamModule_ProvidesDreamOnlyEnabledForDockUserFactory implements Factory<Boolean> {
    public final Provider<Resources> resourcesProvider;

    public DreamModule_ProvidesDreamOnlyEnabledForDockUserFactory(Provider<Resources> provider) {
        this.resourcesProvider = provider;
    }

    public static DreamModule_ProvidesDreamOnlyEnabledForDockUserFactory create(Provider<Resources> provider) {
        return new DreamModule_ProvidesDreamOnlyEnabledForDockUserFactory(provider);
    }

    public static boolean providesDreamOnlyEnabledForDockUser(Resources resources) {
        return DreamModule.providesDreamOnlyEnabledForDockUser(resources);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Boolean m2607get() {
        return Boolean.valueOf(providesDreamOnlyEnabledForDockUser((Resources) this.resourcesProvider.get()));
    }
}