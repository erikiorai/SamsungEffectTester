package com.android.systemui.controls;

import dagger.internal.Factory;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/ControlsMetricsLoggerImpl_Factory.class */
public final class ControlsMetricsLoggerImpl_Factory implements Factory<ControlsMetricsLoggerImpl> {

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/ControlsMetricsLoggerImpl_Factory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final ControlsMetricsLoggerImpl_Factory INSTANCE = new ControlsMetricsLoggerImpl_Factory();
    }

    public static ControlsMetricsLoggerImpl_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static ControlsMetricsLoggerImpl newInstance() {
        return new ControlsMetricsLoggerImpl();
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ControlsMetricsLoggerImpl m1789get() {
        return newInstance();
    }
}