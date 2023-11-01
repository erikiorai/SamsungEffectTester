package com.android.systemui.dreams.dagger;

import android.content.res.Resources;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/dagger/DreamOverlayModule_ProvidesBurnInProtectionUpdateIntervalFactory.class */
public final class DreamOverlayModule_ProvidesBurnInProtectionUpdateIntervalFactory implements Factory<Long> {
    public final Provider<Resources> resourcesProvider;

    public DreamOverlayModule_ProvidesBurnInProtectionUpdateIntervalFactory(Provider<Resources> provider) {
        this.resourcesProvider = provider;
    }

    public static DreamOverlayModule_ProvidesBurnInProtectionUpdateIntervalFactory create(Provider<Resources> provider) {
        return new DreamOverlayModule_ProvidesBurnInProtectionUpdateIntervalFactory(provider);
    }

    public static long providesBurnInProtectionUpdateInterval(Resources resources) {
        return DreamOverlayModule.providesBurnInProtectionUpdateInterval(resources);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Long m2614get() {
        return Long.valueOf(providesBurnInProtectionUpdateInterval((Resources) this.resourcesProvider.get()));
    }
}