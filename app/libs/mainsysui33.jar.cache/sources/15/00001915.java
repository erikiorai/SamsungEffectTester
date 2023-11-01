package com.android.systemui.keyguard.data.quickaffordance;

import android.content.Context;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.util.settings.SecureSettings;
import dagger.internal.Factory;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineDispatcher;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/quickaffordance/DoNotDisturbQuickAffordanceConfig_Factory.class */
public final class DoNotDisturbQuickAffordanceConfig_Factory implements Factory<DoNotDisturbQuickAffordanceConfig> {
    public final Provider<CoroutineDispatcher> backgroundDispatcherProvider;
    public final Provider<Context> contextProvider;
    public final Provider<ZenModeController> controllerProvider;
    public final Provider<SecureSettings> secureSettingsProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public DoNotDisturbQuickAffordanceConfig_Factory(Provider<Context> provider, Provider<ZenModeController> provider2, Provider<SecureSettings> provider3, Provider<UserTracker> provider4, Provider<CoroutineDispatcher> provider5) {
        this.contextProvider = provider;
        this.controllerProvider = provider2;
        this.secureSettingsProvider = provider3;
        this.userTrackerProvider = provider4;
        this.backgroundDispatcherProvider = provider5;
    }

    public static DoNotDisturbQuickAffordanceConfig_Factory create(Provider<Context> provider, Provider<ZenModeController> provider2, Provider<SecureSettings> provider3, Provider<UserTracker> provider4, Provider<CoroutineDispatcher> provider5) {
        return new DoNotDisturbQuickAffordanceConfig_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static DoNotDisturbQuickAffordanceConfig newInstance(Context context, ZenModeController zenModeController, SecureSettings secureSettings, UserTracker userTracker, CoroutineDispatcher coroutineDispatcher) {
        return new DoNotDisturbQuickAffordanceConfig(context, zenModeController, secureSettings, userTracker, coroutineDispatcher);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public DoNotDisturbQuickAffordanceConfig m2921get() {
        return newInstance((Context) this.contextProvider.get(), (ZenModeController) this.controllerProvider.get(), (SecureSettings) this.secureSettingsProvider.get(), (UserTracker) this.userTrackerProvider.get(), (CoroutineDispatcher) this.backgroundDispatcherProvider.get());
    }
}