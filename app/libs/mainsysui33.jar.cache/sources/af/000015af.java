package com.android.systemui.dagger;

import android.app.WallpaperManager;
import android.content.Context;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideWallpaperManagerFactory.class */
public final class FrameworkServicesModule_ProvideWallpaperManagerFactory implements Factory<WallpaperManager> {
    public final Provider<Context> contextProvider;

    public FrameworkServicesModule_ProvideWallpaperManagerFactory(Provider<Context> provider) {
        this.contextProvider = provider;
    }

    public static FrameworkServicesModule_ProvideWallpaperManagerFactory create(Provider<Context> provider) {
        return new FrameworkServicesModule_ProvideWallpaperManagerFactory(provider);
    }

    public static WallpaperManager provideWallpaperManager(Context context) {
        return (WallpaperManager) Preconditions.checkNotNullFromProvides(FrameworkServicesModule.provideWallpaperManager(context));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public WallpaperManager m2361get() {
        return provideWallpaperManager((Context) this.contextProvider.get());
    }
}