package com.android.systemui;

import dagger.internal.Factory;

/* loaded from: mainsysui33.jar:com/android/systemui/InitController_Factory.class */
public final class InitController_Factory implements Factory<InitController> {

    /* loaded from: mainsysui33.jar:com/android/systemui/InitController_Factory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final InitController_Factory INSTANCE = new InitController_Factory();
    }

    public static InitController_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static InitController newInstance() {
        return new InitController();
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public InitController m1276get() {
        return newInstance();
    }
}