package com.android.settingslib.mobile;

import android.content.res.Resources;

/* loaded from: mainsysui33.jar:com/android/settingslib/mobile/MobileIconCarrierIdOverrides.class */
public interface MobileIconCarrierIdOverrides {
    boolean carrierIdEntryExists(int i);

    int getOverrideFor(int i, String str, Resources resources);
}