package com.android.systemui.dagger;

import android.app.job.JobScheduler;
import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideJobSchedulerFactory.class */
public final class FrameworkServicesModule_ProvideJobSchedulerFactory implements Factory<JobScheduler> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideJobSchedulerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideJobSchedulerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideJobSchedulerFactory(provider);
    }

    public static JobScheduler provideJobScheduler(Context context) {
        return (JobScheduler) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideJobScheduler(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public JobScheduler m2325get() {
        return provideJobScheduler((Context) this.contextProvider.get());
    }
}