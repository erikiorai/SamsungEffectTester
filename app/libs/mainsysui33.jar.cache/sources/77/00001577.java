package com.android.systemui.dagger;

import android.media.IAudioService;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideIAudioServiceFactory.class */
public final class FrameworkServicesModule_ProvideIAudioServiceFactory implements Factory<IAudioService> {

    /* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideIAudioServiceFactory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final FrameworkServicesModule_ProvideIAudioServiceFactory INSTANCE = new FrameworkServicesModule_ProvideIAudioServiceFactory();
    }

    public static FrameworkServicesModule_ProvideIAudioServiceFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static IAudioService provideIAudioService() {
        return (IAudioService) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideIAudioService());
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public IAudioService m2295get() {
        return provideIAudioService();
    }
}