package com.android.systemui.broadcast;

import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/broadcast/BroadcastDispatcherStartable_Factory.class */
public final class BroadcastDispatcherStartable_Factory implements Factory<BroadcastDispatcherStartable> {
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;

    public BroadcastDispatcherStartable_Factory(Provider<BroadcastDispatcher> provider) {
        this.broadcastDispatcherProvider = provider;
    }

    public static BroadcastDispatcherStartable_Factory create(Provider<BroadcastDispatcher> provider) {
        return new BroadcastDispatcherStartable_Factory(provider);
    }

    public static BroadcastDispatcherStartable newInstance(BroadcastDispatcher broadcastDispatcher) {
        return new BroadcastDispatcherStartable(broadcastDispatcher);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public BroadcastDispatcherStartable m1635get() {
        return newInstance((BroadcastDispatcher) this.broadcastDispatcherProvider.get());
    }
}