package com.android.systemui.controls.ui;

import android.service.dreams.IDreamManager;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.controls.settings.ControlsSettingsDialogManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.internal.Factory;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/ui/ControlsActivity_Factory.class */
public final class ControlsActivity_Factory implements Factory<ControlsActivity> {
    public final Provider<BroadcastDispatcher> broadcastDispatcherProvider;
    public final Provider<ControlsSettingsDialogManager> controlsSettingsDialogManagerProvider;
    public final Provider<IDreamManager> dreamManagerProvider;
    public final Provider<FeatureFlags> featureFlagsProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<ControlsUiController> uiControllerProvider;

    public ControlsActivity_Factory(Provider<ControlsUiController> provider, Provider<BroadcastDispatcher> provider2, Provider<IDreamManager> provider3, Provider<FeatureFlags> provider4, Provider<ControlsSettingsDialogManager> provider5, Provider<KeyguardStateController> provider6) {
        this.uiControllerProvider = provider;
        this.broadcastDispatcherProvider = provider2;
        this.dreamManagerProvider = provider3;
        this.featureFlagsProvider = provider4;
        this.controlsSettingsDialogManagerProvider = provider5;
        this.keyguardStateControllerProvider = provider6;
    }

    public static ControlsActivity_Factory create(Provider<ControlsUiController> provider, Provider<BroadcastDispatcher> provider2, Provider<IDreamManager> provider3, Provider<FeatureFlags> provider4, Provider<ControlsSettingsDialogManager> provider5, Provider<KeyguardStateController> provider6) {
        return new ControlsActivity_Factory(provider, provider2, provider3, provider4, provider5, provider6);
    }

    public static ControlsActivity newInstance(ControlsUiController controlsUiController, BroadcastDispatcher broadcastDispatcher, IDreamManager iDreamManager, FeatureFlags featureFlags, ControlsSettingsDialogManager controlsSettingsDialogManager, KeyguardStateController keyguardStateController) {
        return new ControlsActivity(controlsUiController, broadcastDispatcher, iDreamManager, featureFlags, controlsSettingsDialogManager, keyguardStateController);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ControlsActivity m1872get() {
        return newInstance((ControlsUiController) this.uiControllerProvider.get(), (BroadcastDispatcher) this.broadcastDispatcherProvider.get(), (IDreamManager) this.dreamManagerProvider.get(), (FeatureFlags) this.featureFlagsProvider.get(), (ControlsSettingsDialogManager) this.controlsSettingsDialogManagerProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get());
    }
}