package com.android.systemui.doze;

import android.content.res.Configuration;
import android.hardware.display.AmbientDisplayConfiguration;
import android.os.Trace;
import android.util.Log;
import com.android.internal.util.Preconditions;
import com.android.systemui.dock.DockManager;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.util.Assert;
import com.android.systemui.util.wakelock.WakeLock;
import java.io.PrintWriter;
import java.util.ArrayList;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeMachine.class */
public class DozeMachine {
    public static final boolean DEBUG = DozeService.DEBUG;
    public final AmbientDisplayConfiguration mAmbientDisplayConfig;
    public final DockManager mDockManager;
    public final DozeHost mDozeHost;
    public final DozeLog mDozeLog;
    public final Service mDozeService;
    public final Part[] mParts;
    public int mPulseReason;
    public final WakeLock mWakeLock;
    public final WakefulnessLifecycle mWakefulnessLifecycle;
    public final ArrayList<State> mQueuedRequests = new ArrayList<>();
    public State mState = State.UNINITIALIZED;
    public boolean mWakeLockHeldForCurrentState = false;
    public int mUiModeType = 1;

    /* renamed from: com.android.systemui.doze.DozeMachine$1  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeMachine$1.class */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$doze$DozeMachine$State;

        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:29:0x00a1 -> B:75:0x0014). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:31:0x00a5 -> B:61:0x001f). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:33:0x00a9 -> B:57:0x002a). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:35:0x00ad -> B:67:0x0035). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:37:0x00b1 -> B:63:0x0040). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:39:0x00b5 -> B:71:0x004c). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:41:0x00b9 -> B:69:0x0058). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:43:0x00bd -> B:77:0x0064). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:45:0x00c1 -> B:73:0x0070). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:47:0x00c5 -> B:59:0x007c). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:49:0x00c9 -> B:55:0x0088). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:51:0x00cd -> B:65:0x0094). Please submit an issue!!! */
        static {
            int[] iArr = new int[State.values().length];
            $SwitchMap$com$android$systemui$doze$DozeMachine$State = iArr;
            try {
                iArr[State.DOZE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[State.DOZE_AOD.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[State.DOZE_AOD_PAUSED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[State.DOZE_AOD_PAUSING.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[State.DOZE_AOD_DOCKED.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[State.DOZE_REQUEST_PULSE.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[State.DOZE_PULSING.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[State.DOZE_PULSING_BRIGHT.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[State.UNINITIALIZED.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[State.INITIALIZED.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[State.DOZE_SUSPEND_TRIGGERS.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[State.FINISH.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[State.DOZE_PULSE_DONE.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
        }
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeMachine$Part.class */
    public interface Part {
        default void destroy() {
        }

        default void dump(PrintWriter printWriter) {
        }

        default void onScreenState(int i) {
        }

        default void onUiModeTypeChanged(int i) {
        }

        default void setDozeMachine(DozeMachine dozeMachine) {
        }

        void transitionTo(State state, State state2);
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeMachine$Service.class */
    public interface Service {

        /* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeMachine$Service$Delegate.class */
        public static class Delegate implements Service {
            public final Service mDelegate;

            public Delegate(Service service) {
                this.mDelegate = service;
            }

            @Override // com.android.systemui.doze.DozeMachine.Service
            public void finish() {
                this.mDelegate.finish();
            }

            @Override // com.android.systemui.doze.DozeMachine.Service
            public void requestWakeUp(int i) {
                this.mDelegate.requestWakeUp(i);
            }

            @Override // com.android.systemui.doze.DozeMachine.Service
            public void setDozeScreenBrightness(int i) {
                this.mDelegate.setDozeScreenBrightness(i);
            }

            @Override // com.android.systemui.doze.DozeMachine.Service
            public void setDozeScreenState(int i) {
                this.mDelegate.setDozeScreenState(i);
            }
        }

        void finish();

        void requestWakeUp(int i);

        void setDozeScreenBrightness(int i);

        void setDozeScreenState(int i);
    }

    /* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeMachine$State.class */
    public enum State {
        UNINITIALIZED,
        INITIALIZED,
        DOZE,
        DOZE_SUSPEND_TRIGGERS,
        DOZE_AOD,
        DOZE_REQUEST_PULSE,
        DOZE_PULSING,
        DOZE_PULSING_BRIGHT,
        DOZE_PULSE_DONE,
        FINISH,
        DOZE_AOD_PAUSED,
        DOZE_AOD_PAUSING,
        DOZE_AOD_DOCKED;

        public boolean canPulse() {
            int i = AnonymousClass1.$SwitchMap$com$android$systemui$doze$DozeMachine$State[ordinal()];
            return i == 1 || i == 2 || i == 3 || i == 4 || i == 5;
        }

        public boolean isAlwaysOn() {
            return this == DOZE_AOD || this == DOZE_AOD_DOCKED;
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        public int screenState(DozeParameters dozeParameters) {
            int i = 2;
            int i2 = 2;
            switch (AnonymousClass1.$SwitchMap$com$android$systemui$doze$DozeMachine$State[ordinal()]) {
                case 1:
                case 3:
                case 11:
                    return 1;
                case 2:
                case 4:
                    return 4;
                case 5:
                case 7:
                case 8:
                    break;
                case 6:
                    i2 = 2;
                    if (dozeParameters.getDisplayNeedsBlanking()) {
                        i2 = 1;
                        break;
                    }
                    break;
                case 9:
                case 10:
                    if (!dozeParameters.shouldControlScreenOff()) {
                        i = 1;
                    }
                    return i;
                default:
                    return 0;
            }
            return i2;
        }

        public boolean staysAwake() {
            int i = AnonymousClass1.$SwitchMap$com$android$systemui$doze$DozeMachine$State[ordinal()];
            return i == 5 || i == 6 || i == 7 || i == 8;
        }
    }

    public DozeMachine(Service service, AmbientDisplayConfiguration ambientDisplayConfiguration, WakeLock wakeLock, WakefulnessLifecycle wakefulnessLifecycle, DozeLog dozeLog, DockManager dockManager, DozeHost dozeHost, Part[] partArr) {
        this.mDozeService = service;
        this.mAmbientDisplayConfig = ambientDisplayConfiguration;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mWakeLock = wakeLock;
        this.mDozeLog = dozeLog;
        this.mDockManager = dockManager;
        this.mDozeHost = dozeHost;
        this.mParts = partArr;
        for (Part part : partArr) {
            part.setDozeMachine(this);
        }
    }

    public void destroy() {
        for (Part part : this.mParts) {
            part.destroy();
        }
    }

    public void dump(PrintWriter printWriter) {
        printWriter.print(" state=");
        printWriter.println(this.mState);
        printWriter.print(" mUiModeType=");
        printWriter.println(this.mUiModeType);
        printWriter.print(" wakeLockHeldForCurrentState=");
        printWriter.println(this.mWakeLockHeldForCurrentState);
        printWriter.print(" wakeLock=");
        printWriter.println(this.mWakeLock);
        printWriter.println("Parts:");
        for (Part part : this.mParts) {
            part.dump(printWriter);
        }
    }

    public int getPulseReason() {
        Assert.isMainThread();
        State state = this.mState;
        boolean z = state == State.DOZE_REQUEST_PULSE || state == State.DOZE_PULSING || state == State.DOZE_PULSING_BRIGHT || state == State.DOZE_PULSE_DONE;
        Preconditions.checkState(z, "must be in pulsing state, but is " + this.mState);
        return this.mPulseReason;
    }

    public State getState() {
        Assert.isMainThread();
        if (isExecutingTransition()) {
            throw new IllegalStateException("Cannot get state because there were pending transitions: " + this.mQueuedRequests);
        }
        return this.mState;
    }

    public boolean isExecutingTransition() {
        return !this.mQueuedRequests.isEmpty();
    }

    public boolean isUninitializedOrFinished() {
        State state = this.mState;
        return state == State.UNINITIALIZED || state == State.FINISH;
    }

    public void onConfigurationChanged(Configuration configuration) {
        int i = configuration.uiMode & 15;
        if (this.mUiModeType == i) {
            return;
        }
        this.mUiModeType = i;
        for (Part part : this.mParts) {
            part.onUiModeTypeChanged(this.mUiModeType);
        }
    }

    public void onScreenState(int i) {
        this.mDozeLog.traceDisplayState(i);
        for (Part part : this.mParts) {
            part.onScreenState(i);
        }
    }

    public final void performTransitionOnComponents(State state, State state2) {
        for (Part part : this.mParts) {
            part.transitionTo(state, state2);
        }
        this.mDozeLog.traceDozeStateSendComplete(state2);
        if (state2 == State.FINISH) {
            this.mDozeService.finish();
        }
    }

    public void requestPulse(int i) {
        Preconditions.checkState(!isExecutingTransition());
        requestState(State.DOZE_REQUEST_PULSE, i);
    }

    public void requestState(State state) {
        Preconditions.checkArgument(state != State.DOZE_REQUEST_PULSE);
        requestState(state, -1);
    }

    public final void requestState(State state, int i) {
        Assert.isMainThread();
        if (DEBUG) {
            Log.i("DozeMachine", "request: current=" + this.mState + " req=" + state, new Throwable("here"));
        }
        boolean isExecutingTransition = isExecutingTransition();
        this.mQueuedRequests.add(state);
        if (!isExecutingTransition) {
            this.mWakeLock.acquire("DozeMachine#requestState");
            for (int i2 = 0; i2 < this.mQueuedRequests.size(); i2++) {
                transitionTo(this.mQueuedRequests.get(i2), i);
            }
            this.mQueuedRequests.clear();
            this.mWakeLock.release("DozeMachine#requestState");
        }
    }

    public final void resolveIntermediateState(State state) {
        int i = AnonymousClass1.$SwitchMap$com$android$systemui$doze$DozeMachine$State[state.ordinal()];
        if (i == 10 || i == 13) {
            int wakefulness = this.mWakefulnessLifecycle.getWakefulness();
            transitionTo((state == State.INITIALIZED || !(wakefulness == 2 || wakefulness == 1)) ? this.mDockManager.isDocked() ? this.mDockManager.isHidden() ? State.DOZE : State.DOZE_AOD_DOCKED : this.mAmbientDisplayConfig.alwaysOnEnabled(-2) ? State.DOZE_AOD : State.DOZE : State.FINISH, -1);
        }
    }

    public final State transitionPolicy(State state) {
        State state2 = this.mState;
        State state3 = State.FINISH;
        if (state2 == state3) {
            return state3;
        }
        if (this.mUiModeType == 3 && (state.canPulse() || state.staysAwake())) {
            Log.i("DozeMachine", "Doze is suppressed with all triggers disabled as car mode is active");
            this.mDozeLog.traceCarModeStarted();
            return State.DOZE_SUSPEND_TRIGGERS;
        } else if (this.mDozeHost.isAlwaysOnSuppressed() && state.isAlwaysOn()) {
            Log.i("DozeMachine", "Doze is suppressed by an app. Suppressing state: " + state);
            this.mDozeLog.traceAlwaysOnSuppressed(state, "app");
            return State.DOZE;
        } else if (this.mDozeHost.isPowerSaveActive() && state.isAlwaysOn()) {
            Log.i("DozeMachine", "Doze is suppressed by battery saver. Suppressing state: " + state);
            this.mDozeLog.traceAlwaysOnSuppressed(state, "batterySaver");
            return State.DOZE;
        } else {
            State state4 = this.mState;
            if ((state4 == State.DOZE_AOD_PAUSED || state4 == State.DOZE_AOD_PAUSING || state4 == State.DOZE_AOD || state4 == State.DOZE || state4 == State.DOZE_AOD_DOCKED) && state == State.DOZE_PULSE_DONE) {
                Log.i("DozeMachine", "Dropping pulse done because current state is already done: " + this.mState);
                return this.mState;
            } else if (state != State.DOZE_REQUEST_PULSE || state4.canPulse()) {
                return state;
            } else {
                Log.i("DozeMachine", "Dropping pulse request because current state can't pulse: " + this.mState);
                return this.mState;
            }
        }
    }

    public final void transitionTo(State state, int i) {
        State transitionPolicy = transitionPolicy(state);
        if (DEBUG) {
            Log.i("DozeMachine", "transition: old=" + this.mState + " req=" + state + " new=" + transitionPolicy);
        }
        if (transitionPolicy == this.mState) {
            return;
        }
        validateTransition(transitionPolicy);
        State state2 = this.mState;
        this.mState = transitionPolicy;
        this.mDozeLog.traceState(transitionPolicy);
        Trace.traceCounter(4096L, "doze_machine_state", transitionPolicy.ordinal());
        updatePulseReason(transitionPolicy, state2, i);
        performTransitionOnComponents(state2, transitionPolicy);
        updateWakeLockState(transitionPolicy);
        resolveIntermediateState(transitionPolicy);
    }

    public final void updatePulseReason(State state, State state2, int i) {
        if (state == State.DOZE_REQUEST_PULSE) {
            this.mPulseReason = i;
        } else if (state2 == State.DOZE_PULSE_DONE) {
            this.mPulseReason = -1;
        }
    }

    public final void updateWakeLockState(State state) {
        boolean staysAwake = state.staysAwake();
        boolean z = this.mWakeLockHeldForCurrentState;
        if (z && !staysAwake) {
            this.mWakeLock.release("DozeMachine#heldForState");
            this.mWakeLockHeldForCurrentState = false;
        } else if (z || !staysAwake) {
        } else {
            this.mWakeLock.acquire("DozeMachine#heldForState");
            this.mWakeLockHeldForCurrentState = true;
        }
    }

    public final void validateTransition(State state) {
        try {
            int[] iArr = AnonymousClass1.$SwitchMap$com$android$systemui$doze$DozeMachine$State;
            int i = iArr[this.mState.ordinal()];
            if (i == 9) {
                Preconditions.checkState(state == State.INITIALIZED);
            } else if (i == 12) {
                Preconditions.checkState(state == State.FINISH);
            }
            int i2 = iArr[state.ordinal()];
            if (i2 == 7) {
                Preconditions.checkState(this.mState == State.DOZE_REQUEST_PULSE);
            } else if (i2 != 13) {
                if (i2 == 9) {
                    throw new IllegalArgumentException("can't transition to UNINITIALIZED");
                }
                if (i2 != 10) {
                    return;
                }
                Preconditions.checkState(this.mState == State.UNINITIALIZED);
            } else {
                State state2 = this.mState;
                boolean z = true;
                if (state2 != State.DOZE_REQUEST_PULSE) {
                    z = true;
                    if (state2 != State.DOZE_PULSING) {
                        z = state2 == State.DOZE_PULSING_BRIGHT;
                    }
                }
                Preconditions.checkState(z);
            }
        } catch (RuntimeException e) {
            throw new IllegalStateException("Illegal Transition: " + this.mState + " -> " + state, e);
        }
    }

    public void wakeUp(int i) {
        this.mDozeService.requestWakeUp(i);
    }
}