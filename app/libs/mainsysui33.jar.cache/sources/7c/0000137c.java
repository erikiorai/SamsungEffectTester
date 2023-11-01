package com.android.systemui.controls;

import dagger.internal.Factory;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/CustomIconCache_Factory.class */
public final class CustomIconCache_Factory implements Factory<CustomIconCache> {

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/CustomIconCache_Factory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final CustomIconCache_Factory INSTANCE = new CustomIconCache_Factory();
    }

    public static CustomIconCache_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static CustomIconCache newInstance() {
        return new CustomIconCache();
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public CustomIconCache m1792get() {
        return newInstance();
    }
}