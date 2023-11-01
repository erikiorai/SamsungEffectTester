package com.android.systemui.mediaprojection.appselector;

import android.content.ComponentName;
import com.android.systemui.mediaprojection.appselector.data.RecentTaskListProvider;
import dagger.internal.Factory;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/MediaProjectionAppSelectorController_Factory.class */
public final class MediaProjectionAppSelectorController_Factory implements Factory<MediaProjectionAppSelectorController> {
    public final Provider<ComponentName> appSelectorComponentNameProvider;
    public final Provider<RecentTaskListProvider> recentTaskListProvider;
    public final Provider<CoroutineScope> scopeProvider;
    public final Provider<MediaProjectionAppSelectorView> viewProvider;

    public MediaProjectionAppSelectorController_Factory(Provider<RecentTaskListProvider> provider, Provider<MediaProjectionAppSelectorView> provider2, Provider<CoroutineScope> provider3, Provider<ComponentName> provider4) {
        this.recentTaskListProvider = provider;
        this.viewProvider = provider2;
        this.scopeProvider = provider3;
        this.appSelectorComponentNameProvider = provider4;
    }

    public static MediaProjectionAppSelectorController_Factory create(Provider<RecentTaskListProvider> provider, Provider<MediaProjectionAppSelectorView> provider2, Provider<CoroutineScope> provider3, Provider<ComponentName> provider4) {
        return new MediaProjectionAppSelectorController_Factory(provider, provider2, provider3, provider4);
    }

    public static MediaProjectionAppSelectorController newInstance(RecentTaskListProvider recentTaskListProvider, MediaProjectionAppSelectorView mediaProjectionAppSelectorView, CoroutineScope coroutineScope, ComponentName componentName) {
        return new MediaProjectionAppSelectorController(recentTaskListProvider, mediaProjectionAppSelectorView, coroutineScope, componentName);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaProjectionAppSelectorController m3366get() {
        return newInstance((RecentTaskListProvider) this.recentTaskListProvider.get(), (MediaProjectionAppSelectorView) this.viewProvider.get(), (CoroutineScope) this.scopeProvider.get(), (ComponentName) this.appSelectorComponentNameProvider.get());
    }
}