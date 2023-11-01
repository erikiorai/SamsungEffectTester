package com.android.settingslib.media;

import android.app.Notification;
import android.content.Context;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: mainsysui33.jar:com/android/settingslib/media/MediaManager.class */
public abstract class MediaManager {
    public Context mContext;
    public Notification mNotification;
    public final Collection<MediaDeviceCallback> mCallbacks = new CopyOnWriteArrayList();
    public final List<MediaDevice> mMediaDevices = new CopyOnWriteArrayList();

    /* loaded from: mainsysui33.jar:com/android/settingslib/media/MediaManager$MediaDeviceCallback.class */
    public interface MediaDeviceCallback {
        void onConnectedDeviceChanged(String str);

        void onDeviceListAdded(List<MediaDevice> list);

        void onRequestFailed(int i);
    }

    public MediaManager(Context context, Notification notification) {
        this.mContext = context;
        this.mNotification = notification;
    }

    public void dispatchConnectedDeviceChanged(String str) {
        for (MediaDeviceCallback mediaDeviceCallback : getCallbacks()) {
            mediaDeviceCallback.onConnectedDeviceChanged(str);
        }
    }

    public void dispatchDeviceListAdded() {
        for (MediaDeviceCallback mediaDeviceCallback : getCallbacks()) {
            mediaDeviceCallback.onDeviceListAdded(new ArrayList(this.mMediaDevices));
        }
    }

    public void dispatchOnRequestFailed(int i) {
        for (MediaDeviceCallback mediaDeviceCallback : getCallbacks()) {
            mediaDeviceCallback.onRequestFailed(i);
        }
    }

    public final Collection<MediaDeviceCallback> getCallbacks() {
        return new CopyOnWriteArrayList(this.mCallbacks);
    }

    public void registerCallback(MediaDeviceCallback mediaDeviceCallback) {
        if (this.mCallbacks.contains(mediaDeviceCallback)) {
            return;
        }
        this.mCallbacks.add(mediaDeviceCallback);
    }

    public void unregisterCallback(MediaDeviceCallback mediaDeviceCallback) {
        if (this.mCallbacks.contains(mediaDeviceCallback)) {
            this.mCallbacks.remove(mediaDeviceCallback);
        }
    }
}