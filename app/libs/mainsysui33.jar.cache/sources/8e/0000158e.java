package com.android.systemui.dagger;

import android.content.Context;
import com.android.internal.util.LatencyTracker;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideLatencyTrackerFactory.class */
public final class FrameworkServicesModule_ProvideLatencyTrackerFactory implements Factory<LatencyTracker> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideLatencyTrackerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideLatencyTrackerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideLatencyTrackerFactory(provider);
    }

    public static LatencyTracker provideLatencyTracker(Context context) {
        return (LatencyTracker) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideLatencyTracker(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LatencyTracker m2327get() {
        return provideLatencyTracker((Context) this.contextProvider.get());
    }
}