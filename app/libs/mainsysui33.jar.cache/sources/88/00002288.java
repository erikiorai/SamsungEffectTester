package com.android.systemui.qs.tiles;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.view.View;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
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

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/AmbientDisplayTile.class */
public class AmbientDisplayTile extends QSTileImpl<QSTile.BooleanState> {
    public final QSTile.Icon mIcon;
    public final SettingObserver mSetting;

    public AmbientDisplayTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, UserTracker userTracker, SecureSettings secureSettings) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mIcon = QSTileImpl.ResourceIcon.get(R$drawable.ic_qs_ambient_display);
        this.mSetting = new SettingObserver(secureSettings, this.mHandler, "doze_enabled", userTracker.getUserId(), 1) { // from class: com.android.systemui.qs.tiles.AmbientDisplayTile.1
            @Override // com.android.systemui.qs.SettingObserver
            public void handleValueChanged(int i, boolean z) {
                AmbientDisplayTile.this.handleRefreshState(Integer.valueOf(i));
            }
        };
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return new Intent("android.settings.DISPLAY_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mContext.getString(R$string.quick_settings_ambient_display_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleClick(View view) {
        this.mSetting.setValue(!((QSTile.BooleanState) this.mState).value ? 1 : 0);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleDestroy() {
        super.handleDestroy();
        this.mSetting.setListening(false);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleSetListening(boolean z) {
        super.handleSetListening(z);
        this.mSetting.setListening(z);
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        boolean z = (obj instanceof Integer ? ((Integer) obj).intValue() : this.mSetting.getValue()) != 0;
        booleanState.value = z;
        booleanState.label = this.mContext.getString(R$string.quick_settings_ambient_display_label);
        booleanState.icon = this.mIcon;
        if (z) {
            booleanState.contentDescription = this.mContext.getString(R$string.accessibility_quick_settings_ambient_display_on);
            booleanState.state = 2;
            return;
        }
        booleanState.contentDescription = this.mContext.getString(R$string.accessibility_quick_settings_ambient_display_off);
        booleanState.state = 1;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUserSwitch(int i) {
        this.mSetting.setUserId(i);
        handleRefreshState(Integer.valueOf(this.mSetting.getValue()));
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public boolean isAvailable() {
        String str = Build.IS_DEBUGGABLE ? SystemProperties.get("debug.doze.component") : null;
        String str2 = str;
        if (TextUtils.isEmpty(str)) {
            str2 = this.mContext.getString(17039977);
        }
        return !TextUtils.isEmpty(str2);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.BooleanState newTileState() {
        return new QSTile.BooleanState();
    }
}