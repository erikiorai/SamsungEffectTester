package com.android.systemui.dagger;

import android.content.Context;
import android.os.Handler;
import com.android.systemui.dagger.NightDisplayListenerModule;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/NightDisplayListenerModule_Builder_Factory.class */
public final class NightDisplayListenerModule_Builder_Factory implements Factory<NightDisplayListenerModule.Builder> {
    public final Provider<Handler> bgHandlerProvider;
    public final Provider<Context> contextProvider;

    public NightDisplayListenerModule_Builder_Factory(Provider<Context> provider, Provider<Handler> provider2) {
        this.contextProvider = provider;
        this.bgHandlerProvider = provider2;
    }

    public static NightDisplayListenerModule_Builder_Factory create(Provider<Context> provider, Provider<Handler> provider2) {
        return new NightDisplayListenerModule_Builder_Factory(provider, provider2);
    }

    public static NightDisplayListenerModule.Builder newInstance(Context context, Handler handler) {
        return new NightDisplayListenerModule.Builder(context, handler);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public NightDisplayListenerModule.Builder m2371get() {
        return newInstance((Context) this.contextProvider.get(), (Handler) this.bgHandlerProvider.get());
    }
}