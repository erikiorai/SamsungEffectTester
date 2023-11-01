package com.android.systemui.navigationbar;

import android.view.IWindowManager;
import com.android.systemui.statusbar.phone.LightBarTransitionsController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/NavigationBarTransitions_Factory.class */
public final class NavigationBarTransitions_Factory implements Factory<NavigationBarTransitions> {
    public final Provider<LightBarTransitionsController.Factory> lightBarTransitionsControllerFactoryProvider;
    public final Provider<NavigationBarView> viewProvider;
    public final Provider<IWindowManager> windowManagerServiceProvider;

    public NavigationBarTransitions_Factory(Provider<NavigationBarView> provider, Provider<IWindowManager> provider2, Provider<LightBarTransitionsController.Factory> provider3) {
        this.viewProvider = provider;
        this.windowManagerServiceProvider = provider2;
        this.lightBarTransitionsControllerFactoryProvider = provider3;
    }

    public static NavigationBarTransitions_Factory create(Provider<NavigationBarView> provider, Provider<IWindowManager> provider2, Provider<LightBarTransitionsController.Factory> provider3) {
        return new NavigationBarTransitions_Factory(provider, provider2, provider3);
    }

    public static NavigationBarTransitions newInstance(NavigationBarView navigationBarView, IWindowManager iWindowManager, LightBarTransitionsController.Factory factory) {
        return new NavigationBarTransitions(navigationBarView, iWindowManager, factory);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public NavigationBarTransitions m3436get() {
        return newInstance((NavigationBarView) this.viewProvider.get(), (IWindowManager) this.windowManagerServiceProvider.get(), (LightBarTransitionsController.Factory) this.lightBarTransitionsControllerFactoryProvider.get());
    }
}