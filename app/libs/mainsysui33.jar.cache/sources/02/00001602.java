package com.android.systemui.demomode.dagger;

import android.content.Context;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.util.settings.GlobalSettings;

/* loaded from: mainsysui33.jar:com/android/systemui/demomode/dagger/DemoModeModule.class */
public abstract class DemoModeModule {
    public static DemoModeController provideDemoModeController(Context context, DumpManager dumpManager, GlobalSettings globalSettings, BroadcastDispatcher broadcastDispatcher) {
        DemoModeController demoModeController = new DemoModeController(context, dumpManager, globalSettings, broadcastDispatcher);
        demoModeController.initialize();
        return demoModeController;
    }
}