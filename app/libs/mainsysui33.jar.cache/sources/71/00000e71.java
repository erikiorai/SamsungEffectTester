package com.android.settingslib.inputmethod;

import android.content.Context;
import androidx.preference.SwitchPreference;

/* loaded from: mainsysui33.jar:com/android/settingslib/inputmethod/SwitchWithNoTextPreference.class */
public class SwitchWithNoTextPreference extends SwitchPreference {
    public SwitchWithNoTextPreference(Context context) {
        super(context);
        setSwitchTextOn("");
        setSwitchTextOff("");
    }
}