package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import dagger.internal.Factory;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/FromDreamingTransitionInteractor_Factory.class */
public final class FromDreamingTransitionInteractor_Factory implements Factory<FromDreamingTransitionInteractor> {
    public final Provider<KeyguardInteractor> keyguardInteractorProvider;
    public final Provider<KeyguardTransitionInteractor> keyguardTransitionInteractorProvider;
    public final Provider<KeyguardTransitionRepository> keyguardTransitionRepositoryProvider;
    public final Provider<CoroutineScope> scopeProvider;

    public FromDreamingTransitionInteractor_Factory(Provider<CoroutineScope> provider, Provider<KeyguardInteractor> provider2, Provider<KeyguardTransitionRepository> provider3, Provider<KeyguardTransitionInteractor> provider4) {
        this.scopeProvider = provider;
        this.keyguardInteractorProvider = provider2;
        this.keyguardTransitionRepositoryProvider = provider3;
        this.keyguardTransitionInteractorProvider = provider4;
    }

    public static FromDreamingTransitionInteractor_Factory create(Provider<CoroutineScope> provider, Provider<KeyguardInteractor> provider2, Provider<KeyguardTransitionRepository> provider3, Provider<KeyguardTransitionInteractor> provider4) {
        return new FromDreamingTransitionInteractor_Factory(provider, provider2, provider3, provider4);
    }

    public static FromDreamingTransitionInteractor newInstance(CoroutineScope coroutineScope, KeyguardInteractor keyguardInteractor, KeyguardTransitionRepository keyguardTransitionRepository, KeyguardTransitionInteractor keyguardTransitionInteractor) {
        return new FromDreamingTransitionInteractor(coroutineScope, keyguardInteractor, keyguardTransitionRepository, keyguardTransitionInteractor);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public FromDreamingTransitionInteractor m3017get() {
        return newInstance((CoroutineScope) this.scopeProvider.get(), (KeyguardInteractor) this.keyguardInteractorProvider.get(), (KeyguardTransitionRepository) this.keyguardTransitionRepositoryProvider.get(), (KeyguardTransitionInteractor) this.keyguardTransitionInteractorProvider.get());
    }
}