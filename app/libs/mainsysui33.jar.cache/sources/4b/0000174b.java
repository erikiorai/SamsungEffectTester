package com.android.systemui.dreams.dagger;

import android.content.res.Resources;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/dagger/DreamOverlayModule_ProvidesMillisUntilFullJitterFactory.class */
public final class DreamOverlayModule_ProvidesMillisUntilFullJitterFactory implements Factory<Long> {
    public final Provider<Resources> resourcesProvider;

    public DreamOverlayModule_ProvidesMillisUntilFullJitterFactory(Provider<Resources> provider) {
        this.resourcesProvider = provider;
    }

    public static DreamOverlayModule_ProvidesMillisUntilFullJitterFactory create(Provider<Resources> provider) {
        return new DreamOverlayModule_ProvidesMillisUntilFullJitterFactory(provider);
    }

    public static long providesMillisUntilFullJitter(Resources resources) {
        return DreamOverlayModule.providesMillisUntilFullJitter(resources);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Long m2627get() {
        return Long.valueOf(providesMillisUntilFullJitter((Resources) this.resourcesProvider.get()));
    }
}