package com.android.systemui.qs.tiles;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.TetheringManager;
import android.os.Handler;
import android.os.Looper;
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

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/UsbTetherTile.class */
public class UsbTetherTile extends QSTileImpl<QSTile.BooleanState> {
    public static final Intent TETHER_SETTINGS = new Intent().setComponent(new ComponentName("com.android.settings", "com.android.settings.TetherSettings"));
    public final QSTile.Icon mIcon;
    public boolean mListening;
    public final BroadcastReceiver mReceiver;
    public final TetheringManager mTetheringManager;
    public boolean mUsbConnected;
    public boolean mUsbTetherEnabled;

    public UsbTetherTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mIcon = QSTileImpl.ResourceIcon.get(R$drawable.ic_qs_usb_tether);
        this.mUsbConnected = false;
        this.mUsbTetherEnabled = false;
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qs.tiles.UsbTetherTile.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                UsbTetherTile.this.mUsbConnected = intent.getBooleanExtra("connected", false);
                if (UsbTetherTile.this.mUsbConnected && UsbTetherTile.this.mTetheringManager.isTetheringSupported()) {
                    UsbTetherTile.this.mUsbTetherEnabled = intent.getBooleanExtra("rndis", false);
                } else {
                    UsbTetherTile.this.mUsbTetherEnabled = false;
                }
                UsbTetherTile.this.refreshState();
            }
        };
        this.mTetheringManager = (TetheringManager) this.mContext.getSystemService(TetheringManager.class);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return new Intent(TETHER_SETTINGS);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mContext.getString(R$string.quick_settings_usb_tether_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleClick(View view) {
        if (this.mUsbConnected) {
            this.mTetheringManager.setUsbTethering(!this.mUsbTetherEnabled);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleSetListening(boolean z) {
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        if (!z) {
            this.mContext.unregisterReceiver(this.mReceiver);
            return;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.hardware.usb.action.USB_STATE");
        this.mContext.registerReceiver(this.mReceiver, intentFilter);
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        booleanState.value = this.mUsbTetherEnabled;
        booleanState.label = this.mContext.getString(R$string.quick_settings_usb_tether_label);
        booleanState.icon = this.mIcon;
        booleanState.state = !this.mUsbConnected ? 0 : this.mUsbTetherEnabled ? 2 : 1;
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.BooleanState newTileState() {
        return new QSTile.BooleanState();
    }
}