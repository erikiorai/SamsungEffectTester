package com.android.systemui.qs.tiles;

import android.os.Handler;
import android.os.Looper;
import android.service.dreams.IDreamManager;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/DreamTile_Factory.class */
public final class DreamTile_Factory implements Factory<DreamTile> {
    public final Provider<ActivityStarter> activityStarterProvider;
    public final Provider<Looper> backgroundLooperProvider;
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<IDreamManager> dreamManagerProvider;
    public final Provider<Boolean> dreamOnlyEnabledForDockUserProvider;
    public final Provider<Boolean> dreamSupportedProvider;
    public final Provider<FalsingManager> falsingManagerProvider;
    public final Provider<QSHost> hostProvider;
    public final Provider<Handler> mainHandlerProvider;
    public final Provider<MetricsLogger> metricsLoggerProvider;
    public final Provider<QSLogger> qsLoggerProvider;
    public final Provider<SecureSettings> secureSettingsProvider;
    public final Provider<StatusBarStateController> statusBarStateControllerProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public DreamTile_Factory(Provider<QSHost> provider, Provider<Looper> provider2, Provider<Handler> provider3, Provider<FalsingManager> provider4, Provider<MetricsLogger> provider5, Provider<StatusBarStateController> provider6, Provider<ActivityStarter> provider7, Provider<QSLogger> provider8, Provider<IDreamManager> provider9, Provider<SecureSettings> provider10, Provider<BroadcastDispatcher> provider11, Provider<UserTracker> provider12, Provider<Boolean> provider13, Provider<Boolean> provider14) {
        this.hostProvider = provider;
        this.backgroundLooperProvider = provider2;
        this.mainHandlerProvider = provider3;
        this.falsingManagerProvider = provider4;
        this.metricsLoggerProvider = provider5;
        this.statusBarStateControllerProvider = provider6;
        this.activityStarterProvider = provider7;
        this.qsLoggerProvider = provider8;
        this.dreamManagerProvider = provider9;
        this.secureSettingsProvider = provider10;
        this.broadcastDispatcherProvider = provider11;
        this.userTrackerProvider = provider12;
        this.dreamSupportedProvider = provider13;
        this.dreamOnlyEnabledForDockUserProvider = provider14;
    }

    public static DreamTile_Factory create(Provider<QSHost> provider, Provider<Looper> provider2, Provider<Handler> provider3, Provider<FalsingManager> provider4, Provider<MetricsLogger> provider5, Provider<StatusBarStateController> provider6, Provider<ActivityStarter> provider7, Provider<QSLogger> provider8, Provider<IDreamManager> provider9, Provider<SecureSettings> provider10, Provider<BroadcastDispatcher> provider11, Provider<UserTracker> provider12, Provider<Boolean> provider13, Provider<Boolean> provider14) {
        return new DreamTile_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13, provider14);
    }

    public static DreamTile newInstance(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, IDreamManager iDreamManager, SecureSettings secureSettings, BroadcastDispatcher broadcastDispatcher, UserTracker userTracker, boolean z, boolean z2) {
        return new DreamTile(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger, iDreamManager, secureSettings, broadcastDispatcher, userTracker, z, z2);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DreamTile m4014get() {
        return newInstance((QSHost) this.hostProvider.get(), (Looper) this.backgroundLooperProvider.get(), (Handler) this.mainHandlerProvider.get(), (FalsingManager) this.falsingManagerProvider.get(), (MetricsLogger) this.metricsLoggerProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (QSLogger) this.qsLoggerProvider.get(), (IDreamManager) this.dreamManagerProvider.get(), (SecureSettings) this.secureSettingsProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (UserTracker) this.userTrackerProvider.get(), ((Boolean) this.dreamSupportedProvider.get()).booleanValue(), ((Boolean) this.dreamOnlyEnabledForDockUserProvider.get()).booleanValue());
    }
}