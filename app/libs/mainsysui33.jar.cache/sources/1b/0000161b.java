package com.android.systemui.doze;

import android.util.TimeUtils;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.Dumpable;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.dump.DumpManager;
import com.google.errorprone.annotations.CompileTimeConstant;
import java.io.PrintWriter;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeLog.class */
public class DozeLog implements Dumpable {
    public final DozeLogger mLogger;
    public boolean mPulsing;
    public final KeyguardUpdateMonitorCallback mKeyguardCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.doze.DozeLog.1
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onEmergencyCallAction() {
            DozeLog.this.traceEmergencyCall();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onFinishedGoingToSleep(int i) {
            DozeLog.this.traceScreenOff(i);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onKeyguardBouncerFullyShowingChanged(boolean z) {
            DozeLog.this.traceKeyguardBouncerChanged(z);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onKeyguardVisibilityChanged(boolean z) {
            DozeLog.this.traceKeyguard(z);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onStartedWakingUp() {
            DozeLog.this.traceScreenOn();
        }
    };
    public long mSince = System.currentTimeMillis();
    public SummaryStats mPickupPulseNearVibrationStats = new SummaryStats();
    public SummaryStats mPickupPulseNotNearVibrationStats = new SummaryStats();
    public SummaryStats mNotificationPulseStats = new SummaryStats();
    public SummaryStats mScreenOnPulsingStats = new SummaryStats();
    public SummaryStats mScreenOnNotPulsingStats = new SummaryStats();
    public SummaryStats mEmergencyCallStats = new SummaryStats();
    public SummaryStats[][] mProxStats = new SummaryStats[12][2];

    /* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeLog$SummaryStats.class */
    public class SummaryStats {
        public int mCount;

        public SummaryStats() {
        }

        public void append() {
            this.mCount++;
        }

        public void dump(PrintWriter printWriter, String str) {
            if (this.mCount == 0) {
                return;
            }
            printWriter.print("    ");
            printWriter.print(str);
            printWriter.print(": n=");
            printWriter.print(this.mCount);
            printWriter.print(" (");
            printWriter.print((this.mCount / (System.currentTimeMillis() - DozeLog.this.mSince)) * 1000.0d * 60.0d * 60.0d);
            printWriter.print("/hr)");
            printWriter.println();
        }
    }

    public DozeLog(KeyguardUpdateMonitor keyguardUpdateMonitor, DumpManager dumpManager, DozeLogger dozeLogger) {
        this.mLogger = dozeLogger;
        for (int i = 0; i < 12; i++) {
            this.mProxStats[i][0] = new SummaryStats();
            this.mProxStats[i][1] = new SummaryStats();
        }
        if (keyguardUpdateMonitor != null) {
            keyguardUpdateMonitor.registerCallback(this.mKeyguardCallback);
        }
        dumpManager.registerDumpable("DumpStats", this);
    }

    public static int getPowerManagerWakeReason(int i) {
        if (i != 3) {
            if (i != 4) {
                if (i != 6) {
                    if (i != 9) {
                        return i != 10 ? 4 : 17;
                    }
                    return 15;
                }
                return 3;
            }
            return 15;
        }
        return 16;
    }

    public static String reasonToString(int i) {
        switch (i) {
            case 0:
                return "intent";
            case 1:
                return "notification";
            case 2:
                return "sigmotion";
            case 3:
                return "pickup";
            case 4:
                return "doubletap";
            case 5:
                return "longpress";
            case 6:
                return "docking";
            case 7:
                return "presence-wakeup";
            case 8:
                return "reach-wakelockscreen";
            case 9:
                return "tap";
            case 10:
                return "udfps";
            case 11:
                return "quickPickup";
            default:
                throw new IllegalArgumentException("invalid reason: " + i);
        }
    }

    public void d(@CompileTimeConstant String str) {
        this.mLogger.log(str);
    }

    @Override // com.android.systemui.Dumpable
    public void dump(PrintWriter printWriter, String[] strArr) {
        synchronized (DozeLog.class) {
            try {
                printWriter.print("  Doze summary stats (for ");
                TimeUtils.formatDuration(System.currentTimeMillis() - this.mSince, printWriter);
                printWriter.println("):");
                this.mPickupPulseNearVibrationStats.dump(printWriter, "Pickup pulse (near vibration)");
                this.mPickupPulseNotNearVibrationStats.dump(printWriter, "Pickup pulse (not near vibration)");
                this.mNotificationPulseStats.dump(printWriter, "Notification pulse");
                this.mScreenOnPulsingStats.dump(printWriter, "Screen on (pulsing)");
                this.mScreenOnNotPulsingStats.dump(printWriter, "Screen on (not pulsing)");
                this.mEmergencyCallStats.dump(printWriter, "Emergency call");
                for (int i = 0; i < 12; i++) {
                    String reasonToString = reasonToString(i);
                    this.mProxStats[i][0].dump(printWriter, "Proximity near (" + reasonToString + ")");
                    this.mProxStats[i][1].dump(printWriter, "Proximity far (" + reasonToString + ")");
                }
            } finally {
            }
        }
    }

    public void traceAlwaysOnSuppressed(DozeMachine.State state, String str) {
        this.mLogger.logAlwaysOnSuppressed(state, str);
    }

    public void traceAlwaysOnSuppressedChange(boolean z, DozeMachine.State state) {
        this.mLogger.logAlwaysOnSuppressedChange(z, state);
    }

    public void traceCarModeEnded() {
        this.mLogger.logCarModeEnded();
    }

    public void traceCarModeStarted() {
        this.mLogger.logCarModeStarted();
    }

    public void traceDisplayState(int i) {
        this.mLogger.logDisplayStateChanged(i);
    }

    public void traceDisplayStateDelayedByUdfps(int i) {
        this.mLogger.logDisplayStateDelayedByUdfps(i);
    }

    public void traceDozeScreenBrightness(int i) {
        this.mLogger.logDozeScreenBrightness(i);
    }

    public void traceDozeStateSendComplete(DozeMachine.State state) {
        this.mLogger.logStateChangedSent(state);
    }

    public void traceDozing(boolean z) {
        this.mLogger.logDozing(z);
        this.mPulsing = false;
    }

    public void traceDozingChanged(boolean z) {
        this.mLogger.logDozingChanged(z);
    }

    public void traceEmergencyCall() {
        this.mLogger.logEmergencyCall();
        this.mEmergencyCallStats.append();
    }

    public void traceFling(boolean z, boolean z2, boolean z3) {
        this.mLogger.logFling(z, z2, z3);
    }

    public void traceImmediatelyEndDoze(String str) {
        this.mLogger.logImmediatelyEndDoze(str);
    }

    public void traceKeyguard(boolean z) {
        this.mLogger.logKeyguardVisibilityChange(z);
        if (z) {
            return;
        }
        this.mPulsing = false;
    }

    public void traceKeyguardBouncerChanged(boolean z) {
        this.mLogger.logKeyguardBouncerChanged(z);
    }

    public void traceMissedTick(String str) {
        this.mLogger.logMissedTick(str);
    }

    public void traceNotificationPulse() {
        this.mLogger.logNotificationPulse();
        this.mNotificationPulseStats.append();
    }

    public void tracePickupWakeUp(boolean z) {
        this.mLogger.logPickupWakeup(z);
        (z ? this.mPickupPulseNearVibrationStats : this.mPickupPulseNotNearVibrationStats).append();
    }

    public void tracePluginSensorUpdate(boolean z) {
        if (z) {
            this.mLogger.log("register plugin sensor");
        } else {
            this.mLogger.log("unregister plugin sensor");
        }
    }

    public void tracePostureChanged(int i, String str) {
        this.mLogger.logPostureChanged(i, str);
    }

    public void tracePowerSaveChanged(boolean z, DozeMachine.State state) {
        this.mLogger.logPowerSaveChanged(z, state);
    }

    public void traceProximityResult(boolean z, long j, int i) {
        this.mLogger.logProximityResult(z, j, i);
        this.mProxStats[i][!z ? 1 : 0].append();
    }

    public void tracePulseDropped(String str) {
        this.mLogger.logPulseDropped(str);
    }

    public void tracePulseDropped(String str, DozeMachine.State state) {
        this.mLogger.logPulseDropped(str, state);
    }

    public void tracePulseEvent(String str, boolean z, int i) {
        this.mLogger.logPulseEvent(str, z, reasonToString(i));
    }

    public void tracePulseFinish() {
        this.mLogger.logPulseFinish();
        this.mPulsing = false;
    }

    public void tracePulseStart(int i) {
        this.mLogger.logPulseStart(i);
        this.mPulsing = true;
    }

    public void tracePulseTouchDisabledByProx(boolean z) {
        this.mLogger.logPulseTouchDisabledByProx(z);
    }

    public void traceScreenOff(int i) {
        this.mLogger.logScreenOff(i);
    }

    public void traceScreenOn() {
        this.mLogger.logScreenOn(this.mPulsing);
        (this.mPulsing ? this.mScreenOnPulsingStats : this.mScreenOnNotPulsingStats).append();
        this.mPulsing = false;
    }

    public void traceSensor(int i) {
        this.mLogger.logSensorTriggered(i);
    }

    public void traceSensorEventDropped(int i, String str) {
        this.mLogger.logSensorEventDropped(i, str);
    }

    public void traceSensorRegisterAttempt(String str, boolean z) {
        this.mLogger.logSensorRegisterAttempt(str, z);
    }

    public void traceSensorUnregisterAttempt(String str, boolean z) {
        this.mLogger.logSensorUnregisterAttempt(str, z);
    }

    public void traceSensorUnregisterAttempt(String str, boolean z, String str2) {
        this.mLogger.logSensorUnregisterAttempt(str, z, str2);
    }

    public void traceSetAodDimmingScrim(float f) {
        this.mLogger.logSetAodDimmingScrim(f);
    }

    public void traceSetIgnoreTouchWhilePulsing(boolean z) {
        this.mLogger.logSetIgnoreTouchWhilePulsing(z);
    }

    public void traceSkipRegisterSensor(String str) {
        this.mLogger.logSkipSensorRegistration(str);
    }

    public void traceState(DozeMachine.State state) {
        this.mLogger.logDozeStateChanged(state);
    }

    public void traceTimeTickScheduled(long j, long j2) {
        this.mLogger.logTimeTickScheduled(j, j2);
    }

    public void traceWakeDisplay(boolean z, int i) {
        this.mLogger.logWakeDisplay(z, i);
    }
}