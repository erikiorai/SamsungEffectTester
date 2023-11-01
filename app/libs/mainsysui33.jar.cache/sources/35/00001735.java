package com.android.systemui.dreams.dagger;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/dreams/dagger/DreamModule_ProvidesDreamOverlayEnabledFactory.class */
public final class DreamModule_ProvidesDreamOverlayEnabledFactory implements Factory<Boolean> {
    public final Provider<ComponentName> componentProvider;
    public final Provider<PackageManager> packageManagerProvider;

    public DreamModule_ProvidesDreamOverlayEnabledFactory(Provider<PackageManager> provider, Provider<ComponentName> provider2) {
        this.packageManagerProvider = provider;
        this.componentProvider = provider2;
    }

    public static DreamModule_ProvidesDreamOverlayEnabledFactory create(Provider<PackageManager> provider, Provider<ComponentName> provider2) {
        return new DreamModule_ProvidesDreamOverlayEnabledFactory(provider, provider2);
    }

    public static Boolean providesDreamOverlayEnabled(PackageManager packageManager, ComponentName componentName) {
        return (Boolean) Preconditions.checkNotNullFromProvides(DreamModule.providesDreamOverlayEnabled(packageManager, componentName));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Boolean m2608get() {
        return providesDreamOverlayEnabled((PackageManager) this.packageManagerProvider.get(), (ComponentName) this.componentProvider.get());
    }
}