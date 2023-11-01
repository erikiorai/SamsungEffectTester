package com.android.systemui.log;

import com.android.internal.statusbar.IStatusBarService;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/log/SessionTracker_Factory.class */
public final class SessionTracker_Factory implements Factory<SessionTracker> {
    public final Provider<AuthController> authControllerProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;
    public final Provider<IStatusBarService> statusBarServiceProvider;

    public SessionTracker_Factory(Provider<IStatusBarService> provider, Provider<AuthController> provider2, Provider<KeyguardUpdateMonitor> provider3, Provider<KeyguardStateController> provider4) {
        this.statusBarServiceProvider = provider;
        this.authControllerProvider = provider2;
        this.keyguardUpdateMonitorProvider = provider3;
        this.keyguardStateControllerProvider = provider4;
    }

    public static SessionTracker_Factory create(Provider<IStatusBarService> provider, Provider<AuthController> provider2, Provider<KeyguardUpdateMonitor> provider3, Provider<KeyguardStateController> provider4) {
        return new SessionTracker_Factory(provider, provider2, provider3, provider4);
    }

    public static SessionTracker newInstance(IStatusBarService iStatusBarService, AuthController authController, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardStateController keyguardStateController) {
        return new SessionTracker(iStatusBarService, authController, keyguardUpdateMonitor, keyguardStateController);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public SessionTracker m3102get() {
        return newInstance((IStatusBarService) this.statusBarServiceProvider.get(), (AuthController) this.authControllerProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get());
    }
}