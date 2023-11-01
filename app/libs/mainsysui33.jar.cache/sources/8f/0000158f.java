package com.android.systemui.dagger;

import android.content.Context;
import android.content.pm.LauncherApps;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideLauncherAppsFactory.class */
public final class FrameworkServicesModule_ProvideLauncherAppsFactory implements Factory<LauncherApps> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideLauncherAppsFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideLauncherAppsFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideLauncherAppsFactory(provider);
    }

    public static LauncherApps provideLauncherApps(Context context) {
        return (LauncherApps) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideLauncherApps(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LauncherApps m2328get() {
        return provideLauncherApps((Context) this.contextProvider.get());
    }
}