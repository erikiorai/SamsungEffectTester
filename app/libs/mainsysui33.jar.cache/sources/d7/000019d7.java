package com.android.systemui.keyguard.data.repository;

import dagger.internal.Factory;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardTransitionRepositoryImpl_Factory.class */
public final class KeyguardTransitionRepositoryImpl_Factory implements Factory<KeyguardTransitionRepositoryImpl> {

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardTransitionRepositoryImpl_Factory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final KeyguardTransitionRepositoryImpl_Factory INSTANCE = new KeyguardTransitionRepositoryImpl_Factory();
    }

    public static KeyguardTransitionRepositoryImpl_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static KeyguardTransitionRepositoryImpl newInstance() {
        return new KeyguardTransitionRepositoryImpl();
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public KeyguardTransitionRepositoryImpl m2989get() {
        return newInstance();
    }
}