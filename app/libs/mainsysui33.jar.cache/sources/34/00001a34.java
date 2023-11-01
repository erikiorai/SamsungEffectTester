package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.shade.data.repository.ShadeRepository;
import dagger.internal.Factory;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/FromLockscreenTransitionInteractor_Factory.class */
public final class FromLockscreenTransitionInteractor_Factory implements Factory<FromLockscreenTransitionInteractor> {
    public final Provider<KeyguardInteractor> keyguardInteractorProvider;
    public final Provider<KeyguardTransitionInteractor> keyguardTransitionInteractorProvider;
    public final Provider<KeyguardTransitionRepository> keyguardTransitionRepositoryProvider;
    public final Provider<CoroutineScope> scopeProvider;
    public final Provider<ShadeRepository> shadeRepositoryProvider;

    public FromLockscreenTransitionInteractor_Factory(Provider<CoroutineScope> provider, Provider<KeyguardInteractor> provider2, Provider<ShadeRepository> provider3, Provider<KeyguardTransitionInteractor> provider4, Provider<KeyguardTransitionRepository> provider5) {
        this.scopeProvider = provider;
        this.keyguardInteractorProvider = provider2;
        this.shadeRepositoryProvider = provider3;
        this.keyguardTransitionInteractorProvider = provider4;
        this.keyguardTransitionRepositoryProvider = provider5;
    }

    public static FromLockscreenTransitionInteractor_Factory create(Provider<CoroutineScope> provider, Provider<KeyguardInteractor> provider2, Provider<ShadeRepository> provider3, Provider<KeyguardTransitionInteractor> provider4, Provider<KeyguardTransitionRepository> provider5) {
        return new FromLockscreenTransitionInteractor_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static FromLockscreenTransitionInteractor newInstance(CoroutineScope coroutineScope, KeyguardInteractor keyguardInteractor, ShadeRepository shadeRepository, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardTransitionRepository keyguardTransitionRepository) {
        return new FromLockscreenTransitionInteractor(coroutineScope, keyguardInteractor, shadeRepository, keyguardTransitionInteractor, keyguardTransitionRepository);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public FromLockscreenTransitionInteractor m3029get() {
        return newInstance((CoroutineScope) this.scopeProvider.get(), (KeyguardInteractor) this.keyguardInteractorProvider.get(), (ShadeRepository) this.shadeRepositoryProvider.get(), (KeyguardTransitionInteractor) this.keyguardTransitionInteractorProvider.get(), (KeyguardTransitionRepository) this.keyguardTransitionRepositoryProvider.get());
    }
}