package com.android.systemui.flags;

import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/FeatureFlagsDebugStartable_Factory.class */
public final class FeatureFlagsDebugStartable_Factory implements Factory<FeatureFlagsDebugStartable> {
    public final Provider<BroadcastSender> broadcastSenderProvider;
    public final Provider<CommandRegistry> commandRegistryProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<FeatureFlagsDebug> featureFlagsProvider;
    public final Provider<FlagCommand> flagCommandProvider;

    public FeatureFlagsDebugStartable_Factory(Provider<DumpManager> provider, Provider<CommandRegistry> provider2, Provider<FlagCommand> provider3, Provider<FeatureFlagsDebug> provider4, Provider<BroadcastSender> provider5) {
        this.dumpManagerProvider = provider;
        this.commandRegistryProvider = provider2;
        this.flagCommandProvider = provider3;
        this.featureFlagsProvider = provider4;
        this.broadcastSenderProvider = provider5;
    }

    public static FeatureFlagsDebugStartable_Factory create(Provider<DumpManager> provider, Provider<CommandRegistry> provider2, Provider<FlagCommand> provider3, Provider<FeatureFlagsDebug> provider4, Provider<BroadcastSender> provider5) {
        return new FeatureFlagsDebugStartable_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static FeatureFlagsDebugStartable newInstance(DumpManager dumpManager, CommandRegistry commandRegistry, FlagCommand flagCommand, FeatureFlagsDebug featureFlagsDebug, BroadcastSender broadcastSender) {
        return new FeatureFlagsDebugStartable(dumpManager, commandRegistry, flagCommand, featureFlagsDebug, broadcastSender);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public FeatureFlagsDebugStartable m2684get() {
        return newInstance((DumpManager) this.dumpManagerProvider.get(), (CommandRegistry) this.commandRegistryProvider.get(), (FlagCommand) this.flagCommandProvider.get(), (FeatureFlagsDebug) this.featureFlagsProvider.get(), (BroadcastSender) this.broadcastSenderProvider.get());
    }
}