package com.android.systemui.screenshot;

import android.content.ContentResolver;
import com.android.systemui.flags.FeatureFlags;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/ImageExporter_Factory.class */
public final class ImageExporter_Factory implements Factory<ImageExporter> {
    public final Provider<FeatureFlags> flagsProvider;
    public final Provider<ContentResolver> resolverProvider;

    public ImageExporter_Factory(Provider<ContentResolver> provider, Provider<FeatureFlags> provider2) {
        this.resolverProvider = provider;
        this.flagsProvider = provider2;
    }

    public static ImageExporter_Factory create(Provider<ContentResolver> provider, Provider<FeatureFlags> provider2) {
        return new ImageExporter_Factory(provider, provider2);
    }

    public static ImageExporter newInstance(ContentResolver contentResolver, FeatureFlags featureFlags) {
        return new ImageExporter(contentResolver, featureFlags);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ImageExporter m4231get() {
        return newInstance((ContentResolver) this.resolverProvider.get(), (FeatureFlags) this.flagsProvider.get());
    }
}