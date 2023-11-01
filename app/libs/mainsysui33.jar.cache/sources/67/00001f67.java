package com.android.systemui.plugins;

import android.content.res.Resources;
import java.util.Locale;
import java.util.TimeZone;

/* loaded from: mainsysui33.jar:com/android/systemui/plugins/ClockEvents.class */
public interface ClockEvents {

    /* loaded from: mainsysui33.jar:com/android/systemui/plugins/ClockEvents$DefaultImpls.class */
    public static final class DefaultImpls {
        public static void onColorPaletteChanged(ClockEvents clockEvents, Resources resources) {
        }

        public static void onLocaleChanged(ClockEvents clockEvents, Locale locale) {
        }

        public static void onTimeFormatChanged(ClockEvents clockEvents, boolean z) {
        }

        public static void onTimeTick(ClockEvents clockEvents) {
        }

        public static void onTimeZoneChanged(ClockEvents clockEvents, TimeZone timeZone) {
        }
    }

    void onColorPaletteChanged(Resources resources);

    void onLocaleChanged(Locale locale);

    void onTimeFormatChanged(boolean z);

    void onTimeTick();

    void onTimeZoneChanged(TimeZone timeZone);
}