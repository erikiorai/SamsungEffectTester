package com.android.systemui.dreams.dagger;

import android.content.res.Resources;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/dagger/DreamOverlayModule_ProvidesDreamInComplicationsTranslationYDurationFactory.class */
public final class DreamOverlayModule_ProvidesDreamInComplicationsTranslationYDurationFactory implements Factory<Long> {
    public final Provider<Resources> resourcesProvider;

    public DreamOverlayModule_ProvidesDreamInComplicationsTranslationYDurationFactory(Provider<Resources> provider) {
        this.resourcesProvider = provider;
    }

    public static DreamOverlayModule_ProvidesDreamInComplicationsTranslationYDurationFactory create(Provider<Resources> provider) {
        return new DreamOverlayModule_ProvidesDreamInComplicationsTranslationYDurationFactory(provider);
    }

    public static long providesDreamInComplicationsTranslationYDuration(Resources resources) {
        return DreamOverlayModule.providesDreamInComplicationsTranslationYDuration(resources);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Long m2618get() {
        return Long.valueOf(providesDreamInComplicationsTranslationYDuration((Resources) this.resourcesProvider.get()));
    }
}