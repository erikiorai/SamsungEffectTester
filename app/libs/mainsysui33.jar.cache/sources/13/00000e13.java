package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothMap;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothUuid;
import android.content.Context;
import android.os.ParcelUuid;
import android.util.Log;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/MapProfile.class */
public class MapProfile implements LocalBluetoothProfile {
    public static final ParcelUuid[] UUIDS = {BluetoothUuid.MAP, BluetoothUuid.MNS, BluetoothUuid.MAS};
    public final CachedBluetoothDeviceManager mDeviceManager;
    public boolean mIsProfileReady;
    public final LocalBluetoothProfileManager mProfileManager;
    public BluetoothMap mService;

    /* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/MapProfile$MapServiceListener.class */
    public final class MapServiceListener implements BluetoothProfile.ServiceListener {
        public MapServiceListener() {
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            MapProfile.this.mService = (BluetoothMap) bluetoothProfile;
            List connectedDevices = MapProfile.this.mService.getConnectedDevices();
            while (!connectedDevices.isEmpty()) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) connectedDevices.remove(0);
                CachedBluetoothDevice findDevice = MapProfile.this.mDeviceManager.findDevice(bluetoothDevice);
                CachedBluetoothDevice cachedBluetoothDevice = findDevice;
                if (findDevice == null) {
                    Log.w("MapProfile", "MapProfile found new device: " + bluetoothDevice);
                    cachedBluetoothDevice = MapProfile.this.mDeviceManager.addDevice(bluetoothDevice);
                }
                cachedBluetoothDevice.onProfileStateChanged(MapProfile.this, 2);
                cachedBluetoothDevice.refresh();
            }
            MapProfile.this.mProfileManager.callServiceConnectedListeners();
            MapProfile.this.mIsProfileReady = true;
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceDisconnected(int i) {
            MapProfile.this.mProfileManager.callServiceDisconnectedListeners();
            MapProfile.this.mIsProfileReady = false;
        }
    }

    public MapProfile(Context context, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mProfileManager = localBluetoothProfileManager;
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, new MapServiceListener(), 9);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public boolean accessProfileEnabled() {
        return true;
    }

    public void finalize() {
        Log.d("MapProfile", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(9, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("MapProfile", "Error cleaning up MAP proxy", th);
            }
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothMap bluetoothMap = this.mService;
        if (bluetoothMap == null) {
            return 0;
        }
        return bluetoothMap.getConnectionState(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getDrawableResource(BluetoothClass bluetoothClass) {
        return 17302822;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getProfileId() {
        return 9;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public boolean setEnabled(BluetoothDevice bluetoothDevice, boolean z) {
        boolean connectionPolicy;
        BluetoothMap bluetoothMap = this.mService;
        if (bluetoothMap == null) {
            return false;
        }
        if (z) {
            connectionPolicy = false;
            if (bluetoothMap.getConnectionPolicy(bluetoothDevice) < 100) {
                connectionPolicy = this.mService.setConnectionPolicy(bluetoothDevice, 100);
            }
        } else {
            connectionPolicy = bluetoothMap.setConnectionPolicy(bluetoothDevice, 0);
        }
        return connectionPolicy;
    }

    public String toString() {
        return "MAP";
    }
}