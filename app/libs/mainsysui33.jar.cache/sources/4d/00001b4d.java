package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/viewmodel/DreamingToLockscreenTransitionViewModel_Factory.class */
public final class DreamingToLockscreenTransitionViewModel_Factory implements Factory<DreamingToLockscreenTransitionViewModel> {
    public final Provider<KeyguardTransitionInteractor> interactorProvider;

    public DreamingToLockscreenTransitionViewModel_Factory(Provider<KeyguardTransitionInteractor> provider) {
        this.interactorProvider = provider;
    }

    public static DreamingToLockscreenTransitionViewModel_Factory create(Provider<KeyguardTransitionInteractor> provider) {
        return new DreamingToLockscreenTransitionViewModel_Factory(provider);
    }

    public static DreamingToLockscreenTransitionViewModel newInstance(KeyguardTransitionInteractor keyguardTransitionInteractor) {
        return new DreamingToLockscreenTransitionViewModel(keyguardTransitionInteractor);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DreamingToLockscreenTransitionViewModel m3086get() {
        return newInstance((KeyguardTransitionInteractor) this.interactorProvider.get());
    }
}