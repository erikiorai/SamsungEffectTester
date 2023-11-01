package com.android.systemui.controls.settings;

import com.android.systemui.user.data.repository.UserRepository;
import com.android.systemui.util.settings.SecureSettings;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: mainsysui33.jar:com/android/systemui/controls/settings/ControlsSettingsRepositoryImpl.class */
public final class ControlsSettingsRepositoryImpl implements ControlsSettingsRepository {
    public final CoroutineDispatcher backgroundDispatcher;
    public final CoroutineScope scope;
    public final SecureSettings secureSettings;
    public final UserRepository userRepository;
    public final StateFlow<Boolean> canShowControlsInLockscreen = makeFlowForSetting("lockscreen_show_controls");
    public final StateFlow<Boolean> allowActionOnTrivialControlsInLockscreen = makeFlowForSetting("lockscreen_allow_trivial_controls");

    public ControlsSettingsRepositoryImpl(CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, UserRepository userRepository, SecureSettings secureSettings) {
        this.scope = coroutineScope;
        this.backgroundDispatcher = coroutineDispatcher;
        this.userRepository = userRepository;
        this.secureSettings = secureSettings;
    }

    @Override // com.android.systemui.controls.settings.ControlsSettingsRepository
    public StateFlow<Boolean> getAllowActionOnTrivialControlsInLockscreen() {
        return this.allowActionOnTrivialControlsInLockscreen;
    }

    @Override // com.android.systemui.controls.settings.ControlsSettingsRepository
    public StateFlow<Boolean> getCanShowControlsInLockscreen() {
        return this.canShowControlsInLockscreen;
    }

    public final StateFlow<Boolean> makeFlowForSetting(String str) {
        return FlowKt.stateIn(FlowKt.transformLatest(FlowKt.distinctUntilChanged(this.userRepository.getSelectedUserInfo()), new ControlsSettingsRepositoryImpl$makeFlowForSetting$$inlined$flatMapLatest$1(null, this, str)), this.scope, SharingStarted.Companion.getEagerly(), Boolean.FALSE);
    }
}