package com.android.keyguard;

import com.android.internal.logging.UiEventLogger;
import com.android.systemui.log.SessionTracker;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/keyguard/KeyguardBiometricLockoutLogger_Factory.class */
public final class KeyguardBiometricLockoutLogger_Factory implements Factory<KeyguardBiometricLockoutLogger> {
    public final Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;
    public final Provider<SessionTracker> sessionTrackerProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;

    public KeyguardBiometricLockoutLogger_Factory(Provider<UiEventLogger> provider, Provider<KeyguardUpdateMonitor> provider2, Provider<SessionTracker> provider3) {
        this.uiEventLoggerProvider = provider;
        this.keyguardUpdateMonitorProvider = provider2;
        this.sessionTrackerProvider = provider3;
    }

    public static KeyguardBiometricLockoutLogger_Factory create(Provider<UiEventLogger> provider, Provider<KeyguardUpdateMonitor> provider2, Provider<SessionTracker> provider3) {
        return new KeyguardBiometricLockoutLogger_Factory(provider, provider2, provider3);
    }

    public static KeyguardBiometricLockoutLogger newInstance(UiEventLogger uiEventLogger, KeyguardUpdateMonitor keyguardUpdateMonitor, SessionTracker sessionTracker) {
        return new KeyguardBiometricLockoutLogger(uiEventLogger, keyguardUpdateMonitor, sessionTracker);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public KeyguardBiometricLockoutLogger m566get() {
        return newInstance((UiEventLogger) this.uiEventLoggerProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get(), (SessionTracker) this.sessionTrackerProvider.get());
    }
}