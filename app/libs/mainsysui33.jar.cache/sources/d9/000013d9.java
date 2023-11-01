package com.android.systemui.controls.dagger;

import android.content.Context;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.ControlsTileResourceConfiguration;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.settings.ControlsSettingsRepository;
import com.android.systemui.controls.ui.ControlsUiController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import java.util.Optional;
import javax.inject.Provider;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/dagger/ControlsComponent_Factory.class */
public final class ControlsComponent_Factory implements Factory<ControlsComponent> {
    public final Provider<Context> contextProvider;
    public final Provider<ControlsController> controlsControllerProvider;
    public final Provider<ControlsListingController> controlsListingControllerProvider;
    public final Provider<ControlsSettingsRepository> controlsSettingsRepositoryProvider;
    public final Provider<ControlsUiController> controlsUiControllerProvider;
    public final Provider<Boolean> featureEnabledProvider;
    public final Provider<KeyguardStateController> keyguardStateControllerProvider;
    public final Provider<LockPatternUtils> lockPatternUtilsProvider;
    public final Provider<Optional<ControlsTileResourceConfiguration>> optionalControlsTileResourceConfigurationProvider;
    public final Provider<UserTracker> userTrackerProvider;

    public ControlsComponent_Factory(Provider<Boolean> provider, Provider<Context> provider2, Provider<ControlsController> provider3, Provider<ControlsUiController> provider4, Provider<ControlsListingController> provider5, Provider<LockPatternUtils> provider6, Provider<KeyguardStateController> provider7, Provider<UserTracker> provider8, Provider<ControlsSettingsRepository> provider9, Provider<Optional<ControlsTileResourceConfiguration>> provider10) {
        this.featureEnabledProvider = provider;
        this.contextProvider = provider2;
        this.controlsControllerProvider = provider3;
        this.controlsUiControllerProvider = provider4;
        this.controlsListingControllerProvider = provider5;
        this.lockPatternUtilsProvider = provider6;
        this.keyguardStateControllerProvider = provider7;
        this.userTrackerProvider = provider8;
        this.controlsSettingsRepositoryProvider = provider9;
        this.optionalControlsTileResourceConfigurationProvider = provider10;
    }

    public static ControlsComponent_Factory create(Provider<Boolean> provider, Provider<Context> provider2, Provider<ControlsController> provider3, Provider<ControlsUiController> provider4, Provider<ControlsListingController> provider5, Provider<LockPatternUtils> provider6, Provider<KeyguardStateController> provider7, Provider<UserTracker> provider8, Provider<ControlsSettingsRepository> provider9, Provider<Optional<ControlsTileResourceConfiguration>> provider10) {
        return new ControlsComponent_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10);
    }

    public static ControlsComponent newInstance(boolean z, Context context, Lazy<ControlsController> lazy, Lazy<ControlsUiController> lazy2, Lazy<ControlsListingController> lazy3, LockPatternUtils lockPatternUtils, KeyguardStateController keyguardStateController, UserTracker userTracker, ControlsSettingsRepository controlsSettingsRepository, Optional<ControlsTileResourceConfiguration> optional) {
        return new ControlsComponent(z, context, lazy, lazy2, lazy3, lockPatternUtils, keyguardStateController, userTracker, controlsSettingsRepository, optional);
    }

    /* JADX DEBUG: Method merged with bridge method */
    /* renamed from: get */
    public ControlsComponent m1817get() {
        return newInstance(((Boolean) this.featureEnabledProvider.get()).booleanValue(), (Context) this.contextProvider.get(), DoubleCheck.lazy(this.controlsControllerProvider), DoubleCheck.lazy(this.controlsUiControllerProvider), DoubleCheck.lazy(this.controlsListingControllerProvider), (LockPatternUtils) this.lockPatternUtilsProvider.get(), (KeyguardStateController) this.keyguardStateControllerProvider.get(), (UserTracker) this.userTrackerProvider.get(), (ControlsSettingsRepository) this.controlsSettingsRepositoryProvider.get(), (Optional) this.optionalControlsTileResourceConfigurationProvider.get());
    }
}