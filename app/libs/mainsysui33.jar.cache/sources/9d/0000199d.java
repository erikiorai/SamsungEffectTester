package com.android.systemui.keyguard.data.repository;

import android.graphics.Point;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.common.shared.model.Position;
import com.android.systemui.doze.DozeHost;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.doze.DozeTransitionListener;
import com.android.systemui.dreams.DreamOverlayCallbackController;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.shared.model.BiometricUnlockModel;
import com.android.systemui.keyguard.shared.model.BiometricUnlockSource;
import com.android.systemui.keyguard.shared.model.DozeStateModel;
import com.android.systemui.keyguard.shared.model.DozeTransitionModel;
import com.android.systemui.keyguard.shared.model.StatusBarState;
import com.android.systemui.keyguard.shared.model.WakefulnessModel;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.MutableStateFlow;
import kotlinx.coroutines.flow.StateFlow;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl.class */
public final class KeyguardRepositoryImpl implements KeyguardRepository {
    public static final Companion Companion = new Companion(null);
    public final MutableStateFlow<Boolean> _animateBottomAreaDozingTransitions;
    public final MutableStateFlow<Float> _bottomAreaAlpha;
    public final MutableStateFlow<Position> _clockPosition;
    public final StateFlow<Boolean> animateBottomAreaDozingTransitions;
    public final AuthController authController;
    public final Flow<BiometricUnlockSource> biometricUnlockSource;
    public final Flow<BiometricUnlockModel> biometricUnlockState;
    public final StateFlow<Float> bottomAreaAlpha;
    public final StateFlow<Position> clockPosition;
    public final DozeTransitionListener dozeTransitionListener;
    public final Flow<DozeTransitionModel> dozeTransitionModel;
    public final DreamOverlayCallbackController dreamOverlayCallbackController;
    public final Flow<Point> faceSensorLocation;
    public final Flow<Point> fingerprintSensorLocation;
    public final Flow<Boolean> isBouncerShowing;
    public final Flow<Boolean> isDozing;
    public final Flow<Boolean> isDreaming;
    public final Flow<Boolean> isDreamingWithOverlay;
    public final Flow<Boolean> isKeyguardGoingAway;
    public final Flow<Boolean> isKeyguardOccluded;
    public final Flow<Boolean> isKeyguardShowing;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final Flow<Float> linearDozeAmount;
    public final Flow<StatusBarState> statusBarState;
    public final Flow<WakefulnessModel> wakefulness;

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl$Companion.class */
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/keyguard/data/repository/KeyguardRepositoryImpl$WhenMappings.class */
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[DozeMachine.State.values().length];
            try {
                iArr[DozeMachine.State.UNINITIALIZED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[DozeMachine.State.INITIALIZED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[DozeMachine.State.DOZE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[DozeMachine.State.DOZE_SUSPEND_TRIGGERS.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[DozeMachine.State.DOZE_AOD.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                iArr[DozeMachine.State.DOZE_REQUEST_PULSE.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                iArr[DozeMachine.State.DOZE_PULSING.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                iArr[DozeMachine.State.DOZE_PULSING_BRIGHT.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                iArr[DozeMachine.State.DOZE_PULSE_DONE.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                iArr[DozeMachine.State.FINISH.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                iArr[DozeMachine.State.DOZE_AOD_PAUSED.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                iArr[DozeMachine.State.DOZE_AOD_PAUSING.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                iArr[DozeMachine.State.DOZE_AOD_DOCKED.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public KeyguardRepositoryImpl(StatusBarStateController statusBarStateController, DozeHost dozeHost, WakefulnessLifecycle wakefulnessLifecycle, BiometricUnlockController biometricUnlockController, KeyguardStateController keyguardStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, DozeTransitionListener dozeTransitionListener, AuthController authController, DreamOverlayCallbackController dreamOverlayCallbackController) {
        this.keyguardStateController = keyguardStateController;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.dozeTransitionListener = dozeTransitionListener;
        this.authController = authController;
        this.dreamOverlayCallbackController = dreamOverlayCallbackController;
        MutableStateFlow<Boolean> MutableStateFlow = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._animateBottomAreaDozingTransitions = MutableStateFlow;
        this.animateBottomAreaDozingTransitions = FlowKt.asStateFlow(MutableStateFlow);
        MutableStateFlow<Float> MutableStateFlow2 = StateFlowKt.MutableStateFlow(Float.valueOf(1.0f));
        this._bottomAreaAlpha = MutableStateFlow2;
        this.bottomAreaAlpha = FlowKt.asStateFlow(MutableStateFlow2);
        MutableStateFlow<Position> MutableStateFlow3 = StateFlowKt.MutableStateFlow(new Position(0, 0));
        this._clockPosition = MutableStateFlow3;
        this.clockPosition = FlowKt.asStateFlow(MutableStateFlow3);
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        this.isKeyguardShowing = FlowKt.distinctUntilChanged(conflatedCallbackFlow.conflatedCallbackFlow(new KeyguardRepositoryImpl$isKeyguardShowing$1(this, null)));
        this.isKeyguardOccluded = FlowKt.distinctUntilChanged(conflatedCallbackFlow.conflatedCallbackFlow(new KeyguardRepositoryImpl$isKeyguardOccluded$1(this, null)));
        this.isKeyguardGoingAway = conflatedCallbackFlow.conflatedCallbackFlow(new KeyguardRepositoryImpl$isKeyguardGoingAway$1(this, null));
        this.isBouncerShowing = conflatedCallbackFlow.conflatedCallbackFlow(new KeyguardRepositoryImpl$isBouncerShowing$1(this, null));
        this.isDozing = FlowKt.distinctUntilChanged(conflatedCallbackFlow.conflatedCallbackFlow(new KeyguardRepositoryImpl$isDozing$1(dozeHost, statusBarStateController, null)));
        this.isDreamingWithOverlay = FlowKt.distinctUntilChanged(conflatedCallbackFlow.conflatedCallbackFlow(new KeyguardRepositoryImpl$isDreamingWithOverlay$1(this, null)));
        this.isDreaming = FlowKt.distinctUntilChanged(conflatedCallbackFlow.conflatedCallbackFlow(new KeyguardRepositoryImpl$isDreaming$1(this, null)));
        this.linearDozeAmount = conflatedCallbackFlow.conflatedCallbackFlow(new KeyguardRepositoryImpl$linearDozeAmount$1(statusBarStateController, null));
        this.dozeTransitionModel = conflatedCallbackFlow.conflatedCallbackFlow(new KeyguardRepositoryImpl$dozeTransitionModel$1(this, null));
        this.statusBarState = conflatedCallbackFlow.conflatedCallbackFlow(new KeyguardRepositoryImpl$statusBarState$1(statusBarStateController, this, null));
        this.biometricUnlockState = conflatedCallbackFlow.conflatedCallbackFlow(new KeyguardRepositoryImpl$biometricUnlockState$1(biometricUnlockController, this, null));
        this.wakefulness = conflatedCallbackFlow.conflatedCallbackFlow(new KeyguardRepositoryImpl$wakefulness$1(wakefulnessLifecycle, null));
        this.fingerprintSensorLocation = conflatedCallbackFlow.conflatedCallbackFlow(new KeyguardRepositoryImpl$fingerprintSensorLocation$1(this, null));
        this.faceSensorLocation = conflatedCallbackFlow.conflatedCallbackFlow(new KeyguardRepositoryImpl$faceSensorLocation$1(this, null));
        this.biometricUnlockSource = conflatedCallbackFlow.conflatedCallbackFlow(new KeyguardRepositoryImpl$biometricUnlockSource$1(this, null));
    }

    public final BiometricUnlockModel biometricModeIntToObject(int i) {
        BiometricUnlockModel biometricUnlockModel;
        switch (i) {
            case 0:
                biometricUnlockModel = BiometricUnlockModel.NONE;
                break;
            case 1:
                biometricUnlockModel = BiometricUnlockModel.WAKE_AND_UNLOCK;
                break;
            case 2:
                biometricUnlockModel = BiometricUnlockModel.WAKE_AND_UNLOCK_PULSING;
                break;
            case 3:
                biometricUnlockModel = BiometricUnlockModel.SHOW_BOUNCER;
                break;
            case 4:
                biometricUnlockModel = BiometricUnlockModel.ONLY_WAKE;
                break;
            case 5:
                biometricUnlockModel = BiometricUnlockModel.UNLOCK_COLLAPSING;
                break;
            case 6:
                biometricUnlockModel = BiometricUnlockModel.WAKE_AND_UNLOCK_FROM_DREAM;
                break;
            case 7:
                biometricUnlockModel = BiometricUnlockModel.DISMISS_BOUNCER;
                break;
            default:
                throw new IllegalArgumentException("Invalid BiometricUnlockModel value: " + i);
        }
        return biometricUnlockModel;
    }

    public final DozeStateModel dozeMachineStateToModel(DozeMachine.State state) {
        DozeStateModel dozeStateModel;
        switch (WhenMappings.$EnumSwitchMapping$0[state.ordinal()]) {
            case 1:
                dozeStateModel = DozeStateModel.UNINITIALIZED;
                break;
            case 2:
                dozeStateModel = DozeStateModel.INITIALIZED;
                break;
            case 3:
                dozeStateModel = DozeStateModel.DOZE;
                break;
            case 4:
                dozeStateModel = DozeStateModel.DOZE_SUSPEND_TRIGGERS;
                break;
            case 5:
                dozeStateModel = DozeStateModel.DOZE_AOD;
                break;
            case 6:
                dozeStateModel = DozeStateModel.DOZE_REQUEST_PULSE;
                break;
            case 7:
                dozeStateModel = DozeStateModel.DOZE_PULSING;
                break;
            case 8:
                dozeStateModel = DozeStateModel.DOZE_PULSING_BRIGHT;
                break;
            case 9:
                dozeStateModel = DozeStateModel.DOZE_PULSE_DONE;
                break;
            case 10:
                dozeStateModel = DozeStateModel.FINISH;
                break;
            case 11:
                dozeStateModel = DozeStateModel.DOZE_AOD_PAUSED;
                break;
            case 12:
                dozeStateModel = DozeStateModel.DOZE_AOD_PAUSING;
                break;
            case 13:
                dozeStateModel = DozeStateModel.DOZE_AOD_DOCKED;
                break;
            default:
                throw new IllegalArgumentException("Invalid DozeMachine.State: state");
        }
        return dozeStateModel;
    }

    @Override // com.android.systemui.keyguard.data.repository.KeyguardRepository
    public StateFlow<Boolean> getAnimateBottomAreaDozingTransitions() {
        return this.animateBottomAreaDozingTransitions;
    }

    @Override // com.android.systemui.keyguard.data.repository.KeyguardRepository
    public Flow<BiometricUnlockSource> getBiometricUnlockSource() {
        return this.biometricUnlockSource;
    }

    @Override // com.android.systemui.keyguard.data.repository.KeyguardRepository
    public Flow<BiometricUnlockModel> getBiometricUnlockState() {
        return this.biometricUnlockState;
    }

    @Override // com.android.systemui.keyguard.data.repository.KeyguardRepository
    public StateFlow<Float> getBottomAreaAlpha() {
        return this.bottomAreaAlpha;
    }

    @Override // com.android.systemui.keyguard.data.repository.KeyguardRepository
    public StateFlow<Position> getClockPosition() {
        return this.clockPosition;
    }

    @Override // com.android.systemui.keyguard.data.repository.KeyguardRepository
    public Flow<DozeTransitionModel> getDozeTransitionModel() {
        return this.dozeTransitionModel;
    }

    @Override // com.android.systemui.keyguard.data.repository.KeyguardRepository
    public Flow<Point> getFaceSensorLocation() {
        return this.faceSensorLocation;
    }

    @Override // com.android.systemui.keyguard.data.repository.KeyguardRepository
    public Flow<Point> getFingerprintSensorLocation() {
        return this.fingerprintSensorLocation;
    }

    @Override // com.android.systemui.keyguard.data.repository.KeyguardRepository
    public Flow<Float> getLinearDozeAmount() {
        return this.linearDozeAmount;
    }

    @Override // com.android.systemui.keyguard.data.repository.KeyguardRepository
    public Flow<StatusBarState> getStatusBarState() {
        return this.statusBarState;
    }

    @Override // com.android.systemui.keyguard.data.repository.KeyguardRepository
    public Flow<WakefulnessModel> getWakefulness() {
        return this.wakefulness;
    }

    @Override // com.android.systemui.keyguard.data.repository.KeyguardRepository
    public Flow<Boolean> isBouncerShowing() {
        return this.isBouncerShowing;
    }

    @Override // com.android.systemui.keyguard.data.repository.KeyguardRepository
    public Flow<Boolean> isDozing() {
        return this.isDozing;
    }

    @Override // com.android.systemui.keyguard.data.repository.KeyguardRepository
    public Flow<Boolean> isDreaming() {
        return this.isDreaming;
    }

    @Override // com.android.systemui.keyguard.data.repository.KeyguardRepository
    public Flow<Boolean> isDreamingWithOverlay() {
        return this.isDreamingWithOverlay;
    }

    @Override // com.android.systemui.keyguard.data.repository.KeyguardRepository
    public Flow<Boolean> isKeyguardGoingAway() {
        return this.isKeyguardGoingAway;
    }

    @Override // com.android.systemui.keyguard.data.repository.KeyguardRepository
    public Flow<Boolean> isKeyguardOccluded() {
        return this.isKeyguardOccluded;
    }

    @Override // com.android.systemui.keyguard.data.repository.KeyguardRepository
    public Flow<Boolean> isKeyguardShowing() {
        return this.isKeyguardShowing;
    }

    @Override // com.android.systemui.keyguard.data.repository.KeyguardRepository
    /* renamed from: isKeyguardShowing */
    public boolean mo2969isKeyguardShowing() {
        return this.keyguardStateController.isShowing();
    }

    @Override // com.android.systemui.keyguard.data.repository.KeyguardRepository
    public boolean isUdfpsSupported() {
        return this.keyguardUpdateMonitor.isUdfpsSupported();
    }

    @Override // com.android.systemui.keyguard.data.repository.KeyguardRepository
    public void setAnimateDozingTransitions(boolean z) {
        this._animateBottomAreaDozingTransitions.setValue(Boolean.valueOf(z));
    }

    @Override // com.android.systemui.keyguard.data.repository.KeyguardRepository
    public void setBottomAreaAlpha(float f) {
        this._bottomAreaAlpha.setValue(Float.valueOf(f));
    }

    @Override // com.android.systemui.keyguard.data.repository.KeyguardRepository
    public void setClockPosition(int i, int i2) {
        this._clockPosition.setValue(new Position(i, i2));
    }

    public final StatusBarState statusBarStateIntToObject(int i) {
        StatusBarState statusBarState;
        if (i == 0) {
            statusBarState = StatusBarState.SHADE;
        } else if (i == 1) {
            statusBarState = StatusBarState.KEYGUARD;
        } else if (i != 2) {
            throw new IllegalArgumentException("Invalid StatusBarState value: " + i);
        } else {
            statusBarState = StatusBarState.SHADE_LOCKED;
        }
        return statusBarState;
    }
}