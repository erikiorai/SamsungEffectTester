package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothVolumeControl;
import android.content.Context;
import android.util.Log;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/VolumeControlProfile.class */
public class VolumeControlProfile implements LocalBluetoothProfile {
    public static boolean DEBUG = true;
    public Context mContext;
    public final CachedBluetoothDeviceManager mDeviceManager;
    public boolean mIsProfileReady;
    public final LocalBluetoothProfileManager mProfileManager;
    public BluetoothVolumeControl mService;

    /* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/VolumeControlProfile$VolumeControlProfileServiceListener.class */
    public final class VolumeControlProfileServiceListener implements BluetoothProfile.ServiceListener {
        public VolumeControlProfileServiceListener() {
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            if (VolumeControlProfile.DEBUG) {
                Log.d("VolumeControlProfile", "Bluetooth service connected");
            }
            VolumeControlProfile.this.mService = (BluetoothVolumeControl) bluetoothProfile;
            List connectedDevices = VolumeControlProfile.this.mService.getConnectedDevices();
            while (!connectedDevices.isEmpty()) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) connectedDevices.remove(0);
                CachedBluetoothDevice findDevice = VolumeControlProfile.this.mDeviceManager.findDevice(bluetoothDevice);
                CachedBluetoothDevice cachedBluetoothDevice = findDevice;
                if (findDevice == null) {
                    if (VolumeControlProfile.DEBUG) {
                        Log.d("VolumeControlProfile", "VolumeControlProfile found new device: " + bluetoothDevice);
                    }
                    cachedBluetoothDevice = VolumeControlProfile.this.mDeviceManager.addDevice(bluetoothDevice);
                }
                cachedBluetoothDevice.onProfileStateChanged(VolumeControlProfile.this, 2);
                cachedBluetoothDevice.refresh();
            }
            VolumeControlProfile.this.mProfileManager.callServiceConnectedListeners();
            VolumeControlProfile.this.mIsProfileReady = true;
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceDisconnected(int i) {
            if (VolumeControlProfile.DEBUG) {
                Log.d("VolumeControlProfile", "Bluetooth service disconnected");
            }
            VolumeControlProfile.this.mProfileManager.callServiceDisconnectedListeners();
            VolumeControlProfile.this.mIsProfileReady = false;
        }
    }

    public VolumeControlProfile(Context context, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mContext = context;
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mProfileManager = localBluetoothProfileManager;
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, new VolumeControlProfileServiceListener(), 23);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public boolean accessProfileEnabled() {
        return false;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothVolumeControl bluetoothVolumeControl = this.mService;
        if (bluetoothVolumeControl == null) {
            return 0;
        }
        return bluetoothVolumeControl.getConnectionState(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getDrawableResource(BluetoothClass bluetoothClass) {
        return 0;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getProfileId() {
        return 23;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public boolean setEnabled(BluetoothDevice bluetoothDevice, boolean z) {
        boolean z2 = false;
        if (this.mService != null) {
            if (bluetoothDevice == null) {
                z2 = false;
            } else {
                if (DEBUG) {
                    Log.d("VolumeControlProfile", bluetoothDevice.getAnonymizedAddress() + " setEnabled: " + z);
                }
                if (z) {
                    z2 = false;
                    if (this.mService.getConnectionPolicy(bluetoothDevice) < 100) {
                        z2 = this.mService.setConnectionPolicy(bluetoothDevice, 100);
                    }
                } else {
                    z2 = this.mService.setConnectionPolicy(bluetoothDevice, 0);
                }
            }
        }
        return z2;
    }

    public String toString() {
        return "VCP";
    }
}