package com.android.systemui.media.controls.resume;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/resume/MediaBrowserFactory_Factory.class */
public final class MediaBrowserFactory_Factory implements Factory<MediaBrowserFactory> {
    public final Provider<Context> contextProvider;

    public MediaBrowserFactory_Factory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static MediaBrowserFactory_Factory create(Provider<Context> provider) {
        return new MediaBrowserFactory_Factory(provider);
    }

    public static MediaBrowserFactory newInstance(Context context) {
        return new MediaBrowserFactory(context);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaBrowserFactory m3210get() {
        return newInstance((Context) this.contextProvider.get());
    }
}