package com.android.systemui.dagger;

import android.content.Context;
import android.hardware.camera2.CameraManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideCameraManagerFactory.class */
public final class FrameworkServicesModule_ProvideCameraManagerFactory implements Factory<CameraManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideCameraManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideCameraManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideCameraManagerFactory(provider);
    }

    public static CameraManager provideCameraManager(Context context) {
        return (CameraManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideCameraManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public CameraManager m2274get() {
        return provideCameraManager((Context) this.contextProvider.get());
    }
}