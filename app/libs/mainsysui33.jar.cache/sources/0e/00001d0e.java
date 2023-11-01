package com.android.systemui.media.controls.util;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/util/MediaFeatureFlag_Factory.class */
public final class MediaFeatureFlag_Factory implements Factory<MediaFeatureFlag> {
    public final Provider<Context> contextProvider;

    public MediaFeatureFlag_Factory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static MediaFeatureFlag_Factory create(Provider<Context> provider) {
        return new MediaFeatureFlag_Factory(provider);
    }

    public static MediaFeatureFlag newInstance(Context context) {
        return new MediaFeatureFlag(context);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaFeatureFlag m3285get() {
        return newInstance((Context) this.contextProvider.get());
    }
}