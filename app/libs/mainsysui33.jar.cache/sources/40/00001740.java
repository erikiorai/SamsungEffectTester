package com.android.systemui.dreams.dagger;

import android.content.res.Resources;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/dagger/DreamOverlayModule_ProvidesDreamInBlurAnimationDurationFactory.class */
public final class DreamOverlayModule_ProvidesDreamInBlurAnimationDurationFactory implements Factory<Long> {
    public final Provider<Resources> resourcesProvider;

    public DreamOverlayModule_ProvidesDreamInBlurAnimationDurationFactory(Provider<Resources> provider) {
        this.resourcesProvider = provider;
    }

    public static DreamOverlayModule_ProvidesDreamInBlurAnimationDurationFactory create(Provider<Resources> provider) {
        return new DreamOverlayModule_ProvidesDreamInBlurAnimationDurationFactory(provider);
    }

    public static long providesDreamInBlurAnimationDuration(Resources resources) {
        return DreamOverlayModule.providesDreamInBlurAnimationDuration(resources);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Long m2616get() {
        return Long.valueOf(providesDreamInBlurAnimationDuration((Resources) this.resourcesProvider.get()));
    }
}