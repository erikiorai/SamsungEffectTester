package com.android.systemui.qs;

import android.content.Context;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.plugins.qs.QSFactory;
import com.android.systemui.qs.external.CustomTileStatePersister;
import com.android.systemui.qs.external.TileLifecycleManager;
import com.android.systemui.qs.external.TileServiceRequestController;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.settings.UserFileManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.phone.AutoTileManager;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.Factory;
import java.util.Optional;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/QSTileHost_Factory.class */
public final class QSTileHost_Factory implements Factory<QSTileHost> {
    public final Provider<AutoTileManager> autoTilesProvider;
    public final Provider<Optional<CentralSurfaces>> centralSurfacesOptionalProvider;
    public final Provider<Context> contextProvider;
    public final Provider<CustomTileStatePersister> customTileStatePersisterProvider;
    public final Provider<QSFactory> defaultFactoryProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<StatusBarIconController> iconControllerProvider;
    public final Provider<Executor> mainExecutorProvider;
    public final Provider<PluginManager> pluginManagerProvider;
    public final Provider<QSLogger> qsLoggerProvider;
    public final Provider<SecureSettings> secureSettingsProvider;
    public final Provider<TileLifecycleManager.Factory> tileLifecycleManagerFactoryProvider;
    public final Provider<TileServiceRequestController.Builder> tileServiceRequestControllerBuilderProvider;
    public final Provider<TunerService> tunerServiceProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;
    public final Provider<UserFileManager> userFileManagerProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public QSTileHost_Factory(Provider<Context> provider, Provider<StatusBarIconController> provider2, Provider<QSFactory> provider3, Provider<Executor> provider4, Provider<PluginManager> provider5, Provider<TunerService> provider6, Provider<AutoTileManager> provider7, Provider<DumpManager> provider8, Provider<Optional<CentralSurfaces>> provider9, Provider<QSLogger> provider10, Provider<UiEventLogger> provider11, Provider<UserTracker> provider12, Provider<SecureSettings> provider13, Provider<CustomTileStatePersister> provider14, Provider<TileServiceRequestController.Builder> provider15, Provider<TileLifecycleManager.Factory> provider16, Provider<UserFileManager> provider17) {
        this.contextProvider = provider;
        this.iconControllerProvider = provider2;
        this.defaultFactoryProvider = provider3;
        this.mainExecutorProvider = provider4;
        this.pluginManagerProvider = provider5;
        this.tunerServiceProvider = provider6;
        this.autoTilesProvider = provider7;
        this.dumpManagerProvider = provider8;
        this.centralSurfacesOptionalProvider = provider9;
        this.qsLoggerProvider = provider10;
        this.uiEventLoggerProvider = provider11;
        this.userTrackerProvider = provider12;
        this.secureSettingsProvider = provider13;
        this.customTileStatePersisterProvider = provider14;
        this.tileServiceRequestControllerBuilderProvider = provider15;
        this.tileLifecycleManagerFactoryProvider = provider16;
        this.userFileManagerProvider = provider17;
    }

    public static QSTileHost_Factory create(Provider<Context> provider, Provider<StatusBarIconController> provider2, Provider<QSFactory> provider3, Provider<Executor> provider4, Provider<PluginManager> provider5, Provider<TunerService> provider6, Provider<AutoTileManager> provider7, Provider<DumpManager> provider8, Provider<Optional<CentralSurfaces>> provider9, Provider<QSLogger> provider10, Provider<UiEventLogger> provider11, Provider<UserTracker> provider12, Provider<SecureSettings> provider13, Provider<CustomTileStatePersister> provider14, Provider<TileServiceRequestController.Builder> provider15, Provider<TileLifecycleManager.Factory> provider16, Provider<UserFileManager> provider17) {
        return new QSTileHost_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14, provider15, provider16, provider17);
    }

    public static QSTileHost newInstance(Context context, StatusBarIconController statusBarIconController, QSFactory qSFactory, Executor executor, PluginManager pluginManager, TunerService tunerService, Provider<AutoTileManager> provider, DumpManager dumpManager, Optional<CentralSurfaces> optional, QSLogger qSLogger, UiEventLogger uiEventLogger, UserTracker userTracker, SecureSettings secureSettings, CustomTileStatePersister customTileStatePersister, TileServiceRequestController.Builder builder, TileLifecycleManager.Factory factory, UserFileManager userFileManager) {
        return new QSTileHost(context, statusBarIconController, qSFactory, executor, pluginManager, tunerService, provider, dumpManager, optional, qSLogger, uiEventLogger, userTracker, secureSettings, customTileStatePersister, builder, factory, userFileManager);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public QSTileHost m3797get() {
        return newInstance((Context) this.contextProvider.get(), (StatusBarIconController) this.iconControllerProvider.get(), (QSFactory) this.defaultFactoryProvider.get(), (Executor) this.mainExecutorProvider.get(), (PluginManager) this.pluginManagerProvider.get(), (TunerService) this.tunerServiceProvider.get(), this.autoTilesProvider, (DumpManager) this.dumpManagerProvider.get(), (Optional) this.centralSurfacesOptionalProvider.get(), (QSLogger) this.qsLoggerProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get(), (UserTracker) this.userTrackerProvider.get(), (SecureSettings) this.secureSettingsProvider.get(), (CustomTileStatePersister) this.customTileStatePersisterProvider.get(), (TileServiceRequestController.Builder) this.tileServiceRequestControllerBuilderProvider.get(), (TileLifecycleManager.Factory) this.tileLifecycleManagerFactoryProvider.get(), (UserFileManager) this.userFileManagerProvider.get());
    }
}