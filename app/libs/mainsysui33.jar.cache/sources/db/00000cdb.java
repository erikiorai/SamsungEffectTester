package com.android.keyguard.dagger;

import com.android.keyguard.CarrierText;
import com.android.systemui.R$id;
import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.statusbar.phone.KeyguardStatusBarView;

/* loaded from: mainsysui33.jar:com/android/keyguard/dagger/KeyguardStatusBarViewModule.class */
public abstract class KeyguardStatusBarViewModule {
    public static BatteryMeterView getBatteryMeterView(KeyguardStatusBarView keyguardStatusBarView) {
        return (BatteryMeterView) keyguardStatusBarView.findViewById(R$id.battery);
    }

    public static CarrierText getCarrierText(KeyguardStatusBarView keyguardStatusBarView) {
        return (CarrierText) keyguardStatusBarView.findViewById(R$id.keyguard_carrier_text);
    }
}