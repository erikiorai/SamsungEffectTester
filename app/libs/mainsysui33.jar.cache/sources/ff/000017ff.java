package com.android.systemui.flags;

import android.os.SystemProperties;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/SystemPropertiesHelper.class */
public class SystemPropertiesHelper {
    public final void erase(String str) {
        set(str, "");
    }

    public final String get(String str) {
        return SystemProperties.get(str);
    }

    public final boolean getBoolean(String str, boolean z) {
        return SystemProperties.getBoolean(str, z);
    }

    public final void set(String str, int i) {
        set(str, String.valueOf(i));
    }

    public final void set(String str, String str2) {
        SystemProperties.set(str, str2);
    }

    public final void setBoolean(String str, boolean z) {
        SystemProperties.set(str, z ? "1" : "0");
    }
}