package com.android.systemui.controls.ui;

import android.content.Context;
import com.android.systemui.controls.ControlsMetricsLogger;
import com.android.systemui.controls.CustomIconCache;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.settings.ControlsSettingsRepository;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.wm.shell.TaskViewFactory;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import java.util.Optional;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/ControlsUiControllerImpl_Factory.class */
public final class ControlsUiControllerImpl_Factory implements Factory<ControlsUiControllerImpl> {
    public final Provider<ActivityStarter> activityStarterProvider;
    public final Provider<DelayableExecutor> bgExecutorProvider;
    public final Provider<Context> contextProvider;
    public final Provider<ControlActionCoordinator> controlActionCoordinatorProvider;
    public final Provider<ControlsController> controlsControllerProvider;
    public final Provider<ControlsListingController> controlsListingControllerProvider;
    public final Provider<ControlsMetricsLogger> controlsMetricsLoggerProvider;
    public final Provider<ControlsSettingsRepository> controlsSettingsRepositoryProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<CustomIconCache> iconCacheProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<Optional<TaskViewFactory>> taskViewFactoryProvider;
    public final Provider<DelayableExecutor> uiExecutorProvider;
    public final Provider<UserFileManager> userFileManagerProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public ControlsUiControllerImpl_Factory(Provider<ControlsController> provider, Provider<Context> provider2, Provider<DelayableExecutor> provider3, Provider<DelayableExecutor> provider4, Provider<ControlsListingController> provider5, Provider<ControlActionCoordinator> provider6, Provider<ActivityStarter> provider7, Provider<CustomIconCache> provider8, Provider<ControlsMetricsLogger> provider9, Provider<KeyguardStateController> provider10, Provider<UserFileManager> provider11, Provider<UserTracker> provider12, Provider<Optional<TaskViewFactory>> provider13, Provider<ControlsSettingsRepository> provider14, Provider<DumpManager> provider15) {
        this.controlsControllerProvider = provider;
        this.contextProvider = provider2;
        this.uiExecutorProvider = provider3;
        this.bgExecutorProvider = provider4;
        this.controlsListingControllerProvider = provider5;
        this.controlActionCoordinatorProvider = provider6;
        this.activityStarterProvider = provider7;
        this.iconCacheProvider = provider8;
        this.controlsMetricsLoggerProvider = provider9;
        this.keyguardStateControllerProvider = provider10;
        this.userFileManagerProvider = provider11;
        this.userTrackerProvider = provider12;
        this.taskViewFactoryProvider = provider13;
        this.controlsSettingsRepositoryProvider = provider14;
        this.dumpManagerProvider = provider15;
    }

    public static ControlsUiControllerImpl_Factory create(Provider<ControlsController> provider, Provider<Context> provider2, Provider<DelayableExecutor> provider3, Provider<DelayableExecutor> provider4, Provider<ControlsListingController> provider5, Provider<ControlActionCoordinator> provider6, Provider<ActivityStarter> provider7, Provider<CustomIconCache> provider8, Provider<ControlsMetricsLogger> provider9, Provider<KeyguardStateController> provider10, Provider<UserFileManager> provider11, Provider<UserTracker> provider12, Provider<Optional<TaskViewFactory>> provider13, Provider<ControlsSettingsRepository> provider14, Provider<DumpManager> provider15) {
        return new ControlsUiControllerImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15);
    }

    public static ControlsUiControllerImpl newInstance(Lazy<ControlsController> lazy, Context context, DelayableExecutor delayableExecutor, DelayableExecutor delayableExecutor2, Lazy<ControlsListingController> lazy2, ControlActionCoordinator controlActionCoordinator, ActivityStarter activityStarter, CustomIconCache customIconCache, ControlsMetricsLogger controlsMetricsLogger, KeyguardStateController keyguardStateController, UserFileManager userFileManager, UserTracker userTracker, Optional<TaskViewFactory> optional, ControlsSettingsRepository controlsSettingsRepository, DumpManager dumpManager) {
        return new ControlsUiControllerImpl(lazy, context, delayableExecutor, delayableExecutor2, lazy2, controlActionCoordinator, activityStarter, customIconCache, controlsMetricsLogger, keyguardStateController, userFileManager, userTracker, optional, controlsSettingsRepository, dumpManager);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ControlsUiControllerImpl m1875get() {
        return newInstance(DoubleCheck.lazy(this.controlsControllerProvider), (Context) this.contextProvider.get(), (DelayableExecutor) this.uiExecutorProvider.get(), (DelayableExecutor) this.bgExecutorProvider.get(), DoubleCheck.lazy(this.controlsListingControllerProvider), (ControlActionCoordinator) this.controlActionCoordinatorProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (CustomIconCache) this.iconCacheProvider.get(), (ControlsMetricsLogger) this.controlsMetricsLoggerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (UserFileManager) this.userFileManagerProvider.get(), (UserTracker) this.userTrackerProvider.get(), (Optional) this.taskViewFactoryProvider.get(), (ControlsSettingsRepository) this.controlsSettingsRepositoryProvider.get(), (DumpManager) this.dumpManagerProvider.get());
    }
}