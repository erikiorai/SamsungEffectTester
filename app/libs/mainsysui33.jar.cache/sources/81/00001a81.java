package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardTransitionInteractor_Factory.class */
public final class KeyguardTransitionInteractor_Factory implements Factory<KeyguardTransitionInteractor> {
    public final Provider<KeyguardTransitionRepository> repositoryProvider;

    public KeyguardTransitionInteractor_Factory(Provider<KeyguardTransitionRepository> provider) {
        this.repositoryProvider = provider;
    }

    public static KeyguardTransitionInteractor_Factory create(Provider<KeyguardTransitionRepository> provider) {
        return new KeyguardTransitionInteractor_Factory(provider);
    }

    public static KeyguardTransitionInteractor newInstance(KeyguardTransitionRepository keyguardTransitionRepository) {
        return new KeyguardTransitionInteractor(keyguardTransitionRepository);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public KeyguardTransitionInteractor m3048get() {
        return newInstance((KeyguardTransitionRepository) this.repositoryProvider.get());
    }
}