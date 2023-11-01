package com.android.systemui.media.taptotransfer;

import com.android.systemui.flags.FeatureFlags;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/taptotransfer/MediaTttFlags_Factory.class */
public final class MediaTttFlags_Factory implements Factory<MediaTttFlags> {
    public final Provider<FeatureFlags> featureFlagsProvider;

    public MediaTttFlags_Factory(Provider<FeatureFlags> provider) {
        this.featureFlagsProvider = provider;
    }

    public static MediaTttFlags_Factory create(Provider<FeatureFlags> provider) {
        return new MediaTttFlags_Factory(provider);
    }

    public static MediaTttFlags newInstance(FeatureFlags featureFlags) {
        return new MediaTttFlags(featureFlags);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaTttFlags m3347get() {
        return newInstance((FeatureFlags) this.featureFlagsProvider.get());
    }
}