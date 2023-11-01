package com.android.systemui.qs.dagger;

import android.content.Context;
import android.hardware.display.ColorDisplayManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.util.settings.GlobalSettings;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/dagger/QSFlagsModule.class */
public interface QSFlagsModule {
    static boolean isPMLiteEnabled(FeatureFlags featureFlags, GlobalSettings globalSettings) {
        boolean z = true;
        if (!featureFlags.isEnabled(Flags.POWER_MENU_LITE) || globalSettings.getInt("sysui_pm_lite", 1) == 0) {
            z = false;
        }
        return z;
    }

    static boolean isReduceBrightColorsAvailable(Context context) {
        return ColorDisplayManager.isReduceBrightColorsAvailable(context);
    }
}