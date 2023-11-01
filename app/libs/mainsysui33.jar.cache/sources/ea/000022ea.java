package com.android.systemui.qs.tiles;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.os.Handler;
import android.os.Looper;
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
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/NfcTile.class */
public class NfcTile extends QSTileImpl<QSTile.BooleanState> {
    public NfcAdapter mAdapter;
    public BroadcastDispatcher mBroadcastDispatcher;
    public final QSTile.Icon mIcon;
    public boolean mListening;
    public BroadcastReceiver mNfcReceiver;

    public NfcTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, BroadcastDispatcher broadcastDispatcher) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mIcon = QSTileImpl.ResourceIcon.get(R$drawable.ic_qs_nfc);
        this.mNfcReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qs.tiles.NfcTile.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                NfcTile.this.refreshState();
            }
        };
        this.mBroadcastDispatcher = broadcastDispatcher;
    }

    public final NfcAdapter getAdapter() {
        if (this.mAdapter == null) {
            try {
                this.mAdapter = NfcAdapter.getNfcAdapter(this.mContext.getApplicationContext());
            } catch (UnsupportedOperationException e) {
                this.mAdapter = null;
            }
        }
        return this.mAdapter;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return new Intent("android.settings.NFC_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 800;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mContext.getString(R$string.quick_settings_nfc_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleClick(View view) {
        if (getAdapter() == null) {
            return;
        }
        if (getAdapter().isEnabled()) {
            getAdapter().disable();
        } else {
            getAdapter().enable();
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
            this.mBroadcastDispatcher.registerReceiver(this.mNfcReceiver, new IntentFilter("android.nfc.action.ADAPTER_STATE_CHANGED"));
        } else {
            this.mBroadcastDispatcher.unregisterReceiver(this.mNfcReceiver);
        }
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        int i = 1;
        booleanState.value = getAdapter() != null && getAdapter().isEnabled();
        if (getAdapter() == null) {
            i = 0;
        } else if (booleanState.value) {
            i = 2;
        }
        booleanState.state = i;
        booleanState.icon = this.mIcon;
        booleanState.label = this.mContext.getString(R$string.quick_settings_nfc_label);
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
        booleanState.contentDescription = booleanState.label;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUserSwitch(int i) {
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public boolean isAvailable() {
        if (this.mContext.getString(R$string.quick_settings_tiles_stock).contains("nfc")) {
            return this.mContext.getPackageManager().hasSystemFeature("android.hardware.nfc");
        }
        return false;
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.BooleanState newTileState() {
        return new QSTile.BooleanState();
    }
}