package com.android.systemui.dreams.dagger;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/dagger/DreamOverlayModule_ProvidesLifecycleFactory.class */
public final class DreamOverlayModule_ProvidesLifecycleFactory implements Factory<Lifecycle> {
    public final Provider<LifecycleOwner> lifecycleOwnerProvider;

    public DreamOverlayModule_ProvidesLifecycleFactory(Provider<LifecycleOwner> provider) {
        this.lifecycleOwnerProvider = provider;
    }

    public static DreamOverlayModule_ProvidesLifecycleFactory create(Provider<LifecycleOwner> provider) {
        return new DreamOverlayModule_ProvidesLifecycleFactory(provider);
    }

    public static Lifecycle providesLifecycle(LifecycleOwner lifecycleOwner) {
        return (Lifecycle) Preconditions.checkNotNullFromProvides(DreamOverlayModule.providesLifecycle(lifecycleOwner));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Lifecycle m2623get() {
        return providesLifecycle((LifecycleOwner) this.lifecycleOwnerProvider.get());
    }
}