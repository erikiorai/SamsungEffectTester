package com.android.settingslib.core.instrumentation;

import android.view.View;
import com.android.internal.jank.InteractionJankMonitor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/* loaded from: mainsysui33.jar:com/android/settingslib/core/instrumentation/SettingsJankMonitor.class */
public final class SettingsJankMonitor {
    public static final SettingsJankMonitor INSTANCE = new SettingsJankMonitor();
    public static final InteractionJankMonitor jankMonitor = InteractionJankMonitor.getInstance();
    public static final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    public static final void detectToggleJank(String str, View view) {
        InteractionJankMonitor.Configuration.Builder withView = InteractionJankMonitor.Configuration.Builder.withView(57, view);
        if (str != null) {
            withView.setTag(str);
        }
        if (jankMonitor.begin(withView)) {
            scheduledExecutorService.schedule(new Runnable() { // from class: com.android.settingslib.core.instrumentation.SettingsJankMonitor$detectToggleJank$1
                @Override // java.lang.Runnable
                public final void run() {
                    InteractionJankMonitor interactionJankMonitor;
                    interactionJankMonitor = SettingsJankMonitor.jankMonitor;
                    interactionJankMonitor.end(57);
                }
            }, 300L, TimeUnit.MILLISECONDS);
        }
    }

    public static /* synthetic */ void getMONITORED_ANIMATION_DURATION_MS$annotations() {
    }
}