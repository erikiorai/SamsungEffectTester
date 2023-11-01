package com.android.systemui.mediaprojection.appselector;

import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/MediaProjectionAppSelectorModule_Companion_ProvideCoroutineScopeFactory.class */
public final class MediaProjectionAppSelectorModule_Companion_ProvideCoroutineScopeFactory implements Factory<CoroutineScope> {
    public final Provider<CoroutineScope> applicationScopeProvider;

    public MediaProjectionAppSelectorModule_Companion_ProvideCoroutineScopeFactory(Provider<CoroutineScope> provider) {
        this.applicationScopeProvider = provider;
    }

    public static MediaProjectionAppSelectorModule_Companion_ProvideCoroutineScopeFactory create(Provider<CoroutineScope> provider) {
        return new MediaProjectionAppSelectorModule_Companion_ProvideCoroutineScopeFactory(provider);
    }

    public static CoroutineScope provideCoroutineScope(CoroutineScope coroutineScope) {
        return (CoroutineScope) Preconditions.checkNotNullFromProvides(MediaProjectionAppSelectorModule.Companion.provideCoroutineScope(coroutineScope));
    }

    /* JADX DEBUG: Method merged with bridge method */
    public CoroutineScope get() {
        return provideCoroutineScope((CoroutineScope) this.applicationScopeProvider.get());
    }
}