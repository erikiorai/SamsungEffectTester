package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothPan;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;
import java.util.HashMap;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/PanProfile.class */
public class PanProfile implements LocalBluetoothProfile {
    public final HashMap<BluetoothDevice, Integer> mDeviceRoleMap = new HashMap<>();
    public boolean mIsProfileReady;
    public BluetoothPan mService;

    /* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/PanProfile$PanServiceListener.class */
    public final class PanServiceListener implements BluetoothProfile.ServiceListener {
        public PanServiceListener() {
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            PanProfile.this.mService = (BluetoothPan) bluetoothProfile;
            PanProfile.this.mIsProfileReady = true;
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceDisconnected(int i) {
            PanProfile.this.mIsProfileReady = false;
        }
    }

    public PanProfile(Context context) {
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, new PanServiceListener(), 5);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public boolean accessProfileEnabled() {
        return true;
    }

    public void finalize() {
        Log.d("PanProfile", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(5, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("PanProfile", "Error cleaning up PAN proxy", th);
            }
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothPan bluetoothPan = this.mService;
        if (bluetoothPan == null) {
            return 0;
        }
        return bluetoothPan.getConnectionState(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getDrawableResource(BluetoothClass bluetoothClass) {
        return 17302340;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getProfileId() {
        return 5;
    }

    public boolean isLocalRoleNap(BluetoothDevice bluetoothDevice) {
        boolean z = false;
        if (this.mDeviceRoleMap.containsKey(bluetoothDevice)) {
            z = false;
            if (this.mDeviceRoleMap.get(bluetoothDevice).intValue() == 1) {
                z = true;
            }
        }
        return z;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public boolean setEnabled(BluetoothDevice bluetoothDevice, boolean z) {
        boolean connectionPolicy;
        BluetoothPan bluetoothPan = this.mService;
        if (bluetoothPan == null) {
            return false;
        }
        if (z) {
            List<BluetoothDevice> connectedDevices = bluetoothPan.getConnectedDevices();
            if (connectedDevices != null) {
                for (BluetoothDevice bluetoothDevice2 : connectedDevices) {
                    this.mService.setConnectionPolicy(bluetoothDevice2, 0);
                }
            }
            connectionPolicy = this.mService.setConnectionPolicy(bluetoothDevice, 100);
        } else {
            connectionPolicy = bluetoothPan.setConnectionPolicy(bluetoothDevice, 0);
        }
        return connectionPolicy;
    }

    public void setLocalRole(BluetoothDevice bluetoothDevice, int i) {
        this.mDeviceRoleMap.put(bluetoothDevice, Integer.valueOf(i));
    }

    public String toString() {
        return "PAN";
    }
}