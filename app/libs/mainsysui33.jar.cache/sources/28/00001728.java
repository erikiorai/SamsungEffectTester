package com.android.systemui.dreams.complication.dagger;

import android.content.res.Resources;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/dagger/ComplicationHostViewModule_ProvidesComplicationsFadeOutDelayFactory.class */
public final class ComplicationHostViewModule_ProvidesComplicationsFadeOutDelayFactory implements Factory<Integer> {
    public final Provider<Resources> resourcesProvider;

    public ComplicationHostViewModule_ProvidesComplicationsFadeOutDelayFactory(Provider<Resources> provider) {
        this.resourcesProvider = provider;
    }

    public static ComplicationHostViewModule_ProvidesComplicationsFadeOutDelayFactory create(Provider<Resources> provider) {
        return new ComplicationHostViewModule_ProvidesComplicationsFadeOutDelayFactory(provider);
    }

    public static int providesComplicationsFadeOutDelay(Resources resources) {
        return ComplicationHostViewModule.providesComplicationsFadeOutDelay(resources);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Integer m2602get() {
        return Integer.valueOf(providesComplicationsFadeOutDelay((Resources) this.resourcesProvider.get()));
    }
}