package com.android.systemui.classifier;

import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.dock.DockManager;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeExpansionStateManager;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.sensors.ProximitySensor;
import com.android.systemui.util.time.SystemClock;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/classifier/FalsingCollectorImpl_Factory.class */
public final class FalsingCollectorImpl_Factory implements Factory<FalsingCollectorImpl> {
    public final Provider<BatteryController> batteryControllerProvider;
    public final Provider<DockManager> dockManagerProvider;
    public final Provider<FalsingDataProvider> falsingDataProvider;
    public final Provider<FalsingManager> falsingManagerProvider;
    public final Provider<HistoryTracker> historyTrackerProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;
    public final Provider<DelayableExecutor> mainExecutorProvider;
    public final Provider<ProximitySensor> proximitySensorProvider;
    public final Provider<ShadeExpansionStateManager> shadeExpansionStateManagerProvider;
    public final Provider<StatusBarStateController> statusBarStateControllerProvider;
    public final Provider<SystemClock> systemClockProvider;

    public FalsingCollectorImpl_Factory(Provider<FalsingDataProvider> provider, Provider<FalsingManager> provider2, Provider<KeyguardUpdateMonitor> provider3, Provider<HistoryTracker> provider4, Provider<ProximitySensor> provider5, Provider<StatusBarStateController> provider6, Provider<KeyguardStateController> provider7, Provider<ShadeExpansionStateManager> provider8, Provider<BatteryController> provider9, Provider<DockManager> provider10, Provider<DelayableExecutor> provider11, Provider<SystemClock> provider12) {
        this.falsingDataProvider = provider;
        this.falsingManagerProvider = provider2;
        this.keyguardUpdateMonitorProvider = provider3;
        this.historyTrackerProvider = provider4;
        this.proximitySensorProvider = provider5;
        this.statusBarStateControllerProvider = provider6;
        this.keyguardStateControllerProvider = provider7;
        this.shadeExpansionStateManagerProvider = provider8;
        this.batteryControllerProvider = provider9;
        this.dockManagerProvider = provider10;
        this.mainExecutorProvider = provider11;
        this.systemClockProvider = provider12;
    }

    public static FalsingCollectorImpl_Factory create(Provider<FalsingDataProvider> provider, Provider<FalsingManager> provider2, Provider<KeyguardUpdateMonitor> provider3, Provider<HistoryTracker> provider4, Provider<ProximitySensor> provider5, Provider<StatusBarStateController> provider6, Provider<KeyguardStateController> provider7, Provider<ShadeExpansionStateManager> provider8, Provider<BatteryController> provider9, Provider<DockManager> provider10, Provider<DelayableExecutor> provider11, Provider<SystemClock> provider12) {
        return new FalsingCollectorImpl_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12);
    }

    public static FalsingCollectorImpl newInstance(FalsingDataProvider falsingDataProvider, FalsingManager falsingManager, KeyguardUpdateMonitor keyguardUpdateMonitor, HistoryTracker historyTracker, ProximitySensor proximitySensor, StatusBarStateController statusBarStateController, KeyguardStateController keyguardStateController, ShadeExpansionStateManager shadeExpansionStateManager, BatteryController batteryController, DockManager dockManager, DelayableExecutor delayableExecutor, SystemClock systemClock) {
        return new FalsingCollectorImpl(falsingDataProvider, falsingManager, keyguardUpdateMonitor, historyTracker, proximitySensor, statusBarStateController, keyguardStateController, shadeExpansionStateManager, batteryController, dockManager, delayableExecutor, systemClock);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public FalsingCollectorImpl m1697get() {
        return newInstance((FalsingDataProvider) this.falsingDataProvider.get(), (FalsingManager) this.falsingManagerProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get(), (HistoryTracker) this.historyTrackerProvider.get(), (ProximitySensor) this.proximitySensorProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (ShadeExpansionStateManager) this.shadeExpansionStateManagerProvider.get(), (BatteryController) this.batteryControllerProvider.get(), (DockManager) this.dockManagerProvider.get(), (DelayableExecutor) this.mainExecutorProvider.get(), (SystemClock) this.systemClockProvider.get());
    }
}