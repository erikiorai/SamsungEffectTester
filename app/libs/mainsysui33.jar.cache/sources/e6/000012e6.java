package com.android.systemui.classifier;

import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/classifier/HistoryTracker_Factory.class */
public final class HistoryTracker_Factory implements Factory<HistoryTracker> {
    public final Provider<SystemClock> systemClockProvider;

    public HistoryTracker_Factory(Provider<SystemClock> provider) {
        this.systemClockProvider = provider;
    }

    public static HistoryTracker_Factory create(Provider<SystemClock> provider) {
        return new HistoryTracker_Factory(provider);
    }

    public static HistoryTracker newInstance(SystemClock systemClock) {
        return new HistoryTracker(systemClock);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public HistoryTracker m1717get() {
        return newInstance((SystemClock) this.systemClockProvider.get());
    }
}