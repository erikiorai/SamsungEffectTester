package com.android.systemui.controls.management;

import com.android.systemui.controls.CustomIconCache;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.ui.ControlsUiController;
import com.android.systemui.settings.UserTracker;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/management/ControlsEditingActivity_Factory.class */
public final class ControlsEditingActivity_Factory implements Factory<ControlsEditingActivity> {
    public final Provider<ControlsControllerImpl> controllerProvider;
    public final Provider<CustomIconCache> customIconCacheProvider;
    public final Provider<Executor> mainExecutorProvider;
    public final Provider<ControlsUiController> uiControllerProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public ControlsEditingActivity_Factory(Provider<Executor> provider, Provider<ControlsControllerImpl> provider2, Provider<UserTracker> provider3, Provider<CustomIconCache> provider4, Provider<ControlsUiController> provider5) {
        this.mainExecutorProvider = provider;
        this.controllerProvider = provider2;
        this.userTrackerProvider = provider3;
        this.customIconCacheProvider = provider4;
        this.uiControllerProvider = provider5;
    }

    public static ControlsEditingActivity_Factory create(Provider<Executor> provider, Provider<ControlsControllerImpl> provider2, Provider<UserTracker> provider3, Provider<CustomIconCache> provider4, Provider<ControlsUiController> provider5) {
        return new ControlsEditingActivity_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static ControlsEditingActivity newInstance(Executor executor, ControlsControllerImpl controlsControllerImpl, UserTracker userTracker, CustomIconCache customIconCache, ControlsUiController controlsUiController) {
        return new ControlsEditingActivity(executor, controlsControllerImpl, userTracker, customIconCache, controlsUiController);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ControlsEditingActivity m1830get() {
        return newInstance((Executor) this.mainExecutorProvider.get(), (ControlsControllerImpl) this.controllerProvider.get(), (UserTracker) this.userTrackerProvider.get(), (CustomIconCache) this.customIconCacheProvider.get(), (ControlsUiController) this.uiControllerProvider.get());
    }
}