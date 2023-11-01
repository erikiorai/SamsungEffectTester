package com.android.systemui.dagger;

import android.content.Context;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideDisplayIdFactory.class */
public final class FrameworkServicesModule_ProvideDisplayIdFactory implements Factory<Integer> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideDisplayIdFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideDisplayIdFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideDisplayIdFactory(provider);
    }

    public static int provideDisplayId(Context context) {
        return FrameworkServicesModule.provideDisplayId(context);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Integer m2286get() {
        return Integer.valueOf(provideDisplayId((Context) this.contextProvider.get()));
    }
}