package com.android.systemui.doze;

import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.KeyValueListParser;
import android.util.Log;
import com.android.systemui.R$array;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/AlwaysOnDisplayPolicy.class */
public class AlwaysOnDisplayPolicy {
    public int defaultDozeBrightness;
    public int dimBrightness;
    public int[] dimmingScrimArray;
    public final Context mContext;
    public final KeyValueListParser mParser;
    public SettingsObserver mSettingsObserver;
    public long proxCooldownPeriodMs;
    public long proxCooldownTriggerMs;
    public long proxScreenOffDelayMs;
    public int[] screenBrightnessArray;
    public long wallpaperFadeOutDuration;
    public long wallpaperVisibilityDuration;

    /* loaded from: mainsysui33.jar:com/android/systemui/doze/AlwaysOnDisplayPolicy$SettingsObserver.class */
    public final class SettingsObserver extends ContentObserver {
        public final Uri ALWAYS_ON_DISPLAY_CONSTANTS_URI;

        public SettingsObserver(Handler handler) {
            super(handler);
            this.ALWAYS_ON_DISPLAY_CONSTANTS_URI = Settings.Global.getUriFor("always_on_display_constants");
        }

        public void observe() {
            AlwaysOnDisplayPolicy.this.mContext.getContentResolver().registerContentObserver(this.ALWAYS_ON_DISPLAY_CONSTANTS_URI, false, this, -1);
            update(null);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            update(uri);
        }

        public void update(Uri uri) {
            if (uri == null || this.ALWAYS_ON_DISPLAY_CONSTANTS_URI.equals(uri)) {
                Resources resources = AlwaysOnDisplayPolicy.this.mContext.getResources();
                try {
                    AlwaysOnDisplayPolicy.this.mParser.setString(Settings.Global.getString(AlwaysOnDisplayPolicy.this.mContext.getContentResolver(), "always_on_display_constants"));
                } catch (IllegalArgumentException e) {
                    Log.e("AlwaysOnDisplayPolicy", "Bad AOD constants");
                }
                AlwaysOnDisplayPolicy alwaysOnDisplayPolicy = AlwaysOnDisplayPolicy.this;
                alwaysOnDisplayPolicy.proxScreenOffDelayMs = alwaysOnDisplayPolicy.mParser.getLong("prox_screen_off_delay", 10000L);
                AlwaysOnDisplayPolicy alwaysOnDisplayPolicy2 = AlwaysOnDisplayPolicy.this;
                alwaysOnDisplayPolicy2.proxCooldownTriggerMs = alwaysOnDisplayPolicy2.mParser.getLong("prox_cooldown_trigger", 2000L);
                AlwaysOnDisplayPolicy alwaysOnDisplayPolicy3 = AlwaysOnDisplayPolicy.this;
                alwaysOnDisplayPolicy3.proxCooldownPeriodMs = alwaysOnDisplayPolicy3.mParser.getLong("prox_cooldown_period", 5000L);
                AlwaysOnDisplayPolicy alwaysOnDisplayPolicy4 = AlwaysOnDisplayPolicy.this;
                alwaysOnDisplayPolicy4.wallpaperFadeOutDuration = alwaysOnDisplayPolicy4.mParser.getLong("wallpaper_fade_out_duration", 400L);
                AlwaysOnDisplayPolicy alwaysOnDisplayPolicy5 = AlwaysOnDisplayPolicy.this;
                alwaysOnDisplayPolicy5.wallpaperVisibilityDuration = alwaysOnDisplayPolicy5.mParser.getLong("wallpaper_visibility_timeout", 60000L);
                AlwaysOnDisplayPolicy.this.defaultDozeBrightness = resources.getInteger(17694952);
                AlwaysOnDisplayPolicy.this.dimBrightness = resources.getInteger(17694951);
                AlwaysOnDisplayPolicy alwaysOnDisplayPolicy6 = AlwaysOnDisplayPolicy.this;
                alwaysOnDisplayPolicy6.screenBrightnessArray = alwaysOnDisplayPolicy6.mParser.getIntArray("screen_brightness_array", resources.getIntArray(R$array.config_doze_brightness_sensor_to_brightness));
                AlwaysOnDisplayPolicy alwaysOnDisplayPolicy7 = AlwaysOnDisplayPolicy.this;
                alwaysOnDisplayPolicy7.dimmingScrimArray = alwaysOnDisplayPolicy7.mParser.getIntArray("dimming_scrim_array", resources.getIntArray(R$array.config_doze_brightness_sensor_to_scrim_opacity));
            }
        }
    }

    public AlwaysOnDisplayPolicy(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        this.mParser = new KeyValueListParser(',');
        SettingsObserver settingsObserver = new SettingsObserver(applicationContext.getMainThreadHandler());
        this.mSettingsObserver = settingsObserver;
        settingsObserver.observe();
    }
}