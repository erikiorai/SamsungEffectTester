package com.android.systemui.keyguard.domain.interactor;

import dagger.internal.Factory;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/PrimaryBouncerCallbackInteractor_Factory.class */
public final class PrimaryBouncerCallbackInteractor_Factory implements Factory<PrimaryBouncerCallbackInteractor> {

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/PrimaryBouncerCallbackInteractor_Factory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final PrimaryBouncerCallbackInteractor_Factory INSTANCE = new PrimaryBouncerCallbackInteractor_Factory();
    }

    public static PrimaryBouncerCallbackInteractor_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static PrimaryBouncerCallbackInteractor newInstance() {
        return new PrimaryBouncerCallbackInteractor();
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public PrimaryBouncerCallbackInteractor m3052get() {
        return newInstance();
    }
}