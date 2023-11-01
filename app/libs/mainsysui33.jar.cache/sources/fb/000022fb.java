package com.android.systemui.qs.tiles;

import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import com.android.internal.logging.MetricsLogger;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.qs.QSHost;
import com.android.systemui.qs.logging.QSLogger;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.wallet.controller.QuickAccessWalletController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/tiles/QuickAccessWalletTile_Factory.class */
public final class QuickAccessWalletTile_Factory implements Factory<QuickAccessWalletTile> {
    public final Provider<ActivityStarter> activityStarterProvider;
    public final Provider<Looper> backgroundLooperProvider;
    public final Provider<FalsingManager> falsingManagerProvider;
    public final Provider<QSHost> hostProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<Handler> mainHandlerProvider;
    public final Provider<MetricsLogger> metricsLoggerProvider;
    public final Provider<PackageManager> packageManagerProvider;
    public final Provider<QSLogger> qsLoggerProvider;
    public final Provider<QuickAccessWalletController> quickAccessWalletControllerProvider;
    public final Provider<SecureSettings> secureSettingsProvider;
    public final Provider<StatusBarStateController> statusBarStateControllerProvider;

    public QuickAccessWalletTile_Factory(Provider<QSHost> provider, Provider<Looper> provider2, Provider<Handler> provider3, Provider<FalsingManager> provider4, Provider<MetricsLogger> provider5, Provider<StatusBarStateController> provider6, Provider<ActivityStarter> provider7, Provider<QSLogger> provider8, Provider<KeyguardStateController> provider9, Provider<PackageManager> provider10, Provider<SecureSettings> provider11, Provider<QuickAccessWalletController> provider12) {
        this.hostProvider = provider;
        this.backgroundLooperProvider = provider2;
        this.mainHandlerProvider = provider3;
        this.falsingManagerProvider = provider4;
        this.metricsLoggerProvider = provider5;
        this.statusBarStateControllerProvider = provider6;
        this.activityStarterProvider = provider7;
        this.qsLoggerProvider = provider8;
        this.keyguardStateControllerProvider = provider9;
        this.packageManagerProvider = provider10;
        this.secureSettingsProvider = provider11;
        this.quickAccessWalletControllerProvider = provider12;
    }

    public static QuickAccessWalletTile_Factory create(Provider<QSHost> provider, Provider<Looper> provider2, Provider<Handler> provider3, Provider<FalsingManager> provider4, Provider<MetricsLogger> provider5, Provider<StatusBarStateController> provider6, Provider<ActivityStarter> provider7, Provider<QSLogger> provider8, Provider<KeyguardStateController> provider9, Provider<PackageManager> provider10, Provider<SecureSettings> provider11, Provider<QuickAccessWalletController> provider12) {
        return new QuickAccessWalletTile_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12);
    }

    public static QuickAccessWalletTile newInstance(QSHost qSHost, Looper looper, Handler handler, FalsingManager falsingManager, MetricsLogger metricsLogger, StatusBarStateController statusBarStateController, ActivityStarter activityStarter, QSLogger qSLogger, KeyguardStateController keyguardStateController, PackageManager packageManager, SecureSettings secureSettings, QuickAccessWalletController quickAccessWalletController) {
        return new QuickAccessWalletTile(qSHost, looper, handler, falsingManager, metricsLogger, statusBarStateController, activityStarter, qSLogger, keyguardStateController, packageManager, secureSettings, quickAccessWalletController);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public QuickAccessWalletTile m4038get() {
        return newInstance((QSHost) this.hostProvider.get(), (Looper) this.backgroundLooperProvider.get(), (Handler) this.mainHandlerProvider.get(), (FalsingManager) this.falsingManagerProvider.get(), (MetricsLogger) this.metricsLoggerProvider.get(), (StatusBarStateController) this.statusBarStateControllerProvider.get(), (ActivityStarter) this.activityStarterProvider.get(), (QSLogger) this.qsLoggerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (PackageManager) this.packageManagerProvider.get(), (SecureSettings) this.secureSettingsProvider.get(), (QuickAccessWalletController) this.quickAccessWalletControllerProvider.get());
    }
}