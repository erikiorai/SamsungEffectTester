package com.android.systemui.controls.dagger;

import android.content.Context;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.ControlsTileResourceConfiguration;
import com.android.systemui.controls.controller.ControlsTileResourceConfigurationImpl;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.settings.ControlsSettingsRepository;
import com.android.systemui.controls.ui.ControlsUiController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import dagger.Lazy;
import java.util.Optional;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/dagger/ControlsComponent.class */
public final class ControlsComponent {
    public final StateFlow<Boolean> canShowWhileLockedSetting;
    public final Context context;
    public final ControlsTileResourceConfiguration controlsTileResourceConfiguration;
    public final boolean featureEnabled;
    public final KeyguardStateController keyguardStateController;
    public final Lazy<ControlsController> lazyControlsController;
    public final Lazy<ControlsListingController> lazyControlsListingController;
    public final Lazy<ControlsUiController> lazyControlsUiController;
    public final LockPatternUtils lockPatternUtils;
    public final UserTracker userTracker;

    /* loaded from: mainsysui33.jar:com/android/systemui/controls/dagger/ControlsComponent$Visibility.class */
    public enum Visibility {
        AVAILABLE,
        AVAILABLE_AFTER_UNLOCK,
        UNAVAILABLE
    }

    public ControlsComponent(boolean z, Context context, Lazy<ControlsController> lazy, Lazy<ControlsUiController> lazy2, Lazy<ControlsListingController> lazy3, LockPatternUtils lockPatternUtils, KeyguardStateController keyguardStateController, UserTracker userTracker, ControlsSettingsRepository controlsSettingsRepository, Optional<ControlsTileResourceConfiguration> optional) {
        this.featureEnabled = z;
        this.context = context;
        this.lazyControlsController = lazy;
        this.lazyControlsUiController = lazy2;
        this.lazyControlsListingController = lazy3;
        this.lockPatternUtils = lockPatternUtils;
        this.keyguardStateController = keyguardStateController;
        this.userTracker = userTracker;
        this.canShowWhileLockedSetting = controlsSettingsRepository.getCanShowControlsInLockscreen();
        this.controlsTileResourceConfiguration = optional.orElse(new ControlsTileResourceConfigurationImpl());
    }

    public final StateFlow<Boolean> getCanShowWhileLockedSetting() {
        return this.canShowWhileLockedSetting;
    }

    public final Optional<ControlsController> getControlsController() {
        return this.featureEnabled ? Optional.of(this.lazyControlsController.get()) : Optional.empty();
    }

    public final Optional<ControlsListingController> getControlsListingController() {
        return this.featureEnabled ? Optional.of(this.lazyControlsListingController.get()) : Optional.empty();
    }

    public final Optional<ControlsUiController> getControlsUiController() {
        return this.featureEnabled ? Optional.of(this.lazyControlsUiController.get()) : Optional.empty();
    }

    public final int getTileImageId() {
        return this.controlsTileResourceConfiguration.getTileImageId();
    }

    public final int getTileTitleId() {
        return this.controlsTileResourceConfiguration.getTileTitleId();
    }

    public final Visibility getVisibility() {
        return !isEnabled() ? Visibility.UNAVAILABLE : this.lockPatternUtils.getStrongAuthForUser(this.userTracker.getUserHandle().getIdentifier()) == 1 ? Visibility.AVAILABLE_AFTER_UNLOCK : (((Boolean) this.canShowWhileLockedSetting.getValue()).booleanValue() || this.keyguardStateController.isUnlocked()) ? Visibility.AVAILABLE : Visibility.AVAILABLE_AFTER_UNLOCK;
    }

    public final boolean isEnabled() {
        return this.featureEnabled;
    }
}