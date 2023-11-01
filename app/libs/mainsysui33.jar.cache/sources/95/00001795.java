package com.android.systemui.dump;

import dagger.internal.Factory;

/* loaded from: mainsysui33.jar:com/android/systemui/dump/DumpManager_Factory.class */
public final class DumpManager_Factory implements Factory<DumpManager> {

    /* loaded from: mainsysui33.jar:com/android/systemui/dump/DumpManager_Factory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final DumpManager_Factory INSTANCE = new DumpManager_Factory();
    }

    public static DumpManager_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static DumpManager newInstance() {
        return new DumpManager();
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DumpManager m2664get() {
        return newInstance();
    }
}