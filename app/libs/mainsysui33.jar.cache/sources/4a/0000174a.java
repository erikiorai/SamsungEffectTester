package com.android.systemui.dreams.dagger;

import android.content.res.Resources;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/dagger/DreamOverlayModule_ProvidesMaxBurnInOffsetFactory.class */
public final class DreamOverlayModule_ProvidesMaxBurnInOffsetFactory implements Factory<Integer> {
    public final Provider<Resources> resourcesProvider;

    public DreamOverlayModule_ProvidesMaxBurnInOffsetFactory(Provider<Resources> provider) {
        this.resourcesProvider = provider;
    }

    public static DreamOverlayModule_ProvidesMaxBurnInOffsetFactory create(Provider<Resources> provider) {
        return new DreamOverlayModule_ProvidesMaxBurnInOffsetFactory(provider);
    }

    public static int providesMaxBurnInOffset(Resources resources) {
        return DreamOverlayModule.providesMaxBurnInOffset(resources);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Integer m2626get() {
        return Integer.valueOf(providesMaxBurnInOffset((Resources) this.resourcesProvider.get()));
    }
}