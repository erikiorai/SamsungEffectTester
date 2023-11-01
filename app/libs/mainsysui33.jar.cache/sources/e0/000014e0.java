package com.android.systemui.dagger;

import com.android.internal.logging.MetricsLogger;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/AndroidInternalsModule_ProvideMetricsLoggerFactory.class */
public final class AndroidInternalsModule_ProvideMetricsLoggerFactory implements Factory<MetricsLogger> {
    public final AndroidInternalsModule module;

    public AndroidInternalsModule_ProvideMetricsLoggerFactory(AndroidInternalsModule androidInternalsModule) {
        this.module = androidInternalsModule;
    }

    public static AndroidInternalsModule_ProvideMetricsLoggerFactory create(AndroidInternalsModule androidInternalsModule) {
        return new AndroidInternalsModule_ProvideMetricsLoggerFactory(androidInternalsModule);
    }

    public static MetricsLogger provideMetricsLogger(AndroidInternalsModule androidInternalsModule) {
        return (MetricsLogger) Preconditions.checkNotNullFromProvides(androidInternalsModule.provideMetricsLogger());
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MetricsLogger m1889get() {
        return provideMetricsLogger(this.module);
    }
}