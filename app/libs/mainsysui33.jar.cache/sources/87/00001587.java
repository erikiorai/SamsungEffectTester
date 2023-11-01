package com.android.systemui.dagger;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideInputMethodManagerFactory.class */
public final class FrameworkServicesModule_ProvideInputMethodManagerFactory implements Factory<InputMethodManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideInputMethodManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideInputMethodManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideInputMethodManagerFactory(provider);
    }

    public static InputMethodManager provideInputMethodManager(Context context) {
        return (InputMethodManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideInputMethodManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public InputMethodManager m2318get() {
        return provideInputMethodManager((Context) this.contextProvider.get());
    }
}