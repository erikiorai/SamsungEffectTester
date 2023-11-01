package com.android.systemui.qs.tiles;

import android.os.Handler;
import android.os.Looper;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.settings.GlobalSettings;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/HeadsUpTile_Factory.class */
public final class HeadsUpTile_Factory implements Factory<HeadsUpTile> {
    public final Provider<ActivityStarter> activityStarterProvider;
    public final Provider<Looper> backgroundLooperProvider;
    public final Provider<FalsingManager> falsingManagerProvider;
    public final Provider<GlobalSettings> globalSettingsProvider;
    public final Provider<QSHost> hostProvider;
    public final Provider<Handler> mainHandlerProvider;
    public final Provider<MetricsLogger> metricsLoggerProvider;
    public final Provider<QSLogger> qsLoggerProvider;
    public final Provider<StatusBarStateController> statusBarStateControllerProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public HeadsUpTile_Factory(Provider<QSHost> provider, Provider<Looper> provider2, Provider<Handler> provider3, Provider<FalsingManager> provider4, Provider<MetricsLogger> provider5, Provider<StatusBarStateController> provider6, Provider<ActivityStarter> provider7, Provider<QSLogger> provider8, Provider<GlobalSettings> provider9, Provider<UserTracker> provider10) {
        this.hostProvider = provider;
        this.backgroundLooperProvider = provider2;
        this.mainHandlerProvider = provider3;
        this.falsingManagerProvider = provider4;
        this.metricsLoggerProvider = provider5;
        this.statusBarStateControllerProvider = provider6;
        this.activityStarterProvider = provider7;
        this.qsLoggerProvider = provider8;
        this.globalSettingsProvider = provider9;
        this.userTrackerProvider = provider10;
    }

    public static HeadsUpTile_Factory create(Provider<QSHost> provider, Provider<Looper> provider2, Provider<Handler> provider3, Provider<FalsingManager> provider4, Provider<MetricsLogger> provider5, Provider<StatusBarStateController> provider6, Provider<ActivityStarter> provider7, Provider<QSLogger> provider8, Provider<GlobalSettings> provider9, Provider<UserTracker> provider10) {
        return new HeadsUpTile_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10);
    }

    public static HeadsUpTile newInstance(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, GlobalSettings globalSettings, UserTracker userTracker) {
        return new HeadsUpTile(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger, globalSettings, userTracker);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public HeadsUpTile m4017get() {
        return newInstance((QSHost) this.hostProvider.get(), (Looper) this.backgroundLooperProvider.get(), (Handler) this.mainHandlerProvider.get(), (FalsingManager) this.falsingManagerProvider.get(), (MetricsLogger) this.metricsLoggerProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (QSLogger) this.qsLoggerProvider.get(), (GlobalSettings) this.globalSettingsProvider.get(), (UserTracker) this.userTrackerProvider.get());
    }
}