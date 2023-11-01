package com.android.systemui.dagger;

import android.content.Context;
import android.telephony.TelephonyManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideTelephonyManagerFactory.class */
public final class FrameworkServicesModule_ProvideTelephonyManagerFactory implements Factory<TelephonyManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideTelephonyManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideTelephonyManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideTelephonyManagerFactory(provider);
    }

    public static TelephonyManager provideTelephonyManager(Context context) {
        return (TelephonyManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideTelephonyManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public TelephonyManager m2355get() {
        return provideTelephonyManager((Context) this.contextProvider.get());
    }
}