package com.android.systemui.media.muteawait;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.AudioDeviceAttributes;
import android.media.AudioManager;
import com.android.settingslib.media.DeviceIconUtil;
import com.android.settingslib.media.LocalMediaManager;
import java.util.concurrent.Executor;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/systemui/media/muteawait/MediaMuteAwaitConnectionManager.class */
public final class MediaMuteAwaitConnectionManager {
    public final AudioManager audioManager;
    public final Context context;
    public AudioDeviceAttributes currentMutedDevice;
    public final DeviceIconUtil deviceIconUtil;
    public final LocalMediaManager localMediaManager;
    public final MediaMuteAwaitLogger logger;
    public final Executor mainExecutor;
    public final AudioManager.MuteAwaitConnectionCallback muteAwaitConnectionChangeListener = new AudioManager.MuteAwaitConnectionCallback() { // from class: com.android.systemui.media.muteawait.MediaMuteAwaitConnectionManager$muteAwaitConnectionChangeListener$1
        public void onMutedUntilConnection(AudioDeviceAttributes audioDeviceAttributes, int[] iArr) {
            MediaMuteAwaitLogger mediaMuteAwaitLogger;
            boolean hasMedia;
            boolean hasMedia2;
            LocalMediaManager localMediaManager;
            Drawable icon;
            mediaMuteAwaitLogger = MediaMuteAwaitConnectionManager.this.logger;
            String address = audioDeviceAttributes.getAddress();
            String name = audioDeviceAttributes.getName();
            hasMedia = MediaMuteAwaitConnectionManager.this.hasMedia(iArr);
            mediaMuteAwaitLogger.logMutedDeviceAdded(address, name, hasMedia);
            hasMedia2 = MediaMuteAwaitConnectionManager.this.hasMedia(iArr);
            if (hasMedia2) {
                MediaMuteAwaitConnectionManager.this.setCurrentMutedDevice(audioDeviceAttributes);
                localMediaManager = MediaMuteAwaitConnectionManager.this.localMediaManager;
                String address2 = audioDeviceAttributes.getAddress();
                String name2 = audioDeviceAttributes.getName();
                icon = MediaMuteAwaitConnectionManager.this.getIcon(audioDeviceAttributes);
                localMediaManager.dispatchAboutToConnectDeviceAdded(address2, name2, icon);
            }
        }

        public void onUnmutedEvent(int i, AudioDeviceAttributes audioDeviceAttributes, int[] iArr) {
            MediaMuteAwaitLogger mediaMuteAwaitLogger;
            boolean hasMedia;
            boolean hasMedia2;
            LocalMediaManager localMediaManager;
            boolean areEqual = Intrinsics.areEqual(MediaMuteAwaitConnectionManager.this.getCurrentMutedDevice(), audioDeviceAttributes);
            mediaMuteAwaitLogger = MediaMuteAwaitConnectionManager.this.logger;
            String address = audioDeviceAttributes.getAddress();
            String name = audioDeviceAttributes.getName();
            hasMedia = MediaMuteAwaitConnectionManager.this.hasMedia(iArr);
            mediaMuteAwaitLogger.logMutedDeviceRemoved(address, name, hasMedia, areEqual);
            if (areEqual) {
                hasMedia2 = MediaMuteAwaitConnectionManager.this.hasMedia(iArr);
                if (hasMedia2) {
                    MediaMuteAwaitConnectionManager.this.setCurrentMutedDevice(null);
                    localMediaManager = MediaMuteAwaitConnectionManager.this.localMediaManager;
                    localMediaManager.dispatchAboutToConnectDeviceRemoved();
                }
            }
        }
    };

    public MediaMuteAwaitConnectionManager(Executor executor, LocalMediaManager localMediaManager, Context context, DeviceIconUtil deviceIconUtil, MediaMuteAwaitLogger mediaMuteAwaitLogger) {
        this.mainExecutor = executor;
        this.localMediaManager = localMediaManager;
        this.context = context;
        this.deviceIconUtil = deviceIconUtil;
        this.logger = mediaMuteAwaitLogger;
        this.audioManager = (AudioManager) context.getSystemService("audio");
    }

    public final AudioDeviceAttributes getCurrentMutedDevice() {
        return this.currentMutedDevice;
    }

    public final Drawable getIcon(AudioDeviceAttributes audioDeviceAttributes) {
        return this.deviceIconUtil.getIconFromAudioDeviceType(audioDeviceAttributes.getType(), this.context);
    }

    public final boolean hasMedia(int[] iArr) {
        return ArraysKt___ArraysKt.contains(iArr, 1);
    }

    public final void setCurrentMutedDevice(AudioDeviceAttributes audioDeviceAttributes) {
        this.currentMutedDevice = audioDeviceAttributes;
    }

    public final void startListening() {
        this.audioManager.registerMuteAwaitConnectionCallback(this.mainExecutor, this.muteAwaitConnectionChangeListener);
        AudioDeviceAttributes mutingExpectedDevice = this.audioManager.getMutingExpectedDevice();
        if (mutingExpectedDevice != null) {
            this.currentMutedDevice = mutingExpectedDevice;
            this.localMediaManager.dispatchAboutToConnectDeviceAdded(mutingExpectedDevice.getAddress(), mutingExpectedDevice.getName(), getIcon(mutingExpectedDevice));
        }
    }

    public final void stopListening() {
        this.audioManager.unregisterMuteAwaitConnectionCallback(this.muteAwaitConnectionChangeListener);
    }
}