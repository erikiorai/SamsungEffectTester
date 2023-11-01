package com.android.systemui.qs.external;

import android.os.Handler;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.qs.QSTileHost;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/external/TileServices_Factory.class */
public final class TileServices_Factory implements Factory<TileServices> {
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<CommandQueue> commandQueueProvider;
    public final Provider<Handler> handlerProvider;
    public final Provider<QSTileHost> hostProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public TileServices_Factory(Provider<QSTileHost> provider, Provider<Handler> provider2, Provider<BroadcastDispatcher> provider3, Provider<UserTracker> provider4, Provider<KeyguardStateController> provider5, Provider<CommandQueue> provider6) {
        this.hostProvider = provider;
        this.handlerProvider = provider2;
        this.broadcastDispatcherProvider = provider3;
        this.userTrackerProvider = provider4;
        this.keyguardStateControllerProvider = provider5;
        this.commandQueueProvider = provider6;
    }

    public static TileServices_Factory create(Provider<QSTileHost> provider, Provider<Handler> provider2, Provider<BroadcastDispatcher> provider3, Provider<UserTracker> provider4, Provider<KeyguardStateController> provider5, Provider<CommandQueue> provider6) {
        return new TileServices_Factory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    public static TileServices newInstance(QSTileHost qSTileHost, Provider<Handler> provider, BroadcastDispatcher broadcastDispatcher, UserTracker userTracker, KeyguardStateController keyguardStateController, CommandQueue commandQueue) {
        return new TileServices(qSTileHost, provider, broadcastDispatcher, userTracker, keyguardStateController, commandQueue);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public TileServices m3918get() {
        return newInstance((QSTileHost) this.hostProvider.get(), this.handlerProvider, (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (UserTracker) this.userTrackerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (CommandQueue) this.commandQueueProvider.get());
    }
}