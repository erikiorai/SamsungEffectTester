package com.android.systemui.qs.tiles;

import android.bluetooth.BluetoothClass;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Switch;
import com.android.internal.logging.MetricsLogger;
import com.android.settingslib.Utils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.statusbar.policy.BluetoothController;
import com.android.systemui.util.PluralMessageFormaterKt;
import java.util.List;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/BluetoothTile.class */
public class BluetoothTile extends QSTileImpl<QSTile.BooleanState> {
    public static final Intent BLUETOOTH_SETTINGS = new Intent("android.settings.BLUETOOTH_SETTINGS");
    public final BluetoothController.Callback mCallback;
    public final BluetoothController mController;

    public BluetoothTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, BluetoothController bluetoothController) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        BluetoothController.Callback callback = new BluetoothController.Callback() { // from class: com.android.systemui.qs.tiles.BluetoothTile.1
            public void onBluetoothDevicesChanged() {
                BluetoothTile.this.refreshState();
            }

            public void onBluetoothStateChange(boolean z) {
                BluetoothTile.this.refreshState();
            }
        };
        this.mCallback = callback;
        this.mController = bluetoothController;
        bluetoothController.observe(getLifecycle(), callback);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return new Intent("android.settings.BLUETOOTH_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 113;
    }

    public final String getSecondaryLabel(boolean z, boolean z2, boolean z3, boolean z4) {
        if (z2) {
            return this.mContext.getString(R$string.quick_settings_connecting);
        }
        if (z4) {
            return this.mContext.getString(R$string.quick_settings_bluetooth_secondary_label_transient);
        }
        List connectedDevices = this.mController.getConnectedDevices();
        if (z && z3 && !connectedDevices.isEmpty()) {
            if (connectedDevices.size() > 1) {
                return PluralMessageFormaterKt.icuMessageFormat(this.mContext.getResources(), R$string.quick_settings_hotspot_secondary_label_num_devices, connectedDevices.size());
            }
            CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) connectedDevices.get(0);
            int batteryLevel = cachedBluetoothDevice.getBatteryLevel();
            if (batteryLevel > -1) {
                return this.mContext.getString(R$string.quick_settings_bluetooth_secondary_label_battery_level, Utils.formatPercentage(batteryLevel));
            }
            BluetoothClass btClass = cachedBluetoothDevice.getBtClass();
            if (btClass != null) {
                if (cachedBluetoothDevice.isHearingAidDevice()) {
                    return this.mContext.getString(R$string.quick_settings_bluetooth_secondary_label_hearing_aids);
                }
                if (btClass.doesClassMatch(1)) {
                    return this.mContext.getString(R$string.quick_settings_bluetooth_secondary_label_audio);
                }
                if (btClass.doesClassMatch(0)) {
                    return this.mContext.getString(R$string.quick_settings_bluetooth_secondary_label_headset);
                }
                if (btClass.doesClassMatch(3)) {
                    return this.mContext.getString(R$string.quick_settings_bluetooth_secondary_label_input);
                }
                return null;
            }
            return null;
        }
        return null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mContext.getString(R$string.quick_settings_bluetooth_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleClick(View view) {
        boolean z = ((QSTile.BooleanState) this.mState).value;
        refreshState(z ? null : QSTileImpl.ARG_SHOW_TRANSIENT_ENABLING);
        this.mController.setBluetoothEnabled(!z);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleSecondaryClick(View view) {
        if (!this.mController.canConfigBluetooth()) {
            this.mActivityStarter.postStartActivityDismissingKeyguard(new Intent("android.settings.BLUETOOTH_SETTINGS"), 0);
        } else if (((QSTile.BooleanState) this.mState).value) {
        } else {
            this.mController.setBluetoothEnabled(true);
        }
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        checkIfRestrictionEnforcedByAdminOnly(booleanState, "no_bluetooth");
        boolean z = obj == QSTileImpl.ARG_SHOW_TRANSIENT_ENABLING;
        boolean z2 = z || this.mController.isBluetoothEnabled();
        boolean isBluetoothConnected = this.mController.isBluetoothConnected();
        boolean isBluetoothConnecting = this.mController.isBluetoothConnecting();
        booleanState.isTransient = z || isBluetoothConnecting || this.mController.getBluetoothState() == 11;
        booleanState.dualTarget = true;
        booleanState.value = z2;
        if (booleanState.slash == null) {
            booleanState.slash = new QSTile.SlashState();
        }
        booleanState.slash.isSlashed = !z2;
        booleanState.label = this.mContext.getString(R$string.quick_settings_bluetooth_label);
        booleanState.secondaryLabel = TextUtils.emptyIfNull(getSecondaryLabel(z2, isBluetoothConnecting, isBluetoothConnected, booleanState.isTransient));
        booleanState.contentDescription = this.mContext.getString(R$string.accessibility_quick_settings_bluetooth);
        booleanState.stateDescription = "";
        if (z2) {
            if (isBluetoothConnected) {
                booleanState.icon = QSTileImpl.ResourceIcon.get(R$drawable.qs_bluetooth_icon_on);
                if (!TextUtils.isEmpty(this.mController.getConnectedDeviceName())) {
                    booleanState.label = this.mController.getConnectedDeviceName();
                }
                booleanState.stateDescription = this.mContext.getString(R$string.accessibility_bluetooth_name, booleanState.label) + ", " + ((Object) booleanState.secondaryLabel);
            } else if (booleanState.isTransient) {
                booleanState.icon = QSTileImpl.ResourceIcon.get(R$drawable.qs_bluetooth_icon_search);
                booleanState.stateDescription = booleanState.secondaryLabel;
            } else {
                booleanState.icon = QSTileImpl.ResourceIcon.get(R$drawable.qs_bluetooth_icon_off);
                booleanState.stateDescription = this.mContext.getString(R$string.accessibility_not_connected);
            }
            booleanState.state = 2;
        } else {
            booleanState.icon = QSTileImpl.ResourceIcon.get(R$drawable.qs_bluetooth_icon_off);
            booleanState.state = 1;
        }
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public boolean isAvailable() {
        return this.mController.isBluetoothSupported();
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.BooleanState newTileState() {
        return new QSTile.BooleanState();
    }
}