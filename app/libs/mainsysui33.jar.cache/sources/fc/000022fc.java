package com.android.systemui.qs.tiles;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import com.android.internal.custom.hardware.LineageHardwareManager;
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

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/ReadingModeTile.class */
public class ReadingModeTile extends QSTileImpl<QSTile.BooleanState> {
    public static final Intent DISPLAY_SETTINGS = new Intent("android.settings.DISPLAY_SETTINGS");
    public LineageHardwareManager mHardware;
    public final QSTile.Icon mIcon;

    public ReadingModeTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mIcon = QSTileImpl.ResourceIcon.get(R$drawable.ic_qs_reader);
        this.mHardware = LineageHardwareManager.getInstance(this.mContext);
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
        return this.mContext.getString(R$string.quick_settings_reading_mode);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleClick(View view) {
        this.mHardware.set(16384, !isReadingModeEnabled());
        refreshState();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleSetListening(boolean z) {
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        boolean isReadingModeEnabled = isReadingModeEnabled();
        booleanState.value = isReadingModeEnabled;
        booleanState.icon = this.mIcon;
        if (isReadingModeEnabled) {
            booleanState.contentDescription = this.mContext.getString(R$string.accessibility_quick_settings_reading_mode_on);
            booleanState.state = 2;
        } else {
            booleanState.contentDescription = this.mContext.getString(R$string.accessibility_quick_settings_reading_mode_off);
            booleanState.state = 1;
        }
        booleanState.label = getTileLabel();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public boolean isAvailable() {
        return this.mHardware.isSupported(16384);
    }

    public final boolean isReadingModeEnabled() {
        return this.mHardware.get(16384);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.BooleanState newTileState() {
        return new QSTile.BooleanState();
    }
}