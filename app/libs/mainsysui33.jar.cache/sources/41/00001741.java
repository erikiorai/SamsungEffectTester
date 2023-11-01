package com.android.systemui.dreams.dagger;

import android.content.res.Resources;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/dagger/DreamOverlayModule_ProvidesDreamInComplicationsAnimationDurationFactory.class */
public final class DreamOverlayModule_ProvidesDreamInComplicationsAnimationDurationFactory implements Factory<Long> {
    public final Provider<Resources> resourcesProvider;

    public DreamOverlayModule_ProvidesDreamInComplicationsAnimationDurationFactory(Provider<Resources> provider) {
        this.resourcesProvider = provider;
    }

    public static DreamOverlayModule_ProvidesDreamInComplicationsAnimationDurationFactory create(Provider<Resources> provider) {
        return new DreamOverlayModule_ProvidesDreamInComplicationsAnimationDurationFactory(provider);
    }

    public static long providesDreamInComplicationsAnimationDuration(Resources resources) {
        return DreamOverlayModule.providesDreamInComplicationsAnimationDuration(resources);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Long m2617get() {
        return Long.valueOf(providesDreamInComplicationsAnimationDuration((Resources) this.resourcesProvider.get()));
    }
}