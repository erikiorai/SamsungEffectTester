package com.android.systemui.dagger;

import android.content.Context;
import android.media.MediaRouter2Manager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideMediaRouter2ManagerFactory.class */
public final class FrameworkServicesModule_ProvideMediaRouter2ManagerFactory implements Factory<MediaRouter2Manager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideMediaRouter2ManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideMediaRouter2ManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideMediaRouter2ManagerFactory(provider);
    }

    public static MediaRouter2Manager provideMediaRouter2Manager(Context context) {
        return (MediaRouter2Manager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideMediaRouter2Manager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaRouter2Manager m2330get() {
        return provideMediaRouter2Manager((Context) this.contextProvider.get());
    }
}