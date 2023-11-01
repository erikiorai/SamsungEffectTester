package com.android.keyguard.logging;

import com.android.systemui.plugins.log.LogBuffer;
import com.android.systemui.plugins.log.LogLevel;
import com.android.systemui.plugins.log.LogMessage;
import com.google.errorprone.annotations.CompileTimeConstant;
import kotlin.jvm.functions.Function1;

/* loaded from: mainsysui33.jar:com/android/keyguard/logging/BiometricUnlockLogger.class */
public final class BiometricUnlockLogger {
    public final LogBuffer logBuffer;

    public BiometricUnlockLogger(LogBuffer logBuffer) {
        this.logBuffer = logBuffer;
    }

    public final void d(@CompileTimeConstant String str) {
        log(str, LogLevel.DEBUG);
    }

    public final void i(@CompileTimeConstant String str) {
        log(str, LogLevel.INFO);
    }

    public final void log(@CompileTimeConstant String str, LogLevel logLevel) {
        this.logBuffer.log("BiometricUnlockLogger", logLevel, str);
    }

    public final void logCalculateModeForFingerprintUnlockingAllowed(boolean z, boolean z2, boolean z3) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("BiometricUnlockLogger", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.BiometricUnlockLogger$logCalculateModeForFingerprintUnlockingAllowed$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                boolean bool3 = logMessage.getBool3();
                return "calculateModeForFingerprint unlockingAllowed=true deviceInteractive=" + bool1 + " isKeyguardShowing=" + bool2 + " deviceDreaming=" + bool3;
            }
        }, null);
        obtain.setBool1(z);
        obtain.setBool2(z2);
        obtain.setBool3(z3);
        logBuffer.commit(obtain);
    }

    public final void logCalculateModeForFingerprintUnlockingNotAllowed(boolean z, int i, boolean z2, boolean z3, boolean z4) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("BiometricUnlockLogger", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.BiometricUnlockLogger$logCalculateModeForFingerprintUnlockingNotAllowed$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                boolean bool1 = logMessage.getBool1();
                int int1 = logMessage.getInt1();
                boolean bool2 = logMessage.getBool2();
                boolean bool3 = logMessage.getBool3();
                boolean bool4 = logMessage.getBool4();
                return "calculateModeForFingerprint unlockingAllowed=false strongBiometric=" + bool1 + " strongAuthFlags=" + int1 + " nonStrongBiometricAllowed=" + bool2 + " deviceInteractive=" + bool3 + " isKeyguardShowing=" + bool4;
            }
        }, null);
        obtain.setInt1(i);
        obtain.setBool1(z);
        obtain.setBool2(z2);
        obtain.setBool3(z3);
        obtain.setBool4(z4);
        logBuffer.commit(obtain);
    }

    public final void logCalculateModeForPassiveAuthUnlockingAllowed(boolean z, boolean z2, boolean z3, boolean z4) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("BiometricUnlockLogger", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.BiometricUnlockLogger$logCalculateModeForPassiveAuthUnlockingAllowed$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                boolean bool1 = logMessage.getBool1();
                boolean bool2 = logMessage.getBool2();
                boolean bool3 = logMessage.getBool3();
                boolean bool4 = logMessage.getBool4();
                return "calculateModeForPassiveAuth unlockingAllowed=true deviceInteractive=" + bool1 + " isKeyguardShowing=" + bool2 + " deviceDreaming=" + bool3 + " bypass=" + bool4;
            }
        }, null);
        obtain.setBool1(z);
        obtain.setBool2(z2);
        obtain.setBool3(z3);
        obtain.setBool4(z4);
        logBuffer.commit(obtain);
    }

    public final void logCalculateModeForPassiveAuthUnlockingNotAllowed(boolean z, int i, boolean z2, boolean z3, boolean z4, boolean z5) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("BiometricUnlockLogger", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.BiometricUnlockLogger$logCalculateModeForPassiveAuthUnlockingNotAllowed$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                boolean z6 = true;
                if (logMessage.getInt1() != 1) {
                    z6 = false;
                }
                return "calculateModeForPassiveAuth unlockingAllowed=false strongBiometric=" + z6 + " strongAuthFlags=" + logMessage.getInt2() + " nonStrongBiometricAllowed=" + logMessage.getBool1() + " deviceInteractive=" + logMessage.getBool2() + " isKeyguardShowing=" + logMessage.getBool3() + " bypass=" + logMessage.getBool4();
            }
        }, null);
        obtain.setInt1(z ? 1 : 0);
        obtain.setInt2(i);
        obtain.setBool1(z2);
        obtain.setBool2(z3);
        obtain.setBool3(z4);
        obtain.setBool4(z5);
        logBuffer.commit(obtain);
    }

    public final void logStartWakeAndUnlock(int i) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("BiometricUnlockLogger", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.BiometricUnlockLogger$logStartWakeAndUnlock$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                String wakeAndUnlockModeToString;
                wakeAndUnlockModeToString = BiometricUnlockLoggerKt.wakeAndUnlockModeToString(logMessage.getInt1());
                return "startWakeAndUnlock(" + wakeAndUnlockModeToString + ")";
            }
        }, null);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }

    public final void logUdfpsAttemptThresholdMet(int i) {
        LogBuffer logBuffer = this.logBuffer;
        LogMessage obtain = logBuffer.obtain("BiometricUnlockLogger", LogLevel.DEBUG, new Function1<LogMessage, String>() { // from class: com.android.keyguard.logging.BiometricUnlockLogger$logUdfpsAttemptThresholdMet$2
            /* JADX DEBUG: Method merged with bridge method */
            public final String invoke(LogMessage logMessage) {
                int int1 = logMessage.getInt1();
                return "udfpsAttemptThresholdMet consecutiveFailedAttempts=" + int1;
            }
        }, null);
        obtain.setInt1(i);
        logBuffer.commit(obtain);
    }
}