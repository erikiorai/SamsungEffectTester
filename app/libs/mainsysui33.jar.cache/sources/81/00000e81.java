package com.android.settingslib.media;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.MediaRoute2Info;
import android.media.MediaRouter2Manager;
import com.android.settingslib.R$string;

/* loaded from: mainsysui33.jar:com/android/settingslib/media/PhoneMediaDevice.class */
public class PhoneMediaDevice extends MediaDevice {
    public final DeviceIconUtil mDeviceIconUtil;
    public String mSummary;

    public PhoneMediaDevice(Context context, MediaRouter2Manager mediaRouter2Manager, MediaRoute2Info mediaRoute2Info, String str) {
        super(context, mediaRouter2Manager, mediaRoute2Info, str);
        this.mSummary = "";
        this.mDeviceIconUtil = new DeviceIconUtil();
        initDeviceRecord();
    }

    public int getDrawableResId() {
        return this.mDeviceIconUtil.getIconResIdFromMediaRouteType(this.mRouteInfo.getType());
    }

    @Override // com.android.settingslib.media.MediaDevice
    public Drawable getIcon() {
        return getIconWithoutBackground();
    }

    @Override // com.android.settingslib.media.MediaDevice
    public Drawable getIconWithoutBackground() {
        return this.mContext.getDrawable(getDrawableResId());
    }

    @Override // com.android.settingslib.media.MediaDevice
    public String getId() {
        String str;
        int type = this.mRouteInfo.getType();
        if (type == 3 || type == 4) {
            str = "wired_headset_media_device_id";
        } else {
            if (type != 9 && type != 22) {
                switch (type) {
                    case 11:
                    case 12:
                    case 13:
                        break;
                    default:
                        str = "phone_media_device_id";
                        break;
                }
            }
            str = "usb_headset_media_device_id";
        }
        return str;
    }

    @Override // com.android.settingslib.media.MediaDevice
    public String getName() {
        String string;
        int type = this.mRouteInfo.getType();
        if (type != 3 && type != 4) {
            if (type != 9) {
                if (type != 22) {
                    switch (type) {
                        case 11:
                        case 12:
                            break;
                        case 13:
                            break;
                        default:
                            string = this.mContext.getString(R$string.media_transfer_this_device_name);
                            break;
                    }
                    return string.toString();
                }
            }
            string = this.mRouteInfo.getName();
            return string.toString();
        }
        string = this.mContext.getString(R$string.media_transfer_wired_usb_device_name);
        return string.toString();
    }

    @Override // com.android.settingslib.media.MediaDevice
    public boolean isConnected() {
        return true;
    }
}