package com.android.systemui.qs.tiles.dialog;

import android.net.wifi.WifiManager;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/dialog/WifiStateWorker_Factory.class */
public final class WifiStateWorker_Factory implements Factory<WifiStateWorker> {
    public final Provider<DelayableExecutor> backgroundExecutorProvider;
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<WifiManager> wifiManagerProvider;

    public WifiStateWorker_Factory(Provider<BroadcastDispatcher> provider, Provider<DelayableExecutor> provider2, Provider<WifiManager> provider3) {
        this.broadcastDispatcherProvider = provider;
        this.backgroundExecutorProvider = provider2;
        this.wifiManagerProvider = provider3;
    }

    public static WifiStateWorker_Factory create(Provider<BroadcastDispatcher> provider, Provider<DelayableExecutor> provider2, Provider<WifiManager> provider3) {
        return new WifiStateWorker_Factory(provider, provider2, provider3);
    }

    public static WifiStateWorker newInstance(BroadcastDispatcher broadcastDispatcher, DelayableExecutor delayableExecutor, WifiManager wifiManager) {
        return new WifiStateWorker(broadcastDispatcher, delayableExecutor, wifiManager);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public WifiStateWorker m4092get() {
        return newInstance((BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (DelayableExecutor) this.backgroundExecutorProvider.get(), (WifiManager) this.wifiManagerProvider.get());
    }
}