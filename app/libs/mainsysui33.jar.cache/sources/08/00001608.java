package com.android.systemui.dock;

import dagger.internal.Factory;

/* loaded from: mainsysui33.jar:com/android/systemui/dock/DockManagerImpl_Factory.class */
public final class DockManagerImpl_Factory implements Factory<DockManagerImpl> {

    /* loaded from: mainsysui33.jar:com/android/systemui/dock/DockManagerImpl_Factory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final DockManagerImpl_Factory INSTANCE = new DockManagerImpl_Factory();
    }

    public static DockManagerImpl_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static DockManagerImpl newInstance() {
        return new DockManagerImpl();
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DockManagerImpl m2399get() {
        return newInstance();
    }
}