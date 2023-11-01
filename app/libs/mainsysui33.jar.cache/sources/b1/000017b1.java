package com.android.systemui.flags;

import android.util.Dumpable;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/FeatureFlags.class */
public interface FeatureFlags extends FlagListenable, Dumpable {
    boolean isEnabled(ReleasedFlag releasedFlag);

    boolean isEnabled(ResourceBooleanFlag resourceBooleanFlag);

    boolean isEnabled(SysPropBooleanFlag sysPropBooleanFlag);

    boolean isEnabled(UnreleasedFlag unreleasedFlag);
}