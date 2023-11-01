package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothSap;
import android.bluetooth.BluetoothUuid;
import android.content.Context;
import android.os.ParcelUuid;
import android.util.Log;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/SapProfile.class */
public final class SapProfile implements LocalBluetoothProfile {
    public static final ParcelUuid[] UUIDS = {BluetoothUuid.SAP};
    public final CachedBluetoothDeviceManager mDeviceManager;
    public boolean mIsProfileReady;
    public final LocalBluetoothProfileManager mProfileManager;
    public BluetoothSap mService;

    /* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/SapProfile$SapServiceListener.class */
    public final class SapServiceListener implements BluetoothProfile.ServiceListener {
        public SapServiceListener() {
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            SapProfile.this.mService = (BluetoothSap) bluetoothProfile;
            List connectedDevices = SapProfile.this.mService.getConnectedDevices();
            while (!connectedDevices.isEmpty()) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) connectedDevices.remove(0);
                CachedBluetoothDevice findDevice = SapProfile.this.mDeviceManager.findDevice(bluetoothDevice);
                CachedBluetoothDevice cachedBluetoothDevice = findDevice;
                if (findDevice == null) {
                    Log.w("SapProfile", "SapProfile found new device: " + bluetoothDevice);
                    cachedBluetoothDevice = SapProfile.this.mDeviceManager.addDevice(bluetoothDevice);
                }
                cachedBluetoothDevice.onProfileStateChanged(SapProfile.this, 2);
                cachedBluetoothDevice.refresh();
            }
            SapProfile.this.mProfileManager.callServiceConnectedListeners();
            SapProfile.this.mIsProfileReady = true;
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceDisconnected(int i) {
            SapProfile.this.mProfileManager.callServiceDisconnectedListeners();
            SapProfile.this.mIsProfileReady = false;
        }
    }

    public SapProfile(Context context, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mProfileManager = localBluetoothProfileManager;
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, new SapServiceListener(), 10);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public boolean accessProfileEnabled() {
        return true;
    }

    public void finalize() {
        Log.d("SapProfile", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(10, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("SapProfile", "Error cleaning up SAP proxy", th);
            }
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothSap bluetoothSap = this.mService;
        if (bluetoothSap == null) {
            return 0;
        }
        return bluetoothSap.getConnectionState(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getDrawableResource(BluetoothClass bluetoothClass) {
        return 17302822;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getProfileId() {
        return 10;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public boolean setEnabled(BluetoothDevice bluetoothDevice, boolean z) {
        boolean connectionPolicy;
        BluetoothSap bluetoothSap = this.mService;
        if (bluetoothSap == null) {
            return false;
        }
        if (z) {
            connectionPolicy = false;
            if (bluetoothSap.getConnectionPolicy(bluetoothDevice) < 100) {
                connectionPolicy = this.mService.setConnectionPolicy(bluetoothDevice, 100);
            }
        } else {
            connectionPolicy = bluetoothSap.setConnectionPolicy(bluetoothDevice, 0);
        }
        return connectionPolicy;
    }

    public String toString() {
        return "SAP";
    }
}