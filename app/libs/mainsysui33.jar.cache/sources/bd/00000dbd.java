package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothA2dpSink;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothUuid;
import android.content.Context;
import android.os.ParcelUuid;
import android.util.Log;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/A2dpSinkProfile.class */
public final class A2dpSinkProfile implements LocalBluetoothProfile {
    public static final ParcelUuid[] SRC_UUIDS = {BluetoothUuid.A2DP_SOURCE, BluetoothUuid.ADV_AUDIO_DIST};
    public final CachedBluetoothDeviceManager mDeviceManager;
    public boolean mIsProfileReady;
    public final LocalBluetoothProfileManager mProfileManager;
    public BluetoothA2dpSink mService;

    /* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/A2dpSinkProfile$A2dpSinkServiceListener.class */
    public final class A2dpSinkServiceListener implements BluetoothProfile.ServiceListener {
        public A2dpSinkServiceListener() {
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            A2dpSinkProfile.this.mService = (BluetoothA2dpSink) bluetoothProfile;
            List connectedDevices = A2dpSinkProfile.this.mService.getConnectedDevices();
            while (!connectedDevices.isEmpty()) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) connectedDevices.remove(0);
                CachedBluetoothDevice findDevice = A2dpSinkProfile.this.mDeviceManager.findDevice(bluetoothDevice);
                CachedBluetoothDevice cachedBluetoothDevice = findDevice;
                if (findDevice == null) {
                    Log.w("A2dpSinkProfile", "A2dpSinkProfile found new device: " + bluetoothDevice);
                    cachedBluetoothDevice = A2dpSinkProfile.this.mDeviceManager.addDevice(bluetoothDevice);
                }
                cachedBluetoothDevice.onProfileStateChanged(A2dpSinkProfile.this, 2);
                cachedBluetoothDevice.refresh();
            }
            A2dpSinkProfile.this.mIsProfileReady = true;
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceDisconnected(int i) {
            A2dpSinkProfile.this.mIsProfileReady = false;
        }
    }

    public A2dpSinkProfile(Context context, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mProfileManager = localBluetoothProfileManager;
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, new A2dpSinkServiceListener(), 11);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public boolean accessProfileEnabled() {
        return true;
    }

    public void finalize() {
        Log.d("A2dpSinkProfile", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(11, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("A2dpSinkProfile", "Error cleaning up A2DP proxy", th);
            }
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothA2dpSink bluetoothA2dpSink = this.mService;
        if (bluetoothA2dpSink == null) {
            return 0;
        }
        return bluetoothA2dpSink.getConnectionState(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getDrawableResource(BluetoothClass bluetoothClass) {
        return 17302335;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getProfileId() {
        return 11;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public boolean setEnabled(BluetoothDevice bluetoothDevice, boolean z) {
        boolean connectionPolicy;
        BluetoothA2dpSink bluetoothA2dpSink = this.mService;
        if (bluetoothA2dpSink == null) {
            return false;
        }
        if (z) {
            connectionPolicy = false;
            if (bluetoothA2dpSink.getConnectionPolicy(bluetoothDevice) < 100) {
                connectionPolicy = this.mService.setConnectionPolicy(bluetoothDevice, 100);
            }
        } else {
            connectionPolicy = bluetoothA2dpSink.setConnectionPolicy(bluetoothDevice, 0);
        }
        return connectionPolicy;
    }

    public String toString() {
        return "A2DPSink";
    }
}