package com.android.systemui.battery;

import android.content.ContentResolver;
import android.os.Handler;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.tuner.TunerService;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/battery/BatteryMeterViewController_Factory.class */
public final class BatteryMeterViewController_Factory implements Factory<BatteryMeterViewController> {
    public final Provider<BatteryController> batteryControllerProvider;
    public final Provider<ConfigurationController> configurationControllerProvider;
    public final Provider<ContentResolver> contentResolverProvider;
    public final Provider<FeatureFlags> featureFlagsProvider;
    public final Provider<Handler> mainHandlerProvider;
    public final Provider<TunerService> tunerServiceProvider;
    public final Provider<UserTracker> userTrackerProvider;
    public final Provider<BatteryMeterView> viewProvider;

    public BatteryMeterViewController_Factory(Provider<BatteryMeterView> provider, Provider<UserTracker> provider2, Provider<ConfigurationController> provider3, Provider<TunerService> provider4, Provider<Handler> provider5, Provider<ContentResolver> provider6, Provider<FeatureFlags> provider7, Provider<BatteryController> provider8) {
        this.viewProvider = provider;
        this.userTrackerProvider = provider2;
        this.configurationControllerProvider = provider3;
        this.tunerServiceProvider = provider4;
        this.mainHandlerProvider = provider5;
        this.contentResolverProvider = provider6;
        this.featureFlagsProvider = provider7;
        this.batteryControllerProvider = provider8;
    }

    public static BatteryMeterViewController_Factory create(Provider<BatteryMeterView> provider, Provider<UserTracker> provider2, Provider<ConfigurationController> provider3, Provider<TunerService> provider4, Provider<Handler> provider5, Provider<ContentResolver> provider6, Provider<FeatureFlags> provider7, Provider<BatteryController> provider8) {
        return new BatteryMeterViewController_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8);
    }

    public static BatteryMeterViewController newInstance(BatteryMeterView batteryMeterView, UserTracker userTracker, ConfigurationController configurationController, TunerService tunerService, Handler handler, ContentResolver contentResolver, FeatureFlags featureFlags, BatteryController batteryController) {
        return new BatteryMeterViewController(batteryMeterView, userTracker, configurationController, tunerService, handler, contentResolver, featureFlags, batteryController);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public BatteryMeterViewController m1497get() {
        return newInstance((BatteryMeterView) this.viewProvider.get(), (UserTracker) this.userTrackerProvider.get(), (ConfigurationController) this.configurationControllerProvider.get(), (TunerService) this.tunerServiceProvider.get(), (Handler) this.mainHandlerProvider.get(), (ContentResolver) this.contentResolverProvider.get(), (FeatureFlags) this.featureFlagsProvider.get(), (BatteryController) this.batteryControllerProvider.get());
    }
}