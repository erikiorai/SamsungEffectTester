package com.android.systemui.flags;

import android.content.Intent;
import com.android.systemui.CoreStartable;
import com.android.systemui.Dumpable;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.commandline.Command;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import java.io.PrintWriter;
import kotlin.jvm.functions.Function0;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/FeatureFlagsDebugStartable.class */
public final class FeatureFlagsDebugStartable implements CoreStartable {
    public final BroadcastSender broadcastSender;
    public final CommandRegistry commandRegistry;
    public final FeatureFlagsDebug featureFlags;
    public final FlagCommand flagCommand;

    public FeatureFlagsDebugStartable(DumpManager dumpManager, CommandRegistry commandRegistry, FlagCommand flagCommand, FeatureFlagsDebug featureFlagsDebug, BroadcastSender broadcastSender) {
        this.commandRegistry = commandRegistry;
        this.flagCommand = flagCommand;
        this.featureFlags = featureFlagsDebug;
        this.broadcastSender = broadcastSender;
        dumpManager.registerCriticalDumpable("SysUIFlags", new Dumpable() { // from class: com.android.systemui.flags.FeatureFlagsDebugStartable.1
            {
                FeatureFlagsDebugStartable.this = this;
            }

            @Override // com.android.systemui.Dumpable
            public final void dump(PrintWriter printWriter, String[] strArr) {
                FeatureFlagsDebugStartable.this.featureFlags.dump(printWriter, strArr);
            }
        });
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.flags.FeatureFlagsDebugStartable$start$1.invoke():com.android.systemui.statusbar.commandline.Command] */
    public static final /* synthetic */ FlagCommand access$getFlagCommand$p(FeatureFlagsDebugStartable featureFlagsDebugStartable) {
        return featureFlagsDebugStartable.flagCommand;
    }

    @Override // com.android.systemui.CoreStartable
    public void start() {
        this.featureFlags.init();
        this.commandRegistry.registerCommand("flag", new Function0<Command>() { // from class: com.android.systemui.flags.FeatureFlagsDebugStartable$start$1
            {
                super(0);
            }

            /* JADX DEBUG: Method merged with bridge method */
            /* renamed from: invoke */
            public final Command m2683invoke() {
                return FeatureFlagsDebugStartable.access$getFlagCommand$p(FeatureFlagsDebugStartable.this);
            }
        });
        this.broadcastSender.sendBroadcast(new Intent("com.android.systemui.STARTED"));
    }
}