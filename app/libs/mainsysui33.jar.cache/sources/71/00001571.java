package com.android.systemui.dagger;

import android.content.Context;
import android.hardware.display.DisplayManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideDisplayManagerFactory.class */
public final class FrameworkServicesModule_ProvideDisplayManagerFactory implements Factory<DisplayManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideDisplayManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideDisplayManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideDisplayManagerFactory(provider);
    }

    public static DisplayManager provideDisplayManager(Context context) {
        return (DisplayManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideDisplayManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DisplayManager m2287get() {
        return provideDisplayManager((Context) this.contextProvider.get());
    }
}