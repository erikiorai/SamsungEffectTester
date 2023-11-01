package com.android.systemui.qs.tiles;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import com.android.internal.logging.MetricsLogger;
import com.android.settingslib.wifi.WifiEnterpriseRestrictionUtils;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.statusbar.policy.DataSaverController;
import com.android.systemui.statusbar.policy.HotspotController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.PluralMessageFormaterKt;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/HotspotTile.class */
public class HotspotTile extends SecureQSTile<QSTile.BooleanState> {
    public final HotspotAndDataSaverCallbacks mCallbacks;
    public final DataSaverController mDataSaverController;
    public final HotspotController mHotspotController;
    public boolean mListening;

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/HotspotTile$CallbackInfo.class */
    public static final class CallbackInfo {
        public boolean isDataSaverEnabled;
        public boolean isHotspotEnabled;
        public int numConnectedDevices;

        public String toString() {
            return "CallbackInfo[isHotspotEnabled=" + this.isHotspotEnabled + ",numConnectedDevices=" + this.numConnectedDevices + ",isDataSaverEnabled=" + this.isDataSaverEnabled + ']';
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/HotspotTile$HotspotAndDataSaverCallbacks.class */
    public final class HotspotAndDataSaverCallbacks implements HotspotController.Callback, DataSaverController.Listener {
        public CallbackInfo mCallbackInfo;

        public HotspotAndDataSaverCallbacks() {
            this.mCallbackInfo = new CallbackInfo();
        }

        public void onDataSaverChanged(boolean z) {
            CallbackInfo callbackInfo = this.mCallbackInfo;
            callbackInfo.isDataSaverEnabled = z;
            HotspotTile.this.refreshState(callbackInfo);
        }

        public void onHotspotAvailabilityChanged(boolean z) {
            if (z) {
                return;
            }
            Log.d(HotspotTile.this.TAG, "Tile removed. Hotspot no longer available");
            HotspotTile.this.mHost.removeTile(HotspotTile.this.getTileSpec());
        }

        public void onHotspotChanged(boolean z, int i) {
            CallbackInfo callbackInfo = this.mCallbackInfo;
            callbackInfo.isHotspotEnabled = z;
            callbackInfo.numConnectedDevices = i;
            HotspotTile.this.refreshState(callbackInfo);
        }
    }

    public HotspotTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, HotspotController hotspotController, DataSaverController dataSaverController, KeyguardStateController keyguardStateController) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger, keyguardStateController);
        HotspotAndDataSaverCallbacks hotspotAndDataSaverCallbacks = new HotspotAndDataSaverCallbacks();
        this.mCallbacks = hotspotAndDataSaverCallbacks;
        this.mHotspotController = hotspotController;
        this.mDataSaverController = dataSaverController;
        hotspotController.observe(this, hotspotAndDataSaverCallbacks);
        dataSaverController.observe(this, hotspotAndDataSaverCallbacks);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return new Intent("com.android.settings.WIFI_TETHER_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 120;
    }

    public final String getSecondaryLabel(boolean z, boolean z2, boolean z3, int i, boolean z4) {
        if (z4) {
            if (z2) {
                return this.mContext.getString(R$string.quick_settings_hotspot_secondary_label_transient);
            }
            if (z3) {
                return this.mContext.getString(R$string.quick_settings_hotspot_secondary_label_data_saver_enabled);
            }
            if (i <= 0 || !z) {
                return null;
            }
            return PluralMessageFormaterKt.icuMessageFormat(this.mContext.getResources(), R$string.quick_settings_hotspot_secondary_label_num_devices, i);
        }
        return this.mContext.getString(R$string.wifitrackerlib_admin_restricted_network);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mContext.getString(R$string.quick_settings_hotspot_label);
    }

    @Override // com.android.systemui.qs.tiles.SecureQSTile
    public void handleClick(View view, boolean z) {
        if (checkKeyguard(view, z)) {
            return;
        }
        boolean z2 = ((QSTile.BooleanState) this.mState).value;
        if (z2 || !this.mDataSaverController.isDataSaverEnabled()) {
            refreshState(z2 ? null : QSTileImpl.ARG_SHOW_TRANSIENT_ENABLING);
            this.mHotspotController.setHotspotEnabled(!z2);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleDestroy() {
        super.handleDestroy();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleSetListening(boolean z) {
        super.handleSetListening(z);
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        if (z) {
            refreshState();
        }
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        int numConnectedDevices;
        boolean isDataSaverEnabled;
        boolean z = obj == QSTileImpl.ARG_SHOW_TRANSIENT_ENABLING;
        boolean z2 = z || this.mHotspotController.isHotspotTransient();
        checkIfRestrictionEnforcedByAdminOnly(booleanState, "no_config_tethering");
        if (obj instanceof CallbackInfo) {
            CallbackInfo callbackInfo = (CallbackInfo) obj;
            booleanState.value = z || callbackInfo.isHotspotEnabled;
            numConnectedDevices = callbackInfo.numConnectedDevices;
            isDataSaverEnabled = callbackInfo.isDataSaverEnabled;
        } else {
            booleanState.value = z || this.mHotspotController.isHotspotEnabled();
            numConnectedDevices = this.mHotspotController.getNumConnectedDevices();
            isDataSaverEnabled = this.mDataSaverController.isDataSaverEnabled();
        }
        booleanState.label = this.mContext.getString(R$string.quick_settings_hotspot_label);
        booleanState.isTransient = z2;
        if (z2) {
            booleanState.icon = QSTileImpl.ResourceIcon.get(R$drawable.qs_hotspot_icon_search);
        } else {
            booleanState.icon = QSTileImpl.ResourceIcon.get(booleanState.value ? R$drawable.qs_hotspot_icon_on : R$drawable.qs_hotspot_icon_off);
        }
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
        booleanState.contentDescription = booleanState.label;
        boolean isWifiTetheringAllowed = WifiEnterpriseRestrictionUtils.isWifiTetheringAllowed(this.mHost.getUserContext());
        boolean z3 = isDataSaverEnabled || !isWifiTetheringAllowed;
        boolean z4 = booleanState.value || booleanState.isTransient;
        if (z3) {
            booleanState.state = 0;
        } else {
            int i = 1;
            if (z4) {
                i = 2;
            }
            booleanState.state = i;
        }
        String secondaryLabel = getSecondaryLabel(z4, z2, isDataSaverEnabled, numConnectedDevices, isWifiTetheringAllowed);
        booleanState.secondaryLabel = secondaryLabel;
        booleanState.stateDescription = secondaryLabel;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public boolean isAvailable() {
        return this.mHotspotController.isHotspotSupported();
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.BooleanState newTileState() {
        return new QSTile.BooleanState();
    }
}