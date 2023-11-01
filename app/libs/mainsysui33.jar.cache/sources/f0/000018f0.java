package com.android.systemui.keyguard;

import com.android.systemui.dump.DumpManager;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/ScreenLifecycle_Factory.class */
public final class ScreenLifecycle_Factory implements Factory<ScreenLifecycle> {
    public final Provider<DumpManager> dumpManagerProvider;

    public ScreenLifecycle_Factory(Provider<DumpManager> provider) {
        this.dumpManagerProvider = provider;
    }

    public static ScreenLifecycle_Factory create(Provider<DumpManager> provider) {
        return new ScreenLifecycle_Factory(provider);
    }

    public static ScreenLifecycle newInstance(DumpManager dumpManager) {
        return new ScreenLifecycle(dumpManager);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ScreenLifecycle m2906get() {
        return newInstance((DumpManager) this.dumpManagerProvider.get());
    }
}