package com.android.systemui.dagger;

import android.app.StatsManager;
import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideStatsManagerFactory.class */
public final class FrameworkServicesModule_ProvideStatsManagerFactory implements Factory<StatsManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideStatsManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideStatsManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideStatsManagerFactory(provider);
    }

    public static StatsManager provideStatsManager(Context context) {
        return (StatsManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideStatsManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public StatsManager m2351get() {
        return provideStatsManager((Context) this.contextProvider.get());
    }
}