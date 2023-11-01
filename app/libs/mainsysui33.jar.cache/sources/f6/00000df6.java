package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHidDevice;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;

/* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/HidDeviceProfile.class */
public class HidDeviceProfile implements LocalBluetoothProfile {
    public final CachedBluetoothDeviceManager mDeviceManager;
    public boolean mIsProfileReady;
    public final LocalBluetoothProfileManager mProfileManager;
    public BluetoothHidDevice mService;

    /* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/HidDeviceProfile$HidDeviceServiceListener.class */
    public final class HidDeviceServiceListener implements BluetoothProfile.ServiceListener {
        public HidDeviceServiceListener() {
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            HidDeviceProfile.this.mService = (BluetoothHidDevice) bluetoothProfile;
            for (BluetoothDevice bluetoothDevice : HidDeviceProfile.this.mService.getConnectedDevices()) {
                CachedBluetoothDevice findDevice = HidDeviceProfile.this.mDeviceManager.findDevice(bluetoothDevice);
                CachedBluetoothDevice cachedBluetoothDevice = findDevice;
                if (findDevice == null) {
                    Log.w("HidDeviceProfile", "HidProfile found new device: " + bluetoothDevice);
                    cachedBluetoothDevice = HidDeviceProfile.this.mDeviceManager.addDevice(bluetoothDevice);
                }
                Log.d("HidDeviceProfile", "Connection status changed: " + cachedBluetoothDevice);
                cachedBluetoothDevice.onProfileStateChanged(HidDeviceProfile.this, 2);
                cachedBluetoothDevice.refresh();
            }
            HidDeviceProfile.this.mIsProfileReady = true;
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceDisconnected(int i) {
            HidDeviceProfile.this.mIsProfileReady = false;
        }
    }

    public HidDeviceProfile(Context context, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mProfileManager = localBluetoothProfileManager;
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, new HidDeviceServiceListener(), 19);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public boolean accessProfileEnabled() {
        return true;
    }

    public void finalize() {
        Log.d("HidDeviceProfile", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(19, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("HidDeviceProfile", "Error cleaning up HID proxy", th);
            }
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothHidDevice bluetoothHidDevice = this.mService;
        if (bluetoothHidDevice == null) {
            return 0;
        }
        return bluetoothHidDevice.getConnectionState(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getDrawableResource(BluetoothClass bluetoothClass) {
        return 17302339;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getProfileId() {
        return 19;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public boolean setEnabled(BluetoothDevice bluetoothDevice, boolean z) {
        boolean z2 = false;
        if (!z) {
            z2 = this.mService.setConnectionPolicy(bluetoothDevice, 0);
        }
        return z2;
    }

    public String toString() {
        return "HID DEVICE";
    }
}