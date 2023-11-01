package com.android.systemui.media.dagger;

import com.android.systemui.media.controls.util.MediaFlags;
import com.android.systemui.media.muteawait.MediaMuteAwaitConnectionCli;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.util.Optional;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/dagger/MediaModule_ProvidesMediaMuteAwaitConnectionCliFactory.class */
public final class MediaModule_ProvidesMediaMuteAwaitConnectionCliFactory implements Factory<Optional<MediaMuteAwaitConnectionCli>> {
    public final Provider<MediaFlags> mediaFlagsProvider;
    public final Provider<MediaMuteAwaitConnectionCli> muteAwaitConnectionCliLazyProvider;

    public MediaModule_ProvidesMediaMuteAwaitConnectionCliFactory(Provider<MediaFlags> provider, Provider<MediaMuteAwaitConnectionCli> provider2) {
        this.mediaFlagsProvider = provider;
        this.muteAwaitConnectionCliLazyProvider = provider2;
    }

    public static MediaModule_ProvidesMediaMuteAwaitConnectionCliFactory create(Provider<MediaFlags> provider, Provider<MediaMuteAwaitConnectionCli> provider2) {
        return new MediaModule_ProvidesMediaMuteAwaitConnectionCliFactory(provider, provider2);
    }

    public static Optional<MediaMuteAwaitConnectionCli> providesMediaMuteAwaitConnectionCli(MediaFlags mediaFlags, Lazy<MediaMuteAwaitConnectionCli> lazy) {
        return (Optional) Preconditions.checkNotNullFromProvides(MediaModule.providesMediaMuteAwaitConnectionCli(mediaFlags, lazy));
    }

    /* JADX DEBUG: Method merged with bridge method */
    public Optional<MediaMuteAwaitConnectionCli> get() {
        return providesMediaMuteAwaitConnectionCli((MediaFlags) this.mediaFlagsProvider.get(), DoubleCheck.lazy(this.muteAwaitConnectionCliLazyProvider));
    }
}