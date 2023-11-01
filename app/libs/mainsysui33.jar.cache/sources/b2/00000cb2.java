package com.android.keyguard.clock;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.util.List;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/keyguard/clock/ClockInfoModule_ProvideClockInfoListFactory.class */
public final class ClockInfoModule_ProvideClockInfoListFactory implements Factory<List<ClockInfo>> {
    public final Provider<ClockManager> clockManagerProvider;

    public ClockInfoModule_ProvideClockInfoListFactory(Provider<ClockManager> provider) {
        this.clockManagerProvider = provider;
    }

    public static ClockInfoModule_ProvideClockInfoListFactory create(Provider<ClockManager> provider) {
        return new ClockInfoModule_ProvideClockInfoListFactory(provider);
    }

    public static List<ClockInfo> provideClockInfoList(ClockManager clockManager) {
        return (List) Preconditions.checkNotNullFromProvides(ClockInfoModule.provideClockInfoList(clockManager));
    }

    /* JADX DEBUG: Method merged with bridge method */
    public List<ClockInfo> get() {
        return provideClockInfoList((ClockManager) this.clockManagerProvider.get());
    }
}