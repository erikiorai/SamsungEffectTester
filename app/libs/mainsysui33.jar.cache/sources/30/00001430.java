package com.android.systemui.controls.management;

import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.ui.ControlsUiController;
import com.android.systemui.settings.UserTracker;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ControlsProviderSelectorActivity_Factory.class */
public final class ControlsProviderSelectorActivity_Factory implements Factory<ControlsProviderSelectorActivity> {
    public final Provider<Executor> backExecutorProvider;
    public final Provider<ControlsController> controlsControllerProvider;
    public final Provider<Executor> executorProvider;
    public final Provider<ControlsListingController> listingControllerProvider;
    public final Provider<ControlsUiController> uiControllerProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public ControlsProviderSelectorActivity_Factory(Provider<Executor> provider, Provider<Executor> provider2, Provider<ControlsListingController> provider3, Provider<ControlsController> provider4, Provider<UserTracker> provider5, Provider<ControlsUiController> provider6) {
        this.executorProvider = provider;
        this.backExecutorProvider = provider2;
        this.listingControllerProvider = provider3;
        this.controlsControllerProvider = provider4;
        this.userTrackerProvider = provider5;
        this.uiControllerProvider = provider6;
    }

    public static ControlsProviderSelectorActivity_Factory create(Provider<Executor> provider, Provider<Executor> provider2, Provider<ControlsListingController> provider3, Provider<ControlsController> provider4, Provider<UserTracker> provider5, Provider<ControlsUiController> provider6) {
        return new ControlsProviderSelectorActivity_Factory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    public static ControlsProviderSelectorActivity newInstance(Executor executor, Executor executor2, ControlsListingController controlsListingController, ControlsController controlsController, UserTracker userTracker, ControlsUiController controlsUiController) {
        return new ControlsProviderSelectorActivity(executor, executor2, controlsListingController, controlsController, userTracker, controlsUiController);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ControlsProviderSelectorActivity m1837get() {
        return newInstance((Executor) this.executorProvider.get(), (Executor) this.backExecutorProvider.get(), (ControlsListingController) this.listingControllerProvider.get(), (ControlsController) this.controlsControllerProvider.get(), (UserTracker) this.userTrackerProvider.get(), (ControlsUiController) this.uiControllerProvider.get());
    }
}