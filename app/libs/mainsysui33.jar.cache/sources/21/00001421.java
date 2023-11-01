package com.android.systemui.controls.management;

import android.content.Context;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.settings.UserTracker;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ControlsListingControllerImpl_Factory.class */
public final class ControlsListingControllerImpl_Factory implements Factory<ControlsListingControllerImpl> {
    public final Provider<Context> contextProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<Executor> executorProvider;
    public final Provider<FeatureFlags> featureFlagsProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public ControlsListingControllerImpl_Factory(Provider<Context> provider, Provider<Executor> provider2, Provider<UserTracker> provider3, Provider<DumpManager> provider4, Provider<FeatureFlags> provider5) {
        this.contextProvider = provider;
        this.executorProvider = provider2;
        this.userTrackerProvider = provider3;
        this.dumpManagerProvider = provider4;
        this.featureFlagsProvider = provider5;
    }

    public static ControlsListingControllerImpl_Factory create(Provider<Context> provider, Provider<Executor> provider2, Provider<UserTracker> provider3, Provider<DumpManager> provider4, Provider<FeatureFlags> provider5) {
        return new ControlsListingControllerImpl_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static ControlsListingControllerImpl newInstance(Context context, Executor executor, UserTracker userTracker, DumpManager dumpManager, FeatureFlags featureFlags) {
        return new ControlsListingControllerImpl(context, executor, userTracker, dumpManager, featureFlags);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ControlsListingControllerImpl m1835get() {
        return newInstance((Context) this.contextProvider.get(), (Executor) this.executorProvider.get(), (UserTracker) this.userTrackerProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (FeatureFlags) this.featureFlagsProvider.get());
    }
}