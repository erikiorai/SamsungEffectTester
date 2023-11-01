package com.android.systemui.dagger;

import android.content.Context;
import android.hardware.display.ColorDisplayManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideColorDisplayManagerFactory.class */
public final class FrameworkServicesModule_ProvideColorDisplayManagerFactory implements Factory<ColorDisplayManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideColorDisplayManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideColorDisplayManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideColorDisplayManagerFactory(provider);
    }

    public static ColorDisplayManager provideColorDisplayManager(Context context) {
        return (ColorDisplayManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideColorDisplayManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ColorDisplayManager m2278get() {
        return provideColorDisplayManager((Context) this.contextProvider.get());
    }
}