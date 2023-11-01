package com.android.systemui.doze.util;

import dagger.internal.Factory;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/util/BurnInHelperWrapper_Factory.class */
public final class BurnInHelperWrapper_Factory implements Factory<BurnInHelperWrapper> {

    /* loaded from: mainsysui33.jar:com/android/systemui/doze/util/BurnInHelperWrapper_Factory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final BurnInHelperWrapper_Factory INSTANCE = new BurnInHelperWrapper_Factory();
    }

    public static BurnInHelperWrapper_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static BurnInHelperWrapper newInstance() {
        return new BurnInHelperWrapper();
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public BurnInHelperWrapper m2537get() {
        return newInstance();
    }
}