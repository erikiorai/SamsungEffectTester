package com.android.systemui.keyguard;

import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/DismissCallbackRegistry_Factory.class */
public final class DismissCallbackRegistry_Factory implements Factory<DismissCallbackRegistry> {
    public final Provider<Executor> uiBgExecutorProvider;

    public DismissCallbackRegistry_Factory(Provider<Executor> provider) {
        this.uiBgExecutorProvider = provider;
    }

    public static DismissCallbackRegistry_Factory create(Provider<Executor> provider) {
        return new DismissCallbackRegistry_Factory(provider);
    }

    public static DismissCallbackRegistry newInstance(Executor executor) {
        return new DismissCallbackRegistry(executor);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DismissCallbackRegistry m2800get() {
        return newInstance((Executor) this.uiBgExecutorProvider.get());
    }
}