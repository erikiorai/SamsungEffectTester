package com.android.systemui.dagger;

import android.content.Context;
import android.os.UserManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideUserManagerFactory.class */
public final class FrameworkServicesModule_ProvideUserManagerFactory implements Factory<UserManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideUserManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideUserManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideUserManagerFactory(provider);
    }

    public static UserManager provideUserManager(Context context) {
        return (UserManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideUserManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public UserManager m2358get() {
        return provideUserManager((Context) this.contextProvider.get());
    }
}