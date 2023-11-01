package com.android.systemui.dagger;

import android.content.Context;
import android.media.projection.MediaProjectionManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideMediaProjectionManagerFactory.class */
public final class FrameworkServicesModule_ProvideMediaProjectionManagerFactory implements Factory<MediaProjectionManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideMediaProjectionManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideMediaProjectionManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideMediaProjectionManagerFactory(provider);
    }

    public static MediaProjectionManager provideMediaProjectionManager(Context context) {
        return (MediaProjectionManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideMediaProjectionManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaProjectionManager m2329get() {
        return provideMediaProjectionManager((Context) this.contextProvider.get());
    }
}