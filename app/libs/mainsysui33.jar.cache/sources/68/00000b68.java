package com.android.keyguard;

import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.content.res.Configuration;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.DejankUtils;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.ViewController;

/* loaded from: mainsysui33.jar:com/android/keyguard/EmergencyButtonController.class */
public class EmergencyButtonController extends ViewController<EmergencyButton> {
    public final ActivityTaskManager mActivityTaskManager;
    public final ConfigurationController mConfigurationController;
    public final ConfigurationController.ConfigurationListener mConfigurationListener;
    public EmergencyButtonCallback mEmergencyButtonCallback;
    public final KeyguardUpdateMonitorCallback mInfoCallback;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final MetricsLogger mMetricsLogger;
    public final PowerManager mPowerManager;
    public ShadeController mShadeController;
    public final TelecomManager mTelecomManager;
    public final TelephonyManager mTelephonyManager;

    /* loaded from: mainsysui33.jar:com/android/keyguard/EmergencyButtonController$EmergencyButtonCallback.class */
    public interface EmergencyButtonCallback {
        void onEmergencyButtonClickedWhenInCall();
    }

    /* loaded from: mainsysui33.jar:com/android/keyguard/EmergencyButtonController$Factory.class */
    public static class Factory {
        public final ActivityTaskManager mActivityTaskManager;
        public final ConfigurationController mConfigurationController;
        public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
        public final MetricsLogger mMetricsLogger;
        public final PowerManager mPowerManager;
        public ShadeController mShadeController;
        public final TelecomManager mTelecomManager;
        public final TelephonyManager mTelephonyManager;

        public Factory(ConfigurationController configurationController, KeyguardUpdateMonitor keyguardUpdateMonitor, TelephonyManager telephonyManager, PowerManager powerManager, ActivityTaskManager activityTaskManager, ShadeController shadeController, TelecomManager telecomManager, MetricsLogger metricsLogger) {
            this.mConfigurationController = configurationController;
            this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
            this.mTelephonyManager = telephonyManager;
            this.mPowerManager = powerManager;
            this.mActivityTaskManager = activityTaskManager;
            this.mShadeController = shadeController;
            this.mTelecomManager = telecomManager;
            this.mMetricsLogger = metricsLogger;
        }

        public EmergencyButtonController create(EmergencyButton emergencyButton) {
            return new EmergencyButtonController(emergencyButton, this.mConfigurationController, this.mKeyguardUpdateMonitor, this.mTelephonyManager, this.mPowerManager, this.mActivityTaskManager, this.mShadeController, this.mTelecomManager, this.mMetricsLogger, null);
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.EmergencyButtonController$$ExternalSyntheticLambda0.run():void] */
    public static /* synthetic */ void $r8$lambda$ijHpXbWm3peqrtyfqK4lKTZFXBE(EmergencyButtonController emergencyButtonController) {
        emergencyButtonController.updateEmergencyCallButton();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.keyguard.EmergencyButtonController$$ExternalSyntheticLambda1.onClick(android.view.View):void] */
    /* renamed from: $r8$lambda$liiUumVyvc6K-1pJpVoffTcaOa4 */
    public static /* synthetic */ void m550$r8$lambda$liiUumVyvc6K1pJpVoffTcaOa4(EmergencyButtonController emergencyButtonController, View view) {
        emergencyButtonController.lambda$onViewAttached$0(view);
    }

    public EmergencyButtonController(EmergencyButton emergencyButton, ConfigurationController configurationController, KeyguardUpdateMonitor keyguardUpdateMonitor, TelephonyManager telephonyManager, PowerManager powerManager, ActivityTaskManager activityTaskManager, ShadeController shadeController, TelecomManager telecomManager, MetricsLogger metricsLogger) {
        super(emergencyButton);
        this.mInfoCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.EmergencyButtonController.1
            {
                EmergencyButtonController.this = this;
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onPhoneStateChanged(int i) {
                EmergencyButtonController.this.updateEmergencyCallButton();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onSimStateChanged(int i, int i2, int i3) {
                EmergencyButtonController.this.updateEmergencyCallButton();
            }
        };
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.EmergencyButtonController.2
            {
                EmergencyButtonController.this = this;
            }

            public void onConfigChanged(Configuration configuration) {
                EmergencyButtonController.this.updateEmergencyCallButton();
            }
        };
        this.mConfigurationController = configurationController;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mTelephonyManager = telephonyManager;
        this.mPowerManager = powerManager;
        this.mActivityTaskManager = activityTaskManager;
        this.mShadeController = shadeController;
        this.mTelecomManager = telecomManager;
        this.mMetricsLogger = metricsLogger;
    }

