package com.android.keyguard.dagger;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.view.LayoutInflater;
import com.android.systemui.R$string;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.shared.clocks.ClockRegistry;
import com.android.systemui.shared.clocks.DefaultClockProvider;

/* loaded from: mainsysui33.jar:com/android/keyguard/dagger/ClockRegistryModule.class */
public abstract class ClockRegistryModule {
    public static ClockRegistry getClockRegistry(Context context, PluginManager pluginManager, Handler handler, FeatureFlags featureFlags, Resources resources, LayoutInflater layoutInflater) {
        return new ClockRegistry(context, pluginManager, handler, featureFlags.isEnabled(Flags.LOCKSCREEN_CUSTOM_CLOCKS), -1, new DefaultClockProvider(context, layoutInflater, resources), context.getString(R$string.lockscreen_clock_id_fallback));
    }
}