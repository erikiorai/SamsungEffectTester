package com.android.systemui.dagger;

import android.content.Context;
import android.safetycenter.SafetyCenterManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideSafetyCenterManagerFactory.class */
public final class FrameworkServicesModule_ProvideSafetyCenterManagerFactory implements Factory<SafetyCenterManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideSafetyCenterManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideSafetyCenterManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideSafetyCenterManagerFactory(provider);
    }

    public static SafetyCenterManager provideSafetyCenterManager(Context context) {
        return (SafetyCenterManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideSafetyCenterManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public SafetyCenterManager m2346get() {
        return provideSafetyCenterManager((Context) this.contextProvider.get());
    }
}