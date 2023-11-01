package com.android.systemui.controls.management;

import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.ui.ControlsUiController;
import com.android.systemui.settings.UserTracker;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ControlsFavoritingActivity_Factory.class */
public final class ControlsFavoritingActivity_Factory implements Factory<ControlsFavoritingActivity> {
    public final Provider<ControlsControllerImpl> controllerProvider;
    public final Provider<Executor> executorProvider;
    public final Provider<ControlsListingController> listingControllerProvider;
    public final Provider<ControlsUiController> uiControllerProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public ControlsFavoritingActivity_Factory(Provider<Executor> provider, Provider<ControlsControllerImpl> provider2, Provider<ControlsListingController> provider3, Provider<UserTracker> provider4, Provider<ControlsUiController> provider5) {
        this.executorProvider = provider;
        this.controllerProvider = provider2;
        this.listingControllerProvider = provider3;
        this.userTrackerProvider = provider4;
        this.uiControllerProvider = provider5;
    }

    public static ControlsFavoritingActivity_Factory create(Provider<Executor> provider, Provider<ControlsControllerImpl> provider2, Provider<ControlsListingController> provider3, Provider<UserTracker> provider4, Provider<ControlsUiController> provider5) {
        return new ControlsFavoritingActivity_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static ControlsFavoritingActivity newInstance(Executor executor, ControlsControllerImpl controlsControllerImpl, ControlsListingController controlsListingController, UserTracker userTracker, ControlsUiController controlsUiController) {
        return new ControlsFavoritingActivity(executor, controlsControllerImpl, controlsListingController, userTracker, controlsUiController);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ControlsFavoritingActivity m1832get() {
        return newInstance((Executor) this.executorProvider.get(), (ControlsControllerImpl) this.controllerProvider.get(), (ControlsListingController) this.listingControllerProvider.get(), (UserTracker) this.userTrackerProvider.get(), (ControlsUiController) this.uiControllerProvider.get());
    }
}