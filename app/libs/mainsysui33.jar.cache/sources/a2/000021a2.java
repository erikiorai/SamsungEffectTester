package com.android.systemui.qs.dagger;

import com.android.systemui.battery.BatteryMeterView;
import com.android.systemui.qs.QuickStatusBarHeader;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/qs/dagger/QSFragmentModule_ProvidesBatteryMeterViewFactory.class */
public final class QSFragmentModule_ProvidesBatteryMeterViewFactory implements Factory<BatteryMeterView> {
    public final Provider<QuickStatusBarHeader> quickStatusBarHeaderProvider;

    public QSFragmentModule_ProvidesBatteryMeterViewFactory(Provider<QuickStatusBarHeader> provider) {
        this.quickStatusBarHeaderProvider = provider;
    }

    public static QSFragmentModule_ProvidesBatteryMeterViewFactory create(Provider<QuickStatusBarHeader> provider) {
        return new QSFragmentModule_ProvidesBatteryMeterViewFactory(provider);
    }

    public static BatteryMeterView providesBatteryMeterView(QuickStatusBarHeader quickStatusBarHeader) {
        return (BatteryMeterView) Preconditions.checkNotNullFromProvides(QSFragmentModule.providesBatteryMeterView(quickStatusBarHeader));
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public BatteryMeterView m3878get() {
        return providesBatteryMeterView((QuickStatusBarHeader) this.quickStatusBarHeaderProvider.get());
    }
}