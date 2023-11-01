package com.android.systemui.dreams.complication.dagger;

import android.content.res.Resources;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/dagger/ComplicationHostViewModule_ProvidesComplicationsRestoreTimeoutFactory.class */
public final class ComplicationHostViewModule_ProvidesComplicationsRestoreTimeoutFactory implements Factory<Integer> {
    public final Provider<Resources> resourcesProvider;

    public ComplicationHostViewModule_ProvidesComplicationsRestoreTimeoutFactory(Provider<Resources> provider) {
        this.resourcesProvider = provider;
    }

    public static ComplicationHostViewModule_ProvidesComplicationsRestoreTimeoutFactory create(Provider<Resources> provider) {
        return new ComplicationHostViewModule_ProvidesComplicationsRestoreTimeoutFactory(provider);
    }

    public static int providesComplicationsRestoreTimeout(Resources resources) {
        return ComplicationHostViewModule.providesComplicationsRestoreTimeout(resources);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Integer m2604get() {
        return Integer.valueOf(providesComplicationsRestoreTimeout((Resources) this.resourcesProvider.get()));
    }
}