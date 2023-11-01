package com.android.systemui.power;

import dagger.internal.Factory;

/* loaded from: mainsysui33.jar:com/android/systemui/power/EnhancedEstimatesImpl_Factory.class */
public final class EnhancedEstimatesImpl_Factory implements Factory<EnhancedEstimatesImpl> {

    /* loaded from: mainsysui33.jar:com/android/systemui/power/EnhancedEstimatesImpl_Factory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final EnhancedEstimatesImpl_Factory INSTANCE = new EnhancedEstimatesImpl_Factory();
    }

    public static EnhancedEstimatesImpl_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static EnhancedEstimatesImpl newInstance() {
        return new EnhancedEstimatesImpl();
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public EnhancedEstimatesImpl m3606get() {
        return newInstance();
    }
}