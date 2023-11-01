package com.android.systemui.qs.tiles;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import com.android.internal.custom.hardware.LiveDisplayManager;
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

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/AntiFlickerTile.class */
public class AntiFlickerTile extends QSTileImpl<QSTile.BooleanState> {
    public static final Intent DISPLAY_SETTINGS = new Intent("android.settings.DISPLAY_SETTINGS");
    public boolean mAntiFlickerEnabled;
    public final QSTile.Icon mIcon;
    public final LiveDisplayManager mLiveDisplay;
    public final BroadcastReceiver mReceiver;
    public boolean mReceiverRegistered;

    public AntiFlickerTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mAntiFlickerEnabled = true;
        this.mIcon = QSTileImpl.ResourceIcon.get(R$drawable.ic_qs_anti_flicker);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qs.tiles.AntiFlickerTile.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                AntiFlickerTile.this.updateConfig();
                AntiFlickerTile.this.refreshState();
                AntiFlickerTile.this.unregisterReceiver();
            }
        };
        this.mReceiver = broadcastReceiver;
        this.mLiveDisplay = LiveDisplayManager.getInstance(this.mContext);
        if (updateConfig()) {
            return;
        }
        this.mContext.registerReceiver(broadcastReceiver, new IntentFilter("lineageos.intent.action.INITIALIZE_LIVEDISPLAY"));
        this.mReceiverRegistered = true;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return DISPLAY_SETTINGS;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 1999;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mContext.getString(R$string.quick_settings_anti_flicker);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleClick(View view) {
        setEnabled(!this.mLiveDisplay.isAntiFlickerEnabled());
        refreshState();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleDestroy() {
        super.handleDestroy();
        unregisterReceiver();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleSetListening(boolean z) {
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        try {
            booleanState.value = this.mLiveDisplay.isAntiFlickerEnabled();
        } catch (NullPointerException e) {
            booleanState.value = false;
        }
        booleanState.icon = this.mIcon;
        booleanState.contentDescription = this.mContext.getString(R$string.quick_settings_anti_flicker);
        booleanState.state = booleanState.value ? 2 : 1;
        booleanState.label = getTileLabel();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public boolean isAvailable() {
        return this.mAntiFlickerEnabled;
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.BooleanState newTileState() {
        return new QSTile.BooleanState();
    }

    public final void setEnabled(boolean z) {
        Settings.System.putInt(this.mContext.getContentResolver(), "display_anti_flicker", z ? 1 : 0);
    }

    public final void unregisterReceiver() {
        if (this.mReceiverRegistered) {
            this.mContext.unregisterReceiver(this.mReceiver);
            this.mReceiverRegistered = false;
        }
    }

    public final boolean updateConfig() {
        if (this.mLiveDisplay.getConfig() != null) {
            this.mAntiFlickerEnabled = this.mLiveDisplay.getConfig().hasFeature(19);
            if (isAvailable()) {
                return true;
            }
            this.mHost.removeTile(getTileSpec());
            return true;
        }
        return false;
    }
}