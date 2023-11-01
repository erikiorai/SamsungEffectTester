package com.android.settingslib.media;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.settingslib.bluetooth.A2dpProfile;
import com.android.settingslib.bluetooth.BluetoothCallback;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.CachedBluetoothDeviceManager;
import com.android.settingslib.bluetooth.HearingAidProfile;
import com.android.settingslib.bluetooth.LeAudioProfile;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfile;
import com.android.settingslib.media.MediaManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: mainsysui33.jar:com/android/settingslib/media/LocalMediaManager.class */
public class LocalMediaManager implements BluetoothCallback {
    @VisibleForTesting
    public AudioManager mAudioManager;
    public Context mContext;
    @VisibleForTesting
    public MediaDevice mCurrentConnectedDevice;
    public InfoMediaManager mInfoMediaManager;
    public LocalBluetoothManager mLocalBluetoothManager;
    public MediaDevice mOnTransferBluetoothDevice;
    public String mPackageName;
    @VisibleForTesting
    public MediaDevice mPhoneDevice;
    public final Collection<DeviceCallback> mCallbacks = new CopyOnWriteArrayList();
    public final Object mMediaDevicesLock = new Object();
    @VisibleForTesting
    public final MediaDeviceCallback mMediaDeviceCallback = new MediaDeviceCallback();
    @VisibleForTesting
    public List<MediaDevice> mMediaDevices = new CopyOnWriteArrayList();
    @VisibleForTesting
    public List<MediaDevice> mDisconnectedMediaDevices = new CopyOnWriteArrayList();
    @VisibleForTesting
    public DeviceAttributeChangeCallback mDeviceAttributeChangeCallback = new DeviceAttributeChangeCallback();
    @VisibleForTesting
    public BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    @VisibleForTesting
    /* loaded from: mainsysui33.jar:com/android/settingslib/media/LocalMediaManager$DeviceAttributeChangeCallback.class */
    public class DeviceAttributeChangeCallback implements CachedBluetoothDevice.Callback {
        public DeviceAttributeChangeCallback() {
        }

