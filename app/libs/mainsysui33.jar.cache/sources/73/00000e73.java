package com.android.settingslib.media;

import android.content.Context;
import android.content.SharedPreferences;

/* loaded from: mainsysui33.jar:com/android/settingslib/media/ConnectionRecordManager.class */
public class ConnectionRecordManager {
    public static ConnectionRecordManager sInstance;
    public static final Object sInstanceSync = new Object();
    public String mLastSelectedDevice;

    public static ConnectionRecordManager getInstance() {
        synchronized (sInstanceSync) {
            if (sInstance == null) {
                sInstance = new ConnectionRecordManager();
            }
        }
        return sInstance;
    }

    public int fetchConnectionRecord(Context context, String str) {
        int i;
        synchronized (this) {
            i = getSharedPreferences(context).getInt(str, 0);
        }
        return i;
    }

    public void fetchLastSelectedDevice(Context context) {
        synchronized (this) {
            this.mLastSelectedDevice = getSharedPreferences(context).getString("last_selected_device", null);
        }
    }

    public String getLastSelectedDevice() {
        String str;
        synchronized (this) {
            str = this.mLastSelectedDevice;
        }
        return str;
    }

    public final SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences("seamless_transfer_record", 0);
    }

    public void setConnectionRecord(Context context, String str, int i) {
        synchronized (this) {
            SharedPreferences.Editor edit = getSharedPreferences(context).edit();
            this.mLastSelectedDevice = str;
            edit.putInt(str, i);
            edit.putString("last_selected_device", this.mLastSelectedDevice);
            edit.apply();
        }
    }
}