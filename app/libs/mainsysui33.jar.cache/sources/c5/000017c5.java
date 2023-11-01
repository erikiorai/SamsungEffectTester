package com.android.systemui.flags;

import dagger.internal.Factory;
import java.util.Map;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/FlagCommand_Factory.class */
public final class FlagCommand_Factory implements Factory<FlagCommand> {
    public final Provider<Map<Integer, Flag<?>>> allFlagsProvider;
    public final Provider<FeatureFlagsDebug> featureFlagsProvider;

    public FlagCommand_Factory(Provider<FeatureFlagsDebug> provider, Provider<Map<Integer, Flag<?>>> provider2) {
        this.featureFlagsProvider = provider;
        this.allFlagsProvider = provider2;
    }

    public static FlagCommand_Factory create(Provider<FeatureFlagsDebug> provider, Provider<Map<Integer, Flag<?>>> provider2) {
        return new FlagCommand_Factory(provider, provider2);
    }

    public static FlagCommand newInstance(FeatureFlagsDebug featureFlagsDebug, Map<Integer, Flag<?>> map) {
        return new FlagCommand(featureFlagsDebug, map);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public FlagCommand m2686get() {
        return newInstance((FeatureFlagsDebug) this.featureFlagsProvider.get(), (Map) this.allFlagsProvider.get());
    }
}