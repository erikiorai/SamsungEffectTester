package com.android.systemui.dump;

import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dump/LogBufferFreezer_Factory.class */
public final class LogBufferFreezer_Factory implements Factory<LogBufferFreezer> {
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<DelayableExecutor> executorProvider;

    public LogBufferFreezer_Factory(Provider<DumpManager> provider, Provider<DelayableExecutor> provider2) {
        this.dumpManagerProvider = provider;
        this.executorProvider = provider2;
    }

    public static LogBufferFreezer_Factory create(Provider<DumpManager> provider, Provider<DelayableExecutor> provider2) {
        return new LogBufferFreezer_Factory(provider, provider2);
    }

    public static LogBufferFreezer newInstance(DumpManager dumpManager, DelayableExecutor delayableExecutor) {
        return new LogBufferFreezer(dumpManager, delayableExecutor);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LogBufferFreezer m2670get() {
        return newInstance((DumpManager) this.dumpManagerProvider.get(), (DelayableExecutor) this.executorProvider.get());
    }
}