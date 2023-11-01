package com.android.systemui.media.nearby;

import com.android.systemui.statusbar.CommandQueue;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/media/nearby/NearbyMediaDevicesManager_Factory.class */
public final class NearbyMediaDevicesManager_Factory implements Factory<NearbyMediaDevicesManager> {
    public final Provider<CommandQueue> commandQueueProvider;
    public final Provider<NearbyMediaDevicesLogger> loggerProvider;

    public NearbyMediaDevicesManager_Factory(Provider<CommandQueue> provider, Provider<NearbyMediaDevicesLogger> provider2) {
        this.commandQueueProvider = provider;
        this.loggerProvider = provider2;
    }

    public static NearbyMediaDevicesManager_Factory create(Provider<CommandQueue> provider, Provider<NearbyMediaDevicesLogger> provider2) {
        return new NearbyMediaDevicesManager_Factory(provider, provider2);
    }

    public static NearbyMediaDevicesManager newInstance(CommandQueue commandQueue, NearbyMediaDevicesLogger nearbyMediaDevicesLogger) {
        return new NearbyMediaDevicesManager(commandQueue, nearbyMediaDevicesLogger);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public NearbyMediaDevicesManager m3340get() {
        return newInstance((CommandQueue) this.commandQueueProvider.get(), (NearbyMediaDevicesLogger) this.loggerProvider.get());
    }
}