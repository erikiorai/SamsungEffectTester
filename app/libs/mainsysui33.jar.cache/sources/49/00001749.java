package com.android.systemui.dreams.dagger;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/dagger/DreamOverlayModule_ProvidesLifecycleRegistryFactory.class */
public final class DreamOverlayModule_ProvidesLifecycleRegistryFactory implements Factory<LifecycleRegistry> {
    public final Provider<LifecycleOwner> lifecycleOwnerProvider;

    public DreamOverlayModule_ProvidesLifecycleRegistryFactory(Provider<LifecycleOwner> provider) {
        this.lifecycleOwnerProvider = provider;
    }

    public static DreamOverlayModule_ProvidesLifecycleRegistryFactory create(Provider<LifecycleOwner> provider) {
        return new DreamOverlayModule_ProvidesLifecycleRegistryFactory(provider);
    }

    public static LifecycleRegistry providesLifecycleRegistry(LifecycleOwner lifecycleOwner) {
        return (LifecycleRegistry) Preconditions.checkNotNullFromProvides(DreamOverlayModule.providesLifecycleRegistry(lifecycleOwner));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public LifecycleRegistry m2625get() {
        return providesLifecycleRegistry((LifecycleOwner) this.lifecycleOwnerProvider.get());
    }
}