package com.android.systemui.flags;

import dagger.internal.Factory;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/SystemPropertiesHelper_Factory.class */
public final class SystemPropertiesHelper_Factory implements Factory<SystemPropertiesHelper> {

    /* loaded from: mainsysui33.jar:com/android/systemui/flags/SystemPropertiesHelper_Factory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final SystemPropertiesHelper_Factory INSTANCE = new SystemPropertiesHelper_Factory();
    }

    public static SystemPropertiesHelper_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static SystemPropertiesHelper newInstance() {
        return new SystemPropertiesHelper();
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public SystemPropertiesHelper m2711get() {
        return newInstance();
    }
}