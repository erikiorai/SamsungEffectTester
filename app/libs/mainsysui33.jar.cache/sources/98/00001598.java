package com.android.systemui.dagger;

import android.content.Context;
import android.content.om.OverlayManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideOverlayManagerFactory.class */
public final class FrameworkServicesModule_ProvideOverlayManagerFactory implements Factory<OverlayManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideOverlayManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideOverlayManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideOverlayManagerFactory(provider);
    }

    public static OverlayManager provideOverlayManager(Context context) {
        return (OverlayManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideOverlayManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public OverlayManager m2337get() {
        return provideOverlayManager((Context) this.contextProvider.get());
    }
}