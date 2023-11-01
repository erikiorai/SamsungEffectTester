package com.android.systemui.keyguard.data.repository;

import android.graphics.Point;
import com.android.systemui.common.shared.model.Position;
import com.android.systemui.keyguard.shared.model.BiometricUnlockModel;
import com.android.systemui.keyguard.shared.model.BiometricUnlockSource;
import com.android.systemui.keyguard.shared.model.DozeTransitionModel;
import com.android.systemui.keyguard.shared.model.StatusBarState;
import com.android.systemui.keyguard.shared.model.WakefulnessModel;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.StateFlow;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardRepository.class */
public interface KeyguardRepository {
    StateFlow<Boolean> getAnimateBottomAreaDozingTransitions();

    Flow<BiometricUnlockSource> getBiometricUnlockSource();

    Flow<BiometricUnlockModel> getBiometricUnlockState();

    StateFlow<Float> getBottomAreaAlpha();

    StateFlow<Position> getClockPosition();

    Flow<DozeTransitionModel> getDozeTransitionModel();

    Flow<Point> getFaceSensorLocation();

    Flow<Point> getFingerprintSensorLocation();

    Flow<Float> getLinearDozeAmount();

    Flow<StatusBarState> getStatusBarState();

    Flow<WakefulnessModel> getWakefulness();

    Flow<Boolean> isBouncerShowing();

    Flow<Boolean> isDozing();

    Flow<Boolean> isDreaming();

    Flow<Boolean> isDreamingWithOverlay();

    Flow<Boolean> isKeyguardGoingAway();

    Flow<Boolean> isKeyguardOccluded();

    Flow<Boolean> isKeyguardShowing();

    /* renamed from: isKeyguardShowing  reason: collision with other method in class */
    boolean mo2969isKeyguardShowing();

    boolean isUdfpsSupported();

    void setAnimateDozingTransitions(boolean z);

    void setBottomAreaAlpha(float f);

    void setClockPosition(int i, int i2);
}