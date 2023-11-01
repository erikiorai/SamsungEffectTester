package com.android.systemui.qs.tiles;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Switch;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.R$string;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.qs.QSTile;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;
import com.android.systemui.statusbar.policy.KeyguardStateController;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/SensorPrivacyToggleTile.class */
public abstract class SensorPrivacyToggleTile extends QSTileImpl<QSTile.BooleanState> implements IndividualSensorPrivacyController.Callback {
    public final KeyguardStateController mKeyguard;
    public IndividualSensorPrivacyController mSensorPrivacyController;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.SensorPrivacyToggleTile$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$x86pmkXsmUujP9aKmirAqUGAzP0(SensorPrivacyToggleTile sensorPrivacyToggleTile, boolean z) {
        sensorPrivacyToggleTile.lambda$handleClick$0(z);
    }

    public SensorPrivacyToggleTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, IndividualSensorPrivacyController individualSensorPrivacyController, KeyguardStateController keyguardStateController) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mSensorPrivacyController = individualSensorPrivacyController;
        this.mKeyguard = keyguardStateController;
        individualSensorPrivacyController.observe(getLifecycle(), this);
    }

    public /* synthetic */ void lambda$handleClick$0(boolean z) {
        this.mSensorPrivacyController.setSensorBlocked(1, getSensorId(), !z);
    }

    public abstract int getIconRes(boolean z);

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return new Intent("android.settings.PRIVACY_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 0;
    }

    public abstract String getRestriction();

    public abstract int getSensorId();

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleClick(View view) {
        final boolean isSensorBlocked = this.mSensorPrivacyController.isSensorBlocked(getSensorId());
        if (this.mSensorPrivacyController.requiresAuthentication() && this.mKeyguard.isMethodSecure() && this.mKeyguard.isShowing()) {
            this.mActivityStarter.postQSRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.tiles.SensorPrivacyToggleTile$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SensorPrivacyToggleTile.$r8$lambda$x86pmkXsmUujP9aKmirAqUGAzP0(SensorPrivacyToggleTile.this, isSensorBlocked);
                }
            });
        } else {
            this.mSensorPrivacyController.setSensorBlocked(1, getSensorId(), !isSensorBlocked);
        }
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        boolean isSensorBlocked = obj == null ? this.mSensorPrivacyController.isSensorBlocked(getSensorId()) : ((Boolean) obj).booleanValue();
        checkIfRestrictionEnforcedByAdminOnly(booleanState, getRestriction());
        booleanState.icon = QSTileImpl.ResourceIcon.get(getIconRes(isSensorBlocked));
        booleanState.state = isSensorBlocked ? 1 : 2;
        booleanState.value = !isSensorBlocked;
        booleanState.label = getTileLabel();
        if (isSensorBlocked) {
            booleanState.secondaryLabel = this.mContext.getString(R$string.quick_settings_camera_mic_blocked);
        } else {
            booleanState.secondaryLabel = this.mContext.getString(R$string.quick_settings_camera_mic_available);
        }
        booleanState.contentDescription = booleanState.label;
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.BooleanState newTileState() {
        return new QSTile.BooleanState();
    }

    public void onSensorBlockedChanged(int i, boolean z) {
        if (i == getSensorId()) {
            refreshState(Boolean.valueOf(z));
        }
    }
}