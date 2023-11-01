package com.android.systemui.dreams.dagger;

import android.view.LayoutInflater;
import com.android.systemui.dreams.DreamOverlayContainerView;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/dagger/DreamOverlayModule_ProvidesDreamOverlayContainerViewFactory.class */
public final class DreamOverlayModule_ProvidesDreamOverlayContainerViewFactory implements Factory<DreamOverlayContainerView> {
    public final Provider<LayoutInflater> layoutInflaterProvider;

    public DreamOverlayModule_ProvidesDreamOverlayContainerViewFactory(Provider<LayoutInflater> provider) {
        this.layoutInflaterProvider = provider;
    }

    public static DreamOverlayModule_ProvidesDreamOverlayContainerViewFactory create(Provider<LayoutInflater> provider) {
        return new DreamOverlayModule_ProvidesDreamOverlayContainerViewFactory(provider);
    }

    public static DreamOverlayContainerView providesDreamOverlayContainerView(LayoutInflater layoutInflater) {
        return (DreamOverlayContainerView) Preconditions.checkNotNullFromProvides(DreamOverlayModule.providesDreamOverlayContainerView(layoutInflater));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DreamOverlayContainerView m2620get() {
        return providesDreamOverlayContainerView((LayoutInflater) this.layoutInflaterProvider.get());
    }
}