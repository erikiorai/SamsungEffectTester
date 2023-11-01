package com.android.systemui.dreams.dagger;

import android.content.res.Resources;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/dagger/DreamOverlayModule_ProvidesDreamInComplicationsTranslationYFactory.class */
public final class DreamOverlayModule_ProvidesDreamInComplicationsTranslationYFactory implements Factory<Integer> {
    public final Provider<Resources> resourcesProvider;

    public DreamOverlayModule_ProvidesDreamInComplicationsTranslationYFactory(Provider<Resources> provider) {
        this.resourcesProvider = provider;
    }

    public static DreamOverlayModule_ProvidesDreamInComplicationsTranslationYFactory create(Provider<Resources> provider) {
        return new DreamOverlayModule_ProvidesDreamInComplicationsTranslationYFactory(provider);
    }

    public static int providesDreamInComplicationsTranslationY(Resources resources) {
        return DreamOverlayModule.providesDreamInComplicationsTranslationY(resources);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Integer m2619get() {
        return Integer.valueOf(providesDreamInComplicationsTranslationY((Resources) this.resourcesProvider.get()));
    }
}