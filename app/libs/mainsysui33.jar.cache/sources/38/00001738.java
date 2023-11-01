package com.android.systemui.dreams.dagger;

import android.content.ComponentName;
import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/dagger/DreamModule_ProvidesDreamOverlayServiceFactory.class */
public final class DreamModule_ProvidesDreamOverlayServiceFactory implements Factory<ComponentName> {
    public final Provider<Context> contextProvider;

    public DreamModule_ProvidesDreamOverlayServiceFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static DreamModule_ProvidesDreamOverlayServiceFactory create(Provider<Context> provider) {
        return new DreamModule_ProvidesDreamOverlayServiceFactory(provider);
    }

    public static ComponentName providesDreamOverlayService(Context context) {
        return (ComponentName) Preconditions.checkNotNullFromProvides(DreamModule.providesDreamOverlayService(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ComponentName m2612get() {
        return providesDreamOverlayService((Context) this.contextProvider.get());
    }
}