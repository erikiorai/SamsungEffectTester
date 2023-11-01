package com.android.systemui.dagger;

import android.content.Context;
import android.hardware.display.NightDisplayListener;
import android.os.Handler;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/NightDisplayListenerModule_ProvideNightDisplayListenerFactory.class */
public final class NightDisplayListenerModule_ProvideNightDisplayListenerFactory implements Factory<NightDisplayListener> {
    public final Provider<Handler> bgHandlerProvider;
    public final Provider<Context> contextProvider;
    public final NightDisplayListenerModule module;

    public NightDisplayListenerModule_ProvideNightDisplayListenerFactory(NightDisplayListenerModule nightDisplayListenerModule, Provider<Context> provider, Provider<Handler> provider2) {
        this.module = nightDisplayListenerModule;
        this.contextProvider = provider;
        this.bgHandlerProvider = provider2;
    }

    public static NightDisplayListenerModule_ProvideNightDisplayListenerFactory create(NightDisplayListenerModule nightDisplayListenerModule, Provider<Context> provider, Provider<Handler> provider2) {
        return new NightDisplayListenerModule_ProvideNightDisplayListenerFactory(nightDisplayListenerModule, provider, provider2);
    }

    public static NightDisplayListener provideNightDisplayListener(NightDisplayListenerModule nightDisplayListenerModule, Context context, Handler handler) {
        return (NightDisplayListener) Preconditions.checkNotNullFromProvides(nightDisplayListenerModule.provideNightDisplayListener(context, handler));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public NightDisplayListener m2372get() {
        return provideNightDisplayListener(this.module, (Context) this.contextProvider.get(), (Handler) this.bgHandlerProvider.get());
    }
}