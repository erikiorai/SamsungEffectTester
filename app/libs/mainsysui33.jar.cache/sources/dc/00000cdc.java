package com.android.keyguard.dagger;

import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.statusbar.phone.KeyguardStatusBarView;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/keyguard/dagger/KeyguardStatusBarViewModule_GetBatteryMeterViewFactory.class */
public final class KeyguardStatusBarViewModule_GetBatteryMeterViewFactory implements Factory<BatteryMeterView> {
    public final Provider<KeyguardStatusBarView> viewProvider;

    public KeyguardStatusBarViewModule_GetBatteryMeterViewFactory(Provider<KeyguardStatusBarView> provider) {
        this.viewProvider = provider;
    }

    public static KeyguardStatusBarViewModule_GetBatteryMeterViewFactory create(Provider<KeyguardStatusBarView> provider) {
        return new KeyguardStatusBarViewModule_GetBatteryMeterViewFactory(provider);
    }

    public static BatteryMeterView getBatteryMeterView(KeyguardStatusBarView keyguardStatusBarView) {
        return (BatteryMeterView) Preconditions.checkNotNullFromProvides(KeyguardStatusBarViewModule.getBatteryMeterView(keyguardStatusBarView));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public BatteryMeterView m841get() {
        return getBatteryMeterView((KeyguardStatusBarView) this.viewProvider.get());
    }
}