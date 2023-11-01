package com.android.systemui.keyguard.data.quickaffordance;

import android.content.Context;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.wallet.controller.QuickAccessWalletController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/QuickAccessWalletKeyguardQuickAffordanceConfig_Factory.class */
public final class QuickAccessWalletKeyguardQuickAffordanceConfig_Factory implements Factory<QuickAccessWalletKeyguardQuickAffordanceConfig> {
    public final Provider<ActivityStarter> activityStarterProvider;
    public final Provider<Context> contextProvider;
    public final Provider<QuickAccessWalletController> walletControllerProvider;

    public QuickAccessWalletKeyguardQuickAffordanceConfig_Factory(Provider<Context> provider, Provider<QuickAccessWalletController> provider2, Provider<ActivityStarter> provider3) {
        this.contextProvider = provider;
        this.walletControllerProvider = provider2;
        this.activityStarterProvider = provider3;
    }

    public static QuickAccessWalletKeyguardQuickAffordanceConfig_Factory create(Provider<Context> provider, Provider<QuickAccessWalletController> provider2, Provider<ActivityStarter> provider3) {
        return new QuickAccessWalletKeyguardQuickAffordanceConfig_Factory(provider, provider2, provider3);
    }

    public static QuickAccessWalletKeyguardQuickAffordanceConfig newInstance(Context context, QuickAccessWalletController quickAccessWalletController, ActivityStarter activityStarter) {
        return new QuickAccessWalletKeyguardQuickAffordanceConfig(context, quickAccessWalletController, activityStarter);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public QuickAccessWalletKeyguardQuickAffordanceConfig m2961get() {
        return newInstance((Context) this.contextProvider.get(), (QuickAccessWalletController) this.walletControllerProvider.get(), (ActivityStarter) this.activityStarterProvider.get());
    }
}