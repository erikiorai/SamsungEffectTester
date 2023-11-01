package com.android.systemui.doze;

import android.hardware.display.AmbientDisplayConfiguration;
import com.android.systemui.dock.DockManager;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeDockHandler_Factory.class */
public final class DozeDockHandler_Factory implements Factory<DozeDockHandler> {
    public final Provider<AmbientDisplayConfiguration> configProvider;
    public final Provider<DockManager> dockManagerProvider;

    public DozeDockHandler_Factory(Provider<AmbientDisplayConfiguration> provider, Provider<DockManager> provider2) {
        this.configProvider = provider;
        this.dockManagerProvider = provider2;
    }

    public static DozeDockHandler_Factory create(Provider<AmbientDisplayConfiguration> provider, Provider<DockManager> provider2) {
        return new DozeDockHandler_Factory(provider, provider2);
    }

    public static DozeDockHandler newInstance(AmbientDisplayConfiguration ambientDisplayConfiguration, DockManager dockManager) {
        return new DozeDockHandler(ambientDisplayConfiguration, dockManager);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DozeDockHandler m2414get() {
        return newInstance((AmbientDisplayConfiguration) this.configProvider.get(), (DockManager) this.dockManagerProvider.get());
    }
}