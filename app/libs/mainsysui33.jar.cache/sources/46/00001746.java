package com.android.systemui.dreams.dagger;

import com.android.systemui.dreams.DreamOverlayContainerView;
import com.android.systemui.dreams.DreamOverlayStatusBarView;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/dagger/DreamOverlayModule_ProvidesDreamOverlayStatusBarViewFactory.class */
public final class DreamOverlayModule_ProvidesDreamOverlayStatusBarViewFactory implements Factory<DreamOverlayStatusBarView> {
    public final Provider<DreamOverlayContainerView> viewProvider;

    public DreamOverlayModule_ProvidesDreamOverlayStatusBarViewFactory(Provider<DreamOverlayContainerView> provider) {
        this.viewProvider = provider;
    }

    public static DreamOverlayModule_ProvidesDreamOverlayStatusBarViewFactory create(Provider<DreamOverlayContainerView> provider) {
        return new DreamOverlayModule_ProvidesDreamOverlayStatusBarViewFactory(provider);
    }

    public static DreamOverlayStatusBarView providesDreamOverlayStatusBarView(DreamOverlayContainerView dreamOverlayContainerView) {
        return (DreamOverlayStatusBarView) Preconditions.checkNotNullFromProvides(DreamOverlayModule.providesDreamOverlayStatusBarView(dreamOverlayContainerView));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DreamOverlayStatusBarView m2622get() {
        return providesDreamOverlayStatusBarView((DreamOverlayContainerView) this.viewProvider.get());
    }
}