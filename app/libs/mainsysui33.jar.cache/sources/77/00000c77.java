package com.android.keyguard;

import android.app.admin.DevicePolicyManager;
import android.app.trust.TrustManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.SensorPrivacyManager;
import android.hardware.biometrics.BiometricManager;
import android.hardware.face.FaceManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Looper;
import android.os.PowerManager;
import android.os.UserManager;
import android.service.dreams.IDreamManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.telephony.TelephonyListenerManager;
import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardUpdateMonitor_Factory.class */
public final class KeyguardUpdateMonitor_Factory implements Factory<KeyguardUpdateMonitor> {
    public final Provider<ActiveUnlockConfig> activeUnlockConfigurationProvider;
    public final Provider<AuthController> authControllerProvider;
    public final Provider<Executor> backgroundExecutorProvider;
    public final Provider<BiometricManager> biometricManagerProvider;
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DevicePolicyManager> devicePolicyManagerProvider;
    public final Provider<IDreamManager> dreamManagerProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<FaceManager> faceManagerProvider;
    public final Provider<FaceWakeUpTriggersConfig> faceWakeUpTriggersConfigProvider;
    public final Provider<FingerprintManager> fingerprintManagerProvider;
    public final Provider<InteractionJankMonitor> interactionJankMonitorProvider;
    public final Provider<LatencyTracker> latencyTrackerProvider;
    public final Provider<LockPatternUtils> lockPatternUtilsProvider;
    public final Provider<KeyguardUpdateMonitorLogger> loggerProvider;
    public final Provider<Executor> mainExecutorProvider;
    public final Provider<Looper> mainLooperProvider;
    public final Provider<PackageManager> packageManagerProvider;
    public final Provider<PowerManager> powerManagerProvider;
    public final Provider<SecureSettings> secureSettingsProvider;
    public final Provider<SensorPrivacyManager> sensorPrivacyManagerProvider;
    public final Provider<SessionTracker> sessionTrackerProvider;
    public final Provider<StatusBarStateController> statusBarStateControllerProvider;
    public final Provider<SubscriptionManager> subscriptionManagerProvider;
    public final Provider<TelephonyListenerManager> telephonyListenerManagerProvider;
    public final Provider<TelephonyManager> telephonyManagerProvider;
    public final Provider<TrustManager> trustManagerProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;
    public final Provider<UserManager> userManagerProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public KeyguardUpdateMonitor_Factory(Provider<Context> provider, Provider<UserTracker> provider2, Provider<Looper> provider3, Provider<BroadcastDispatcher> provider4, Provider<SecureSettings> provider5, Provider<DumpManager> provider6, Provider<Executor> provider7, Provider<Executor> provider8, Provider<StatusBarStateController> provider9, Provider<LockPatternUtils> provider10, Provider<AuthController> provider11, Provider<TelephonyListenerManager> provider12, Provider<InteractionJankMonitor> provider13, Provider<LatencyTracker> provider14, Provider<ActiveUnlockConfig> provider15, Provider<KeyguardUpdateMonitorLogger> provider16, Provider<UiEventLogger> provider17, Provider<SessionTracker> provider18, Provider<PowerManager> provider19, Provider<TrustManager> provider20, Provider<SubscriptionManager> provider21, Provider<UserManager> provider22, Provider<IDreamManager> provider23, Provider<DevicePolicyManager> provider24, Provider<SensorPrivacyManager> provider25, Provider<TelephonyManager> provider26, Provider<PackageManager> provider27, Provider<FaceManager> provider28, Provider<FingerprintManager> provider29, Provider<BiometricManager> provider30, Provider<FaceWakeUpTriggersConfig> provider31) {
        this.contextProvider = provider;
        this.userTrackerProvider = provider2;
        this.mainLooperProvider = provider3;
        this.broadcastDispatcherProvider = provider4;
        this.secureSettingsProvider = provider5;
        this.dumpManagerProvider = provider6;
        this.backgroundExecutorProvider = provider7;
        this.mainExecutorProvider = provider8;
        this.statusBarStateControllerProvider = provider9;
        this.lockPatternUtilsProvider = provider10;
        this.authControllerProvider = provider11;
        this.telephonyListenerManagerProvider = provider12;
        this.interactionJankMonitorProvider = provider13;
        this.latencyTrackerProvider = provider14;
        this.activeUnlockConfigurationProvider = provider15;
        this.loggerProvider = provider16;
        this.uiEventLoggerProvider = provider17;
        this.sessionTrackerProvider = provider18;
        this.powerManagerProvider = provider19;
        this.trustManagerProvider = provider20;
        this.subscriptionManagerProvider = provider21;
        this.userManagerProvider = provider22;
        this.dreamManagerProvider = provider23;
        this.devicePolicyManagerProvider = provider24;
        this.sensorPrivacyManagerProvider = provider25;
        this.telephonyManagerProvider = provider26;
        this.packageManagerProvider = provider27;
        this.faceManagerProvider = provider28;
        this.fingerprintManagerProvider = provider29;
        this.biometricManagerProvider = provider30;
        this.faceWakeUpTriggersConfigProvider = provider31;
    }

