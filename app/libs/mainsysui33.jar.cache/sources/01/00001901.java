package com.android.systemui.keyguard.dagger;

import com.android.keyguard.ViewMediatorCallback;
import com.android.systemui.keyguard.KeyguardViewMediator;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/dagger/KeyguardModule_ProvidesViewMediatorCallbackFactory.class */
public final class KeyguardModule_ProvidesViewMediatorCallbackFactory implements Factory<ViewMediatorCallback> {
    public final KeyguardModule module;
    public final Provider<KeyguardViewMediator> viewMediatorProvider;

    public KeyguardModule_ProvidesViewMediatorCallbackFactory(KeyguardModule keyguardModule, Provider<KeyguardViewMediator> provider) {
        this.module = keyguardModule;
        this.viewMediatorProvider = provider;
    }

    public static KeyguardModule_ProvidesViewMediatorCallbackFactory create(KeyguardModule keyguardModule, Provider<KeyguardViewMediator> provider) {
        return new KeyguardModule_ProvidesViewMediatorCallbackFactory(keyguardModule, provider);
    }

    public static ViewMediatorCallback providesViewMediatorCallback(KeyguardModule keyguardModule, KeyguardViewMediator keyguardViewMediator) {
        return (ViewMediatorCallback) Preconditions.checkNotNullFromProvides(keyguardModule.providesViewMediatorCallback(keyguardViewMediator));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ViewMediatorCallback m2913get() {
        return providesViewMediatorCallback(this.module, (KeyguardViewMediator) this.viewMediatorProvider.get());
    }
}