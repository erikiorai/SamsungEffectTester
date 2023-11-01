package com.android.systemui.qs.tiles;

import android.app.UiModeManager;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Switch;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.LocationController;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/UiModeNightTile.class */
public class UiModeNightTile extends QSTileImpl<QSTile.BooleanState> implements ConfigurationController.ConfigurationListener, BatteryController.BatteryStateChangeCallback {
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
    public final BatteryController mBatteryController;
    public final LocationController mLocationController;
    public final UiModeManager mUiModeManager;

    public UiModeNightTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, ConfigurationController configurationController, BatteryController batteryController, LocationController locationController) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mBatteryController = batteryController;
        this.mUiModeManager = (UiModeManager) qSHost.getUserContext().getSystemService(UiModeManager.class);
        this.mLocationController = locationController;
        configurationController.observe(getLifecycle(), this);
        batteryController.observe(getLifecycle(), this);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return new Intent("android.settings.DARK_THEME_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 1706;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return getState().label;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleClick(View view) {
        if (getState().state == 0) {
            return;
        }
        boolean z = !((QSTile.BooleanState) this.mState).value;
        this.mUiModeManager.setNightModeActivated(z);
        refreshState(Boolean.valueOf(z));
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        int nightMode = this.mUiModeManager.getNightMode();
        boolean isPowerSave = this.mBatteryController.isPowerSave();
        boolean z = (this.mContext.getResources().getConfiguration().uiMode & 48) == 32;
        if (isPowerSave) {
            booleanState.secondaryLabel = this.mContext.getResources().getString(R$string.quick_settings_dark_mode_secondary_label_battery_saver);
        } else if (nightMode == 0 && this.mLocationController.isLocationEnabled()) {
            booleanState.secondaryLabel = this.mContext.getResources().getString(z ? R$string.quick_settings_dark_mode_secondary_label_until_sunrise : R$string.quick_settings_dark_mode_secondary_label_on_at_sunset);
        } else if (nightMode == 3) {
            int nightModeCustomType = this.mUiModeManager.getNightModeCustomType();
            if (nightModeCustomType == 0) {
                boolean is24HourFormat = DateFormat.is24HourFormat(this.mContext);
                LocalTime customNightModeEnd = z ? this.mUiModeManager.getCustomNightModeEnd() : this.mUiModeManager.getCustomNightModeStart();
                booleanState.secondaryLabel = this.mContext.getResources().getString(z ? R$string.quick_settings_dark_mode_secondary_label_until : R$string.quick_settings_dark_mode_secondary_label_on_at, is24HourFormat ? customNightModeEnd.toString() : formatter.format(customNightModeEnd));
            } else if (nightModeCustomType == 1) {
                booleanState.secondaryLabel = this.mContext.getResources().getString(z ? R$string.quick_settings_dark_mode_secondary_label_until_bedtime_ends : R$string.quick_settings_dark_mode_secondary_label_on_at_bedtime);
            } else {
                booleanState.secondaryLabel = null;
            }
        } else {
            booleanState.secondaryLabel = null;
        }
        booleanState.value = z;
        booleanState.label = this.mContext.getString(R$string.quick_settings_ui_mode_night_label);
        booleanState.contentDescription = TextUtils.isEmpty(booleanState.secondaryLabel) ? booleanState.label : TextUtils.concat(booleanState.label, ", ", booleanState.secondaryLabel);
        if (isPowerSave) {
            booleanState.state = 0;
        } else {
            int i = 1;
            if (booleanState.value) {
                i = 2;
            }
            booleanState.state = i;
        }
        booleanState.icon = QSTileImpl.ResourceIcon.get(booleanState.state == 2 ? R$drawable.qs_light_dark_theme_icon_on : R$drawable.qs_light_dark_theme_icon_off);
        booleanState.showRippleEffect = false;
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.BooleanState newTileState() {
        return new QSTile.BooleanState();
    }

    public void onPowerSaveChanged(boolean z) {
        refreshState();
    }

    public void onUiModeChanged() {
        refreshState();
    }
}