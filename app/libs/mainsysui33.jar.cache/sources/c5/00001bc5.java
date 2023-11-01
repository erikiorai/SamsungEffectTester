package com.android.systemui.media;

import com.android.systemui.mediaprojection.appselector.MediaProjectionAppSelectorComponent;
import com.android.systemui.util.AsyncActivityLauncher;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/MediaProjectionAppSelectorActivity_Factory.class */
public final class MediaProjectionAppSelectorActivity_Factory implements Factory<MediaProjectionAppSelectorActivity> {
    public final Provider<AsyncActivityLauncher> activityLauncherProvider;
    public final Provider<MediaProjectionAppSelectorComponent.Factory> componentFactoryProvider;

    public MediaProjectionAppSelectorActivity_Factory(Provider<MediaProjectionAppSelectorComponent.Factory> provider, Provider<AsyncActivityLauncher> provider2) {
        this.componentFactoryProvider = provider;
        this.activityLauncherProvider = provider2;
    }

    public static MediaProjectionAppSelectorActivity_Factory create(Provider<MediaProjectionAppSelectorComponent.Factory> provider, Provider<AsyncActivityLauncher> provider2) {
        return new MediaProjectionAppSelectorActivity_Factory(provider, provider2);
    }

    public static MediaProjectionAppSelectorActivity newInstance(MediaProjectionAppSelectorComponent.Factory factory, AsyncActivityLauncher asyncActivityLauncher) {
        return new MediaProjectionAppSelectorActivity(factory, asyncActivityLauncher);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaProjectionAppSelectorActivity m3148get() {
        return newInstance((MediaProjectionAppSelectorComponent.Factory) this.componentFactoryProvider.get(), (AsyncActivityLauncher) this.activityLauncherProvider.get());
    }
}