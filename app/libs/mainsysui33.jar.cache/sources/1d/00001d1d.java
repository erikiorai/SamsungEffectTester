package com.android.systemui.media.dialog;

import com.android.settingslib.media.MediaDevice;
import com.android.systemui.R$layout;
import java.util.Optional;

/* loaded from: mainsysui33.jar:com/android/systemui/media/dialog/MediaItem.class */
public class MediaItem {
    public final Optional<MediaDevice> mMediaDeviceOptional;
    public final int mMediaItemType;
    public final String mTitle;

    public MediaItem() {
        this.mMediaDeviceOptional = Optional.empty();
        this.mTitle = null;
        this.mMediaItemType = 2;
    }

    public MediaItem(MediaDevice mediaDevice) {
        this.mMediaDeviceOptional = Optional.of(mediaDevice);
        this.mTitle = mediaDevice.getName();
        this.mMediaItemType = 0;
    }

    public MediaItem(String str, int i) {
        this.mMediaDeviceOptional = Optional.empty();
        this.mTitle = str;
        this.mMediaItemType = i;
    }

    public static int getMediaLayoutId(int i) {
        return (i == 0 || i == 2) ? R$layout.media_output_list_item_advanced : R$layout.media_output_list_group_divider;
    }

    public Optional<MediaDevice> getMediaDevice() {
        return this.mMediaDeviceOptional;
    }

    public int getMediaItemType() {
        return this.mMediaItemType;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public boolean isMutingExpectedDevice() {
        return this.mMediaDeviceOptional.isPresent() && this.mMediaDeviceOptional.get().isMutingExpectedDevice();
    }
}