package com.android.systemui.dagger;

import android.content.Context;
import android.view.WindowManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideWindowManagerFactory.class */
public final class FrameworkServicesModule_ProvideWindowManagerFactory implements Factory<WindowManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideWindowManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideWindowManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideWindowManagerFactory(provider);
    }

    public static WindowManager provideWindowManager(Context context) {
        return (WindowManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideWindowManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public WindowManager m2363get() {
        return provideWindowManager((Context) this.contextProvider.get());
    }
}