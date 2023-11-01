package com.android.settingslib.notification;

import android.content.Context;
import com.android.internal.logging.MetricsLogger;

/* loaded from: mainsysui33.jar:com/android/settingslib/notification/ZenModeDialogMetricsLogger.class */
public class ZenModeDialogMetricsLogger {
    public final Context mContext;

    public ZenModeDialogMetricsLogger(Context context) {
        this.mContext = context;
    }

    public void logOnClickTimeButton(boolean z) {
        MetricsLogger.action(this.mContext, 163, z);
    }

    public void logOnConditionSelected() {
        MetricsLogger.action(this.mContext, 164);
    }

    public void logOnEnableZenModeForever() {
        MetricsLogger.action(this.mContext, 1259);
    }

    public void logOnEnableZenModeUntilAlarm() {
        MetricsLogger.action(this.mContext, 1261);
    }

    public void logOnEnableZenModeUntilCountdown() {
        MetricsLogger.action(this.mContext, 1260);
    }
}