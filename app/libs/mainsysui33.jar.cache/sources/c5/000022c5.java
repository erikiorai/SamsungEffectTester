package com.android.systemui.qs.tiles;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.service.dreams.IDreamManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
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
import com.android.systemui.util.settings.SecureSettings;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/DreamTile.class */
public class DreamTile extends QSTileImpl<QSTile.BooleanState> {
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final IDreamManager mDreamManager;
    public final boolean mDreamOnlyEnabledForDockUser;
    public final SettingObserver mDreamSettingObserver;
    public final boolean mDreamSupported;
    public final SettingObserver mEnabledSettingObserver;
    public final QSTile.Icon mIconDocked;
    public final QSTile.Icon mIconUndocked;
    public boolean mIsDocked;
    public final BroadcastReceiver mReceiver;
    public final UserTracker mUserTracker;

    public DreamTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, IDreamManager iDreamManager, SecureSettings secureSettings, BroadcastDispatcher broadcastDispatcher, UserTracker userTracker, boolean z, boolean z2) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mIconDocked = QSTileImpl.ResourceIcon.get(R$drawable.ic_qs_screen_saver);
        this.mIconUndocked = QSTileImpl.ResourceIcon.get(R$drawable.ic_qs_screen_saver_undocked);
        this.mIsDocked = false;
        this.mReceiver = new BroadcastReceiver() { // from class: com.android.systemui.qs.tiles.DreamTile.1
            @Override // android.content.BroadcastReceiver
            public void onReceive(Context context, Intent intent) {
                if ("android.intent.action.DOCK_EVENT".equals(intent.getAction())) {
                    DreamTile.this.mIsDocked = intent.getIntExtra("android.intent.extra.DOCK_STATE", -1) != 0;
                }
                DreamTile.this.refreshState();
            }
        };
        this.mDreamManager = iDreamManager;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mEnabledSettingObserver = new SettingObserver(secureSettings, this.mHandler, "screensaver_enabled", userTracker.getUserId()) { // from class: com.android.systemui.qs.tiles.DreamTile.2
            @Override // com.android.systemui.qs.SettingObserver
            public void handleValueChanged(int i, boolean z3) {
                DreamTile.this.refreshState();
            }
        };
        this.mDreamSettingObserver = new SettingObserver(secureSettings, this.mHandler, "screensaver_components", userTracker.getUserId()) { // from class: com.android.systemui.qs.tiles.DreamTile.3
            @Override // com.android.systemui.qs.SettingObserver
            public void handleValueChanged(int i, boolean z3) {
                DreamTile.this.refreshState();
            }
        };
        this.mUserTracker = userTracker;
        this.mDreamSupported = z;
        this.mDreamOnlyEnabledForDockUser = z2;
    }

    public final ComponentName getActiveDream() {
        try {
            ComponentName[] dreamComponents = this.mDreamManager.getDreamComponents();
            ComponentName componentName = null;
            if (dreamComponents != null) {
                componentName = null;
                if (dreamComponents.length > 0) {
                    componentName = dreamComponents[0];
                }
            }
            return componentName;
        } catch (RemoteException e) {
            Log.w(this.TAG, "Failed to get active dream", e);
            return null;
        }
    }

    public final CharSequence getActiveDreamName() {
        ComponentName activeDream = getActiveDream();
        if (activeDream != null) {
            PackageManager packageManager = this.mContext.getPackageManager();
            try {
                ServiceInfo serviceInfo = packageManager.getServiceInfo(activeDream, 0);
                if (serviceInfo != null) {
                    return serviceInfo.loadLabel(packageManager);
                }
                return null;
            } catch (PackageManager.NameNotFoundException e) {
                return null;
            }
        }
        return null;
    }

    public CharSequence getContentDescription(CharSequence charSequence) {
        String tileLabel;
        if (TextUtils.isEmpty(charSequence)) {
            tileLabel = getTileLabel();
        } else {
            tileLabel = ((Object) getTileLabel()) + ", " + ((Object) charSequence);
        }
        return tileLabel;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return new Intent("android.settings.DREAM_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mContext.getString(R$string.quick_settings_screensaver_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleClick(View view) {
        try {
            if (this.mDreamManager.isDreaming()) {
                this.mDreamManager.awaken();
            } else {
                this.mDreamManager.dream();
            }
        } catch (RemoteException e) {
            Log.e("QSDream", "Can't dream", e);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleLongClick(View view) {
        try {
            this.mDreamManager.awaken();
        } catch (RemoteException e) {
            Log.e("QSDream", "Can't awaken", e);
        }
        super.handleLongClick(view);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleSetListening(boolean z) {
        super.handleSetListening(z);
        if (z) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.DREAMING_STARTED");
            intentFilter.addAction("android.intent.action.DREAMING_STOPPED");
            intentFilter.addAction("android.intent.action.DOCK_EVENT");
            this.mBroadcastDispatcher.registerReceiver(this.mReceiver, intentFilter);
        } else {
            this.mBroadcastDispatcher.unregisterReceiver(this.mReceiver);
        }
        this.mEnabledSettingObserver.setListening(z);
        this.mDreamSettingObserver.setListening(z);
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        booleanState.label = getTileLabel();
        CharSequence activeDreamName = getActiveDreamName();
        booleanState.secondaryLabel = activeDreamName;
        booleanState.contentDescription = getContentDescription(activeDreamName);
        booleanState.icon = this.mIsDocked ? this.mIconDocked : this.mIconUndocked;
        if (getActiveDream() == null || !isScreensaverEnabled()) {
            booleanState.state = 0;
        } else {
            booleanState.state = isDreaming() ? 2 : 1;
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public boolean isAvailable() {
        return this.mDreamSupported && (!this.mDreamOnlyEnabledForDockUser || this.mUserTracker.getUserHandle().isSystem());
    }

    public final boolean isDreaming() {
        try {
            return this.mDreamManager.isDreaming();
        } catch (RemoteException e) {
            Log.e("QSDream", "Can't check if dreaming", e);
            return false;
        }
    }

    public final boolean isScreensaverEnabled() {
        boolean z = true;
        if (this.mEnabledSettingObserver.getValue() != 1) {
            z = false;
        }
        return z;
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.BooleanState newTileState() {
        return new QSTile.BooleanState();
    }
}