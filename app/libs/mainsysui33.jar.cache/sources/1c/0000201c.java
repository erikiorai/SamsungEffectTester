package com.android.systemui.power.domain.interactor;

import com.android.systemui.power.data.repository.PowerRepository;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/power/domain/interactor/PowerInteractor_Factory.class */
public final class PowerInteractor_Factory implements Factory<PowerInteractor> {
    public final Provider<PowerRepository> repositoryProvider;

    public PowerInteractor_Factory(Provider<PowerRepository> provider) {
        this.repositoryProvider = provider;
    }

    public static PowerInteractor_Factory create(Provider<PowerRepository> provider) {
        return new PowerInteractor_Factory(provider);
    }

    public static PowerInteractor newInstance(PowerRepository powerRepository) {
        return new PowerInteractor(powerRepository);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public PowerInteractor m3661get() {
        return newInstance((PowerRepository) this.repositoryProvider.get());
    }
}