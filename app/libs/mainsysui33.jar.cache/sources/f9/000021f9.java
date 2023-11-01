package com.android.systemui.qs.footer.data.repository;

import com.android.systemui.qs.FgsManagerController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/footer/data/repository/ForegroundServicesRepositoryImpl_Factory.class */
public final class ForegroundServicesRepositoryImpl_Factory implements Factory<ForegroundServicesRepositoryImpl> {
    public final Provider<FgsManagerController> fgsManagerControllerProvider;

    public ForegroundServicesRepositoryImpl_Factory(Provider<FgsManagerController> provider) {
        this.fgsManagerControllerProvider = provider;
    }

    public static ForegroundServicesRepositoryImpl_Factory create(Provider<FgsManagerController> provider) {
        return new ForegroundServicesRepositoryImpl_Factory(provider);
    }

    public static ForegroundServicesRepositoryImpl newInstance(FgsManagerController fgsManagerController) {
        return new ForegroundServicesRepositoryImpl(fgsManagerController);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ForegroundServicesRepositoryImpl m3923get() {
        return newInstance((FgsManagerController) this.fgsManagerControllerProvider.get());
    }
}