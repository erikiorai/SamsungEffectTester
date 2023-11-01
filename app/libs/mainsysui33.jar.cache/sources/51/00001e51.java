package com.android.systemui.navigationbar;

import android.view.LayoutInflater;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/NavigationBarModule_ProvideNavigationBarFrameFactory.class */
public final class NavigationBarModule_ProvideNavigationBarFrameFactory implements Factory<NavigationBarFrame> {
    public final Provider<LayoutInflater> layoutInflaterProvider;

    public NavigationBarModule_ProvideNavigationBarFrameFactory(Provider<LayoutInflater> provider) {
        this.layoutInflaterProvider = provider;
    }

    public static NavigationBarModule_ProvideNavigationBarFrameFactory create(Provider<LayoutInflater> provider) {
        return new NavigationBarModule_ProvideNavigationBarFrameFactory(provider);
    }

    public static NavigationBarFrame provideNavigationBarFrame(LayoutInflater layoutInflater) {
        return (NavigationBarFrame) Preconditions.checkNotNullFromProvides(NavigationBarModule.provideNavigationBarFrame(layoutInflater));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public NavigationBarFrame m3430get() {
        return provideNavigationBarFrame((LayoutInflater) this.layoutInflaterProvider.get());
    }
}