        @Override // com.android.settingslib.bluetooth.CachedBluetoothDevice.Callback
        public void onDeviceAttributesChanged() {
            if (LocalMediaManager.this.mOnTransferBluetoothDevice != null && !((BluetoothMediaDevice) LocalMediaManager.this.mOnTransferBluetoothDevice).getCachedDevice().isBusy() && !LocalMediaManager.this.mOnTransferBluetoothDevice.isConnected()) {
                LocalMediaManager.this.mOnTransferBluetoothDevice.setState(3);
                LocalMediaManager.this.mOnTransferBluetoothDevice = null;
                LocalMediaManager.this.dispatchOnRequestFailed(0);
            }
            LocalMediaManager.this.dispatchDeviceAttributesChanged();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/settingslib/media/LocalMediaManager$DeviceCallback.class */
    public interface DeviceCallback {
        default void onAboutToConnectDeviceAdded(String str, String str2, Drawable drawable) {
        }

        default void onAboutToConnectDeviceRemoved() {
        }

        default void onDeviceAttributesChanged() {
        }

        default void onDeviceListUpdate(List<MediaDevice> list) {
        }

        default void onRequestFailed(int i) {
        }

        default void onSelectedDeviceStateChanged(MediaDevice mediaDevice, int i) {
        }
    }

    /* loaded from: mainsysui33.jar:com/android/settingslib/media/LocalMediaManager$MediaDeviceCallback.class */
    public class MediaDeviceCallback implements MediaManager.MediaDeviceCallback {
        public MediaDeviceCallback() {
        }

        public final MediaDevice getMutingExpectedDevice() {
            LocalMediaManager localMediaManager = LocalMediaManager.this;
            if (localMediaManager.mBluetoothAdapter == null || localMediaManager.mAudioManager.getMutingExpectedDevice() == null) {
                Log.w("LocalMediaManager", "BluetoothAdapter is null or muting expected device not exist");
                return null;
            }
            List<BluetoothDevice> mostRecentlyConnectedDevices = LocalMediaManager.this.mBluetoothAdapter.getMostRecentlyConnectedDevices();
            CachedBluetoothDeviceManager cachedDeviceManager = LocalMediaManager.this.mLocalBluetoothManager.getCachedDeviceManager();
            for (BluetoothDevice bluetoothDevice : mostRecentlyConnectedDevices) {
                CachedBluetoothDevice findDevice = cachedDeviceManager.findDevice(bluetoothDevice);
                if (isBondedMediaDevice(findDevice) && isMutingExpectedDevice(findDevice)) {
                    return new BluetoothMediaDevice(LocalMediaManager.this.mContext, findDevice, null, null, LocalMediaManager.this.mPackageName);
                }
            }
            return null;
        }

        public final boolean isBondedMediaDevice(CachedBluetoothDevice cachedBluetoothDevice) {
            return cachedBluetoothDevice != null && cachedBluetoothDevice.getBondState() == 12 && !cachedBluetoothDevice.isConnected() && isMediaDevice(cachedBluetoothDevice);
        }

        /* JADX WARN: Removed duplicated region for block: B:5:0x0013  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final boolean isMediaDevice(CachedBluetoothDevice cachedBluetoothDevice) {
            for (LocalBluetoothProfile localBluetoothProfile : cachedBluetoothDevice.getConnectableProfiles()) {
                if ((localBluetoothProfile instanceof A2dpProfile) || (localBluetoothProfile instanceof HearingAidProfile) || (localBluetoothProfile instanceof LeAudioProfile)) {
                    return true;
                }
                while (r0.hasNext()) {
                }
            }
            return false;
        }

        public final boolean isMutingExpectedDevice(CachedBluetoothDevice cachedBluetoothDevice) {
            return LocalMediaManager.this.mAudioManager.getMutingExpectedDevice() != null && cachedBluetoothDevice.getAddress().equals(LocalMediaManager.this.mAudioManager.getMutingExpectedDevice().getAddress());
        }

        @Override // com.android.settingslib.media.MediaManager.MediaDeviceCallback
        public void onConnectedDeviceChanged(String str) {
            MediaDevice mediaDeviceById;
            synchronized (LocalMediaManager.this.mMediaDevicesLock) {
                LocalMediaManager localMediaManager = LocalMediaManager.this;
                mediaDeviceById = localMediaManager.getMediaDeviceById(localMediaManager.mMediaDevices, str);
            }
            if (mediaDeviceById == null) {
                mediaDeviceById = LocalMediaManager.this.updateCurrentConnectedDevice();
            }
            LocalMediaManager.this.mCurrentConnectedDevice = mediaDeviceById;
            if (mediaDeviceById != null) {
                mediaDeviceById.setState(0);
                LocalMediaManager localMediaManager2 = LocalMediaManager.this;
                localMediaManager2.dispatchSelectedDeviceStateChanged(localMediaManager2.mCurrentConnectedDevice, 0);
            }
        }

        @Override // com.android.settingslib.media.MediaManager.MediaDeviceCallback
        public void onDeviceListAdded(List<MediaDevice> list) {
            int deviceType;
            synchronized (LocalMediaManager.this.mMediaDevicesLock) {
                LocalMediaManager.this.mMediaDevices.clear();
                LocalMediaManager.this.mMediaDevices.addAll(list);
                Iterator<MediaDevice> it = list.iterator();
                do {
                    if (!it.hasNext()) {
                        break;
                    }
                    deviceType = it.next().getDeviceType();
                    if (deviceType == 2 || deviceType == 3) {
                        break;
                    }
                } while (deviceType != 1);
                MediaDevice mutingExpectedDevice = getMutingExpectedDevice();
                if (mutingExpectedDevice != null) {
                    LocalMediaManager.this.mMediaDevices.add(mutingExpectedDevice);
                }
            }
            MediaDevice currentConnectedDevice = LocalMediaManager.this.mInfoMediaManager.getCurrentConnectedDevice();
            LocalMediaManager localMediaManager = LocalMediaManager.this;
            if (currentConnectedDevice == null) {
                currentConnectedDevice = localMediaManager.updateCurrentConnectedDevice();
            }
            localMediaManager.mCurrentConnectedDevice = currentConnectedDevice;
            LocalMediaManager.this.dispatchDeviceListUpdate();
            if (LocalMediaManager.this.mOnTransferBluetoothDevice == null || !LocalMediaManager.this.mOnTransferBluetoothDevice.isConnected()) {
                return;
            }
            LocalMediaManager localMediaManager2 = LocalMediaManager.this;
            localMediaManager2.connectDevice(localMediaManager2.mOnTransferBluetoothDevice);
            LocalMediaManager.this.mOnTransferBluetoothDevice.setState(0);
            LocalMediaManager localMediaManager3 = LocalMediaManager.this;
            localMediaManager3.dispatchSelectedDeviceStateChanged(localMediaManager3.mOnTransferBluetoothDevice, 0);
            LocalMediaManager.this.mOnTransferBluetoothDevice = null;
        }

        @Override // com.android.settingslib.media.MediaManager.MediaDeviceCallback
        public void onRequestFailed(int i) {
            synchronized (LocalMediaManager.this.mMediaDevicesLock) {
                for (MediaDevice mediaDevice : LocalMediaManager.this.mMediaDevices) {
                    if (mediaDevice.getState() == 1) {
                        mediaDevice.setState(3);
                    }
                }
            }
            LocalMediaManager.this.dispatchOnRequestFailed(i);
        }
    }

    public LocalMediaManager(Context context, LocalBluetoothManager localBluetoothManager, InfoMediaManager infoMediaManager, String str) {
        this.mContext = context;
        this.mLocalBluetoothManager = localBluetoothManager;
        this.mInfoMediaManager = infoMediaManager;
        this.mPackageName = str;
        this.mAudioManager = (AudioManager) context.getSystemService(AudioManager.class);
    }

    public boolean addDeviceToPlayMedia(MediaDevice mediaDevice) {
        mediaDevice.setState(5);
        return this.mInfoMediaManager.addDeviceToPlayMedia(mediaDevice);
    }

    public boolean connectDevice(MediaDevice mediaDevice) {
        MediaDevice mediaDeviceById;
        synchronized (this.mMediaDevicesLock) {
            mediaDeviceById = getMediaDeviceById(this.mMediaDevices, mediaDevice.getId());
        }
        if (mediaDeviceById == null) {
            Log.w("LocalMediaManager", "connectDevice() connectDevice not in the list!");
            return false;
        }
        if (mediaDeviceById instanceof BluetoothMediaDevice) {
            CachedBluetoothDevice cachedDevice = ((BluetoothMediaDevice) mediaDeviceById).getCachedDevice();
            if (!cachedDevice.isConnected() && !cachedDevice.isBusy()) {
                this.mOnTransferBluetoothDevice = mediaDevice;
                mediaDeviceById.setState(1);
                cachedDevice.connect();
                return true;
            }
        }
        if (mediaDeviceById.equals(this.mCurrentConnectedDevice)) {
            Log.d("LocalMediaManager", "connectDevice() this device is already connected! : " + mediaDeviceById.getName());
            return false;
        }
        MediaDevice mediaDevice2 = this.mCurrentConnectedDevice;
        if (mediaDevice2 != null) {
            mediaDevice2.disconnect();
        }
        mediaDeviceById.setState(1);
        if (TextUtils.isEmpty(this.mPackageName)) {
            this.mInfoMediaManager.connectDeviceWithoutPackageName(mediaDeviceById);
            return true;
        }
        mediaDeviceById.connect();
        return true;
    }

    public void dispatchAboutToConnectDeviceAdded(String str, String str2, Drawable drawable) {
        for (DeviceCallback deviceCallback : getCallbacks()) {
            deviceCallback.onAboutToConnectDeviceAdded(str, str2, drawable);
        }
    }

    public void dispatchAboutToConnectDeviceRemoved() {
        for (DeviceCallback deviceCallback : getCallbacks()) {
            deviceCallback.onAboutToConnectDeviceRemoved();
        }
    }

    public void dispatchDeviceAttributesChanged() {
        for (DeviceCallback deviceCallback : getCallbacks()) {
            deviceCallback.onDeviceAttributesChanged();
        }
    }

    public void dispatchDeviceListUpdate() {
        ArrayList arrayList = new ArrayList(this.mMediaDevices);
        for (DeviceCallback deviceCallback : getCallbacks()) {
            deviceCallback.onDeviceListUpdate(arrayList);
        }
    }

    public void dispatchOnRequestFailed(int i) {
        for (DeviceCallback deviceCallback : getCallbacks()) {
            deviceCallback.onRequestFailed(i);
        }
    }

    public void dispatchSelectedDeviceStateChanged(MediaDevice mediaDevice, int i) {
        for (DeviceCallback deviceCallback : getCallbacks()) {
            deviceCallback.onSelectedDeviceStateChanged(mediaDevice, i);
        }
    }

    public final Collection<DeviceCallback> getCallbacks() {
        return new CopyOnWriteArrayList(this.mCallbacks);
    }

    public MediaDevice getCurrentConnectedDevice() {
        return this.mCurrentConnectedDevice;
    }

    public List<MediaDevice> getDeselectableMediaDevice() {
        return this.mInfoMediaManager.getDeselectableMediaDevice();
    }

    public MediaDevice getMediaDeviceById(String str) {
        MediaDevice next;
        synchronized (this.mMediaDevicesLock) {
            Iterator<MediaDevice> it = this.mMediaDevices.iterator();
            do {
                if (!it.hasNext()) {
                    Log.i("LocalMediaManager", "Unable to find device " + str);
                    return null;
                }
                next = it.next();
            } while (!TextUtils.equals(next.getId(), str));
            return next;
        }
    }

    public MediaDevice getMediaDeviceById(List<MediaDevice> list, String str) {
        for (MediaDevice mediaDevice : list) {
            if (TextUtils.equals(mediaDevice.getId(), str)) {
                return mediaDevice;
            }
        }
        Log.i("LocalMediaManager", "getMediaDeviceById() can't found device");
        return null;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public List<MediaDevice> getSelectableMediaDevice() {
        return this.mInfoMediaManager.getSelectableMediaDevice();
    }

    public List<MediaDevice> getSelectedMediaDevice() {
        return this.mInfoMediaManager.getSelectedMediaDevice();
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x008a, code lost:
        if (r9 != false) goto L25;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isActiveDevice(CachedBluetoothDevice cachedBluetoothDevice) {
        boolean z;
        LeAudioProfile leAudioProfile;
        HearingAidProfile hearingAidProfile;
        A2dpProfile a2dpProfile = this.mLocalBluetoothManager.getProfileManager().getA2dpProfile();
        boolean equals = a2dpProfile != null ? cachedBluetoothDevice.getDevice().equals(a2dpProfile.getActiveDevice()) : false;
        boolean contains = (equals || (hearingAidProfile = this.mLocalBluetoothManager.getProfileManager().getHearingAidProfile()) == null) ? false : hearingAidProfile.getActiveDevices().contains(cachedBluetoothDevice.getDevice());
        boolean contains2 = (equals || contains || (leAudioProfile = this.mLocalBluetoothManager.getProfileManager().getLeAudioProfile()) == null) ? false : leAudioProfile.getActiveDevices().contains(cachedBluetoothDevice.getDevice());
        if (!equals && !contains) {
            z = false;
        }
        z = true;
        return z;
    }

    public void registerCallback(DeviceCallback deviceCallback) {
        this.mCallbacks.add(deviceCallback);
    }

    public boolean releaseSession() {
        return this.mInfoMediaManager.releaseSession();
    }

    public boolean removeDeviceFromPlayMedia(MediaDevice mediaDevice) {
        mediaDevice.setState(5);
        return this.mInfoMediaManager.removeDeviceFromPlayMedia(mediaDevice);
    }

    public void startScan() {
        synchronized (this.mMediaDevicesLock) {
            this.mMediaDevices.clear();
        }
        this.mInfoMediaManager.registerCallback(this.mMediaDeviceCallback);
        this.mInfoMediaManager.startScan();
    }

    public void stopScan() {
        this.mInfoMediaManager.unregisterCallback(this.mMediaDeviceCallback);
        this.mInfoMediaManager.stopScan();
        unRegisterDeviceAttributeChangeCallback();
    }

    public final void unRegisterDeviceAttributeChangeCallback() {
        Iterator<MediaDevice> it = this.mDisconnectedMediaDevices.iterator();
        while (it.hasNext()) {
            ((BluetoothMediaDevice) it.next()).getCachedDevice().unregisterCallback(this.mDeviceAttributeChangeCallback);
        }
    }

    public void unregisterCallback(DeviceCallback deviceCallback) {
        this.mCallbacks.remove(deviceCallback);
    }

    @VisibleForTesting
    public MediaDevice updateCurrentConnectedDevice() {
        synchronized (this.mMediaDevicesLock) {
            MediaDevice mediaDevice = null;
            for (MediaDevice mediaDevice2 : this.mMediaDevices) {
                if (mediaDevice2 instanceof BluetoothMediaDevice) {
                    if (isActiveDevice(((BluetoothMediaDevice) mediaDevice2).getCachedDevice()) && mediaDevice2.isConnected()) {
                        return mediaDevice2;
                    }
                } else if (mediaDevice2 instanceof PhoneMediaDevice) {
                    mediaDevice = mediaDevice2;
                }
            }
            return mediaDevice;
        }
    }
}