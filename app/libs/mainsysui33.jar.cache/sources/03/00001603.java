package com.android.systemui.demomode.dagger;

import android.content.Context;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.util.settings.GlobalSettings;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/demomode/dagger/DemoModeModule_ProvideDemoModeControllerFactory.class */
public final class DemoModeModule_ProvideDemoModeControllerFactory implements Factory<DemoModeController> {
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<GlobalSettings> globalSettingsProvider;

    public DemoModeModule_ProvideDemoModeControllerFactory(Provider<Context> provider, Provider<DumpManager> provider2, Provider<GlobalSettings> provider3, Provider<BroadcastDispatcher> provider4) {
        this.contextProvider = provider;
        this.dumpManagerProvider = provider2;
        this.globalSettingsProvider = provider3;
        this.broadcastDispatcherProvider = provider4;
    }

    public static DemoModeModule_ProvideDemoModeControllerFactory create(Provider<Context> provider, Provider<DumpManager> provider2, Provider<GlobalSettings> provider3, Provider<BroadcastDispatcher> provider4) {
        return new DemoModeModule_ProvideDemoModeControllerFactory(provider, provider2, provider3, provider4);
    }

    public static DemoModeController provideDemoModeController(Context context, DumpManager dumpManager, GlobalSettings globalSettings, BroadcastDispatcher broadcastDispatcher) {
        return (DemoModeController) Preconditions.checkNotNullFromProvides(DemoModeModule.provideDemoModeController(context, dumpManager, globalSettings, broadcastDispatcher));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DemoModeController m2398get() {
        return provideDemoModeController((Context) this.contextProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (GlobalSettings) this.globalSettingsProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get());
    }
}