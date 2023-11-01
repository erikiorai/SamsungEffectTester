package com.android.systemui.dagger;

import android.app.AlarmManager;
import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideAlarmManagerFactory.class */
public final class FrameworkServicesModule_ProvideAlarmManagerFactory implements Factory<AlarmManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideAlarmManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideAlarmManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideAlarmManagerFactory(provider);
    }

    public static AlarmManager provideAlarmManager(Context context) {
        return (AlarmManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideAlarmManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public AlarmManager m2269get() {
        return provideAlarmManager((Context) this.contextProvider.get());
    }
}