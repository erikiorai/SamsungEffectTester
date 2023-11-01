package com.android.systemui.power;

import com.android.settingslib.fuelgauge.Estimate;

/* loaded from: mainsysui33.jar:com/android/systemui/power/EnhancedEstimates.class */
public interface EnhancedEstimates {
    Estimate getEstimate();

    boolean getLowWarningEnabled();

    long getLowWarningThreshold();

    long getSevereWarningThreshold();

    boolean isHybridNotificationEnabled();
}