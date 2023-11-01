package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.keyguard.data.repository.KeyguardRepository;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardBottomAreaInteractor_Factory.class */
public final class KeyguardBottomAreaInteractor_Factory implements Factory<KeyguardBottomAreaInteractor> {
    public final Provider<KeyguardRepository> repositoryProvider;

    public KeyguardBottomAreaInteractor_Factory(Provider<KeyguardRepository> provider) {
        this.repositoryProvider = provider;
    }

    public static KeyguardBottomAreaInteractor_Factory create(Provider<KeyguardRepository> provider) {
        return new KeyguardBottomAreaInteractor_Factory(provider);
    }

    public static KeyguardBottomAreaInteractor newInstance(KeyguardRepository keyguardRepository) {
        return new KeyguardBottomAreaInteractor(keyguardRepository);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public KeyguardBottomAreaInteractor m3038get() {
        return newInstance((KeyguardRepository) this.repositoryProvider.get());
    }
}