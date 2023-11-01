package com.android.systemui.qs.tiles;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SyncStatusObserver;
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

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/SyncTile.class */
public class SyncTile extends QSTileImpl<QSTile.BooleanState> {
    public final QSTile.Icon mIcon;
    public boolean mListening;
    public SyncStatusObserver mSyncObserver;
    public Object mSyncObserverHandle;

    public SyncTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mIcon = QSTileImpl.ResourceIcon.get(R$drawable.ic_qs_sync);
        this.mSyncObserverHandle = null;
        this.mSyncObserver = new SyncStatusObserver() { // from class: com.android.systemui.qs.tiles.SyncTile.1
            @Override // android.content.SyncStatusObserver
            public void onStatusChanged(int i) {
                SyncTile.this.mHandler.post(new Runnable() { // from class: com.android.systemui.qs.tiles.SyncTile.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        SyncTile.this.refreshState();
                    }
                });
            }
        };
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        Intent intent = new Intent("android.settings.SYNC_SETTINGS");
        intent.addCategory("android.intent.category.DEFAULT");
        return intent;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mContext.getString(R$string.quick_settings_sync_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleClick(View view) {
        ContentResolver.setMasterSyncAutomatically(!((QSTile.BooleanState) this.mState).value);
        refreshState();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleSetListening(boolean z) {
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        if (z) {
            this.mSyncObserverHandle = ContentResolver.addStatusChangeListener(1, this.mSyncObserver);
            return;
        }
        ContentResolver.removeStatusChangeListener(this.mSyncObserverHandle);
        this.mSyncObserverHandle = null;
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        booleanState.value = ContentResolver.getMasterSyncAutomatically();
        booleanState.label = this.mContext.getString(R$string.quick_settings_sync_label);
        booleanState.icon = this.mIcon;
        if (booleanState.value) {
            booleanState.contentDescription = this.mContext.getString(R$string.accessibility_quick_settings_sync_on);
            booleanState.state = 2;
            return;
        }
        booleanState.contentDescription = this.mContext.getString(R$string.accessibility_quick_settings_sync_off);
        booleanState.state = 1;
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.BooleanState newTileState() {
        return new QSTile.BooleanState();
    }
}