package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothUuid;
import android.content.Context;
import android.os.ParcelUuid;
import android.util.Log;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/HeadsetProfile.class */
public class HeadsetProfile implements LocalBluetoothProfile {
    public static final ParcelUuid[] UUIDS = {BluetoothUuid.HSP, BluetoothUuid.HFP};
    public final BluetoothAdapter mBluetoothAdapter;
    public final CachedBluetoothDeviceManager mDeviceManager;
    public boolean mIsProfileReady;
    public final LocalBluetoothProfileManager mProfileManager;
    public BluetoothHeadset mService;

    /* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/HeadsetProfile$HeadsetServiceListener.class */
    public final class HeadsetServiceListener implements BluetoothProfile.ServiceListener {
        public HeadsetServiceListener() {
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            HeadsetProfile.this.mService = (BluetoothHeadset) bluetoothProfile;
            List<BluetoothDevice> connectedDevices = HeadsetProfile.this.mService.getConnectedDevices();
            while (!connectedDevices.isEmpty()) {
                BluetoothDevice remove = connectedDevices.remove(0);
                CachedBluetoothDevice findDevice = HeadsetProfile.this.mDeviceManager.findDevice(remove);
                CachedBluetoothDevice cachedBluetoothDevice = findDevice;
                if (findDevice == null) {
                    Log.w("HeadsetProfile", "HeadsetProfile found new device: " + remove);
                    cachedBluetoothDevice = HeadsetProfile.this.mDeviceManager.addDevice(remove);
                }
                cachedBluetoothDevice.onProfileStateChanged(HeadsetProfile.this, 2);
                cachedBluetoothDevice.refresh();
            }
            HeadsetProfile.this.mIsProfileReady = true;
            HeadsetProfile.this.mProfileManager.callServiceConnectedListeners();
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceDisconnected(int i) {
            HeadsetProfile.this.mProfileManager.callServiceDisconnectedListeners();
            HeadsetProfile.this.mIsProfileReady = false;
        }
    }

    public HeadsetProfile(Context context, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mProfileManager = localBluetoothProfileManager;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mBluetoothAdapter = defaultAdapter;
        defaultAdapter.getProfileProxy(context, new HeadsetServiceListener(), 1);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public boolean accessProfileEnabled() {
        return true;
    }

    public void finalize() {
        Log.d("HeadsetProfile", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(1, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("HeadsetProfile", "Error cleaning up HID proxy", th);
            }
        }
    }

    public BluetoothDevice getActiveDevice() {
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        BluetoothDevice bluetoothDevice = null;
        if (bluetoothAdapter == null) {
            return null;
        }
        List activeDevices = bluetoothAdapter.getActiveDevices(1);
        if (activeDevices.size() > 0) {
            bluetoothDevice = (BluetoothDevice) activeDevices.get(0);
        }
        return bluetoothDevice;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothHeadset bluetoothHeadset = this.mService;
        if (bluetoothHeadset == null) {
            return 0;
        }
        return bluetoothHeadset.getConnectionState(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getDrawableResource(BluetoothClass bluetoothClass) {
        return 17302336;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getProfileId() {
        return 1;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public boolean setEnabled(BluetoothDevice bluetoothDevice, boolean z) {
        boolean connectionPolicy;
        BluetoothHeadset bluetoothHeadset = this.mService;
        if (bluetoothHeadset == null) {
            return false;
        }
        if (z) {
            connectionPolicy = false;
            if (bluetoothHeadset.getConnectionPolicy(bluetoothDevice) < 100) {
                connectionPolicy = this.mService.setConnectionPolicy(bluetoothDevice, 100);
            }
        } else {
            connectionPolicy = bluetoothHeadset.setConnectionPolicy(bluetoothDevice, 0);
        }
        return connectionPolicy;
    }

    public String toString() {
        return "HEADSET";
    }
}