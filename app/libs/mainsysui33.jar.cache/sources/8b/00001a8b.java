package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.data.repository.LightRevealScrimRepository;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/LightRevealScrimInteractor_Factory.class */
public final class LightRevealScrimInteractor_Factory implements Factory<LightRevealScrimInteractor> {
    public final Provider<LightRevealScrimRepository> lightRevealScrimRepositoryProvider;
    public final Provider<KeyguardTransitionInteractor> transitionInteractorProvider;
    public final Provider<KeyguardTransitionRepository> transitionRepositoryProvider;

    public LightRevealScrimInteractor_Factory(Provider<KeyguardTransitionRepository> provider, Provider<KeyguardTransitionInteractor> provider2, Provider<LightRevealScrimRepository> provider3) {
        this.transitionRepositoryProvider = provider;
        this.transitionInteractorProvider = provider2;
        this.lightRevealScrimRepositoryProvider = provider3;
    }

    public static LightRevealScrimInteractor_Factory create(Provider<KeyguardTransitionRepository> provider, Provider<KeyguardTransitionInteractor> provider2, Provider<LightRevealScrimRepository> provider3) {
        return new LightRevealScrimInteractor_Factory(provider, provider2, provider3);
    }

    public static LightRevealScrimInteractor newInstance(KeyguardTransitionRepository keyguardTransitionRepository, KeyguardTransitionInteractor keyguardTransitionInteractor, LightRevealScrimRepository lightRevealScrimRepository) {
        return new LightRevealScrimInteractor(keyguardTransitionRepository, keyguardTransitionInteractor, lightRevealScrimRepository);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LightRevealScrimInteractor m3051get() {
        return newInstance((KeyguardTransitionRepository) this.transitionRepositoryProvider.get(), (KeyguardTransitionInteractor) this.transitionInteractorProvider.get(), (LightRevealScrimRepository) this.lightRevealScrimRepositoryProvider.get());
    }
}