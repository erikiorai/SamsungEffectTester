package com.android.systemui.media.controls.ui;

import dagger.internal.Factory;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaHostStatesManager_Factory.class */
public final class MediaHostStatesManager_Factory implements Factory<MediaHostStatesManager> {

    /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/ui/MediaHostStatesManager_Factory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final MediaHostStatesManager_Factory INSTANCE = new MediaHostStatesManager_Factory();
    }

    public static MediaHostStatesManager_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static MediaHostStatesManager newInstance() {
        return new MediaHostStatesManager();
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaHostStatesManager m3270get() {
        return newInstance();
    }
}