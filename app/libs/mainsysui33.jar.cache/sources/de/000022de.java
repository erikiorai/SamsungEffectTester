package com.android.systemui.qs.tiles;

import android.os.Handler;
import android.os.Looper;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.qs.tiles.dialog.InternetDialogFactory;
import com.android.systemui.statusbar.connectivity.AccessPointController;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/InternetTile_Factory.class */
public final class InternetTile_Factory implements Factory<InternetTile> {
    public final Provider<AccessPointController> accessPointControllerProvider;
    public final Provider<ActivityStarter> activityStarterProvider;
    public final Provider<Looper> backgroundLooperProvider;
    public final Provider<FalsingManager> falsingManagerProvider;
    public final Provider<QSHost> hostProvider;
    public final Provider<InternetDialogFactory> internetDialogFactoryProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<Handler> mainHandlerProvider;
    public final Provider<MetricsLogger> metricsLoggerProvider;
    public final Provider<NetworkController> networkControllerProvider;
    public final Provider<QSLogger> qsLoggerProvider;
    public final Provider<StatusBarStateController> statusBarStateControllerProvider;

    public InternetTile_Factory(Provider<QSHost> provider, Provider<Looper> provider2, Provider<Handler> provider3, Provider<FalsingManager> provider4, Provider<MetricsLogger> provider5, Provider<StatusBarStateController> provider6, Provider<ActivityStarter> provider7, Provider<QSLogger> provider8, Provider<NetworkController> provider9, Provider<AccessPointController> provider10, Provider<InternetDialogFactory> provider11, Provider<KeyguardStateController> provider12) {
        this.hostProvider = provider;
        this.backgroundLooperProvider = provider2;
        this.mainHandlerProvider = provider3;
        this.falsingManagerProvider = provider4;
        this.metricsLoggerProvider = provider5;
        this.statusBarStateControllerProvider = provider6;
        this.activityStarterProvider = provider7;
        this.qsLoggerProvider = provider8;
        this.networkControllerProvider = provider9;
        this.accessPointControllerProvider = provider10;
        this.internetDialogFactoryProvider = provider11;
        this.keyguardStateControllerProvider = provider12;
    }

    public static InternetTile_Factory create(Provider<QSHost> provider, Provider<Looper> provider2, Provider<Handler> provider3, Provider<FalsingManager> provider4, Provider<MetricsLogger> provider5, Provider<StatusBarStateController> provider6, Provider<ActivityStarter> provider7, Provider<QSLogger> provider8, Provider<NetworkController> provider9, Provider<AccessPointController> provider10, Provider<InternetDialogFactory> provider11, Provider<KeyguardStateController> provider12) {
        return new InternetTile_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12);
    }

    public static InternetTile newInstance(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, NetworkController networkController, AccessPointController accessPointController, InternetDialogFactory internetDialogFactory, KeyguardStateController keyguardStateController) {
        return new InternetTile(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger, networkController, accessPointController, internetDialogFactory, keyguardStateController);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public InternetTile m4021get() {
        return newInstance((QSHost) this.hostProvider.get(), (Looper) this.backgroundLooperProvider.get(), (Handler) this.mainHandlerProvider.get(), (FalsingManager) this.falsingManagerProvider.get(), (MetricsLogger) this.metricsLoggerProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (QSLogger) this.qsLoggerProvider.get(), (NetworkController) this.networkControllerProvider.get(), (AccessPointController) this.accessPointControllerProvider.get(), (InternetDialogFactory) this.internetDialogFactoryProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get());
    }
}