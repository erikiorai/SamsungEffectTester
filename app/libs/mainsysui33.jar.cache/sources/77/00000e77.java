package com.android.settingslib.media;

import android.app.Notification;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.media.MediaRoute2Info;
import android.media.MediaRouter2Manager;
import android.media.RoutingSessionInfo;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* loaded from: mainsysui33.jar:com/android/settingslib/media/InfoMediaManager.class */
public class InfoMediaManager extends MediaManager {
    public static final boolean DEBUG = Log.isLoggable("InfoMediaManager", 3);
    public LocalBluetoothManager mBluetoothManager;
    public MediaDevice mCurrentConnectedDevice;
    @VisibleForTesting
    public final Executor mExecutor;
    @VisibleForTesting
    public final RouterManagerCallback mMediaRouterCallback;
    @VisibleForTesting
    public String mPackageName;
    @VisibleForTesting
    public MediaRouter2Manager mRouterManager;
    public final boolean mVolumeAdjustmentForRemoteGroupSessions;

    /* loaded from: mainsysui33.jar:com/android/settingslib/media/InfoMediaManager$RouterManagerCallback.class */
    public class RouterManagerCallback implements MediaRouter2Manager.Callback {
        public RouterManagerCallback() {
        }

        public void onPreferredFeaturesChanged(String str, List<String> list) {
            if (TextUtils.equals(InfoMediaManager.this.mPackageName, str)) {
                InfoMediaManager.this.refreshDevices();
            }
        }

        public void onRequestFailed(int i) {
            InfoMediaManager.this.dispatchOnRequestFailed(i);
        }

        public void onRoutesAdded(List<MediaRoute2Info> list) {
            InfoMediaManager.this.refreshDevices();
        }

        public void onRoutesChanged(List<MediaRoute2Info> list) {
            InfoMediaManager.this.refreshDevices();
        }

        public void onRoutesRemoved(List<MediaRoute2Info> list) {
            InfoMediaManager.this.refreshDevices();
        }

        public void onSessionUpdated(RoutingSessionInfo routingSessionInfo) {
            InfoMediaManager.this.refreshDevices();
        }

        public void onTransferFailed(RoutingSessionInfo routingSessionInfo, MediaRoute2Info mediaRoute2Info) {
            InfoMediaManager.this.dispatchOnRequestFailed(0);
        }

        public void onTransferred(RoutingSessionInfo routingSessionInfo, RoutingSessionInfo routingSessionInfo2) {
            if (InfoMediaManager.DEBUG) {
                Log.d("InfoMediaManager", "onTransferred() oldSession : " + ((Object) routingSessionInfo.getName()) + ", newSession : " + ((Object) routingSessionInfo2.getName()));
            }
            InfoMediaManager.this.mMediaDevices.clear();
            String str = null;
            InfoMediaManager.this.mCurrentConnectedDevice = null;
            if (TextUtils.isEmpty(InfoMediaManager.this.mPackageName)) {
                InfoMediaManager.this.buildAllRoutes();
            } else {
                InfoMediaManager.this.buildAvailableRoutes();
            }
            if (InfoMediaManager.this.mCurrentConnectedDevice != null) {
                str = InfoMediaManager.this.mCurrentConnectedDevice.getId();
            }
            InfoMediaManager.this.dispatchConnectedDeviceChanged(str);
        }
    }

    public InfoMediaManager(Context context, String str, Notification notification, LocalBluetoothManager localBluetoothManager) {
        super(context, notification);
        this.mMediaRouterCallback = new RouterManagerCallback();
        this.mExecutor = Executors.newSingleThreadExecutor();
        this.mRouterManager = MediaRouter2Manager.getInstance(context);
        this.mBluetoothManager = localBluetoothManager;
        if (!TextUtils.isEmpty(str)) {
            this.mPackageName = str;
        }
        this.mVolumeAdjustmentForRemoteGroupSessions = context.getResources().getBoolean(17891865);
    }

