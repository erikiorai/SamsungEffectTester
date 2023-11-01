package com.android.systemui.qs;

import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.statusbar.policy.ConfigurationController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/QSContainerImplController_Factory.class */
public final class QSContainerImplController_Factory implements Factory<QSContainerImplController> {
    public final Provider<ConfigurationController> configurationControllerProvider;
    public final Provider<FalsingManager> falsingManagerProvider;
    public final Provider<FeatureFlags> featureFlagsProvider;
    public final Provider<QSPanelController> qsPanelControllerProvider;
    public final Provider<QuickStatusBarHeaderController> quickStatusBarHeaderControllerProvider;
    public final Provider<QSContainerImpl> viewProvider;

    public QSContainerImplController_Factory(Provider<QSContainerImpl> provider, Provider<QSPanelController> provider2, Provider<QuickStatusBarHeaderController> provider3, Provider<ConfigurationController> provider4, Provider<FalsingManager> provider5, Provider<FeatureFlags> provider6) {
        this.viewProvider = provider;
        this.qsPanelControllerProvider = provider2;
        this.quickStatusBarHeaderControllerProvider = provider3;
        this.configurationControllerProvider = provider4;
        this.falsingManagerProvider = provider5;
        this.featureFlagsProvider = provider6;
    }

    public static QSContainerImplController_Factory create(Provider<QSContainerImpl> provider, Provider<QSPanelController> provider2, Provider<QuickStatusBarHeaderController> provider3, Provider<ConfigurationController> provider4, Provider<FalsingManager> provider5, Provider<FeatureFlags> provider6) {
        return new QSContainerImplController_Factory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    public static QSContainerImplController newInstance(QSContainerImpl qSContainerImpl, QSPanelController qSPanelController, Object obj, ConfigurationController configurationController, FalsingManager falsingManager, FeatureFlags featureFlags) {
        return new QSContainerImplController(qSContainerImpl, qSPanelController, (QuickStatusBarHeaderController) obj, configurationController, falsingManager, featureFlags);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public QSContainerImplController m3744get() {
        return newInstance((QSContainerImpl) this.viewProvider.get(), (QSPanelController) this.qsPanelControllerProvider.get(), this.quickStatusBarHeaderControllerProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (FalsingManager) this.falsingManagerProvider.get(), (FeatureFlags) this.featureFlagsProvider.get());
    }
}