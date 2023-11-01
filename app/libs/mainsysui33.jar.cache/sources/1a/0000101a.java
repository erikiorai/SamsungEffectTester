package com.android.systemui;

import dagger.internal.Factory;

/* loaded from: mainsysui33.jar:com/android/systemui/UiOffloadThread_Factory.class */
public final class UiOffloadThread_Factory implements Factory<UiOffloadThread> {

    /* loaded from: mainsysui33.jar:com/android/systemui/UiOffloadThread_Factory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final UiOffloadThread_Factory INSTANCE = new UiOffloadThread_Factory();
    }

    public static UiOffloadThread_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static UiOffloadThread newInstance() {
        return new UiOffloadThread();
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public UiOffloadThread m1320get() {
        return newInstance();
    }
}