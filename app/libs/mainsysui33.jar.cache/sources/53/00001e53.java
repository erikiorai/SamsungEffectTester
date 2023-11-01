package com.android.systemui.navigationbar;

import android.content.Context;
import android.view.WindowManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/NavigationBarModule_ProvideWindowManagerFactory.class */
public final class NavigationBarModule_ProvideWindowManagerFactory implements Factory<WindowManager> {
    public final Provider<Context> contextProvider;

    public NavigationBarModule_ProvideWindowManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static NavigationBarModule_ProvideWindowManagerFactory create(Provider<Context> provider) {
        return new NavigationBarModule_ProvideWindowManagerFactory(provider);
    }

    public static WindowManager provideWindowManager(Context context) {
        return (WindowManager) Preconditions.checkNotNullFromProvides(NavigationBarModule.provideWindowManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public WindowManager m3432get() {
        return provideWindowManager((Context) this.contextProvider.get());
    }
}