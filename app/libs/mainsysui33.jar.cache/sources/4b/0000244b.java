package com.android.systemui.screenshot;

import dagger.internal.Factory;

/* loaded from: mainsysui33.jar:com/android/systemui/screenshot/LongScreenshotData_Factory.class */
public final class LongScreenshotData_Factory implements Factory<LongScreenshotData> {

    /* loaded from: mainsysui33.jar:com/android/systemui/screenshot/LongScreenshotData_Factory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final LongScreenshotData_Factory INSTANCE = new LongScreenshotData_Factory();
    }

    public static LongScreenshotData_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static LongScreenshotData newInstance() {
        return new LongScreenshotData();
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LongScreenshotData m4247get() {
        return newInstance();
    }
}