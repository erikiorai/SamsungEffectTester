package com.android.systemui.keyguard.data.quickaffordance;

import android.content.Context;
import com.android.systemui.statusbar.policy.FlashlightController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/FlashlightQuickAffordanceConfig_Factory.class */
public final class FlashlightQuickAffordanceConfig_Factory implements Factory<FlashlightQuickAffordanceConfig> {
    public final Provider<Context> contextProvider;
    public final Provider<FlashlightController> flashlightControllerProvider;

    public FlashlightQuickAffordanceConfig_Factory(Provider<Context> provider, Provider<FlashlightController> provider2) {
        this.contextProvider = provider;
        this.flashlightControllerProvider = provider2;
    }

    public static FlashlightQuickAffordanceConfig_Factory create(Provider<Context> provider, Provider<FlashlightController> provider2) {
        return new FlashlightQuickAffordanceConfig_Factory(provider, provider2);
    }

    public static FlashlightQuickAffordanceConfig newInstance(Context context, FlashlightController flashlightController) {
        return new FlashlightQuickAffordanceConfig(context, flashlightController);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public FlashlightQuickAffordanceConfig m2927get() {
        return newInstance((Context) this.contextProvider.get(), (FlashlightController) this.flashlightControllerProvider.get());
    }
}