    public static KeyguardUpdateMonitor_Factory create(Provider<Context> provider, Provider<UserTracker> provider2, Provider<Looper> provider3, Provider<BroadcastDispatcher> provider4, Provider<SecureSettings> provider5, Provider<DumpManager> provider6, Provider<Executor> provider7, Provider<Executor> provider8, Provider<StatusBarStateController> provider9, Provider<LockPatternUtils> provider10, Provider<AuthController> provider11, Provider<TelephonyListenerManager> provider12, Provider<InteractionJankMonitor> provider13, Provider<LatencyTracker> provider14, Provider<ActiveUnlockConfig> provider15, Provider<KeyguardUpdateMonitorLogger> provider16, Provider<UiEventLogger> provider17, Provider<SessionTracker> provider18, Provider<PowerManager> provider19, Provider<TrustManager> provider20, Provider<SubscriptionManager> provider21, Provider<UserManager> provider22, Provider<IDreamManager> provider23, Provider<DevicePolicyManager> provider24, Provider<SensorPrivacyManager> provider25, Provider<TelephonyManager> provider26, Provider<PackageManager> provider27, Provider<FaceManager> provider28, Provider<FingerprintManager> provider29, Provider<BiometricManager> provider30, Provider<FaceWakeUpTriggersConfig> provider31) {
        return new KeyguardUpdateMonitor_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17, provider18, provider19, provider20, provider21, provider22, provider23, provider24, provider25, provider26, provider27, provider28, provider29, provider30, provider31);
    }

    public static KeyguardUpdateMonitor newInstance(Context context, UserTracker userTracker, Looper looper, BroadcastDispatcher broadcastDispatcher, SecureSettings secureSettings, DumpManager dumpManager, Executor executor, Executor executor2, StatusBarStateController statusBarStateController, LockPatternUtils lockPatternUtils, AuthController authController, TelephonyListenerManager telephonyListenerManager, InteractionJankMonitor interactionJankMonitor, LatencyTracker latencyTracker, ActiveUnlockConfig activeUnlockConfig, KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger, UiEventLogger uiEventLogger, Provider<SessionTracker> provider, PowerManager powerManager, TrustManager trustManager, SubscriptionManager subscriptionManager, UserManager userManager, IDreamManager iDreamManager, DevicePolicyManager devicePolicyManager, SensorPrivacyManager sensorPrivacyManager, TelephonyManager telephonyManager, PackageManager packageManager, FaceManager faceManager, FingerprintManager fingerprintManager, BiometricManager biometricManager, FaceWakeUpTriggersConfig faceWakeUpTriggersConfig) {
        return new KeyguardUpdateMonitor(context, userTracker, looper, broadcastDispatcher, secureSettings, dumpManager, executor, executor2, statusBarStateController, lockPatternUtils, authController, telephonyListenerManager, interactionJankMonitor, latencyTracker, activeUnlockConfig, keyguardUpdateMonitorLogger, uiEventLogger, provider, powerManager, trustManager, subscriptionManager, userManager, iDreamManager, devicePolicyManager, sensorPrivacyManager, telephonyManager, packageManager, faceManager, fingerprintManager, biometricManager, faceWakeUpTriggersConfig);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public KeyguardUpdateMonitor m773get() {
        return newInstance((Context) this.contextProvider.get(), (UserTracker) this.userTrackerProvider.get(), (Looper) this.mainLooperProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (SecureSettings) this.secureSettingsProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (Executor) this.backgroundExecutorProvider.get(), (Executor) this.mainExecutorProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (LockPatternUtils) this.lockPatternUtilsProvider.get(), (AuthController) this.authControllerProvider.get(), (TelephonyListenerManager) this.telephonyListenerManagerProvider.get(), (InteractionJankMonitor) this.interactionJankMonitorProvider.get(), (LatencyTracker) this.latencyTrackerProvider.get(), (ActiveUnlockConfig) this.activeUnlockConfigurationProvider.get(), (KeyguardUpdateMonitorLogger) this.loggerProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get(), this.sessionTrackerProvider, (PowerManager) this.powerManagerProvider.get(), (TrustManager) this.trustManagerProvider.get(), (SubscriptionManager) this.subscriptionManagerProvider.get(), (UserManager) this.userManagerProvider.get(), (IDreamManager) this.dreamManagerProvider.get(), (DevicePolicyManager) this.devicePolicyManagerProvider.get(), (SensorPrivacyManager) this.sensorPrivacyManagerProvider.get(), (TelephonyManager) this.telephonyManagerProvider.get(), (PackageManager) this.packageManagerProvider.get(), (FaceManager) this.faceManagerProvider.get(), (FingerprintManager) this.fingerprintManagerProvider.get(), (BiometricManager) this.biometricManagerProvider.get(), (FaceWakeUpTriggersConfig) this.faceWakeUpTriggersConfigProvider.get());
    }
}