package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.LightRevealScrimInteractor;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/viewmodel/LightRevealScrimViewModel_Factory.class */
public final class LightRevealScrimViewModel_Factory implements Factory<LightRevealScrimViewModel> {
    public final Provider<LightRevealScrimInteractor> interactorProvider;

    public LightRevealScrimViewModel_Factory(Provider<LightRevealScrimInteractor> provider) {
        this.interactorProvider = provider;
    }

    public static LightRevealScrimViewModel_Factory create(Provider<LightRevealScrimInteractor> provider) {
        return new LightRevealScrimViewModel_Factory(provider);
    }

    public static LightRevealScrimViewModel newInstance(LightRevealScrimInteractor lightRevealScrimInteractor) {
        return new LightRevealScrimViewModel(lightRevealScrimInteractor);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LightRevealScrimViewModel m3091get() {
        return newInstance((LightRevealScrimInteractor) this.interactorProvider.get());
    }
}