package com.android.systemui.qs.dagger;

import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.util.settings.GlobalSettings;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/dagger/QSFlagsModule_IsPMLiteEnabledFactory.class */
public final class QSFlagsModule_IsPMLiteEnabledFactory implements Factory<Boolean> {
    public final Provider<FeatureFlags> featureFlagsProvider;
    public final Provider<GlobalSettings> globalSettingsProvider;

    public QSFlagsModule_IsPMLiteEnabledFactory(Provider<FeatureFlags> provider, Provider<GlobalSettings> provider2) {
        this.featureFlagsProvider = provider;
        this.globalSettingsProvider = provider2;
    }

    public static QSFlagsModule_IsPMLiteEnabledFactory create(Provider<FeatureFlags> provider, Provider<GlobalSettings> provider2) {
        return new QSFlagsModule_IsPMLiteEnabledFactory(provider, provider2);
    }

    public static boolean isPMLiteEnabled(FeatureFlags featureFlags, GlobalSettings globalSettings) {
        return QSFlagsModule.isPMLiteEnabled(featureFlags, globalSettings);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public Boolean m3873get() {
        return Boolean.valueOf(isPMLiteEnabled((FeatureFlags) this.featureFlagsProvider.get(), (GlobalSettings) this.globalSettingsProvider.get()));
    }
}