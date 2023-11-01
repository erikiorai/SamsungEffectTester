package com.android.systemui.power.data.repository;

import android.os.PowerManager;
import com.android.systemui.broadcast.BroadcastDispatcher;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/power/data/repository/PowerRepositoryImpl_Factory.class */
public final class PowerRepositoryImpl_Factory implements Factory<PowerRepositoryImpl> {
    public final Provider<BroadcastDispatcher> dispatcherProvider;
    public final Provider<PowerManager> managerProvider;

    public PowerRepositoryImpl_Factory(Provider<PowerManager> provider, Provider<BroadcastDispatcher> provider2) {
        this.managerProvider = provider;
        this.dispatcherProvider = provider2;
    }

    public static PowerRepositoryImpl_Factory create(Provider<PowerManager> provider, Provider<BroadcastDispatcher> provider2) {
        return new PowerRepositoryImpl_Factory(provider, provider2);
    }

    public static PowerRepositoryImpl newInstance(PowerManager powerManager, BroadcastDispatcher broadcastDispatcher) {
        return new PowerRepositoryImpl(powerManager, broadcastDispatcher);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public PowerRepositoryImpl m3660get() {
        return newInstance((PowerManager) this.managerProvider.get(), (BroadcastDispatcher) this.dispatcherProvider.get());
    }
}