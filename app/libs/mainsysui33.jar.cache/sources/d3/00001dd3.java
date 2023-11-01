package com.android.systemui.mediaprojection.appselector.data;

import com.android.systemui.shared.system.ActivityManagerWrapper;
import dagger.internal.Factory;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/data/ActivityTaskManagerThumbnailLoader_Factory.class */
public final class ActivityTaskManagerThumbnailLoader_Factory implements Factory<ActivityTaskManagerThumbnailLoader> {
    public final Provider<ActivityManagerWrapper> activityManagerProvider;
    public final Provider<CoroutineDispatcher> coroutineDispatcherProvider;

    public ActivityTaskManagerThumbnailLoader_Factory(Provider<CoroutineDispatcher> provider, Provider<ActivityManagerWrapper> provider2) {
        this.coroutineDispatcherProvider = provider;
        this.activityManagerProvider = provider2;
    }

    public static ActivityTaskManagerThumbnailLoader_Factory create(Provider<CoroutineDispatcher> provider, Provider<ActivityManagerWrapper> provider2) {
        return new ActivityTaskManagerThumbnailLoader_Factory(provider, provider2);
    }

    public static ActivityTaskManagerThumbnailLoader newInstance(CoroutineDispatcher coroutineDispatcher, ActivityManagerWrapper activityManagerWrapper) {
        return new ActivityTaskManagerThumbnailLoader(coroutineDispatcher, activityManagerWrapper);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ActivityTaskManagerThumbnailLoader m3372get() {
        return newInstance((CoroutineDispatcher) this.coroutineDispatcherProvider.get(), (ActivityManagerWrapper) this.activityManagerProvider.get());
    }
}