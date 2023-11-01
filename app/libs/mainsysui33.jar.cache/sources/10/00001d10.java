package com.android.systemui.media.controls.util;

import com.android.systemui.flags.FeatureFlags;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/util/MediaFlags_Factory.class */
public final class MediaFlags_Factory implements Factory<MediaFlags> {
    public final Provider<FeatureFlags> featureFlagsProvider;

    public MediaFlags_Factory(Provider<FeatureFlags> provider) {
        this.featureFlagsProvider = provider;
    }

    public static MediaFlags_Factory create(Provider<FeatureFlags> provider) {
        return new MediaFlags_Factory(provider);
    }

    public static MediaFlags newInstance(FeatureFlags featureFlags) {
        return new MediaFlags(featureFlags);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaFlags m3286get() {
        return newInstance((FeatureFlags) this.featureFlagsProvider.get());
    }
}