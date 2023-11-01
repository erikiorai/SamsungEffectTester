package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothCsipSetCoordinator;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.ParcelUuid;
import android.util.Log;
import java.util.List;
import java.util.Map;

/* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/CsipSetCoordinatorProfile.class */
public class CsipSetCoordinatorProfile implements LocalBluetoothProfile {
    public Context mContext;
    public final CachedBluetoothDeviceManager mDeviceManager;
    public boolean mIsProfileReady;
    public final LocalBluetoothProfileManager mProfileManager;
    public BluetoothCsipSetCoordinator mService;

    /* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/CsipSetCoordinatorProfile$CoordinatedSetServiceListener.class */
    public final class CoordinatedSetServiceListener implements BluetoothProfile.ServiceListener {
        public CoordinatedSetServiceListener() {
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            Log.d("CsipSetCoordinatorProfile", "Bluetooth service connected");
            CsipSetCoordinatorProfile.this.mService = (BluetoothCsipSetCoordinator) bluetoothProfile;
            List connectedDevices = CsipSetCoordinatorProfile.this.mService.getConnectedDevices();
            while (!connectedDevices.isEmpty()) {
                BluetoothDevice bluetoothDevice = (BluetoothDevice) connectedDevices.remove(0);
                CachedBluetoothDevice findDevice = CsipSetCoordinatorProfile.this.mDeviceManager.findDevice(bluetoothDevice);
                CachedBluetoothDevice cachedBluetoothDevice = findDevice;
                if (findDevice == null) {
                    Log.d("CsipSetCoordinatorProfile", "CsipSetCoordinatorProfile found new device: " + bluetoothDevice);
                    cachedBluetoothDevice = CsipSetCoordinatorProfile.this.mDeviceManager.addDevice(bluetoothDevice);
                }
                cachedBluetoothDevice.onProfileStateChanged(CsipSetCoordinatorProfile.this, 2);
                cachedBluetoothDevice.refresh();
            }
            CsipSetCoordinatorProfile.this.mDeviceManager.updateCsipDevices();
            CsipSetCoordinatorProfile.this.mProfileManager.callServiceConnectedListeners();
            CsipSetCoordinatorProfile.this.mIsProfileReady = true;
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceDisconnected(int i) {
            Log.d("CsipSetCoordinatorProfile", "Bluetooth service disconnected");
            CsipSetCoordinatorProfile.this.mProfileManager.callServiceDisconnectedListeners();
            CsipSetCoordinatorProfile.this.mIsProfileReady = false;
        }
    }

    public CsipSetCoordinatorProfile(Context context, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mContext = context;
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mProfileManager = localBluetoothProfileManager;
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, new CoordinatedSetServiceListener(), 25);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public boolean accessProfileEnabled() {
        return false;
    }

    public void finalize() {
        Log.d("CsipSetCoordinatorProfile", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(25, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("CsipSetCoordinatorProfile", "Error cleaning up CSIP Set Coordinator proxy", th);
            }
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothCsipSetCoordinator bluetoothCsipSetCoordinator = this.mService;
        if (bluetoothCsipSetCoordinator == null) {
            return 0;
        }
        return bluetoothCsipSetCoordinator.getConnectionState(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getDrawableResource(BluetoothClass bluetoothClass) {
        return 0;
    }

    public Map<Integer, ParcelUuid> getGroupUuidMapByDevice(BluetoothDevice bluetoothDevice) {
        BluetoothCsipSetCoordinator bluetoothCsipSetCoordinator = this.mService;
        if (bluetoothCsipSetCoordinator == null || bluetoothDevice == null) {
            return null;
        }
        return bluetoothCsipSetCoordinator.getGroupUuidMapByDevice(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getProfileId() {
        return 25;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public boolean setEnabled(BluetoothDevice bluetoothDevice, boolean z) {
        BluetoothCsipSetCoordinator bluetoothCsipSetCoordinator = this.mService;
        boolean z2 = false;
        if (bluetoothCsipSetCoordinator != null) {
            if (bluetoothDevice == null) {
                z2 = false;
            } else if (z) {
                z2 = false;
                if (bluetoothCsipSetCoordinator.getConnectionPolicy(bluetoothDevice) < 100) {
                    z2 = this.mService.setConnectionPolicy(bluetoothDevice, 100);
                }
            } else {
                z2 = bluetoothCsipSetCoordinator.setConnectionPolicy(bluetoothDevice, 0);
            }
        }
        return z2;
    }

    public String toString() {
        return "CSIP Set Coordinator";
    }
}