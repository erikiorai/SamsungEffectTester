package com.android.systemui.mediaprojection.appselector;

import android.content.Context;
import com.android.launcher3.icons.IconFactory;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/MediaProjectionAppSelectorModule_Companion_BindIconFactoryFactory.class */
public final class MediaProjectionAppSelectorModule_Companion_BindIconFactoryFactory implements Factory<IconFactory> {
    public final Provider<Context> contextProvider;

    public MediaProjectionAppSelectorModule_Companion_BindIconFactoryFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static IconFactory bindIconFactory(Context context) {
        return (IconFactory) Preconditions.checkNotNullFromProvides(MediaProjectionAppSelectorModule.Companion.bindIconFactory(context));
    }

    public static MediaProjectionAppSelectorModule_Companion_BindIconFactoryFactory create(Provider<Context> provider) {
        return new MediaProjectionAppSelectorModule_Companion_BindIconFactoryFactory(provider);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public IconFactory m3369get() {
        return bindIconFactory((Context) this.contextProvider.get());
    }
}