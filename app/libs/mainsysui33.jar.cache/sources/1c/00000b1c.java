package com.android.dream.lowlight.dagger;

import android.content.ComponentName;
import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/dream/lowlight/dagger/LowLightDreamModule_ProvidesLowLightDreamComponentFactory.class */
public final class LowLightDreamModule_ProvidesLowLightDreamComponentFactory implements Factory<ComponentName> {
    public final Provider<Context> contextProvider;

    public LowLightDreamModule_ProvidesLowLightDreamComponentFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static LowLightDreamModule_ProvidesLowLightDreamComponentFactory create(Provider<Context> provider) {
        return new LowLightDreamModule_ProvidesLowLightDreamComponentFactory(provider);
    }

    public static ComponentName providesLowLightDreamComponent(Context context) {
        return LowLightDreamModule.providesLowLightDreamComponent(context);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ComponentName m513get() {
        return providesLowLightDreamComponent((Context) this.contextProvider.get());
    }
}