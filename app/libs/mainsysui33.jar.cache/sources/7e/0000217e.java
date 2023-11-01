package com.android.systemui.qs.customize;

import com.android.internal.logging.UiEventLogger;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.qs.QSTileHost;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/customize/QSCustomizerController_Factory.class */
public final class QSCustomizerController_Factory implements Factory<QSCustomizerController> {
    public final Provider<ConfigurationController> configurationControllerProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<LightBarController> lightBarControllerProvider;
    public final Provider<QSTileHost> qsTileHostProvider;
    public final Provider<ScreenLifecycle> screenLifecycleProvider;
    public final Provider<TileAdapter> tileAdapterProvider;
    public final Provider<TileQueryHelper> tileQueryHelperProvider;
    public final Provider<UiEventLogger> uiEventLoggerProvider;
    public final Provider<QSCustomizer> viewProvider;

    public QSCustomizerController_Factory(Provider<QSCustomizer> provider, Provider<TileQueryHelper> provider2, Provider<QSTileHost> provider3, Provider<TileAdapter> provider4, Provider<ScreenLifecycle> provider5, Provider<KeyguardStateController> provider6, Provider<LightBarController> provider7, Provider<ConfigurationController> provider8, Provider<UiEventLogger> provider9) {
        this.viewProvider = provider;
        this.tileQueryHelperProvider = provider2;
        this.qsTileHostProvider = provider3;
        this.tileAdapterProvider = provider4;
        this.screenLifecycleProvider = provider5;
        this.keyguardStateControllerProvider = provider6;
        this.lightBarControllerProvider = provider7;
        this.configurationControllerProvider = provider8;
        this.uiEventLoggerProvider = provider9;
    }

    public static QSCustomizerController_Factory create(Provider<QSCustomizer> provider, Provider<TileQueryHelper> provider2, Provider<QSTileHost> provider3, Provider<TileAdapter> provider4, Provider<ScreenLifecycle> provider5, Provider<KeyguardStateController> provider6, Provider<LightBarController> provider7, Provider<ConfigurationController> provider8, Provider<UiEventLogger> provider9) {
        return new QSCustomizerController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9);
    }

    public static QSCustomizerController newInstance(QSCustomizer qSCustomizer, TileQueryHelper tileQueryHelper, QSTileHost qSTileHost, TileAdapter tileAdapter, ScreenLifecycle screenLifecycle, KeyguardStateController keyguardStateController, LightBarController lightBarController, ConfigurationController configurationController, UiEventLogger uiEventLogger) {
        return new QSCustomizerController(qSCustomizer, tileQueryHelper, qSTileHost, tileAdapter, screenLifecycle, keyguardStateController, lightBarController, configurationController, uiEventLogger);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public QSCustomizerController m3843get() {
        return newInstance((QSCustomizer) this.viewProvider.get(), (TileQueryHelper) this.tileQueryHelperProvider.get(), (QSTileHost) this.qsTileHostProvider.get(), (TileAdapter) this.tileAdapterProvider.get(), (ScreenLifecycle) this.screenLifecycleProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (LightBarController) this.lightBarControllerProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (UiEventLogger) this.uiEventLoggerProvider.get());
    }
}