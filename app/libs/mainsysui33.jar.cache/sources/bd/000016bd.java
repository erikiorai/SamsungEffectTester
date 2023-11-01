package com.android.systemui.dreams;

import android.content.res.Resources;
import android.os.Handler;
import android.view.ViewGroup;
import com.android.systemui.dreams.complication.ComplicationHostViewController;
import com.android.systemui.keyguard.domain.interactor.PrimaryBouncerCallbackInteractor;
import com.android.systemui.statusbar.BlurUtils;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/DreamOverlayContainerViewController_Factory.class */
public final class DreamOverlayContainerViewController_Factory implements Factory<DreamOverlayContainerViewController> {
    public final Provider<DreamOverlayAnimationsController> animationsControllerProvider;
    public final Provider<BlurUtils> blurUtilsProvider;
    public final Provider<Long> burnInProtectionUpdateIntervalProvider;
    public final Provider<ComplicationHostViewController> complicationHostViewControllerProvider;
    public final Provider<DreamOverlayContainerView> containerViewProvider;
    public final Provider<ViewGroup> contentViewProvider;
    public final Provider<Handler> handlerProvider;
    public final Provider<Integer> maxBurnInOffsetProvider;
    public final Provider<Long> millisUntilFullJitterProvider;
    public final Provider<PrimaryBouncerCallbackInteractor> primaryBouncerCallbackInteractorProvider;
    public final Provider<Resources> resourcesProvider;
    public final Provider<DreamOverlayStateController> stateControllerProvider;
    public final Provider<StatusBarKeyguardViewManager> statusBarKeyguardViewManagerProvider;
    public final Provider<DreamOverlayStatusBarViewController> statusBarViewControllerProvider;

    public DreamOverlayContainerViewController_Factory(Provider<DreamOverlayContainerView> provider, Provider<ComplicationHostViewController> provider2, Provider<ViewGroup> provider3, Provider<DreamOverlayStatusBarViewController> provider4, Provider<StatusBarKeyguardViewManager> provider5, Provider<BlurUtils> provider6, Provider<Handler> provider7, Provider<Resources> provider8, Provider<Integer> provider9, Provider<Long> provider10, Provider<Long> provider11, Provider<PrimaryBouncerCallbackInteractor> provider12, Provider<DreamOverlayAnimationsController> provider13, Provider<DreamOverlayStateController> provider14) {
        this.containerViewProvider = provider;
        this.complicationHostViewControllerProvider = provider2;
        this.contentViewProvider = provider3;
        this.statusBarViewControllerProvider = provider4;
        this.statusBarKeyguardViewManagerProvider = provider5;
        this.blurUtilsProvider = provider6;
        this.handlerProvider = provider7;
        this.resourcesProvider = provider8;
        this.maxBurnInOffsetProvider = provider9;
        this.burnInProtectionUpdateIntervalProvider = provider10;
        this.millisUntilFullJitterProvider = provider11;
        this.primaryBouncerCallbackInteractorProvider = provider12;
        this.animationsControllerProvider = provider13;
        this.stateControllerProvider = provider14;
    }

    public static DreamOverlayContainerViewController_Factory create(Provider<DreamOverlayContainerView> provider, Provider<ComplicationHostViewController> provider2, Provider<ViewGroup> provider3, Provider<DreamOverlayStatusBarViewController> provider4, Provider<StatusBarKeyguardViewManager> provider5, Provider<BlurUtils> provider6, Provider<Handler> provider7, Provider<Resources> provider8, Provider<Integer> provider9, Provider<Long> provider10, Provider<Long> provider11, Provider<PrimaryBouncerCallbackInteractor> provider12, Provider<DreamOverlayAnimationsController> provider13, Provider<DreamOverlayStateController> provider14) {
        return new DreamOverlayContainerViewController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14);
    }

    public static DreamOverlayContainerViewController newInstance(DreamOverlayContainerView dreamOverlayContainerView, ComplicationHostViewController complicationHostViewController, ViewGroup viewGroup, DreamOverlayStatusBarViewController dreamOverlayStatusBarViewController, StatusBarKeyguardViewManager statusBarKeyguardViewManager, BlurUtils blurUtils, Handler handler, Resources resources, int i, long j, long j2, PrimaryBouncerCallbackInteractor primaryBouncerCallbackInteractor, DreamOverlayAnimationsController dreamOverlayAnimationsController, DreamOverlayStateController dreamOverlayStateController) {
        return new DreamOverlayContainerViewController(dreamOverlayContainerView, complicationHostViewController, viewGroup, dreamOverlayStatusBarViewController, statusBarKeyguardViewManager, blurUtils, handler, resources, i, j, j2, primaryBouncerCallbackInteractor, dreamOverlayAnimationsController, dreamOverlayStateController);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DreamOverlayContainerViewController m2549get() {
        return newInstance((DreamOverlayContainerView) this.containerViewProvider.get(), (ComplicationHostViewController) this.complicationHostViewControllerProvider.get(), (ViewGroup) this.contentViewProvider.get(), (DreamOverlayStatusBarViewController) this.statusBarViewControllerProvider.get(), (StatusBarKeyguardViewManager) this.statusBarKeyguardViewManagerProvider.get(), (BlurUtils) this.blurUtilsProvider.get(), (Handler) this.handlerProvider.get(), (Resources) this.resourcesProvider.get(), ((Integer) this.maxBurnInOffsetProvider.get()).intValue(), ((Long) this.burnInProtectionUpdateIntervalProvider.get()).longValue(), ((Long) this.millisUntilFullJitterProvider.get()).longValue(), (PrimaryBouncerCallbackInteractor) this.primaryBouncerCallbackInteractorProvider.get(), (DreamOverlayAnimationsController) this.animationsControllerProvider.get(), (DreamOverlayStateController) this.stateControllerProvider.get());
    }
}