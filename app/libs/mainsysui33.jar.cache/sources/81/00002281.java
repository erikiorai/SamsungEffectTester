package com.android.systemui.qs.tiles;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Looper;
import android.sysprop.TelephonyProperties;
import android.view.View;
import android.widget.Switch;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.SettingObserver;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.settings.GlobalSettings;
import dagger.Lazy;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/AirplaneModeTile.class */
public class AirplaneModeTile extends SecureQSTile<QSTile.BooleanState> {
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final Lazy<ConnectivityManager> mLazyConnectivityManager;
    public boolean mListening;
    public final BroadcastReceiver mReceiver;
    public final SettingObserver mSetting;

    public AirplaneModeTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, BroadcastDispatcher broadcastDispatcher, Lazy<ConnectivityManager> lazy, GlobalSettings globalSettings, UserTracker userTracker, KeyguardStateController keyguardStateController) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger, keyguardStateController);
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qs.tiles.AirplaneModeTile.2
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if ("android.intent.action.AIRPLANE_MODE".equals(intent.getAction())) {
                    AirplaneModeTile.this.refreshState();
                }
            }
        };
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mLazyConnectivityManager = lazy;
        this.mSetting = new SettingObserver(globalSettings, this.mHandler, "airplane_mode_on", userTracker.getUserId()) { // from class: com.android.systemui.qs.tiles.AirplaneModeTile.1
            @Override // com.android.systemui.qs.SettingObserver
            public void handleValueChanged(int i, boolean z) {
                AirplaneModeTile.this.handleRefreshState(Integer.valueOf(i));
            }
        };
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return new Intent("android.settings.AIRPLANE_MODE_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 112;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mContext.getString(R$string.airplane_mode);
    }

    @Override // com.android.systemui.qs.tiles.SecureQSTile
    public void handleClick(View view, boolean z) {
        if (checkKeyguard(view, z)) {
            return;
        }
        boolean z2 = ((QSTile.BooleanState) this.mState).value;
        MetricsLogger.action(this.mContext, getMetricsCategory(), !z2);
        if (z2 || !((Boolean) TelephonyProperties.in_ecm_mode().orElse(Boolean.FALSE)).booleanValue()) {
            setEnabled(!z2);
        } else {
            this.mActivityStarter.postStartActivityDismissingKeyguard(new Intent("android.telephony.action.SHOW_NOTICE_ECM_BLOCK_OTHERS"), 0);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleSetListening(boolean z) {
        super.handleSetListening(z);
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        if (z) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
            this.mBroadcastDispatcher.registerReceiver(this.mReceiver, intentFilter);
        } else {
            this.mBroadcastDispatcher.unregisterReceiver(this.mReceiver);
        }
        this.mSetting.setListening(z);
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        checkIfRestrictionEnforcedByAdminOnly(booleanState, "no_airplane_mode");
        boolean z = (obj instanceof Integer ? ((Integer) obj).intValue() : this.mSetting.getValue()) != 0;
        booleanState.value = z;
        booleanState.label = this.mContext.getString(R$string.airplane_mode);
        booleanState.icon = QSTileImpl.ResourceIcon.get(booleanState.value ? R$drawable.qs_airplane_icon_on : R$drawable.qs_airplane_icon_off);
        int i = 1;
        if (z) {
            i = 2;
        }
        booleanState.state = i;
        booleanState.contentDescription = booleanState.label;
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.BooleanState newTileState() {
        return new QSTile.BooleanState();
    }

    public final void setEnabled(boolean z) {
        ((ConnectivityManager) this.mLazyConnectivityManager.get()).setAirplaneMode(z);
    }
}