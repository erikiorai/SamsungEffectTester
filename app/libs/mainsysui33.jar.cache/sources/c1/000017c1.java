package com.android.systemui.flags;

import android.content.Context;
import android.content.res.Resources;
import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.Factory;
import java.util.Map;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/flags/FeatureFlagsDebug_Factory.class */
public final class FeatureFlagsDebug_Factory implements Factory<FeatureFlagsDebug> {
    public final Provider<Map<Integer, Flag<?>>> allFlagsProvider;
    public final Provider<Context> contextProvider;
    public final Provider<FlagManager> flagManagerProvider;
    public final Provider<Resources> resourcesProvider;
    public final Provider<Restarter> restarterProvider;
    public final Provider<SecureSettings> secureSettingsProvider;
    public final Provider<ServerFlagReader> serverFlagReaderProvider;
    public final Provider<SystemPropertiesHelper> systemPropertiesProvider;

    public FeatureFlagsDebug_Factory(Provider<FlagManager> provider, Provider<Context> provider2, Provider<SecureSettings> provider3, Provider<SystemPropertiesHelper> provider4, Provider<Resources> provider5, Provider<ServerFlagReader> provider6, Provider<Map<Integer, Flag<?>>> provider7, Provider<Restarter> provider8) {
        this.flagManagerProvider = provider;
        this.contextProvider = provider2;
        this.secureSettingsProvider = provider3;
        this.systemPropertiesProvider = provider4;
        this.resourcesProvider = provider5;
        this.serverFlagReaderProvider = provider6;
        this.allFlagsProvider = provider7;
        this.restarterProvider = provider8;
    }

    public static FeatureFlagsDebug_Factory create(Provider<FlagManager> provider, Provider<Context> provider2, Provider<SecureSettings> provider3, Provider<SystemPropertiesHelper> provider4, Provider<Resources> provider5, Provider<ServerFlagReader> provider6, Provider<Map<Integer, Flag<?>>> provider7, Provider<Restarter> provider8) {
        return new FeatureFlagsDebug_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8);
    }

    public static FeatureFlagsDebug newInstance(FlagManager flagManager, Context context, SecureSettings secureSettings, SystemPropertiesHelper systemPropertiesHelper, Resources resources, ServerFlagReader serverFlagReader, Map<Integer, Flag<?>> map, Restarter restarter) {
        return new FeatureFlagsDebug(flagManager, context, secureSettings, systemPropertiesHelper, resources, serverFlagReader, map, restarter);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public FeatureFlagsDebug m2685get() {
        return newInstance((FlagManager) this.flagManagerProvider.get(), (Context) this.contextProvider.get(), (SecureSettings) this.secureSettingsProvider.get(), (SystemPropertiesHelper) this.systemPropertiesProvider.get(), (Resources) this.resourcesProvider.get(), (ServerFlagReader) this.serverFlagReaderProvider.get(), (Map) this.allFlagsProvider.get(), (Restarter) this.restarterProvider.get());
    }
}