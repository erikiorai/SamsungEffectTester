package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothLeAudio;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;
import com.android.settingslib.R$drawable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/LeAudioProfile.class */
public class LeAudioProfile implements LocalBluetoothProfile {
    public static boolean DEBUG = true;
    public final BluetoothAdapter mBluetoothAdapter;
    public Context mContext;
    public final CachedBluetoothDeviceManager mDeviceManager;
    public boolean mIsProfileReady;
    public final LocalBluetoothProfileManager mProfileManager;
    public BluetoothLeAudio mService;

    /* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/LeAudioProfile$LeAudioServiceListener.class */
    public final class LeAudioServiceListener implements BluetoothProfile.ServiceListener {
        public LeAudioServiceListener() {
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            if (LeAudioProfile.DEBUG) {
                Log.d("LeAudioProfile", "Bluetooth service connected");
            }
            LeAudioProfile.this.mService = (BluetoothLeAudio) bluetoothProfile;
            List<BluetoothDevice> connectedDevices = LeAudioProfile.this.mService.getConnectedDevices();
            while (!connectedDevices.isEmpty()) {
                BluetoothDevice remove = connectedDevices.remove(0);
                CachedBluetoothDevice findDevice = LeAudioProfile.this.mDeviceManager.findDevice(remove);
                CachedBluetoothDevice cachedBluetoothDevice = findDevice;
                if (findDevice == null) {
                    if (LeAudioProfile.DEBUG) {
                        Log.d("LeAudioProfile", "LeAudioProfile found new device: " + remove);
                    }
                    cachedBluetoothDevice = LeAudioProfile.this.mDeviceManager.addDevice(remove);
                }
                cachedBluetoothDevice.onProfileStateChanged(LeAudioProfile.this, 2);
                cachedBluetoothDevice.refresh();
            }
            LeAudioProfile.this.mProfileManager.callServiceConnectedListeners();
            LeAudioProfile.this.mIsProfileReady = true;
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceDisconnected(int i) {
            if (LeAudioProfile.DEBUG) {
                Log.d("LeAudioProfile", "Bluetooth service disconnected");
            }
            LeAudioProfile.this.mProfileManager.callServiceDisconnectedListeners();
            LeAudioProfile.this.mIsProfileReady = false;
        }
    }

    public LeAudioProfile(Context context, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mContext = context;
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mProfileManager = localBluetoothProfileManager;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mBluetoothAdapter = defaultAdapter;
        defaultAdapter.getProfileProxy(context, new LeAudioServiceListener(), 22);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public boolean accessProfileEnabled() {
        return true;
    }

    public void finalize() {
        if (DEBUG) {
            Log.d("LeAudioProfile", "finalize()");
        }
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(22, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("LeAudioProfile", "Error cleaning up LeAudio proxy", th);
            }
        }
    }

    public List<BluetoothDevice> getActiveDevices() {
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        return bluetoothAdapter == null ? new ArrayList() : bluetoothAdapter.getActiveDevices(22);
    }

    public BluetoothDevice getConnectedGroupLeadDevice(int i) {
        if (DEBUG) {
            Log.d("LeAudioProfile", "getConnectedGroupLeadDevice");
        }
        BluetoothLeAudio bluetoothLeAudio = this.mService;
        if (bluetoothLeAudio == null) {
            Log.e("LeAudioProfile", "No service.");
            return null;
        }
        return bluetoothLeAudio.getConnectedGroupLeadDevice(i);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothLeAudio bluetoothLeAudio = this.mService;
        if (bluetoothLeAudio == null) {
            return 0;
        }
        return bluetoothLeAudio.getConnectionState(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getDrawableResource(BluetoothClass bluetoothClass) {
        return R$drawable.ic_bt_le_audio;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getProfileId() {
        return 22;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public boolean setEnabled(BluetoothDevice bluetoothDevice, boolean z) {
        BluetoothLeAudio bluetoothLeAudio = this.mService;
        boolean z2 = false;
        if (bluetoothLeAudio != null) {
            if (bluetoothDevice == null) {
                z2 = false;
            } else if (z) {
                z2 = false;
                if (bluetoothLeAudio.getConnectionPolicy(bluetoothDevice) < 100) {
                    z2 = this.mService.setConnectionPolicy(bluetoothDevice, 100);
                }
            } else {
                z2 = bluetoothLeAudio.setConnectionPolicy(bluetoothDevice, 0);
            }
        }
        return z2;
    }

    public String toString() {
        return "LE_AUDIO";
    }
}