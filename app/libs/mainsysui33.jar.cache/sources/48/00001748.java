package com.android.systemui.dreams.dagger;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/dagger/DreamOverlayModule_ProvidesLifecycleOwnerFactory.class */
public final class DreamOverlayModule_ProvidesLifecycleOwnerFactory implements Factory<LifecycleOwner> {
    public final Provider<LifecycleRegistry> lifecycleRegistryLazyProvider;

    public DreamOverlayModule_ProvidesLifecycleOwnerFactory(Provider<LifecycleRegistry> provider) {
        this.lifecycleRegistryLazyProvider = provider;
    }

    public static DreamOverlayModule_ProvidesLifecycleOwnerFactory create(Provider<LifecycleRegistry> provider) {
        return new DreamOverlayModule_ProvidesLifecycleOwnerFactory(provider);
    }

    public static LifecycleOwner providesLifecycleOwner(Lazy<LifecycleRegistry> lazy) {
        return (LifecycleOwner) Preconditions.checkNotNullFromProvides(DreamOverlayModule.providesLifecycleOwner(lazy));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LifecycleOwner m2624get() {
        return providesLifecycleOwner(DoubleCheck.lazy(this.lifecycleRegistryLazyProvider));
    }
}