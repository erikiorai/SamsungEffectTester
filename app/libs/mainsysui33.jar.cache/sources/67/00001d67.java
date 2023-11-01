package com.android.systemui.media.dialog;

import android.content.Context;
import android.util.Log;
import androidx.recyclerview.widget.RecyclerView;
import com.android.settingslib.media.MediaDevice;
import com.android.systemui.shared.system.SysUiStatsLog;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/media/dialog/MediaOutputMetricLogger.class */
public class MediaOutputMetricLogger {
    public static final boolean DEBUG = Log.isLoggable("MediaOutputMetricLogger", 3);
    public int mAppliedDeviceCountWithinRemoteGroup;
    public int mConnectedBluetoothDeviceCount;
    public final Context mContext;
    public final String mPackageName;
    public int mRemoteDeviceCount;
    public MediaDevice mSourceDevice;
    public MediaDevice mTargetDevice;
    public int mWiredDeviceCount;

    public MediaOutputMetricLogger(Context context, String str) {
        this.mContext = context;
        this.mPackageName = str;
    }

    public final int getInteractionDeviceType(MediaDevice mediaDevice) {
        if (mediaDevice == null) {
            return 0;
        }
        int deviceType = mediaDevice.getDeviceType();
        if (deviceType != 1) {
            if (deviceType != 2) {
                if (deviceType != 3) {
                    if (deviceType != 5) {
                        if (deviceType != 6) {
                            return deviceType != 7 ? 0 : 500;
                        }
                        return 400;
                    }
                    return 300;
                }
                return 100;
            }
            return 200;
        }
        return 1;
    }

    public final int getLoggingDeviceType(MediaDevice mediaDevice, boolean z) {
        if (mediaDevice == null) {
            return 0;
        }
        int deviceType = mediaDevice.getDeviceType();
        if (deviceType != 1) {
            if (deviceType != 2) {
                if (deviceType != 3) {
                    if (deviceType != 5) {
                        if (deviceType != 6) {
                            return deviceType != 7 ? 0 : 500;
                        }
                        return 400;
                    }
                    return 300;
                }
                return 100;
            }
            return 200;
        }
        return 1;
    }

    public final String getLoggingPackageName() {
        String str = this.mPackageName;
        if (str == null || str.isEmpty()) {
            return "";
        }
        try {
            int i = this.mContext.getPackageManager().getApplicationInfo(this.mPackageName, 0).flags;
            return ((i & 1) == 0 && (i & RecyclerView.ViewHolder.FLAG_IGNORE) == 0) ? "" : this.mPackageName;
        } catch (Exception e) {
            Log.e("MediaOutputMetricLogger", this.mPackageName + " is invalid.");
            return "";
        }
    }

