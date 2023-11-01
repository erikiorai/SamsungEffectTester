package com.android.systemui.doze;

import android.app.AlarmManager;
import android.os.Handler;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.util.AlarmTimeout;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/DozePauser.class */
public class DozePauser implements DozeMachine.Part {
    public static final String TAG = "DozePauser";
    public DozeMachine mMachine;
    public final AlarmTimeout mPauseTimeout;
    public final AlwaysOnDisplayPolicy mPolicy;

    /* renamed from: com.android.systemui.doze.DozePauser$1 */
    /* loaded from: mainsysui33.jar:com/android/systemui/doze/DozePauser$1.class */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$doze$DozeMachine$State;

        static {
            int[] iArr = new int[DozeMachine.State.values().length];
            $SwitchMap$com$android$systemui$doze$DozeMachine$State = iArr;
            try {
                iArr[DozeMachine.State.DOZE_AOD_PAUSING.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.doze.DozePauser$$ExternalSyntheticLambda0.onAlarm():void] */
    public static /* synthetic */ void $r8$lambda$FVkmNuZ6KH2bD4WjyZqi1lKUmUE(DozePauser dozePauser) {
        dozePauser.onTimeout();
    }

    public DozePauser(Handler handler, AlarmManager alarmManager, AlwaysOnDisplayPolicy alwaysOnDisplayPolicy) {
        this.mPauseTimeout = new AlarmTimeout(alarmManager, new AlarmManager.OnAlarmListener() { // from class: com.android.systemui.doze.DozePauser$$ExternalSyntheticLambda0
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                DozePauser.$r8$lambda$FVkmNuZ6KH2bD4WjyZqi1lKUmUE(DozePauser.this);
            }
        }, TAG, handler);
        this.mPolicy = alwaysOnDisplayPolicy;
    }

    public final void onTimeout() {
        this.mMachine.requestState(DozeMachine.State.DOZE_AOD_PAUSED);
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public void setDozeMachine(DozeMachine dozeMachine) {
        this.mMachine = dozeMachine;
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        if (AnonymousClass1.$SwitchMap$com$android$systemui$doze$DozeMachine$State[state2.ordinal()] != 1) {
            this.mPauseTimeout.cancel();
        } else {
            this.mPauseTimeout.schedule(this.mPolicy.proxScreenOffDelayMs, 1);
        }
    }
}