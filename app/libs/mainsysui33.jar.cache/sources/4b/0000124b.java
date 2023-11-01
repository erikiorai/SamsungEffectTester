package com.android.systemui.biometrics.udfps;

import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/udfps/SinglePointerTouchProcessor_Factory.class */
public final class SinglePointerTouchProcessor_Factory implements Factory<SinglePointerTouchProcessor> {
    public final Provider<OverlapDetector> overlapDetectorProvider;

    public SinglePointerTouchProcessor_Factory(Provider<OverlapDetector> provider) {
        this.overlapDetectorProvider = provider;
    }

    public static SinglePointerTouchProcessor_Factory create(Provider<OverlapDetector> provider) {
        return new SinglePointerTouchProcessor_Factory(provider);
    }

    public static SinglePointerTouchProcessor newInstance(OverlapDetector overlapDetector) {
        return new SinglePointerTouchProcessor(overlapDetector);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public SinglePointerTouchProcessor m1618get() {
        return newInstance((OverlapDetector) this.overlapDetectorProvider.get());
    }
}