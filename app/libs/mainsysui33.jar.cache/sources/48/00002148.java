package com.android.systemui.qs;

import com.android.systemui.battery.BatteryMeterViewController;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.demomode.DemoModeController;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.qs.carrier.QSCarrierGroupController;
import com.android.systemui.statusbar.phone.StatusBarContentInsetsProvider;
import com.android.systemui.statusbar.phone.StatusBarIconController;
import com.android.systemui.statusbar.policy.VariableDateViewController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/QuickStatusBarHeaderController_Factory.class */
public final class QuickStatusBarHeaderController_Factory implements Factory<QuickStatusBarHeaderController> {
    public final Provider<BatteryMeterViewController> batteryMeterViewControllerProvider;
    public final Provider<SysuiColorExtractor> colorExtractorProvider;
    public final Provider<DemoModeController> demoModeControllerProvider;
    public final Provider<FeatureFlags> featureFlagsProvider;
    public final Provider<HeaderPrivacyIconsController> headerPrivacyIconsControllerProvider;
    public final Provider<QSCarrierGroupController.Builder> qsCarrierGroupControllerBuilderProvider;
    public final Provider<QSExpansionPathInterpolator> qsExpansionPathInterpolatorProvider;
    public final Provider<QuickQSPanelController> quickQSPanelControllerProvider;
    public final Provider<StatusBarContentInsetsProvider> statusBarContentInsetsProvider;
    public final Provider<StatusBarIconController> statusBarIconControllerProvider;
    public final Provider<StatusBarIconController.TintedIconManager.Factory> tintedIconManagerFactoryProvider;
    public final Provider<VariableDateViewController.Factory> variableDateViewControllerFactoryProvider;
    public final Provider<QuickStatusBarHeader> viewProvider;

    public QuickStatusBarHeaderController_Factory(Provider<QuickStatusBarHeader> provider, Provider<HeaderPrivacyIconsController> provider2, Provider<StatusBarIconController> provider3, Provider<DemoModeController> provider4, Provider<QuickQSPanelController> provider5, Provider<QSCarrierGroupController.Builder> provider6, Provider<SysuiColorExtractor> provider7, Provider<QSExpansionPathInterpolator> provider8, Provider<FeatureFlags> provider9, Provider<VariableDateViewController.Factory> provider10, Provider<BatteryMeterViewController> provider11, Provider<StatusBarContentInsetsProvider> provider12, Provider<StatusBarIconController.TintedIconManager.Factory> provider13) {
        this.viewProvider = provider;
        this.headerPrivacyIconsControllerProvider = provider2;
        this.statusBarIconControllerProvider = provider3;
        this.demoModeControllerProvider = provider4;
        this.quickQSPanelControllerProvider = provider5;
        this.qsCarrierGroupControllerBuilderProvider = provider6;
        this.colorExtractorProvider = provider7;
        this.qsExpansionPathInterpolatorProvider = provider8;
        this.featureFlagsProvider = provider9;
        this.variableDateViewControllerFactoryProvider = provider10;
        this.batteryMeterViewControllerProvider = provider11;
        this.statusBarContentInsetsProvider = provider12;
        this.tintedIconManagerFactoryProvider = provider13;
    }

    public static QuickStatusBarHeaderController_Factory create(Provider<QuickStatusBarHeader> provider, Provider<HeaderPrivacyIconsController> provider2, Provider<StatusBarIconController> provider3, Provider<DemoModeController> provider4, Provider<QuickQSPanelController> provider5, Provider<QSCarrierGroupController.Builder> provider6, Provider<SysuiColorExtractor> provider7, Provider<QSExpansionPathInterpolator> provider8, Provider<FeatureFlags> provider9, Provider<VariableDateViewController.Factory> provider10, Provider<BatteryMeterViewController> provider11, Provider<StatusBarContentInsetsProvider> provider12, Provider<StatusBarIconController.TintedIconManager.Factory> provider13) {
        return new QuickStatusBarHeaderController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11, provider12, provider13);
    }

    public static QuickStatusBarHeaderController newInstance(QuickStatusBarHeader quickStatusBarHeader, HeaderPrivacyIconsController headerPrivacyIconsController, StatusBarIconController statusBarIconController, DemoModeController demoModeController, QuickQSPanelController quickQSPanelController, QSCarrierGroupController.Builder builder, SysuiColorExtractor sysuiColorExtractor, QSExpansionPathInterpolator qSExpansionPathInterpolator, FeatureFlags featureFlags, VariableDateViewController.Factory factory, BatteryMeterViewController batteryMeterViewController, StatusBarContentInsetsProvider statusBarContentInsetsProvider, StatusBarIconController.TintedIconManager.Factory factory2) {
        return new QuickStatusBarHeaderController(quickStatusBarHeader, headerPrivacyIconsController, statusBarIconController, demoModeController, quickQSPanelController, builder, sysuiColorExtractor, qSExpansionPathInterpolator, featureFlags, factory, batteryMeterViewController, statusBarContentInsetsProvider, factory2);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public QuickStatusBarHeaderController m3812get() {
        return newInstance((QuickStatusBarHeader) this.viewProvider.get(), (HeaderPrivacyIconsController) this.headerPrivacyIconsControllerProvider.get(), (StatusBarIconController) this.statusBarIconControllerProvider.get(), (DemoModeController) this.demoModeControllerProvider.get(), (QuickQSPanelController) this.quickQSPanelControllerProvider.get(), (QSCarrierGroupController.Builder) this.qsCarrierGroupControllerBuilderProvider.get(), (SysuiColorExtractor) this.colorExtractorProvider.get(), (QSExpansionPathInterpolator) this.qsExpansionPathInterpolatorProvider.get(), (FeatureFlags) this.featureFlagsProvider.get(), (VariableDateViewController.Factory) this.variableDateViewControllerFactoryProvider.get(), (BatteryMeterViewController) this.batteryMeterViewControllerProvider.get(), (StatusBarContentInsetsProvider) this.statusBarContentInsetsProvider.get(), (StatusBarIconController.TintedIconManager.Factory) this.tintedIconManagerFactoryProvider.get());
    }
}