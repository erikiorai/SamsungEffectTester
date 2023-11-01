package com.android.systemui;

import android.content.Context;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.settings.UserTracker;
import dagger.internal.Factory;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: mainsysui33.jar:com/android/systemui/ChooserSelector_Factory.class */
public final class ChooserSelector_Factory implements Factory<ChooserSelector> {
    public final Provider<CoroutineDispatcher> bgDispatcherProvider;
    public final Provider<Context> contextProvider;
    public final Provider<CoroutineScope> coroutineScopeProvider;
    public final Provider<FeatureFlags> featureFlagsProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public ChooserSelector_Factory(Provider<Context> provider, Provider<UserTracker> provider2, Provider<FeatureFlags> provider3, Provider<CoroutineScope> provider4, Provider<CoroutineDispatcher> provider5) {
        this.contextProvider = provider;
        this.userTrackerProvider = provider2;
        this.featureFlagsProvider = provider3;
        this.coroutineScopeProvider = provider4;
        this.bgDispatcherProvider = provider5;
    }

    public static ChooserSelector_Factory create(Provider<Context> provider, Provider<UserTracker> provider2, Provider<FeatureFlags> provider3, Provider<CoroutineScope> provider4, Provider<CoroutineDispatcher> provider5) {
        return new ChooserSelector_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static ChooserSelector newInstance(Context context, UserTracker userTracker, FeatureFlags featureFlags, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher) {
        return new ChooserSelector(context, userTracker, featureFlags, coroutineScope, coroutineDispatcher);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ChooserSelector m1232get() {
        return newInstance((Context) this.contextProvider.get(), (UserTracker) this.userTrackerProvider.get(), (FeatureFlags) this.featureFlagsProvider.get(), (CoroutineScope) this.coroutineScopeProvider.get(), (CoroutineDispatcher) this.bgDispatcherProvider.get());
    }
}