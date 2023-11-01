package com.android.settingslib.bluetooth;

import android.content.Context;
import android.os.Handler;
import android.os.UserHandle;

/* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/LocalBluetoothManager.class */
public class LocalBluetoothManager {
    public static LocalBluetoothManager sInstance;
    public final CachedBluetoothDeviceManager mCachedDeviceManager;
    public final Context mContext;
    public final BluetoothEventManager mEventManager;
    public final LocalBluetoothAdapter mLocalAdapter;
    public final LocalBluetoothProfileManager mProfileManager;

    /* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/LocalBluetoothManager$BluetoothManagerCallback.class */
    public interface BluetoothManagerCallback {
        void onBluetoothManagerInitialized(Context context, LocalBluetoothManager localBluetoothManager);
    }

    public LocalBluetoothManager(LocalBluetoothAdapter localBluetoothAdapter, Context context, Handler handler, UserHandle userHandle) {
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        this.mLocalAdapter = localBluetoothAdapter;
        CachedBluetoothDeviceManager cachedBluetoothDeviceManager = new CachedBluetoothDeviceManager(applicationContext, this);
        this.mCachedDeviceManager = cachedBluetoothDeviceManager;
        BluetoothEventManager bluetoothEventManager = new BluetoothEventManager(localBluetoothAdapter, cachedBluetoothDeviceManager, applicationContext, handler, userHandle);
        this.mEventManager = bluetoothEventManager;
        LocalBluetoothProfileManager localBluetoothProfileManager = new LocalBluetoothProfileManager(applicationContext, localBluetoothAdapter, cachedBluetoothDeviceManager, bluetoothEventManager);
        this.mProfileManager = localBluetoothProfileManager;
        localBluetoothProfileManager.updateLocalProfiles();
        bluetoothEventManager.readPairedDevices();
    }

    public static LocalBluetoothManager create(Context context, Handler handler, UserHandle userHandle) {
        LocalBluetoothAdapter localBluetoothAdapter = LocalBluetoothAdapter.getInstance();
        if (localBluetoothAdapter == null) {
            return null;
        }
        return new LocalBluetoothManager(localBluetoothAdapter, context, handler, userHandle);
    }

    public static LocalBluetoothManager getInstance(Context context, BluetoothManagerCallback bluetoothManagerCallback) {
        synchronized (LocalBluetoothManager.class) {
            try {
                if (sInstance == null) {
                    LocalBluetoothAdapter localBluetoothAdapter = LocalBluetoothAdapter.getInstance();
                    if (localBluetoothAdapter == null) {
                        return null;
                    }
                    sInstance = new LocalBluetoothManager(localBluetoothAdapter, context, null, null);
                    if (bluetoothManagerCallback != null) {
                        bluetoothManagerCallback.onBluetoothManagerInitialized(context.getApplicationContext(), sInstance);
                    }
                }
                return sInstance;
            } finally {
            }
        }
    }

    public LocalBluetoothAdapter getBluetoothAdapter() {
        return this.mLocalAdapter;
    }

    public CachedBluetoothDeviceManager getCachedDeviceManager() {
        return this.mCachedDeviceManager;
    }

    public BluetoothEventManager getEventManager() {
        return this.mEventManager;
    }

    public LocalBluetoothProfileManager getProfileManager() {
        return this.mProfileManager;
    }
}