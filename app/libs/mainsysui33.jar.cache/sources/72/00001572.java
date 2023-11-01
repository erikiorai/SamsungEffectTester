package com.android.systemui.dagger;

import android.content.Context;
import android.hardware.face.FaceManager;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideFaceManagerFactory.class */
public final class FrameworkServicesModule_ProvideFaceManagerFactory implements Factory<FaceManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideFaceManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideFaceManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideFaceManagerFactory(provider);
    }

    public static FaceManager provideFaceManager(Context context) {
        return FrameworkServicesModule.provideFaceManager(context);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public FaceManager m2288get() {
        return provideFaceManager((Context) this.contextProvider.get());
    }
}