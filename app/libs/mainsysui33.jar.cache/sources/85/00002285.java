package com.android.systemui.qs.tiles;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.NextAlarmController;
import java.util.Locale;
import kotlin.Unit;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/AlarmTile.class */
public final class AlarmTile extends QSTileImpl<QSTile.State> {
    public final NextAlarmController.NextAlarmChangeCallback callback;
    public final Intent defaultIntent;
    public final QSTile.Icon icon;
    public AlarmManager.AlarmClockInfo lastAlarmInfo;
    public final UserTracker userTracker;

    public AlarmTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, UserTracker userTracker, NextAlarmController nextAlarmController) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.userTracker = userTracker;
        this.icon = QSTileImpl.ResourceIcon.get(R$drawable.ic_alarm);
        this.defaultIntent = new Intent("android.intent.action.SHOW_ALARMS");
        NextAlarmController.NextAlarmChangeCallback nextAlarmChangeCallback = new NextAlarmController.NextAlarmChangeCallback() { // from class: com.android.systemui.qs.tiles.AlarmTile$callback$1
            public final void onNextAlarmChanged(AlarmManager.AlarmClockInfo alarmClockInfo) {
                AlarmTile.access$setLastAlarmInfo$p(AlarmTile.this, alarmClockInfo);
                AlarmTile.this.refreshState();
            }
        };
        this.callback = nextAlarmChangeCallback;
        nextAlarmController.observe(this, nextAlarmChangeCallback);
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.AlarmTile$callback$1.onNextAlarmChanged(android.app.AlarmManager$AlarmClockInfo):void] */
    public static final /* synthetic */ void access$setLastAlarmInfo$p(AlarmTile alarmTile, AlarmManager.AlarmClockInfo alarmClockInfo) {
        alarmTile.lastAlarmInfo = alarmClockInfo;
    }

    public static /* synthetic */ void getDefaultIntent$frameworks__base__packages__SystemUI__android_common__SystemUI_core$annotations() {
    }

    public final String formatNextAlarm(AlarmManager.AlarmClockInfo alarmClockInfo) {
        return DateFormat.format(DateFormat.getBestDateTimePattern(Locale.getDefault(), use24HourFormat() ? "EHm" : "Ehma"), alarmClockInfo.getTriggerTime()).toString();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mContext.getString(R$string.status_bar_alarm);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleClick(View view) {
        PendingIntent pendingIntent = null;
        ActivityLaunchAnimator.Controller fromView = view != null ? ActivityLaunchAnimator.Controller.Companion.fromView(view, 32) : null;
        AlarmManager.AlarmClockInfo alarmClockInfo = this.lastAlarmInfo;
        if (alarmClockInfo != null) {
            pendingIntent = alarmClockInfo.getShowIntent();
        }
        if (pendingIntent != null) {
            this.mActivityStarter.postStartActivityDismissingKeyguard(pendingIntent, fromView);
        } else {
            this.mActivityStarter.postStartActivityDismissingKeyguard(this.defaultIntent, 0, fromView);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.State state, Object obj) {
        Unit unit;
        state.icon = this.icon;
        state.label = getTileLabel();
        AlarmManager.AlarmClockInfo alarmClockInfo = this.lastAlarmInfo;
        if (alarmClockInfo != null) {
            state.secondaryLabel = formatNextAlarm(alarmClockInfo);
            state.state = 2;
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            state.secondaryLabel = this.mContext.getString(R$string.qs_alarm_tile_no_alarm);
            state.state = 1;
        }
        state.contentDescription = TextUtils.concat(state.label, ", ", state.secondaryLabel);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.State newTileState() {
        QSTile.State state = new QSTile.State();
        state.handlesLongClick = false;
        return state;
    }

    public final boolean use24HourFormat() {
        return DateFormat.is24HourFormat(this.mContext, this.userTracker.getUserId());
    }
}