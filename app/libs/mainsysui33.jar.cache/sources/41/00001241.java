package com.android.systemui.biometrics.dagger;

import com.android.systemui.biometrics.udfps.OverlapDetector;
import com.android.systemui.flags.FeatureFlags;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/biometrics/dagger/UdfpsModule_Companion_ProvidesOverlapDetectorFactory.class */
public final class UdfpsModule_Companion_ProvidesOverlapDetectorFactory implements Factory<OverlapDetector> {
    public final Provider<FeatureFlags> featureFlagsProvider;

    public UdfpsModule_Companion_ProvidesOverlapDetectorFactory(Provider<FeatureFlags> provider) {
        this.featureFlagsProvider = provider;
    }

    public static UdfpsModule_Companion_ProvidesOverlapDetectorFactory create(Provider<FeatureFlags> provider) {
        return new UdfpsModule_Companion_ProvidesOverlapDetectorFactory(provider);
    }

    public static OverlapDetector providesOverlapDetector(FeatureFlags featureFlags) {
        return (OverlapDetector) Preconditions.checkNotNullFromProvides(UdfpsModule.Companion.providesOverlapDetector(featureFlags));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public OverlapDetector m1615get() {
        return providesOverlapDetector((FeatureFlags) this.featureFlagsProvider.get());
    }
}