package com.android.systemui.controls.dagger;

import android.content.pm.PackageManager;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/dagger/ControlsModule_ProvidesControlsFeatureEnabledFactory.class */
public final class ControlsModule_ProvidesControlsFeatureEnabledFactory implements Factory<Boolean> {
    public final Provider<PackageManager> pmProvider;

    public ControlsModule_ProvidesControlsFeatureEnabledFactory(Provider<PackageManager> provider) {
        this.pmProvider = provider;
    }

    public static ControlsModule_ProvidesControlsFeatureEnabledFactory create(Provider<PackageManager> provider) {
        return new ControlsModule_ProvidesControlsFeatureEnabledFactory(provider);
    }

    public static boolean providesControlsFeatureEnabled(PackageManager packageManager) {
        return ControlsModule.providesControlsFeatureEnabled(packageManager);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Boolean m1819get() {
        return Boolean.valueOf(providesControlsFeatureEnabled((PackageManager) this.pmProvider.get()));
    }
}