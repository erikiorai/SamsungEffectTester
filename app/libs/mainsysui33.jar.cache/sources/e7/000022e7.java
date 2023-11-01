package com.android.systemui.qs.tiles;

import android.os.Handler;
import android.os.Looper;
import android.provider.DeviceConfig;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.DejankUtils;
import com.android.systemui.R$drawable;
import com.android.systemui.R$string;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.statusbar.policy.IndividualSensorPrivacyController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.function.Supplier;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/MicrophoneToggleTile.class */
public class MicrophoneToggleTile extends SensorPrivacyToggleTile {
    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.qs.tiles.MicrophoneToggleTile$$ExternalSyntheticLambda0.get():java.lang.Object] */
    public static /* synthetic */ Boolean $r8$lambda$j1O7iq4x5I67ASFS50WSfnHKPa8() {
        return lambda$isAvailable$0();
    }

    public MicrophoneToggleTile(QSHost qSHost, Looper looper, Handler handler, MetricsLogger metricsLogger, FalsingManager falsingManager, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, IndividualSensorPrivacyController individualSensorPrivacyController, KeyguardStateController keyguardStateController) {
        super(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger, individualSensorPrivacyController, keyguardStateController);
    }

    public static /* synthetic */ Boolean lambda$isAvailable$0() {
        return Boolean.valueOf(DeviceConfig.getBoolean("privacy", "mic_toggle_enabled", true));
    }

    @Override // com.android.systemui.qs.tiles.SensorPrivacyToggleTile
    public int getIconRes(boolean z) {
        return z ? R$drawable.qs_mic_access_off : R$drawable.qs_mic_access_on;
    }

    @Override // com.android.systemui.qs.tiles.SensorPrivacyToggleTile
    public String getRestriction() {
        return "disallow_microphone_toggle";
    }

    @Override // com.android.systemui.qs.tiles.SensorPrivacyToggleTile
    public int getSensorId() {
        return 1;
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public CharSequence getTileLabel() {
        return this.mContext.getString(R$string.quick_settings_mic_label);
    }

    @Override // com.android.systemui.qs.tileimpl.QSTileImpl, com.android.systemui.plugins.qs.QSTile
    public boolean isAvailable() {
        boolean z = true;
        if (!this.mSensorPrivacyController.supportsSensorToggle(1) || !((Boolean) DejankUtils.whitelistIpcs(new Supplier() { // from class: com.android.systemui.qs.tiles.MicrophoneToggleTile$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return MicrophoneToggleTile.$r8$lambda$j1O7iq4x5I67ASFS50WSfnHKPa8();
            }
        })).booleanValue()) {
            z = false;
        }
        return z;
    }
}