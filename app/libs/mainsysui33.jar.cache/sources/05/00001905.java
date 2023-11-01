package com.android.systemui.keyguard.data;

import dagger.internal.Factory;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/BouncerViewImpl_Factory.class */
public final class BouncerViewImpl_Factory implements Factory<BouncerViewImpl> {

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/BouncerViewImpl_Factory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final BouncerViewImpl_Factory INSTANCE = new BouncerViewImpl_Factory();
    }

    public static BouncerViewImpl_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static BouncerViewImpl newInstance() {
        return new BouncerViewImpl();
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public BouncerViewImpl m2914get() {
        return newInstance();
    }
}