package com.android.systemui.doze;

import android.hardware.display.AmbientDisplayConfiguration;
import com.android.systemui.dock.DockManager;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.util.wakelock.WakeLock;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeMachine_Factory.class */
public final class DozeMachine_Factory implements Factory<DozeMachine> {
    public final Provider<AmbientDisplayConfiguration> ambientDisplayConfigProvider;
    public final Provider<DockManager> dockManagerProvider;
    public final Provider<DozeHost> dozeHostProvider;
    public final Provider<DozeLog> dozeLogProvider;
    public final Provider<DozeMachine.Part[]> partsProvider;
    public final Provider<DozeMachine.Service> serviceProvider;
    public final Provider<WakeLock> wakeLockProvider;
    public final Provider<WakefulnessLifecycle> wakefulnessLifecycleProvider;

    public DozeMachine_Factory(Provider<DozeMachine.Service> provider, Provider<AmbientDisplayConfiguration> provider2, Provider<WakeLock> provider3, Provider<WakefulnessLifecycle> provider4, Provider<DozeLog> provider5, Provider<DockManager> provider6, Provider<DozeHost> provider7, Provider<DozeMachine.Part[]> provider8) {
        this.serviceProvider = provider;
        this.ambientDisplayConfigProvider = provider2;
        this.wakeLockProvider = provider3;
        this.wakefulnessLifecycleProvider = provider4;
        this.dozeLogProvider = provider5;
        this.dockManagerProvider = provider6;
        this.dozeHostProvider = provider7;
        this.partsProvider = provider8;
    }

    public static DozeMachine_Factory create(Provider<DozeMachine.Service> provider, Provider<AmbientDisplayConfiguration> provider2, Provider<WakeLock> provider3, Provider<WakefulnessLifecycle> provider4, Provider<DozeLog> provider5, Provider<DockManager> provider6, Provider<DozeHost> provider7, Provider<DozeMachine.Part[]> provider8) {
        return new DozeMachine_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8);
    }

    public static DozeMachine newInstance(DozeMachine.Service service, AmbientDisplayConfiguration ambientDisplayConfiguration, WakeLock wakeLock, WakefulnessLifecycle wakefulnessLifecycle, DozeLog dozeLog, DockManager dockManager, DozeHost dozeHost, DozeMachine.Part[] partArr) {
        return new DozeMachine(service, ambientDisplayConfiguration, wakeLock, wakefulnessLifecycle, dozeLog, dockManager, dozeHost, partArr);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DozeMachine m2464get() {
        return newInstance((DozeMachine.Service) this.serviceProvider.get(), (AmbientDisplayConfiguration) this.ambientDisplayConfigProvider.get(), (WakeLock) this.wakeLockProvider.get(), (WakefulnessLifecycle) this.wakefulnessLifecycleProvider.get(), (DozeLog) this.dozeLogProvider.get(), (DockManager) this.dockManagerProvider.get(), (DozeHost) this.dozeHostProvider.get(), (DozeMachine.Part[]) this.partsProvider.get());
    }
}