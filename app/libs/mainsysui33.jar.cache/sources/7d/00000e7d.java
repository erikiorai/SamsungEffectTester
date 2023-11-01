package com.android.settingslib.media;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaRoute2Info;
import android.media.MediaRouter2Manager;
import android.media.NearbyDevice;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/settingslib/media/MediaDevice.class */
public abstract class MediaDevice implements Comparable<MediaDevice> {
    public int mConnectedRecord;
    public final Context mContext;
    public final String mPackageName;
    public int mRangeZone = 0;
    public final MediaRoute2Info mRouteInfo;
    public final MediaRouter2Manager mRouterManager;
    public int mState;
    public int mType;

    public MediaDevice(Context context, MediaRouter2Manager mediaRouter2Manager, MediaRoute2Info mediaRoute2Info, String str) {
        this.mContext = context;
        this.mRouteInfo = mediaRoute2Info;
        this.mRouterManager = mediaRouter2Manager;
        this.mPackageName = str;
        setType(mediaRoute2Info);
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // java.lang.Comparable
    public int compareTo(MediaDevice mediaDevice) {
        int i = -1;
        if (mediaDevice == null) {
            return -1;
        }
        if (isConnected() ^ mediaDevice.isConnected()) {
            return isConnected() ? -1 : 1;
        } else if (getState() == 4) {
            return -1;
        } else {
            if (mediaDevice.getState() == 4) {
                return 1;
            }
            int i2 = this.mType;
            int i3 = mediaDevice.mType;
            if (i2 != i3) {
                if (i2 >= i3) {
                    i = 1;
                }
                return i;
            } else if (isMutingExpectedDevice()) {
                return -1;
            } else {
                if (mediaDevice.isMutingExpectedDevice()) {
                    return 1;
                }
                if (isFastPairDevice()) {
                    return -1;
                }
                if (mediaDevice.isFastPairDevice()) {
                    return 1;
                }
                if (isCarKitDevice()) {
                    return -1;
                }
                if (mediaDevice.isCarKitDevice()) {
                    return 1;
                }
                if (NearbyDevice.compareRangeZones(getRangeZone(), mediaDevice.getRangeZone()) != 0) {
                    return NearbyDevice.compareRangeZones(getRangeZone(), mediaDevice.getRangeZone());
                }
                String lastSelectedDevice = ConnectionRecordManager.getInstance().getLastSelectedDevice();
                if (TextUtils.equals(lastSelectedDevice, getId())) {
                    return -1;
                }
                if (TextUtils.equals(lastSelectedDevice, mediaDevice.getId())) {
                    return 1;
                }
                int i4 = this.mConnectedRecord;
                int i5 = mediaDevice.mConnectedRecord;
                return (i4 == i5 || (i5 <= 0 && i4 <= 0)) ? getName().compareToIgnoreCase(mediaDevice.getName()) : i5 - i4;
            }
        }
    }

    public boolean connect() {
        if (this.mRouteInfo == null) {
            Log.w("MediaDevice", "Unable to connect. RouteInfo is empty");
            return false;
        }
        setConnectedRecord();
        this.mRouterManager.selectRoute(this.mPackageName, this.mRouteInfo);
        return true;
    }

    public void disconnect() {
    }

    public boolean equals(Object obj) {
        if (obj instanceof MediaDevice) {
            return ((MediaDevice) obj).getId().equals(getId());
        }
        return false;
    }

    public int getCurrentVolume() {
        MediaRoute2Info mediaRoute2Info = this.mRouteInfo;
        if (mediaRoute2Info == null) {
            Log.w("MediaDevice", "Unable to get current volume. RouteInfo is empty");
            return 0;
        }
        return mediaRoute2Info.getVolume();
    }

    public int getDeviceType() {
        return this.mType;
    }

    public List<String> getFeatures() {
        MediaRoute2Info mediaRoute2Info = this.mRouteInfo;
        if (mediaRoute2Info == null) {
            Log.w("MediaDevice", "Unable to get features. RouteInfo is empty");
            return new ArrayList();
        }
        return mediaRoute2Info.getFeatures();
    }

    public abstract Drawable getIcon();

    public abstract Drawable getIconWithoutBackground();

    public abstract String getId();

    public int getMaxVolume() {
        MediaRoute2Info mediaRoute2Info = this.mRouteInfo;
        if (mediaRoute2Info == null) {
            Log.w("MediaDevice", "Unable to get max volume. RouteInfo is empty");
            return 0;
        }
        return mediaRoute2Info.getVolumeMax();
    }

    public abstract String getName();

    public int getRangeZone() {
        return this.mRangeZone;
    }

    public int getState() {
        return this.mState;
    }

    public void initDeviceRecord() {
        ConnectionRecordManager.getInstance().fetchLastSelectedDevice(this.mContext);
        this.mConnectedRecord = ConnectionRecordManager.getInstance().fetchConnectionRecord(this.mContext, getId());
    }

    public boolean isBLEDevice() {
        return this.mRouteInfo.getType() == 26;
    }

    public boolean isCarKitDevice() {
        return false;
    }

    public abstract boolean isConnected();

    public boolean isFastPairDevice() {
        return false;
    }

    public boolean isMutingExpectedDevice() {
        return false;
    }

    @SuppressLint({"NewApi"})
    public boolean isVolumeFixed() {
        MediaRoute2Info mediaRoute2Info = this.mRouteInfo;
        boolean z = true;
        if (mediaRoute2Info == null) {
            Log.w("MediaDevice", "RouteInfo is empty, regarded as volume fixed.");
            return true;
        }
        if (mediaRoute2Info.getVolumeHandling() != 0) {
            z = false;
        }
        return z;
    }

    public void requestSetVolume(int i) {
        MediaRoute2Info mediaRoute2Info = this.mRouteInfo;
        if (mediaRoute2Info == null) {
            Log.w("MediaDevice", "Unable to set volume. RouteInfo is empty");
        } else {
            this.mRouterManager.setRouteVolume(mediaRoute2Info, i);
        }
    }

    public void setConnectedRecord() {
        this.mConnectedRecord++;
        ConnectionRecordManager.getInstance().setConnectionRecord(this.mContext, getId(), this.mConnectedRecord);
    }

    public void setRangeZone(int i) {
        this.mRangeZone = i;
    }

    public void setState(int i) {
        this.mState = i;
    }

    public final void setType(MediaRoute2Info mediaRoute2Info) {
        if (mediaRoute2Info == null) {
            this.mType = 5;
            return;
        }
        int type = mediaRoute2Info.getType();
        if (type == 2) {
            this.mType = 1;
        } else if (type == 3 || type == 4) {
            this.mType = 3;
        } else {
            if (type != 8) {
                if (type != 9 && type != 22) {
                    if (type != 23 && type != 26) {
                        if (type == 2000) {
                            this.mType = 7;
                            return;
                        }
                        switch (type) {
                            case 11:
                            case 12:
                            case 13:
                                break;
                            default:
                                this.mType = 6;
                                return;
                        }
                    }
                }
                this.mType = 2;
                return;
            }
            this.mType = 5;
        }
    }
}