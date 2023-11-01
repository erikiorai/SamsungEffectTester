package com.android.systemui.qs.tiles.dialog;

import android.content.Context;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.notification.ZenModeDialogMetricsLogger;
import com.android.systemui.qs.QSDndEvent;
import com.android.systemui.qs.QSEvents;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/dialog/QSZenModeDialogMetricsLogger.class */
public class QSZenModeDialogMetricsLogger extends ZenModeDialogMetricsLogger {
    public final UiEventLogger mUiEventLogger;

    public QSZenModeDialogMetricsLogger(Context context) {
        super(context);
        this.mUiEventLogger = QSEvents.INSTANCE.getQsUiEventsLogger();
    }

    @Override // com.android.settingslib.notification.ZenModeDialogMetricsLogger
    public void logOnClickTimeButton(boolean z) {
        super.logOnClickTimeButton(z);
        this.mUiEventLogger.log(z ? QSDndEvent.QS_DND_TIME_UP : QSDndEvent.QS_DND_TIME_DOWN);
    }

    @Override // com.android.settingslib.notification.ZenModeDialogMetricsLogger
    public void logOnConditionSelected() {
        super.logOnConditionSelected();
        this.mUiEventLogger.log(QSDndEvent.QS_DND_CONDITION_SELECT);
    }

    @Override // com.android.settingslib.notification.ZenModeDialogMetricsLogger
    public void logOnEnableZenModeForever() {
        super.logOnEnableZenModeForever();
        this.mUiEventLogger.log(QSDndEvent.QS_DND_DIALOG_ENABLE_FOREVER);
    }

    @Override // com.android.settingslib.notification.ZenModeDialogMetricsLogger
    public void logOnEnableZenModeUntilAlarm() {
        super.logOnEnableZenModeUntilAlarm();
        this.mUiEventLogger.log(QSDndEvent.QS_DND_DIALOG_ENABLE_UNTIL_ALARM);
    }

    @Override // com.android.settingslib.notification.ZenModeDialogMetricsLogger
    public void logOnEnableZenModeUntilCountdown() {
        super.logOnEnableZenModeUntilCountdown();
        this.mUiEventLogger.log(QSDndEvent.QS_DND_DIALOG_ENABLE_UNTIL_COUNTDOWN);
    }
}