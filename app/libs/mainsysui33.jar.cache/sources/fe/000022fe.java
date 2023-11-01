package com.android.systemui.qs.tiles;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Switch;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R$drawable;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.ReduceBrightColorsController;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/ReduceBrightColorsTile.class */
public class ReduceBrightColorsTile extends QSTileImpl<QSTile.BooleanState> implements ReduceBrightColorsController.Listener {
    public final boolean mIsAvailable;
    public final ReduceBrightColorsController mReduceBrightColorsController;

    public ReduceBrightColorsTile(boolean z, ReduceBrightColorsController reduceBrightColorsController, QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mReduceBrightColorsController = reduceBrightColorsController;
        reduceBrightColorsController.observe(getLifecycle(), this);
        this.mIsAvailable = z;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return new Intent("android.settings.REDUCE_BRIGHT_COLORS_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mContext.getString(17041401);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleClick(View view) {
        this.mReduceBrightColorsController.setReduceBrightColorsActivated(!((QSTile.BooleanState) this.mState).value);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleDestroy() {
        super.handleDestroy();
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        boolean isReduceBrightColorsActivated = this.mReduceBrightColorsController.isReduceBrightColorsActivated();
        booleanState.value = isReduceBrightColorsActivated;
        booleanState.state = isReduceBrightColorsActivated ? 2 : 1;
        booleanState.label = this.mContext.getString(17041401);
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
        booleanState.contentDescription = booleanState.label;
        booleanState.icon = QSTileImpl.ResourceIcon.get(booleanState.value ? R$drawable.qs_extra_dim_icon_on : R$drawable.qs_extra_dim_icon_off);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public boolean isAvailable() {
        return this.mIsAvailable;
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.BooleanState newTileState() {
        return new QSTile.BooleanState();
    }

    @Override // com.android.systemui.qs.ReduceBrightColorsController.Listener
    public void onActivated(boolean z) {
        refreshState();
    }
}