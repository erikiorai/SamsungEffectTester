package com.android.systemui.dreams.dagger;

import android.view.ViewGroup;
import com.android.systemui.dreams.DreamOverlayContainerView;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/dagger/DreamOverlayModule_ProvidesDreamOverlayContentViewFactory.class */
public final class DreamOverlayModule_ProvidesDreamOverlayContentViewFactory implements Factory<ViewGroup> {
    public final Provider<DreamOverlayContainerView> viewProvider;

    public DreamOverlayModule_ProvidesDreamOverlayContentViewFactory(Provider<DreamOverlayContainerView> provider) {
        this.viewProvider = provider;
    }

    public static DreamOverlayModule_ProvidesDreamOverlayContentViewFactory create(Provider<DreamOverlayContainerView> provider) {
        return new DreamOverlayModule_ProvidesDreamOverlayContentViewFactory(provider);
    }

    public static ViewGroup providesDreamOverlayContentView(DreamOverlayContainerView dreamOverlayContainerView) {
        return (ViewGroup) Preconditions.checkNotNullFromProvides(DreamOverlayModule.providesDreamOverlayContentView(dreamOverlayContainerView));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ViewGroup m2621get() {
        return providesDreamOverlayContentView((DreamOverlayContainerView) this.viewProvider.get());
    }
}