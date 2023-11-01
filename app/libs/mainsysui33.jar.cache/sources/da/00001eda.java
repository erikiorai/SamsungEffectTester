package com.android.systemui.navigationbar.gestural;

import android.content.Context;
import com.android.internal.util.LatencyTracker;
import com.android.systemui.statusbar.VibratorHelper;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/navigationbar/gestural/NavigationBarEdgePanel_Factory.class */
public final class NavigationBarEdgePanel_Factory implements Factory<NavigationBarEdgePanel> {
    public final Provider<Executor> backgroundExecutorProvider;
    public final Provider<Context> contextProvider;
    public final Provider<LatencyTracker> latencyTrackerProvider;
    public final Provider<VibratorHelper> vibratorHelperProvider;

    public NavigationBarEdgePanel_Factory(Provider<Context> provider, Provider<LatencyTracker> provider2, Provider<VibratorHelper> provider3, Provider<Executor> provider4) {
        this.contextProvider = provider;
        this.latencyTrackerProvider = provider2;
        this.vibratorHelperProvider = provider3;
        this.backgroundExecutorProvider = provider4;
    }

    public static NavigationBarEdgePanel_Factory create(Provider<Context> provider, Provider<LatencyTracker> provider2, Provider<VibratorHelper> provider3, Provider<Executor> provider4) {
        return new NavigationBarEdgePanel_Factory(provider, provider2, provider3, provider4);
    }

    public static NavigationBarEdgePanel newInstance(Context context, LatencyTracker latencyTracker, VibratorHelper vibratorHelper, Executor executor) {
        return new NavigationBarEdgePanel(context, latencyTracker, vibratorHelper, executor);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public NavigationBarEdgePanel m3512get() {
        return newInstance((Context) this.contextProvider.get(), (LatencyTracker) this.latencyTrackerProvider.get(), (VibratorHelper) this.vibratorHelperProvider.get(), (Executor) this.backgroundExecutorProvider.get());
    }
}