package com.android.keyguard;

import android.content.res.Resources;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.util.settings.GlobalSettings;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/keyguard/FaceWakeUpTriggersConfig_Factory.class */
public final class FaceWakeUpTriggersConfig_Factory implements Factory<FaceWakeUpTriggersConfig> {
    public final Provider<DumpManager> dumpManagerProvider;
    public final Provider<GlobalSettings> globalSettingsProvider;
    public final Provider<Resources> resourcesProvider;

    public FaceWakeUpTriggersConfig_Factory(Provider<Resources> provider, Provider<GlobalSettings> provider2, Provider<DumpManager> provider3) {
        this.resourcesProvider = provider;
        this.globalSettingsProvider = provider2;
        this.dumpManagerProvider = provider3;
    }

    public static FaceWakeUpTriggersConfig_Factory create(Provider<Resources> provider, Provider<GlobalSettings> provider2, Provider<DumpManager> provider3) {
        return new FaceWakeUpTriggersConfig_Factory(provider, provider2, provider3);
    }

    public static FaceWakeUpTriggersConfig newInstance(Resources resources, GlobalSettings globalSettings, DumpManager dumpManager) {
        return new FaceWakeUpTriggersConfig(resources, globalSettings, dumpManager);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public FaceWakeUpTriggersConfig m557get() {
        return newInstance((Resources) this.resourcesProvider.get(), (GlobalSettings) this.globalSettingsProvider.get(), (DumpManager) this.dumpManagerProvider.get());
    }
}