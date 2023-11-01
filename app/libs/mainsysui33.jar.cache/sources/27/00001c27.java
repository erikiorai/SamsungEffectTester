package com.android.systemui.media.controls.pipeline;

import android.content.Context;
import android.media.MediaRouter2Manager;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.controls.util.MediaControllerFactory;
import com.android.systemui.media.muteawait.MediaMuteAwaitConnectionManagerFactory;
import com.android.systemui.statusbar.policy.ConfigurationController;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/controls/pipeline/MediaDeviceManager_Factory.class */
public final class MediaDeviceManager_Factory implements Factory<MediaDeviceManager> {
    public final Provider<Executor> bgExecutorProvider;
    public final Provider<ConfigurationController> configurationControllerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<MediaControllerFactory> controllerFactoryProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<Executor> fgExecutorProvider;
    public final Provider<LocalBluetoothManager> localBluetoothManagerProvider;
    public final Provider<LocalMediaManagerFactory> localMediaManagerFactoryProvider;
    public final Provider<MediaRouter2Manager> mr2managerProvider;
    public final Provider<MediaMuteAwaitConnectionManagerFactory> muteAwaitConnectionManagerFactoryProvider;

    public MediaDeviceManager_Factory(Provider<Context> provider, Provider<MediaControllerFactory> provider2, Provider<LocalMediaManagerFactory> provider3, Provider<MediaRouter2Manager> provider4, Provider<MediaMuteAwaitConnectionManagerFactory> provider5, Provider<ConfigurationController> provider6, Provider<LocalBluetoothManager> provider7, Provider<Executor> provider8, Provider<Executor> provider9, Provider<DumpManager> provider10) {
        this.contextProvider = provider;
        this.controllerFactoryProvider = provider2;
        this.localMediaManagerFactoryProvider = provider3;
        this.mr2managerProvider = provider4;
        this.muteAwaitConnectionManagerFactoryProvider = provider5;
        this.configurationControllerProvider = provider6;
        this.localBluetoothManagerProvider = provider7;
        this.fgExecutorProvider = provider8;
        this.bgExecutorProvider = provider9;
        this.dumpManagerProvider = provider10;
    }

    public static MediaDeviceManager_Factory create(Provider<Context> provider, Provider<MediaControllerFactory> provider2, Provider<LocalMediaManagerFactory> provider3, Provider<MediaRouter2Manager> provider4, Provider<MediaMuteAwaitConnectionManagerFactory> provider5, Provider<ConfigurationController> provider6, Provider<LocalBluetoothManager> provider7, Provider<Executor> provider8, Provider<Executor> provider9, Provider<DumpManager> provider10) {
        return new MediaDeviceManager_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10);
    }

    public static MediaDeviceManager newInstance(Context context, MediaControllerFactory mediaControllerFactory, LocalMediaManagerFactory localMediaManagerFactory, MediaRouter2Manager mediaRouter2Manager, MediaMuteAwaitConnectionManagerFactory mediaMuteAwaitConnectionManagerFactory, ConfigurationController configurationController, LocalBluetoothManager localBluetoothManager, Executor executor, Executor executor2, DumpManager dumpManager) {
        return new MediaDeviceManager(context, mediaControllerFactory, localMediaManagerFactory, mediaRouter2Manager, mediaMuteAwaitConnectionManagerFactory, configurationController, localBluetoothManager, executor, executor2, dumpManager);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaDeviceManager m3195get() {
        return newInstance((Context) this.contextProvider.get(), (MediaControllerFactory) this.controllerFactoryProvider.get(), (LocalMediaManagerFactory) this.localMediaManagerFactoryProvider.get(), (MediaRouter2Manager) this.mr2managerProvider.get(), (MediaMuteAwaitConnectionManagerFactory) this.muteAwaitConnectionManagerFactoryProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (LocalBluetoothManager) this.localBluetoothManagerProvider.get(), (Executor) this.fgExecutorProvider.get(), (Executor) this.bgExecutorProvider.get(), (DumpManager) this.dumpManagerProvider.get());
    }
}