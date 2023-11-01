package com.android.systemui.doze;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.text.format.Formatter;
import android.util.Log;
import com.android.systemui.doze.DozeHost;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.DozeParameters;
import com.android.systemui.util.AlarmTimeout;
import com.android.systemui.util.wakelock.WakeLock;
import java.util.Calendar;
import java.util.Objects;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeUi.class */
public class DozeUi implements DozeMachine.Part {
    public final boolean mCanAnimateTransition;
    public final Context mContext;
    public final DozeLog mDozeLog;
    public final DozeParameters mDozeParameters;
    public final Handler mHandler;
    public final DozeHost mHost;
    public long mLastTimeTickElapsed = 0;
    public DozeMachine mMachine;
    public final StatusBarStateController mStatusBarStateController;
    public final AlarmTimeout mTimeTicker;
    public final WakeLock mWakeLock;

    /* renamed from: com.android.systemui.doze.DozeUi$2 */
    /* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeUi$2.class */
    public static /* synthetic */ class AnonymousClass2 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$systemui$doze$DozeMachine$State;

        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:103:0x0095 -> B:137:0x0014). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:105:0x0099 -> B:131:0x001f). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:107:0x009d -> B:149:0x002a). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:109:0x00a1 -> B:141:0x0035). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:111:0x00a5 -> B:135:0x0040). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:113:0x00a9 -> B:129:0x004c). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:115:0x00ad -> B:147:0x0058). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:117:0x00b1 -> B:139:0x0064). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:119:0x00b5 -> B:133:0x0070). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:121:0x00b9 -> B:127:0x007c). Please submit an issue!!! */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:123:0x00bd -> B:145:0x0088). Please submit an issue!!! */
        static {
            int[] iArr = new int[DozeMachine.State.values().length];
            $SwitchMap$com$android$systemui$doze$DozeMachine$State = iArr;
            try {
                iArr[DozeMachine.State.DOZE_AOD.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD_DOCKED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD_PAUSING.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_AOD_PAUSED.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_SUSPEND_TRIGGERS.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_REQUEST_PULSE.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.INITIALIZED.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.FINISH.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_PULSING.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_PULSING_BRIGHT.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$com$android$systemui$doze$DozeMachine$State[DozeMachine.State.DOZE_PULSE_DONE.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
        }
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.doze.DozeUi$$ExternalSyntheticLambda0.onAlarm():void] */
    /* renamed from: $r8$lambda$2oXvcafRfs9agk1A4kbfyyGB-ok */
    public static /* synthetic */ void m2526$r8$lambda$2oXvcafRfs9agk1A4kbfyyGBok(DozeUi dozeUi) {
        dozeUi.onTimeTick();
    }

    /* JADX DEBUG: Method not inlined, still used in: [com.android.systemui.doze.DozeUi$$ExternalSyntheticLambda1.run():void] */
    public static /* synthetic */ void $r8$lambda$BB1OPQIP3EP9frhxxwOzPbv6P54() {
        lambda$onTimeTick$0();
    }

    public DozeUi(Context context, AlarmManager alarmManager, WakeLock wakeLock, DozeHost dozeHost, Handler handler, DozeParameters dozeParameters, StatusBarStateController statusBarStateController, DozeLog dozeLog) {
        this.mContext = context;
        this.mWakeLock = wakeLock;
        this.mHost = dozeHost;
        this.mHandler = handler;
        this.mCanAnimateTransition = !dozeParameters.getDisplayNeedsBlanking();
        this.mDozeParameters = dozeParameters;
        this.mTimeTicker = new AlarmTimeout(alarmManager, new AlarmManager.OnAlarmListener() { // from class: com.android.systemui.doze.DozeUi$$ExternalSyntheticLambda0
            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                DozeUi.m2526$r8$lambda$2oXvcafRfs9agk1A4kbfyyGBok(DozeUi.this);
            }
        }, "doze_time_tick", handler);
        this.mDozeLog = dozeLog;
        this.mStatusBarStateController = statusBarStateController;
    }

    public static /* synthetic */ void lambda$onTimeTick$0() {
    }

    public final void onTimeTick() {
        verifyLastTimeTick();
        this.mHost.dozeTimeTick();
        this.mHandler.post(this.mWakeLock.wrap(new Runnable() { // from class: com.android.systemui.doze.DozeUi$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                DozeUi.$r8$lambda$BB1OPQIP3EP9frhxxwOzPbv6P54();
            }
        }));
        scheduleTimeTick();
    }

    public final void pulseWhileDozing(final int i) {
        this.mHost.pulseWhileDozing(new DozeHost.PulseCallback() { // from class: com.android.systemui.doze.DozeUi.1
            {
                DozeUi.this = this;
            }

            @Override // com.android.systemui.doze.DozeHost.PulseCallback
            public void onPulseFinished() {
                DozeUi.this.mMachine.requestState(DozeMachine.State.DOZE_PULSE_DONE);
            }

            @Override // com.android.systemui.doze.DozeHost.PulseCallback
            public void onPulseStarted() {
                try {
                    DozeUi.this.mMachine.requestState(i == 8 ? DozeMachine.State.DOZE_PULSING_BRIGHT : DozeMachine.State.DOZE_PULSING);
                } catch (IllegalStateException e) {
                }
            }
        }, i);
    }

    public final long roundToNextMinute(long j) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j);
        calendar.set(14, 0);
        calendar.set(13, 0);
        calendar.add(12, 1);
        return calendar.getTimeInMillis();
    }

    public final void scheduleTimeTick() {
        if (this.mTimeTicker.isScheduled()) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        long roundToNextMinute = roundToNextMinute(currentTimeMillis) - System.currentTimeMillis();
        if (this.mTimeTicker.schedule(roundToNextMinute, 1)) {
            this.mDozeLog.traceTimeTickScheduled(currentTimeMillis, roundToNextMinute + currentTimeMillis);
        }
        this.mLastTimeTickElapsed = SystemClock.elapsedRealtime();
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public void setDozeMachine(DozeMachine dozeMachine) {
        this.mMachine = dozeMachine;
    }

    @Override // com.android.systemui.doze.DozeMachine.Part
    public void transitionTo(DozeMachine.State state, DozeMachine.State state2) {
        switch (AnonymousClass2.$SwitchMap$com$android$systemui$doze$DozeMachine$State[state2.ordinal()]) {
            case 1:
            case 2:
                if (state == DozeMachine.State.DOZE_AOD_PAUSED || state == DozeMachine.State.DOZE) {
                    this.mHost.dozeTimeTick();
                    Handler handler = this.mHandler;
                    WakeLock wakeLock = this.mWakeLock;
                    final DozeHost dozeHost = this.mHost;
                    Objects.requireNonNull(dozeHost);
                    handler.postDelayed(wakeLock.wrap(new Runnable() { // from class: com.android.systemui.doze.DozeUi$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            DozeHost.this.dozeTimeTick();
                        }
                    }), 500L);
                }
                scheduleTimeTick();
                break;
            case 3:
                scheduleTimeTick();
                break;
            case 4:
            case 5:
            case 6:
                unscheduleTimeTick();
                break;
            case 7:
                scheduleTimeTick();
                pulseWhileDozing(this.mMachine.getPulseReason());
                break;
            case 8:
                this.mHost.startDozing();
                break;
            case 9:
                this.mHost.stopDozing();
                unscheduleTimeTick();
                break;
        }
        updateAnimateWakeup(state2);
    }

    public final void unscheduleTimeTick() {
        if (this.mTimeTicker.isScheduled()) {
            verifyLastTimeTick();
            this.mTimeTicker.cancel();
        }
    }

    public final void updateAnimateWakeup(DozeMachine.State state) {
        boolean z = true;
        switch (AnonymousClass2.$SwitchMap$com$android$systemui$doze$DozeMachine$State[state.ordinal()]) {
            case 7:
            case 10:
            case 11:
            case 12:
                this.mHost.setAnimateWakeup(true);
                return;
            case 8:
            default:
                DozeHost dozeHost = this.mHost;
                if (!this.mCanAnimateTransition || !this.mDozeParameters.getAlwaysOn()) {
                    z = false;
                }
                dozeHost.setAnimateWakeup(z);
                return;
            case 9:
                return;
        }
    }

    public final void verifyLastTimeTick() {
        long elapsedRealtime = SystemClock.elapsedRealtime() - this.mLastTimeTickElapsed;
        if (elapsedRealtime > 90000) {
            String formatShortElapsedTime = Formatter.formatShortElapsedTime(this.mContext, elapsedRealtime);
            this.mDozeLog.traceMissedTick(formatShortElapsedTime);
            Log.e("DozeMachine", "Missed AOD time tick by " + formatShortElapsedTime);
        }
    }
}