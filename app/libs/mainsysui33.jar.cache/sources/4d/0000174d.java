package com.android.systemui.dreams.dagger;

import com.android.systemui.touch.TouchInsetManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/dagger/DreamOverlayModule_ProvidesTouchInsetSessionFactory.class */
public final class DreamOverlayModule_ProvidesTouchInsetSessionFactory implements Factory<TouchInsetManager.TouchInsetSession> {
    public final Provider<TouchInsetManager> managerProvider;

    public DreamOverlayModule_ProvidesTouchInsetSessionFactory(Provider<TouchInsetManager> provider) {
        this.managerProvider = provider;
    }

    public static DreamOverlayModule_ProvidesTouchInsetSessionFactory create(Provider<TouchInsetManager> provider) {
        return new DreamOverlayModule_ProvidesTouchInsetSessionFactory(provider);
    }

    public static TouchInsetManager.TouchInsetSession providesTouchInsetSession(TouchInsetManager touchInsetManager) {
        return (TouchInsetManager.TouchInsetSession) Preconditions.checkNotNullFromProvides(DreamOverlayModule.providesTouchInsetSession(touchInsetManager));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public TouchInsetManager.TouchInsetSession m2629get() {
        return providesTouchInsetSession((TouchInsetManager) this.managerProvider.get());
    }
}