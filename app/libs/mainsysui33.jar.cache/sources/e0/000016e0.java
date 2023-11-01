package com.android.systemui.dreams;

import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/DreamOverlayStatusBarItemsProvider_Factory.class */
public final class DreamOverlayStatusBarItemsProvider_Factory implements Factory<DreamOverlayStatusBarItemsProvider> {
    public final Provider<Executor> executorProvider;

    public DreamOverlayStatusBarItemsProvider_Factory(Provider<Executor> provider) {
        this.executorProvider = provider;
    }

    public static DreamOverlayStatusBarItemsProvider_Factory create(Provider<Executor> provider) {
        return new DreamOverlayStatusBarItemsProvider_Factory(provider);
    }

    public static DreamOverlayStatusBarItemsProvider newInstance(Executor executor) {
        return new DreamOverlayStatusBarItemsProvider(executor);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DreamOverlayStatusBarItemsProvider m2567get() {
        return newInstance((Executor) this.executorProvider.get());
    }
}