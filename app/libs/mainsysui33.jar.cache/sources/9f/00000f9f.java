package com.android.systemui;

import com.android.internal.logging.MetricsLogger;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/ForegroundServicesDialog_Factory.class */
public final class ForegroundServicesDialog_Factory implements Factory<ForegroundServicesDialog> {
    public final Provider<MetricsLogger> metricsLoggerProvider;

    public ForegroundServicesDialog_Factory(Provider<MetricsLogger> provider) {
        this.metricsLoggerProvider = provider;
    }

    public static ForegroundServicesDialog_Factory create(Provider<MetricsLogger> provider) {
        return new ForegroundServicesDialog_Factory(provider);
    }

    public static ForegroundServicesDialog newInstance(MetricsLogger metricsLogger) {
        return new ForegroundServicesDialog(metricsLogger);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ForegroundServicesDialog m1265get() {
        return newInstance((MetricsLogger) this.metricsLoggerProvider.get());
    }
}