    public final int getLoggingSwitchOpSubResult(int i) {
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    return i != 4 ? 0 : 5;
                }
                return 4;
            }
            return 3;
        }
        return 2;
    }

    public void logInteractionAdjustVolume(MediaDevice mediaDevice) {
        if (DEBUG) {
            Log.d("MediaOutputMetricLogger", "logInteraction - AdjustVolume");
        }
        SysUiStatsLog.write(466, 1, getInteractionDeviceType(mediaDevice), getLoggingPackageName());
    }

    public void logInteractionExpansion(MediaDevice mediaDevice) {
        if (DEBUG) {
            Log.d("MediaOutputMetricLogger", "logInteraction - Expansion");
        }
        SysUiStatsLog.write(466, 0, getInteractionDeviceType(mediaDevice), getLoggingPackageName());
    }

    public void logInteractionStopCasting() {
        if (DEBUG) {
            Log.d("MediaOutputMetricLogger", "logInteraction - Stop casting");
        }
        SysUiStatsLog.write(466, 2, 0, getLoggingPackageName());
    }

    public void logOutputFailure(List<MediaDevice> list, int i) {
        if (DEBUG) {
            Log.e("MediaOutputMetricLogger", "logRequestFailed - " + i);
        }
        updateLoggingDeviceCount(list);
        SysUiStatsLog.write(277, getLoggingDeviceType(this.mSourceDevice, true), getLoggingDeviceType(this.mTargetDevice, false), 0, getLoggingSwitchOpSubResult(i), getLoggingPackageName(), this.mWiredDeviceCount, this.mConnectedBluetoothDeviceCount, this.mRemoteDeviceCount, this.mAppliedDeviceCountWithinRemoteGroup);
    }

    public void logOutputItemFailure(List<MediaItem> list, int i) {
        if (DEBUG) {
            Log.e("MediaOutputMetricLogger", "logRequestFailed - " + i);
        }
        updateLoggingMediaItemCount(list);
        SysUiStatsLog.write(277, getLoggingDeviceType(this.mSourceDevice, true), getLoggingDeviceType(this.mTargetDevice, false), 0, getLoggingSwitchOpSubResult(i), getLoggingPackageName(), this.mWiredDeviceCount, this.mConnectedBluetoothDeviceCount, this.mRemoteDeviceCount, this.mAppliedDeviceCountWithinRemoteGroup);
    }

    public void logOutputItemSuccess(String str, List<MediaItem> list) {
        if (DEBUG) {
            Log.d("MediaOutputMetricLogger", "logOutputSuccess - selected device: " + str);
        }
        updateLoggingMediaItemCount(list);
        SysUiStatsLog.write(277, getLoggingDeviceType(this.mSourceDevice, true), getLoggingDeviceType(this.mTargetDevice, false), 1, 1, getLoggingPackageName(), this.mWiredDeviceCount, this.mConnectedBluetoothDeviceCount, this.mRemoteDeviceCount, this.mAppliedDeviceCountWithinRemoteGroup);
    }

    public void logOutputSuccess(String str, List<MediaDevice> list) {
        if (DEBUG) {
            Log.d("MediaOutputMetricLogger", "logOutputSuccess - selected device: " + str);
        }
        updateLoggingDeviceCount(list);
        SysUiStatsLog.write(277, getLoggingDeviceType(this.mSourceDevice, true), getLoggingDeviceType(this.mTargetDevice, false), 1, 1, getLoggingPackageName(), this.mWiredDeviceCount, this.mConnectedBluetoothDeviceCount, this.mRemoteDeviceCount, this.mAppliedDeviceCountWithinRemoteGroup);
    }

    public final void updateLoggingDeviceCount(List<MediaDevice> list) {
        this.mRemoteDeviceCount = 0;
        this.mConnectedBluetoothDeviceCount = 0;
        this.mWiredDeviceCount = 0;
        this.mAppliedDeviceCountWithinRemoteGroup = 0;
        for (MediaDevice mediaDevice : list) {
            if (mediaDevice.isConnected()) {
                int deviceType = mediaDevice.getDeviceType();
                if (deviceType == 2 || deviceType == 3) {
                    this.mWiredDeviceCount++;
                } else if (deviceType == 5) {
                    this.mConnectedBluetoothDeviceCount++;
                } else if (deviceType == 6 || deviceType == 7) {
                    this.mRemoteDeviceCount++;
                }
            }
        }
        if (DEBUG) {
            Log.d("MediaOutputMetricLogger", "connected devices: wired: " + this.mWiredDeviceCount + " bluetooth: " + this.mConnectedBluetoothDeviceCount + " remote: " + this.mRemoteDeviceCount);
        }
    }

    public final void updateLoggingMediaItemCount(List<MediaItem> list) {
        this.mRemoteDeviceCount = 0;
        this.mConnectedBluetoothDeviceCount = 0;
        this.mWiredDeviceCount = 0;
        this.mAppliedDeviceCountWithinRemoteGroup = 0;
        for (MediaItem mediaItem : list) {
            if (mediaItem.getMediaDevice().isPresent() && mediaItem.getMediaDevice().get().isConnected()) {
                int deviceType = mediaItem.getMediaDevice().get().getDeviceType();
                if (deviceType == 2 || deviceType == 3) {
                    this.mWiredDeviceCount++;
                } else if (deviceType == 5) {
                    this.mConnectedBluetoothDeviceCount++;
                } else if (deviceType == 6 || deviceType == 7) {
                    this.mRemoteDeviceCount++;
                }
            }
        }
        if (DEBUG) {
            Log.d("MediaOutputMetricLogger", "connected devices: wired: " + this.mWiredDeviceCount + " bluetooth: " + this.mConnectedBluetoothDeviceCount + " remote: " + this.mRemoteDeviceCount);
        }
    }

    public void updateOutputEndPoints(MediaDevice mediaDevice, MediaDevice mediaDevice2) {
        this.mSourceDevice = mediaDevice;
        this.mTargetDevice = mediaDevice2;
        if (DEBUG) {
            Log.d("MediaOutputMetricLogger", "updateOutputEndPoints - source:" + this.mSourceDevice.toString() + " target:" + this.mTargetDevice.toString());
        }
    }
}