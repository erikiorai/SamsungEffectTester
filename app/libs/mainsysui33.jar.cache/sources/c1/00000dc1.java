package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.util.Log;
import com.android.settingslib.R$string;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/BluetoothEventManager.class */
public class BluetoothEventManager {
    public static final boolean DEBUG = Log.isLoggable("BluetoothEventManager", 3);
    public final Context mContext;
    public final CachedBluetoothDeviceManager mDeviceManager;
    public final LocalBluetoothAdapter mLocalAdapter;
    public final android.os.Handler mReceiverHandler;
    public final UserHandle mUserHandle;
    public final BroadcastReceiver mBroadcastReceiver = new BluetoothBroadcastReceiver();
    public final BroadcastReceiver mProfileBroadcastReceiver = new BluetoothBroadcastReceiver();
    public final Collection<BluetoothCallback> mCallbacks = new CopyOnWriteArrayList();
    public final IntentFilter mAdapterIntentFilter = new IntentFilter();
    public final IntentFilter mProfileIntentFilter = new IntentFilter();
    public final Map<String, Handler> mHandlerMap = new HashMap();

    /* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/BluetoothEventManager$AclStateChangedHandler.class */
    public class AclStateChangedHandler implements Handler {
        public AclStateChangedHandler() {
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            int i;
            if (bluetoothDevice == null) {
                Log.w("BluetoothEventManager", "AclStateChangedHandler: device is null");
            } else if (BluetoothEventManager.this.mDeviceManager.isSubDevice(bluetoothDevice)) {
            } else {
                String action = intent.getAction();
                if (action == null) {
                    Log.w("BluetoothEventManager", "AclStateChangedHandler: action is null");
                    return;
                }
                CachedBluetoothDevice findDevice = BluetoothEventManager.this.mDeviceManager.findDevice(bluetoothDevice);
                if (findDevice == null) {
                    Log.w("BluetoothEventManager", "AclStateChangedHandler: activeDevice is null");
                    return;
                }
                if (action.equals("android.bluetooth.device.action.ACL_CONNECTED")) {
                    i = 2;
                } else if (!action.equals("android.bluetooth.device.action.ACL_DISCONNECTED")) {
                    Log.w("BluetoothEventManager", "ActiveDeviceChangedHandler: unknown action " + action);
                    return;
                } else {
                    i = 0;
                }
                BluetoothEventManager.this.dispatchAclStateChanged(findDevice, i);
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/BluetoothEventManager$ActiveDeviceChangedHandler.class */
    public class ActiveDeviceChangedHandler implements Handler {
        public ActiveDeviceChangedHandler() {
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            int i;
            String action = intent.getAction();
            if (action == null) {
                Log.w("BluetoothEventManager", "ActiveDeviceChangedHandler: action is null");
                return;
            }
            CachedBluetoothDevice findDevice = BluetoothEventManager.this.mDeviceManager.findDevice(bluetoothDevice);
            if (action.equals("android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED")) {
                i = 2;
            } else if (action.equals("android.bluetooth.headset.profile.action.ACTIVE_DEVICE_CHANGED")) {
                i = 1;
            } else if (action.equals("android.bluetooth.hearingaid.profile.action.ACTIVE_DEVICE_CHANGED")) {
                i = 21;
            } else if (!action.equals("android.bluetooth.action.LE_AUDIO_ACTIVE_DEVICE_CHANGED")) {
                Log.w("BluetoothEventManager", "ActiveDeviceChangedHandler: unknown action " + action);
                return;
            } else {
                i = 22;
            }
            BluetoothEventManager.this.dispatchActiveDeviceChanged(findDevice, i);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/BluetoothEventManager$AdapterStateChangedHandler.class */
    public class AdapterStateChangedHandler implements Handler {
        public AdapterStateChangedHandler() {
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            int intExtra = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE);
            BluetoothEventManager.this.mLocalAdapter.setBluetoothStateInt(intExtra);
            for (BluetoothCallback bluetoothCallback : BluetoothEventManager.this.mCallbacks) {
                bluetoothCallback.onBluetoothStateChanged(intExtra);
            }
            BluetoothEventManager.this.mDeviceManager.onBluetoothStateChanged(intExtra);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/BluetoothEventManager$AudioModeChangedHandler.class */
    public class AudioModeChangedHandler implements Handler {
        public AudioModeChangedHandler() {
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            if (intent.getAction() == null) {
                Log.w("BluetoothEventManager", "AudioModeChangedHandler() action is null");
            } else {
                BluetoothEventManager.this.dispatchAudioModeChanged();
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/BluetoothEventManager$BatteryLevelChangedHandler.class */
    public class BatteryLevelChangedHandler implements Handler {
        public BatteryLevelChangedHandler() {
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            CachedBluetoothDevice findDevice = BluetoothEventManager.this.mDeviceManager.findDevice(bluetoothDevice);
            if (findDevice != null) {
                findDevice.refresh();
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/BluetoothEventManager$BluetoothBroadcastReceiver.class */
    public class BluetoothBroadcastReceiver extends BroadcastReceiver {
        public BluetoothBroadcastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE");
            Handler handler = (Handler) BluetoothEventManager.this.mHandlerMap.get(action);
            if (handler != null) {
                handler.onReceive(context, intent, bluetoothDevice);
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/BluetoothEventManager$BondStateChangedHandler.class */
    public class BondStateChangedHandler implements Handler {
        public BondStateChangedHandler() {
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            if (bluetoothDevice == null) {
                Log.e("BluetoothEventManager", "ACTION_BOND_STATE_CHANGED with no EXTRA_DEVICE");
                return;
            }
            int intExtra = intent.getIntExtra("android.bluetooth.device.extra.BOND_STATE", Integer.MIN_VALUE);
            if (BluetoothEventManager.this.mDeviceManager.onBondStateChangedIfProcess(bluetoothDevice, intExtra)) {
                Log.d("BluetoothEventManager", "Should not update UI for the set member");
                return;
            }
            CachedBluetoothDevice findDevice = BluetoothEventManager.this.mDeviceManager.findDevice(bluetoothDevice);
            CachedBluetoothDevice cachedBluetoothDevice = findDevice;
            if (findDevice == null) {
                Log.w("BluetoothEventManager", "Got bonding state changed for " + bluetoothDevice + ", but we have no record of that device.");
                cachedBluetoothDevice = BluetoothEventManager.this.mDeviceManager.addDevice(bluetoothDevice);
            }
            for (BluetoothCallback bluetoothCallback : BluetoothEventManager.this.mCallbacks) {
                bluetoothCallback.onDeviceBondStateChanged(cachedBluetoothDevice, intExtra);
            }
            cachedBluetoothDevice.onBondingStateChanged(intExtra);
            if (intExtra == 10) {
                if (BluetoothEventManager.DEBUG) {
                    Log.d("BluetoothEventManager", "BondStateChangedHandler: cachedDevice.getGroupId() = " + cachedBluetoothDevice.getGroupId() + ", cachedDevice.getHiSyncId()= " + cachedBluetoothDevice.getHiSyncId());
                }
                if (cachedBluetoothDevice.getGroupId() != -1 || cachedBluetoothDevice.getHiSyncId() != 0) {
                    Log.d("BluetoothEventManager", "BondStateChangedHandler: Start onDeviceUnpaired");
                    BluetoothEventManager.this.mDeviceManager.onDeviceUnpaired(cachedBluetoothDevice);
                }
                showUnbondMessage(context, cachedBluetoothDevice.getName(), intent.getIntExtra("android.bluetooth.device.extra.REASON", Integer.MIN_VALUE));
            }
        }

        public final void showUnbondMessage(Context context, String str, int i) {
            int i2;
            if (BluetoothEventManager.DEBUG) {
                Log.d("BluetoothEventManager", "showUnbondMessage() name : " + str + ", reason : " + i);
            }
            switch (i) {
                case 1:
                    i2 = R$string.bluetooth_pairing_pin_error_message;
                    break;
                case 2:
                    i2 = R$string.bluetooth_pairing_rejected_error_message;
                    break;
                case 3:
                default:
                    Log.w("BluetoothEventManager", "showUnbondMessage: Not displaying any message for reason: " + i);
                    return;
                case 4:
                    i2 = R$string.bluetooth_pairing_device_down_error_message;
                    break;
                case 5:
                case 6:
                case 7:
                case 8:
                    i2 = R$string.bluetooth_pairing_error_message;
                    break;
            }
            BluetoothUtils.showError(context, str, i2);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/BluetoothEventManager$ClassChangedHandler.class */
    public class ClassChangedHandler implements Handler {
        public ClassChangedHandler() {
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            CachedBluetoothDevice findDevice = BluetoothEventManager.this.mDeviceManager.findDevice(bluetoothDevice);
            if (findDevice != null) {
                findDevice.refresh();
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/BluetoothEventManager$ConnectionStateChangedHandler.class */
    public class ConnectionStateChangedHandler implements Handler {
        public ConnectionStateChangedHandler() {
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            BluetoothEventManager.this.dispatchConnectionStateChanged(BluetoothEventManager.this.mDeviceManager.findDevice(bluetoothDevice), intent.getIntExtra("android.bluetooth.adapter.extra.CONNECTION_STATE", Integer.MIN_VALUE));
        }
    }

    /* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/BluetoothEventManager$DeviceFoundHandler.class */
    public class DeviceFoundHandler implements Handler {
        public DeviceFoundHandler() {
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            CachedBluetoothDevice cachedBluetoothDevice;
            short shortExtra = intent.getShortExtra("android.bluetooth.device.extra.RSSI", Short.MIN_VALUE);
            intent.getStringExtra("android.bluetooth.device.extra.NAME");
            boolean booleanExtra = intent.getBooleanExtra("android.bluetooth.extra.IS_COORDINATED_SET_MEMBER", false);
            CachedBluetoothDevice findDevice = BluetoothEventManager.this.mDeviceManager.findDevice(bluetoothDevice);
            if (findDevice == null) {
                cachedBluetoothDevice = BluetoothEventManager.this.mDeviceManager.addDevice(bluetoothDevice);
                Log.d("BluetoothEventManager", "DeviceFoundHandler created new CachedBluetoothDevice " + cachedBluetoothDevice.getDevice().getAnonymizedAddress());
            } else {
                cachedBluetoothDevice = findDevice;
                if (findDevice.getBondState() == 12) {
                    cachedBluetoothDevice = findDevice;
                    if (!findDevice.getDevice().isConnected()) {
                        BluetoothEventManager.this.dispatchDeviceAdded(findDevice);
                        cachedBluetoothDevice = findDevice;
                    }
                }
            }
            cachedBluetoothDevice.setRssi(shortExtra);
            cachedBluetoothDevice.setJustDiscovered(true);
            cachedBluetoothDevice.setIsCoordinatedSetMember(booleanExtra);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/BluetoothEventManager$Handler.class */
    public interface Handler {
        void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice);
    }

    /* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/BluetoothEventManager$NameChangedHandler.class */
    public class NameChangedHandler implements Handler {
        public NameChangedHandler() {
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            BluetoothEventManager.this.mDeviceManager.onDeviceNameUpdated(bluetoothDevice);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/BluetoothEventManager$ScanningStateChangedHandler.class */
    public class ScanningStateChangedHandler implements Handler {
        public final boolean mStarted;

        public ScanningStateChangedHandler(boolean z) {
            this.mStarted = z;
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            for (BluetoothCallback bluetoothCallback : BluetoothEventManager.this.mCallbacks) {
                bluetoothCallback.onScanningStateChanged(this.mStarted);
            }
            BluetoothEventManager.this.mDeviceManager.onScanningStateChanged(this.mStarted);
        }
    }

    /* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/BluetoothEventManager$UuidChangedHandler.class */
    public class UuidChangedHandler implements Handler {
        public UuidChangedHandler() {
        }

        @Override // com.android.settingslib.bluetooth.BluetoothEventManager.Handler
        public void onReceive(Context context, Intent intent, BluetoothDevice bluetoothDevice) {
            CachedBluetoothDevice findDevice = BluetoothEventManager.this.mDeviceManager.findDevice(bluetoothDevice);
            if (findDevice != null) {
                findDevice.onUuidChanged();
            }
        }
    }

    public BluetoothEventManager(LocalBluetoothAdapter localBluetoothAdapter, CachedBluetoothDeviceManager cachedBluetoothDeviceManager, Context context, android.os.Handler handler, UserHandle userHandle) {
        this.mLocalAdapter = localBluetoothAdapter;
        this.mDeviceManager = cachedBluetoothDeviceManager;
        this.mContext = context;
        this.mUserHandle = userHandle;
        this.mReceiverHandler = handler;
        addHandler("android.bluetooth.adapter.action.STATE_CHANGED", new AdapterStateChangedHandler());
        addHandler("android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED", new ConnectionStateChangedHandler());
        addHandler("android.bluetooth.adapter.action.DISCOVERY_STARTED", new ScanningStateChangedHandler(true));
        addHandler("android.bluetooth.adapter.action.DISCOVERY_FINISHED", new ScanningStateChangedHandler(false));
        addHandler("android.bluetooth.device.action.FOUND", new DeviceFoundHandler());
        addHandler("android.bluetooth.device.action.NAME_CHANGED", new NameChangedHandler());
        addHandler("android.bluetooth.device.action.ALIAS_CHANGED", new NameChangedHandler());
        addHandler("android.bluetooth.device.action.BOND_STATE_CHANGED", new BondStateChangedHandler());
        addHandler("android.bluetooth.device.action.CLASS_CHANGED", new ClassChangedHandler());
        addHandler("android.bluetooth.device.action.UUID", new UuidChangedHandler());
        addHandler("android.bluetooth.device.action.BATTERY_LEVEL_CHANGED", new BatteryLevelChangedHandler());
        addHandler("android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED", new ActiveDeviceChangedHandler());
        addHandler("android.bluetooth.headset.profile.action.ACTIVE_DEVICE_CHANGED", new ActiveDeviceChangedHandler());
        addHandler("android.bluetooth.hearingaid.profile.action.ACTIVE_DEVICE_CHANGED", new ActiveDeviceChangedHandler());
        addHandler("android.bluetooth.action.LE_AUDIO_ACTIVE_DEVICE_CHANGED", new ActiveDeviceChangedHandler());
        addHandler("android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED", new AudioModeChangedHandler());
        addHandler("android.intent.action.PHONE_STATE", new AudioModeChangedHandler());
        addHandler("android.bluetooth.device.action.ACL_CONNECTED", new AclStateChangedHandler());
        addHandler("android.bluetooth.device.action.ACL_DISCONNECTED", new AclStateChangedHandler());
        registerAdapterIntentReceiver();
    }

    public void addHandler(String str, Handler handler) {
        this.mHandlerMap.put(str, handler);
        this.mAdapterIntentFilter.addAction(str);
    }

    public void addProfileHandler(String str, Handler handler) {
        this.mHandlerMap.put(str, handler);
        this.mProfileIntentFilter.addAction(str);
    }

    public final void dispatchAclStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        for (BluetoothCallback bluetoothCallback : this.mCallbacks) {
            bluetoothCallback.onAclConnectionStateChanged(cachedBluetoothDevice, i);
        }
    }

    public void dispatchActiveDeviceChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        for (CachedBluetoothDevice cachedBluetoothDevice2 : this.mDeviceManager.getCachedDevicesCopy()) {
            Set<CachedBluetoothDevice> memberDevice = cachedBluetoothDevice2.getMemberDevice();
            boolean equals = cachedBluetoothDevice2.equals(cachedBluetoothDevice);
            boolean z = equals;
            CachedBluetoothDevice cachedBluetoothDevice3 = cachedBluetoothDevice;
            if (!equals) {
                z = equals;
                cachedBluetoothDevice3 = cachedBluetoothDevice;
                if (!memberDevice.isEmpty()) {
                    Iterator<CachedBluetoothDevice> it = memberDevice.iterator();
                    z = equals;
                    while (true) {
                        cachedBluetoothDevice3 = cachedBluetoothDevice;
                        if (it.hasNext()) {
                            boolean equals2 = Objects.equals(it.next(), cachedBluetoothDevice);
                            z = equals2;
                            if (equals2) {
                                Log.d("BluetoothEventManager", "The active device is the member device " + cachedBluetoothDevice.getDevice().getAnonymizedAddress() + ". change activeDevice as main device " + cachedBluetoothDevice2.getDevice().getAnonymizedAddress());
                                cachedBluetoothDevice3 = cachedBluetoothDevice2;
                                z = equals2;
                                break;
                            }
                        }
                    }
                }
            }
            cachedBluetoothDevice2.onActiveDeviceChanged(z, i);
            cachedBluetoothDevice = cachedBluetoothDevice3;
        }
        for (BluetoothCallback bluetoothCallback : this.mCallbacks) {
            bluetoothCallback.onActiveDeviceChanged(cachedBluetoothDevice, i);
        }
    }

    public final void dispatchAudioModeChanged() {
        for (CachedBluetoothDevice cachedBluetoothDevice : this.mDeviceManager.getCachedDevicesCopy()) {
            cachedBluetoothDevice.onAudioModeChanged();
        }
        for (BluetoothCallback bluetoothCallback : this.mCallbacks) {
            bluetoothCallback.onAudioModeChanged();
        }
    }

    public final void dispatchConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i) {
        for (BluetoothCallback bluetoothCallback : this.mCallbacks) {
            bluetoothCallback.onConnectionStateChanged(cachedBluetoothDevice, i);
        }
    }

    public void dispatchDeviceAdded(CachedBluetoothDevice cachedBluetoothDevice) {
        for (BluetoothCallback bluetoothCallback : this.mCallbacks) {
            bluetoothCallback.onDeviceAdded(cachedBluetoothDevice);
        }
    }

    public void dispatchDeviceRemoved(CachedBluetoothDevice cachedBluetoothDevice) {
        for (BluetoothCallback bluetoothCallback : this.mCallbacks) {
            bluetoothCallback.onDeviceDeleted(cachedBluetoothDevice);
        }
    }

    public void dispatchProfileConnectionStateChanged(CachedBluetoothDevice cachedBluetoothDevice, int i, int i2) {
        for (BluetoothCallback bluetoothCallback : this.mCallbacks) {
            bluetoothCallback.onProfileConnectionStateChanged(cachedBluetoothDevice, i, i2);
        }
    }

    public boolean readPairedDevices() {
        Set<BluetoothDevice> bondedDevices = this.mLocalAdapter.getBondedDevices();
        boolean z = false;
        if (bondedDevices == null) {
            return false;
        }
        for (BluetoothDevice bluetoothDevice : bondedDevices) {
            if (this.mDeviceManager.findDevice(bluetoothDevice) == null) {
                this.mDeviceManager.addDevice(bluetoothDevice);
                z = true;
            }
        }
        return z;
    }

    public void registerAdapterIntentReceiver() {
        registerIntentReceiver(this.mBroadcastReceiver, this.mAdapterIntentFilter);
    }

    public void registerCallback(BluetoothCallback bluetoothCallback) {
        this.mCallbacks.add(bluetoothCallback);
    }

    public final void registerIntentReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        UserHandle userHandle = this.mUserHandle;
        if (userHandle == null) {
            this.mContext.registerReceiver(broadcastReceiver, intentFilter, null, this.mReceiverHandler, 2);
        } else {
            this.mContext.registerReceiverAsUser(broadcastReceiver, userHandle, intentFilter, null, this.mReceiverHandler, 2);
        }
    }

    public void registerProfileIntentReceiver() {
        registerIntentReceiver(this.mProfileBroadcastReceiver, this.mProfileIntentFilter);
    }
}