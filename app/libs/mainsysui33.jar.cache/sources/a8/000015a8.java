package com.android.systemui.dagger;

import android.content.Context;
import android.telecom.TelecomManager;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideTelecomManagerFactory.class */
public final class FrameworkServicesModule_ProvideTelecomManagerFactory implements Factory<TelecomManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideTelecomManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideTelecomManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideTelecomManagerFactory(provider);
    }

    public static TelecomManager provideTelecomManager(Context context) {
        return FrameworkServicesModule.provideTelecomManager(context);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public TelecomManager m2354get() {
        return provideTelecomManager((Context) this.contextProvider.get());
    }
}