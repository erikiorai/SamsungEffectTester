package com.android.keyguard;

import android.content.Context;
import android.content.res.Resources;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.plugins.log.LogBuffer;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/keyguard/ClockEventController_Factory.class */
public final class ClockEventController_Factory implements Factory<ClockEventController> {
    public final Provider<BatteryController> batteryControllerProvider;
    public final Provider<Executor> bgExecutorProvider;
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<ConfigurationController> configurationControllerProvider;
    public final Provider<Context> contextProvider;
    public final Provider<FeatureFlags> featureFlagsProvider;
    public final Provider<KeyguardInteractor> keyguardInteractorProvider;
    public final Provider<KeyguardTransitionInteractor> keyguardTransitionInteractorProvider;
    public final Provider<KeyguardUpdateMonitor> keyguardUpdateMonitorProvider;
    public final Provider<LogBuffer> logBufferProvider;
    public final Provider<Executor> mainExecutorProvider;
    public final Provider<Resources> resourcesProvider;

    public ClockEventController_Factory(Provider<KeyguardInteractor> provider, Provider<KeyguardTransitionInteractor> provider2, Provider<BroadcastDispatcher> provider3, Provider<BatteryController> provider4, Provider<KeyguardUpdateMonitor> provider5, Provider<ConfigurationController> provider6, Provider<Resources> provider7, Provider<Context> provider8, Provider<Executor> provider9, Provider<Executor> provider10, Provider<LogBuffer> provider11, Provider<FeatureFlags> provider12) {
        this.keyguardInteractorProvider = provider;
        this.keyguardTransitionInteractorProvider = provider2;
        this.broadcastDispatcherProvider = provider3;
        this.batteryControllerProvider = provider4;
        this.keyguardUpdateMonitorProvider = provider5;
        this.configurationControllerProvider = provider6;
        this.resourcesProvider = provider7;
        this.contextProvider = provider8;
        this.mainExecutorProvider = provider9;
        this.bgExecutorProvider = provider10;
        this.logBufferProvider = provider11;
        this.featureFlagsProvider = provider12;
    }

    public static ClockEventController_Factory create(Provider<KeyguardInteractor> provider, Provider<KeyguardTransitionInteractor> provider2, Provider<BroadcastDispatcher> provider3, Provider<BatteryController> provider4, Provider<KeyguardUpdateMonitor> provider5, Provider<ConfigurationController> provider6, Provider<Resources> provider7, Provider<Context> provider8, Provider<Executor> provider9, Provider<Executor> provider10, Provider<LogBuffer> provider11, Provider<FeatureFlags> provider12) {
        return new ClockEventController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12);
    }

    public static ClockEventController newInstance(KeyguardInteractor keyguardInteractor, KeyguardTransitionInteractor keyguardTransitionInteractor, BroadcastDispatcher broadcastDispatcher, BatteryController batteryController, KeyguardUpdateMonitor keyguardUpdateMonitor, ConfigurationController configurationController, Resources resources, Context context, Executor executor, Executor executor2, LogBuffer logBuffer, FeatureFlags featureFlags) {
        return new ClockEventController(keyguardInteractor, keyguardTransitionInteractor, broadcastDispatcher, batteryController, keyguardUpdateMonitor, configurationController, resources, context, executor, executor2, logBuffer, featureFlags);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ClockEventController m548get() {
        return newInstance((KeyguardInteractor) this.keyguardInteractorProvider.get(), (KeyguardTransitionInteractor) this.keyguardTransitionInteractorProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (BatteryController) this.batteryControllerProvider.get(), (KeyguardUpdateMonitor) this.keyguardUpdateMonitorProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (Resources) this.resourcesProvider.get(), (Context) this.contextProvider.get(), (Executor) this.mainExecutorProvider.get(), (Executor) this.bgExecutorProvider.get(), (LogBuffer) this.logBufferProvider.get(), (FeatureFlags) this.featureFlagsProvider.get());
    }
}