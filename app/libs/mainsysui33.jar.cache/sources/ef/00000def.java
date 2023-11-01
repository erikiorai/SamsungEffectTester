package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHearingAid;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/HearingAidProfile.class */
public class HearingAidProfile implements LocalBluetoothProfile {
    public static boolean V = true;
    public final BluetoothAdapter mBluetoothAdapter;
    public Context mContext;
    public final CachedBluetoothDeviceManager mDeviceManager;
    public boolean mIsProfileReady;
    public final LocalBluetoothProfileManager mProfileManager;
    public BluetoothHearingAid mService;

    /* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/HearingAidProfile$HearingAidServiceListener.class */
    public final class HearingAidServiceListener implements BluetoothProfile.ServiceListener {
        public HearingAidServiceListener() {
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            HearingAidProfile.this.mService = (BluetoothHearingAid) bluetoothProfile;
            List<BluetoothDevice> connectedDevices = HearingAidProfile.this.mService.getConnectedDevices();
            while (!connectedDevices.isEmpty()) {
                BluetoothDevice remove = connectedDevices.remove(0);
                CachedBluetoothDevice findDevice = HearingAidProfile.this.mDeviceManager.findDevice(remove);
                CachedBluetoothDevice cachedBluetoothDevice = findDevice;
                if (findDevice == null) {
                    if (HearingAidProfile.V) {
                        Log.d("HearingAidProfile", "HearingAidProfile found new device: " + remove);
                    }
                    cachedBluetoothDevice = HearingAidProfile.this.mDeviceManager.addDevice(remove);
                }
                cachedBluetoothDevice.onProfileStateChanged(HearingAidProfile.this, 2);
                cachedBluetoothDevice.refresh();
            }
            HearingAidProfile.this.mDeviceManager.updateHearingAidsDevices();
            HearingAidProfile.this.mIsProfileReady = true;
            HearingAidProfile.this.mProfileManager.callServiceConnectedListeners();
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceDisconnected(int i) {
            HearingAidProfile.this.mIsProfileReady = false;
        }
    }

    public HearingAidProfile(Context context, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mContext = context;
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mProfileManager = localBluetoothProfileManager;
        BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mBluetoothAdapter = defaultAdapter;
        defaultAdapter.getProfileProxy(context, new HearingAidServiceListener(), 21);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public boolean accessProfileEnabled() {
        return false;
    }

    public void finalize() {
        Log.d("HearingAidProfile", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(21, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("HearingAidProfile", "Error cleaning up Hearing Aid proxy", th);
            }
        }
    }

    public List<BluetoothDevice> getActiveDevices() {
        BluetoothAdapter bluetoothAdapter = this.mBluetoothAdapter;
        return bluetoothAdapter == null ? new ArrayList() : bluetoothAdapter.getActiveDevices(21);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothHearingAid bluetoothHearingAid = this.mService;
        if (bluetoothHearingAid == null) {
            return 0;
        }
        return bluetoothHearingAid.getConnectionState(bluetoothDevice);
    }

    public int getDeviceMode(BluetoothDevice bluetoothDevice) {
        BluetoothHearingAid bluetoothHearingAid = this.mService;
        if (bluetoothHearingAid == null) {
            Log.w("HearingAidProfile", "Proxy not attached to HearingAidService");
            return -1;
        }
        try {
            Method declaredMethod = bluetoothHearingAid.getClass().getDeclaredMethod("getDeviceModeInternal", BluetoothDevice.class);
            declaredMethod.setAccessible(true);
            return ((Integer) declaredMethod.invoke(this.mService, bluetoothDevice)).intValue();
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Log.e("HearingAidProfile", "fail to get getDeviceModeInternal\n" + e.toString() + "\n" + Log.getStackTraceString(new Throwable()));
            return -1;
        }
    }

    public int getDeviceSide(BluetoothDevice bluetoothDevice) {
        BluetoothHearingAid bluetoothHearingAid = this.mService;
        if (bluetoothHearingAid == null) {
            Log.w("HearingAidProfile", "Proxy not attached to HearingAidService");
            return -1;
        }
        try {
            Method declaredMethod = bluetoothHearingAid.getClass().getDeclaredMethod("getDeviceSideInternal", BluetoothDevice.class);
            declaredMethod.setAccessible(true);
            return ((Integer) declaredMethod.invoke(this.mService, bluetoothDevice)).intValue();
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Log.e("HearingAidProfile", "fail to get getDeviceSideInternal\n" + e.toString() + "\n" + Log.getStackTraceString(new Throwable()));
            return -1;
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getDrawableResource(BluetoothClass bluetoothClass) {
        return 17302337;
    }

    public long getHiSyncId(BluetoothDevice bluetoothDevice) {
        BluetoothHearingAid bluetoothHearingAid = this.mService;
        if (bluetoothHearingAid == null || bluetoothDevice == null) {
            return 0L;
        }
        return bluetoothHearingAid.getHiSyncId(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getProfileId() {
        return 21;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public boolean setEnabled(BluetoothDevice bluetoothDevice, boolean z) {
        BluetoothHearingAid bluetoothHearingAid = this.mService;
        boolean z2 = false;
        if (bluetoothHearingAid != null) {
            if (bluetoothDevice == null) {
                z2 = false;
            } else if (z) {
                z2 = false;
                if (bluetoothHearingAid.getConnectionPolicy(bluetoothDevice) < 100) {
                    z2 = this.mService.setConnectionPolicy(bluetoothDevice, 100);
                }
            } else {
                z2 = bluetoothHearingAid.setConnectionPolicy(bluetoothDevice, 0);
            }
        }
        return z2;
    }

    public String toString() {
        return "HearingAid";
    }
}