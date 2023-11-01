package com.android.systemui.qs.tiles;

import android.content.Intent;
import android.content.res.Resources;
import android.hardware.SensorPrivacyManager;
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
import com.android.systemui.qs.SettingObserver;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.RotationLockController;
import com.android.systemui.statusbar.policy.RotationLockControllerImpl;
import com.android.systemui.util.settings.SecureSettings;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/RotationLockTile.class */
public class RotationLockTile extends QSTileImpl<QSTile.BooleanState> implements BatteryController.BatteryStateChangeCallback {
    public final BatteryController mBatteryController;
    public final RotationLockController.RotationLockControllerCallback mCallback;
    public final RotationLockController mController;
    public final QSTile.Icon mIcon;
    public final SensorPrivacyManager mPrivacyManager;
    public final SensorPrivacyManager.OnSensorPrivacyChangedListener mSensorPrivacyChangedListener;
    public final SettingObserver mSetting;

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.RotationLockTile$$ExternalSyntheticLambda0.onSensorPrivacyChanged(int, boolean):void] */
    public static /* synthetic */ void $r8$lambda$YX50WhLvLWZ8axnPiGDk6Ux1eJE(RotationLockTile rotationLockTile, int i, boolean z) {
        rotationLockTile.lambda$new$0(i, z);
    }

    public RotationLockTile(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, RotationLockController rotationLockController, SensorPrivacyManager sensorPrivacyManager, BatteryController batteryController, SecureSettings secureSettings) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger);
        this.mIcon = QSTileImpl.ResourceIcon.get(17302833);
        RotationLockController.RotationLockControllerCallback rotationLockControllerCallback = new RotationLockController.RotationLockControllerCallback() { // from class: com.android.systemui.qs.tiles.RotationLockTile.2
            {
                RotationLockTile.this = this;
            }

            public void onRotationLockStateChanged(boolean z, boolean z2) {
                RotationLockTile.this.refreshState(Boolean.valueOf(z));
            }
        };
        this.mCallback = rotationLockControllerCallback;
        this.mSensorPrivacyChangedListener = new SensorPrivacyManager.OnSensorPrivacyChangedListener() { // from class: com.android.systemui.qs.tiles.RotationLockTile$$ExternalSyntheticLambda0
            public final void onSensorPrivacyChanged(int i, boolean z) {
                RotationLockTile.$r8$lambda$YX50WhLvLWZ8axnPiGDk6Ux1eJE(RotationLockTile.this, i, z);
            }
        };
        this.mController = rotationLockController;
        rotationLockController.observe(this, rotationLockControllerCallback);
        this.mPrivacyManager = sensorPrivacyManager;
        this.mBatteryController = batteryController;
        this.mSetting = new SettingObserver(secureSettings, this.mHandler, "camera_autorotate", qSHost.getUserContext().getUserId()) { // from class: com.android.systemui.qs.tiles.RotationLockTile.1
            {
                RotationLockTile.this = this;
            }

            @Override // com.android.systemui.qs.SettingObserver
            public void handleValueChanged(int i, boolean z) {
                RotationLockTile.this.handleRefreshState(null);
            }
        };
        batteryController.observe(getLifecycle(), this);
    }

    public static boolean isCurrentOrientationLockPortrait(RotationLockController rotationLockController, Resources resources) {
        int rotationLockOrientation = rotationLockController.getRotationLockOrientation();
        boolean z = true;
        if (rotationLockOrientation != 0) {
            return rotationLockOrientation != 2;
        }
        if (resources.getConfiguration().orientation == 2) {
            z = false;
        }
        return z;
    }

    public /* synthetic */ void lambda$new$0(int i, boolean z) {
        refreshState();
    }

    public final String getAccessibilityString(boolean z) {
        return this.mContext.getString(R$string.accessibility_quick_settings_rotation);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public Intent getLongClickIntent() {
        return new Intent("android.settings.AUTO_ROTATE_SETTINGS");
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public int getMetricsCategory() {
        return 123;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return getState().label;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleClick(View view) {
        boolean z = !((QSTile.BooleanState) this.mState).value;
        this.mController.setRotationLocked(!z);
        refreshState(Boolean.valueOf(z));
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleDestroy() {
        super.handleDestroy();
        this.mSetting.setListening(false);
        this.mPrivacyManager.removeSensorPrivacyListener(2, this.mSensorPrivacyChangedListener);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleInitialize() {
        this.mPrivacyManager.addSensorPrivacyListener(2, this.mSensorPrivacyChangedListener);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleSetListening(boolean z) {
        super.handleSetListening(z);
        this.mSetting.setListening(z);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUpdateState(QSTile.BooleanState booleanState, Object obj) {
        boolean isRotationLocked = this.mController.isRotationLocked();
        boolean z = !this.mBatteryController.isPowerSave() && !this.mPrivacyManager.isSensorPrivacyEnabled(2) && RotationLockControllerImpl.hasSufficientPermission(this.mContext) && this.mController.isCameraRotationEnabled();
        booleanState.value = !isRotationLocked;
        booleanState.label = this.mContext.getString(R$string.quick_settings_rotation_unlocked_label);
        booleanState.icon = QSTileImpl.ResourceIcon.get(R$drawable.qs_auto_rotate_icon_off);
        booleanState.contentDescription = getAccessibilityString(isRotationLocked);
        if (isRotationLocked) {
            booleanState.secondaryLabel = "";
        } else {
            booleanState.secondaryLabel = z ? this.mContext.getResources().getString(R$string.rotation_lock_camera_rotation_on) : "";
            booleanState.icon = QSTileImpl.ResourceIcon.get(R$drawable.qs_auto_rotate_icon_on);
        }
        booleanState.stateDescription = booleanState.secondaryLabel;
        booleanState.expandedAccessibilityClassName = Switch.class.getName();
        booleanState.state = booleanState.value ? 2 : 1;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public void handleUserSwitch(int i) {
        this.mSetting.setUserId(i);
        handleRefreshState(null);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl
    public QSTile.BooleanState newTileState() {
        return new QSTile.BooleanState();
    }

    public void onPowerSaveChanged(boolean z) {
        refreshState();
    }
}