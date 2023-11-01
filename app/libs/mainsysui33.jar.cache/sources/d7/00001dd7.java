package com.android.systemui.mediaprojection.appselector.data;

import android.content.Context;
import android.content.pm.PackageManager;
import com.android.launcher3.icons.IconFactory;
import com.android.systemui.shared.system.PackageManagerWrapper;
import dagger.internal.Factory;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: mainsysui33.jar:com/android/systemui/mediaprojection/appselector/data/IconLoaderLibAppIconLoader_Factory.class */
public final class IconLoaderLibAppIconLoader_Factory implements Factory<IconLoaderLibAppIconLoader> {
    public final Provider<CoroutineDispatcher> backgroundDispatcherProvider;
    public final Provider<Context> contextProvider;
    public final Provider<IconFactory> iconFactoryProvider;
    public final Provider<PackageManager> packageManagerProvider;
    public final Provider<PackageManagerWrapper> packageManagerWrapperProvider;

    public IconLoaderLibAppIconLoader_Factory(Provider<CoroutineDispatcher> provider, Provider<Context> provider2, Provider<PackageManagerWrapper> provider3, Provider<PackageManager> provider4, Provider<IconFactory> provider5) {
        this.backgroundDispatcherProvider = provider;
        this.contextProvider = provider2;
        this.packageManagerWrapperProvider = provider3;
        this.packageManagerProvider = provider4;
        this.iconFactoryProvider = provider5;
    }

    public static IconLoaderLibAppIconLoader_Factory create(Provider<CoroutineDispatcher> provider, Provider<Context> provider2, Provider<PackageManagerWrapper> provider3, Provider<PackageManager> provider4, Provider<IconFactory> provider5) {
        return new IconLoaderLibAppIconLoader_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static IconLoaderLibAppIconLoader newInstance(CoroutineDispatcher coroutineDispatcher, Context context, PackageManagerWrapper packageManagerWrapper, PackageManager packageManager, Provider<IconFactory> provider) {
        return new IconLoaderLibAppIconLoader(coroutineDispatcher, context, packageManagerWrapper, packageManager, provider);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public IconLoaderLibAppIconLoader m3373get() {
        return newInstance((CoroutineDispatcher) this.backgroundDispatcherProvider.get(), (Context) this.contextProvider.get(), (PackageManagerWrapper) this.packageManagerWrapperProvider.get(), (PackageManager) this.packageManagerProvider.get(), this.iconFactoryProvider);
    }
}