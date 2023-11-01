package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHidHost;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/HidProfile.class */
public class HidProfile implements LocalBluetoothProfile {
    public final CachedBluetoothDeviceManager mDeviceManager;
    public boolean mIsProfileReady;
    public final LocalBluetoothProfileManager mProfileManager;
    public BluetoothHidHost mService;

    /* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/HidProfile$HidHostServiceListener.class */
    public final class HidHostServiceListener implements BluetoothProfile.ServiceListener {
        public HidHostServiceListener() {
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            HidProfile.this.mService = (BluetoothHidHost) bluetoothProfile;
            List connectedDevices = HidProfile.this.mService.getConnectedDevices();
            while (!connectedDevices.isEmpty()) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) connectedDevices.remove(0);
                CachedBluetoothDevice findDevice = HidProfile.this.mDeviceManager.findDevice(bluetoothDevice);
                CachedBluetoothDevice cachedBluetoothDevice = findDevice;
                if (findDevice == null) {
                    Log.w("HidProfile", "HidProfile found new device: " + bluetoothDevice);
                    cachedBluetoothDevice = HidProfile.this.mDeviceManager.addDevice(bluetoothDevice);
                }
                cachedBluetoothDevice.onProfileStateChanged(HidProfile.this, 2);
                cachedBluetoothDevice.refresh();
            }
            HidProfile.this.mIsProfileReady = true;
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceDisconnected(int i) {
            HidProfile.this.mIsProfileReady = false;
        }
    }

    public HidProfile(Context context, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mProfileManager = localBluetoothProfileManager;
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, new HidHostServiceListener(), 4);
    }

    public static int getHidClassDrawable(BluetoothClass bluetoothClass) {
        int deviceClass = bluetoothClass.getDeviceClass();
        if (deviceClass != 1344) {
            if (deviceClass != 1408) {
                return deviceClass != 1472 ? 17302339 : 17302535;
            }
            return 17302341;
        }
        return 17302535;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public boolean accessProfileEnabled() {
        return true;
    }

    public void finalize() {
        Log.d("HidProfile", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(4, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("HidProfile", "Error cleaning up HID proxy", th);
            }
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothHidHost bluetoothHidHost = this.mService;
        if (bluetoothHidHost == null) {
            return 0;
        }
        return bluetoothHidHost.getConnectionState(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getDrawableResource(BluetoothClass bluetoothClass) {
        if (bluetoothClass == null) {
            return 17302535;
        }
        return getHidClassDrawable(bluetoothClass);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getProfileId() {
        return 4;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public boolean setEnabled(BluetoothDevice bluetoothDevice, boolean z) {
        boolean connectionPolicy;
        BluetoothHidHost bluetoothHidHost = this.mService;
        if (bluetoothHidHost == null) {
            return false;
        }
        if (z) {
            connectionPolicy = false;
            if (bluetoothHidHost.getConnectionPolicy(bluetoothDevice) < 100) {
                connectionPolicy = this.mService.setConnectionPolicy(bluetoothDevice, 100);
            }
        } else {
            connectionPolicy = bluetoothHidHost.setConnectionPolicy(bluetoothDevice, 0);
        }
        return connectionPolicy;
    }

    public String toString() {
        return "HID";
    }
}