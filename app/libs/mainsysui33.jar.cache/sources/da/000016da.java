package com.android.systemui.dreams;

import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/DreamOverlayStateController_Factory.class */
public final class DreamOverlayStateController_Factory implements Factory<DreamOverlayStateController> {
    public final Provider<Executor> executorProvider;
    public final Provider<Boolean> overlayEnabledProvider;

    public DreamOverlayStateController_Factory(Provider<Executor> provider, Provider<Boolean> provider2) {
        this.executorProvider = provider;
        this.overlayEnabledProvider = provider2;
    }

    public static DreamOverlayStateController_Factory create(Provider<Executor> provider, Provider<Boolean> provider2) {
        return new DreamOverlayStateController_Factory(provider, provider2);
    }

    public static DreamOverlayStateController newInstance(Executor executor, boolean z) {
        return new DreamOverlayStateController(executor, z);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DreamOverlayStateController m2565get() {
        return newInstance((Executor) this.executorProvider.get(), ((Boolean) this.overlayEnabledProvider.get()).booleanValue());
    }
}