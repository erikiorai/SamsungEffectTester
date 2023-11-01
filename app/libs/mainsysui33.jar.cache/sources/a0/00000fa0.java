package com.android.systemui;

import android.util.ArrayMap;
import android.util.ArraySet;
import java.util.Arrays;

/* loaded from: mainsysui33.jar:com/android/systemui/ForegroundServicesUserState.class */
public class ForegroundServicesUserState {
    public String[] mRunning = null;
    public long mServiceStartTime = 0;
    public ArrayMap<String, ArraySet<String>> mImportantNotifications = new ArrayMap<>(1);
    public ArrayMap<String, ArraySet<String>> mStandardLayoutNotifications = new ArrayMap<>(1);
    public ArrayMap<String, ArraySet<Integer>> mAppOps = new ArrayMap<>(1);

    public void addImportantNotification(String str, String str2) {
        addNotification(this.mImportantNotifications, str, str2);
    }

    public void addNotification(ArrayMap<String, ArraySet<String>> arrayMap, String str, String str2) {
        if (arrayMap.get(str) == null) {
            arrayMap.put(str, new ArraySet<>());
        }
        arrayMap.get(str).add(str2);
    }

    public void addOp(String str, int i) {
        if (this.mAppOps.get(str) == null) {
            this.mAppOps.put(str, new ArraySet<>(3));
        }
        this.mAppOps.get(str).add(Integer.valueOf(i));
    }

    public void addStandardLayoutNotification(String str, String str2) {
        addNotification(this.mStandardLayoutNotifications, str, str2);
    }

    public boolean isDisclosureNeeded() {
        if (this.mRunning == null || System.currentTimeMillis() - this.mServiceStartTime < 5000) {
            return false;
        }
        for (String str : this.mRunning) {
            ArraySet<String> arraySet = this.mImportantNotifications.get(str);
            if (arraySet == null || arraySet.size() == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean removeImportantNotification(String str, String str2) {
        return removeNotification(this.mImportantNotifications, str, str2);
    }

    public boolean removeNotification(ArrayMap<String, ArraySet<String>> arrayMap, String str, String str2) {
        boolean remove;
        ArraySet<String> arraySet = arrayMap.get(str);
        if (arraySet == null) {
            remove = false;
        } else {
            remove = arraySet.remove(str2);
            if (arraySet.size() == 0) {
                arrayMap.remove(str);
            }
        }
        return remove;
    }

    public boolean removeNotification(String str, String str2) {
        return removeStandardLayoutNotification(str, str2) | removeImportantNotification(str, str2) | false;
    }

    public boolean removeOp(String str, int i) {
        boolean remove;
        ArraySet<Integer> arraySet = this.mAppOps.get(str);
        if (arraySet == null) {
            remove = false;
        } else {
            remove = arraySet.remove(Integer.valueOf(i));
            if (arraySet.size() == 0) {
                this.mAppOps.remove(str);
            }
        }
        return remove;
    }

    public boolean removeStandardLayoutNotification(String str, String str2) {
        return removeNotification(this.mStandardLayoutNotifications, str, str2);
    }

    public void setRunningServices(String[] strArr, long j) {
        this.mRunning = strArr != null ? (String[]) Arrays.copyOf(strArr, strArr.length) : null;
        this.mServiceStartTime = j;
    }

    public String toString() {
        return "UserServices{mRunning=" + Arrays.toString(this.mRunning) + ", mServiceStartTime=" + this.mServiceStartTime + ", mImportantNotifications=" + this.mImportantNotifications + ", mStandardLayoutNotifications=" + this.mStandardLayoutNotifications + '}';
    }
}