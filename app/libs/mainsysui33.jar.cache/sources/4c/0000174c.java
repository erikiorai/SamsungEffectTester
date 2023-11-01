package com.android.systemui.dreams.dagger;

import com.android.systemui.dreams.DreamOverlayContainerView;
import com.android.systemui.touch.TouchInsetManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/dagger/DreamOverlayModule_ProvidesTouchInsetManagerFactory.class */
public final class DreamOverlayModule_ProvidesTouchInsetManagerFactory implements Factory<TouchInsetManager> {
    public final Provider<Executor> executorProvider;
    public final Provider<DreamOverlayContainerView> viewProvider;

    public DreamOverlayModule_ProvidesTouchInsetManagerFactory(Provider<Executor> provider, Provider<DreamOverlayContainerView> provider2) {
        this.executorProvider = provider;
        this.viewProvider = provider2;
    }

    public static DreamOverlayModule_ProvidesTouchInsetManagerFactory create(Provider<Executor> provider, Provider<DreamOverlayContainerView> provider2) {
        return new DreamOverlayModule_ProvidesTouchInsetManagerFactory(provider, provider2);
    }

    public static TouchInsetManager providesTouchInsetManager(Executor executor, DreamOverlayContainerView dreamOverlayContainerView) {
        return (TouchInsetManager) Preconditions.checkNotNullFromProvides(DreamOverlayModule.providesTouchInsetManager(executor, dreamOverlayContainerView));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public TouchInsetManager m2628get() {
        return providesTouchInsetManager((Executor) this.executorProvider.get(), (DreamOverlayContainerView) this.viewProvider.get());
    }
}