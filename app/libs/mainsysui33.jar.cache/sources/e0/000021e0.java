package com.android.systemui.qs.external;

import com.android.systemui.qs.external.TileServiceRequestController;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/external/TileServiceRequestController_Builder_Factory.class */
public final class TileServiceRequestController_Builder_Factory implements Factory<TileServiceRequestController.Builder> {
    public final Provider<CommandQueue> commandQueueProvider;
    public final Provider<CommandRegistry> commandRegistryProvider;

    public TileServiceRequestController_Builder_Factory(Provider<CommandQueue> provider, Provider<CommandRegistry> provider2) {
        this.commandQueueProvider = provider;
        this.commandRegistryProvider = provider2;
    }

    public static TileServiceRequestController_Builder_Factory create(Provider<CommandQueue> provider, Provider<CommandRegistry> provider2) {
        return new TileServiceRequestController_Builder_Factory(provider, provider2);
    }

    public static TileServiceRequestController.Builder newInstance(CommandQueue commandQueue, CommandRegistry commandRegistry) {
        return new TileServiceRequestController.Builder(commandQueue, commandRegistry);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public TileServiceRequestController.Builder m3911get() {
        return newInstance((CommandQueue) this.commandQueueProvider.get(), (CommandRegistry) this.commandRegistryProvider.get());
    }
}