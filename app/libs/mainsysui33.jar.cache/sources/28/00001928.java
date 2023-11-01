package com.android.systemui.keyguard.data.quickaffordance;

import android.content.Context;
import com.android.systemui.controls.dagger.ControlsComponent;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/HomeControlsKeyguardQuickAffordanceConfig_Factory.class */
public final class HomeControlsKeyguardQuickAffordanceConfig_Factory implements Factory<HomeControlsKeyguardQuickAffordanceConfig> {
    public final Provider<ControlsComponent> componentProvider;
    public final Provider<Context> contextProvider;

    public HomeControlsKeyguardQuickAffordanceConfig_Factory(Provider<Context> provider, Provider<ControlsComponent> provider2) {
        this.contextProvider = provider;
        this.componentProvider = provider2;
    }

    public static HomeControlsKeyguardQuickAffordanceConfig_Factory create(Provider<Context> provider, Provider<ControlsComponent> provider2) {
        return new HomeControlsKeyguardQuickAffordanceConfig_Factory(provider, provider2);
    }

    public static HomeControlsKeyguardQuickAffordanceConfig newInstance(Context context, ControlsComponent controlsComponent) {
        return new HomeControlsKeyguardQuickAffordanceConfig(context, controlsComponent);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public HomeControlsKeyguardQuickAffordanceConfig m2932get() {
        return newInstance((Context) this.contextProvider.get(), (ControlsComponent) this.componentProvider.get());
    }
}