package com.android.dream.lowlight.dagger;

import android.content.ComponentName;
import android.content.Context;
import com.android.dream.lowlight.R$string;

/* loaded from: mainsysui33.jar:com/android/dream/lowlight/dagger/LowLightDreamModule.class */
public interface LowLightDreamModule {
    static ComponentName providesLowLightDreamComponent(Context context) {
        String string = context.getResources().getString(R$string.config_lowLightDreamComponent);
        return string.isEmpty() ? null : ComponentName.unflattenFromString(string);
    }
}