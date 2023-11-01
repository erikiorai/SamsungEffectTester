package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import java.util.Set;

@Deprecated
/* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/LocalBluetoothAdapter.class */
public class LocalBluetoothAdapter {
    public static LocalBluetoothAdapter sInstance;
    public final BluetoothAdapter mAdapter;
    public LocalBluetoothProfileManager mProfileManager;
    public int mState = Integer.MIN_VALUE;

    public LocalBluetoothAdapter(BluetoothAdapter bluetoothAdapter) {
        this.mAdapter = bluetoothAdapter;
    }

    public static LocalBluetoothAdapter getInstance() {
        LocalBluetoothAdapter localBluetoothAdapter;
        BluetoothAdapter defaultAdapter;
        synchronized (LocalBluetoothAdapter.class) {
            try {
                if (sInstance == null && (defaultAdapter = BluetoothAdapter.getDefaultAdapter()) != null) {
                    sInstance = new LocalBluetoothAdapter(defaultAdapter);
                }
                localBluetoothAdapter = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return localBluetoothAdapter;
    }

    public boolean enable() {
        return this.mAdapter.enable();
    }

    public BluetoothLeScanner getBluetoothLeScanner() {
        return this.mAdapter.getBluetoothLeScanner();
    }

    public int getBluetoothState() {
        int i;
        synchronized (this) {
            syncBluetoothState();
            i = this.mState;
        }
        return i;
    }

    public Set<BluetoothDevice> getBondedDevices() {
        return this.mAdapter.getBondedDevices();
    }

    public int getConnectionState() {
        return this.mAdapter.getConnectionState();
    }

    public int getState() {
        return this.mAdapter.getState();
    }

    public boolean setBluetoothEnabled(boolean z) {
        boolean enable = z ? this.mAdapter.enable() : this.mAdapter.disable();
        if (enable) {
            setBluetoothStateInt(z ? 11 : 13);
        } else {
            syncBluetoothState();
        }
        return enable;
    }

    public void setBluetoothStateInt(int i) {
        LocalBluetoothProfileManager localBluetoothProfileManager;
        synchronized (this) {
            if (this.mState == i) {
                return;
            }
            this.mState = i;
            if (i != 12 || (localBluetoothProfileManager = this.mProfileManager) == null) {
                return;
            }
            localBluetoothProfileManager.setBluetoothStateOn();
        }
    }

    public void setProfileManager(LocalBluetoothProfileManager localBluetoothProfileManager) {
        this.mProfileManager = localBluetoothProfileManager;
    }

    public boolean syncBluetoothState() {
        if (this.mAdapter.getState() != this.mState) {
            setBluetoothStateInt(this.mAdapter.getState());
            return true;
        }
        return false;
    }
}