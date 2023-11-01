package com.android.systemui.dagger;

import android.content.Context;
import android.os.Vibrator;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideVibratorFactory.class */
public final class FrameworkServicesModule_ProvideVibratorFactory implements Factory<Vibrator> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideVibratorFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideVibratorFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideVibratorFactory(provider);
    }

    public static Vibrator provideVibrator(Context context) {
        return FrameworkServicesModule.provideVibrator(context);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Vibrator m2359get() {
        return provideVibrator((Context) this.contextProvider.get());
    }
}