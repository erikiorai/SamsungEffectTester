package com.android.systemui.mediaprojection.appselector;

import android.content.ComponentName;
import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/MediaProjectionAppSelectorModule_Companion_ProvideAppSelectorComponentNameFactory.class */
public final class MediaProjectionAppSelectorModule_Companion_ProvideAppSelectorComponentNameFactory implements Factory<ComponentName> {
    public final Provider<Context> contextProvider;

    public MediaProjectionAppSelectorModule_Companion_ProvideAppSelectorComponentNameFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static MediaProjectionAppSelectorModule_Companion_ProvideAppSelectorComponentNameFactory create(Provider<Context> provider) {
        return new MediaProjectionAppSelectorModule_Companion_ProvideAppSelectorComponentNameFactory(provider);
    }

    public static ComponentName provideAppSelectorComponentName(Context context) {
        return (ComponentName) Preconditions.checkNotNullFromProvides(MediaProjectionAppSelectorModule.Companion.provideAppSelectorComponentName(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ComponentName m3370get() {
        return provideAppSelectorComponentName((Context) this.contextProvider.get());
    }
}