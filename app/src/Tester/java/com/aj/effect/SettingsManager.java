package com.aj.effect;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;

public class SettingsManager {
    private static SharedPreferences preferences;

    public static boolean putInt(Context con, ContentResolver cr, String setting, int val) {
        if (preferences == null) {
            preferences = con.getSharedPreferences("settings", Context.MODE_PRIVATE);
        }
        preferences.edit().putInt(setting, val).apply();
        return true;
    }

    public static int getInt(Context con, ContentResolver cr, String setting, int def) {
        if (preferences == null) {
            preferences = con.getSharedPreferences("settings", Context.MODE_PRIVATE);
        }
        return preferences.getInt(setting, def);
    }
}
