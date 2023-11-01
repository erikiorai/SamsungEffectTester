package com.android.systemui.media.dagger;

import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.media.nearby.NearbyMediaDevicesManager;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.util.Optional;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/dagger/MediaModule_ProvidesNearbyMediaDevicesManagerFactory.class */
public final class MediaModule_ProvidesNearbyMediaDevicesManagerFactory implements Factory<Optional<NearbyMediaDevicesManager>> {
    public final Provider<MediaFlags> mediaFlagsProvider;
    public final Provider<NearbyMediaDevicesManager> nearbyMediaDevicesManagerLazyProvider;

    public MediaModule_ProvidesNearbyMediaDevicesManagerFactory(Provider<MediaFlags> provider, Provider<NearbyMediaDevicesManager> provider2) {
        this.mediaFlagsProvider = provider;
        this.nearbyMediaDevicesManagerLazyProvider = provider2;
    }

    public static MediaModule_ProvidesNearbyMediaDevicesManagerFactory create(Provider<MediaFlags> provider, Provider<NearbyMediaDevicesManager> provider2) {
        return new MediaModule_ProvidesNearbyMediaDevicesManagerFactory(provider, provider2);
    }

    public static Optional<NearbyMediaDevicesManager> providesNearbyMediaDevicesManager(MediaFlags mediaFlags, Lazy<NearbyMediaDevicesManager> lazy) {
        return (Optional) Preconditions.checkNotNullFromProvides(MediaModule.providesNearbyMediaDevicesManager(mediaFlags, lazy));
    }

    /* JADX DEBUG: Method merged with bridge method */
    public Optional<NearbyMediaDevicesManager> get() {
        return providesNearbyMediaDevicesManager((MediaFlags) this.mediaFlagsProvider.get(), DoubleCheck.lazy(this.nearbyMediaDevicesManagerLazyProvider));
    }
}