package com.android.systemui.dreams.complication.dagger;

import android.content.res.Resources;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/complication/dagger/ComplicationHostViewModule_ProvidesComplicationsFadeInDurationFactory.class */
public final class ComplicationHostViewModule_ProvidesComplicationsFadeInDurationFactory implements Factory<Integer> {
    public final Provider<Resources> resourcesProvider;

    public ComplicationHostViewModule_ProvidesComplicationsFadeInDurationFactory(Provider<Resources> provider) {
        this.resourcesProvider = provider;
    }

    public static ComplicationHostViewModule_ProvidesComplicationsFadeInDurationFactory create(Provider<Resources> provider) {
        return new ComplicationHostViewModule_ProvidesComplicationsFadeInDurationFactory(provider);
    }

    public static int providesComplicationsFadeInDuration(Resources resources) {
        return ComplicationHostViewModule.providesComplicationsFadeInDuration(resources);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Integer m2601get() {
        return Integer.valueOf(providesComplicationsFadeInDuration((Resources) this.resourcesProvider.get()));
    }
}