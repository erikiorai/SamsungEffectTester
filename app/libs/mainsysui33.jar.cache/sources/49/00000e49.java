package com.android.settingslib.devicestate;

import android.database.ContentObserver;

/* loaded from: mainsysui33.jar:com/android/settingslib/devicestate/SecureSettings.class */
public interface SecureSettings {
    String getStringForUser(String str, int i);

    void putStringForUser(String str, String str2, int i);

    void registerContentObserver(String str, boolean z, ContentObserver contentObserver, int i);
}