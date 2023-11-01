package com.android.systemui.controls.management;

import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.settings.UserTracker;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ControlsRequestDialog_Factory.class */
public final class ControlsRequestDialog_Factory implements Factory<ControlsRequestDialog> {
    public final Provider<ControlsController> controllerProvider;
    public final Provider<ControlsListingController> controlsListingControllerProvider;
    public final Provider<Executor> mainExecutorProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public ControlsRequestDialog_Factory(Provider<Executor> provider, Provider<ControlsController> provider2, Provider<UserTracker> provider3, Provider<ControlsListingController> provider4) {
        this.mainExecutorProvider = provider;
        this.controllerProvider = provider2;
        this.userTrackerProvider = provider3;
        this.controlsListingControllerProvider = provider4;
    }

    public static ControlsRequestDialog_Factory create(Provider<Executor> provider, Provider<ControlsController> provider2, Provider<UserTracker> provider3, Provider<ControlsListingController> provider4) {
        return new ControlsRequestDialog_Factory(provider, provider2, provider3, provider4);
    }

    public static ControlsRequestDialog newInstance(Executor executor, ControlsController controlsController, UserTracker userTracker, ControlsListingController controlsListingController) {
        return new ControlsRequestDialog(executor, controlsController, userTracker, controlsListingController);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ControlsRequestDialog m1839get() {
        return newInstance((Executor) this.mainExecutorProvider.get(), (ControlsController) this.controllerProvider.get(), (UserTracker) this.userTrackerProvider.get(), (ControlsListingController) this.controlsListingControllerProvider.get());
    }
}