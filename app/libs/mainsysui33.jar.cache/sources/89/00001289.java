package com.android.systemui.broadcast.logging;

import com.android.systemui.plugins.log.LogBuffer;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/broadcast/logging/BroadcastDispatcherLogger_Factory.class */
public final class BroadcastDispatcherLogger_Factory implements Factory<BroadcastDispatcherLogger> {
    public final Provider<LogBuffer> bufferProvider;

    public BroadcastDispatcherLogger_Factory(Provider<LogBuffer> provider) {
        this.bufferProvider = provider;
    }

    public static BroadcastDispatcherLogger_Factory create(Provider<LogBuffer> provider) {
        return new BroadcastDispatcherLogger_Factory(provider);
    }

    public static BroadcastDispatcherLogger newInstance(LogBuffer logBuffer) {
        return new BroadcastDispatcherLogger(logBuffer);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public BroadcastDispatcherLogger m1653get() {
        return newInstance((LogBuffer) this.bufferProvider.get());
    }
}