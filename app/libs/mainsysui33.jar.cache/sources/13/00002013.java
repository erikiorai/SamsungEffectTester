package com.android.systemui.power;

import android.content.Context;
import android.os.PowerManager;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.power.PowerUI;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.CentralSurfaces;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import java.util.Optional;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/power/PowerUI_Factory.class */
public final class PowerUI_Factory implements Factory<PowerUI> {
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<Optional<CentralSurfaces>> centralSurfacesOptionalLazyProvider;
    public final Provider<CommandQueue> commandQueueProvider;
    public final Provider<Context> contextProvider;
    public final Provider<EnhancedEstimates> enhancedEstimatesProvider;
    public final Provider<PowerManager> powerManagerProvider;
    public final Provider<UserTracker> userTrackerProvider;
    public final Provider<WakefulnessLifecycle> wakefulnessLifecycleProvider;
    public final Provider<PowerUI.WarningsUI> warningsUIProvider;

    public PowerUI_Factory(Provider<Context> provider, Provider<BroadcastDispatcher> provider2, Provider<CommandQueue> provider3, Provider<Optional<CentralSurfaces>> provider4, Provider<PowerUI.WarningsUI> provider5, Provider<EnhancedEstimates> provider6, Provider<WakefulnessLifecycle> provider7, Provider<PowerManager> provider8, Provider<UserTracker> provider9) {
        this.contextProvider = provider;
        this.broadcastDispatcherProvider = provider2;
        this.commandQueueProvider = provider3;
        this.centralSurfacesOptionalLazyProvider = provider4;
        this.warningsUIProvider = provider5;
        this.enhancedEstimatesProvider = provider6;
        this.wakefulnessLifecycleProvider = provider7;
        this.powerManagerProvider = provider8;
        this.userTrackerProvider = provider9;
    }

    public static PowerUI_Factory create(Provider<Context> provider, Provider<BroadcastDispatcher> provider2, Provider<CommandQueue> provider3, Provider<Optional<CentralSurfaces>> provider4, Provider<PowerUI.WarningsUI> provider5, Provider<EnhancedEstimates> provider6, Provider<WakefulnessLifecycle> provider7, Provider<PowerManager> provider8, Provider<UserTracker> provider9) {
        return new PowerUI_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9);
    }

    public static PowerUI newInstance(Context context, BroadcastDispatcher broadcastDispatcher, CommandQueue commandQueue, Lazy<Optional<CentralSurfaces>> lazy, PowerUI.WarningsUI warningsUI, EnhancedEstimates enhancedEstimates, WakefulnessLifecycle wakefulnessLifecycle, PowerManager powerManager, UserTracker userTracker) {
        return new PowerUI(context, broadcastDispatcher, commandQueue, lazy, warningsUI, enhancedEstimates, wakefulnessLifecycle, powerManager, userTracker);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public PowerUI m3657get() {
        return newInstance((Context) this.contextProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (CommandQueue) this.commandQueueProvider.get(), DoubleCheck.lazy(this.centralSurfacesOptionalLazyProvider), (PowerUI.WarningsUI) this.warningsUIProvider.get(), (EnhancedEstimates) this.enhancedEstimatesProvider.get(), (WakefulnessLifecycle) this.wakefulnessLifecycleProvider.get(), (PowerManager) this.powerManagerProvider.get(), (UserTracker) this.userTrackerProvider.get());
    }
}