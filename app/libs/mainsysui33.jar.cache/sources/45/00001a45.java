package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardInteractor_Factory.class */
public final class KeyguardInteractor_Factory implements Factory<KeyguardInteractor> {
    public final Provider<KeyguardRepository> repositoryProvider;

    public KeyguardInteractor_Factory(Provider<KeyguardRepository> provider) {
        this.repositoryProvider = provider;
    }

    public static KeyguardInteractor_Factory create(Provider<KeyguardRepository> provider) {
        return new KeyguardInteractor_Factory(provider);
    }

    public static KeyguardInteractor newInstance(KeyguardRepository keyguardRepository) {
        return new KeyguardInteractor(keyguardRepository);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public KeyguardInteractor m3040get() {
        return newInstance((KeyguardRepository) this.repositoryProvider.get());
    }
}