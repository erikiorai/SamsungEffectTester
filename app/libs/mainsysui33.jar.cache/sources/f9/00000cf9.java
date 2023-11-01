package com.android.keyguard.logging;

import android.os.PowerManager;
import android.telephony.ServiceState;
import android.telephony.SubscriptionInfo;
import com.android.keyguard.ActiveUnlockConfig;
import com.android.keyguard.FaceAuthUiEvent;
import com.android.keyguard.KeyguardListenModel;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.TrustGrantFlags;
import com.android.systemui.plugins.log.LogBuffer;
import com.android.systemui.plugins.log.LogLevel;
import com.android.systemui.plugins.log.LogMessage;
import com.google.errorprone.annotations.CompileTimeConstant;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: mainsysui33.jar:com/android/keyguard/logging/KeyguardUpdateMonitorLogger.class */
public final class KeyguardUpdateMonitorLogger {
    public final LogBuffer logBuffer;

    public KeyguardUpdateMonitorLogger(LogBuffer logBuffer) {
        this.logBuffer = logBuffer;
    }

    public final void d(@CompileTimeConstant String str) {
        log(str, LogLevel.DEBUG);
    }

    public final void e(@CompileTimeConstant String str) {
        log(str, LogLevel.ERROR);
    }

    public final void log(@CompileTimeConstant String str, LogLevel logLevel) {
        this.logBuffer.log("KeyguardUpdateMonitorLog", logLevel, str);
    }

