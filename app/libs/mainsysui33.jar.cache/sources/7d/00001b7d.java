package com.android.systemui.log;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.log.LogcatEchoTracker;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/log/LogBufferFactory_Factory.class */
public final class LogBufferFactory_Factory implements Factory<LogBufferFactory> {
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<LogcatEchoTracker> logcatEchoTrackerProvider;

    public LogBufferFactory_Factory(Provider<DumpManager> provider, Provider<LogcatEchoTracker> provider2) {
        this.dumpManagerProvider = provider;
        this.logcatEchoTrackerProvider = provider2;
    }

    public static LogBufferFactory_Factory create(Provider<DumpManager> provider, Provider<LogcatEchoTracker> provider2) {
        return new LogBufferFactory_Factory(provider, provider2);
    }

    public static LogBufferFactory newInstance(DumpManager dumpManager, LogcatEchoTracker logcatEchoTracker) {
        return new LogBufferFactory(dumpManager, logcatEchoTracker);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LogBufferFactory m3094get() {
        return newInstance((DumpManager) this.dumpManagerProvider.get(), (LogcatEchoTracker) this.logcatEchoTrackerProvider.get());
    }
}