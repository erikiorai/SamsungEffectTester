package com.android.settingslib.wifi;

import androidx.annotation.Keep;

/* loaded from: mainsysui33.jar:com/android/settingslib/wifi/WifiTrackerFactory.class */
public class WifiTrackerFactory {
    public static WifiTracker sTestingWifiTracker;

    @Keep
    public static void setTestingWifiTracker(WifiTracker wifiTracker) {
        sTestingWifiTracker = wifiTracker;
    }
}