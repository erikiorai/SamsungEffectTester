package com.android.systemui.dagger;

import android.content.Context;
import android.permission.PermissionManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvidePermissionManagerFactory.class */
public final class FrameworkServicesModule_ProvidePermissionManagerFactory implements Factory<PermissionManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvidePermissionManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvidePermissionManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvidePermissionManagerFactory(provider);
    }

    public static PermissionManager providePermissionManager(Context context) {
        return (PermissionManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.providePermissionManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public PermissionManager m2342get() {
        return providePermissionManager((Context) this.contextProvider.get());
    }
}