package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothLeBroadcastAssistant;
import android.bluetooth.BluetoothLeBroadcastMetadata;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/LocalBluetoothLeBroadcastAssistant.class */
public class LocalBluetoothLeBroadcastAssistant implements LocalBluetoothProfile {
    public BluetoothLeBroadcastMetadata.Builder mBuilder;
    public final CachedBluetoothDeviceManager mDeviceManager;
    public boolean mIsProfileReady;
    public LocalBluetoothProfileManager mProfileManager;
    public BluetoothLeBroadcastAssistant mService;
    public final BluetoothProfile.ServiceListener mServiceListener;

    public LocalBluetoothLeBroadcastAssistant(Context context, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, LocalBluetoothProfileManager localBluetoothProfileManager) {
        BluetoothProfile.ServiceListener serviceListener = new BluetoothProfile.ServiceListener() { // from class: com.android.settingslib.bluetooth.LocalBluetoothLeBroadcastAssistant.1
            @Override // android.bluetooth.BluetoothProfile.ServiceListener
            public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
                Log.d("LocalBluetoothLeBroadcastAssistant", "Bluetooth service connected");
                LocalBluetoothLeBroadcastAssistant.this.mService = (BluetoothLeBroadcastAssistant) bluetoothProfile;
                List connectedDevices = LocalBluetoothLeBroadcastAssistant.this.mService.getConnectedDevices();
                while (!connectedDevices.isEmpty()) {
                    BluetoothDevice bluetoothDevice = (BluetoothDevice) connectedDevices.remove(0);
                    CachedBluetoothDevice findDevice = LocalBluetoothLeBroadcastAssistant.this.mDeviceManager.findDevice(bluetoothDevice);
                    CachedBluetoothDevice cachedBluetoothDevice = findDevice;
                    if (findDevice == null) {
                        Log.d("LocalBluetoothLeBroadcastAssistant", "LocalBluetoothLeBroadcastAssistant found new device: " + bluetoothDevice);
                        cachedBluetoothDevice = LocalBluetoothLeBroadcastAssistant.this.mDeviceManager.addDevice(bluetoothDevice);
                    }
                    cachedBluetoothDevice.onProfileStateChanged(LocalBluetoothLeBroadcastAssistant.this, 2);
                    cachedBluetoothDevice.refresh();
                }
                LocalBluetoothLeBroadcastAssistant.this.mProfileManager.callServiceConnectedListeners();
                LocalBluetoothLeBroadcastAssistant.this.mIsProfileReady = true;
            }

            @Override // android.bluetooth.BluetoothProfile.ServiceListener
            public void onServiceDisconnected(int i) {
                if (i != 29) {
                    Log.d("LocalBluetoothLeBroadcastAssistant", "The profile is not LE_AUDIO_BROADCAST_ASSISTANT");
                    return;
                }
                Log.d("LocalBluetoothLeBroadcastAssistant", "Bluetooth service disconnected");
                LocalBluetoothLeBroadcastAssistant.this.mProfileManager.callServiceDisconnectedListeners();
                LocalBluetoothLeBroadcastAssistant.this.mIsProfileReady = false;
            }
        };
        this.mServiceListener = serviceListener;
        this.mProfileManager = localBluetoothProfileManager;
        this.mDeviceManager = cachedBluetoothDeviceManager;
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, serviceListener, 29);
        this.mBuilder = new BluetoothLeBroadcastMetadata.Builder();
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public boolean accessProfileEnabled() {
        return false;
    }

    public void finalize() {
        Log.d("LocalBluetoothLeBroadcastAssistant", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(29, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("LocalBluetoothLeBroadcastAssistant", "Error cleaning up LeAudio proxy", th);
            }
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant = this.mService;
        if (bluetoothLeBroadcastAssistant == null) {
            return 0;
        }
        return bluetoothLeBroadcastAssistant.getConnectionState(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getDrawableResource(BluetoothClass bluetoothClass) {
        return 0;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getProfileId() {
        return 29;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public boolean setEnabled(BluetoothDevice bluetoothDevice, boolean z) {
        BluetoothLeBroadcastAssistant bluetoothLeBroadcastAssistant = this.mService;
        boolean z2 = false;
        if (bluetoothLeBroadcastAssistant != null) {
            if (bluetoothDevice == null) {
                z2 = false;
            } else if (z) {
                z2 = false;
                if (bluetoothLeBroadcastAssistant.getConnectionPolicy(bluetoothDevice) < 100) {
                    z2 = this.mService.setConnectionPolicy(bluetoothDevice, 100);
                }
            } else {
                z2 = bluetoothLeBroadcastAssistant.setConnectionPolicy(bluetoothDevice, 0);
            }
        }
        return z2;
    }

    public String toString() {
        return "LE_AUDIO_BROADCAST_ASSISTANT";
    }
}