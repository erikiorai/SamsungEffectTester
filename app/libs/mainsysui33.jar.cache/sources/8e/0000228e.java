package com.android.systemui.qs.tiles;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Switch;
import com.android.internal.annotations.VisibleForTesting;
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
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.util.settings.SecureSettings;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/BatterySaverTile.class */
public class BatterySaverTile extends QSTileImpl<QSTile.BooleanState> implements BatteryController.BatteryStateChangeCallback {
    private final BatteryController mBatteryController;
    private boolean mCharging;
    private int mLevel;
    private boolean mPluggedIn;
    private boolean mPowerSave;
    @VisibleForTesting
    public final SettingObserver mSetting;

    public BatterySaverTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, BatteryController batteryController, SecureSettings secureSettings) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mBatteryController = batteryController;
        batteryController.observe(getLifecycle(), this);
        this.mSetting = new SettingObserver(secureSettings, this.mHandler, "low_power_warning_acknowledged", qSHost.getUserContext().getUserId()) { // from class: com.android.systemui.qs.tiles.BatterySaverTile.1
            @Override // com.android.systemui.qs.SettingObserver
            public void handleValueChanged(int i, boolean z) {
                BatterySaverTile.this.handleRefreshState(null);
            }
        };
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return new Intent("android.settings.BATTERY_SAVER_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 261;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mContext.getString(R$string.battery_detail_switch_title);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleClick(View view) {
        if (getState().state == 0) {
            return;
        }
        this.mBatteryController.setPowerSaveMode(!this.mPowerSave, view);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleDestroy() {
        super.handleDestroy();
        this.mSetting.setListening(false);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleSetListening(boolean z) {
        super.handleSetListening(z);
        this.mSetting.setListening(z);
        if (z) {
            return;
        }
        this.mBatteryController.clearLastPowerSaverStartView();
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        boolean z = true;
        booleanState.state = this.mPluggedIn ? 0 : this.mPowerSave ? 2 : 1;
        booleanState.icon = QSTileImpl.ResourceIcon.get(this.mPowerSave ? R$drawable.qs_battery_saver_icon_on : R$drawable.qs_battery_saver_icon_off);
        String string = this.mContext.getString(R$string.battery_detail_switch_title);
        booleanState.label = string;
        booleanState.secondaryLabel = "";
        booleanState.contentDescription = string;
        booleanState.value = this.mPowerSave;
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
        if (this.mSetting.getValue() != 0) {
            z = false;
        }
        booleanState.showRippleEffect = z;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUserSwitch(int i) {
        this.mSetting.setUserId(i);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.BooleanState newTileState() {
        return new QSTile.BooleanState();
    }

    public void onBatteryLevelChanged(int i, boolean z, boolean z2) {
        this.mLevel = i;
        this.mPluggedIn = z;
        this.mCharging = z2;
        refreshState(Integer.valueOf(i));
    }

    public void onPowerSaveChanged(boolean z) {
        this.mPowerSave = z;
        refreshState(null);
    }
}