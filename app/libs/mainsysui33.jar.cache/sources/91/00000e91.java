package com.android.settingslib.net;

import android.content.Context;
import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;

/* loaded from: mainsysui33.jar:com/android/settingslib/net/SignalStrengthUtil.class */
public class SignalStrengthUtil {
    public static boolean shouldInflateSignalStrength(Context context, int i) {
        CarrierConfigManager carrierConfigManager = (CarrierConfigManager) context.getSystemService(CarrierConfigManager.class);
        PersistableBundle configForSubId = carrierConfigManager != null ? carrierConfigManager.getConfigForSubId(i) : null;
        boolean z = false;
        if (configForSubId != null) {
            z = false;
            if (configForSubId.getBoolean("inflate_signal_strength_bool", false)) {
                z = true;
            }
        }
        return z;
    }
}