package com.android.systemui.broadcast;

import com.android.systemui.broadcast.logging.BroadcastDispatcherLogger;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/broadcast/PendingRemovalStore_Factory.class */
public final class PendingRemovalStore_Factory implements Factory<PendingRemovalStore> {
    public final Provider<BroadcastDispatcherLogger> loggerProvider;

    public PendingRemovalStore_Factory(Provider<BroadcastDispatcherLogger> provider) {
        this.loggerProvider = provider;
    }

    public static PendingRemovalStore_Factory create(Provider<BroadcastDispatcherLogger> provider) {
        return new PendingRemovalStore_Factory(provider);
    }

    public static PendingRemovalStore newInstance(BroadcastDispatcherLogger broadcastDispatcherLogger) {
        return new PendingRemovalStore(broadcastDispatcherLogger);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public PendingRemovalStore m1642get() {
        return newInstance((BroadcastDispatcherLogger) this.loggerProvider.get());
    }
}