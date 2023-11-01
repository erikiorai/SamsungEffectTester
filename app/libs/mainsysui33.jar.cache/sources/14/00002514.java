package com.android.systemui.security.data.repository;

import com.android.systemui.statusbar.policy.SecurityController;
import dagger.internal.Factory;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: mainsysui33.jar:com/android/systemui/security/data/repository/SecurityRepositoryImpl_Factory.class */
public final class SecurityRepositoryImpl_Factory implements Factory<SecurityRepositoryImpl> {
    public final Provider<CoroutineDispatcher> bgDispatcherProvider;
    public final Provider<SecurityController> securityControllerProvider;

    public SecurityRepositoryImpl_Factory(Provider<SecurityController> provider, Provider<CoroutineDispatcher> provider2) {
        this.securityControllerProvider = provider;
        this.bgDispatcherProvider = provider2;
    }

    public static SecurityRepositoryImpl_Factory create(Provider<SecurityController> provider, Provider<CoroutineDispatcher> provider2) {
        return new SecurityRepositoryImpl_Factory(provider, provider2);
    }

    public static SecurityRepositoryImpl newInstance(SecurityController securityController, CoroutineDispatcher coroutineDispatcher) {
        return new SecurityRepositoryImpl(securityController, coroutineDispatcher);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public SecurityRepositoryImpl m4351get() {
        return newInstance((SecurityController) this.securityControllerProvider.get(), (CoroutineDispatcher) this.bgDispatcherProvider.get());
    }
}