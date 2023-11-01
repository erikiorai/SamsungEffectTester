package com.android.systemui.doze;

import android.hardware.display.AmbientDisplayConfiguration;
import android.text.TextUtils;
import com.android.systemui.doze.DozeHost;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import dagger.Lazy;
import java.io.PrintWriter;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeSuppressor.class */
public class DozeSuppressor implements DozeMachine.Part {
    public final Lazy<BiometricUnlockController> mBiometricUnlockControllerLazy;
    public final AmbientDisplayConfiguration mConfig;
    public final DozeHost mDozeHost;
    public final DozeLog mDozeLog;
    public DozeMachine mMachine;
    public boolean mIsCarModeEnabled = false;
    public final DozeHost.Callback mHostCallback = new DozeHost.Callback() { // from class: com.android.systemui.doze.DozeSuppressor.1
        @Override // com.android.systemui.doze.DozeHost.Callback
        public void onAlwaysOnSuppressedChanged(boolean z) {
            DozeMachine.State state = (!DozeSuppressor.this.mConfig.alwaysOnEnabled(-2) || z) ? DozeMachine.State.DOZE : DozeMachine.State.DOZE_AOD;
            DozeSuppressor.this.mDozeLog.traceAlwaysOnSuppressedChange(z, state);
            DozeSuppressor.this.mMachine.requestState(state);
        }

        @Override // com.android.systemui.doze.DozeHost.Callback
        public void onPowerSaveChanged(boolean z) {
            DozeMachine.State state = DozeSuppressor.this.mDozeHost.isPowerSaveActive() ? DozeMachine.State.DOZE : (DozeSuppressor.this.mMachine.getState() == DozeMachine.State.DOZE && DozeSuppressor.this.mConfig.alwaysOnEnabled(-2)) ? DozeMachine.State.DOZE_AOD : null;
            if (state != null) {
                DozeSuppressor.this.mDozeLog.tracePowerSaveChanged(DozeSuppressor.this.mDozeHost.isPowerSaveActive(), state);
                DozeSuppressor.this.mMachine.requestState(state);
            }
        }
    };

    /* renamed from: com.android.systemui.doze.DozeSuppressor$2  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeSuppressor$2.class */
    public static /* synthetic */ class AnonymousClass2 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$doze$DozeMachine$State;

        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:7:0x0020 -> B:11:0x0014). Please submit an issue!!! */
        static {
            int[] iArr = new int[DozeMachine.State.values().length];
            $SwitchMap$com$android$systemui$doze$DozeMachine$State = iArr;
            try {
                iArr[DozeMachine.State.INITIALIZED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.FINISH.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public DozeSuppressor(DozeHost dozeHost, AmbientDisplayConfiguration ambientDisplayConfiguration, DozeLog dozeLog, Lazy<BiometricUnlockController> lazy) {
        this.mDozeHost = dozeHost;
        this.mConfig = ambientDisplayConfiguration;
        this.mDozeLog = dozeLog;
        this.mBiometricUnlockControllerLazy = lazy;
    }

    public final void checkShouldImmediatelyEndDoze() {
        String str = !this.mDozeHost.isProvisioned() ? "device_unprovisioned" : ((BiometricUnlockController) this.mBiometricUnlockControllerLazy.get()).hasPendingAuthentication() ? "has_pending_auth" : null;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mDozeLog.traceImmediatelyEndDoze(str);
        this.mMachine.requestState(DozeMachine.State.FINISH);
    }

    public final void checkShouldImmediatelySuspendDoze() {
        if (this.mIsCarModeEnabled) {
            handleCarModeStarted();
        }
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public void destroy() {
        this.mDozeHost.removeCallback(this.mHostCallback);
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public void dump(PrintWriter printWriter) {
        printWriter.println(" isCarModeEnabled=" + this.mIsCarModeEnabled);
        printWriter.println(" hasPendingAuth=" + ((BiometricUnlockController) this.mBiometricUnlockControllerLazy.get()).hasPendingAuthentication());
        printWriter.println(" isProvisioned=" + this.mDozeHost.isProvisioned());
        printWriter.println(" isAlwaysOnSuppressed=" + this.mDozeHost.isAlwaysOnSuppressed());
        printWriter.println(" aodPowerSaveActive=" + this.mDozeHost.isPowerSaveActive());
    }

    public final void handleCarModeExited() {
        this.mDozeLog.traceCarModeEnded();
        this.mMachine.requestState(this.mConfig.alwaysOnEnabled(-2) ? DozeMachine.State.DOZE_AOD : DozeMachine.State.DOZE);
    }

    public final void handleCarModeStarted() {
        this.mDozeLog.traceCarModeStarted();
        this.mMachine.requestState(DozeMachine.State.DOZE_SUSPEND_TRIGGERS);
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public void onUiModeTypeChanged(int i) {
        boolean z = i == 3;
        if (this.mIsCarModeEnabled == z) {
            return;
        }
        this.mIsCarModeEnabled = z;
        if (this.mMachine.isUninitializedOrFinished()) {
            return;
        }
        if (this.mIsCarModeEnabled) {
            handleCarModeStarted();
        } else {
            handleCarModeExited();
        }
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public void setDozeMachine(DozeMachine dozeMachine) {
        this.mMachine = dozeMachine;
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        int i = AnonymousClass2.$SwitchMap$com$android$systemui$doze$DozeMachine$State[state2.ordinal()];
        if (i != 1) {
            if (i != 2) {
                return;
            }
            destroy();
            return;
        }
        this.mDozeHost.addCallback(this.mHostCallback);
        checkShouldImmediatelyEndDoze();
        checkShouldImmediatelySuspendDoze();
    }
}