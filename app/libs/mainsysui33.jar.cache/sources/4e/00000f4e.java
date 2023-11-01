package com.android.systemui;

import com.android.systemui.dump.DumpManager;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/BootCompleteCacheImpl_Factory.class */
public final class BootCompleteCacheImpl_Factory implements Factory<BootCompleteCacheImpl> {
    public final Provider<DumpManager> dumpManagerProvider;

    public BootCompleteCacheImpl_Factory(Provider<DumpManager> provider) {
        this.dumpManagerProvider = provider;
    }

    public static BootCompleteCacheImpl_Factory create(Provider<DumpManager> provider) {
        return new BootCompleteCacheImpl_Factory(provider);
    }

    public static BootCompleteCacheImpl newInstance(DumpManager dumpManager) {
        return new BootCompleteCacheImpl(dumpManager);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public BootCompleteCacheImpl m1230get() {
        return newInstance((DumpManager) this.dumpManagerProvider.get());
    }
}