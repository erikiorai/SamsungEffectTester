package com.android.systemui.dagger;

import com.android.internal.app.IBatteryStats;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideIBatteryStatsFactory.class */
public final class FrameworkServicesModule_ProvideIBatteryStatsFactory implements Factory<IBatteryStats> {

    /* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideIBatteryStatsFactory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final FrameworkServicesModule_ProvideIBatteryStatsFactory INSTANCE = new FrameworkServicesModule_ProvideIBatteryStatsFactory();
    }

    public static FrameworkServicesModule_ProvideIBatteryStatsFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static IBatteryStats provideIBatteryStats() {
        return (IBatteryStats) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideIBatteryStats());
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public IBatteryStats m2298get() {
        return provideIBatteryStats();
    }
}