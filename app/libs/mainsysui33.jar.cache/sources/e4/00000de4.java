package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import com.android.internal.annotations.VisibleForTesting;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/CachedBluetoothDeviceManager.class */
public class CachedBluetoothDeviceManager {
    public final LocalBluetoothManager mBtManager;
    @VisibleForTesting
    public final List<CachedBluetoothDevice> mCachedDevices;
    public Context mContext;
    @VisibleForTesting
    public CsipDeviceManager mCsipDeviceManager;
    @VisibleForTesting
    public HearingAidDeviceManager mHearingAidDeviceManager;
    public BluetoothDevice mOngoingSetMemberPair;

    public CachedBluetoothDeviceManager(Context context, LocalBluetoothManager localBluetoothManager) {
        ArrayList arrayList = new ArrayList();
        this.mCachedDevices = arrayList;
        this.mContext = context;
        this.mBtManager = localBluetoothManager;
        this.mHearingAidDeviceManager = new HearingAidDeviceManager(localBluetoothManager, arrayList);
        this.mCsipDeviceManager = new CsipDeviceManager(localBluetoothManager, arrayList);
    }

    public static /* synthetic */ boolean lambda$clearNonBondedDevices$0(CachedBluetoothDevice cachedBluetoothDevice) {
        return cachedBluetoothDevice.getBondState() == 10;
    }

    public CachedBluetoothDevice addDevice(BluetoothDevice bluetoothDevice) {
        CachedBluetoothDevice cachedBluetoothDevice;
        LocalBluetoothProfileManager profileManager = this.mBtManager.getProfileManager();
        synchronized (this) {
            CachedBluetoothDevice findDevice = findDevice(bluetoothDevice);
            cachedBluetoothDevice = findDevice;
            if (findDevice == null) {
                CachedBluetoothDevice cachedBluetoothDevice2 = new CachedBluetoothDevice(this.mContext, profileManager, bluetoothDevice);
                this.mCsipDeviceManager.initCsipDeviceIfNeeded(cachedBluetoothDevice2);
                this.mHearingAidDeviceManager.initHearingAidDeviceIfNeeded(cachedBluetoothDevice2);
                cachedBluetoothDevice = cachedBluetoothDevice2;
                if (!this.mCsipDeviceManager.setMemberDeviceIfNeeded(cachedBluetoothDevice2)) {
                    cachedBluetoothDevice = cachedBluetoothDevice2;
                    if (!this.mHearingAidDeviceManager.setSubDeviceIfNeeded(cachedBluetoothDevice2)) {
                        this.mCachedDevices.add(cachedBluetoothDevice2);
                        this.mBtManager.getEventManager().dispatchDeviceAdded(cachedBluetoothDevice2);
                        cachedBluetoothDevice = cachedBluetoothDevice2;
                    }
                }
            }
        }
        return cachedBluetoothDevice;
    }

