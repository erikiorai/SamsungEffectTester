package com.android.systemui.media.controls.models.recommendation;

import dagger.internal.Factory;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/models/recommendation/SmartspaceMediaDataProvider_Factory.class */
public final class SmartspaceMediaDataProvider_Factory implements Factory<SmartspaceMediaDataProvider> {

    /* loaded from: mainsysui33.jar:com/android/systemui/media/controls/models/recommendation/SmartspaceMediaDataProvider_Factory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final SmartspaceMediaDataProvider_Factory INSTANCE = new SmartspaceMediaDataProvider_Factory();
    }

    public static SmartspaceMediaDataProvider_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static SmartspaceMediaDataProvider newInstance() {
        return new SmartspaceMediaDataProvider();
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public SmartspaceMediaDataProvider m3181get() {
        return newInstance();
    }
}