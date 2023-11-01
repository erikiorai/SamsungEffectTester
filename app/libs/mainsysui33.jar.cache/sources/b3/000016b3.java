package com.android.systemui.dreams;

import com.android.systemui.dreams.complication.ComplicationHostViewController;
import com.android.systemui.keyguard.ui.viewmodel.DreamingToLockscreenTransitionViewModel;
import com.android.systemui.statusbar.BlurUtils;
import com.android.systemui.statusbar.policy.ConfigurationController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/DreamOverlayAnimationsController_Factory.class */
public final class DreamOverlayAnimationsController_Factory implements Factory<DreamOverlayAnimationsController> {
    public final Provider<ConfigurationController> configControllerProvider;
    public final Provider<BlurUtils> mBlurUtilsProvider;
    public final Provider<ComplicationHostViewController> mComplicationHostViewControllerProvider;
    public final Provider<Integer> mDreamBlurRadiusProvider;
    public final Provider<Long> mDreamInBlurAnimDurationMsProvider;
    public final Provider<Long> mDreamInComplicationsAnimDurationMsProvider;
    public final Provider<Integer> mDreamInTranslationYDistanceProvider;
    public final Provider<Long> mDreamInTranslationYDurationMsProvider;
    public final Provider<DreamOverlayStateController> mOverlayStateControllerProvider;
    public final Provider<DreamOverlayStatusBarViewController> mStatusBarViewControllerProvider;
    public final Provider<DreamingToLockscreenTransitionViewModel> transitionViewModelProvider;

    public DreamOverlayAnimationsController_Factory(Provider<BlurUtils> provider, Provider<ComplicationHostViewController> provider2, Provider<DreamOverlayStatusBarViewController> provider3, Provider<DreamOverlayStateController> provider4, Provider<Integer> provider5, Provider<DreamingToLockscreenTransitionViewModel> provider6, Provider<ConfigurationController> provider7, Provider<Long> provider8, Provider<Long> provider9, Provider<Integer> provider10, Provider<Long> provider11) {
        this.mBlurUtilsProvider = provider;
        this.mComplicationHostViewControllerProvider = provider2;
        this.mStatusBarViewControllerProvider = provider3;
        this.mOverlayStateControllerProvider = provider4;
        this.mDreamBlurRadiusProvider = provider5;
        this.transitionViewModelProvider = provider6;
        this.configControllerProvider = provider7;
        this.mDreamInBlurAnimDurationMsProvider = provider8;
        this.mDreamInComplicationsAnimDurationMsProvider = provider9;
        this.mDreamInTranslationYDistanceProvider = provider10;
        this.mDreamInTranslationYDurationMsProvider = provider11;
    }

    public static DreamOverlayAnimationsController_Factory create(Provider<BlurUtils> provider, Provider<ComplicationHostViewController> provider2, Provider<DreamOverlayStatusBarViewController> provider3, Provider<DreamOverlayStateController> provider4, Provider<Integer> provider5, Provider<DreamingToLockscreenTransitionViewModel> provider6, Provider<ConfigurationController> provider7, Provider<Long> provider8, Provider<Long> provider9, Provider<Integer> provider10, Provider<Long> provider11) {
        return new DreamOverlayAnimationsController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11);
    }

    public static DreamOverlayAnimationsController newInstance(BlurUtils blurUtils, ComplicationHostViewController complicationHostViewController, DreamOverlayStatusBarViewController dreamOverlayStatusBarViewController, DreamOverlayStateController dreamOverlayStateController, int i, DreamingToLockscreenTransitionViewModel dreamingToLockscreenTransitionViewModel, ConfigurationController configurationController, long j, long j2, int i2, long j3) {
        return new DreamOverlayAnimationsController(blurUtils, complicationHostViewController, dreamOverlayStatusBarViewController, dreamOverlayStateController, i, dreamingToLockscreenTransitionViewModel, configurationController, j, j2, i2, j3);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DreamOverlayAnimationsController m2542get() {
        return newInstance((BlurUtils) this.mBlurUtilsProvider.get(), (ComplicationHostViewController) this.mComplicationHostViewControllerProvider.get(), (DreamOverlayStatusBarViewController) this.mStatusBarViewControllerProvider.get(), (DreamOverlayStateController) this.mOverlayStateControllerProvider.get(), ((Integer) this.mDreamBlurRadiusProvider.get()).intValue(), (DreamingToLockscreenTransitionViewModel) this.transitionViewModelProvider.get(), (ConfigurationController) this.configControllerProvider.get(), ((Long) this.mDreamInBlurAnimDurationMsProvider.get()).longValue(), ((Long) this.mDreamInComplicationsAnimDurationMsProvider.get()).longValue(), ((Integer) this.mDreamInTranslationYDistanceProvider.get()).intValue(), ((Long) this.mDreamInTranslationYDurationMsProvider.get()).longValue());
    }
}