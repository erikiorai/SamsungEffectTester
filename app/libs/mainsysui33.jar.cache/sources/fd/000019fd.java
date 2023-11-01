package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.shade.data.repository.ShadeRepository;
import dagger.internal.Factory;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/FromBouncerTransitionInteractor_Factory.class */
public final class FromBouncerTransitionInteractor_Factory implements Factory<FromBouncerTransitionInteractor> {
    public final Provider<KeyguardInteractor> keyguardInteractorProvider;
    public final Provider<KeyguardTransitionInteractor> keyguardTransitionInteractorProvider;
    public final Provider<KeyguardTransitionRepository> keyguardTransitionRepositoryProvider;
    public final Provider<CoroutineScope> scopeProvider;
    public final Provider<ShadeRepository> shadeRepositoryProvider;

    public FromBouncerTransitionInteractor_Factory(Provider<CoroutineScope> provider, Provider<KeyguardInteractor> provider2, Provider<ShadeRepository> provider3, Provider<KeyguardTransitionRepository> provider4, Provider<KeyguardTransitionInteractor> provider5) {
        this.scopeProvider = provider;
        this.keyguardInteractorProvider = provider2;
        this.shadeRepositoryProvider = provider3;
        this.keyguardTransitionRepositoryProvider = provider4;
        this.keyguardTransitionInteractorProvider = provider5;
    }

    public static FromBouncerTransitionInteractor_Factory create(Provider<CoroutineScope> provider, Provider<KeyguardInteractor> provider2, Provider<ShadeRepository> provider3, Provider<KeyguardTransitionRepository> provider4, Provider<KeyguardTransitionInteractor> provider5) {
        return new FromBouncerTransitionInteractor_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static FromBouncerTransitionInteractor newInstance(CoroutineScope coroutineScope, KeyguardInteractor keyguardInteractor, ShadeRepository shadeRepository, KeyguardTransitionRepository keyguardTransitionRepository, KeyguardTransitionInteractor keyguardTransitionInteractor) {
        return new FromBouncerTransitionInteractor(coroutineScope, keyguardInteractor, shadeRepository, keyguardTransitionRepository, keyguardTransitionInteractor);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public FromBouncerTransitionInteractor m3002get() {
        return newInstance((CoroutineScope) this.scopeProvider.get(), (KeyguardInteractor) this.keyguardInteractorProvider.get(), (ShadeRepository) this.shadeRepositoryProvider.get(), (KeyguardTransitionRepository) this.keyguardTransitionRepositoryProvider.get(), (KeyguardTransitionInteractor) this.keyguardTransitionInteractorProvider.get());
    }
}