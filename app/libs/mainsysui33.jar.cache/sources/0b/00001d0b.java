package com.android.systemui.media.controls.util;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/util/MediaControllerFactory_Factory.class */
public final class MediaControllerFactory_Factory implements Factory<MediaControllerFactory> {
    public final Provider<Context> contextProvider;

    public MediaControllerFactory_Factory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static MediaControllerFactory_Factory create(Provider<Context> provider) {
        return new MediaControllerFactory_Factory(provider);
    }

    public static MediaControllerFactory newInstance(Context context) {
        return new MediaControllerFactory(context);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaControllerFactory m3284get() {
        return newInstance((Context) this.contextProvider.get());
    }
}