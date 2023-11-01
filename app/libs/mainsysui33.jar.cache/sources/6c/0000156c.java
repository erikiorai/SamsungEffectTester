package com.android.systemui.dagger;

import android.view.CrossWindowBlurListeners;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideCrossWindowBlurListenersFactory.class */
public final class FrameworkServicesModule_ProvideCrossWindowBlurListenersFactory implements Factory<CrossWindowBlurListeners> {

    /* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideCrossWindowBlurListenersFactory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final FrameworkServicesModule_ProvideCrossWindowBlurListenersFactory INSTANCE = new FrameworkServicesModule_ProvideCrossWindowBlurListenersFactory();
    }

    public static FrameworkServicesModule_ProvideCrossWindowBlurListenersFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static CrossWindowBlurListeners provideCrossWindowBlurListeners() {
        return (CrossWindowBlurListeners) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideCrossWindowBlurListeners());
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public CrossWindowBlurListeners m2281get() {
        return provideCrossWindowBlurListeners();
    }
}