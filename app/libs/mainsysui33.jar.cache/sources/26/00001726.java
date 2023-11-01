package com.android.systemui.dreams.complication.dagger;

import android.content.res.Resources;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/dagger/ComplicationHostViewModule_ProvidesComplicationPaddingFactory.class */
public final class ComplicationHostViewModule_ProvidesComplicationPaddingFactory implements Factory<Integer> {
    public final Provider<Resources> resourcesProvider;

    public ComplicationHostViewModule_ProvidesComplicationPaddingFactory(Provider<Resources> provider) {
        this.resourcesProvider = provider;
    }

    public static ComplicationHostViewModule_ProvidesComplicationPaddingFactory create(Provider<Resources> provider) {
        return new ComplicationHostViewModule_ProvidesComplicationPaddingFactory(provider);
    }

    public static int providesComplicationPadding(Resources resources) {
        return ComplicationHostViewModule.providesComplicationPadding(resources);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Integer m2600get() {
        return Integer.valueOf(providesComplicationPadding((Resources) this.resourcesProvider.get()));
    }
}