package com.android.systemui.dagger;

import android.app.ActivityManager;
import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideActivityManagerFactory.class */
public final class FrameworkServicesModule_ProvideActivityManagerFactory implements Factory<ActivityManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideActivityManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideActivityManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideActivityManagerFactory(provider);
    }

    public static ActivityManager provideActivityManager(Context context) {
        return (ActivityManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideActivityManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ActivityManager m2265get() {
        return provideActivityManager((Context) this.contextProvider.get());
    }
}