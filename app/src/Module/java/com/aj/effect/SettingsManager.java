package com.aj.effect;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;

public class SettingsManager {

    public static boolean putInt(Context context, ContentResolver cr, String setting, int val) {
        return Settings.System.putInt(cr, setting, val);
    }

    public static int getInt(Context context, ContentResolver cr, String setting, int def) {
        return Settings.System.getInt(cr, setting, def);
    }
}
