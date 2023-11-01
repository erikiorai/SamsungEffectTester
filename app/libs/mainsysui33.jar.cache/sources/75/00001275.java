package com.android.systemui.broadcast;

import android.content.Context;
import com.android.systemui.util.wakelock.WakeLock;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/broadcast/BroadcastSender_Factory.class */
public final class BroadcastSender_Factory implements Factory<BroadcastSender> {
    public final Provider<Executor> bgExecutorProvider;
    public final Provider<Context> contextProvider;
    public final Provider<WakeLock.Builder> wakeLockBuilderProvider;

    public BroadcastSender_Factory(Provider<Context> provider, Provider<WakeLock.Builder> provider2, Provider<Executor> provider3) {
        this.contextProvider = provider;
        this.wakeLockBuilderProvider = provider2;
        this.bgExecutorProvider = provider3;
    }

    public static BroadcastSender_Factory create(Provider<Context> provider, Provider<WakeLock.Builder> provider2, Provider<Executor> provider3) {
        return new BroadcastSender_Factory(provider, provider2, provider3);
    }

    public static BroadcastSender newInstance(Context context, WakeLock.Builder builder, Executor executor) {
        return new BroadcastSender(context, builder, executor);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public BroadcastSender m1641get() {
        return newInstance((Context) this.contextProvider.get(), (WakeLock.Builder) this.wakeLockBuilderProvider.get(), (Executor) this.bgExecutorProvider.get());
    }
}