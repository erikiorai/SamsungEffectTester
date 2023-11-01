package com.android.systemui.media.controls.pipeline;

import com.android.settingslib.media.MediaDevice;
import com.android.systemui.media.controls.models.player.MediaDeviceData;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/pipeline/AboutToConnectDevice.class */
public final class AboutToConnectDevice {
    public final MediaDeviceData backupMediaDeviceData;
    public final MediaDevice fullMediaDevice;

    public AboutToConnectDevice() {
        this(null, null, 3, null);
    }

    public AboutToConnectDevice(MediaDevice mediaDevice, MediaDeviceData mediaDeviceData) {
        this.fullMediaDevice = mediaDevice;
        this.backupMediaDeviceData = mediaDeviceData;
    }

    public /* synthetic */ AboutToConnectDevice(MediaDevice mediaDevice, MediaDeviceData mediaDeviceData, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : mediaDevice, (i & 2) != 0 ? null : mediaDeviceData);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof AboutToConnectDevice) {
            AboutToConnectDevice aboutToConnectDevice = (AboutToConnectDevice) obj;
            return Intrinsics.areEqual(this.fullMediaDevice, aboutToConnectDevice.fullMediaDevice) && Intrinsics.areEqual(this.backupMediaDeviceData, aboutToConnectDevice.backupMediaDeviceData);
        }
        return false;
    }

    public final MediaDeviceData getBackupMediaDeviceData() {
        return this.backupMediaDeviceData;
    }

    public final MediaDevice getFullMediaDevice() {
        return this.fullMediaDevice;
    }

    public int hashCode() {
        MediaDevice mediaDevice = this.fullMediaDevice;
        int i = 0;
        int hashCode = mediaDevice == null ? 0 : mediaDevice.hashCode();
        MediaDeviceData mediaDeviceData = this.backupMediaDeviceData;
        if (mediaDeviceData != null) {
            i = mediaDeviceData.hashCode();
        }
        return (hashCode * 31) + i;
    }

    public String toString() {
        MediaDevice mediaDevice = this.fullMediaDevice;
        MediaDeviceData mediaDeviceData = this.backupMediaDeviceData;
        return "AboutToConnectDevice(fullMediaDevice=" + mediaDevice + ", backupMediaDeviceData=" + mediaDeviceData + ")";
    }
}