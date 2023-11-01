package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothUuid;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelUuid;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.EventLog;
import android.util.Log;
import android.util.LruCache;
import androidx.recyclerview.widget.RecyclerView;
import com.android.internal.util.ArrayUtils;
import com.android.settingslib.utils.ThreadUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/CachedBluetoothDevice.class */
public class CachedBluetoothDevice implements Comparable<CachedBluetoothDevice> {
    public long mConnectAttempted;
    public final Context mContext;
    public BluetoothDevice mDevice;
    public int mDeviceMode;
    public int mDeviceSide;
    public LruCache<String, BitmapDrawable> mDrawableCache;
    public int mGroupId;
    public long mHiSyncId;
    public boolean mJustDiscovered;
    public boolean mLocalNapRoleConnected;
    public final LocalBluetoothProfileManager mProfileManager;
    public short mRssi;
    public CachedBluetoothDevice mSubDevice;
    public boolean mUnpairing;
    public final Object mProfileLock = new Object();
    public final Collection<LocalBluetoothProfile> mProfiles = new CopyOnWriteArrayList();
    public final Collection<LocalBluetoothProfile> mRemovedProfiles = new CopyOnWriteArrayList();
    public boolean mIsCoordinatedSetMember = false;
    public final Collection<Callback> mCallbacks = new CopyOnWriteArrayList();
    public boolean mIsActiveDeviceA2dp = false;
    public boolean mIsActiveDeviceHeadset = false;
    public boolean mIsActiveDeviceHearingAid = false;
    public boolean mIsActiveDeviceLeAudio = false;
    public boolean mIsA2dpProfileConnectedFail = false;
    public boolean mIsHeadsetProfileConnectedFail = false;
    public boolean mIsHearingAidProfileConnectedFail = false;
    public boolean mIsLeAudioProfileConnectedFail = false;
    public Set<CachedBluetoothDevice> mMemberDevices = new HashSet();
    public final Handler mHandler = new Handler(Looper.getMainLooper()) { // from class: com.android.settingslib.bluetooth.CachedBluetoothDevice.1
        {
            CachedBluetoothDevice.this = this;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                CachedBluetoothDevice.this.mIsHeadsetProfileConnectedFail = true;
            } else if (i == 2) {
                CachedBluetoothDevice.this.mIsA2dpProfileConnectedFail = true;
            } else if (i == 21) {
                CachedBluetoothDevice.this.mIsHearingAidProfileConnectedFail = true;
            } else if (i != 22) {
                Log.w("CachedBluetoothDevice", "handleMessage(): unknown message : " + message.what);
            } else {
                CachedBluetoothDevice.this.mIsLeAudioProfileConnectedFail = true;
            }
            Log.w("CachedBluetoothDevice", "Connect to profile : " + message.what + " timeout, show error message !");
            CachedBluetoothDevice.this.refresh();
        }
    };
    public final BluetoothAdapter mLocalAdapter = BluetoothAdapter.getDefaultAdapter();

    /* loaded from: mainsysui33.jar:com/android/settingslib/bluetooth/CachedBluetoothDevice$Callback.class */
    public interface Callback {
        void onDeviceAttributesChanged();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.bluetooth.CachedBluetoothDevice$$ExternalSyntheticLambda0.run():void] */
    /* renamed from: $r8$lambda$-0-xnN0IkijBrHYwoB9nz3zjHpk */
    public static /* synthetic */ void m971$r8$lambda$0xnN0IkijBrHYwoB9nz3zjHpk(CachedBluetoothDevice cachedBluetoothDevice) {
        cachedBluetoothDevice.lambda$refresh$1();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.settingslib.bluetooth.CachedBluetoothDevice$$ExternalSyntheticLambda1.run():void] */
    public static /* synthetic */ void $r8$lambda$4IYXIWE9azg2QshMjGh5evIGUmg(CachedBluetoothDevice cachedBluetoothDevice) {
        cachedBluetoothDevice.lambda$refresh$0();
    }

    public CachedBluetoothDevice(Context context, LocalBluetoothProfileManager localBluetoothProfileManager, BluetoothDevice bluetoothDevice) {
        this.mContext = context;
        this.mProfileManager = localBluetoothProfileManager;
        this.mDevice = bluetoothDevice;
        fillData();
        this.mHiSyncId = 0L;
        this.mGroupId = -1;
        initDrawableCache();
        this.mUnpairing = false;
    }

    public /* synthetic */ void lambda$refresh$1() {
        Uri uriMetaData;
        if (BluetoothUtils.isAdvancedDetailsHeader(this.mDevice) && (uriMetaData = BluetoothUtils.getUriMetaData(getDevice(), 5)) != null && this.mDrawableCache.get(uriMetaData.toString()) == null) {
            this.mDrawableCache.put(uriMetaData.toString(), (BitmapDrawable) BluetoothUtils.getBtDrawableWithDescription(this.mContext, this).first);
        }
        ThreadUtils.postOnMainThread(new Runnable() { // from class: com.android.settingslib.bluetooth.CachedBluetoothDevice$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                CachedBluetoothDevice.$r8$lambda$4IYXIWE9azg2QshMjGh5evIGUmg(CachedBluetoothDevice.this);
            }
        });
    }

    public void addMemberDevice(CachedBluetoothDevice cachedBluetoothDevice) {
        this.mMemberDevices.add(cachedBluetoothDevice);
    }

    @Override // java.lang.Comparable
    public int compareTo(CachedBluetoothDevice cachedBluetoothDevice) {
        int i = (cachedBluetoothDevice.isConnected() ? 1 : 0) - (isConnected() ? 1 : 0);
        if (i != 0) {
            return i;
        }
        int i2 = 1;
        int i3 = cachedBluetoothDevice.getBondState() == 12 ? 1 : 0;
        if (getBondState() != 12) {
            i2 = 0;
        }
        int i4 = i3 - i2;
        if (i4 != 0) {
            return i4;
        }
        int i5 = (cachedBluetoothDevice.mJustDiscovered ? 1 : 0) - (this.mJustDiscovered ? 1 : 0);
        if (i5 != 0) {
            return i5;
        }
        int i6 = cachedBluetoothDevice.mRssi - this.mRssi;
        return i6 != 0 ? i6 : getName().compareTo(cachedBluetoothDevice.getName());
    }

    public void connect() {
        if (ensurePaired()) {
            this.mConnectAttempted = SystemClock.elapsedRealtime();
            connectDevice();
        }
    }

    @Deprecated
    public void connect(boolean z) {
        connect();
    }

    public final void connectDevice() {
        synchronized (this.mProfileLock) {
            if (this.mProfiles.isEmpty()) {
                Log.d("CachedBluetoothDevice", "No profiles. Maybe we will connect later for device " + this.mDevice);
                return;
            }
            this.mDevice.connect();
            if (getGroupId() != -1) {
                for (CachedBluetoothDevice cachedBluetoothDevice : getMemberDevice()) {
                    Log.d("CachedBluetoothDevice", "connect the member(" + cachedBluetoothDevice.getAddress() + ")");
                    cachedBluetoothDevice.connect();
                }
            }
        }
    }

    public final String describe(LocalBluetoothProfile localBluetoothProfile) {
        StringBuilder sb = new StringBuilder();
        sb.append("Address:");
        sb.append(this.mDevice);
        if (localBluetoothProfile != null) {
            sb.append(" Profile:");
            sb.append(localBluetoothProfile);
        }
        return sb.toString();
    }

    /* renamed from: dispatchAttributesChanged */
    public void lambda$refresh$0() {
        for (Callback callback : this.mCallbacks) {
            callback.onDeviceAttributesChanged();
        }
    }

    public final boolean ensurePaired() {
        if (getBondState() == 10) {
            startPairing();
            return false;
        }
        return true;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof CachedBluetoothDevice)) {
            return false;
        }
        return this.mDevice.equals(((CachedBluetoothDevice) obj).mDevice);
    }

    public final void fetchActiveDevices() {
        A2dpProfile a2dpProfile = this.mProfileManager.getA2dpProfile();
        if (a2dpProfile != null) {
            this.mIsActiveDeviceA2dp = this.mDevice.equals(a2dpProfile.getActiveDevice());
        }
        HeadsetProfile headsetProfile = this.mProfileManager.getHeadsetProfile();
        if (headsetProfile != null) {
            this.mIsActiveDeviceHeadset = this.mDevice.equals(headsetProfile.getActiveDevice());
        }
        HearingAidProfile hearingAidProfile = this.mProfileManager.getHearingAidProfile();
        if (hearingAidProfile != null) {
            this.mIsActiveDeviceHearingAid = hearingAidProfile.getActiveDevices().contains(this.mDevice);
        }
        LeAudioProfile leAudioProfile = this.mProfileManager.getLeAudioProfile();
        if (leAudioProfile != null) {
            this.mIsActiveDeviceLeAudio = leAudioProfile.getActiveDevices().contains(this.mDevice);
        }
    }

    public final void fillData() {
        updateProfiles();
        fetchActiveDevices();
        migratePhonebookPermissionChoice();
        migrateMessagePermissionChoice();
        lambda$refresh$0();
    }

    public String getAddress() {
        return this.mDevice.getAddress();
    }

    public int getBatteryLevel() {
        return this.mDevice.getBatteryLevel();
    }

    public int getBondState() {
        return this.mDevice.getBondState();
    }

    public BluetoothClass getBtClass() {
        return this.mDevice.getBluetoothClass();
    }

    public List<LocalBluetoothProfile> getConnectableProfiles() {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mProfileLock) {
            for (LocalBluetoothProfile localBluetoothProfile : this.mProfiles) {
                if (localBluetoothProfile.accessProfileEnabled()) {
                    arrayList.add(localBluetoothProfile);
                }
            }
        }
        return arrayList;
    }

    public BluetoothDevice getDevice() {
        return this.mDevice;
    }

    public int getDeviceMode() {
        return this.mDeviceMode;
    }

    public int getDeviceSide() {
        return this.mDeviceSide;
    }

    public int getGroupId() {
        return this.mGroupId;
    }

    public long getHiSyncId() {
        return this.mHiSyncId;
    }

    public int getMaxConnectionState() {
        int i;
        synchronized (this.mProfileLock) {
            i = 0;
            for (LocalBluetoothProfile localBluetoothProfile : getProfiles()) {
                int profileConnectionState = getProfileConnectionState(localBluetoothProfile);
                if (profileConnectionState > i) {
                    i = profileConnectionState;
                }
            }
        }
        return i;
    }

    public Set<CachedBluetoothDevice> getMemberDevice() {
        return this.mMemberDevices;
    }

    public String getName() {
        String alias = this.mDevice.getAlias();
        String str = alias;
        if (TextUtils.isEmpty(alias)) {
            str = getAddress();
        }
        return str;
    }

    public int getProfileConnectionState(LocalBluetoothProfile localBluetoothProfile) {
        return localBluetoothProfile != null ? localBluetoothProfile.getConnectionStatus(this.mDevice) : 0;
    }

    public List<LocalBluetoothProfile> getProfiles() {
        return new ArrayList(this.mProfiles);
    }

    public CachedBluetoothDevice getSubDevice() {
        return this.mSubDevice;
    }

    public boolean getUnpairing() {
        return this.mUnpairing;
    }

    public int hashCode() {
        return this.mDevice.getAddress().hashCode();
    }

    public final void initDrawableCache() {
        this.mDrawableCache = new LruCache<String, BitmapDrawable>(((int) (Runtime.getRuntime().maxMemory() / 1024)) / 8) { // from class: com.android.settingslib.bluetooth.CachedBluetoothDevice.2
            {
                CachedBluetoothDevice.this = this;
            }

            @Override // android.util.LruCache
            public int sizeOf(String str, BitmapDrawable bitmapDrawable) {
                return bitmapDrawable.getBitmap().getByteCount() / RecyclerView.ViewHolder.FLAG_ADAPTER_FULLUPDATE;
            }
        };
    }

    public boolean isActiveDevice(int i) {
        if (i != 1) {
            if (i != 2) {
                if (i != 21) {
                    if (i != 22) {
                        Log.w("CachedBluetoothDevice", "getActiveDevice: unknown profile " + i);
                        return false;
                    }
                    return this.mIsActiveDeviceLeAudio;
                }
                return this.mIsActiveDeviceHearingAid;
            }
            return this.mIsActiveDeviceA2dp;
        }
        return this.mIsActiveDeviceHeadset;
    }

    public boolean isBusy() {
        int profileConnectionState;
        synchronized (this.mProfileLock) {
            Iterator<LocalBluetoothProfile> it = this.mProfiles.iterator();
            do {
                boolean z = true;
                if (!it.hasNext()) {
                    if (getBondState() != 11) {
                        z = false;
                    }
                    return z;
                }
                profileConnectionState = getProfileConnectionState(it.next());
                if (profileConnectionState == 1) {
                    break;
                }
            } while (profileConnectionState != 3);
            return true;
        }
    }

    public boolean isConnected() {
        synchronized (this.mProfileLock) {
            Iterator<LocalBluetoothProfile> it = this.mProfiles.iterator();
            do {
                if (!it.hasNext()) {
                    return false;
                }
            } while (getProfileConnectionState(it.next()) != 2);
            return true;
        }
    }

    public boolean isConnectedProfile(LocalBluetoothProfile localBluetoothProfile) {
        return getProfileConnectionState(localBluetoothProfile) == 2;
    }

    public boolean isHearingAidDevice() {
        return this.mHiSyncId != 0;
    }

    public final void migrateMessagePermissionChoice() {
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences("bluetooth_message_permission", 0);
        if (sharedPreferences.contains(this.mDevice.getAddress())) {
            if (this.mDevice.getMessageAccessPermission() == 0) {
                int i = sharedPreferences.getInt(this.mDevice.getAddress(), 0);
                if (i == 1) {
                    this.mDevice.setMessageAccessPermission(1);
                } else if (i == 2) {
                    this.mDevice.setMessageAccessPermission(2);
                }
            }
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.remove(this.mDevice.getAddress());
            edit.commit();
        }
    }

    public final void migratePhonebookPermissionChoice() {
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences("bluetooth_phonebook_permission", 0);
        if (sharedPreferences.contains(this.mDevice.getAddress())) {
            if (this.mDevice.getPhonebookAccessPermission() == 0) {
                int i = sharedPreferences.getInt(this.mDevice.getAddress(), 0);
                if (i == 1) {
                    this.mDevice.setPhonebookAccessPermission(1);
                } else if (i == 2) {
                    this.mDevice.setPhonebookAccessPermission(2);
                }
            }
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.remove(this.mDevice.getAddress());
            edit.commit();
        }
    }

    public void onActiveDeviceChanged(boolean z, int i) {
        boolean z2;
        Log.d("CachedBluetoothDevice", "onActiveDeviceChanged: profile " + BluetoothProfile.getProfileName(i) + ", device " + this.mDevice.getAnonymizedAddress() + ", isActive " + z);
        if (i == 1) {
            z2 = this.mIsActiveDeviceHeadset != z;
            this.mIsActiveDeviceHeadset = z;
        } else if (i == 2) {
            z2 = this.mIsActiveDeviceA2dp != z;
            this.mIsActiveDeviceA2dp = z;
        } else if (i == 21) {
            z2 = this.mIsActiveDeviceHearingAid != z;
            this.mIsActiveDeviceHearingAid = z;
        } else if (i != 22) {
            Log.w("CachedBluetoothDevice", "onActiveDeviceChanged: unknown profile " + i + " isActive " + z);
            z2 = false;
        } else {
            z2 = this.mIsActiveDeviceLeAudio != z;
            this.mIsActiveDeviceLeAudio = z;
        }
        if (z2) {
            lambda$refresh$0();
        }
    }

    public void onAudioModeChanged() {
        lambda$refresh$0();
    }

    public void onBondingStateChanged(int i) {
        if (i == 10) {
            synchronized (this.mProfileLock) {
                this.mProfiles.clear();
            }
            this.mDevice.setPhonebookAccessPermission(0);
            this.mDevice.setMessageAccessPermission(0);
            this.mDevice.setSimAccessPermission(0);
        }
        refresh();
        if (i == 12 && this.mDevice.isBondingInitiatedLocally()) {
            connect();
        }
    }

    public void onProfileStateChanged(LocalBluetoothProfile localBluetoothProfile, int i) {
        Log.d("CachedBluetoothDevice", "onProfileStateChanged: profile " + localBluetoothProfile + ", device " + this.mDevice.getAnonymizedAddress() + ", newProfileState " + i);
        if (this.mLocalAdapter.getState() == 13) {
            Log.d("CachedBluetoothDevice", " BT Turninig Off...Profile conn state change ignored...");
            return;
        }
        synchronized (this.mProfileLock) {
            if ((localBluetoothProfile instanceof A2dpProfile) || (localBluetoothProfile instanceof HeadsetProfile) || (localBluetoothProfile instanceof HearingAidProfile)) {
                setProfileConnectedStatus(localBluetoothProfile.getProfileId(), false);
                if (i != 0) {
                    if (i == 1) {
                        this.mHandler.sendEmptyMessageDelayed(localBluetoothProfile.getProfileId(), 60000L);
                    } else if (i == 2) {
                        this.mHandler.removeMessages(localBluetoothProfile.getProfileId());
                    } else if (i != 3) {
                        Log.w("CachedBluetoothDevice", "onProfileStateChanged(): unknown profile state : " + i);
                    } else if (this.mHandler.hasMessages(localBluetoothProfile.getProfileId())) {
                        this.mHandler.removeMessages(localBluetoothProfile.getProfileId());
                    }
                } else if (this.mHandler.hasMessages(localBluetoothProfile.getProfileId())) {
                    this.mHandler.removeMessages(localBluetoothProfile.getProfileId());
                    setProfileConnectedStatus(localBluetoothProfile.getProfileId(), true);
                }
            }
            if (i == 2) {
                if (localBluetoothProfile instanceof MapProfile) {
                    localBluetoothProfile.setEnabled(this.mDevice, true);
                }
                if (!this.mProfiles.contains(localBluetoothProfile)) {
                    this.mRemovedProfiles.remove(localBluetoothProfile);
                    this.mProfiles.add(localBluetoothProfile);
                    if ((localBluetoothProfile instanceof PanProfile) && ((PanProfile) localBluetoothProfile).isLocalRoleNap(this.mDevice)) {
                        this.mLocalNapRoleConnected = true;
                    }
                }
            } else if ((localBluetoothProfile instanceof MapProfile) && i == 0) {
                localBluetoothProfile.setEnabled(this.mDevice, false);
            } else if (this.mLocalNapRoleConnected && (localBluetoothProfile instanceof PanProfile) && ((PanProfile) localBluetoothProfile).isLocalRoleNap(this.mDevice) && i == 0) {
                Log.d("CachedBluetoothDevice", "Removing PanProfile from device after NAP disconnect");
                this.mProfiles.remove(localBluetoothProfile);
                this.mRemovedProfiles.add(localBluetoothProfile);
                this.mLocalNapRoleConnected = false;
            }
        }
        fetchActiveDevices();
    }

    public void onUuidChanged() {
        updateProfiles();
        ParcelUuid[] uuids = this.mDevice.getUuids();
        long j = 30000;
        if (!ArrayUtils.contains(uuids, BluetoothUuid.HOGP)) {
            if (ArrayUtils.contains(uuids, BluetoothUuid.HEARING_AID)) {
                j = 15000;
            } else if (!ArrayUtils.contains(uuids, BluetoothUuid.LE_AUDIO)) {
                j = 5000;
            }
        }
        Log.d("CachedBluetoothDevice", "onUuidChanged: Time since last connect=" + (SystemClock.elapsedRealtime() - this.mConnectAttempted));
        if (this.mConnectAttempted + j > SystemClock.elapsedRealtime()) {
            Log.d("CachedBluetoothDevice", "onUuidChanged: triggering connectDevice");
            connectDevice();
        }
        lambda$refresh$0();
    }

    public final void processPhonebookAccess() {
        if (this.mDevice.getBondState() == 12 && BluetoothUuid.containsAnyUuid(this.mDevice.getUuids(), PbapServerProfile.PBAB_CLIENT_UUIDS)) {
            BluetoothClass bluetoothClass = this.mDevice.getBluetoothClass();
            if (this.mDevice.getPhonebookAccessPermission() != 0 || bluetoothClass == null) {
                return;
            }
            if (bluetoothClass.getDeviceClass() == 1032 || bluetoothClass.getDeviceClass() == 1028) {
                EventLog.writeEvent(1397638484, "138529441", -1, "");
            }
        }
    }

    public void refresh() {
        ThreadUtils.postOnBackgroundThread(new Runnable() { // from class: com.android.settingslib.bluetooth.CachedBluetoothDevice$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CachedBluetoothDevice.m971$r8$lambda$0xnN0IkijBrHYwoB9nz3zjHpk(CachedBluetoothDevice.this);
            }
        });
    }

    public void refreshName() {
        Log.d("CachedBluetoothDevice", "Device name: " + getName());
        lambda$refresh$0();
    }

    public void registerCallback(Callback callback) {
        this.mCallbacks.add(callback);
    }

    public void releaseLruCache() {
        this.mDrawableCache.evictAll();
    }

    public void removeMemberDevice(CachedBluetoothDevice cachedBluetoothDevice) {
        this.mMemberDevices.remove(cachedBluetoothDevice);
    }

    public void setDeviceMode(int i) {
        this.mDeviceMode = i;
    }

    public void setDeviceSide(int i) {
        this.mDeviceSide = i;
    }

    public void setGroupId(int i) {
        Log.d("CachedBluetoothDevice", getDevice().getAnonymizedAddress() + " set GroupId " + i);
        this.mGroupId = i;
    }

    public void setHiSyncId(long j) {
        this.mHiSyncId = j;
    }

    public void setIsCoordinatedSetMember(boolean z) {
        this.mIsCoordinatedSetMember = z;
    }

    public void setJustDiscovered(boolean z) {
        if (this.mJustDiscovered != z) {
            this.mJustDiscovered = z;
            lambda$refresh$0();
        }
    }

    public void setName(String str) {
        if (str == null || TextUtils.equals(str, getName())) {
            return;
        }
        this.mDevice.setAlias(str);
        lambda$refresh$0();
    }

    public void setProfileConnectedStatus(int i, boolean z) {
        if (i == 1) {
            this.mIsHeadsetProfileConnectedFail = z;
        } else if (i == 2) {
            this.mIsA2dpProfileConnectedFail = z;
        } else if (i == 21) {
            this.mIsHearingAidProfileConnectedFail = z;
        } else if (i == 22) {
            this.mIsLeAudioProfileConnectedFail = z;
        } else {
            Log.w("CachedBluetoothDevice", "setProfileConnectedStatus(): unknown profile id : " + i);
        }
    }

    public void setRssi(short s) {
        if (this.mRssi != s) {
            this.mRssi = s;
            lambda$refresh$0();
        }
    }

    public void setSubDevice(CachedBluetoothDevice cachedBluetoothDevice) {
        this.mSubDevice = cachedBluetoothDevice;
    }

    public boolean startPairing() {
        if (this.mLocalAdapter.isDiscovering()) {
            this.mLocalAdapter.cancelDiscovery();
        }
        return this.mDevice.createBond();
    }

    public void switchMemberDeviceContent(CachedBluetoothDevice cachedBluetoothDevice) {
        BluetoothDevice bluetoothDevice = this.mDevice;
        short s = this.mRssi;
        boolean z = this.mJustDiscovered;
        this.mDevice = cachedBluetoothDevice.mDevice;
        this.mRssi = cachedBluetoothDevice.mRssi;
        this.mJustDiscovered = cachedBluetoothDevice.mJustDiscovered;
        cachedBluetoothDevice.mDevice = bluetoothDevice;
        cachedBluetoothDevice.mRssi = s;
        cachedBluetoothDevice.mJustDiscovered = z;
        fetchActiveDevices();
    }

    public void switchSubDeviceContent() {
        BluetoothDevice bluetoothDevice = this.mDevice;
        short s = this.mRssi;
        boolean z = this.mJustDiscovered;
        int i = this.mDeviceSide;
        CachedBluetoothDevice cachedBluetoothDevice = this.mSubDevice;
        this.mDevice = cachedBluetoothDevice.mDevice;
        this.mRssi = cachedBluetoothDevice.mRssi;
        this.mJustDiscovered = cachedBluetoothDevice.mJustDiscovered;
        this.mDeviceSide = cachedBluetoothDevice.mDeviceSide;
        cachedBluetoothDevice.mDevice = bluetoothDevice;
        cachedBluetoothDevice.mRssi = s;
        cachedBluetoothDevice.mJustDiscovered = z;
        cachedBluetoothDevice.mDeviceSide = i;
        fetchActiveDevices();
    }

    public String toString() {
        return "CachedBluetoothDevice (anonymizedAddress=" + this.mDevice.getAnonymizedAddress() + ", name=" + getName() + ", groupId=" + this.mGroupId + ")";
    }

    public void unpair() {
        BluetoothDevice bluetoothDevice;
        int bondState = getBondState();
        if (bondState == 11) {
            this.mDevice.cancelBondProcess();
        }
        if (bondState == 10 || (bluetoothDevice = this.mDevice) == null) {
            return;
        }
        this.mUnpairing = true;
        if (bluetoothDevice.removeBond()) {
            releaseLruCache();
            Log.d("CachedBluetoothDevice", "Command sent successfully:REMOVE_BOND " + describe(null));
        }
    }

    public void unregisterCallback(Callback callback) {
        this.mCallbacks.remove(callback);
    }

    public final boolean updateProfiles() {
        BluetoothClass bluetoothClass;
        ParcelUuid[] uuids = this.mDevice.getUuids();
        if (uuids == null) {
            return false;
        }
        List uuidsList = this.mLocalAdapter.getUuidsList();
        ParcelUuid[] parcelUuidArr = new ParcelUuid[uuidsList.size()];
        uuidsList.toArray(parcelUuidArr);
        processPhonebookAccess();
        synchronized (this.mProfileLock) {
            this.mProfileManager.updateProfiles(uuids, parcelUuidArr, this.mProfiles, this.mRemovedProfiles, this.mLocalNapRoleConnected, this.mDevice);
        }
        Log.d("CachedBluetoothDevice", "updating profiles for " + this.mDevice.getAnonymizedAddress());
        if (this.mDevice.getBluetoothClass() != null) {
            Log.v("CachedBluetoothDevice", "Class: " + bluetoothClass.toString());
        }
        Log.v("CachedBluetoothDevice", "UUID:");
        for (ParcelUuid parcelUuid : uuids) {
            Log.v("CachedBluetoothDevice", "  " + parcelUuid);
        }
        return true;
    }
}