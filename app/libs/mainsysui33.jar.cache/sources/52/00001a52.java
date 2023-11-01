package com.android.systemui.keyguard.domain.interactor;

import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.data.quickaffordance.KeyguardQuickAffordanceConfig;
import com.android.systemui.keyguard.data.repository.KeyguardQuickAffordanceRepository;
import com.android.systemui.keyguard.domain.quickaffordance.KeyguardQuickAffordanceRegistry;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/domain/interactor/KeyguardQuickAffordanceInteractor_Factory.class */
public final class KeyguardQuickAffordanceInteractor_Factory implements Factory<KeyguardQuickAffordanceInteractor> {
    public final Provider<ActivityStarter> activityStarterProvider;
    public final Provider<FeatureFlags> featureFlagsProvider;
    public final Provider<KeyguardInteractor> keyguardInteractorProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<DialogLaunchAnimator> launchAnimatorProvider;
    public final Provider<LockPatternUtils> lockPatternUtilsProvider;
    public final Provider<KeyguardQuickAffordanceRegistry<? extends KeyguardQuickAffordanceConfig>> registryProvider;
    public final Provider<KeyguardQuickAffordanceRepository> repositoryProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public KeyguardQuickAffordanceInteractor_Factory(Provider<KeyguardInteractor> provider, Provider<KeyguardQuickAffordanceRegistry<? extends KeyguardQuickAffordanceConfig>> provider2, Provider<LockPatternUtils> provider3, Provider<KeyguardStateController> provider4, Provider<UserTracker> provider5, Provider<ActivityStarter> provider6, Provider<FeatureFlags> provider7, Provider<KeyguardQuickAffordanceRepository> provider8, Provider<DialogLaunchAnimator> provider9) {
        this.keyguardInteractorProvider = provider;
        this.registryProvider = provider2;
        this.lockPatternUtilsProvider = provider3;
        this.keyguardStateControllerProvider = provider4;
        this.userTrackerProvider = provider5;
        this.activityStarterProvider = provider6;
        this.featureFlagsProvider = provider7;
        this.repositoryProvider = provider8;
        this.launchAnimatorProvider = provider9;
    }

    public static KeyguardQuickAffordanceInteractor_Factory create(Provider<KeyguardInteractor> provider, Provider<KeyguardQuickAffordanceRegistry<? extends KeyguardQuickAffordanceConfig>> provider2, Provider<LockPatternUtils> provider3, Provider<KeyguardStateController> provider4, Provider<UserTracker> provider5, Provider<ActivityStarter> provider6, Provider<FeatureFlags> provider7, Provider<KeyguardQuickAffordanceRepository> provider8, Provider<DialogLaunchAnimator> provider9) {
        return new KeyguardQuickAffordanceInteractor_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9);
    }

    public static KeyguardQuickAffordanceInteractor newInstance(KeyguardInteractor keyguardInteractor, KeyguardQuickAffordanceRegistry<? extends KeyguardQuickAffordanceConfig> keyguardQuickAffordanceRegistry, LockPatternUtils lockPatternUtils, KeyguardStateController keyguardStateController, UserTracker userTracker, ActivityStarter activityStarter, FeatureFlags featureFlags, Lazy<KeyguardQuickAffordanceRepository> lazy, DialogLaunchAnimator dialogLaunchAnimator) {
        return new KeyguardQuickAffordanceInteractor(keyguardInteractor, keyguardQuickAffordanceRegistry, lockPatternUtils, keyguardStateController, userTracker, activityStarter, featureFlags, lazy, dialogLaunchAnimator);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public KeyguardQuickAffordanceInteractor m3043get() {
        return newInstance((KeyguardInteractor) this.keyguardInteractorProvider.get(), (KeyguardQuickAffordanceRegistry) this.registryProvider.get(), (LockPatternUtils) this.lockPatternUtilsProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (UserTracker) this.userTrackerProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (FeatureFlags) this.featureFlagsProvider.get(), DoubleCheck.lazy(this.repositoryProvider), (DialogLaunchAnimator) this.launchAnimatorProvider.get());
    }
}