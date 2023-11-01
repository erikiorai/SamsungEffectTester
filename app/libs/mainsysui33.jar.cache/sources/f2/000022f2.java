package com.android.systemui.qs.tiles;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.view.View;
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
import java.util.NoSuchElementException;
import vendor.lineage.powershare.V1_0.IPowerShare;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/PowerShareTile.class */
public class PowerShareTile extends QSTileImpl<QSTile.BooleanState> implements BatteryController.BatteryStateChangeCallback {
    public BatteryController mBatteryController;
    public Notification mNotification;
    public NotificationManager mNotificationManager;
    public IPowerShare mPowerShare;

    public PowerShareTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, BatteryController batteryController) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        IPowerShare powerShare = getPowerShare();
        this.mPowerShare = powerShare;
        if (powerShare == null) {
            return;
        }
        this.mBatteryController = batteryController;
        this.mNotificationManager = (NotificationManager) this.mContext.getSystemService(NotificationManager.class);
        this.mNotificationManager.createNotificationChannel(new NotificationChannel("powershare", this.mContext.getString(R$string.quick_settings_powershare_label), 3));
        Notification.Builder builder = new Notification.Builder(this.mContext, "powershare");
        builder.setContentTitle(this.mContext.getString(R$string.quick_settings_powershare_enabled_label));
        builder.setSmallIcon(R$drawable.ic_qs_powershare);
        builder.setOnlyAlertOnce(true);
        Notification build = builder.build();
        this.mNotification = build;
        build.flags |= 34;
        build.visibility = 1;
        batteryController.addCallback(this);
    }

    public final int getBatteryLevel() {
        return ((BatteryManager) this.mContext.getSystemService(BatteryManager.class)).getIntProperty(4);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return null;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 1999;
    }

    public final int getMinBatteryLevel() {
        try {
            return this.mPowerShare.getMinBattery();
        } catch (RemoteException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public final IPowerShare getPowerShare() {
        synchronized (this) {
            try {
                return IPowerShare.getService();
            } catch (RemoteException e) {
                e.printStackTrace();
                return null;
            } catch (NoSuchElementException e2) {
                return null;
            }
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mBatteryController.isPowerSave() ? this.mContext.getString(R$string.quick_settings_powershare_off_powersave_label) : getBatteryLevel() < getMinBatteryLevel() ? this.mContext.getString(R$string.quick_settings_powershare_off_low_battery_label) : this.mContext.getString(R$string.quick_settings_powershare_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleClick(View view) {
        try {
            boolean isEnabled = this.mPowerShare.isEnabled();
            if (this.mPowerShare.setEnabled(!isEnabled) != isEnabled) {
                refreshState();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleSetListening(boolean z) {
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        if (isAvailable()) {
            if (booleanState.slash == null) {
                booleanState.slash = new QSTile.SlashState();
            }
            booleanState.icon = QSTileImpl.ResourceIcon.get(R$drawable.ic_qs_powershare);
            try {
                booleanState.value = this.mPowerShare.isEnabled();
            } catch (RemoteException e) {
                booleanState.value = false;
                e.printStackTrace();
            }
            booleanState.slash.isSlashed = booleanState.value;
            booleanState.label = this.mContext.getString(R$string.quick_settings_powershare_label);
            if (this.mBatteryController.isPowerSave() || getBatteryLevel() < getMinBatteryLevel()) {
                booleanState.state = 0;
            } else if (booleanState.value) {
                booleanState.state = 2;
            } else {
                booleanState.state = 1;
            }
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public boolean isAvailable() {
        return this.mPowerShare != null;
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.BooleanState newTileState() {
        QSTile.BooleanState booleanState = new QSTile.BooleanState();
        booleanState.handlesLongClick = false;
        return booleanState;
    }

    public void onPowerSaveChanged(boolean z) {
        refreshState();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public void refreshState() {
        updatePowerShareState();
        super.refreshState();
    }

    public final void updatePowerShareState() {
        if (isAvailable()) {
            if (this.mBatteryController.isPowerSave()) {
                try {
                    this.mPowerShare.setEnabled(false);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            try {
                if (this.mPowerShare.isEnabled()) {
                    this.mNotificationManager.notify(273298, this.mNotification);
                } else {
                    this.mNotificationManager.cancel(273298);
                }
            } catch (RemoteException e2) {
                e2.printStackTrace();
            }
        }
    }
}