package com.android.systemui.plugins;

import com.android.systemui.plugins.annotations.ProvidesInterface;

@ProvidesInterface(action = "com.android.systemui.action.PLUGIN_CLOCK_PROVIDER", version = 1)
/* loaded from: mainsysui33.jar:com/android/systemui/plugins/ClockProviderPlugin.class */
public interface ClockProviderPlugin extends Plugin, ClockProvider {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_CLOCK_PROVIDER";
    public static final Companion Companion = Companion.$$INSTANCE;
    public static final int VERSION = 1;

    /* loaded from: mainsysui33.jar:com/android/systemui/plugins/ClockProviderPlugin$Companion.class */
    public static final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final String ACTION = "com.android.systemui.action.PLUGIN_CLOCK_PROVIDER";
        public static final int VERSION = 1;

        private Companion() {
        }
    }
}