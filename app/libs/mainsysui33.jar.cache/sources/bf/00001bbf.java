package com.android.systemui.log.table;

import com.android.systemui.dump.DumpManager;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/log/table/TableLogBufferFactory_Factory.class */
public final class TableLogBufferFactory_Factory implements Factory<TableLogBufferFactory> {
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<SystemClock> systemClockProvider;

    public TableLogBufferFactory_Factory(Provider<DumpManager> provider, Provider<SystemClock> provider2) {
        this.dumpManagerProvider = provider;
        this.systemClockProvider = provider2;
    }

    public static TableLogBufferFactory_Factory create(Provider<DumpManager> provider, Provider<SystemClock> provider2) {
        return new TableLogBufferFactory_Factory(provider, provider2);
    }

    public static TableLogBufferFactory newInstance(DumpManager dumpManager, SystemClock systemClock) {
        return new TableLogBufferFactory(dumpManager, systemClock);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public TableLogBufferFactory m3145get() {
        return newInstance((DumpManager) this.dumpManagerProvider.get(), (SystemClock) this.systemClockProvider.get());
    }
}