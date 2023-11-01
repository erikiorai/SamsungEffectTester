package com.android.systemui.dagger;

import android.content.Context;
import android.telecom.TelecomManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.util.Optional;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideOptionalTelecomManagerFactory.class */
public final class FrameworkServicesModule_ProvideOptionalTelecomManagerFactory implements Factory<Optional<TelecomManager>> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideOptionalTelecomManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideOptionalTelecomManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideOptionalTelecomManagerFactory(provider);
    }

    public static Optional<TelecomManager> provideOptionalTelecomManager(Context context) {
        return (Optional) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideOptionalTelecomManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    public Optional<TelecomManager> get() {
        return provideOptionalTelecomManager((Context) this.contextProvider.get());
    }
}