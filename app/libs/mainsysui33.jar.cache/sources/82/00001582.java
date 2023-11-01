package com.android.systemui.dagger;

import android.app.IWallpaperManager;
import dagger.internal.Factory;

/* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideIWallPaperManagerFactory.class */
public final class FrameworkServicesModule_ProvideIWallPaperManagerFactory implements Factory<IWallpaperManager> {

    /* loaded from: mainsysui33.jar:com/android/systemui/dagger/FrameworkServicesModule_ProvideIWallPaperManagerFactory$InstanceHolder.class */
    public static final class InstanceHolder {
        public static final FrameworkServicesModule_ProvideIWallPaperManagerFactory INSTANCE = new FrameworkServicesModule_ProvideIWallPaperManagerFactory();
    }

    public static FrameworkServicesModule_ProvideIWallPaperManagerFactory create() {
        return InstanceHolder.INSTANCE;
    }

    public static IWallpaperManager provideIWallPaperManager() {
        return FrameworkServicesModule.provideIWallPaperManager();
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public IWallpaperManager m2311get() {
        return provideIWallPaperManager();
    }
}