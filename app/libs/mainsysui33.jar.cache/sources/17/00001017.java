package com.android.systemui;

import android.os.Handler;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpHandler;
import com.android.systemui.dump.LogBufferFreezer;
import com.android.systemui.statusbar.policy.BatteryStateNotifier;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/SystemUIService_Factory.class */
public final class SystemUIService_Factory implements Factory<SystemUIService> {
    public final Provider<BatteryStateNotifier> batteryStateNotifierProvider;
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<DumpHandler> dumpHandlerProvider;
    public final Provider<LogBufferFreezer> logBufferFreezerProvider;
    public final Provider<Handler> mainHandlerProvider;

    public SystemUIService_Factory(Provider<Handler> provider, Provider<DumpHandler> provider2, Provider<BroadcastDispatcher> provider3, Provider<LogBufferFreezer> provider4, Provider<BatteryStateNotifier> provider5) {
        this.mainHandlerProvider = provider;
        this.dumpHandlerProvider = provider2;
        this.broadcastDispatcherProvider = provider3;
        this.logBufferFreezerProvider = provider4;
        this.batteryStateNotifierProvider = provider5;
    }

    public static SystemUIService_Factory create(Provider<Handler> provider, Provider<DumpHandler> provider2, Provider<BroadcastDispatcher> provider3, Provider<LogBufferFreezer> provider4, Provider<BatteryStateNotifier> provider5) {
        return new SystemUIService_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static SystemUIService newInstance(Handler handler, DumpHandler dumpHandler, BroadcastDispatcher broadcastDispatcher, LogBufferFreezer logBufferFreezer, BatteryStateNotifier batteryStateNotifier) {
        return new SystemUIService(handler, dumpHandler, broadcastDispatcher, logBufferFreezer, batteryStateNotifier);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public SystemUIService m1318get() {
        return newInstance((Handler) this.mainHandlerProvider.get(), (DumpHandler) this.dumpHandlerProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (LogBufferFreezer) this.logBufferFreezerProvider.get(), (BatteryStateNotifier) this.batteryStateNotifierProvider.get());
    }
}