    public /* synthetic */ EmergencyButtonController(EmergencyButton emergencyButton, ConfigurationController configurationController, KeyguardUpdateMonitor keyguardUpdateMonitor, TelephonyManager telephonyManager, PowerManager powerManager, ActivityTaskManager activityTaskManager, ShadeController shadeController, TelecomManager telecomManager, MetricsLogger metricsLogger, EmergencyButtonController-IA r21) {
        this(emergencyButton, configurationController, keyguardUpdateMonitor, telephonyManager, powerManager, activityTaskManager, shadeController, telecomManager, metricsLogger);
    }

    public /* synthetic */ void lambda$onViewAttached$0(View view) {
        takeEmergencyCallAction();
    }

    public void onInit() {
        DejankUtils.whitelistIpcs(new Runnable() { // from class: com.android.keyguard.EmergencyButtonController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                EmergencyButtonController.$r8$lambda$ijHpXbWm3peqrtyfqK4lKTZFXBE(EmergencyButtonController.this);
            }
        });
    }

    public void onViewAttached() {
        this.mKeyguardUpdateMonitor.registerCallback(this.mInfoCallback);
        this.mConfigurationController.addCallback(this.mConfigurationListener);
        ((EmergencyButton) ((ViewController) this).mView).setOnClickListener(new View.OnClickListener() { // from class: com.android.keyguard.EmergencyButtonController$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                EmergencyButtonController.m550$r8$lambda$liiUumVyvc6K1pJpVoffTcaOa4(EmergencyButtonController.this, view);
            }
        });
    }

    public void onViewDetached() {
        this.mKeyguardUpdateMonitor.removeCallback(this.mInfoCallback);
        this.mConfigurationController.removeCallback(this.mConfigurationListener);
    }

    public void setEmergencyButtonCallback(EmergencyButtonCallback emergencyButtonCallback) {
        this.mEmergencyButtonCallback = emergencyButtonCallback;
    }

    public void takeEmergencyCallAction() {
        this.mMetricsLogger.action(200);
        PowerManager powerManager = this.mPowerManager;
        if (powerManager != null) {
            powerManager.userActivity(SystemClock.uptimeMillis(), true);
        }
        this.mActivityTaskManager.stopSystemLockTaskMode();
        this.mShadeController.collapseShade(false);
        TelecomManager telecomManager = this.mTelecomManager;
        if (telecomManager != null && telecomManager.isInCall()) {
            this.mTelecomManager.showInCallScreen(false);
            EmergencyButtonCallback emergencyButtonCallback = this.mEmergencyButtonCallback;
            if (emergencyButtonCallback != null) {
                emergencyButtonCallback.onEmergencyButtonClickedWhenInCall();
                return;
            }
            return;
        }
        this.mKeyguardUpdateMonitor.reportEmergencyCallAction(true);
        TelecomManager telecomManager2 = this.mTelecomManager;
        if (telecomManager2 == null) {
            Log.wtf("EmergencyButton", "TelecomManager was null, cannot launch emergency dialer");
            return;
        }
        getContext().startActivityAsUser(telecomManager2.createLaunchEmergencyDialerIntent(null).setFlags(343932928).putExtra("com.android.phone.EmergencyDialer.extra.ENTRY_TYPE", 1), ActivityOptions.makeCustomAnimation(getContext(), 0, 0).toBundle(), new UserHandle(KeyguardUpdateMonitor.getCurrentUser()));
    }

    public final void updateEmergencyCallButton() {
        View view = ((ViewController) this).mView;
        if (view != null) {
            EmergencyButton emergencyButton = (EmergencyButton) view;
            TelecomManager telecomManager = this.mTelecomManager;
            emergencyButton.updateEmergencyCallButton(telecomManager != null && telecomManager.isInCall(), getContext().getPackageManager().hasSystemFeature("android.hardware.telephony"), this.mKeyguardUpdateMonitor.isSimPinVoiceSecure());
        }
    }
}