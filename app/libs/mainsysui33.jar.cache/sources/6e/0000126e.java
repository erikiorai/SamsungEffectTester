package com.android.systemui.broadcast;

import android.content.Context;
import android.os.Looper;
import com.android.systemui.broadcast.logging.BroadcastDispatcherLogger;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.settings.UserTracker;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/broadcast/BroadcastDispatcher_Factory.class */
public final class BroadcastDispatcher_Factory implements Factory<BroadcastDispatcher> {
    public final Provider<Executor> broadcastExecutorProvider;
    public final Provider<Looper> broadcastLooperProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<BroadcastDispatcherLogger> loggerProvider;
    public final Provider<Executor> mainExecutorProvider;
    public final Provider<PendingRemovalStore> removalPendingStoreProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public BroadcastDispatcher_Factory(Provider<Context> provider, Provider<Executor> provider2, Provider<Looper> provider3, Provider<Executor> provider4, Provider<DumpManager> provider5, Provider<BroadcastDispatcherLogger> provider6, Provider<UserTracker> provider7, Provider<PendingRemovalStore> provider8) {
        this.contextProvider = provider;
        this.mainExecutorProvider = provider2;
        this.broadcastLooperProvider = provider3;
        this.broadcastExecutorProvider = provider4;
        this.dumpManagerProvider = provider5;
        this.loggerProvider = provider6;
        this.userTrackerProvider = provider7;
        this.removalPendingStoreProvider = provider8;
    }

    public static BroadcastDispatcher_Factory create(Provider<Context> provider, Provider<Executor> provider2, Provider<Looper> provider3, Provider<Executor> provider4, Provider<DumpManager> provider5, Provider<BroadcastDispatcherLogger> provider6, Provider<UserTracker> provider7, Provider<PendingRemovalStore> provider8) {
        return new BroadcastDispatcher_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8);
    }

    public static BroadcastDispatcher newInstance(Context context, Executor executor, Looper looper, Executor executor2, DumpManager dumpManager, BroadcastDispatcherLogger broadcastDispatcherLogger, UserTracker userTracker, PendingRemovalStore pendingRemovalStore) {
        return new BroadcastDispatcher(context, executor, looper, executor2, dumpManager, broadcastDispatcherLogger, userTracker, pendingRemovalStore);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public BroadcastDispatcher m1636get() {
        return newInstance((Context) this.contextProvider.get(), (Executor) this.mainExecutorProvider.get(), (Looper) this.broadcastLooperProvider.get(), (Executor) this.broadcastExecutorProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (BroadcastDispatcherLogger) this.loggerProvider.get(), (UserTracker) this.userTrackerProvider.get(), (PendingRemovalStore) this.removalPendingStoreProvider.get());
    }
}