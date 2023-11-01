package com.android.systemui.dagger;

import android.content.Context;
import android.media.session.MediaSessionManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideMediaSessionManagerFactory.class */
public final class FrameworkServicesModule_ProvideMediaSessionManagerFactory implements Factory<MediaSessionManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideMediaSessionManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideMediaSessionManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideMediaSessionManagerFactory(provider);
    }

    public static MediaSessionManager provideMediaSessionManager(Context context) {
        return (MediaSessionManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideMediaSessionManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public MediaSessionManager m2331get() {
        return provideMediaSessionManager((Context) this.contextProvider.get());
    }
}