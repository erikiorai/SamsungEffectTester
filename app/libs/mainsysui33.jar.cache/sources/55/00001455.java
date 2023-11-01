package com.android.systemui.controls.settings;

import kotlinx.coroutines.flow.StateFlow;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/settings/ControlsSettingsRepository.class */
public interface ControlsSettingsRepository {
    StateFlow<Boolean> getAllowActionOnTrivialControlsInLockscreen();

    StateFlow<Boolean> getCanShowControlsInLockscreen();
}