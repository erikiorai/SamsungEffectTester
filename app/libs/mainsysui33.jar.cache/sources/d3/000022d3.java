package com.android.systemui.qs.tiles;

import android.os.Handler;
import android.os.Looper;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.statusbar.policy.DataSaverController;
import com.android.systemui.statusbar.policy.HotspotController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/HotspotTile_Factory.class */
public final class HotspotTile_Factory implements Factory<HotspotTile> {
    public final Provider<ActivityStarter> activityStarterProvider;
    public final Provider<Looper> backgroundLooperProvider;
    public final Provider<DataSaverController> dataSaverControllerProvider;
    public final Provider<FalsingManager> falsingManagerProvider;
    public final Provider<QSHost> hostProvider;
    public final Provider<HotspotController> hotspotControllerProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<Handler> mainHandlerProvider;
    public final Provider<MetricsLogger> metricsLoggerProvider;
    public final Provider<QSLogger> qsLoggerProvider;
    public final Provider<StatusBarStateController> statusBarStateControllerProvider;

    public HotspotTile_Factory(Provider<QSHost> provider, Provider<Looper> provider2, Provider<Handler> provider3, Provider<FalsingManager> provider4, Provider<MetricsLogger> provider5, Provider<StatusBarStateController> provider6, Provider<ActivityStarter> provider7, Provider<QSLogger> provider8, Provider<HotspotController> provider9, Provider<DataSaverController> provider10, Provider<KeyguardStateController> provider11) {
        this.hostProvider = provider;
        this.backgroundLooperProvider = provider2;
        this.mainHandlerProvider = provider3;
        this.falsingManagerProvider = provider4;
        this.metricsLoggerProvider = provider5;
        this.statusBarStateControllerProvider = provider6;
        this.activityStarterProvider = provider7;
        this.qsLoggerProvider = provider8;
        this.hotspotControllerProvider = provider9;
        this.dataSaverControllerProvider = provider10;
        this.keyguardStateControllerProvider = provider11;
    }

    public static HotspotTile_Factory create(Provider<QSHost> provider, Provider<Looper> provider2, Provider<Handler> provider3, Provider<FalsingManager> provider4, Provider<MetricsLogger> provider5, Provider<StatusBarStateController> provider6, Provider<ActivityStarter> provider7, Provider<QSLogger> provider8, Provider<HotspotController> provider9, Provider<DataSaverController> provider10, Provider<KeyguardStateController> provider11) {
        return new HotspotTile_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11);
    }

    public static HotspotTile newInstance(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, HotspotController hotspotController, DataSaverController dataSaverController, KeyguardStateController keyguardStateController) {
        return new HotspotTile(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger, hotspotController, dataSaverController, keyguardStateController);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public HotspotTile m4018get() {
        return newInstance((QSHost) this.hostProvider.get(), (Looper) this.backgroundLooperProvider.get(), (Handler) this.mainHandlerProvider.get(), (FalsingManager) this.falsingManagerProvider.get(), (MetricsLogger) this.metricsLoggerProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (QSLogger) this.qsLoggerProvider.get(), (HotspotController) this.hotspotControllerProvider.get(), (DataSaverController) this.dataSaverControllerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get());
    }
}