package com.android.systemui.keyguard.data.repository;

import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.doze.DozeHost;
import com.android.systemui.doze.DozeTransitionListener;
import com.android.systemui.dreams.DreamOverlayCallbackController;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl_Factory.class */
public final class KeyguardRepositoryImpl_Factory implements Factory<KeyguardRepositoryImpl> {
    public final Provider<AuthController> authControllerProvider;
    public final Provider<BiometricUnlockController> biometricUnlockControllerProvider;
    public final Provider<DozeHost> dozeHostProvider;
    public final Provider<DozeTransitionListener> dozeTransitionListenerProvider;
    public final Provider<DreamOverlayCallbackController> dreamOverlayCallbackControllerProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;
    public final Provider<StatusBarStateController> statusBarStateControllerProvider;
    public final Provider<WakefulnessLifecycle> wakefulnessLifecycleProvider;

    public KeyguardRepositoryImpl_Factory(Provider<StatusBarStateController> provider, Provider<DozeHost> provider2, Provider<WakefulnessLifecycle> provider3, Provider<BiometricUnlockController> provider4, Provider<KeyguardStateController> provider5, Provider<KeyguardUpdateMonitor> provider6, Provider<DozeTransitionListener> provider7, Provider<AuthController> provider8, Provider<DreamOverlayCallbackController> provider9) {
        this.statusBarStateControllerProvider = provider;
        this.dozeHostProvider = provider2;
        this.wakefulnessLifecycleProvider = provider3;
        this.biometricUnlockControllerProvider = provider4;
        this.keyguardStateControllerProvider = provider5;
        this.keyguardUpdateMonitorProvider = provider6;
        this.dozeTransitionListenerProvider = provider7;
        this.authControllerProvider = provider8;
        this.dreamOverlayCallbackControllerProvider = provider9;
    }

    public static KeyguardRepositoryImpl_Factory create(Provider<StatusBarStateController> provider, Provider<DozeHost> provider2, Provider<WakefulnessLifecycle> provider3, Provider<BiometricUnlockController> provider4, Provider<KeyguardStateController> provider5, Provider<KeyguardUpdateMonitor> provider6, Provider<DozeTransitionListener> provider7, Provider<AuthController> provider8, Provider<DreamOverlayCallbackController> provider9) {
        return new KeyguardRepositoryImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9);
    }

    public static KeyguardRepositoryImpl newInstance(StatusBarStateController statusBarStateController, DozeHost dozeHost, WakefulnessLifecycle wakefulnessLifecycle, BiometricUnlockController biometricUnlockController, KeyguardStateController keyguardStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, DozeTransitionListener dozeTransitionListener, AuthController authController, DreamOverlayCallbackController dreamOverlayCallbackController) {
        return new KeyguardRepositoryImpl(statusBarStateController, dozeHost, wakefulnessLifecycle, biometricUnlockController, keyguardStateController, keyguardUpdateMonitor, dozeTransitionListener, authController, dreamOverlayCallbackController);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public KeyguardRepositoryImpl m2987get() {
        return newInstance((StatusBarStateController) this.statusBarStateControllerProvider.get(), (DozeHost) this.dozeHostProvider.get(), (WakefulnessLifecycle) this.wakefulnessLifecycleProvider.get(), (BiometricUnlockController) this.biometricUnlockControllerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get(), (DozeTransitionListener) this.dozeTransitionListenerProvider.get(), (AuthController) this.authControllerProvider.get(), (DreamOverlayCallbackController) this.dreamOverlayCallbackControllerProvider.get());
    }
}