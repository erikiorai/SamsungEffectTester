package com.android.systemui.qs;

import android.os.Handler;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.qs.AutoAddTracker;
import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/AutoAddTracker_Builder_Factory.class */
public final class AutoAddTracker_Builder_Factory implements Factory<AutoAddTracker.Builder> {
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<Executor> executorProvider;
    public final Provider<Handler> handlerProvider;
    public final Provider<QSHost> qsHostProvider;
    public final Provider<SecureSettings> secureSettingsProvider;

    public AutoAddTracker_Builder_Factory(Provider<SecureSettings> provider, Provider<BroadcastDispatcher> provider2, Provider<QSHost> provider3, Provider<DumpManager> provider4, Provider<Handler> provider5, Provider<Executor> provider6) {
        this.secureSettingsProvider = provider;
        this.broadcastDispatcherProvider = provider2;
        this.qsHostProvider = provider3;
        this.dumpManagerProvider = provider4;
        this.handlerProvider = provider5;
        this.executorProvider = provider6;
    }

    public static AutoAddTracker_Builder_Factory create(Provider<SecureSettings> provider, Provider<BroadcastDispatcher> provider2, Provider<QSHost> provider3, Provider<DumpManager> provider4, Provider<Handler> provider5, Provider<Executor> provider6) {
        return new AutoAddTracker_Builder_Factory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    public static AutoAddTracker.Builder newInstance(SecureSettings secureSettings, BroadcastDispatcher broadcastDispatcher, QSHost qSHost, DumpManager dumpManager, Handler handler, Executor executor) {
        return new AutoAddTracker.Builder(secureSettings, broadcastDispatcher, qSHost, dumpManager, handler, executor);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public AutoAddTracker.Builder m3714get() {
        return newInstance((SecureSettings) this.secureSettingsProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (QSHost) this.qsHostProvider.get(), (DumpManager) this.dumpManagerProvider.get(), (Handler) this.handlerProvider.get(), (Executor) this.executorProvider.get());
    }
}