package com.android.systemui.doze;

import android.content.Context;
import android.hardware.display.AmbientDisplayConfiguration;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dock.DockManager;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.sensors.AsyncSensorManager;
import com.android.systemui.util.sensors.ProximityCheck;
import com.android.systemui.util.sensors.ProximitySensor;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.util.wakelock.WakeLock;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeTriggers_Factory.class */
public final class DozeTriggers_Factory implements Factory<DozeTriggers> {
    public final Provider<AuthController> authControllerProvider;
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<AmbientDisplayConfiguration> configProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DevicePostureController> devicePostureControllerProvider;
    public final Provider<DockManager> dockManagerProvider;
    public final Provider<DozeHost> dozeHostProvider;
    public final Provider<DozeLog> dozeLogProvider;
    public final Provider<DozeParameters> dozeParametersProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<ProximityCheck> proxCheckProvider;
    public final Provider<ProximitySensor> proximitySensorProvider;
    public final Provider<SecureSettings> secureSettingsProvider;
    public final Provider<AsyncSensorManager> sensorManagerProvider;
    public final Provider<SessionTracker> sessionTrackerProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;
    public final Provider<UserTracker> userTrackerProvider;
    public final Provider<WakeLock> wakeLockProvider;

    public DozeTriggers_Factory(Provider<Context> provider, Provider<DozeHost> provider2, Provider<AmbientDisplayConfiguration> provider3, Provider<DozeParameters> provider4, Provider<AsyncSensorManager> provider5, Provider<WakeLock> provider6, Provider<DockManager> provider7, Provider<ProximitySensor> provider8, Provider<ProximityCheck> provider9, Provider<DozeLog> provider10, Provider<BroadcastDispatcher> provider11, Provider<SecureSettings> provider12, Provider<AuthController> provider13, Provider<UiEventLogger> provider14, Provider<SessionTracker> provider15, Provider<KeyguardStateController> provider16, Provider<DevicePostureController> provider17, Provider<UserTracker> provider18) {
        this.contextProvider = provider;
        this.dozeHostProvider = provider2;
        this.configProvider = provider3;
        this.dozeParametersProvider = provider4;
        this.sensorManagerProvider = provider5;
        this.wakeLockProvider = provider6;
        this.dockManagerProvider = provider7;
        this.proximitySensorProvider = provider8;
        this.proxCheckProvider = provider9;
        this.dozeLogProvider = provider10;
        this.broadcastDispatcherProvider = provider11;
        this.secureSettingsProvider = provider12;
        this.authControllerProvider = provider13;
        this.uiEventLoggerProvider = provider14;
        this.sessionTrackerProvider = provider15;
        this.keyguardStateControllerProvider = provider16;
        this.devicePostureControllerProvider = provider17;
        this.userTrackerProvider = provider18;
    }

    public static DozeTriggers_Factory create(Provider<Context> provider, Provider<DozeHost> provider2, Provider<AmbientDisplayConfiguration> provider3, Provider<DozeParameters> provider4, Provider<AsyncSensorManager> provider5, Provider<WakeLock> provider6, Provider<DockManager> provider7, Provider<ProximitySensor> provider8, Provider<ProximityCheck> provider9, Provider<DozeLog> provider10, Provider<BroadcastDispatcher> provider11, Provider<SecureSettings> provider12, Provider<AuthController> provider13, Provider<UiEventLogger> provider14, Provider<SessionTracker> provider15, Provider<KeyguardStateController> provider16, Provider<DevicePostureController> provider17, Provider<UserTracker> provider18) {
        return new DozeTriggers_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17, provider18);
    }

    public static DozeTriggers newInstance(Context context, DozeHost dozeHost, AmbientDisplayConfiguration ambientDisplayConfiguration, DozeParameters dozeParameters, AsyncSensorManager asyncSensorManager, WakeLock wakeLock, DockManager dockManager, ProximitySensor proximitySensor, ProximityCheck proximityCheck, DozeLog dozeLog, BroadcastDispatcher broadcastDispatcher, SecureSettings secureSettings, AuthController authController, UiEventLogger uiEventLogger, SessionTracker sessionTracker, KeyguardStateController keyguardStateController, DevicePostureController devicePostureController, UserTracker userTracker) {
        return new DozeTriggers(context, dozeHost, ambientDisplayConfiguration, dozeParameters, asyncSensorManager, wakeLock, dockManager, proximitySensor, proximityCheck, dozeLog, broadcastDispatcher, secureSettings, authController, uiEventLogger, sessionTracker, keyguardStateController, devicePostureController, userTracker);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DozeTriggers m2525get() {
        return newInstance((Context) this.contextProvider.get(), (DozeHost) this.dozeHostProvider.get(), (AmbientDisplayConfiguration) this.configProvider.get(), (DozeParameters) this.dozeParametersProvider.get(), (AsyncSensorManager) this.sensorManagerProvider.get(), (WakeLock) this.wakeLockProvider.get(), (DockManager) this.dockManagerProvider.get(), (ProximitySensor) this.proximitySensorProvider.get(), (ProximityCheck) this.proxCheckProvider.get(), (DozeLog) this.dozeLogProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (SecureSettings) this.secureSettingsProvider.get(), (AuthController) this.authControllerProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get(), (SessionTracker) this.sessionTrackerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (DevicePostureController) this.devicePostureControllerProvider.get(), (UserTracker) this.userTrackerProvider.get());
    }
}