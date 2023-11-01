package com.android.keyguard.clock;

import android.content.Context;
import android.view.LayoutInflater;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.dock.DockManager;
import com.android.systemui.plugins.PluginManager;
import com.android.systemui.settings.UserTracker;
import dagger.internal.Factory;
import java.util.concurrent.Executor;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/keyguard/clock/ClockManager_Factory.class */
public final class ClockManager_Factory implements Factory<ClockManager> {
    public final Provider<SysuiColorExtractor> colorExtractorProvider;
    public final Provider<Context> contextProvider;
    public final Provider<DockManager> dockManagerProvider;
    public final Provider<LayoutInflater> layoutInflaterProvider;
    public final Provider<Executor> mainExecutorProvider;
    public final Provider<PluginManager> pluginManagerProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public ClockManager_Factory(Provider<Context> provider, Provider<LayoutInflater> provider2, Provider<PluginManager> provider3, Provider<SysuiColorExtractor> provider4, Provider<DockManager> provider5, Provider<UserTracker> provider6, Provider<Executor> provider7) {
        this.contextProvider = provider;
        this.layoutInflaterProvider = provider2;
        this.pluginManagerProvider = provider3;
        this.colorExtractorProvider = provider4;
        this.dockManagerProvider = provider5;
        this.userTrackerProvider = provider6;
        this.mainExecutorProvider = provider7;
    }

    public static ClockManager_Factory create(Provider<Context> provider, Provider<LayoutInflater> provider2, Provider<PluginManager> provider3, Provider<SysuiColorExtractor> provider4, Provider<DockManager> provider5, Provider<UserTracker> provider6, Provider<Executor> provider7) {
        return new ClockManager_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7);
    }

    public static ClockManager newInstance(Context context, LayoutInflater layoutInflater, PluginManager pluginManager, SysuiColorExtractor sysuiColorExtractor, DockManager dockManager, UserTracker userTracker, Executor executor) {
        return new ClockManager(context, layoutInflater, pluginManager, sysuiColorExtractor, dockManager, userTracker, executor);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ClockManager m834get() {
        return newInstance((Context) this.contextProvider.get(), (LayoutInflater) this.layoutInflaterProvider.get(), (PluginManager) this.pluginManagerProvider.get(), (SysuiColorExtractor) this.colorExtractorProvider.get(), (DockManager) this.dockManagerProvider.get(), (UserTracker) this.userTrackerProvider.get(), (Executor) this.mainExecutorProvider.get());
    }
}