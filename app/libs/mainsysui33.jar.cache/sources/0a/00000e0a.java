package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;

/* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/LocalBluetoothProfile.class */
public interface LocalBluetoothProfile {
    boolean accessProfileEnabled();

    int getConnectionStatus(BluetoothDevice bluetoothDevice);

    int getDrawableResource(BluetoothClass bluetoothClass);

    int getProfileId();

    boolean setEnabled(BluetoothDevice bluetoothDevice, boolean z);
}