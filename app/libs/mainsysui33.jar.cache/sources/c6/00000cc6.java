package com.android.keyguard.clock;

import dagger.MembersInjector;
import java.util.List;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/keyguard/clock/ClockOptionsProvider_MembersInjector.class */
public final class ClockOptionsProvider_MembersInjector implements MembersInjector<ClockOptionsProvider> {
    public static void injectMClockInfosProvider(ClockOptionsProvider clockOptionsProvider, Provider<List<ClockInfo>> provider) {
        clockOptionsProvider.mClockInfosProvider = provider;
    }
}