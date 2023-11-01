package com.android.systemui.media.dialog;

import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/dialog/MediaOutputDialogReceiver_Factory.class */
public final class MediaOutputDialogReceiver_Factory implements Factory<MediaOutputDialogReceiver> {
    public final Provider<MediaOutputBroadcastDialogFactory> mediaOutputBroadcastDialogFactoryProvider;
    public final Provider<MediaOutputDialogFactory> mediaOutputDialogFactoryProvider;

    public MediaOutputDialogReceiver_Factory(Provider<MediaOutputDialogFactory> provider, Provider<MediaOutputBroadcastDialogFactory> provider2) {
        this.mediaOutputDialogFactoryProvider = provider;
        this.mediaOutputBroadcastDialogFactoryProvider = provider2;
    }

    public static MediaOutputDialogReceiver_Factory create(Provider<MediaOutputDialogFactory> provider, Provider<MediaOutputBroadcastDialogFactory> provider2) {
        return new MediaOutputDialogReceiver_Factory(provider, provider2);
    }

    public static MediaOutputDialogReceiver newInstance(MediaOutputDialogFactory mediaOutputDialogFactory, MediaOutputBroadcastDialogFactory mediaOutputBroadcastDialogFactory) {
        return new MediaOutputDialogReceiver(mediaOutputDialogFactory, mediaOutputBroadcastDialogFactory);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaOutputDialogReceiver m3326get() {
        return newInstance((MediaOutputDialogFactory) this.mediaOutputDialogFactoryProvider.get(), (MediaOutputBroadcastDialogFactory) this.mediaOutputBroadcastDialogFactoryProvider.get());
    }
}