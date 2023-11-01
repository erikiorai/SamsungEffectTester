package com.android.systemui.dagger;

import android.content.Context;
import android.media.AudioManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideAudioManagerFactory.class */
public final class FrameworkServicesModule_ProvideAudioManagerFactory implements Factory<AudioManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideAudioManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideAudioManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideAudioManagerFactory(provider);
    }

    public static AudioManager provideAudioManager(Context context) {
        return (AudioManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideAudioManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public AudioManager m2271get() {
        return provideAudioManager((Context) this.contextProvider.get());
    }
}