    public void clearNonBondedDevices() {
        synchronized (this) {
            clearNonBondedSubDevices();
            this.mCachedDevices.removeIf(new Predicate() { // from class: com.android.settingslib.bluetooth.CachedBluetoothDeviceManager$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean lambda$clearNonBondedDevices$0;
                    lambda$clearNonBondedDevices$0 = CachedBluetoothDeviceManager.lambda$clearNonBondedDevices$0((CachedBluetoothDevice) obj);
                    return lambda$clearNonBondedDevices$0;
                }
            });
        }
    }

    public final void clearNonBondedSubDevices() {
        for (int size = this.mCachedDevices.size() - 1; size >= 0; size--) {
            CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevices.get(size);
            Set<CachedBluetoothDevice> memberDevice = cachedBluetoothDevice.getMemberDevice();
            if (!memberDevice.isEmpty()) {
                for (Object obj : memberDevice.toArray()) {
                    CachedBluetoothDevice cachedBluetoothDevice2 = (CachedBluetoothDevice) obj;
                    if (cachedBluetoothDevice2.getDevice().getBondState() == 10) {
                        cachedBluetoothDevice.removeMemberDevice(cachedBluetoothDevice2);
                    }
                }
                return;
            }
            CachedBluetoothDevice subDevice = cachedBluetoothDevice.getSubDevice();
            if (subDevice != null && subDevice.getDevice().getBondState() == 10) {
                cachedBluetoothDevice.setSubDevice(null);
            }
        }
    }

    public CachedBluetoothDevice findDevice(BluetoothDevice bluetoothDevice) {
        synchronized (this) {
            for (CachedBluetoothDevice cachedBluetoothDevice : this.mCachedDevices) {
                if (cachedBluetoothDevice.getDevice().equals(bluetoothDevice)) {
                    return cachedBluetoothDevice;
                }
                Set<CachedBluetoothDevice> memberDevice = cachedBluetoothDevice.getMemberDevice();
                if (!memberDevice.isEmpty()) {
                    for (CachedBluetoothDevice cachedBluetoothDevice2 : memberDevice) {
                        if (cachedBluetoothDevice2.getDevice().equals(bluetoothDevice)) {
                            return cachedBluetoothDevice2;
                        }
                    }
                }
                CachedBluetoothDevice subDevice = cachedBluetoothDevice.getSubDevice();
                if (subDevice != null && subDevice.getDevice().equals(bluetoothDevice)) {
                    return subDevice;
                }
            }
            return null;
        }
    }

    public Collection<CachedBluetoothDevice> getCachedDevicesCopy() {
        ArrayList arrayList;
        synchronized (this) {
            arrayList = new ArrayList(this.mCachedDevices);
        }
        return arrayList;
    }

    public boolean isSubDevice(BluetoothDevice bluetoothDevice) {
        synchronized (this) {
            for (CachedBluetoothDevice cachedBluetoothDevice : this.mCachedDevices) {
                if (!cachedBluetoothDevice.getDevice().equals(bluetoothDevice)) {
                    Set<CachedBluetoothDevice> memberDevice = cachedBluetoothDevice.getMemberDevice();
                    if (memberDevice.isEmpty()) {
                        CachedBluetoothDevice subDevice = cachedBluetoothDevice.getSubDevice();
                        if (subDevice != null && subDevice.getDevice().equals(bluetoothDevice)) {
                            return true;
                        }
                    } else {
                        for (CachedBluetoothDevice cachedBluetoothDevice2 : memberDevice) {
                            if (cachedBluetoothDevice2.getDevice().equals(bluetoothDevice)) {
                                return true;
                            }
                        }
                        continue;
                    }
                }
            }
            return false;
        }
    }

    public void onBluetoothStateChanged(int i) {
        synchronized (this) {
            if (i == 13) {
                for (int size = this.mCachedDevices.size() - 1; size >= 0; size--) {
                    CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevices.get(size);
                    Set<CachedBluetoothDevice> memberDevice = cachedBluetoothDevice.getMemberDevice();
                    if (memberDevice.isEmpty()) {
                        CachedBluetoothDevice subDevice = cachedBluetoothDevice.getSubDevice();
                        if (subDevice != null && subDevice.getBondState() != 12) {
                            cachedBluetoothDevice.setSubDevice(null);
                        }
                    } else {
                        for (CachedBluetoothDevice cachedBluetoothDevice2 : memberDevice) {
                            if (cachedBluetoothDevice2.getBondState() != 12) {
                                cachedBluetoothDevice.removeMemberDevice(cachedBluetoothDevice2);
                            }
                        }
                    }
                    if (cachedBluetoothDevice.getBondState() != 12) {
                        cachedBluetoothDevice.setJustDiscovered(false);
                        this.mCachedDevices.remove(size);
                    }
                }
                this.mOngoingSetMemberPair = null;
            }
        }
    }

    public boolean onBondStateChangedIfProcess(BluetoothDevice bluetoothDevice, int i) {
        synchronized (this) {
            BluetoothDevice bluetoothDevice2 = this.mOngoingSetMemberPair;
            if (bluetoothDevice2 == null || !bluetoothDevice2.equals(bluetoothDevice)) {
                return false;
            }
            if (i == 11) {
                return true;
            }
            this.mOngoingSetMemberPair = null;
            if (i != 10 && findDevice(bluetoothDevice) == null) {
                this.mCachedDevices.add(new CachedBluetoothDevice(this.mContext, this.mBtManager.getProfileManager(), bluetoothDevice));
                findDevice(bluetoothDevice).connect();
            }
            return true;
        }
    }

    public void onDeviceNameUpdated(BluetoothDevice bluetoothDevice) {
        CachedBluetoothDevice findDevice = findDevice(bluetoothDevice);
        if (findDevice != null) {
            findDevice.refreshName();
        }
    }

    public void onDeviceUnpaired(CachedBluetoothDevice cachedBluetoothDevice) {
        synchronized (this) {
            cachedBluetoothDevice.setGroupId(-1);
            CachedBluetoothDevice findMainDevice = this.mCsipDeviceManager.findMainDevice(cachedBluetoothDevice);
            Set<CachedBluetoothDevice> memberDevice = cachedBluetoothDevice.getMemberDevice();
            if (!memberDevice.isEmpty()) {
                for (CachedBluetoothDevice cachedBluetoothDevice2 : memberDevice) {
                    cachedBluetoothDevice2.unpair();
                    cachedBluetoothDevice2.setGroupId(-1);
                    cachedBluetoothDevice.removeMemberDevice(cachedBluetoothDevice2);
                }
            } else if (findMainDevice != null) {
                findMainDevice.unpair();
            }
            CachedBluetoothDevice findMainDevice2 = this.mHearingAidDeviceManager.findMainDevice(cachedBluetoothDevice);
            CachedBluetoothDevice subDevice = cachedBluetoothDevice.getSubDevice();
            if (subDevice != null) {
                subDevice.unpair();
                cachedBluetoothDevice.setSubDevice(null);
            } else if (findMainDevice2 != null) {
                findMainDevice2.unpair();
                findMainDevice2.setSubDevice(null);
            }
        }
    }

    public boolean onProfileConnectionStateChangedIfProcessed(CachedBluetoothDevice cachedBluetoothDevice, int i, int i2) {
        synchronized (this) {
            if (i2 == 21) {
                return this.mHearingAidDeviceManager.onProfileConnectionStateChangedIfProcessed(cachedBluetoothDevice, i);
            } else if (i2 == 25) {
                return this.mCsipDeviceManager.onProfileConnectionStateChangedIfProcessed(cachedBluetoothDevice, i);
            } else {
                return false;
            }
        }
    }

    public void onScanningStateChanged(boolean z) {
        synchronized (this) {
            if (z) {
                for (int size = this.mCachedDevices.size() - 1; size >= 0; size--) {
                    CachedBluetoothDevice cachedBluetoothDevice = this.mCachedDevices.get(size);
                    cachedBluetoothDevice.setJustDiscovered(false);
                    Set<CachedBluetoothDevice> memberDevice = cachedBluetoothDevice.getMemberDevice();
                    if (!memberDevice.isEmpty()) {
                        for (CachedBluetoothDevice cachedBluetoothDevice2 : memberDevice) {
                            cachedBluetoothDevice2.setJustDiscovered(false);
                        }
                        return;
                    }
                    CachedBluetoothDevice subDevice = cachedBluetoothDevice.getSubDevice();
                    if (subDevice != null) {
                        subDevice.setJustDiscovered(false);
                    }
                }
            }
        }
    }

    public void updateCsipDevices() {
        synchronized (this) {
            this.mCsipDeviceManager.updateCsipDevices();
        }
    }

    public void updateHearingAidsDevices() {
        synchronized (this) {
            this.mHearingAidDeviceManager.updateHearingAidsDevices();
        }
    }
}