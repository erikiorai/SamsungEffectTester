package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/viewmodel/OccludedToLockscreenTransitionViewModel_Factory.class */
public final class OccludedToLockscreenTransitionViewModel_Factory implements Factory<OccludedToLockscreenTransitionViewModel> {
    public final Provider<KeyguardTransitionInteractor> interactorProvider;

    public OccludedToLockscreenTransitionViewModel_Factory(Provider<KeyguardTransitionInteractor> provider) {
        this.interactorProvider = provider;
    }

    public static OccludedToLockscreenTransitionViewModel_Factory create(Provider<KeyguardTransitionInteractor> provider) {
        return new OccludedToLockscreenTransitionViewModel_Factory(provider);
    }

    public static OccludedToLockscreenTransitionViewModel newInstance(KeyguardTransitionInteractor keyguardTransitionInteractor) {
        return new OccludedToLockscreenTransitionViewModel(keyguardTransitionInteractor);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public OccludedToLockscreenTransitionViewModel m3093get() {
        return newInstance((KeyguardTransitionInteractor) this.interactorProvider.get());
    }
}