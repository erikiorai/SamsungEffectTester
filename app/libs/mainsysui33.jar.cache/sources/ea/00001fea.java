package com.android.systemui.power;

import com.android.settingslib.fuelgauge.Estimate;

/* loaded from: mainsysui33.jar:com/android/systemui/power/EnhancedEstimatesImpl.class */
public class EnhancedEstimatesImpl implements EnhancedEstimates {
    @Override // com.android.systemui.power.EnhancedEstimates
    public Estimate getEstimate() {
        return new Estimate(-1L, false, -1L);
    }

    @Override // com.android.systemui.power.EnhancedEstimates
    public boolean getLowWarningEnabled() {
        return true;
    }

    @Override // com.android.systemui.power.EnhancedEstimates
    public long getLowWarningThreshold() {
        return 0L;
    }

    @Override // com.android.systemui.power.EnhancedEstimates
    public long getSevereWarningThreshold() {
        return 0L;
    }

    @Override // com.android.systemui.power.EnhancedEstimates
    public boolean isHybridNotificationEnabled() {
        return false;
    }
}