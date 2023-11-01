package com.android.settingslib.bluetooth;

import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.FrameworkStatsLog;
import java.util.HashMap;

/* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/HearingAidStatsLogUtils.class */
public final class HearingAidStatsLogUtils {
    public static final HashMap<String, Integer> sDeviceAddressToBondEntryMap = new HashMap<>();

    @VisibleForTesting
    public static HashMap<String, Integer> getDeviceAddressToBondEntryMap() {
        return sDeviceAddressToBondEntryMap;
    }

    public static void logHearingAidInfo(CachedBluetoothDevice cachedBluetoothDevice) {
        String address = cachedBluetoothDevice.getAddress();
        HashMap<String, Integer> hashMap = sDeviceAddressToBondEntryMap;
        if (!hashMap.containsKey(address)) {
            Log.w("HearingAidStatsLogUtils", "The device address was not found. Hearing aid device info is not logged.");
            return;
        }
        FrameworkStatsLog.write(513, cachedBluetoothDevice.getDeviceMode(), cachedBluetoothDevice.getDeviceSide(), hashMap.getOrDefault(address, -1).intValue());
        hashMap.remove(address);
    }
}