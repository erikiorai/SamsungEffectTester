package com.android.systemui.controls.controller;

import android.content.Context;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.ui.ControlsUiController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.internal.Factory;
import java.util.Optional;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/controller/ControlsControllerImpl_Factory.class */
public final class ControlsControllerImpl_Factory implements Factory<ControlsControllerImpl> {
    public final Provider<ControlsBindingController> bindingControllerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<DelayableExecutor> executorProvider;
    public final Provider<ControlsListingController> listingControllerProvider;
    public final Provider<Optional<ControlsFavoritePersistenceWrapper>> optionalWrapperProvider;
    public final Provider<ControlsUiController> uiControllerProvider;
    public final Provider<UserFileManager> userFileManagerProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public ControlsControllerImpl_Factory(Provider<Context> provider, Provider<DelayableExecutor> provider2, Provider<ControlsUiController> provider3, Provider<ControlsBindingController> provider4, Provider<ControlsListingController> provider5, Provider<UserFileManager> provider6, Provider<UserTracker> provider7, Provider<Optional<ControlsFavoritePersistenceWrapper>> provider8, Provider<DumpManager> provider9) {
        this.contextProvider = provider;
        this.executorProvider = provider2;
        this.uiControllerProvider = provider3;
        this.bindingControllerProvider = provider4;
        this.listingControllerProvider = provider5;
        this.userFileManagerProvider = provider6;
        this.userTrackerProvider = provider7;
        this.optionalWrapperProvider = provider8;
        this.dumpManagerProvider = provider9;
    }

    public static ControlsControllerImpl_Factory create(Provider<Context> provider, Provider<DelayableExecutor> provider2, Provider<ControlsUiController> provider3, Provider<ControlsBindingController> provider4, Provider<ControlsListingController> provider5, Provider<UserFileManager> provider6, Provider<UserTracker> provider7, Provider<Optional<ControlsFavoritePersistenceWrapper>> provider8, Provider<DumpManager> provider9) {
        return new ControlsControllerImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9);
    }

    public static ControlsControllerImpl newInstance(Context context, DelayableExecutor delayableExecutor, ControlsUiController controlsUiController, ControlsBindingController controlsBindingController, ControlsListingController controlsListingController, UserFileManager userFileManager, UserTracker userTracker, Optional<ControlsFavoritePersistenceWrapper> optional, DumpManager dumpManager) {
        return new ControlsControllerImpl(context, delayableExecutor, controlsUiController, controlsBindingController, controlsListingController, userFileManager, userTracker, optional, dumpManager);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ControlsControllerImpl m1805get() {
        return newInstance((Context) this.contextProvider.get(), (DelayableExecutor) this.executorProvider.get(), (ControlsUiController) this.uiControllerProvider.get(), (ControlsBindingController) this.bindingControllerProvider.get(), (ControlsListingController) this.listingControllerProvider.get(), (UserFileManager) this.userFileManagerProvider.get(), (UserTracker) this.userTrackerProvider.get(), (Optional) this.optionalWrapperProvider.get(), (DumpManager) this.dumpManagerProvider.get());
    }
}