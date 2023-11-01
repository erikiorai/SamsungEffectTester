package com.android.systemui.dagger;

import android.content.Context;
import android.content.SharedPreferences;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideSharePreferencesFactory.class */
public final class FrameworkServicesModule_ProvideSharePreferencesFactory implements Factory<SharedPreferences> {
    public final Provider<Context> contextProvider;
    public final FrameworkServicesModule module;

    public FrameworkServicesModule_ProvideSharePreferencesFactory(FrameworkServicesModule frameworkServicesModule, Provider<Context> provider) {
        this.module = frameworkServicesModule;
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideSharePreferencesFactory create(FrameworkServicesModule frameworkServicesModule, Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideSharePreferencesFactory(frameworkServicesModule, provider);
    }

    public static SharedPreferences provideSharePreferences(FrameworkServicesModule frameworkServicesModule, Context context) {
        return (SharedPreferences) Preconditions.checkNotNullFromProvides(frameworkServicesModule.provideSharePreferences(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public SharedPreferences m2348get() {
        return provideSharePreferences(this.module, (Context) this.contextProvider.get());
    }
}