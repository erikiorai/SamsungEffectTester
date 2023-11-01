package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothPbap;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothUuid;
import android.content.Context;
import android.os.ParcelUuid;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;

/* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/PbapServerProfile.class */
public class PbapServerProfile implements LocalBluetoothProfile {
    @VisibleForTesting
    public static final String NAME = "PBAP Server";
    public static final ParcelUuid[] PBAB_CLIENT_UUIDS = {BluetoothUuid.HSP, BluetoothUuid.HFP, BluetoothUuid.PBAP_PCE};
    public boolean mIsProfileReady;
    public BluetoothPbap mService;

    /* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/PbapServerProfile$PbapServiceListener.class */
    public final class PbapServiceListener implements BluetoothProfile.ServiceListener {
        public PbapServiceListener() {
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceConnected(int i, BluetoothProfile bluetoothProfile) {
            PbapServerProfile.this.mService = (BluetoothPbap) bluetoothProfile;
            PbapServerProfile.this.mIsProfileReady = true;
        }

        @Override // android.bluetooth.BluetoothProfile.ServiceListener
        public void onServiceDisconnected(int i) {
            PbapServerProfile.this.mIsProfileReady = false;
        }
    }

    public PbapServerProfile(Context context) {
        BluetoothAdapter.getDefaultAdapter().getProfileProxy(context, new PbapServiceListener(), 6);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public boolean accessProfileEnabled() {
        return true;
    }

    public void finalize() {
        Log.d("PbapServerProfile", "finalize()");
        if (this.mService != null) {
            try {
                BluetoothAdapter.getDefaultAdapter().closeProfileProxy(6, this.mService);
                this.mService = null;
            } catch (Throwable th) {
                Log.w("PbapServerProfile", "Error cleaning up PBAP proxy", th);
            }
        }
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getConnectionStatus(BluetoothDevice bluetoothDevice) {
        BluetoothPbap bluetoothPbap = this.mService;
        if (bluetoothPbap == null) {
            return 0;
        }
        return bluetoothPbap.getConnectionState(bluetoothDevice);
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getDrawableResource(BluetoothClass bluetoothClass) {
        return 17302822;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public int getProfileId() {
        return 6;
    }

    @Override // com.android.settingslib.bluetooth.LocalBluetoothProfile
    public boolean setEnabled(BluetoothDevice bluetoothDevice, boolean z) {
        BluetoothPbap bluetoothPbap = this.mService;
        boolean z2 = false;
        if (bluetoothPbap == null) {
            return false;
        }
        if (!z) {
            z2 = bluetoothPbap.setConnectionPolicy(bluetoothDevice, 0);
        }
        return z2;
    }

    public String toString() {
        return NAME;
    }
}