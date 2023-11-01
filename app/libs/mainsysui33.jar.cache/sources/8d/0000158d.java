package com.android.systemui.dagger;

import android.app.KeyguardManager;
import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideKeyguardManagerFactory.class */
public final class FrameworkServicesModule_ProvideKeyguardManagerFactory implements Factory<KeyguardManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideKeyguardManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideKeyguardManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideKeyguardManagerFactory(provider);
    }

    public static KeyguardManager provideKeyguardManager(Context context) {
        return (KeyguardManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideKeyguardManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public KeyguardManager m2326get() {
        return provideKeyguardManager((Context) this.contextProvider.get());
    }
}