    public final void logActiveUnlockTriggered(String str) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("ActiveUnlock", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logActiveUnlockTriggered$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "initiate active unlock triggerReason=" + str1;
            }
        }, null);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logAuthInterruptDetected(boolean z) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logAuthInterruptDetected$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                boolean bool1 = logMessage.getBool1();
                return "onAuthInterruptDetected(" + bool1 + ")";
            }
        }, null);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }

    public final void logBroadcastReceived(String str) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logBroadcastReceived$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "received broadcast " + str1;
            }
        }, null);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logDeviceProvisionedState(boolean z) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logDeviceProvisionedState$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                boolean bool1 = logMessage.getBool1();
                return "DEVICE_PROVISIONED state = " + bool1;
            }
        }, null);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }

    public final void logException(Exception exc, @CompileTimeConstant final String str) {
        LogBuffer logBuffer = this.logBuffer;
        logBuffer.commit(logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.ERROR, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logException$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                return str;
            }
        }, exc));
    }

    public final void logFaceAcquired(int i) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logFaceAcquired$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                return "Face acquired acquireInfo=" + int1;
            }
        }, null);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logFaceAuthDisabledForUser(int i) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logFaceAuthDisabledForUser$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                return "Face authentication disabled by DPM for userId: " + int1;
            }
        }, null);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logFaceAuthError(int i, String str) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logFaceAuthError$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                return "Face error received: " + str1 + " msgId= " + int1;
            }
        }, null);
        obtain.setStr1(str);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logFaceAuthForWrongUser(int i) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logFaceAuthForWrongUser$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                return "Face authenticated for wrong user: " + int1;
            }
        }, null);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logFaceAuthHelpMsg(int i, String str) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logFaceAuthHelpMsg$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                String str1 = logMessage.getStr1();
                return "Face help received, msgId: " + int1 + " msg: " + str1;
            }
        }, null);
        obtain.setInt1(i);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logFaceAuthRequested(String str) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logFaceAuthRequested$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "requestFaceAuth() reason=" + str1;
            }
        }, null);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logFaceAuthSuccess(int i) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logFaceAuthSuccess$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                return "Face auth succeeded for user " + int1;
            }
        }, null);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logFaceLockoutReset(int i) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logFaceLockoutReset$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                return "handleFaceLockoutReset: " + int1;
            }
        }, null);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logFaceRunningState(int i) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logFaceRunningState$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                return "faceRunningState: " + int1;
            }
        }, null);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logFingerprintAuthForWrongUser(int i) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logFingerprintAuthForWrongUser$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                return "Fingerprint authenticated for wrong user: " + int1;
            }
        }, null);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logFingerprintDisabledForUser(int i) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logFingerprintDisabledForUser$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                return "Fingerprint disabled by DPM for userId: " + int1;
            }
        }, null);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logFingerprintError(int i, String str) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logFingerprintError$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                int int1 = logMessage.getInt1();
                return "Fingerprint error received: " + str1 + " msgId= " + int1;
            }
        }, null);
        obtain.setStr1(str);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logFingerprintLockoutReset(int i) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logFingerprintLockoutReset$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                return "handleFingerprintLockoutReset: " + int1;
            }
        }, null);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logFingerprintRunningState(int i) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logFingerprintRunningState$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                return "fingerprintRunningState: " + int1;
            }
        }, null);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logFingerprintSuccess(int i, boolean z) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logFingerprintSuccess$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                boolean bool1 = logMessage.getBool1();
                return "Fingerprint auth successful: userId: " + int1 + ", isStrongBiometric: " + bool1;
            }
        }, null);
        obtain.setInt1(i);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }

    public final void logInvalidSubId(int i) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.INFO, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logInvalidSubId$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                return "Previously active sub id " + int1 + " is now invalid, will remove";
            }
        }, null);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logKeyguardListenerModel(KeyguardListenModel keyguardListenModel) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.VERBOSE, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logKeyguardListenerModel$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                Intrinsics.checkNotNull(str1);
                return str1;
            }
        }, null);
        obtain.setStr1(String.valueOf(keyguardListenModel));
        logBuffer.commit(obtain);
    }

    public final void logKeyguardShowingChanged(boolean z, boolean z2, boolean z3) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logKeyguardShowingChanged$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                boolean bool3 = logMessage.getBool3();
                return "keyguardShowingChanged(showing=" + bool1 + " occluded=" + bool2 + " visible=" + bool3 + ")";
            }
        }, null);
        obtain.setBool1(z);
        obtain.setBool2(z2);
        obtain.setBool3(z3);
        logBuffer.commit(obtain);
    }

    public final void logKeyguardStateUpdate(boolean z, boolean z2, boolean z3, boolean z4) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardState", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logKeyguardStateUpdate$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                boolean bool3 = logMessage.getBool3();
                boolean bool4 = logMessage.getBool4();
                return "#update secure=" + bool1 + " canDismissKeyguard=" + bool2 + " trusted=" + bool3 + " trustManaged=" + bool4;
            }
        }, null);
        obtain.setBool1(z);
        obtain.setBool2(z2);
        obtain.setBool3(z3);
        obtain.setBool4(z4);
        logBuffer.commit(obtain);
    }

    public final void logMissingSupervisorAppError(int i) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.ERROR, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logMissingSupervisorAppError$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                return "No Profile Owner or Device Owner supervision app found for User " + int1;
            }
        }, null);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logPhoneStateChanged(String str) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logPhoneStateChanged$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "handlePhoneStateChanged(" + str1 + ")";
            }
        }, null);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logPrimaryKeyguardBouncerChanged(boolean z, boolean z2) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logPrimaryKeyguardBouncerChanged$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                return "handlePrimaryBouncerChanged primaryBouncerIsOrWillBeShowing=" + bool1 + " primaryBouncerFullyShown=" + bool2;
            }
        }, null);
        obtain.setBool1(z);
        obtain.setBool2(z2);
        logBuffer.commit(obtain);
    }

    public final void logRegisterCallback(KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.VERBOSE, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logRegisterCallback$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "*** register callback for " + str1;
            }
        }, null);
        obtain.setStr1(String.valueOf(keyguardUpdateMonitorCallback));
        logBuffer.commit(obtain);
    }

    public final void logRetryAfterFpErrorWithDelay(int i, String str, int i2) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logRetryAfterFpErrorWithDelay$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int2 = logMessage.getInt2();
                int int1 = logMessage.getInt1();
                String str1 = logMessage.getStr1();
                return "Fingerprint scheduling retry auth after " + int2 + " ms due to(" + int1 + ") -> " + str1;
            }
        }, null);
        obtain.setInt1(i);
        obtain.setInt2(i2);
        obtain.setStr1(String.valueOf(str));
        logBuffer.commit(obtain);
    }

    public final void logRetryAfterFpHwUnavailable(int i) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.WARNING, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logRetryAfterFpHwUnavailable$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                return "Retrying fingerprint attempt: " + int1;
            }
        }, null);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logRetryingAfterFaceHwUnavailable(int i) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.WARNING, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logRetryingAfterFaceHwUnavailable$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                return "Retrying face after HW unavailable, attempt " + int1;
            }
        }, null);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logSendPrimaryBouncerChanged(boolean z, boolean z2) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logSendPrimaryBouncerChanged$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                return "sendPrimaryBouncerChanged primaryBouncerIsOrWillBeShowing=" + bool1 + " primaryBouncerFullyShown=" + bool2;
            }
        }, null);
        obtain.setBool1(z);
        obtain.setBool2(z2);
        logBuffer.commit(obtain);
    }

    public final void logServiceStateChange(int i, ServiceState serviceState) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logServiceStateChange$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                String str1 = logMessage.getStr1();
                return "handleServiceStateChange(subId=" + int1 + ", serviceState=" + str1 + ")";
            }
        }, null);
        obtain.setInt1(i);
        obtain.setStr1(String.valueOf(serviceState));
        logBuffer.commit(obtain);
    }

    public final void logServiceStateIntent(String str, ServiceState serviceState, int i) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.VERBOSE, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logServiceStateIntent$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                int int1 = logMessage.getInt1();
                return "action " + str1 + " serviceState=" + str2 + " subId=" + int1;
            }
        }, null);
        obtain.setStr1(str);
        obtain.setStr2(String.valueOf(serviceState));
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logSimState(int i, int i2, int i3) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logSimState$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                long long1 = logMessage.getLong1();
                return "handleSimStateChange(subId=" + int1 + ", slotId=" + int2 + ", state=" + long1 + ")";
            }
        }, null);
        obtain.setInt1(i);
        obtain.setInt2(i2);
        obtain.setLong1(i3);
        logBuffer.commit(obtain);
    }

    public final void logSimStateFromIntent(String str, String str2, int i, int i2) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.VERBOSE, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logSimStateFromIntent$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                String str22 = logMessage.getStr2();
                int int1 = logMessage.getInt1();
                int int2 = logMessage.getInt2();
                return "action " + str1 + " state: " + str22 + " slotId: " + int1 + " subid: " + int2;
            }
        }, null);
        obtain.setStr1(str);
        obtain.setStr2(str2);
        obtain.setInt1(i);
        obtain.setInt2(i2);
        logBuffer.commit(obtain);
    }

    public final void logSimUnlocked(int i) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.VERBOSE, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logSimUnlocked$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                return "reportSimUnlocked(subId=" + int1 + ")";
            }
        }, null);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logSkipUpdateFaceListeningOnWakeup(int i) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.VERBOSE, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logSkipUpdateFaceListeningOnWakeup$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "Skip updating face listening state on wakeup from " + str1;
            }
        }, null);
        obtain.setStr1(PowerManager.wakeReasonToString(i));
        logBuffer.commit(obtain);
    }

    public final void logStartedListeningForFace(int i, FaceAuthUiEvent faceAuthUiEvent) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.VERBOSE, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logStartedListeningForFace$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                return "startListeningForFace(): " + int1 + ", reason: " + str1 + " " + str2;
            }
        }, null);
        obtain.setInt1(i);
        obtain.setStr1(faceAuthUiEvent.getReason());
        obtain.setStr2(faceAuthUiEvent.extraInfoToString());
        logBuffer.commit(obtain);
    }

    public final void logStoppedListeningForFace(int i, String str) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.VERBOSE, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logStoppedListeningForFace$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                String str1 = logMessage.getStr1();
                return "stopListeningForFace(): currentFaceRunningState: " + int1 + ", reason: " + str1;
            }
        }, null);
        obtain.setInt1(i);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logSubInfo(SubscriptionInfo subscriptionInfo) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.VERBOSE, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logSubInfo$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "SubInfo:" + str1;
            }
        }, null);
        obtain.setStr1(String.valueOf(subscriptionInfo));
        logBuffer.commit(obtain);
    }

    public final void logTimeFormatChanged(String str) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logTimeFormatChanged$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "handleTimeFormatUpdate timeFormat=" + str1;
            }
        }, null);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logTrustChanged(boolean z, boolean z2, int i) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logTrustChanged$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                return "onTrustChanged[user=" + int1 + "] wasTrusted=" + bool1 + " isNowTrusted=" + bool2;
            }
        }, null);
        obtain.setBool1(z);
        obtain.setBool2(z2);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logTrustGrantedWithFlags(int i, boolean z, int i2, String str) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logTrustGrantedWithFlags$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int2 = logMessage.getInt2();
                boolean bool1 = logMessage.getBool1();
                TrustGrantFlags trustGrantFlags = new TrustGrantFlags(logMessage.getInt1());
                String str1 = logMessage.getStr1();
                return "trustGrantedWithFlags[user=" + int2 + "] newlyUnlocked=" + bool1 + " flags=" + trustGrantFlags + " message=" + str1;
            }
        }, null);
        obtain.setInt1(i);
        obtain.setBool1(z);
        obtain.setInt2(i2);
        obtain.setStr1(str);
        logBuffer.commit(obtain);
    }

    public final void logUdfpsPointerDown(int i) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logUdfpsPointerDown$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                return "onUdfpsPointerDown, sensorId: " + int1;
            }
        }, null);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logUdfpsPointerUp(int i) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logUdfpsPointerUp$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                return "onUdfpsPointerUp, sensorId: " + int1;
            }
        }, null);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logUnexpectedFaceCancellationSignalState(int i, boolean z) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.ERROR, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logUnexpectedFaceCancellationSignalState$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                boolean bool1 = logMessage.getBool1();
                return "Cancellation signal is not null, high chance of bug in face auth lifecycle management. Face state: " + int1 + ", unlockPossible: " + bool1;
            }
        }, null);
        obtain.setInt1(i);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }

    public final void logUnexpectedFpCancellationSignalState(int i, boolean z) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.ERROR, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logUnexpectedFpCancellationSignalState$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                boolean bool1 = logMessage.getBool1();
                return "Cancellation signal is not null, high chance of bug in fp auth lifecycle management. FP state: " + int1 + ", unlockPossible: " + bool1;
            }
        }, null);
        obtain.setInt1(i);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }

    public final void logUnregisterCallback(KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", LogLevel.VERBOSE, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logUnregisterCallback$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                return "*** unregister callback for " + str1;
            }
        }, null);
        obtain.setStr1(String.valueOf(keyguardUpdateMonitorCallback));
        logBuffer.commit(obtain);
    }

    public final void logUserRequestedUnlock(ActiveUnlockConfig.ACTIVE_UNLOCK_REQUEST_ORIGIN active_unlock_request_origin, String str, boolean z) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("ActiveUnlock", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.KeyguardUpdateMonitorLogger$logUserRequestedUnlock$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String str1 = logMessage.getStr1();
                String str2 = logMessage.getStr2();
                boolean bool1 = logMessage.getBool1();
                return "reportUserRequestedUnlock origin=" + str1 + " reason=" + str2 + " dismissKeyguard=" + bool1;
            }
        }, null);
        obtain.setStr1(active_unlock_request_origin.name());
        obtain.setStr2(str);
        obtain.setBool1(z);
        logBuffer.commit(obtain);
    }

    public final void v(@CompileTimeConstant String str) {
        log(str, LogLevel.VERBOSE);
    }

    public final void w(@CompileTimeConstant String str) {
        log(str, LogLevel.WARNING);
    }
}