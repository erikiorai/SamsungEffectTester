package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothUuid;
import android.content.Context;
import android.os.ParcelUuid;
import android.util.Log;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/A2dpProfile.class */
public class A2dpProfile implements LocalBluetoothProfile {
    public static final ParcelUuid[] SINK_UUIDS = {BluetoothUuid.A2DP_SINK, BluetoothUuid.ADV_AUDIO_DIST};
    public final BluetoothAdapter mBluetoothAdapter;
    public Context mContext;
    public final CachedBluetoothDeviceManager mDeviceManager;
    public boolean mIsProfileReady;
    public final LocalBluetoothProfileManager mProfileManager;
    public BluetoothA2dp mService;

    /* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/A2dpProfile$A2dpServiceListener.class */
    public final class A2dpServiceListener implements BluetoothProfile.ServiceListener {
        public A2dpServiceListener() {
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            A2dpProfile.this.mService = (BluetoothA2dp) bluetoothProfile;
            List<BluetoothDevice> connectedDevices = A2dpProfile.this.mService.getConnectedDevices();
            while (!connectedDevices.isEmpty()) {
                BluetoothDevice remove = connectedDevices.remove(0);
                CachedBluetoothDevice findDevice = A2dpProfile.this.mDeviceManager.findDevice(remove);
                CachedBluetoothDevice cachedBluetoothDevice = findDevice;
                if (findDevice == null) {
                    Log.w("A2dpProfile", "A2dpProfile found new device: " + remove);
                    cachedBluetoothDevice = A2dpProfile.this.mDeviceManager.addDevice(remove);
                }
                cachedBluetoothDevice.onProfileStateChanged(A2dpProfile.this, 2);
                cachedBluetoothDevice.refresh();
            }
            A2dpProfile.this.mIsProfileReady = true;
            A2dpProfile.this.mProfileManager.callServiceConnectedListeners();
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceDisconnected(int i) {
            A2dpProfile.this.mIsProfileReady = false;
        }
    }

    public A2dpProfile(Context context, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mContext = context;
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mProfileManager = localBluetoothProfileManager;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mBluetoothAdapter = defaultAdapter;
        defaultAdapter.getProfileProxy(context, new A2dpServiceListener(), 2);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public boolean accessProfileEnabled() {
        return true;
    }

    public void finalize() {
        Log.d("A2dpProfile", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(2, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("A2dpProfile", "Error cleaning up A2DP proxy", th);
            }
        }
    }

    public BluetoothDevice getActiveDevice() {
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        BluetoothDevice bluetoothDevice = null;
        if (bluetoothAdapter == null) {
            return null;
        }
        List activeDevices = bluetoothAdapter.getActiveDevices(2);
        if (activeDevices.size() > 0) {
            bluetoothDevice = (BluetoothDevice) activeDevices.get(0);
        }
        return bluetoothDevice;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothA2dp bluetoothA2dp = this.mService;
        if (bluetoothA2dp == null) {
            return 0;
        }
        return bluetoothA2dp.getConnectionState(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getDrawableResource(BluetoothClass bluetoothClass) {
        return 17302335;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getProfileId() {
        return 2;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public boolean setEnabled(BluetoothDevice bluetoothDevice, boolean z) {
        boolean connectionPolicy;
        BluetoothA2dp bluetoothA2dp = this.mService;
        if (bluetoothA2dp == null) {
            return false;
        }
        if (z) {
            connectionPolicy = false;
            if (bluetoothA2dp.getConnectionPolicy(bluetoothDevice) < 100) {
                connectionPolicy = this.mService.setConnectionPolicy(bluetoothDevice, 100);
            }
        } else {
            connectionPolicy = bluetoothA2dp.setConnectionPolicy(bluetoothDevice, 0);
        }
        return connectionPolicy;
    }

    public String toString() {
        return "A2DP";
    }
}