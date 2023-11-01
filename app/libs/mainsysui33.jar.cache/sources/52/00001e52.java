package com.android.systemui.navigationbar;

import android.view.LayoutInflater;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/NavigationBarModule_ProvideNavigationBarviewFactory.class */
public final class NavigationBarModule_ProvideNavigationBarviewFactory implements Factory<NavigationBarView> {
    public final Provider<NavigationBarFrame> frameProvider;
    public final Provider<LayoutInflater> layoutInflaterProvider;

    public NavigationBarModule_ProvideNavigationBarviewFactory(Provider<LayoutInflater> provider, Provider<NavigationBarFrame> provider2) {
        this.layoutInflaterProvider = provider;
        this.frameProvider = provider2;
    }

    public static NavigationBarModule_ProvideNavigationBarviewFactory create(Provider<LayoutInflater> provider, Provider<NavigationBarFrame> provider2) {
        return new NavigationBarModule_ProvideNavigationBarviewFactory(provider, provider2);
    }

    public static NavigationBarView provideNavigationBarview(LayoutInflater layoutInflater, NavigationBarFrame navigationBarFrame) {
        return (NavigationBarView) Preconditions.checkNotNullFromProvides(NavigationBarModule.provideNavigationBarview(layoutInflater, navigationBarFrame));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public NavigationBarView m3431get() {
        return provideNavigationBarview((LayoutInflater) this.layoutInflaterProvider.get(), (NavigationBarFrame) this.frameProvider.get());
    }
}