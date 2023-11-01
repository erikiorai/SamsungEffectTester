package com.android.systemui.doze;

import dagger.internal.Factory;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeTransitionListener_Factory.class */
public final class DozeTransitionListener_Factory implements Factory<DozeTransitionListener> {

    /* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeTransitionListener_Factory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final DozeTransitionListener_Factory INSTANCE = new DozeTransitionListener_Factory();
    }

    public static DozeTransitionListener_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static DozeTransitionListener newInstance() {
        return new DozeTransitionListener();
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DozeTransitionListener m2511get() {
        return newInstance();
    }
}