package com.android.systemui.dagger;

import android.content.Context;
import android.os.Vibrator;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import java.util.Optional;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideOptionalVibratorFactory.class */
public final class FrameworkServicesModule_ProvideOptionalVibratorFactory implements Factory<Optional<Vibrator>> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideOptionalVibratorFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideOptionalVibratorFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideOptionalVibratorFactory(provider);
    }

    public static Optional<Vibrator> provideOptionalVibrator(Context context) {
        return (Optional) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideOptionalVibrator(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    public Optional<Vibrator> get() {
        return provideOptionalVibrator((Context) this.contextProvider.get());
    }
}