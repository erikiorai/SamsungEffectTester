package com.android.systemui.media.controls.pipeline;

import dagger.internal.Factory;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/pipeline/MediaDataCombineLatest_Factory.class */
public final class MediaDataCombineLatest_Factory implements Factory<MediaDataCombineLatest> {

    /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/pipeline/MediaDataCombineLatest_Factory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final MediaDataCombineLatest_Factory INSTANCE = new MediaDataCombineLatest_Factory();
    }

    public static MediaDataCombineLatest_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static MediaDataCombineLatest newInstance() {
        return new MediaDataCombineLatest();
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaDataCombineLatest m3185get() {
        return newInstance();
    }
}