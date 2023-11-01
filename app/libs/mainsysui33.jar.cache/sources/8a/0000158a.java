package com.android.systemui.dagger;

import dagger.internal.Factory;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideIsTestHarnessFactory.class */
public final class FrameworkServicesModule_ProvideIsTestHarnessFactory implements Factory<Boolean> {

    /* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideIsTestHarnessFactory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final FrameworkServicesModule_ProvideIsTestHarnessFactory INSTANCE = new FrameworkServicesModule_ProvideIsTestHarnessFactory();
    }

    public static FrameworkServicesModule_ProvideIsTestHarnessFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static boolean provideIsTestHarness() {
        return FrameworkServicesModule.provideIsTestHarness();
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Boolean m2322get() {
        return Boolean.valueOf(provideIsTestHarness());
    }
}