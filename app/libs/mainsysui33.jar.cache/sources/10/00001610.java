package com.android.systemui.doze;

import android.hardware.display.AmbientDisplayConfiguration;
import android.util.Log;
import com.android.systemui.dock.DockManager;
import com.android.systemui.doze.DozeMachine;
import java.io.PrintWriter;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeDockHandler.class */
public class DozeDockHandler implements DozeMachine.Part {
    public static final boolean DEBUG = DozeService.DEBUG;
    public final AmbientDisplayConfiguration mConfig;
    public final DockManager mDockManager;
    public DozeMachine mMachine;
    public int mDockState = 0;
    public final DockEventListener mDockEventListener = new DockEventListener();

    /* renamed from: com.android.systemui.doze.DozeDockHandler$1  reason: invalid class name */
    /* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeDockHandler$1.class */
    public static /* synthetic */ class AnonymousClass1 {
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

    /* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeDockHandler$DockEventListener.class */
    public class DockEventListener implements DockManager.DockEventListener {
        public boolean mRegistered;

        public DockEventListener() {
        }

        public final boolean isPulsing() {
            DozeMachine.State state = DozeDockHandler.this.mMachine.getState();
            return state == DozeMachine.State.DOZE_REQUEST_PULSE || state == DozeMachine.State.DOZE_PULSING || state == DozeMachine.State.DOZE_PULSING_BRIGHT;
        }

        @Override // com.android.systemui.dock.DockManager.DockEventListener
        public void onEvent(int i) {
            DozeMachine.State state;
            if (DozeDockHandler.DEBUG) {
                Log.d("DozeDockHandler", "dock event = " + i);
            }
            if (DozeDockHandler.this.mDockState == i) {
                return;
            }
            DozeDockHandler.this.mDockState = i;
            if (isPulsing()) {
                return;
            }
            int i2 = DozeDockHandler.this.mDockState;
            if (i2 == 0) {
                state = DozeDockHandler.this.mConfig.alwaysOnEnabled(-2) ? DozeMachine.State.DOZE_AOD : DozeMachine.State.DOZE;
            } else if (i2 == 1) {
                state = DozeMachine.State.DOZE_AOD_DOCKED;
            } else if (i2 != 2) {
                return;
            } else {
                state = DozeMachine.State.DOZE;
            }
            DozeDockHandler.this.mMachine.requestState(state);
        }

        public void register() {
            if (this.mRegistered) {
                return;
            }
            if (DozeDockHandler.this.mDockManager != null) {
                DozeDockHandler.this.mDockManager.addListener(this);
            }
            this.mRegistered = true;
        }

        public void unregister() {
            if (this.mRegistered) {
                if (DozeDockHandler.this.mDockManager != null) {
                    DozeDockHandler.this.mDockManager.removeListener(this);
                }
                this.mRegistered = false;
            }
        }
    }

    public DozeDockHandler(AmbientDisplayConfiguration ambientDisplayConfiguration, DockManager dockManager) {
        this.mConfig = ambientDisplayConfiguration;
        this.mDockManager = dockManager;
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public void dump(PrintWriter printWriter) {
        printWriter.println("DozeDockHandler:");
        printWriter.println(" dockState=" + this.mDockState);
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public void setDozeMachine(DozeMachine dozeMachine) {
        this.mMachine = dozeMachine;
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        int i = AnonymousClass1.$SwitchMap$com$android$systemui$doze$DozeMachine$State[state2.ordinal()];
        if (i == 1) {
            this.mDockEventListener.register();
        } else if (i != 2) {
        } else {
            this.mDockEventListener.unregister();
        }
    }
}