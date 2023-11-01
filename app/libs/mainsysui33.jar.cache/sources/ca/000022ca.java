package com.android.systemui.qs.tiles;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Switch;
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
import com.android.systemui.statusbar.policy.FlashlightController;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/FlashlightTile.class */
public class FlashlightTile extends QSTileImpl<QSTile.BooleanState> implements FlashlightController.FlashlightListener {
    public final FlashlightController mFlashlightController;

    public FlashlightTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, FlashlightController flashlightController) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mFlashlightController = flashlightController;
        flashlightController.observe(getLifecycle(), this);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return new Intent("android.media.action.STILL_IMAGE_CAMERA");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 119;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mContext.getString(R$string.quick_settings_flashlight_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleClick(View view) {
        if (ActivityManager.isUserAMonkey()) {
            return;
        }
        boolean z = !((QSTile.BooleanState) this.mState).value;
        refreshState(Boolean.valueOf(z));
        this.mFlashlightController.setFlashlight(z);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleDestroy() {
        super.handleDestroy();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleLongClick(View view) {
        handleClick(view);
    }

    /* JADX DEBUG: Method merged with bridge method */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        Context context = this.mHost.getContext();
        int i = R$string.quick_settings_flashlight_label;
        booleanState.label = context.getString(i);
        booleanState.secondaryLabel = "";
        booleanState.stateDescription = "";
        if (!this.mFlashlightController.isAvailable()) {
            String string = this.mContext.getString(R$string.quick_settings_flashlight_camera_in_use);
            booleanState.secondaryLabel = string;
            booleanState.stateDescription = string;
            booleanState.state = 0;
            booleanState.icon = QSTileImpl.ResourceIcon.get(R$drawable.qs_flashlight_icon_off);
            return;
        }
        if (obj instanceof Boolean) {
            boolean booleanValue = ((Boolean) obj).booleanValue();
            if (booleanValue == booleanState.value) {
                return;
            }
            booleanState.value = booleanValue;
        } else {
            booleanState.value = this.mFlashlightController.isEnabled();
        }
        booleanState.contentDescription = this.mContext.getString(i);
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
        boolean z = booleanState.value;
        booleanState.state = z ? 2 : 1;
        booleanState.icon = QSTileImpl.ResourceIcon.get(z ? R$drawable.qs_flashlight_icon_on : R$drawable.qs_flashlight_icon_off);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUserSwitch(int i) {
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public boolean isAvailable() {
        return this.mFlashlightController.hasFlashlight();
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.BooleanState newTileState() {
        QSTile.BooleanState booleanState = new QSTile.BooleanState();
        booleanState.handlesLongClick = false;
        return booleanState;
    }

    public void onFlashlightAvailabilityChanged(boolean z) {
        refreshState();
    }

    public void onFlashlightChanged(boolean z) {
        refreshState(Boolean.valueOf(z));
    }

    public void onFlashlightError() {
        refreshState(Boolean.FALSE);
    }
}