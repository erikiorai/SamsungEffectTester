package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.doze.util.BurnInHelperWrapper;
import com.android.systemui.keyguard.domain.interactor.KeyguardBottomAreaInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardQuickAffordanceInteractor;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ui/viewmodel/KeyguardBottomAreaViewModel_Factory.class */
public final class KeyguardBottomAreaViewModel_Factory implements Factory<KeyguardBottomAreaViewModel> {
    public final Provider<KeyguardBottomAreaInteractor> bottomAreaInteractorProvider;
    public final Provider<BurnInHelperWrapper> burnInHelperWrapperProvider;
    public final Provider<KeyguardInteractor> keyguardInteractorProvider;
    public final Provider<KeyguardQuickAffordanceInteractor> quickAffordanceInteractorProvider;

    public KeyguardBottomAreaViewModel_Factory(Provider<KeyguardInteractor> provider, Provider<KeyguardQuickAffordanceInteractor> provider2, Provider<KeyguardBottomAreaInteractor> provider3, Provider<BurnInHelperWrapper> provider4) {
        this.keyguardInteractorProvider = provider;
        this.quickAffordanceInteractorProvider = provider2;
        this.bottomAreaInteractorProvider = provider3;
        this.burnInHelperWrapperProvider = provider4;
    }

    public static KeyguardBottomAreaViewModel_Factory create(Provider<KeyguardInteractor> provider, Provider<KeyguardQuickAffordanceInteractor> provider2, Provider<KeyguardBottomAreaInteractor> provider3, Provider<BurnInHelperWrapper> provider4) {
        return new KeyguardBottomAreaViewModel_Factory(provider, provider2, provider3, provider4);
    }

    public static KeyguardBottomAreaViewModel newInstance(KeyguardInteractor keyguardInteractor, KeyguardQuickAffordanceInteractor keyguardQuickAffordanceInteractor, KeyguardBottomAreaInteractor keyguardBottomAreaInteractor, BurnInHelperWrapper burnInHelperWrapper) {
        return new KeyguardBottomAreaViewModel(keyguardInteractor, keyguardQuickAffordanceInteractor, keyguardBottomAreaInteractor, burnInHelperWrapper);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public KeyguardBottomAreaViewModel m3088get() {
        return newInstance((KeyguardInteractor) this.keyguardInteractorProvider.get(), (KeyguardQuickAffordanceInteractor) this.quickAffordanceInteractorProvider.get(), (KeyguardBottomAreaInteractor) this.bottomAreaInteractorProvider.get(), (BurnInHelperWrapper) this.burnInHelperWrapperProvider.get());
    }
}