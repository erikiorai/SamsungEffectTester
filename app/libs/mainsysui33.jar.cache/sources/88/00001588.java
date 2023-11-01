package com.android.systemui.dagger;

import com.android.internal.jank.InteractionJankMonitor;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideInteractionJankMonitorFactory.class */
public final class FrameworkServicesModule_ProvideInteractionJankMonitorFactory implements Factory<InteractionJankMonitor> {

    /* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideInteractionJankMonitorFactory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final FrameworkServicesModule_ProvideInteractionJankMonitorFactory INSTANCE = new FrameworkServicesModule_ProvideInteractionJankMonitorFactory();
    }

    public static FrameworkServicesModule_ProvideInteractionJankMonitorFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static InteractionJankMonitor provideInteractionJankMonitor() {
        return (InteractionJankMonitor) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideInteractionJankMonitor());
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public InteractionJankMonitor m2319get() {
        return provideInteractionJankMonitor();
    }
}