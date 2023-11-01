package com.android.keyguard;

import android.content.res.Resources;
import android.view.accessibility.AccessibilityManager;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.AuthRippleController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/keyguard/LockIconViewController_Factory.class */
public final class LockIconViewController_Factory implements Factory<LockIconViewController> {
    public final Provider<AccessibilityManager> accessibilityManagerProvider;
    public final Provider<AuthController> authControllerProvider;
    public final Provider<AuthRippleController> authRippleControllerProvider;
    public final Provider<ConfigurationController> configurationControllerProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<DelayableExecutor> executorProvider;
    public final Provider<FalsingManager> falsingManagerProvider;
    public final Provider<FeatureFlags> featureFlagsProvider;
    public final Provider<KeyguardInteractor> keyguardInteractorProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;
    public final Provider<KeyguardViewController> keyguardViewControllerProvider;
    public final Provider<Resources> resourcesProvider;
    public final Provider<StatusBarStateController> statusBarStateControllerProvider;
    public final Provider<KeyguardTransitionInteractor> transitionInteractorProvider;
    public final Provider<VibratorHelper> vibratorProvider;
    public final Provider<LockIconView> viewProvider;

    public LockIconViewController_Factory(Provider<LockIconView> provider, Provider<StatusBarStateController> provider2, Provider<KeyguardUpdateMonitor> provider3, Provider<KeyguardViewController> provider4, Provider<KeyguardStateController> provider5, Provider<FalsingManager> provider6, Provider<AuthController> provider7, Provider<DumpManager> provider8, Provider<AccessibilityManager> provider9, Provider<ConfigurationController> provider10, Provider<DelayableExecutor> provider11, Provider<VibratorHelper> provider12, Provider<AuthRippleController> provider13, Provider<Resources> provider14, Provider<KeyguardTransitionInteractor> provider15, Provider<KeyguardInteractor> provider16, Provider<FeatureFlags> provider17) {
        this.viewProvider = provider;
        this.statusBarStateControllerProvider = provider2;
        this.keyguardUpdateMonitorProvider = provider3;
        this.keyguardViewControllerProvider = provider4;
        this.keyguardStateControllerProvider = provider5;
        this.falsingManagerProvider = provider6;
        this.authControllerProvider = provider7;
        this.dumpManagerProvider = provider8;
        this.accessibilityManagerProvider = provider9;
        this.configurationControllerProvider = provider10;
        this.executorProvider = provider11;
        this.vibratorProvider = provider12;
        this.authRippleControllerProvider = provider13;
        this.resourcesProvider = provider14;
        this.transitionInteractorProvider = provider15;
        this.keyguardInteractorProvider = provider16;
        this.featureFlagsProvider = provider17;
    }

    public static LockIconViewController_Factory create(Provider<LockIconView> provider, Provider<StatusBarStateController> provider2, Provider<KeyguardUpdateMonitor> provider3, Provider<KeyguardViewController> provider4, Provider<KeyguardStateController> provider5, Provider<FalsingManager> provider6, Provider<AuthController> provider7, Provider<DumpManager> provider8, Provider<AccessibilityManager> provider9, Provider<ConfigurationController> provider10, Provider<DelayableExecutor> provider11, Provider<VibratorHelper> provider12, Provider<AuthRippleController> provider13, Provider<Resources> provider14, Provider<KeyguardTransitionInteractor> provider15, Provider<KeyguardInteractor> provider16, Provider<FeatureFlags> provider17) {
        return new LockIconViewController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17);
    }

    public static LockIconViewController newInstance(LockIconView lockIconView, StatusBarStateController statusBarStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardViewController keyguardViewController, KeyguardStateController keyguardStateController, FalsingManager falsingManager, AuthController authController, DumpManager dumpManager, AccessibilityManager accessibilityManager, ConfigurationController configurationController, DelayableExecutor delayableExecutor, VibratorHelper vibratorHelper, AuthRippleController authRippleController, Resources resources, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardInteractor keyguardInteractor, FeatureFlags featureFlags) {
        return new LockIconViewController(lockIconView, statusBarStateController, keyguardUpdateMonitor, keyguardViewController, keyguardStateController, falsingManager, authController, dumpManager, accessibilityManager, configurationController, delayableExecutor, vibratorHelper, authRippleController, resources, keyguardTransitionInteractor, keyguardInteractor, featureFlags);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LockIconViewController m804get() {
        return newInstance((LockIconView) this.viewProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get(), (KeyguardViewController) this.keyguardViewControllerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (FalsingManager) this.falsingManagerProvider.get(), (AuthController) this.authControllerProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (AccessibilityManager) this.accessibilityManagerProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (DelayableExecutor) this.executorProvider.get(), (VibratorHelper) this.vibratorProvider.get(), (AuthRippleController) this.authRippleControllerProvider.get(), (Resources) this.resourcesProvider.get(), (KeyguardTransitionInteractor) this.transitionInteractorProvider.get(), (KeyguardInteractor) this.keyguardInteractorProvider.get(), (FeatureFlags) this.featureFlagsProvider.get());
    }
}