    public boolean addDeviceToPlayMedia(MediaDevice mediaDevice) {
        if (TextUtils.isEmpty(this.mPackageName)) {
            Log.w("InfoMediaManager", "addDeviceToPlayMedia() package name is null or empty!");
            return false;
        }
        RoutingSessionInfo routingSessionInfo = getRoutingSessionInfo();
        if (routingSessionInfo != null && routingSessionInfo.getSelectableRoutes().contains(mediaDevice.mRouteInfo.getId())) {
            this.mRouterManager.selectRoute(routingSessionInfo, mediaDevice.mRouteInfo);
            return true;
        }
        Log.w("InfoMediaManager", "addDeviceToPlayMedia() Ignoring selecting a non-selectable device : " + mediaDevice.getName());
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x0131  */
    /* JADX WARN: Removed duplicated region for block: B:45:? A[RETURN, SYNTHETIC] */
    @VisibleForTesting
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void addMediaDevice(MediaRoute2Info mediaRoute2Info) {
        MediaDevice mediaDevice;
        int type = mediaRoute2Info.getType();
        if (type != 0) {
            if (type != 26) {
                if (type != 2000) {
                    if (type != 2 && type != 3 && type != 4) {
                        if (type != 8) {
                            if (type != 9 && type != 22) {
                                if (type != 23) {
                                    if (type != 1001 && type != 1002) {
                                        switch (type) {
                                            case 11:
                                            case 12:
                                            case 13:
                                                break;
                                            default:
                                                Log.w("InfoMediaManager", "addMediaDevice() unknown device type : " + type);
                                                mediaDevice = null;
                                                break;
                                        }
                                        if (mediaDevice != null) {
                                            this.mMediaDevices.add(mediaDevice);
                                            return;
                                        }
                                        return;
                                    }
                                }
                            }
                        }
                    }
                    mediaDevice = new PhoneMediaDevice(this.mContext, this.mRouterManager, mediaRoute2Info, this.mPackageName);
                    if (mediaDevice != null) {
                    }
                }
            }
            CachedBluetoothDevice findDevice = this.mBluetoothManager.getCachedDeviceManager().findDevice(BluetoothAdapter.getDefaultAdapter().getRemoteDevice(mediaRoute2Info.getAddress()));
            if (findDevice != null) {
                mediaDevice = new BluetoothMediaDevice(this.mContext, findDevice, this.mRouterManager, mediaRoute2Info, this.mPackageName);
                if (mediaDevice != null) {
                }
            }
            mediaDevice = null;
            if (mediaDevice != null) {
            }
        }
        MediaDevice infoMediaDevice = new InfoMediaDevice(this.mContext, this.mRouterManager, mediaRoute2Info, this.mPackageName);
        mediaDevice = infoMediaDevice;
        if (!TextUtils.isEmpty(this.mPackageName)) {
            mediaDevice = infoMediaDevice;
            if (getRoutingSessionInfo().getSelectedRoutes().contains(mediaRoute2Info.getId())) {
                infoMediaDevice.setState(4);
                mediaDevice = infoMediaDevice;
                if (this.mCurrentConnectedDevice == null) {
                    this.mCurrentConnectedDevice = infoMediaDevice;
                    mediaDevice = infoMediaDevice;
                }
            }
        }
        if (mediaDevice != null) {
        }
    }

    public final void buildAllRoutes() {
        for (MediaRoute2Info mediaRoute2Info : this.mRouterManager.getAllRoutes()) {
            if (DEBUG) {
                Log.d("InfoMediaManager", "buildAllRoutes() route : " + ((Object) mediaRoute2Info.getName()) + ", volume : " + mediaRoute2Info.getVolume() + ", type : " + mediaRoute2Info.getType());
            }
            if (mediaRoute2Info.isSystemRoute()) {
                addMediaDevice(mediaRoute2Info);
            }
        }
    }

    public final void buildAvailableRoutes() {
        synchronized (this) {
            for (MediaRoute2Info mediaRoute2Info : getAvailableRoutes(this.mPackageName)) {
                if (DEBUG) {
                    Log.d("InfoMediaManager", "buildAvailableRoutes() route : " + ((Object) mediaRoute2Info.getName()) + ", volume : " + mediaRoute2Info.getVolume() + ", type : " + mediaRoute2Info.getType());
                }
                addMediaDevice(mediaRoute2Info);
            }
        }
    }

    public boolean connectDeviceWithoutPackageName(MediaDevice mediaDevice) {
        boolean z;
        RoutingSessionInfo systemRoutingSession = this.mRouterManager.getSystemRoutingSession((String) null);
        if (systemRoutingSession != null) {
            this.mRouterManager.transfer(systemRoutingSession, mediaDevice.mRouteInfo);
            z = true;
        } else {
            z = false;
        }
        return z;
    }

    public final List<MediaRoute2Info> getAvailableRoutes(String str) {
        ArrayList arrayList;
        boolean z;
        synchronized (this) {
            arrayList = new ArrayList();
            RoutingSessionInfo routingSessionInfo = getRoutingSessionInfo(str);
            if (routingSessionInfo != null) {
                arrayList.addAll(this.mRouterManager.getSelectedRoutes(routingSessionInfo));
                arrayList.addAll(this.mRouterManager.getSelectableRoutes(routingSessionInfo));
            }
            for (MediaRoute2Info mediaRoute2Info : this.mRouterManager.getTransferableRoutes(str)) {
                Iterator it = arrayList.iterator();
                while (true) {
                    z = false;
                    if (!it.hasNext()) {
                        break;
                    }
                    if (TextUtils.equals(mediaRoute2Info.getId(), ((MediaRoute2Info) it.next()).getId())) {
                        z = true;
                        break;
                    }
                }
                if (!z) {
                    arrayList.add(mediaRoute2Info);
                }
            }
        }
        return arrayList;
    }

    public MediaDevice getCurrentConnectedDevice() {
        return this.mCurrentConnectedDevice;
    }

    public List<MediaDevice> getDeselectableMediaDevice() {
        ArrayList arrayList = new ArrayList();
        if (TextUtils.isEmpty(this.mPackageName)) {
            Log.d("InfoMediaManager", "getDeselectableMediaDevice() package name is null or empty!");
            return arrayList;
        }
        RoutingSessionInfo routingSessionInfo = getRoutingSessionInfo();
        if (routingSessionInfo == null) {
            Log.d("InfoMediaManager", "getDeselectableMediaDevice() cannot found deselectable MediaDevice from : " + this.mPackageName);
            return arrayList;
        }
        for (MediaRoute2Info mediaRoute2Info : this.mRouterManager.getDeselectableRoutes(routingSessionInfo)) {
            arrayList.add(new InfoMediaDevice(this.mContext, this.mRouterManager, mediaRoute2Info, this.mPackageName));
            Log.d("InfoMediaManager", ((Object) mediaRoute2Info.getName()) + " is deselectable for " + this.mPackageName);
        }
        return arrayList;
    }

    public final RoutingSessionInfo getRoutingSessionInfo() {
        return getRoutingSessionInfo(this.mPackageName);
    }

    public final RoutingSessionInfo getRoutingSessionInfo(String str) {
        List routingSessions = this.mRouterManager.getRoutingSessions(str);
        if (routingSessions == null || routingSessions.isEmpty()) {
            return null;
        }
        return (RoutingSessionInfo) routingSessions.get(routingSessions.size() - 1);
    }

    public List<MediaDevice> getSelectableMediaDevice() {
        ArrayList arrayList = new ArrayList();
        if (TextUtils.isEmpty(this.mPackageName)) {
            Log.w("InfoMediaManager", "getSelectableMediaDevice() package name is null or empty!");
            return arrayList;
        }
        RoutingSessionInfo routingSessionInfo = getRoutingSessionInfo();
        if (routingSessionInfo == null) {
            Log.w("InfoMediaManager", "getSelectableMediaDevice() cannot found selectable MediaDevice from : " + this.mPackageName);
            return arrayList;
        }
        for (MediaRoute2Info mediaRoute2Info : this.mRouterManager.getSelectableRoutes(routingSessionInfo)) {
            arrayList.add(new InfoMediaDevice(this.mContext, this.mRouterManager, mediaRoute2Info, this.mPackageName));
        }
        return arrayList;
    }

    public List<MediaDevice> getSelectedMediaDevice() {
        ArrayList arrayList = new ArrayList();
        if (TextUtils.isEmpty(this.mPackageName)) {
            Log.w("InfoMediaManager", "getSelectedMediaDevice() package name is null or empty!");
            return arrayList;
        }
        RoutingSessionInfo routingSessionInfo = getRoutingSessionInfo();
        if (routingSessionInfo == null) {
            Log.w("InfoMediaManager", "getSelectedMediaDevice() cannot found selectable MediaDevice from : " + this.mPackageName);
            return arrayList;
        }
        for (MediaRoute2Info mediaRoute2Info : this.mRouterManager.getSelectedRoutes(routingSessionInfo)) {
            arrayList.add(new InfoMediaDevice(this.mContext, this.mRouterManager, mediaRoute2Info, this.mPackageName));
        }
        return arrayList;
    }

    public final void refreshDevices() {
        synchronized (this) {
            this.mMediaDevices.clear();
            this.mCurrentConnectedDevice = null;
            if (TextUtils.isEmpty(this.mPackageName)) {
                buildAllRoutes();
            } else {
                buildAvailableRoutes();
            }
            dispatchDeviceListAdded();
        }
    }

    public boolean releaseSession() {
        if (TextUtils.isEmpty(this.mPackageName)) {
            Log.w("InfoMediaManager", "releaseSession() package name is null or empty!");
            return false;
        }
        RoutingSessionInfo routingSessionInfo = getRoutingSessionInfo();
        if (routingSessionInfo != null) {
            this.mRouterManager.releaseSession(routingSessionInfo);
            return true;
        }
        Log.w("InfoMediaManager", "releaseSession() Ignoring release session : " + this.mPackageName);
        return false;
    }

    public boolean removeDeviceFromPlayMedia(MediaDevice mediaDevice) {
        if (TextUtils.isEmpty(this.mPackageName)) {
            Log.w("InfoMediaManager", "removeDeviceFromMedia() package name is null or empty!");
            return false;
        }
        RoutingSessionInfo routingSessionInfo = getRoutingSessionInfo();
        if (routingSessionInfo != null && routingSessionInfo.getSelectedRoutes().contains(mediaDevice.mRouteInfo.getId())) {
            this.mRouterManager.deselectRoute(routingSessionInfo, mediaDevice.mRouteInfo);
            return true;
        }
        Log.w("InfoMediaManager", "removeDeviceFromMedia() Ignoring deselecting a non-deselectable device : " + mediaDevice.getName());
        return false;
    }

    public void startScan() {
        this.mMediaDevices.clear();
        this.mRouterManager.registerCallback(this.mExecutor, this.mMediaRouterCallback);
        this.mRouterManager.registerScanRequest();
        refreshDevices();
    }

    public void stopScan() {
        this.mRouterManager.unregisterCallback(this.mMediaRouterCallback);
        this.mRouterManager.unregisterScanRequest();
    }
}