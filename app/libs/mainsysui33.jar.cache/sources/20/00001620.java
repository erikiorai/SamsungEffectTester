package com.android.systemui.doze;

import android.view.Display;
import com.android.systemui.doze.DozeMachine;
import com.android.systemui.plugins.log.LogBuffer;
import com.android.systemui.plugins.log.LogLevel;
import com.android.systemui.plugins.log.LogMessage;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.google.errorprone.annotations.CompileTimeConstant;
import java.util.Date;
import kotlin.jvm.functions.Function1;

/* loaded from: mainsysui33.jar:com/android/systemui/doze/DozeLogger.class */
public final class DozeLogger {
    public final LogBuffer buffer;

    public DozeLogger(LogBuffer logBuffer) {
        this.buffer = logBuffer;
    }

    public final void log(@CompileTimeConstant String str) {
        this.buffer.log("DozeLog", LogLevel.DEBUG, str);
    }

    public final void logAlwaysOnSuppressed(DozeMachine.State state, String str) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", LogLevel.INFO, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logAlwaysOnSuppressed$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                return "Always-on state suppressed, suppressed state=" + str1 + " reason=" + str2;
            }
        }, null);
        obtain.setStr1(state.name());
        obtain.setStr2(str);
        logBuffer.commit(obtain);
    }

    public final void logAlwaysOnSuppressedChange(boolean z, DozeMachine.State state) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", LogLevel.INFO, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logAlwaysOnSuppressedChange$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                boolean bool1 = logMessage.getBool1();
                String str1 = logMessage.getStr1();
                return "Always on (AOD) suppressed changed, suppressed=" + bool1 + " nextState=" + str1;
            }
        }, null);
        obtain.setBool1(z);
        obtain.setStr1(state.name());
        logBuffer.commit(obtain);
    }

    public final void logCarModeEnded() {
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain("DozeLog", LogLevel.INFO, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logCarModeEnded$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                return "Doze car mode ended";
            }
        }, null));
    }

    public final void logCarModeStarted() {
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain("DozeLog", LogLevel.INFO, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logCarModeStarted$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                return "Doze car mode started";
            }
        }, null));
    }

    public final void logDisplayStateChanged(int i) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", LogLevel.INFO, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logDisplayStateChanged$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "Display state changed to " + str1;
            }
        }, null);
        obtain.setStr1(Display.stateToString(i));
        logBuffer.commit(obtain);
    }

    public final void logDisplayStateDelayedByUdfps(int i) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", LogLevel.INFO, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logDisplayStateDelayedByUdfps$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "Delaying display state change to: " + str1 + " due to UDFPS activity";
            }
        }, null);
        obtain.setStr1(Display.stateToString(i));
        logBuffer.commit(obtain);
    }

    public final void logDozeScreenBrightness(int i) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", LogLevel.INFO, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logDozeScreenBrightness$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                return "Doze screen brightness set, brightness=" + int1;
            }
        }, null);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logDozeStateChanged(DozeMachine.State state) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", LogLevel.INFO, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logDozeStateChanged$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "Doze state changed to " + str1;
            }
        }, null);
        obtain.setStr1(state.name());
        logBuffer.commit(obtain);
    }

    public final void logDozing(boolean z) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", LogLevel.INFO, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logDozing$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                boolean bool1 = logMessage.getBool1();
                return "Dozing=" + bool1;
            }
        }, null);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }

    public final void logDozingChanged(boolean z) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", LogLevel.INFO, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logDozingChanged$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                boolean bool1 = logMessage.getBool1();
                return "Dozing changed dozing=" + bool1;
            }
        }, null);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }

    public final void logEmergencyCall() {
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain("DozeLog", LogLevel.INFO, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logEmergencyCall$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                return "Emergency call";
            }
        }, null));
    }

    public final void logFling(boolean z, boolean z2, boolean z3) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logFling$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                boolean bool3 = logMessage.getBool3();
                boolean bool4 = logMessage.getBool4();
                return "Fling expand=" + bool1 + " aboveThreshold=" + bool2 + " thresholdNeeded=" + bool3 + " screenOnFromTouch=" + bool4;
            }
        }, null);
        obtain.setBool1(z);
        obtain.setBool2(z2);
        obtain.setBool4(z3);
        logBuffer.commit(obtain);
    }

    public final void logImmediatelyEndDoze(String str) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", LogLevel.INFO, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logImmediatelyEndDoze$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "Doze immediately ended due to " + str1;
            }
        }, null);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logKeyguardBouncerChanged(boolean z) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", LogLevel.INFO, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logKeyguardBouncerChanged$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                boolean bool1 = logMessage.getBool1();
                return "Keyguard bouncer changed, showing=" + bool1;
            }
        }, null);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }

    public final void logKeyguardVisibilityChange(boolean z) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", LogLevel.INFO, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logKeyguardVisibilityChange$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                boolean bool1 = logMessage.getBool1();
                return "Keyguard visibility change, isVisible=" + bool1;
            }
        }, null);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }

    public final void logMissedTick(String str) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", LogLevel.ERROR, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logMissedTick$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "Missed AOD time tick by " + str1;
            }
        }, null);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logNotificationPulse() {
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain("DozeLog", LogLevel.INFO, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logNotificationPulse$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                return "Notification pulse";
            }
        }, null));
    }

    public final void logPickupWakeup(boolean z) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logPickupWakeup$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                boolean bool1 = logMessage.getBool1();
                return "PickupWakeup withinVibrationThreshold=" + bool1;
            }
        }, null);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }

    public final void logPostureChanged(int i, String str) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", LogLevel.INFO, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logPostureChanged$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String devicePostureToString = DevicePostureController.devicePostureToString(logMessage.getInt1());
                String str1 = logMessage.getStr1();
                return "Posture changed, posture=" + devicePostureToString + " partUpdated=" + str1;
            }
        }, null);
        obtain.setInt1(i);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logPowerSaveChanged(boolean z, DozeMachine.State state) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", LogLevel.INFO, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logPowerSaveChanged$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                boolean bool1 = logMessage.getBool1();
                String str1 = logMessage.getStr1();
                return "Power save active=" + bool1 + " nextState=" + str1;
            }
        }, null);
        obtain.setBool1(z);
        obtain.setStr1(state.name());
        logBuffer.commit(obtain);
    }

    public final void logProximityResult(boolean z, long j, int i) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logProximityResult$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String reasonToString = DozeLog.reasonToString(logMessage.getInt1());
                boolean bool1 = logMessage.getBool1();
                long long1 = logMessage.getLong1();
                return "Proximity result reason=" + reasonToString + " near=" + bool1 + " millis=" + long1;
            }
        }, null);
        obtain.setBool1(z);
        obtain.setLong1(j);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logPulseDropped(String str) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", LogLevel.INFO, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logPulseDropped$4
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "Pulse dropped, why=" + str1;
            }
        }, null);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logPulseDropped(String str, DozeMachine.State state) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", LogLevel.INFO, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logPulseDropped$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                return "Pulse dropped, cannot pulse from=" + str1 + " state=" + str2;
            }
        }, null);
        obtain.setStr1(str);
        String str2 = null;
        if (state != null) {
            str2 = state.name();
        }
        obtain.setStr2(str2);
        logBuffer.commit(obtain);
    }

    public final void logPulseEvent(String str, boolean z, String str2) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logPulseEvent$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                boolean bool1 = logMessage.getBool1();
                String str22 = logMessage.getStr2();
                return "Pulse-" + str1 + " dozing=" + bool1 + " pulseReason=" + str22;
            }
        }, null);
        obtain.setStr1(str);
        obtain.setBool1(z);
        obtain.setStr2(str2);
        logBuffer.commit(obtain);
    }

    public final void logPulseFinish() {
        LogBuffer logBuffer = this.buffer;
        logBuffer.commit(logBuffer.obtain("DozeLog", LogLevel.INFO, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logPulseFinish$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                return "Pulse finish";
            }
        }, null));
    }

    public final void logPulseStart(int i) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", LogLevel.INFO, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logPulseStart$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String reasonToString = DozeLog.reasonToString(logMessage.getInt1());
                return "Pulse start, reason=" + reasonToString;
            }
        }, null);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logPulseTouchDisabledByProx(boolean z) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logPulseTouchDisabledByProx$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                boolean bool1 = logMessage.getBool1();
                return "Pulse touch modified by prox, disabled=" + bool1;
            }
        }, null);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }

    public final void logScreenOff(int i) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", LogLevel.INFO, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logScreenOff$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                return "Screen off, why=" + int1;
            }
        }, null);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logScreenOn(boolean z) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", LogLevel.INFO, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logScreenOn$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                boolean bool1 = logMessage.getBool1();
                return "Screen on, pulsing=" + bool1;
            }
        }, null);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }

    public final void logSensorEventDropped(int i, String str) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", LogLevel.INFO, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logSensorEventDropped$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                String str1 = logMessage.getStr1();
                return "SensorEvent [" + int1 + "] dropped, reason=" + str1;
            }
        }, null);
        obtain.setInt1(i);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logSensorRegisterAttempt(String str, boolean z) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", LogLevel.INFO, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logSensorRegisterAttempt$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                boolean bool1 = logMessage.getBool1();
                String str1 = logMessage.getStr1();
                return "Register sensor. Success=" + bool1 + " sensor=" + str1;
            }
        }, null);
        obtain.setStr1(str);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }

    public final void logSensorTriggered(int i) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logSensorTriggered$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String reasonToString = DozeLog.reasonToString(logMessage.getInt1());
                return "Sensor triggered, type=" + reasonToString;
            }
        }, null);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logSensorUnregisterAttempt(String str, boolean z) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", LogLevel.INFO, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logSensorUnregisterAttempt$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                boolean bool1 = logMessage.getBool1();
                String str1 = logMessage.getStr1();
                return "Unregister sensor. Success=" + bool1 + " sensor=" + str1;
            }
        }, null);
        obtain.setStr1(str);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }

    public final void logSensorUnregisterAttempt(String str, boolean z, String str2) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", LogLevel.INFO, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logSensorUnregisterAttempt$4
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str22 = logMessage.getStr2();
                boolean bool1 = logMessage.getBool1();
                String str1 = logMessage.getStr1();
                return "Unregister sensor. reason=" + str22 + ". Success=" + bool1 + " sensor=" + str1;
            }
        }, null);
        obtain.setStr1(str);
        obtain.setBool1(z);
        obtain.setStr2(str2);
        logBuffer.commit(obtain);
    }

    public final void logSetAodDimmingScrim(long j) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", LogLevel.INFO, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logSetAodDimmingScrim$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                long long1 = logMessage.getLong1();
                return "Doze aod dimming scrim opacity set, opacity=" + long1;
            }
        }, null);
        obtain.setLong1(j);
        logBuffer.commit(obtain);
    }

    public final void logSetIgnoreTouchWhilePulsing(boolean z) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logSetIgnoreTouchWhilePulsing$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                boolean bool1 = logMessage.getBool1();
                return "Prox changed while pulsing. setIgnoreTouchWhilePulsing=" + bool1;
            }
        }, null);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }

    public final void logSkipSensorRegistration(String str) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logSkipSensorRegistration$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "Skipping sensor registration because its already registered. sensor=" + str1;
            }
        }, null);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logStateChangedSent(DozeMachine.State state) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", LogLevel.INFO, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logStateChangedSent$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "Doze state sent to all DozeMachineParts stateSent=" + str1;
            }
        }, null);
        obtain.setStr1(state.name());
        logBuffer.commit(obtain);
    }

    public final void logTimeTickScheduled(long j, long j2) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logTimeTickScheduled$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String format = DozeLoggerKt.getDATE_FORMAT().format(new Date(logMessage.getLong1()));
                String format2 = DozeLoggerKt.getDATE_FORMAT().format(new Date(logMessage.getLong2()));
                return "Time tick scheduledAt=" + format + " triggerAt=" + format2;
            }
        }, null);
        obtain.setLong1(j);
        obtain.setLong2(j2);
        logBuffer.commit(obtain);
    }

    public final void logWakeDisplay(boolean z, int i) {
        LogBuffer logBuffer = this.buffer;
        LogMessage obtain = logBuffer.obtain("DozeLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.systemui.doze.DozeLogger$logWakeDisplay$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                boolean bool1 = logMessage.getBool1();
                String reasonToString = DozeLog.reasonToString(logMessage.getInt1());
                return "Display wakefulness changed, isAwake=" + bool1 + ", reason=" + reasonToString;
            }
        }, null);
        obtain.setBool1(z);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }
}