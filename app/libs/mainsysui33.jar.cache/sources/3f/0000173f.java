package com.android.systemui.dreams.dagger;

import android.content.res.Resources;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/dagger/DreamOverlayModule_ProvidesDreamBlurRadiusFactory.class */
public final class DreamOverlayModule_ProvidesDreamBlurRadiusFactory implements Factory<Integer> {
    public final Provider<Resources> resourcesProvider;

    public DreamOverlayModule_ProvidesDreamBlurRadiusFactory(Provider<Resources> provider) {
        this.resourcesProvider = provider;
    }

    public static DreamOverlayModule_ProvidesDreamBlurRadiusFactory create(Provider<Resources> provider) {
        return new DreamOverlayModule_ProvidesDreamBlurRadiusFactory(provider);
    }

    public static int providesDreamBlurRadius(Resources resources) {
        return DreamOverlayModule.providesDreamBlurRadius(resources);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Integer m2615get() {
        return Integer.valueOf(providesDreamBlurRadius((Resources) this.resourcesProvider.get()));
    }
}