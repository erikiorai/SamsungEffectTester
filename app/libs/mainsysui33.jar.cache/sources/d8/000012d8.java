package com.android.systemui.classifier;

import dagger.internal.Factory;

/* loaded from: mainsysui33.jar:com/android/systemui/classifier/FalsingModule_ProvidesDoubleTapTimeoutMsFactory.class */
public final class FalsingModule_ProvidesDoubleTapTimeoutMsFactory implements Factory<Long> {

    /* loaded from: mainsysui33.jar:com/android/systemui/classifier/FalsingModule_ProvidesDoubleTapTimeoutMsFactory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final FalsingModule_ProvidesDoubleTapTimeoutMsFactory INSTANCE = new FalsingModule_ProvidesDoubleTapTimeoutMsFactory();
    }

    public static FalsingModule_ProvidesDoubleTapTimeoutMsFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static long providesDoubleTapTimeoutMs() {
        return FalsingModule.providesDoubleTapTimeoutMs();
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Long m1706get() {
        return Long.valueOf(providesDoubleTapTimeoutMs());
    }
}