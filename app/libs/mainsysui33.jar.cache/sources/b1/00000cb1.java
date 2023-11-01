package com.android.keyguard.clock;

import java.util.List;

@Deprecated
/* loaded from: mainsysui33.jar:com/android/keyguard/clock/ClockInfoModule.class */
public abstract class ClockInfoModule {
    public static List<ClockInfo> provideClockInfoList(ClockManager clockManager) {
        return clockManager.getClockInfos();
    }
}