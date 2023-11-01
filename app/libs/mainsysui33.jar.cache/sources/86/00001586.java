package com.android.systemui.dagger;

import android.content.Context;
import android.hardware.input.InputManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideInputManagerFactory.class */
public final class FrameworkServicesModule_ProvideInputManagerFactory implements Factory<InputManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideInputManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideInputManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideInputManagerFactory(provider);
    }

    public static InputManager provideInputManager(Context context) {
        return (InputManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideInputManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public InputManager m2317get() {
        return provideInputManager((Context) this.contextProvider.get());
    }
}