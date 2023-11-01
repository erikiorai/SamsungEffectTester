package com.android.systemui.qs.tiles;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.SettingObserver;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.settings.GlobalSettings;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/HeadsUpTile.class */
public class HeadsUpTile extends QSTileImpl<QSTile.BooleanState> {
    public static final Intent NOTIFICATION_SETTINGS = new Intent("android.settings.NOTIFICATION_SETTINGS");
    public final QSTile.Icon mIcon;
    public final SettingObserver mSetting;

    public HeadsUpTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, GlobalSettings globalSettings, UserTracker userTracker) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mIcon = QSTileImpl.ResourceIcon.get(R$drawable.ic_qs_heads_up);
        this.mSetting = new SettingObserver(globalSettings, this.mHandler, "heads_up_notifications_enabled", userTracker.getUserId()) { // from class: com.android.systemui.qs.tiles.HeadsUpTile.1
            @Override // com.android.systemui.qs.SettingObserver
            public void handleValueChanged(int i, boolean z) {
                HeadsUpTile.this.handleRefreshState(Integer.valueOf(i));
            }
        };
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return NOTIFICATION_SETTINGS;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mContext.getString(R$string.quick_settings_heads_up_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleClick(View view) {
        setEnabled(!((QSTile.BooleanState) this.mState).value);
        refreshState();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleSetListening(boolean z) {
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        boolean z = (obj instanceof Integer ? ((Integer) obj).intValue() : this.mSetting.getValue()) != 0;
        booleanState.value = z;
        booleanState.label = this.mContext.getString(R$string.quick_settings_heads_up_label);
        booleanState.icon = this.mIcon;
        if (z) {
            booleanState.contentDescription = this.mContext.getString(R$string.accessibility_quick_settings_heads_up_on);
            booleanState.state = 2;
            return;
        }
        booleanState.contentDescription = this.mContext.getString(R$string.accessibility_quick_settings_heads_up_off);
        booleanState.state = 1;
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.BooleanState newTileState() {
        return new QSTile.BooleanState();
    }

    public final void setEnabled(boolean z) {
        Settings.Global.putInt(this.mContext.getContentResolver(), "heads_up_notifications_enabled", z ? 1 : 0);
    }
}