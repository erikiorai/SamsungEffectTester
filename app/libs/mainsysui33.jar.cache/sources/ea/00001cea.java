package com.android.systemui.media.controls.ui;

import com.android.systemui.media.controls.ui.MediaHost;
import dagger.internal.Factory;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaHost_MediaHostStateHolder_Factory.class */
public final class MediaHost_MediaHostStateHolder_Factory implements Factory<MediaHost.MediaHostStateHolder> {

    /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaHost_MediaHostStateHolder_Factory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final MediaHost_MediaHostStateHolder_Factory INSTANCE = new MediaHost_MediaHostStateHolder_Factory();
    }

    public static MediaHost_MediaHostStateHolder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static MediaHost.MediaHostStateHolder newInstance() {
        return new MediaHost.MediaHostStateHolder();
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaHost.MediaHostStateHolder m3273get() {
        return newInstance();
    }
}